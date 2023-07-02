package mx.gob.sedesol.basegestor.gestion.aprendizaje;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescriptivaOaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblFichaDescriptivaObjetoAprendizaje;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.FichaDescriptivaOaRepo;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.FichaDescriptivaOaService;
import mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje.FichaDescriptivaOaServiceImpl;

public class FichaDescriptivaOaServiceMockTest {

	private static final Logger logger = Logger.getLogger(FichaDescriptivaOaServiceMockTest.class);
	
	
	@Mock
	private FichaDescriptivaOaRepo fichaDescriptivaObjetoAprendizajeRepo;
	
	/**
	 * Inyeccion del servicio 
	 */
	@InjectMocks
	private FichaDescriptivaOaService fichaDescObjAprenService = new FichaDescriptivaOaServiceImpl();
	
	
	@Before
	public void init(){
		  MockitoAnnotations.initMocks(this);
	}
	
	
	/**
	 * Metod que prueba el manejo de excepcion en el metodo findAll
	 * de la clase fichaDescObjAprenService 
	 */
	
	@Test
	public void findAllTestexception(){
		
		
		Mockito.when(fichaDescriptivaObjetoAprendizajeRepo.findAll()).
				thenThrow(new NullPointerException("Esta es una excepcion controlada"));
		
		 List<FichaDescriptivaOaDTO> fichaDescriptivaOaDTOs = null; 
		
		try{
			
			fichaDescriptivaOaDTOs = 
					fichaDescObjAprenService.findAll();
			 
			Assert.assertTrue(ObjectUtils.isNullOrEmpty(fichaDescriptivaOaDTOs));
		
		}catch(Exception e){			
			e.printStackTrace();
			logger.error("Ocurrio una excepcion no esperada en la prueba findAllTestexception");
			Assert.assertNull(e);
			
		}
		
		
	}
	
	
	@Test 
	public void guardarTestException(){

		Mockito.when(fichaDescriptivaObjetoAprendizajeRepo.
				save((TblFichaDescriptivaObjetoAprendizaje)Mockito.anyObject())).
					thenThrow(new NullPointerException("Esta es una excepcion controlada"));

		FichaDescriptivaOaDTO fichaDescriptivaOaDTO = new FichaDescriptivaOaDTO();
		fichaDescriptivaOaDTO.setFechaRegistro(new Date());

		fichaDescriptivaOaDTO.setUsuarioModifico(1L);
		
		ResultadoDTO<FichaDescriptivaOaDTO> resultado = null;
		
		try{
			
			resultado = 
			fichaDescObjAprenService.guardar(fichaDescriptivaOaDTO);
			
			Assert.assertEquals(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS,resultado.getMensajeError());
			
			
		}catch(Exception e){			
			e.printStackTrace();
			logger.error("Ocurrio una excepcion no esperada en la prueba guardarTestException");
			Assert.assertNull(e);
			
		}
		
		
		
	}
	
	@Test
	public void actualizarTestException(){
			
		Mockito.when(fichaDescriptivaObjetoAprendizajeRepo.
				saveAndFlush((TblFichaDescriptivaObjetoAprendizaje)Mockito.anyObject())).
					thenThrow(new NullPointerException("Esta es una excepcion controlada"));
		
		
		FichaDescriptivaOaDTO fichaDescriptivaOaDTO = new FichaDescriptivaOaDTO();
		fichaDescriptivaOaDTO.setFechaActualizacion(new Date());

		fichaDescriptivaOaDTO.setUsuarioModifico(1L);
		fichaDescriptivaOaDTO.setIdFoa(1);
		
		ResultadoDTO<FichaDescriptivaOaDTO> resultado = null;
		
		try{
			
			resultado = 
					fichaDescObjAprenService.actualizar(fichaDescriptivaOaDTO);
			
			Assert.assertEquals(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS,resultado.getMensajeError());
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Ocurrio una excepcion no esperada en la prueba guardarTestException");
			Assert.assertNull(e);
			
		}
		
		
		
	}
	
	@Test
	public void eliminaTestExcepcion(){
			
		Mockito.doThrow(new NullPointerException("Esta es una excepcion controlada")).
				when(fichaDescriptivaObjetoAprendizajeRepo).
						delete((TblFichaDescriptivaObjetoAprendizaje)Mockito.anyObject());
		
		FichaDescriptivaOaDTO fichaDescriptivaOaDTO = new FichaDescriptivaOaDTO();		
		fichaDescriptivaOaDTO.setIdFoa(1);
		
		ResultadoDTO<FichaDescriptivaOaDTO> resultado = null;
		
		try{			
			resultado = 
					fichaDescObjAprenService.eliminar(fichaDescriptivaOaDTO);
			
			Assert.assertEquals(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS,resultado.getMensajeError());						
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Ocurrio una excepcion no esperada en la prueba guardarTestException");
			Assert.assertNull(e);					
		}
		
		
	}
	
	
	
	
	
}
