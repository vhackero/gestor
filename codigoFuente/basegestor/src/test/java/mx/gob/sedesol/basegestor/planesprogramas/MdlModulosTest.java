package mx.gob.sedesol.basegestor.planesprogramas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ModuloMoodleDTO;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ModuloMoodleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class MdlModulosTest {

	public static final Logger logger = Logger.getLogger(MdlModulosTest.class);
	
	@Autowired
	private ModuloMoodleService moduloMoodleService;
	
	//@Test
	public void consultarMdlModulos(){
		
		List<ModuloMoodleDTO> lst = moduloMoodleService.findAll();
		logger.info(lst.size());
		for(ModuloMoodleDTO tmp : lst){
			logger.info(tmp.getNombre());
		}
	}
	
	//@Test
	public void consultaMdlModulosPorTipo(){
		List<ModuloMoodleDTO> lst = moduloMoodleService.buscarPorTipoDeModulo(1);
		logger.info(lst.size());
		for(ModuloMoodleDTO tmp : lst){
			logger.info(tmp.getNombre());
		}
	}
	
	 @Test
		public void prueba() {
			assertThat(1==2, is(false));
		}
}
