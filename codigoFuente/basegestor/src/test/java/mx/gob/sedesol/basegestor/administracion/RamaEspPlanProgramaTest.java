package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RamaEspPlanProgramaDTO;
import mx.gob.sedesol.basegestor.service.planesyprogramas.RamaEspPlanProgramaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class RamaEspPlanProgramaTest {
	
	private static final Logger logger = Logger.getLogger(RamaEspPlanProgramaTest.class);
	
	@Autowired
	private RamaEspPlanProgramaService ramaEspPlanProgramaService;
	
	//@Test
	public void insertaRamaEspPlanProgTest(){
		RamaEspPlanProgramaDTO dto = new RamaEspPlanProgramaDTO();
		dto.setActivo(1);
		dto.setDescripcion("Rama_especialidad_test");
		dto.setFechaRegistro(new Date());
		dto.setNombre("Matematicas Avanzadas I");
		dto.setNombreUsuarioMod("system");
		try {
			ResultadoDTO res = ramaEspPlanProgramaService.guardar(dto);
			logger.info(res.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void actualizaRamaEspPlanProgTest(){
		RamaEspPlanProgramaDTO dto = ramaEspPlanProgramaService.buscarPorId(1);
		dto.setDescripcion("Rama_especialidad_test_update");
		dto.setFechaActualizacion(new Date());
		dto.setNombreUsuarioMod("system");
		dto.setOrden(1);
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO res = ramaEspPlanProgramaService.actualizar(dto);
			logger.info(res.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
	

}
