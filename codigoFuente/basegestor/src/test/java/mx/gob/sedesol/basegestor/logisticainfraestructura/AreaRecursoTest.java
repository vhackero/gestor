package mx.gob.sedesol.basegestor.logisticainfraestructura;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaRecursoService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.RecursosInfraestructuraService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class AreaRecursoTest {
	private static final Logger logger = Logger.getLogger(AreaRecursoTest.class);

	@Autowired
	private AreaRecursoService areaRecursoService;

	@Autowired
	private RecursosInfraestructuraService recursosInfraestructuraService;

	// @Test
	public void recursosPorArea() {
		List<RelAreaRecursoDTO> res = areaRecursoService.obtieneRecursosPorIdArea(1);
		for (RelAreaRecursoDTO a : res) {
			logger.info("######################## " + a.getCantidad() + " "
					+ a.getCatRecursosInfraestructura().getNombre());
		}
	}

	//@Test
	public void guardar() {
		RelAreaRecursoDTO res = new RelAreaRecursoDTO();
		res.setCantidad(120);
		res.setFechaRegistro(new Date());
		res.setIdAreaConfig(1);
		res.setIdRecurso(1);
		res.setUsuarioRegistro(2L);

		ResultadoDTO<RelAreaRecursoDTO> r = areaRecursoService.guardar(res);
		logger.info("############################################ Checa shavo se guarda " + r.getDto().getCantidad());
	}
	
	//@Test
	public void actualizar() {
		RelAreaRecursoDTO res = new RelAreaRecursoDTO();
		res.setCantidad(1200);
		res.setIdAreaConfig(1);
		res.setIdRecurso(1);

		ResultadoDTO<RelAreaRecursoDTO> r = areaRecursoService.actualizar(res);
		logger.info("############################################ Checa shavo se actualiza " + r.getDto().getCantidad());
	}
	
	//@Test
	public void eliminar() {
		RelAreaRecursoDTO res = new RelAreaRecursoDTO();
		res.setIdAreaConfig(1);
		res.setIdRecurso(2);

		ResultadoDTO<RelAreaRecursoDTO> r = areaRecursoService.eliminar(res);
		logger.info("############################################ Checa shavo se elimina " + r.getDto().getIdRecurso());
	}
	
	public AreaRecursoService getAreaRecursoService() {
		return areaRecursoService;
	}

	public void setAreaRecursoService(AreaRecursoService areaRecursoService) {
		this.areaRecursoService = areaRecursoService;
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
