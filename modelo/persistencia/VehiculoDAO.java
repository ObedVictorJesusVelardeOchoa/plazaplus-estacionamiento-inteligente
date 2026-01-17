package modelo.persistencia;

import modelo.entidades.Vehiculo;
import modelo.entidades.TipoVehiculo;
import modelo.estructuras.ListaEnlazada;
import utilidad.ManejadorArchivos;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DAO para gestionar vehículos en archivo TXT.
 * Formato: placa|tipo|dniPropietario|marca|modelo|color|fechaRegistro
 */
public class VehiculoDAO {
  
  private static final String ARCHIVO = "vehiculos.txt";
  private static final DateTimeFormatter FORMATO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  
  /**
   * Guarda un vehículo en el archivo.
   */
  public static boolean guardarVehiculo(Vehiculo vehiculo) {
    // Verificar si ya existe
    if (buscarVehiculo(vehiculo.getPlaca()) != null) {
      return actualizarVehiculo(vehiculo);
    }
    
    String linea = String.format("%s|%s|%s|%s|%s|%s|%s",
      vehiculo.getPlaca(),
      vehiculo.getTipo(),
      vehiculo.getPropietario(),
      vehiculo.getMarca() != null ? vehiculo.getMarca() : "",
      vehiculo.getModelo() != null ? vehiculo.getModelo() : "",
      vehiculo.getColor() != null ? vehiculo.getColor() : "",
      vehiculo.getFechaRegistro().format(FORMATO)
    );
    
    return ManejadorArchivos.agregarLinea(ARCHIVO, linea);
  }
  
  /**
   * Busca un vehículo por placa.
   */
  public static Vehiculo buscarVehiculo(String placa) {
    String linea = ManejadorArchivos.buscarLinea(ARCHIVO, placa.toUpperCase() + "|");
    if (linea == null) {
      return null;
    }
    
    return parsearVehiculo(linea);
  }
  
  /**
   * Carga todos los vehículos.
   */
  public static ListaEnlazada<Vehiculo> cargarTodos() {
    ListaEnlazada<Vehiculo> vehiculos = new ListaEnlazada<>();
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      Vehiculo vehiculo = parsearVehiculo(lineas.get(i));
      if (vehiculo != null) {
        vehiculos.agregarAlFinal(vehiculo);
      }
    }
    
    return vehiculos;
  }
  
  /**
   * Actualiza un vehículo existente.
   */
  public static boolean actualizarVehiculo(Vehiculo vehiculo) {
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      String linea = lineas.get(i);
      if (linea.toUpperCase().startsWith(vehiculo.getPlaca().toUpperCase() + "|")) {
        String lineaNueva = String.format("%s|%s|%s|%s|%s|%s|%s",
          vehiculo.getPlaca(),
          vehiculo.getTipo(),
          vehiculo.getPropietario(),
          vehiculo.getMarca() != null ? vehiculo.getMarca() : "",
          vehiculo.getModelo() != null ? vehiculo.getModelo() : "",
          vehiculo.getColor() != null ? vehiculo.getColor() : "",
          vehiculo.getFechaRegistro().format(FORMATO)
        );
        
        return ManejadorArchivos.actualizarLinea(ARCHIVO, linea, lineaNueva);
      }
    }
    
    return false;
  }
  
  /**
   * Parsea una línea a objeto Vehiculo.
   */
  private static Vehiculo parsearVehiculo(String linea) {
    try {
      String[] datos = linea.split("\\|");
      if (datos.length < 7) {
        return null;
      }
      
      String placa = datos[0];
      TipoVehiculo tipo = TipoVehiculo.valueOf(datos[1]);
      String dniPropietario = datos[2];
      String marca = datos[3].isEmpty() ? null : datos[3];
      String modelo = datos[4].isEmpty() ? null : datos[4];
      String color = datos[5].isEmpty() ? null : datos[5];
      LocalDateTime fechaRegistro = LocalDateTime.parse(datos[6], FORMATO);
      
      Vehiculo vehiculo = new Vehiculo(placa, tipo, dniPropietario, fechaRegistro);
      if (marca != null) vehiculo.setMarca(marca);
      if (modelo != null) vehiculo.setModelo(modelo);
      if (color != null) vehiculo.setColor(color);
      
      return vehiculo;
    } catch (Exception e) {
      System.err.println("Error al parsear vehículo: " + e.getMessage());
      return null;
    }
  }
}
