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
import mx.gob.sedesol.basegestor.commons.dto.admin.TemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoTemaEnum;
import mx.gob.sedesol.basegestor.service.admin.TemaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class TemaServiceTest {
	
	private static final Logger logger = Logger.getLogger(TemaServiceTest.class);
	
	@Autowired
	private TemaService temaService;
	
	@Test
	public void buscarTodos() {
		List<TemaDTO> lista = temaService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		TemaDTO dto = temaService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		TemaDTO dto = temaService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		TemaDTO dto = new TemaDTO(TipoTemaEnum.PRIVADO.getValor(), 1L);
		dto.setNombre("Tema");
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		TemaDTO dto = null;
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		TemaDTO dto = new TemaDTO();
		dto.setFechaRegistro(null);
		dto.setActivo(null);
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		TemaDTO dto = new TemaDTO(TipoTemaEnum.PRIVADO.getValor(), 1L);
		dto.setIdTema(1);
		dto.setNombre("Tema");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		TemaDTO dto = null;
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		TemaDTO dto = new TemaDTO();
		dto.setActivo(null);
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		TemaDTO dto = new TemaDTO(TipoTemaEnum.PRIVADO.getValor(), 1L);
		dto.setIdTema(1);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		TemaDTO dto = null;
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		TemaDTO dto = new TemaDTO();
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarPorTipo() {
		List<TemaDTO> lista = temaService.buscarPorTipo(TipoTemaEnum.PRIVADO.getValor());
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void activarTema() {
		TemaDTO dto = new TemaDTO(TipoTemaEnum.PRIVADO.getValor(), 1L);
		dto.setIdTema(2);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<TemaDTO> resultado = temaService.activarTema(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void obtenerTemaActivoPublico() {
		String ruta = temaService.obtenerTemaActivo(TipoTemaEnum.PUBLICO.getValor());
		assertThat(ruta, is(not(nullValue())));
	}
	
	@Test
	public void obtenerTemaActivoPrivado() {
		String ruta = temaService.obtenerTemaActivo(TipoTemaEnum.PRIVADO.getValor());
		assertThat(ruta, is(not(nullValue())));
	}
	
	@Test
	public void obtenerTemaActivoNulo() {
		String ruta = temaService.obtenerTemaActivo(0);
		assertThat(ruta, is(not(nullValue())));
	}
	
	@Test
	public void validarTemaNull() {
		TemaDTO dto = new TemaDTO(TipoTemaEnum.PRIVADO.getValor(), 1L);
		dto.setIdTema(1);
		dto.setNombre("Tema");
		dto.setFechaActualizacion(new Date());
		
		try {
			boolean resultado = temaService.validarTema(dto);

			assertThat(resultado, is(false));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
