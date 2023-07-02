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

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class RoleServiceTest {
	
private static final Logger logger = Logger.getLogger(RoleServiceTest.class);
	
	@Autowired
	private RoleService roleService;
	
	@Test
	public void buscarTodos() {
		List<RolDTO> lista = roleService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		RolDTO dto = roleService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		RolDTO dto = roleService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void buscarPorClave() {
		RolDTO dto = roleService.buscarPorClave("ROLE_ADMIN");
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorClaveNull() {
		RolDTO dto = roleService.buscarPorClave("ROL");
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		RolDTO dto = new RolDTO(1L);
		dto.setClave("ROL");
		dto.setNombre("Rol");
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		RolDTO dto = null;
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		RolDTO dto = new RolDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		RolDTO dto = new RolDTO(1L);
		dto.setClave("ROL");
		dto.setNombre("Rol");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		RolDTO dto = null;
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		RolDTO dto = new RolDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		RolDTO dto = new RolDTO(1L);
		dto.setClave("ROL");
		dto.setNombre("Rol");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		RolDTO dto = null;
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		RolDTO dto = new RolDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<RolDTO> resultado = roleService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
