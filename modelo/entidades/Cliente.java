package modelo.entidades;

import java.time.LocalDate;
import modelo.estructuras.ListaEnlazada;

/**
 * Clase que representa un cliente del estacionamiento (usuario final).
 * Extiende de Usuario para aprovechar credenciales de acceso.
 */
public class Cliente {
  
  private String dni;
  private String nombres;
  private String apellidos;
  private String correo;
  private String telefono;
  private String direccion;
  private String genero;
  private LocalDate fechaRegistro;
  private TipoUsuario tipoUsuario;
  private boolean activo;
  private ListaEnlazada<String> placasVehiculos; // Placas de vehículos asociados
  
  public Cliente(String dni, String nombres, String apellidos, String correo) {
    this.dni = dni;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.correo = correo;
    this.telefono = "";
    this.direccion = "";
    this.genero = "";
    this.fechaRegistro = LocalDate.now();
    this.tipoUsuario = TipoUsuario.REGULAR;
    this.activo = true;
    this.placasVehiculos = new ListaEnlazada<>();
  }
  
  public Cliente(String dni, String nombres, String apellidos, String correo, 
                 String telefono, String direccion, String genero, TipoUsuario tipoUsuario) {
    this.dni = dni;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.correo = correo;
    this.telefono = telefono;
    this.direccion = direccion;
    this.genero = genero;
    this.fechaRegistro = LocalDate.now();
    this.tipoUsuario = tipoUsuario;
    this.activo = true;
    this.placasVehiculos = new ListaEnlazada<>();
  }
  
  // Métodos para gestionar vehículos
  public void agregarVehiculo(String placa) {
    // Verificar si ya existe
    boolean existe = false;
    for (int i = 0; i < placasVehiculos.getTamano(); i++) {
      if (placasVehiculos.get(i).equals(placa)) {
        existe = true;
        break;
      }
    }
    if (!existe) {
      placasVehiculos.agregarAlFinal(placa);
    }
  }
  
  public void eliminarVehiculo(String placa) {
    placasVehiculos.eliminar(placa);
  }
  
  public boolean tieneVehiculo(String placa) {
    for (int i = 0; i < placasVehiculos.getTamano(); i++) {
      if (placasVehiculos.get(i).equals(placa)) {
        return true;
      }
    }
    return false;
  }
  
  public String getNombreCompleto() {
    return nombres + " " + apellidos;
  }
  
  // Getters y Setters
  public String getDni() { return dni; }
  public void setDni(String dni) { this.dni = dni; }
  
  public String getNombres() { return nombres; }
  public void setNombres(String nombres) { this.nombres = nombres; }
  
  public String getApellidos() { return apellidos; }
  public void setApellidos(String apellidos) { this.apellidos = apellidos; }
  
  public String getCorreo() { return correo; }
  public void setCorreo(String correo) { this.correo = correo; }
  
  public String getTelefono() { return telefono; }
  public void setTelefono(String telefono) { this.telefono = telefono; }
  
  public String getDireccion() { return direccion; }
  public void setDireccion(String direccion) { this.direccion = direccion; }
  
  public String getGenero() { return genero; }
  public void setGenero(String genero) { this.genero = genero; }
  
  public LocalDate getFechaRegistro() { return fechaRegistro; }
  public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
  
  public TipoUsuario getTipoUsuario() { return tipoUsuario; }
  public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }
  
  public boolean isActivo() { return activo; }
  public void setActivo(boolean activo) { this.activo = activo; }
  
  public ListaEnlazada<String> getPlacasVehiculos() { return placasVehiculos; }
  
  @Override
  public String toString() {
    return "Cliente{" +
            "dni='" + dni + '\'' +
            ", nombre='" + getNombreCompleto() + '\'' +
            ", tipo=" + tipoUsuario +
            ", activo=" + activo +
            '}';
  }
}
