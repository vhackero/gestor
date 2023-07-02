package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class PersonaRolesServiceTest {
	
	private static final Logger logger = Logger.getLogger(PersonaRolesServiceTest.class);
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private RoleService rolService;
	
	@Autowired
	private PersonaRolesService personaRolesService;
	
	@Test
	public void buscarTodos() {
		List<PersonaRolDTO> lista = personaRolesService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		PersonaRolDTO dto = personaRolesService.buscarPorId(44L);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		PersonaRolDTO dto = personaRolesService.buscarPorId(0L);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		
		RolDTO rol = rolService.buscarPorId(2);
		PersonaDTO persona = personaService.buscarPorId(2L);
		
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setRol(rol);
		dto.setPersona(persona);
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		PersonaRolDTO dto = null;
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatosSegundoNivel() {
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setFechaRegistro(null);
		dto.setPersona(new PersonaDTO());
		dto.setRol(new RolDTO());
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		
		RolDTO rol = rolService.buscarPorId(2);
		PersonaDTO persona = personaService.buscarPorId(2L);
		
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setRol(rol);
		dto.setPersona(persona);
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		PersonaRolDTO dto = null;
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatosSegundoNivel() {
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setFechaRegistro(null);
		dto.setPersona(new PersonaDTO());
		dto.setRol(new RolDTO());
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		
		RolDTO rol = rolService.buscarPorId(2);
		PersonaDTO persona = personaService.buscarPorId(2L);
		
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setRol(rol);
		dto.setPersona(persona);
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		PersonaRolDTO dto = null;
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatosSegundoNivel() {
		PersonaRolDTO dto = new PersonaRolDTO();
		dto.setFechaRegistro(null);
		dto.setPersona(new PersonaDTO());
		dto.setRol(new RolDTO());
		
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
		List<PersonaRolDTO> lista = personaRolesService.obtieneRelPersonaRolesPorUsuario("carlosl");
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void obtieneRelPersonaRolesPorUsuarioVacio() {
		List<PersonaRolDTO> lista = personaRolesService.obtieneRelPersonaRolesPorUsuario("");
		assertThat(lista, is(empty()));
	}
	
	@Test
	public void obtenerFuncionalidadesUsuario() {
		List<FuncionalidadDTO> lista = personaRolesService.obtenerFuncionalidadesUsuario("system");
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void obtenerFuncionalidadesRol() {
		Map<String, String> lista = personaRolesService.obtenerFuncionalidadesRol(1);
		assertThat(lista.values(), is(not(empty())));
	}
	
	@Test
	public void almacenarRolesUsuario() {
		PersonaDTO persona = new PersonaDTO();
		persona.setIdPersona(1L);
		persona.setUsuario("system");
		
		RolDTO rolDTO = new RolDTO();
		rolDTO.setIdRol(2);
		rolDTO.setNombre("rol");
		List<RolDTO> lista = new ArrayList<>();
		lista.add(rolDTO);
		
		ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.almacenarRolesUsuario(persona, lista, 1L);
		
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void almacenarRolesUsuarioAgregar() {
		PersonaDTO persona = new PersonaDTO();
		persona.setIdPersona(11L);
		persona.setUsuario("profesor");
		
		
		List<RolDTO> lista = new ArrayList<>();
		RolDTO rolDTO = new RolDTO();
		rolDTO.setIdRol(1);
		rolDTO.setNombre("rol");
		lista.add(rolDTO);
		
		rolDTO = new RolDTO();
		rolDTO.setIdRol(2);
		rolDTO.setNombre("rol");
		lista.add(rolDTO);
		
		rolDTO = new RolDTO();
		rolDTO.setIdRol(4);
		rolDTO.setNombre("rol");
		lista.add(rolDTO);
		
		rolDTO = new RolDTO();
		rolDTO.setIdRol(5);
		rolDTO.setNombre("rol");
		lista.add(rolDTO);
		
		ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.almacenarRolesUsuario(persona, lista, 1L);
		
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void almacenarRolesUsuarioEliminar() {
		PersonaDTO persona = new PersonaDTO();
		persona.setIdPersona(34L);
		persona.setUsuario("usuariorevision");
		
		
		List<RolDTO> lista = new ArrayList<>();
		RolDTO rolDTO = new RolDTO();
		rolDTO.setIdRol(1);
		rolDTO.setNombre("rol");
		lista.add(rolDTO);
		
		rolDTO = new RolDTO();
		rolDTO.setIdRol(4);
		rolDTO.setNombre("rol");
		lista.add(rolDTO);
		
		rolDTO = new RolDTO();
		rolDTO.setIdRol(5);
		rolDTO.setNombre("rol");
		lista.add(rolDTO);
		
		ResultadoDTO<PersonaRolDTO> resultado = personaRolesService.almacenarRolesUsuario(persona, lista, 1L);
		
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}

}
