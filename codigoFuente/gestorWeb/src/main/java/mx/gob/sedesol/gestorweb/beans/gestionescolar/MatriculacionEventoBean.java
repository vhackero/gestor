package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoBusquedaUsuariosEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.AsistenciaFacadeService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean
public class MatriculacionEventoBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MatriculacionEventoBean.class);

	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	@ManagedProperty("#{correoNotificacionBean}")
	private CorreoNotificacionBean correoNotificacionBean;

	private EventoCapacitacionDTO evento;
	private List<GrupoDTO> grupos;
	private GrupoDTO grupo;
	private Integer idGrupo;

	private GrupoDTO grupoEliminar;

	private Integer idCursoLms;
	private ParametroWSMoodleDTO parametroWSMoodleDTO;

	private int numeroGrupos;

	private int tipoBusqueda;

	private PersonaDTO filtrosPersona;
	private LoteCargaUsuarioDTO filtrosLote;

	private List<LoteCargaUsuarioDTO> lotes;

	private List<PersonaDatosDTO> personas;
	private PersonaDatosDTO persona;

	private boolean todasPersonas;
	private boolean todosAlumnos;

	private List<RelGrupoParticipanteDTO> participantes;
	private RelGrupoParticipanteDTO participante;

	private List<ReponsableProduccionEcDTO> facilitadores;
	private Integer idFacilitador;

	@ManagedProperty(value = "#{asistenciaFacadeService}")
	private AsistenciaFacadeService asistenciaFacadeService;

	@ManagedProperty("#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	public TipoBusquedaUsuariosEnum[] getTiposBusquedaUsuarios() {
		return TipoBusquedaUsuariosEnum.values();
	}

	public String cargarGrupos() {
		numeroGrupos = 1;
		tipoBusqueda = 1;
		idGrupo = null;
		personas = new ArrayList<>();
		filtrosLote = new LoteCargaUsuarioDTO();
		filtrosPersona = new PersonaDTO();
		grupos = eventoCapacitacionServiceFacade.getGrupoService().getGruposByEvento(evento.getIdEvento());
		facilitadores = eventoCapacitacionServiceFacade.getRelReponsableProduccionEcService()
				.getResponsableDelEvento(evento.getIdEvento(), ConstantesGestor.TIPO_RESPONSABILIDAD_FACILITADOR);
		contarAlumnosEnGrupos(grupos);

		List<ReponsableProduccionEcDTO> respProd = getAsistenciaFacadeService().getRelReponsableProduccionEcService()
				.getResponsableDelEvento(evento.getIdEvento());
		if (!ObjectUtils.isNullOrEmpty(respProd)) {
			evento.setResponsableCoordinadorAcademico(getAsistenciaFacadeService().getRelReponsableProduccionEcService()
					.getResponsableDelEvento(evento.getIdEvento()).get(0));
		}

		if (evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
				|| evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO) {
			AmbienteVirtualAprendizajeDTO ava = eventoCapacitacionServiceFacade.getAmbienteVirtualApService()
					.obtenerAVAPorEvento(evento.getIdEvento());
			if (ObjectUtils.isNotNull(ava)) {
				idCursoLms = ava.getIdCursoLms();
				parametroWSMoodleDTO = ava.getPlataformaMoodle();
				evento.setIdCursoLmsBorrador(idCursoLms);
				evento.setIdPlataformaLmsBorrador(parametroWSMoodleDTO.getIdParametroWSMoodle());
			} else {
				idCursoLms = evento.getIdCursoLmsBorrador();
				parametroWSMoodleDTO = eventoCapacitacionServiceFacade.getParametroWSMoodleService()
						.buscarPorId(evento.getIdPlataformaLmsBorrador());
			}
		}
		return ConstantesGestorWeb.NAVEGA_MATRICULACION_EVENTO;
	}

	public void cambioTipoBusqueda() {
		personas = new ArrayList<>();
	}

	/***
	 * Despues de eliminar un grupo se ejecuta el metodo
	 */
	public void cargaGruposDespuesDeEliminar() {
		grupos = eventoCapacitacionServiceFacade.getGrupoService().getGruposByEvento(evento.getIdEvento());
		contarAlumnosEnGrupos(grupos);

	}

	public void eliminarGrupo() {
		participantes = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
				.getParticipantesByGrupo(grupoEliminar.getIdGrupo());
		if (participantes.isEmpty()) {
			grupoEliminar.setEvento(evento);
			ResultadoDTO<GrupoDTO> resultado = eventoCapacitacionServiceFacade.getGrupoService().eliminarGrupo(
					grupoEliminar, getUsuarioEnSession().getIdPersona(), evento.getIdPlataformaLmsBorrador());
			Integer idGrupoEliminar = grupoEliminar.getIdGrupo();
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ELI_GPO", String.valueOf(idGrupoEliminar),
						requestActual(), TipoServicioEnum.LOCAL);
				grupos.remove(grupoEliminar);
				grupo = null;
				grupoEliminar = null;
				idGrupo = null;
				personas = new ArrayList<>();
				if (ObjectUtils.isNotNull(resultado.getMensajes())) {
					agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
				}
				cargaGruposDespuesDeEliminar();
			} else {
				if (ObjectUtils.isNotNull(resultado.getMensajes())) {
					agregarMsgError(resultado.getMensajes(), null, sistema);
				}
			}
		} else {
			agregarMsgError("El grupo tiene alumnos.", null, sistema);
		}
	}

	public void generarGrupos() {

		grupos.addAll(eventoCapacitacionServiceFacade.getGrupoService().generarGrupos(evento, numeroGrupos,
				getUsuarioEnSession().getIdPersona(), parametroWSMoodleDTO));
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_GPO", "", requestActual(), TipoServicioEnum.LOCAL);

		contarAlumnosEnGrupos(grupos);

	}

	public void contarAlumnosEnGrupos(List<GrupoDTO> grupos) {
		for (GrupoDTO grupoDTO : grupos) {
			grupoDTO.setNumAlumnosMatriculados(eventoCapacitacionServiceFacade.getGrupoParticipanteService()
					.getParticipantesByGrupo(grupoDTO.getIdGrupo()).size());
		}
	}

	public void seleccionarGrupo(ValueChangeEvent e) {
		todosAlumnos = false;
		personas = new ArrayList<>();
		lotes = new ArrayList<>();
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (GrupoDTO grupoDTO : grupos) {
				if (grupoDTO.getIdGrupo().equals(e.getNewValue())) {
					grupo = grupoDTO;
					if (ObjectUtils.isNull(grupo.getFacilitador())) {
						idFacilitador = null;
					} else {
						idFacilitador = grupo.getFacilitador().getId();
					}
					idGrupo = (Integer) e.getNewValue();
					personas = new ArrayList<>();
					participantes = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
							.getParticipantesByGrupo(idGrupo);
				}
			}
		}
	}

	public void buscarPersonas() {
		try {
			todasPersonas = false;
			List<PersonaDatosDTO> lista = eventoCapacitacionServiceFacade.getPersonaService()
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
				if (noExiste && existeUsuarioEnEvento(grupo.getEvento().getIdEvento(),datos.getPersona().getIdPersona())) {
					personas.add(datos);
				}
			}
		} catch (NullPointerException e) {
			agregarMsgInfo("Se debe seleccionar un grupo.", null);
			logger.info("No se selecciono un grupo. " + e);
		}
	}

	/**
	 * Si existe el usuario en el evento retorna false
	 * @param idEvento
	 * @param idPersona
	 * @return
	 */
	public boolean existeUsuarioEnEvento(Integer idEvento, Long idPersona) {
		List<RelGrupoParticipanteDTO> rgp = grupoParticipanteService.buscarParticipanteEnEvento(idEvento, idPersona);
		if (!rgp.isEmpty()) {
			return false;
		}else{
			return true;
		}
	}

	public void buscarLotes() {
		lotes = eventoCapacitacionServiceFacade.getLoteCargaUsuarioService().buscarPorCriterios(filtrosLote);
	}

	public void cargarPersonasLote(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idLote = Integer.parseInt((String) e.getNewValue());
			if (idLote != 0) {
				personas = new ArrayList<>();
				List<UsuarioDatosLaboralesDTO> usuarios = eventoCapacitacionServiceFacade.getLoteUsuarioService()
						.obtenerUsuarioPorLote(idLote);
				for (UsuarioDatosLaboralesDTO usuario : usuarios) {
					PersonaDatosDTO persona = new PersonaDatosDTO();
					persona.setPersona(usuario.getPersona());
					persona.setInstitucion(usuario.getInstitucion());
					persona.setAreaAdscripcion(usuario.getAreaAdscripcion());
					boolean noExiste = true;
					for (RelGrupoParticipanteDTO alumno : participantes) {
						if (alumno.getPersona().getIdPersona().equals(persona.getPersona().getIdPersona())) {
							noExiste = false;
							break;
						}
					}
					if (noExiste) {
						personas.add(persona);
					}
				}

			} else {
				personas = new ArrayList<>();
			}

		}
	}

	public void seleccionarTodasPersonas(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			todasPersonas = (Boolean) e.getNewValue();
			for (PersonaDatosDTO elemento : personas) {
				elemento.setSeleccionado(todasPersonas);
			}
		}
	}

	public void matricularPersonas() {
		todasPersonas = false;
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

			ResultadoDTO<RelGrupoParticipanteDTO> resultado = eventoCapacitacionServiceFacade
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
				participantes = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
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

	public void matricularIndividual() {
		grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		grupo.setFachaActualizacion(new Date());
		RelGrupoParticipanteDTO alumno = eventoCapacitacionServiceFacade.almacenarParticipante(grupo,
				persona.getPersona(), evento, parametroWSMoodleDTO);
		if (ObjectUtils.isNull(alumno)) {
			agregarMsgError("Error al matricular.", null, sistema);
		} else {
			// TODO Enviar notificacion y correo al usuario matriculado
			String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_MATRICULAR_USUARIOS;
			String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_MATRICULAR_USUARIOS;
			PersonaDTO participante = alumno.getPersona();
			correoNotificacionBean.notificarUsuarioMatriculado(claveNotificacion, claveCorreo, participante, grupo,
					evento);

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "MTR_PAR", "", requestActual(), TipoServicioEnum.LOCAL);
			alumno.setInstitucion(persona.getInstitucion());
			alumno.setAreaAdscripcion(persona.getAreaAdscripcion());
			participantes.add(alumno);
			personas.remove(persona);
			agregaCantidadAlumnos(grupo.getIdGrupo(), participantes.size());
			agregarMsgInfo("Matriculación exitosa.", null, sistema);
		}
	}

	public void seleccionarTodosAlumnos(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			todosAlumnos = (Boolean) e.getNewValue();
			for (RelGrupoParticipanteDTO elemento : participantes) {
				elemento.setSeleccionado(todosAlumnos);
			}
		}
	}

	public void darBajaPersonas() {
		todosAlumnos = false;
		boolean algunaSeleccion = false;
		List<RelGrupoParticipanteDTO> listaEliminar = new ArrayList<>();
		for (RelGrupoParticipanteDTO alumno : participantes) {
			if (alumno.isSeleccionado()) {
				algunaSeleccion = true;
				alumno.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

				ResultadoDTO<RelGrupoParticipanteDTO> resultado = eventoCapacitacionServiceFacade
						.getGrupoParticipanteService().eliminarParticipante(alumno, evento);
				if (resultado.esCorrecto()) {
					listaEliminar.add(alumno);
				}
			}
		}
		participantes.removeAll(listaEliminar);
		if (algunaSeleccion) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DES_MTR_PAR", "", requestActual(),
					TipoServicioEnum.LOCAL);
			agregarMsgInfo("Baja exitosa.", null, sistema);
			agregaCantidadAlumnos(grupo.getIdGrupo(), participantes.size());
			grupo.setFachaActualizacion(new Date());
			grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			eventoCapacitacionServiceFacade.getGrupoService().actualizarGrupo(grupo,
					getUsuarioEnSession().getIdPersona());
		} else {
			agregarMsgError("No existe selección.", null, sistema);
		}
	}

	public void bajaIndividual() {
		participante.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

		ResultadoDTO<RelGrupoParticipanteDTO> resultado = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
				.eliminarParticipante(participante, evento);
		if (resultado.esCorrecto()) {
			participantes.remove(participante);
			grupo.setFachaActualizacion(new Date());
			grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			eventoCapacitacionServiceFacade.getGrupoService().actualizarGrupo(grupo,
					getUsuarioEnSession().getIdPersona());
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DES_MTR_PAR", "", requestActual(),
					TipoServicioEnum.LOCAL);
			agregarMsgInfo("Baja exitosa.", null, sistema);
			agregaCantidadAlumnos(grupo.getIdGrupo(), participantes.size());
		}
	}

	public void onChangeFacilitador(ValueChangeEvent e) {
		if (ObjectUtils.isNull(e.getNewValue())) {
			grupo.setFacilitador(null);
			grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			grupo.setFachaActualizacion(new Date());
			eventoCapacitacionServiceFacade.getGrupoService().actualizarGrupo(grupo,
					getUsuarioEnSession().getIdPersona());
		} else {
			PersonaResponsabilidadesDTO facilitador = new PersonaResponsabilidadesDTO();
			idFacilitador = (Integer) e.getNewValue();
			facilitador.setId(idFacilitador);
			grupo.setFacilitador(facilitador);
			grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			grupo.setFachaActualizacion(new Date());
			eventoCapacitacionServiceFacade.getGrupoService().establecerFacilitador(grupo, evento,
					parametroWSMoodleDTO);
		}
	}

	/* INICIO DE GETS Y SETS */
	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public List<GrupoDTO> getGrupos() {
		return grupos;
	}

	public GrupoDTO getGrupo() {
		return grupo;
	}

	public int getNumeroGrupos() {
		return numeroGrupos;
	}

	public int getTipoBusqueda() {
		return tipoBusqueda;
	}

	public PersonaDTO getFiltrosPersona() {
		return filtrosPersona;
	}

	public LoteCargaUsuarioDTO getFiltrosLote() {
		return filtrosLote;
	}

	public void setFiltrosLote(LoteCargaUsuarioDTO filtrosLote) {
		this.filtrosLote = filtrosLote;
	}

	public List<PersonaDatosDTO> getPersonas() {
		return personas;
	}

	public PersonaDatosDTO getPersona() {
		return persona;
	}

	public List<RelGrupoParticipanteDTO> getParticipantes() {
		return participantes;
	}

	public RelGrupoParticipanteDTO getParticipante() {
		return participante;
	}

	public List<ReponsableProduccionEcDTO> getFacilitadores() {
		return facilitadores;
	}

	public Integer getIdFacilitador() {
		return idFacilitador;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}

	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}

	public void setNumeroGrupos(int numeroGrupos) {
		this.numeroGrupos = numeroGrupos;
	}

	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public void setFiltrosPersona(PersonaDTO filtrosPersona) {
		this.filtrosPersona = filtrosPersona;
	}

	public void setPersonas(List<PersonaDatosDTO> personas) {
		this.personas = personas;
	}

	public void setPersona(PersonaDatosDTO persona) {
		this.persona = persona;
	}

	public void setParticipantes(List<RelGrupoParticipanteDTO> participantes) {
		this.participantes = participantes;
	}

	public void setParticipante(RelGrupoParticipanteDTO participante) {
		this.participante = participante;
	}

	public void setFacilitadores(List<ReponsableProduccionEcDTO> facilitadores) {
		this.facilitadores = facilitadores;
	}

	public void setIdFacilitador(Integer idFacilitador) {
		this.idFacilitador = idFacilitador;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public boolean isTodasPersonas() {
		return todasPersonas;
	}

	public boolean isTodosAlumnos() {
		return todosAlumnos;
	}

	public void setTodasPersonas(boolean todasPersonas) {
		this.todasPersonas = todasPersonas;
	}

	public void setTodosAlumnos(boolean todosAlumnos) {
		this.todosAlumnos = todosAlumnos;
	}

	public List<LoteCargaUsuarioDTO> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteCargaUsuarioDTO> lotes) {
		this.lotes = lotes;
	}

	public GrupoDTO getGrupoEliminar() {
		return grupoEliminar;
	}

	public void setGrupoEliminar(GrupoDTO grupoEliminar) {
		this.grupoEliminar = grupoEliminar;
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

	public AsistenciaFacadeService getAsistenciaFacadeService() {
		return asistenciaFacadeService;
	}

	public void setAsistenciaFacadeService(AsistenciaFacadeService asistenciaFacadeService) {
		this.asistenciaFacadeService = asistenciaFacadeService;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}
}
