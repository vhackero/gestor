package mx.gob.sedesol.basegestor.gestionescolar;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.junit.Test;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaActaEventoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ActaDocumentoUtils;
import mx.gob.sedesol.basegestor.commons.utils.EstatusConsultaActaEnum;

public class ConsultaActaTest {
	static ConsultaActaEventoDTO estado(Boolean cierre, int documentos, int alumnos, int calificados, int invalidos) {
		ConsultaActaEventoDTO grupo = new ConsultaActaEventoDTO();
		grupo.setActaCerrada(cierre);
		grupo.setNumeroActas(documentos);
		grupo.setNumeroAlumnos(alumnos);
		grupo.setAlumnosCalificados(calificados);
		grupo.setDocumentosInvalidos(invalidos);
		return grupo;
	}

	static byte[] pdf(boolean cifrado) throws Exception {
		ByteArrayOutputStream salida = new ByteArrayOutputStream();
		Document documento = new Document();
		PdfWriter escritor = PdfWriter.getInstance(documento, salida);
		if (cifrado) { escritor.setEncryption("usuario".getBytes("UTF-8"), "propietario".getBytes("UTF-8"), 0, PdfWriter.STANDARD_ENCRYPTION_128); }
		documento.open();
		documento.add(new Paragraph("Acta de prueba"));
		documento.close();
		return salida.toByteArray();
	}

	@Test public void cierreCorrectoExigeAlumnosDocumentoUnicoYCalificaciones() {
		assertTrue(EstatusConsultaActaEnum.CERRADA.incluye(estado(true, 1, 2, 2, 0)));
		assertFalse(EstatusConsultaActaEnum.CERRADA.incluye(estado(true, 1, 0, 0, 0)));
		assertFalse(EstatusConsultaActaEnum.CERRADA.incluye(estado(true, 2, 2, 2, 0)));
		assertFalse(EstatusConsultaActaEnum.CERRADA.incluye(estado(true, 1, 2, 1, 0)));
		assertFalse(EstatusConsultaActaEnum.CERRADA.incluye(estado(true, 1, 2, 2, 1)));
	}

	@Test public void incidenciasCoexistenSinOcultarGrupos() {
		ConsultaActaEventoDTO grupo = estado(true, 0, 2, 1, 0);
		assertTrue(EstatusConsultaActaEnum.CERRADA_SIN_DOCUMENTO.incluye(grupo));
		assertTrue(EstatusConsultaActaEnum.CIERRE_INCOMPLETO.incluye(grupo));
		grupo = estado(false, 2, 2, 1, 1);
		assertTrue(EstatusConsultaActaEnum.ABIERTA_CON_DOCUMENTO.incluye(grupo));
		assertTrue(EstatusConsultaActaEnum.DOCUMENTOS_MULTIPLES.incluye(grupo));
		assertTrue(EstatusConsultaActaEnum.DOCUMENTO_INVALIDO.incluye(grupo));
	}

	@Test public void todasLasCombinacionesTienenUnFiltroEspecifico() {
		for (Boolean cierre : new Boolean[] { null, false, true }) {
			for (int documentos = 0; documentos <= 2; documentos++) {
				for (int alumnos = 0; alumnos <= 2; alumnos++) {
					for (int calificados = 0; calificados <= alumnos; calificados++) {
						for (int invalidos = 0; invalidos <= documentos; invalidos++) {
							ConsultaActaEventoDTO grupo = estado(cierre, documentos, alumnos, calificados, invalidos);
							boolean visible = false;
							for (EstatusConsultaActaEnum filtro : EstatusConsultaActaEnum.values()) {
								if (filtro != EstatusConsultaActaEnum.TODAS && filtro.incluye(grupo)) { visible = true; }
							}
							assertTrue("Combinación sin filtro: " + cierre + ", " + documentos + ", " + alumnos + ", " + calificados + ", " + invalidos, visible);
						}
					}
				}
			}
		}
	}

	@Test public void pdfDebeSerLegibleYNoCifrado() throws Exception {
		assertTrue(ActaDocumentoUtils.esPdfValido(pdf(false)));
		assertFalse(ActaDocumentoUtils.esPdfValido(null));
		assertFalse(ActaDocumentoUtils.esPdfValido(new byte[0]));
		assertFalse(ActaDocumentoUtils.esPdfValido("%PDF-1.4 archivo truncado".getBytes("UTF-8")));
		assertFalse(ActaDocumentoUtils.esPdfValido("texto que no es PDF".getBytes("UTF-8")));
		assertFalse(ActaDocumentoUtils.esPdfValido(pdf(true)));
	}
}
