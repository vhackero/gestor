package mx.gob.sedesol.basegestor.gestionescolar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })

public class EventoCapacitacionServiceTest {

	private static final Logger logger = Logger.getLogger(EventoCapacitacionServiceTest.class);
	
	@Autowired
	private EventoCapacitacionService eventoCapacitacionService; 
	
	
	
	//@Test
	public void consultaEventoPorEstatusTest(){
		
		Integer idEstatus = 1;
		
		
		try{
		
		logger.info("Inicia la prueba de consultaEventoPorEstatusTest");
		
		List<EventoCapacitacionDTO> eventoCapacitacionDTOs = 								
		eventoCapacitacionService.consultaEventoPorEstatus(idEstatus);
		
		logger.info("termina la prueba de consultaEventoPorEstatusTest");
		
		
		assertFalse(ObjectUtils.isNullOrEmpty(eventoCapacitacionDTOs));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Excepcion inesperada, la prueba unitaria fallo ");
			assertNull(e);
		}
		
		
	}
	
	
	//@Test
	public void busquedaPorCriterioTest(){
		
		EventoCapacitacionDTO filtro = new EventoCapacitacionDTO();
		
		filtro.setNombreEc("EVENTO INTRODUCTORIO");
		
		try{
			
			logger.info("Inicia la prueba de busquedaPorCriterioTest");
			
			List<EventoCapacitacionDTO> eventoCapacitacionDTOs = 								
			eventoCapacitacionService.buscaEventosPorCriterios(filtro,null);
			
			logger.info("termina la prueba de busquedaPorCriterioTest");
			
			assertFalse(ObjectUtils.isNullOrEmpty(eventoCapacitacionDTOs));
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Excepcion inesperada, la prueba unitaria fallo ");
			assertNull(e);
		}
		
		
		
	}
	
	
	//@Test
	public void getEventoTest(){
		
		Integer idEvento  = 1;
		
		EventoCapacitacionDTO eventoCapacitacionDTO = null;
		
		try{
		logger.info("Inicia la prueba de getEventoTest");
		
		eventoCapacitacionDTO = 
		
		eventoCapacitacionService.getEvento(idEvento);
		
		
		assertFalse(ObjectUtils.isNull(eventoCapacitacionDTO));
		
		
		logger.info("termina la prueba de getEventoTest");
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("Excepcion inesperada, la prueba unitaria fallo ");
			assertNull(e);
			
		}
		
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
	

	
	
}
