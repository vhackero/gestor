package mx.gob.sedesol.basegestor.planesprogramas;

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

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEtematicaTemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelUDidacticaTposCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelUniDidacticaMaterialDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciaEspecifica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatMaterialDidactico;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstUnidadDidacticaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstructuraTematicaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class DetEstUnidadDidacticaTest {
	
	private static final Logger logger = Logger.getLogger(DetEstUnidadDidacticaTest.class);
	
	@Autowired
	private EstUnidadDidacticaService estUnidadDidacticaService;
	
	@Autowired
	private CatalogoComunService<CatMaterialDidactico, Integer> materialDidacticoService;
	
	@Autowired
	private CatalogoComunService<CatCompetenciaEspecifica, Integer> competenciasEspeService;
	
	@Autowired
	private EstructuraTematicaService estructuraTematicaService;
	
	
	//@Test
	public void actualizar(){
		
		DetEstUniDidacticaDTO unidadDidSved = estUnidadDidacticaService.buscarPorId(15);
		
		 List<CatalogoComunDTO> uniDidcts = materialDidacticoService.findAll(CatMaterialDidactico.class);
		
		 List<CatalogoComunDTO> compsEspecificas  = competenciasEspeService.findAll(CatCompetenciaEspecifica.class);
		 
		 DetEtematicaTemaDTO tema = estructuraTematicaService.obtieneDetEtematicaTema(1,"TEMA UNO");
		 
		 RelEstUnidadDidacticaDTO relEstUnidDid = new RelEstUnidadDidacticaDTO();
		 relEstUnidDid.setDetEtematicaTema(tema);
		 relEstUnidDid.setFechaRegistro(new Date());
		 relEstUnidDid.setDetEstUnidadDidactica(unidadDidSved);
		 relEstUnidDid.setUsuarioModifico(unidadDidSved.getUsuarioModifico());
			
		 unidadDidSved.getRelEstructuraUnidadDidacticas().add(relEstUnidDid);
		 unidadDidSved.setFechaActualizacion(new Date());
//		 
		if(!ObjectUtils.isNullOrEmpty(uniDidcts)){
			for(CatalogoComunDTO matDid : uniDidcts){
				
				RelUniDidacticaMaterialDTO relMat = new RelUniDidacticaMaterialDTO();
				relMat.setFechaRegistro(new Date());
				relMat.setCatMaterialDidactico(matDid);
				relMat.setDetEstUnidadDidactica(unidadDidSved);
				relMat.setUsuarioModifico(unidadDidSved.getUsuarioModifico());
				
				unidadDidSved.getRelUniDidacticaMaterial().add(relMat);
			}
		}
			
		if(!ObjectUtils.isNullOrEmpty(uniDidcts)){
			
			for(CatalogoComunDTO cmpEsp : compsEspecificas){
				RelUDidacticaTposCompetenciaDTO rel = new RelUDidacticaTposCompetenciaDTO();
				rel.setCatCompetenciaEspecifica(cmpEsp);
				rel.setDetEstUnidadDidactica(unidadDidSved);
				rel.setFechaRegistro(new Date());
				rel.setUsuarioModifico(unidadDidSved.getUsuarioModifico());
				
				unidadDidSved.getRelUDidacticaTposCompetencia().add(rel);
			}
		}
			
			ResultadoDTO<DetEstUniDidacticaDTO> res =  estUnidadDidacticaService.actualizar(unidadDidSved);
			if(res != null && res.getResultado().getValor()){
				logger.info("guardado exitoso");
			}else{
				logger.error("error");
			}
	}
	
	 @Test
		public void prueba() {
			assertThat(1==2, is(false));
		}

}
