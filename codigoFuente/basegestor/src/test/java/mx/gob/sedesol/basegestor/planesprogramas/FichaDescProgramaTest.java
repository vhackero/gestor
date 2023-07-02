package mx.gob.sedesol.basegestor.planesprogramas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgActividadesAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgAreaConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgCreadorProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgAutoreDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgResponsableDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgTecnicaDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatActividadesAprendizajePrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAreasConocimiento;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTpoCargaHoraria;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatNivelEnsenanzaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatStatusPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTecnicaDidacticaPrograma;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.AmbientesAprendizajeEcService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@EnableTransactionManagement
public class FichaDescProgramaTest {

	private static final Logger logger = Logger.getLogger(FichaDescProgramaTest.class);

	@Autowired
	private FichaDescProgramaService fichaDescProgramaService;
	@Autowired
	private MallaCurricularService mallaCurricularService;
	@Autowired
	private CatalogoComunService<CatNivelEnsenanzaPrograma, Integer> catNivelEnsenanzaService;
	@Autowired
	private CatalogoComunService<CatStatusPrograma, Integer> catStatusProgramaService;
	@Autowired
	private AmbientesAprendizajeEcService ambientesAprendizajeService;
	@Autowired
	private CatalogoComunService<CatActividadesAprendizajePrograma, Integer> catActAprendizajeService;

	@Autowired
	private CatalogoComunService<CatAreasConocimiento, Integer> catAreasConocService;

	@Autowired
	private CatalogoComunService<CatTpoCargaHoraria, Integer> catFormatoDurProgService;

	@Autowired
	private CatalogoComunService<CatTecnicaDidacticaPrograma, Integer> catTecnicaDidacticaService;

	@Autowired
	FECServiceFacade fecServiceFacade;

	//@Test
	public void generaFolioTest() {
		//fecServiceFacade.generaClaveProgramaPlanSedesol();
		

	}

	// @Test
	public void busquedaFichaPrograma() {

		FichaDescProgramaDTO fichaDescProg = fichaDescProgramaService.buscarPorId(40);
		logger.info("#### BUSQUEDA: " + fichaDescProg);
	}
	
	//@Test
	public void consultarProgramaById(){
		FichaDescProgramaDTO res  = fichaDescProgramaService.buscarPorId(40);
		res.toString();
	}

	//@Test
	public void saveFichaDescripcionPrograma() {

		//List<FichaDescProgramaDTO> descProgramaDTO =

				fichaDescProgramaService.buscarPorId(1);

		logger.info("Check 1 El tamaño de la lista es " );

		/*
		 * for (FichaDescProgramaDTO fichaDescProgramaDTO : descProgramaDTO) {
		 * 
		 * }
		 */

		/*
		 * FichaDescProgramaDTO fdp = new FichaDescProgramaDTO();
		 * 
		 * 
		 * 
		 * 
		 * ResultadoDTO rs = fichaDescProgramaService.guardar(fdp);
		 */

	}
	
	// @Test
	public void guardaFichaPrograma() {

		MallaCurricularDTO programa = mallaCurricularService.buscarPorId(40);

		FichaDescProgramaDTO fdp = new FichaDescProgramaDTO();
		Date fechaActual = new Date();
		fdp.setIdPrograma(programa.getId());
		fdp.setConocimietosPrevios("Matematicas,Ciencias Naturales, Algebra, ecuaciones diferenciales, programacion");
		fdp.setCvePrograma("MATE-19-10-16");
		fdp.setDescripcion("Programa para poder cursar programaojción estructurada");

		fdp.setFechaSolicitud(fechaActual);
		fdp.setFechaArranque(DateUtils.convierteStringADate("12/01/2017", "dd/MM/yyyy"));
		fdp.setFechaProduccion(DateUtils.convierteStringADate("07/01/2017", "dd/MM/yyyy"));

		CatalogoComunDTO nivelEns = catNivelEnsenanzaService.buscarPorId(2, CatNivelEnsenanzaPrograma.class);
		fdp.setCatNivelEnsenanzaPrograma(nivelEns);

		CatalogoComunDTO statusProg = catStatusProgramaService.buscarPorId(4, CatStatusPrograma.class);
		fdp.setCatStatusPrograma(statusProg);

		fdp.setFechaRegistro(fechaActual);
		fdp.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);

		fdp.setIdentificadorFinal("IDPROG_NVO_03");
		fdp.setInstrumentoAprendizaje("LIBROS E INTERNET");
		fdp.setJustificacionAcademica("Curso para personas interesadas en programación");
		fdp.setMetodologia("Metodologia ACID");
		fdp.setNombreTentativo("MATEMATICAS AVANZADAS");
		fdp.setObjetivosGenerales(
				"Desarrollar el potencial del alumno a modo de dominar los procesos matematicos requeridos para el siguiente nivel");
		fdp.setPresentacion("Desarrolla tus habilidades mentales");
		fdp.setPropositos("Proveer al alumno un ambiente agradable y conocimiento de alto nivel");
		fdp.setRequerimientoEquipo("windows xp");
		fdp.setRequisitosIngreso("Certificado de preparatoria e identificacion como IFE,CURP o Acta de Nacimiento");

		// Consultando Ambientes de Aprendizaje Moodle y seteando valor
		fdp.setCatAmbientesAprendizajeEc(ambientesAprendizajeService.buscarPorId(2));

		// ##### Activiades de Aprendizaje
		List<RelProgActividadesAprendizajeDTO> lstAA = new ArrayList<>();
		RelProgActividadesAprendizajeDTO aa1, aa2;
		aa1 = new RelProgActividadesAprendizajeDTO();
		aa1.setCatActividadesAprendizajePrograma(
				catActAprendizajeService.buscarPorId(1, CatActividadesAprendizajePrograma.class)); // Solucion
																									// Problemas
		aa1.setFechaRegistro(fechaActual);
		aa1.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		aa1.setTblFichaDescriptivaPrograma(fdp);
		// aa1.setIdActividadAprendizaje(catActAprendizajeService.buscarPorId(1,
		// CatActividadesAprendizajePrograma.class).getId());
		// aa1.setIdPrograma(fdp.getIdPrograma());
		lstAA.add(aa1);

		aa2 = new RelProgActividadesAprendizajeDTO();
		aa2.setCatActividadesAprendizajePrograma(
				catActAprendizajeService.buscarPorId(3, CatActividadesAprendizajePrograma.class)); // mapas
																									// conceptuales
		aa2.setFechaRegistro(fechaActual);
		aa2.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		aa2.setTblFichaDescriptivaPrograma(fdp);
		// aa2.setIdActividadAprendizaje(catActAprendizajeService.buscarPorId(3,
		// CatActividadesAprendizajePrograma.class).getId());
		// naa2.setIdPrograma(fdp.getIdPrograma());
		lstAA.add(aa2);

		//fdp.setRelProgramaActividadesAprendizajes(lstAA);
		// ##################

		// ##### AREAS DE CONOCIMIENTO
		List<RelProgAreaConocimientoDTO> lstAreasConoc = new ArrayList<>();
		List<CatalogoComunDTO> areasc = catAreasConocService.findAll(CatAreasConocimiento.class);
		for (CatalogoComunDTO c : areasc) {
			RelProgAreaConocimientoDTO relac = new RelProgAreaConocimientoDTO();
			relac.setFechaRegistro(fechaActual);
			relac.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
			relac.setCatAreasConocimiento(c);
			relac.setFichaDescriptivaPrograma(fdp);
			lstAreasConoc.add(relac);
		}
		//fdp.setRelProgramaAreasConocimientos(lstAreasConoc);

		// #############################

		// CREADORES PROGRAMA
		List<RelProgCreadorProgramaDTO> lstCreadores = new ArrayList<>();
		RelProgCreadorProgramaDTO c1 = new RelProgCreadorProgramaDTO();
		c1.setFechaRegistro(fechaActual);
		c1.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		c1.setIdClasificacionPersona(1);
		c1.setIdTipoPersona(new Integer(1));
		c1.setNombreCompleto("Pedro");
		c1.setFichaDescriptivaPrograma(fdp);
		lstCreadores.add(c1);

		RelProgCreadorProgramaDTO c2 = new RelProgCreadorProgramaDTO();
		c2.setFechaRegistro(fechaActual);
		c2.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		c2.setIdClasificacionPersona(1);
		c2.setIdTipoPersona(new Integer(1));
		c2.setNombreCompleto("Jose");
		c2.setFichaDescriptivaPrograma(fdp);
		lstCreadores.add(c2);

		//fdp.setRelProgramaCreadorProgramas(lstCreadores);
		// #################################

		// ### DESTINATARIOS
		List<RelProgAutoreDTO> lstDest = new ArrayList<>();
		RelProgAutoreDTO pd1 = new RelProgAutoreDTO();
		pd1.setAutores("Destinatario1Test");
		pd1.setFechaRegistro(fechaActual);
		pd1.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		pd1.setFichaDescriptivaPrograma(fdp);
		lstDest.add(pd1);
		RelProgAutoreDTO pd2 = new RelProgAutoreDTO();
		pd2.setAutores("Destinatario2Test");
		pd2.setFechaRegistro(fechaActual);
		pd2.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		pd2.setFichaDescriptivaPrograma(fdp);
		lstDest.add(pd2);

		fdp.setRelProgramaAutores(lstDest);
		// ###############################

		// ##### Duracion
		List<RelProgDuracionDTO> lstDur = new ArrayList<>();
		RelProgDuracionDTO pdur = new RelProgDuracionDTO();
		pdur.setCatTpoCargaHoraria((catFormatoDurProgService.buscarPorId(1, CatTpoCargaHoraria.class)));
//		pdur.setTiempoDuracion("4:30");
		pdur.setFechaRegistro(fechaActual);
		pdur.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		pdur.setFichaDescriptivaPrograma(fdp);
		lstDur.add(pdur);

		RelProgDuracionDTO pdur2 = new RelProgDuracionDTO();
		pdur2.setCatTpoCargaHoraria(catFormatoDurProgService.buscarPorId(2, CatTpoCargaHoraria.class));
	//	pdur2.setTiempoDuracion("180");
		pdur2.setFechaRegistro(fechaActual);
		pdur2.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		pdur2.setFichaDescriptivaPrograma(fdp);
		lstDur.add(pdur2);
		fdp.setRelProgramaDuracion(lstDur);
		// #################

		// ####### Entidades Academicas ###
		// List<RelProgEntidadAcademicaDTO> entAcademicas = new ArrayList<>();
		// RelProgEntidadAcademicaDTO entAc = new RelProgEntidadAcademicaDTO();
		// entAc.setFechaRegistro(fechaActual);
		// entAc.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		// entAc.setFichaDescriptivaPrograma(fdp);
		// entAc.setTblEntidad(tblEntidad);

		// ###### Tecnicas didacticas ######
		List<RelProgTecnicaDidacticaDTO> lstTecnicas = new ArrayList<>();
		RelProgTecnicaDidacticaDTO tecnica = new RelProgTecnicaDidacticaDTO();
		tecnica.setFechaRegistro(fechaActual);
		tecnica.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		tecnica.setFichaDescriptivaPrograma(fdp);
		tecnica.setCatTecnicaDidacticaPrograma(
				catTecnicaDidacticaService.buscarPorId(1, CatTecnicaDidacticaPrograma.class));
		lstTecnicas.add(tecnica);

		RelProgTecnicaDidacticaDTO tecnica2 = new RelProgTecnicaDidacticaDTO();
		tecnica2.setFechaRegistro(fechaActual);
		tecnica2.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		tecnica2.setFichaDescriptivaPrograma(fdp);
		tecnica2.setCatTecnicaDidacticaPrograma(
				catTecnicaDidacticaService.buscarPorId(3, CatTecnicaDidacticaPrograma.class));
		lstTecnicas.add(tecnica2);

		//fdp.setRelProgramaTecnicasDidacticas(lstTecnicas);
		// #################

		// ###### Responsables Programa
		List<RelProgResponsableDTO> lstRespProg = new ArrayList<>();
		RelProgResponsableDTO resp = new RelProgResponsableDTO();
		resp.setFechaRegistro(fechaActual);
		resp.setFichaDescriptivaPrograma(fdp);
		resp.setResponsables("Jose Morales");
		resp.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		lstRespProg.add(resp);

		RelProgResponsableDTO resp2 = new RelProgResponsableDTO();
		resp2.setFechaRegistro(fechaActual);
		resp2.setFichaDescriptivaPrograma(fdp);
		resp2.setResponsables("Maria Fernandez");
		resp2.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		lstRespProg.add(resp2);

		fdp.setRelProgramaResponsables(lstRespProg);
		// ######################################

		logger.info("####### INIT TRANSACSION ####");

		ResultadoDTO rs = fichaDescProgramaService.guardar(fdp);
		if (ObjectUtils.isNotNull(rs)) {
			logger.info("#### INFO RESULT ####" + rs.getResultado().toString());
			obtenerErroresDeServicio(rs.getMensajes());
		}
	}

	private String obtenerErroresDeServicio(List<String> errores) {
		StringBuffer cadena = new StringBuffer();
		if (!ObjectUtils.isNullOrEmpty(errores)) {
			for (String error : errores) {
				cadena.append(error).append("\n");
			}
		}
		return cadena.toString();
	}


	/**
	 * 
	 * public void guardaFichaProgramaEntidades(){
	 * 
	 * MallaCurricularDTO programa = mallaCurricularService.buscarPorId(40);
	 * ModelMapper map = new ModelMapper();
	 * 
	 * TblFichaDescriptivaPrograma fdp = new TblFichaDescriptivaPrograma(); Date
	 * fechaActual = new Date(); fdp.setIdPrograma(programa.getId());
	 * fdp.setConocimietosPrevios(
	 * "Matematicas,Ciencias Naturales, Algebra, ecuaciones diferenciales, programacion"
	 * ); fdp.setCvePrograma("MATE-19-10-16"); fdp.setDescripcion(
	 * "Programa para poder cursar programaojción estructurada");
	 * 
	 * fdp.setFechaSolicitud(fechaActual);
	 * fdp.setFechaArranque(DateUtils.convierteStringADate("12/01/2017",
	 * "dd/MM/yyyy"));
	 * fdp.setFechaProduccion(DateUtils.convierteStringADate("07/01/2017",
	 * "dd/MM/yyyy"));
	 * 
	 * CatalogoComunDTO nivelEns = catNivelEnsenanzaService.buscarPorId(2,
	 * CatNivelEnsenanzaPrograma.class);
	 * fdp.setCatNivelEnsenanzaPrograma(map.map(nivelEns,
	 * CatNivelEnsenanzaPrograma.class));
	 * 
	 * CatalogoComunDTO statusProg = catStatusProgramaService.buscarPorId(4,
	 * CatStatusPrograma.class);
	 * fdp.setCatStatusPrograma(map.map(statusProg,CatStatusPrograma.class));
	 * 
	 * 
	 * fdp.setFechaRegistro(fechaActual);
	 * fdp.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
	 * 
	 * fdp.setIdentificadorFinal("IDPROG_NVO_03");
	 * fdp.setInstrumentoAprendizaje("LIBROS E INTERNET");
	 * fdp.setJustificacionAcademica(
	 * "Curso para personas interesadas en programación"); fdp.setMetodologia(
	 * "Metodologia ACID"); fdp.setNombreTentativo("MATEMATICAS AVANZADAS");
	 * fdp.setObjetivosGenerales(
	 * "Desarrollar el potencial del alumno a modo de dominar los procesos matematicos requeridos para el siguiente nivel"
	 * ); fdp.setPresentacion("Desarrolla tus habilidades mentales");
	 * fdp.setPropositos(
	 * "Proveer al alumno un ambiente agradable y conocimiento de alto nivel");
	 * fdp.setRequerimientoEquipo("windows xp"); fdp.setRequisitosIngreso(
	 * "Certificado de preparatoria e identificacion como IFE,CURP o Acta de Nacimiento"
	 * );
	 * 
	 * //Consultando Ambientes de Aprendizaje Moodle y seteando valor
	 * fdp.setCatAmbientesAprendizajeEc(map.map(ambientesAprendizajeService.
	 * buscarPorId(2), CatAmbientesAprendizajeEc.class));
	 * 
	 * //##### Activiades de Aprendizaje List
	 * <RelProgramaActividadesAprendizaje> lstAA = new ArrayList<>();
	 * RelProgramaActividadesAprendizaje aa1, aa2; aa1 = new
	 * RelProgramaActividadesAprendizaje();
	 * aa1.setCatActividadesAprendizajePrograma(
	 * map.map(catActAprendizajeService.buscarPorId(1,
	 * CatActividadesAprendizajePrograma.class),
	 * CatActividadesAprendizajePrograma.class )); //Solucion Problemas
	 * aa1.setFechaRegistro(fechaActual);
	 * aa1.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
	 * aa1.setTblFichaDescriptivaPrograma(fdp);
	 * aa1.setIdPrograma(fdp.getIdPrograma());
	 * aa1.setIdActividadAprendizaje(aa1.getCatActividadesAprendizajePrograma().
	 * getId()); //aa1.setIdPk(new
	 * RelProgramaActividadesAprendizajePK(fdp.getIdPrograma(),
	 * aa1.getCatActividadesAprendizajePrograma().getId()));
	 * //aa1.setTblFichaDescriptivaPrograma(fdp); lstAA.add(aa1);
	 * 
	 * aa2 = new RelProgramaActividadesAprendizaje();
	 * aa2.setCatActividadesAprendizajePrograma(
	 * map.map(catActAprendizajeService.buscarPorId(3,
	 * CatActividadesAprendizajePrograma.class),
	 * CatActividadesAprendizajePrograma.class)); //mapas conceptuales
	 * aa2.setFechaRegistro(fechaActual);
	 * aa2.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
	 * aa2.setTblFichaDescriptivaPrograma(fdp);
	 * aa2.setIdPrograma(fdp.getIdPrograma());
	 * aa2.setIdActividadAprendizaje(aa2.getCatActividadesAprendizajePrograma().
	 * getId()); lstAA.add(aa2);
	 * 
	 * fdp.setRelProgramaActividadesAprendizajes(lstAA); //##################
	 * logger.info("####### INIT TRANSACSION ####"); try{
	 * 
	 * TblFichaDescriptivaPrograma rs = fichaDescProgramaRepo.save(fdp);
	 * if(ObjectUtils.isNotNull(rs)){ System.out.println(rs.getIdPrograma()); }
	 * 
	 * }catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * private String obtenerErroresDeServicio(List<String> errores){
	 * StringBuffer cadena = new StringBuffer();
	 * if(!ObjectUtils.isNullOrEmpty(errores)){ for(String error : errores){
	 * cadena.append(error).append("\n"); } } return cadena.toString(); }
	 * 
	 */
	
	 @Test
		public void prueba() {
			assertThat(1==2, is(false));
		}
}
