package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.service.admin.FuncionalidadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class FuncionalidadServiceTest {
	
	@Autowired
	private FuncionalidadService funcionalidadService;
	
	
	@Test
	public void obtenerFuncionalidadesPorCriteriosNulo() {
		FuncionalidadDTO criterios = null;
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.buscarPorCriterios(criterios);
		assertThat(listaFuncionalidades, is(empty()));
	}
	
	@Test
	public void obtenerFuncionalidadesPorCriterios() {
		FuncionalidadDTO criterios = new FuncionalidadDTO();
		criterios.setClave("ADMIN");
		criterios.setDescripcion("Admin");
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.buscarPorCriterios(criterios);
		assertThat(listaFuncionalidades, is(not(empty())));
	}

	@Test
	public void obtenerFuncionalidades() {
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.findAll();
		assertThat(listaFuncionalidades, is(not(empty())));
	}
	
	@Test
	public void obtenerFuncionalidadExito() {
		FuncionalidadDTO dto = funcionalidadService.buscarPorId(1L);
		assertThat(dto, allOf(hasProperty("idFuncionalidad", is(1L)), 
				  hasProperty("clave", is("ADMIN")),
				  hasProperty("descripcion", is("Administraci\u00F3n"))));
	}
	
	@Test
	public void obtenerFuncionalidadFallo() {
		FuncionalidadDTO dto = funcionalidadService.buscarPorId(0L);
		assertThat(dto, is(nullValue()));
	}
	
	@Test
	public void guardarFuncionalidadExito() {
		FuncionalidadDTO dto = new FuncionalidadDTO();
		
		dto.setClave("FUN_TEST");
		dto.setDescripcion("Funcionalidad prueba");
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.guardar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void guardarFuncionalidadFallo() {
		FuncionalidadDTO dto = null;
		
		try {
			ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.guardar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFuncionalidadExito() {
		FuncionalidadDTO dto = funcionalidadService.buscarPorId(1L);
		
		dto.setClave("FUN_TEST");
		dto.setDescripcion("Funcionalidad prueba");
		dto.setFechaActualizacion(new Date());;
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.actualizar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFuncionalidadFallo() {
		FuncionalidadDTO dto = null;
		
		try {
			ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.actualizar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFuncionalidadExito() {
		FuncionalidadDTO dto = funcionalidadService.buscarPorId(1L);
		
		dto.setClave("FUN_TEST");
		dto.setDescripcion("Funcionalidad prueba");
		dto.setFechaActualizacion(new Date());;
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.eliminar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFuncionalidadFallo() {
		FuncionalidadDTO dto = null;
		
		try {
			ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.eliminar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void datosRequeridosPersistenciaExito() {
		FuncionalidadDTO dto = new FuncionalidadDTO();

		dto.setClave("FUN_TEST");
		dto.setDescripcion("Funcionalidad prueba");
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);

		ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void datosRequeridosPersistenciaFallo() {
		FuncionalidadDTO dto = new FuncionalidadDTO();
		dto.setFechaRegistro(null);

		ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.FUNCIONALIDADES_DESCRIPCION_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_CLAVE_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_FECHA_REG_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_USUARIO_REQ.getId()
						));
	}
	
	@Test
	public void datosRequeridosActualizacionExito() {
		FuncionalidadDTO dto = new FuncionalidadDTO();

		dto.setClave("FUN_TEST");
		dto.setDescripcion("Funcionalidad prueba");
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void datosRequeridosActualizacionFallo() {
		FuncionalidadDTO dto = new FuncionalidadDTO();

		ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.FUNCIONALIDADES_DESCRIPCION_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_CLAVE_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_FECHA_EDI_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_USUARIO_REQ.getId()
						));
	}
	
	@Test
	public void datosRequeridosEliminacionExito() {
		FuncionalidadDTO dto = new FuncionalidadDTO();
		
		dto.setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void datosRequeridosEliminaFallo() {
		FuncionalidadDTO dto = new FuncionalidadDTO();

		ResultadoDTO<FuncionalidadDTO> resultado = funcionalidadService.sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.FUNCIONALIDADES_ID_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_FECHA_EDI_REQ.getId(),
						MensajesSistemaEnum.FUNCIONALIDADES_USUARIO_REQ.getId()
						));
	}
	
	@Test
	public void obtenerModulos() {
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.obtenerModulos();
		assertThat(listaFuncionalidades, is(not(empty())));
	}
	
	@Test
	public void obtenerComponentes() {
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.obtenerComponentes();
		assertThat(listaFuncionalidades, is(not(empty())));
	}
	
	@Test
	public void obtenerComponentesHijo() {
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.obtenerComponentes(1L);
		assertThat(listaFuncionalidades, is(not(empty())));
	}
	
	@Test
	public void obtenerOperaciones() {
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.obtenerOperaciones(1L);
		assertThat(listaFuncionalidades, is(not(empty())));
	}
	
	@Test
	public void obtenerOperacionesVacio() {
		List<FuncionalidadDTO> listaFuncionalidades = funcionalidadService.obtenerOperaciones(0L);
		assertThat(listaFuncionalidades, is(empty()));
	}

}
