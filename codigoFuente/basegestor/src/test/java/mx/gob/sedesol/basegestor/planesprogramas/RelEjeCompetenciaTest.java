package mx.gob.sedesol.basegestor.planesprogramas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEjeCompetenciaDTO;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciaEspecifica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblMallaCurricular;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EjeCompetenciaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class RelEjeCompetenciaTest {
	
	private static final Logger logger = Logger.getLogger(RelEjeCompetenciaTest.class);

	@Autowired
	private CatalogoComunService<CatCompetenciaEspecifica, Integer> competenciaEspecificaService;
	
	@Autowired
	private MallaCurricularService mallaCurricularService;
	
	@Autowired
	private EjeCompetenciaService ejeCompetenciaService;

	//@Test
	public void guardarEjeCompetenciaDB() {

		MallaCurricularDTO eje = mallaCurricularService.buscarPorId(1);
		CatalogoComunDTO com = competenciaEspecificaService.buscarPorId(1, CatCompetenciaEspecifica.class);
		
		ModelMapper mapper = new ModelMapper();
		
		RelEjeCompetenciaDTO ec = new RelEjeCompetenciaDTO();
		ec.setIdCompetenciaEspecifica(com.getId());
		ec.setIdMallaCurricular(eje.getId());
		ec.setFechaRegistro(new Date());
		//ec.setCatCompetenciaEspecifica(mapper.map(com,CatCompetenciaEspecifica.class));
		//ec.setTblMallaCurricular(mapper.map(eje,TblMallaCurricular.class));
		
		logger.info("###### GUARDAR: " + ejeCompetenciaService.guardar(ec));
	}
	
	@Test
	public void eliminarEjeCompetenciaDB(){
		RelEjeCompetenciaDTO ec = new RelEjeCompetenciaDTO();
		
		ec.setIdCompetenciaEspecifica(1);
		ec.setIdMallaCurricular(1);
		ec.setFechaRegistro(new Date());
		ec.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		
		logger.info("###### BORRAR: " + ejeCompetenciaService.eliminar(ec));
	}
	
	//@Test
	public void buscarPorEjeDB(){
		logger.info("###### OBTENER_COMPETENCIAS_ESPECIFICAS_POR_EJE: " + ejeCompetenciaService.obtenerCompetenciasEspecificasPorEje(1));
	}
	
	//@Test
	public void buscarCompetenciasNoAsignadasDB(){
		logger.info("###### OBTENER_COMPETENCIAS_NO_ASIGNADAS: " + ejeCompetenciaService.obtieneCompetenciasEspecificasNoAsignadas(1));
	}
	
	 @Test
		public void prueba() {
			assertThat(1==2, is(false));
		}
}
