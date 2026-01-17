package utilidad;

import java.io.*;
import java.nio.file.*;
import modelo.estructuras.ListaEnlazada;

/**
 * Manejador de archivos para persistencia de datos.
 * Simula una base de datos usando archivos TXT.
 */
public class ManejadorArchivos {
  
  private static final String CARPETA_DATOS = "datos";
  
  static {
    // Crear carpeta de datos si no existe
    try {
      Files.createDirectories(Paths.get(CARPETA_DATOS));
    } catch (IOException e) {
      System.err.println("Error al crear carpeta de datos: " + e.getMessage());
    }
  }
  
  /**
   * Lee todas las líneas de un archivo.
   * @param nombreArchivo Nombre del archivo (ej: "usuarios.txt")
   * @return Lista de líneas del archivo
   */
  public static ListaEnlazada<String> leerArchivo(String nombreArchivo) {
    ListaEnlazada<String> lineas = new ListaEnlazada<>();
    String ruta = CARPETA_DATOS + File.separator + nombreArchivo;
    
    try {
      File archivo = new File(ruta);
      if (!archivo.exists()) {
        return lineas; // Retorna lista vacía si no existe
      }
      
      BufferedReader reader = new BufferedReader(new FileReader(archivo));
      String linea;
      while ((linea = reader.readLine()) != null) {
        if (!linea.trim().isEmpty()) {
          lineas.agregarAlFinal(linea);
        }
      }
      reader.close();
    } catch (IOException e) {
      System.err.println("Error al leer archivo " + nombreArchivo + ": " + e.getMessage());
    }
    
    return lineas;
  }
  
  /**
   * Escribe líneas en un archivo (sobrescribe).
   * @param nombreArchivo Nombre del archivo
   * @param lineas Lista de líneas a escribir
   * @return true si fue exitoso
   */
  public static boolean escribirArchivo(String nombreArchivo, ListaEnlazada<String> lineas) {
    String ruta = CARPETA_DATOS + File.separator + nombreArchivo;
    
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(ruta));
      for (int i = 0; i < lineas.getTamano(); i++) {
        writer.write(lineas.get(i));
        writer.newLine();
      }
      writer.close();
      return true;
    } catch (IOException e) {
      System.err.println("Error al escribir archivo " + nombreArchivo + ": " + e.getMessage());
      return false;
    }
  }
  
  /**
   * Agrega una línea al final del archivo.
   * @param nombreArchivo Nombre del archivo
   * @param linea Línea a agregar
   * @return true si fue exitoso
   */
  public static boolean agregarLinea(String nombreArchivo, String linea) {
    String ruta = CARPETA_DATOS + File.separator + nombreArchivo;
    
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true));
      writer.write(linea);
      writer.newLine();
      writer.close();
      return true;
    } catch (IOException e) {
      System.err.println("Error al agregar línea en " + nombreArchivo + ": " + e.getMessage());
      return false;
    }
  }
  
  /**
   * Busca una línea que contenga un texto específico.
   * @param nombreArchivo Nombre del archivo
   * @param textoBuscar Texto a buscar
   * @return La línea encontrada o null
   */
  public static String buscarLinea(String nombreArchivo, String textoBuscar) {
    ListaEnlazada<String> lineas = leerArchivo(nombreArchivo);
    for (int i = 0; i < lineas.getTamano(); i++) {
      String linea = lineas.get(i);
      if (linea.contains(textoBuscar)) {
        return linea;
      }
    }
    return null;
  }
  
  /**
   * Elimina una línea del archivo.
   * @param nombreArchivo Nombre del archivo
   * @param lineaEliminar Línea exacta a eliminar
   * @return true si fue exitoso
   */
  public static boolean eliminarLinea(String nombreArchivo, String lineaEliminar) {
    ListaEnlazada<String> lineas = leerArchivo(nombreArchivo);
    boolean removido = lineas.eliminar(lineaEliminar);
    if (removido) {
      return escribirArchivo(nombreArchivo, lineas);
    }
    return false;
  }
  
  /**
   * Actualiza una línea del archivo.
   * @param nombreArchivo Nombre del archivo
   * @param lineaAntigua Línea a reemplazar
   * @param lineaNueva Nueva línea
   * @return true si fue exitoso
   */
  public static boolean actualizarLinea(String nombreArchivo, String lineaAntigua, String lineaNueva) {
    ListaEnlazada<String> lineas = leerArchivo(nombreArchivo);
    ListaEnlazada<String> nuevasLineas = new ListaEnlazada<>();
    
    for (int i = 0; i < lineas.getTamano(); i++) {
      if (lineas.get(i).equals(lineaAntigua)) {
        nuevasLineas.agregarAlFinal(lineaNueva);
      } else {
        nuevasLineas.agregarAlFinal(lineas.get(i));
      }
    }
    return escribirArchivo(nombreArchivo, nuevasLineas);
  }
  
  /**
   * Inicializa archivos con datos por defecto si no existen.
   */
  public static void inicializarArchivos() {
    // Crear usuarios.txt con usuario admin por defecto
    File usuariosFile = new File(CARPETA_DATOS + File.separator + "usuarios.txt");
    if (!usuariosFile.exists()) {
      ListaEnlazada<String> usuarios = new ListaEnlazada<>();
      usuarios.agregarAlFinal("admin|admin123|ADMINISTRADOR");
      usuarios.agregarAlFinal("usuario1|user123|CONSUMIDOR");
      escribirArchivo("usuarios.txt", usuarios);
      System.out.println("Archivo usuarios.txt inicializado con usuarios por defecto");
    }
    
    // Crear archivos vacíos si no existen
    String[] archivos = {"clientes.txt", "vehiculos.txt", "tickets.txt"};
    for (String archivo : archivos) {
      File file = new File(CARPETA_DATOS + File.separator + archivo);
      if (!file.exists()) {
        try {
          file.createNewFile();
          System.out.println("Archivo " + archivo + " creado");
        } catch (IOException e) {
          System.err.println("Error al crear " + archivo + ": " + e.getMessage());
        }
      }
    }
  }
}
