package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.FuenteExternaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.basegestor.service.admin.PersonaSigeService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class NuevaAltaUsuariosBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final List<String> COLUMNAS_REQUERIDAS = Arrays.asList("matricula_sige", "password_sige",
            "nombre_sige", "apellidop_sige", "apellidom_sige", "programa_sige", "division_sige",
            "correo_institucional_sige", "fecha_nacimiento_sige", "curp_sige", "nivel_sige", "persona_id_sige",
            "perfil_id_sige");

    @ManagedProperty("#{personaServiceFacade}")
    private transient PersonaServiceFacade personaServiceFacade;

    @ManagedProperty("#{personaSigeService}")
    private transient PersonaSigeService personaSigeService;

    @ManagedProperty("#{eventoCapacitacionServiceFacade}")
    private transient EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

    private static final Logger LOGGER = Logger.getLogger(NuevaAltaUsuariosBean.class);

    private List<SelectImportarDTO> listaFuentesExternas;
    private List<SelectImportarDTO> listaPlanes;
    private List<SelectImportarDTO> listaSemestres;
    private List<SelectImportarDTO> listaBloques;
    private List<SelectImportarDTO> listaProgramas;
    private List<SelectImportarDTO> listaPeriodos;
    private List<SelectImportarDTO> listaEventos;
    private List<SelectImportarDTO> listaGrupos;

    private String matriculaImportar;
    private String matriculaNuevaAlta;
    private String fuenteExternaSeleccionada;
    private String planSeleccionado;
    private String semestreSeleccionado;
    private String bloqueSeleccionado;
    private String programaSeleccionado;
    private String periodoSeleccionado;
    private String eventoSeleccionado;
    private String grupoSeleccionado;

    private String mensajeValidacionDialogo;
    private String mensajeErrorDialogo;
    private String mensajeExitoDialogo;

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando formulario de nueva alta de usuarios");
        listaFuentesExternas = personaServiceFacade.consultaFuenteExterna();
        listaPlanes = personaServiceFacade.consultaPlanesActivos();
        listaPeriodos = personaServiceFacade.consultaPeriodosInscripcion();

        listaSemestres = new ArrayList<>();
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
    }

    public void importarDesdeFuenteExterna() {
        String traceId = "IMP-" + System.currentTimeMillis();
        try {
            LOGGER.info(String.format("[%s] Iniciando importación. Matricula: %s, Fuente seleccionada: %s", traceId,
                    matriculaImportar != null ? matriculaImportar.trim() : null, fuenteExternaSeleccionada));
            if (esVacio(matriculaImportar) || esVacio(fuenteExternaSeleccionada)) {
                agregarMsgWarn("Capture matrícula y seleccione fuente externa.", null);
                LOGGER.warn(String.format("[%s] Faltan datos de entrada para importar.", traceId));
                return;
            }

            Integer idFuente = parseEntero(fuenteExternaSeleccionada);
            if (ObjectUtils.isNull(idFuente)) {
                agregarMsgWarn("Fuente externa inválida.", null);
                LOGGER.warn(String.format("[%s] No se pudo parsear la fuente externa seleccionada: %s", traceId,
                        fuenteExternaSeleccionada));
                return;
            }
            LOGGER.info(String.format("[%s] Fuente externa parseada. idFuente=%s", traceId, idFuente));

            FuenteExternaDTO fuente = personaServiceFacade.buscarFuenteExternaPorId(idFuente);
            if (ObjectUtils.isNull(fuente)) {
                agregarMsgWarn("No se encontró la configuración de la fuente externa.", null);
                LOGGER.warn(String.format("[%s] Fuente externa no encontrada. idFuente=%s", traceId, idFuente));
                return;
            }
            if (esVacio(fuente.getConsulta())) {
                agregarMsgWarn("La fuente externa no tiene consulta configurada.", null);
                LOGGER.warn(String.format("[%s] Consulta vacía para fuente id=%s", traceId, idFuente));
                return;
            }
            LOGGER.info(String.format("[%s] Fuente obtenida. id=%s, nombre=%s, servidor=%s, usuario=%s, alias=%s, base=%s, consultaLen=%d, consultaPreview=%s",
                    traceId, fuente.getId(), fuente.getNombre(), enmascararServidor(fuente.getServidor()),
                    esVacio(fuente.getUsuario()) ? "N/A" : fuente.getUsuario(),
                    esVacio(fuente.getAlias()) ? "N/A" : fuente.getAlias(),
                    esVacio(fuente.getNombreBaseDatos()) ? "N/A" : fuente.getNombreBaseDatos(),
                    fuente.getConsulta() != null ? fuente.getConsulta().length() : 0,
                    truncar(fuente.getConsulta(), 200)));

            String consultaNormalizada = normalizarConsulta(fuente.getConsulta());
            if (consultaNormalizada == null) {
                int numInterrogaciones = contarOcurrencias(fuente.getConsulta(), "?");
                int numMatricula = contarOcurrenciasIgnoreCase(fuente.getConsulta(), ":matricula");
                int numUsuario = contarOcurrenciasIgnoreCase(fuente.getConsulta(), ":usuario");
                LOGGER.warn(String.format(
                        "[%s] Consulta inválida. Parámetros encontrados -> ?: %d, :matricula: %d, :usuario: %d",
                        traceId, numInterrogaciones, numMatricula, numUsuario));
                agregarMsgWarn("Configurar correctamente los datos de la fuente externa", null);
                return;
            }
            LOGGER.info(String.format("[%s] Consulta normalizada lista. Preview=%s", traceId,
                    truncar(consultaNormalizada, 200)));

            try (Connection conexion = crearConexion(fuente)) {
                if (conexion == null) {
                    agregarMsgError("No se pudo construir la conexión con la fuente externa.", null);
                    LOGGER.warn(String.format("[%s] No se pudo construir la conexión (servidor vacío).", traceId));
                    return;
                }
                String urlConexion = construirUrlConexion(fuente);
                LOGGER.info(String.format("[%s] Conexión creada. url=%s, userPresent=%s, passwordPresent=%s, passwordLength=%d",
                        traceId, enmascararServidor(urlConexion), !esVacio(fuente.getUsuario()),
                        fuente.getContrasena() != null, fuente.getContrasena() != null ? fuente.getContrasena().length() : 0));
                try (PreparedStatement ps = conexion.prepareStatement(consultaNormalizada)) {
                    ps.setString(1, matriculaImportar.trim());
                    LOGGER.info(String.format("[%s] Ejecutando consulta. SQL=%s, parametro1=%s", traceId,
                            truncar(consultaNormalizada, 200), matriculaImportar.trim()));
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            agregarMsgWarn("No se encontró información para la matrícula especificada.", null);
                            LOGGER.warn(String.format("[%s] ResultSet vacío para matrícula %s", traceId,
                                    matriculaImportar.trim()));
                            return;
                        }
                        List<String> columnasFaltantes = validarColumnasRequeridas(rs);
                        if (!columnasFaltantes.isEmpty()) {
                            String columnasActuales = obtenerColumnas(rs);
                            LOGGER.warn(String.format(
                                    "[%s] ResultSet sin columnas requeridas. Faltantes=%s, Columnas=%s", traceId,
                                    columnasFaltantes, columnasActuales));
                            agregarMsgWarn("La consulta de la fuente externa no incluye las columnas requeridas: "
                                    + String.join(", ", columnasFaltantes), null);
                            return;
                        }
                        LOGGER.info(String.format("[%s] ResultSet con datos. Columnas=%s", traceId,
                                obtenerColumnas(rs)));
                        PersonaSigeDTO personaSige = mapearPersonaSige(rs);
                        LOGGER.info(String.format(
                                "[%s] Datos mapeo previo a validación de matrícula. Columnas=%s, passwordPresent=%s, matriculaFinal=%s",
                                traceId, obtenerColumnas(rs), !esVacio(personaSige.getPassword()),
                                ObjectUtils.isNull(personaSige) ? null : personaSige.getMatricula()));
                        if (ObjectUtils.isNull(personaSige) || esVacio(personaSige.getMatricula())) {
                            agregarMsgWarn(
                                    "La fuente externa no devolvió matrícula (se esperaba matricula_sige). Revise la consulta/mapeo.",
                                    null);
                            LOGGER.warn(String.format("[%s] PersonaSige mapeada sin matrícula.", traceId));
                            return;
                        }
                        List<String> camposFaltantes = validarCamposObligatorios(personaSige);
                        if (!camposFaltantes.isEmpty()) {
                            LOGGER.warn(String.format("[%s] PersonaSige con campos obligatorios vacíos. Faltantes=%s",
                                    traceId, camposFaltantes));
                            agregarMsgWarn(
                                    "La fuente externa devolvió campos obligatorios vacíos: "
                                            + String.join(", ", camposFaltantes),
                                    null);
                            return;
                        }
                        LOGGER.info(String.format(
                                "[%s] Persona mapeada. Matricula=%s, Nombre=%s, Apellidos=%s %s, Correo=%s", traceId,
                                personaSige.getMatricula(), personaSige.getNombre(),
                                personaSige.getApellidoPaterno(), personaSige.getApellidoMaterno(),
                                personaSige.getCorreoInstitucional()));
                        guardarPersonaSige(personaSige, traceId);
                        matriculaNuevaAlta = personaSige.getMatricula();
                        agregarMsgInfo("Información importada correctamente", null);
                        LOGGER.info(String.format("[%s] Importación exitosa para matrícula %s", traceId,
                                matriculaNuevaAlta));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al importar datos desde fuente externa", e);
            String mensaje = ObjectUtils.isNullOrEmpty(e.getMessage()) ? "Error al importar datos desde fuente externa"
                    : e.getMessage();
            agregarMsgError(mensaje, null);
        }
    }

    public void onPlanChange() {
        semestreSeleccionado = null;
        bloqueSeleccionado = null;
        programaSeleccionado = null;
        periodoSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaSemestres = new ArrayList<>();
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();

        Integer idPlan = parseEntero(planSeleccionado);
        if (ObjectUtils.isNotNull(idPlan)) {
            listaSemestres = personaServiceFacade.consultaSemestresPorPlan(idPlan);
        } else {
            listaSemestres = new ArrayList<>();
        }
    }

    public void onSemestreChange() {
        bloqueSeleccionado = null;
        programaSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();

        Integer idSemestre = parseEntero(semestreSeleccionado);
        if (ObjectUtils.isNotNull(idSemestre)) {
            listaBloques = personaServiceFacade.consultaBloquesPorSemestre(idSemestre);
        }
    }

    public void onBloqueChange() {
        programaSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        cargarProgramas();
    }

    public void onProgramaChange() {
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        cargarEventos();
    }

    public void onPeriodoChange() {
        eventoSeleccionado = null;
        grupoSeleccionado = null;
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        cargarEventos();
    }

    public void onEventoChange() {
        grupoSeleccionado = null;
        listaGrupos = new ArrayList<>();
        Integer idEvento = parseEntero(eventoSeleccionado);
        if (ObjectUtils.isNotNull(idEvento)) {
            listaGrupos = personaServiceFacade.consultaGruposPorEvento(idEvento);
        }
    }

    public void guardarAlta() {
        if (faltaAlgunCampoObligatorio()) {
            LOGGER.warn("Validación fallida: campos obligatorios incompletos");
            mostrarDialogo("dlgNuevaAltaValidacion");
            return;
        }

        LOGGER.info(String.format("Iniciando alta de usuario con matrícula %s", matriculaNuevaAlta));
        Optional<Long> idPersona = personaServiceFacade.getPersonaService()
                .obtenerIdPersonaPorMatricula(matriculaNuevaAlta.trim());

        if (!idPersona.isPresent()) {
            LOGGER.warn("No se encontró la matrícula ingresada");
            mostrarDialogoError("No se encontró la matrícula ingresada.");
            return;
        }

        PersonaDTO persona = personaServiceFacade.obtenerPersonaPorId(idPersona.get());
        if (ObjectUtils.isNull(persona)) {
            LOGGER.warn("No se encontró la información del usuario");
            mostrarDialogoError("No se encontró información asociada para registrar, por favor intenta, importar datos para continuar.");
            return;
        }

        Integer idEvento = parseEntero(eventoSeleccionado);
        Integer idGrupo = parseEntero(grupoSeleccionado);
        if (ObjectUtils.isNull(idEvento) || ObjectUtils.isNull(idGrupo)) {
            LOGGER.warn("Evento o grupo no seleccionados correctamente");
            mostrarDialogoError("No se pudo recuperar el evento o grupo seleccionado.");
            return;
        }

        EventoCapacitacionDTO evento = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
                .getEvento(idEvento);
        GrupoDTO grupo = eventoCapacitacionServiceFacade.getGrupoService().buscarGrupoPorId(idGrupo);

        if (ObjectUtils.isNull(evento) || ObjectUtils.isNull(grupo)) {
            LOGGER.warn("No se pudo recuperar la información de evento o grupo");
            mostrarDialogoError("No se pudo recuperar la información seleccionada.");
            return;
        }

        if (yaEstaMatriculado(idEvento, idPersona.get())) {
            LOGGER.info("El usuario ya está matriculado en el evento seleccionado");
            mostrarDialogoError("El usuario ya está matriculado en el evento seleccionado.");
            return;
        }

        grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        grupo.setFachaActualizacion(new Date());

        ParametroWSMoodleDTO parametroWSMoodleDTO = obtenerParametrosMoodle(evento);
        if (requiereMoodle(evento) && ObjectUtils.isNull(parametroWSMoodleDTO)) {
            LOGGER.error("No se encontró la plataforma para matricular en el LMS");
            mostrarDialogoError("No se encontró la plataforma para matricular en el LMS.");
            return;
        }

        LOGGER.info(String.format("Matriculando usuario %s en evento %s y grupo %s", idPersona.get(), idEvento,
                idGrupo));
        RelGrupoParticipanteDTO participante = eventoCapacitacionServiceFacade.almacenarParticipante(grupo, persona,
                evento, parametroWSMoodleDTO);

        if (ObjectUtils.isNull(participante)) {
            LOGGER.error("No fue posible matricular al usuario");
            mostrarDialogoError("No fue posible matricular al usuario.");
            return;
        }

        LOGGER.info("Alta registrada correctamente");
        mostrarDialogoExito("Alta aplicada correctamente");
        limpiarFormulario();
    }

    private ParametroWSMoodleDTO obtenerParametrosMoodle(EventoCapacitacionDTO evento) {
        if (ObjectUtils.isNull(evento) || ObjectUtils.isNull(evento.getCatModalidadPlanPrograma())) {
            return null;
        }
        if (requiereMoodle(evento) && ObjectUtils.isNotNull(evento.getIdPlataformaLmsBorrador())) {
            return eventoCapacitacionServiceFacade.getParametroWSMoodleService()
                    .buscarPorId(evento.getIdPlataformaLmsBorrador());
        }
        return null;
    }

    private boolean requiereMoodle(EventoCapacitacionDTO evento) {
        if (ObjectUtils.isNull(evento) || ObjectUtils.isNull(evento.getCatModalidadPlanPrograma())) {
            return false;
        }
        Integer idModalidad = evento.getCatModalidadPlanPrograma().getId();
        return ConstantesGestor.MODALIDAD_LINEA.equals(idModalidad)
                || ConstantesGestor.MODALIDAD_MIXTO.equals(idModalidad);
    }

    private boolean yaEstaMatriculado(Integer idEvento, Long idPersona) {
        List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
                .buscarParticipanteEnEvento(idEvento, idPersona);
        return !ObjectUtils.isNullOrEmpty(participantes);
    }

    private boolean faltaAlgunCampoObligatorio() {
        if (esVacio(matriculaNuevaAlta)) {
            mensajeValidacionDialogo = "La matrícula es obligatoria.";
            return true;
        }
        if (esVacio(planSeleccionado) || esVacio(semestreSeleccionado) || esVacio(bloqueSeleccionado)
                || esVacio(programaSeleccionado) || esVacio(periodoSeleccionado) || esVacio(eventoSeleccionado)
                || esVacio(grupoSeleccionado)) {
            mensajeValidacionDialogo = "Ingrese los datos marcados como obligatorios.";
            return true;
        }
        return false;
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private void limpiarFormulario() {
        matriculaNuevaAlta = null;
        planSeleccionado = null;
        semestreSeleccionado = null;
        bloqueSeleccionado = null;
        programaSeleccionado = null;
        periodoSeleccionado = null;
        eventoSeleccionado = null;
        grupoSeleccionado = null;

        listaSemestres = new ArrayList<>();
        listaBloques = new ArrayList<>();
        listaProgramas = new ArrayList<>();
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();

        mensajeValidacionDialogo = null;
        mensajeErrorDialogo = null;
    }

    private void cargarProgramas() {
        programaSeleccionado = null;
        listaProgramas = new ArrayList<>();

        Integer idEje = parseEntero(bloqueSeleccionado);
        if (ObjectUtils.isNotNull(idEje)) {
            listaProgramas = personaServiceFacade.consultaProgramasPorEje(idEje);
        }
    }

    private void cargarEventos() {
        listaEventos = new ArrayList<>();
        listaGrupos = new ArrayList<>();

        Integer idPrograma = parseEntero(programaSeleccionado);
        if (ObjectUtils.isNotNull(idPrograma) && tienePeriodoSeleccionado()) {
            listaEventos = personaServiceFacade.consultaEventosPorPeriodoYPrograma(periodoSeleccionado, idPrograma);
        }
    }

    private boolean tienePeriodoSeleccionado() {
        return periodoSeleccionado != null && !periodoSeleccionado.trim().isEmpty();
    }

    private Integer parseEntero(String valor) {
        try {
            return valor != null && !valor.trim().isEmpty() ? Integer.parseInt(valor) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int contarOcurrencias(String cadena, String subcadena) {
        if (esVacio(cadena) || esVacio(subcadena)) {
            return 0;
        }
        int contador = 0;
        int indice = cadena.indexOf(subcadena);
        while (indice != -1) {
            contador++;
            indice = cadena.indexOf(subcadena, indice + subcadena.length());
        }
        return contador;
    }

    private int contarOcurrenciasIgnoreCase(String cadena, String subcadena) {
        if (cadena == null || subcadena == null) {
            return 0;
        }
        return contarOcurrencias(cadena.toLowerCase(), subcadena.toLowerCase());
    }

    private String normalizarConsulta(String consulta) {
        if (esVacio(consulta)) {
            return null;
        }
        int numInterrogaciones = contarOcurrencias(consulta, "?");
        int numMatricula = contarOcurrenciasIgnoreCase(consulta, ":matricula");
        int numUsuario = contarOcurrenciasIgnoreCase(consulta, ":usuario");
        int totalParametros = numInterrogaciones + numMatricula + numUsuario;
        if (totalParametros != 1) {
            return null;
        }
        return consulta.replaceAll("(?i):matricula", "?").replaceAll("(?i):usuario", "?");
    }

    private Connection crearConexion(FuenteExternaDTO fuente) throws SQLException {
        String url = construirUrlConexion(fuente);
        if (url == null) {
            return null;
        }
        Properties propiedades = new Properties();
        if (!esVacio(fuente.getUsuario())) {
            propiedades.put("user", fuente.getUsuario());
        }
        String password = fuente.getContrasena();
        if (!esVacio(password)) {
            propiedades.put("password", password);
        }
        return DriverManager.getConnection(url, propiedades);
    }

    private PersonaSigeDTO mapearPersonaSige(ResultSet rs) throws SQLException {
        PersonaSigeDTO persona = new PersonaSigeDTO();
        persona.setMatricula(obtenerString(rs, "matricula_sige"));
        persona.setPassword(obtenerString(rs, "password_sige"));
        persona.setNombre(obtenerString(rs, "nombre_sige"));
        persona.setApellidoPaterno(obtenerString(rs, "apellidop_sige"));
        persona.setApellidoMaterno(obtenerString(rs, "apellidom_sige"));
        persona.setProgramaEducativo(obtenerString(rs, "programa_sige"));
        persona.setDivision(obtenerString(rs, "division_sige"));
        persona.setCorreoInstitucional(obtenerString(rs, "correo_institucional_sige"));
        persona.setFechaNacimiento(obtenerFecha(rs, "fecha_nacimiento_sige"));
        persona.setCurp(obtenerString(rs, "curp_sige"));
        persona.setNivelSige(obtenerString(rs, "nivel_sige"));
        Integer personaId = obtenerEntero(rs, "persona_id_sige");
        Integer perfilId = obtenerEntero(rs, "perfil_id_sige");
        if (personaId != null) {
            persona.setPersonaIdSige(personaId);
        }
        if (perfilId != null) {
            persona.setPerfilIdSige(perfilId);
        }
        return persona;
    }

    private String obtenerString(ResultSet rs, String... columnas) throws SQLException {
        for (String columna : columnas) {
            if (tieneColumna(rs, columna)) {
                return rs.getString(columna);
            }
        }
        return null;
    }

    private Date obtenerFecha(ResultSet rs, String... columnas) throws SQLException {
        for (String columna : columnas) {
            if (tieneColumna(rs, columna)) {
                java.sql.Date fecha = rs.getDate(columna);
                return fecha != null ? new Date(fecha.getTime()) : null;
            }
        }
        return null;
    }

    private Integer obtenerEntero(ResultSet rs, String... columnas) throws SQLException {
        for (String columna : columnas) {
            if (tieneColumna(rs, columna)) {
                Object valor = rs.getObject(columna);
                if (valor instanceof Number) {
                    return ((Number) valor).intValue();
                }
            }
        }
        return null;
    }

    private boolean tieneColumna(ResultSet rs, String columna) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnas = metaData.getColumnCount();
        for (int i = 1; i <= columnas; i++) {
            if (columna.equalsIgnoreCase(metaData.getColumnLabel(i))) {
                return true;
            }
        }
        return false;
    }

    private String obtenerColumnas(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnas = metaData.getColumnCount();
        List<String> nombres = new ArrayList<>();
        for (int i = 1; i <= columnas; i++) {
            nombres.add(metaData.getColumnLabel(i));
        }
        return nombres.toString();
    }

    private List<String> validarColumnasRequeridas(ResultSet rs) throws SQLException {
        List<String> faltantes = new ArrayList<>();
        for (String columna : COLUMNAS_REQUERIDAS) {
            if (!tieneColumna(rs, columna)) {
                faltantes.add(columna);
            }
        }
        return faltantes;
    }

    private List<String> validarCamposObligatorios(PersonaSigeDTO personaSige) {
        List<String> faltantes = new ArrayList<>();
        if (ObjectUtils.isNull(personaSige)) {
            faltantes.addAll(COLUMNAS_REQUERIDAS);
            return faltantes;
        }
        if (esVacio(personaSige.getMatricula())) {
            faltantes.add("matricula_sige");
        }
        if (esVacio(personaSige.getPassword())) {
            faltantes.add("password_sige");
        }
        if (esVacio(personaSige.getNombre())) {
            faltantes.add("nombre_sige");
        }
        if (esVacio(personaSige.getApellidoPaterno())) {
            faltantes.add("apellidop_sige");
        }
        if (esVacio(personaSige.getApellidoMaterno())) {
            faltantes.add("apellidom_sige");
        }
        if (esVacio(personaSige.getProgramaEducativo())) {
            faltantes.add("programa_sige");
        }
        if (esVacio(personaSige.getDivision())) {
            faltantes.add("division_sige");
        }
        if (esVacio(personaSige.getCorreoInstitucional())) {
            faltantes.add("correo_institucional_sige");
        }
        if (ObjectUtils.isNull(personaSige.getFechaNacimiento())) {
            faltantes.add("fecha_nacimiento_sige");
        }
        if (esVacio(personaSige.getCurp())) {
            faltantes.add("curp_sige");
        }
        if (esVacio(personaSige.getNivelSige())) {
            faltantes.add("nivel_sige");
        }
        if (ObjectUtils.isNull(personaSige.getPersonaIdSige())) {
            faltantes.add("persona_id_sige");
        }
        if (ObjectUtils.isNull(personaSige.getPerfilIdSige())) {
            faltantes.add("perfil_id_sige");
        }
        return faltantes;
    }

    private String construirUrlConexion(FuenteExternaDTO fuente) {
        String url = fuente.getServidor();
        if (ObjectUtils.isNull(url) || url.trim().isEmpty()) {
            return null;
        }
        if (!url.toLowerCase().startsWith("jdbc:")) {
            StringBuilder urlBuilder = new StringBuilder("jdbc:mysql://");
            urlBuilder.append(url.trim());
            if (!esVacio(fuente.getNombreBaseDatos())) {
                urlBuilder.append("/").append(fuente.getNombreBaseDatos().trim());
            }
            url = urlBuilder.toString();
        }
        return url;
    }

    private String truncar(String valor, int max) {
        if (valor == null) {
            return null;
        }
        return valor.length() > max ? valor.substring(0, max) + "..." : valor;
    }

    private String enmascararServidor(String url) {
        if (esVacio(url)) {
            return url;
        }
        try {
            int idxParams = url.indexOf("?");
            String sinParametros = idxParams > 0 ? url.substring(0, idxParams) : url;
            int idxCred = sinParametros.indexOf("@");
            if (idxCred > 0) {
                sinParametros = sinParametros.substring(idxCred + 1);
            }
            return truncar(sinParametros, 200);
        } catch (Exception e) {
            return truncar(url, 200);
        }
    }

    private void guardarPersonaSige(PersonaSigeDTO personaSige, String traceId) {
        PersonaSigeDTO existente = personaSigeService.buscarPorMatricula(personaSige.getMatricula());
        if (ObjectUtils.isNotNull(existente)) {
            personaSige.setIdPersonaSige(existente.getIdPersonaSige());
            LOGGER.info(String.format("[%s] Actualizando persona SIGE existente para matrícula %s", traceId,
                    personaSige.getMatricula()));
            manejarResultadoPersistencia(personaSigeService.actualizar(personaSige));
        } else {
            LOGGER.info(String.format("[%s] Insertando nueva persona SIGE para matrícula %s", traceId,
                    personaSige.getMatricula()));
            manejarResultadoPersistencia(personaSigeService.guardar(personaSige));
        }
    }

    private void manejarResultadoPersistencia(ResultadoDTO<PersonaSigeDTO> resultado) {
        if (ObjectUtils.isNull(resultado) || !resultado.esCorrecto() || ObjectUtils.isNull(resultado.getDto())) {
            String mensaje = "No se pudo guardar la información de la persona SIGE";
            if (resultado != null) {
                if (!ObjectUtils.isNullOrEmpty(resultado.getMensajes())) {
                    mensaje = resultado.getMensajes().get(0);
                } else if (!esVacio(resultado.getMensaje())) {
                    mensaje = resultado.getMensaje();
                }
                LOGGER.error(String.format(
                        "Fallo en persistencia SIGE. esCorrecto=%s, mensaje=%s, mensajes=%s, mensajeError=%s",
                        resultado.esCorrecto(), resultado.getMensaje(), resultado.getMensajes(),
                        resultado.getMensajeError()));
            }
            throw new RuntimeException(mensaje);
        }
    }

    public List<SelectImportarDTO> getListaFuentesExternas() {
        return listaFuentesExternas;
    }

    public List<SelectImportarDTO> getListaPlanes() {
        return listaPlanes;
    }

    public List<SelectImportarDTO> getListaSemestres() {
        return listaSemestres;
    }

    public List<SelectImportarDTO> getListaBloques() {
        return listaBloques;
    }

    public List<SelectImportarDTO> getListaProgramas() {
        return listaProgramas;
    }

    public List<SelectImportarDTO> getListaPeriodos() {
        return listaPeriodos;
    }

    public List<SelectImportarDTO> getListaEventos() {
        return listaEventos;
    }

    public List<SelectImportarDTO> getListaGrupos() {
        return listaGrupos;
    }

    public String getMatriculaNuevaAlta() {
        return matriculaNuevaAlta;
    }

    public void setMatriculaNuevaAlta(String matriculaNuevaAlta) {
        this.matriculaNuevaAlta = matriculaNuevaAlta;
    }

    public String getMatriculaImportar() {
        return matriculaImportar;
    }

    public void setMatriculaImportar(String matriculaImportar) {
        this.matriculaImportar = matriculaImportar;
    }

    public String getFuenteExternaSeleccionada() {
        return fuenteExternaSeleccionada;
    }

    public void setFuenteExternaSeleccionada(String fuenteExternaSeleccionada) {
        this.fuenteExternaSeleccionada = fuenteExternaSeleccionada;
    }

    public String getPlanSeleccionado() {
        return planSeleccionado;
    }

    public void setPlanSeleccionado(String planSeleccionado) {
        this.planSeleccionado = planSeleccionado;
    }

    public String getSemestreSeleccionado() {
        return semestreSeleccionado;
    }

    public void setSemestreSeleccionado(String semestreSeleccionado) {
        this.semestreSeleccionado = semestreSeleccionado;
    }

    public String getBloqueSeleccionado() {
        return bloqueSeleccionado;
    }

    public void setBloqueSeleccionado(String bloqueSeleccionado) {
        this.bloqueSeleccionado = bloqueSeleccionado;
    }

    public String getProgramaSeleccionado() {
        return programaSeleccionado;
    }

    public void setProgramaSeleccionado(String programaSeleccionado) {
        this.programaSeleccionado = programaSeleccionado;
    }

    public String getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(String periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public String getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(String eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }

    public String getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(String grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public String getMensajeValidacionDialogo() {
        return mensajeValidacionDialogo;
    }

    public String getMensajeErrorDialogo() {
        return mensajeErrorDialogo;
    }

    public String getMensajeExitoDialogo() {
        return mensajeExitoDialogo;
    }

    public void setPersonaServiceFacade(PersonaServiceFacade personaServiceFacade) {
        this.personaServiceFacade = personaServiceFacade;
    }

    public void setPersonaSigeService(PersonaSigeService personaSigeService) {
        this.personaSigeService = personaSigeService;
    }

    public void setEventoCapacitacionServiceFacade(
            EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
        this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
    }

    private void mostrarDialogo(String widgetVar) {
        RequestContext.getCurrentInstance().execute("PF('" + widgetVar + "').show()");
    }

    private void mostrarDialogoError(String mensaje) {
        mensajeErrorDialogo = mensaje;
        mostrarDialogo("dlgNuevaAltaError");
    }

    private void mostrarDialogoExito(String mensaje) {
        mensajeExitoDialogo = mensaje;
        mostrarDialogo("dlgNuevaAltaExito");
    }

}
