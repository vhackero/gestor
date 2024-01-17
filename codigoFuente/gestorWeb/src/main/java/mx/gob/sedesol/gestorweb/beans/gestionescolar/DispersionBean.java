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
	private List<String> selectedPlanes;
	private List<Integer> selectedIdPlanes;
	
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
		setSelectedPlanes(new ArrayList<>());
	}
	
	public void dispersar() {
		System.out.println("planesSelec >>>>>>>>>>>>> ");
	    Optional.ofNullable(selectedPlanes).orElse(Collections.emptyList()).stream()
         .forEach(System.out::println);
	   
	    List<PlanDTO> listaPlanesSeleccionados= obtenerListaPlanesByIds();
	    Optional.ofNullable(listaPlanesSeleccionados).orElse(Collections.emptyList()).stream()
        .forEach(System.out::println);
	 
	  System.out.println("listaInscripcionesResumen >>>>>>>>>>>>> ");
	    List<TblInscripcionResumenDTO> lista=   obtenerListaInscripcionResumen(listaPlanesSeleccionados);
	    Optional.ofNullable(lista).orElse(Collections.emptyList()).stream()
        .forEach(System.out::println);
	
	  System.out.println("listaInscripciones >>>>>>>>>>>>> ");
	   List<TblInscripcionDTO> inscrip=   obtenerListaInscripcionesByPLanes(listaPlanesSeleccionados);
	  /* Optional.ofNullable(inscrip).orElse(Collections.emptyList()).stream()
        .forEach(System.out::println);*/
	   /* System.out.println("listaInscripciones >>>>>>>>>>>>> "+inscrip.size());
	   generarGrupos(lista);*/
	 //  crearMapaInscripcionesXGrupo(lista,inscrip, grupos);
	}
	
	public void planesSelectChange() {		
		if (!selectedPlanes.isEmpty()) {			
			System.out.println("planes seleccionadas : " + selectedPlanes.size());			
		} else {
			System.out.println("seleccionar planes");
		}
	}

	public List<PlanDTO> obtenerListaPlanesByIds() {
		List<PlanDTO> planesSeleccionados = new ArrayList<PlanDTO>();

		for (String nombrePlan : selectedPlanes) {
			for (PlanDTO plan : listaPlanes) {
				System.out.println("nombrePlan - " +nombrePlan + " - " + plan.getNombre());
				if(nombrePlan.equals(plan.getNombre())) {
					planesSeleccionados.add(plan);
				}
			}
		}
		System.out.println("obtenerListaPlanesByIds >>>>>>>>>>>>> "+ planesSeleccionados.size());

		//return planService.obtenerPlanesPorIds(selectedIdPlanes);
		return planesSeleccionados;
	}
	
	public List<TblInscripcionResumenDTO>  obtenerListaInscripcionResumen(List<PlanDTO> planes) {
		
		List<String> programas = new ArrayList<String>();
		for(PlanDTO plan: planes) {
			programas.add(plan.getNombre());
		}
	 
		listaInscripcionesResumen= dispersionServiceFacade.getInscripcionResumenByProgramaEducativo(programas);
		System.out.println("obtenerListaInscripcionResumen >>>>>>>>>>>>> "  + programas +" - " + + listaInscripcionesResumen.size());
		return listaInscripcionesResumen;
	}
	
	public List<TblInscripcionDTO>  obtenerListaInscripcionesByPLanes(List<PlanDTO> planes) {
	//	System.out.println("obtenerListaInscripcionesByPLanes >>>>>>>>>>>>> " + programas);
		List<Integer> idPlanes = new ArrayList<Integer>();
		for(PlanDTO plan: planes) {
			idPlanes.add(plan.getIdPlan());
		}
		listaInscripciones= dispersionServiceFacade.getInscripcionesByIdPlanes(idPlanes);
		
		System.out.println("obtenerListaInscripcionesByPLanes >>>>>>>>>>>>> " + idPlanes.size() +" - " + listaInscripciones.size());
		
		return listaInscripciones;
	}


	public void generarGrupos() {

		System.out.println("generarGrupos >>>>>>>>>>>>> ");

		List<PlanDTO> listaPlanesSeleccionados = obtenerListaPlanesByIds();
		Optional.ofNullable(listaPlanesSeleccionados).orElse(Collections.emptyList()).stream()
				.forEach(System.out::println);

		if (!listaPlanesSeleccionados.isEmpty()) {

			System.out.println("listaInscripcionesResumen >>>>>>>>>>>>> ");
			List<TblInscripcionResumenDTO> listaInscripcionResumen = obtenerListaInscripcionResumen(listaPlanesSeleccionados);
			Optional.ofNullable(listaInscripcionResumen).orElse(Collections.emptyList()).stream().forEach(System.out::println);
			
			for (PlanDTO plan : listaPlanesSeleccionados) {
			//	System.out.println("plan > " + plan.getNombre() );
				 for( TblInscripcionResumenDTO inscripcionResumen: listaInscripcionResumen) {
					System.out.println("plan > " + plan.getNombre()+ " - "+ inscripcionResumen.getProgramaEducativo()  );
					if (plan.getNombre().equals(inscripcionResumen.getProgramaEducativo())) {
						System.out.println("Es el mismo programa educativo" );
						List<EventoCapacitacionDTO> eventos = dispersionServiceFacade.getEventoCapacitacionService()
								.obtenerEventosPorProgramaIdPlan(inscripcionResumen.getAsignatura(), plan.getIdPlan());
						
						System.out.println("lista de eventos >>>>>>>>>>>>> " + eventos.size());  
						Optional.ofNullable(eventos).orElse(Collections.emptyList()).stream()
						.forEach(System.out::println);
						
						Optional.ofNullable(eventos).orElse(Collections.emptyList()).stream().forEach(System.out::println);
						if (!eventos.isEmpty()) {
							//evento = eventos.get(0);
							for (EventoCapacitacionDTO eve:eventos) {
								AmbienteVirtualAprendizajeDTO ava = dispersionServiceFacade.getAmbienteVirtualApService()
										.obtenerAVAPorEvento(eve.getIdEvento());
								if (ObjectUtils.isNotNull(ava)) {
									// idCursoLms = ava.getIdCursoLms();
									parametroWSMoodleDTO = ava.getPlataformaMoodle();
									eve.setIdCursoLmsBorrador(ava.getIdCursoLms());
									eve.setIdPlataformaLmsBorrador(parametroWSMoodleDTO.getIdParametroWSMoodle());
								} else {
								//	idCursoLms = eve.getIdCursoLmsBorrador();
									parametroWSMoodleDTO = dispersionServiceFacade.getParametroWSMoodleService()
											.buscarPorId(eve.getIdPlataformaLmsBorrador());
								}
							}
							
							// crearLogica para crear Mapa de grupos AQUI
							grupos.addAll(dispersionServiceFacade.getGrupoService().generarGruposDispersion(eventos,
									inscripcionResumen, getUsuarioEnSession().getIdPersona(), parametroWSMoodleDTO));
							// bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_GPO", "",
							// requestActual(), TipoServicioEnum.LOCAL);
							 
						}
					}
					
				}

			}
		}

		//System.out.println("generarGrupos >>>>>>>>>>>>> " + grupos.size());
		// contarAlumnosEnGrupos(grupos);

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
		Map<String, List<TblInscripcionDTO>> mapaInscripciones= obtenerMapaIncripcionesxGrupoBase(listaInscripcionResumen,listaInscripciones);
		Map<String, List<GrupoDTO>> mapaGrupos= obtenerGruposxGrupoBase(listaInscripcionResumen);
		Map<String, Map<String,Integer>> mapaNoEstudiantesEnGrupo= obtenerNoEstudiantesEnGruposxGrupoBase(listaInscripcionResumen, mapaGrupos);
		/*for(TblInscripcionResumenDTO resumen: listaInscripcionResumen) {
			List<TblInscripcionDTO> inscripcionesByGrupoBase = new ArrayList<TblInscripcionDTO>();
			for(TblInscripcionDTO ins: listaInscripciones) {
				 if(resumen.getGrupo().equals(ins.getGroupbase())) {
					 inscripcionesByGrupoBase.add(ins);
				 }
			}
			System.out.println("resumen.getGrupo() >>>>>>>>>>>>> " +resumen.getGrupo() + " - " +inscripcionesByGrupoBase.size() );
			mapaInscripciones.put(resumen.getGrupo(), inscripcionesByGrupoBase);
		}*/
		
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
			   /* Map<String, List<GrupoDTO>> mapaGrupos= new HashMap<String, List<GrupoDTO>>();
				for(GrupoDTO group: listaGrupos) {
					List<GrupoDTO> gruposByGrupoBase = new ArrayList<GrupoDTO>();
					System.out.println("mapagrupos >>>>>>>>>>>>> " +grupoBase.substring(0, 19) + " - " +group.getNombre().substring(0, 19) );
						 if( grupoBase.substring(0, 19).equals(group.getNombre().substring(0, 19))) {
							 gruposByGrupoBase.add(group);
						 }
					 
					System.out.println("mapaGrupos >>>>>>>>>>>>> " +grupoBase + " - " +gruposByGrupoBase.size() );
					mapaGrupos.put(grupoBase, gruposByGrupoBase);
				}*/
			    
			    if(resumen!=null /*&& mapaGrupos !=null */) {
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

	public Map<String, List<GrupoDTO>> obtenerGruposxGrupoBase(List<TblInscripcionResumenDTO> listaInscripcionResumen) {
		Map<String, List<GrupoDTO>> mapaGrupos= new HashMap<String, List<GrupoDTO>>();
		for(TblInscripcionResumenDTO resumen: listaInscripcionResumen) {
			List<GrupoDTO> gruposByGrupoBase = new ArrayList<GrupoDTO>();
			String grupoBase= resumen.getGrupo().substring(0, 19);
			for(GrupoDTO grupo: grupos) {
				String grupoActual=grupo.getNombre().substring(0, 19);
				System.out.println("mapagrupos >>>>>>>>>>>>> " +grupoBase + " - " + grupoActual);
				 if( grupoBase.equals(grupoActual)) {
					 gruposByGrupoBase.add(grupo);
				 }
			}
			System.out.println("resumen.getGrupo() >>>>>>>>>>>>> " +resumen.getGrupo() + " - " +gruposByGrupoBase.size() );
			mapaGrupos.put(resumen.getGrupo(), gruposByGrupoBase);
		}
		return mapaGrupos;
	}
	
	public Map<String, List<TblInscripcionDTO>> obtenerMapaIncripcionesxGrupoBase(List<TblInscripcionResumenDTO> listaInscripcionResumen,
			List<TblInscripcionDTO> listaInscripciones) {
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
		return mapaInscripciones;
	}
	
	public Map<String, Map<String,Integer>> obtenerNoEstudiantesEnGruposxGrupoBase(List<TblInscripcionResumenDTO> listaInscripcionResumen, Map<String, List<GrupoDTO>> mapaGrupos) {
		Map<String, Map<String,Integer>> mapa= new HashMap<String, Map<String,Integer>>();
		for(TblInscripcionResumenDTO resumen: listaInscripcionResumen) {
			Map<String,Integer> noAlumnosXGrupo = new HashMap<String,Integer>();
			List<GrupoDTO> gruposDTOs = mapaGrupos.get(resumen.getGrupo());
			 
			for(int i=0; i<resumen.getNoGrupos();i++) {
				noAlumnosXGrupo.put(gruposDTOs.get(i).getNombre(), resumen.getEstudiantesXGrupo()); 
			}
			noAlumnosXGrupo.put(gruposDTOs.get(gruposDTOs.size()).getNombre(), resumen.getEstudiantesResto()); 
			System.out.println("No De alumnos por grupo >>>>>>>>>>>>> " );
			noAlumnosXGrupo.forEach((key, value) -> System.out.println(key + "- " + value));
			
			System.out.println("resumen.getGrupo() >>>>>>>>>>>>> " +resumen.getGrupo() + " - " +noAlumnosXGrupo.size());
			mapa.put(resumen.getGrupo(), noAlumnosXGrupo);
		}
		return mapa;
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

	public List<String> getSelectedPlanes() {
		return selectedPlanes;
	}

	public void setSelectedPlanes(List<String> selectedPlanes) {
		this.selectedPlanes = selectedPlanes;
	}

	public List<Integer> getSelectedIdPlanes() {
		return selectedIdPlanes;
	}

	public void setSelectedIdPlanes(List<Integer> selectedIdPlanes) {
		this.selectedIdPlanes = selectedIdPlanes;
	}
	
	/* INICIO DE GETS Y SETS */
	
}
