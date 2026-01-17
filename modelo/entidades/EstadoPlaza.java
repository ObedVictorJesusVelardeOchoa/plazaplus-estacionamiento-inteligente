package modelo.entidades;

/*
 * ENUM (Conjunto fijo de constantes) que representa los estados posibles de una plaza.
 *  - Libre: La plaza no está ocupada ni reservada.
 *  - Ocupada: La plaza está ocupada por un vehículo.
 *  - Reservada: La plaza está reservada para un vehículo.
 *  - Mantenimiento: La plaza está en mantenimiento.
 */
public enum EstadoPlaza {
  LIBRE("Libre", true),
  OCUPADA("Ocupada", false),
  RESERVADA("Reservada", false),
  MANTENIMIENTO("Mantenimiento", false);

  /*
   * ATRIBUTOS DEL ENUM EstadoPlaza
   * - @see etiqueta: etiqueta del estado de la plaza
   * - @see disponibilidad: disponibilidad del estado de la plaza
   */
  private final String etiqueta;
  private final boolean disponibilidad;

  EstadoPlaza(String etiqueta, boolean disponibilidad) {
    this.etiqueta = etiqueta;
    this.disponibilidad = disponibilidad;
  }

  public String getEtiqueta() {
    return etiqueta;
  }

  public boolean isDisponible() {
    return disponibilidad;
  }
  
  /**
  * 
  **/
  public String[] getArregloCodigos() {
    // Obtiene todas las constantes del enum en un array
    EstadoPlaza[] estados = EstadoPlaza.values();
    //
    String[] arreglo = new String[estados.length];

    // Itera sobre el array y extrae el código de cada constante
    for (int i = 0; i < estados.length; i++) {
      arreglo[i] = estados[i].getEtiqueta(); // Usa el método getCodigo()
    }

    return arreglo;
  }
}
