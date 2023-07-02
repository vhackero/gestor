package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoPlan;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CatalogoComunServiceTest {

	private static final Logger logger = Logger.getLogger(CatalogoComunServiceTest.class);

	@Resource(name="catalogoComunService")
	private CatalogoComunService<CatTipoPlan, Integer> catalogoComunService;
	
	@Test
	public void buscarTodos() {
		List<CatalogoComunDTO> lista = catalogoComunService.findAll(CatTipoPlan.class);
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		CatalogoComunDTO dto = catalogoComunService.buscarPorId(1, CatTipoPlan.class);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		CatalogoComunDTO dto = catalogoComunService.buscarPorId(0, CatTipoPlan.class);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void buscarPorNombre() {
		CatalogoComunDTO dto = catalogoComunService.buscarRegistroPorNombre("Normalizado", CatTipoPlan.class);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorNombreNull() {
		CatalogoComunDTO dto = catalogoComunService.buscarRegistroPorNombre("___", CatTipoPlan.class);
		assertThat(dto,  is(nullValue()));
	}
	
	@Test
	public void persistirExito() {
		CatalogoComunDTO dto = new CatalogoComunDTO();
		dto.setActivo(1);
		dto.setDescripcion("Plan Licenciatura");
		dto.setFechaRegistro(new Date());
		dto.setNombre("Licenciatura");
		dto.setNombreUsuarioMod("system");
		dto.setOrden(1);
		dto.setUsuarioModifico(1l);
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.guardar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		CatalogoComunDTO dto = null;
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.guardar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		CatalogoComunDTO dto = new CatalogoComunDTO();
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.guardar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		CatalogoComunDTO dto = new CatalogoComunDTO();
		dto.setId(1);
		dto.setActivo(1);
		dto.setDescripcion("Plan Licenciatura");
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setNombre("Licenciatura");
		dto.setNombreUsuarioMod("system");
		dto.setOrden(1);
		dto.setUsuarioModifico(1l);
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.actualizar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		CatalogoComunDTO dto = null;
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.actualizar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		CatalogoComunDTO dto = new CatalogoComunDTO();
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.actualizar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarLogicamenteExito() {
		CatalogoComunDTO dto = catalogoComunService.buscarPorId(1, CatTipoPlan.class);
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.eliminaLogicamente(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		CatalogoComunDTO dto = catalogoComunService.buscarPorId(6, CatTipoPlan.class);
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.eliminar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		CatalogoComunDTO dto = null;
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.eliminar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		CatalogoComunDTO dto = new CatalogoComunDTO();
		
		try {
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.eliminar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
