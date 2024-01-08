package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionResumenDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.DispersionServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean
public class DispersionBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DispersionBean.class);

	@ManagedProperty(value = "#{dispersionServiceFacade}")
	private DispersionServiceFacade dispersionServiceFacade;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	@ManagedProperty("#{correoNotificacionBean}")
	private CorreoNotificacionBean correoNotificacionBean;
	
	@ManagedProperty("#{planService}")
	private PlanService planService;
	
	private List<PlanDTO> listaPlanes;
	private List<String> planesSelec;
	private List<TblInscripcionResumenDTO> listaInscripcionesResumen;
	private List<TblInscripcionDTO> listaInscripciones;
	
	private List<GrupoDTO> grupos; 
	private List<PersonaDatosDTO> personas;
	private List<RelGrupoParticipanteDTO> participantes;
	List<PersonaDTO> listaPersonas = new ArrayList<>();

	private ParametroWSMoodleDTO parametroWSMoodleDTO;
	private PersonaDTO filtrosPersona; 
	private PersonaDatosDTO persona;
	private RelGrupoParticipanteDTO participante;
	private EventoCapacitacionDTO evento;
	private GrupoDTO grupo;
	
	private Integer idGrupo;
	private int numeroGrupos;
	private Integer idCursoLms;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void iniciaRecursos() {
		listaPlanes = planService.findAll();
		System.out.println(" DispersionBean >>>>>> "+ listaPlanes.size());
		grupos= new ArrayList<GrupoDTO>();
	}
	
	public void dispersar() {
		System.out.println("planesSelec >>>>>>>>>>>>> ");
	    Optional.ofNullable(planesSelec).orElse(Collections.emptyList()).stream()
         .forEach(System.out::println);
	    String programas = "Gestión Industrial";
	   
	    System.out.println("listaInscripcionesResumen >>>>>>>>>>>>> ");
	    List<TblInscripcionResumenDTO> lista=   obtenerListaInscripcionResumen(programas);
	    Optional.ofNullable(lista).orElse(Collections.emptyList()).stream()
        .forEach(System.out::println);
	
	   /*   System.out.println("listaInscripciones >>>>>>>>>>>>> ");
	   List<TblInscripcionDTO> inscrip=   obtenerListaInscripcionesByProgramas(programas);
	    Optional.ofNullable(inscrip).orElse(Collections.emptyList()).stream()
        .forEach(System.out::println);
	    System.out.println("listaInscripciones >>>>>>>>>>>>> ");*/
	    generarGrupos(lista);
	   // crearMapaInscripcionesXGrupo(lista,inscrip, grupos);
	
	}
	
	
	public List<TblInscripcionResumenDTO>  obtenerListaInscripcionResumen(String programas) {
		System.out.println("obtenerListaInscripcionResumen >>>>>>>>>>>>> ");
		
		listaInscripcionesResumen= dispersionServiceFacade.getInscripcionResumenByProgramaEducativo(programas);
		
		return listaInscripcionesResumen;
	}
	
	public List<TblInscripcionDTO>  obtenerListaInscripcionesByProgramas(String programas) {
		System.out.println("obtenerListaInscripcionesByProgramas >>>>>>>>>>>>> " + programas);
	
		listaInscripciones= dispersionServiceFacade.getInscripcionesByProgramasEducativos(programas);
		
		System.out.println("listaInscripciones >>>>>>>>>>>>> " + listaInscripciones.size());
		
		return listaInscripciones;
	}


	public void generarGrupos(List<TblInscripcionResumenDTO> listaInscripcionResumen) {
		
		System.out.println("generarGrupos >>>>>>>>>>>>> " );
		for (TblInscripcionResumenDTO inscripcionResumen: listaInscripcionResumen ) {
			List<EventoCapacitacionDTO> eventos = dispersionServiceFacade.getEventoCapacitacionService().obtenerEventosPorNombrePrograma(inscripcionResumen.getAsignatura());
			System.out.println("lista de eventos >>>>>>>>>>>>> " + eventos.size());
			
			Optional.ofNullable(eventos).orElse(Collections.emptyList()).stream()
	        .forEach(System.out::println);
			if(!eventos.isEmpty()) {
				evento = eventos.get(0);
				AmbienteVirtualAprendizajeDTO ava = dispersionServiceFacade.getAmbienteVirtualApService()
						.obtenerAVAPorEvento(evento.getIdEvento());
				if (ObjectUtils.isNotNull(ava)) {
					idCursoLms = ava.getIdCursoLms();
					parametroWSMoodleDTO = ava.getPlataformaMoodle();
					evento.setIdCursoLmsBorrador(idCursoLms);
					evento.setIdPlataformaLmsBorrador(parametroWSMoodleDTO.getIdParametroWSMoodle());
				} else {
					idCursoLms = evento.getIdCursoLmsBorrador();
					parametroWSMoodleDTO = dispersionServiceFacade.getParametroWSMoodleService()
							.buscarPorId(evento.getIdPlataformaLmsBorrador());
				}
				//crearLogica para crear Mapa de grupos AQUI 
				grupos.addAll(dispersionServiceFacade.getGrupoService().generarGruposDispersion(evento, inscripcionResumen,
						getUsuarioEnSession().getIdPersona(), parametroWSMoodleDTO));
				//bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_GPO", "", requestActual(), TipoServicioEnum.LOCAL);
			}
			
		}
		
		System.out.println("generarGrupos >>>>>>>>>>>>> " +grupos.size() );
		//contarAlumnosEnGrupos(grupos);

	}

	/*public void contarAlumnosEnGrupos(List<GrupoDTO> grupos) {
		for (GrupoDTO grupoDTO : grupos) {
			grupoDTO.setNumAlumnosMatriculados(dispersionServiceFacade.getGrupoParticipanteService()
					.getParticipantesByGrupo(grupoDTO.getIdGrupo()).size());
		}
	}*/


	public void buscarPersonas() {
		try {
			//todasPersonas = false;
			List<PersonaDatosDTO> lista = dispersionServiceFacade.getPersonaService()
					.busquedaDatosLaboralesPorCriterios(filtrosPersona);
			personas = new ArrayList<>();
			for (PersonaDatosDTO datos : lista) {
				boolean noExiste = true;
				for (RelGrupoParticipanteDTO alumno : participantes) {
					if (alumno.getPersona().getIdPersona().equals(datos.getPersona().getIdPersona())) {
						noExiste = false;
						break;
					}
				}
				/*if (noExiste && existeUsuarioEnEvento(grupo.getEvento().getIdEvento(),datos.getPersona().getIdPersona())) {
					personas.add(datos);
				}*/
			}
		} catch (NullPointerException e) {
			agregarMsgInfo("Se debe seleccionar un grupo.", null);
			logger.info("No se selecciono un grupo. " + e);
		}
	}

	public void crearMapaInscripcionesXGrupo(List<TblInscripcionResumenDTO> listaInscripcionResumen,
				List<TblInscripcionDTO> listaInscripciones, List<GrupoDTO> listaGrupos) {
		System.out.println("crearMapaInscripcionesXGrupo >>>>>>>>>>>>> ");
		Map<String, List<TblInscripcionDTO>> mapaInscripciones= new HashMap<String, List<TblInscripcionDTO>>();
		for(TblInscripcionResumenDTO resumen: listaInscripcionResumen) {
			List<TblInscripcionDTO> inscripcionesByGrupoBase = new ArrayList<TblInscripcionDTO>();
			for(TblInscripcionDTO ins: listaInscripciones) {
				 if(resumen.getGrupo().equals(ins.getGroupbase())) {
					 inscripcionesByGrupoBase.add(ins);
				 }
			}
			System.out.println("resumen.getGrupo() >>>>>>>>>>>>> " +resumen.getGrupo() + " - " +inscripcionesByGrupoBase.size() );
			mapaInscripciones.put(resumen.getGrupo(), inscripcionesByGrupoBase);
		}
		
		if(!mapaInscripciones.isEmpty()) {
			for (Map.Entry<String, List<TblInscripcionDTO>> entry : mapaInscripciones.entrySet()) {
			    System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue().size());
			    String grupoBase= entry.getKey();
			    List<TblInscripcionDTO> listInscripcionesByGrupoBase = entry.getValue();
			    
			    TblInscripcionResumenDTO resumen = listaInscripcionResumen.stream()
			    		  .filter(res -> grupoBase.equals(res.getGrupo()))
			    		  .findAny()
			    		  .orElse(null);
			    
			    System.out.println("resumen>>>>  " + resumen.toString());
			    Map<String, List<GrupoDTO>> mapaGrupos= new HashMap<String, List<GrupoDTO>>();
				for(GrupoDTO group: listaGrupos) {
					List<GrupoDTO> gruposByGrupoBase = new ArrayList<GrupoDTO>();
					System.out.println("mapagrupos >>>>>>>>>>>>> " +grupoBase.substring(0, 19) + " - " +group.getNombre().substring(0, 19) );
						 if( grupoBase.substring(0, 19).equals(group.getNombre().substring(0, 19))) {
							 gruposByGrupoBase.add(group);
						 }
					 
					System.out.println("mapaGrupos >>>>>>>>>>>>> " +grupoBase + " - " +gruposByGrupoBase.size() );
					mapaGrupos.put(grupoBase, gruposByGrupoBase);
				}
			    
			    if(resumen!=null && mapaGrupos !=null ) {
			    	if (grupoBase.equals(resumen.getGrupo())) {
			    		System.out.println("grupoBase >>>>>>>>>>>>> " +grupoBase + " - resumen.getGrupo() -  " +resumen.getGrupo() );
			    		
			    		
			    		System.out.println("grupos a dividir >>>>>>>>>>>>>  " + (resumen.getNoGrupos() + resumen.getGrupoResto()) + " -  -  " +resumen.getGrupo() );
			    		//cambiar guardar los numeros de alumnos en los grupos
			    		// Segregar inscripciones en grupos
			    		// buscarpersonas
			    		// Add personas a matricular
			    	}
			    }
			}
		}
		 
	}

	public void obtnerG() {
		
	}
	

	public void matricularPersonas() {
		//todasPersonas = false;
		List<PersonaDTO> listaPersonas = new ArrayList<>();
		List<PersonaDatosDTO> listaEliminar = new ArrayList<>();
		grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		grupo.setFachaActualizacion(new Date());
		for (PersonaDatosDTO datos : personas) {
			if (datos.isSeleccionado()) {
				listaPersonas.add(datos.getPersona());
			}
		}
		if (listaPersonas.isEmpty()) {
			agregarMsgError("No se han seleccionado usuarios.", null, sistema);
		} else {

			ResultadoDTO<RelGrupoParticipanteDTO> resultado = dispersionServiceFacade
					.getGrupoParticipanteService()
					.almacenarParticipantes(listaPersonas, grupo, evento, parametroWSMoodleDTO);

			if (resultado.esCorrecto()) {

				// TODO Enviar notificacion y correo a los usuarios matriculados
				String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_MATRICULAR_USUARIOS;
				String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_MATRICULAR_USUARIOS;
				correoNotificacionBean.notificarUsuariosMatriculados(claveNotificacion, claveCorreo, listaPersonas,
						grupo, evento);

				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "MTR_PAR", "", requestActual(),
						TipoServicioEnum.LOCAL);
				participantes = dispersionServiceFacade.getGrupoParticipanteService()
						.getParticipantesByGrupo(idGrupo);
				agregaCantidadAlumnos(idGrupo, participantes.size());
				for (RelGrupoParticipanteDTO alumno : participantes) {
					for (PersonaDatosDTO datos : personas) {
						if (alumno.getPersona().getIdPersona().equals(datos.getPersona().getIdPersona())) {
							listaEliminar.add(datos);
							break;
						}
					}
				}
				for (String mensaje : resultado.getMensajes()) {
					if (mensaje.contains(MensajesSistemaEnum.EC_REGISTRO_MOODLE_ERROR.getId())) {
						agregarMsgError(mensaje, null);
					} else {
						agregarMsgInfo(mensaje, null);
					}
				}
				personas.removeAll(listaEliminar);
			} else {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}

	}

	/***
	 * Agrega la cantidad de alumnos matriculados a un grupo
	 * 
	 * @param idGrupoBuscado
	 */
	private void agregaCantidadAlumnos(Integer idGrupoBuscado, Integer cantidadAlumnos) {
		for (GrupoDTO grupo : grupos) {
			if (grupo.getIdGrupo().equals(idGrupoBuscado)) {
				grupo.setNumAlumnosMatriculados(cantidadAlumnos);
				break;
			}

		}
	}

	public DispersionServiceFacade getDispersionServiceFacade() {
		return dispersionServiceFacade;
	}

	public void setDispersionServiceFacade(DispersionServiceFacade dispersionServiceFacade) {
		this.dispersionServiceFacade = dispersionServiceFacade;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public CorreoNotificacionBean getCorreoNotificacionBean() {
		return correoNotificacionBean;
	}

	public void setCorreoNotificacionBean(CorreoNotificacionBean correoNotificacionBean) {
		this.correoNotificacionBean = correoNotificacionBean;
	}

	public PlanService getPlanService() {
		return planService;
	}

	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}

	public List<PlanDTO> getListaPlanes() {
		return listaPlanes;
	}

	public void setListaPlanes(List<PlanDTO> listaPlanes) {
		this.listaPlanes = listaPlanes;
	}

	public List<String> getPlanesSelec() {
		return planesSelec;
	}

	public void setPlanesSelec(List<String> planesSelec) {
		this.planesSelec = planesSelec;
	}

	public List<TblInscripcionResumenDTO> getListaInscripcionesResumen() {
		return listaInscripcionesResumen;
	}

	public void setListaInscripcionesResumen(List<TblInscripcionResumenDTO> listaInscripcionesResumen) {
		this.listaInscripcionesResumen = listaInscripcionesResumen;
	}
	
	public List<TblInscripcionDTO> getListaInscripciones() {
		return listaInscripciones;
	}

	public void setListaInscripciones(List<TblInscripcionDTO> listaInscripciones) {
		this.listaInscripciones = listaInscripciones;
	}

	public List<GrupoDTO> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}


	public List<PersonaDatosDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<PersonaDatosDTO> personas) {
		this.personas = personas;
	}

	public List<RelGrupoParticipanteDTO> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<RelGrupoParticipanteDTO> participantes) {
		this.participantes = participantes;
	}


	public ParametroWSMoodleDTO getParametroWSMoodleDTO() {
		return parametroWSMoodleDTO;
	}

	public void setParametroWSMoodleDTO(ParametroWSMoodleDTO parametroWSMoodleDTO) {
		this.parametroWSMoodleDTO = parametroWSMoodleDTO;
	}

	public PersonaDTO getFiltrosPersona() {
		return filtrosPersona;
	}

	public void setFiltrosPersona(PersonaDTO filtrosPersona) {
		this.filtrosPersona = filtrosPersona;
	}

	public PersonaDatosDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDatosDTO persona) {
		this.persona = persona;
	}

	public RelGrupoParticipanteDTO getParticipante() {
		return participante;
	}

	public void setParticipante(RelGrupoParticipanteDTO participante) {
		this.participante = participante;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}

	public GrupoDTO getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public int getNumeroGrupos() {
		return numeroGrupos;
	}

	public void setNumeroGrupos(int numeroGrupos) {
		this.numeroGrupos = numeroGrupos;
	}

	public Integer getIdCursoLms() {
		return idCursoLms;
	}

	public void setIdCursoLms(Integer idCursoLms) {
		this.idCursoLms = idCursoLms;
	}

	/* INICIO DE GETS Y SETS */
	
}
