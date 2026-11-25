package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import mx.gob.sedesol.basegestor.service.admin.TextoSistemaService;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AdeudoOptativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AprobacionAsignaturasPorSemestreDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaNuevoIngresoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaIrregularDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaRegularDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionOmisionReinscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoInscripcionEstudianteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CupoOptativaDTO;
import mx.gob.sedesol.basegestor.commons.utils.CupoOptativaException;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReenvioCorreoMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoElectivasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResumenSeleccionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TerminosCondicionesDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionPreviaException;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionFacade;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.basegestor.service.inscripcion.EnvioCorreoService;

@Service("inscripcionFacade")
public class InscripcionFacadeImpl implements InscripcionFacade {

	@org.springframework.beans.factory.annotation.Autowired
	private TextoSistemaService textosInscripcion;


	private static final Logger logger = Logger.getLogger(InscripcionFacadeImpl.class);

	@Autowired
	private InscripcionService inscripcionService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	@Autowired
	private CorreoElectronicoService correoElectronicoService;

	@Autowired
	private EnvioCorreoService envioCorreoService;

	@Transactional(readOnly = true)
	@Override
	public InscripcionContextoDTO obtenerContextoInscripcion(Long idPersona) throws InscripcionException {
		InscripcionContextoDTO actual = construirContextoInscripcion(idPersona, true);
		validarVigenciaProcesos(actual, false);
        registrarEvaluacion(actual, null, "APERTURA");
        return actual;
	}

	@Transactional(readOnly = true)
	@Override
	public InscripcionContextoDTO obtenerContextoInscripcionConsulta(Long idPersona) throws InscripcionException {
		return construirContextoInscripcion(idPersona, false);
	}

	@Override
	@Transactional(readOnly = true)
	public void validarPropuestaInscripcion(Long idPersona, List<Long> idsProgramas) throws InscripcionException {
		InscripcionContextoDTO contexto = construirContextoInscripcion(idPersona, false);
		validarVigenciaProcesos(contexto, false);
        validarOportunidades(contexto,false);
		validarConclusionEstudios(contexto, true);
		validarOmisionReinscripcion(contexto, true);
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponibles(contexto);
		Set<Long> idsSeleccionados = idsProgramas != null ? new HashSet<Long>(idsProgramas) : Collections.<Long>emptySet();
		Set<Long> idsOfertados = materiasDisponibles.stream()
				.filter(materia -> materia != null && materia.getIdPrograma() != null)
				.map(InscripcionMateriasDTO::getIdPrograma).collect(Collectors.toSet());

		for (Long idPrograma : idsSeleccionados) {
			if (idPrograma != null && !idsOfertados.contains(idPrograma)) {
				throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.unidad.didactica.seleccionada.no.forma.parte.de"));
			}
		}
		for (InscripcionMateriasDTO materia : materiasDisponibles) {
			if (materia != null) {
				materia.setCheck(Boolean.valueOf(materia.getIdPrograma() != null
						&& idsSeleccionados.contains(materia.getIdPrograma())));
			}
		}
		for (InscripcionMateriasDTO materia : materiasDisponibles) {
			if (materia != null && Boolean.TRUE.equals(materia.getCheck())) {
				validarSeleccionMateria(materia, contexto);
			}
		}
		validarSeleccionMateriasSegunEstatusAcademico(contexto);
	}

    private void prepararEvidenciaEvaluacion(InscripcionContextoDTO c, Date fecha, String evidencia) throws InscripcionException {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper=new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.node.ObjectNode e=(com.fasterxml.jackson.databind.node.ObjectNode)mapper.readTree(
                evidencia);
            c.setSemestreReferencia(e.path("semestreReferencia").isNumber()?e.path("semestreReferencia").asInt():null);
            c.setMensajeReferencia(e.path("conciliacion").asText());
            ordenarOptativasPorSemestreReferencia(c);
            e.set("estadoAcademico",mapper.valueToTree(c.getEstadoAcademico()));
            e.set("limitesCarga",mapper.valueToTree(c.getLimitesCargaAcademica()));
            e.set("adscripcion",mapper.valueToTree(c.getInscripcionPersona()));
            e.set("conclusionEstudios",mapper.valueToTree(c.getConclusionEstudios()));
            e.set("oportunidadesPorUD",mapper.valueToTree(c.getOportunidades()));
            e.set("configuracionOportunidades",mapper.valueToTree(c.getConfiguracionOportunidades()));
            e.put("mensajeSeriacion",c.getMensajeSeriacion());
            e.put("mensajeLimiteReprobaciones",c.getMensajeLimiteReprobacionesAlcanzado());
            e.put("mensajeOmision",c.getMensajeOmisionReinscripcion());
            e.put("permiteOmision",Boolean.TRUE.equals(c.getPermiteInscripcionOmision()));
            com.fasterxml.jackson.databind.node.ArrayNode resultados=mapper.createArrayNode();
            for(com.fasterxml.jackson.databind.JsonNode fila:e.path("historial")) {
                boolean baja=fila.path(8).asInt()==0;
                String causa=baja?"NP_POR_BAJA_AUTORIZADA":fila.path(6).isNull()?"EN_CURSO":
                    fila.path(6).asDouble()>=fila.path(7).asDouble()?"ACREDITADA":"REPROBACION";
                String nombre=normalizarNombreMateria(fila.path(9).asText());
                boolean vigente="REPROBACION".equals(causa) && c.getEstadoAcademico().getMateriasReprobadas().stream().anyMatch(d ->
                    Objects.equals(d.getIdPrograma(),fila.path(3).asLong()) || (!nombre.isEmpty() && nombre.equals(normalizarNombreMateria(d.getNombrePrograma()))));
                com.fasterxml.jackson.databind.node.ObjectNode r=resultados.addObject();
                r.put("participacion",fila.path(0).asLong());r.put("causa",causa);r.put("adeudoVigentePorIdentidad",vigente);
                r.put("intentoReprobado",!baja && "REPROBACION".equals(causa));
                r.put("criterio",baja?"1.2: baja aplicable; NP sin irregularidad ni oportunidad consumida":
                    "EN_CURSO".equals(causa)?"1.2: NULL no equivale a reprobación":
                    vigente?"1.2: adeudo vigente; identidad del plan destino":
                    "REPROBACION".equals(causa)?"1.2: registro histórico; contrastado con adeudos y cadenas vigentes":"1.2: acreditación válida");
            }
            e.set("resultadoPorRegistro",resultados);
            c.setEvidenciaEvaluacion(mapper.writeValueAsString(e)); c.setFechaEvaluacion(fecha);
        } catch(java.io.IOException ex) { throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.se.pudo.preparar.la.evidencia.de.evaluacion"),ex); }
    }
    private void registrarEvaluacion(InscripcionContextoDTO actual, InscripcionContextoDTO anterior, String momento) {
        Long proceso=actual.getEstadoAcademico().getIdProcesoEvaluado();
        inscripcionService.registrarEvaluacion(actual.obtenerIdPersona(),actual.getInscripcionPersona().getIdPlan(),proceso,
            momento,anterior==null?null:anterior.getEvidenciaEvaluacion(),actual.getEvidenciaEvaluacion());
    }
    private void copiarEvaluacion(InscripcionContextoDTO destino, InscripcionContextoDTO actual) {
        destino.setOportunidades(actual.getOportunidades());destino.setConfiguracionOportunidades(actual.getConfiguracionOportunidades());destino.setAceptaUltimaOportunidad(false);
        destino.setConclusionEstudios(actual.getConclusionEstudios());
        destino.setInscripcionPersona(actual.getInscripcionPersona()); destino.setEstadoAcademico(actual.getEstadoAcademico());
        destino.setLimitesCargaAcademica(actual.getLimitesCargaAcademica()); destino.setCreditosTotalesPlan(actual.getCreditosTotalesPlan());
        destino.setTerminosCondiciones(actual.getTerminosCondiciones()); destino.setMensajeSeriacion(actual.getMensajeSeriacion());
        destino.setMensajeLimiteReprobacionesAlcanzado(actual.getMensajeLimiteReprobacionesAlcanzado());
        destino.setPermiteInscripcionLimiteReprobadas(actual.getPermiteInscripcionLimiteReprobadas());
        destino.setMensajeOmisionReinscripcion(actual.getMensajeOmisionReinscripcion()); destino.setPermiteInscripcionOmision(actual.getPermiteInscripcionOmision());
        destino.setPeriodosSinReinscripcion(actual.getPeriodosSinReinscripcion()); destino.setEvidenciaEvaluacion(actual.getEvidenciaEvaluacion());
        destino.setSemestreReferencia(actual.getSemestreReferencia()); destino.setMensajeReferencia(actual.getMensajeReferencia());
        destino.setFechaEvaluacion(actual.getFechaEvaluacion());
    }
    @Override
    @Transactional(readOnly = true)
    public InscripcionContextoDTO actualizarEvaluacion(InscripcionContextoDTO anterior) throws InscripcionException {
        InscripcionContextoDTO actual=construirContextoInscripcion(anterior.obtenerIdPersona(),false);
        boolean cambio=!Objects.equals(anterior.getEvidenciaEvaluacion(),actual.getEvidenciaEvaluacion());
        if(cambio) registrarEvaluacion(actual,anterior,"REEVALUACION");
        if(!cambio && !actual.getEstadoAcademico().getMateriasDisponibles().isEmpty()) validarYRestaurarSeleccionActual(anterior,actual);
        if(!cambio)actual.setAceptaUltimaOportunidad(anterior.isAceptaUltimaOportunidad());
        return actual;
    }

	private InscripcionContextoDTO construirContextoInscripcion(Long idPersona, boolean validarInscripcionPrevia)
			throws InscripcionException {
		validarHabilitacionInscripcion(idPersona);
		Date fechaActual = new Date();

		InscripcionPersonaDTO persona = obtenerInscripcionPersona(idPersona);
		if (validarInscripcionPrevia) {
			validarInscripcionPrevia(fechaActual, persona);
		}
		TerminosCondicionesDTO terminosCondiciones = obtenerTerminosCondiciones();
		CreditosTotalesPlanDTO creditosTotalesPlan = obtenerCreditosTotalesPorPlan(persona.getIdPlan());
		LimitesCargaAcademicaDTO limitesCargaAcademica = obtenerLimitesCargaAcademicaPorPlan(persona.getIdPlan());
        String evidencia=inscripcionService.obtenerEvidenciaEvaluacion(persona.getIdPersona(),persona.getIdPlan(),fechaActual);
		EstadoAcademicoDTO estadoAcademico = obtenerEstadoAcademico(persona, fechaActual, creditosTotalesPlan,
				limitesCargaAcademica);
		InscripcionContextoDTO contexto = crearContextoInscripcion(persona, terminosCondiciones, creditosTotalesPlan,
				limitesCargaAcademica, estadoAcademico);
		validarOmisionReinscripcion(contexto, false);
        prepararOportunidades(contexto,true);
        validarConclusionEstudios(contexto, false);
        prepararEvidenciaEvaluacion(contexto, fechaActual, evidencia);
		return contexto;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = InscripcionException.class)
	@Override
	public void finalizarInscripcion(Boolean aceptaTerminos, InscripcionContextoDTO contexto)
			throws InscripcionException {
        finalizarInscripcion(aceptaTerminos, contexto, "ESTUDIANTE");
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = InscripcionException.class)
    @Override
    public void finalizarInscripcionManual(Boolean aceptaTerminos, InscripcionContextoDTO contexto)
            throws InscripcionException {
        finalizarInscripcion(aceptaTerminos, contexto, "MANUAL_SIGIE");
    }

    private void finalizarInscripcion(Boolean aceptaTerminos, InscripcionContextoDTO contexto, String viaRegistro)
            throws InscripcionException {
		validarAceptaTerminos(aceptaTerminos);
		validarInscripcionPreviaAlFinalizar(contexto);
		InscripcionContextoDTO contextoVista = contexto;
		contexto = reconstruirSeleccionAlFinalizar(contexto);
        validarConclusionEstudios(contexto, true);
		validarLimiteReprobacionesParaFinalizar(contexto);
		validarSeleccionMateriasSegunEstatusAcademico(contexto);
		contextoVista.setMensajeOmisionReinscripcion(contexto.getMensajeOmisionReinscripcion());
		contextoVista.setPermiteInscripcionOmision(contexto.getPermiteInscripcionOmision());
        registrarAvisoEnEvidencia(contexto);
		guardarInscripcion(contexto, viaRegistro);
        inscripcionService.registrarConfirmacionEvaluacion(contexto.obtenerIdPersona(),contexto.getInscripcionPersona().getIdPlan(),
            contexto.getEstadoAcademico().getIdProcesoEvaluado(),contexto.getEvidenciaEvaluacion());
		gestionarEnvioCorreoInscripcion(contexto);
	}

	/** Reevalúa la oferta y las reglas actuales antes de persistir la selección de la sesión. */
	private InscripcionContextoDTO reconstruirSeleccionAlFinalizar(InscripcionContextoDTO anterior)
			throws InscripcionException {
		InscripcionContextoDTO actual = construirContextoInscripcion(anterior.obtenerIdPersona(), false);
        boolean cambio=!Objects.equals(anterior.getEvidenciaEvaluacion(),actual.getEvidenciaEvaluacion());
        registrarEvaluacion(actual,anterior,cambio?"CAMBIO_ANTES_CONFIRMAR":"VALIDACION_CONFIRMACION");
        if(cambio) {
            copiarEvaluacion(anterior,actual);
            validarConclusionEstudios(anterior, true);
            String avisoCupo = actual.getEstadoAcademico().getAvisoCupoOptativas();
            throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.tu.historial.o.las.reglas.cambiaron.se.actualizaron", (avisoCupo.isEmpty() ? "" : avisoCupo + " ")));
        }
        try { validarYRestaurarSeleccionActual(anterior, actual); }
        catch(InscripcionException ex) { copiarEvaluacion(anterior,actual); throw ex; }
        actual.setAceptaUltimaOportunidad(anterior.isAceptaUltimaOportunidad());
		return actual;
	}

	private void validarYRestaurarSeleccionActual(InscripcionContextoDTO anterior, InscripcionContextoDTO actual)
			throws InscripcionException {
		if (!Objects.equals(anterior.getInscripcionPersona().getIdPlan(), actual.getInscripcionPersona().getIdPlan())) {
			throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.tu.plan.de.estudios.cambio.actualiza.la.pagina"));
		}
		List<InscripcionMateriasDTO> disponibles = actual.getEstadoAcademico().getMateriasDisponibles();
		Long procesoAnterior = anterior.obtenerIdProcesoInscripcionDesdeMateriasDisponibles();
		if (disponibles.isEmpty() || disponibles.stream()
				.anyMatch(materia -> !Objects.equals(procesoAnterior, materia.getIdProcesoInscripcion()))) {
			throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.oferta.o.el.proceso.de.inscripcion.cambio"));
		}
		List<InscripcionMateriasDTO> seleccionadas = anterior.obtenerMateriasSeleccionadas();
		for (InscripcionMateriasDTO seleccionada : seleccionadas) {
			if (disponibles.stream().noneMatch(materia -> mismaMateriaOfertada(materia, seleccionada))) {
				throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.la.unidad.didactica.ya.no.esta.permitida.para", seleccionada.getNombreTentativoPrograma()));
			}
		}
		for (InscripcionMateriasDTO materia : disponibles) {
			boolean seleccionada = seleccionadas.stream().anyMatch(previa -> mismaMateriaOfertada(materia, previa));
			if (Boolean.TRUE.equals(materia.getCheck()) && Boolean.TRUE.equals(materia.getDisabled())
					&& !seleccionada) {
				throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.debes.incluir.la.unidad.didactica.segun.tu.situacion", materia.getNombreTentativoPrograma()));
			}
			if (seleccionada && Boolean.TRUE.equals(materia.getDisabled()) && !Boolean.TRUE.equals(materia.getCheck())) {
				throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.la.unidad.didactica.no.esta.habilitada.para.inscripcion", materia.getNombreTentativoPrograma()));
			}
            if(seleccionada) {
                InscripcionMateriasDTO previa=seleccionadas.stream().filter(x->mismaMateriaOfertada(materia,x)).findFirst().get();
                materia.setIdAdeudoOptativa(previa.getIdAdeudoOptativa());
            }
			materia.setCheck(seleccionada);
		}
	}

	private boolean mismaMateriaOfertada(InscripcionMateriasDTO primera, InscripcionMateriasDTO segunda) {
		// La equivalencia académica no autoriza sustituir la oferta elegida por otro programa o bloque.
		return primera.getIdPrograma() != null && Objects.equals(primera.getIdPrograma(), segunda.getIdPrograma())
				&& Objects.equals(primera.getIdPlan(), segunda.getIdPlan())
				&& Objects.equals(primera.getIdProcesoInscripcion(), segunda.getIdProcesoInscripcion())
				&& Objects.equals(primera.getPeriodo(), segunda.getPeriodo())
				&& Objects.equals(primera.getEstructura(), segunda.getEstructura())
				&& Objects.equals(primera.getSubestructura(), segunda.getSubestructura());
	}

	private void validarInscripcionPreviaAlFinalizar(InscripcionContextoDTO contexto)
			throws InscripcionException {
		if (contexto == null || contexto.getInscripcionPersona() == null
				|| contexto.obtenerIdPersona() == null) {
			throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.existe.una.inscripcion.disponible.para.finalizar.actualiza"));
		}
		Long idProceso;
		try {
			idProceso = contexto.obtenerIdProcesoInscripcionDesdeMateriasDisponibles();
		} catch (IllegalStateException e) {
			throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.se.pudo.identificar.el.proceso.de.inscripcion"), e);
		}
		Long idPersona = contexto.obtenerIdPersona();
		inscripcionService.bloquearPersonaParaInscripcion(idPersona);
		// Consultar el estado después de adquirir el bloqueo: un formulario abierto
		// no autoriza persistir si la persona dejó de estar activa en SIGIE.
		validarHabilitacionInscripcion(idPersona);
		validarOmisionReinscripcion(contexto, true);
		validarVigenciaProcesos(contexto, true);
		if (inscripcionService.existeInscripcionEnProceso(idPersona, idProceso)) {
			throw new InscripcionPreviaException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.ya.cuenta.con.una.inscripcion.para.este.periodo"));
		}
	}

	private void gestionarEnvioCorreoInscripcion(InscripcionContextoDTO contexto) {
		if (!estaHabilitadoEnvioCorreoInscripcion()) {
			registrarResultadoEnvioCorreoInscripcion(contexto, ConstantesGestor.NO_SE_ENVIO_CORREO);
			return;
		}
		boolean correoEnviado = intentarEnviarCorreoInscripcionAlEstudiante(contexto);
		int estadoEnvioCorreo = obtenerEstadoEnvioCorreoInscripcion(correoEnviado);
		registrarResultadoEnvioCorreoInscripcion(contexto, estadoEnvioCorreo);
	}

	private int obtenerEstadoEnvioCorreoInscripcion(boolean correoEnviado) {
		return correoEnviado ? ConstantesGestor.SI_SE_ENVIO_CORREO : ConstantesGestor.NO_SE_ENVIO_CORREO;
	}

	private void registrarResultadoEnvioCorreoInscripcion(InscripcionContextoDTO contexto, int estadoEnvioCorreo) {
		Long idPersona = contexto.obtenerIdPersona();
		Long idProcesoInscripcion = contexto.obtenerIdProcesoInscripcionDesdeMateriasDisponibles();

		// Por si alguna vez eliminan una inscripcion y olvidan eliminar el registro de
		// envio de correo
		// se deja esta actualización
		if (existeRegistroResultadoEnvioCorreo(idPersona, idProcesoInscripcion)) {
			envioCorreoService.actualizarResultadoEnvioCorreoInscripcion(idPersona, idProcesoInscripcion,
					estadoEnvioCorreo);
		} else {
			envioCorreoService.registrarResultadoEnvioCorreoInscripcion(idPersona, idProcesoInscripcion,
					estadoEnvioCorreo);
		}
	}

	private boolean existeRegistroResultadoEnvioCorreo(Long idPersona, Long idProcesoInscripcion) {
		return envioCorreoService.existeRegistroResultadoEnvioCorreoInscripcion(idPersona, idProcesoInscripcion);
	}

	private boolean estaHabilitadoEnvioCorreoInscripcion() {
		String enviarCorreoInscripcion = obtenerParametroSistemaEnvioCorreoInscripcion();

		return estaActivoEnvioCorreoInscripcionParametroSistema(enviarCorreoInscripcion);
	}

	private boolean estaActivoEnvioCorreoInscripcionParametroSistema(String enviarCorreoInscripcion) {
		return enviarCorreoInscripcion != null
				&& enviarCorreoInscripcion.equalsIgnoreCase(ConstantesGestor.ENVIO_DE_CORREO_INSCRIPCION_ACTIVO);
	}

	private String obtenerParametroSistemaEnvioCorreoInscripcion() {
		return parametroSistemaService.obtenerParametro(ConstantesGestor.ENVIAR_CORREO_INSCRIPCION);
	}

	private Date validarVigenciaProcesos(InscripcionContextoDTO contexto, boolean bloquear) throws InscripcionException {
		if (contexto == null || contexto.getInscripcionPersona() == null || contexto.getEstadoAcademico() == null)
			throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.existe.un.proceso.disponible.para.inscribir.consulta"));
		InscripcionPersonaDTO persona = contexto.getInscripcionPersona();
		java.util.Map<Long, java.util.Set<Long>> procesos = new java.util.TreeMap<>();
		Long principal = contexto.getEstadoAcademico().getIdProcesoEvaluado();
		if (principal == null) {
			try { principal = contexto.obtenerIdProcesoInscripcionDesdeMateriasDisponibles(); }
			catch (IllegalStateException ex) { throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.existe.un.proceso.de.inscripcion.activo.y"), ex); }
		}
		if (persona.getIdPlan() == null) throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.se.pudo.identificar.el.plan.de.inscripcion"));
		procesos.computeIfAbsent(principal, id -> new java.util.TreeSet<>()).add(persona.getIdPlan());
		for (InscripcionMateriasDTO materia : contexto.getEstadoAcademico().getMateriasDisponibles()) {
			if (!Boolean.TRUE.equals(materia.getCheck())) continue;
			if (materia.getIdProcesoInscripcion() == null || materia.getIdPlan() == null)
				throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.se.pudo.identificar.el.proceso.o.plan"));
			procesos.computeIfAbsent(materia.getIdProcesoInscripcion(), id -> new java.util.TreeSet<>()).add(materia.getIdPlan());
		}
		Date fecha = new Date();
		for (java.util.Map.Entry<Long, java.util.Set<Long>> proceso : procesos.entrySet()) {
			for (Long plan : proceso.getValue()) {
				if (!Boolean.TRUE.equals(inscripcionService.procesoVigenteParaInscripcion(proceso.getKey(), plan,
						persona.getIdConvocatoria(), fecha, bloquear)))
					throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.proceso.de.inscripcion.esta.inactivo.o.fuera"));
			}
		}
		return fecha;
	}

	private void guardarInscripcion(InscripcionContextoDTO contexto, String viaRegistro) throws InscripcionException {
		Date fechaRegistro = validarVigenciaProcesos(contexto, true);
		List<InscripcionInsertDTO> inscripciones = mapearModelosInscripcion(contexto);
		inscripciones.forEach(inscripcion -> {
            inscripcion.setFechaRegistro(fechaRegistro);
            inscripcion.setViaRegistro(viaRegistro);
        });
        try {
            inscripcionService.insertarInscripciones(inscripciones);
        } catch (CupoOptativaException ex) {
            registrarRechazoInscripcion(contexto, ex.getMomentoEvaluacion(), ex.getMessage());
            throw new InscripcionException(ex.getMessage(), ex);
        } catch(IllegalArgumentException ex) {
            // La transacción se revierte y la vista conserva el motivo que debe atenderse.
            throw new InscripcionException(ex.getMessage(),ex);
        }
	}

    private void registrarRechazoInscripcion(InscripcionContextoDTO contexto, String momento, String mensaje) {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        com.fasterxml.jackson.databind.node.ObjectNode evidencia = mapper.createObjectNode();
        evidencia.put("motivo", mensaje);
        inscripcionService.registrarEvaluacion(contexto.obtenerIdPersona(), contexto.getInscripcionPersona().getIdPlan(),
            contexto.getEstadoAcademico().getIdProcesoEvaluado(), momento,
            contexto.getEvidenciaEvaluacion(), evidencia.toString());
    }

	private boolean intentarEnviarCorreoInscripcionAlEstudiante(InscripcionContextoDTO contexto) {
		CorreoDTO correo = crearContenidoCorreo(contexto);
		return intentarEnviarCorreo(correo);
	}

	private CorreoDTO crearContenidoCorreo(InscripcionContextoDTO contexto) {
		List<InscripcionMateriasDTO> materias = obtenerMateriasSeleccionas(contexto);

		String anioPeriodo = construirAnioPeriodo(materias);
		String nombreCompleto = construirNombreCompleto(contexto);
		String bloquesConMaterias = construirTextoBloquesConMaterias(materias);
		String correoDestinatario = obtenerCorreoDestinatario(contexto);
		CorreoDTO correo = crearCorreoConfirmacionInscripcion(correoDestinatario, nombreCompleto, anioPeriodo,
				bloquesConMaterias);
		return correo;
	}

	private String obtenerCorreoDestinatario(InscripcionContextoDTO contexto) {
		InscripcionPersonaDTO inscripcion = contexto.getInscripcionPersona();
		String correoDestinatario = inscripcion.getCorreo();
		return correoDestinatario;
	}

	private List<InscripcionMateriasDTO> obtenerMateriasSeleccionas(InscripcionContextoDTO contexto) {
		return contexto.obtenerMateriasSeleccionadas();
	}

	private String construirAnioPeriodo(List<InscripcionMateriasDTO> materias) {
		Integer periodo = obtenerPeriodoDePrimerMateria(materias);
		String anio = obtenerParametroSistemaAnioInscripcion();
		return crearFormatoAnioPeriodo(anio, periodo);
	}

	private Integer obtenerPeriodoDePrimerMateria(List<InscripcionMateriasDTO> materias) {
		if (materias == null || materias.isEmpty()) {
			throw new IllegalArgumentException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.lista.de.materias.al.construir.el.correo"));
		}
		InscripcionMateriasDTO primerMateria = materias.get(0);
		return primerMateria.getPeriodo();
	}

	private String crearFormatoAnioPeriodo(String anio, Integer periodo) {
		return anio + "-" + periodo;
	}

	private InscripcionPersonaDTO obtenerInscripcionPersona(InscripcionContextoDTO contexto) {
		return contexto.getInscripcionPersona();
	}

	private String construirContenidoCorreo(String nombreCompleto, String periodo, String bloquesConMaterias,
			String informacionExtraCorreo, String urlPaginaInicio) {

		return MessageFormat.format("<p>Estimado estudiante <strong>{0}</strong>.</p>"
				+ "<p>Te confirmamos que has concluido exitosamente tu inscripción. A continuación, te mostramos tus unidades didácticas para el período <strong>{1}</strong>:</p>"
				+ "<div>{2}</div>"
				+ "<p>Podrás acceder a través de la siguiente url <a href=\"{3}\" target=\"_blank\">{3}</a> ingresando tu usuario y contraseña.</p>"
				+ "<p>{4}</p>", nombreCompleto, periodo, bloquesConMaterias, urlPaginaInicio, informacionExtraCorreo);
	}

	private CorreoDTO crearCorreoConfirmacionInscripcion(String correoDestino, String nombreCompleto, String periodo,
			String bloquesConMaterias) {

		CorreoDTO correo = correoElectronicoService.asignaParametrosConfigCorreo();
		correo.setAsunto(obtenerAsuntoCorreoInscripcionParametroSistema());
		correo.setDestinatarios(Collections.singletonList(correoDestino));
		correo.setCorreoRemitente(obtenerParametroSistemaCuentaAdminCorreo());
		correo.setNombreRemitente(obtenerParametroSistemaNombreRemitenteCorreo());

		String informacionExtraCorreo = obtenerInformacionExtraCorreoParametroSistema();

		String urlPaginaInicio = obtenerUrlPaginaInicioParametroSistema();

		String contenido = construirContenidoCorreo(nombreCompleto, periodo, bloquesConMaterias, informacionExtraCorreo,
				urlPaginaInicio);
		correo.setContenido(contenido);

		return correo;
	}

	private String obtenerParametroSistemaNombreRemitenteCorreo() {
		String nombreRemitente = parametroSistemaService
				.obtenerParametro(ConstantesGestor.REMITENTE_CORREO_INSCRIPCION);
		return nombreRemitente != null ? nombreRemitente
				: ConstantesGestor.NOMBRE_POR_DEFECTO_REMITENTE_CORREO_INSCRIPCION;
	}

	private String obtenerAsuntoCorreoInscripcionParametroSistema() {
		String asunto = parametroSistemaService.obtenerParametro(ConstantesGestor.ASUNTO_CORREO_INSCRIPCION);
		return asunto != null ? asunto : ConstantesGestor.ASUNTO_POR_DEFECTO_CORREO_INSCRIPCION;
	}

	private String obtenerUrlPaginaInicioParametroSistema() {
		return parametroSistemaService.obtenerParametro(ConstantesGestor.URL_PAGINA_INICIO);
	}

	private String obtenerInformacionExtraCorreoParametroSistema() {
		return parametroSistemaService.obtenerParametro(ConstantesGestor.INFORMACION_EXTRA_CORREO_INSCRIPCION);
	}

	private String obtenerParametroSistemaCuentaAdminCorreo() {
		return parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave());
	}

	private String construirTextoBloquesConMaterias(List<InscripcionMateriasDTO> materiasSeleccionadas) {
		Map<String, List<InscripcionMateriasDTO>> materiasPorBloque = agruparMateriasPorBloqueOrdenadas(
				materiasSeleccionadas);

		return construirHtmlBloques(materiasPorBloque);
	}

	/**
	 * <pre>
	 * Agrupa las materias por subestructura (bloque) y:
	 *  - ordena los bloques alfabéticamente (TreeMap)
	 *  - ordena las materias de cada bloque por nombre de programa
	 * </pre>
	 */
	private Map<String, List<InscripcionMateriasDTO>> agruparMateriasPorBloqueOrdenadas(
			List<InscripcionMateriasDTO> materiasSeleccionadas) {

		return materiasSeleccionadas.stream()
				.collect(Collectors.groupingBy(InscripcionMateriasDTO::getSubestructura, TreeMap::new,
						Collectors.collectingAndThen(Collectors.toList(), lista -> lista.stream()
								.sorted(Comparator.comparing(InscripcionMateriasDTO::getNombreTentativoPrograma))
								.collect(Collectors.toList()))));
	}

	/**
	 * <pre>
	 * Construye el HTML final a partir del mapa:
	 *
	 * Bloque 1
	 *   * materia A
	 *   * materia B
	 *
	 * Bloque 2
	 *   * materia C
	 * </pre>
	 */
	private String construirHtmlBloques(Map<String, List<InscripcionMateriasDTO>> materiasPorBloque) {
		StringBuilder html = new StringBuilder();

		materiasPorBloque.forEach((subestructura, materias) -> {
			html.append(construirTituloBloque(subestructura));
			html.append(construirListaMateriasHtml(materias));
		});

		return html.toString();
	}

	private String construirTituloBloque(String subestructura) {
		return new StringBuilder().append("<p><strong>").append(subestructura).append("</strong></p>").toString();
	}

	private String construirListaMateriasHtml(List<InscripcionMateriasDTO> materias) {
		StringBuilder html = new StringBuilder("<ul>");

		for (InscripcionMateriasDTO materia : materias) {
			html.append("<li>").append(materia.getNombreTentativoPrograma()).append("</li>");
		}

		html.append("</ul>");
		return html.toString();
	}

	private String construirTextoNombreCompleto(ReenvioCorreoInscripcionDTO inscripcion) {
		StringBuilder nombreCompleto = new StringBuilder();

		if (inscripcion.getNombre() != null) {
			nombreCompleto.append(inscripcion.getNombre());
		}
		if (inscripcion.getPrimerApellido() != null) {
			nombreCompleto.append(" ").append(inscripcion.getPrimerApellido());
		}
		if (inscripcion.getSegundoApellido() != null) {
			nombreCompleto.append(" ").append(inscripcion.getSegundoApellido());
		}

		return nombreCompleto.toString().trim();
	}

	private String construirNombreCompleto(InscripcionContextoDTO contexto) {
		InscripcionPersonaDTO inscripcion = contexto.getInscripcionPersona();
		StringBuilder nombreCompleto = new StringBuilder();

		if (inscripcion.getNombre() != null) {
			nombreCompleto.append(inscripcion.getNombre());
		}
		if (inscripcion.getPrimerApellido() != null) {
			nombreCompleto.append(" ").append(inscripcion.getPrimerApellido());
		}
		if (inscripcion.getSegundoApellido() != null) {
			nombreCompleto.append(" ").append(inscripcion.getSegundoApellido());
		}

		return nombreCompleto.toString().trim();
	}

	private List<InscripcionInsertDTO> mapearModelosInscripcion(InscripcionContextoDTO contexto) {
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponibles(contexto);
		return materiasDisponibles.stream().filter(materia -> materia.getCheck())
				.map(materia -> convertirAModeloInscripcion(materia, contexto)).collect(Collectors.toList());
	}

	private InscripcionInsertDTO convertirAModeloInscripcion(InscripcionMateriasDTO materia,
			InscripcionContextoDTO contexto) {
		InscripcionInsertDTO dto = new InscripcionInsertDTO();

		dto.setIdPersona(obtenerInscripcionPersona(contexto).getIdPersona());
		dto.setPrograma(materia.getNombrePlan());
		dto.setAsignatura(materia.getNombreTentativoPrograma());
		dto.setGroupBase(generarGroupBase(materia));
		dto.setIdPlan(materia.getIdPlan());
		dto.setIdPrograma(materia.getIdPrograma());
		dto.setIdEvento(ConstantesGestor.SIN_EVENTO);
		dto.setNivel(generarAcronimoTresLetras(materia.getNivelEnsenanza()));
		dto.setDivision(generarAcronimo(materia.getDivision()));
		dto.setProfileFieldPerfil(obtenerPerfil(materia));
		dto.setBloque(obtenerBloque(materia));
		dto.setClaveAsig(materia.getClavePrograma());
		dto.setNuevoIngreso(obtenerEstatusNuevoIngreso(contexto));
		dto.setRecursamiento(obtenerEstatusRecursamiento(contexto, materia));
		dto.setAlta(ConstantesGestor.SOLICITO_ALTA_EL_ESTUDIANTE);
		dto.setSemestre(InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura()));
		dto.setFechaRegistro(new Date());
        dto.setOptativa(InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()));
        dto.setIdAdeudoOptativa(materia.getIdAdeudoOptativa());
        dto.setIdProcesoInscripcion(materia.getIdProcesoInscripcion());
        dto.setIdConvocatoria(contexto.getInscripcionPersona().getIdConvocatoria());

		return dto;
	}

	private int obtenerEstatusRecursamiento(InscripcionContextoDTO contexto, InscripcionMateriasDTO materia) {
		List<InscripcionBajasDTO> materiasBajas = contexto.getEstadoAcademico().getMateriasBajas();
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = contexto.getEstadoAcademico()
				.getMateriasReprobadas();
		return verificarRecursamiento(materiasBajas, materiasReprobadas, materia);
	}

	private int verificarRecursamiento(List<InscripcionBajasDTO> materiasBajas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, InscripcionMateriasDTO materia) {

		if (esMateriaDadaDeBaja(materiasBajas, materia.getClavePrograma())) {
			return ConstantesGestor.CURSANDO_MATERIA_POR_PRIMERA_VEZ;
		}

		if (esMateriaReprobadaPorNombre(materiasReprobadas, materia.getNombreTentativoPrograma())) {
			return ConstantesGestor.RECURSANDO_MATERIA;
		}

		return ConstantesGestor.CURSANDO_MATERIA_POR_PRIMERA_VEZ;
	}

	private boolean esMateriaDadaDeBaja(List<InscripcionBajasDTO> materiasBajas, String clavePrograma) {
		return materiasBajas.stream().anyMatch(materia -> materia.getClavePrograma().equalsIgnoreCase(clavePrograma));
	}

	private Integer obtenerEstatusNuevoIngreso(InscripcionContextoDTO contexto) {
		return contexto.getEstadoAcademico().getEsNuevoIngreso() ? ConstantesGestor.ES_ESTUDIANTE_NUEVO_INGRESO
				: ConstantesGestor.NO_ES_ESTUDIANTE_NUEVO_INGRESO;
	}

	private int obtenerBloque(InscripcionMateriasDTO materia) {
		return Integer.parseInt(materia.getSubestructura().replaceAll("[^0-9]", ""));
	}

	private String obtenerPerfil(InscripcionMateriasDTO materia) {
		return materia.getPerfil() != null ? materia.getPerfil() : "";
	}

	private String generarGroupBase(InscripcionMateriasDTO materia) {
		StringBuilder sb = new StringBuilder();
		sb.append(generarAcronimoMayusculas(materia.getNombrePlan()));
		sb.append("-");
		String bloque = materia.getSubestructura().trim().substring(0, 1) + obtenerBloque(materia);
		String semestre = materia.getEstructura().trim().substring(0, 1)
				+ InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura());
		String clave = materia.getClavePrograma();
		sb.append(clave);
		if (!clave.endsWith(bloque + semestre)) {
			sb.append(bloque).append(semestre);
		}
		sb.append("-");
		sb.append(generarPeriodo(materia));
		sb.append("-");
		sb.append(bloque);
		sb.append("-000");
		return sb.toString();
	}

	private String generarPeriodo(InscripcionMateriasDTO materia) {

		// Obtener el año actual
		String year = parametroSistemaService.obtenerParametro(ConstantesGestor.ANIO_INSCRIPCION);

		// Obtener los últimos dos dígitos del año
		String yearLastTwoDigits = String.valueOf(year).substring(2);

		// Verificar si el semestre es de dos dígitos; si es así, no agregar el "0"
		if (materia.getPeriodo() >= 10) {
			return yearLastTwoDigits + materia.getPeriodo();
		} else {
			// Para semestres de un solo dígito, agregar el "0" antes del número
			return yearLastTwoDigits + "0" + materia.getPeriodo();
		}
	}

	private String generarAcronimoMayusculas(String texto) {
		StringBuilder acronimo = new StringBuilder();
		for (char c : texto.toCharArray()) {
			if (Character.isUpperCase(c)) {
				acronimo.append(c);
			}
		}
		return acronimo.toString();
	}

	private String generarAcronimo(String texto) {
		StringBuilder acronimo = new StringBuilder();
		for (char c : texto.toCharArray()) {
			if (Character.isUpperCase(c)) {
				acronimo.append(c);
			}
		}
		return acronimo.toString();
	}

	private String generarAcronimoTresLetras(String texto) {
		if (texto == null) {
			return "";
		}
		texto = texto.trim();
		if (texto.length() < 3) {
			return texto.toUpperCase();
		}
		return texto.substring(0, 3).toUpperCase();
	}

	private void validarSeleccionMateriasSegunEstatusAcademico(InscripcionContextoDTO contexto)
			throws InscripcionException {
		validarOmisionReinscripcion(contexto, true);
		validarPrioridadBajaTemporal(contexto);
        validarOptativasAdicionales(contexto);
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponibles(contexto);
		ResumenSeleccionMateriasDTO resumen = construirResumenSeleccion(materiasDisponibles);
		validarSeleccionElectivas(materiasDisponibles, obtenerCantidadMaximaMateriasElectivas(contexto));
		validarCuposElectivas(materiasDisponibles);
		validarReglasCriticasAlFinalizar(contexto, materiasDisponibles);

		boolean aplicarCargaNuevoIngreso = esNuevoIngreso(contexto);
		if (esNuevoIngreso(contexto) || tienePrimerSemestrePendiente(contexto)) {
			ConfiguracionCargaNuevoIngresoDTO configuracion = inscripcionService
					.obtenerConfiguracionCargaNuevoIngreso(contexto.getInscripcionPersona().getIdPlan(),
							contexto.getInscripcionPersona().getIdPersona());
			if (tienePrimerSemestrePendiente(contexto)) {
				aplicarCargaNuevoIngreso = Boolean.TRUE.equals(configuracion.getForzarPrimerSemestrePendiente());
			}
			if (aplicarCargaNuevoIngreso && Boolean.TRUE.equals(configuracion.getActiva())) {
				validarCargaPrimerSemestre(resumen, contexto, configuracion);
			} else {
				validarMinimoMateriasPorPeriodo(resumen, contexto);
				validarMaximoMateriasSegunEstatus(resumen, contexto);
				if (tienePrimerSemestrePendiente(contexto)) {
					validarMinimoObligatoriasEnSemestresPosteriores(resumen, contexto);
				}
			}
		}

		if (!esNuevoIngreso(contexto) && !tienePrimerSemestrePendiente(contexto)) {
			validarMinimoMateriasPorPeriodo(resumen, contexto);
			validarMaximoMateriasSegunEstatus(resumen, contexto);
			validarMinimoObligatoriasEnSemestresPosteriores(resumen, contexto);
		}

	}

	/**
	 * A partir del segundo semestre, los estudiantes regulares e irregulares no
	 * podran seleccionar menos de dos materias obligatorias si las tienen
	 * disponibles.
	 */
	private void validarMinimoObligatoriasEnSemestresPosteriores(ResumenSeleccionMateriasDTO resumen,
			InscripcionContextoDTO contexto)
			throws InscripcionException {

		int minimoObligatorias;
		if (esRegular(contexto)) {
			ConfiguracionCargaRegularDTO configuracion = obtenerConfiguracionCargaRegular(
					contexto.getInscripcionPersona().getIdPlan());
			if (!Boolean.TRUE.equals(configuracion.getActiva())) {
				return;
			}
			minimoObligatorias = configuracion.getMinimoObligatorias();
		} else {
			ConfiguracionCargaIrregularDTO configuracion = inscripcionService.obtenerConfiguracionCargaIrregular(
					contexto.getInscripcionPersona().getIdPlan());
			if (!Boolean.TRUE.equals(configuracion.getActiva())) {
				return;
			}
			minimoObligatorias = configuracion.getMinimoObligatorias();
		}
		if (resumen.getObligatoriasDisponibles() >= minimoObligatorias
				&& resumen.getObligatoriasSeleccionadas() < minimoObligatorias) {
			throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.estimad.a.o.estudiante.debes.seleccionar.al.menos", minimoObligatorias));
		}
	}

	private void validarMaximoMateriasSegunEstatus(ResumenSeleccionMateriasDTO resumen, InscripcionContextoDTO contexto)
			throws InscripcionException {

		if (esRegular(contexto)) {
			/*
			 * A partir del segundo semestre, los estudiantes regulares no pueden elegir mas
			 * materias del maximo permitido
			 */
			Integer maxProgramasRegulares = obtenerMaxProgramasRegulares(contexto);

			if (resumen.getTotalSeleccionadas() > maxProgramasRegulares) {
				throw new InscripcionException(
						textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.puedes.seleccionar.maximo.unidades.didacticas", maxProgramasRegulares));
			}
			return;
		}
		// Irregulares
		/*
		 * A partir del segundo semestre, los estudiantes irregulares no pueden elegir
		 * mas materias del maximo permitido
		 */
		Integer maxProgramasIrregulares = obtenerMaxProgramasIrregulares(contexto);
		if (resumen.getTotalSeleccionadas() > maxProgramasIrregulares) {
			throw new InscripcionException(
					textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.puedes.seleccionar.maximo.unidades.didacticas", maxProgramasIrregulares));
		}
	}

	/**
	 * A partir del segundo semestre, las materias seleccionadas deben cumplir con
	 * el minimo de materias por periodo.
	 */
	private void validarMinimoMateriasPorPeriodo(ResumenSeleccionMateriasDTO resumen, InscripcionContextoDTO contexto)
			throws InscripcionException {
		List<InscripcionMateriasDTO> materiasDisponibles = contexto.getEstadoAcademico().getMateriasDisponibles();
		int cantidadMateriasOfertadas = (int) materiasDisponibles.stream().filter(m -> m.getTipoPrograma() != null)
				.filter(m -> m.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OBLIGATORIA)).count();

		Integer minProgramasPorPeriodo = obtenerMinProgramasPorPeriodo(contexto);

		if (cantidadMateriasOfertadas < minProgramasPorPeriodo || materiasDisponibles.size() < minProgramasPorPeriodo) {
			int cantidadMateriasOfertadasmarcadas = (int) materiasDisponibles.stream()
					.filter(m -> m.getTipoPrograma() != null).filter(m -> m.getCheck().equals(true)).count();
			if (cantidadMateriasOfertadasmarcadas == 0) {
				throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.debes.seleccionar.al.menos.unidades.didactica"));
			}
			return;
		}

		if (resumen.getTotalSeleccionadas() < minProgramasPorPeriodo) {
			throw new InscripcionException(
					textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.debes.seleccionar.al.menos.unidades.didactica.s", minProgramasPorPeriodo));
		}
	}

	/**
	 * Los estudiantes de nuevo ingreso y quienes tienen pendiente cursar el primer
	 * semestre deben seleccionar las materias obligatorias y optativas requeridas.
	 */
	private void validarCargaPrimerSemestre(ResumenSeleccionMateriasDTO resumen, InscripcionContextoDTO contexto,
			ConfiguracionCargaNuevoIngresoDTO configuracion) throws InscripcionException {
		List<InscripcionMateriasDTO> materiasDisponibles = contexto.getEstadoAcademico().getMateriasDisponibles();
		List<String> faltantes = new ArrayList<>();
		long obligatoriasOfertadas = resumen.getObligatoriasDisponibles();
		if (obligatoriasOfertadas < configuracion.getObligatoriasRequeridas()) {
			faltantes.add(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.se.requieren.ud.obligatorias.y.hay.disponibles", configuracion.getObligatoriasRequeridas(), obligatoriasOfertadas));
		}

		List<InscripcionMateriasDTO> optativasOfertadas = materiasDisponibles.stream()
				.filter(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
				.filter(materia -> !Boolean.TRUE.equals(materia.getDisabled()) || Boolean.TRUE.equals(materia.getCheck()))
				.collect(Collectors.toList());
		if (optativasOfertadas.size() < configuracion.getOptativasRequeridas()) {
			faltantes.add(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.se.requieren.ud.optativas.y.hay.disponibles", configuracion.getOptativasRequeridas(), optativasOfertadas.size()));
		}
		if (configuracion.getOptativasRequeridas() == 2) {
			for (String bloque : new String[] {"Bloque 1", "Bloque 2"}) {
				if (optativasOfertadas.stream().noneMatch(materia -> bloque.equalsIgnoreCase(materia.getSubestructura()))) {
					faltantes.add(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.no.hay.optativas.disponibles.en.el", bloque));
				}
			}
		}
		if (!faltantes.isEmpty()) {
            EstadoAcademicoDTO estado = contexto.getEstadoAcademico();
            String avisoCupo = estado.getAvisoCupoOptativas();
            String mensaje = textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.no.se.puede.finalizar.la.inscripcion.porque.la", String.join(", ", faltantes), (avisoCupo.isEmpty() ? "" : avisoCupo + " "));
            String momento = "OFERTA_NUEVO_INGRESO_INSUFICIENTE";
            if (!estado.getOptativasSinCupo().isEmpty()) {
                momento = "FALTA_CUPO_OPTATIVAS";
            } else if (!estado.getOptativasSinCupoConfigurado().isEmpty()) {
                momento = "CUPO_OPTATIVAS_SIN_CONFIGURAR";
            }
            registrarRechazoInscripcion(contexto, momento, mensaje);
            throw new InscripcionException(mensaje);
		}

		if (esCargaAcademicaInvalidaPrimerSemestre(resumen.getObligatoriasSeleccionadas(),
				resumen.getOptativasSeleccionadas(), configuracion)) {

			throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.para.finalizar.la.inscripcion.debe.seleccionar.unidades.didacticas", configuracion.getObligatoriasRequeridas(), configuracion.getOptativasRequeridas()));
		}
	}

	private ResumenSeleccionMateriasDTO construirResumenSeleccion(List<InscripcionMateriasDTO> materiasDisponibles) {
		long totalSeleccionadas = contarMateriasSeleccionadas(materiasDisponibles);
		long obligatoriasSeleccionadas = contarMateriasSeleccionadasObligatorias(materiasDisponibles);
		long optativasSeleccionadas = contarMateriasSeleccionadasOptativas(materiasDisponibles);
		long obligatoriasDisponibles = contarMateriasDisponiblesObligatorias(materiasDisponibles);

		return new ResumenSeleccionMateriasDTO(totalSeleccionadas, obligatoriasSeleccionadas, optativasSeleccionadas,
				obligatoriasDisponibles);
	}

	private Integer obtenerMaxProgramasIrregulares(InscripcionContextoDTO contexto) {
		return Integer.valueOf(contexto.getLimitesCargaAcademica().getMaxProgramasIrregulares());
	}

	private Integer obtenerMaxProgramasRegulares(InscripcionContextoDTO contexto) {
		return Integer.valueOf(contexto.getLimitesCargaAcademica().getMaxProgramasRegulares());
	}

	private Integer obtenerMinProgramasPorPeriodo(InscripcionContextoDTO contexto) {
		return Integer.valueOf(contexto.getLimitesCargaAcademica().getMinProgramasPorPeriodo());
	}

	private Boolean esRegular(InscripcionContextoDTO contexto) {
		return contexto.getEstadoAcademico().getEsRegular();
	}

	private Boolean esNuevoIngreso(InscripcionContextoDTO contexto) {
		return contexto.getEstadoAcademico().getEsNuevoIngreso();
	}

	private void validarReglasCriticasAlFinalizar(InscripcionContextoDTO contexto,
			List<InscripcionMateriasDTO> materiasDisponibles) throws InscripcionException {
		Long idPersona = contexto.getInscripcionPersona().getIdPersona();
		Long idPlan = contexto.getInscripcionPersona().getIdPlan();
		List<InscripcionMateriasCursadasDTO> materiasCursadasActuales = inscripcionService
				.obtenerMateriasCursadas(idPersona);
		List<InscripcionMateriasReprobadasDTO> materiasReprobadasActuales = inscripcionService
				.obtenerMateriasCursadasReprobadas(idPersona, idPlan);
		Double porcentajeCreditosActual = obtenerPorcentajeCreditosCompletados(materiasCursadasActuales,
				contexto.getCreditosTotalesPlan());
		List<InscripcionMateriasDTO> materiasSeleccionadas = materiasDisponibles.stream()
				.filter(materia -> Boolean.TRUE.equals(materia.getCheck())).collect(Collectors.toList());
		ConfiguracionCargaRegularDTO configuracionGeneral = obtenerConfiguracionRestriccionesAcademicasGenerales();

		for (InscripcionMateriasDTO materia : materiasSeleccionadas) {
			validarPorcentajeAvanceCreditos(porcentajeCreditosActual, materia, configuracionGeneral);
			validarSeleccionMateriasOctavoSemestre(materia, materiasReprobadasActuales, configuracionGeneral);
			validarSeleccionOptativas(materia, materiasDisponibles, configuracionGeneral, materiasReprobadasActuales);
            validarPrioridadOptativas(contexto, materia, configuracionGeneral, materiasReprobadasActuales,
                porcentajeCreditosActual);
			validarSeriacionAlFinalizar(materia, materiasSeleccionadas, materiasCursadasActuales);
		}
	}

	private void validarSeriacionAlFinalizar(InscripcionMateriasDTO materia,
			List<InscripcionMateriasDTO> materiasSeleccionadas,
			List<InscripcionMateriasCursadasDTO> materiasCursadas) throws InscripcionException {
		if (!esMateriaSeriada(materia)) {
			return;
		}
		boolean antecedenteAprobado = materiasCursadas.stream()
				.filter(cursada -> ConstantesGestor.MATERIA_APROBADA.equals(cursada.getEstatusAprobacion()))
				.anyMatch(cursada -> esAntecedenteEquivalente(materia, cursada));
		boolean antecedenteSeleccionadoMismoSemestre = materiasSeleccionadas.stream()
				.anyMatch(antecedente -> esAntecedenteSeleccionadoMismoSemestre(materia, antecedente));
		if (!antecedenteAprobado && !antecedenteSeleccionadoMismoSemestre) {
			throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.no.es.posible.seleccionar.la.unidad.didactica.porque", materia.getNombreTentativoPrograma()));
		}
	}

	private boolean esAntecedenteEquivalente(InscripcionMateriasDTO materia,
			InscripcionMateriasCursadasDTO cursada) {
		if (materia.getIdProgramaAntecedente().equals(cursada.getIdPrograma())) {
			return true;
		}
		String nombreAntecedente = normalizarNombreMateria(materia.getNombreProgramaAntecedente());
		return !nombreAntecedente.isEmpty()
				&& nombreAntecedente.equals(normalizarNombreMateria(cursada.getPrograma()));
	}

	private boolean esAntecedenteSeleccionadoMismoSemestre(InscripcionMateriasDTO materia,
			InscripcionMateriasDTO antecedente) {
		boolean correspondeAntecedente = materia.getIdProgramaAntecedente().equals(antecedente.getIdPrograma())
				|| (!normalizarNombreMateria(materia.getNombreProgramaAntecedente()).isEmpty()
						&& normalizarNombreMateria(materia.getNombreProgramaAntecedente())
								.equals(normalizarNombreMateria(antecedente.getNombreTentativoPrograma())));
		return correspondeAntecedente && InscripcionUtils.sonMateriasDelMismoSemestre(materia, antecedente)
				&& !InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma());
	}

	private Boolean tienePrimerSemestrePendiente(InscripcionContextoDTO contexto) {
		return Boolean.TRUE.equals(contexto.getEstadoAcademico().getPrimerSemestrePendiente());
	}

	private boolean esCargaAcademicaInvalidaPrimerSemestre(Long cantidadMateriasObligatorias,
			Long cantidadMateriasOptativas, ConfiguracionCargaNuevoIngresoDTO configuracion) {
		return cantidadMateriasObligatorias.longValue() != configuracion.getObligatoriasRequeridas().longValue()
				|| cantidadMateriasOptativas.longValue() != configuracion.getOptativasRequeridas().longValue();
	}

	private long contarMateriasDisponiblesObligatorias(List<InscripcionMateriasDTO> materiasDisponibles) {
		return materiasDisponibles.stream()
				.filter(materia -> InscripcionUtils.esMateriaObligatoria(materia.getTipoPrograma())).count();
	}

	private long contarMateriasSeleccionadasObligatorias(List<InscripcionMateriasDTO> materiasDisponibles) {
		return materiasDisponibles.stream().filter(materia -> {
			return estaSeleccionada(materia) && InscripcionUtils.esMateriaObligatoria(materia.getTipoPrograma());
		}).count();
	}

	private long contarMateriasSeleccionadasOptativas(List<InscripcionMateriasDTO> materiasDisponibles) {
		return materiasDisponibles.stream().filter(materia -> {
			return estaSeleccionada(materia) && InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma());
		}).count();
	}

	private long contarMateriasSeleccionadas(List<InscripcionMateriasDTO> materiasDisponibles) {
		return materiasDisponibles.stream().filter(md -> md.getCheck()).count();
	}

	private void validarAceptaTerminos(Boolean aceptaTerminos) throws InscripcionException {
		if (Boolean.FALSE.equals(aceptaTerminos)) {
			throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.acepta.los.terminos.y.condiciones"));
		}
	}

	@Override
	public void validarSeleccionMateria(InscripcionMateriasDTO materiaSeleccionada, InscripcionContextoDTO contexto)
			throws InscripcionException {
		validarHabilitacionInscripcion(contexto.obtenerIdPersona());
        validarOportunidades(contexto,false);
        String nombreOportunidad=normalizarNombreMateria(materiaSeleccionada.getNombreTentativoPrograma());
        for(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.OportunidadesUnidadDTO d:contexto.getOportunidades()) {
            if(d.isAgotada() && (Objects.equals(d.getIdPrograma(),materiaSeleccionada.getIdPrograma()) || (!nombreOportunidad.isEmpty() && nombreOportunidad.equals(normalizarNombreMateria(d.getNombre())))))
                throw new InscripcionException(contexto.getConfiguracionOportunidades().mensaje(contexto.getConfiguracionOportunidades().getMensajeAgotada(),d));
        }
        validarConclusionEstudios(contexto, true);
		validarOmisionReinscripcion(contexto, true);
        validarOptativasAdicionales(contexto);

		Double porcentajeCreditosCompletados = obtenerPorcentajeCreditosCompletados(contexto);
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = obtenerMateriasReprobadas(contexto);
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponibles(contexto);
		Long cantidadMaximaMateriasElectivas = obtenerCantidadMaximaMateriasElectivas(contexto);
		ConfiguracionCargaRegularDTO configuracionGeneral = obtenerConfiguracionRestriccionesAcademicasGenerales();

		validarPorcentajeAvanceCreditos(porcentajeCreditosCompletados, materiaSeleccionada, configuracionGeneral);
		validarSeleccionMateriasOctavoSemestre(materiaSeleccionada, materiasReprobadas, configuracionGeneral);
		validarSeleccionOptativas(materiaSeleccionada, materiasDisponibles, configuracionGeneral, materiasReprobadas);
        validarPrioridadOptativas(contexto, materiaSeleccionada, configuracionGeneral, materiasReprobadas,
            porcentajeCreditosCompletados);
		if (InscripcionUtils.esMateriaElectiva(materiaSeleccionada.getTipoPrograma())) {
			validarSeleccionElectivas(materiasDisponibles, cantidadMaximaMateriasElectivas);
		}

	}

	private InscripcionContextoDTO crearContextoInscripcion(InscripcionPersonaDTO persona,
			TerminosCondicionesDTO terminosCondiciones, CreditosTotalesPlanDTO creditosTotalesPlan,
			LimitesCargaAcademicaDTO limitesCargaAcademica, EstadoAcademicoDTO estadoAcademico) {
		InscripcionContextoDTO contexto = new InscripcionContextoDTO();
		contexto.setInscripcionPersona(persona);
		contexto.setTerminosCondiciones(terminosCondiciones);
		contexto.setCreditosTotalesPlan(creditosTotalesPlan);
		contexto.setLimitesCargaAcademica(limitesCargaAcademica);
		contexto.setEstadoAcademico(estadoAcademico);
		contexto.setPermiteInscripcionLimiteReprobadas(permiteInscripcionTrasLimiteReprobadas());
		contexto.setMensajeSeriacion(obtenerMensajeSeriacion(estadoAcademico));
		contexto.setMensajeLimiteReprobacionesAlcanzado(obtenerMensajeLimiteReprobacionesAlcanzado(estadoAcademico,
				contexto.getPermiteInscripcionLimiteReprobadas()));
		return contexto;
	}

	private EstadoAcademicoDTO obtenerEstadoAcademico(InscripcionPersonaDTO persona, Date fechaActual,
			CreditosTotalesPlanDTO creditosTotalesPlan, LimitesCargaAcademicaDTO limitesCargaAcademica)
			throws InscripcionException {
		Boolean esNuevoIngreso = esNuevoIngreso(persona);
		Boolean primerSemestrePendiente = tienePrimerSemestrePendiente(persona);

		Boolean esRegular = esRegular(persona);

		List<InscripcionMateriasDTO> materiasOfertadas = obtenerMateriasOfertadasPorPeriodoInscripcion(persona,
				fechaActual);

		Long idProcesoInscripcion = obtenerIdProcesoInscripcion(materiasOfertadas);

		EstadoInscripcionEstudianteDTO estadoInscripcion = obtenerEstadoInscripcion(persona, idProcesoInscripcion);

		ResultadoElectivasDTO resultadoElectivas = procesarMateriasElectivas(materiasOfertadas, persona, fechaActual);

		Long cantidadMaximaMateriasElectivas = obtenerCantidadMaximaMateriasElectivas(resultadoElectivas);

		materiasOfertadas = obtenerElectivasOtrosPlanesFusionadasConOfertadas(resultadoElectivas);

		List<InscripcionMateriasCursadasDTO> materiasCursadas = obtenerMateriasCursadas(persona);

		Double porcentajeCreditosCompletados = obtenerPorcentajeCreditosCompletados(materiasCursadas,
				creditosTotalesPlan);

		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = obtenerMateriasReprobadas(persona, esNuevoIngreso,
				esRegular);

		Integer semestreBajaTemporal = obtenerSemestreBajaTemporalParaReincorporacion(persona);
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponiblesParaInscripcion(materiasOfertadas,
				materiasCursadas, materiasReprobadas, esNuevoIngreso, primerSemestrePendiente, esRegular, limitesCargaAcademica,
				estadoInscripcion, persona, semestreBajaTemporal);

		List<InscripcionBajasDTO> materiasBajas = obtenerBajasDeMateriasSolicitadas(persona);

		EstadoAcademicoDTO estadoAcademico = crearEstadoAcademico(esNuevoIngreso, primerSemestrePendiente, esRegular,
				cantidadMaximaMateriasElectivas, materiasCursadas, porcentajeCreditosCompletados, materiasReprobadas,
				materiasDisponibles, materiasBajas);
		estadoAcademico.setSemestrePrioritarioBajaTemporal(semestreBajaTemporal);
        estadoAcademico.setIdProcesoEvaluado(idProcesoInscripcion);
		estadoAcademico.setClasificacionInscripcion(estadoInscripcion);
        prepararOptativasAdicionales(estadoAcademico,persona);
        aplicarCupoOfertaOptativas(estadoAcademico);

        return estadoAcademico;
    }

    private void aplicarCupoOfertaOptativas(EstadoAcademicoDTO estado) {
        Map<Long, Map<String, CupoOptativaDTO>> cuposPorProceso = new HashMap<>();
        List<InscripcionMateriasDTO> disponibles = new ArrayList<>();
        for (InscripcionMateriasDTO ud : estado.getMateriasDisponibles()) {
            if (!InscripcionUtils.esMateriaOptativa(ud.getTipoPrograma()) || ud.getIdProcesoInscripcion() == null) {
                disponibles.add(ud);
                continue;
            }
            Long proceso = ud.getIdProcesoInscripcion();
            if (!cuposPorProceso.containsKey(proceso)) {
                cuposPorProceso.put(proceso, inscripcionService.controlaCupoOptativas(proceso)
                    ? inscripcionService.obtenerCuposOptativas(proceso).stream()
                        .collect(Collectors.toMap(CupoOptativaDTO::getClave, item -> item))
                    : null);
            }
            Map<String, CupoOptativaDTO> cupos = cuposPorProceso.get(proceso);
            String nombre = ud.getNombreTentativoPrograma();
            String clave = nombre == null ? "" : nombre.trim().toLowerCase(java.util.Locale.ROOT);
            CupoOptativaDTO cupo = cupos == null ? null : cupos.get(clave);
            String etiqueta = ud.getNombreTentativoPrograma() + " (" + ud.getSubestructura() + ")";
            if (cupos == null || (cupo != null && cupo.getMaximo() != null
                && cupo.getOcupados() < cupo.getMaximo())) {
                disponibles.add(ud);
            } else if (cupo == null || cupo.getMaximo() == null) {
                estado.getOptativasSinCupoConfigurado().add(etiqueta);
            } else {
                estado.getOptativasSinCupo().add(etiqueta);
            }
        }
        estado.setMateriasDisponibles(disponibles);
        List<String> avisos = new ArrayList<>();
        if (!estado.getOptativasSinCupo().isEmpty()) {
            avisos.add(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.cupo.sinLugares",
                    String.join(", ", estado.getOptativasSinCupo())));
        }
        if (!estado.getOptativasSinCupoConfigurado().isEmpty()) {
            avisos.add(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.cupo.sinConfigurar",
                    String.join(", ", estado.getOptativasSinCupoConfigurado())));
        }
        estado.setAvisoCupoOptativas(String.join(" ", avisos));
    }

    private boolean mismaOptativa(InscripcionMateriasDTO m, InscripcionMateriasReprobadasDTO d) {
        return Objects.equals(m.getIdPrograma(),d.getIdPrograma()) || (!normalizarNombreMateria(m.getNombreTentativoPrograma()).isEmpty()
            && normalizarNombreMateria(m.getNombreTentativoPrograma()).equals(normalizarNombreMateria(d.getNombrePrograma())));
    }

    private void prepararOptativasAdicionales(EstadoAcademicoDTO estado,InscripcionPersonaDTO persona) {
        ConfiguracionCargaRegularDTO configuracionOptativas=obtenerConfiguracionRestriccionesAcademicasGenerales();
        int requeridas=configuracionOptativas.getOptativasRequeridasPlan();
        List<AdeudoOptativaDTO> deudas=inscripcionService.obtenerAdeudosOptativasAdicionales(persona.getIdPersona(),persona.getIdPlan());
        int acreditadas=inscripcionService.obtenerOptativasRequeridasAcreditadas(persona.getIdPersona(),persona.getIdPlan(),persona.getIdConvocatoria());
        estado.setAdeudosOptativasAdicionales(deudas);estado.setOptativasRequeridasAcreditadas(acreditadas);
        boolean obligatoriasCubiertas=!inscripcionService.tieneObligatoriasPendientes(persona.getIdPersona(),persona.getIdPlan());
        List<InscripcionMateriasDTO> permitidas=new ArrayList<>();
        for(InscripcionMateriasDTO m:estado.getMateriasDisponibles()) {
            if(!InscripcionUtils.esMateriaOptativa(m.getTipoPrograma())) { permitidas.add(m);continue; }
            boolean requeridaReprobada=estado.getMateriasReprobadas().stream().anyMatch(d->!(d instanceof AdeudoOptativaDTO) && mismaOptativa(m,d));
            m.setOptativaAdicional(acreditadas>=requeridas && !requeridaReprobada);
            m.setAdmiteSustitucionOptativa(!requeridaReprobada && !configuracionOptativas.esOptativaSoloRequerida(m.getNombreTentativoPrograma())
                && deudas.stream().anyMatch(d->!d.isEnCurso() && d.isAdmiteOtraOportunidad() && (d.isAdmiteSustitucion() || mismaOptativa(m,d))));
            AdeudoOptativaDTO propia=deudas.stream().filter(d->mismaOptativa(m,d)).findFirst().orElse(null);
            if(propia!=null) {
                m.setOptativaAdicional(true);
                if(propia.isEnCurso() || !propia.isAdmiteOtraOportunidad())continue;
                m.setIdAdeudoOptativa(propia.getIdOrigen());m.setCheck(false);m.setDisabled(false);
            }
            if(m.isOptativaAdicional()) {
                if(configuracionOptativas.esOptativaSoloRequerida(m.getNombreTentativoPrograma()))continue;
                boolean puedeRegularizar=deudas.stream().anyMatch(d->!d.isEnCurso() && d.isAdmiteOtraOportunidad() && (d.isAdmiteSustitucion() || mismaOptativa(m,d)));
                if((obligatoriasCubiertas || !deudas.isEmpty()) && !puedeRegularizar)continue;
            }
            permitidas.add(m);
        }
        estado.setMateriasDisponibles(permitidas);
    }

    private void validarOptativasAdicionales(InscripcionContextoDTO contexto) throws InscripcionException {
        ConfiguracionCargaRegularDTO configuracionOptativas=obtenerConfiguracionRestriccionesAcademicasGenerales();
        int requeridas=configuracionOptativas.getOptativasRequeridasPlan();
        InscripcionPersonaDTO persona=contexto.getInscripcionPersona();
        List<AdeudoOptativaDTO> deudas=inscripcionService.obtenerAdeudosOptativasAdicionales(persona.getIdPersona(),persona.getIdPlan());
        int acreditadas=inscripcionService.obtenerOptativasRequeridasAcreditadas(persona.getIdPersona(),persona.getIdPlan(),persona.getIdConvocatoria());
        Set<Long> usadas=new HashSet<>();Set<String> unidades=new HashSet<>();int nuevasRequeridas=0;
        for(InscripcionMateriasDTO m:contexto.obtenerMateriasSeleccionadas()) {
            if(!InscripcionUtils.esMateriaOptativa(m.getTipoPrograma())) {
                if(m.getIdAdeudoOptativa()!=null)throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.solo.una.optativa.adicional.puede.regularizar.este.adeudo"));
                continue;
            }
            String nombre=normalizarNombreMateria(m.getNombreTentativoPrograma());
            if(!unidades.add(nombre))throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.puedes.inscribir.dos.veces.la.misma.optativa"));
            boolean requeridaReprobada=contexto.getEstadoAcademico().getMateriasReprobadas().stream().anyMatch(d->!(d instanceof AdeudoOptativaDTO) && mismaOptativa(m,d));
            AdeudoOptativaDTO propia=deudas.stream().filter(d->mismaOptativa(m,d)).findFirst().orElse(null);
            if(m.getIdAdeudoOptativa()==null && propia!=null)m.setIdAdeudoOptativa(propia.getIdOrigen());
            if(m.getIdAdeudoOptativa()!=null) {
                if(requeridaReprobada)throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.una.optativa.requerida.reprobada.debe.recursarse.no.puede"));
                AdeudoOptativaDTO deuda=deudas.stream().filter(d->d.getIdOrigen().equals(m.getIdAdeudoOptativa())).findFirst().orElse(null);
                if(deuda==null)throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.adeudo.seleccionado.ya.no.esta.vigente.actualiza"));
                if(!usadas.add(deuda.getIdOrigen()))throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.selecciona.una.sola.optativa.para.regularizar.cada.adeudo"));
                if(deuda.isEnCurso())throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.optativa.que.conserva.este.adeudo.esta.en"));
                if(!deuda.isAdmiteOtraOportunidad())throw new InscripcionException(inscripcionService.obtenerConfiguracionOportunidades().mensajeAdeudo(inscripcionService.obtenerConfiguracionOportunidades().getMensajeAgotadaAcumulada(),deuda,m.getNombreTentativoPrograma()));
                if(propia!=null && !propia.getIdOrigen().equals(deuda.getIdOrigen()))throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.optativa.ya.conserva.otro.adeudo.vigente"));
                if(!mismaOptativa(m,deuda) && !deuda.isAdmiteSustitucion())throw new InscripcionException(inscripcionService.obtenerConfiguracionOportunidades().mensajeAdeudo(inscripcionService.obtenerConfiguracionOportunidades().getMensajeSustituciones(),deuda,m.getNombreTentativoPrograma()));
                if(deuda.isLimiteOportunidadesActivo() && inscripcionService.contarInscripcionesOptativa(persona.getIdPersona(),persona.getIdPlan(),m.getIdPrograma())>=inscripcionService.obtenerConfiguracionOportunidades().getLimite())
                    throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.optativa.seleccionada.agoto.sus.oportunidades.individuales"));
                if(configuracionOptativas.esOptativaSoloRequerida(m.getNombreTentativoPrograma()))throw new InscripcionException(configuracionOptativas.avisoOptativaSoloRequerida(m.getNombreTentativoPrograma()));
            } else if(acreditadas>=requeridas && !requeridaReprobada && !deudas.isEmpty()) {
                throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.selecciona.el.adeudo.de.optativa.adicional.que.deseas"));
            } else if(acreditadas<requeridas && !requeridaReprobada) {
                nuevasRequeridas++;
            }
        }
        if(nuevasRequeridas>Math.max(0,requeridas-acreditadas))throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.la.seleccion.excede.las.optativas.requeridas.que.faltan", requeridas));
    }

	private EstadoInscripcionEstudianteDTO obtenerEstadoInscripcion(InscripcionPersonaDTO persona,
			Long idProcesoInscripcion) {
		return inscripcionService.obtenerEstadoInscripcionEstudiante(persona.getIdPersona(), idProcesoInscripcion, persona.getIdPlan())
				.orElse(new EstadoInscripcionEstudianteDTO());
	}

	private Long obtenerIdProcesoInscripcion(List<InscripcionMateriasDTO> materiasOfertadas) {
		if (materiasOfertadas != null) {
			for (InscripcionMateriasDTO materia : materiasOfertadas) {
				if (materia != null && materia.getIdProcesoInscripcion() != null) {
					return materia.getIdProcesoInscripcion();
				}
			}
		}
		return null;
	}

	private String obtenerMensajeSeriacion(EstadoAcademicoDTO estadoAcademico) {
		if (estadoAcademico.getMateriasDisponibles().stream()
				.anyMatch(materia -> materia.getEsMateriaSeriada() != null && materia.getEsMateriaSeriada())) {
			return "Estimad(a/o) estudiante, la(s) unidades didácticas a las que deseas inscribirte están condicionadas a la aprobación previa de según la seriación establecida en tu plan de estudio";
		}
		return "";
	}

	private EstadoAcademicoDTO crearEstadoAcademico(Boolean esNuevoIngreso, Boolean primerSemestrePendiente,
			Boolean esRegular,
			Long cantidadMaximaMateriasElectivas, List<InscripcionMateriasCursadasDTO> materiasCursadas,
			Double porcentajeCreditosCompletados, List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			List<InscripcionMateriasDTO> materiasDisponibles, List<InscripcionBajasDTO> materiasBajas) {
		EstadoAcademicoDTO estadoAcademico = new EstadoAcademicoDTO();
		estadoAcademico.setEsNuevoIngreso(esNuevoIngreso);
		estadoAcademico.setPrimerSemestrePendiente(primerSemestrePendiente);
		estadoAcademico.setEsRegular(esRegular);
		estadoAcademico.setMateriasCursadas(materiasCursadas);
		estadoAcademico.setMateriasReprobadas(materiasReprobadas);
		estadoAcademico.setMateriasDisponibles(materiasDisponibles);
		estadoAcademico.setMateriasBajas(materiasBajas);
		estadoAcademico.setCantidadMaximaMateriasElectivas(cantidadMaximaMateriasElectivas);
		estadoAcademico.setPorcentajeCreditosCompletados(porcentajeCreditosCompletados);
		return estadoAcademico;
	}

	private List<InscripcionMateriasDTO> obtenerElectivasOtrosPlanesFusionadasConOfertadas(
			ResultadoElectivasDTO resultadoElectivas) {
		return resultadoElectivas.getElectivasDeOtrosPlanesConOfertadas();
	}

	private Long obtenerCantidadMaximaMateriasElectivas(ResultadoElectivasDTO resultadoElectivas) {
		return resultadoElectivas.getCantidadMaximaElectivas();
	}

	private Boolean esRegular(InscripcionPersonaDTO persona) {
		return inscripcionService.esEstudianteRegular(persona.getIdPersona(), persona.getIdPlan());
	}

	private Boolean esNuevoIngreso(InscripcionPersonaDTO persona) {
		return inscripcionService.esEstudianteNuevoIngreso(persona.getIdPersona(), persona.getIdPlan());
	}

	private Boolean tienePrimerSemestrePendiente(InscripcionPersonaDTO persona) {
		return inscripcionService.tienePrimerSemestrePendiente(persona.getIdPersona(), persona.getIdPlan());
	}

	private List<InscripcionBajasDTO> obtenerBajasDeMateriasSolicitadas(InscripcionPersonaDTO persona) {
		return inscripcionService.obtenerBajasDeMateriasSolicitadas(persona.getIdPersona());
	}

	private List<InscripcionMateriasReprobadasDTO> obtenerMateriasReprobadas(InscripcionPersonaDTO persona,
			Boolean esNuevoIngreso, Boolean esRegular) {
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = inscripcionService
				.obtenerMateriasCursadasReprobadas(persona.getIdPersona(), persona.getIdPlan());
		return materiasReprobadas;
	}

	private List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(InscripcionPersonaDTO persona) {
		return inscripcionService.obtenerMateriasCursadas(persona.getIdPersona());
	}

	private void validarInscripcionPrevia(Date fechaActual, InscripcionPersonaDTO persona)
			throws InscripcionPreviaException {
		Boolean existeInscripcionPrevia = inscripcionService.existeInscripcionPrevia(persona, fechaActual);

		if (existeInscripcionPrevia) {
			throw new InscripcionPreviaException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.ya.cuenta.con.una.inscripcion.para.este.periodo.2"));
		}
	}

	private InscripcionPersonaDTO obtenerInscripcionPersona(Long idPersona) {
		return inscripcionService.obtenerInscripcionPorPersona(idPersona.toString());
	}

	private TerminosCondicionesDTO obtenerTerminosCondiciones() {
		String parametroSistemaMostrarTerminos = obtenerParametroSistemaMostrarTerminos();
		Boolean mostrarTerminosCondiciones = estaActivoTerminosYCondiciones(parametroSistemaMostrarTerminos);
		if (mostrarTerminosCondiciones) {
			String parametroSistemaEnlaceTerminos = obtenerParametroSistemaEnlaceTerminos();
			return new TerminosCondicionesDTO(mostrarTerminosCondiciones, parametroSistemaEnlaceTerminos);
		}
		return new TerminosCondicionesDTO(mostrarTerminosCondiciones);
	}

	private String obtenerParametroSistemaEnlaceTerminos() {
		return parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_TERMINOS_CONDICIONES.getClave());
	}

	private String obtenerParametroSistemaMostrarTerminos() {
		return parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_MOSTRAR_TERMINOS_CONDICIONES.getClave());
	}

	private boolean estaActivoTerminosYCondiciones(String verTerminosCondiciones) {
		return verTerminosCondiciones != null && !verTerminosCondiciones.trim().isEmpty()
				&& !verTerminosCondiciones.trim().equals("0");
	}

	private List<InscripcionMateriasDTO> obtenerMateriasDisponiblesParaInscripcion(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasCursadasDTO> materiasCursadas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, Boolean esEstudianteNuevoIngreso,
			Boolean primerSemestrePendiente, Boolean esEstudianteRegular,
			LimitesCargaAcademicaDTO limitesCargaAcademica,
			EstadoInscripcionEstudianteDTO estadoInscripcion, InscripcionPersonaDTO persona,
			Integer semestreBajaTemporal)
			throws InscripcionException {

		List<InscripcionMateriasDTO> materiasOfertadasSinAprobadas = excluirMateriasAprobadas(materiasOfertadas,
				materiasCursadas);

        // P1-14 se evalúa por inscripciones formales al construir el contexto, no por notas reprobatorias.
		List<InscripcionMateriasDTO> materiasOfertadasSinReprobadasConLimiteAlcanzado = materiasOfertadasSinAprobadas;

		List<InscripcionMateriasDTO> materiasDeAcuerdoASituacionAcademica = obtenerMateriasDeAcuerdoASituacionAcademica(
					materiasReprobadas, esEstudianteNuevoIngreso, primerSemestrePendiente, esEstudianteRegular,
					materiasOfertadasSinReprobadasConLimiteAlcanzado, limitesCargaAcademica, estadoInscripcion, persona,
					materiasCursadas, semestreBajaTemporal);

        List<AdeudoOptativaDTO> adicionales=inscripcionService.obtenerAdeudosOptativasAdicionales(persona.getIdPersona(),persona.getIdPlan());
        materiasDeAcuerdoASituacionAcademica=new ArrayList<>(materiasDeAcuerdoASituacionAcademica);
        if(adicionales.stream().anyMatch(d->!d.isEnCurso() && d.isAdmiteOtraOportunidad())) {
            Set<Long> ids=new HashSet<>();for(InscripcionMateriasDTO m:materiasDeAcuerdoASituacionAcademica)ids.add(m.getIdPrograma());
            for(InscripcionMateriasDTO m:materiasOfertadasSinReprobadasConLimiteAlcanzado) {
                if(InscripcionUtils.esMateriaOptativa(m.getTipoPrograma()) && ids.add(m.getIdPrograma()))materiasDeAcuerdoASituacionAcademica.add(m);
            }
        }
		List<InscripcionMateriasDTO> materiasConSeriacionValidada = aplicarValidacionDeSeriacion(
				materiasDeAcuerdoASituacionAcademica, materiasReprobadas, materiasCursadas);

		if (semestreBajaTemporal != null) {
			// Ordena la oferta ya validada; no introduce UD excluidas por otras reglas.
			materiasConSeriacionValidada.sort(Comparator.comparingInt(materia ->
					InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura())));
		}
		return materiasConSeriacionValidada;
	}

	    private void validarOmisionReinscripcion(InscripcionContextoDTO contexto, boolean impedirAccion) throws InscripcionException {
        ConfiguracionOmisionReinscripcionDTO c = inscripcionService.obtenerConfiguracionOmisionReinscripcion();
        contexto.setMensajeOmisionReinscripcion(null);
        contexto.setPermiteInscripcionOmision(Boolean.TRUE);
        contexto.setPeriodosSinReinscripcion(Collections.<String>emptyList());
        if (!Boolean.TRUE.equals(c.getActiva())) return;
        List<String> periodos = inscripcionService.obtenerPeriodosSinReinscripcion(
                contexto.obtenerIdPersona(), contexto.getInscripcionPersona().getIdPlan(), new Date());
        contexto.setPeriodosSinReinscripcion(periodos);
        if (periodos.size() < c.getPeriodosConsecutivos()) return;
        String mensaje = c.getMensaje().replace("{cantidad}", String.valueOf(periodos.size()))
                .replace("{periodos}", String.join(", ", periodos));
        contexto.setMensajeOmisionReinscripcion(mensaje);
        contexto.setPermiteInscripcionOmision(c.getPermitirInscripcion());
        if (impedirAccion && !Boolean.TRUE.equals(c.getPermitirInscripcion())) throw new InscripcionException(mensaje);
    }
    private void validarConclusionEstudios(InscripcionContextoDTO contexto, boolean impedir) throws InscripcionException {
        InscripcionPersonaDTO persona=contexto.getInscripcionPersona();
        mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConclusionEstudiosDTO resultado=
            inscripcionService.evaluarConclusionEstudios(persona.getIdPersona(),persona.getIdPlan(),persona.getIdConvocatoria());
        contexto.setConclusionEstudios(resultado);
        if(impedir && resultado.isBloqueaInscripcion()) throw new InscripcionException(resultado.getMensaje());
    }
private void validarHabilitacionInscripcion(Long idPersona) throws InscripcionException {
		if (inscripcionService.reglaInscripcionActiva("VALIDAR_USUARIO_ACTIVO_INSCRIPCION")
				&& !inscripcionService.personaActivaParaInscripcion(idPersona)) {
			throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.tu.usuario.no.esta.activo.en.sigie.no"));
		}
	}

	private Integer obtenerSemestreBajaTemporalParaReincorporacion(InscripcionPersonaDTO persona) {
		if (!inscripcionService.reglaInscripcionActiva("SEGUIMIENTO_PENDIENTES_BAJA_TEMPORAL")) {
			return null;
		}
		return inscripcionService.obtenerSemestreBajaTemporalPendiente(persona.getIdPersona(), persona.getIdPlan());
	}

	private void validarPrioridadBajaTemporal(InscripcionContextoDTO contexto) throws InscripcionException {
		Integer semestre = contexto.getEstadoAcademico().getSemestrePrioritarioBajaTemporal();
		if (semestre == null) {
			return;
		}
		List<InscripcionMateriasDTO> disponibles = contexto.getEstadoAcademico().getMateriasDisponibles();
		boolean complementa = disponibles.stream().anyMatch(m -> estaSeleccionada(m)
				&& InscripcionUtils.obtenerNumeroSemestre(m.getEstructura()) > semestre);
		boolean omitePrioritaria = disponibles.stream().anyMatch(m ->
				InscripcionUtils.obtenerNumeroSemestre(m.getEstructura()) == semestre
				&& InscripcionUtils.esMateriaObligatoria(m.getTipoPrograma())
				&& !Boolean.TRUE.equals(m.getDisabled()) && !estaSeleccionada(m));
		boolean omiteOptativa = disponibles.stream().anyMatch(m ->
				InscripcionUtils.obtenerNumeroSemestre(m.getEstructura()) == semestre
				&& InscripcionUtils.esMateriaOptativa(m.getTipoPrograma())
				&& !Boolean.TRUE.equals(m.getDisabled())
				&& !normalizarNombreMateria(m.getNombreTentativoPrograma()).contains("(OPCIONAL)")
				&& disponibles.stream().noneMatch(elegida -> estaSeleccionada(elegida)
						&& InscripcionUtils.esMateriaOptativa(elegida.getTipoPrograma())
						&& Objects.equals(m.getEstructura(), elegida.getEstructura())
						&& Objects.equals(m.getSubestructura(), elegida.getSubestructura())));
		if (complementa && (omitePrioritaria || omiteOptativa)) {
			throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.debes.dar.prioridad.a.las.unidades.didacticas.disponibles", semestre));
		}
	}

	private List<InscripcionMateriasDTO> excluirMateriasReprobadasConLimiteAlcanzado(
			List<InscripcionMateriasDTO> materiasOfertadasSinAprobadas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		if (permiteInscripcionTrasLimiteReprobadas()) {
			return materiasOfertadasSinAprobadas;
		}
		Set<String> nombresMateriasReprobadasConLimiteAlcanzado = obtenerNombresMateriasReprobadasConLimiteAlcanzado(
				materiasReprobadas);

		return materiasOfertadasSinAprobadas.stream()
				.filter(materia -> !nombresMateriasReprobadasConLimiteAlcanzado
						.contains(normalizarNombreMateria(materia.getNombreTentativoPrograma())))
				.collect(Collectors.toList());

	}

	private Set<String> obtenerNombresMateriasReprobadasConLimiteAlcanzado(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {

		int limite = obtenerLimiteReprobacionesPorMateria();
		return materiasReprobadas.stream().filter(materia -> alcanzoLimiteReprobaciones(materia, limite))
				.map(InscripcionMateriasReprobadasDTO::getNombrePrograma).map(this::normalizarNombreMateria)
				.filter(nombre -> !nombre.isEmpty())
				.collect(Collectors.toSet());
	}

	private List<InscripcionMateriasDTO> aplicarValidacionDeSeriacion(List<InscripcionMateriasDTO> materiasDisponibles,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			List<InscripcionMateriasCursadasDTO> materiasCursadas) {

		Set<Long> idsMateriasReprobadas = obtenerIdsMateriasReprobadas(materiasReprobadas);
		Set<Long> idsMateriasCursadas = obtenerIdsMateriasCursadas(materiasCursadas);
		Set<String> nombresMateriasReprobadas = materiasReprobadas.stream()
				.map(InscripcionMateriasReprobadasDTO::getNombrePrograma).map(this::normalizarNombreMateria)
				.filter(nombre -> !nombre.isEmpty())
				.collect(Collectors.toSet());
		Set<String> nombresMateriasAprobadas = generarNombresMateriasAprobadas(materiasCursadas);

		Map<Long, InscripcionMateriasDTO> materiasMap = materiasDisponibles.stream()
				.collect(Collectors.toMap(InscripcionMateriasDTO::getIdPrograma, materia -> materia, (a, b) -> b));

		for (InscripcionMateriasDTO materia : materiasDisponibles) {
			if (esMateriaSeriada(materia)) {
				Long idAntecedente = materia.getIdProgramaAntecedente();
				InscripcionMateriasDTO materiaAntecedente = materiasMap.get(idAntecedente);

				if (materiaAntecedente != null
						&& InscripcionUtils.sonMateriasDelMismoSemestre(materia, materiaAntecedente)
						&& !InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())) {
					marcarMateriasSeriadas(materia, materiaAntecedente);
				}
			}
		}

		return materiasDisponibles.stream()
				.filter(m -> m.getCheck()
						|| (!esMateriaSeriadaReprobada(idsMateriasReprobadas, nombresMateriasReprobadas, materiasMap, m)
								&& !esMateriaSeriadaYNoCursada(idsMateriasCursadas, nombresMateriasAprobadas,
										materiasMap, m)))
				.collect(Collectors.toList());
	}

	private boolean esMateriaSeriadaYNoCursada(Set<Long> idsMateriasCursadas,
			Set<String> nombresMateriasAprobadas, Map<Long, InscripcionMateriasDTO> materiasMap,
			InscripcionMateriasDTO materia) {
		return esMateriaSeriada(materia)
				&& !esMateriaCursada(idsMateriasCursadas, nombresMateriasAprobadas, materiasMap, materia);
	}

	private boolean esMateriaCursada(Set<Long> idsMateriasCursadas, Set<String> nombresMateriasAprobadas,
			Map<Long, InscripcionMateriasDTO> materiasMap, InscripcionMateriasDTO materia) {
		if (idsMateriasCursadas.contains(materia.getIdProgramaAntecedente())) {
			return true;
		}
		String nombreAntecedente = obtenerNombreAntecedente(materiasMap, materia);
		return !nombreAntecedente.isEmpty() && nombresMateriasAprobadas.contains(nombreAntecedente);
	}

	private boolean esMateriaSeriadaReprobada(Set<Long> idsMateriasReprobadas,
			Set<String> nombresMateriasReprobadas, Map<Long, InscripcionMateriasDTO> materiasMap,
			InscripcionMateriasDTO materia) {
		if (!esMateriaSeriada(materia)) {
			return false;
		}
		if (esMateriaReprobada(idsMateriasReprobadas, materia)) {
			return true;
		}
		String nombreAntecedente = obtenerNombreAntecedente(materiasMap, materia);
		return !nombreAntecedente.isEmpty() && nombresMateriasReprobadas.contains(nombreAntecedente);
	}

	private String obtenerNombreAntecedente(Map<Long, InscripcionMateriasDTO> materiasMap,
			InscripcionMateriasDTO materia) {
		String nombreAntecedente = normalizarNombreMateria(materia.getNombreProgramaAntecedente());
		if (!nombreAntecedente.isEmpty()) {
			return nombreAntecedente;
		}
		InscripcionMateriasDTO antecedente = materiasMap.get(materia.getIdProgramaAntecedente());
		return antecedente == null ? "" : normalizarNombreMateria(antecedente.getNombreTentativoPrograma());
	}

	private boolean esMateriaReprobadaPorNombre(List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			String nombrePrograma) {
		String nombreNormalizado = normalizarNombreMateria(nombrePrograma);
		return !nombreNormalizado.isEmpty() && materiasReprobadas.stream()
				.anyMatch(materia -> normalizarNombreMateria(materia.getNombrePrograma()).equals(nombreNormalizado));
	}

	private boolean esMateriaReprobada(Set<Long> idsMateriasReprobadas, InscripcionMateriasDTO m) {
		return idsMateriasReprobadas.contains(m.getIdProgramaAntecedente());
	}

	private boolean esMateriaSeriada(InscripcionMateriasDTO m) {
		return m.getIdProgramaAntecedente() != null;
	}

	private void marcarMateriasSeriadas(InscripcionMateriasDTO materia, InscripcionMateriasDTO materiaAntecedente) {
		materia.setCheck(Boolean.TRUE);
		materia.setDisabled(Boolean.TRUE);
		materia.setEsMateriaSeriada(Boolean.TRUE);
		materiaAntecedente.setCheck(Boolean.TRUE);
		materiaAntecedente.setDisabled(Boolean.TRUE);
		materiaAntecedente.setEsMateriaSeriada(Boolean.TRUE);
	}

	private Set<Long> obtenerIdsMateriasReprobadas(List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		return materiasReprobadas.stream().map(InscripcionMateriasReprobadasDTO::getIdPrograma)
				.collect(Collectors.toSet());
	}

	private Set<Long> obtenerIdsMateriasCursadas(List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		return materiasCursadas.stream()
				.filter(materia -> ConstantesGestor.MATERIA_APROBADA.equals(materia.getEstatusAprobacion()))
				.map(InscripcionMateriasCursadasDTO::getIdPrograma).collect(Collectors.toSet());
	}

	private List<InscripcionMateriasDTO> obtenerMateriasDeAcuerdoASituacionAcademica(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, Boolean esEstudianteNuevoIngreso,
			Boolean primerSemestrePendiente, Boolean esEstudianteRegular,
			List<InscripcionMateriasDTO> materiasOfertadas,
			LimitesCargaAcademicaDTO limitesCargaAcademica, EstadoInscripcionEstudianteDTO estadoInscripcion,
			InscripcionPersonaDTO persona, List<InscripcionMateriasCursadasDTO> materiasCursadas,
			Integer semestreBajaTemporal)
			throws InscripcionException {

		// El tipo de catálogo del proceso no sustituye la trayectoria ni autoriza
		// una carga distinta. Inicial y reinscripción usan las reglas académicas
		// siguientes, conservando las UD de ambos bloques que cumplan requisitos.

		if (Boolean.TRUE.equals(primerSemestrePendiente)) {
			ConfiguracionCargaNuevoIngresoDTO configuracion = obtenerConfiguracionCargaNuevoIngresoControlada(
					persona.getIdPlan(), persona.getIdPersona());
			if (Boolean.TRUE.equals(configuracion.getActiva())
					&& Boolean.TRUE.equals(configuracion.getForzarPrimerSemestrePendiente())) {
				return obtenerMateriasConConfiguracionNuevoIngreso(materiasOfertadas, configuracion, true);
			}
		}

		// Estudiantes regulares de nuevo ingreso
		if (esEstudianteNuevoIngreso && esEstudianteRegular) {
			// logger.info("Estudiantes regulares de nuevo ingreso");
			return obtenerMateriasEstudianteNuevoIngreso(materiasOfertadas, persona.getIdPlan(), persona.getIdPersona());
		}

		// Verifica si las materias a msotrar de tipo optativas son opcionales tomar
		materiasOfertadas = modificaMateriasOptativasDeAcuerdoAProbacion(materiasOfertadas, materiasCursadas);
		final List<InscripcionMateriasDTO> ofertaAvanceAnual = materiasOfertadas;

		// Estudiantes regulares que no son de nuevo ingreso
		if (!esEstudianteNuevoIngreso && esEstudianteRegular) {
			// logger.info("Estudiantes regulares que no son de nuevo ingreso");
			return aplicarAvanceAnualPorSemestre(ofertaAvanceAnual,
					() -> obtenerMateriasPorAvanceAnualEstudianteRegular(ofertaAvanceAnual, persona, semestreBajaTemporal));
		}

		// Estudiantes irregulares de nuevo ingreso
		if (esEstudianteNuevoIngreso && !esEstudianteRegular) {
			// logger.info("Estudiantes irregulares de nuevo ingreso");
			return obtenerMateriasEstudianteNuevoIngreso(materiasOfertadas, persona.getIdPlan(), persona.getIdPersona());
		}

		// Estudiantes irregulares que no son de nuevo ingreso
		if (!esEstudianteNuevoIngreso && !esEstudianteRegular) {
			ConfiguracionCargaIrregularDTO configuracion = inscripcionService.obtenerConfiguracionCargaIrregular(persona.getIdPlan());
			if (!Boolean.TRUE.equals(configuracion.getActiva())) {
				return materiasOfertadas;
			}
			// La primera condición aplicable determina la ruta; no acumular las posteriores.

			// Las materias reprobadas se vuelven obligatorias en la lista de materias
			// ofertadas
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas = marcarMateriasOfertadasReprobadas(
					materiasOfertadas, materiasReprobadas, limitesCargaAcademica, configuracion);

			long cantidadMateriasReprobadasObligatorias = obtenerCantidadMateriasReprobadasObligatorias(
					materiasReprobadas);
			long cantidadDeMateriasREprobadasTotales = materiasReprobadas.size();

			// Regla para respetar el avance anual (Leer descripcion del metodo
			// 'obtenerMateriasPorAvanceAnualIrregulares')
			if (cantidadMateriasReprobadasObligatorias >= 1
					&& cantidadDeMateriasREprobadasTotales <= configuracion.getMaxReprobadasAvanceAnual()) {
				logger.info("Estudiante irregular: avance anual dentro del máximo configurado");
				return aplicarAvanceAnualPorSemestre(materiasConReprobadasMarcadas,
						() -> obtenerMateriasPorAvanceAnualIrregulares(materiasConReprobadasMarcadas, materiasReprobadas, configuracion, semestreBajaTemporal));
			}

			if (cantidadMateriasReprobadasObligatorias == 0) {
				logger.info("Estudiantes irregulares sin reprobadas obligatorias");
				return aplicarAvanceAnualPorSemestre(materiasConReprobadasMarcadas,
						() -> obtenerMateriasPorAvanceAnualSinTomarEnCuentaObligatorias(materiasConReprobadasMarcadas, persona, semestreBajaTemporal));
			}

			// Concentración de reprobadas en un semestre ofertado (umbral inclusivo).
			Map.Entry<String, Integer> semestreConMasReprobadas = buscarSemestreConMasReprobadas(materiasReprobadas);
			if (estaSemestreDentroDeMateriasOfertadas(semestreConMasReprobadas, materiasConReprobadasMarcadas)
					&& semestreConMasReprobadas.getValue() >= configuracion.getMinReprobadasMismoSemestre()) {
				logger.info("Estudiante irregular: reprobadas concentradas en un semestre ofertado");
				return filtrarPorSemestreUOptativas(materiasConReprobadasMarcadas, semestreConMasReprobadas.getKey(), configuracion);
			}

			logger.info("Estudiante irregular: ruta restante de recuperación");
			// Recuperación restante: reprobadas y optativas, con sus propios umbrales.
			return obtenerMateriasReprobadasUOptativas(materiasConReprobadasMarcadas, materiasReprobadas, configuracion);
		}
		return Collections.emptyList();
	}

	/** El control afecta únicamente las UD del semestre configurado, incluso en carga mixta. */
	private List<InscripcionMateriasDTO> aplicarAvanceAnualPorSemestre(List<InscripcionMateriasDTO> oferta,
			java.util.function.Supplier<List<InscripcionMateriasDTO>> evaluarAvance) {
		Set<Integer> semestres = obtenerConfiguracionRestriccionesAcademicasGenerales().getSemestresAplicacionAvanceAnual();
		Predicate<InscripcionMateriasDTO> aplica = m -> semestres.contains(InscripcionUtils.obtenerNumeroSemestre(m.getEstructura()));
		if (oferta.stream().noneMatch(aplica)) return oferta;
		Set<InscripcionMateriasDTO> permitidas = new HashSet<>(evaluarAvance.get());
		return oferta.stream().filter(m -> !aplica.test(m) || permitidas.contains(m)).collect(Collectors.toList());
	}

	private List<InscripcionMateriasDTO> modificaMateriasOptativasDeAcuerdoAProbacion(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		ConfiguracionCargaRegularDTO configuracionGeneral = obtenerConfiguracionRestriccionesAcademicasGenerales();
		if (!Boolean.TRUE.equals(configuracionGeneral.getActiva())) {
			return materiasOfertadas;
		}
		int optativasRequeridas = configuracionGeneral.getOptativasAprobadasParaOpcionales();

		Map<Integer, Long> optativasAprobadasPorSemestre = materiasCursadas.stream()
				.filter(mc -> mc.getTipoPrograma() != null
						&& mc.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OPTATIVA))
				.filter(mc -> mc.getEstatusAprobacion() != null
						&& mc.getEstatusAprobacion().equals(ConstantesGestor.MATERIA_APROBADA))
				.collect(Collectors.groupingBy(mc -> {
					try {
						return InscripcionUtils.obtenerNumeroSemestre(mc.getEstructura());
					} catch (Exception e) {
						return -1; // semestre inválido
					}
				}, Collectors.counting()));

		// 2. Marcar las materias ofertadas usando el Map construido
		materiasOfertadas.forEach(m -> {

			if (m.getTipoPrograma() != null
					&& m.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OPTATIVA)) {

				int semestreMateria;
				try {
					semestreMateria = InscripcionUtils.obtenerNumeroSemestre(m.getEstructura());
				} catch (Exception e) {
					return;
				}

				// obtener optativas aprobadas del semestre
				long optativasAprobadas = optativasAprobadasPorSemestre.getOrDefault(semestreMateria, 0L);

				// regla de negocio
				boolean habilitarOpcionales = optativasAprobadas >= optativasRequeridas;

				if (habilitarOpcionales) {
					if (m.getNombreTentativoPrograma() != null
							&& !normalizarNombreMateria(m.getNombreTentativoPrograma()).contains("(OPCIONAL)")) {

						m.setNombreTentativoPrograma(m.getNombreTentativoPrograma() + " (Opcional)");
					}
				}
			}
		});
		return materiasOfertadas;
	}

	private boolean estaSemestreDentroDeMateriasOfertadas(Map.Entry<String, Integer> semestreConMasReprobada,
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas) {
		return materiasConReprobadasMarcadas.stream()
				.anyMatch(materia -> materia.getEstructura().equalsIgnoreCase(semestreConMasReprobada.getKey()));
	}

	private List<InscripcionMateriasDTO> obtenerMateriasSemestreInscripcionOrdinariaConOptativas(
			List<InscripcionMateriasDTO> materiasOfertadas, EstadoInscripcionEstudianteDTO estadoInscripcion) {
		return materiasOfertadas.stream().filter(materia -> esElMismoSemestre(estadoInscripcion, materia)
				|| InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma())).collect(Collectors.toList());
	}

	private boolean esElMismoSemestre(EstadoInscripcionEstudianteDTO estadoInscripcion,
			InscripcionMateriasDTO materia) {
		return estadoInscripcion.getSemestreInscripcionOrdinaria()
				.equals(InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura()));
	}

	private boolean esInscripcionExtraordinariaInicial(EstadoInscripcionEstudianteDTO estadoInscripcion) {
		return Boolean.TRUE.equals(estadoInscripcion.getEsInscripcionExtraordinariaInicial());
	}

	private boolean esInscripcionOrdinariaInicial(EstadoInscripcionEstudianteDTO estadoInscripcion) {
		return Boolean.TRUE.equals(estadoInscripcion.getEsInscripcionOrdinariaInicial());
	}

	private long obtenerCantidadMateriasReprobadasObligatorias(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		return materiasReprobadas.stream().filter(
				materia -> materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OBLIGATORIA))
				.count();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasReprobadasUOptativas(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			ConfiguracionCargaIrregularDTO configuracion) {

		Predicate<InscripcionMateriasDTO> esReprobada = crearComparacionMateriasReprobadas(materiasReprobadas);

		List<InscripcionMateriasDTO> materias = materiasOfertadas.stream()
				.filter(esReprobada.or(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))).collect(Collectors.toList());

		long cantidadMateriasReprobadasOptativas = materiasReprobadas.stream()
				.filter(m -> InscripcionUtils.esMateriaOptativa(m.getTipoPrograma())).count();

		if (Boolean.TRUE.equals(configuracion.getRestringirOptativasPorRezago())
				&& (materiasReprobadas.size() >= configuracion.getMinReprobadasRestringirOptativas()
				|| cantidadMateriasReprobadasOptativas >= configuracion.getMinOptativasReprobadasRestringir())) {
			return limitarMateriasOptativas(materias, materiasReprobadas, configuracion);
		}

		return materias;
	}

	private List<InscripcionMateriasDTO> limitarMateriasOptativas(List<InscripcionMateriasDTO> materias,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			ConfiguracionCargaIrregularDTO configuracion) {

		Optional<String> optSemestreReprobado = buscarSemestreReprobadoMasAntiguo(materiasReprobadas);
		if (!optSemestreReprobado.isPresent()) {
			return materias;
		}

		int numeroSemestreReprobado = InscripcionUtils.obtenerNumeroSemestre(optSemestreReprobado.get());
		int numeroSemestreAdyacente = obtenerNumeroSemestreAdyacente(numeroSemestreReprobado);

		return materias.stream().filter(materia -> {
			if (InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma())) {
				int semestreMateria = InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura());
				if (semestreMateria != numeroSemestreReprobado
						&& (!Boolean.TRUE.equals(configuracion.getPermitirSemestreAdyacente())
								|| semestreMateria != numeroSemestreAdyacente)) {
					return false;
				}
			}
			return true;
		}).collect(Collectors.toList());
	}

	private Predicate<InscripcionMateriasDTO> crearComparacionMateriasReprobadas(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		Set<String> claves = materiasReprobadas.stream().map(InscripcionMateriasReprobadasDTO::getClavePrograma)
				.filter(clave -> clave != null && !clave.trim().isEmpty()).collect(Collectors.toSet());
		Set<String> nombres = materiasReprobadas.stream().map(InscripcionMateriasReprobadasDTO::getNombrePrograma)
				.map(this::normalizarNombreMateria).filter(nombre -> !nombre.isEmpty()).collect(Collectors.toSet());
		return materia -> claves.contains(materia.getClavePrograma())
				|| nombres.contains(normalizarNombreMateria(materia.getNombreTentativoPrograma()));
	}

	private List<InscripcionMateriasDTO> filtrarPorSemestreUOptativas(
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas, String semestreConMasReprobadas,
			ConfiguracionCargaIrregularDTO configuracion) {
		return materiasConReprobadasMarcadas.stream()
				.filter(materia -> materia.getEstructura().equalsIgnoreCase(semestreConMasReprobadas)).map(materia -> {
					if (Boolean.TRUE.equals(configuracion.getMarcarObligatoriasSemestreRestringido())
							&& materia.getEstructura().equalsIgnoreCase(semestreConMasReprobadas)
							&& materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OBLIGATORIA)) {
						materia.setCheck(Boolean.TRUE);
						materia.setDisabled(Boolean.TRUE);
					}
					return materia;
				}).collect(Collectors.toList());
	}

	private Map.Entry<String, Integer> buscarSemestreConMasReprobadas(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		Map<String, Integer> semestresConMasReprobadas = new HashMap<>();
		materiasReprobadas.forEach(m -> {
			Integer contador = semestresConMasReprobadas.get(m.getEstructura());
			if (contador == null) {
				semestresConMasReprobadas.put(m.getEstructura(), 1);
			} else {
				semestresConMasReprobadas.put(m.getEstructura(), contador + 1);
			}
		});
		// Encontrar el elemento con mayor frecuencia
		Map.Entry<String, Integer> semestreMasReprobado = null;
		int maxFrecuencia = 0;

		for (Map.Entry<String, Integer> entry : semestresConMasReprobadas.entrySet()) {
			if (entry.getValue() > maxFrecuencia) {
				maxFrecuencia = entry.getValue();
				semestreMasReprobado = entry;
			}
		}
		return semestreMasReprobado;
	}

	private List<InscripcionMateriasDTO> obtenerMateriasPorAvanceAnualSinTomarEnCuentaObligatorias(
			List<InscripcionMateriasDTO> materiasOfertadas, InscripcionPersonaDTO persona, Integer semestreBajaTemporal) {

		if (materiasOfertadas == null || materiasOfertadas.isEmpty()) {
			return Collections.emptyList();
		}

		String semestreDondeFaltanObligatorias = obtenerSemestre(persona.getIdPersona(), persona.getIdPlan(),
				materiasOfertadas.get(0).getEstructura());
		if (semestreBajaTemporal != null) {
			semestreDondeFaltanObligatorias = "Semestre " + Math.min(semestreBajaTemporal,
					InscripcionUtils.obtenerNumeroSemestre(semestreDondeFaltanObligatorias));
		}
		final String semestrePrioritario = semestreDondeFaltanObligatorias;

		return materiasOfertadas.stream()
				.filter(materia -> InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura()) == InscripcionUtils.obtenerNumeroSemestre(semestrePrioritario)
						|| materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OPTATIVA))
				.map(materia -> {
					if (InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura()) == InscripcionUtils.obtenerNumeroSemestre(semestrePrioritario)
							&& materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OBLIGATORIA)) {
						//materia.setCheck(Boolean.TRUE);
						//materia.setDisabled(Boolean.TRUE);
					} else {
						//materia.setDisabled(Boolean.FALSE); Comentado para el periodo actual
					}
					return materia;
				}).collect(Collectors.toList());
	}

	/**
	 * <pre>
	 * Regla de negocio:
	 * Si el alumno reprobó en un semestre X, SOLO puede inscribir:
	 *  - materias del mismo semestre X
	 *  - materias del semestre complementario del mismo año (X par ⇒ X-1, X non ⇒ X+1)
	 *  - las optativas de otros semestres y el complementario dependen de la configuración.
	 *
	 * Si no existe semestre reprobado, no se aplica filtro.
	 * </pre>
	 *
	 * @param materiasOfertadas
	 * @param materiasReprobadas
	 * @return
	 */
	private List<InscripcionMateriasDTO> obtenerMateriasPorAvanceAnualIrregulares(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			ConfiguracionCargaIrregularDTO configuracion, Integer semestreBajaTemporal) {

		Optional<String> optSemestreReprobado = buscarSemestreReprobadoMasAntiguo(materiasReprobadas);
		if (!optSemestreReprobado.isPresent()) {
			return materiasOfertadas;
		}

		int numeroSemestreReprobado = semestreBajaTemporal == null
				? InscripcionUtils.obtenerNumeroSemestre(optSemestreReprobado.get())
				: Math.min(semestreBajaTemporal, InscripcionUtils.obtenerNumeroSemestre(optSemestreReprobado.get()));
		int numeroSemestreAdyacente = obtenerNumeroSemestreAdyacente(numeroSemestreReprobado);

		Predicate<InscripcionMateriasDTO> esReprobada = crearComparacionMateriasReprobadas(materiasReprobadas);

		return materiasOfertadas.stream().filter(materia -> {
			int semestre = InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura());
			return semestre == numeroSemestreReprobado
					|| (semestreBajaTemporal != null && esReprobada.test(materia))
					|| (Boolean.TRUE.equals(configuracion.getPermitirSemestreAdyacente()) && semestre == numeroSemestreAdyacente)
					|| (Boolean.TRUE.equals(configuracion.getPermitirOptativasOtrosSemestresAvance())
							&& InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
					|| InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma());
		}).collect(Collectors.toList());
	}

	private Optional<String> buscarSemestreReprobadoMasAntiguo(
			List<InscripcionMateriasReprobadasDTO> materiasCursadas) {
		Optional<InscripcionMateriasReprobadasDTO> optionalMateria = materiasCursadas.stream()
				.filter(dto -> ConstantesGestor.TEXTO_MATERIA_OBLIGATORIA
	                    .equalsIgnoreCase(dto.getTipoPrograma()))
				.sorted(Comparator.comparing(dto -> {
					String estructura = dto.getEstructura();
					if (estructura == null) {
						return Integer.MAX_VALUE; // nulos al final
					}

					// Extraer el número del string "Semestre X"
					try {
						String[] partes = estructura.split(" ");
						return Integer.parseInt(partes[partes.length - 1]);
					} catch (Exception e) {
						return Integer.MAX_VALUE; // Si no tiene número, al final
					}
				})).findFirst();

		return optionalMateria.map(InscripcionMateriasReprobadasDTO::getEstructura);
	}

	private List<InscripcionMateriasDTO> marcarMateriasOfertadasReprobadas(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			LimitesCargaAcademicaDTO limitesCargaAcademica, ConfiguracionCargaIrregularDTO configuracion) {

		int maxReprobadasPermitidasIrregulares = Integer.valueOf(limitesCargaAcademica.getMaxProgramasIrregulares());

		Predicate<InscripcionMateriasDTO> esReprobada = crearComparacionMateriasReprobadas(materiasReprobadas);
		Set<Long> variantesOptativasReprobadas = materiasReprobadas.stream()
				.filter(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
				.map(InscripcionMateriasReprobadasDTO::getIdProgramaReprobado)
				.filter(Objects::nonNull).collect(Collectors.toSet());

		int cantidadMateriasMarcadas = 0;

		for (InscripcionMateriasDTO materia : materiasOfertadas) {
			boolean esOptativa = InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma());
			boolean estaReprobada = esOptativa
					? variantesOptativasReprobadas.contains(materia.getIdPrograma()) && !Boolean.TRUE.equals(materia.getDisabled())
					: esReprobada.test(materia);
			boolean exigirSeleccion = esOptativa
					? Boolean.TRUE.equals(configuracion.getMarcarReprobadasOptativas())
					: InscripcionUtils.esMateriaObligatoria(materia.getTipoPrograma())
							&& Boolean.TRUE.equals(configuracion.getMarcarReprobadasObligatorias());
            boolean deudaAdicional=materiasReprobadas.stream().anyMatch(d->d instanceof AdeudoOptativaDTO
                && normalizarNombreMateria(d.getNombrePrograma()).equals(normalizarNombreMateria(materia.getNombreTentativoPrograma())));
			if (estaReprobada && !deudaAdicional && exigirSeleccion
					&& cantidadMateriasMarcadas < maxReprobadasPermitidasIrregulares) {
				materia.setCheck(Boolean.TRUE);
				materia.setDisabled(Boolean.TRUE);
				if (esOptativa) {
					variantesOptativasReprobadas.remove(materia.getIdPrograma());
				}
				cantidadMateriasMarcadas++;
			}
		}

		return materiasOfertadas;

	}

	/**
	 * <pre>
	 * Regla de negocio para estudiantes regulares:
	 * Si el alumno cursó en un semestre X, SOLO puede inscribir:
	 *  - materias del mismo semestre X
	 *  - materias del semestre complementario del mismo año (X par ⇒ X-1, X non ⇒ X+1)
	 *  - además SIEMPRE se permiten las optativas.
	 *
	 * </pre>
	 *
	 * @param materiasOfertadas
	 * 
	 * @return
	 */
	private List<InscripcionMateriasDTO> obtenerMateriasPorAvanceAnualEstudianteRegular(
			List<InscripcionMateriasDTO> materiasOfertadas, InscripcionPersonaDTO persona, Integer semestreBajaTemporal) {

		if (materiasOfertadas == null || materiasOfertadas.isEmpty()) {
			return Collections.emptyList();
		}

		ConfiguracionCargaRegularDTO configuracion = obtenerConfiguracionCargaRegular(persona.getIdPlan());
		if (!Boolean.TRUE.equals(configuracion.getActiva())
				|| !Boolean.TRUE.equals(configuracion.getRestringirAvanceAnual())) {
			return materiasOfertadas;
		}

		int semestreAprobado = obtenerNumeroSemestreAprobado(materiasOfertadas, persona);
		if (semestreBajaTemporal != null) {
			semestreAprobado = Math.min(semestreAprobado, semestreBajaTemporal);
		}

		int semestreAdyacente = Boolean.TRUE.equals(configuracion.getPermitirSemestreAdyacente())
				? obtenerNumeroSemestreAdyacente(semestreAprobado) : semestreAprobado;

		return obtenerMateriasPermitidas(materiasOfertadas, semestreAprobado, semestreAdyacente);
	}

	private List<InscripcionMateriasDTO> obtenerMateriasPermitidas(List<InscripcionMateriasDTO> materiasOfertadas,
			int semestreAprobado, int semestreAdyacente) {
		return materiasOfertadas.stream()
				.filter(m -> perteneceASemestrePermitido(m, semestreAprobado, semestreAdyacente))
				.collect(Collectors.toList());
	}

	// Las optativas y electivas configuradas se conservan aunque pertenezcan a
	// otro semestre; las obligatorias siguen limitadas a los dos semestres de avance.

	private boolean perteneceASemestrePermitido(InscripcionMateriasDTO materia, int semestreAprobado,
			int semestreAdyacente) {

		int semestreMateria = InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura());

		return semestreMateria == semestreAprobado || semestreMateria == semestreAdyacente
				|| InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma())
				|| InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma());
	}

	private int obtenerNumeroSemestreAdyacente(int semestreAprobado) {
		return InscripcionUtils.esSemestrePar(semestreAprobado) ? semestreAprobado - 1 : semestreAprobado + 1;
	}

	private int obtenerNumeroSemestreAprobado(List<InscripcionMateriasDTO> materiasOfertadas,
			InscripcionPersonaDTO persona) {
		String semestre = obtenerSemestre(persona.getIdPersona(), persona.getIdPlan(),
				materiasOfertadas.get(0).getEstructura());

		int numeroSemestreAprobado = InscripcionUtils.obtenerNumeroSemestre(semestre);
		return numeroSemestreAprobado;
	}

	private String obtenerSemestre(Long idPersona, Long idPlan, String semestrePorDefecto) {
		List<AprobacionAsignaturasPorSemestreDTO> aprobaciones = obtenerAprobacionesAsignaturasPorSemestre(idPersona,
				idPlan);

		for (int i = 0; i < aprobaciones.size(); i++) {
			Long obligatorias = aprobaciones.get(i).getAsignaturasObligatoriasPorPrograma();
			Long aprobadas = aprobaciones.get(i).getAsignaturasAprobadas();

			if (!obligatorias.equals(aprobadas)) {
				return aprobaciones.get(i).getSemestre();
			}
		}
		return semestrePorDefecto;
	}

	private List<AprobacionAsignaturasPorSemestreDTO> obtenerAprobacionesAsignaturasPorSemestre(Long idPersona,
			Long idPlan) {
		return inscripcionService.obtenerAprobacionAsignaturasPorSemestre(idPlan, idPersona);
	}

	private List<InscripcionMateriasDTO> obtenerMateriasEstudianteNuevoIngreso(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion, Long idPlan, Long idPersona)
			throws InscripcionException {
		ConfiguracionCargaNuevoIngresoDTO configuracion = obtenerConfiguracionCargaNuevoIngresoControlada(idPlan,
				idPersona);
		return obtenerMateriasConConfiguracionNuevoIngreso(materiasPorPeriodoInscripcion, configuracion, false);
	}

	private ConfiguracionCargaNuevoIngresoDTO obtenerConfiguracionCargaNuevoIngresoControlada(Long idPlan,
			Long idPersona) throws InscripcionException {
		try {
			return inscripcionService.obtenerConfiguracionCargaNuevoIngreso(idPlan, idPersona);
		} catch (RuntimeException e) {
			logger.error("No fue posible aplicar la configuración de nuevo ingreso para el plan " + idPlan, e);
			throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.no.fue.posible.aplicar.la.configuracion.de.nuevo", idPlan, obtenerCausaErrorConfiguracion(e)));
		}
	}

	private ConfiguracionCargaRegularDTO obtenerConfiguracionCargaRegular(Long idPlan) {
		return inscripcionService.obtenerConfiguracionCargaRegular(idPlan);
	}

	private ConfiguracionCargaRegularDTO obtenerConfiguracionRestriccionesAcademicasGenerales() {
		return inscripcionService.obtenerConfiguracionRestriccionesAcademicasGenerales();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasConConfiguracionNuevoIngreso(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion,
			ConfiguracionCargaNuevoIngresoDTO configuracion, boolean forzarPrimerSemestre) {
		List<InscripcionMateriasDTO> materiasPermitidas = materiasPorPeriodoInscripcion;
		if (forzarPrimerSemestre) {
			materiasPermitidas = obtenerMateriasDelPrimerPeriodo(materiasPorPeriodoInscripcion);
		} else if (Boolean.TRUE.equals(configuracion.getRestringirPrimerSemestre())) {
			List<InscripcionMateriasDTO> materiasPrimerPeriodo = obtenerMateriasDelPrimerPeriodo(
					materiasPorPeriodoInscripcion);
			materiasPermitidas = materiasPrimerPeriodo;
			if (materiasPrimerPeriodo.isEmpty()
					&& Boolean.TRUE.equals(configuracion.getMostrarSegundoSemestreSinOfertaPrimero())) {
				materiasPermitidas = obtenerMateriasDelSegundoPeriodo(materiasPorPeriodoInscripcion);
			}
		}
		return Boolean.TRUE.equals(configuracion.getActiva())
				? configurarMateriasObligatorias(materiasPermitidas, configuracion) : materiasPermitidas;
	}

	private String obtenerCausaErrorConfiguracion(Throwable error) {
		Throwable causa = error;
		while (causa.getCause() != null && causa.getCause() != causa) {
			causa = causa.getCause();
		}
		String detalle = error.getMessage();
		String causaRaiz = causa.getMessage();
		if (detalle == null || detalle.trim().isEmpty()) {
			detalle = error.getClass().getSimpleName();
		}
		return causa != error && causaRaiz != null && !causaRaiz.trim().isEmpty()
				&& !causaRaiz.equals(detalle) ? detalle + " (causa: " + causaRaiz + ")" : detalle;
	}

	private List<InscripcionMateriasDTO> configurarMateriasObligatorias(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion,
			ConfiguracionCargaNuevoIngresoDTO configuracion) {
		int obligatoriasSeleccionadas = 0;
		for (InscripcionMateriasDTO materia : materiasPorPeriodoInscripcion) {
			if (InscripcionUtils.esMateriaObligatoria(materia.getTipoPrograma())) {
				boolean debeSeleccionarse = Boolean.TRUE.equals(configuracion.getAutoseleccionarObligatorias())
						&& obligatoriasSeleccionadas < configuracion.getObligatoriasRequeridas();
				if (debeSeleccionarse) {
					materia.setCheck(Boolean.TRUE);
					obligatoriasSeleccionadas++;
				}
				materia.setDisabled(debeSeleccionarse
						&& Boolean.TRUE.equals(configuracion.getBloquearObligatorias()));
			}
		}
		return materiasPorPeriodoInscripcion;
	}

	/**
	 * La estructura puede ser una cadena como "Semestre 1, Semestre 2, Trimestre 1,
	 * Bimestre 1, etc." Se toma el ultimo digito el cual representa el numero del
	 * periodo y con eso se obtienen las materias del primer periodo.
	 * 
	 * @return
	 */
	private List<InscripcionMateriasDTO> obtenerMateriasDelPrimerPeriodo(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion) {
		return obtenerMateriasDelPeriodo(materiasPorPeriodoInscripcion, ConstantesGestor.NUMERO_PRIMER_ESTRUCTURA);
	}

	private List<InscripcionMateriasDTO> obtenerMateriasDelSegundoPeriodo(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion) {
		return obtenerMateriasDelPeriodo(materiasPorPeriodoInscripcion, ConstantesGestor.NUMERO_SEGUNDA_ESTRUCTURA);
	}

	private List<InscripcionMateriasDTO> obtenerMateriasDelPeriodo(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion, String numeroPeriodo) {
		return materiasPorPeriodoInscripcion.stream().filter(materia -> {
			String numeroSemestre = obtenerNumeroSemestre(materia);
			return numeroSemestre.equalsIgnoreCase(numeroPeriodo);
		}).collect(Collectors.toList());
	}

	private String obtenerNumeroSemestre(InscripcionMateriasDTO mpi) {
		String[] textoSemestre = mpi.getEstructura().split(ConstantesGestor.ESPACIO_EN_BLANCO);
		String numeroSemestre = textoSemestre[textoSemestre.length - 1];
		return numeroSemestre;
	}

	private List<InscripcionMateriasDTO> excluirMateriasAprobadas(List<InscripcionMateriasDTO> materiasOfertadas,
			List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		Set<String> nombresMateriasAprobadas = generarNombresMateriasAprobadas(materiasCursadas);

		return materiasOfertadas.stream()
				.filter(materia -> !nombresMateriasAprobadas
						.contains(normalizarNombreMateria(materia.getNombreTentativoPrograma())))
				.collect(Collectors.toList());
	}

	private Set<String> generarNombresMateriasAprobadas(List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		return materiasCursadas.stream()
				.filter(mc -> mc.getEstatusAprobacion().equals(ConstantesGestor.MATERIA_APROBADA))
				.map(InscripcionMateriasCursadasDTO::getPrograma).map(this::normalizarNombreMateria)
				.filter(nombre -> !nombre.isEmpty())
				.collect(Collectors.toSet());
	}

	private String normalizarNombreMateria(String nombre) {
		if (nombre == null) {
			return "";
		}
		String sinAcentos = Normalizer.normalize(nombre, Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "");
		return sinAcentos.replaceAll("(?i)\\s*\\(opcional\\)\\s*$", "").trim().replaceAll("\\s+", " ").toUpperCase();
	}

	private Double obtenerPorcentajeCreditosCompletados(List<InscripcionMateriasCursadasDTO> materiasCursadas,
			CreditosTotalesPlanDTO creditosTotalesPlan) {
		long totalCreditos = creditosTotalesPlan.getTotalCreditos(); // 100%

		long totalHastaElMomento = materiasCursadas.stream()
				.filter(mc -> !mc.isOptativaAdicional() && ConstantesGestor.MATERIA_APROBADA.equals(mc.getEstatusAprobacion()))
				.mapToLong(InscripcionMateriasCursadasDTO::getCreditos).sum();

		if (totalCreditos == 0)
			return 0.0;

		return (totalHastaElMomento * 100.0) / totalCreditos;
	}

	private String obtenerMensajeLimiteReprobacionesAlcanzado(EstadoAcademicoDTO estadoAcademico,
			Boolean permiteInscripcion) {

		List<InscripcionMateriasReprobadasDTO> materiasReprobadasConIntentos = estadoAcademico.getMateriasReprobadas();
		List<String> materiasConLimiteDeReprobacionesAlcanzados = obtenerNombresMateriasQueAlcanzaronLimiteReprobaciones(
				materiasReprobadasConIntentos);
		if (!materiasConLimiteDeReprobacionesAlcanzados.isEmpty()) {
			return crearMensajeLimiteReprobacionesAlcanzado(materiasConLimiteDeReprobacionesAlcanzados,
					Boolean.TRUE.equals(permiteInscripcion));
		}
		return "";

	}

	private String crearMensajeLimiteReprobacionesAlcanzado(List<String> nombresMaterias,
			boolean permiteInscripcion) {

		String materiasFormateadas = String.join(", ", nombresMaterias);

		String prefijoMateria = nombresMaterias.size() > 1 ? "las materias: " : "la materia: ";

		String resultado = permiteInscripcion
				? textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.procedeInscripcion")
				: textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.noProcedeInscripcion");

		String mensaje = textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.limiteReprobaciones",
				obtenerLimiteReprobacionesPorMateria(), prefijoMateria, materiasFormateadas, resultado);

		return mensaje;
	}

	private List<String> obtenerNombresMateriasQueAlcanzaronLimiteReprobaciones(
			List<InscripcionMateriasReprobadasDTO> materias) {
		int limite = obtenerLimiteReprobacionesPorMateria();
		return materias.stream().filter(materia -> alcanzoLimiteReprobaciones(materia, limite))
				.map(InscripcionMateriasReprobadasDTO::getNombrePrograma).collect(Collectors.toList());
	}

	private int obtenerLimiteReprobacionesPorMateria() {
		return obtenerConfiguracionRestriccionesAcademicasGenerales().getLimiteReprobacionesPorMateria();
	}

	private boolean alcanzoLimiteReprobaciones(InscripcionMateriasReprobadasDTO materia, int limite) {
		return materia != null && materia.getIntentosReprobados() != null
				&& materia.getIntentosReprobados() >= limite;
	}

	private boolean permiteInscripcionTrasLimiteReprobadas() {
		return inscripcionService.reglaInscripcionActiva(
				ConstantesGestor.REGLA_PERMITE_INSCRIPCION_LIMITE_REPROBADAS);
	}

	private void validarLimiteReprobacionesParaFinalizar(InscripcionContextoDTO contexto)
			throws InscripcionException {
        validarOportunidades(contexto,true);
	}
    private void prepararOportunidades(InscripcionContextoDTO c,boolean marcarOferta) {
        mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionOportunidadesDTO config=inscripcionService.obtenerConfiguracionOportunidades();
        List<mx.gob.sedesol.basegestor.commons.dto.gestionescolar.OportunidadesUnidadDTO> lista=inscripcionService.obtenerOportunidades(c.obtenerIdPersona(),c.getInscripcionPersona().getIdPlan(),config.getLimite());
        for(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.OportunidadesUnidadDTO d:lista)d.setLimiteActivo(config.isAplicarLimite());
        c.getEstadoAcademico().setAdeudosOptativasAdicionales(inscripcionService.obtenerAdeudosOptativasAdicionales(c.obtenerIdPersona(),c.getInscripcionPersona().getIdPlan()));
        c.setConfiguracionOportunidades(config);c.setOportunidades(lista);c.setPermiteInscripcionLimiteReprobadas(!config.isAplicarLimite() || config.isPermiteContinuar());
        for(InscripcionMateriasDTO m:c.getEstadoAcademico().getMateriasDisponibles()) {
            String nombre=normalizarNombreMateria(m.getNombreTentativoPrograma());
            mx.gob.sedesol.basegestor.commons.dto.gestionescolar.OportunidadesUnidadDTO d=lista.stream().filter(x->Objects.equals(x.getIdPrograma(),m.getIdPrograma())
                || (!nombre.isEmpty() && nombre.equals(normalizarNombreMateria(x.getNombre())))).findFirst().orElse(null);
            if(d==null){d=new mx.gob.sedesol.basegestor.commons.dto.gestionescolar.OportunidadesUnidadDTO();d.setIdPrograma(m.getIdPrograma());d.setNombre(m.getNombreTentativoPrograma());d.setLimite(config.getLimite());}
            m.setOportunidades(d);
            d.setLimiteActivo(config.isAplicarLimite());
            if(marcarOferta && d.isAgotada()){m.setCheck(false);m.setDisabled(true);}
        }
        String agotadas=lista.stream().filter(x->x.isAgotada()).map(x->config.mensaje(config.getMensajeAgotada(),x)).collect(Collectors.joining(" "));
        if(!agotadas.isEmpty() && config.isPermiteContinuar())agotadas+= " " + textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.continuarConOportunidades");
        c.setMensajeLimiteReprobacionesAlcanzado(agotadas);
    }
    private void validarOportunidades(InscripcionContextoDTO c,boolean confirmar) throws InscripcionException {
        prepararOportunidades(c,false);
        Set<String> unidades=new HashSet<>();
        for(InscripcionMateriasDTO m:c.obtenerMateriasSeleccionadas()) {
            String nombre=normalizarNombreMateria(m.getNombreTentativoPrograma());
            if(!unidades.add(nombre.isEmpty()?"ID:"+m.getIdPrograma():nombre))throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.puedes.inscribir.dos.variantes.de.la.misma"));
            if(m.getOportunidades().isAgotada())throw new InscripcionException(c.getConfiguracionOportunidades().mensaje(c.getConfiguracionOportunidades().getMensajeAgotada(),m.getOportunidades()));
            if(m.getIdAdeudoOptativa()!=null) {
                AdeudoOptativaDTO d=c.obtenerAdeudoSeleccionado(m);
                if(d==null)throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.el.adeudo.seleccionado.ya.no.esta.vigente.actualiza"));
                if(d.isEnCurso())throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.optativa.que.conserva.este.adeudo.esta.en"));
                if(!d.isAdmiteOtraOportunidad())throw new InscripcionException(c.getConfiguracionOportunidades().mensajeAdeudo(c.getConfiguracionOportunidades().getMensajeAgotadaAcumulada(),d,m.getNombreTentativoPrograma()));
                if(!mismaOptativa(m,d) && !d.isAdmiteSustitucion())throw new InscripcionException(c.getConfiguracionOportunidades().mensajeAdeudo(c.getConfiguracionOportunidades().getMensajeSustituciones(),d,m.getNombreTentativoPrograma()));
            }
        }
        if(!c.getPermiteInscripcionLimiteReprobadas() && !c.getMensajeLimiteReprobacionesAlcanzado().isEmpty())throw new InscripcionException(c.getMensajeLimiteReprobacionesAlcanzado());
        if(confirmar && !c.getAvisoUltimaOportunidad().isEmpty() && !c.isAceptaUltimaOportunidad())throw new InscripcionException(c.getAvisoUltimaOportunidad() + " " + textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.aceptarUltimaOportunidad"));
    }
    private void registrarAvisoEnEvidencia(InscripcionContextoDTO c) throws InscripcionException {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper=new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.node.ObjectNode evidencia=(com.fasterxml.jackson.databind.node.ObjectNode)mapper.readTree(c.getEvidenciaEvaluacion());
            evidencia.put("avisoUltimaOportunidad",c.getAvisoUltimaOportunidad());evidencia.put("avisoUltimaReconocido",c.isAceptaUltimaOportunidad());
            evidencia.set("seleccionConfirmada",mapper.valueToTree(c.obtenerMateriasSeleccionadas()));
            c.setEvidenciaEvaluacion(mapper.writeValueAsString(evidencia));
        } catch(java.io.IOException ex) { throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.fue.posible.registrar.la.evidencia.del.aviso"),ex); }
    }

	private ResultadoElectivasDTO procesarMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas,
			InscripcionPersonaDTO persona, Date fechaActual) {
		if (!puedeCursarMateriasElectivas(materiasOfertadas)) {
			return new ResultadoElectivasDTO(excluirMateriasElectivas(materiasOfertadas), 0L);
		}
		Long cantidadMaximaMateriasElectivas = contarMateriasElectivasConSemestreValido(materiasOfertadas);
		ConfiguracionCargaRegularDTO configuracionGeneral = obtenerConfiguracionRestriccionesAcademicasGenerales();
		if (Boolean.TRUE.equals(configuracionGeneral.getActiva())
				&& configuracionGeneral.getMaximoElectivasPorPeriodo() > 0) {
			cantidadMaximaMateriasElectivas = Math.min(cantidadMaximaMateriasElectivas,
					configuracionGeneral.getMaximoElectivasPorPeriodo().longValue());
		}
		List<InscripcionMateriasDTO> materiasOfertadasSinElectivas = excluirMateriasElectivas(materiasOfertadas);
		List<InscripcionMateriasDTO> materiasElectivasOtrosPlanes = obtenerMateriasElectivasDeOtrosPlanes(persona,
				fechaActual);
		List<InscripcionMateriasDTO> materiasCombinadas = combinarElectivasDeOtrosPlanesConOfertadas(
				materiasOfertadasSinElectivas, materiasElectivasOtrosPlanes);
		return new ResultadoElectivasDTO(materiasCombinadas, cantidadMaximaMateriasElectivas);
	}

	private List<InscripcionMateriasDTO> combinarElectivasDeOtrosPlanesConOfertadas(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasDTO> materiasElectivasOtrosPlanes) {

		if (materiasElectivasOtrosPlanes == null || materiasElectivasOtrosPlanes.isEmpty()) {
			return materiasOfertadas;
		}

		List<InscripcionMateriasDTO> materiasCombinadas = new ArrayList<>(materiasOfertadas);
		materiasCombinadas.addAll(materiasElectivasOtrosPlanes);
		return materiasCombinadas;
	}

	private List<InscripcionMateriasDTO> obtenerMateriasElectivasDeOtrosPlanes(InscripcionPersonaDTO infoPersona,
			Date fechaActual) {
		return inscripcionService.obtenerMateriasElectivasDeOtrosPlanes(infoPersona.getIdPlan(), fechaActual,
				infoPersona.getIdConvocatoria(), obtenerConfiguracionRestriccionesAcademicasGenerales().getPrimerSemestreOrigenElectivas().toString(),
				obtenerConfiguracionRestriccionesAcademicasGenerales().getSegundoSemestreOrigenElectivas().toString());
	}

	private List<InscripcionMateriasDTO> excluirMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas) {
		return materiasOfertadas.stream()
				.filter(materia -> !InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma()))
				.collect(Collectors.toList());
	}

	private boolean puedeCursarMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas) {
		int semestreMinimo = obtenerConfiguracionRestriccionesAcademicasGenerales().getSemestreMinimoElectivas();
		return materiasOfertadas.stream()
				.anyMatch(materia -> InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())
						&& InscripcionUtils.esSemestreValidoMateriaElectiva(materia.getEstructura(), semestreMinimo));
	}

	private long contarMateriasElectivasConSemestreValido(List<InscripcionMateriasDTO> materiasOfertadas) {
		int semestreMinimo = obtenerConfiguracionRestriccionesAcademicasGenerales().getSemestreMinimoElectivas();
		return materiasOfertadas.stream()
				.filter(materia -> InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())
						&& InscripcionUtils.esSemestreValidoMateriaElectiva(materia.getEstructura(), semestreMinimo))
				.count();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasOfertadasPorPeriodoInscripcion(InscripcionPersonaDTO persona,
			Date fechaActual) {
		return inscripcionService.obtenerMateriasPorPeriodoInscripcion(persona.getIdPlan(), fechaActual,
				persona.getIdConvocatoria());
	}

	private LimitesCargaAcademicaDTO obtenerLimitesCargaAcademicaPorPlan(Long idPlan) throws InscripcionException {
		return inscripcionService.obtenerLimitesCargaAcademicaPorPlan(idPlan)
				.orElseThrow(() -> new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.existen.maximos.y.minimos.configurados.en.el")));
	}

	private CreditosTotalesPlanDTO obtenerCreditosTotalesPorPlan(Long idPlan) throws InscripcionException {
		return inscripcionService.obtenerCreditosTotalesPorPlan(idPlan)
				.orElseThrow(() -> new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.se.capturaron.los.creditos.totales.del.plan")));
	}

	private Long obtenerCantidadMaximaMateriasElectivas(InscripcionContextoDTO contexto) {
		return contexto.getEstadoAcademico().getCantidadMaximaMateriasElectivas();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasDisponibles(InscripcionContextoDTO contexto) {
		return contexto.getEstadoAcademico().getMateriasDisponibles();
	}

	private List<InscripcionMateriasReprobadasDTO> obtenerMateriasReprobadas(InscripcionContextoDTO contexto) {
		return contexto.getEstadoAcademico().getMateriasReprobadas();
	}

	private Double obtenerPorcentajeCreditosCompletados(InscripcionContextoDTO contexto) {
		return contexto.getEstadoAcademico().getPorcentajeCreditosCompletados();
	}

	private void validarSeleccionElectivas(List<InscripcionMateriasDTO> materiasDisponibles,
			Long cantidadMaximaMateriasElectivas)
			throws InscripcionException {

		Long cantidadElectivasSeleccionadas = materiasDisponibles.stream().filter(
				materia -> estaSeleccionada(materia) && InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma()))
				.count();

		if (cantidadElectivasSeleccionadas > cantidadMaximaMateriasElectivas) {
			throw new InscripcionException(
					textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.maximoElectivas",
							cantidadMaximaMateriasElectivas));
		}

	}

	private void validarCuposElectivas(List<InscripcionMateriasDTO> materiasDisponibles) throws InscripcionException {
		for (InscripcionMateriasDTO materia : materiasDisponibles) {
			if (estaSeleccionada(materia) && InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())
					&& (materia.getIdProcesoInscripcion() == null || !inscripcionService.tieneCupoElectiva(
							materia.getIdProcesoInscripcion(), materia.getIdPrograma()))) {
				throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.la.unidad.didactica.electiva.ya.no.cuenta.con", materia.getNombreTentativoPrograma()));
			}
		}
	}

	private boolean estaSeleccionada(InscripcionMateriasDTO m) {
		return m.getCheck().equals(Boolean.TRUE);
	}

	public void validarSeleccionOptativas(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles, ConfiguracionCargaRegularDTO configuracion)
			throws InscripcionException {
        validarSeleccionOptativas(materiaSeleccionada, materiasDisponibles, configuracion, Collections.emptyList());
    }

    private void validarSeleccionOptativas(InscripcionMateriasDTO materiaSeleccionada,
            List<InscripcionMateriasDTO> materiasDisponibles, ConfiguracionCargaRegularDTO configuracion,
            List<InscripcionMateriasReprobadasDTO> reprobadas) throws InscripcionException {
		if (!Boolean.TRUE.equals(configuracion.getActiva())) {
			return;
		}
		if (InscripcionUtils.esMateriaOptativa(materiaSeleccionada.getTipoPrograma())) {
            if (Boolean.TRUE.equals(configuracion.getLimitarOptativasOrdinariasPorPeriodo())) {
                validarLimitesOptativasOrdinarias(materiaSeleccionada, materiasDisponibles, configuracion, reprobadas);
            } else if (materiaSeleccionada.getIdAdeudoOptativa()==null && contarOptativasBloqueSemestreSeleccionadas(materiaSeleccionada, materiasDisponibles)
					>= configuracion.getMaximoOptativasPorBloque()) {
				throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.solo.se.permite.seleccionar.un.maximo.de.unidad", configuracion.getMaximoOptativasPorBloque()));
			}
            if (Boolean.TRUE.equals(configuracion.getImpedirClaveOptativaRepetida())
					&& esClaveOptativaYaSeleccionada(materiaSeleccionada, materiasDisponibles)) {
				throw new InscripcionException(
						textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.no.es.posible.tomar.la.misma.unidad.didactica"));
			}

		}
	}

    private boolean aplicaPrioridadOptativas(InscripcionContextoDTO contexto, ConfiguracionCargaRegularDTO configuracion) {
        return Boolean.TRUE.equals(configuracion.getActiva())
            && Boolean.TRUE.equals(configuracion.getPriorizarOptativasSemestreReferencia())
            && !Boolean.TRUE.equals(esNuevoIngreso(contexto))
            && contexto.getSemestreReferencia() != null && contexto.getSemestreReferencia() > 1;
    }

    private boolean esOptativaOrdinaria(InscripcionMateriasDTO ud, Predicate<InscripcionMateriasDTO> esReprobada) {
        return InscripcionUtils.esMateriaOptativa(ud.getTipoPrograma())
            && ud.getIdAdeudoOptativa() == null && !esReprobada.test(ud);
    }

    private Predicate<InscripcionMateriasDTO> optativaDisponibleDeReferencia(InscripcionContextoDTO contexto,
            ConfiguracionCargaRegularDTO configuracion, List<InscripcionMateriasReprobadasDTO> reprobadas,
            Double avance) {
        Predicate<InscripcionMateriasDTO> esReprobada = crearComparacionMateriasReprobadas(reprobadas);
        return ud -> {
            if (!esOptativaOrdinaria(ud, esReprobada)
                    || !Objects.equals(contexto.getSemestreReferencia(), InscripcionUtils.obtenerNumeroSemestre(ud.getEstructura()))
                    || (Boolean.TRUE.equals(ud.getDisabled()) && !Boolean.TRUE.equals(ud.getCheck()))
                    || (ud.getOportunidades() != null && ud.getOportunidades().isAgotada())) {
                return false;
            }
            try {
                validarPorcentajeAvanceCreditos(avance, ud, configuracion);
                validarSeleccionMateriasOctavoSemestre(ud, reprobadas, configuracion);
                return true;
            } catch (InscripcionException ex) {
                return false;
            }
        };
    }

    private void ordenarOptativasPorSemestreReferencia(InscripcionContextoDTO contexto) {
        if (contexto.getSemestreReferencia() == null || Boolean.TRUE.equals(esNuevoIngreso(contexto))) {
            return;
        }
        ConfiguracionCargaRegularDTO configuracion = obtenerConfiguracionRestriccionesAcademicasGenerales();
        if (aplicaPrioridadOptativas(contexto, configuracion)) {
            Predicate<InscripcionMateriasDTO> prioritaria = optativaDisponibleDeReferencia(contexto, configuracion,
                obtenerMateriasReprobadas(contexto), obtenerPorcentajeCreditosCompletados(contexto));
            obtenerMateriasDisponibles(contexto).sort(Comparator.comparing(prioritaria.negate()::test));
        }
    }

    private void validarPrioridadOptativas(InscripcionContextoDTO contexto, InscripcionMateriasDTO seleccionada,
            ConfiguracionCargaRegularDTO configuracion, List<InscripcionMateriasReprobadasDTO> reprobadas,
            Double avance) throws InscripcionException {
        if (!aplicaPrioridadOptativas(contexto, configuracion)
                || !esOptativaOrdinaria(seleccionada, crearComparacionMateriasReprobadas(reprobadas))
                || Objects.equals(contexto.getSemestreReferencia(), InscripcionUtils.obtenerNumeroSemestre(seleccionada.getEstructura()))) {
            return;
        }
        if (!Boolean.TRUE.equals(esRegular(contexto))) {
            throw new InscripcionException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.solo.los.estudiantes.regulares.pueden.complementar.su.seleccion"));
        }

        List<InscripcionMateriasDTO> prioritarias = obtenerMateriasDisponibles(contexto).stream()
            .filter(optativaDisponibleDeReferencia(contexto, configuracion, reprobadas, avance))
            .collect(Collectors.toList());
        List<InscripcionMateriasDTO> elegidas = prioritarias.stream()
            .filter(ud -> Boolean.TRUE.equals(ud.getCheck())).collect(Collectors.toList());
        Set<String> nombresElegidos = elegidas.stream()
            .map(ud -> normalizarNombreMateria(ud.getNombreTentativoPrograma()))
            .filter(nombre -> !nombre.isEmpty()).collect(Collectors.toSet());
        boolean quedanOpciones = prioritarias.stream()
            .filter(ud -> !nombresElegidos.contains(normalizarNombreMateria(ud.getNombreTentativoPrograma())))
            .anyMatch(ud -> elegidas.stream().noneMatch(elegida ->
                Objects.equals(ud.getSubestructura(), elegida.getSubestructura())
                    || (Boolean.TRUE.equals(configuracion.getImpedirClaveOptativaRepetida())
                        && elegida.getClavePrograma() != null && esClaveProgramaIgual(ud, elegida))));
        if (quedanOpciones) {
            throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.selecciona.primero.las.optativas.disponibles.del.semestre.de", contexto.getSemestreReferencia()));
        }
    }

    private void validarLimitesOptativasOrdinarias(InscripcionMateriasDTO seleccionada,
            List<InscripcionMateriasDTO> disponibles, ConfiguracionCargaRegularDTO configuracion,
            List<InscripcionMateriasReprobadasDTO> reprobadas) throws InscripcionException {
        Predicate<InscripcionMateriasDTO> esReprobada = crearComparacionMateriasReprobadas(reprobadas);
        if (seleccionada.getIdAdeudoOptativa() != null || esReprobada.test(seleccionada)) {
            return;
        }
        List<InscripcionMateriasDTO> otrasOrdinarias = disponibles.stream()
            .filter(ud -> ud != seleccionada && Boolean.TRUE.equals(ud.getCheck()))
            .filter(ud -> esOptativaOrdinaria(ud, esReprobada))
            .collect(Collectors.toList());
        long mismoBloque = otrasOrdinarias.stream()
            .filter(ud -> Objects.equals(ud.getSubestructura(), seleccionada.getSubestructura())).count();
        if (mismoBloque >= configuracion.getMaximoOptativasPorBloque()) {
            throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.solo.puedes.seleccionar.hasta.optativa.s.nueva.s", configuracion.getMaximoOptativasPorBloque()));
        }
        if (otrasOrdinarias.size() >= configuracion.getMaximoOptativasOrdinariasPorPeriodo()) {
            throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.solo.puedes.seleccionar.hasta.optativa.s.nueva.s.2", configuracion.getMaximoOptativasOrdinariasPorPeriodo()));
        }
    }

	private boolean esClaveOptativaYaSeleccionada(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles) {

		// Descartar de la lista la materia seleccionada
		List<InscripcionMateriasDTO> primerFiltro = materiasDisponibles.stream()
				.filter(materia -> !materiaSeleccionada.getIdPrograma().equals(materia.getIdPrograma()))
				.collect(Collectors.toList());

		// Solo quedarse con materias optativas
		List<InscripcionMateriasDTO> segundoFiltro = primerFiltro.stream()
				.filter(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
				.collect(Collectors.toList());

		// Validar si ya existe una materia marcada con la misma clave
		boolean yaExisteUnaSeleccionada = segundoFiltro.stream()
				.anyMatch(materia -> materia.getCheck().equals(Boolean.TRUE)
						&& esClaveProgramaIgual(materiaSeleccionada, materia));

		return yaExisteUnaSeleccionada;
	}

	private boolean esClaveProgramaIgual(InscripcionMateriasDTO materiaSeleccionada, InscripcionMateriasDTO md) {
		return md.getClavePrograma().equalsIgnoreCase(materiaSeleccionada.getClavePrograma());
	}

	private long contarOptativasBloqueSemestreSeleccionadas(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles) {
		String bloqueSemestreBusqueda = materiaSeleccionada.getEstructura() + materiaSeleccionada.getSubestructura();

		// Descartar de la lista la materia seleccionada
		List<InscripcionMateriasDTO> primerFiltro = materiasDisponibles.stream()
				.filter(materia -> !materiaSeleccionada.getIdPrograma().equals(materia.getIdPrograma()))
				.collect(Collectors.toList());

		// Solo quedarse con materias optativas
		List<InscripcionMateriasDTO> segundoFiltro = primerFiltro.stream()
				.filter(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()) && materia.getIdAdeudoOptativa()==null)
				.collect(Collectors.toList());

		// Validar si ya existe una materia marcada con el mismo bloque y semestre
		return segundoFiltro.stream().filter(materia -> materia.getCheck().equals(Boolean.TRUE)
				&& esMismoSemestreBloque(bloqueSemestreBusqueda, materia)).count();
	}

	private boolean esMismoSemestreBloque(String bloqueSemestreBusqueda, InscripcionMateriasDTO md) {
		String bloqueSemestre = md.getEstructura() + md.getSubestructura();
		return bloqueSemestreBusqueda.equalsIgnoreCase(bloqueSemestre);
	}

	private void validarPorcentajeAvanceCreditos(Double porcentajeCreditosCompletados,
			InscripcionMateriasDTO materiaSeleccionada, ConfiguracionCargaRegularDTO configuracion)
			throws InscripcionException {
		if (Boolean.TRUE.equals(configuracion.getActiva())
				&& esMateriaDelTramoFinal(materiaSeleccionada, configuracion.getSemestreInicioTramoFinal())) {
			if (porcentajeCreditosCompletados < configuracion.getPorcentajeMinimoTramoFinal()) {
				throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.para.seleccionar.unidades.didacticas.de.este.semestre.debes", configuracion.getPorcentajeMinimoTramoFinal()));
			}
		}
	}

	private boolean esMateriaDelTramoFinal(InscripcionMateriasDTO materiaSeleccionada, int semestreInicio) {
		int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(materiaSeleccionada.getEstructura());
		return numeroSemestre >= semestreInicio;
	}

	private void validarSeleccionMateriasOctavoSemestre(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, ConfiguracionCargaRegularDTO configuracion)
			throws InscripcionException {

		if (Boolean.TRUE.equals(configuracion.getActiva())
				&& Boolean.TRUE.equals(configuracion.getValidarRezagosSeriados())
				&& esMateriaSemestre(materiaSeleccionada, configuracion.getSemestreDestinoRezagos())) {
			Optional<InscripcionMateriasReprobadasDTO> materiaEncontrada = buscarMateriaReprobadaDeTerceroASextoSeriada(
					materiasReprobadas, configuracion.getSemestreInicialAntecedentes(),
					configuracion.getSemestreFinalAntecedentes());
			if (materiaEncontrada.isPresent()) {
				throw new InscripcionException(textosInscripcion.obtenerTextoFormateado("gw.inscripcion.mensaje.para.cursar.materias.de.semestre.debes.haber.aprobado", configuracion.getSemestreDestinoRezagos(), configuracion.getSemestreInicialAntecedentes(), configuracion.getSemestreFinalAntecedentes()));
			}
		}

	}

	private boolean esMateriaSemestre(InscripcionMateriasDTO materiaSeleccionada, int semestreEsperado) {
		int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(materiaSeleccionada.getEstructura());
		return numeroSemestre == semestreEsperado;
	}

	private Optional<InscripcionMateriasReprobadasDTO> buscarMateriaReprobadaDeTerceroASextoSeriada(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, int semestreInicial, int semestreFinal) {
		return materiasReprobadas.stream().filter(mcr -> {
			int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(mcr.getEstructura());
			return esMateriaDelRangoConfigurado(numeroSemestre, semestreInicial, semestreFinal) && esMateriaSeriada(mcr);
		}).findAny();
	}

	private boolean esMateriaSeriada(InscripcionMateriasReprobadasDTO mcr) {
		return mcr.getIdProgramaAntecedente() != null;
	}

	private boolean esMateriaDelRangoConfigurado(int numeroSemestre, int semestreInicial, int semestreFinal) {
		return numeroSemestre >= semestreInicial && numeroSemestre <= semestreFinal;
	}

	@Transactional
	@Override
	public boolean intentarReenviarCorreoInscripcion(ReenvioCorreoInscripcionDTO inscripcion) {
		boolean correoEnviado = intentarReenviarCorreoInscripcionAlEstudiante(inscripcion);
		int estadoEnvioCorreo = obtenerEstadoEnvioCorreoInscripcion(correoEnviado);
		registrarResultadoEnvioCorreo(inscripcion, estadoEnvioCorreo);
		return correoEnviado;
	}

	private boolean intentarReenviarCorreoInscripcionAlEstudiante(ReenvioCorreoInscripcionDTO inscripcion) {
		CorreoDTO correoDTO = crearCorreoDTO(inscripcion);
		return intentarEnviarCorreo(correoDTO);
	}

	private CorreoDTO crearCorreoDTO(ReenvioCorreoInscripcionDTO inscripcion) {
		List<ReenvioCorreoMateriasDTO> materias = obtenerMateriasReenvioCorreo(inscripcion);
		String anioPeriodo = construirTextoAnioPeriodo(materias);
		String nombreCompleto = construirTextoNombreCompleto(inscripcion);
		String bloquesConMaterias = construirTextoBloquesConMaterias(convertirAInscripcionMateriasDTO(materias));
		String correo = obtenerCorreoDestinatario(inscripcion.getIdPersona());

		return crearCorreoConfirmacionInscripcion(correo, nombreCompleto, anioPeriodo, bloquesConMaterias);
	}

	private String construirTextoAnioPeriodo(List<ReenvioCorreoMateriasDTO> materias) {
		Long periodo = obtenerPeriodoPrimerMateria(materias);
		String anio = obtenerParametroSistemaAnioInscripcion();
		return crearFormatoAnioPeriodo(anio, periodo);
	}

	private String crearFormatoAnioPeriodo(String anio, Long periodo) {
		return anio + "-" + periodo;
	}

	private String obtenerParametroSistemaAnioInscripcion() {
		return parametroSistemaService.obtenerParametro(ConstantesGestor.ANIO_INSCRIPCION);
	}

	private Long obtenerPeriodoPrimerMateria(List<ReenvioCorreoMateriasDTO> materias) {
		if (materias == null || materias.isEmpty()) {
			throw new IllegalArgumentException(textosInscripcion.obtenerTexto("gw.inscripcion.mensaje.la.lista.de.materias.al.construir.el.correo"));
		}
		ReenvioCorreoMateriasDTO primerMateria = materias.get(0);
		return primerMateria.getPeriodo();
	}

	private String obtenerCorreoDestinatario(Long idPersona) {
		InscripcionPersonaDTO inscripcionPersona = obtenerInscripcionPersona(idPersona);
		return inscripcionPersona.getCorreo();
	}

	private List<InscripcionMateriasDTO> convertirAInscripcionMateriasDTO(List<ReenvioCorreoMateriasDTO> materias) {
		return materias.stream().map(m -> {
			InscripcionMateriasDTO materia = new InscripcionMateriasDTO();
			materia.setNombreTentativoPrograma(m.getPrograma());
			materia.setTipoPrograma(m.getTipoPrograma());
			materia.setEstructura(m.getSemestre());
			materia.setSubestructura(m.getBloque());
			return materia;
		}).collect(Collectors.toList());
	}

	public List<ReenvioCorreoMateriasDTO> obtenerMateriasReenvioCorreo(ReenvioCorreoInscripcionDTO inscripcion) {
		return envioCorreoService.obtenerMateriasParaReenvioCorreo(inscripcion.getIdPersona(),
				inscripcion.getIdProcesoInscripcion());
	}

	@Transactional
	@Override
	public boolean intentarReenviarCorreoInscripcion(ReenvioCorreoInscripcionDTO inscripcion,
			List<ReenvioCorreoMateriasDTO> materias) {
		boolean correoEnviado = intentarReenviarCorreoInscripcionAlEstudiante(inscripcion, materias);
		int estadoEnvioCorreo = obtenerEstadoEnvioCorreoInscripcion(correoEnviado);
		registrarResultadoEnvioCorreo(inscripcion, estadoEnvioCorreo);
		return correoEnviado;
	}

	private void registrarResultadoEnvioCorreo(ReenvioCorreoInscripcionDTO inscripcion, int estadoEnvioCorreo) {
		if (existeRegistroResultadoEnvioCorreo(inscripcion.getIdPersona(), inscripcion.getIdProcesoInscripcion())) {
			actualizarResultadoEnvioCorreo(inscripcion, estadoEnvioCorreo);
		} else {
			guardarResultadoEnvioCorreo(inscripcion, estadoEnvioCorreo);
		}
	}

	private void guardarResultadoEnvioCorreo(ReenvioCorreoInscripcionDTO inscripcion, int estadoEnvioCorreo) {
		envioCorreoService.registrarResultadoEnvioCorreoInscripcion(inscripcion.getIdPersona(),
				inscripcion.getIdProcesoInscripcion(), estadoEnvioCorreo);
	}

	private void actualizarResultadoEnvioCorreo(ReenvioCorreoInscripcionDTO inscripcion, int estadoEnvioCorreo) {
		envioCorreoService.actualizarResultadoEnvioCorreoInscripcion(inscripcion.getIdPersona(),
				inscripcion.getIdProcesoInscripcion(), estadoEnvioCorreo);
	}

	private boolean intentarReenviarCorreoInscripcionAlEstudiante(ReenvioCorreoInscripcionDTO inscripcion,
			List<ReenvioCorreoMateriasDTO> materias) {
		CorreoDTO correo = crearContenidoDelCorreo(inscripcion, materias);
		return intentarEnviarCorreo(correo);
	}

	private boolean intentarEnviarCorreo(CorreoDTO correoDTO) {
		return correoElectronicoService.enviaCorreoElectronico(correoDTO);
	}

	private CorreoDTO crearContenidoDelCorreo(ReenvioCorreoInscripcionDTO inscripcion,
			List<ReenvioCorreoMateriasDTO> materias) {
		String anioPeriodo = construirTextoAnioPeriodo(materias);
		String nombreCompleto = construirTextoNombreCompleto(inscripcion);
		String bloquesConMaterias = construirTextoBloquesConMaterias(convertirAInscripcionMateriasDTO(materias));
		String correoDestinatario = obtenerCorreoDestinatario(inscripcion.getIdPersona());
		return crearCorreoConfirmacionInscripcion(correoDestinatario, nombreCompleto, anioPeriodo, bloquesConMaterias);
	}

}
