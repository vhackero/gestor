package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaRol;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaRolesRepo;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaRolesServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class PersonaRolesServiceMockTest {
	
	private static final Logger logger = Logger.getLogger(PersonaRolesServiceMockTest.class);

	@Mock
	private PersonaRolesRepo personaRolesRepo;
	
	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private PersonaRolesService personaRolesService = new PersonaRolesServiceImpl();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void persistirException() {
		
		Mockito.when(personaRolesRepo.save((RelPersonaRol) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		RolDTO rol = new RolDTO();
		rol.setIdRol(2);
		PersonaDTO persona = new PersonaDTO();
		persona.setIdPersona(2L);
		
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setRol(rol);
		dto.setPersona(persona);
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarException() {
		
		Mockito.when(personaRolesRepo.saveAndFlush((RelPersonaRol) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		RolDTO rol = new RolDTO();
		rol.setIdRol(2);
		PersonaDTO persona = new PersonaDTO();
		persona.setIdPersona(2L);
		
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setRol(rol);
		dto.setPersona(persona);
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarException() {
		
		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(personaRolesRepo)
		.delete((RelPersonaRol) Mockito.anyObject());
		
		RolDTO rol = new RolDTO();
		rol.setIdRol(2);
		PersonaDTO persona = new PersonaDTO();
		
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setRol(rol);
		dto.setPersona(persona);
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void obtieneRelPersonaRolesPorUsuario() {
		
		List<RelPersonaRol> listaPersonaRoles = new ArrayList<>();
		listaPersonaRoles.add(new RelPersonaRol());
		
		Mockito.when(personaRolesRepo.obtieneRelPersonaRoles(Mockito.anyString()))
		.thenReturn(listaPersonaRoles);
		
		Mockito.when(modelMapper.map((RelPersonaRol) Mockito.anyObject(), Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		try {
			List<PersonaRolDTO> lista = personaRolesService.obtieneRelPersonaRolesPorUsuario("system");

			assertThat(lista, is(empty()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
		
	}

}
