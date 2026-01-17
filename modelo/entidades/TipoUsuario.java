package modelo.entidades;

/**
 * Enumeración que representa los diferentes tipos de usuarios/clientes del estacionamiento.
 */
public enum TipoUsuario {
  ABONADO("Abonado Mensual"),
  FRECUENTE("Cliente Frecuente"),
  REGULAR("Cliente Regular");
  
  private final String descripcion;
  
  TipoUsuario(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  @Override
  public String toString() {
    return descripcion;
  }
}
