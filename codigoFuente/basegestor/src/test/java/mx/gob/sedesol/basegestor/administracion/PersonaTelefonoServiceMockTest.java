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

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaTelefono;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaTelefonoRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.PersonaTelefonoService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaTelefonoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class PersonaTelefonoServiceMockTest {
/*
	private static final Logger logger = Logger.getLogger(PersonaTelefonoServiceMockTest.class);

	@Mock
	private PersonaTelefonoRepo personaTelefonoRepo;

	@Mock
	private AdministradorBitacora administradorBitacora;

	@InjectMocks
	private PersonaTelefonoService personaTelefonoService = new PersonaTelefonoServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirException() {

		Mockito.when(personaTelefonoRepo.save((RelPersonaTelefono) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(1L);
		dto.setTelefono("1315345615");

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarException() {

		RelPersonaTelefono entidad = new RelPersonaTelefono();
		entidad.setPersona(new TblPersona());
		entidad.getPersona().setIdPersona(187L);

		Mockito.when(personaTelefonoRepo.findOne(Mockito.anyInt())).thenReturn(entidad);

		Mockito.when(personaTelefonoRepo.saveAndFlush((RelPersonaTelefono) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setIdPersonaTelefono(1);
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
	public void eliminarException() {

		Mockito.when(personaTelefonoRepo.saveAndFlush((RelPersonaTelefono) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		PersonaTelefonoDTO dto = new PersonaTelefonoDTO(1L, 1);

		dto.setIdPersonaTelefono(1);
		dto.setPersona(new PersonaDTO());
		dto.getPersona().setIdPersona(187L);
		dto.setTelefono("1315345615");

		dto.setFechaActualizacion(new Date());

		try {
			ResultadoDTO<PersonaTelefonoDTO> resultado = personaTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
*/
}
