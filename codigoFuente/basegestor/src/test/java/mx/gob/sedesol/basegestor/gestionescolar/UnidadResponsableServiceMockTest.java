package mx.gob.sedesol.basegestor.gestionescolar;

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

import mx.gob.sedesol.basegestor.administracion.RoleServiceMockTest;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadResponsableDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatUnidadResponsable;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.UnidadResponsableRepo;
import mx.gob.sedesol.basegestor.service.gestionescolar.UnidadResponsableService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.UnidadResponsableServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class UnidadResponsableServiceMockTest {
	
	private static final Logger logger = Logger.getLogger(RoleServiceMockTest.class);

	@Mock
	private UnidadResponsableRepo unidadResponsableRepo;
	
	@InjectMocks
	private UnidadResponsableService unidadResponsableService = new UnidadResponsableServiceImpl();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void persistirException() {
		Mockito.when(unidadResponsableRepo.save((CatUnidadResponsable) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		UnidadResponsableDTO dto = new UnidadResponsableDTO(1L);
		dto.setClave("clave");
		dto.setNombre("nombre");
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarException() {
		Mockito.when(unidadResponsableRepo.saveAndFlush((CatUnidadResponsable) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		UnidadResponsableDTO dto = new UnidadResponsableDTO(1L);
		dto.setIdUnidadResponsable(2);
		dto.setClave("clave");
		dto.setNombre("nombre");
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarException() {
		
		CatUnidadResponsable entidad = new CatUnidadResponsable();
		
		Mockito.when(unidadResponsableRepo.findOne(Mockito.anyInt())).thenReturn(entidad);

		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(unidadResponsableRepo)
				.delete((CatUnidadResponsable) Mockito.anyObject());
		
		UnidadResponsableDTO dto = new UnidadResponsableDTO(1L);
		dto.setIdUnidadResponsable(2);
		
		try {
			ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
