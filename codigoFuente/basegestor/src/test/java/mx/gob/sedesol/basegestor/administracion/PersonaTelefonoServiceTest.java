package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
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
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.PersonaTelefonoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@Transactional
public class PersonaTelefonoServiceTest {
	
	private static final Logger logger = Logger.getLogger(PersonaTelefonoServiceTest.class);

	@Autowired
	private PersonaTelefonoService personaTelefonoService;
	
	@Test
	public void buscarTodos() {
		List<PersonaTelefonoDTO> lista = personaTelefonoService.findAll();
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void buscarPorId() {
		PersonaTelefonoDTO dto = personaTelefonoService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}

	@Test
	public void buscarPorIdNull() {
		PersonaTelefonoDTO dto = personaTelefonoService.buscarPorId(0);
		assertThat(dto, is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(1L);
		dto.setTelefono("1315345615");

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void persistirFalloNulo() {
		PersonaTelefonoDTO dto = null;

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void persistirFalloDatos() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO();
		dto.setFechaRegistro(null);

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void persistirFalloDatosSegundoNivel() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO();
		dto.setPersona(new PersonaDTO());
		dto.setTipoTelefono(new TipoTelefonoDTO());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setIdPersonaTelefono(1);
		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(187L);
		dto.setTelefono("1315345615");
		
		dto.setFechaActualizacion(new Date());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarFalloNulo() {
		PersonaTelefonoDTO dto = null;

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarFalloDatos() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO();
		dto.setFechaRegistro(null);

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarFalloDatosSegundoNivel() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO();
		dto.setPersona(new PersonaDTO());
		dto.setTipoTelefono(new TipoTelefonoDTO());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarNoExiste() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setIdPersonaTelefono(0);
		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(187L);
		dto.setTelefono("1315345615");
		
		dto.setFechaActualizacion(new Date());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarPersonaDiferente() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setIdPersonaTelefono(1);
		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(1L);
		dto.setTelefono("1315345615");
		
		dto.setFechaActualizacion(new Date());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setIdPersonaTelefono(1);
		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(187L);
		dto.setTelefono("1315345615");
		
		dto.setFechaActualizacion(new Date());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarFalloNulo() {
		PersonaTelefonoDTO dto = null;

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarFalloDatos() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO();
		dto.setFechaRegistro(null);
		dto.setActivo(null);

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarFalloDatosSegundoNivel() {
		PersonaTelefonoDTO dto = new PersonaTelefonoDTO();
		dto.setPersona(new PersonaDTO());
		dto.setTipoTelefono(new TipoTelefonoDTO());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
