package modelo.gestores;

import modelo.entidades.Cliente;
import modelo.entidades.Vehiculo;
import modelo.entidades.Ticket;
import modelo.entidades.Ticket.EstadoTicket;
import modelo.entidades.Plaza;
import modelo.entidades.TipoVehiculo;
import modelo.entidades.TipoUsuario;
import modelo.entidades.EstadoPlaza;
import modelo.entidades.SolicitudEspera;
import modelo.estructuras.ArbolBinario;
import modelo.estructuras.ListaEnlazada;
import modelo.estructuras.Cola;
import modelo.estructuras.Pila;
import modelo.estructuras.NodoArbol;
import modelo.persistencia.ClienteDAO;
import modelo.persistencia.VehiculoDAO;
import modelo.persistencia.TicketDAO;
import utilidad.ManejadorArchivos;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Comparator;

/**
 * Gestor Principal que integra TODOS los componentes del sistema de estacionamiento.
 * Utiliza las estructuras de datos de manera óptima:
 * - Árboles Binarios para búsquedas rápidas (O(log n))
 * - Lista Enlazada para tickets activos
 * - Cola para lista de espera (FIFO)
 * - Pila para historial (LIFO)
 */
public class GestorPrincipal {
  
  // === ÁRBOLES BINARIOS PARA BÚSQUEDAS RÁPIDAS ===
  private ArbolBinario<Cliente> arbolClientes;
  private ArbolBinario<Vehiculo> arbolVehiculos;
  
  // === GESTIÓN DE TICKETS ===
  private ListaEnlazada<Ticket> ticketsActivos;    // Estadías en curso
  private Pila<Ticket> historialTickets;            // Últimas operaciones
  
  // === GESTIÓN DE PLAZAS Y ESPERA ===
  private ListaEnlazada<Plaza> plazas;              // Todas las plazas del estacionamiento
  private Cola<SolicitudEspera> colaEspera;
  
  // === CONTADORES ===
  private int contadorTickets;
  
  // === TARIFAS POR HORA SEGÚN TIPO DE VEHÍCULO ===
  private static final double TARIFA_BICICLETA = 1.0;
  private static final double TARIFA_MOTO = 2.0;
  private static final double TARIFA_AUTO = 3.5;
  private static final double TARIFA_SUV = 4.0;
  private static final double TARIFA_MINIVAN = 4.5;
  
  /**
   * Constructor del gestor principal.
   * AHORA CARGA DATOS DESDE ARCHIVOS TXT.
   */
  public GestorPrincipal() {
    // Inicializar archivos de datos
    ManejadorArchivos.inicializarArchivos();
    
    // Inicializar árboles binarios
    this.arbolClientes = new ArbolBinario<>(Comparator.comparing(Cliente::getDni));
    this.arbolVehiculos = new ArbolBinario<>(Comparator.comparing(Vehiculo::getPlaca));
    
    // Inicializar estructuras de tickets
    this.ticketsActivos = new ListaEnlazada<>();
    this.historialTickets = new Pila<>();
    
    // Inicializar gestión de plazas (3 pisos, 6 sectores, 10 plazas/sector)
    this.plazas = new ListaEnlazada<>();
    inicializarPlazas(3, 6, 10);
    this.colaEspera = new Cola<>();
    
    this.contadorTickets = 1000;
    
    // Cargar datos desde archivos TXT
    cargarDatosDesdeArchivos();
  }
  
  /**
   * Inicializa las plazas del estacionamiento.
   * @param numPisos Número de pisos
   * @param numSectores Número de sectores por piso
   * @param numPlazasPorSector Número de plazas por sector
   */
  private void inicializarPlazas(int numPisos, int numSectores, int numPlazasPorSector) {
    for (int piso = 1; piso <= numPisos; piso++) {
      for (char sector = 'A'; sector < 'A' + numSectores; sector++) {
        for (int numero = 1; numero <= numPlazasPorSector; numero++) {
          Plaza plaza = new Plaza(numero, piso, sector);
          plaza.setEstado(EstadoPlaza.LIBRE);
          plazas.agregarAlFinal(plaza);
        }
      }
    }
  }
  
  /**
   * Busca una plaza libre para el tipo de vehículo especificado.
   * @param tipo Tipo de vehículo
   * @return Plaza libre o null si no hay disponibles
   */
  private Plaza buscarPlazaLibre(TipoVehiculo tipo) {
    for (int i = 0; i < plazas.getTamano(); i++) {
      Plaza plaza = plazas.get(i);
      if (plaza.getEstado() == EstadoPlaza.LIBRE) {
        return plaza;
      }
    }
    return null;
  }
  
  /**
   * Carga datos desde archivos TXT al sistema.
   */
  private void cargarDatosDesdeArchivos() {
    System.out.println("Cargando datos desde archivos TXT...");
    
    // Cargar clientes
    ListaEnlazada<Cliente> clientes = ClienteDAO.cargarTodos();
    for (int i = 0; i < clientes.getTamano(); i++) {
      arbolClientes.insertar(clientes.get(i));
    }
    System.out.println("Clientes cargados: " + clientes.getTamano());
    
    // Cargar vehículos
    ListaEnlazada<Vehiculo> vehiculos = VehiculoDAO.cargarTodos();
    for (int i = 0; i < vehiculos.getTamano(); i++) {
      arbolVehiculos.insertar(vehiculos.get(i));
    }
    System.out.println("Vehículos cargados: " + vehiculos.getTamano());
    
    // Cargar tickets
    ListaEnlazada<Ticket> tickets = TicketDAO.cargarTodos();
    for (int i = 0; i < tickets.getTamano(); i++) {
      Ticket ticket = tickets.get(i);
      ticketsActivos.agregarAlFinal(ticket);
      
      // Si el ticket está activo, ocupar la plaza
      if (ticket.getEstado() == EstadoTicket.ACTIVO) {
        Plaza plaza = ticket.getPlaza();
        plaza.setEstado(EstadoPlaza.OCUPADA);
        plaza.setVehiculoActual(ticket.getVehiculo());
        plaza.setHoraOcupacion(ticket.getHoraIngreso());
      }
    }
    System.out.println("Tickets cargados: " + tickets.getTamano());
    
    // Ajustar contador de tickets
    if (tickets.getTamano() > 0) {
      // Buscar el código más alto
      int maxCodigo = 1000;
      for (int i = 0; i < tickets.getTamano(); i++) {
        String codigo = tickets.get(i).getCodigo();
        if (codigo.startsWith("T-")) {
          try {
            int num = Integer.parseInt(codigo.substring(2));
            if (num >= maxCodigo) {
              maxCodigo = num + 1;
            }
          } catch (NumberFormatException e) {
            // Ignorar códigos inválidos
          }
        }
      }
      contadorTickets = maxCodigo;
    }
    
    System.out.println("Datos cargados exitosamente desde archivos TXT.");
  }
  
  // ============================================================================
  // GESTIÓN DE CLIENTES
  // ============================================================================
  
  /**
   * Registra un nuevo cliente en el sistema.
   * Usa árbol binario para búsqueda rápida de duplicados.
   */
  public boolean registrarCliente(Cliente cliente) {
    if (cliente == null || cliente.getDni() == null) {
      return false;
    }
    
    // Verificar duplicados usando árbol (O(log n))
    if (buscarCliente(cliente.getDni()) != null) {
      return false;
    }
    
    arbolClientes.insertar(cliente);
    
    // Guardar en archivo TXT
    ClienteDAO.guardarCliente(cliente);
    
    return true;
  }
  
  /**
   * Busca un cliente por DNI usando el árbol binario (O(log n)).
   */
  public Cliente buscarCliente(String dni) {
    return buscarClienteEnArbol(arbolClientes.getRaiz(), dni);
  }
  
  private Cliente buscarClienteEnArbol(NodoArbol<Cliente> nodo, String dni) {
    if (nodo == null) {
      return null;
    }
    
    int comparacion = dni.compareTo(nodo.getDato().getDni());
    if (comparacion == 0) {
      return nodo.getDato();
    } else if (comparacion < 0) {
      return buscarClienteEnArbol(nodo.getIzquierdo(), dni);
    } else {
      return buscarClienteEnArbol(nodo.getDerecho(), dni);
    }
  }
  
  /**
   * Obtiene todos los clientes (recorrido inorden del árbol).
   */
  public ListaEnlazada<Cliente> obtenerTodosLosClientes() {
    ListaEnlazada<Cliente> lista = new ListaEnlazada<>();
    recorridoInordenClientes(arbolClientes.getRaiz(), lista);
    return lista;
  }
  
  private void recorridoInordenClientes(NodoArbol<Cliente> nodo, ListaEnlazada<Cliente> lista) {
    if (nodo != null) {
      recorridoInordenClientes(nodo.getIzquierdo(), lista);
      lista.agregarAlFinal(nodo.getDato());
      recorridoInordenClientes(nodo.getDerecho(), lista);
    }
  }
  
  // ============================================================================
  // GESTIÓN DE VEHÍCULOS
  // ============================================================================
  
  /**
   * Registra un nuevo vehículo en el sistema.
   * Valida que el propietario (cliente) exista.
   */
  public boolean registrarVehiculo(Vehiculo vehiculo) {
    if (vehiculo == null || vehiculo.getPlaca() == null) {
      return false;
    }
    
    // Verificar que no exista la placa
    if (buscarVehiculo(vehiculo.getPlaca()) != null) {
      return false;
    }
    
    // Verificar que el cliente propietario exista
    Cliente propietario = buscarCliente(vehiculo.getPropietario());
    if (propietario == null) {
      return false; // No se puede registrar vehículo sin cliente
    }
    
    arbolVehiculos.insertar(vehiculo);
    propietario.agregarVehiculo(vehiculo.getPlaca());
    
    // Guardar vehículo en archivo TXT
    VehiculoDAO.guardarVehiculo(vehiculo);
    
    // Actualizar cliente en archivo TXT
    ClienteDAO.actualizarCliente(propietario);
    
    return true;
  }
  
  /**
   * Busca un vehículo por placa usando el árbol binario (O(log n)).
   */
  public Vehiculo buscarVehiculo(String placa) {
    return buscarVehiculoEnArbol(arbolVehiculos.getRaiz(), placa);
  }
  
  private Vehiculo buscarVehiculoEnArbol(NodoArbol<Vehiculo> nodo, String placa) {
    if (nodo == null) {
      return null;
    }
    
    int comparacion = placa.toUpperCase().compareTo(nodo.getDato().getPlaca());
    if (comparacion == 0) {
      return nodo.getDato();
    } else if (comparacion < 0) {
      return buscarVehiculoEnArbol(nodo.getIzquierdo(), placa);
    } else {
      return buscarVehiculoEnArbol(nodo.getDerecho(), placa);
    }
  }
  
  /**
   * Obtiene todos los vehículos (recorrido inorden del árbol).
   */
  public ListaEnlazada<Vehiculo> obtenerTodosLosVehiculos() {
    ListaEnlazada<Vehiculo> lista = new ListaEnlazada<>();
    recorridoInordenVehiculos(arbolVehiculos.getRaiz(), lista);
    return lista;
  }
  
  private void recorridoInordenVehiculos(NodoArbol<Vehiculo> nodo, ListaEnlazada<Vehiculo> lista) {
    if (nodo != null) {
      recorridoInordenVehiculos(nodo.getIzquierdo(), lista);
      lista.agregarAlFinal(nodo.getDato());
      recorridoInordenVehiculos(nodo.getDerecho(), lista);
    }
  }
  
  /**
   * Busca todos los vehículos de un propietario específico.
   */
  public ListaEnlazada<Vehiculo> buscarVehiculosPorPropietario(String dniPropietario) {
    ListaEnlazada<Vehiculo> resultado = new ListaEnlazada<>();
    ListaEnlazada<Vehiculo> todos = obtenerTodosLosVehiculos();
    
    for (int i = 0; i < todos.getTamano(); i++) {
      Vehiculo v = todos.get(i);
      if (v != null && v.getPropietario() != null && 
          v.getPropietario().equals(dniPropietario)) {
        resultado.agregarAlFinal(v);
      }
    }
    
    return resultado;
  }
  
  // ============================================================================
  // GESTIÓN DE INGRESOS Y SALIDAS
  // ============================================================================
  
  /**
   * Registra el ingreso de un vehículo al estacionamiento.
   * Flujo completo integrado:
   * 1. Validar/registrar vehículo
   * 2. Validar/registrar cliente
   * 3. Buscar plaza disponible
   * 4. Crear ticket
   * 5. Si no hay plaza, agregar a cola de espera
   */
  public Ticket registrarIngreso(String placa, TipoVehiculo tipo, String dniPropietario) {
    placa = placa.toUpperCase();
    
    // 1. Buscar o registrar vehículo
    Vehiculo vehiculo = buscarVehiculo(placa);
    if (vehiculo == null) {
      // Vehículo no registrado, crear uno temporal
      vehiculo = new Vehiculo(placa, tipo, dniPropietario, LocalDateTime.now());
      arbolVehiculos.insertar(vehiculo);
    }
    
    // 2. Buscar o registrar cliente
    Cliente cliente = buscarCliente(dniPropietario);
    if (cliente == null) {
      // Cliente no registrado, crear uno temporal (REGULAR)
      cliente = new Cliente(dniPropietario, "Cliente", "Temporal", 
                           "", "", "", "", TipoUsuario.REGULAR);
      arbolClientes.insertar(cliente);
    }
    
    // Asociar vehículo al cliente
    if (!cliente.tieneVehiculo(placa)) {
      cliente.agregarVehiculo(placa);
    }
    
    // 3. Verificar que el vehículo no esté ya dentro
    if (buscarTicketActivo(placa) != null) {
      return null; // Ya tiene un ticket activo
    }
    
    // 4. Buscar plaza disponible
    Plaza plazaAsignada = buscarPlazaLibre(tipo);
    
    if (plazaAsignada != null) {
      // HAY PLAZA DISPONIBLE
      // Actualizar plaza
      plazaAsignada.setVehiculoActual(vehiculo);
      plazaAsignada.setEstado(EstadoPlaza.OCUPADA);
      plazaAsignada.setHoraOcupacion(LocalDateTime.now());
      
      // Crear ticket
      String codigoTicket = generarCodigoTicket();
      Ticket ticket = new Ticket(codigoTicket, vehiculo, plazaAsignada, 
                                 LocalDateTime.now(), null, 0.0, false);
      
      // Registrar en estructuras
      ticketsActivos.agregarAlFinal(ticket);
      historialTickets.apilar(ticket);
      
      // Guardar en archivo TXT
      TicketDAO.guardarTicket(ticket);
      
      // Guardar vehículo y cliente si son nuevos
      VehiculoDAO.guardarVehiculo(vehiculo);
      ClienteDAO.guardarCliente(cliente);
      
      return ticket;
    } else {
      // NO HAY PLAZA - Agregar a cola de espera
      SolicitudEspera solicitud = new SolicitudEspera(placa, tipo, LocalDateTime.now());
      colaEspera.encolar(solicitud);
      return null;
    }
  }
  
  /**
   * Registra la salida de un vehículo.
   * Calcula el monto según tiempo, tipo de vehículo y descuentos.
   */
  public Ticket registrarSalida(String placa) {
    placa = placa.toUpperCase();
    
    // 1. Buscar ticket activo
    Ticket ticket = buscarTicketActivo(placa);
    if (ticket == null) {
      return null;
    }
    
    // 2. Registrar salida
    ticket.setHoraSalida(LocalDateTime.now());
    
    // 3. Calcular monto
    double monto = calcularMonto(ticket);
    ticket.setMontoAPagar(monto);
    
    // 4. Cambiar estado del ticket a FINALIZADO
    ticket.setEstado(EstadoTicket.FINALIZADO);
    
    // 5. Actualizar ticket en archivo TXT
    TicketDAO.actualizarTicket(ticket);
    
    // 6. Liberar plaza
    Plaza plaza = ticket.getPlaza();
    if (plaza != null) {
      plaza.setEstado(EstadoPlaza.LIBRE);
      plaza.setVehiculoActual(null);
      plaza.setHoraOcupacion(null);
    }
    
    // 7. Procesar siguiente en cola de espera (si hay)
    if (!colaEspera.estaVacia()) {
      SolicitudEspera siguiente = colaEspera.desencolar();
      // Aquí se podría notificar al siguiente en espera
      System.out.println("Plaza liberada. Siguiente en espera: " + siguiente.getPlaca());
    }
    
    return ticket;
  }
  
  /**
   * Registra el pago de un ticket.
   */
  public boolean registrarPago(String codigoTicket) {
    // Buscar en historial
    Pila<Ticket> temp = new Pila<>();
    boolean encontrado = false;
    
    while (!historialTickets.estaVacia()) {
      Ticket t = historialTickets.desapilar();
      if (t.getCodigo().equals(codigoTicket)) {
        t.setPagado(true);
        encontrado = true;
      }
      temp.apilar(t);
    }
    
    // Restaurar historial
    while (!temp.estaVacia()) {
      historialTickets.apilar(temp.desapilar());
    }
    
    return encontrado;
  }
  
  // ============================================================================
  // MÉTODOS AUXILIARES
  // ============================================================================
  
  /**
   * Busca un ticket activo por placa.
   */
  private Ticket buscarTicketActivo(String placa) {
    for (int i = 0; i < ticketsActivos.getTamano(); i++) {
      Ticket t = ticketsActivos.get(i);
      if (t.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
        return t;
      }
    }
    return null;
  }
  
  
  /**
   * Calcula el monto a pagar según tiempo, tipo de vehículo y descuentos.
   */
  private double calcularMonto(Ticket ticket) {
    LocalDateTime ingreso = ticket.getHoraIngreso();
    LocalDateTime salida = ticket.getHoraSalida();
    
    // Calcular horas (redondear hacia arriba)
    long minutos = Duration.between(ingreso, salida).toMinutes();
    double horas = Math.max(1, Math.ceil(minutos / 60.0)); // Mínimo 1 hora
    
    // Tarifa base según tipo de vehículo
    TipoVehiculo tipo = ticket.getVehiculo().getTipo();
    double tarifaPorHora = obtenerTarifa(tipo);
    
    // Aplicar descuento según tipo de usuario
    Vehiculo vehiculo = ticket.getVehiculo();
    Cliente cliente = buscarCliente(vehiculo.getPropietario());
    
    if (cliente != null) {
      switch (cliente.getTipoUsuario()) {
        case ABONADO:
          tarifaPorHora *= 0.8; // 20% descuento
          break;
        case FRECUENTE:
          tarifaPorHora *= 0.9; // 10% descuento
          break;
        case REGULAR:
          // Sin descuento
          break;
      }
    }
    
    return Math.round(horas * tarifaPorHora * 100.0) / 100.0; // Redondear a 2 decimales
  }
  
  /**
   * Obtiene la tarifa por hora según el tipo de vehículo.
   */
  private double obtenerTarifa(TipoVehiculo tipo) {
    switch (tipo) {
      case BICICLETA: return TARIFA_BICICLETA;
      case MOTO: return TARIFA_MOTO;
      case AUTO: return TARIFA_AUTO;
      case SUV: return TARIFA_SUV;
      case MINIVAN: return TARIFA_MINIVAN;
      default: return TARIFA_AUTO;
    }
  }
  
  /**
   * Genera un código único para el ticket.
   */
  private String generarCodigoTicket() {
    return "T-" + String.format("%04d", contadorTickets++);
  }
  
  // ============================================================================
  // CONSULTAS Y ESTADÍSTICAS
  // ============================================================================
  
  /**
   * Obtiene TODOS los tickets (historial completo incluyendo activos y finalizados).
   */
  public ListaEnlazada<Ticket> getTodosLosTickets() {
    return ticketsActivos;
  }
  
  /**
   * Obtiene solo los tickets ACTIVOS (vehículos actualmente en estacionamiento).
   */
  public ListaEnlazada<Ticket> getTicketsActivos() {
    ListaEnlazada<Ticket> soloActivos = new ListaEnlazada<>();
    for (int i = 0; i < ticketsActivos.getTamano(); i++) {
      Ticket ticket = ticketsActivos.get(i);
      if (ticket.getEstado() == EstadoTicket.ACTIVO) {
        soloActivos.agregarAlFinal(ticket);
      }
    }
    return soloActivos;
  }
  
  public Pila<Ticket> getHistorialTickets() {
    return historialTickets;
  }
  
  public int getCantidadVehiculosEnEstacionamiento() {
    int count = 0;
    for (int i = 0; i < ticketsActivos.getTamano(); i++) {
      if (ticketsActivos.get(i).getEstado() == EstadoTicket.ACTIVO) {
        count++;
      }
    }
    return count;
  }
  
  public int getCantidadEnEspera() {
    return colaEspera.getTamano();
  }
  
  /**
   * Obtiene estadísticas completas del sistema.
   */
  public String obtenerEstadisticas() {
    int totalClientes = contarNodos(arbolClientes.getRaiz());
    int totalVehiculos = contarNodos(arbolVehiculos.getRaiz());
    int vehiculosActivos = ticketsActivos.getTamano();
    int vehiculosEnEspera = colaEspera.getTamano();
    int totalTickets = historialTickets.getTamano();
    
    return String.format(
      "═══════════════════════════════════════\n" +
      "  ESTADÍSTICAS DEL SISTEMA\n" +
      "═══════════════════════════════════════\n\n" +
      "Clientes registrados:        %d\n" +
      "Vehículos registrados:       %d\n" +
      "Vehículos en estacionamiento: %d\n" +
      "Vehículos en espera:         %d\n" +
      "Total tickets procesados:    %d\n",
      totalClientes, totalVehiculos, vehiculosActivos, vehiculosEnEspera, totalTickets
    );
  }
  
  private <T> int contarNodos(NodoArbol<T> nodo) {
    if (nodo == null) {
      return 0;
    }
    return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
  }
}
