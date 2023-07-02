package mx.gob.sedesol.basegestor.gestion.aprendizaje;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescripcionOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescriptivaOaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.FichaDescriptivaOaService;


/**
 * clase de prueba del servicio de la entidad de TblFichaDescriptivaObjetoAprendizaje
 * 
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class FichaDescriptivaOaServiceTest {
	
	/**
	 * variable logger
	 */
	private static final Logger logger = Logger.getLogger(FichaDescriptivaOaServiceTest.class);
	
	/**
	 * Inyeccion del servicio 
	 */
	@Autowired	
	private FichaDescriptivaOaService fichaDescObjAprenService;
	
	
	
	/**
	 * Consulta todos los registros de ficha descriptiva
	 */
	
	@Test
	public void obtenerRegistrosFichaDescriptiva(){
		try{
		
		logger.info(":::::::::::obtenerRegistro FichaDescObjAprenService :::::::::::");
		
		
		List<FichaDescriptivaOaDTO> resultadoDTOs =   fichaDescObjAprenService.findAll();
		assertFalse(ObjectUtils.isNullOrEmpty(resultadoDTOs));
		logger.info("El tamanio de la lista de los objetos regresados es "+((resultadoDTOs == null)?"0":resultadoDTOs.size()));
		logger.info("::::::::::: Termina prueba correctamente :::::::::::");
		
		}catch(Exception e){
			e.printStackTrace();
			logger.info("Ocurrio una excepcion fallo obtenerRegistro");
			  assertNull(e);
				
			
		}
		
		
	}
	
	
	/**
	 * Busqueda de ficha descriptiva  por id 
	 */
	
	@Test
	public void busquedaFichaDescriptivaPorIdTest(){
	try{		
		logger.info(":::::::::::busqueda por id FichaDescObjAprenService :::::::::::");
		FichaDescriptivaOaDTO resultadoDTO =   fichaDescObjAprenService.buscarPorId(2);
		assertFalse(ObjectUtils.isNull(resultadoDTO.getIdFoa()));
				
		logger.info("El id del objeto regresado es "+((resultadoDTO == null)?"0":resultadoDTO.getIdFoa()));
		logger.info("::::::::::: Termina prueba correctamente :::::::::::");
		
		}catch(Exception e){
			e.printStackTrace();
			logger.info("Ocurrio una excepcion fallo obtenerRegistro");
			  assertNull(e);
				
			
		}
	
	}

	
	/**
	 * Guarda ficha descriptiva
	 */
	
	
	@Test
	@Transactional
    @Rollback(true)
	public void guardarFichaDescriptivaTest(){
		
		try{
		logger.info(":::::::::::guardar FichaDescObjAprenService :::::::::::");
		
		ResultadoDTO<FichaDescriptivaOaDTO> dto = 
		
		fichaDescObjAprenService.guardar(this.generaFichaDescriptiva());
		
		
		logger.info("Se guardo exitosamente");
		
		assertEquals(ResultadoTransaccionEnum.EXITOSO, dto.getResultado());
		
		}catch(Exception e){
		e.printStackTrace();
		logger.info("Ocurrio una excepcion fallo saveTest");
		  assertNull(e);
			
		}
		
		
		
	}
	
	/**
	 * Insertar ficha descripcion
	 */
	@Test
	public void guardarFichaDescriptivaDescripcionTest(){
		
		try{
		logger.info(":::::::::::guardar FichaDescObjAprenService :::::::::::");
		
		
		FichaDescriptivaOaDTO descriptivaOaDTO = this.generaFichaDescriptiva(); 
		FichaDescripcionOaDTO descripcionOaDTO = this.generaFichaDescripcion();
		
		ResultadoDTO<FichaDescriptivaOaDTO> dto = 
								
		fichaDescObjAprenService.guardar(descriptivaOaDTO);
			
		dto.getDto().setFichaDescripcionOa(descripcionOaDTO);
		//descripcionOaDTO.setFichaDescriptivaOa(dto.getDto());
		

		dto.getDto().setUsuarioModifico(1L);
		dto.getDto().setFechaActualizacion(new Date());
		
		ResultadoDTO<FichaDescriptivaOaDTO> resultadoActualizaDto = 
				
		
		fichaDescObjAprenService.actualizar(dto.getDto());
		
		assertEquals(ResultadoTransaccionEnum.EXITOSO, resultadoActualizaDto.getResultado());

		
		logger.info("Se guardo exitosamente");
		
		
		
		}catch(Exception e){
		e.printStackTrace();
		logger.info("Ocurrio una excepcion fallo saveTest");
		  assertNull(e);
			
		}
		}
		
	
	
	
	/**
	 * Actualiza ficha rescriptiva
	 * @return
	 */
	@Transactional
    @Rollback(true)
	@Test
	public void actualizaTestFichadescritpivaTest(){
		
		try{
			logger.info(":::::::::::actualizaTestFichadescritpivaTest FichaDescObjAprenService :::::::::::");
					

			ResultadoDTO<FichaDescriptivaOaDTO> dto = 
			
			fichaDescObjAprenService.guardar(this.generaFichaDescriptiva());
			
			dto.getDto().setDescripcionContenido("Contenido actualizado nuevamente");
			dto.getDto().setFechaActualizacion(new Date());

			dto.getDto().setUsuarioModifico(1L);
			
			
			ResultadoDTO<FichaDescriptivaOaDTO> actualizaDTO = 
				
			
			fichaDescObjAprenService.actualizar(dto.getDto());
			
			
			assertEquals(ResultadoTransaccionEnum.EXITOSO, actualizaDTO.getResultado());

			logger.info("Se actualizo exitosamente");
			
			
			}catch(Exception e){
			e.printStackTrace();
			logger.info("Ocurrio una excepcion fallo saveTest");
			  assertNull(e);
				
			}
			
	} 
	
	/**
	 * Borrar ficha descriptiva.
	 */
	@Transactional
    @Rollback(true)
	@Test
	public void borraFichadescriptivaTest(){
		try{
		logger.info(":::::::::::borraFichadescriptivaTest FichaDescObjAprenService :::::::::::");
		

		ResultadoDTO<FichaDescriptivaOaDTO> dto = 
		
		fichaDescObjAprenService.guardar(this.generaFichaDescriptiva());

		logger.info("Se borrara el registro con el id = "+dto.getDto().getIdFoa());
				
		ResultadoDTO<FichaDescriptivaOaDTO> borraDTO = 
				
		fichaDescObjAprenService.eliminar(dto.getDto());
				
		
		assertEquals(ResultadoTransaccionEnum.EXITOSO, borraDTO.getResultado());

		
		logger.info("Se borro  exitosamente el registro");
		
		
		}catch(Exception e){
		e.printStackTrace();
		logger.info("Ocurrio una excepcion fallo saveTest");
		  assertNull(e);
			
		}
		
	}
	
	
	/**
	 * Guarda ficha descriptiva con la fecha de registro nulo
	 */
	@Test	    
	public void guardarFichaDescriptivaTestSinFechaRegistro(){
		
		try{
		logger.info(":::::::::::guardar guardarFichaDescriptivaTestSinFechaRegistro :::::::::::");
		
		
		FichaDescriptivaOaDTO descriptivaOaDTO = this.generaFichaDescriptiva();
		descriptivaOaDTO.setFechaRegistro(null);
		
		ResultadoDTO<FichaDescriptivaOaDTO> dto = 
		
		fichaDescObjAprenService.guardar(descriptivaOaDTO);
		
		assertEquals(MensajesErrorEnum.ERROR_VALOR_REQ, dto.getMensajeError());
		
		}catch(Exception e){
		e.printStackTrace();
		logger.info("Ocurrio una excepcion fallo saveTest");
		 assertNull(e);
			
		}
		
	}

		/**
		 *Guarda ficha descriptiva con el usuario modifico nulo 
		 */
		@Test	    
		public void guardarFichaDescriptivaTestSinUsuarioModifico(){
			
			try{
			logger.info(":::::::::::guardar guardarFichaDescriptivaTestSinFechaRegistro :::::::::::");
			
			
			FichaDescriptivaOaDTO descriptivaOaDTO = this.generaFichaDescriptiva();
			descriptivaOaDTO.setUsuarioModifico(null);
			
			ResultadoDTO<FichaDescriptivaOaDTO> dto = 
			
			fichaDescObjAprenService.guardar(descriptivaOaDTO);
			
			assertEquals(MensajesErrorEnum.ERROR_VALOR_REQ, dto.getMensajeError());
			
			}catch(Exception e){
			e.printStackTrace();
			logger.info("Ocurrio una excepcion fallo saveTest");
			  assertNull(e);
				
			}
		
		
	}
		
		

		/**
		 * Actualiza ficha rescriptiva con id foa nulo
		 * @return
		 */
		@Transactional
	    @Rollback(true)
		@Test
		public void actualizaTestFichadescritpivaIdFoaNullTest(){
			
			try{
				logger.info(":::::::::::actualizaTestFichadescritpivaTest FichaDescObjAprenService :::::::::::");
						

				ResultadoDTO<FichaDescriptivaOaDTO> dto = 
				
				fichaDescObjAprenService.guardar(this.generaFichaDescriptiva());
				
				dto.getDto().setDescripcionContenido("Contenido actualizado nuevamente");
				dto.getDto().setFechaActualizacion(new Date());

				dto.getDto().setIdFoa(null);
				
				ResultadoDTO<FichaDescriptivaOaDTO> actualizaDTO = 
					
				
				fichaDescObjAprenService.actualizar(dto.getDto());
				
				
				assertEquals(MensajesErrorEnum.ERROR_ID_REQ, actualizaDTO.getMensajeError());
				
				
				logger.info("Se actualizo exitosamente");
				
				
				
				}catch(Exception e){
				e.printStackTrace();
				logger.info("Ocurrio una excepcion fallo saveTest");
				  assertNull(e);
					
				}
				
		} 
	
	
		/**
		 * Actualiza ficha rescriptiva con usuario modifico nulo
		 * @return
		 */
		@Transactional
	    @Rollback(true)
		@Test
		public void actualizaTestFichaDescriptivaUsuarioModificoNullTest(){
			
			try{
				logger.info(":::::::::::actualizaTestFichadescritpivaTest FichaDescObjAprenService :::::::::::");
						

				ResultadoDTO<FichaDescriptivaOaDTO> dto = 
				
				fichaDescObjAprenService.guardar(this.generaFichaDescriptiva());
				
				dto.getDto().setDescripcionContenido("Contenido actualizado nuevamente");
				dto.getDto().setFechaActualizacion(new Date());
				dto.getDto().setUsuarioModifico(null);
				
				
				ResultadoDTO<FichaDescriptivaOaDTO> actualizaDTO = 
					
				
				fichaDescObjAprenService.actualizar(dto.getDto());
				
				
				assertEquals(MensajesErrorEnum.ERROR_VALOR_REQ, actualizaDTO.getMensajeError());
				
				
				logger.info("Se actualizo exitosamente");
				
				
				
				}catch(Exception e){
				e.printStackTrace();
				logger.info("Ocurrio una excepcion fallo saveTest");
				  assertNull(e);
					
				}
				
		} 
	
		
	
		/**
		 * Borrar ficha descriptiva con el id foa nullo.
		 */
		@Transactional
	    @Rollback(true)
		@Test
		public void borraFichadescriptivaEliminaIdFoaNullTest(){
			try{
			logger.info("::::::::::: borraFichadescriptivaEliminaIdFoaNullTest :::::::::::");
			

			ResultadoDTO<FichaDescriptivaOaDTO> dto = 
			
			fichaDescObjAprenService.guardar(this.generaFichaDescriptiva());

			logger.info("Se borrara el registro con el id = "+dto.getDto().getIdFoa());
					
			dto.getDto().setIdFoa(null);
			
			ResultadoDTO<FichaDescriptivaOaDTO> borraDTO = 																				
			fichaDescObjAprenService.eliminar(dto.getDto());
					
			

			assertEquals(MensajesErrorEnum.ERROR_ID_REQ, borraDTO.getMensajeError());
			
			
			}catch(Exception e){
			e.printStackTrace();
			logger.info("Ocurrio una excepcion fallo saveTest");
			  assertNull(e);
				
			}
			
		}


		/**
		 * Busqueda de ficha descriptiva  por id que no existe en la base 
		 * de datos.
		 */
		
		@Test
		public void busquedaFichaDescriptivaPorIdInexistenteTest(){
		try{		
			logger.info(":::::::::::busqueda por id FichaDescObjAprenService :::::::::::");
			FichaDescriptivaOaDTO resultadoDTO =   fichaDescObjAprenService.buscarPorId(10000);
			assertTrue(ObjectUtils.isNullOrEmpty(resultadoDTO.getIdFoa()));
			logger.info("::::::::::: Termina prueba correctamente :::::::::::");
			
			}catch(Exception e){
				e.printStackTrace();
				logger.info("Ocurrio una excepcion fallo obtenerRegistro");
				  assertNull(e);
					
				
			}
		
		}
		
		
		/**
		 * Borra fichaDescriptiva con objeto nulo 
		 */
		
		@Test
		public void borraFichadescriptivaObjetoNulo(){
			try{
			logger.info("::::::::::: borraFichadescriptivaEliminaIdFoaNullTest :::::::::::");
			
			
			ResultadoDTO<FichaDescriptivaOaDTO> borraDTO = 																				
			fichaDescObjAprenService.eliminar(null);

			assertNull(borraDTO);
			
			}catch(Exception e){
			e.printStackTrace();
			logger.info("Ocurrio una excepcion fallo saveTest");
			  assertNull(e);
				
			}
			
		}
	
	
	
	private FichaDescriptivaOaDTO generaFichaDescriptiva(){
		
		FichaDescriptivaOaDTO dto = new FichaDescriptivaOaDTO();			
		dto.setDescripcionContenido("Estos son los contenidos");
		dto.setFechaRegistro(new Date());

		dto.setUsuarioModifico(1L);
		
		return dto;
	}
	
	
	private FichaDescripcionOaDTO generaFichaDescripcion(){		
	 	
		FichaDescripcionOaDTO dto = new FichaDescripcionOaDTO();

	return dto;
}
	
	

}
