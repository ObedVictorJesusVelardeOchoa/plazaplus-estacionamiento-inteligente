package modelo.entidades;

import java.time.LocalDateTime;

public class Plaza {

  /*
   * ATRIBUTOS DE LA PLAZA
   * - @see id : identificador único de la plaza (sector+piso+numero)
   * - @see numero: número relativo al piso de la plaza
   * - @see piso: numero de piso en el que se encuentra la plaza
   * - @see sector: sector de la plaza
   * - @see tipoPermitido: tipo de vehículo permitido en la plaza
   * - @see estado: estado de la plaza
   *    - LIBRE: la plaza no está ocupada ni reservada
   *    - OCUPADA: la plaza está ocupada por un vehículo
   *    - RESERVADA: la plaza está reservada para un vehículo
   *    - MANTENIMIENTO: la plaza está en mantenimiento
   * - vehiculoActual: vehículo actual contenido en la plaza
   * - horaOcupacion: hora de último inicio de ocupación de la plaza
   */
    private int numero;
    private int piso;
    private char sector;
    private TipoVehiculo tipoPermitido;
    private EstadoPlaza estado;
    private Vehiculo vehiculoActual;
    private LocalDateTime horaOcupacion;

    /*
     * CONSTRUCTURES DE LA CLASE Plaza
     */
    public Plaza() {
    }

    // - Constructor con parámetros de la clase
    public Plaza(int numero, int piso, char sector){
        this.numero = numero;
        this.piso = piso;
        this.sector = sector;
    }

    public Plaza(int numero, int piso, char sector, TipoVehiculo tipoPermitido, EstadoPlaza estado, Vehiculo vehiculoActual, LocalDateTime horaOcupacion) {
        this.numero = numero;
        this.piso = piso;
        this.sector = sector;
        this.tipoPermitido = tipoPermitido;
        this.estado = estado;
        this.vehiculoActual = vehiculoActual;
        this.horaOcupacion = horaOcupacion;
    }
    
    /*
     * GETTERS Y SETTERS Plaza
     * - getters: devuelven el valor de un atributo
     * - setters: modifican el valor de un atributo
     */
    public int getNumero() {
        return numero;
    }

    // - 
    public void setNumero(int numero) {
        if(numero <= 0){
            throw new IllegalArgumentException("El numero ingresado debe ser mayor a 0");
        }
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        if(piso <= 0){
            throw new IllegalArgumentException("El piso ingresado debe ser mayor a 0");
        }
        this.piso = piso;
    }

    public char getSector() {
        return sector;
    }

    public void setSector(char sector) {
        this.sector = sector;
    }

    public TipoVehiculo getTipoPermitido() {
        return tipoPermitido;
    }

    public void setTipoPermitido(TipoVehiculo tipoPermitido) {
        this.tipoPermitido = tipoPermitido;
    }

    public EstadoPlaza getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlaza estadoPlaza) {
        this.estado = estadoPlaza;
    }

    public Vehiculo getVehiculoActual() {
        return vehiculoActual;
    }

    public void setVehiculoActual(Vehiculo vehiculoActual) {
        this.vehiculoActual = vehiculoActual;
    }

    public LocalDateTime getHoraOcupacion() {
        return horaOcupacion;
    }

    public void setHoraOcupacion(LocalDateTime horaOcupacion) {
        this.horaOcupacion = horaOcupacion;
    }
}
