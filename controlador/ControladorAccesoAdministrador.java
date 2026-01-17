package controlador;

import modelo.entidades.Usuario;
import modelo.persistencia.UsuarioDAO;
import vista.privado.vistas.VentanaAdministrador;

/**
 * Controlador para la ventana de acceso de administradores.
 * Maneja la validación de credenciales y el acceso al panel de administración.
 * USA ARCHIVOS TXT COMO BASE DE DATOS mediante UsuarioDAO.
 */

public class ControladorAccesoAdministrador {
  private static Usuario usuario;
  private VentanaAdministrador ventana;

 /**
 * Constructor del controlador de acceso.
 * Usa directamente UsuarioDAO para validación de credenciales desde archivo TXT.
 */
  
  public ControladorAccesoAdministrador() {
    // No requiere inicialización, usa UsuarioDAO directamente
 }
 
 
 /**
 * Intenta validar el acceso de un administrador.
 * Verifica credenciales usando el sistema de archivos TXT.
 *
 * @param email Nombre de usuario
 * @param password Contraseña del usuario
 */
 public boolean intentarAcceso(String email, String password){
   // Validar credenciales desde archivo TXT
   String tipoUsuario = UsuarioDAO.validarCredenciales(email, password);
   
   if (tipoUsuario != null && tipoUsuario.equals("ADMINISTRADOR")) {
     // Crear usuario temporal para mantener compatibilidad
     usuario = new Usuario(email, password, email, tipoUsuario);
     return true;
   }
   return false;
 }
 
 /**
  * Devuelve la ventana de administrador, creándola si no existe.
  * @param acceso Indica si el acceso fue exitoso
  * @return VentanaAdministrador si el acceso es true, null en caso contrario
  */
 public VentanaAdministrador devolverVentana(boolean acceso){
   
   if (ventana == null) {
     // Crear la ventana con el controlador de administrador
     ControladorAdministrador controladorAdmin = new ControladorAdministrador();
     this.ventana = new VentanaAdministrador(controladorAdmin);
   }
   
   if (acceso==true) {
     return ventana;
   }
   return null;
 }
 
 /**
  * Obtiene el usuario actualmente autenticado.
  * @return Usuario autenticado
  */
 public static Usuario getUsuario() {
   return usuario;
 }
 
}
