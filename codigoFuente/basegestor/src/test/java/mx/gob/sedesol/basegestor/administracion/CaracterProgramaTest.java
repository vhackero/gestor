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

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CaracterProgramaDTO;
import mx.gob.sedesol.basegestor.service.planesyprogramas.CaracterProgramaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CaracterProgramaTest {
	private static final Logger logger = Logger.getLogger(CaracterProgramaTest.class);
	
	@Autowired
	private CaracterProgramaService caracterProgramaService;
	
	//@Test
	public void insertaCaracterPrograma(){
		
		CaracterProgramaDTO dto = new CaracterProgramaDTO();
		dto.setActivo(1);
		dto.setDescripcion("De caracter Obligatorio");
		dto.setFechaRegistro(new Date());
		dto.setNombre("Obligatorio");
		dto.setNombreUsuarioMod("system");
		dto.setOrden(1);
		dto.setUsuarioModifico(1l);
		
		try {
			logger.info("##### RESULTADO #### " + caracterProgramaService.guardar(dto));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void actualizaCaracterPrograma(){
		CaracterProgramaDTO dto = caracterProgramaService.buscarPorId(1);
		dto.setFechaActualizacion(new Date());
		dto.setActivo(0);
		dto.setDescripcion("obligatorio man!");
		dto.setNombreUsuarioMod("system");
		dto.setUsuarioModifico(1l);
		
		try {
			logger.info("##### RESULTADO #### " + caracterProgramaService.actualizar(dto));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}

}
