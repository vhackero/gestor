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
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadResponsableDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatProgramaSocial;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.ProgramaSocialRepo;
import mx.gob.sedesol.basegestor.service.gestionescolar.ProgramaSocialService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.ProgramaSocialServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class ProgramaSocialServiceMockTest {
	
	private static final Logger logger = Logger.getLogger(RoleServiceMockTest.class);

	@Mock
	private ProgramaSocialRepo programaSocialRepo;
	
	@InjectMocks
	private ProgramaSocialService programaSocialService = new ProgramaSocialServiceImpl();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void persistirException() {
		Mockito.when(programaSocialRepo.save((CatProgramaSocial) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		ProgramaSocialDTO dto = new ProgramaSocialDTO(1L);
		dto.setClave("clave");
		dto.setNombre("nombre");
		dto.setUnidadResponsable(new UnidadResponsableDTO());
		dto.getUnidadResponsable().setIdUnidadResponsable(1);
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarException() {
		Mockito.when(programaSocialRepo.saveAndFlush((CatProgramaSocial) Mockito.anyObject()))
		.thenThrow(new NullPointerException("Esta es una excepcion provocada"));
		
		ProgramaSocialDTO dto = new ProgramaSocialDTO(1L);
		dto.setIdProgramaSocial(1);
		dto.setClave("clave");
		dto.setNombre("nombre");
		dto.setUnidadResponsable(new UnidadResponsableDTO());
		dto.getUnidadResponsable().setIdUnidadResponsable(1);
		dto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarException() {
		
		CatProgramaSocial entidad = new CatProgramaSocial();
		
		Mockito.when(programaSocialRepo.findOne(Mockito.anyInt())).thenReturn(entidad);

		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(programaSocialRepo)
				.delete((CatProgramaSocial) Mockito.anyObject());
		
		ProgramaSocialDTO dto = new ProgramaSocialDTO(1L);
		dto.setIdProgramaSocial(1);
		
		try {
			ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
