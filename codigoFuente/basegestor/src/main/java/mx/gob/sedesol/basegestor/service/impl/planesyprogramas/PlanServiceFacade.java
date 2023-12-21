package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelMallaPlanDTO;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanAptitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanHabilidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaPlanService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Categoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.RespuestaCrearCategorias;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CrearCategoria;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

@Service("planServiceFacade")
public class PlanServiceFacade {

	private static final Logger logger = Logger.getLogger(PlanServiceFacade.class);

	@Autowired
	private PlanService planService;
	@Autowired
	private MallaCurricularService mallaCurricularService;
	@Autowired
	private CatalogoComunService<CatObjetoCurricular, Integer> catObjCurrService;

	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;
	
	@Autowired
	private MallaPlanService mallaPlanService;

	/**
	 *
	 * @param clon
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PlanDTO> clonarPlan(PlanDTO clon) {
		ResultadoDTO<PlanDTO> resultado = new ResultadoDTO<>();
		try {

			List<RelPlanHabilidadDTO> habilidadesClon = clon.getRelPlanHabilidades();
			List<RelPlanAptitudDTO> aptitudesClon = clon.getRelPlanAptitudes();
			List<RelPlanConocimientoDTO> conocimientosClon = clon.getRelPlanConocimientos();

			MallaCurricularDTO mallaCurClon = mallaCurricularService.obtenerMallaCurricularPorIdPlan(clon.getIdPlan());

			clon.setRelPlanAptitudes(null);
			clon.setRelPlanConocimientos(null);
			clon.setRelPlanHabilidades(null);
			clon.setIdPlan(null);
			clon.setFechaRegistro(new Date());
			clon.setVersion(clon.getVersion() + ConstantesGestor.VERSION_UNO);

			ResultadoDTO<PlanDTO> resClon = planService.guardar(clon);

			if (ObjectUtils.isNotNull(resClon) && resClon.getResultado().getValor()) {
				PlanDTO planClonGuardado = resClon.getDto();

				if (!ObjectUtils.isNullOrEmpty(habilidadesClon)) {
					for (RelPlanHabilidadDTO hab : habilidadesClon) {
						hab.setTblPlan(planClonGuardado);
					}
					planClonGuardado.setRelPlanHabilidades(habilidadesClon);
				}

				if (!ObjectUtils.isNullOrEmpty(aptitudesClon)) {
					for (RelPlanAptitudDTO apt : aptitudesClon) {
						apt.setTblPlan(planClonGuardado);
					}
					planClonGuardado.setRelPlanAptitudes(aptitudesClon);
				}

				if (!ObjectUtils.isNullOrEmpty(conocimientosClon)) {
					for (RelPlanConocimientoDTO con : conocimientosClon) {
						con.setTblPlan(planClonGuardado);
					}
					planClonGuardado.setRelPlanConocimientos(conocimientosClon);
				}
				// Se actualiza el plan
				planService.actualizar(planClonGuardado);

				// Se genera la malla Curricular del Plan Clonado
				if (ObjectUtils.isNotNull(mallaCurClon)) {

					List<MallaCurricularDTO> mallaHijosClon = mallaCurClon.getLstHijosMallaCurr();
					mallaCurClon.setIdPlan(planClonGuardado.getIdPlan());
					mallaCurClon.setId(null);
					mallaCurClon.setLstHijosMallaCurr(null);

					MallaCurricularDTO mallaClonGuardada = mallaCurricularService.guardar(mallaCurClon).getDto();
					if (ObjectUtils.isNotNull(mallaClonGuardada)) {
						this.clonaMallaCurHijos(mallaHijosClon, mallaClonGuardada.getId());
					}

				} else {

					// RN: Se genera un registro en la malla curricular como nuevo plan
					MallaCurricularDTO planMalla = new MallaCurricularDTO();
					planMalla.setIdPlan(planClonGuardado.getIdPlan());
					planMalla.setActivo(ConstantesGestor.ACTIVO);
					planMalla.setFechaRegistro(planClonGuardado.getFechaRegistro());
					planMalla.setNombre(planClonGuardado.getNombre());
					planMalla.setMallaCurricularPadre(null);
					planMalla.setUsuarioModifico(planClonGuardado.getUsuarioModifico());
					planMalla.setObjetoCurricular(catObjCurrService
							.buscarRegistroPorNombre(ObjetoCurricularEnum.PLAN.getNombre(), CatObjetoCurricular.class));

					mallaCurricularService.guardar(planMalla);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
			throw e;
		}

		return resultado;
	}

	/**
	 * Metodo que se encarga de generar los nuevos Ids para la malla curricular
	 * clonada
	 *
	 * @param mallaHijos
	 * @param mallaPadre
	 */
	private void clonaMallaCurHijos(List<MallaCurricularDTO> mallaHijos, Integer idMallaPadre) {
		if (ObjectUtils.isNotNull(mallaHijos) && ObjectUtils.isNotNull(idMallaPadre)) {

			for (MallaCurricularDTO mcurHijo : mallaHijos) {

				List<MallaCurricularDTO> hijosClon = mcurHijo.getLstHijosMallaCurr();

				mcurHijo.setId(null);
				mcurHijo.setLstHijosMallaCurr(null);
				mcurHijo.setFechaActualizacion(new Date());
				mcurHijo.setMallaCurricularPadre(mallaCurricularService.obtenerMallaCurricularPorId(idMallaPadre));

				// Se persiste el identificador para poder generar los registros subsecuentes
				MallaCurricularDTO mHijoGuard = mallaCurricularService.guardar(mcurHijo).getDto();

				if (!ObjectUtils.isNullOrEmpty(hijosClon) && ObjectUtils.isNotNull(mHijoGuard)) {
					this.clonaMallaCurHijos(hijosClon, mHijoGuard.getId());
				}
			}
		}
	}

	/**
	 * Guarda un nuevo plan con sus relaciones
	 *
	 * @param plan
	 * @param habilidadesPlan
	 * @param aptitudesPlan
	 * @param conocimientosPlan
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
	public ResultadoDTO<PlanDTO> guardaNuevoPlan(PlanDTO plan, List<CatalogoComunDTO> habilidadesPlan,
			List<CatalogoComunDTO> aptitudesPlan, List<CatalogoComunDTO> conocimientosPlan, RelMallaPlanDTO relMallaPlan)
			throws Exception {

		ResultadoDTO<PlanDTO> resultado = new ResultadoDTO<>();
		List<RelPlanHabilidadDTO> relHabilidades;
		List<RelPlanConocimientoDTO> relConocimientos;
		List<RelPlanAptitudDTO> relAptitudes;

		if (ObjectUtils.isNull(plan)) {
			return null;
		}

		// Se persiste el plan para generar Id y se procede a generar las relaciones
		// multiples
		logger.debug("Persistiendo el nuevo plan");
		plan.setFechaRegistro(new Date());
		plan.setVersion(ConstantesGestor.VERSION_UNO);

		resultado = planService.guardar(plan);

		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			PlanDTO nuevoPlan = resultado.getDto();

			// RN: Se genera un registro en la malla curricular como nuevo plan
			MallaCurricularDTO planMalla = new MallaCurricularDTO();
			planMalla.setIdPlan(nuevoPlan.getIdPlan());
			planMalla.setActivo(ConstantesGestor.ACTIVO);
			planMalla.setFechaRegistro(nuevoPlan.getFechaRegistro());
			planMalla.setNombre(nuevoPlan.getNombre());
			planMalla.setMallaCurricularPadre(null);
			planMalla.setUsuarioModifico(nuevoPlan.getUsuarioModifico());
			planMalla.setObjetoCurricular(catObjCurrService
					.buscarRegistroPorNombre(ObjetoCurricularEnum.PLAN.getNombre(), CatObjetoCurricular.class));
			// planMalla.getBitacoraDTO().setIdUsuario(plan.getBitacoraDTO().getIdUsuario());
			// planMalla.getBitacoraDTO().setFechaBitacora(new Date());
			// planMalla.getBitacoraDTO().setIp(plan.getBitacoraDTO().getIp());
			// planMalla.getBitacoraDTO().setNavegador(plan.getBitacoraDTO().getNavegador());
			// planMalla.getBitacoraDTO().setFuncion(ConstantesBitacora.MALLA_CURR_AGREGAR);
			mallaCurricularService.guardar(planMalla);

			// Se generan las Relaciones de acuerdo a las listas de seleccion multiple
			if (!ObjectUtils.isNullOrEmpty(habilidadesPlan)) {
				relHabilidades = new ArrayList<>();
				for (CatalogoComunDTO habilidad : habilidadesPlan) {

					RelPlanHabilidadDTO relHabil = new RelPlanHabilidadDTO();
					relHabil.setFechaRegistro(nuevoPlan.getFechaRegistro());
					relHabil.setActivo(ConstantesGestor.ACTIVO);
					relHabil.setUsuarioModifico(plan.getUsuarioModifico());
					relHabil.setTblPlan(nuevoPlan);
					relHabil.setCatHabilidadesPlan(habilidad);

					relHabilidades.add(relHabil);
				}
				nuevoPlan.setRelPlanHabilidades(relHabilidades);
			}

			if (!ObjectUtils.isNullOrEmpty(aptitudesPlan)) {
				relAptitudes = new ArrayList<>();
				for (CatalogoComunDTO aptitud : aptitudesPlan) {
					RelPlanAptitudDTO relApt = new RelPlanAptitudDTO();
					relApt.setFechaRegistro(nuevoPlan.getFechaRegistro());
					relApt.setActivo(ConstantesGestor.ACTIVO);
					relApt.setUsuarioModifico(plan.getUsuarioModifico());
					relApt.setTblPlan(nuevoPlan);
					relApt.setCatAptitudesPlan(aptitud);

					relAptitudes.add(relApt);
				}
				nuevoPlan.setRelPlanAptitudes(relAptitudes);
			}

			if (!ObjectUtils.isNullOrEmpty(conocimientosPlan)) {
				relConocimientos = new ArrayList<>();
				for (CatalogoComunDTO conoc : conocimientosPlan) {
					RelPlanConocimientoDTO relConoc = new RelPlanConocimientoDTO();
					relConoc.setFechaRegistro(nuevoPlan.getFechaRegistro());
					relConoc.setActivo(ConstantesGestor.ACTIVO);
					relConoc.setUsuarioModifico(plan.getUsuarioModifico());
					relConoc.setTblPlan(nuevoPlan);
					relConoc.setCatAreasConocimiento(conoc);

					relConocimientos.add(relConoc);
				}
				nuevoPlan.setRelPlanConocimientos(relConocimientos);
			}

			// Se Guardan plan con relaciones
			resultado = planService.actualizar(nuevoPlan);

			// Se genera Estructura Moodle
			if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
				
				ResultadoDTO<RelMallaPlanDTO> resultadoMalla = new ResultadoDTO<>();
				
				resultadoMalla = mallaPlanService.guardar(relMallaPlan);
				
				if(ObjectUtils.isNotNull(resultadoMalla) && resultadoMalla.getResultado().getValor()) {
					if(resultado.getDto().getCatEstatusPlan().getNombre().equals("Ejecución")){
						resultado = guardarDatosMoodle(resultado.getDto(), relMallaPlan);
						
						if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
							resultadoMalla.getDto().setFecha_actualizacion(new Date());
							resultadoMalla.getDto().setActivo(0);
							
							mallaPlanService.actualizar(resultadoMalla.getDto());
						}else {
							throw new Exception("¡No se pudieron guardar lo datos en Moodle!");
						}
					}
				}else {
					throw new Exception("¡No se pudo guardar el detalle de las estructuras y subestructuras!");
				}
				
				logger.error("Datos guardados!");
			}else {
				throw new Exception("¡No se pudieron guardar los conocimientos, habilidades o competencias!");
			}

			logger.debug("finaliza persistencia de plan");
			return resultado;
		}
		
		throw new Exception("¡No se pudo guardar el plan!");
	}
	
	public ResultadoDTO<PlanDTO> guardarDatosMoodle(PlanDTO planSaved, RelMallaPlanDTO relMallaPlan) throws ErrorWS{
		ResultadoDTO<PlanDTO> respuesta = new ResultadoDTO<>();

		List<ParametroWSMoodleDTO> plataformas = parametroWSMoodleService.findAll();
		if (!ObjectUtils.isNullOrEmpty(plataformas)) {

			for (ParametroWSMoodleDTO ptf : plataformas) {

				CrearCategoria categoriaWS = new CrearCategoria(ptf);
				Categoria planCategoria = new Categoria();
				planCategoria.setIdnumber(planSaved.getIdPlan().toString());
				planCategoria.setName(planSaved.getNombre());

				ArrayList<Categoria> categorias = new ArrayList<Categoria>();
				categorias.add(planCategoria);

				List<RespuestaCrearCategorias> respuestasWS = categoriaWS.crearCategoria(categorias);
				if (ObjectUtils.isNotNull(respuestasWS)) {

					int idCategoriaMdl = respuestasWS.get(ConstantesGestor.PRIMER_ELEMENTO).getId();

					planSaved.setIdCategoriaMdl(idCategoriaMdl);
					respuesta = getPlanService().actualizar(planSaved);

					MallaCurricularDTO mallaCurr = mallaCurricularService
							.obtenerMallaCurricularPorIdPlan(planSaved.getIdPlan());
					mallaCurr.setIdCategoriaMdl(idCategoriaMdl);
					mallaCurr.setFechaActualizacion(new Date());
					// mallaCurr.getBitacoraDTO().setIdUsuario(plan.getBitacoraDTO().getIdUsuario());
					// mallaCurr.getBitacoraDTO().setFechaBitacora(new Date());
					// mallaCurr.getBitacoraDTO().setIp(plan.getBitacoraDTO().getIp());
					// mallaCurr.getBitacoraDTO().setNavegador(plan.getBitacoraDTO().getNavegador());
					// mallaCurr.getBitacoraDTO().setFuncion(ConstantesBitacora.MALLA_CUR_EDITAR);
					mallaCurricularService.actualizar(mallaCurr);

				}
			}
			
			if(ObjectUtils.isNotNull(planSaved.getCatPeriodo())) {
				CatalogoComunDTO objetoEstructura = catObjCurrService.buscarRegistroPorNombre(ObjetoCurricularEnum.ESTRUCTURA.getNombre(), CatObjetoCurricular.class);
				CatalogoComunDTO objetoSubestructura = catObjCurrService.buscarRegistroPorNombre(ObjetoCurricularEnum.SUB_ESTRUCTURA.getNombre(), CatObjetoCurricular.class);
			
				for (int i = 0; i < relMallaPlan.getElementosEstructuras(); i++) {
					MallaCurricularDTO mallaLvl0 = guardarMalla(objetoEstructura, relMallaPlan.getNombreEstructuras() + " " + (i + 1),
							planSaved.getUsuarioModifico(),
							mallaCurricularService.obtenerMallaCurricularPorIdPlan(respuesta.getDto().getIdPlan()))
							.getDto();

					int j, k, l = 0;

					if (relMallaPlan.getNiveles() > 0) {
						for (j = 0; j < relMallaPlan.getElementosSubestructuras1(); j++) {
							MallaCurricularDTO mallaLvl1 = guardarMalla(objetoSubestructura, relMallaPlan.getNombreSubestructuras1() + " " + (j + 1),
									planSaved.getUsuarioModifico(), mallaLvl0).getDto();

							if (relMallaPlan.getNiveles() > 1) {
								for (k = 0; k < relMallaPlan.getElementosSubestructuras2(); k++) {
									MallaCurricularDTO mallaLvl2 = guardarMalla(objetoSubestructura, relMallaPlan.getNombreSubestructuras2() + " " + (k + 1),
											planSaved.getUsuarioModifico(), mallaLvl1).getDto();

									if (relMallaPlan.getNiveles() > 2) {
										for (l = 0; l < relMallaPlan.getElementosSubestructuras3(); l++) {
											guardarMalla(objetoSubestructura, relMallaPlan.getNombreSubestructuras3() + " " + (l + 1),
													planSaved.getUsuarioModifico(), mallaLvl2).getDto();
										}
									}
								}
							}
						}
					}
					
				}
			}
		}else {
			throw new ErrorWS("¡No hay plataformas moodle configuradas!");
		}
		
		return respuesta;
	}

	public ResultadoDTO<MallaCurricularDTO> guardarMalla(CatalogoComunDTO objetoCurricular, String nombre, Long usuarioMod,
			MallaCurricularDTO mallaPadre) throws ErrorWS {
		ResultadoDTO<MallaCurricularDTO> resultado = new ResultadoDTO<>();
		
		MallaCurricularDTO mallaStru = new MallaCurricularDTO();
		mallaStru.setActivo(ConstantesGestor.ACTIVO);
		mallaStru.setFechaRegistro(new Date());
		mallaStru.setNombre(nombre);
		mallaStru.setMallaCurricularPadre(mallaPadre);
		mallaStru.setUsuarioModifico(usuarioMod);
		mallaStru.setObjetoCurricular(objetoCurricular);
		
		resultado = mallaCurricularService.guardar(mallaStru);
		MallaCurricularDTO mallaGuardada = resultado.getDto();
		List<ParametroWSMoodleDTO> plataformas = parametroWSMoodleService.findAll();
		if (!ObjectUtils.isNullOrEmpty(plataformas)) {

			for (ParametroWSMoodleDTO ptf : plataformas) {
				CrearCategoria categoriaWS = new CrearCategoria(ptf);
				Categoria mallaCategoria = new Categoria();

				mallaCategoria.setName(mallaGuardada.getNombre());
				mallaCategoria.setParent(mallaPadre.getIdCategoriaMdl());
				
				ArrayList<Categoria> categorias = new ArrayList<Categoria>();
				categorias.add(mallaCategoria);

				List<RespuestaCrearCategorias> respuestasWS = categoriaWS.crearCategoria(categorias);
				if (ObjectUtils.isNotNull(respuestasWS)) {
					int idCategoriaMdl = respuestasWS.get(ConstantesGestor.PRIMER_ELEMENTO).getId();
					mallaGuardada.setIdCategoriaMdl(idCategoriaMdl);
					mallaGuardada.setFechaActualizacion(new Date());
					
					resultado = mallaCurricularService.actualizar(mallaGuardada);
					
				} else {
					logger.error("Falló al crear la categoria para la malla curricular");
					throw new ErrorWS("Problemas al crear la categoria");
				}
			}
		} else {
			return null;
		}

		return resultado;
	}

	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<PlanDTO> editarPlan(PlanDTO plan, List<CatalogoComunDTO> habilidadesPlan,
			List<CatalogoComunDTO> aptitudesPlan, List<CatalogoComunDTO> conocimientosPlan, RelMallaPlanDTO relMallaPlan) throws Exception {

		ResultadoDTO<PlanDTO> resultado = null;
		List<RelPlanHabilidadDTO> relHabilidades;
		List<RelPlanConocimientoDTO> relConocimientos;
		List<RelPlanAptitudDTO> relAptitudes;
		Date fechaAct = new Date();

		try {

			plan.setRelPlanAptitudes(null);
			plan.setRelPlanConocimientos(null);
			plan.setRelPlanHabilidades(null);

			planService.eliminaConocimientosPorIdPlan(plan.getIdPlan());
			planService.eliminAptitudesPorIdPlan(plan.getIdPlan());
			planService.eliminaHabilidadesPorIdPlan(plan.getIdPlan());

			logger.debug("Editando el nuevo plan");
			plan.setFechaActualizacion(fechaAct);

			// RN: Actualizacion de Nombre de malla curricular
			MallaCurricularDTO mallaCurr = mallaCurricularService.obtenerMallaCurricularPorIdPlan(plan.getIdPlan());
			mallaCurr.setNombre(plan.getNombre());
			mallaCurr.setFechaActualizacion(fechaAct);
			mallaCurr.setUsuarioModifico(plan.getUsuarioModifico());
			// mallaCurr.getBitacoraDTO().setIdUsuario(plan.getBitacoraDTO().getIdUsuario());
			// mallaCurr.getBitacoraDTO().setFechaBitacora(new Date());
			// mallaCurr.getBitacoraDTO().setIp(plan.getBitacoraDTO().getIp());
			// mallaCurr.getBitacoraDTO().setNavegador(plan.getBitacoraDTO().getNavegador());
			// mallaCurr.getBitacoraDTO().setFuncion(ConstantesBitacora.MALLA_CUR_EDITAR);
			mallaCurricularService.actualizar(mallaCurr);

			// Se generan las Relaciones de acuerdo a las listas de seleccion multiple
			if (!ObjectUtils.isNullOrEmpty(habilidadesPlan)) {
				relHabilidades = new ArrayList<>();
				for (CatalogoComunDTO habilidad : habilidadesPlan) {

					RelPlanHabilidadDTO relHabil = new RelPlanHabilidadDTO();
					relHabil.setFechaRegistro(plan.getFechaActualizacion());
					relHabil.setActivo(ConstantesGestor.ACTIVO);
					relHabil.setUsuarioModifico(plan.getUsuarioModifico());
					relHabil.setTblPlan(plan);
					relHabil.setCatHabilidadesPlan(habilidad);

					relHabilidades.add(relHabil);
				}
				plan.setRelPlanHabilidades(relHabilidades);
			}

			if (!ObjectUtils.isNullOrEmpty(aptitudesPlan)) {
				relAptitudes = new ArrayList<>();
				for (CatalogoComunDTO aptitud : aptitudesPlan) {
					RelPlanAptitudDTO relApt = new RelPlanAptitudDTO();
					relApt.setFechaRegistro(plan.getFechaActualizacion());
					relApt.setActivo(ConstantesGestor.ACTIVO);
					relApt.setUsuarioModifico(plan.getUsuarioModifico());
					relApt.setTblPlan(plan);
					relApt.setCatAptitudesPlan(aptitud);

					relAptitudes.add(relApt);
				}
				plan.setRelPlanAptitudes(relAptitudes);
			}

			if (!ObjectUtils.isNullOrEmpty(conocimientosPlan)) {
				relConocimientos = new ArrayList<>();
				for (CatalogoComunDTO conoc : conocimientosPlan) {
					RelPlanConocimientoDTO relConoc = new RelPlanConocimientoDTO();
					relConoc.setFechaRegistro(plan.getFechaRegistro());
					relConoc.setActivo(ConstantesGestor.ACTIVO);
					relConoc.setUsuarioModifico(plan.getUsuarioModifico());
					relConoc.setTblPlan(plan);
					relConoc.setCatAreasConocimiento(conoc);

					relConocimientos.add(relConoc);
				}
				plan.setRelPlanConocimientos(relConocimientos);
			}
			
			if(ObjectUtils.isNotNull(plan.getCatEstatusPlan())){
				if(plan.getCatEstatusPlan().getNombre().equals("Ejecución")){
					resultado = guardarDatosMoodle(plan, relMallaPlan);
					
					if(ObjectUtils.isNotNull(resultado) || ObjectUtils.isNotNull(resultado.getDto()) ){
						plan = resultado.getDto();
						
						relMallaPlan.setActivo(0);
					}else{
						throw new Exception("¡No se pudieron guardar lo cambios en Mooodle!");
					}
				}
			}
			
			if(ObjectUtils.isNotNull(relMallaPlan)) {
				mallaPlanService.actualizar(relMallaPlan);
			}

			resultado = planService.actualizar(plan);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
			throw e;
		}

		return resultado;
	}

	public String generaIdentificadorPlan() {
		String identif = "";
		String idPlan = "";

		Integer maxId = planService.obtieneUltimoIdPlanes();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String anio = Integer.toString(cal.get(Calendar.YEAR)).substring(2, 4);

		if (ObjectUtils.isNotNull(maxId)) {
			maxId = maxId + 1;
			if (maxId < 10) {
				idPlan = "0".concat(maxId.toString());
			} else {
				idPlan = maxId.toString();
			}

			identif = idPlan.concat("-").concat(anio);
		} else {
			identif = ("01-").concat(anio);
		}

		return identif;
	}

	/**
	 * @return the planService
	 */
	public PlanService getPlanService() {
		return planService;
	}

	/**
	 * @param planService the planService to set
	 */
	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}

	/**
	 * @return the parametroWSMoodleService
	 */
	public ParametroWSMoodleService getParametroWSMoodleService() {
		return parametroWSMoodleService;
	}

	/**
	 * @param parametroWSMoodleService the parametroWSMoodleService to set
	 */
	public void setParametroWSMoodleService(ParametroWSMoodleService parametroWSMoodleService) {
		this.parametroWSMoodleService = parametroWSMoodleService;
	}

}
