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
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AprobacionAsignaturasPorSemestreDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoInscripcionEstudianteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
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
		return construirContextoInscripcion(idPersona, true);
	}

	@Transactional(readOnly = true)
	@Override
	public InscripcionContextoDTO obtenerContextoInscripcionConsulta(Long idPersona) throws InscripcionException {
		return construirContextoInscripcion(idPersona, false);
	}

	private InscripcionContextoDTO construirContextoInscripcion(Long idPersona, boolean validarInscripcionPrevia)
			throws InscripcionException {
		Date fechaActual = new Date();

		InscripcionPersonaDTO persona = obtenerInscripcionPersona(idPersona);
		if (validarInscripcionPrevia) {
			validarInscripcionPrevia(fechaActual, persona);
		}
		TerminosCondicionesDTO terminosCondiciones = obtenerTerminosCondiciones();
		CreditosTotalesPlanDTO creditosTotalesPlan = obtenerCreditosTotalesPorPlan(persona.getIdPlan());
		LimitesCargaAcademicaDTO limitesCargaAcademica = obtenerLimitesCargaAcademicaPorPlan(persona.getIdPlan());
		EstadoAcademicoDTO estadoAcademico = obtenerEstadoAcademico(persona, fechaActual, creditosTotalesPlan,
				limitesCargaAcademica);
		InscripcionContextoDTO contexto = crearContextoInscripcion(persona, terminosCondiciones, creditosTotalesPlan,
				limitesCargaAcademica, estadoAcademico);

		return contexto;
	}

	@Transactional
	@Override
	public void finalizarInscripcion(Boolean aceptaTerminos, InscripcionContextoDTO contexto)
			throws InscripcionException {
		validarAceptaTerminos(aceptaTerminos);
		validarSeleccionMateriasSegunEstatusAcademico(contexto);
		guardarInscripcion(contexto);
		gestionarEnvioCorreoInscripcion(contexto);
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

	private void guardarInscripcion(InscripcionContextoDTO contexto) {
		List<InscripcionInsertDTO> inscripciones = mapearModelosInscripcion(contexto);
		inscripcionService.insertarInscripciones(inscripciones);
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
			throw new IllegalArgumentException("La lista de materias al construir el correo no puede estar vacía");
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

		return dto;
	}

	private int obtenerEstatusRecursamiento(InscripcionContextoDTO contexto, InscripcionMateriasDTO materia) {
		List<InscripcionBajasDTO> materiasBajas = contexto.getEstadoAcademico().getMateriasBajas();
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = contexto.getEstadoAcademico()
				.getMateriasReprobadas();
		return verificarRecursamiento(materiasBajas, materiasReprobadas, materia.getClavePrograma());
	}

	private int verificarRecursamiento(List<InscripcionBajasDTO> materiasBajas,
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
		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponibles(contexto);
		ResumenSeleccionMateriasDTO resumen = construirResumenSeleccion(materiasDisponibles);

		if (esNuevoIngreso(contexto) && esRegular(contexto)) {
			validarCargaPrimerSemestre(resumen, contexto);
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
					"Estimad(a/o) estudiante, debes seleccionar al menos 2 unidades didácticas obligatorias.");
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
						"Puedes seleccionar máximo " + maxProgramasRegulares + " unidades didácticas");
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
					"Puedes seleccionar máximo " + maxProgramasIrregulares + " unidades didácticas");
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
				throw new InscripcionException("Debes seleccionar al menos 1 unidades didáctica");
			}
			return;
		}

		if (resumen.getTotalSeleccionadas() < minProgramasPorPeriodo) {
			throw new InscripcionException(
					"Debes seleccionar al menos " + minProgramasPorPeriodo + " unidades didáctica(s)");
		}
	}

	/**
	 * Los estudiantes regulares de nuevo ingreso deben seleccionar sus materias
	 * obligatorias y optativas requeridas.
	 */
	private void validarCargaPrimerSemestre(ResumenSeleccionMateriasDTO resumen, InscripcionContextoDTO contexto)
			throws InscripcionException {
		List<InscripcionMateriasDTO> materiasDisponibles = contexto.getEstadoAcademico().getMateriasDisponibles();
		int cantidadMateriasOfertadas = materiasDisponibles.size();
		Long cantidadMaxMaterias = ConstantesGestor.CANT_MATERIAS_OBLIGATORIAS_EST_REGULAR_PRIMER_SEMESTRE
				+ ConstantesGestor.CANT_MATERIAS_OPTATIVAS_EST_REGULAR_PRIMER_SEMESTRE;

		if (cantidadMateriasOfertadas < cantidadMaxMaterias) {
			return;
		}

		if (esCargaAcademicaInvalidaPrimerSemestre(resumen.getObligatoriasSeleccionadas(),
				resumen.getOptativasSeleccionadas())) {

			throw new InscripcionException("Para finalizar la inscripción, debe seleccionar "
					+ ConstantesGestor.CANT_MATERIAS_OBLIGATORIAS_EST_REGULAR_PRIMER_SEMESTRE
					+ " unidades didácticas obligatorias y "
					+ ConstantesGestor.CANT_MATERIAS_OPTATIVAS_EST_REGULAR_PRIMER_SEMESTRE
					+ " unidades didácticas optativas.");
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
			throw new InscripcionException("Acepta los términos y condiciones");
		}
	}

	@Override
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
		contexto.setMensajeSeriacion(obtenerMensajeSeriacion(estadoAcademico));
		contexto.setMensajeLimiteReprobacionesAlcanzado(obtenerMensajeLimiteReprobacionesAlcanzado(estadoAcademico));
		return contexto;
	}

	private EstadoAcademicoDTO obtenerEstadoAcademico(InscripcionPersonaDTO persona, Date fechaActual,
			CreditosTotalesPlanDTO creditosTotalesPlan, LimitesCargaAcademicaDTO limitesCargaAcademica)
			throws InscripcionException {
		Boolean esNuevoIngreso = esNuevoIngreso(persona);

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

		List<InscripcionMateriasDTO> materiasDisponibles = obtenerMateriasDisponiblesParaInscripcion(materiasOfertadas,
				materiasCursadas, materiasReprobadas, esNuevoIngreso, esRegular, limitesCargaAcademica,
				estadoInscripcion, persona);

		List<InscripcionBajasDTO> materiasBajas = obtenerBajasDeMateriasSolicitadas(persona);

		EstadoAcademicoDTO estadoAcademico = crearEstadoAcademico(esNuevoIngreso, esRegular,
				cantidadMaximaMateriasElectivas, materiasCursadas, porcentajeCreditosCompletados, materiasReprobadas,
				materiasDisponibles, materiasBajas);

		return estadoAcademico;

	}

	private EstadoInscripcionEstudianteDTO obtenerEstadoInscripcion(InscripcionPersonaDTO persona,
			Long idProcesoInscripcion) throws InscripcionException {
		return inscripcionService.obtenerEstadoInscripcionEstudiante(persona.getIdPersona(), idProcesoInscripcion)
				.orElseThrow(() -> new InscripcionException("No existe el proceso de inscripción"));
	}

	private Long obtenerIdProcesoInscripcion(List<InscripcionMateriasDTO> materiasOfertadas) {
		if (!materiasOfertadas.isEmpty()) {
			materiasOfertadas.get(0).getIdProcesoInscripcion();
		}
		return 0l;
	}

	private String obtenerMensajeSeriacion(EstadoAcademicoDTO estadoAcademico) {
		if (estadoAcademico.getMateriasDisponibles().stream()
				.anyMatch(materia -> materia.getEsMateriaSeriada() != null && materia.getEsMateriaSeriada())) {
			return "Estimad(a/o) estudiante, la(s) unidades didácticas a las que deseas inscribirte están condicionadas a la aprobación previa de según la seriación establecida en tu plan de estudio";
		}
		return "";
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
			Boolean esNuevoIngreso, Boolean esRegular) {
		List<InscripcionMateriasReprobadasDTO> materiasReprobadas = inscripcionService
				.obtenerMateriasCursadasReprobadas(persona.getIdPersona());
		return materiasReprobadas;
	}

	private List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(InscripcionPersonaDTO persona) {
		return inscripcionService.obtenerMateriasCursadas(persona.getIdPersona());
	}

	private void validarInscripcionPrevia(Date fechaActual, InscripcionPersonaDTO persona)
			throws InscripcionPreviaException {
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
			Boolean esEstudianteRegular, LimitesCargaAcademicaDTO limitesCargaAcademica,
			EstadoInscripcionEstudianteDTO estadoInscripcion, InscripcionPersonaDTO persona) {

		List<InscripcionMateriasDTO> materiasOfertadasSinAprobadas = excluirMateriasAprobadas(materiasOfertadas,
				materiasCursadas);

		List<InscripcionMateriasDTO> materiasOfertadasSinReprobadasConLimiteAlcanzado = excluirMateriasReprobadasConLimiteAlcanzado(
				materiasOfertadasSinAprobadas, materiasReprobadas);

		List<InscripcionMateriasDTO> materiasDeAcuerdoASituacionAcademica = obtenerMateriasDeAcuerdoASituacionAcademica(
				materiasReprobadas, esEstudianteNuevoIngreso, esEstudianteRegular,
				materiasOfertadasSinReprobadasConLimiteAlcanzado, limitesCargaAcademica, estadoInscripcion, persona,
				materiasCursadas);

		List<InscripcionMateriasDTO> materiasConSeriacionValidada = aplicarValidacionDeSeriacion(
				materiasDeAcuerdoASituacionAcademica, materiasReprobadas, materiasCursadas);

		return materiasConSeriacionValidada;
	}

	private List<InscripcionMateriasDTO> excluirMateriasReprobadasConLimiteAlcanzado(
			List<InscripcionMateriasDTO> materiasOfertadasSinAprobadas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		Set<String> clavesMateriasReprobadasConLimiteAlcanzado = obtenerClavesMateriasReprobadasConLimiteAlcanzado(
				materiasReprobadas);

		return materiasOfertadasSinAprobadas.stream()
				.filter(materia -> !clavesMateriasReprobadasConLimiteAlcanzado.contains(materia.getClavePrograma()))
				.collect(Collectors.toList());

	}

	private Set<String> obtenerClavesMateriasReprobadasConLimiteAlcanzado(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {

		return materiasReprobadas.stream().filter(
				materia -> materia.getIntentosReprobados().equals(ConstantesGestor.LIMITE_REPROBACIONES_POR_MATERIA))
				.map(InscripcionMateriasReprobadasDTO::getClavePrograma).collect(Collectors.toSet());
	}

	private List<InscripcionMateriasDTO> aplicarValidacionDeSeriacion(List<InscripcionMateriasDTO> materiasDisponibles,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			List<InscripcionMateriasCursadasDTO> materiasCursadas) {

		Set<Long> idsMateriasReprobadas = obtenerIdsMateriasReprobadas(materiasReprobadas);
		Set<Long> idsMateriasCursadas = obtenerIdsMateriasCursadas(materiasCursadas);

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
			Boolean esEstudianteRegular, List<InscripcionMateriasDTO> materiasOfertadas,
			LimitesCargaAcademicaDTO limitesCargaAcademica, EstadoInscripcionEstudianteDTO estadoInscripcion,
			InscripcionPersonaDTO persona, List<InscripcionMateriasCursadasDTO> materiasCursadas) {

		if (esInscripcionExtraordinariaInicial(estadoInscripcion)) {
			// logger.info("esInscripcionExtraordinariaInicial");
			// Filtra las materias ofertadas y se queda solo con las del semestre ordinario
			// y todas las optativas ofertadas (de todos los semestres y bloques).
			materiasOfertadas = obtenerMateriasSemestreInscripcionOrdinariaConOptativas(materiasOfertadas,
					estadoInscripcion);
		}

		// Estudiantes regulares de nuevo ingreso
		if (esEstudianteNuevoIngreso && esEstudianteRegular) {
			// logger.info("Estudiantes regulares de nuevo ingreso");
			return obtenerMateriasEstudianteNuevoIngreso(materiasOfertadas);
		}

		// Verifica si las materias a msotrar de tipo optativas son opcionales tomar
		materiasOfertadas = modificaMateriasOptativasDeAcuerdoAProbacion(materiasOfertadas, materiasCursadas);

		// Estudiantes regulares que no son de nuevo ingreso
		if ((!esEstudianteNuevoIngreso && esEstudianteRegular) || esInscripcionOrdinariaInicial(estadoInscripcion)) {
			// logger.info("Estudiantes regulares que no son de nuevo ingreso");
			return obtenerMateriasPorAvanceAnualEstudianteRegular(materiasOfertadas, persona);
		}

		// Estudiantes irregulares de nuevo ingreso
		if (esEstudianteNuevoIngreso && !esEstudianteRegular) {
			// logger.info("Estudiantes irregulares de nuevo ingreso");
			return obtenerMateriasEstudianteNuevoIngreso(materiasOfertadas);
		}

		// Estudiantes irregulares que no son de nuevo ingreso
		if (!esEstudianteNuevoIngreso && !esEstudianteRegular) {
			// logger.info("Estudiantes irregulares que no son de nuevo ingreso");

			// Las materias reprobadas se vuelven obligatorias en la lista de materias
			// ofertadas
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas = marcarMateriasOfertadasReprobadas(
					materiasOfertadas, materiasReprobadas, limitesCargaAcademica);

			long cantidadMateriasReprobadasObligatorias = obtenerCantidadMateriasReprobadasObligatorias(
					materiasReprobadas);

			// Regla para respetar el avance anual (Leer descripcion del metodo
			// 'obtenerMateriasPorAvanceAnualIrregulares')
			if (cantidadMateriasReprobadasObligatorias >= 1
					&& cantidadMateriasReprobadasObligatorias <= ConstantesGestor.NUMERO_MAXIMO_MATERIAS_REPROBADAS) {
				logger.info("Estudiantes irregulares de a 1 a 3 reprobadas");
				return obtenerMateriasPorAvanceAnualIrregulares(materiasConReprobadasMarcadas, materiasReprobadas);
			}

			if (cantidadMateriasReprobadasObligatorias == 0) {
				logger.info("Estudiantes irregulares sin reprobadas obligatorias");
				return obtenerMateriasPorAvanceAnualSinTomarEnCuentaObligatorias(materiasConReprobadasMarcadas,
						persona);
			}

			// Regla cuando el estudiante reprueba mas de 4 materias del mismo semestre
			Map.Entry<String, Integer> semestreConMasReprobadas = buscarSemestreConMasReprobadas(materiasReprobadas);
			if (estaSemestreDentroDeMateriasOfertadas(semestreConMasReprobadas, materiasConReprobadasMarcadas)
					&& semestreConMasReprobadas.getValue() > ConstantesGestor.NUMERO_MAXIMO_MATERIAS_REPROBADAS) {
				logger.info("Estudiantes irregulares con más de 4 asignaturas reprobas en un semestre");
				return filtrarPorSemestreUOptativas(materiasConReprobadasMarcadas, semestreConMasReprobadas.getKey());
			}

			logger.info("Estudiantes irregulare con 4 o más reprobadas");
			// Regla cuando el estudiante reprueba 4 o mas materias de diferentes semestres
			return obtenerMateriasReprobadasUOptativas(materiasConReprobadasMarcadas, materiasReprobadas);
		}
		return Collections.emptyList();
	}

	private List<InscripcionMateriasDTO> modificaMateriasOptativasDeAcuerdoAProbacion(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasCursadasDTO> materiasCursadas) {

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
				boolean habilitarOpcionales = optativasAprobadas == ConstantesGestor.MATERIAS_OPTATIVAS_APROBADAS_POR_SEMESTRE;

				if (habilitarOpcionales) {
					if (m.getNombreTentativoPrograma() != null
							&& !m.getNombreTentativoPrograma().contains("(Opcional)")) {

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
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {

		Set<String> clavesMateriasReprobadas = materiasReprobadas.stream()
				.map(InscripcionMateriasReprobadasDTO::getClavePrograma).collect(Collectors.toSet());

		List<InscripcionMateriasDTO> materias = materiasOfertadas.stream()
				.filter(esMateriaReprobadaUOptativa(clavesMateriasReprobadas)).collect(Collectors.toList());

		long cantidadMateriasReprobadasOptativas = materiasReprobadas.stream()
				.filter(m -> InscripcionUtils.esMateriaOptativa(m.getTipoPrograma())).count();

		if (materiasReprobadas.size() > ConstantesGestor.CANTIDAD_MAXIMA_MATERIAS_REPROBADAS
				|| cantidadMateriasReprobadasOptativas >= ConstantesGestor.CANTIDAD_MAXIMA_MATERIAS_REPROBADAS_OPTATIVAS) {
			return limitarMateriasOptativas(materias, materiasReprobadas);
		}

		return materias;
	}

	private List<InscripcionMateriasDTO> limitarMateriasOptativas(List<InscripcionMateriasDTO> materias,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {

		Optional<String> optSemestreReprobado = buscarSemestreReprobadoMasAntiguo(materiasReprobadas);
		if (!optSemestreReprobado.isPresent()) {
			return materias;
		}

		int numeroSemestreReprobado = InscripcionUtils.obtenerNumeroSemestre(optSemestreReprobado.get());
		int numeroSemestreAdyacente = obtenerNumeroSemestreAdyacente(numeroSemestreReprobado);

		return materias.stream().filter(materia -> {
			if (InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma())) {
				int semestreMateria = InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura());
				if (semestreMateria != numeroSemestreReprobado && semestreMateria != numeroSemestreAdyacente) {
					return false;
				}
			}
			return true;
		}).collect(Collectors.toList());
	}

	private Predicate<? super InscripcionMateriasDTO> esMateriaReprobadaUOptativa(
			Set<String> clavesMateriasReprobadas) {
		return materia -> clavesMateriasReprobadas.contains(materia.getClavePrograma())
				|| InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma());
	}

	private List<InscripcionMateriasDTO> filtrarPorSemestreUOptativas(
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas, String semestreConMasReprobadas) {
		return materiasConReprobadasMarcadas.stream()
				.filter(materia -> materia.getEstructura().equalsIgnoreCase(semestreConMasReprobadas)).map(materia -> {
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

	private List<InscripcionMateriasDTO> obtenerMateriasPorAvanceAnualSinTomarEnCuentaObligatorias(
			List<InscripcionMateriasDTO> materiasOfertadas, InscripcionPersonaDTO persona) {

		String semestreDondeFaltanObligatorias = obtenerSemestre(persona.getIdPersona(), persona.getIdPlan(),
				materiasOfertadas.get(0).getEstructura());

		return materiasOfertadas.stream()
				.filter(materia -> materia.getEstructura().equalsIgnoreCase(semestreDondeFaltanObligatorias)
						|| materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OPTATIVA))
				.map(materia -> {
					if (materia.getEstructura().equalsIgnoreCase(semestreDondeFaltanObligatorias)
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
		int numeroSemestreAdyacente = obtenerNumeroSemestreAdyacente(numeroSemestreReprobado);

		return obtenerMateriasPermitidas(materiasOfertadas, numeroSemestreReprobado, numeroSemestreAdyacente);
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
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			LimitesCargaAcademicaDTO limitesCargaAcademica) {

		int maxReprobadasPermitidasIrregulares = Integer.valueOf(limitesCargaAcademica.getMaxProgramasIrregulares());

		/*
		 * Las claves en las materias optativas se pueden repetir, por eso se le
		 * concatena el idprograma para crear un id unico
		 */
		Set<String> clavesMateriasReprobadas = materiasReprobadas.stream()
				.map(InscripcionUtils::obtenerClaveUnicaPrograma).collect(Collectors.toSet());

		int cantidadMateriasMarcadas = 0;

		for (InscripcionMateriasDTO materia : materiasOfertadas) {
			boolean estaReprobada = clavesMateriasReprobadas.contains(InscripcionUtils.claveUnicaPrograma(materia));
			if (estaReprobada && !InscripcionUtils.esMateriaElectiva(materia.getTipoPrograma())
					&& cantidadMateriasMarcadas < maxReprobadasPermitidasIrregulares) {
				materia.setCheck(Boolean.TRUE);
				// if (InscripcionUtils.esMateriaObligatoria(materia.getTipoPrograma())) {
				materia.setDisabled(Boolean.TRUE);
				// }
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
			List<InscripcionMateriasDTO> materiasOfertadas, InscripcionPersonaDTO persona) {

		if (materiasOfertadas == null || materiasOfertadas.isEmpty()) {
			return Collections.emptyList();
		}

		int semestreAprobado = obtenerNumeroSemestreAprobado(materiasOfertadas, persona);

		int semestreAdyacente = obtenerNumeroSemestreAdyacente(semestreAprobado);

		return obtenerMateriasPermitidas(materiasOfertadas, semestreAprobado, semestreAdyacente);
	}

	private List<InscripcionMateriasDTO> obtenerMateriasPermitidas(List<InscripcionMateriasDTO> materiasOfertadas,
			int semestreAprobado, int semestreAdyacente) {
		return materiasOfertadas.stream()
				.filter(m -> perteneceASemestrePermitido(m, semestreAprobado, semestreAdyacente))
				.collect(Collectors.toList());
	}

	// Quitar solo las optativas que no pertenezcan a los dos semestres

	private boolean perteneceASemestrePermitido(InscripcionMateriasDTO materia, int semestreAprobado,
			int semestreAdyacente) {

		int semestreMateria = InscripcionUtils.obtenerNumeroSemestre(materia.getEstructura());

		return semestreMateria == semestreAprobado || semestreMateria == semestreAdyacente
				|| InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma());
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

	private String obtenerMensajeLimiteReprobacionesAlcanzado(EstadoAcademicoDTO estadoAcademico) {

		List<InscripcionMateriasReprobadasDTO> materiasReprobadasConIntentos = estadoAcademico.getMateriasReprobadas();
		Boolean esEstudianteNuevoIngreso = estadoAcademico.getEsNuevoIngreso();
		Boolean esEstudianteRegular = estadoAcademico.getEsRegular();

		if (esEstudianteNuevoIngreso || esEstudianteRegular) {
			return "";
		}

		// Solo aplica para estudiantes que NO sean de nuevo ingreso y que sean
		// IRREGULARES

		List<String> materiasConLimiteDeReprobacionesAlcanzados = obtenerNombresMateriasQueAlcanzaronLimiteReprobaciones(
				materiasReprobadasConIntentos);
		if (!materiasConLimiteDeReprobacionesAlcanzados.isEmpty()) {
			return crearMensajeLimiteReprobacionesAlcanzado(materiasConLimiteDeReprobacionesAlcanzados);
		}
		return "";

	}

	private String crearMensajeLimiteReprobacionesAlcanzado(List<String> nombresMaterias) {

		String materiasFormateadas = String.join(", ", nombresMaterias);

		String prefijoMateria = nombresMaterias.size() > 1 ? "las materias: " : "la materia: ";

		String mensaje = String.format(
				"Estimad(a/o) estudiante, dada su situación académica en la que ha reprobado %s%s más de %d veces, no le es permitido realizar una nueva inscripción. "
						+ "Contacte a la mesa de ayuda para revisar su caso.",
				prefijoMateria, materiasFormateadas, ConstantesGestor.LIMITE_REPROBACIONES_POR_MATERIA);

		return mensaje;
	}

	private List<String> obtenerNombresMateriasQueAlcanzaronLimiteReprobaciones(
			List<InscripcionMateriasReprobadasDTO> materias) {
		return materias.stream().filter(
				materia -> materia.getIntentosReprobados().equals(ConstantesGestor.LIMITE_REPROBACIONES_POR_MATERIA))
				.map(InscripcionMateriasReprobadasDTO::getNombrePrograma).collect(Collectors.toList());
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
			throw new InscripcionException(
					String.format("Solo puedes seleccionar un máximo de %d unidades didácticas electivas",
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
				throw new InscripcionException(
						"Solo se permite seleccionar una unidad didáctica optativa por bloque, por favor revisa tu selección.");
			} else if (esClaveOptativaYaSeleccionada(materiaSeleccionada, materiasDisponibles)) {
				throw new InscripcionException(
						"No es posible tomar la misma unidad didáctica dos veces, por favor revisa tu selección.");
			}

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

	private boolean esOptativaBloqueSemestreYaSeleccionada(InscripcionMateriasDTO materiaSeleccionada,
			List<InscripcionMateriasDTO> materiasDisponibles) {
		String bloqueSemestreBusqueda = materiaSeleccionada.getEstructura() + materiaSeleccionada.getSubestructura();

		// Descartar de la lista la materia seleccionada
		List<InscripcionMateriasDTO> primerFiltro = materiasDisponibles.stream()
				.filter(materia -> !materiaSeleccionada.getIdPrograma().equals(materia.getIdPrograma()))
				.collect(Collectors.toList());

		// Solo quedarse con materias optativas
		List<InscripcionMateriasDTO> segundoFiltro = primerFiltro.stream()
				.filter(materia -> InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma()))
				.collect(Collectors.toList());

		// Validar si ya existe una materia marcada con el mismo bloque y semestre
		boolean esMismoSemestreBloque = segundoFiltro.stream()
				.anyMatch(materia -> materia.getCheck().equals(Boolean.TRUE)
						&& esMismoSemestreBloque(bloqueSemestreBusqueda, materia));

		return esMismoSemestreBloque;
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
						"Para seleccionar unidades didácticas de este semestre, debes haber acreditado al menos el 50% del total de créditos de tu plan de estudios.");
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
			throw new IllegalArgumentException("La lista de materias al construir el correo no puede estar vacía");
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
