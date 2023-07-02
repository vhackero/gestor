package mx.gob.sedesol.gestorweb.beans.administracion.publico;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Email;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.utils.CorreoUtil;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.Curso;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;

@ManagedBean
@ViewScoped
public class CursosBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<Curso> cursosTodos;
	private List<Curso> cursosPublicoGeneral;
	private String filtroCursos;

	private static final Logger logger = Logger.getLogger(CursosBean.class);

	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@ManagedProperty(value = "#{correoElectronicoService}")
	private transient CorreoElectronicoService correoElectronicoService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{encuestaService}")
	private transient EncuestaService encuestaService;

	private String nombreInteresado;
	@Email(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_EMAIL)
	private String correoInteresado;
	private String mensajeInteresado;
	private Curso cursoSelec;
	private Curso seleccionCurso;

	private List<CatalogoComunDTO> listaTiposCompetencias;
	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> ejesCapacitacion;

	private EventoCapacitacionDTO evento;

	private String ejeCapacitacion;
	private String tipoCompetencia;
	private String modalidad;
	private String nivelEnsenanza;
	private String cargaHoraria;

	@PostConstruct
	public void init() {
		ejeCapacitacion = "";
		tipoCompetencia = "";
		modalidad = "";
		nivelEnsenanza = "";
		cargaHoraria = "";

		cursosTodos = new LinkedList<Curso>();

		List<EventoCapacitacionDTO> eventosPublicos = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
				.obtenerEventosPublicosEnEjec();

		if (!ObjectUtils.isNullOrEmpty(eventosPublicos)) {
			int contador = 0;
			for (EventoCapacitacionDTO ec : eventosPublicos) {

				cursosTodos.add(new Curso(this.getCorreoCordinadorAcad(ec.getReponsableProduccionEcs()),
						ObjectUtils.isNullOrEmpty(ec.getUrlImagen())
								? eventoCapacitacionServiceFacade.obtenerRutaUndertow()
										.concat(eventoCapacitacionServiceFacade.obtenerNombreImagenComun())
								: eventoCapacitacionServiceFacade.obtenerRutaUndertow().concat(ec.getUrlImagen()),
						ec.getNombreEc(), ec.getObjetivoGeneralEc(),
						ObjectUtils.isNotNull(ec.getFichaDescriptivaPrograma().getCatNivelConocimiento())
								? ec.getFichaDescriptivaPrograma().getCatNivelConocimiento().getNombre()
								: StringUtils.EMPTY,
						encuestaService.calificacionPrograma(ec.getFichaDescriptivaPrograma().getIdPrograma())
								.intValue(),
						DateUtils.restaFechasEnDiasMes(ec.getFechaInicial(), ec.getFechaFinal()).toString()));
				cursosTodos.get(contador).setEvento(ec);
				contador++;
			}
		}
		cursosPublicoGeneral = cursosTodos;
		this.generaEstructuraCatTpoCompetenciaPlan();
		this.generaCatEjesCapacitBusqueda();
	}

	private void generaEstructuraCatTpoCompetenciaPlan() {

		MallaCurricularDTO mallaSedesol = eventoCapacitacionServiceFacade.obtenerMallaCurricular();

		if (ObjectUtils.isNotNull(mallaSedesol)) {
			List<NodoeHijosDTO> planes = new ArrayList<>();
			List<MallaCurricularDTO> mallas = new ArrayList<>();
			mallas.add(mallaSedesol);

			for (MallaCurricularDTO m : mallas) {
				NodoeHijosDTO nodog = new NodoeHijosDTO();
				nodog.setNombre(m.getNombre());
				nodog.setIdNodo(m.getId());
				nodog.setIdPadre(m.getMallaCurricularPadre() != null ? m.getMallaCurricularPadre().getId() : null);
				nodog.setIdObjCurr(m.getObjetoCurricular().getId());
				nodog.setNivel(0);

				if (!m.getLstHijosMallaCurr().isEmpty()) {
					this.generaCatxNivel(m.getLstHijosMallaCurr(), nodog, nodog.getNivel());
				}

				planes.add(nodog);
			}

			setListaTiposCompetencias(new ArrayList<>());

			// Genera el Catalogo Tipo de Competencia
			setEstPlanSedesol(planes.get(ConstantesGestorWeb.INDICE_INICIAL));
			for (NodoeHijosDTO nh : getEstPlanSedesol().getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nh.getIdNodo());
				cc.setNombre(nh.getNombre());
				getListaTiposCompetencias().add(cc);
			}
		}

	}

	/**
	 * Metodo que sirve para extraer los ejes de capacitacion
	 */
	private void generaCatEjesCapacitBusqueda() {
		if (ObjectUtils.isNotNull(getEstPlanSedesol())) {
			setEjesCapacitacion(new ArrayList<>());
			for (NodoeHijosDTO nh : getEstPlanSedesol().getNodosHijos()) {
				for (NodoeHijosDTO nint : nh.getNodosHijos()) {
					CatalogoComunDTO cc = new CatalogoComunDTO();
					cc.setId(nint.getIdNodo());
					cc.setNombre(nint.getNombre());
					getEjesCapacitacion().add(cc);
				}
			}
		}

	}

	public void obtenerDatosEvento() {

		List<RelProgDuracionDTO> relProgDuracion = new ArrayList<>();

		evento = cursoSelec.getEvento();
		ejeCapacitacion = obtieneNombreEjeCapacit(evento.getFichaDescriptivaPrograma().getEjeCapacitacion());
		tipoCompetencia = obtieneNombreTpoCompetencia(evento.getFichaDescriptivaPrograma().getTipoCompetencia());

		if (ObjectUtils.isNotNull(evento.getCatModalidadPlanPrograma())) {
			modalidad = evento.getCatModalidadPlanPrograma().getNombre();
		}

		if (ObjectUtils.isNotNull(evento.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma())) {
			nivelEnsenanza = evento.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma().getNombre();
		}

		if (ObjectUtils.isNotNull(evento.getFichaDescriptivaPrograma().getRelProgramaDuracion())) {
			relProgDuracion = evento.getFichaDescriptivaPrograma().getRelProgramaDuracion();
			for (RelProgDuracionDTO relProgDuracionDTO : relProgDuracion) {
				cargaHoraria = "Tipo: " + relProgDuracionDTO.getCatTpoCargaHoraria().getNombre() + "\nHoras: "
						+ relProgDuracionDTO.getHoras() + "\nMinutos: " + relProgDuracionDTO.getMinutos() + "\n";

			}
		}

	}

	/**
	 * Metodo que obtiene el nombre de un tipo de competencia apartir de su id
	 */
	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {

		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : getListaTiposCompetencias()) {
				if (tpoCom.getId().equals(idTpoComp)) {
					return tpoCom.getNombre();
				}
			}
		}
		return null;
	}

	/**
	 * Metodo que obtienen el nombre de un eje de capacitacion apartir de su id
	 */
	public String obtieneNombreEjeCapacit(Integer idEjeCap) {
		if (ObjectUtils.isNotNull(idEjeCap)) {
			for (CatalogoComunDTO ejeCap : getEjesCapacitacion()) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0) {
					return ejeCap.getNombre();
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param responsables
	 * @return
	 */
	private String getCorreoCordinadorAcad(List<ReponsableProduccionEcDTO> responsables) {
		if (!ObjectUtils.isNullOrEmpty(responsables)) {
			for (ReponsableProduccionEcDTO rp : responsables) {
				if (rp.getEsResponsablePrincipal()) {
					List<PersonaCorreoDTO> correos = rp.getPersonaResponsabilidad().getTblPersona().getPersonaCorreos();
					if (!ObjectUtils.isNullOrEmpty(correos)) {
						for (PersonaCorreoDTO c : correos) {
							if (c.getTipoCorreo().getIdTipoCorreo().equals(1)) {
								return c.getCorreoElectronico();
							}
						}
					}
				}
			}
		}
		return StringUtils.EMPTY;
	}

	public void filtarCursos() {
		if (this.filtroCursos == null || this.filtroCursos.equals("")) {
			cursosPublicoGeneral = cursosTodos;
		} else {
			// cursosPublicoGeneral = new LinkedList<Curso>();
			// cursosPublicoGeneral.add(cursosTodos.get(0));
			cursosPublicoGeneral = cursosTodos.parallelStream().filter(c -> c.hayCioncidencia(this.filtroCursos))
					.collect(Collectors.toList());
		}
	}

	public void asignaCursoSeleccionado(Curso curso) {
		this.cursoSelec = curso;
		// cursoSelec = (Curso)evt.getComponent().getAttributes().get("curso");

		logger.info("Info: " + cursoSelec.getNombreCurso());
		obtenerDatosEvento();

		// RequestContext.getCurrentInstance().update("form:panel");
		// RequestContext.getCurrentInstance().execute("PF('dlgInteresado').show()");
	}

	/**
	 *
	 * @param curso
	 */
	public void registroEnCurso() {
		if (ObjectUtils.isNotNull(this.cursoSelec)) {

			CorreoDTO correoDto = correoElectronicoService.asignaParametrosConfigCorreo();
			correoDto.setDestinatarios(new ArrayList<String>());
			correoDto.setAsunto(nombreInteresado + "Esta interesado en el curso " + this.cursoSelec.getNombreCurso());
			correoDto.setTitulo(nombreInteresado + "Esta interesado en el curso " + this.cursoSelec.getNombreCurso());
			correoDto.setRemitente(parametroSistemaService
					.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
			correoDto.setContenido(mensajeInteresado);
			// correoDto.agregarDestinatario(this.cursoSelec.getCorreoRespCordinadorAcad());
			correoDto.agregarDestinatario(correoInteresado);
			try {
				CorreoUtil.sendMail(correoDto);
				agregarMsgInfo("Se envió correctamente la notificación", null);
				RequestContext.getCurrentInstance().execute("PF('dlgInteresado').hide()");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Prueba en registra curso: " + this.cursoSelec.getNombreCurso());
	}

	public void cancelarContacto() {
		nombreInteresado = StringUtils.EMPTY;
		correoInteresado = StringUtils.EMPTY;
		mensajeInteresado = StringUtils.EMPTY;
	}

	/**
	 * @return the eventoCapacitacionServiceFacade
	 */
	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	/**
	 * @param eventoCapacitacionServiceFacade
	 *            the eventoCapacitacionServiceFacade to set
	 */
	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	/**
	 * @return the correoElectronicoService
	 */
	public CorreoElectronicoService getCorreoElectronicoService() {
		return correoElectronicoService;
	}

	/**
	 * @param correoElectronicoService
	 *            the correoElectronicoService to set
	 */
	public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
		this.correoElectronicoService = correoElectronicoService;
	}

	/**
	 * @return the parametroSistemaService
	 */
	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	/**
	 * @param parametroSistemaService
	 *            the parametroSistemaService to set
	 */
	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	/**
	 * @return the nombreInteresado
	 */
	public String getNombreInteresado() {
		return nombreInteresado;
	}

	/**
	 * @param nombreInteresado
	 *            the nombreInteresado to set
	 */
	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}

	/**
	 * @return the correoInteresado
	 */
	public String getCorreoInteresado() {
		return correoInteresado;
	}

	/**
	 * @param correoInteresado
	 *            the correoInteresado to set
	 */
	public void setCorreoInteresado(String correoInteresado) {
		this.correoInteresado = correoInteresado;
	}

	/**
	 * @return the mensajeInteresado
	 */
	public String getMensajeInteresado() {
		return mensajeInteresado;
	}

	/**
	 * @param mensajeInteresado
	 *            the mensajeInteresado to set
	 */
	public void setMensajeInteresado(String mensajeInteresado) {
		this.mensajeInteresado = mensajeInteresado;
	}

	/**
	 * @return the cursoSelec
	 */
	public Curso getCursoSelec() {
		return cursoSelec;
	}

	/**
	 * @param cursoSelec
	 *            the cursoSelec to set
	 */
	public void setCursoSelec(Curso cursoSelec) {
		this.cursoSelec = cursoSelec;
	}

	/**
	 * @return the encuestaService
	 */
	public EncuestaService getEncuestaService() {
		return encuestaService;
	}

	/**
	 * @param encuestaService
	 *            the encuestaService to set
	 */
	public void setEncuestaService(EncuestaService encuestaService) {
		this.encuestaService = encuestaService;
	}

	public Curso getSeleccionCurso() {
		return seleccionCurso;
	}

	public void setSeleccionCurso(Curso seleccionCurso) {
		this.seleccionCurso = seleccionCurso;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}

	public String getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	public void setEjeCapacitacion(String ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	public String getTipoCompetencia() {
		return tipoCompetencia;
	}

	public void setTipoCompetencia(String tipoCompetencia) {
		this.tipoCompetencia = tipoCompetencia;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getNivelEnsenanza() {
		return nivelEnsenanza;
	}

	public void setNivelEnsenanza(String nivelEnsenanza) {
		this.nivelEnsenanza = nivelEnsenanza;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public List<Curso> getcursosTodos() {
		return cursosTodos;
	}

	public void setcursosTodos(List<Curso> cursosTodos) {
		this.cursosTodos = cursosTodos;
	}

	public List<Curso> getCursosPublicoGeneral() {
		return cursosPublicoGeneral;
	}

	public void setCursosPublicoGeneral(List<Curso> cursosPublicoGeneral) {
		this.cursosPublicoGeneral = cursosPublicoGeneral;
	}

	public String getFiltroCursos() {
		return filtroCursos;
	}

	public void setFiltroCursos(String filtroCusros) {
		this.filtroCursos = filtroCusros;
	}

	public List<CatalogoComunDTO> getListaTiposCompetencias() {
		return listaTiposCompetencias;
	}

	public void setListaTiposCompetencias(List<CatalogoComunDTO> listaTiposCompetencias) {
		this.listaTiposCompetencias = listaTiposCompetencias;
	}

	public NodoeHijosDTO getEstPlanSedesol() {
		return estPlanSedesol;
	}

	public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
		this.estPlanSedesol = estPlanSedesol;
	}

	public List<CatalogoComunDTO> getEjesCapacitacion() {
		return ejesCapacitacion;
	}

	public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
		this.ejesCapacitacion = ejesCapacitacion;
	}

}
