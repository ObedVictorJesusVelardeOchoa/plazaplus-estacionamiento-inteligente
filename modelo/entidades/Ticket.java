package modelo.entidades;

import java.time.LocalDateTime;

public class Ticket {
  
  public enum EstadoTicket {
    ACTIVO, FINALIZADO
  }
  /*
   * ATRIBUTOS DEL TICKET
   * - codigo: código del ticket
   * - vehiculo: vehículo contenido en el ticket
   * - plaza: plaza contenido en el ticket
   * - horaIngreso: hora de ingreso del vehículo
   * - horaSalida: hora de salida del vehículo
   * - tiempoEstadia: tiempo de estadia del vehículo
   * - montoAPagar: monto a pagar por el ticket
   * - pagado: indica si el ticket ha sido pagado
   */
    private String codigo;
    private Vehiculo vehiculo;
    private Plaza plaza;
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;
    private LocalDateTime tiempoEstadia;
    private double montoAPagar;
    private boolean pagado;
    private EstadoTicket estado;

    /*
     * CONSTRUCTURES DE LA CLASE Ticket
     */
    public Ticket() {
    }

    public Ticket(String codigo, Vehiculo vehiculo, Plaza plaza, LocalDateTime horaIngreso, LocalDateTime horaSalida, double montoAPagar, boolean pagado) {
        this.codigo = codigo;
        this.vehiculo = vehiculo;
        this.plaza = plaza;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.montoAPagar = montoAPagar;
        this.pagado = pagado;
        this.estado = EstadoTicket.ACTIVO;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Plaza getPlaza() {
        return plaza;
    }

    public void setPlaza(Plaza plaza) {
        this.plaza = plaza;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getMontoAPagar() {
        return montoAPagar;
    }

    public void setMontoAPagar(double montoAPagar) {
        this.montoAPagar = montoAPagar;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }
    
    
}
