package modelo.entidades;

import java.time.LocalDateTime;

public class Vehiculo {
    private String placa; 
    private TipoVehiculo tipo;
    private String propietario; // DNI del propietario
    private LocalDateTime horaIngreso;
    
    // Características adicionales para registro permanente
    private String marca;
    private String modelo;
    private String color;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    public Vehiculo() {
    }
    
    // Constructor para estadías (temporal)
    public Vehiculo(String placa, TipoVehiculo tipo, String propietario, LocalDateTime horaIngreso) {
        this.placa = placa;
        this.tipo = tipo;
        this.propietario = propietario;
        this.horaIngreso = horaIngreso;
        this.fechaRegistro = LocalDateTime.now(); // Siempre inicializar fechaRegistro
        this.activo = true;
    }
    
    // Constructor completo para registro permanente
    public Vehiculo(String placa, TipoVehiculo tipo, String propietario, String marca, 
                   String modelo, String color) {
        this.placa = placa;
        this.tipo = tipo;
        this.propietario = propietario;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
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

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    @Override
    public String toString() {
        return placa + " - " + tipo + " (" + marca + " " + modelo + ")";
    }
}

