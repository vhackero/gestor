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

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@Transactional
public class PersonaCorreoServiceTest {

	private static final Logger logger = Logger.getLogger(PersonaCorreoServiceTest.class);

	@Autowired
	private PersonaCorreoService personaCorreoService;

	@Test
	public void buscarTodos() {
		List<PersonaCorreoDTO> lista = personaCorreoService.findAll();
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void buscarPorId() {
		PersonaCorreoDTO dto = personaCorreoService.buscarPorId(6);
		assertThat(dto, is(not(nullValue())));
	}

	@Test
	public void buscarPorIdNull() {
		PersonaCorreoDTO dto = personaCorreoService.buscarPorId(0);
		assertThat(dto, is(nullValue()));
	}

	@Test
	public void persistirExito() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();

		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setCorreoElectronico("correo@correo.com");

		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(1L);
		dto.setTipoCorreo(new TipoCorreoDTO());
		dto.getTipoCorreo().setIdTipoCorreo(1);

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void persistirFalloNulo() {
		PersonaCorreoDTO dto = null;

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void persistirFalloDatos() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();
		dto.setFechaRegistro(null);

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void persistirFalloDatosSegundoNivel() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();
		dto.setPersona(new PersonaDTO());
		dto.setTipoCorreo(new TipoCorreoDTO());

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarExito() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();

		dto.setIdPersonaCorreo(6);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setCorreoElectronico("correo@correo.com");

		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(11L);
		dto.setTipoCorreo(new TipoCorreoDTO());
		dto.getTipoCorreo().setIdTipoCorreo(1);

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarFalloNulo() {
		PersonaCorreoDTO dto = null;

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarFalloDatos() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarFalloDatosSegundoNivel() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();
		dto.setPersona(new PersonaDTO());
		dto.setTipoCorreo(new TipoCorreoDTO());

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarExitoNoExiste() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();

		dto.setIdPersonaCorreo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setCorreoElectronico("correo@correo.com");

		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(11L);
		dto.setTipoCorreo(new TipoCorreoDTO());
		dto.getTipoCorreo().setIdTipoCorreo(1);

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarExitoPersonaDiferente() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();

		dto.setIdPersonaCorreo(6);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setCorreoElectronico("correo@correo.com");

		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(1L);
		dto.setTipoCorreo(new TipoCorreoDTO());
		dto.getTipoCorreo().setIdTipoCorreo(1);

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarExito() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();

		dto.setIdPersonaCorreo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setCorreoElectronico("correo@correo.com");
		dto.setActivo(1);

		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(1L);
		dto.setTipoCorreo(new TipoCorreoDTO());
		dto.getTipoCorreo().setIdTipoCorreo(1);

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarFalloNulo() {
		PersonaCorreoDTO dto = null;

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarFalloDatos() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();
		dto.setActivo(null);

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarFalloDatosSegundoNivel() {
		PersonaCorreoDTO dto = new PersonaCorreoDTO();
		dto.setPersona(new PersonaDTO());
		dto.setTipoCorreo(new TipoCorreoDTO());

		try {
			ResultadoDTO<PersonaCorreoDTO> resultado = personaCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void buscaPersonaCorreoElectronico() {
		PersonaCorreoDTO dto = personaCorreoService.buscaPersonaCorreoElectronico("profesor@planetmedia.com.mx");
		assertThat(dto, is(not(nullValue())));
	}

	@Test
	public void buscaPersonaCorreoElectronicoNulo() {
		PersonaCorreoDTO dto = personaCorreoService.buscaPersonaCorreoElectronico("zzz@planetmedia.com.mx");
		assertThat(dto, is(nullValue()));
	}

}
