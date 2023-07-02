package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

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

import mx.gob.sedesol.basegestor.model.entities.admin.TblTema;
import mx.gob.sedesol.basegestor.model.repositories.admin.TemaRepo;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.TemaService;
import mx.gob.sedesol.basegestor.service.impl.admin.TemaServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class TemaServiceMockTest {
	
	private static final Logger logger = Logger.getLogger(TemaServiceMockTest.class);
	
	@Mock
	private TemaRepo temaRepo;
	
	@Mock
	private ParametroSistemaService parametroSistemaService;
	
	@InjectMocks
	private TemaService temaService = new TemaServiceImpl();
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);	
	}
	
	@Test
	public void obtenerTemaActivo() {
		List<TblTema> temas = new ArrayList<>();
		TblTema tema = new TblTema();
		tema.setTipoTema(0);
		tema.setRuta("RUTA");
		temas.add(tema);
		
		Mockito.when(temaRepo.buscarPorTipoActivo(0, true)).thenReturn(temas);
		
		Mockito.when(parametroSistemaService.obtenerParametroConRutaCompleta(Mockito.anyString())).thenReturn("/RUTA");
		
		String ruta = temaService.obtenerTemaActivo(0);
		logger.info(ruta);
		assertThat(ruta, is(not(nullValue())));
	}

}
