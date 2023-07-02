package mx.gob.sedesol.gestorweb.commons.utils;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaAuxDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;


public class ReporteAsistenciaUtil {

	
	
    /*documento con las hojas de calculo*/
	private static  Workbook libro;

	/*la hoja de calculo*/
	private static Sheet hojaAsistencia;

	/*estilo de las celdas del encabezado (con el nombre de las columnas)*/
	private static CellStyle estiloTitulo;

	/*lista de participantes*/
	private static List<RelGrupoParticipanteDTO> relGrupoParticipanteDTOs;
	
	/*constructor*/
	public static byte[] generaReporte(List<RelGrupoParticipanteDTO> relGrupoParticipantes, List<RelDiasEventoCapacitacionDTO> diasEvento) throws IOException {
		libro = new HSSFWorkbook();
		hojaAsistencia = libro.createSheet(ConstantesGestorWeb.ASISTENCIA);
		estiloTitulo = getEstiloTitulo();
		relGrupoParticipanteDTOs = relGrupoParticipantes;
		anadeFilaEncabezado(diasEvento);
		generarDocumento();
		return escribeDocumento();
	}

	/*crea una fila con los datos del participante*/
	private static  void generarDocumento() {
		
		for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : relGrupoParticipanteDTOs) {
		
			final Row filaPiloto = getNuevaFila();

			
			/*no*/
			filaPiloto.createCell(ConstantesGestorWeb.NUMERO_CERO).setCellValue(relGrupoParticipanteDTO.getPersona().getIdPersona());
			
			/*Nombre*/
			filaPiloto.createCell(ConstantesGestorWeb.NUMERO_UNO).setCellValue(relGrupoParticipanteDTO.getPersona().getNombre());
			
			
			/*Apellido Paterno*/
			filaPiloto.createCell(ConstantesGestorWeb.NUMERO_DOS).setCellValue(relGrupoParticipanteDTO.getPersona().getApellidoPaterno());
			
			
			/*Apellido Materno*/
			filaPiloto.createCell(ConstantesGestorWeb.NUMERO_TRES).setCellValue(relGrupoParticipanteDTO.getPersona().getApellidoMaterno());
			
			
			
			
							
			int indice =  ConstantesGestorWeb.NUMERO_CUATRO;
			for (AsistenciaAuxDTO asistenciaAuxDTO : relGrupoParticipanteDTO.getAsistencias()) {
				
				final Cell celda = filaPiloto.createCell(indice);
				celda.setCellValue(asistenciaAuxDTO.getTipoAsistencia());
				celda.setCellType(Cell.CELL_TYPE_STRING);
				indice ++;
			}
			
			/*Porcentaje*/
			filaPiloto.createCell(indice).setCellValue(relGrupoParticipanteDTO.getPorcentajeAsistencia().doubleValue());	
		}
		
	}




	/*genera el documento*/
	private  static byte[] escribeDocumento() throws IOException {
		byte[] bytes;
		   
		ajustaColumnas();  

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		
		libro.write(baos);
		return baos.toByteArray();
	}

	/* crea la fila y celdas del encabezado con el nombre de las columnas */
	private static void anadeFilaEncabezado(List<RelDiasEventoCapacitacionDTO> diasEvento) {
		final Row filaEncabezado = getNuevaFila();
		int numeroCelda = 0;
		creaCeldaEncabezado(filaEncabezado, numeroCelda++, ConstantesGestorWeb.NO);
		creaCeldaEncabezado(filaEncabezado, numeroCelda++, ConstantesGestorWeb.NOMBRE);
		creaCeldaEncabezado(filaEncabezado, numeroCelda++, ConstantesGestorWeb.APELLIDO_PATERNO);
		creaCeldaEncabezado(filaEncabezado, numeroCelda++, ConstantesGestorWeb.APELLIDO_MATERNO);
		
		for (RelDiasEventoCapacitacionDTO diaEvento: diasEvento) {
			creaCeldaEncabezado(filaEncabezado, numeroCelda++, diaEvento.getFechaEventoFormat());
		}
		
		creaCeldaEncabezado(filaEncabezado, numeroCelda++, ConstantesGestorWeb.PORCENTAJE_ASISTENCIA);
		
		
	}

	/* crea una celda de encabezado (las del título) y añade el estilo */
	private static void creaCeldaEncabezado(Row filaEncabezado, int numeroCelda, String valor) {
		final Cell celdaEncabezado = filaEncabezado.createCell(numeroCelda);
		celdaEncabezado.setCellValue(valor);
		celdaEncabezado.setCellStyle(estiloTitulo);
	}

	/*ajusta el ancho de las columnas en función de su contenido*/
	private static void ajustaColumnas() {
		final short numeroColumnas = hojaAsistencia.getRow(0).getLastCellNum();
		for (int i = 0; i < numeroColumnas; i++) {
			hojaAsistencia.autoSizeColumn(i);
		}
	}

	/*devuelve el estilo que tendrán las celdas del título (negrita y color de fondo azul)*/
	private static CellStyle getEstiloTitulo() {
		final CellStyle cellStyle = libro.createCellStyle();
		final Font cellFont = libro.createFont();
		cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(cellFont);
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}

	

	/*crea una nueva fila a continuación de la anterior*/
	private static Row getNuevaFila() {
		return hojaAsistencia.createRow(hojaAsistencia.getPhysicalNumberOfRows());
	}


}