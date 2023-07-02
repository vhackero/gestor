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

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.AreaConocimientoDTO;
import mx.gob.sedesol.basegestor.service.planesyprogramas.AreaConocimientoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudAreasConocimientoTest {
	
	private static final Logger logger = Logger.getLogger(DomicilioPersonaServiceTest.class);
	
	@Autowired
	private AreaConocimientoService areaConocimientoService;
	
	//@Test
	public void guardarAreaConocimientoTest(){
		
		AreaConocimientoDTO dto = new AreaConocimientoDTO();
		dto.setActivo(1);
		dto.setDescripcion("area de conocimiento test");
		dto.setFechaRegistro(new Date());
		dto.setNombre("Psicologia en la sociedad");
		dto.setNombreUsuarioMod("system");
		dto.setUsuarioModifico(2L);
		try {
			areaConocimientoService.guardar(dto);
			logger.info("Se genero correctamente el area de conocimiento.");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void actualizaAreaConocimiento(){
		AreaConocimientoDTO ac = areaConocimientoService.buscarPorId(1);
		if(ac != null){
			ac.setDescripcion("es un test de actualizacion II");
			ac.setFechaActualizacion(new Date());
			ac.setNombreUsuarioMod("system");
			ac.setUsuarioModifico(2L);
			ac.setActivo(0);
			
			try {
				areaConocimientoService.actualizar(ac);
				logger.info("Se actualizo correctamente el area de conocimiento.");
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}

}
