package modelo.persistencia;

import modelo.entidades.Ticket;
import modelo.entidades.Ticket.EstadoTicket;
import modelo.entidades.Vehiculo;
import modelo.entidades.Plaza;
import modelo.estructuras.ListaEnlazada;
import utilidad.ManejadorArchivos;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DAO para gestionar tickets en archivo TXT.
 * Formato: codigo|placa|piso|sector|numero|horaIngreso|horaSalida|monto|pagado|estado
 */
public class TicketDAO {
  
  private static final String ARCHIVO = "tickets.txt";
  private static final DateTimeFormatter FORMATO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  
  /**
   * Guarda un ticket en el archivo.
   * Si ya existe, lo actualiza.
   */
  public static boolean guardarTicket(Ticket ticket) {
    // Verificar si ya existe
    if (buscarTicket(ticket.getCodigo()) != null) {
      return actualizarTicket(ticket);
    }
    
    String horaSalida = ticket.getHoraSalida() != null 
                        ? ticket.getHoraSalida().format(FORMATO) 
                        : "null";
    
    String linea = String.format("%s|%s|%d|%c|%d|%s|%s|%.2f|%s|%s",
      ticket.getCodigo(),
      ticket.getVehiculo().getPlaca(),
      ticket.getPlaza().getPiso(),
      ticket.getPlaza().getSector(),
      ticket.getPlaza().getNumero(),
      ticket.getHoraIngreso().format(FORMATO),
      horaSalida,
      ticket.getMontoAPagar(),
      ticket.isPagado(),
      ticket.getEstado()
    );
    
    return ManejadorArchivos.agregarLinea(ARCHIVO, linea);
  }
  
  /**
   * Carga todos los tickets del archivo.
   */
  public static ListaEnlazada<Ticket> cargarTodos() {
    ListaEnlazada<Ticket> tickets = new ListaEnlazada<>();
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      Ticket ticket = parsearTicket(lineas.get(i));
      if (ticket != null) {
        tickets.agregarAlFinal(ticket);
      }
    }
    
    return tickets;
  }
  
  /**
   * Actualiza un ticket existente.
   */
  public static boolean actualizarTicket(Ticket ticket) {
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      String linea = lineas.get(i);
      if (linea.startsWith(ticket.getCodigo() + "|")) {
        String horaSalida = ticket.getHoraSalida() != null 
                            ? ticket.getHoraSalida().format(FORMATO) 
                            : "null";
        
        String lineaNueva = String.format("%s|%s|%d|%c|%d|%s|%s|%.2f|%s|%s",
          ticket.getCodigo(),
          ticket.getVehiculo().getPlaca(),
          ticket.getPlaza().getPiso(),
          ticket.getPlaza().getSector(),
          ticket.getPlaza().getNumero(),
          ticket.getHoraIngreso().format(FORMATO),
          horaSalida,
          ticket.getMontoAPagar(),
          ticket.isPagado(),
          ticket.getEstado()
        );
        
        return ManejadorArchivos.actualizarLinea(ARCHIVO, linea, lineaNueva);
      }
    }
    
    return false;
  }
  
  /**
   * Parsea una línea a objeto Ticket.
   * Nota: Requiere cargar el vehículo desde VehiculoDAO.
   */
  private static Ticket parsearTicket(String linea) {
    try {
      String[] datos = linea.split("\\|");
      if (datos.length < 10) {
        return null;
      }
      
      String codigo = datos[0];
      String placa = datos[1];
      int piso = Integer.parseInt(datos[2]);
      char sector = datos[3].charAt(0);
      int numero = Integer.parseInt(datos[4]);
      LocalDateTime horaIngreso = LocalDateTime.parse(datos[5], FORMATO);
      LocalDateTime horaSalida = datos[6].equals("null") ? null : LocalDateTime.parse(datos[6], FORMATO);
      double monto = Double.parseDouble(datos[7]);
      boolean pagado = Boolean.parseBoolean(datos[8]);
      EstadoTicket estado = EstadoTicket.valueOf(datos[9]);
      
      // Buscar vehículo
      Vehiculo vehiculo = VehiculoDAO.buscarVehiculo(placa);
      if (vehiculo == null) {
        System.err.println("Vehículo no encontrado para ticket: " + codigo);
        return null;
      }
      
      // Crear plaza temporal (constructor: numero, piso, sector)
      Plaza plaza = new Plaza(numero, piso, sector);
      
      // Crear ticket
      Ticket ticket = new Ticket(codigo, vehiculo, plaza, horaIngreso, horaSalida, monto, pagado);
      ticket.setEstado(estado);
      
      return ticket;
    } catch (Exception e) {
      System.err.println("Error al parsear ticket: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Busca un ticket por código.
   */
  public static Ticket buscarTicket(String codigo) {
    String linea = ManejadorArchivos.buscarLinea(ARCHIVO, codigo + "|");
    if (linea == null) {
      return null;
    }
    
    return parsearTicket(linea);
  }
}
