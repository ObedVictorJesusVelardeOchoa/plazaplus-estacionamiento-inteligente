package modelo.entidades;

/*
 * ENUM (Conjunto fijo de constantes) que representa los tipos de vehículos posibles.
 *  - BICICLETA: Vehículo tipo bicicleta.
 *  - MOTO: Vehículo tipo moto.
 *  - AUTO: Vehículo tipo automóvil.
 *  - SUV: Vehículo tipo SUV.
 *  - MINIVAN: Vehículo tipo minivan.
 */
public enum TipoVehiculo {
  BICICLETA(1.0, "BIC"),
  MOTO(1.5, "MOT"),
  AUTO(2.0, "AUT"),
  SUV(2.5, "SUV"),
  MINIVAN(3.0, "MIN");

  /*
     * ATRIBUTOS DEL ENUM TipoVehiculo
     * - factorTarifa: factor de tarifa del tipo de vehículo
     * - codigo: código del tipo de vehículo
   */
  private final double factorTarifa;
  private final String codigo;

  /**
   * CONSTRUCTORES de TipoVehiculo
   *
   *
   */
  TipoVehiculo(double factorTarifa, String codigo) {
    this.factorTarifa = factorTarifa;
    this.codigo = codigo;
  }

  /*
    * GETTERS Y SETTERS DEL ENUM TipoVehiculo
   */
  // -
  private double getFactorTarifa() {
    return factorTarifa;
  }

  // -
  private String getCodigo() {
    return codigo;
  }

  /**
   *
   *
   */
  public String[] getArregloCodigos() {
    // Obtiene todas las constantes del enum en un array
    TipoVehiculo[] tipos = TipoVehiculo.values();
    String[] arreglo = new String[tipos.length];

    // Itera sobre el array y extrae el código de cada constante
    for (int i = 0; i < tipos.length; i++) {
      arreglo[i] = tipos[i].getCodigo(); // Usa el método getCodigo()
    }
    return arreglo;
  }

}
