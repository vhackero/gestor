package mx.gob.sedesol.basegestor.gestionescolar;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAsistencia;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDictamen;
import mx.gob.sedesol.basegestor.service.gestionescolar.CatalogoAsistenciaService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

/**
 * Created by jhcortes on 2/02/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudCatalogoAsistenciaTest {


    private static final Logger logger = Logger.getLogger(CrudCatalogoAsistenciaTest.class);

    @Autowired
    private CatalogoAsistenciaService catalogoAsistenciaService;

    //@Test
    public void insertaRegistro(){
        logger.info("*****************************************************************************************************");
        AsistenciaDTO entidadDTO = new AsistenciaDTO();
        entidadDTO.setNombre("Asistencia");
        entidadDTO.setNombreCorto("A");
        entidadDTO.setDescripcion("----------");
        entidadDTO.setFechaRegistro(new Date());
        entidadDTO.setUsuarioModifico(2L);
        entidadDTO.setOrden(1);
        try {
            ResultadoDTO resultado = catalogoAsistenciaService.guardar(entidadDTO);
            entidadDTO = (AsistenciaDTO) resultado.getDto();
            logger.info("ID["+entidadDTO.getId()+"]");
        } catch (Exception e) {

            e.printStackTrace();
        }
        logger.info("*****************************************************************************************************");
    }
    
    @Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
}
