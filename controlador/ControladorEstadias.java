package controlador;

import modelo.entidades.Ticket;
import modelo.entidades.TipoVehiculo;
import modelo.estructuras.ListaEnlazada;
import modelo.estructuras.Pila;
import modelo.gestores.GestorPrincipal;
import modelo.gestores.SistemaEstacionamiento;

/**
 * Controlador para gestionar ingresos y salidas del estacionamiento.
 * Maneja el flujo completo integrado con validaciones y cálculos de tarifas.
 */
public class ControladorEstadias {
  
  private final GestorPrincipal gestorPrincipal;
  
  public ControladorEstadias() {
    this.gestorPrincipal = SistemaEstacionamiento.getInstancia().getGestorPrincipal();
  }
  
  /**
   * Registra el ingreso de un vehículo al estacionamiento.
   * 
   * Flujo completo:
   * 1. Valida que el vehículo esté registrado (si no, lo crea)
   * 2. Valida que el cliente esté registrado (si no, lo crea)
   * 3. Asocia vehículo con cliente si es necesario
   * 4. Busca plaza disponible según tipo de vehículo
   * 5. Si hay plaza: crea ticket y lo registra
   * 6. Si NO hay plaza: agrega a cola de espera
   * 
   * @param placa Placa del vehículo
   * @param tipo Tipo de vehículo
   * @param dniCliente DNI del cliente/conductor
   * @return Ticket creado si hubo plaza, null si se agregó a cola de espera
   */
  public Ticket registrarIngreso(String placa, TipoVehiculo tipo, String dniCliente) {
    if (placa == null || placa.trim().isEmpty()) {
      return null;
    }
    
    if (tipo == null) {
      return null;
    }
    
    if (dniCliente == null || dniCliente.trim().isEmpty()) {
      return null;
    }
    
    return gestorPrincipal.registrarIngreso(
      placa.trim().toUpperCase(), 
      tipo, 
      dniCliente.trim()
    );
  }
  
  /**
   * Registra la salida de un vehículo del estacionamiento.
   * 
   * Flujo completo:
   * 1. Busca el ticket activo por placa
   * 2. Calcula el tiempo de estadía
   * 3. Aplica tarifa según tipo de vehículo
   * 4. Aplica descuento según tipo de usuario (ABONADO 20%, FRECUENTE 10%)
   * 5. Marca el ticket como pagado
   * 6. Libera la plaza
   * 7. Si hay vehículos en cola de espera, asigna la plaza al siguiente
   * 
   * @param placa Placa del vehículo que sale
   * @return Ticket con el monto calculado, null si no se encontró
   */
  public Ticket registrarSalida(String placa) {
    if (placa == null || placa.trim().isEmpty()) {
      return null;
    }
    
    return gestorPrincipal.registrarSalida(placa.trim().toUpperCase());
  }
  
  /**
   * Registra el pago de un ticket.
   * @param codigoTicket Código del ticket a pagar
   * @return true si se registró el pago correctamente
   */
  public boolean registrarPago(String codigoTicket) {
    if (codigoTicket == null || codigoTicket.trim().isEmpty()) {
      return false;
    }
    
    return gestorPrincipal.registrarPago(codigoTicket);
  }
  
  /**
   * Obtiene todos los tickets activos (vehículos actualmente en el estacionamiento).
   * @return Lista de tickets activos
   */
  public ListaEnlazada<Ticket> obtenerTicketsActivos() {
    return gestorPrincipal.getTicketsActivos();
  }
  
  /**
   * Obtiene el historial de tickets (últimas operaciones).
   * @return Pila con historial de tickets
   */
  public Pila<Ticket> obtenerHistorialTickets() {
    return gestorPrincipal.getHistorialTickets();
  }
  
  /**
   * Obtiene la cantidad de vehículos en estacionamiento.
   * @return Cantidad de vehículos activos
   */
  public int getCantidadVehiculosEnEstacionamiento() {
    return gestorPrincipal.getCantidadVehiculosEnEstacionamiento();
  }
  
  /**
   * Obtiene la cantidad de vehículos en espera.
   * @return Cantidad de solicitudes en cola
   */
  public int getCantidadEnEspera() {
    return gestorPrincipal.getCantidadEnEspera();
  }
  
  /**
   * Busca un ticket activo por placa de vehículo.
   * @param placa Placa del vehículo
   * @return Ticket encontrado o null
   */
  public Ticket buscarTicketPorPlaca(String placa) {
    if (placa == null || placa.trim().isEmpty()) {
      return null;
    }
    
    ListaEnlazada<Ticket> activos = obtenerTicketsActivos();
    for (int i = 0; i < activos.getTamano(); i++) {
      Ticket t = activos.get(i);
      if (t != null && t.getVehiculo() != null && 
          t.getVehiculo().getPlaca().equalsIgnoreCase(placa.trim())) {
        return t;
      }
    }
    
    return null;
  }
  
  /**
   * Busca un ticket por su código.
   * @param codigo Código del ticket
   * @return Ticket encontrado o null
   */
  public Ticket buscarTicketPorCodigo(String codigo) {
    if (codigo == null || codigo.trim().isEmpty()) {
      return null;
    }
    
    ListaEnlazada<Ticket> activos = obtenerTicketsActivos();
    for (int i = 0; i < activos.getTamano(); i++) {
      Ticket t = activos.get(i);
      if (t != null && t.getCodigo().equals(codigo)) {
        return t;
      }
    }
    
    return null;
  }
  
  /**
   * Obtiene estadísticas de tickets.
   * @return String con estadísticas formateadas
   */
  public String obtenerEstadisticas() {
    int totalActivos = getCantidadVehiculosEnEstacionamiento();
    int totalEspera = getCantidadEnEspera();
    Pila<Ticket> historial = obtenerHistorialTickets();
    int totalHistorial = historial.getTamano();
    
    double ingresoTotal = 0.0;
    Pila<Ticket> temp = new Pila<>();
    
    // Calcular ingreso total sin perder datos de la pila
    while (!historial.estaVacia()) {
      Ticket t = historial.desapilar();
      if (t != null && t.isPagado()) {
        ingresoTotal += t.getMontoAPagar();
      }
      temp.apilar(t);
    }
    
    // Restaurar la pila original
    while (!temp.estaVacia()) {
      historial.apilar(temp.desapilar());
    }
    
    return String.format(
      "Tickets Activos: %d | Historial: %d | En Espera: %d | Ingreso Total: S/. %.2f",
      totalActivos, totalHistorial, totalEspera, ingresoTotal
    );
  }
}
