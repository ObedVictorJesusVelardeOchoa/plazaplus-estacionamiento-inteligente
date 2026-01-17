package controlador;

import modelo.entidades.Cliente;
import modelo.entidades.TipoUsuario;
import modelo.estructuras.ListaEnlazada;
import modelo.gestores.GestorPrincipal;
import modelo.gestores.SistemaEstacionamiento;

/**
 * Controlador para gestionar las operaciones del módulo de Clientes/Usuarios.
 * Usa el GestorPrincipal para integración completa del sistema.
 */
public class ControladorClientes {
  
  private GestorPrincipal gestorPrincipal;
  
  public ControladorClientes() {
    this.gestorPrincipal = SistemaEstacionamiento.getInstancia().getGestorPrincipal();
  }
  
  /**
   * Registra un nuevo cliente en el sistema (integrado con GestorPrincipal).
   */
  public boolean registrarCliente(String dni, String nombres, String apellidos, 
                                   String correo, String telefono, String direccion, 
                                   String genero, TipoUsuario tipo, String placaInicial) {
    
    // Validaciones básicas
    if (dni == null || dni.trim().isEmpty() || dni.length() != 8) {
      return false;
    }
    
    if (nombres == null || nombres.trim().isEmpty()) {
      return false;
    }
    
    if (apellidos == null || apellidos.trim().isEmpty()) {
      return false;
    }
    
    Cliente nuevoCliente = new Cliente(dni, nombres, apellidos, correo, 
                                       telefono, direccion, genero, tipo);
    
    // Si proporcionó una placa, agregarla
    if (placaInicial != null && !placaInicial.trim().isEmpty()) {
      nuevoCliente.agregarVehiculo(placaInicial.trim().toUpperCase());
    }
    
    return gestorPrincipal.registrarCliente(nuevoCliente);
  }
  
  /**
   * Busca un cliente por DNI (usa árbol binario O(log n)).
   */
  public Cliente buscarPorDni(String dni) {
    return gestorPrincipal.buscarCliente(dni);
  }
  
  /**
   * Obtiene todos los clientes (recorrido inorden del árbol).
   */
  public ListaEnlazada<Cliente> obtenerTodosLosClientes() {
    return gestorPrincipal.obtenerTodosLosClientes();
  }
  
  /**
   * Agrega un vehículo a un cliente.
   */
  public boolean agregarVehiculoACliente(String dni, String placa) {
    Cliente cliente = gestorPrincipal.buscarCliente(dni);
    if (cliente != null && placa != null && !placa.trim().isEmpty()) {
      cliente.agregarVehiculo(placa.trim().toUpperCase());
      return true;
    }
    return false;
  }
  
  /**
   * Elimina un vehículo de un cliente.
   */
  public boolean eliminarVehiculoDeCliente(String dni, String placa) {
    Cliente cliente = gestorPrincipal.buscarCliente(dni);
    if (cliente != null && placa != null) {
      cliente.eliminarVehiculo(placa.trim().toUpperCase());
      return true;
    }
    return false;
  }
  
  /**
   * Modifica un cliente existente.
   */
  public boolean modificarCliente(String dni, String nombres, String apellidos,
                                   String correo, String telefono, String direccion,
                                   String genero, TipoUsuario tipo) {
    Cliente cliente = gestorPrincipal.buscarCliente(dni);
    if (cliente == null) {
      return false;
    }
    
    cliente.setNombres(nombres);
    cliente.setApellidos(apellidos);
    cliente.setCorreo(correo);
    cliente.setTelefono(telefono);
    cliente.setDireccion(direccion);
    cliente.setGenero(genero);
    cliente.setTipoUsuario(tipo);
    
    return true;
  }
  
  /**
   * Elimina (desactiva) un cliente.
   */
  public boolean eliminarCliente(String dni) {
    Cliente cliente = gestorPrincipal.buscarCliente(dni);
    if (cliente == null) {
      return false;
    }
    
    cliente.setActivo(false);
    return true;
  }
}
