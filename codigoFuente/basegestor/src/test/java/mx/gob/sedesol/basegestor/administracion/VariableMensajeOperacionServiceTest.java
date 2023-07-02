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
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.VariableMensajeOperacionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class VariableMensajeOperacionServiceTest {
	
private static final Logger logger = Logger.getLogger(VariableMensajeOperacionServiceTest.class);
	
	@Autowired
	private VariableMensajeOperacionService variableMensajeOperacionService;
	
	@Test
	public void buscarTodos() {
		List<VariableMensajeOperacionDTO> lista = variableMensajeOperacionService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		VariableMensajeOperacionDTO dto = variableMensajeOperacionService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		VariableMensajeOperacionDTO dto = variableMensajeOperacionService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		dto.setClave("CLAVE");
		dto.setDescripcion("Descripcion");
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		VariableMensajeOperacionDTO dto = null;
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatosSegundoNivel() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		dto.setFechaRegistro(null);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		dto.setIdVariableMensajeOperacion(1);
		dto.setClave("CLAVE");
		dto.setDescripcion("Descripcion");
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		VariableMensajeOperacionDTO dto = null;
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatosSegundoNivel() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		dto.setFechaRegistro(null);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		dto.setIdVariableMensajeOperacion(1);
		dto.setClave("CLAVE");
		dto.setDescripcion("Descripcion");
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		VariableMensajeOperacionDTO dto = null;
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatosSegundoNivel() {
		VariableMensajeOperacionDTO dto = new VariableMensajeOperacionDTO();
		dto.setFechaRegistro(null);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		
		try {
			ResultadoDTO<VariableMensajeOperacionDTO> resultado = variableMensajeOperacionService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void obtenerVariablesPorOperacion() {
		List<VariableMensajeOperacionDTO> lista = variableMensajeOperacionService.obtenerVariablesPorOperacion(33);
		assertThat(lista, is(not(empty())));
	}

}
