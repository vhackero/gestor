package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try {
            if (esVacio(matriculaImportar) || esVacio(fuenteExternaSeleccionada)) {
                agregarMsgWarn("Configurar correctamente los datos de la fuente externa", null);
                return;
            }

            Integer idFuente = parseEntero(fuenteExternaSeleccionada);
            if (ObjectUtils.isNull(idFuente)) {
                agregarMsgWarn("Configurar correctamente los datos de la fuente externa", null);
                return;
            }

            FuenteExternaDTO fuente = personaServiceFacade.buscarFuenteExternaPorId(idFuente);
            if (ObjectUtils.isNull(fuente) || esVacio(fuente.getConsulta())) {
                agregarMsgWarn("Configurar correctamente los datos de la fuente externa", null);
                return;
            }

            String consultaNormalizada = normalizarConsulta(fuente.getConsulta());
            if (consultaNormalizada == null) {
                agregarMsgWarn("Configurar correctamente los datos de la fuente externa", null);
                return;
            }

            try (Connection conexion = crearConexion(fuente)) {
                if (conexion == null) {
                    agregarMsgError("No fue posible establecer conexión con la fuente externa.", null);
                    return;
                }
                try (PreparedStatement ps = conexion.prepareStatement(consultaNormalizada)) {
                    ps.setString(1, matriculaImportar.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            agregarMsgWarn("No se encontró información para la matrícula especificada.", null);
                            return;
                        }
                        PersonaSigeDTO personaSige = mapearPersonaSige(rs);
                        if (ObjectUtils.isNull(personaSige) || esVacio(personaSige.getMatricula())) {
                            agregarMsgWarn("Configurar correctamente los datos de la fuente externa", null);
                            return;
                        }
                        guardarPersonaSige(personaSige);
                        matriculaNuevaAlta = personaSige.getMatricula();
                        agregarMsgInfo("Información importada correctamente", null);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al importar datos desde fuente externa", e);
            agregarMsgError(e.getMessage(), null);
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

    private String normalizarConsulta(String consulta) {
        if (consulta.contains("?")) {
            return consulta;
        }
        if (consulta.toLowerCase().contains(":matricula")) {
            return consulta.replaceAll("(?i):matricula", "?");
        }
        return null;
    }

    private Connection crearConexion(FuenteExternaDTO fuente) throws SQLException {
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
        Properties propiedades = new Properties();
        if (!esVacio(fuente.getUsuario())) {
            propiedades.put("user", fuente.getUsuario());
        }
        String password = obtenerPassword(fuente.getAlias());
        if (!esVacio(password)) {
            propiedades.put("password", password);
        }
        return DriverManager.getConnection(url, propiedades);
    }

    private String obtenerPassword(String alias) {
        if (esVacio(alias)) {
            return null;
        }
        String porPropiedad = System.getProperty("fuente.externa." + alias + ".password");
        if (!esVacio(porPropiedad)) {
            return porPropiedad;
        }
        String porAmbiente = System.getenv("FUENTE_EXTERNA_" + alias.toUpperCase() + "_PASSWORD");
        return porAmbiente;
    }

    private PersonaSigeDTO mapearPersonaSige(ResultSet rs) throws SQLException {
        PersonaSigeDTO persona = new PersonaSigeDTO();
        persona.setMatricula(obtenerString(rs, "matricula", "matricula_sige"));
        persona.setPassword(obtenerString(rs, "password", "password_sige"));
        persona.setNombre(obtenerString(rs, "nombre", "nombre_sige"));
        persona.setApellidoPaterno(obtenerString(rs, "apellido_paterno", "apellidop_sige"));
        persona.setApellidoMaterno(obtenerString(rs, "apellido_materno", "apellidom_sige"));
        persona.setProgramaEducativo(obtenerString(rs, "programa_educativo", "programa_educativo_sige"));
        persona.setDivision(obtenerString(rs, "division", "division_sige"));
        persona.setCorreoInstitucional(obtenerString(rs, "correo_institucional", "correo_institucional_sige"));
        persona.setFechaNacimiento(obtenerFecha(rs, "fecha_nacimiento", "fecha_nacimiento_sige"));
        persona.setCurp(obtenerString(rs, "curp", "curp_sige"));
        persona.setNivelSige(obtenerString(rs, "nivel", "nivel_sige"));
        persona.setPersonaIdSige(obtenerEntero(rs, "persona_id", "persona_id_sige"));
        persona.setPerfilIdSige(obtenerEntero(rs, "perfil_id", "perfil_id_sige"));
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

    private int obtenerEntero(ResultSet rs, String... columnas) throws SQLException {
        for (String columna : columnas) {
            if (tieneColumna(rs, columna)) {
                return rs.getInt(columna);
            }
        }
        return 0;
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

    private void guardarPersonaSige(PersonaSigeDTO personaSige) {
        PersonaSigeDTO existente = personaSigeService.buscarPorMatricula(personaSige.getMatricula());
        if (ObjectUtils.isNotNull(existente)) {
            personaSige.setIdPersonaSige(existente.getIdPersonaSige());
            if (ObjectUtils.isNull(personaSigeService.actualizar(personaSige).getDto())) {
                LOGGER.warn("No se pudo actualizar la información de la persona SIGE");
            }
        } else {
            if (ObjectUtils.isNull(personaSigeService.guardar(personaSige).getDto())) {
                LOGGER.warn("No se pudo guardar la información de la persona SIGE");
            }
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
