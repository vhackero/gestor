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
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadResponsableDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.UnidadResponsableService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class UnidadResponsableServiceTest {
	
	private static final Logger logger = Logger.getLogger(UnidadResponsableServiceTest.class);
	
	@Autowired
	private UnidadResponsableService unidadResponsableService;
	
	@Test
	public void buscarTodos() {
		List<UnidadResponsableDTO> lista = unidadResponsableService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		UnidadResponsableDTO dto = unidadResponsableService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		UnidadResponsableDTO dto = unidadResponsableService.buscarPorId(0);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		UnidadResponsableDTO dto = new UnidadResponsableDTO(1L);
		dto.setClave("clave");
		dto.setNombre("nombre");
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		UnidadResponsableDTO dto = null;
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizacionExito() {
		UnidadResponsableDTO dto = new UnidadResponsableDTO(1L);
		dto.setIdUnidadResponsable(1);
		dto.setClave("clave");
		dto.setNombre("nombre");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizacionFalloNulo() {
		UnidadResponsableDTO dto = null;
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizacionFalloDatos() {
		UnidadResponsableDTO dto = new UnidadResponsableDTO();
		dto.setFechaRegistro(null);
		dto.setActivo(null);
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		UnidadResponsableDTO dto = new UnidadResponsableDTO(1L);
		dto.setIdUnidadResponsable(2);
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		UnidadResponsableDTO dto = new UnidadResponsableDTO();
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
