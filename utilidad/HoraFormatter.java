package utilidad;

import javax.swing.JFormattedTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Formateador personalizado para horas en formato HH:mm:ss.
 * Permite entrada parcial y valida la hora completa al convertir.
 */
public class HoraFormatter extends JFormattedTextField.AbstractFormatter {

    private final String regexParcial = "^$|^\\d{1,2}$|^\\d{2}:?$|^\\d{2}:\\d{1,2}$|^\\d{2}:\\d{2}:?$|^\\d{2}:\\d{2}:\\d{1,2}$";
    private static final int MAX_LENGTH = 8; // HH:mm:ss
    private final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");

    public HoraFormatter() {
        sdf.setLenient(false);
    }

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        String t = text.trim();
        // Validar hora completa
        sdf.parse(t); // lanzará ParseException si inválida
        return t;
    }

    @Override
    public String valueToString(Object value) throws java.text.ParseException {
        if (value == null) return "";
        return value.toString();
    }

    @Override
    protected DocumentFilter getDocumentFilter() {
        return new DocumentFilter() {

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null) return;
                replace(fb, offset, 0, string, attr);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                replace(fb, offset, length, "", null);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                javax.swing.text.Document doc = fb.getDocument();
                String currentText = doc.getText(0, doc.getLength());
                String newText = (text == null) ? "" : text;

                // Sólo permitir dígitos y ':'
                newText = newText.replaceAll("[^0-9:]", "");

                // Si el usuario está escribiendo un dígito que debe llevar un ':', autoinsertarlo
                if (text != null && text.length() == 1 && text.matches("\\d")) {
                    if (offset == 2 && currentText.matches("^\\d{2}$")) {
                        newText = ":" + newText;
                    } else if (offset == 5 && currentText.matches("^\\d{2}:\\d{2}$")) {
                        newText = ":" + newText;
                    }
                }

                String futureText = new StringBuilder(currentText).replace(offset, offset + length, newText).toString();

                if (futureText.length() > MAX_LENGTH) return;

                // Validación por partes: HH, mm, ss
                if (!futureText.matches(regexParcial)) return;

                String[] parts = futureText.split(":");
                try {
                    if (parts.length >= 1 && parts[0].length() > 0) {
                        int hour = Integer.parseInt(parts[0]);
                        if (hour < 0 || hour > 23) return;
                    }
                    if (parts.length >= 2 && parts[1].length() > 0) {
                        int minute = Integer.parseInt(parts[1]);
                        if (minute < 0 || minute > 59) return;
                    }
                    if (parts.length >= 3 && parts[2].length() > 0) {
                        int second = Integer.parseInt(parts[2]);
                        if (second < 0 || second > 59) return;
                    }
                    // Si están las 3 partes completas (HH:mm:ss) validar con LocalTime
                    if (parts.length == 3 && parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 2) {
                        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
                        java.time.LocalTime.parse(futureText, dtf);
                    }
                } catch (NumberFormatException | java.time.format.DateTimeParseException ex) {
                    return;
                }

                super.replace(fb, offset, length, newText, attrs);
            }
        };
    }
}
