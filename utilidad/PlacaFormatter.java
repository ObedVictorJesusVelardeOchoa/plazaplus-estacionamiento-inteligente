package utilidad;

import javax.swing.JFormattedTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 * Formateador personalizado para placas que acepta dos formatos:
 * UU-#### (2 letras, guion, 4 números)
 * UUU-### (3 letras, guion, 3 números)
 */
  public class PlacaFormatter extends JFormattedTextField.AbstractFormatter {

    private final String regexParcial = "^$|^[A-Z]{1,3}$|^[A-Z]{2}-?$|^[A-Z]{2}-\\d{1,4}$|^[A-Z]{3}-?$|^[A-Z]{3}-\\d{1,3}$";
    private final String regexCompleta1 = "^[A-Z]{2}-\\d{4}$"; // UU-####
    private final String regexCompleta2 = "^[A-Z]{3}-\\d{3}$"; // UUU-###

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
      if (text == null || text.trim().isEmpty()) {
        return null;
      }
      text = text.trim();
      if (text.matches(regexCompleta1) || text.matches(regexCompleta2)) {
        return text;
      }
      throw new java.text.ParseException("Formato de placa inválido. Use UU-#### o UUU-###", 0);
    }

    @Override
    public String valueToString(Object value) throws java.text.ParseException {
      if (value == null) {
        return "";
      }
      return value.toString();
    }

    @Override
    protected javax.swing.text.DocumentFilter getDocumentFilter() {
      return new PlacaDocumentFilter();
    }

    /**
     * Filtro de documento que maneja la lógica de entrada.
     */
    private class PlacaDocumentFilter extends javax.swing.text.DocumentFilter {

      @Override
      public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
        // Maneja la inserción (ignora, usa replace)
        replace(fb, offset, 0, string, attr);
      }

      @Override
      public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws javax.swing.text.BadLocationException {
        // Maneja la eliminación (ignora, usa replace)
        replace(fb, offset, length, "", null);
      }

      @Override
      public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
        javax.swing.text.Document doc = fb.getDocument();
        String currentText = doc.getText(0, doc.getLength());
        String newText = (text == null) ? "" : text.toUpperCase(); // Siempre a mayúsculas

        // Construir el texto futuro tentativo
        String futureText = new StringBuilder(currentText).replace(offset, offset + length, newText).toString();

        // 1. Auto-insertar guion
        // Si el usuario escribe un dígito después de "AA" o "AAA"
        if (length == 0 && newText.length() == 1 && newText.matches("\\d")) {
          if (offset == 2 && currentText.matches("^[A-Z]{2}$")) { // "AA" + "1" -> "AA-1"
            newText = "-" + newText;
            futureText = new StringBuilder(currentText).replace(offset, offset + length, newText).toString();
          } else if (offset == 3 && currentText.matches("^[A-Z]{3}$")) { // "AAA" + "1" -> "AAA-1"
            newText = "-" + newText;
            futureText = new StringBuilder(currentText).replace(offset, offset + length, newText).toString();
          }
        }
        
        // 2. Validar contra la expresión regular parcial
        if (isValid(futureText)) {
          // Si es válido, aplicar el cambio
          super.replace(fb, offset, length, newText, attrs);
        }
        // Si no es válido, simplemente no hacer nada (ignorar la entrada)
      }
      
      /**
       * Valida si el texto es un formato de placa parcial o completo válido.
       */
      private boolean isValid(String text) {
        if (!text.matches(regexParcial)) {
          return false;
        }
        
        // Validaciones de longitud máxima
        if (text.startsWith("AA-") && text.length() > 7) { // UU-####
          return false;
        }
        if (text.startsWith("AAA-") && text.length() > 7) { // UUU-###
          return false;
        }
        
        return true;
      }
    }
  }
