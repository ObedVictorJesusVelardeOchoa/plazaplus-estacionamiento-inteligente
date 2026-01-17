package modelo.gestores;

/**
 * Singleton que mantiene la instancia única del GestorPrincipal.
 * Asegura que todos los controladores trabajen con los mismos datos.
 */
public class SistemaEstacionamiento {
  
  private static SistemaEstacionamiento instancia;
  private GestorPrincipal gestorPrincipal;
  
  private SistemaEstacionamiento() {
    this.gestorPrincipal = new GestorPrincipal();
  }
  
  /**
   * Obtiene la instancia única del sistema.
   */
  public static SistemaEstacionamiento getInstancia() {
    if (instancia == null) {
      instancia = new SistemaEstacionamiento();
    }
    return instancia;
  }
  
  /**
   * Obtiene el gestor principal del sistema.
   */
  public GestorPrincipal getGestorPrincipal() {
    return gestorPrincipal;
  }
}
