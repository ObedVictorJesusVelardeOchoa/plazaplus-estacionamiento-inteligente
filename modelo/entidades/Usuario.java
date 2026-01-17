package modelo.entidades;

/**
 * Clase que representa un usuario del sistema.
 *
 */
public class Usuario {
  
  private String email;
  private String password;
  private String nombre;
  private String rol;

  public Usuario(String email, String password, String nombre) {
    this.email = email;
    this.password = password;
    this.nombre = nombre;
    this.rol = "USUARIO"; // Rol por defecto
  }

  public Usuario(String email, String password, String nombre, String rol) {
    this.email = email;
    this.password = password;
    this.nombre = nombre;
    this.rol = rol;
  }

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
  /**
   * Verifica si el usuario es administrador.
   *
   * @return true si es administrador, false en caso contrario
   */
  public boolean esAdministrador() {
    return "ADMINISTRADOR".equalsIgnoreCase(rol) || "admin".equalsIgnoreCase(rol);
  }

  @Override
  public String toString() {
    return "Usuario{"
            + "email='" + email + '\''
            + ", nombre='" + nombre + '\''
            + ", rol='" + rol + '\''
            + '}';
  }
}
