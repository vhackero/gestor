package mx.gob.sedesol.basegestor.gestionescolar;

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

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadResponsableDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.ProgramaSocialService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class ProgramaSocialServiceTest {

	private static final Logger logger = Logger.getLogger(ProgramaSocialServiceTest.class);

	@Autowired
	private ProgramaSocialService programaSocialService;
	
	@Test
	public void buscarTodos() {
		List<ProgramaSocialDTO> lista = programaSocialService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void obtenerProgramasPorUnidad() {
		List<ProgramaSocialDTO> lista = programaSocialService.obtenerProgramasPorUnidad(1);
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		ProgramaSocialDTO dto = programaSocialService.buscarPorId(2);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		ProgramaSocialDTO dto = programaSocialService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		ProgramaSocialDTO dto = new ProgramaSocialDTO(1L);
		dto.setClave("clave");
		dto.setNombre("nombre");
		dto.setUnidadResponsable(new UnidadResponsableDTO());
		dto.getUnidadResponsable().setIdUnidadResponsable(1);
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		ProgramaSocialDTO dto = null;
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizacionExito() {
		ProgramaSocialDTO dto = new ProgramaSocialDTO(1L);
		dto.setIdProgramaSocial(1);
		dto.setClave("clave");
		dto.setNombre("nombre");
		dto.setUnidadResponsable(new UnidadResponsableDTO());
		dto.getUnidadResponsable().setIdUnidadResponsable(1);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizacionFalloNulo() {
		ProgramaSocialDTO dto = null;
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizacionFalloDatos() {
		ProgramaSocialDTO dto = new ProgramaSocialDTO();
		dto.setFechaRegistro(null);
		dto.setActivo(null);
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizacionFalloDatosSegundoNivel() {
		ProgramaSocialDTO dto = new ProgramaSocialDTO();
		dto.setUnidadResponsable(new UnidadResponsableDTO());
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		ProgramaSocialDTO dto = new ProgramaSocialDTO(1L);
		dto.setIdProgramaSocial(1);
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		ProgramaSocialDTO dto = new ProgramaSocialDTO();
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
