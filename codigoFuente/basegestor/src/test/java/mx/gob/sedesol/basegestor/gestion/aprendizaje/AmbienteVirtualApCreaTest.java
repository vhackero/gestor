package mx.gob.sedesol.basegestor.gestion.aprendizaje;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelReponsableProduccionEcService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class AmbienteVirtualApCreaTest { 

	
	private static final Logger logger = Logger.getLogger(AmbienteVirtualApCreaTest.class);
	
	@Autowired
	private AmbienteVirtualApService ambienteVirtualApService;  
	
	@Autowired 
	private PersonaResponsabilidadesService personaResponsabilidadesService;
	
	@Autowired 
	private RelReponsableProduccionEcService  relReponsableProduccionEcService;
	
	
	//@Test
	public void busquedaPorCriterios(){ 
		
		List<AmbienteVirtualAprendizajeDTO>  lista = null;
		
		EventoCapacitacionDTO filtro = new EventoCapacitacionDTO();  
		
		FichaDescProgramaDTO fichaDescProgramaDTO = new FichaDescProgramaDTO();
		filtro.setFichaDescriptivaPrograma(fichaDescProgramaDTO);
		
		filtro.getFichaDescriptivaPrograma().setTipoCompetencia(2);
		filtro.getFichaDescriptivaPrograma().setEjeCapacitacion(4);
		filtro.setNombreEc("INTRODUCTORIO");
		filtro.getFichaDescriptivaPrograma().setNombreTentativo("MATES PARA");
	
		
		filtro.setFechaInicial(DateUtils.getCreateDate(13, 2, 2017));
		filtro.setFechaFinal  (DateUtils.getCreateDate(13, 2, 2017));
		
		logger.info("****FECHA        "+ DateUtils.darFormatoFecha(DateUtils.getCreateDate(13, 2, 2017), "yyyy-MM-dd HH:mm:ss"));
		
		
		logger.info("****FECHA INICIO "+ DateUtils.darFormatoFecha(DateUtils.procesarFechaInicio (filtro.getFechaInicial()), "yyyy-MM-dd HH:mm:ss"));
		logger.info("****FECHA FIN    "+    DateUtils.darFormatoFecha(DateUtils.procesarFechaFin (filtro.getFechaInicial()), "yyyy-MM-dd HH:mm:ss"));
		
		
		logger.info("tipo competencia " + filtro.getFichaDescriptivaPrograma().getTipoCompetencia());
		logger.info("eje capacitacion " + filtro.getFichaDescriptivaPrograma().getEjeCapacitacion());
		logger.info("nombre evento    " + filtro.getNombreEc());
		logger.info("nombre programa  " + filtro.getFichaDescriptivaPrograma().getNombreTentativo());
		logger.info("fecha inicial    " + filtro.getFechaInicial());
		logger.info("fecha final      " + filtro.getFechaFinal());
		
		//lista = ambienteVirtualApService.buscaEventosPorCriterios(filtro);  
		
		for (AmbienteVirtualAprendizajeDTO avaDTO : lista){
			logger.info("DTO tipo competencia " + avaDTO.getEventoCapacitacion().getFichaDescriptivaPrograma().getTipoCompetencia());
			logger.info("DTO eje capacitacion " + avaDTO.getEventoCapacitacion().getFichaDescriptivaPrograma().getEjeCapacitacion());
			logger.info("DTO nombre evento    " + avaDTO.getEventoCapacitacion().getNombreEc());
			logger.info("DTO nombre programa  " + avaDTO.getEventoCapacitacion().getFichaDescriptivaPrograma().getNombreTentativo());
			logger.info("DTO fecha inicial    " + avaDTO.getEventoCapacitacion().getFechaInicial());
			logger.info("DTO fecha final      " + avaDTO.getEventoCapacitacion().getFechaFinal());
			logger.info("DTO id estado AVA       " + avaDTO.getCatEstadoAva().getId());
			logger.info("DTO estado AVA       " + avaDTO.getCatEstadoAva().getDescripcion());
		}
		   
		  
		logger.info("*************FIN PRUEBA*************" );
	}
	
	
	//@Test
	public void busquedaPorEstatus() {
		int estatus = 1;
		List<AmbienteVirtualAprendizajeDTO>  lista = null;
		
	 //	lista = ambienteVirtualApService.consultaEventoPorEstatus(estatus);  
		
		for (AmbienteVirtualAprendizajeDTO avaDTO : lista){
			logger.info("DTO tipo competencia " + avaDTO.getEventoCapacitacion().getFichaDescriptivaPrograma().getTipoCompetencia());
			logger.info("DTO eje capacitacion " + avaDTO.getEventoCapacitacion().getFichaDescriptivaPrograma().getEjeCapacitacion());
			logger.info("DTO nombre evento    " + avaDTO.getEventoCapacitacion().getNombreEc());
			logger.info("DTO nombre programa  " + avaDTO.getEventoCapacitacion().getFichaDescriptivaPrograma().getNombreTentativo());
			logger.info("DTO fecha inicial    " + avaDTO.getEventoCapacitacion().getFechaInicial());
			logger.info("DTO fecha final      " + avaDTO.getEventoCapacitacion().getFechaFinal());
			logger.info("DTO id estado AVA       " + avaDTO.getCatEstadoAva().getId());
			logger.info("DTO estado AVA       " + avaDTO.getCatEstadoAva().getDescripcion());
		}
		   
	}
		
	//@Test
	public void busquedaTipoPersonaPorResponsabilidad() {
		int idTipoResponsabilida = 3; 
		List<PersonaResponsabilidadesDTO>  lista = null;
		
		lista = personaResponsabilidadesService.obtienePersonasPorResponsabilidad (idTipoResponsabilida); 
		
		logger.info("DTO LISTA SIZE " + lista.size());
		
		for (PersonaResponsabilidadesDTO personaResDTO : lista){
			logger.info("*DTO id persona-res   " + personaResDTO.getId());      
			logger.info("DTO id persona       " + personaResDTO.getTblPersona().getIdPersona());        
			logger.info("DTO nombre           " + personaResDTO.getTblPersona().getNombre());        
			logger.info("DTO apellido paterno " + personaResDTO.getTblPersona().getApellidoPaterno());
			logger.info("DTO apellido materno " + personaResDTO.getTblPersona().getApellidoMaterno());		
		}
	}
	
	//@Test
	public void buscarCoordinadorEvento(){
		int idEvento = 1;
		
		
		List<ReponsableProduccionEcDTO>  lista = relReponsableProduccionEcService.getResponsableDelEvento(idEvento
				, ConstantesGestor.TIPO_RESPONSABILIDAD_COORDINADOR );
	
		for (ReponsableProduccionEcDTO respEvento :lista){
			logger.info("*DTO id persona-res   " + respEvento.getIdReponsableProduccion());   
			logger.info("*DTO nombre           " + respEvento.getPersonaResponsabilidad().getTblPersona().getNombre());   
			logger.info("*DTO apellido paterno " + respEvento.getPersonaResponsabilidad().getTblPersona().getApellidoPaterno());
			logger.info("*DTO apellido materno " + respEvento.getPersonaResponsabilidad().getTblPersona().getApellidoMaterno());
			logger.info("*DTO responsabilidad  " + respEvento.getPersonaResponsabilidad().getCatTipoResponsabilidadEc().getNombre());
		}
	
	
	}
	
	@Test
	public void updateResponsableAva() { 
		
		int idEvento = 1;
		int idPersonaResponsabilidadNew = 3;  
		int idPersonaResponsabilidadOld = 0;
		AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO = null;
		
		ambienteVirtualAprendizajeDTO = ambienteVirtualApService.findAvaById(idEvento);
		
		idPersonaResponsabilidadOld = ambienteVirtualAprendizajeDTO.getPersonaResponsabilidades().getId(); 
		ambienteVirtualAprendizajeDTO.getPersonaResponsabilidades().setId(idPersonaResponsabilidadNew);
		ambienteVirtualApService.save(ambienteVirtualAprendizajeDTO); 
		
		ambienteVirtualAprendizajeDTO = ambienteVirtualApService.findAvaById(idEvento);
		
		logger.info("*DTO id persona-res   ANTES   " + idPersonaResponsabilidadOld);   
		logger.info("*DTO id persona-res   DESPUES " + ambienteVirtualAprendizajeDTO.getPersonaResponsabilidades().getId());   
		
	}
	
	
	
}