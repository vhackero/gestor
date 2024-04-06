package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CapturaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionResumenDTO;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;

@Service("dispersionServiceFacade")
public class DispersionServiceFacade {

	private static final Logger logger = Logger.getLogger(DispersionServiceFacade.class);
	
	@Autowired
	private DispersionService dispersionService;

	@Autowired
	private EventoCapacitacionService eventoCapacitacionService;

	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;
	
	@Autowired
	private AmbienteVirtualApService ambienteVirtualApService;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoParticipanteService grupoParticipanteService;

	@Autowired
	private PersonaService personaService;

	@Autowired

	private CorreoElectronicoService correoElectronicoService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	
	public List<TblInscripcionResumenDTO> getInscripcionResumenByProgramaEducativo(List<String> programaEducativo) {
		return dispersionService.getInscripcionResumenByProgramaEducativo(programaEducativo);
	}
	
	public List<TblInscripcionResumenDTO> getInscripcionResumenByIdPlanesSemestreBloque(List<Integer> programaEducativo, Integer semestre, String bloque) {
		return dispersionService.getInscripcionResumenByIdPlanesSemestreBloque(programaEducativo, semestre, bloque);
	}
	
	public List<TblInscripcionDTO> getInscripcionesByIdPlan(Integer idPLan) {
		return dispersionService.getInscripcionesByIdPlan(idPLan);
	}
	
	public List<TblInscripcionDTO> getInscripcionesByProgramasEducativos(List<String> programas) {
		return dispersionService.getInscripcionesByProgramasEducativos(programas);
	}
	public List<TblInscripcionDTO> getInscripcionesByIdPlanes(List<Integer> idPlanes) {
		return dispersionService.getInscripcionesByIdPlanes(idPlanes);
	}
	
	public List<TblInscripcionDTO> getInscripcionesByIdPlanPrograma(Integer idPlan, String programa) {
		return dispersionService.getInscripcionesByIdPlanPrograma(idPlan,programa);
	}


	public List<ParametroWSMoodleDTO> obtenerPlataformasMoodle() {
		return parametroWSMoodleService.findAll();
	}

	public RelGrupoParticipanteDTO almacenarParticipante(GrupoDTO grupo, PersonaDTO persona,
			EventoCapacitacionDTO evento, ParametroWSMoodleDTO parametroWSMoodleDTO) {
		RelGrupoParticipanteDTO alumno;
		try {
			alumno = grupoParticipanteService.almacenarParticipante(grupo, persona, evento, parametroWSMoodleDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			alumno = null;
		}
		return alumno;
	}

	public List<GrupoDTO> obtenerGruposPorIdEvento(Integer idEvento) {

		return grupoService.getGruposByEvento(idEvento);
	}
	
	public List<GrupoDTO> obtenerGruposPorIdEventos(List<Integer> eventos) {

		return grupoService.getGruposPorIdEventos(eventos);
	}

	public List<RelGrupoParticipanteDTO> obtenerParticipantesPorEvento(Integer idEvento) {
		return grupoParticipanteService.getParticipantesByEvento(idEvento);
	}

	public CapturaEventoCapacitacionDTO obtenerEvento(EventoCapacitacionDTO evento) {
		return eventoCapacitacionService.obtenerEvento(evento);
	}

	public EventoCapacitacionService getEventoCapacitacionService() {
		return eventoCapacitacionService;
	}

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public ParametroWSMoodleService getParametroWSMoodleService() {
		return parametroWSMoodleService;
	}

	public void setParametroWSMoodleService(ParametroWSMoodleService parametroWSMoodleService) {
		this.parametroWSMoodleService = parametroWSMoodleService;
	}
	
	public AmbienteVirtualApService getAmbienteVirtualApService() {
		return ambienteVirtualApService;
	}

	public void setAmbienteVirtualApService(AmbienteVirtualApService ambienteVirtualApService) {
		this.ambienteVirtualApService = ambienteVirtualApService;
	}

	public CorreoElectronicoService getCorreoElectronicoService() {
		return correoElectronicoService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
		this.correoElectronicoService = correoElectronicoService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}


	public String obtenerRutaAlmacenamientoImagenEvento() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaAlmacenamiento
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_EVENTOS));
		return rutaAlmacenamiento.toString();
	}

	public String obtenerNombreImagenComun() {
		return parametroSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_NOMBRE_IMAGEN_EVENTO_COMUN);
	}

	public String obtenerRutaUndertow() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW));
		rutaAlmacenamiento
				.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_EVENTOS));
		return rutaAlmacenamiento.toString();
	}



}
