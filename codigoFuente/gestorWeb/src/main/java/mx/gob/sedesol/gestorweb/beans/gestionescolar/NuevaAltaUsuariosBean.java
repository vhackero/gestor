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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.FuenteExternaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.basegestor.service.admin.PersonaSigeService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class NuevaAltaUsuariosBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final List<String> COLUMNAS_REQUERIDAS = Arrays.asList("matricula_sige", "password_sige",
            "nombre_sige", "apellidop_sige", "apellidom_sige", "programa_educativo_sige", "division_sige",
            "correo_institucional_sige", "fecha_nacimiento_sige", "curp_sige", "nivel_sige", "persona_id_sige",
            "perfil_id_sige");
    private static final int MAX_PARAMETROS_BUSQUEDA = 2;
    private static final Integer ID_SEDE_DEFAULT = 11;
    private static final String ID_PAIS_DEFAULT = ConstantesGestor.ID_PAIS_MEXICO;
    private static final String ID_ASENTAMIENTO_DEFAULT = "100010407";
    private static final String ID_ENTIDAD_FEDERATIVA_DEFAULT = "01";
    private static final String ENTIDAD_FEDERATIVA_DEFAULT = "Mexico";
    private static final String ID_MUNICIPIO_DEFAULT = "12";
    private static final String MUNICIPIO_DEFAULT = "Patriotismo";
    private static final String ID_DEPENDENCIA_DEFAULT = "2-00";
    private static final String CLAVE_DEPENDENCIA_DEFAULT = "11";
    private static final String DEPENDENCIA_DEFAULT = "Dependencia";
    private static final String ID_UNIDAD_ADMINISTRATIVA_DEFAULT = "11";
    private static final String CALLE_DEFAULT = "Reforma";
    private static final String NUMERO_EXTERIOR_DEFAULT = "001";
    private static final String INSTITUCION_DEFAULT = "UNADM";
    private static final String MENSAJE_IMPORTACION_IGNORADA =
            "La matrícula/usuario ya existe en tbl_persona_sige. El registro se ignoró.";
    private static final String MENSAJE_ERROR_REGISTRO_AUTOMATICO =
            "No se pudo registrar al usuario en el sistema.";

    @ManagedProperty("#{personaServiceFacade}")
    private transient PersonaServiceFacade personaServiceFacade;

    @ManagedProperty("#{personaSigeService}")
    private transient PersonaSigeService personaSigeService;

    @ManagedProperty("#{eventoCapacitacionServiceFacade}")
    private transient EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

    @ManagedProperty("#{sistema}")
    private transient SistemaBean sistema;

    private static final Logger LOGGER = Logger.getLogger(NuevaAltaUsuariosBean.class);
    private transient BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
                mostrarDialogoValidacion(obtenerTextoSistema(
                        "gw.gestionescolar.altasbajas.nuevaAlta.modal.capturarModalFuente"));
                LOGGER.warn(String.format("[%s] Faltan datos de entrada para importar.", traceId));
                return;
            }

            Integer idFuente = parseEntero(fuenteExternaSeleccionada);
            if (ObjectUtils.isNull(idFuente)) {
                mostrarDialogoError(obtenerTextoSistema(
                        "gw.gestionescolar.altasbajas.nuevaAlta.modal.fuenteInvalida"));
                LOGGER.warn(String.format("[%s] No se pudo parsear la fuente externa seleccionada: %s", traceId,
                        fuenteExternaSeleccionada));
                return;
            }
            LOGGER.info(String.format("[%s] Fuente externa parseada. idFuente=%s", traceId, idFuente));

            FuenteExternaDTO fuente = personaServiceFacade.buscarFuenteExternaPorId(idFuente);
            if (ObjectUtils.isNull(fuente)) {
                mostrarDialogoError(obtenerTextoSistema(
                        "gw.gestionescolar.altasbajas.nuevaAlta.modal.sinConfigFuenteExterna"));
                LOGGER.warn(String.format("[%s] Fuente externa no encontrada. idFuente=%s", traceId, idFuente));
                return;
            }
            if (esVacio(fuente.getConsulta())) {
                mostrarDialogoError(obtenerTextoSistema(
                        "gw.gestionescolar.altasbajas.nuevaAlta.modal.sinConsultaConfig"));
                LOGGER.warn(String.format("[%s] Consulta vacía para fuente id=%s", traceId, idFuente));
                return;
            }

            int totalParametros = obtenerTotalParametrosBusqueda(fuente.getConsulta());
            String consultaNormalizada = normalizarConsulta(fuente.getConsulta(), totalParametros);
            if (consultaNormalizada == null) {
                int numInterrogaciones = contarOcurrencias(fuente.getConsulta(), "?");
                int numMatricula = contarOcurrenciasIgnoreCase(fuente.getConsulta(), ":matricula");
                int numUsuario = contarOcurrenciasIgnoreCase(fuente.getConsulta(), ":usuario");
                LOGGER.warn(String.format(
                        "[%s] Consulta inválida. Parámetros encontrados -> ?: %d, :matricula: %d, :usuario: %d",
                        traceId, numInterrogaciones, numMatricula, numUsuario));
                mostrarDialogoError(obtenerTextoSistema(
                        "gw.gestionescolar.altasbajas.nuevaAlta.modal.configurarDatosFuente"));
                return;
            }

            try (Connection conexion = crearConexion(fuente)) {
                if (conexion == null) {
                    mostrarDialogoError(obtenerTextoSistema(
                            "gw.gestionescolar.altasbajas.nuevaAlta.modal.sinConexionFuenteExterna"));
                    LOGGER.warn(String.format("[%s] No se pudo construir la conexión (servidor vacío).", traceId));
                    return;
                }
                String urlConexion = construirUrlConexion(fuente);
                LOGGER.info(String.format("[%s] Conexión creada. url=%s, userPresent=%s, passwordPresent=%s, passwordLength=%d",
                        traceId, enmascararServidor(urlConexion), !esVacio(fuente.getUsuario()),
                        fuente.getContrasena() != null, fuente.getContrasena() != null ? fuente.getContrasena().length() : 0));
                try (PreparedStatement ps = conexion.prepareStatement(consultaNormalizada)) {
                    String criterioBusqueda = matriculaImportar.trim();
                    asignarParametrosBusqueda(ps, criterioBusqueda, totalParametros);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            mostrarDialogoError(obtenerTextoSistema(
                                    "gw.gestionescolar.altasbajas.nuevaAlta.modal.matriculaNoEncontrada"));
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
                            mostrarDialogoError(obtenerTextoSistema(
                                    "gw.gestionescolar.altasbajas.nuevaAlta.modal.consultaColumnasRequeridas")
                                    + " " + String.join(", ", columnasFaltantes));
                            return;
                        }

                        PersonaSigeDTO personaSige = mapearPersonaSige(rs);

                        if (ObjectUtils.isNull(personaSige) || esVacio(personaSige.getMatricula())) {
                            mostrarDialogoError(obtenerTextoSistema(
                                    "gw.gestionescolar.altasbajas.nuevaAlta.modal.sinMatriculaSige"));
                            LOGGER.warn(String.format("[%s] PersonaSige mapeada sin matrícula.", traceId));
                            return;
                        }
                        List<String> camposFaltantes = validarCamposObligatorios(personaSige);
                        if (!camposFaltantes.isEmpty()) {
                            LOGGER.warn(String.format("[%s] PersonaSige con campos obligatorios vacíos. Faltantes=%s",
                                    traceId, camposFaltantes));
                            mostrarDialogoError(obtenerTextoSistema(
                                    "gw.gestionescolar.altasbajas.nuevaAlta.modal.camposObligatoriosVacios")
                                    + " " + String.join(", ", camposFaltantes));
                            return;
                        }
                        LOGGER.info(String.format(
                                "[%s] Persona mapeada. Matricula=%s, Nombre=%s, Apellidos=%s %s, Correo=%s", traceId,
                                personaSige.getMatricula(), personaSige.getNombre(),
                                personaSige.getApellidoPaterno(), personaSige.getApellidoMaterno(),
                                personaSige.getCorreoInstitucional()));
                        boolean insertada = guardarPersonaSigeSiNoExiste(personaSige, traceId);
                        limpiarDatosImportacion();
                        if (!insertada) {
                            mostrarDialogoExito(MENSAJE_IMPORTACION_IGNORADA);
                            LOGGER.info(String.format("[%s] Importación ignorada para matrícula %s por registro existente",
                                    traceId, personaSige.getMatricula()));
                            return;
                        }
                        mostrarDialogoExito(obtenerTextoSistema(
                                "gw.gestionescolar.altasbajas.nuevaAlta.modal.importacionCorrecta"));
                        LOGGER.info(String.format("[%s] Importación exitosa para matrícula %s", traceId,
                                personaSige.getMatricula()));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al importar datos desde fuente externa", e);
            String mensaje = ObjectUtils.isNullOrEmpty(e.getMessage()) ? "Error al importar datos desde fuente externa"
                    : e.getMessage();
            mostrarDialogoError(mensaje);
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
        PersonaDTO persona = obtenerPersonaParaMatriculacion(matriculaNuevaAlta != null ? matriculaNuevaAlta.trim() : null);
        if (ObjectUtils.isNull(persona)) {
            return;
        }
        Long idPersona = persona.getIdPersona();

        Integer idEvento = parseEntero(eventoSeleccionado);
        Integer idGrupo = parseEntero(grupoSeleccionado);
        if (ObjectUtils.isNull(idEvento) || ObjectUtils.isNull(idGrupo)) {
            LOGGER.warn("Evento o grupo no seleccionados correctamente");
            mostrarDialogoError(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.sinEventoGrupo"));
            return;
        }

        EventoCapacitacionDTO evento = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
                .getEvento(idEvento);
        GrupoDTO grupo = eventoCapacitacionServiceFacade.getGrupoService().buscarGrupoPorId(idGrupo);

        if (ObjectUtils.isNull(evento) || ObjectUtils.isNull(grupo)) {
            LOGGER.warn("No se pudo recuperar la información de evento o grupo");
            mostrarDialogoError(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.sinInformacionRecuperada"));
            return;
        }

        if (yaEstaMatriculado(idEvento, idPersona)) {
            LOGGER.info("El usuario ya está matriculado en el evento seleccionado");
            mostrarDialogoError(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.usuarioYaMatriculadoEvento"));
            return;
        }

        grupo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        grupo.setFachaActualizacion(new Date());

        ParametroWSMoodleDTO parametroWSMoodleDTO = obtenerParametrosMoodle(evento);
        if (requiereMoodle(evento) && ObjectUtils.isNull(parametroWSMoodleDTO)) {
            LOGGER.error("No se encontró la plataforma para matricular");
            mostrarDialogoError(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.noEncontradaPlataforma"));
            return;
        }

        LOGGER.info(String.format("Matriculando usuario %s en evento %s y grupo %s", idPersona, idEvento,
                idGrupo));
        RelGrupoParticipanteDTO participante = eventoCapacitacionServiceFacade.almacenarParticipante(grupo, persona,
                evento, parametroWSMoodleDTO);

        if (ObjectUtils.isNull(participante)) {
            LOGGER.error("No fue posible matricular al usuario");
            mostrarDialogoError(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.noMatriculacionUsuario"));
            return;
        }

        LOGGER.info("Alta registrada correctamente");
        mostrarDialogoExito(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.altaAplicada"));
        limpiarFormulario();
    }

    private PersonaDTO obtenerPersonaParaMatriculacion(String matriculaOUsuario) {
        Optional<Long> idPersona = personaServiceFacade.getPersonaService().obtenerIdPersonaPorMatricula(matriculaOUsuario);

        if (idPersona.isPresent()) {
            PersonaDTO personaExistente = personaServiceFacade.obtenerPersonaPorId(idPersona.get());
            if (ObjectUtils.isNull(personaExistente)) {
                LOGGER.warn("No se encontró la información del usuario existente en tbl_persona");
                mostrarDialogoError(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.informacionNoEncontrada"));
            }
            return personaExistente;
        }

        PersonaSigeDTO personaSige = personaSigeService.buscarPorMatricula(matriculaOUsuario);
        if (ObjectUtils.isNull(personaSige)) {
            LOGGER.warn("No se encontró la matrícula/usuario en tbl_persona ni en tbl_persona_sige");
            mostrarDialogoError(obtenerTextoSistema("gw.gestionescolar.altasbajas.nuevaAlta.modal.informacionNoEncontrada"));
            return null;
        }

        return registrarPersonaDesdeSige(personaSige);
    }

    private PersonaDTO registrarPersonaDesdeSige(PersonaSigeDTO personaSige) {
        try {
            CapturaPersonaDTO datos = construirCapturaPersonaDesdeSige(personaSige);
            ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.guardarPersona(datos);

            if (ObjectUtils.isNull(resultado) || !resultado.esCorrecto() || ObjectUtils.isNull(resultado.getDto())
                    || ObjectUtils.isNull(resultado.getDto().getIdPersona())) {
                LOGGER.error(String.format("No se pudo registrar automáticamente al usuario %s desde tbl_persona_sige",
                        personaSige.getMatricula()));
                mostrarDialogoError(obtenerMensajeErrorRegistro(resultado));
                return null;
            }

            LOGGER.info(String.format("Usuario %s registrado automáticamente en tbl_persona", personaSige.getMatricula()));
            return personaServiceFacade.obtenerPersonaPorId(resultado.getDto().getIdPersona());
        } catch (Exception e) {
            LOGGER.error("Error al registrar automáticamente al usuario desde tbl_persona_sige", e);
            mostrarDialogoError(obtenerMensajeErrorRegistro(e));
            return null;
        }
    }

    private CapturaPersonaDTO construirCapturaPersonaDesdeSige(PersonaSigeDTO personaSige) {
        Long usuarioModifico = getUsuarioEnSession().getIdPersona();
        EntidadFederativaDTO sede = personaServiceFacade.getEntidadFederativaService().buscarPorId(ID_SEDE_DEFAULT);
        List<MunicipioDTO> municipios = personaServiceFacade.getMunicipioService()
                .buscarPorEntidadFederativa(ID_SEDE_DEFAULT);
        AsentamientoDTO asentamiento = personaServiceFacade.getAsentamientoService().buscarPorId(ID_ASENTAMIENTO_DEFAULT);

        CapturaPersonaDTO datos = new CapturaPersonaDTO();
        PersonaDTO persona = crearPersonaDesdeSige(personaSige, usuarioModifico);
        datos.setPersona(persona);
        datos.setRoles(obtenerRolesPorDefectoAlumno());
        datos.setDatosAcademicos(new PersonaDatosAcademicoDTO());
        datos.setDomicilioPersona(crearDomicilioPorDefecto(asentamiento, usuarioModifico, datos));
        datos.setDatosLaborales(crearDatosLaboralesPorDefecto(persona, sede, municipios));
        datos.setPersonaCorreo(crearCorreoPersona(usuarioModifico, datos));
        datos.getPersona().setContraseniaEncriptada(encoder.encode(datos.getPersona().getNuevaContrasenia()));
        return datos;
    }

    private PersonaDTO crearPersonaDesdeSige(PersonaSigeDTO personaSige, Long usuarioModifico) {
        PersonaDTO persona = new PersonaDTO(usuarioModifico, ID_PAIS_DEFAULT);
        String password = personaSige.getPassword();
        persona.setUsuario(personaSige.getMatricula().toUpperCase());
        persona.setNuevaContrasenia(password);
        persona.setConfirmacionContrasenia(password);
        persona.setCurp(personaSige.getCurp());
        persona.setUnidadAdministrativa(personaSige.getPassword());
        persona.setNombre(personaSige.getNombre());
        persona.setApellidoPaterno(personaSige.getApellidoPaterno());
        persona.setApellidoMaterno(personaSige.getApellidoMaterno());
        persona.setFechaNacimiento(personaSige.getFechaNacimiento());
        if (!esVacio(personaSige.getCurp()) && personaSige.getCurp().length() >= 9) {
            persona.setRfc(personaSige.getCurp().substring(0, 9));
        }
        persona.setCorreoElectronico(personaSige.getCorreoInstitucional());
        persona.setFuenteExterna(personaSige.getProgramaEducativo());
        persona.setIdEntidadFederativa(ID_ENTIDAD_FEDERATIVA_DEFAULT);
        persona.setEntidadFederativa(ENTIDAD_FEDERATIVA_DEFAULT);
        persona.setIdMunicipio(ID_MUNICIPIO_DEFAULT);
        persona.setMunicipio(MUNICIPIO_DEFAULT);
        persona.setIdDependencia(ID_DEPENDENCIA_DEFAULT);
        persona.setClaveDependencia(CLAVE_DEPENDENCIA_DEFAULT);
        persona.setDependencia(DEPENDENCIA_DEFAULT);
        persona.setIdUnidadAdministrativa(ID_UNIDAD_ADMINISTRATIVA_DEFAULT);
        persona.setSso_status(String.valueOf(personaSige.getIdPersonaSige()));
        return persona;
    }

    private UsuarioDatosLaboralesDTO crearDatosLaboralesPorDefecto(PersonaDTO persona, EntidadFederativaDTO sede,
            List<MunicipioDTO> municipios) {
        UsuarioDatosLaboralesDTO datosLaborales = new UsuarioDatosLaboralesDTO(persona);
        datosLaborales.setInstitucion(INSTITUCION_DEFAULT);
        datosLaborales.setSede(sede);
        if (municipios != null && !municipios.isEmpty()) {
            datosLaborales.setMunicipio(municipios.get(0));
        }
        datosLaborales.setFechaIngreso(persona.getFechaActualizacion());
        return datosLaborales;
    }

    private PersonaCorreoDTO crearCorreoPersona(Long usuarioModifico, CapturaPersonaDTO datos) {
        PersonaCorreoDTO correo = new PersonaCorreoDTO(usuarioModifico, ConstantesGestor.TIPO_CORREO_INSTITUCIONAL);
        TipoCorreoDTO tipoCorreo = new TipoCorreoDTO();
        tipoCorreo.setDescripcion("Correo");
        tipoCorreo.setIdTipoCorreo(ConstantesGestor.TIPO_CORREO_INSTITUCIONAL);
        tipoCorreo.setActivo(1);
        correo.setCorreoElectronico(datos.getPersona().getCorreoElectronico());
        correo.setPersona(datos.getPersona());
        correo.setTipoCorreo(tipoCorreo);
        return correo;
    }

    private DomicilioPersonaDTO crearDomicilioPorDefecto(AsentamientoDTO asentamiento, Long usuarioModifico,
            CapturaPersonaDTO datos) {
        DomicilioPersonaDTO domicilio = new DomicilioPersonaDTO(usuarioModifico, ID_PAIS_DEFAULT);
        domicilio.setAsentamiento(asentamiento);
        domicilio.setPersona(datos.getPersona());
        domicilio.setIdMunicipio("11");
        domicilio.setIdEntidadFederativa(11);
        domicilio.setCalle(CALLE_DEFAULT);
        domicilio.setNumeroExterior(NUMERO_EXTERIOR_DEFAULT);
        return domicilio;
    }

    private List<RolDTO> obtenerRolesPorDefectoAlumno() {
        List<RolDTO> roles = new ArrayList<>();
        RolDTO rolAlumno = personaServiceFacade.obtenerRolAlumno();
        if (ObjectUtils.isNotNull(rolAlumno)) {
            roles.add(rolAlumno);
        }
        return roles;
    }

    private String obtenerMensajeErrorRegistro(ResultadoDTO<PersonaDTO> resultado) {
        String detalle = obtenerDetalleErrorRegistro(resultado);
        if (!esVacio(detalle)) {
            return MENSAJE_ERROR_REGISTRO_AUTOMATICO + " Detalle: " + detalle;
        }
        return MENSAJE_ERROR_REGISTRO_AUTOMATICO;
    }

    private String obtenerDetalleErrorRegistro(ResultadoDTO<PersonaDTO> resultado) {
        if (ObjectUtils.isNotNull(resultado)) {
            if (!esVacio(resultado.getMensaje())) {
                return resultado.getMensaje();
            }
            if (!ObjectUtils.isNullOrEmpty(resultado.getMensajes())) {
                for (String mensaje : resultado.getMensajes()) {
                    String detalle = obtenerTextoResultado(mensaje);
                    if (!esVacio(detalle)) {
                        return detalle;
                    }
                }
            }
            if (ObjectUtils.isNotNull(resultado.getMensajeError())) {
                return obtenerTextoSistema(resultado.getMensajeError().getId());
            }
        }
        return null;
    }

    private String obtenerMensajeErrorRegistro(Exception e) {
        if (ObjectUtils.isNotNull(e) && !esVacio(e.getMessage())) {
            return MENSAJE_ERROR_REGISTRO_AUTOMATICO + " Detalle: " + e.getMessage();
        }
        return MENSAJE_ERROR_REGISTRO_AUTOMATICO;
    }

    private String obtenerTextoResultado(String mensaje) {
        if (esVacio(mensaje)) {
            return null;
        }
        String texto = obtenerTextoSistema(mensaje);
        return esVacio(texto) ? mensaje : texto;
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

    private int obtenerTotalParametrosBusqueda(String consulta) {
        if (esVacio(consulta)) {
            return 0;
        }
        int numInterrogaciones = contarOcurrencias(consulta, "?");
        int numMatricula = contarOcurrenciasIgnoreCase(consulta, ":matricula");
        int numUsuario = contarOcurrenciasIgnoreCase(consulta, ":usuario");
        return numInterrogaciones + numMatricula + numUsuario;
    }

    private String normalizarConsulta(String consulta, int totalParametros) {
        if (esVacio(consulta)) {
            return null;
        }
        if (totalParametros < 1 || totalParametros > MAX_PARAMETROS_BUSQUEDA) {
            return null;
        }
        return consulta.replaceAll("(?i):matricula", "?").replaceAll("(?i):usuario", "?");
    }

    private void asignarParametrosBusqueda(PreparedStatement ps, String criterioBusqueda, int totalParametros)
            throws SQLException {
        for (int i = 1; i <= totalParametros; i++) {
            ps.setString(i, criterioBusqueda);
        }
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
        persona.setProgramaEducativo(obtenerString(rs, "programa_educativo_sige"));
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
            faltantes.add("programa_educativo_sige");
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

    private boolean guardarPersonaSigeSiNoExiste(PersonaSigeDTO personaSige, String traceId) {
        PersonaSigeDTO existente = personaSigeService.buscarPorMatricula(personaSige.getMatricula());
        if (ObjectUtils.isNotNull(existente)) {
            LOGGER.info(String.format("[%s] Persona SIGE ya existente para matrícula %s. Se ignora la importación.",
                    traceId, personaSige.getMatricula()));
            return false;
        }
        LOGGER.info(String.format("[%s] Insertando nueva persona SIGE para matrícula %s", traceId,
                personaSige.getMatricula()));
        manejarResultadoPersistencia(personaSigeService.guardar(personaSige));
        return true;
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

    public void setSistema(SistemaBean sistema) {
        this.sistema = sistema;
    }

    private void mostrarDialogo(String widgetVar) {
        RequestContext.getCurrentInstance().execute("PF('" + widgetVar + "').show()");
    }

    private String obtenerTextoSistema(String clave) {
        return sistema != null ? sistema.obtenerTexto(clave) : clave;
    }

    private void mostrarDialogoValidacion(String mensaje) {
        mensajeValidacionDialogo = mensaje;
        mostrarDialogo("dlgNuevaAltaValidacion");
    }

    private void mostrarDialogoError(String mensaje) {
        mensajeErrorDialogo = mensaje;
        mostrarDialogo("dlgNuevaAltaError");
    }

    private void mostrarDialogoExito(String mensaje) {
        mensajeExitoDialogo = mensaje;
        mostrarDialogo("dlgNuevaAltaExito");
    }

    private void limpiarDatosImportacion() {
        matriculaNuevaAlta = null;
        matriculaImportar = null;
        fuenteExternaSeleccionada = null;
    }

}
