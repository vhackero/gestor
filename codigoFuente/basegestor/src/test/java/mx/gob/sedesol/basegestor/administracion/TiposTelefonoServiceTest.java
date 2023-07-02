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
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.TiposTelefonoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class TiposTelefonoServiceTest {
	
private static final Logger logger = Logger.getLogger(TiposTelefonoServiceTest.class);
	
	@Autowired
	private TiposTelefonoService tiposTelefonoService;
	
	@Test
	public void buscarTodos() {
		List<TipoTelefonoDTO> lista = tiposTelefonoService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		TipoTelefonoDTO dto = tiposTelefonoService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		TipoTelefonoDTO dto = tiposTelefonoService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		TipoTelefonoDTO dto = null;
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setIdTipoTelefono(1);
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		TipoTelefonoDTO dto = null;
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setIdTipoTelefono(1);
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		TipoTelefonoDTO dto = null;
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
