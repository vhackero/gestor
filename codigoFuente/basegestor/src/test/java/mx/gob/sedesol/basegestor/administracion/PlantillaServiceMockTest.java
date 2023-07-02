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

import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPlantilla;
import mx.gob.sedesol.basegestor.model.repositories.admin.PlantillaRepo;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;
import mx.gob.sedesol.basegestor.service.impl.admin.PlantillaServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class PlantillaServiceMockTest {
	
	private static final Logger logger = Logger.getLogger(PlantillaServiceMockTest.class);

	@Mock
	private PlantillaRepo plantillaRepo;
	
	@InjectMocks
	private PlantillaService plantillaService = new PlantillaServiceImpl();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void persistirException() {
		
		Mockito.when(plantillaRepo.save((TblPlantilla) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		PlantillaDTO dto = new PlantillaDTO();
		dto.setNombre("Plantilla");
		dto.setUsuarioCreo(1L);
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarException() {
		
		Mockito.when(plantillaRepo.saveAndFlush((TblPlantilla) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		PlantillaDTO dto = new PlantillaDTO();
		dto.setNombre("Plantilla");
		dto.setUsuarioModifico(1L);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarException() {
		
		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(plantillaRepo)
		.delete((TblPlantilla) Mockito.anyObject());
		
		PlantillaDTO dto = new PlantillaDTO();
		dto.setIdPlantilla(2L);
		dto.setNombre("Plantilla");
		dto.setUsuarioModifico(1L);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<PlantillaDTO> resultado = plantillaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
