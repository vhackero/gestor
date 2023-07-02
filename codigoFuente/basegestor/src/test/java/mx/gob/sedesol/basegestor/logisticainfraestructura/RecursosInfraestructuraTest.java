package mx.gob.sedesol.basegestor.logisticainfraestructura;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RecursosInfraestructuraDTO;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.RecursosInfraestructuraService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class RecursosInfraestructuraTest {
	
	private static final Logger logger = Logger.getLogger(RecursosInfraestructuraTest.class);

	@Autowired
	private RecursosInfraestructuraService recursosInfraestructuraService;

	@Test
	public void buscarportiporecurso() {
		List<RecursosInfraestructuraDTO> res = recursosInfraestructuraService.obtieneRecursosPorTipoDeRecurso(1);
		for (RecursosInfraestructuraDTO aux : res) {
			logger.info("#########################################Recurso " + aux.getNombre() );
		}
	}
	
	//@Test
	public void todos(){
		List<RecursosInfraestructuraDTO> res = recursosInfraestructuraService.findAll();
		for (RecursosInfraestructuraDTO aux : res) {
			logger.info("#########################################Recurso " + aux.getNombre() );
		}
	}
	
	//@Test
	public void guardar(){
		RecursosInfraestructuraDTO rec = new RecursosInfraestructuraDTO();
	//	rec.setCatTipoRecurso(catTipoRecurso);
	}
	
	public RecursosInfraestructuraService getRecursosInfraestructuraService() {
		return recursosInfraestructuraService;
	}

	public void setRecursosInfraestructuraService(RecursosInfraestructuraService recursosInfraestructuraService) {
		this.recursosInfraestructuraService = recursosInfraestructuraService;
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}

}
