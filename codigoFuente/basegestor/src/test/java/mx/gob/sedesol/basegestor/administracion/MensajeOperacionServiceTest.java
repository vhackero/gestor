package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoNotificacionEnum;
import mx.gob.sedesol.basegestor.service.admin.MensajeOperacionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class MensajeOperacionServiceTest {
/*	
	@Autowired
	private MensajeOperacionService mensajeOperacionService;
	
	@Test
	public void buscarTodos() {
		List<MensajeOperacionDTO> lista = mensajeOperacionService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void obtenerFuncionalidadExito() {
		MensajeOperacionDTO dto = mensajeOperacionService.buscarPorId(1L);
		assertThat(dto, allOf(
				hasProperty("titulo", is("Mensaje agregar rol")), 
				hasProperty("mensaje", is("Se ha agregado el rol {rol}<br>"))
				  ));
	}
	
	@Test
	public void obtenerFuncionalidadFallo() {
		MensajeOperacionDTO dto = mensajeOperacionService.buscarPorId(0L);
		assertThat(dto, is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setTitulo("Titulo");
		dto.setMensaje("Mensaje");
		dto.setTipo(TipoNotificacionEnum.ALERTA.getValor());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		MensajeOperacionDTO dto = null;
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatosSegundoNivel() {
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setFechaRegistro(null);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setIdMensajeOperacion(1L);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setTitulo("Titulo");
		dto.setMensaje("Mensaje");
		dto.setTipo(TipoNotificacionEnum.ALERTA.getValor());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		MensajeOperacionDTO dto = null;
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatosSegundoNivel() {
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setFechaRegistro(null);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setIdMensajeOperacion(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		MensajeOperacionDTO dto = null;
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatosSegundoNivel() {
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setFechaRegistro(null);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarPorOperacion() {
		List<MensajeOperacionDTO> lista = mensajeOperacionService.obtenerMensajesPorOperacion(33L);
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void establecerPlantillaPredeterminada() {
		MensajeOperacionDTO dto = new MensajeOperacionDTO();
		dto.setIdMensajeOperacion(1L);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(33L);
		dto.setTitulo("Titulo");
		dto.setMensaje("Mensaje");
		dto.setTipo(TipoNotificacionEnum.ALERTA.getValor());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		ResultadoDTO<MensajeOperacionDTO> resultado = mensajeOperacionService.establecerPlantillaPredeterminada(dto);
		
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
*/
}
