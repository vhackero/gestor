package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

import mx.gob.sedesol.basegestor.model.repositories.admin.ParametroSistemaRepo;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.impl.admin.ParametroSistemaServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class ParametroSistemaServiceMockTest {
	
	private static final Logger logger = Logger.getLogger(ParametroSistemaServiceMockTest.class);
	
	@Mock
	private ParametroSistemaRepo parametroSistemaRepo;
	
	@InjectMocks
	private ParametroSistemaService parametroSistemaService = new ParametroSistemaServiceImpl();
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);	
	}
	
	@Test
	public void obtenerParametroConRutaCompleta() {
		Mockito.when(parametroSistemaRepo.findOne(Mockito.anyString())).thenReturn(null);
		
		try {
			String parametro = parametroSistemaService.obtenerParametroConRutaCompleta("RUTA_PRINCIPAL");
			assertThat(parametro, is(nullValue()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
		
	}

}
