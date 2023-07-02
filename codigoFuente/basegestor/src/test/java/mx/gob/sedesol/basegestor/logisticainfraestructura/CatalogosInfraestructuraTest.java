package mx.gob.sedesol.basegestor.logisticainfraestructura;

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

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ConfiguracionAreaService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SedeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CatalogosInfraestructuraTest {

	private static final Logger log = Logger.getLogger(CatalogosInfraestructuraTest.class);
	@Autowired
	private SedeService sedeService;
	
	@Autowired
	private ConfiguracionAreaService configuracionAreaService;
	
	
	//@Test
	public void consultaSedes(){
		log.info("Consultando sedes");
		List<SedeDTO> sedes = sedeService.findAll();
		for(SedeDTO s :  sedes){
			log.info(s.getNombre());
		}
	}
	
	//@Test
	public void consultaSedesPorUbicacionYOrgGub(){
		List<SedeDTO> sedes = sedeService.consultaSedesPorUbicacionOrgGub(1,13);
		sedes.size();
	}
	
	//@Test
	public void consultaAreaSedeByIdArea(){
		AreaSedeDTO dto  = configuracionAreaService.consultaAreaSedePorIdSede(2);
		log.info(dto.toString());
	}
	
	/**
	 * 
	 */
	//@Test
	public void insertarConfiguracionArea(){
		
		ConfiguracionAreaDTO configArea = new ConfiguracionAreaDTO();
		
		configArea.setEmailCc("example@mail.com");
		configArea.setEmailResponsable("exampleRes@mail.com");
		configArea.setEmailSolicitante("solicitante@example.com");
		configArea.setFechaRegistro(new Date());
		//configArea.setIdEstatus(ConstantesGestor.ACTIVO);
		configArea.setRutaImgDos("ruta/2/");
		configArea.setRutaImgUno("ruta/1/");
		configArea.setRutaImgTres("ruta/3/");
		configArea.setSolicitante("Pedro Paramo Gonzales");
		configArea.setUsuarioModifico(new Long(1));
		configArea.setCatAreasSede(configuracionAreaService.consultaAreaSedePorIdSede(1));
		
		configuracionAreaService.guardar(configArea);
		
	}
	
	//@Test
	public void consultaAreasSedesPorCriterios(){
		
		CritBusquedaAreasConfigDTO criterios = new CritBusquedaAreasConfigDTO();
		
		criterios.setIdOrgGubernamental(13);
		criterios.setIdUbicacion(1);
		//criterios.setIdSede(1);
		//criterios.setIdArea(2);
		
		List<ConfiguracionAreaDTO> lst = configuracionAreaService.busquedaAreasConfiguradasCriterios(criterios);
		log.info("#### JABA is Here ####" + lst.size());
	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
}
