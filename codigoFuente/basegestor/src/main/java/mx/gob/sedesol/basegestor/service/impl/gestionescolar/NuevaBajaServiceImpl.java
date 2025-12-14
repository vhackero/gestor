package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaAplicacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaSolicitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DatosMoodlePersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.INuevaBajaRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.NuevaBajaService;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.UsuarioWSClient;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("nuevaBajaService")
public class NuevaBajaServiceImpl implements NuevaBajaService {

    private static final Logger LOGGER = Logger.getLogger(NuevaBajaServiceImpl.class);

    @Autowired
    private INuevaBajaRepository nuevaBajaRepository;

    @Autowired
    private ParametroWSMoodleService parametroWSMoodleService;

    @Override
    public List<NodoDTO> obtenerTiposBaja() {
        return nuevaBajaRepository.consultarTiposBajaActivos();
    }

    @Override
    public List<PlanBajaDTO> obtenerPlanes() {
        return nuevaBajaRepository.consultarPlanesActivos();
    }

    @Override
    public List<NodoDTO> obtenerSemestresPorPlan(Long idPlan) {
        return nuevaBajaRepository.consultarSemestresPorPlan(idPlan);
    }

    @Override
    public List<NodoDTO> obtenerBloquesPorSemestre(Long idSemestre) {
        return nuevaBajaRepository.consultarBloquesPorSemestre(idSemestre);
    }

    @Override
    public List<NodoDTO> obtenerProgramasPorEje(Long idEjeCapacitacion) {
        return nuevaBajaRepository.consultarProgramasPorEje(idEjeCapacitacion);
    }

    @Override
    public List<String> obtenerPeriodos() {
        return nuevaBajaRepository.consultarPeriodosInscripcion();
    }

    @Override
    public List<NodoDTO> obtenerEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma, String matricula) {
        return nuevaBajaRepository.consultarEventosPorPeriodoYPrograma(nombrePeriodo, idPrograma, matricula);
    }

    @Override
    public BajaMatriculaDetalleDTO obtenerDatosPorMatricula(String matricula) {
        return nuevaBajaRepository.consultarDatosPorMatricula(matricula);
    }

    @Override
    @Transactional
    public void aplicarBaja(BajaSolicitudDTO solicitud) {
        Long idPersona = nuevaBajaRepository.obtenerIdPersonaPorMatricula(solicitud.getMatriculaUsuario());
        if (idPersona == null) {
            throw new IllegalArgumentException("No se encontró la matrícula proporcionada");
        }

        nuevaBajaRepository.actualizarPersonaInactiva(idPersona);

        Long motivoId = nuevaBajaRepository.insertarMotivoBaja(solicitud.getIdTipoBaja(), solicitud.getMotivo());
        Long procesoId = nuevaBajaRepository.obtenerIdProcesoBaja();

        BajaMatriculacionDTO matriculacion = solicitud.getIdEvento() != null
                ? nuevaBajaRepository.consultarMatriculacionPorEvento(solicitud.getMatriculaUsuario(), solicitud.getIdEvento())
                : null;

        Long idPlan = matriculacion != null && matriculacion.getIdPlan() != null ? matriculacion.getIdPlan() : solicitud.getIdPlan();
        Long idPrograma = matriculacion != null && matriculacion.getIdPrograma() != null ? matriculacion.getIdPrograma() : solicitud.getIdPrograma();
        validarPlanYPrograma(idPersona, idPlan, idPrograma);

        boolean esDefinitiva = contieneTexto(solicitud.getNombreTipoBaja(), "definitiva");
        boolean esSinAsignaturas = contieneTexto(solicitud.getNombreTipoBaja(), "sin asignaturas");
        boolean esTemporalOParcial = contieneTexto(solicitud.getNombreTipoBaja(), "temporal")
                || contieneTexto(solicitud.getNombreTipoBaja(), "parcial");

        Long idEvento = obtenerEventoSeleccionado(esTemporalOParcial, solicitud, matriculacion);
        Long idGrupo = matriculacion != null && matriculacion.getIdGrupo() != null ? matriculacion.getIdGrupo() : 0L;

        Integer idUserEnrolmentsLms = 0;

        if (esTemporalOParcial) {
            validarEventoParaBajaParcial(matriculacion, idEvento);
            Integer idUsuarioMoodle = nuevaBajaRepository.obtenerIdUsuarioMoodle(idPersona, idEvento);
            if (idUsuarioMoodle == null) {
                throw new IllegalArgumentException("No se encontró el usuario en Moodle para el evento seleccionado");
            }
            idUserEnrolmentsLms = suspenderUsuarioEnCurso(idEvento, idUsuarioMoodle);
        } else if (esDefinitiva) {
            DatosMoodlePersonaDTO datosMoodle = obtenerDatosMoodle(idPersona);
            suspenderUsuarioDefinitivamente(datosMoodle.getIdPersonaMoodle(), datosMoodle.getIdPlataformaMoodle());
            idPrograma = idPrograma != null ? idPrograma : 0L;
            idEvento = 0L;
            idGrupo = 0L;
        }

        int contabilizar = 1;

        BajaAplicacionDTO bajaAplicacionDTO = new BajaAplicacionDTO(
                idPersona,
                motivoId,
                procesoId,
                idPlan,
                idPrograma != null ? idPrograma : 0L,
                idEvento != null ? idEvento : 0L,
                idGrupo != null ? idGrupo : 0L,
                idUserEnrolmentsLms,
                solicitud.getQuienAplica(),
                contabilizar,
                solicitud.getNumeroSolicitud());

        nuevaBajaRepository.insertarBaja(bajaAplicacionDTO);
    }

    private boolean contieneTexto(String origen, String texto) {
        return origen != null && texto != null && origen.toLowerCase().contains(texto.toLowerCase());
    }

    private void validarPlanYPrograma(Long idPersona, Long idPlan, Long idPrograma) {
        if (idPlan == null || idPrograma == null) {
            throw new IllegalArgumentException("Debe seleccionar un plan y programa válidos para aplicar la baja");
        }

        if (!nuevaBajaRepository.validarPlanProgramaPorPersona(idPersona, idPlan, idPrograma)) {
            throw new IllegalArgumentException("El usuario no pertenece al plan y programa seleccionados");
        }
    }

    private void validarEventoParaBajaParcial(BajaMatriculacionDTO matriculacion, Long idEvento) {
        if (idEvento == null || idEvento == 0) {
            throw new IllegalArgumentException("Debe seleccionar un evento válido para aplicar la baja parcial o temporal");
        }

        if (matriculacion == null || matriculacion.getIdEvento() == null || matriculacion.getIdGrupo() == null) {
            throw new IllegalArgumentException("El usuario no se encuentra matriculado en el evento seleccionado");
        }

        if (!idEvento.equals(matriculacion.getIdEvento())) {
            throw new IllegalArgumentException("El evento seleccionado no coincide con la matrícula del usuario");
        }
    }

    private Long obtenerEventoSeleccionado(boolean esTemporalOParcial, BajaSolicitudDTO solicitud, BajaMatriculacionDTO matriculacion) {
        if (!esTemporalOParcial) {
            return solicitud.getIdEvento() != null ? solicitud.getIdEvento() : 0L;
        }

        if (matriculacion != null && matriculacion.getIdEvento() != null) {
            return matriculacion.getIdEvento();
        }

        if (solicitud.getIdEvento() != null) {
            return solicitud.getIdEvento();
        }

        throw new IllegalArgumentException("Debe seleccionar un evento para aplicar la baja parcial o temporal");
    }

    private Integer suspenderUsuarioEnCurso(Long idEvento, Integer idUsuarioMoodle) {
        Integer idPlataforma = nuevaBajaRepository.obtenerIdPlataformaMoodle(idEvento);
        if (idPlataforma == null) {
            throw new IllegalArgumentException("No se encontró la plataforma de Moodle para el evento seleccionado");
        }

        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
        if (plataforma == null) {
            throw new IllegalArgumentException("No se encontró la configuración de la plataforma Moodle");
        }

        try {
            Integer idCurso = nuevaBajaRepository.obtenerIdCursoMoodle(idEvento);
            if (idCurso == null) {
                throw new IllegalArgumentException("No se encontró el curso en Moodle para el evento seleccionado");
            }
            CursoWS cursoWS = new CursoWS(plataforma);
            Integer idUserEnrolmentsLms = cursoWS.suspenderUsuarioEnCurso(idCurso, idUsuarioMoodle, 1);
            if (idUserEnrolmentsLms == null) {
                LOGGER.warn("El servicio Moodle no devolvió id_user_enrolments_lms para la suspensión parcial");
                throw new IllegalStateException("No fue posible obtener el identificador de enrolment al suspender en Moodle");
            }
            return idUserEnrolmentsLms;
        } catch (ErrorWS e) {
            LOGGER.error("Error al suspender al usuario en el curso de Moodle", e);
            throw new IllegalStateException("No se pudo suspender al usuario en el curso de Moodle", e);
        }
    }

    private void suspenderUsuarioDefinitivamente(Integer idUsuarioMoodle, Integer idPlataforma) {
        if (idUsuarioMoodle == null || idPlataforma == null) {
            throw new IllegalArgumentException("No se cuenta con información suficiente para suspender al usuario en Moodle");
        }

        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
        if (plataforma == null) {
            throw new IllegalArgumentException("No se encontró la configuración de la plataforma Moodle");
        }

        try {
            UsuarioWSClient usuarioWSClient = new UsuarioWSClient(plataforma);
            boolean suspendido = usuarioWSClient.suspenderUsuarioDefinitivo(idUsuarioMoodle);
            if (!suspendido) {
                throw new IllegalStateException("No fue posible suspender al usuario en Moodle");
            }
        } catch (ErrorWS e) {
            LOGGER.error("Error al suspender definitivamente al usuario en Moodle", e);
            throw new IllegalStateException("No se pudo suspender definitivamente al usuario en Moodle", e);
        }
    }

    private DatosMoodlePersonaDTO obtenerDatosMoodle(Long idPersona) {
        List<DatosMoodlePersonaDTO> datosMoodle = nuevaBajaRepository.obtenerDatosMoodlePorPersona(idPersona);
        if (datosMoodle == null || datosMoodle.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la relación del usuario con Moodle");
        }
        return datosMoodle.get(0);
    }
}
