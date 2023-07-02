package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.AsentamientoService;
import mx.gob.sedesol.basegestor.service.admin.DomicilioPersonaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class DomicilioPersonaServiceTest {

	private static final Logger logger = Logger.getLogger(DomicilioPersonaServiceTest.class);

	@Autowired
	private DomicilioPersonaService domicilioPersonaService;
	@Autowired
	private AsentamientoService asentamientoService;

	@Test
	public void guardarExito() {
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

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void guardarNulo() {
		DomicilioPersonaDTO domPerDto = null;
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.guardar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void guardarFallido() {
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();
		domPerDto.setFechaRegistro(null);

		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.guardar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void guardarFallidoSegundoNivel() {
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();
		domPerDto.setAsentamiento(new AsentamientoDTO());
		domPerDto.setPersona(new PersonaDTO());
		
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.guardar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
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

	@Test
	public void eliminarNulo() {
		DomicilioPersonaDTO domPerDto = null;
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.eliminar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarFallido() {
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();

		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.eliminar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarTodos() {
		List<DomicilioPersonaDTO> domicilios = domicilioPersonaService.findAll();
		assertThat(domicilios, is(not(empty())));
	}

	@Test
	public void actualizarExito() {
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
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.actualizar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloPersonaDiferente() {
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
	
	@Test
	public void actualizarFalloDomicioNoExiste() {
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();

		domPerDto.setIdDomicilioPersona(0L);
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

	@Test
	public void actualizarNulo() {
		DomicilioPersonaDTO domPerDto = null;
		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.actualizar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarFallido() {
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();

		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.actualizar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFallidoSegundoNivel() {
		DomicilioPersonaDTO domPerDto = new DomicilioPersonaDTO();
		domPerDto.setAsentamiento(new AsentamientoDTO());
		domPerDto.setPersona(new PersonaDTO());

		try {
			ResultadoDTO<DomicilioPersonaDTO> resultado = domicilioPersonaService.actualizar(domPerDto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarPorIdExito() {
		DomicilioPersonaDTO dto = domicilioPersonaService.buscarPorId(1L);
		assertThat(dto, allOf(hasProperty("idDomicilioPersona", is(1L)), hasProperty("numeroExterior", is("14"))));
	}

	@Test
	public void buscarPorIdFallido() {
		DomicilioPersonaDTO dto = domicilioPersonaService.buscarPorId(0L);
		assertThat(dto, is(nullValue()));
	}

}
