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
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.TiposCorreoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class TiposCorreoServiceTest {
	
private static final Logger logger = Logger.getLogger(TiposCorreoServiceTest.class);
	
	@Autowired
	private TiposCorreoService tiposCorreoService;
	
	@Test
	public void buscarTodos() {
		List<TipoCorreoDTO> lista = tiposCorreoService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		TipoCorreoDTO dto = tiposCorreoService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		TipoCorreoDTO dto = tiposCorreoService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		TipoCorreoDTO dto = new TipoCorreoDTO();
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		TipoCorreoDTO dto = null;
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		TipoCorreoDTO dto = new TipoCorreoDTO();
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		TipoCorreoDTO dto = new TipoCorreoDTO();
		dto.setIdTipoCorreo(1);
		dto.setDescripcion("Institucional");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		TipoCorreoDTO dto = null;
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		TipoCorreoDTO dto = new TipoCorreoDTO();
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		TipoCorreoDTO dto = new TipoCorreoDTO();
		dto.setIdTipoCorreo(1);
		dto.setDescripcion("Institucional");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		TipoCorreoDTO dto = null;
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		TipoCorreoDTO dto = new TipoCorreoDTO();
		
		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
