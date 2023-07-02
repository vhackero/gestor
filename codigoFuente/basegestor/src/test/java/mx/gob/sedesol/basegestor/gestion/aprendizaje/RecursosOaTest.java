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
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RecursosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RecursosOaSerivce;

/**
 * @author jhcortes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class RecursosOaTest {
	  private static final Logger logger = Logger.getLogger(RecursosOaTest.class);

	    @Autowired
	    private RecursosOaSerivce recursosOaSerivce;

	    //@Test
	    public void buscarRecursosOasPorUnidadOaAvaTest(){
	    	
	    	 
	    	
	    	
	    	
	    	UnidadOaAvaDTO avaDTO = new UnidadOaAvaDTO();
	    	
	    	avaDTO.setId(1);
	    	
	    	List<RecursosOaDTO> recursosOaDTOs = 
	    	recursosOaSerivce.buscarRecursosOasPorUnidadOaAva(avaDTO);
	    	
	    	
	    	logger.info("El resultado es "+recursosOaDTOs.size());
	    	
	    	
	    	logger.info("Borrara ");
	    	
	    	RecursosOaDTO dto = 
	    	recursosOaDTOs.get(0);
	    	
	    	logger.info("El registro que Borrara sera  "+dto);
	    	
	    	recursosOaSerivce.eliminar(dto);
	    	
	    	
	    	logger.info("Borro correctamente el registro  ");
	    	
	    	List<RecursosOaDTO> recursosOaDTOs2 = 
	    	    	recursosOaSerivce.findAll();
	    	
	    	
	    	logger.info("El tamanio de la lista encontrada es   "+recursosOaDTOs2.size());
	    	
	    	
	    }
	    
	    @Test
	    public void guardarRecursosTest(){
	    	
	    	UnidadOaAvaDTO unidadOaAva = new UnidadOaAvaDTO();
	    	unidadOaAva.setId(1);
	    	
	    	CatalogoComunDTO catTemaRecurso = new CatalogoComunDTO();
	    	catTemaRecurso.setId(2);
	    	
	    	
	    	RecursosOaDTO recursosOaDTO = new RecursosOaDTO();
	    	recursosOaDTO.setFechaRegistro(new Date());
	    	recursosOaDTO.setFechaActualizacion(new Date());
	    	recursosOaDTO.setInstruccionesContenido("Estas son las instrucciones");
	    	recursosOaDTO.setNombre("Este es el nombre");
	    	recursosOaDTO.setUsuarioModifico(new BigInteger("2"));
	    	recursosOaDTO.setUnidadOaAva(unidadOaAva);
	    	recursosOaDTO.setCatTemaRecurso(catTemaRecurso);
	    
	    
	    	ResultadoDTO<RecursosOaDTO> recursosOaDTO2 = 
	    	recursosOaSerivce.guardar(recursosOaDTO);
	    	
	    	
	    	logger.info("Se persistio correctamente el objeto"+recursosOaDTO2.getDto().getIdUObjetoAprendizaje());
	    	
	    	List<RecursosOaDTO> recursosOaDTOs2 = 
	    	    	recursosOaSerivce.findAll();
	    	
	    	
	    	logger.info("El tamanio de la lista encontrada es   "+recursosOaDTOs2.size());
	    	
	    	
	    }
	    
	    
	   // @Test
		public void prueba() {
			assertThat(1==2, is(false));
		}

}
