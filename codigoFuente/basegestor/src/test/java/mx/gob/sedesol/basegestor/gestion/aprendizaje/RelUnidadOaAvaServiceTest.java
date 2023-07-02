package mx.gob.sedesol.basegestor.gestion.aprendizaje;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.FichaDescriptivaOaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelUnidadOaAvaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class RelUnidadOaAvaServiceTest {
	
	
	/**
	 * variable logger
	 */
	private static final Logger logger = Logger.getLogger(RelUnidadOaAvaServiceTest.class);
	
	/**
	 * Inyeccion del servicio 
	 */
	@Autowired	
	private RelUnidadOaAvaService relUnidadOaAvaService;
	
	
	//@Test
	public void actualizaTest(){
		List<UnidadOaAvaDTO> unidadOaAvaDTO =  
		relUnidadOaAvaService.findByIdAva(1);
		
		UnidadOaAvaDTO unidadOaAvaDTOSeleccionado = unidadOaAvaDTO.get(0); 
		
		unidadOaAvaDTOSeleccionado.setValidacionDesarrolloOa(false);
		unidadOaAvaDTOSeleccionado.setValidacionGuionInst(false);
		unidadOaAvaDTOSeleccionado.setValidacionScorm(false);
		
		ResultadoDTO<UnidadOaAvaDTO>  resultado = 
		relUnidadOaAvaService.actualizar(unidadOaAvaDTOSeleccionado);
		
		logger.info("El resultado de la actualizacion fue "+resultado.getResultado());
		
		List<UnidadOaAvaDTO> unidadOaAvaDTO2 =  
				relUnidadOaAvaService.findByIdAva(1);
		
		
		logger.info("El numero de registros es "+unidadOaAvaDTO2.size());
		
		List<UnidadOaAvaDTO> unidadOaAvaDTO3 =  
				relUnidadOaAvaService.findAll();
		
		logger.info("El tamaño de la lista del find all es "+unidadOaAvaDTO3);
	}
	
	
	//@Test 
	public void guardarTest(){
		AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO = new AmbienteVirtualAprendizajeDTO();
		ambienteVirtualAprendizajeDTO.setId(1);
		
		DetEstUniDidacticaDTO detEstUniDidacticaDTO = new DetEstUniDidacticaDTO();
		detEstUniDidacticaDTO.setIdUnidadDidactica(3);
		
		UnidadOaAvaDTO unidadOaAvaDTO = new UnidadOaAvaDTO();
		
		unidadOaAvaDTO.setAmbienteVirtualAprendizaje(ambienteVirtualAprendizajeDTO);
		
		unidadOaAvaDTO.setValidacionDesarrolloOa(true);
		unidadOaAvaDTO.setValidacionGuionInst(true);
		unidadOaAvaDTO.setValidacionScorm(true);
		unidadOaAvaDTO.setDetEstUnidadDidactica(detEstUniDidacticaDTO);
		unidadOaAvaDTO.setUsuarioModifico(new BigInteger("2") );
		unidadOaAvaDTO.setFechaRegistro(new Date());
		relUnidadOaAvaService.guardar(unidadOaAvaDTO);
		
		
		
		List<UnidadOaAvaDTO>  avaDTOs  = 
				
		relUnidadOaAvaService.findByIdAva(1);
		
		logger.info("El tamaño es "+avaDTOs.size());
		
	}
	
	@Test
	public void findByIdAvaTest(){
		
		Integer idAva = 1;
		
		List<UnidadOaAvaDTO>  avaDTOs  = 
		
		relUnidadOaAvaService.findByIdAva(idAva);
		
		logger.info("El tamaño es "+avaDTOs.size());
		
	}
	
	@Test
	public void dummy(){
		Assert.assertTrue(true);
		
	}

}
