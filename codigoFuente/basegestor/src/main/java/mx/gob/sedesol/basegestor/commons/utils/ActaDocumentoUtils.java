package mx.gob.sedesol.basegestor.commons.utils;

import com.lowagie.text.pdf.PdfReader;

/** Comprueba que el archivo se puede abrir; no certifica firmas ni su contenido académico. */
public final class ActaDocumentoUtils {
	private ActaDocumentoUtils() { }

	public static boolean esPdfValido(byte[] archivo) {
		if (archivo == null || archivo.length == 0) { return false; }
		PdfReader lector = null;
		try {
			lector = new PdfReader(archivo);
			if (lector.isEncrypted() || lector.getNumberOfPages() == 0) { return false; }
			for (int pagina = 1; pagina <= lector.getNumberOfPages(); pagina++) {
				if (lector.getPageN(pagina) == null) { return false; }
				lector.getPageContent(pagina);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (lector != null) { lector.close(); }
		}
	}
}
