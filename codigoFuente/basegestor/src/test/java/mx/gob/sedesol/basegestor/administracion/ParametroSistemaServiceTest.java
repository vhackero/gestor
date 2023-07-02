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

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class ParametroSistemaServiceTest {
	
private static final Logger logger = Logger.getLogger(ParametroSistemaServiceTest.class);
	
	@Autowired
	private ParametroSistemaService parametroSistemaService;
	
	@Test
	public void buscarTodasActividades() {
		List<ParametroSistemaDTO> lista = parametroSistemaService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		ParametroSistemaDTO dto = parametroSistemaService.buscarPorId("RUTA_PRINCIPAL");
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		ParametroSistemaDTO dto = parametroSistemaService.buscarPorId("zzz");
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		ParametroSistemaDTO dto = new ParametroSistemaDTO(1L);
		dto.setClave("CLAVE");
		dto.setValor("VALOR");
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		ParametroSistemaDTO dto = null;
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		ParametroSistemaDTO dto = new ParametroSistemaDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		ParametroSistemaDTO dto = new ParametroSistemaDTO(1L);
		dto.setClave("CLAVE");
		dto.setValor("VALOR");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		ParametroSistemaDTO dto = null;
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		ParametroSistemaDTO dto = new ParametroSistemaDTO();
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		ParametroSistemaDTO dto = new ParametroSistemaDTO(1L);
		dto.setClave("CLAVE");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		ParametroSistemaDTO dto = null;
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		ParametroSistemaDTO dto = new ParametroSistemaDTO();
		
		try {
			ResultadoDTO<ParametroSistemaDTO> resultado = parametroSistemaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void obtenerParametro() {
		String parametro = parametroSistemaService.obtenerParametro("RUTA_PRINCIPAL");
		assertThat(parametro, is(not(nullValue())));
	}
	
	@Test
	public void obtenerParametroNulo() {
		String parametro = parametroSistemaService.obtenerParametro("zzz");
		assertThat(parametro,  is(nullValue()));
	}
	
	@Test
	public void obtenerParametroConRuta() {
		String parametro = parametroSistemaService.obtenerParametroConRutaCompleta("RUTA_PRINCIPAL");
		assertThat(parametro, is(not(nullValue())));
	}
	
	@Test
	public void obtenerParametroConRutaNulo() {
		String parametro = parametroSistemaService.obtenerParametroConRutaCompleta("zzz");
		assertThat(parametro,  is(nullValue()));
	}
	
	@Test
	public void obtenerParametroRuta() {
		String parametro = parametroSistemaService.obtenerParametroRuta("RUTA_ADJUNTOS");
		assertThat(parametro, is(not(nullValue())));
	}
	
	@Test
	public void obtenerParametroRutaNulo() {
		String parametro = parametroSistemaService.obtenerParametroRuta("zzz");
		assertThat(parametro,  is(nullValue()));
	}

}
