package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblDomiciliosPersona;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.repositories.admin.DomicilioPersonaRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.AsentamientoService;
import mx.gob.sedesol.basegestor.service.admin.DomicilioPersonaService;
import mx.gob.sedesol.basegestor.service.impl.admin.DomicilioPersonaServiceImpl;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class DomicilioPersonaServiceMockTest {
/*	
	/**
	 * Variable logger
	 */
/*	private static final Logger logger = Logger.getLogger(DomicilioPersonaServiceMockTest.class);

	/**
	 * Mock de DomicilioPersonaRepo
	 */
/*	@Mock
/*	private DomicilioPersonaRepo domicilioPersonaRepo;
	
	/**
	 * Mock de AdministradorBitacora
	 */
/*	@Mock
	private AdministradorBitacora administradorBitacora;
	
	/**
	 * Mock de DomicilioPersonaService con @InjectMock para 
	 * la inyeccion de mocks
	 */
	/*	@InjectMocks
	private DomicilioPersonaService domicilioPersonaService = new  DomicilioPersonaServiceImpl(); 
	
	@Autowired
	private AsentamientoService asentamientoService;
	
	/**
	 * Inicialización e inyeccion de las propiedades marcadas con 
	 * @Mock
	 */
/*	@Before
/*	public void initMocks(){
		MockitoAnnotations.initMocks(this);	
	}
	
	
	
	/**
	 * Test que hace un Mock de la clase domicilioPersonaService del metodo
	 * eliminar que emula una Excepcion(NullpointerException) cuando 
	 * es invocado el metodo saveAndFlush de la clase DomicilioPersonaRepo
	 */
/*	@Test 
/*	public void   eliminarPersonaExceptionTest(){
		
		TblDomiciliosPersona entidad = new TblDomiciliosPersona();
		entidad.setCalle("Calle false");
		entidad.setIdDomicilioPersona(1l);
		
		
		/**
		 * Configuracion del Mock 
		 */
/*		Mockito.when(domicilioPersonaRepo.saveAndFlush((TblDomiciliosPersona)Mockito.anyObject())).
/*		thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		/**
		 * Configuracion del mock
		 */
/*		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString());	

/*		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();

		domPerDto.setIdDomicilioPersona(1L);
		domPerDto.setActivo(1);
		domPerDto.setCalle("Felipe Mu\u00F1oz, coyoacan");
		domPerDto.setFechaRegistro(new Date());
		domPerDto.setNumeroExterior("14");
		domPerDto.setPersona(new PersonaDTO());
		domPerDto.getPersona().setIdPersona(2L);
		domPerDto.setFechaActualizacion(new Date());
		domPerDto.setUsuarioModifico(1L);
		domPerDto.setAsentamiento(asentamientoService.buscarPorId("100010409"));
		
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.eliminar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
		
	}
*/	
	/**
	 * Test que realiza un Mock de la clase AsentamientoService del metodo
	 * actualizar que emula una Excepcion(NulpointerException) cuando
	 * es invocado el metodo   saveAndFlush de 
	 * la clase DomicilioPersonaRepo
	 */
/*	@Test
	public void actualizarExceptionTest(){
		TblDomiciliosPersona entidad = new TblDomiciliosPersona();
		entidad.setCalle("Calle false");
		entidad.setIdDomicilioPersona(1l);
		
		TblDomiciliosPersona tblDomiciliosPersona  = new TblDomiciliosPersona();  
		tblDomiciliosPersona.setIdDomicilioPersona(1L);
		tblDomiciliosPersona.setPersona(new TblPersona());
		tblDomiciliosPersona.getPersona().setIdPersona(1L);
		/**
		 * Configuracion del mock
		 */
/*		Mockito.when(domicilioPersonaRepo.findOne(Mockito.anyLong())).thenReturn(tblDomiciliosPersona);
		
		/**
		 * Configuracion del Mock 
		 */
/*		Mockito.when(domicilioPersonaRepo.saveAndFlush((TblDomiciliosPersona)Mockito.anyObject())).
		thenThrow(new NullPointerException("Esta es una excepcion provocada"));			
		
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();
								
		domPerDto.setIdDomicilioPersona(1L);
		domPerDto.setActivo(1);
		domPerDto.setCalle("Felipe Mu\u00F1oz, coyoacan");
		domPerDto.setFechaRegistro(new Date());
		domPerDto.setNumeroExterior("14");
		domPerDto.setPersona(new PersonaDTO());
		domPerDto.getPersona().setIdPersona(1L);
		domPerDto.setFechaActualizacion(new Date());
		domPerDto.setUsuarioModifico(1L);		
		domPerDto.setAsentamiento(asentamientoService.buscarPorId("100010409"));
		
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.actualizar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
*/	
	/**
	 * Clase Test que reliza un Mock de la calse AsentamientoService
	 * del metodo buscarPorId que emula una Excepcion(NullPointerException)
	 * cuando es invocado el metodo save de la clase DomicilioPersonaRepo.
	 */
/*	@Test
	public void guardarExceptionTest(){

		/**
		 * Configuracion del Mock 
		 */
/*		Mockito.when(domicilioPersonaRepo.save((TblDomiciliosPersona)Mockito.anyObject())).
		thenThrow(new NullPointerException("Esta es una excepcion provocada"));			
		
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();

		domPerDto.setActivo(1);
		domPerDto.setCalle("Felipe Mu\u00F1oz, coyoacan");
		domPerDto.setFechaRegistro(new Date());
		domPerDto.setNumeroExterior("14");
		domPerDto.setUsuarioModifico(1L);
		domPerDto.setPersona(new PersonaDTO());
		domPerDto.getPersona().setIdPersona(1L);
		domPerDto.setAsentamiento(asentamientoService.buscarPorId("100010409"));
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.guardar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	/**
	 * Test que hace un Mock de la clase domicilioPersonaService
	 * del metodo saveAndFlush que simula el regreso de un objeto
	 * TblDomiciliosPersona
	 */
/*	@Test 
	public void   eliminarPersonaMockCorrect(){
		
		TblDomiciliosPersona entidad = new TblDomiciliosPersona();
		entidad.setCalle("Calle false");
		entidad.setIdDomicilioPersona(1l);
		
		
		/**
		 * Mockito para metodos que se esperan datos 
		 */
/*		Mockito.when(domicilioPersonaRepo.saveAndFlush((TblDomiciliosPersona)Mockito.anyObject())).
		thenReturn(entidad);
				
		/**
		 * Mockito para metodos void
		 */
/*		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString());							
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();
		domPerDto.setIdDomicilioPersona(1L);
		domPerDto.setActivo(1);
		domPerDto.setCalle("Felipe Mu\u00F1oz, coyoacan");
		domPerDto.setFechaRegistro(new Date());
		domPerDto.setNumeroExterior("14");
		domPerDto.setPersona(new PersonaDTO());
		domPerDto.getPersona().setIdPersona(2L);
		domPerDto.setFechaActualizacion(new Date());
		domPerDto.setUsuarioModifico(1L);
		domPerDto.setAsentamiento(asentamientoService.buscarPorId("100010409"));				
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.eliminar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}								
	}
*/	
}
