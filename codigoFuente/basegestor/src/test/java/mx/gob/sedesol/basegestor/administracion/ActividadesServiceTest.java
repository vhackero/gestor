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

import mx.gob.sedesol.basegestor.commons.dto.admin.ActividadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.ActividadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class ActividadesServiceTest {
	
	private static final Logger logger = Logger.getLogger(ActividadesServiceTest.class);
	
	@Autowired
	private ActividadService actividadService;
	
	@Test
	public void buscarTodos() {
		List<ActividadDTO> lista = actividadService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		ActividadDTO dto = actividadService.buscarPorId(actividadService.findAll().get(0).getId());
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		ActividadDTO dto = actividadService.buscarPorId("zzz");
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		ActividadDTO dto = new ActividadDTO(1L);
		dto.setActividad("Actividad de prueba");
		dto.setFechaInicio(new Date());
		dto.setFechaFin(new Date());
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		ActividadDTO dto = null;
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		ActividadDTO dto = new ActividadDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		ActividadDTO dto = new ActividadDTO(1L);
		dto.setId("ZZZ");
		dto.setActividad("Actividad de prueba");
		dto.setFechaInicio(new Date());
		dto.setFechaFin(new Date());
		dto.setFechaTermino(new Date());
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		ActividadDTO dto = null;
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		ActividadDTO dto = new ActividadDTO();
		dto.setFechaRegistro(null);
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		ActividadDTO dto = new ActividadDTO(1L);
		dto.setId("ZZZ");
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		ActividadDTO dto = null;
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		ActividadDTO dto = new ActividadDTO();
		
		try {
			ResultadoDTO<ActividadDTO> resultado = actividadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void buscarPorUsuario() {
		List<ActividadDTO> listaActividades = actividadService.buscarPorUsuario(1L);
		assertThat(listaActividades, is(not(empty())));
	}

}
