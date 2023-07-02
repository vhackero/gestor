package mx.gob.sedesol.basegestor.gestion.aprendizaje;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;

/**
 * 
 * Clase de pueba del servicio AmbienteVirtualApService
 * @author egonzalez
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class AmbienteVirtualApTest {

	
	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(AmbienteVirtualApTest.class);
	
	/**
	 * Inyeccion del servicio
	 */
	@Autowired
	private AmbienteVirtualApService ambienteVirtualApService;
	
	
	/**
	 * Metodo que prueba la busqueda por estatus
	 */
	//@Test
	/*public void consultaEventoPorEstatusTest (){
		try{
		logger.info("********** Incia la prueba de busqueda por estatus **********");
		String estadoAvaEnSolicitu = "En solicitud";  
		
		List<EventoCapacitacionAvaDTO>  eventoCapacitacionAvaDTOs = 
		ambienteVirtualApService.consultaEventoPorEstatus(estadoAvaEnSolicitu);
		logger.info("********** El tamanio de la lista extraida es de "+
					eventoCapacitacionAvaDTOs.size()+" **********"); 
		assertNotNull(eventoCapacitacionAvaDTOs);
		
		
		logger.info("********** Termina exitosamente la prueba de busqueda por estatus **********");
		}catch(Exception e){
			e.printStackTrace();
			logger.info("********** Ocurrio una excepcion inesperada en la prueba de busqueda por estatus **********");
			 assertNull(e);
			
		}
	}*/
	
	@Test
	public void actualizaTest(){
		AmbienteVirtualAprendizajeDTO  eventoCapacitacionAvaDTO = 		
		ambienteVirtualApService.findAvaById(1);
		
		eventoCapacitacionAvaDTO.setFechaActualizacion(new Date());
		eventoCapacitacionAvaDTO.setFechaRegistro(new Date());
		eventoCapacitacionAvaDTO.setUsuarioModifico(1l);
		
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado = 
		ambienteVirtualApService.actualizar(eventoCapacitacionAvaDTO);
		
		
		logger.info("El resultado de la actualizacion fue "+resultado.getMensaje());
		
		AmbienteVirtualAprendizajeDTO  eventoCapacitacionAvaDTO2 = 		
				ambienteVirtualApService.findAvaById(1);
		
		logger.info("El objeto recuperado es "+eventoCapacitacionAvaDTO2.getId());
				
		
		
		
		
		
		
	}
	
	
	
	//@Test
	public void getAvaById(){
		
		Integer idAva = 1;
		
		AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO =  
		
		ambienteVirtualApService.findAvaById(idAva);
		
		logger.info("El valor del objeto regresado es "+ambienteVirtualAprendizajeDTO.getId());
		
	}
	
	
	//@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
	
	
	
}
