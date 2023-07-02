package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoPlan;
import mx.gob.sedesol.basegestor.model.repositories.admin.CatalogoComunRepo;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.impl.admin.CatalogoComunServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CatalogoComunServiceMockTest {

	private static final Logger logger = Logger.getLogger(CatalogoComunServiceMockTest.class);

	@Mock
	private CatalogoComunRepo<CatTipoPlan, Integer> catalogoComunRepo;

	@InjectMocks
	private CatalogoComunService<CatTipoPlan, Integer> catalogoComunService = new CatalogoComunServiceImpl<>();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirException() {
		Mockito.when(catalogoComunRepo.save((CatTipoPlan) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

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

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarException() {
		Mockito.when(catalogoComunRepo.saveAndFlush((CatTipoPlan) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

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

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarLogicamenteException() {
		Mockito.when(catalogoComunRepo.saveAndFlush((CatTipoPlan) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

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
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.eliminaLogicamente(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarException() {
		CatTipoPlan entidad = new CatTipoPlan();
		entidad.setId(6);
		entidad.setFechaActualizacion(new Date());
		
		Mockito.when(catalogoComunRepo.findOne(Mockito.anyInt())).thenReturn(null);

		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(catalogoComunRepo)
				.delete((CatTipoPlan) Mockito.anyObject());

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
			ResultadoDTO<CatalogoComunDTO> resultado = catalogoComunService.eliminar(dto, CatTipoPlan.class);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
