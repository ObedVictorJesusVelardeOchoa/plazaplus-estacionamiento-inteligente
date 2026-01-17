package vista.estilos;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * Clase utilitaria para aplicar estilos estandarizados a componentes.
 * Carga configuración desde archivo de propiedades y proporciona métodos
 * para aplicar estilos de forma consistente en toda la aplicación.
 */
public class EstiloComponentes extends UIManager{

    private static Properties props;
    private static final String ARCHIVO_PROPIEDADES = "estilos.properties";

    static {
        cargarPropiedades();
    }

    /**
     * Carga las propiedades desde el archivo estilos.properties
     */
    private static void cargarPropiedades() {
        props = new Properties();
        try (InputStream input = EstiloComponentes.class.getClassLoader()
                .getResourceAsStream("vista/componentes/atomos/" + ARCHIVO_PROPIEDADES)) {
            if (input == null) {
                System.err.println("No se pudo encontrar el archivo " + ARCHIVO_PROPIEDADES);
                return;
            }
            props.load(input);
        } catch (IOException e) {
            System.err.println("Error cargando propiedades: " + e.getMessage());
        }
    }

    /**
     * Obtiene el color de fondo estandar
     * @return Color de fondo
     */
    public static Color obtenerColorFondo() {
        return parsearColor(props.getProperty("color.fondo", "255,255,255"));
    }

    /**
     * Obtiene el color de texto estandar
     * @return Color de texto
     */
    public static Color obtenerColorTexto() {
        return parsearColor(props.getProperty("color.texto", "30,30,30"));
    }

    /**
     * Obtiene un color de borde especifico
     * @param tipo Tipo de borde (primario, secundario, terciario)
     * @return Color del borde
     */
    public static Color obtenerColorBorde(String tipo) {
        String clave = "color.borde." + tipo;
        String colorDefault = tipo.equals("primario") ? "79,178,227" : "148,198,101";
        return parsearColor(props.getProperty(clave, colorDefault));
    }

    /**
     * Obtiene una fuente estandar
     * @param tamaño Tamaño de la fuente en puntos
     * @param estilo Estilo (0=normal, 1=bold, 3=italic)
     * @return Font configurada
     */
    public static Font obtenerFuente(int tamaño, int estilo) {
        String nombre = props.getProperty("fuente.nombre", "Segoe UI");
        return new Font(nombre, estilo, tamaño);
    }

    /**
     * Obtiene la fuente estandar para componentes grandes
     * @return Font para componentes grandes (20pt bold)
     */
    public static Font obtenerFuenteGrande() {
        return obtenerFuente(20, Font.BOLD);
    }

    /**
     * Obtiene la fuente estandar para componentes medianos
     * @return Font para componentes medianos (15pt bold)
     */
    public static Font obtenerFuenteMediana() {
        return obtenerFuente(15, Font.BOLD);
    }

    /**
     * Obtiene la fuente estandar para componentes pequeños
     * @return Font para componentes pequeños (14pt italic)
     */
    public static Font obtenerFuentePequeña() {
        return obtenerFuente(14, Font.ITALIC);
    }

    /**
     * Aplica un borde estandar a un componente
     * @param componente Componente al que aplicar el borde
     * @param titulo Título del borde
     * @param tipoBorde Tipo de borde (primario, secundario, etc)
     */
    public static void aplicarBordeEstandar(JComponent componente, String titulo, String tipoBorde) {
        Color colorBorde = obtenerColorBorde(tipoBorde);
        int ancho = Integer.parseInt(props.getProperty("borde.ancho", "2"));
        
        componente.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(colorBorde, ancho),
                titulo,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                obtenerFuentePequeña(),
                obtenerColorTexto()
        ));
    }

    /**
     * Obtiene las dimensiones de margenes
     * @return Array de margenes [arriba, izquierda, abajo, derecha]
     */
    public static int[] obtenerMargenes() {
        int izq = Integer.parseInt(props.getProperty("margen.izquierda", "2"));
        int arr = Integer.parseInt(props.getProperty("margen.arriba", "2"));
        int der = Integer.parseInt(props.getProperty("margen.derecha", "2"));
        int aba = Integer.parseInt(props.getProperty("margen.abajo", "2"));
        return new int[]{arr, izq, aba, der};
    }

    /**
     * Obtiene el ancho de un componente segun su tipo
     * @param tipo Tipo de componente (combobox, dni, dv, reloj, etc)
     * @return Ancho del componente
     */
    public static int obtenerAncho(String tipo) {
        return Integer.parseInt(props.getProperty("dimension.ancho." + tipo, "150"));
    }

    /**
     * Obtiene la altura estandar de componentes
     * @return Altura de componentes
     */
    public static int obtenerAlto() {
        return Integer.parseInt(props.getProperty("dimension.alto", "50"));
    }

    /**
     * Parsea una cadena de colores en formato RGB
     * @param colorStr Cadena en formato "R,G,B"
     * @return Color parseado
     */
    private static Color parsearColor(String colorStr) {
        try {
            String[] partes = colorStr.split(",");
            int r = Integer.parseInt(partes[0].trim());
            int g = Integer.parseInt(partes[1].trim());
            int b = Integer.parseInt(partes[2].trim());
            return new Color(r, g, b);
        } catch (Exception e) {
            System.err.println("Error parseando color: " + colorStr);
            return new Color(255, 255, 255);
        }
    }
}