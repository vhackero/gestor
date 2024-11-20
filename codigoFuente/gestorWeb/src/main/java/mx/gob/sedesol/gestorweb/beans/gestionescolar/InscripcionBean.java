package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;

@ManagedBean
@ViewScoped
public class InscripcionBean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(InscripcionBean.class);

	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{correoElectronicoService}")
	private CorreoElectronicoService correoElectronicoService;

	private UsuarioSessionDTO usuarioEnSesion;

	private InscripcionDTO infoPersona;

	private List<InscripcionMateriasDTO> listadatosMaterias;

	private InscripcionMaxMinDTO maxMin;

	private List<InscripcionMateriasPasadasDTO> listadatosMateriasCursadas;

	private List<InscripcionMateriasInsDTO> listadatosMateriasInscritas;

	private Long numeroEstructura;

	private List<IntentosAsignaturasDTO> listaIntentosAsignaturas;

	private Boolean nuevoIngreso;

	private List<InscripcionBajasDTO> listaBajasAsignaturas;

	private Boolean verificaRegular;

	private String mensaje;

	private Boolean aceptaTerminos;

	private String terminosCondiciones;

	private Boolean mostrarTerminosCondiciones;

	List<InscripcionMateriasPasadasDTO> listaMateriasAprobadas = new ArrayList<InscripcionMateriasPasadasDTO>();
	List<InscripcionMateriasPasadasDTO> listaMateriasReprobadas = new ArrayList<InscripcionMateriasPasadasDTO>();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		verificaRegular = true;

		usuarioEnSesion = this.getUsuarioEnSession();
		infoPersona = inscripcionService.consultaInformacionPersona(usuarioEnSesion.getIdPersona().toString());

		String parametro = parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_PROGRAMAS_A_MOSTRAR_POR_CONVOCATORIA.getClave());

		String inscripciones_sin_restriccion = parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_INSCRIPCIONES_SIN_RESTRICCION.getClave());

		String verTerminosCondiciones = parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_MOSTRAR_TERMINOS_CONDICIONES.getClave());

		terminosCondiciones = parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_TERMINOS_CONDICIONES.getClave());

		List<InscripcionMateriasDTO> listaMateriasSeriadas = new ArrayList<InscripcionMateriasDTO>();



		if (verTerminosCondiciones != null && !verTerminosCondiciones.trim().isEmpty()
				&& !verTerminosCondiciones.trim().equals("0")) {

			mostrarTerminosCondiciones = true;

		} else {
			mostrarTerminosCondiciones = false;

		}

		if (mostrarTerminosCondiciones) {
			aceptaTerminos = false;
		} else {
			aceptaTerminos = true;

		}

		if (parametro != null && !parametro.trim().isEmpty() && !parametro.trim().equals("0")) {

			String[] parts = parametro.split("/");
			String convocatoria = parts[0];
			String estructuras = parts.length > 1 ? parts[1] : "";

			listadatosMaterias = inscripcionService.consultarMateriasPorConvocatoria(infoPersona.getIdPlan().toString(),
					convocatoria, estructuras);
//			listadatosMaterias = inscripcionService.consultarMaterias("5");

		} else {
			listadatosMaterias = inscripcionService.consultarMaterias(infoPersona.getIdPlan().toString());
		}

		listaIntentosAsignaturas = inscripcionService
				.consultarIntentosAsignaturas(usuarioEnSesion.getIdPersona().toString());
		nuevoIngreso = inscripcionService.consultarNuevoIngreso(usuarioEnSesion.getIdPersona().toString());

		maxMin = inscripcionService.consultarMaxMin(infoPersona.getIdPlan().toString());
		listadatosMateriasCursadas = inscripcionService
				.consultarMateriasCursadas(usuarioEnSesion.getIdPersona().toString());
		listadatosMateriasInscritas = inscripcionService
				.consultarMateriasInscritas(infoPersona.getIdPersona().toString(), infoPersona.getIdPlan().toString());

		listaBajasAsignaturas = inscripcionService.consultarBajas(usuarioEnSesion.getIdPersona().toString());

		List<Long> idsBajasAsignaturas = listaBajasAsignaturas.stream().map(InscripcionBajasDTO::getIdPrograma)
				.collect(Collectors.toList());

		listadatosMaterias.removeIf(materia -> idsBajasAsignaturas.contains(materia.getIdPrograma()));

		List<Long> idsInscritas = listadatosMateriasInscritas.stream()
				.map(inscripcion -> Long.valueOf(inscripcion.getIdPrograma())).collect(Collectors.toList());

		listadatosMaterias.removeIf(materia -> idsInscritas.contains(materia.getIdPrograma()));

		listaMateriasAprobadas = listadatosMateriasCursadas.stream()
				.filter(materia -> materia.getEstatusAprobacion() == 1).collect(Collectors.toList());

		List<Long> idsProgramasAprobados = listaMateriasAprobadas.stream()
				.map(InscripcionMateriasPasadasDTO::getIdPrograma).collect(Collectors.toList());

		listadatosMaterias.removeIf(materia -> idsProgramasAprobados.contains(materia.getIdPrograma()));

		listaMateriasReprobadas = listadatosMateriasCursadas.stream()
				.filter(materia -> materia.getEstatusAprobacion() == 0).collect(Collectors.toList());

		List<Long> idsProgramasReprobadas = listaMateriasReprobadas.stream()
				.map(InscripcionMateriasPasadasDTO::getIdPrograma).collect(Collectors.toList());

		if (listaMateriasReprobadas.size() >= 1) {
			verificaRegular = false;
		}

		if (nuevoIngreso) {
			listadatosMaterias = listadatosMaterias.stream()
					.filter(materia -> "Semestre 1".equals(materia.getEstructura())).collect(Collectors.toList());
		}

		if (inscripciones_sin_restriccion.equals("1")) {

			// Filtramos las materias seriadas
			listaMateriasSeriadas = listadatosMaterias.stream()
					.filter(materia -> materia.getIdProgramaAntecedente() != null).collect(Collectors.toList());

			// Creamos un mapa para asociar cada materia con su antecedente
			Map<Long, Long> antecedenteProgramaMap = listaMateriasSeriadas.stream().collect(Collectors
					.toMap(InscripcionMateriasDTO::getIdPrograma, InscripcionMateriasDTO::getIdProgramaAntecedente));

			// Set para almacenar los programas que se deben eliminar
			Set<Long> programasAEliminar = new HashSet<>();

			// Verificamos cada materia seriada
			for (InscripcionMateriasDTO materia : listaMateriasSeriadas) {
				Long programaActual = materia.getIdPrograma();
				Long antecedente = materia.getIdProgramaAntecedente();

				// Si el antecedente no está en aprobados, se marca para eliminar
				if (antecedente != null && !idsProgramasAprobados.contains(antecedente)) {
					programasAEliminar.add(programaActual);

					// Eliminamos todos los consecuentes del programa actual
					while ((programaActual = antecedenteProgramaMap.get(programaActual)) != null) {
						programasAEliminar.add(programaActual);
					}
				}
			}

			// Removemos las materias que tienen antecedente no aprobado
			listadatosMaterias.removeIf(materia -> programasAEliminar.contains(materia.getIdPrograma()));
		}

		if (listaMateriasReprobadas.size() > 4) {
			listadatosMaterias = listadatosMaterias.stream()
					.filter(materia -> idsProgramasReprobadas.contains(materia.getIdPrograma()))
					.collect(Collectors.toList());
		}
		numeroEstructura = inscripcionService.consultarNumeroEstructura(infoPersona.getIdPlan().toString());

	}

	public void cancelar() {
		logger.info("cancelar");

	}

	public void verificarAsignaturas() {

	}

	public void finalizarInscripcion() {
		logger.info("finalizar");

		logger.info("aceptaTerminos");
		logger.info(aceptaTerminos);

		if (aceptaTerminos == false) {
			mensaje = "Acepta terminos y condiciones";
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
			return;
		}

		List<InscripcionMateriasDTO> materiasSeleccionadas = listadatosMaterias.stream()
				.filter(inscripcion -> Boolean.TRUE.equals(inscripcion.getCheck())).collect(Collectors.toList());

		List<InscripcionMateriasDTO> materiasSeleccionadasYObligatorias = listadatosMaterias.stream()
				.filter(inscripcion -> Boolean.TRUE.equals(inscripcion.getCheck())
						|| "Obligatoria".equals(inscripcion.getTipoPrograma()))
				.collect(Collectors.toList());

		int minimo = Integer.parseInt(maxMin.getMinimo());
		int maximo = verificaRegular ? Integer.parseInt(maxMin.getMaximoRegular())
				: Integer.parseInt(maxMin.getMaximoIrregular());

		if (materiasSeleccionadas.size() < minimo) {
			logger.info("Selecciona minimo " + minimo + " programas");
			mensaje = "Selecciona minimo " + minimo + " programas";
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
			return;
		}

		if (materiasSeleccionadas.size() > maximo) {
			logger.info("Solo puedes seleccionar " + maximo + " programas");
			mensaje = "Solo puedes seleccionar " + maximo + " programas";
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
			return;

		}

		List<Long> programasConMaximosIntentos = listaIntentosAsignaturas.stream()
				.filter(intentos -> intentos.getIntetosReprobados() != null && intentos.getIntetosReprobados() == 3)
				.map(IntentosAsignaturasDTO::getIdPrograma).collect(Collectors.toList());

		List<InscripcionMateriasDTO> materiasRepobadas3Intentos = materiasSeleccionadasYObligatorias.stream()
				.filter(materia -> programasConMaximosIntentos.contains(materia.getIdPrograma()))
				.collect(Collectors.toList());

		if (!materiasRepobadas3Intentos.isEmpty()) {
			String materias = materiasRepobadas3Intentos.stream()
					.map(InscripcionMateriasDTO::getNombreTentativoPrograma).collect(Collectors.joining(", "));

			mensaje = "Has superado los intentos permitidos para recursar la(s) asignatura(s): " + materias + ".";
			logger.info(mensaje);
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");

			return;
		}

		if (materiasSeleccionadasYObligatorias.isEmpty()) {
			logger.info("No se seleccionaron materias.");
		} else {

			List<InscripcionInsertDTO> inscripciones = materiasSeleccionadasYObligatorias.stream()
			        .filter(materia -> materia.getCheck() || "Obligatoria".equals(materia.getTipoPrograma())) // Filtrar por `check` o `tipoPrograma`
			        .map(this::convertirAModeloInscripcion)
			        .collect(Collectors.toList());

			// Llama al servicio para insertar las inscripciones
			inscripcionService.insertarInscripciones(inscripciones);

			enviarCorreoInscripcion(infoPersona, materiasSeleccionadasYObligatorias);
			mensaje = "Inscripcion de materias completa";
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
			logger.info("Materias seleccionadas guardadas: " + materiasSeleccionadasYObligatorias.size());
		}
	}

	public void enviarCorreoInscripcion(InscripcionDTO inscripcion,
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
						+ "<p>A continuación, te mostramos tus asignaturas para el período {1}:</p>" + "{2}", // Lista de asignaturas
				nombreCompleto.toString(), periodo, listaAsignaturas.toString()));

		correoElectronicoService.enviaCorreoElectronico(correoDto);
	}

	private InscripcionInsertDTO convertirAModeloInscripcion(InscripcionMateriasDTO materia) {
		InscripcionInsertDTO dto = new InscripcionInsertDTO();

		dto.setIdPersona(infoPersona.getIdPersona());
		dto.setPrograma(materia.getNombrePlan());
		dto.setAsignatura(materia.getNombreTentativoPrograma());
		dto.setGroupBase(
				generateAcronymMayus(materia.getNombrePlan())+"-" + generateAcronymMayus(materia.getClavePrograma())
						+ generateAcronym(materia.getSubestructura()) + generateAcronym(materia.getEstructura()) + "-" 
						+ generatePeriodo(materia.getEstructura()) +"-"+ generateAcronym(materia.getSubestructura()) +"000" );

		dto.setIdPlan(materia.getIdPlan());
		dto.setIdPrograma(materia.getIdPrograma());
		dto.setIdEvento(100l);  // obtener
		dto.setNivel(materia.getNivelEnsenanza()); 
		dto.setDivision(generateAcronym(materia.getDivision()));
		dto.setProfileFieldPerfil("ES"); // obtener
		dto.setBloque(Integer.parseInt(materia.getSubestructura().replaceAll("[^0-9]", ""))); 
		dto.setClaveAsig(materia.getClavePrograma());
		dto.setNuevoIngreso(nuevoIngreso ? 1 : 0);
		dto.setRecursamiento(verificarRecursamiento(listaMateriasReprobadas, materia.getIdPrograma()));
		dto.setAlta(0);
		dto.setSemestre(1); // obtener
		dto.setFechaRegistro(new Date());

		return dto;
	}
	public int verificarRecursamiento(List<InscripcionMateriasPasadasDTO> listaMateriasReprobadas, Long idPrograma) {
	    return listaMateriasReprobadas.stream()
	            .anyMatch(materia -> materia.getIdPrograma() == idPrograma) ? 1 : 0;
	}
	
	 public String generatePeriodo(String estructura) {
	        // Obtener el año actual
	        int year = Calendar.getInstance().get(Calendar.YEAR);

	        // Obtener los últimos dos dígitos del año
	        String yearLastTwoDigits = String.valueOf(year).substring(2);

	        // Extraer el número de semestre de la cadena, asumiendo el formato "Semestre X" o "Semestre XX"
	        String semestreNumberStr = estructura.split(" ")[1];
	        int semester = Integer.parseInt(semestreNumberStr);

	        // Verificar si el semestre es de dos dígitos; si es así, no agregar el "0"
	        if (semester >= 10) {
	            return yearLastTwoDigits + semester;
	        } else {
	            // Para semestres de un solo dígito, agregar el "0" antes del número
	            return yearLastTwoDigits + "0" + semester;
	        }
	    }

	public String generateAcronymMayus(String phrase) {
		// StringBuilder para construir el acrónimo
		StringBuilder acronym = new StringBuilder();

		// Itera sobre cada carácter de la frase
		for (char c : phrase.toCharArray()) {
			// Agrega solo los caracteres que ya están en mayúsculas
			if (Character.isUpperCase(c)) {
				acronym.append(c);
			}
		}

		// Retorna el acrónimo tal como está (en mayúsculas de la frase original)
		return acronym.toString();
	}

	public String generateAcronym(String phrase) {
		// Divide la frase en palabras usando el espacio como delimitador
		String[] words = phrase.split(" ");

		// StringBuilder para construir el acrónimo
		StringBuilder acronym = new StringBuilder();

		// Itera sobre cada palabra y toma la primera letra
		for (String word : words) {
			// Solo agregamos la primera letra si la palabra no está vacía
			if (!word.isEmpty()) {
				acronym.append(word.charAt(0));
			}
		}

		// Retornamos el acrónimo en mayúsculas
		return acronym.toString().toUpperCase();
	}

	public InscripcionDTO getInfoPersona() {
		return infoPersona;
	}

	public void setInfoPersona(InscripcionDTO infoPersona) {
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

	public List<InscripcionMateriasDTO> getListadatosMaterias() {
		return listadatosMaterias;
	}

	public void setListadatosMaterias(List<InscripcionMateriasDTO> listadatosMaterias) {
		this.listadatosMaterias = listadatosMaterias;
	}

	public InscripcionMaxMinDTO getMaxMin() {
		return maxMin;
	}

	public void setMaxMin(InscripcionMaxMinDTO maxMin) {
		this.maxMin = maxMin;
	}

	public List<InscripcionMateriasPasadasDTO> getListadatosMateriasCursadas() {
		return listadatosMateriasCursadas;
	}

	public void setListadatosMateriasCursadas(List<InscripcionMateriasPasadasDTO> listadatosMateriasCursadas) {
		this.listadatosMateriasCursadas = listadatosMateriasCursadas;
	}

	public List<InscripcionMateriasInsDTO> getListadatosMateriasInscritas() {
		return listadatosMateriasInscritas;
	}

	public void setListadatosMateriasInscritas(List<InscripcionMateriasInsDTO> listadatosMateriasInscritas) {
		this.listadatosMateriasInscritas = listadatosMateriasInscritas;
	}

	public Long getNumeroEstructura() {
		return numeroEstructura;
	}

	public void setNumeroEstructura(Long numeroEstructura) {
		this.numeroEstructura = numeroEstructura;
	}

	public List<IntentosAsignaturasDTO> getListaIntentosAsignaturas() {
		return listaIntentosAsignaturas;
	}

	public void setListaIntentosAsignaturas(List<IntentosAsignaturasDTO> listaIntentosAsignaturas) {
		this.listaIntentosAsignaturas = listaIntentosAsignaturas;
	}

	public Boolean getNuevoIngreso() {
		return nuevoIngreso;
	}

	public void setNuevoIngreso(Boolean nuevoIngreso) {
		this.nuevoIngreso = nuevoIngreso;
	}

	public List<InscripcionBajasDTO> getListaBajasAsignaturas() {
		return listaBajasAsignaturas;
	}

	public void setListaBajasAsignaturas(List<InscripcionBajasDTO> listaBajasAsignaturas) {
		this.listaBajasAsignaturas = listaBajasAsignaturas;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public Boolean getVerificaRegular() {
		return verificaRegular;
	}

	public void setVerificaRegular(Boolean verificaRegular) {
		this.verificaRegular = verificaRegular;
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
	
	public List<InscripcionMateriasPasadasDTO> getListaMateriasAprobadas() {
		return listaMateriasAprobadas;
	}

	public void setListaMateriasAprobadas(List<InscripcionMateriasPasadasDTO> listaMateriasAprobadas) {
		this.listaMateriasAprobadas = listaMateriasAprobadas;
	}

	public List<InscripcionMateriasPasadasDTO> getListaMateriasReprobadas() {
		return listaMateriasReprobadas;
	}

	public void setListaMateriasReprobadas(List<InscripcionMateriasPasadasDTO> listaMateriasReprobadas) {
		this.listaMateriasReprobadas = listaMateriasReprobadas;
	}
}
