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

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TextoSistemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.TextoSistemaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class TextoSistemaServiceTest {
	
private static final Logger logger = Logger.getLogger(TextoSistemaServiceTest.class);
	
	@Autowired
	private TextoSistemaService textoSistemaService;
	
	@Test
	public void buscarTodos() {
		List<TextoSistemaDTO> lista = textoSistemaService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorFuncionalidad() {
		List<TextoSistemaDTO> lista = textoSistemaService.obtenerTextosPorFuncionalidad(49L);
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		TextoSistemaDTO dto = textoSistemaService.buscarPorId("gw.actividades.boton.agregar");
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		TextoSistemaDTO dto = textoSistemaService.buscarPorId("zzz");
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		TextoSistemaDTO dto = new TextoSistemaDTO(1L);
		dto.setClave("gw");
		dto.setValor("Gestor web");
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		TextoSistemaDTO dto = null;
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		TextoSistemaDTO dto = new TextoSistemaDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		TextoSistemaDTO dto = new TextoSistemaDTO(1L);
		dto.setClave("gw");
		dto.setValor("Gestor web");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		TextoSistemaDTO dto = null;
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		TextoSistemaDTO dto = new TextoSistemaDTO();
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		TextoSistemaDTO dto = new TextoSistemaDTO(1L);
		dto.setClave("gw");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		TextoSistemaDTO dto = null;
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		TextoSistemaDTO dto = new TextoSistemaDTO();
		
		try {
			ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarPorCriteriosExito() {
		TextoSistemaDTO dto = new TextoSistemaDTO(1L);
		dto.setFuncionalidad(new FuncionalidadDTO());
		dto.getFuncionalidad().setIdFuncionalidad(124L);
		dto.setValor("Crear");
		
		try {
			List<TextoSistemaDTO> lista = textoSistemaService.buscarPorCriterios(dto);
			assertThat(lista, is(not(empty())));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarPorCriteriosTodosExito() {
		TextoSistemaDTO dto = new TextoSistemaDTO(1L);
		
		try {
			List<TextoSistemaDTO> lista = textoSistemaService.buscarPorCriterios(dto);
			assertThat(lista, is(not(empty())));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarPorCriteriosNulo() {
		TextoSistemaDTO dto = null;
		
		try {
			List<TextoSistemaDTO> lista = textoSistemaService.buscarPorCriterios(dto);
			assertThat(lista, is(empty()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
