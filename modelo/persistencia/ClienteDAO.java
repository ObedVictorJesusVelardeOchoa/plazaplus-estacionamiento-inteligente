package modelo.persistencia;

import modelo.entidades.Cliente;
import modelo.entidades.TipoUsuario;
import modelo.estructuras.ListaEnlazada;
import utilidad.ManejadorArchivos;

/**
 * DAO para gestionar clientes en archivo TXT.
 * Formato: dni|nombres|apellidos|correo|telefono|direccion|genero|tipoUsuario|vehiculos
 * vehiculos: lista separada por comas (ej: ABC123,XYZ789)
 */
public class ClienteDAO {
  
  private static final String ARCHIVO = "clientes.txt";
  
  /**
   * Convierte ListaEnlazada a String separado por comas.
   */
  private static String listaAString(ListaEnlazada<String> lista) {
    if (lista.getTamano() == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < lista.getTamano(); i++) {
      if (i > 0) sb.append(",");
      sb.append(lista.get(i));
    }
    return sb.toString();
  }
  
  /**
   * Guarda un cliente en el archivo.
   */
  public static boolean guardarCliente(Cliente cliente) {
    // Verificar si ya existe
    if (buscarCliente(cliente.getDni()) != null) {
      return actualizarCliente(cliente);
    }
    
    String vehiculos = listaAString(cliente.getPlacasVehiculos());
    String linea = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
      cliente.getDni(),
      cliente.getNombres(),
      cliente.getApellidos(),
      cliente.getCorreo(),
      cliente.getTelefono(),
      cliente.getDireccion(),
      cliente.getGenero(),
      cliente.getTipoUsuario(),
      vehiculos
    );
    
    return ManejadorArchivos.agregarLinea(ARCHIVO, linea);
  }
  
  /**
   * Busca un cliente por DNI.
   */
  public static Cliente buscarCliente(String dni) {
    String linea = ManejadorArchivos.buscarLinea(ARCHIVO, dni + "|");
    if (linea == null) {
      return null;
    }
    
    return parsearCliente(linea);
  }
  
  /**
   * Carga todos los clientes.
   */
  public static ListaEnlazada<Cliente> cargarTodos() {
    ListaEnlazada<Cliente> clientes = new ListaEnlazada<>();
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      Cliente cliente = parsearCliente(lineas.get(i));
      if (cliente != null) {
        clientes.agregarAlFinal(cliente);
      }
    }
    
    return clientes;
  }
  
  /**
   * Actualiza un cliente existente.
   */
  public static boolean actualizarCliente(Cliente cliente) {
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      String linea = lineas.get(i);
      if (linea.startsWith(cliente.getDni() + "|")) {
        String vehiculos = listaAString(cliente.getPlacasVehiculos());
        String lineaNueva = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
          cliente.getDni(),
          cliente.getNombres(),
          cliente.getApellidos(),
          cliente.getCorreo(),
          cliente.getTelefono(),
          cliente.getDireccion(),
          cliente.getGenero(),
          cliente.getTipoUsuario(),
          vehiculos
        );
        
        return ManejadorArchivos.actualizarLinea(ARCHIVO, linea, lineaNueva);
      }
    }
    
    return false;
  }
  
  /**
   * Parsea una línea a objeto Cliente.
   */
  private static Cliente parsearCliente(String linea) {
    try {
      String[] datos = linea.split("\\|");
      if (datos.length < 8) {
        return null;
      }
      
      String dni = datos[0];
      String nombres = datos[1];
      String apellidos = datos[2];
      String correo = datos[3];
      String telefono = datos[4];
      String direccion = datos[5];
      String genero = datos[6];
      
      // Limpiar el tipo de usuario (remover "Cliente " y convertir a mayúsculas)
      String tipoStr = datos[7].replace("Cliente ", "").trim().toUpperCase();
      TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipoStr);
      
      Cliente cliente = new Cliente(dni, nombres, apellidos, correo, telefono, direccion, genero, tipoUsuario);
      
      // Agregar vehículos si existen
      if (datos.length > 8 && !datos[8].isEmpty()) {
        String[] vehiculos = datos[8].split(",");
        for (String placa : vehiculos) {
          if (!placa.trim().isEmpty()) {
            cliente.agregarVehiculo(placa.trim());
          }
        }
      }
      
      return cliente;
    } catch (Exception e) {
      System.err.println("Error al parsear cliente: " + e.getMessage());
      return null;
    }
  }
}
