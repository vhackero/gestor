package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoElectivasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResumenSeleccionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TerminosCondicionesDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionPreviaException;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;

@Service("inscripcionFacade")
public class InscripcionFacade {

	private static final Logger logger = Logger.getLogger(InscripcionFacade.class);

	@Autowired
	private InscripcionService inscripcionService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	@Autowired
	private CorreoElectronicoService correoElectronicoService;

	public InscripcionContextoDTO obtenerContextoInscripcion(Long idPersona) throws InscripcionException,InscripcionPreviaException {

		Date fechaActual = new Date();

		InscripcionPersonaDTO persona = obtenerInscripcionPersona(idPersona);
		validarInscripcionPrevia(fechaActual, persona);
		TerminosCondicionesDTO terminosCondiciones = obtenerTerminosCondiciones();
		CreditosTotalesPlanDTO creditosTotalesPlan = obtenerCreditosTotalesPorPlan(persona.getIdPlan());
		LimitesCargaAcademicaDTO limitesCargaAcademica = obtenerLimitesCargaAcademicaPorPlan(persona.getIdPlan());
		EstadoAcademicoDTO estadoAcademico = obtenerEstadoAcademico(persona, fechaActual, creditosTotalesPlan);
		InscripcionContextoDTO contexto = crearContextoInscripcion(persona, terminosCondiciones, creditosTotalesPlan,
				limitesCargaAcademica, estadoAcademico);

		return contexto;
	}

	@Transactional
	public void finalizarInscripcion(Boolean aceptaTerminos, InscripcionContextoDTO contexto)
			throws InscripcionException {
		validarAceptaTerminos(aceptaTerminos);
		validarSeleccionMateriasSegunEstatusAcademico(contexto);
		insertarInscripcion(contexto);
		//enviarCorreoInscripcion(contexto);
	}

	private void insertarInscripcion(InscripcionContextoDTO contexto) {
		List<InscripcionInsertDTO> inscripciones = mapearModelosInscripcion(contexto);
		inscripcionService.insertarInscripciones(inscripciones);
	}

	private List<InscripcionMateriasDTO> obtenerMateriasSeleccionadas(
			List<InscripcionMateriasDTO> materiasDisponibles) {
		return materiasDisponibles.stream().filter(materia -> materia.getCheck()).collect(Collectors.toList());
	}

	public void enviarCorreoInscripcion(InscripcionContextoDTO contexto) throws RuntimeException {
		try {
			List<InscripcionMateriasDTO> materiasSeleccionadas = obtenerMateriasSeleccionadas(
					contexto.getEstadoAcademico().getMateriasDisponibles());
			InscripcionPersonaDTO inscripcion = obtenerInscripcionPersona(contexto);

			String periodo = construirPeriodo(inscripcion.getIdPlan());
			String nombreCompleto = construirNombreCompleto(inscripcion);
			String listaAsignaturasHtml = construirListaAsignaturasHtml(materiasSeleccionadas);

			CorreoDTO correo = crearCorreoConfirmacionInscripcion(inscripcion.getCorreo(), nombreCompleto, periodo,
					listaAsignaturasHtml);

			correoElectronicoService.enviaCorreoElectronico(correo);
		} catch (Exception e) {
			logger.error("Error al enviar correo de confirmación de inscripción: ", e);
			throw new InscripcionException("Error al enviar correo de confirmación de inscripción.", e);
		}
	}

	private InscripcionPersonaDTO obtenerInscripcionPersona(InscripcionContextoDTO contexto) {
		return contexto.getInscripcionPersona();
	}

	private String construirContenidoCorreo(String nombreCompleto, String periodo, String listaAsignaturasHtml) {

		return MessageFormat.format(
				"<p>Estimado estudiante {0},</p>"
						+ "<p>Te confirmamos que has concluido exitosamente tu inscripción.</p>"
						+ "<p>A continuación, te mostramos tus asignaturas para el período {1}:</p>" + "{2}",
				nombreCompleto, periodo, listaAsignaturasHtml);
	}

	private CorreoDTO crearCorreoConfirmacionInscripcion(String correoDestino, String nombreCompleto, String periodo,
			String listaAsignaturasHtml) {

		CorreoDTO correo = correoElectronicoService.asignaParametrosConfigCorreo();
		correo.setTitulo("Confirmación de Inscripción");
		correo.setAsunto("Inscripción Completa");
		correo.setDestinatarios(Collections.singletonList(correoDestino));

		String cuentaAdmin = obtenerParametroSistemaCuentaAdminCorreo();
		correo.setRemitente(cuentaAdmin);

		String contenido = construirContenidoCorreo(nombreCompleto, periodo, listaAsignaturasHtml);
		correo.setContenido(contenido);

		return correo;
	}

	private String obtenerParametroSistemaCuentaAdminCorreo() {
		return parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave());
	}

	private String construirListaAsignaturasHtml(List<InscripcionMateriasDTO> materiasSeleccionadas) {
		StringBuilder listaAsignaturas = new StringBuilder("<ul>");

		for (InscripcionMateriasDTO materia : materiasSeleccionadas) {
			listaAsignaturas.append("<li>").append(materia.getNombrePlan()).append(" - ").append(materia.getClavePlan())
					.append("</li>");
		}

		listaAsignaturas.append("</ul>");
		return listaAsignaturas.toString();
	}

	private String construirNombreCompleto(InscripcionPersonaDTO inscripcion) {
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

	private String construirPeriodo(Long idPlan) {
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		String currentYear = yearFormat.format(new Date());

		String idPlanTexto = idPlan.toString();
		String planFormateado = (idPlanTexto.length() == 1) ? "0" + idPlanTexto : idPlanTexto;

		return currentYear + planFormateado;
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
		dto.setNivel(materia.getNivelEnsenanza());
		dto.setDivision(generarAcronimo(materia.getDivision()));
		dto.setProfileFieldPerfil(obtenerPerfil(materia));
		dto.setBloque(obtenerBloque(materia));
		dto.setClaveAsig(materia.getClavePrograma());
		dto.setNuevoIngreso(obtenerEstatusNuevoIngreso(contexto));
		dto.setRecursamiento(obtenerEstatusRecursamiento(contexto, materia));
		dto.setAlta(ConstantesGestor.SOLICITO_ALTA_EL_ESTUDIANTE);
		dto.setSemestre(InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura()));
		dto.setFechaRegistro(new Date());

		return dto;
	}

	private int obtenerEstatusRecursamiento(InscripcionContextoDTO contexto, InscripcionMateriasDTO materia) {
		List<InscripcionBajasDTO> materiasBajas = contexto.getEstadoAcademico().getMateriasBajas();
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = contexto.getEstadoAcademico()
				.getMateriasReprobadas();
		return verificarRecursamiento(materiasBajas, materiasReprobadas, materia.getClavePrograma());
	}

	public int verificarRecursamiento(List<InscripcionBajasDTO> materiasBajas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, String clavePrograma) {

		if (esMateriaDadaDeBaja(materiasBajas, clavePrograma)) {
			return ConstantesGestor.CURSANDO_MATERIA_POR_PRIMERA_VEZ;
		}

		if (esMateriaReprobada(materiasReprobadas, clavePrograma)) {
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
		sb.append(generarAcronimoMayusculas(materia.getClavePrograma()));
		sb.append(generarAcronimo(materia.getSubestructura()));
		sb.append(generarAcronimo(materia.getEstructura()));
		sb.append("-");
		sb.append(generarPeriodo(materia));
		sb.append("-");
		sb.append(generarAcronimo(materia.getSubestructura()));
		sb.append("000");
		return sb.toString();
	}

	public String generarPeriodo(InscripcionMateriasDTO materia) {

		// Obtener el año actual
		int year = Calendar.getInstance().get(Calendar.YEAR);

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
		String[] palabras = texto.split(" ");
		StringBuilder acronimo = new StringBuilder();
		for (String palabra : palabras) {
			if (!palabra.isEmpty()) {
				acronimo.append(palabra.charAt(0));
			}
		}
		return acronimo.toString().toUpperCase();
	}

	private void validarSeleccionMateriasSegunEstatusAcademico(InscripcionContextoDTO contexto)
			throws InscripcionException {
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponibles(contexto);
		ResumenSeleccionMateriasDTO resumen = construirResumenSeleccion(materiasDisponibles);

		if (esNuevoIngreso(contexto) && esRegular(contexto)) {
			validarCargaPrimerSemestre(resumen);
		}

		if (!esNuevoIngreso(contexto)) {
			validarMinimoMateriasPorPeriodo(resumen, contexto);
			validarMaximoMateriasSegunEstatus(resumen, contexto);
			validarMinimoObligatoriasEnSemestresPosteriores(resumen);
		}

	}

	/**
	 * A partir del segundo semestre, los estudiantes regulares e irregulares no
	 * podran seleccionar menos de dos materias obligatorias si las tienen
	 * disponibles.
	 */
	private void validarMinimoObligatoriasEnSemestresPosteriores(ResumenSeleccionMateriasDTO resumen)
			throws InscripcionException {

		if (resumen.getObligatoriasDisponibles() >= 2 && resumen.getObligatoriasSeleccionadas() < 2) {
			throw new InscripcionException(
					"Estimad(a/o) estudiante, debes seleccionar al menos 2 materias obligatorias.");
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
				throw new InscripcionException("Puedes seleccionar máximo " + maxProgramasRegulares + " materias");
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
			throw new InscripcionException("Puedes seleccionar máximo " + maxProgramasIrregulares + " materias");
		}
	}

	/**
	 * A partir del segundo semestre, las materias seleccionadas deben cumplir con
	 * el minimo de materias por periodo.
	 */
	private void validarMinimoMateriasPorPeriodo(ResumenSeleccionMateriasDTO resumen, InscripcionContextoDTO contexto)
			throws InscripcionException {
		Integer minProgramasPorPeriodo = obtenerMinProgramasPorPeriodo(contexto);

		if (resumen.getTotalSeleccionadas() < minProgramasPorPeriodo) {
			throw new InscripcionException("Debes seleccionar al menos " + minProgramasPorPeriodo + " materia(s)");
		}
	}

	/**
	 * Los estudiantes regulares de nuevo ingreso deben seleccionar sus materias
	 * obligatorias y optativas requeridas.
	 */
	private void validarCargaPrimerSemestre(ResumenSeleccionMateriasDTO resumen) throws InscripcionException {
		if (esCargaAcademicaInvalidaPrimerSemestre(resumen.getObligatoriasSeleccionadas(),
				resumen.getOptativasSeleccionadas())) {

			throw new InscripcionException("Para finalizar la inscripción, debe seleccionar "
					+ ConstantesGestor.CANT_MATERIAS_OBLIGATORIAS_EST_REGULAR_PRIMER_SEMESTRE
					+ " materias obligatorias y " + ConstantesGestor.CANT_MATERIAS_OPTATIVAS_EST_REGULAR_PRIMER_SEMESTRE
					+ " materias optativas.");
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

	private boolean esCargaAcademicaInvalidaPrimerSemestre(Long cantidadMateriasObligatorias,
			Long cantidadMateriasOptativas) {
		return !cantidadMateriasObligatorias
				.equals(ConstantesGestor.CANT_MATERIAS_OBLIGATORIAS_EST_REGULAR_PRIMER_SEMESTRE)
				|| !cantidadMateriasOptativas
						.equals(ConstantesGestor.CANT_MATERIAS_OPTATIVAS_EST_REGULAR_PRIMER_SEMESTRE);
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
			throw new InscripcionException("Acepta términos y condiciones");
		}
	}

	public void validarSeleccionMateria(InscripcionMateriasDTO materiaSeleccionada, InscripcionContextoDTO contexto)
			throws InscripcionException {

		Double porcentajeCreditosCompletados = obtenerPorcentajeCreditosCompletados(contexto);
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = obtenerMateriasReprobadas(contexto);
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponibles(contexto);
		Long cantidadMaximaMateriasElectivas = obtenerCantidadMaximaMateriasElectivas(contexto);

		validarPorcentajeAvanceCreditos(porcentajeCreditosCompletados, materiaSeleccionada);
		validarSeleccionMateriasOctavoSemestre(materiaSeleccionada, materiasReprobadas);
		validarSeleccionOptativas(materiaSeleccionada, materiasDisponibles);
		if (InscripcionUtils.esMateriaElectiva(materiaSeleccionada.getTipoPrograma())) {
			validarSeleccionElectivas(materiaSeleccionada, materiasDisponibles, cantidadMaximaMateriasElectivas);
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
		contexto.setMostrarMensajeSeriacion(debeMostrarMensajeSeriacion(estadoAcademico));
		return contexto;
	}

	private EstadoAcademicoDTO obtenerEstadoAcademico(InscripcionPersonaDTO persona, Date fechaActual,
			CreditosTotalesPlanDTO creditosTotalesPlan) throws InscripcionException {
		Boolean esNuevoIngreso = esNuevoIngreso(persona);

		Boolean esRegular = esRegular(persona);

		List<InscripcionMateriasDTO> materiasOfertadas = obtenerMateriasOfertadasPorPeriodoInscripcion(persona,
				fechaActual);

		ResultadoElectivasDTO resultadoElectivas = procesarMateriasElectivas(materiasOfertadas, persona, fechaActual);

		Long cantidadMaximaMateriasElectivas = obtenerCantidadMaximaMateriasElectivas(resultadoElectivas);

		materiasOfertadas = obtenerElectivasOtrosPlanesFusionadasConOfertadas(resultadoElectivas);

		List<InscripcionMateriasCursadasDTO> materiasCursadas = obtenerMateriasCursadas(persona);

		Double porcentajeCreditosCompletados = obtenerPorcentajeCreditosCompletados(materiasCursadas,
				creditosTotalesPlan);

		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = obtenerMateriasReprobadas(persona, esNuevoIngreso,
				esRegular);

		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponiblesParaInscripcion(materiasOfertadas,
				materiasCursadas, materiasReprobadas, esNuevoIngreso, esRegular);

		List<InscripcionBajasDTO> materiasBajas = obtenerBajasDeMateriasSolicitadas(persona);

		EstadoAcademicoDTO estadoAcademico = crearEstadoAcademico(esNuevoIngreso, esRegular,
				cantidadMaximaMateriasElectivas, materiasCursadas, porcentajeCreditosCompletados, materiasReprobadas,
				materiasDisponibles, materiasBajas);

		return estadoAcademico;

	}

	private Boolean debeMostrarMensajeSeriacion(EstadoAcademicoDTO estadoAcademico) {
		return estadoAcademico.getMateriasDisponibles().stream()
				.anyMatch(materia -> materia.getEsMateriaSeriada() != null && materia.getEsMateriaSeriada());
	}

	private EstadoAcademicoDTO crearEstadoAcademico(Boolean esNuevoIngreso, Boolean esRegular,
			Long cantidadMaximaMateriasElectivas, List<InscripcionMateriasCursadasDTO> materiasCursadas,
			Double porcentajeCreditosCompletados, List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			List<InscripcionMateriasDTO> materiasDisponibles, List<InscripcionBajasDTO> materiasBajas) {
		EstadoAcademicoDTO estadoAcademico = new EstadoAcademicoDTO();
		estadoAcademico.setEsNuevoIngreso(esNuevoIngreso);
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
		return inscripcionService.esEstudianteRegular(persona.getIdPersona());
	}

	private Boolean esNuevoIngreso(InscripcionPersonaDTO persona) {
		return inscripcionService.esEstudianteNuevoIngreso(persona.getIdPersona());
	}

	private List<InscripcionBajasDTO> obtenerBajasDeMateriasSolicitadas(InscripcionPersonaDTO persona) {
		return inscripcionService.obtenerBajasDeMateriasSolicitadas(persona.getIdPersona());
	}

	private List<InscripcionMateriasReprobadasDTO> obtenerMateriasReprobadas(InscripcionPersonaDTO persona,
			Boolean esNuevoIngreso, Boolean esRegular) throws InscripcionException {
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = inscripcionService
				.obtenerMateriasCursadasReprobadas(persona.getIdPersona());
		validarLimiteReprobaciones(materiasReprobadas, esNuevoIngreso, esRegular);
		return materiasReprobadas;
	}

	private List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(InscripcionPersonaDTO persona) {
		return inscripcionService.obtenerMateriasCursadas(persona.getIdPersona());
	}

	private void validarInscripcionPrevia(Date fechaActual, InscripcionPersonaDTO persona) throws InscripcionPreviaException {
		Boolean existeInscripcionPrevia = inscripcionService.existeInscripcionPrevia(persona, fechaActual);

		if (existeInscripcionPrevia) {
			throw new InscripcionPreviaException("Ya cuenta con una inscripción para este periodo.");
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
			Boolean esEstudianteRegular) {

		List<InscripcionMateriasDTO> materiasOfertadasSinAprobadas = excluirMateriasAprobadas(materiasOfertadas,
				materiasCursadas);

		List<InscripcionMateriasDTO> materiasDeAcuerdoASituacionAcademica = obtenerMateriasDeAcuerdoASituacionAcademica(
				materiasReprobadas, esEstudianteNuevoIngreso, esEstudianteRegular, materiasOfertadasSinAprobadas);

		List<InscripcionMateriasDTO> materiasConSeriacionValidada = aplicarValidacionDeSeriacion(
				materiasDeAcuerdoASituacionAcademica, materiasReprobadas, materiasCursadas);

		return materiasConSeriacionValidada;
	}

	private List<InscripcionMateriasDTO> aplicarValidacionDeSeriacion(List<InscripcionMateriasDTO> materiasDisponibles,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			List<InscripcionMateriasCursadasDTO> materiasCursadas) {

		Set<Long> idsMateriasReprobadas = obtenerIdsMateriasReprobadas(materiasReprobadas);
		Set<Long> idsMateriasCursadas = obtenerIdsMateriasCursadas(materiasCursadas);

		Map<Long, InscripcionMateriasDTO> materiasMap = materiasDisponibles.stream()
				.collect(Collectors.toMap(InscripcionMateriasDTO::getIdPrograma, materia -> materia));

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
				.filter(m -> m.getCheck() || !esMateriaSeriadaReprobada(idsMateriasReprobadas, m)
						|| !esMateriaSeriadaYNoCursada(idsMateriasCursadas, m))
				.collect(Collectors.toList());
	}

	private boolean esMateriaSeriadaYNoCursada(Set<Long> idsMateriasCursadas, InscripcionMateriasDTO m) {
		return esMateriaSeriada(m) && !esMateriaCursada(idsMateriasCursadas, m);
	}

	private boolean esMateriaCursada(Set<Long> idsMateriasCursadas, InscripcionMateriasDTO m) {
		return idsMateriasCursadas.contains(m.getIdProgramaAntecedente());
	}

	private boolean esMateriaSeriadaReprobada(Set<Long> idsMateriasReprobadas, InscripcionMateriasDTO m) {
		return esMateriaSeriada(m) && esMateriaReprobada(idsMateriasReprobadas, m);
	}

	private boolean esMateriaReprobada(List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			String clavePrograma) {
		return materiasReprobadas.stream()
				.anyMatch(materia -> materia.getClavePrograma().equalsIgnoreCase(clavePrograma));
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

//		mostrarMensajeSeriacion = Boolean.TRUE;
	}

	private Set<Long> obtenerIdsMateriasReprobadas(List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		return materiasReprobadas.stream().map(InscripcionMateriasReprobadasDTO::getIdPrograma)
				.collect(Collectors.toSet());
	}

	private Set<Long> obtenerIdsMateriasCursadas(List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		return materiasCursadas.stream().map(InscripcionMateriasCursadasDTO::getIdPrograma).collect(Collectors.toSet());
	}

	private List<InscripcionMateriasDTO> obtenerMateriasDeAcuerdoASituacionAcademica(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, Boolean esEstudianteNuevoIngreso,
			Boolean esEstudianteRegular, List<InscripcionMateriasDTO> materiasOfertadasSinAprobadas) {
		// Estudiantes regulares de nuevo ingreso
		if (esEstudianteNuevoIngreso && esEstudianteRegular) {
			return obtenerMateriasEstudianteNuevoIngreso(materiasOfertadasSinAprobadas);
		}

		// Estudiantes regulares que no son de nuevo ingreso
		if (!esEstudianteNuevoIngreso && esEstudianteRegular) {
			return obtenerMateriasPorAvanceAnualEstudianteRegular(materiasOfertadasSinAprobadas);
		}

		// Estudiantes irregulares de nuevo ingreso
		if (esEstudianteNuevoIngreso && !esEstudianteRegular) {
			return obtenerMateriasEstudianteNuevoIngreso(materiasOfertadasSinAprobadas);
		}

		// Estudiantes irregulares que no son de nuevo ingreso
		if (!esEstudianteNuevoIngreso && !esEstudianteRegular) {

			// Las materias reprobadas se vuelven obligatorias en la lista de materias
			// ofertadas
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas = marcarMateriasOfertadasReprobadas(
					materiasOfertadasSinAprobadas, materiasReprobadas);

			// Regla para respetar el avance anual (Leer descripcion del metodo)
			if (materiasReprobadas.size() < ConstantesGestor.NUMERO_MAXIMO_MATERIAS_REPROBADAS) {
				return obtenerMateriasPorAvanceAnualIrregulares(materiasConReprobadasMarcadas, materiasReprobadas);
			}

			// Regla cuando el estudiante reprueba mas de 4 materias del mismo semestre
			Map.Entry<String, Integer> semestreConMasReprobadas = buscarSemestreConMasReprobadas(materiasReprobadas);
			if (semestreConMasReprobadas.getValue() > ConstantesGestor.NUMERO_MAXIMO_MATERIAS_REPROBADAS) {
				return filtrarPorSemestreUOptativas(materiasConReprobadasMarcadas, semestreConMasReprobadas.getKey());
			}

			// Regla cuando el estudiante reprueba 4 o mas materias de diferentes semestres
			return obtenerMateriasReprobadasUOptativas(materiasConReprobadasMarcadas, materiasReprobadas);
		}
		return Collections.emptyList();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasReprobadasUOptativas(
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		Set<String> clavesMateriasReprobadas = materiasReprobadas.stream()
				.map(InscripcionMateriasReprobadasDTO::getClavePrograma).collect(Collectors.toSet());
		return materiasConReprobadasMarcadas.stream().filter(esMateriaReprobadaUOptativa(clavesMateriasReprobadas))
				.collect(Collectors.toList());
	}

	private Predicate<? super InscripcionMateriasDTO> esMateriaReprobadaUOptativa(
			Set<String> clavesMateriasReprobadas) {
		return materia -> clavesMateriasReprobadas.contains(materia.getClavePrograma())
				|| InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma());
	}

	private List<InscripcionMateriasDTO> filtrarPorSemestreUOptativas(
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas, String semestreConMasReprobadas) {
		return materiasConReprobadasMarcadas.stream()
				.filter(materia -> materia.getEstructura().equalsIgnoreCase(semestreConMasReprobadas)
						|| InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
				.map(materia -> {
					if (materia.getEstructura().equalsIgnoreCase(semestreConMasReprobadas)
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

	/**
	 * <pre>
	 * Regla de negocio:
	 * Si el alumno reprobó en un semestre X, SOLO puede inscribir:
	 *  - materias del mismo semestre X
	 *  - materias del semestre complementario del mismo año (X par ⇒ X-1, X non ⇒ X+1)
	 *  - además SIEMPRE se permiten las optativas.
	 *
	 * Si no existe semestre reprobado, no se aplica filtro.
	 * </pre>
	 *
	 * @param materiasOfertadas
	 * @param materiasReprobadas
	 * @return
	 */
	private List<InscripcionMateriasDTO> obtenerMateriasPorAvanceAnualIrregulares(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {

		Optional<String> optSemestreReprobado = buscarSemestreReprobadoMasAntiguo(materiasReprobadas);
		if (!optSemestreReprobado.isPresent()) {
			return materiasOfertadas;
		}

		int numeroSemestreReprobado = InscripcionUtils.obtenerNumeroSemestre(optSemestreReprobado.get());
		int numeroSemestreAdyacente = InscripcionUtils.esSemestrePar(numeroSemestreReprobado)
				? numeroSemestreReprobado - 1
				: numeroSemestreReprobado + 1;

		return materiasOfertadas.stream().filter(materia -> {
			int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura());
			return numeroSemestre == numeroSemestreReprobado || numeroSemestre == numeroSemestreAdyacente
					|| InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma());
		}).collect(Collectors.toList());
	}

	private Optional<String> buscarSemestreReprobadoMasAntiguo(
			List<InscripcionMateriasReprobadasDTO> materiasCursadas) {
		Optional<InscripcionMateriasReprobadasDTO> optionalMateria = materiasCursadas.stream()
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
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {

		/*
		 * Las claves en las materias optativas se pueden repetir, por eso se le
		 * concatena el idprograma para crear un id unico
		 */
		Set<String> clavesMateriasReprobadas = materiasReprobadas.stream()
				.map(InscripcionUtils::obtenerClaveUnicaPrograma).collect(Collectors.toSet());

		materiasOfertadas.forEach(materia -> {
			boolean estaReprobada = clavesMateriasReprobadas.contains(InscripcionUtils.claveUnicaPrograma(materia));
			if (estaReprobada && !InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())) {
				materia.setCheck(Boolean.TRUE);
				materia.setDisabled(Boolean.TRUE);
			}
		});

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
			List<InscripcionMateriasDTO> materiasOfertadas) {

		if (materiasOfertadas == null || materiasOfertadas.isEmpty()) {
			return Collections.emptyList();
		}

		String semestre = materiasOfertadas.get(0).getEstructura();
		int numeroSemestreAprobado = InscripcionUtils.obtenerNumeroSemestre(semestre);

		int semestreAdyacente = InscripcionUtils.esSemestrePar(numeroSemestreAprobado) ? numeroSemestreAprobado - 1
				: numeroSemestreAprobado + 1;

		return materiasOfertadas.stream().filter(m -> {
			int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(m.getEstructura());
			return numeroSemestre == numeroSemestreAprobado || numeroSemestre == semestreAdyacente
					|| InscripcionUtils.esMateriaOptativa(m.getTipoPrograma());
		}).collect(Collectors.toList());
	}

	private List<InscripcionMateriasDTO> obtenerMateriasEstudianteNuevoIngreso(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion) {
		List<InscripcionMateriasDTO> materiasPrimerPeriodo = obtenerMateriasDelPrimerPeriodo(
				materiasPorPeriodoInscripcion);
		return marcarMateriasObligatorias(materiasPrimerPeriodo);
	}

	private List<InscripcionMateriasDTO> marcarMateriasObligatorias(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion) {
		return materiasPorPeriodoInscripcion.stream().map(materia -> {
			if (InscripcionUtils.esMateriaObligatoria(materia.getTipoPrograma())) {
				materia.setCheck(Boolean.TRUE);
				materia.setDisabled(Boolean.TRUE);
			}
			return materia;
		}).collect(Collectors.toList());
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
		return materiasPorPeriodoInscripcion.stream().filter(materia -> {
			String numeroSemestre = obtenerNumeroSemestre(materia);
			return numeroSemestre.equalsIgnoreCase(ConstantesGestor.NUMERO_PRIMER_ESTRUCTURA);
		}).collect(Collectors.toList());
	}

	private String obtenerNumeroSemestre(InscripcionMateriasDTO mpi) {
		String[] textoSemestre = mpi.getEstructura().split(ConstantesGestor.ESPACIO_EN_BLANCO);
		String numeroSemestre = textoSemestre[textoSemestre.length - 1];
		return numeroSemestre;
	}

	private List<InscripcionMateriasDTO> excluirMateriasAprobadas(List<InscripcionMateriasDTO> materiasOfertadas,
			List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		Set<String> clavesMateriasAprobadas = generarClavesMateriasAprobadas(materiasCursadas);

		return excluirMateriasAprobadasPorClave(materiasOfertadas, clavesMateriasAprobadas);
	}

	private List<InscripcionMateriasDTO> excluirMateriasAprobadasPorClave(
			List<InscripcionMateriasDTO> materiasOfertadas, Set<String> clavesMateriasAprobadas) {
		return materiasOfertadas.stream().filter(mpi -> !clavesMateriasAprobadas.contains(mpi.getClavePrograma()))
				.collect(Collectors.toList());
	}

	private Set<String> generarClavesMateriasAprobadas(List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		return materiasCursadas.stream()
				.filter(mc -> mc.getEstatusAprobacion().equals(ConstantesGestor.MATERIA_APROBADA))
				.map(InscripcionMateriasCursadasDTO::getClavePrograma).collect(Collectors.toSet());
	}

	private Double obtenerPorcentajeCreditosCompletados(List<InscripcionMateriasCursadasDTO> materiasCursadas,
			CreditosTotalesPlanDTO creditosTotalesPlan) {
		long totalCreditos = creditosTotalesPlan.getTotalCreditos(); // 100%

		long totalHastaElMomento = materiasCursadas.stream()
				.filter(mc -> ConstantesGestor.MATERIA_APROBADA.equals(mc.getEstatusAprobacion()))
				.mapToLong(InscripcionMateriasCursadasDTO::getCreditos).sum();

		if (totalCreditos == 0)
			return 0.0;

		return (totalHastaElMomento * 100.0) / totalCreditos;
	}

	private void validarLimiteReprobaciones(List<InscripcionMateriasReprobadasDTO> materiasReprobadasConIntentos,
			Boolean esEstudianteNuevoIngreso, Boolean esEstudianteRegular) throws InscripcionException {
		if (esEstudianteNuevoIngreso || esEstudianteRegular) {
			return;
		}

		// Solo aplica para estudiantes que NO sean de nuevo ingreso y que sean
		// IRREGULARES
		if (haAlcanzadoMaximoDeReprobaciones(materiasReprobadasConIntentos)) {
			String mensaje = String.format(
					"Estimad(a/o) estudiante, dada su situación académica en la que ha reprobado la misma asignatura más de %d veces no le es permitido realizar una nueva inscripción, contacte a la mesa de ayuda para revisar su caso.",
					ConstantesGestor.LIMITE_REPROBACIONES_POR_MATERIA);

			throw new InscripcionException(mensaje);
		}
	}

	private boolean haAlcanzadoMaximoDeReprobaciones(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadasConIntentos) {
		return materiasReprobadasConIntentos.stream()
				.anyMatch(mcr -> mcr.getIntentosReprobados().equals(ConstantesGestor.LIMITE_REPROBACIONES_POR_MATERIA));
	}

	private ResultadoElectivasDTO procesarMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas,
			InscripcionPersonaDTO persona, Date fechaActual) {
		if (!puedeCursarMateriasElectivas(materiasOfertadas)) {
			return new ResultadoElectivasDTO(materiasOfertadas, 0L);
		}
		Long cantidadMaximaMateriasElectivas = contarMateriasElectivasConSemestreValido(materiasOfertadas);
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
				infoPersona.getIdConvocatoria(), ConstantesGestor.NUMERO_SEMESTRE_CINCO,
				ConstantesGestor.NUMERO_SEMESTRE_SEIS);
	}

	private List<InscripcionMateriasDTO> excluirMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas) {
		return materiasOfertadas.stream()
				.filter(materia -> !InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma()))
				.collect(Collectors.toList());
	}

	private boolean puedeCursarMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas) {
		return materiasOfertadas.stream()
				.anyMatch(materia -> InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())
						&& InscripcionUtils.esSemestreValidoMateriaElectiva(materia.getEstructura()));
	}

	private long contarMateriasElectivasConSemestreValido(List<InscripcionMateriasDTO> materiasOfertadas) {
		return materiasOfertadas.stream()
				.filter(materia -> InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())
						&& InscripcionUtils.esSemestreValidoMateriaElectiva(materia.getEstructura()))
				.count();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasOfertadasPorPeriodoInscripcion(InscripcionPersonaDTO persona,
			Date fechaActual) {
		return inscripcionService.obtenerMateriasPorPeriodoInscripcion(persona.getIdPlan(), fechaActual,
				persona.getIdConvocatoria());
	}

	private LimitesCargaAcademicaDTO obtenerLimitesCargaAcademicaPorPlan(Long idPlan) throws InscripcionException {
		return inscripcionService.obtenerLimitesCargaAcademicaPorPlan(idPlan)
				.orElseThrow(() -> new InscripcionException("No existen máximos y mínimos configurados en el plan"));
	}

	private CreditosTotalesPlanDTO obtenerCreditosTotalesPorPlan(Long idPlan) throws InscripcionException {
		return inscripcionService.obtenerCreditosTotalesPorPlan(idPlan)
				.orElseThrow(() -> new InscripcionException("No se capturaron los creditos totales del plan"));
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

	private void validarSeleccionElectivas(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles, Long cantidadMaximaMateriasElectivas)
			throws InscripcionException {

		Long cantidadElectivasSeleccionadas = materiasDisponibles.stream().filter(
				materia -> estaSeleccionada(materia) && InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma()))
				.count();

		if (cantidadElectivasSeleccionadas > cantidadMaximaMateriasElectivas) {
			throw new InscripcionException(String.format("Solo puedes seleccionar un máximo de %d materias electivas",
					cantidadMaximaMateriasElectivas));
		}

	}

	private boolean estaSeleccionada(InscripcionMateriasDTO m) {
		return m.getCheck().equals(Boolean.TRUE);
	}

	public void validarSeleccionOptativas(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles) throws InscripcionException {
		if (InscripcionUtils.esMateriaOptativa(materiaSeleccionada.getTipoPrograma())) {
			if (esOptativaBloqueSemestreYaSeleccionada(materiaSeleccionada, materiasDisponibles)) {
				throw new InscripcionException("Solo se permite seleccionar una optativa por bloque y semestre");
			} else if (esClaveOptativaYaSeleccionada(materiaSeleccionada, materiasDisponibles)) {
				throw new InscripcionException("No es posible tomar la misma asignatura dos veces");
			}

		}
	}

	private boolean esClaveOptativaYaSeleccionada(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles) {
		return materiasDisponibles.stream().filter(materia -> esIdProgramaDiferente(materiaSeleccionada, materia))
				.filter(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
				.anyMatch(materia -> esClaveProgramaIgual(materiaSeleccionada, materia));
	}

	private boolean esClaveProgramaIgual(InscripcionMateriasDTO materiaSeleccionada, InscripcionMateriasDTO md) {
		return md.getClavePrograma().equalsIgnoreCase(materiaSeleccionada.getClavePrograma());
	}

	private boolean esIdProgramaDiferente(InscripcionMateriasDTO materiaSeleccionada, InscripcionMateriasDTO md) {
		return noEsLaMismaSeleccionada(md, materiaSeleccionada);
	}

	private boolean noEsLaMismaSeleccionada(InscripcionMateriasDTO materiaSeleccionada, InscripcionMateriasDTO m) {
		return !materiaSeleccionada.getIdPrograma().equals(m.getIdPrograma());
	}

	private boolean esOptativaBloqueSemestreYaSeleccionada(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles) {
		String bloqueSemestreBusqueda = materiaSeleccionada.getEstructura() + materiaSeleccionada.getSubestructura();
		return materiasDisponibles.stream().filter(materia -> esIdProgramaDiferente(materiaSeleccionada, materia))
				.filter(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
				.anyMatch(materia -> esMismoSemestreBloque(bloqueSemestreBusqueda, materia));
	}

	private boolean esMismoSemestreBloque(String bloqueSemestreBusqueda, InscripcionMateriasDTO md) {
		String bloqueSemestre = md.getEstructura() + md.getSubestructura();
		return bloqueSemestreBusqueda.equalsIgnoreCase(bloqueSemestre);
	}

	private void validarPorcentajeAvanceCreditos(Double porcentajeCreditosCompletados,
			InscripcionMateriasDTO materiaSeleccionada) throws InscripcionException {
		if (esMateriaMayorASextoSemestre(materiaSeleccionada)) {
			if (porcentajeCreditosCompletados < ConstantesGestor.PORCENTAJE_CREDITOS_REQUERIDOS_SEPTIMO_SEMESTRE) {
				throw new InscripcionException(
						"Para seleccionar materias de este semestre, debes haber acreditado al menos el 50% del total de créditos de tu plan de estudios.");
			}
		}
	}

	private boolean esMateriaMayorASextoSemestre(InscripcionMateriasDTO materiaSeleccionada) {
		int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(materiaSeleccionada.getEstructura());
		return numeroSemestre > ConstantesGestor.SEXTO_SEMESTRE;
	}

	private void validarSeleccionMateriasOctavoSemestre(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) throws InscripcionException {

		if (esMateriaOctavoSemestre(materiaSeleccionada)) {
			Optional<InscripcionMateriasReprobadasDTO> materiaEncontrada = buscarMateriaReprobadaDeTerceroASextoSeriada(
					materiasReprobadas);
			if (materiaEncontrada.isPresent()) {
				throw new InscripcionException(
						"Para cursar materias de octavo semestre, debes haber aprobado todas las materias de tercero a sexto semestre que se encuentren seriadas");
			}
		}

	}

	private boolean esMateriaOctavoSemestre(InscripcionMateriasDTO materiaSeleccionada) {
		int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(materiaSeleccionada.getEstructura());
		return numeroSemestre == ConstantesGestor.OCTAVO_SEMESTRE;
	}

	private Optional<InscripcionMateriasReprobadasDTO> buscarMateriaReprobadaDeTerceroASextoSeriada(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		return materiasReprobadas.stream().filter(mcr -> {
			int numeroSemestre = InscripcionUtils.obtenerNumeroSemestre(mcr.getEstructura());
			return esMateriaDeTerceroASextoSemestre(numeroSemestre) && esMateriaSeriada(mcr);
		}).findAny();
	}

	private boolean esMateriaSeriada(InscripcionMateriasReprobadasDTO mcr) {
		return mcr.getIdProgramaAntecedente() != null;
	}

	private boolean esMateriaDeTerceroASextoSemestre(int numeroSemestre) {
		return numeroSemestre >= ConstantesGestor.TERCER_SEMESTRE && numeroSemestre <= ConstantesGestor.SEXTO_SEMESTRE;
	}

}
