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

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.NivelEnPlanProgramaDTO;
import mx.gob.sedesol.basegestor.service.planesyprogramas.NivelEnsPlanProgramaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class NivelPlanProgramasTest {
	
	private static final Logger logger = Logger.getLogger(NivelPlanProgramasTest.class);
	//@Autowired
	//private NivelEnsPlanProgramaService nivelEnsPlanProgramaService;
	
	//@Test
	public void insertaNivelPlanPrograma(){
		NivelEnPlanProgramaDTO dto = new NivelEnPlanProgramaDTO();
		dto.setActivo(0);
		dto.setDescripcion("Nivel medio superior");
		dto.setFechaRegistro(new Date());
		dto.setNombre("Preparatoria");
		dto.setNombreUsuarioMod("system");
		dto.setOrden(1);
		dto.setUsuarioModifico(1l);
		
		try {
			//logger.info("##### RESULTADO #### " + nivelEnsPlanProgramaService.guardar(dto));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void actualizaNivelPlanPrograma(){
		NivelEnPlanProgramaDTO dto = new NivelEnPlanProgramaDTO(); //nivelEnsPlanProgramaService.buscarPorId(1);
		dto.setDescripcion("Nivel medio superior II");
		dto.setFechaActualizacion(new Date());
		dto.setNombreUsuarioMod("system");
		dto.setUsuarioModifico(1l);
		
		try {
			//logger.info("##### RESULTADO #### " + nivelEnsPlanProgramaService.actualizar(dto));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}

}
