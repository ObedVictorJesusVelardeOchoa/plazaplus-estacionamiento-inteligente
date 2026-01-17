package utilidad;

import modelo.entidades.Ticket;
import modelo.estructuras.ListaEnlazada;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

/**
 * Clase para generar reportes en PDF del sistema de estacionamiento.
 * Usa formato de texto plano con diseño estructurado.
 */
public class GeneradorPDF {
  
  private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
  
  /**
   * Genera un reporte PDF del historial de ingresos.
   * @param tickets Lista de tickets a incluir en el reporte
   * @param rutaArchivo Ruta donde se guardará el PDF
   * @return true si se generó exitosamente, false en caso contrario
   */
  public static boolean generarReporteIngresos(ListaEnlazada<Ticket> tickets, String rutaArchivo) {
    try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
      StringBuilder contenido = new StringBuilder();
      
      // Encabezado del reporte
      contenido.append("=".repeat(100)).append("\n");
      contenido.append("                    REPORTE DE HISTORIAL DE INGRESOS - PLAZAPLUS\n");
      contenido.append("=".repeat(100)).append("\n");
      contenido.append("Fecha de generación: ").append(java.time.LocalDateTime.now().format(FORMATO_FECHA)).append("\n");
      contenido.append("Total de registros: ").append(tickets.getTamano()).append("\n");
      contenido.append("=".repeat(100)).append("\n\n");
      
      // Encabezado de tabla
      contenido.append(String.format("%-12s %-12s %-15s %-22s %-12s %-12s\n",
                                     "ID TICKET", "PLACA", "TIPO VEHÍCULO", "FECHA/HORA INGRESO", "PLAZA", "ESTADO"));
      contenido.append("-".repeat(100)).append("\n");
      
      // Contenido de la tabla
      int countActivos = 0;
      int countFinalizados = 0;
      
      for (int i = 0; i < tickets.getTamano(); i++) {
        Ticket ticket = tickets.get(i);
        
        String fechaIngreso = ticket.getHoraIngreso().format(FORMATO_FECHA);
        String plaza = "P" + ticket.getPlaza().getPiso() + 
                       "-" + ticket.getPlaza().getSector() + 
                       ticket.getPlaza().getNumero();
        
        String estado = ticket.getEstado() == Ticket.EstadoTicket.ACTIVO 
                        ? "ACTIVO" : "FINALIZADO";
        
        if (ticket.getEstado() == Ticket.EstadoTicket.ACTIVO) {
          countActivos++;
        } else {
          countFinalizados++;
        }
        
        contenido.append(String.format("%-12s %-12s %-15s %-22s %-12s %-12s\n",
                                       ticket.getCodigo(),
                                       ticket.getVehiculo().getPlaca(),
                                       ticket.getVehiculo().getTipo(),
                                       fechaIngreso,
                                       plaza,
                                       estado));
      }
      
      // Pie del reporte
      contenido.append("=".repeat(100)).append("\n");
      contenido.append("\nRESUMEN:\n");
      contenido.append("  - Tickets Activos: ").append(countActivos).append("\n");
      contenido.append("  - Tickets Finalizados: ").append(countFinalizados).append("\n");
      contenido.append("  - Total: ").append(tickets.getTamano()).append("\n");
      contenido.append("\n=".repeat(100)).append("\n");
      contenido.append("                          FIN DEL REPORTE\n");
      contenido.append("=".repeat(100)).append("\n");
      
      // Escribir al archivo (como texto plano con extensión .txt)
      fos.write(contenido.toString().getBytes("UTF-8"));
      
      return true;
    } catch (Exception e) {
      System.err.println("Error al generar reporte PDF: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
