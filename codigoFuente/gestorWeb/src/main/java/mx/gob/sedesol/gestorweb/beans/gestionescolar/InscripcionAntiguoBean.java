package mx.gob.sedesol.gestorweb.beans.gestionescolar;

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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;

//@ManagedBean
//@ViewScoped
public class InscripcionAntiguoBean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(InscripcionAntiguoBean.class);

	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{correoElectronicoService}")
	private CorreoElectronicoService correoElectronicoService;

	private UsuarioSessionDTO usuarioEnSesion;

	private InscripcionPersonaDTO infoPersona;

	private List<InscripcionMateriasDTO> materiasOfertadas;

	private List<InscripcionMateriasDTO> materiasDisponibles;
	private List<InscripcionMateriasCursadasDTO> materiasCursadas;
	private List<InscripcionMateriasReprobadasDTO> materiasReprobadas;
	private List<InscripcionBajasDTO> materiasBajas;
	private Long cantidadMaximaMateriasElectivas;

	private CreditosTotalesPlanDTO creditosTotalesPlan;

	private Double porcentajeCreditosCompletados;

	private String mensaje;

	private Boolean mostrarMensajeSeriacion = Boolean.FALSE;

	private Boolean aceptaTerminos;

	private String terminosCondiciones;

	private Boolean mostrarTerminosCondiciones;

	private Date fechaActual;

	private Boolean esEstudianteRegular;

	private Boolean esEstudianteNuevoIngreso;

	private LimitesCargaAcademicaDTO maxMinPlan;

	private Boolean existeInscripcionPrevia;

	// Crear objeto estado academico alumno o situacion academica alumno
	// contendra materias cursadas, materias reprobadas, materias con baja,
	// porcentaje creditos completados , su informacion personal

	// Crear objeto contexto inscripcion
	// contendra el estado academico alumno, mas fecha incripcion, mas materias
	// ofertables, materias seleccionadas,
	// acepta terminos, existe inscripcion previa,

	// Refactorizar para crear metodos puros faciles de testear. Enviar logica de
	// negocio a la capa de servicio
	@PostConstruct
	public void init() {
		try {
			inicializarInscripcion();
		} catch (InscripcionException e) {
			manejarErrorDeInscripcion(e);
		} catch (Exception e) {
			manejarErrorInesperado(e);
		}
	}

	private void inicializarInscripcion() throws InscripcionException {
		
		configurarTerminosCondiciones();
		fechaActual = new Date();
		usuarioEnSesion = this.getUsuarioEnSession();
		infoPersona = inscripcionService.obtenerInscripcionPorPersona(usuarioEnSesion.getIdPersona().toString());
		creditosTotalesPlan = obtenerCreditosTotalesPorPlan(infoPersona.getIdPlan());
		maxMinPlan = validarMaximosMinimosMateriasPorPlan(infoPersona);
		validarInscripcionPrevia(infoPersona, fechaActual);
		esEstudianteNuevoIngreso = inscripcionService.esEstudianteNuevoIngreso(infoPersona.getIdPersona());
		esEstudianteRegular = inscripcionService.esEstudianteRegular(infoPersona.getIdPersona());
		materiasOfertadas = obtenerMateriasPorPeriodoInscripcion(infoPersona, fechaActual);
		if (puedeCursarMateriasElectivas(materiasOfertadas)) {
			cantidadMaximaMateriasElectivas = contarMateriasElectivasConSemestreValido(materiasOfertadas);
			materiasOfertadas = excluirMateriasElectivas(materiasOfertadas);
			List<InscripcionMateriasDTO> materiasElectivasOtrosPlanes = obtenerMateriasElectivasDeOtrosPlanes(
					infoPersona, fechaActual);
			materiasOfertadas = agregarMateriasElectivasDeOtrosPlanes(materiasOfertadas, materiasElectivasOtrosPlanes);
		}

		materiasCursadas = inscripcionService.obtenerMateriasCursadas(infoPersona.getIdPersona());
		porcentajeCreditosCompletados = obtenerPorcentajeCreditosCompletados(materiasCursadas, creditosTotalesPlan);
		materiasReprobadas = inscripcionService.obtenerMateriasCursadasReprobadas(infoPersona.getIdPersona());
		materiasBajas = inscripcionService.obtenerBajasDeMateriasSolicitadas(infoPersona.getIdPersona());

		validarLimiteReprobaciones(materiasReprobadas, esEstudianteNuevoIngreso, esEstudianteRegular);
		materiasDisponibles = obtenerMateriasParaInscripcion(materiasOfertadas, materiasCursadas, materiasReprobadas,
				esEstudianteNuevoIngreso, esEstudianteRegular);

	}

	private void configurarTerminosCondiciones() {
		String verTerminosCondiciones = parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_MOSTRAR_TERMINOS_CONDICIONES.getClave());
		terminosCondiciones = parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_TERMINOS_CONDICIONES.getClave());
		mostrarTerminosCondiciones = estaActivoTerminosYCondiciones(verTerminosCondiciones);
		aceptaTerminos = !mostrarTerminosCondiciones;
	}

	private boolean estaActivoTerminosYCondiciones(String verTerminosCondiciones) {
		return verTerminosCondiciones != null && !verTerminosCondiciones.trim().isEmpty()
				&& !verTerminosCondiciones.trim().equals("0");
	}

	private List<InscripcionMateriasDTO> agregarMateriasElectivasDeOtrosPlanes(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasDTO> materiasElectivasOtrosPlanes) {

		if (materiasElectivasOtrosPlanes == null || materiasElectivasOtrosPlanes.isEmpty()) {
			return materiasOfertadas;
		}

		List<InscripcionMateriasDTO> materiasCombinadas = new ArrayList<>(materiasOfertadas);
		materiasCombinadas.addAll(materiasElectivasOtrosPlanes);
		return materiasCombinadas;
	}

	private List<InscripcionMateriasDTO> excluirMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas) {
		return materiasOfertadas.stream().filter(materia -> !esMateriaElectiva(materia)).collect(Collectors.toList());
	}

	private long contarMateriasElectivasConSemestreValido(List<InscripcionMateriasDTO> materiasOfertadas) {
		return materiasOfertadas.stream()
				.filter(materia -> esMateriaElectiva(materia) && esSemestreValidoParaElectiva(materia)).count();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasElectivasDeOtrosPlanes(InscripcionPersonaDTO infoPersona,
			Date fechaActual) {
		return inscripcionService.obtenerMateriasElectivasDeOtrosPlanes(infoPersona.getIdPlan(), fechaActual,
				infoPersona.getIdConvocatoria(), ConstantesGestorWeb.NUMERO_SEMESTRE_CINCO,
				ConstantesGestorWeb.NUMERO_SEMESTRE_SEIS);
	}

	private boolean puedeCursarMateriasElectivas(List<InscripcionMateriasDTO> materiasOfertadas) {
		return materiasOfertadas.stream()
				.anyMatch(materia -> esMateriaElectiva(materia) && esSemestreValidoParaElectiva(materia));
	}

	private boolean esSemestreValidoParaElectiva(InscripcionMateriasDTO materia) {
		int numeroSemestre = obtenerNumeroSemestre(materia.getEstructura());
		return numeroSemestre >= ConstantesGestorWeb.SEMESTRE_MINIMO_PARA_ELECTIVAS;
	}

	private void manejarErrorDeInscripcion(InscripcionException e) {
		mostrarMensajeDialog(e.getMessage());
	}

	private void manejarErrorInesperado(Exception e) {
		logger.error("Error inesperado en inicializacion de inscripcion", e);
		mostrarMensajeDialog("Ha ocurrido un error inesperado");
	}

	private Double obtenerPorcentajeCreditosCompletados(List<InscripcionMateriasCursadasDTO> materiasCursadas,
			CreditosTotalesPlanDTO creditosTotalesPlan) {
		long totalCreditos = creditosTotalesPlan.getTotalCreditos(); // 100%

		long totalHastaElMomento = materiasCursadas.stream()
				.filter(mc -> ConstantesGestorWeb.MATERIA_APROBADA.equals(mc.getEstatusAprobacion()))
				.mapToLong(InscripcionMateriasCursadasDTO::getCreditos).sum();

		if (totalCreditos == 0)
			return 0.0;

		return (totalHastaElMomento * 100.0) / totalCreditos;
	}

	private CreditosTotalesPlanDTO obtenerCreditosTotalesPorPlan(Long idPlan) throws InscripcionException {
		return inscripcionService.obtenerCreditosTotalesPorPlan(idPlan)
				.orElseThrow(() -> new InscripcionException("No se capturaron los creditos totales del plan"));
	}

	private void validarInscripcionPrevia(InscripcionPersonaDTO infoPersona, Date fechaActual) throws InscripcionException {
		existeInscripcionPrevia = inscripcionService.existeInscripcionPrevia(infoPersona, fechaActual);
		if (existeInscripcionPrevia) {
			throw new InscripcionException("Ya cuenta con una inscripción para este periodo.");
		}
	}

	private List<InscripcionMateriasDTO> obtenerMateriasParaInscripcion(List<InscripcionMateriasDTO> materiasOfertadas,
			List<InscripcionMateriasCursadasDTO> materiasCursadas,
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

				if (materiaAntecedente != null && esElMismoSemestre(materia, materiaAntecedente)
						&& !esMateriaElectiva(materia)) {
					marcarMateriasSeriadas(materia, materiaAntecedente);
				}
			}
		}

		return materiasDisponibles.stream()
				.filter(m -> m.getCheck() || !esMateriaSeriadaReprobada(idsMateriasReprobadas, m)
						|| !esMateriaSeriadaYNoCursada(idsMateriasCursadas, m))
				.collect(Collectors.toList());
	}

	private void marcarMateriasSeriadas(InscripcionMateriasDTO materia, InscripcionMateriasDTO materiaAntecedente) {
		materia.setCheck(Boolean.TRUE);
		materia.setDisabled(Boolean.TRUE);
		materiaAntecedente.setCheck(Boolean.TRUE);
		materiaAntecedente.setDisabled(Boolean.TRUE);
		mostrarMensajeSeriacion = Boolean.TRUE;
	}

	private boolean esElMismoSemestre(InscripcionMateriasDTO materia, InscripcionMateriasDTO m) {
		return m.getEstructura().equalsIgnoreCase(materia.getEstructura());
	}

	private Set<Long> obtenerIdsMateriasCursadas(List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		return materiasCursadas.stream().map(InscripcionMateriasCursadasDTO::getIdPrograma).collect(Collectors.toSet());
	}

	private Set<Long> obtenerIdsMateriasReprobadas(List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		return materiasReprobadas.stream().map(InscripcionMateriasReprobadasDTO::getIdPrograma)
				.collect(Collectors.toSet());
	}

	private boolean esMateriaSeriadaReprobada(Set<Long> idsMateriasReprobadas, InscripcionMateriasDTO m) {
		return esMateriaSeriada(m) && esMateriaReprobada(idsMateriasReprobadas, m);
	}

	private boolean esMateriaSeriadaYNoCursada(Set<Long> idsMateriasCursadas, InscripcionMateriasDTO m) {
		return esMateriaSeriada(m) && !esMateriaCursada(idsMateriasCursadas, m);
	}

	private boolean esMateriaReprobada(Set<Long> idsMateriasReprobadas, InscripcionMateriasDTO m) {
		return idsMateriasReprobadas.contains(m.getIdProgramaAntecedente());
	}

	private boolean esMateriaCursada(Set<Long> idsMateriasCursadas, InscripcionMateriasDTO m) {
		return idsMateriasCursadas.contains(m.getIdProgramaAntecedente());
	}

	private boolean esMateriaSeriada(InscripcionMateriasDTO m) {
		return m.getIdProgramaAntecedente() != null;
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
			if (materiasReprobadas.size() < ConstantesGestorWeb.NUMERO_MAXIMO_MATERIAS_REPROBADAS) {
				return obtenerMateriasPorAvanceAnualIrregulares(materiasConReprobadasMarcadas, materiasReprobadas);
			}

			// Regla cuando el estudiante reprueba mas de 4 materias del mismo semestre
			Map.Entry<String, Integer> semestreConMasReprobadas = buscarSemestreConMasReprobadas(materiasReprobadas);
			if (semestreConMasReprobadas.getValue() > ConstantesGestorWeb.NUMERO_MAXIMO_MATERIAS_REPROBADAS) {
				return filtrarPorSemestreUOptativas(materiasConReprobadasMarcadas, semestreConMasReprobadas.getKey());
			}

			// Regla cuando el estudiante reprueba 4 o mas materias de diferentes semestres
			return obtenerMateriasReprobadasUOptativas(materiasConReprobadasMarcadas, materiasReprobadas);
		}
		return Collections.emptyList();
	}

	private List<InscripcionMateriasDTO> filtrarPorSemestreUOptativas(
			List<InscripcionMateriasDTO> materiasConReprobadasMarcadas, String semestreConMasReprobadas) {
		return materiasConReprobadasMarcadas.stream()
				.filter(m -> m.getEstructura().equalsIgnoreCase(semestreConMasReprobadas) || esMateriaOptativa(m))
				.map(m -> {
					if (m.getEstructura().equalsIgnoreCase(semestreConMasReprobadas)
							&& m.getTipoPrograma().equalsIgnoreCase(ConstantesGestorWeb.TEXTO_MATERIA_OBLIGATORIA)) {
						m.setCheck(Boolean.TRUE);
						m.setDisabled(Boolean.TRUE);
					}
					return m;
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

		int numeroSemestreReprobado = obtenerNumeroSemestre(optSemestreReprobado.get());
		int numeroSemestreAdyacente = esSemestrePar(numeroSemestreReprobado) ? numeroSemestreReprobado - 1
				: numeroSemestreReprobado + 1;

		return materiasOfertadas.stream().filter(m -> {
			int numeroSemestre = obtenerNumeroSemestre(m.getEstructura());
			return numeroSemestre == numeroSemestreReprobado || numeroSemestre == numeroSemestreAdyacente
					|| esMateriaOptativa(m);
		}).collect(Collectors.toList());
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
		int numeroSemestreAprobado = obtenerNumeroSemestre(semestre);

		int semestreAdyacente = esSemestrePar(numeroSemestreAprobado) ? numeroSemestreAprobado - 1
				: numeroSemestreAprobado + 1;

		return materiasOfertadas.stream().filter(m -> {
			int numeroSemestre = obtenerNumeroSemestre(m.getEstructura());
			return numeroSemestre == numeroSemestreAprobado || numeroSemestre == semestreAdyacente
					|| esMateriaOptativa(m);
		}).collect(Collectors.toList());
	}

	private boolean esSemestrePar(int numeroSemestreAprobado) {
		return numeroSemestreAprobado % 2 == 0;
	}

	private int obtenerNumeroSemestre(String semestre) {
		String[] partes = semestre.split(" ");
		int numeroSemestre = Integer.parseInt(partes[partes.length - 1]);
		return numeroSemestre;
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
		return mpi -> clavesMateriasReprobadas.contains(mpi.getClavePrograma()) || esMateriaOptativa(mpi);
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
				.filter(mc -> mc.getEstatusAprobacion().equals(ConstantesGestorWeb.MATERIA_APROBADA))
				.map(InscripcionMateriasCursadasDTO::getClavePrograma).collect(Collectors.toSet());
	}

	private List<InscripcionMateriasDTO> marcarMateriasOfertadasReprobadas(
			List<InscripcionMateriasDTO> materiasOfertadas, List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {

		/*
		 * Las claves en las materias optativas se pueden repetir, por eso se le
		 * concatena el idprograma para crear un id unico
		 */
		Set<String> clavesMateriasReprobadas = materiasReprobadas.stream().map(this::claveUnicaPrograma)
				.collect(Collectors.toSet());

		materiasOfertadas.forEach(mpi -> {
			boolean estaReprobada = clavesMateriasReprobadas.contains(claveUnicaPrograma(mpi));
			if (estaReprobada && !esMateriaElectiva(mpi)) {
				mpi.setCheck(Boolean.TRUE);
				mpi.setDisabled(Boolean.TRUE);
			}
		});

		return materiasOfertadas;

	}

	private String claveUnicaPrograma(InscripcionMateriasReprobadasDTO mcr) {
		return mcr.getClavePrograma() + ":" + mcr.getIdPrograma();
	}

	private String claveUnicaPrograma(InscripcionMateriasDTO mpi) {
		return mpi.getClavePrograma() + ":" + mpi.getIdPrograma();
	}

	private List<InscripcionMateriasDTO> obtenerMateriasEstudianteNuevoIngreso(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion) {
		List<InscripcionMateriasDTO> materiasPrimerPeriodo = obtenerMateriasDelPrimerPeriodo(
				materiasPorPeriodoInscripcion);
		return marcarMateriasObligatorias(materiasPrimerPeriodo);
	}

	private void validarLimiteReprobaciones(List<InscripcionMateriasReprobadasDTO> materiasReprobadasConIntentos,
			Boolean esEstudianteNuevoIngreso, Boolean esEstudianteRegular) throws InscripcionException {
		if (esEstudianteNuevoIngreso || esEstudianteRegular) {
			return;
		}

		/*
		 * Solo aplica para estudiantes que NO sean de nuevo ingreso y que sean
		 * IRREGULARES
		 */

		if (haAlcanzadoMaximoDeReprobaciones(materiasReprobadasConIntentos)) {
			String mensaje = String.format(
					"Estimad(a/o) estudiante, dada su situación académica en la que ha reprobado la misma asignatura más de %d veces no le es permitido realizar una nueva inscripción, contacte a la mesa de ayuda para revisar su caso.",
					ConstantesGestorWeb.LIMITE_REPROBACIONES_POR_MATERIA);

			throw new InscripcionException(mensaje);
		}
	}

	private LimitesCargaAcademicaDTO validarMaximosMinimosMateriasPorPlan(InscripcionPersonaDTO infoPersona)
			throws InscripcionException {
		return inscripcionService.obtenerLimitesCargaAcademicaPorPlan(infoPersona.getIdPlan())
				.orElseThrow(() -> new InscripcionException("No existen máximos y mínimos configurados en el plan"));
	}

	private boolean haAlcanzadoMaximoDeReprobaciones(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadasConIntentos) {
		return materiasReprobadasConIntentos.stream().anyMatch(
				mcr -> mcr.getIntentosReprobados().equals(ConstantesGestorWeb.LIMITE_REPROBACIONES_POR_MATERIA));
	}

	private List<InscripcionMateriasDTO> marcarMateriasObligatorias(
			List<InscripcionMateriasDTO> materiasPorPeriodoInscripcion) {
		return materiasPorPeriodoInscripcion.stream().map(mpi -> {
			if (esMateriaObligatoria(mpi)) {
				mpi.setCheck(Boolean.TRUE);
				mpi.setDisabled(Boolean.TRUE);
			}
			return mpi;
		}).collect(Collectors.toList());
	}

	private Boolean esMateriaObligatoria(InscripcionMateriasDTO materia) {
		return materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestorWeb.TEXTO_MATERIA_OBLIGATORIA);
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
		return materiasPorPeriodoInscripcion.stream().filter(mpi -> {
			String numeroSemestre = obtenerNumeroSemestre(mpi);
			return numeroSemestre.equalsIgnoreCase(ConstantesGestorWeb.NUMERO_PRIMER_ESTRUCTURA);
		}).collect(Collectors.toList());
	}

	private String obtenerNumeroSemestre(InscripcionMateriasDTO mpi) {
		String[] textoSemestre = mpi.getEstructura().split(ConstantesGestorWeb.ESPACIO_EN_BLANCO);
		String numeroSemestre = textoSemestre[textoSemestre.length - 1];
		return numeroSemestre;
	}

	private List<InscripcionMateriasDTO> obtenerMateriasPorPeriodoInscripcion(InscripcionPersonaDTO persona,
			Date fechaActual) {
		return inscripcionService.obtenerMateriasPorPeriodoInscripcion(persona.getIdPlan(), fechaActual,
				persona.getIdConvocatoria());
	}

	public void verificarAsignaturas() {

	}

	public void finalizarInscripcion() {
		
		if (Boolean.FALSE.equals(aceptaTerminos)) {
			mostrarMensajeDialog("Acepta términos y condiciones");
			return;
		}

		Long cantidadMateriasSeleccionadas = contarMateriasSeleccionadas();
		Long cantidadMateriasObligatoriasSeleccionadas = contarMateriasSeleccionadasPorTipo(
				ConstantesGestorWeb.TEXTO_MATERIA_OBLIGATORIA);
		Long cantidadMateriasOptativasSeleccionadas = contarMateriasSeleccionadasPorTipo(
				ConstantesGestorWeb.TEXTO_MATERIA_OPTATIVA);
		Long cantidadMateriasObligatoriasDisponibles = contarMateriasDisponiblesPorTipo(
				ConstantesGestorWeb.TEXTO_MATERIA_OBLIGATORIA);

		if (esEstudianteNuevoIngreso && esEstudianteRegular) {

			/*
			 * Los estudiantes regulares de nuevo ingreso deben seleccionar sus materias
			 * obligatorias y optativas requeridas.
			 */
			if (esCargaAcademicaInvalidaPrimerSemestre(cantidadMateriasObligatoriasSeleccionadas,
					cantidadMateriasOptativasSeleccionadas)) {
				mostrarMensajeDialog("Para finalizar la inscripción, debe seleccionar "
						+ ConstantesGestorWeb.CANT_MATERIAS_OBLIGATORIAS_EST_REGULAR_PRIMER_SEMESTRE
						+ " materias obligatorias y "
						+ ConstantesGestorWeb.CANT_MATERIAS_OPTATIVAS_EST_REGULAR_PRIMER_SEMESTRE
						+ " materias optativas.");
				return;
			}
		}

		if (!esEstudianteNuevoIngreso) {

			Integer minProgramasPorPeriodo = Integer.valueOf(maxMinPlan.getMinProgramasPorPeriodo());
			Integer maxMateriasRegular = Integer.valueOf(maxMinPlan.getMaxProgramasRegulares());
			Integer maxMateriasIrregular = Integer.valueOf(maxMinPlan.getMaxProgramasIrregulares());

			/*
			 * A partir del segundo semestre, las materias seleccionadas deben cumplir con
			 * el minimo de materias por periodo.
			 */
			if (cantidadMateriasSeleccionadas < minProgramasPorPeriodo) {
				mostrarMensajeDialog("Debes seleccionar al menos " + minProgramasPorPeriodo + " materia(s)");
				return;
			}

			if (esEstudianteRegular) {
				/*
				 * A partir del segundo semestre, los estudiantes regulares no pueden elegir mas
				 * materias del maximo permitido
				 */
				if (cantidadMateriasSeleccionadas > maxMateriasRegular) {
					mostrarMensajeDialog("Puedes seleccionar máximo " + maxMateriasRegular + " materias");
					return;
				}
			}

			if (esEstudianteIrregular()) {
				/*
				 * A partir del segundo semestre, los estudiantes irregulares no pueden elegir
				 * mas materias del maximo permitido
				 */
				if (cantidadMateriasSeleccionadas > maxMateriasIrregular) {
					mostrarMensajeDialog("Puedes seleccionar máximo " + maxMateriasIrregular + " materias");
					return;
				}
			}

			/*
			 * A partir del segundo semestre, los estudiantes regulares e irregulares no
			 * podran seleccionar menos de dos materias obligatorias si las tienen
			 * disponibles.
			 */
			if (cantidadMateriasObligatoriasDisponibles >= 2 && cantidadMateriasObligatoriasSeleccionadas < 2) {
				mostrarMensajeDialog("Estimad(a/o) estudiante, debes seleccionar al menos 2 materias obligatorias.");
				return;
			}

		}

//
		List<InscripcionInsertDTO> inscripciones = materiasDisponibles.stream().filter(materia -> materia.getCheck())
				.map(this::convertirAModeloInscripcion).collect(Collectors.toList());
//
//			// Llama al servicio para insertar las inscripciones
		inscripcionService.insertarInscripciones(inscripciones);
		existeInscripcionPrevia = Boolean.TRUE;
		materiasDisponibles = new ArrayList<>();
//
//			enviarCorreoInscripcion(infoPersona, materiasSeleccionadasYObligatorias);
//			mensaje = "Inscripcion de materias completa";
//			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
		mostrarMensajeDialog("Inscripcion de materias completa");
//			logger.info("Materias seleccionadas guardadas: " + materiasSeleccionadasYObligatorias.size());
//		}

		logger.info("PROCESO DE INSCRIPCION FINALIZADO");
	}

	private long contarMateriasSeleccionadas() {
		return materiasDisponibles.stream().filter(md -> md.getCheck()).count();
	}

	private boolean esEstudianteIrregular() {
		return !esEstudianteRegular.booleanValue();
	}

	private boolean esCargaAcademicaInvalidaPrimerSemestre(Long cantidadMateriasObligatorias,
			Long cantidadMateriasOptativas) {
		return !cantidadMateriasObligatorias
				.equals(ConstantesGestorWeb.CANT_MATERIAS_OBLIGATORIAS_EST_REGULAR_PRIMER_SEMESTRE)
				|| !cantidadMateriasOptativas
						.equals(ConstantesGestorWeb.CANT_MATERIAS_OPTATIVAS_EST_REGULAR_PRIMER_SEMESTRE);
	}

	private long contarMateriasSeleccionadasPorTipo(String tipoPrograma) {
		return materiasDisponibles.stream().filter(md -> {
			return estaSeleccionada(md) && md.getTipoPrograma().equalsIgnoreCase(tipoPrograma);
		}).count();
	}

	private long contarMateriasDisponiblesPorTipo(String tipoPrograma) {
		return materiasDisponibles.stream().filter(md -> md.getTipoPrograma().equalsIgnoreCase(tipoPrograma)).count();
	}

	public void validarSeleccion(InscripcionMateriasDTO materiaSeleccionada) {
		if (materiaSeleccionada.getCheck().equals(Boolean.FALSE)) {
			return;
		}

		try {
			validarPorcentajeAvanceCreditos(porcentajeCreditosCompletados, materiaSeleccionada);
			validarSeleccionMateriasOctavoSemestre(materiaSeleccionada, materiasReprobadas);
			validarSeleccionOptativas(materiaSeleccionada);
			if (esMateriaElectiva(materiaSeleccionada)) {
				validarSeleccionElectivas(materiaSeleccionada);
			}
		} catch (InscripcionException ie) {
			materiaSeleccionada.setCheck(Boolean.FALSE);
			mostrarMensajeDialog(ie.getMessage());
		}
	}

	private void validarSeleccionElectivas(InscripcionMateriasDTO materiaSeleccionada) throws InscripcionException {

		Long cantidadElectivasSeleccionadas = materiasDisponibles.stream()
				.filter(m -> estaSeleccionada(m) && esMateriaElectiva(m)).count();

		if (cantidadElectivasSeleccionadas > cantidadMaximaMateriasElectivas) {
			throw new InscripcionException(String.format("Solo puedes seleccionar un máximo de %d materias electivas",
					cantidadMaximaMateriasElectivas));
		}

	}

	private boolean estaSeleccionada(InscripcionMateriasDTO m) {
		return m.getCheck().equals(Boolean.TRUE);
	}

	private boolean noEsLaMismaSeleccionada(InscripcionMateriasDTO materiaSeleccionada, InscripcionMateriasDTO m) {
		return !materiaSeleccionada.getIdPrograma().equals(m.getIdPrograma());
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

	private Optional<InscripcionMateriasReprobadasDTO> buscarMateriaReprobadaDeTerceroASextoSeriada(
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		return materiasReprobadas.stream().filter(mcr -> {
			int numeroSemestre = obtenerNumeroSemestre(mcr.getEstructura());
			return esMateriaDeTerceroASextoSemestre(numeroSemestre) && esMateriaSeriada(mcr);
		}).findAny();
	}

	private boolean esMateriaSeriada(InscripcionMateriasReprobadasDTO mcr) {
		return mcr.getIdProgramaAntecedente() != null;
	}

	private boolean esMateriaDeTerceroASextoSemestre(int numeroSemestre) {
		return numeroSemestre >= ConstantesGestorWeb.TERCER_SEMESTRE
				&& numeroSemestre <= ConstantesGestorWeb.SEXTO_SEMESTRE;
	}

	private boolean esMateriaOctavoSemestre(InscripcionMateriasDTO materiaSeleccionada) {
		int numeroSemestre = obtenerNumeroSemestre(materiaSeleccionada.getEstructura());
		return numeroSemestre == ConstantesGestorWeb.OCTAVO_SEMESTRE;
	}

	private void validarPorcentajeAvanceCreditos(Double porcentajeCreditosCompletados,
			InscripcionMateriasDTO materiaSeleccionada) throws InscripcionException {
		if (esMateriaMayorASextoSemestre(materiaSeleccionada)) {
			if (porcentajeCreditosCompletados < ConstantesGestorWeb.PORCENTAJE_CREDITOS_REQUERIDOS_SEPTIMO_SEMESTRE) {
				throw new InscripcionException(
						"Para seleccionar materias de este semestre, debes haber acreditado al menos el 50% del total de créditos de tu plan de estudios.");
			}
		}
	}

	private boolean esMateriaMayorASextoSemestre(InscripcionMateriasDTO materiaSeleccionada) {
		int numeroSemestre = obtenerNumeroSemestre(materiaSeleccionada.getEstructura());
		return numeroSemestre > ConstantesGestorWeb.SEXTO_SEMESTRE;
	}

	public void validarSeleccionOptativas(InscripcionMateriasDTO materiaSeleccionada) throws InscripcionException {
		if (esMateriaOptativa(materiaSeleccionada)) {
			if (esOptativaBloqueSemestreYaSeleccionada(materiaSeleccionada)) {
				throw new InscripcionException("Solo se permite seleccionar una optativa por bloque y semestre");
			} else if (esClaveOptativaYaSeleccionada(materiaSeleccionada)) {
				throw new InscripcionException("No es posible tomar la misma asignatura dos veces");
			}

		}
	}

	private void mostrarMensajeDialog(String mensaje) {
		this.mensaje = mensaje;
		RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
	}

	private boolean esClaveOptativaYaSeleccionada(InscripcionMateriasDTO materiaSeleccionada) {
		return materiasDisponibles.stream().filter(md -> esIdProgramaDiferente(materiaSeleccionada, md))
				.filter(md -> esMateriaOptativa(md)).anyMatch(md -> esClaveProgramaIgual(materiaSeleccionada, md));
	}

	private boolean esOptativaBloqueSemestreYaSeleccionada(InscripcionMateriasDTO materiaSeleccionada) {
		String bloqueSemestreBusqueda = materiaSeleccionada.getEstructura() + materiaSeleccionada.getSubestructura();
		return materiasDisponibles.stream().filter(md -> esIdProgramaDiferente(materiaSeleccionada, md))
				.filter(md -> esMateriaOptativa(md)).anyMatch(md -> esMismoSemestreBloque(bloqueSemestreBusqueda, md));
	}

	private boolean esMismoSemestreBloque(String bloqueSemestreBusqueda, InscripcionMateriasDTO md) {
		String bloqueSemestre = md.getEstructura() + md.getSubestructura();
		return bloqueSemestreBusqueda.equalsIgnoreCase(bloqueSemestre);
	}

	private boolean esMateriaOptativa(InscripcionMateriasDTO materia) {
		return materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestorWeb.TEXTO_MATERIA_OPTATIVA);
	}

	private boolean esMateriaElectiva(InscripcionMateriasDTO materia) {
		return materia.getTipoPrograma().equalsIgnoreCase(ConstantesGestorWeb.TEXTO_MATERIA_ELECTIVA);
	}

	private boolean esIdProgramaDiferente(InscripcionMateriasDTO materiaSeleccionada, InscripcionMateriasDTO md) {
		return noEsLaMismaSeleccionada(md, materiaSeleccionada);
	}

	private boolean esClaveProgramaIgual(InscripcionMateriasDTO materiaSeleccionada, InscripcionMateriasDTO md) {
		return md.getClavePrograma().equalsIgnoreCase(materiaSeleccionada.getClavePrograma());
	}

	public void enviarCorreoInscripcion(InscripcionPersonaDTO inscripcion,
			List<InscripcionMateriasDTO> materiasSeleccionadas) {
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		String currentYear = yearFormat.format(new Date()).toString();
		String periodo = currentYear + (inscripcion.getIdPlan().toString().length() == 1 ? "0" + inscripcion.getIdPlan()
				: inscripcion.getIdPlan().toString());

		StringBuilder nombreCompleto = new StringBuilder(inscripcion.getNombre()).append(" ")
				.append(inscripcion.getPrimerApellido()).append(" ").append(inscripcion.getSegundoApellido());

		// Crear el contenido de la lista de asignaturas seleccionadas
		StringBuilder listaAsignaturas = new StringBuilder("<ul>");
		for (InscripcionMateriasDTO materia : materiasSeleccionadas) {
			listaAsignaturas.append("<li>").append(materia.getNombrePlan()).append(" - ").append(materia.getClavePlan())
					.append("</li>");
		}
		listaAsignaturas.append("</ul>");

		// Configurar el correo
		CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();
		correoDto.setTitulo("Confirmación de Inscripción");
		correoDto.setAsunto("Inscripción Completa");
		correoDto.setDestinatarios(Collections.singletonList(inscripcion.getCorreo()));
		correoDto.setRemitente(parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));

		// Formatear el contenido del correo con los datos de inscripción
		correoDto.setContenido(MessageFormat.format(
				"<p>Estimado estudiante {0},</p>"
						+ "<p>Te confirmamos que has concluido exitosamente tu inscripción.</p>"
						+ "<p>A continuación, te mostramos tus asignaturas para el período {1}:</p>" + "{2}", // Lista
																												// de
																												// asignaturas
				nombreCompleto.toString(), periodo, listaAsignaturas.toString()));

		correoElectronicoService.enviaCorreoElectronico(correoDto);
	}

	private InscripcionInsertDTO convertirAModeloInscripcion(InscripcionMateriasDTO materia) {
		InscripcionInsertDTO dto = new InscripcionInsertDTO();

		dto.setIdPersona(infoPersona.getIdPersona());
		dto.setPrograma(materia.getNombrePlan());
		dto.setAsignatura(materia.getNombreTentativoPrograma());
		dto.setGroupBase(generarGroupBase(materia));
		dto.setIdPlan(materia.getIdPlan());
		dto.setIdPrograma(materia.getIdPrograma());
		dto.setIdEvento(ConstantesGestorWeb.SIN_EVENTO);
		dto.setNivel(materia.getNivelEnsenanza());
		dto.setDivision(generarAcronimo(materia.getDivision()));
		dto.setProfileFieldPerfil(obtenerPerfil(materia));
		dto.setBloque(obtenerBloque(materia));
		dto.setClaveAsig(materia.getClavePrograma());
		dto.setNuevoIngreso(obtenerEstatusNuevoIngreso());
		dto.setRecursamiento(obtenerEstatusRecursamiento(materiasBajas, materiasReprobadas, materia));
		dto.setAlta(ConstantesGestorWeb.SOLICITO_ALTA_EL_ESTUDIANTE);
		dto.setSemestre(obtenerNumeroSemestre(materia.getEstructura()));
		dto.setFechaRegistro(new Date());

		return dto;
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

	private int obtenerEstatusRecursamiento(List<InscripcionBajasDTO> materiasBajas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, InscripcionMateriasDTO materia) {
		return verificarRecursamiento(materiasBajas, materiasReprobadas, materia.getClavePrograma());
	}

	private Integer obtenerEstatusNuevoIngreso() {
		return esEstudianteNuevoIngreso ? ConstantesGestorWeb.ES_ESTUDIANTE_NUEVO_INGRESO
				: ConstantesGestorWeb.NO_ES_ESTUDIANTE_NUEVO_INGRESO;
	}

	public int verificarRecursamiento(List<InscripcionBajasDTO> materiasBajas,
			List<InscripcionMateriasReprobadasDTO> materiasReprobadas, String clavePrograma) {

		if (esMateriaDadaDeBaja(materiasBajas, clavePrograma)) {
			return ConstantesGestorWeb.CURSANDO_MATERIA_POR_PRIMERA_VEZ;
		}

		if (esMateriaReprobada(materiasReprobadas, clavePrograma)) {
			return ConstantesGestorWeb.RECURSANDO_MATERIA;
		}

		return ConstantesGestorWeb.CURSANDO_MATERIA_POR_PRIMERA_VEZ;
	}

	private boolean esMateriaDadaDeBaja(List<InscripcionBajasDTO> materiasBajas, String clavePrograma) {
		return materiasBajas.stream().anyMatch(materia -> materia.getClavePrograma().equalsIgnoreCase(clavePrograma));
	}

	private boolean esMateriaReprobada(List<InscripcionMateriasReprobadasDTO> materiasReprobadas,
			String clavePrograma) {
		return materiasReprobadas.stream()
				.anyMatch(materia -> materia.getClavePrograma().equalsIgnoreCase(clavePrograma));
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

	public String generarAcronimoMayusculas(String texto) {
		StringBuilder acronimo = new StringBuilder();
		for (char c : texto.toCharArray()) {
			if (Character.isUpperCase(c)) {
				acronimo.append(c);
			}
		}
		return acronimo.toString();
	}

	public String generarAcronimo(String texto) {
		String[] palabras = texto.split(" ");
		StringBuilder acronimo = new StringBuilder();
		for (String palabra : palabras) {
			if (!palabra.isEmpty()) {
				acronimo.append(palabra.charAt(0));
			}
		}
		return acronimo.toString().toUpperCase();
	}

	public InscripcionPersonaDTO getInfoPersona() {
		return infoPersona;
	}

	public void setInfoPersona(InscripcionPersonaDTO infoPersona) {
		this.infoPersona = infoPersona;
	}

	public UsuarioSessionDTO getUsuarioEnSesion() {
		return usuarioEnSesion;
	}

	public void setUsuarioEnSesion(UsuarioSessionDTO usuarioEnSesion) {
		this.usuarioEnSesion = usuarioEnSesion;
	}

	public InscripcionService getInscripcionService() {
		return inscripcionService;
	}

	public void setInscripcionService(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public CorreoElectronicoService getCorreoElectronicoService() {
		return correoElectronicoService;
	}

	public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
		this.correoElectronicoService = correoElectronicoService;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(Boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}

	public String getTerminosCondiciones() {
		return terminosCondiciones;
	}

	public void setTerminosCondiciones(String terminosCondiciones) {
		this.terminosCondiciones = terminosCondiciones;
	}

	public Boolean getMostrarTerminosCondiciones() {
		return mostrarTerminosCondiciones;
	}

	public void setMostrarTerminosCondiciones(Boolean mostrarTerminosCondiciones) {
		this.mostrarTerminosCondiciones = mostrarTerminosCondiciones;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public Boolean getEsEstudianteRegular() {
		return esEstudianteRegular;
	}

	public void setEsEstudianteRegular(Boolean esEstudianteRegular) {
		this.esEstudianteRegular = esEstudianteRegular;
	}

	public Boolean getEsEstudianteNuevoIngreso() {
		return esEstudianteNuevoIngreso;
	}

	public void setEsEstudianteNuevoIngreso(Boolean esEstudianteNuevoIngreso) {
		this.esEstudianteNuevoIngreso = esEstudianteNuevoIngreso;
	}

	public LimitesCargaAcademicaDTO getMaxMinPlan() {
		return maxMinPlan;
	}

	public void setMaxMinPlan(LimitesCargaAcademicaDTO maxMinPlan) {
		this.maxMinPlan = maxMinPlan;
	}

	public Boolean getExisteInscripcionPrevia() {
		return existeInscripcionPrevia;
	}

	public void setExisteInscripcionPrevia(Boolean existeInscripcionPrevia) {
		this.existeInscripcionPrevia = existeInscripcionPrevia;
	}

	public List<InscripcionMateriasCursadasDTO> getMateriasCursadas() {
		return materiasCursadas;
	}

	public void setMateriasCursadas(List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		this.materiasCursadas = materiasCursadas;
	}

	public List<InscripcionMateriasDTO> getMateriasOfertadas() {
		return materiasOfertadas;
	}

	public void setMateriasOfertadas(List<InscripcionMateriasDTO> materiasOfertadas) {
		this.materiasOfertadas = materiasOfertadas;
	}

	public List<InscripcionMateriasDTO> getMateriasDisponibles() {
		return materiasDisponibles;
	}

	public void setMateriasDisponibles(List<InscripcionMateriasDTO> materiasDisponibles) {
		this.materiasDisponibles = materiasDisponibles;
	}

	public List<InscripcionMateriasReprobadasDTO> getMateriasReprobadas() {
		return materiasReprobadas;
	}

	public void setMateriasReprobadas(List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		this.materiasReprobadas = materiasReprobadas;
	}

	public List<InscripcionBajasDTO> getMateriasBajas() {
		return materiasBajas;
	}

	public void setMateriasBajas(List<InscripcionBajasDTO> materiasBajas) {
		this.materiasBajas = materiasBajas;
	}

	public CreditosTotalesPlanDTO getCreditosTotalesPlan() {
		return creditosTotalesPlan;
	}

	public void setCreditosTotalesPlan(CreditosTotalesPlanDTO creditosTotalesPlan) {
		this.creditosTotalesPlan = creditosTotalesPlan;
	}

	public Double getPorcentajeCreditosCompletados() {
		return porcentajeCreditosCompletados;
	}

	public void setPorcentajeCreditosCompletados(Double porcentajeCreditosCompletados) {
		this.porcentajeCreditosCompletados = porcentajeCreditosCompletados;
	}

	public Boolean getMostrarMensajeSeriacion() {
		return mostrarMensajeSeriacion;
	}

	public void setMostrarMensajeSeriacion(Boolean mostrarMensajeSeriacion) {
		this.mostrarMensajeSeriacion = mostrarMensajeSeriacion;
	}

	public Long getCantidadMaximaMateriasElectivas() {
		return cantidadMaximaMateriasElectivas;
	}

	public void setCantidadMaximaMateriasElectivas(Long cantidadMaximaMateriasElectivas) {
		this.cantidadMaximaMateriasElectivas = cantidadMaximaMateriasElectivas;
	}

}
