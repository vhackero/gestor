package mx.gob.sedesol.gestorweb.ws;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.dto.ws.SolicitarCursoRequest;
import mx.gob.sedesol.basegestor.commons.dto.ws.SolicitarCursoResponse;
import mx.gob.sedesol.basegestor.commons.utils.CorreoUtil;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.logisticainfraestructura.SedeInfraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.CargaHorariaPrograma;
import mx.gob.sedesol.gestorweb.commons.dto.CursoPublico;
import mx.gob.sedesol.gestorweb.commons.dto.Evento;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.Programa;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.web.bind.annotation.RestController
@RequestMapping({ "/cursos" })
public class CursosREST {

	@Autowired
	private transient EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;
	@Autowired
	private transient EncuestaService encuestaService;
	@Autowired
	private transient CorreoElectronicoService correoElectronicoService;
	@Autowired
	private transient ParametroSistemaService parametroSistemaService;
	private List<CatalogoComunDTO> listaTiposCompetencias;
	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> ejesCapacitacion;
	private Logger logger = Logger.getLogger(CursosREST.class);

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	// TODO ajustar servicio para que solo obtenga los eventos publicos con
	// estatus en ejecucion
	@RequestMapping({ "/publicos" })
	public List<CursoPublico> obtenerCursosPublicos() {
		generaEstructuraCatTpoCompetenciaPlan();
		generaCatEjesCapacitBusqueda();
		List<CursoPublico> cursosPublicos = new ArrayList<>();
		List<EventoCapacitacionDTO> eventosPublicos = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
				.obtenerEventosPublicosEnEjec();

		if (!ObjectUtils.isNullOrEmpty(eventosPublicos)) {
			for (EventoCapacitacionDTO ec : eventosPublicos) {
				CursoPublico curso = new CursoPublico();
				curso.setCurso(new Evento());
				curso.setPrograma(new Programa());
				curso.setIdCurso(String.valueOf(ec.getIdEvento()));
				curso.getCurso()
						.setRutaImagenCurso(ObjectUtils.isNullOrEmpty(ec.getUrlImagen())
								? eventoCapacitacionServiceFacade.obtenerRutaUndertow()
										.concat(eventoCapacitacionServiceFacade.obtenerNombreImagenComun())
								: eventoCapacitacionServiceFacade.obtenerRutaUndertow().concat(ec.getUrlImagen()));
				curso.getCurso().setObjetivoGeneralCurso(ec.getObjetivoGeneralEc());
				curso.getCurso().setNombreCurso(ec.getNombreEc());
				curso.getCurso().setPerfil(ec.getPerfilEc());
				curso.getCurso().setRequisitos(ec.getRequisitosEc());
				curso.getCurso().setModalidadCurso(ec.getCatModalidadPlanPrograma().getNombre());
				curso.getCurso().setValoracionCurso(String.valueOf(encuestaService
						.calificacionPrograma(ec.getFichaDescriptivaPrograma().getIdPrograma()).intValue()));
				curso.getCurso()
						.setFechaInicioCurso(DateUtils.convierteDateAString(ec.getFechaInicial(), "dd-MM-yyyy"));
				curso.getCurso().setFechaFinalCurso(DateUtils.convierteDateAString(ec.getFechaFinal(), "dd-MM-yyyy"));
				curso.getCurso().setDuracionCurso(
						DateUtils.restaFechasEnDiasMes(ec.getFechaInicial(), ec.getFechaFinal()).toString());
				curso.getPrograma().setIdPrograma(String.valueOf(ec.getFichaDescriptivaPrograma().getIdPrograma()));
				curso.getPrograma().setNombrePrograma(ec.getFichaDescriptivaPrograma().getNombreTentativo());
				curso.getPrograma().setDescripcionPrograma(ec.getFichaDescriptivaPrograma().getDescripcion());
				curso.getPrograma()
						.setModalidadPrograma(ec.getFichaDescriptivaPrograma().getCatModalidad().getNombre());
				curso.getPrograma()
						.setNivelConocimiento(ec.getFichaDescriptivaPrograma().getCatNivelConocimiento().getNombre());
				curso.getPrograma()
						.setNivelEnsenanza(ec.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma().getNombre());
				curso.getPrograma().setTipoCompetencia(
						obtieneNombreTpoCompetencia(ec.getFichaDescriptivaPrograma().getTipoCompetencia()));
				curso.getPrograma().setEjeCapacitacion(
						obtieneNombreEjeCapacit(ec.getFichaDescriptivaPrograma().getEjeCapacitacion()));
				curso.getPrograma().setCargaHorariaPrograma(obtenerCargaHorariaDePrograma(ec));
				cursosPublicos.add(curso);
			}

		}

		return cursosPublicos;
	}

	@RequestMapping(value = "/solicitarCurso", method = RequestMethod.POST)
	public SolicitarCursoResponse solicitarCurso(@RequestBody SolicitarCursoRequest request) {
		SolicitarCursoResponse response = new SolicitarCursoResponse();
		if (!ObjectUtils.isNull(request) && !ObjectUtils.isNullOrEmpty(request.getIdCurso())
				&& !ObjectUtils.isNullOrEmpty(request.getNombre()) && !ObjectUtils.isNullOrEmpty(request.getCorreo())
				&& !ObjectUtils.isNullOrEmpty(request.getMensaje())) {

			if (validaCorreo(request.getCorreo())) {

				if (validaLongitudMensaje(request.getMensaje())) {
					EventoCapacitacionDTO evento = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
							.getEvento(request.getIdCurso());

					if (ObjectUtils.isNotNull(evento)) {
						CorreoDTO correoDto = correoElectronicoService.asignaParametrosConfigCorreo();
						correoDto.setDestinatarios(new ArrayList<String>());
						correoDto
								.setAsunto(request.getNombre() + "Esta interesado en el curso " + evento.getNombreEc());
						correoDto
								.setTitulo(request.getNombre() + "Esta interesado en el curso " + evento.getNombreEc());
						correoDto.setRemitente(parametroSistemaService
								.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
						correoDto.setContenido(request.getNombre());
						correoDto.agregarDestinatario(request.getCorreo());
						try {
							CorreoUtil.sendMail(correoDto);
							response.setExito(true);
							response.setMensaje("Exito");
						} catch (Exception e) {
							e.printStackTrace();
							response.setExito(false);
							response.setMensaje("Ocurrio un error");
						}
					} else {
						response.setExito(false);
						response.setMensaje("El curso no existe");
					}
				} else {
					response.setExito(false);
					response.setMensaje("El mensaje debe contener mas de 10 caracteres.");
				}

			} else {
				response.setExito(false);
				response.setMensaje("La dirección de correo no es válida.");
			}

		} else {
			response.setExito(false);
			response.setMensaje("Faltan datos");
		}
		return response;
	}

	private boolean validaCorreo(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean validaLongitudMensaje(String mensaje) {
		return mensaje.length() <= 10 ? false : true;
	}

	private String getCorreoCordinadorAcad(List<ReponsableProduccionEcDTO> responsables) {
		if (!ObjectUtils.isNullOrEmpty(responsables)) {
			for (ReponsableProduccionEcDTO rp : responsables) {
				if (rp.getEsResponsablePrincipal().booleanValue()) {
					List<PersonaCorreoDTO> correos = rp.getPersonaResponsabilidad().getTblPersona().getPersonaCorreos();
					if (!ObjectUtils.isNullOrEmpty(correos)) {
						for (PersonaCorreoDTO c : correos) {
							if (c.getTipoCorreo().getIdTipoCorreo().equals(Integer.valueOf(1))) {
								return c.getCorreoElectronico();
							}
						}
					}
				}
			}
		}
		return "";
	}

	private CargaHorariaPrograma obtenerCargaHorariaDePrograma(EventoCapacitacionDTO evento) {
		CargaHorariaPrograma cargaHoraria = new CargaHorariaPrograma();
		for (RelProgDuracionDTO duracion : evento.getFichaDescriptivaPrograma().getRelProgramaDuracion()) {
			cargaHoraria = new CargaHorariaPrograma();
			switch (duracion.getIdTpoCargaHoraria()) {
			case 1:
				cargaHoraria.setHorasTeoria(duracion.getHoras());
				cargaHoraria.setMinutosTeoria(duracion.getMinutos());
				break;
			case 2:
				cargaHoraria.setHorasPractica(duracion.getHoras());
				cargaHoraria.setMinutosPractica(duracion.getMinutos());
				break;
			case 3:
				cargaHoraria.setHorasEvaluacion(duracion.getHoras());
				cargaHoraria.setMinutosEvaluacion(duracion.getMinutos());
				break;
			}

		}
		return cargaHoraria;
	}

	/**
	 * Metodo que obtiene el nombre de un tipo de competencia apartir de su id
	 */
	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {

		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : getListaTiposCompetencias()) {
				if (tpoCom.getId().equals(idTpoComp))
					return tpoCom.getNombre();
			}
		}
		return null;
	}

	/**
	 * Metodo que obtienen el nombre de un eje de capacitacion apartir de su id
	 */
	public String obtieneNombreEjeCapacit(Integer idEjeCap) {
		if (ObjectUtils.isNotNull(idEjeCap)) {
			for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0)
					return ejeCap.getNombre();
			}
		}
		return null;
	}

	/**
	 * Metodo que extraer los datos para mostrar el tipo competencia
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();

		MallaCurricularDTO mallaSedesol = eventoCapacitacionServiceFacade.obtenerMallaCurricular();
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

	/**
	 * Metodo que genera el catalogo de Mallas curriculares por nivel de
	 * profudidad
	 * 
	 * @param hijos
	 * @param nodoGral
	 */
	public void generaCatxNivel(List<MallaCurricularDTO> hijos, NodoeHijosDTO nodoGral, int nivel) {

		for (MallaCurricularDTO mint : hijos) {

			NodoeHijosDTO hijo = new NodoeHijosDTO();
			hijo.setIdNodo(mint.getId());
			hijo.setIdPadre(nodoGral.getIdNodo());
			hijo.setIdObjCurr(mint.getObjetoCurricular().getId());
			hijo.setNivel(nivel + 1);
			hijo.setNombre(mint.getNombre());
			nodoGral.getNodosHijos().add(hijo);

			if (!ObjectUtils.isNullOrEmpty(mint.getLstHijosMallaCurr()))
				this.generaCatxNivel(mint.getLstHijosMallaCurr(), hijo, hijo.getNivel());
			;
		}
	}

	/**
	 * Metodo que sirve para extraer los ejes de capacitacion
	 */
	private void generaCatEjesCapacitBusqueda() {
		ejesCapacitacion = new ArrayList<>();

		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			for (NodoeHijosDTO nint : nh.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nint.getIdNodo());
				cc.setNombre(nint.getNombre());
				ejesCapacitacion.add(cc);
			}
		}
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
