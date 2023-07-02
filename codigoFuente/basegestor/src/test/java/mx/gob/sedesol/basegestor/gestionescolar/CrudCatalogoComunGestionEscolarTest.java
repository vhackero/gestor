package mx.gob.sedesol.basegestor.gestionescolar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatCategoriaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionAva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDictamen;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

/**
 * Created by jhcortes on 31/01/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudCatalogoComunGestionEscolarTest {

    private static final Logger logger = Logger.getLogger(CrudCatalogoComunGestionEscolarTest.class);

    @Autowired
    private CatalogoComunService<CatCategoriaEventoCapacitacion, Integer> catalogoEventoCapacitacionService;

    @Autowired
    private CatalogoComunService<CatDictamen, Integer> catalogoDictamenService;

    @Autowired
    private CatalogoComunService<CatEstadoEventoCapacitacion, Integer> catalogoEstadoEventoCapacitacionService;
    
    @Autowired
    private CatalogoComunService<CatClasificacionAva, Integer> catalogoClasificacionAvaService;

    private ModelMapper catComunMapper = new ModelMapper();

    private static final String NOMBRE = "Externo";
    private static final String DESCRIPCION = "Descripcion generica del obejeto";
    private static final Integer ESTADO_ACTIVO= 1;
    private static final Integer ORDEN= 1;
    private static final Long USUARIO_MODIFICO = 2L;

    private CatalogoComunDTO crearRegistro(){
        
        CatalogoComunDTO catalogoComunDTO = new CatalogoComunDTO();
        
        catalogoComunDTO.setNombre(NOMBRE);
        catalogoComunDTO.setDescripcion(DESCRIPCION);
        catalogoComunDTO.setActivo(ESTADO_ACTIVO);
        catalogoComunDTO.setFechaRegistro(new Date());
        catalogoComunDTO.setOrden(ORDEN);
        catalogoComunDTO.setUsuarioModifico(USUARIO_MODIFICO);
        
        
        return catalogoComunDTO;
    }

    //@Test
    public void insertaRegistro() {
        //CatalogoComunDTO catalogoComunDTO = crearRegistro();
        CatalogoComunDTO catalogoDTO = crearRegistro();

        try {
            logger.info("*****************************************************************************************************");
            //ResultadoDTO resultado = catalogoEventoCapacitacionService.guardar(catalogoDTO, CatCategoriaEventoCapacitacion.class);
     //       ResultadoDTO resultado = catalogoDictamenService.guardar(catalogoDTO, CatDictamen.class);
            ResultadoDTO resultado = catalogoEstadoEventoCapacitacionService.guardar(catalogoDTO, CatEstadoEventoCapacitacion.class);

            logger.info("ID[" + catalogoDTO.getId() + "]");
            CatalogoComunDTO catalogoAvaDTO = crearRegistro();

        }catch (Exception e){

        }
    }

   // @Test
    public void actualizarRegistro(){
    	//CatalogoComunDTO catalogoComunDTO = catalogoEventoCapacitacionService.buscarPorId(1, CatCategoriaEventoCapacitacion.class);
        CatalogoComunDTO catalogoComunDTO = catalogoClasificacionAvaService.buscarPorId(1, CatClasificacionAva.class);
    	catalogoComunDTO.setNombre("Actualizacion de registro");
        catalogoComunDTO.setFechaActualizacion(new Date());
    	 try {
             logger.info("*****************************************************************************************************");
             ResultadoDTO resultado = catalogoClasificacionAvaService.actualizar(catalogoComunDTO, CatClasificacionAva.class);
             logger.info("ID["+catalogoComunDTO.getId()+"]");
         } catch (Exception e) {

             e.printStackTrace();
         }

    }
    
    @Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
}
