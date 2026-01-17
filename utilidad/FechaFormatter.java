package utilidad;

import javax.swing.JFormattedTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Formateador personalizado para fechas en formato dd/MM/yyyy.
 * Extiende AbstractFormatter para ser compatible con cualquier JFormattedTextField.
 * Muestra placeholder 00/00/0000 y valida entrada con rangos reales (días por mes, bisiestos).
 * Usa java.time.LocalDate para validación real de fechas.
 */
public class FechaFormatter extends JFormattedTextField.AbstractFormatter {

	private static final int MASK_LENGTH = 10; // dd/MM/yyyy
	private final java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public Object stringToValue(String text) throws java.text.ParseException {
		if (text == null || text.trim().isEmpty()) return null;
		String t = text.trim();
		try {
			// Validar fecha completa usando LocalDate
			java.time.LocalDate.parse(t, dtf);
			return t;
		} catch (java.time.format.DateTimeParseException ex) {
			throw new java.text.ParseException("Fecha inválida: " + t, 0);
		}
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
				if (string == null || string.isEmpty()) return;
				replace(fb, offset, 0, string, attr);
			}

			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
				// Rellenar con '0' en posiciones de dígitos al eliminar
				javax.swing.text.Document doc = fb.getDocument();
				String current = doc.getLength() == 0 ? "" : doc.getText(0, doc.getLength());
				char[] chars = buildMaskedArray(current);
				int end = Math.min(offset + length, chars.length);
				for (int i = offset; i < end; i++) if (isDigitPosition(i)) chars[i] = '0';
				String result = new String(chars);
				super.replace(fb, 0, doc.getLength(), result, null);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				javax.swing.text.Document doc = fb.getDocument();
				String current = doc.getLength() == 0 ? "" : doc.getText(0, doc.getLength());
				char[] chars = buildMaskedArray(current);

				// Sólo dígitos de entrada
				String digits = (text == null) ? "" : text.replaceAll("[^0-9]", "");
				int writePos = offset;

				for (int i = 0; i < digits.length(); i++) {
					while (writePos < MASK_LENGTH && !isDigitPosition(writePos)) writePos++;
					if (writePos >= MASK_LENGTH) break;
					chars[writePos] = digits.charAt(i);
					writePos++;
				}

				if ((text == null || text.isEmpty()) && length > 0) {
					int end = Math.min(offset + length, MASK_LENGTH);
					for (int i = offset; i < end; i++) if (isDigitPosition(i)) chars[i] = '0';
				}

				String futureText = new String(chars);

				if (futureText.length() != MASK_LENGTH) return;

				// Validación por partes: permitir '00' como placeholder durante edición
				String[] parts = futureText.split("/");
				try {
					if (parts.length >= 1 && parts[0].length() == 2) {
						int day = Integer.parseInt(parts[0]);
						if (day == 0) {
							if (parts.length == 3 && parts[2].length() == 4) return; // no aceptar 00 cuando año completo
						} else if (day < 1 || day > 31) return;
					}
					if (parts.length >= 2 && parts[1].length() == 2) {
						int month = Integer.parseInt(parts[1]);
						if (month == 0) {
							if (parts.length == 3 && parts[2].length() == 4) return;
						} else if (month < 1 || month > 12) return;
					}
					if (parts.length == 3 && parts[2].length() == 4) {
						String candidate = String.format("%s/%s/%s", parts[0], parts[1], parts[2]);
						java.time.LocalDate.parse(candidate, dtf);
					}
				} catch (NumberFormatException | java.time.format.DateTimeParseException ex) {
					return;
				}

				super.replace(fb, 0, doc.getLength(), futureText, attrs);
			}

			private char[] buildMaskedArray(String current) {
				char[] chars = new char[MASK_LENGTH];
				chars[0] = '0'; chars[1] = '0'; chars[2] = '/';
				chars[3] = '0'; chars[4] = '0'; chars[5] = '/';
				chars[6] = '0'; chars[7] = '0'; chars[8] = '0'; chars[9] = '0';
				for (int i = 0; i < Math.min(current.length(), MASK_LENGTH); i++) {
					char c = current.charAt(i);
					if (i == 2 || i == 5) chars[i] = '/';
					else if (Character.isDigit(c)) chars[i] = c;
				}
				return chars;
			}

			private boolean isDigitPosition(int pos) {
				return pos == 0 || pos == 1 || pos == 3 || pos == 4 || pos == 6 || pos == 7 || pos == 8 || pos == 9;
			}
		};
	}
}
