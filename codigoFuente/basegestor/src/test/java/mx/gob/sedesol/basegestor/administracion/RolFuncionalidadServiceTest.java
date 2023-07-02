package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolFuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.RolFuncionalidadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class RolFuncionalidadServiceTest {
	
private static final Logger logger = Logger.getLogger(RolFuncionalidadServiceTest.class);
	
	@Autowired
	private RolFuncionalidadService rolFuncionalidadService;
	
	@Test
	public void buscarTodos() {
		List<RolFuncionalidadDTO> lista = rolFuncionalidadService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		RolFuncionalidadDTO dto = rolFuncionalidadService.buscarPorId(84L);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		RolFuncionalidadDTO dto = rolFuncionalidadService.buscarPorId(0L);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		RolFuncionalidadDTO dto = null;
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatosSegundoNivel() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setRol(new RolDTO());
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setIdRolFuncionalidad(108L);
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		RolFuncionalidadDTO dto = null;
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatosSegundoNivel() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setRol(new RolDTO());
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloRegistroNoExiste() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setIdRolFuncionalidad(0L);
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloFuncionalidadDiferente() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setIdRolFuncionalidad(108L);
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(0L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloRolDiferente() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setIdRolFuncionalidad(108L);
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(0);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		RolFuncionalidadDTO dto = null;
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatosSegundoNivel() {
		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setRol(new RolDTO());
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void obtenerArbolFuncionalidadesRol() {
		List<RolFuncionalidadDTO> lista = rolFuncionalidadService.obtenerArbolFuncionalidadesRol(1);
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void guardarArbolFuncionalidadesRol() {
		List<RolFuncionalidadDTO> lista = rolFuncionalidadService.obtenerArbolFuncionalidadesRol(1);
		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.guardarArbolFuncionalidadesRol(lista, 1, 1L);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void guardarArbolFuncionalidadesxRol() {
		List<RolFuncionalidadDTO> arbol = rolFuncionalidadService.obtenerArbolFuncionalidadesRol(19);
		imprimirArbol(arbol, "");
		arbol.get(0).setSeleccionado(true);
		arbol.get(1).getFuncionalidades().get(0).setSeleccionado(true);
		arbol.get(2).getFuncionalidades().get(1).getFuncionalidades().get(0).setSeleccionado(true);
		arbol.get(5).getFuncionalidades().get(0).setSeleccionado(true);
		imprimirArbol(arbol, "");
		logger.info("");
		rolFuncionalidadService.procesarListaFuncionalidades(arbol);
		imprimirArbol(arbol, "");
		rolFuncionalidadService.guardarArbolFuncionalidadesRol(arbol, 1, 1L);
		assertThat(arbol, is(not(empty())));
		
	}
	
	private void imprimirArbol(List<RolFuncionalidadDTO> lista, String cadena) {
		for (RolFuncionalidadDTO dto : lista) {
			logger.info(cadena + " " + dto.getFuncionalidadDTO().getClave() + " " + dto.isSeleccionado());
			imprimirArbol(dto.getFuncionalidades(), cadena + "####");
		}
	}
	
	@Test
	public void obtenerMapaFuncionalidadesPorRol() {
		Map<String, String> mapaFuncionalidadesRol = rolFuncionalidadService.obtenerMapaFuncionalidadesPorRol(1);
		assertThat(mapaFuncionalidadesRol.isEmpty(), is(false));
	}

}
