package controlador;

import modelo.entidades.TipoVehiculo;
import modelo.entidades.Vehiculo;
import modelo.estructuras.ListaEnlazada;
import modelo.gestores.GestorPrincipal;
import modelo.gestores.SistemaEstacionamiento;

/**
 * Controlador para el módulo de vehículos.
 * Usa el GestorPrincipal para validación integrada (verifica que el propietario exista).
 */
public class ControladorVehiculos {
  
  private final GestorPrincipal gestorPrincipal;
  
  public ControladorVehiculos() {
    this.gestorPrincipal = SistemaEstacionamiento.getInstancia().getGestorPrincipal();
  }
  
  /**
   * Registra un nuevo vehículo en el sistema.
   * VALIDA que el propietario (cliente) exista en el sistema.
   */
  public boolean registrarVehiculo(String placa, TipoVehiculo tipo, String dniPropietario,
                                   String marca, String modelo, String color) {
    // Validaciones básicas
    if (placa == null || placa.trim().isEmpty() || placa.length() < 6) {
      return false;
    }
    
    if (tipo == null) {
      return false;
    }
    
    if (dniPropietario == null || dniPropietario.trim().isEmpty()) {
      return false;
    }
    
    Vehiculo nuevoVehiculo = new Vehiculo(placa.trim().toUpperCase(), tipo, 
                                           dniPropietario.trim(), marca, modelo, color);
    
    // GestorPrincipal valida que el propietario exista
    return gestorPrincipal.registrarVehiculo(nuevoVehiculo);
  }
  
  /**
   * Busca un vehículo por placa (usa árbol binario O(log n)).
   */
  public Vehiculo buscarPorPlaca(String placa) {
    if (placa == null || placa.trim().isEmpty()) {
      return null;
    }
    return gestorPrincipal.buscarVehiculo(placa.trim().toUpperCase());
  }
  
  /**
   * Busca vehículos por DNI del propietario.
   */
  public ListaEnlazada<Vehiculo> buscarPorPropietario(String dniPropietario) {
    return gestorPrincipal.buscarVehiculosPorPropietario(dniPropietario);
  }
  
  /**
   * Obtiene todos los vehículos registrados (recorrido inorden del árbol).
   */
  public ListaEnlazada<Vehiculo> obtenerTodosLosVehiculos() {
    return gestorPrincipal.obtenerTodosLosVehiculos();
  }
  
  /**
   * Obtiene estadísticas de vehículos por tipo.
   */
  public String obtenerEstadisticas() {
    ListaEnlazada<Vehiculo> todos = gestorPrincipal.obtenerTodosLosVehiculos();
    int total = todos.getTamano();
    int bicicletas = 0;
    int motos = 0;
    int autos = 0;
    int suvs = 0;
    int minivans = 0;
    
    for (int i = 0; i < total; i++) {
      Vehiculo v = todos.get(i);
      if (v != null) {
        switch (v.getTipo()) {
          case BICICLETA:
            bicicletas++;
            break;
          case MOTO:
            motos++;
            break;
          case AUTO:
            autos++;
            break;
          case SUV:
            suvs++;
            break;
          case MINIVAN:
            minivans++;
            break;
        }
      }
    }
    
    return String.format("Total: %d | Bicicletas: %d | Motos: %d | Autos: %d | SUVs: %d | Minivans: %d",
                        total, bicicletas, motos, autos, suvs, minivans);
  }
}
