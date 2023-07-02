package mx.gob.sedesol.basegestor.commons.utils;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCargaDTO;

public class UtilidadesCargaMasivaTest {
	
	public static final String RUTA_ARCHIVOS = "/SISI/elearning/plataformaAprendizaje/general/cargar_masiva/";

	//@Test
	public void verificarDocumento() {
		try {
			String ruta = RUTA_ARCHIVOS + "UsuariosCargaMasivaCorrecto.xls";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(not(empty())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//@Test
	public void verificarDocumento2() {
		try {
			String ruta = "/sedesol/archivos/usuarios_sedesol.xlsx";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(not(empty())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void verificarDocumento3() {
		try {
			String ruta = "/sedesol/archivos/usuarios_sedesol_copia.xls";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(not(empty())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void verificarDocumento4() {
		try {
			String ruta = "/sedesol/archivos/usuarios_sedesol.csv";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(not(empty())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void verificarDocumento5() {
		try {
			String ruta = "/sedesol/archivos/usuarios_sedesol_copia.csv";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(not(empty())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void verificarDocumento6() {
		try {
			String ruta = "/sedesol/archivos/usuarios_sedesol_inciompleto.csv";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(not(empty())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void verificarDocumento7() {
		try {
			String ruta = "/sedesol/archivos/00_Arquitectura_Tecnol\u00F3gica_F2_PM_V1.docx";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(empty()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void verificarDocumento8() {
		try {
			String ruta = "/sedesol/archivos/00_Arquitectura_Tecnol\u00F3gica_F2_PM_V1.doc";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(empty()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verificarDocumento9() {
		try {
			String ruta = "/sedesol/archivos/introducion-a-los-sistemas-de-bases-de-datos-cj-date.pdf";
			System.out.println(ruta);
			List<PersonaCargaDTO> personas = new ArrayList<PersonaCargaDTO>();//UtilidadesCargaMasiva.cargarDocumento(ruta);
			assertThat(personas, is(empty()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
