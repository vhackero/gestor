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
		grupos= new ArrayList<GrupoDTO>();
		setSelectedPlanes(new ArrayList<>());
	}
	
	public void dispersar() {
		System.out.println(">>>>>>>>>>>>> DISPERSAR >>>>>>>>>>>>> ");
		List<PlanDTO> listaPlanesSeleccionados = obtenerListaPlanesByIds();

		List<TblInscripcionResumenDTO> listaInscripcionResumen = obtenerListaInscripcionResumen(
				listaPlanesSeleccionados);

		List<GrupoDTO> listaGruposXEvento = obtenerListaGruposXEvento(listaPlanesSeleccionados,
				listaInscripcionResumen);
		System.out.println("listaGruposPorEvento >>>>>>>>>>>>> " + listaGruposXEvento.size());

		generarDispersion(listaPlanesSeleccionados, listaInscripcionResumen, listaGruposXEvento);

	}
	
	
	public List<GrupoDTO> obtenerListaGruposXEvento(List<PlanDTO> listaPlanesSeleccionados, List<TblInscripcionResumenDTO> listaInscripcionResumen) {		

		List<GrupoDTO> listaGrupos = new ArrayList<GrupoDTO>();

		List<Integer> listaEventos = new ArrayList<Integer>();		
		if (!listaPlanesSeleccionados.isEmpty() && !listaInscripcionResumen.isEmpty()) {
			
			for (PlanDTO plan : listaPlanesSeleccionados) {
				 for( TblInscripcionResumenDTO inscripcionResumen: listaInscripcionResumen) {
//					System.out.println("plan > " + plan.getNombre()+ " - "+ inscripcionResumen.getProgramaEducativo()  );
					if (plan.getNombre().equals(inscripcionResumen.getProgramaEducativo())) {
//						System.out.println(" IdPlan: "+ plan.getIdPlan() +"inscripcionResumen.getAsignatura() "+ inscripcionResumen.getAsignatura() );
						
						List<EventoCapacitacionDTO> eventos = dispersionServiceFacade.getEventoCapacitacionService()
								.obtenerEventosPorProgramaIdPlan(inscripcionResumen.getAsignatura(), plan.getIdPlan());
						
//						System.out.println("lista de eventos >>>>>>>>>>>>> " + eventos.size());  
						Optional.ofNullable(eventos).orElse(Collections.emptyList()).stream()
						.forEach(System.out::println);
			
						if (!eventos.isEmpty()) {
							for (EventoCapacitacionDTO eve:eventos) {								
								listaEventos.add(eve.getIdEvento());
							}
//							System.out.println("Numero de eventos en total>>>>>>> " + listaEventos.size());
//							List<GrupoDTO>  listaGruposAux = dispersionServiceFacade.obtenerGruposPorIdEventos(listaEventos);
//							System.out.println("Numero de Grupos AUX>>>>>>>> " + listaGruposAux.size());
							listaGrupos.addAll(dispersionServiceFacade.obtenerGruposPorIdEventos(listaEventos));
//							listaGrupos.addAll(obtenerGruposxGrupoBase(inscripcionResumen,listaGruposAux));
							System.out.println("Numero de Grupos>>>>>>> listaGrupos " + listaGrupos.size());
							 
						}
					}
				}
			}	
		}
		return listaGrupos;		
	}
	
	public void generarDispersion(List<PlanDTO> listaPlanesSeleccionados, List<TblInscripcionResumenDTO> listaInscripcionResumen, List<GrupoDTO> listaGrupos) {		
		System.out.println("generarDispersion >>>>>>>>>>>>> ");
		
		if (!listaPlanesSeleccionados.isEmpty() && !listaInscripcionResumen.isEmpty()&& !listaGrupos.isEmpty()) {
			
			for (PlanDTO plan : listaPlanesSeleccionados) {
				 for( TblInscripcionResumenDTO inscripcionResumen: listaInscripcionResumen) {
					System.out.println("plan > " + plan.getNombre()+ " - "+ inscripcionResumen.getProgramaEducativo()  );
					if (plan.getNombre().equals(inscripcionResumen.getProgramaEducativo())) {
						System.out.println(" IdPlan: "+ plan.getIdPlan() +" - Asignatura> "+ inscripcionResumen.getAsignatura() );		
						
						 List<GrupoDTO> listaGruposAux = obtenerGruposxGrupoBase(inscripcionResumen,listaGrupos);
							System.out.println("Numero de Grupos AUX>>>>>>>> " + listaGruposAux.size());
						if(!listaGruposAux.isEmpty()) {
							List<TblInscripcionDTO> listaInscripcionesTemp = dispersionServiceFacade.getInscripcionesByIdPlanPrograma(plan.getIdPlan(), inscripcionResumen.getAsignatura());
							System.out.println("listaInscripcionesTemp >>>>>>>>>>>>> " + listaInscripcionesTemp.size());  
							
							List<PersonaDTO> listaPersonas = new ArrayList<PersonaDTO>();
							for (TblInscripcionDTO ins : listaInscripcionesTemp) {
								 listaPersonas.add(ins.getPersonaSige()); 
							}
							System.out.println("listaPersonas >>>>>>>>>>>>> " + listaPersonas.size());
							
							// obtener el tamaño de la lista
						    int size = listaPersonas.size();
						    int n= listaGruposAux.size();
						    System.out.println("size >>>>>>>>>>>>> "+size );
						    System.out.println("n >>>>>>>>>>>>> "+n );
						    // Calcular el número total de particiones `m` de tamaño `n` cada una
						    int m = size / n;
						    System.out.println("m >>>>>>>>>>>>> " + m);
						    if (size % n != 0) {
						        m++;
						    }
						   
						    List<PersonaDTO> listaPersonasAux = new ArrayList<PersonaDTO>();
						    for (int i = 0; i < n; i++)
						    {
						        int fromIndex = i*m;
						        int toIndex = (i*m + m < size) ? (i*m + m) : size;
						        listaPersonasAux = listaPersonas.subList(fromIndex, toIndex);
						        
						        GrupoDTO grupo= listaGruposAux.get(i);
						       
						        ParametroWSMoodleDTO parametroWSMoodleDTO ;
						        AmbienteVirtualAprendizajeDTO ava = dispersionServiceFacade
										.getAmbienteVirtualApService().obtenerAVAPorEvento(grupo.getEvento().getIdEvento());
								if (ObjectUtils.isNotNull(ava)) {
									parametroWSMoodleDTO = ava.getPlataformaMoodle();
									grupo.getEvento().setIdCursoLmsBorrador(ava.getIdCursoLms());
									grupo.getEvento().setIdPlataformaLmsBorrador(parametroWSMoodleDTO.getIdParametroWSMoodle());
								} else {
									parametroWSMoodleDTO = dispersionServiceFacade.getParametroWSMoodleService()
											.buscarPorId(grupo.getEvento().getIdPlataformaLmsBorrador());
								}
						        matricularPersonas(listaPersonasAux, grupo, parametroWSMoodleDTO);
						    }
						}else {

							System.out.println("NO EXISTEN GRUPOS CREADOS PARA GENERAR LA DISPERSION >>>>>>>>>>>>> ");
						}
					}
				}
			}	
		}else {
			System.out.println("NO SE PUEDE GENERAR LA DISPERSION >>>>>>>>>>>>> ");
			System.out.println("listaPlanesSeleccionados> "+ listaPlanesSeleccionados.size());
			System.out.println("listaInscripcionResumen> "+ listaInscripcionResumen.size());
			System.out.println("listaGrupos> "+ listaInscripcionResumen.size());
		} 	
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
				if(nombrePlan.equals(plan.getNombre())) {
					planesSeleccionados.add(plan);
				}
			}
		}
		return planesSeleccionados;
	}
	
	public List<TblInscripcionResumenDTO>  obtenerListaInscripcionResumen(List<PlanDTO> planes) {
		
		List<String> programas = new ArrayList<String>();
		for(PlanDTO plan: planes) {
			programas.add(plan.getNombre());
		}
	 
		listaInscripcionesResumen= dispersionServiceFacade.getInscripcionResumenByProgramaEducativo(programas);
//		System.out.println("obtenerListaInscripcionResumen >>>>>>>>>>>>> "  + programas +" - " + + listaInscripcionesResumen.size());
		return listaInscripcionesResumen;
	}

	
	public void generarGrupos() {

		System.out.println(">>>>>>>>>>>>> GENERAR GRUPOS >>>>>>>>>>>>> ");

		List<PlanDTO> listaPlanesSeleccionados = obtenerListaPlanesByIds();
	
		if (!listaPlanesSeleccionados.isEmpty()) {

			List<TblInscripcionResumenDTO> listaInscripcionResumen = obtenerListaInscripcionResumen(
					listaPlanesSeleccionados);

			for (PlanDTO plan : listaPlanesSeleccionados) {
				for (TblInscripcionResumenDTO inscripcionResumen : listaInscripcionResumen) {
					if (plan.getNombre().equals(inscripcionResumen.getProgramaEducativo())) {
						List<EventoCapacitacionDTO> eventos = dispersionServiceFacade.getEventoCapacitacionService()
								.obtenerEventosPorProgramaIdPlan(inscripcionResumen.getAsignatura(), plan.getIdPlan());


						if (!eventos.isEmpty()) {
							for (EventoCapacitacionDTO eve : eventos) {
								AmbienteVirtualAprendizajeDTO ava = dispersionServiceFacade
										.getAmbienteVirtualApService().obtenerAVAPorEvento(eve.getIdEvento());
								if (ObjectUtils.isNotNull(ava)) {
									parametroWSMoodleDTO = ava.getPlataformaMoodle();
									eve.setIdCursoLmsBorrador(ava.getIdCursoLms());
									eve.setIdPlataformaLmsBorrador(parametroWSMoodleDTO.getIdParametroWSMoodle());
								} else {
									parametroWSMoodleDTO = dispersionServiceFacade.getParametroWSMoodleService()
											.buscarPorId(eve.getIdPlataformaLmsBorrador());
								}
							}

							grupos.addAll(dispersionServiceFacade.getGrupoService().generarGruposDispersion(eventos,
									inscripcionResumen, getUsuarioEnSession().getIdPersona(), parametroWSMoodleDTO));
							// bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_GPO", "",
							// requestActual(), TipoServicioEnum.LOCAL);

						}
					}

				}

			}
		}
	}

	public List<GrupoDTO> obtenerGruposxGrupoBase(TblInscripcionResumenDTO inscripcionResumen, List<GrupoDTO> listaGrupos) {
		System.out.println("::::obtenerGruposxGrupoBase::::"+ inscripcionResumen.getGrupo() + " - "+ listaGrupos.size()); 
	Map<String, List<GrupoDTO>> mapaGrupos= new HashMap<String, List<GrupoDTO>>();
	List<GrupoDTO> gruposByGrupoBase = new ArrayList<GrupoDTO>();
  
		String grupoBase= inscripcionResumen.getGrupo().substring(0, 19);
		for(GrupoDTO grupo: listaGrupos) {
			System.out.println("mapagrupos >>>>>>>>>>>>> " +grupoBase + " - " + grupo.getNombre());
			 if( grupo.getNombre().contains(grupoBase)) {
				 gruposByGrupoBase.add(grupo);
			 }
		}
		System.out.println("resumen.getGrupo() >>>>>>>>>>>>> " +inscripcionResumen.getGrupo() + " - " +gruposByGrupoBase.size() );
		mapaGrupos.put(inscripcionResumen.getGrupo(), gruposByGrupoBase);
	 
	return gruposByGrupoBase;
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
//			System.out.println("resumen.getGrupo() >>>>>>>>>>>>> " +resumen.getGrupo() + " - " +inscripcionesByGrupoBase.size() );
			mapaInscripciones.put(resumen.getGrupo(), inscripcionesByGrupoBase);
		}
		return mapaInscripciones;
	}

	public void matricularPersonas( List<PersonaDTO> listaPersonas, GrupoDTO grupo, ParametroWSMoodleDTO parametroWSMoodleDTO ) {
		System.out.println("matricularPersonas >>>>>>>>>>>>> " +listaPersonas.size()+ " - " +grupo.getNombre() );
		grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		grupo.setFachaActualizacion(new Date());

		if (listaPersonas.isEmpty()) {
			agregarMsgError("No se han seleccionado usuarios.", null, sistema);
			System.out.println("No se han seleccionado usuarios. >>>>>>>>>>>>> " +listaPersonas.size()+ " - " +grupo.getNombre() );
		} else {
			ResultadoDTO<RelGrupoParticipanteDTO> resultado = dispersionServiceFacade
					.getGrupoParticipanteService()
					.almacenarParticipantes(listaPersonas, grupo, grupo.getEvento(), parametroWSMoodleDTO);
			
			System.out.println("resultado >>>>>>>>>>>>> " +resultado.getMensajes().toString() );
			if (resultado.esCorrecto()) {
			 
				// TODO Enviar notificacion y correo a los usuarios matriculados
			/*	String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_MATRICULAR_USUARIOS;
				String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_MATRICULAR_USUARIOS;
				correoNotificacionBean.notificarUsuariosMatriculados(claveNotificacion, claveCorreo, listaPersonas,
						grupo, evento);

				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "MTR_PAR", "", requestActual(),
						TipoServicioEnum.LOCAL);
				participantes = dispersionServiceFacade.getGrupoParticipanteService()
						.getParticipantesByGrupo(grupo.getIdGrupo());
				agregaCantidadAlumnos(grupo.getIdGrupo(), participantes.size());
*/
				for (String mensaje : resultado.getMensajes()) {
					if (mensaje.contains(MensajesSistemaEnum.EC_REGISTRO_MOODLE_ERROR.getId())) {
						agregarMsgError(mensaje, null);
						System.out.println("ERROR >>>>>>>>>>>>> " +MensajesSistemaEnum.EC_REGISTRO_MOODLE_ERROR + " - " + mensaje);
					} else {
						agregarMsgInfo(mensaje, null);
						System.out.println("INFO >>>>>>>>>>>>> " +mensaje );
					}
				}
//				personas.removeAll(listaEliminar);
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
