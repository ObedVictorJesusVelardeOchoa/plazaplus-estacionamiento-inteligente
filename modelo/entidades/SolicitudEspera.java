package modelo.entidades;

import java.time.LocalDateTime;

public class SolicitudEspera {
    private String placa;
    private TipoVehiculo tipo;
    private LocalDateTime fechaHoraSolicitud;
    
    public SolicitudEspera(String placa, TipoVehiculo tipo, LocalDateTime fechaHoraSolicitud) {
        this.placa = placa;
        this.tipo = tipo;
        this.fechaHoraSolicitud = fechaHoraSolicitud;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public TipoVehiculo getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }
    
    public LocalDateTime getFechaHoraSolicitud() {
        return fechaHoraSolicitud;
    }
    
    public void setFechaHoraSolicitud(LocalDateTime fechaHoraSolicitud) {
        this.fechaHoraSolicitud = fechaHoraSolicitud;
    }
    
    @Override
    public String toString() {
        return "SolicitudEspera{" +
                "placa='" + placa + '\'' +
                ", tipo=" + tipo +
                ", fechaHoraSolicitud=" + fechaHoraSolicitud +
                '}';
    }
}
