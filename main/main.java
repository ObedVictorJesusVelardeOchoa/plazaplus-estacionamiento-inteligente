package main;

import vista.VentanaInicio;
import utilidad.ManejadorArchivos;

public class main {

  public static void main(String[] args) {
    // Inicializar archivos TXT (base de datos) ANTES de cualquier otra cosa
    System.out.println("Inicializando sistema de archivos...");
    ManejadorArchivos.inicializarArchivos();
    System.out.println("Sistema de archivos listo.");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        VentanaInicio inicio = new VentanaInicio();
        inicio.setVisible(true);
      }
    });
  }
}
