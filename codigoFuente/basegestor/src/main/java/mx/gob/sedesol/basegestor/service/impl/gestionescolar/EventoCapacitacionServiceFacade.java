package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CalificacionECDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CapturaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelEvaluacionCalificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoEvaluacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TablaCalificacionesDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionEscolarEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoCalificacionECEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatCategoriaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionAva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDestinatariosEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDictamen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoCalificacionEc;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;
import mx.gob.sedesol.basegestor.service.admin.LoteCargaUsuarioService;
import mx.gob.sedesol.basegestor.service.admin.LoteUsuarioService;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.RelPersonaPlataformaMoodleService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;
import mx.gob.sedesol.basegestor.service.gestionescolar.ProgramaSocialService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RegistroAsistenciaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelDiasEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelGpoEvaluacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelReponsableProduccionEcService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.springinit.GestionEscolarServiceAdapter;

@Service("eventoCapacitacionServiceFacade")
public class EventoCapacitacionServiceFacade {

	private static final Logger logger = Logger.getLogger(EventoCapacitacionServiceFacade.class);

	@Autowired
	private EventoCapacitacionService eventoCapacitacionService;

	@Autowired
	private LoteCargaUsuarioService loteCargaUsuarioService;

	@Autowired
	private AmbienteVirtualApService ambienteVirtualApService;

	@Autowired
	private GestionEscolarServiceAdapter gestionEscolarServiceAdapter;

	@Autowired
	private MallaCurricularService mallaCurricularService;

	@Autowired
	private FichaDescProgramaService fichaDescProgramaService;

	@Autowired
	private ProgramaSocialService programaSocialService;

	@Autowired
	private EntidadFederativaService entidadFederativaService;

	@Autowired
	private MunicipioService municipioService;

	@Autowired
	private PersonaResponsabilidadesService personaResponsabilidadesService;

	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoParticipanteService grupoParticipanteService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private RelDiasEventoCapacitacionService relDiasEventoCapacitacionService;

	@Autowired
	private CatalogoComunService<CatTipoCalificacionEc, Integer> catTipoCalificacionService;

	@Autowired
	private CatalogoComunService<CatDictamen, Integer> catDictamenService;

	@Autowired
	private RelGpoEvaluacionService relGpoEvaluacionService;

	@Autowired
	private RelReponsableProduccionEcService relReponsableProduccionEcService;

	@Autowired
	private RegistroAsistenciaService registroAsistenciaService;

	@Autowired

	private CorreoElectronicoService correoElectronicoService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	@Autowired
	private RelPersonaPlataformaMoodleService relPersonaPlataformaMoodle;

	@Autowired
	private LoteUsuarioService loteUsuarioService;

	/**
	 * ñ
	 *
	 * @param calificaciones
	 * @param gpoSeleccionado
	 * @param tpoCalifSel
	 * @param usuarioReg
	 * @param cerrarActa
	 * @param participantesByGrupo
	 * @param tablaAuxCalif
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RelGrupoEvaluacionDTO> guardaCalificacionesECPresencial(List<CalificacionECDTO> calificaciones,
			GrupoDTO gpoSeleccionado, EventoCapacitacionDTO evento, Long usuarioReg, boolean cerrarActa,
			List<RelGrupoParticipanteDTO> participantesByGrupo, List<TablaCalificacionesDTO> tablaAuxCalif)
			throws Exception {

		ResultadoDTO<RelGrupoEvaluacionDTO> res = null;

		try {

			Date fechaRegistro = new Date();
			// Se generan DTOs de Evaluaciones
			List<RelGrupoEvaluacionDTO> gpoEvaluaciones = new ArrayList<RelGrupoEvaluacionDTO>();

			// Se crean las evaluaciones
			for (CalificacionECDTO evaluacion : calificaciones) {

				RelGrupoEvaluacionDTO ge = new RelGrupoEvaluacionDTO();
				GrupoDTO gpo = getGrupoService().buscarGrupoPorId(gpoSeleccionado.getIdGrupo());

				if (ObjectUtils.isNotNull(gpo)) {

					ge.setTblGrupo(gpo);
					ge.setRelEvaluacionCalificaciones(new ArrayList<RelEvaluacionCalificacionDTO>());
					ge.setCatTipoCalificacionEc(getCatTipoCalificacionService().buscarPorId(evento.getIdEvento(),
							CatTipoCalificacionEc.class));

					ge.setNombreEvaluacion(evaluacion.getNombreEvaluacion());
					ge.setUsuarioModifico(usuarioReg);
					ge.setFechaRegistro(fechaRegistro);
					// ge.setActaCerrada(cerrarActa);
					ge.setPonderacion(evaluacion.getPonderacion());

					gpoEvaluaciones.add(ge);
				}
			}

			List<RelEvaluacionCalificacionDTO> aux = new ArrayList<>();
			for (RelGrupoParticipanteDTO partce : participantesByGrupo) {

				TablaCalificacionesDTO evaluacionPart = getEvaluacionByParticipante(partce.getPersona().getIdPersona(),
						tablaAuxCalif);

				// Por cada evaluacion genero Sus calificaciones
				if (ObjectUtils.isNotNull(evaluacionPart)
						&& !ObjectUtils.isNullOrEmpty(evaluacionPart.getCalificacionEC())) {

					partce.setCalifFinal(evaluacionPart.getCalifFinal());
					partce.setCalifTotal(evaluacionPart.getCalifTotal());
					partce.setPorcentajeAsist(Integer.parseInt(evaluacionPart.getAsistencia()));

					for (CalificacionECDTO calif : evaluacionPart.getCalificacionEC()) {

						RelEvaluacionCalificacionDTO eval = new RelEvaluacionCalificacionDTO();

						if (evento.getTpoCalificacion().equals(TipoCalificacionECEnum.TPO_CAL_SUMA.getId())) {
							eval.setCalificacion(calif.getCalifPonderacion());
						} else if (evento.getTpoCalificacion()
								.equals(TipoCalificacionECEnum.TPO_CAL_PROMEDIO.getId())) {
							eval.setCalificacion(calif.getCalificacion());
						}

						eval.setFechaRegistro(fechaRegistro);
						eval.setUsuarioModifico(usuarioReg);
						eval.setRelGrupoParticipante(partce);
						eval.setDictamen(getCatDictamenService().buscarPorId(evaluacionPart.getDictamen().getId(),
								CatDictamen.class));
						eval.setNombreEvaluacion(calif.getNombreEvaluacion());

						aux.add(eval);

					}
				}
			}

			// Persistencia de datos
			if (!ObjectUtils.isNullOrEmpty(gpoEvaluaciones)) {
				for (RelGrupoEvaluacionDTO eval : gpoEvaluaciones) {
					List<RelEvaluacionCalificacionDTO> califAux = this
							.getCalificacionesByEvaluacion(eval.getNombreEvaluacion(), aux);
					if (!ObjectUtils.isNullOrEmpty(califAux)) {

						califAux.stream().forEach(c -> c.setRelGrupoEvaluacion(eval));
						eval.setRelEvaluacionCalificaciones(califAux);
						res = relGpoEvaluacionService.guardar(eval);
						if (ObjectUtils.isNotNull(res) && !res.getResultado().getValor()) {
							res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
							throw new Exception("Error al guardar actualizacion del Grupo");
						}

					}
				}
			}

			if (cerrarActa) {

				for (RelGrupoParticipanteDTO rpg : participantesByGrupo) {
					ResultadoDTO<RelGrupoParticipanteDTO> resx = grupoParticipanteService
							.actualizaRelGrupoParticipante(rpg);
					if (ObjectUtils.isNotNull(resx) && !resx.getResultado().getValor()) {
						res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
						throw new Exception("Error al Actualizar la relación grupo-participante");
					}
				}

				gpoSeleccionado.setActaCerrada(Boolean.TRUE);
				ResultadoDTO<GrupoDTO> resActGpo = grupoService.actualizarGrupo(gpoSeleccionado, usuarioReg);
				if (ObjectUtils.isNotNull(resActGpo) && !resActGpo.getResultado().getValor()) {
					res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
					throw new Exception("Error al Actualizar el Grupo");
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
		}

		return res;
	}

	/**
	 *
	 * @param nombreEval
	 * @param calificaciones
	 * @return
	 */
	private List<RelEvaluacionCalificacionDTO> getCalificacionesByEvaluacion(String nombreEval,
			List<RelEvaluacionCalificacionDTO> calificaciones) {

		List<RelEvaluacionCalificacionDTO> auxCali = new ArrayList<>();

		for (RelEvaluacionCalificacionDTO cal : calificaciones) {
			if (cal.getNombreEvaluacion().equalsIgnoreCase(nombreEval)) {
				auxCali.add(cal);
			}
		}
		return auxCali;
	}

	/**
	 *
	 * @param evaluacionesGpo
	 * @param tablaAuxCalif
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RelGrupoEvaluacionDTO> actualizaCalificacionesECPresencial(
			List<CalificacionECDTO> calificaciones, GrupoDTO gpoSeleccionado, EventoCapacitacionDTO evento,
			boolean cerrarActa, List<RelGrupoEvaluacionDTO> evaluacionesGpo,
			List<RelGrupoParticipanteDTO> participantesByGrupo, List<TablaCalificacionesDTO> tablaAuxCalif,
			Long usuarioModifico) throws Exception {

		ResultadoDTO<RelGrupoEvaluacionDTO> res = null;

		try {

			Date fechaActual = new Date();
			if (calificaciones.size() != evaluacionesGpo.size()) {

				// Se eliminan calificaciones
				for (RelGrupoEvaluacionDTO rge : evaluacionesGpo) {
					/*
					 * rge.getBitacoraDTO().setIdUsuario(gpoSeleccionado.
					 * getBitacoraDTO().getIdUsuario());
					 * rge.getBitacoraDTO().setFechaBitacora(new Date());
					 * rge.getBitacoraDTO().setIp(gpoSeleccionado.getBitacoraDTO
					 * ().getIp());
					 * rge.getBitacoraDTO().setNavegador(gpoSeleccionado.
					 * getBitacoraDTO().getNavegador());
					 * rge.getBitacoraDTO().setFuncion(ConstantesBitacora.
					 * EC_MOD_CALIF_ELIMINAR);
					 */
					relGpoEvaluacionService.eliminar(rge);
				}

				return this.guardaCalificacionesECPresencial(calificaciones, gpoSeleccionado, evento, usuarioModifico,
						cerrarActa, participantesByGrupo, tablaAuxCalif);

			} else {

				for (RelGrupoEvaluacionDTO rge : evaluacionesGpo) {
					relGpoEvaluacionService.eliminaEvaluacionesByIdGpoEvaluacion(rge.getIdGpoEvaluacion());
					rge.setRelEvaluacionCalificaciones(new ArrayList<>());
				}

				for (RelGrupoParticipanteDTO partce : participantesByGrupo) {

					TablaCalificacionesDTO evaluacionPart = getEvaluacionByParticipante(
							partce.getPersona().getIdPersona(), tablaAuxCalif);
					// Por cada evaluacion genero Sus calificaciones
					if (!ObjectUtils.isNullOrEmpty(evaluacionPart.getCalificacionEC())) {

						partce.setCalifFinal(evaluacionPart.getCalifFinal());
						partce.setCalifTotal(evaluacionPart.getCalifTotal());
						partce.setPorcentajeAsist(Integer.parseInt(evaluacionPart.getAsistencia()));

						for (CalificacionECDTO calif : evaluacionPart.getCalificacionEC()) {

							RelEvaluacionCalificacionDTO eval = new RelEvaluacionCalificacionDTO();

							if (evento.getTpoCalificacion().equals(TipoCalificacionECEnum.TPO_CAL_SUMA.getId())) {
								eval.setCalificacion(calif.getCalifPonderacion());
							} else if (evento.getTpoCalificacion()
									.equals(TipoCalificacionECEnum.TPO_CAL_PROMEDIO.getId())) {
								eval.setCalificacion(calif.getCalificacion());
							}

							eval.setFechaRegistro(fechaActual);
							eval.setUsuarioModifico(usuarioModifico);
							eval.setRelGrupoParticipante(partce);
							eval.setDictamen(getCatDictamenService().buscarPorId(evaluacionPart.getDictamen().getId(),
									CatDictamen.class));

							RelGrupoEvaluacionDTO gpoEval = this.getGpoEvaluacionByNomEval(calif.getNombreEvaluacion(),
									evaluacionesGpo);
							if (ObjectUtils.isNotNull(gpoEval)) {
								eval.setRelGrupoEvaluacion(gpoEval);
								gpoEval.getRelEvaluacionCalificaciones().add(eval);
							}
						}
					}
				}

				// Persistencia de datos
				if (!ObjectUtils.isNullOrEmpty(evaluacionesGpo)) {
					for (RelGrupoEvaluacionDTO eval : evaluacionesGpo) {
						/*
						 * eval.getBitacoraDTO().setIdUsuario(gpoSeleccionado.
						 * getBitacoraDTO().getIdUsuario());
						 * eval.getBitacoraDTO().setFechaBitacora(new Date());
						 * eval.getBitacoraDTO().setIp(gpoSeleccionado.
						 * getBitacoraDTO().getIp());
						 * eval.getBitacoraDTO().setNavegador(gpoSeleccionado.
						 * getBitacoraDTO().getNavegador());
						 * eval.getBitacoraDTO().setFuncion(ConstantesBitacora.
						 * EC_MOD_CALIF_EDITAR);
						 */
						res = relGpoEvaluacionService.actualizar(eval);
						if (ObjectUtils.isNotNull(res) && !res.getResultado().getValor()) {
							res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
							throw new Exception("Error al Actualizar el Grupo");
						}
					}
				}

				if (cerrarActa) {

					for (RelGrupoParticipanteDTO rpg : participantesByGrupo) {
						/*
						 * rpg.getBitacoraDTO().setIdUsuario(gpoSeleccionado.
						 * getBitacoraDTO().getIdUsuario());
						 * rpg.getBitacoraDTO().setFechaBitacora(new Date());
						 * rpg.getBitacoraDTO().setIp(gpoSeleccionado.
						 * getBitacoraDTO().getIp());
						 * rpg.getBitacoraDTO().setNavegador(gpoSeleccionado.
						 * getBitacoraDTO().getNavegador());
						 * rpg.getBitacoraDTO().setFuncion(ConstantesBitacora.
						 * GRUPO_EC_EDITAR);
						 */
						ResultadoDTO<RelGrupoParticipanteDTO> resx = grupoParticipanteService
								.actualizaRelGrupoParticipante(rpg);
						if (ObjectUtils.isNotNull(resx) && !resx.getResultado().getValor()) {
							res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
							throw new Exception("Error al Actualizar la relación grupo-participante");
						}
					}

					gpoSeleccionado.setActaCerrada(Boolean.TRUE);
					ResultadoDTO<GrupoDTO> resActGpo = grupoService.actualizarGrupo(gpoSeleccionado, usuarioModifico);
					if (ObjectUtils.isNotNull(resActGpo) && !resActGpo.getResultado().getValor()) {
						res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
						throw new Exception("Error al Actualizar el Grupo");
					}

				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	/**
	 *
	 * @param nomEvaluacion
	 * @param evaluaciones
	 * @return
	 */
	private RelGrupoEvaluacionDTO getGpoEvaluacionByNomEval(String nomEvaluacion,
			List<RelGrupoEvaluacionDTO> evaluaciones) {
		for (RelGrupoEvaluacionDTO row : evaluaciones) {
			if (row.getNombreEvaluacion().equalsIgnoreCase(nomEvaluacion)) {
				return row;
			}
		}
		return null;
	}

	/**
	 *
	 * @param idPartce
	 * @param tablaAuxCalif
	 * @return
	 */
	private TablaCalificacionesDTO getEvaluacionByParticipante(Long idPartce,
			List<TablaCalificacionesDTO> tablaAuxCalif) {
		for (TablaCalificacionesDTO calif : tablaAuxCalif) {
			if (calif.getParticipante().getIdPersona().equals(idPartce)) {
				return calif;
			}
		}
		return null;
	}

	public MallaCurricularDTO obtenerMallaCurricular() {
		return mallaCurricularService.obtenerMallaCurricularPorId(1);
	}

	public List<EventoCapacitacionDTO> consultaEventoPorEstatus(Integer idEstatus) {
		return eventoCapacitacionService.consultaEventoPorEstatus(idEstatus);
	}

	public CapturaEventoCapacitacionDTO nuevoEventoCapacitacion(Long usuarioModifico) {
		CapturaEventoCapacitacionDTO datos = new CapturaEventoCapacitacionDTO();
		datos.setEvento(new EventoCapacitacionDTO());
		return datos;
	}

	public List<EventoCapacitacionDTO> busquedaPorCriterios(EventoCapacitacionDTO filtros, String tipoDatoFechas) {
		return eventoCapacitacionService.buscaEventosPorCriterios(filtros, tipoDatoFechas);
	}

	public EventoCapacitacionDTO busquedaPorID(EventoCapacitacionDTO filtros) {
		return eventoCapacitacionService.getEvento(filtros.getIdEvento());
	}

	public List<FichaDescProgramaDTO> buscaProgramasPorCriterios(FichaDescProgramaDTO filtro) {
		// return
		// fichaDescProgramaService.buscaProgramasPorCriteriosDatosBasicos(filtro);
		return fichaDescProgramaService.buscaProgramasPorCriterios(filtro);
	}

	public List<CatalogoComunDTO> obtenerCatelogriasEC() {
		return (List<CatalogoComunDTO>) gestionEscolarServiceAdapter
				.getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum.CAT_CATEGORIA_EVENTO_CAPACITACION)
				.findAll(CatCategoriaEventoCapacitacion.class);
	}

	public List<ParametroWSMoodleDTO> obtenerPlataformasMoodle() {
		return parametroWSMoodleService.findAll();
	}

	public List<CatalogoComunDTO> obtenerClasificacionesAVA() {
		return (List<CatalogoComunDTO>) gestionEscolarServiceAdapter
				.getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum.CAT_CLASIFICACION_AVA)
				.findAll(CatClasificacionAva.class);
	}

	public List<CatalogoComunDTO> obtenerDestinatariosEc() {
		return (List<CatalogoComunDTO>) gestionEscolarServiceAdapter
				.getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum.CAT_DESTINATARIOS_EC)
				.findAll(CatDestinatariosEc.class);
	}

	public List<ProgramaSocialDTO> obtenerProgramasSociales() {
		return getProgramaSocialService().findAll();
	}

	public List<EntidadFederativaDTO> obtenerEntidadesFederativas() {
		return entidadFederativaService.obtenerEntidadesPorPais(ConstantesGestor.ID_PAIS_MEXICO);
	}

	public List<MunicipioDTO> obtenerMunicipiosPorEntidad(Integer idEntidadFederativa) {
		return municipioService.buscarPorEntidadFederativa(idEntidadFederativa);
	}

	public List<PersonaResponsabilidadesDTO> obtienePersonasPorResponsabilidad(Integer idResponsabilidad) {
		return personaResponsabilidadesService.obtienePersonasPorResponsabilidad(idResponsabilidad);
	}

	public ResultadoDTO<EventoCapacitacionDTO> guardarEventoCapacitacion(CapturaEventoCapacitacionDTO evento) {
		ResultadoDTO<EventoCapacitacionDTO> resultado = new ResultadoDTO<>();
		try {
			resultado = eventoCapacitacionService.guardarEventoCapacitacion(evento);
		} catch (Exception e) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
			logger.error(e.getMessage(), e);
		}
		return resultado;
	}

	public ResultadoDTO<EventoCapacitacionDTO> guardarBorrador(CapturaEventoCapacitacionDTO evento) {
		ResultadoDTO<EventoCapacitacionDTO> resultado = new ResultadoDTO<>();
		try {
			resultado = eventoCapacitacionService.guardarBorrador(evento);
		} catch (Exception e) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
			logger.error(e.getMessage(), e);
		}
		return resultado;
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

	/**
	 * @return the relDiasEventoCapacitacionService
	 */
	public RelDiasEventoCapacitacionService getRelDiasEventoCapacitacionService() {
		return relDiasEventoCapacitacionService;
	}

	/**
	 * @param relDiasEventoCapacitacionService
	 *            the relDiasEventoCapacitacionService to set
	 */
	public void setRelDiasEventoCapacitacionService(RelDiasEventoCapacitacionService relDiasEventoCapacitacionService) {
		this.relDiasEventoCapacitacionService = relDiasEventoCapacitacionService;
	}

	/**
	 * @return the catTipoCalificacionService
	 */
	public CatalogoComunService<CatTipoCalificacionEc, Integer> getCatTipoCalificacionService() {
		return catTipoCalificacionService;
	}

	/**
	 * @param catTipoCalificacionService
	 *            the catTipoCalificacionService to set
	 */
	public void setCatTipoCalificacionService(
			CatalogoComunService<CatTipoCalificacionEc, Integer> catTipoCalificacionService) {
		this.catTipoCalificacionService = catTipoCalificacionService;
	}

	public AmbienteVirtualApService getAmbienteVirtualApService() {
		return ambienteVirtualApService;
	}

	public void setAmbienteVirtualApService(AmbienteVirtualApService ambienteVirtualApService) {
		this.ambienteVirtualApService = ambienteVirtualApService;
	}

	public CatalogoComunService<CatDictamen, Integer> getCatDictamenService() {
		return catDictamenService;
	}

	public void setCatDictamenService(CatalogoComunService<CatDictamen, Integer> catDictamenService) {
		this.catDictamenService = catDictamenService;
	}

	/**
	 * @return the relGpoEvaluacionService
	 */
	public RelGpoEvaluacionService getRelGpoEvaluacionService() {
		return relGpoEvaluacionService;
	}

	/**
	 * @param relGpoEvaluacionService
	 *            the relGpoEvaluacionService to set
	 */
	public void setRelGpoEvaluacionService(RelGpoEvaluacionService relGpoEvaluacionService) {
		this.relGpoEvaluacionService = relGpoEvaluacionService;
	}

	public RelReponsableProduccionEcService getRelReponsableProduccionEcService() {
		return relReponsableProduccionEcService;
	}

	public void setRelReponsableProduccionEcService(RelReponsableProduccionEcService relReponsableProduccionEcService) {
		this.relReponsableProduccionEcService = relReponsableProduccionEcService;
	}

	/**
	 * @return the registroAsistenciaService
	 */
	public RegistroAsistenciaService getRegistroAsistenciaService() {
		return registroAsistenciaService;
	}

	/**
	 * @param registroAsistenciaService
	 *            the registroAsistenciaService to set
	 */
	public void setRegistroAsistenciaService(RegistroAsistenciaService registroAsistenciaService) {
		this.registroAsistenciaService = registroAsistenciaService;
	}

	/**
	 * @return the parametroWSMoodleService
	 */
	public ParametroWSMoodleService getParametroWSMoodleService() {
		return parametroWSMoodleService;
	}

	/**
	 * @param parametroWSMoodleService
	 *            the parametroWSMoodleService to set
	 */
	public void setParametroWSMoodleService(ParametroWSMoodleService parametroWSMoodleService) {
		this.parametroWSMoodleService = parametroWSMoodleService;
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

	/**
	 * @return the relPersonaPlataformaMoodle
	 */
	public RelPersonaPlataformaMoodleService getRelPersonaPlataformaMoodle() {
		return relPersonaPlataformaMoodle;
	}

	/**
	 * @param relPersonaPlataformaMoodle
	 *            the relPersonaPlataformaMoodle to set
	 */
	public void setRelPersonaPlataformaMoodle(RelPersonaPlataformaMoodleService relPersonaPlataformaMoodle) {
		this.relPersonaPlataformaMoodle = relPersonaPlataformaMoodle;
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

	public LoteCargaUsuarioService getLoteCargaUsuarioService() {
		return loteCargaUsuarioService;
	}

	public void setLoteCargaUsuarioService(LoteCargaUsuarioService loteCargaUsuarioService) {
		this.loteCargaUsuarioService = loteCargaUsuarioService;
	}

	public LoteUsuarioService getLoteUsuarioService() {
		return loteUsuarioService;
	}

	public void setLoteUsuarioService(LoteUsuarioService loteUsuarioService) {
		this.loteUsuarioService = loteUsuarioService;
	}

	public ProgramaSocialService getProgramaSocialService() {
		return programaSocialService;
	}

	public void setProgramaSocialService(ProgramaSocialService programaSocialService) {
		this.programaSocialService = programaSocialService;
	}

}
