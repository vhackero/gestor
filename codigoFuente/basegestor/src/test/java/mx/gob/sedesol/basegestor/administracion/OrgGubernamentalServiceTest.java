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

import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class OrgGubernamentalServiceTest {
	
private static final Logger logger = Logger.getLogger(OrgGubernamentalServiceTest.class);
	
	@Autowired
	private OrgGubernamentalService orgGubernamentalService;
	
	@Test
	public void buscarTodos() {
		List<OrgGubernamentalDTO> lista = orgGubernamentalService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		OrgGubernamentalDTO dto = orgGubernamentalService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		OrgGubernamentalDTO dto = orgGubernamentalService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);
		dto.setDescripcion("Descripcion");
		dto.setIdTipoOrganismo(1);
		dto.setNombre("Nombre");
		dto.setOrden(0);
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		OrgGubernamentalDTO dto = null;
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setId(1);
		dto.setFechaActualizacion(new Date());
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);
		dto.setDescripcion("Descripcion");
		dto.setIdTipoOrganismo(1);
		dto.setNombre("Nombre");
		dto.setOrden(0);
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		OrgGubernamentalDTO dto = null;
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setId(1);
		dto.setFechaActualizacion(new Date());
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);
		dto.setDescripcion("Descripcion");
		dto.setIdTipoOrganismo(1);
		dto.setNombre("Nombre");
		dto.setOrden(0);
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		OrgGubernamentalDTO dto = null;
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	
	
	@Test
	public void obtenerOrgGubernamentalPorId() {
		OrgGubernamentalDTO dto = orgGubernamentalService.obtenerOrgGubernamentalPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void obtenerOrgGubernamentalNuloPorId() {
		OrgGubernamentalDTO dto = orgGubernamentalService.obtenerOrgGubernamentalPorId(0);
		assertThat(dto, is(nullValue()));
	}
	
	@Test
	public void obtenerOrgGubsPadres() {
		List<OrgGubernamentalDTO> lista = orgGubernamentalService.obtenerOrgGubsPadres();
		assertThat(lista, is(not(empty())));
	}

}
