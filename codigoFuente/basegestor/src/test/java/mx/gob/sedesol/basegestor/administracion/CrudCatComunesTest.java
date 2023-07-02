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

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatDocumentosExpidePlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatEstadoPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatEstatusPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModeloPedagogicoPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoPlan;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudCatComunesTest {
	
	private static final Logger logger = Logger.getLogger(CatTipoPlan.class);
	
	@Autowired
	private CatalogoComunService<CatTipoPlan, Long> catalogoComunService;
	
	@Autowired
	private CatalogoComunService<CatDocumentosExpidePlan,Long> catalogoDocumentosPlan;
	
	@Autowired
	private CatalogoComunService<CatEstatusPlan,Long> catalogoEstadoPlan;
	
	@Autowired
	private CatalogoComunService<CatEstadoPrograma,Long> catalogoEstadoPrograma;
	
	@Autowired
	private CatalogoComunService<CatModeloPedagogicoPlan,Long> catalogoModeloPedagogico;
	
	
	//@Test
	public void insertarTipoPlanTest(){
		
		//catalogoComunService.buscarPorId(1L);
		
		CatalogoComunDTO tipoPlan = new CatalogoComunDTO();
		tipoPlan.setActivo(1);
		tipoPlan.setFechaRegistro(new Date());
		tipoPlan.setNombre("ADDIE");
		tipoPlan.setNombreUsuarioMod("system");
		tipoPlan.setOrden(1);
		tipoPlan.setUsuarioModifico(1l);
		
		try {
			logger.info(" ##### RESULTADO: " + catalogoModeloPedagogico.guardar(tipoPlan, CatModeloPedagogicoPlan.class));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void insertarCatEstadoPlanTest(){
		
		CatalogoComunDTO catComun = new CatalogoComunDTO();
		catComun.setActivo(1);
		catComun.setDescripcion("Estado cerrado");
		catComun.setFechaRegistro(new Date());
		catComun.setNombre("Cerrado");
		catComun.setNombreUsuarioMod("system");
		catComun.setOrden(1);
		catComun.setUsuarioModifico(1l);
		
		try {
			logger.info(" ##### RESULTADO: " + catalogoEstadoPlan.guardar(catComun, CatEstatusPlan.class));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void insertarCatEstadoPrograma(){
		
		CatalogoComunDTO catComun = new CatalogoComunDTO();
		catComun.setActivo(1);
		catComun.setDescripcion("Programa cerrado");
		catComun.setFechaRegistro(new Date());
		catComun.setNombre("Cerrar programa");
		catComun.setNombreUsuarioMod("system");
		catComun.setOrden(1);
		catComun.setUsuarioModifico(1l);
		
		try {
			logger.info(" ##### RESULTADO: " + catalogoEstadoPrograma.guardar(catComun, CatEstadoPrograma.class));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	
	
	//@Test
	public void buscarPorId(){
		//logger.info(" ##### RESULTADO: " + catalogoComunService.buscarPorId(1l,CatTipoPlan.class));
		logger.info(" ##### RESULTADO: " + catalogoComunService.findAll(CatTipoPlan.class));
	}
	
	//@Test
	public void insertarDocPlanTest(){

		CatalogoComunDTO tipoPlan = new CatalogoComunDTO();
		tipoPlan.setActivo(1);
		tipoPlan.setDescripcion("Plan Licenciatura");
		tipoPlan.setFechaRegistro(new Date());
		tipoPlan.setNombre("Licenciatura");
		tipoPlan.setNombreUsuarioMod("system");
		tipoPlan.setOrden(1);
		tipoPlan.setUsuarioModifico(1l);
		
		try {
			logger.info(" ##### RESULTADO: " + catalogoDocumentosPlan.guardar(tipoPlan, CatDocumentosExpidePlan.class));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void actualizaDocPlanTest(){
		CatalogoComunDTO cat = catalogoDocumentosPlan.buscarPorId(1l, CatDocumentosExpidePlan.class);
		cat.setActivo(0);
		cat.setFechaActualizacion(new Date());
		cat.setUsuarioModifico(1L);
		cat.setDescripcion("Documento maestria");
		cat.setNombreUsuarioMod("system");
		cat.setActivo(1);
		try {
			logger.info(" ##### RESULTADO: " + catalogoDocumentosPlan.actualizar(cat, CatDocumentosExpidePlan.class));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void eliminaDocPlanTest(){
		CatalogoComunDTO cat = catalogoDocumentosPlan.buscarPorId(1l, CatDocumentosExpidePlan.class);
		try {
			
			cat.setFechaActualizacion(new Date());
			cat.setUsuarioModifico(1L);
			cat.setNombreUsuarioMod("system");
			catalogoDocumentosPlan.eliminaLogicamente(cat,CatDocumentosExpidePlan.class);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}

}
