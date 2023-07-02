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

import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class PlantillaServiceTest {
	
	private static final Logger logger = Logger.getLogger(PlantillaServiceTest.class);
	
	@Autowired
	private PlantillaService plantillaService;

	@Test
	public void buscarTodos() {
		List<PlantillaDTO> lista = plantillaService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		PlantillaDTO dto = plantillaService.buscarPorId(1L);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		PlantillaDTO dto = plantillaService.buscarPorId(0L);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		PlantillaDTO dto = new PlantillaDTO();
		dto.setNombre("Plantilla");
		dto.setUsuarioCreo(1L);
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		PlantillaDTO dto = null;
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		PlantillaDTO dto = new PlantillaDTO();
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		PlantillaDTO dto = new PlantillaDTO();
		dto.setNombre("Plantilla");
		dto.setUsuarioModifico(1L);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		PlantillaDTO dto = null;
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		PlantillaDTO dto = new PlantillaDTO();
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		PlantillaDTO dto = new PlantillaDTO();
		dto.setIdPlantilla(2L);
		dto.setNombre("Plantilla");
		dto.setUsuarioModifico(1L);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		PlantillaDTO dto = null;
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		PlantillaDTO dto = new PlantillaDTO();
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
