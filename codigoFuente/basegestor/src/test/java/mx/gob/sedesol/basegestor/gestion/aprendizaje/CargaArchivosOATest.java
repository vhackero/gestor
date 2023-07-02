/**
 * 
 */
package mx.gob.sedesol.basegestor.gestion.aprendizaje;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.CargaArchivosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatClasificacionArchivoOa;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.CargaArchivosOaService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CargaArchivosOATest {

	private static final Logger logger = Logger.getLogger(CargaArchivosOATest.class);

	@Autowired
	private CargaArchivosOaService cargaArchivosOaService;

	@Test
	public void buscarPorIdUnidadOaTest(){
		
		logger.info("Inicia la prueba buscarPorIdUnidadOaTest");
		
		UnidadOaAvaDTO unidadOaAvaDTO = new UnidadOaAvaDTO();
		unidadOaAvaDTO.setId(1);
		CatalogoComunDTO  catalogoComunDTO = new CatalogoComunDTO();
		catalogoComunDTO.setId(2);
		
		List<CargaArchivosOaDTO> archivosOaDTOs = 
	
	    		
				
		cargaArchivosOaService.buscarPorIdUnidadOa(unidadOaAvaDTO,catalogoComunDTO);
		
		
		
		logger.info("El valor a obtener es "+(archivosOaDTOs.size()));
		
		
		CargaArchivosOaDTO ultimoArchivo = archivosOaDTOs.get(0);
		
		logger.info("Termina la prueba buscarPorIdUnidadOaTest"+ultimoArchivo.getId());
		
		
	
		
	}
	
	//@Test
	public void guardaArchivosTest(){
		
		logger.info("Inicia la prueba buscarPorIdUnidadOaTest");
		
		List<CargaArchivosOaDTO> archivosOaDTOs = 		
		
		cargaArchivosOaService.findAll();
		
		logger.info("El total de los archivos encontrados es "+archivosOaDTOs.size());
		
		UnidadOaAvaDTO unidadOaAvaDTO = new UnidadOaAvaDTO();
		unidadOaAvaDTO.setId(1);
		
		CatalogoComunDTO catalogoComunDTO = new CatalogoComunDTO();
		
		catalogoComunDTO.setId(1);
		
		
		CargaArchivosOaDTO  cargaArchivosOaDTO =new CargaArchivosOaDTO(); 
		cargaArchivosOaDTO.setUnidadOaAva(unidadOaAvaDTO);
		cargaArchivosOaDTO.setDirectorio(System.getProperty("user.home"));
		cargaArchivosOaDTO.setFechaActualizacion(new Date());
		cargaArchivosOaDTO.setFechaRegistro(new Date());
		cargaArchivosOaDTO.setIdLms(1);
		cargaArchivosOaDTO.setNombreArchivo("Archivo dummy");
		cargaArchivosOaDTO.setUsuarioModifico(new BigInteger("2"));
		cargaArchivosOaDTO.setCatClasificacionArchivoOa(catalogoComunDTO);
		
		ResultadoDTO<CargaArchivosOaDTO> cargaArchivosOaDTOs = 
		cargaArchivosOaService.guardar(cargaArchivosOaDTO);
		
		logger.info("Se guardo el archivo :"+
		cargaArchivosOaDTOs.getResultado());
		

		archivosOaDTOs =
		
		cargaArchivosOaService.findAll();
		
		logger.info("El total de los archivos encontrados es "+archivosOaDTOs.size());
		
		
		logger.info("Termina la prueba buscarPorIdUnidadOaTest");
		
	}
	
	//@Test
	public void eliminarArchivosTest(){
		
		List<CargaArchivosOaDTO> archivosOaDTOs = 		
				
				cargaArchivosOaService.findAll();
		
		
		
		logger.info("El tamaño de la lista antes de borrar "+archivosOaDTOs.size());
		archivosOaDTOs = null;
		
		CargaArchivosOaDTO dto = new CargaArchivosOaDTO();
		dto.setId(1);
		
		
		ResultadoDTO<CargaArchivosOaDTO> resultadoDTO = 
		cargaArchivosOaService.eliminar(dto);
		
		logger.info("El resultado de borrar fue "+resultadoDTO.getResultado());
		
		
		 archivosOaDTOs = 		
				
				cargaArchivosOaService.findAll();
		 
		 
		 logger.info("El tamaño de la lista despues de borrar "+archivosOaDTOs.size());
	}
	
	
	
	
	@Test
	public void prueba() {
		assertThat(1 == 2, is(false));
	}

}
