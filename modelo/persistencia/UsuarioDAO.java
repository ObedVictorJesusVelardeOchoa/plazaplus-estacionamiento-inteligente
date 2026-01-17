package modelo.persistencia;

import utilidad.ManejadorArchivos;
import modelo.estructuras.ListaEnlazada;

/**
 * DAO para gestionar usuarios en archivo TXT.
 * Formato: usuario|contraseña|tipo
 */
public class UsuarioDAO {
  
  private static final String ARCHIVO = "usuarios.txt";
  
  /**
   * Valida credenciales de usuario.
   * @param usuario Nombre de usuario
   * @param contrasena Contraseña
   * @return Tipo de usuario (ADMINISTRADOR/CONSUMIDOR) o null si no es válido
   */
  public static String validarCredenciales(String usuario, String contrasena) {
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      String[] datos = lineas.get(i).split("\\|");
      if (datos.length == 3) {
        String user = datos[0];
        String pass = datos[1];
        String tipo = datos[2];
        
        if (user.equals(usuario) && pass.equals(contrasena)) {
          return tipo;
        }
      }
    }
    
    return null;
  }
  
  /**
   * Registra un nuevo usuario.
   * @param usuario Nombre de usuario
   * @param contrasena Contraseña
   * @param tipo Tipo de usuario
   * @return true si fue exitoso
   */
  public static boolean registrarUsuario(String usuario, String contrasena, String tipo) {
    // Verificar que no exista
    if (buscarUsuario(usuario) != null) {
      return false;
    }
    
    String linea = usuario + "|" + contrasena + "|" + tipo;
    return ManejadorArchivos.agregarLinea(ARCHIVO, linea);
  }
  
  /**
   * Busca un usuario por nombre.
   * @param usuario Nombre de usuario
   * @return Línea del usuario o null
   */
  public static String buscarUsuario(String usuario) {
    return ManejadorArchivos.buscarLinea(ARCHIVO, usuario + "|");
  }
  
  /**
   * Actualiza la contraseña de un usuario.
   * @param usuario Nombre de usuario
   * @param nuevaContrasena Nueva contraseña
   * @return true si fue exitoso
   */
  public static boolean actualizarContrasena(String usuario, String nuevaContrasena) {
    ListaEnlazada<String> lineas = ManejadorArchivos.leerArchivo(ARCHIVO);
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      String linea = lineas.get(i);
      String[] datos = linea.split("\\|");
      
      if (datos.length == 3 && datos[0].equals(usuario)) {
        String lineaNueva = datos[0] + "|" + nuevaContrasena + "|" + datos[2];
        return ManejadorArchivos.actualizarLinea(ARCHIVO, linea, lineaNueva);
      }
    }
    
    return false;
  }
}
