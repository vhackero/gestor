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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaCorreo;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaCorreoRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaCorreoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class PersonaCorreoServiceMockTest {
/*
	private static final Logger logger = Logger.getLogger(PersonaCorreoServiceMockTest.class);

	@Mock
	private PersonaCorreoRepo personaCorreoRepo;

	@Mock
	private AdministradorBitacora administradorBitacora;

	@InjectMocks
	private PersonaCorreoService personaCorreoService = new PersonaCorreoServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirException() {

		Mockito.when(personaCorreoRepo.save((RelPersonaCorreo) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

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

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarException() {
		
		RelPersonaCorreo entidad = new RelPersonaCorreo();
		entidad.setPersona(new TblPersona());
		entidad.getPersona().setIdPersona(11L);
		
		Mockito.when(personaCorreoRepo.findOne(Mockito.anyInt())).thenReturn(entidad);

		Mockito.when(personaCorreoRepo.saveAndFlush((RelPersonaCorreo) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

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

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarException() {

		Mockito.when(personaCorreoRepo.saveAndFlush((RelPersonaCorreo) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

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

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
*/
}
