package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaAplicacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaSolicitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.INuevaBajaRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.NuevaBajaService;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Usuario;
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
    @Transactional(noRollbackFor = IllegalStateException.class)
    public void aplicarBaja(BajaSolicitudDTO solicitud) {
        validarCamposObligatorios(solicitud);

        Long idPersona = nuevaBajaRepository.obtenerIdPersonaPorMatricula(solicitud.getMatriculaUsuario());
        if (idPersona == null) {
            throw new IllegalArgumentException("No se encontró la matrícula proporcionada");
        }

        boolean esDefinitiva = contieneTexto(solicitud.getNombreTipoBaja(), "definitiva");
        boolean esTemporalOParcial = contieneTexto(solicitud.getNombreTipoBaja(), "temporal")
                || contieneTexto(solicitud.getNombreTipoBaja(), "parcial");

        if (!esDefinitiva && !datosSeleccionadosValidos(solicitud)) {
            throw new IllegalArgumentException("Los datos seleccionados no corresponden al estudiante");
        }

        nuevaBajaRepository.actualizarPersonaInactiva(idPersona);

        boolean suspensionMoodleCorrecta;
        if (esDefinitiva) {
            suspensionMoodleCorrecta = aplicarBajaDefinitiva(solicitud, idPersona);
        } else {
            suspensionMoodleCorrecta = aplicarBajaEnCurso(solicitud, idPersona, esTemporalOParcial);
        }

        if (!suspensionMoodleCorrecta) {
            throw new IllegalStateException("La baja se registró, pero no fue posible aplicarla en Moodle. Intente más tarde.");
        }
    }

    private void validarCamposObligatorios(BajaSolicitudDTO solicitud) {
        if (solicitud.getMatriculaUsuario() == null || solicitud.getMatriculaUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("La matrícula o usuario es obligatoria");
        }

        if (solicitud.getIdTipoBaja() == null) {
            throw new IllegalArgumentException("El tipo de baja es obligatorio");
        }

        boolean esDefinitiva = contieneTexto(solicitud.getNombreTipoBaja(), "definitiva");
        boolean requiereCurso = !esDefinitiva;

        if (solicitud.getIdPlan() == null) {
            throw new IllegalArgumentException("El plan es obligatorio para este tipo de baja");
        }

        if (requiereCurso) {
            if (solicitud.getIdSemestre() == null) {
                throw new IllegalArgumentException("El semestre es obligatorio");
            }
            if (solicitud.getIdBloque() == null) {
                throw new IllegalArgumentException("El bloque es obligatorio");
            }
            if (solicitud.getIdPrograma() == null) {
                throw new IllegalArgumentException("El programa es obligatorio");
            }
            if (solicitud.getIdPeriodo() == null || solicitud.getIdPeriodo().trim().isEmpty()) {
                throw new IllegalArgumentException("El periodo es obligatorio");
            }
            if (solicitud.getIdEvento() == null) {
                throw new IllegalArgumentException("El evento es obligatorio");
            }
        }
    }

    private boolean datosSeleccionadosValidos(BajaSolicitudDTO solicitud) {
        Long idPlanSeleccionado = solicitud.getIdPlan();
        Long idProgramaSeleccionado = solicitud.getIdPrograma();

        if (idProgramaSeleccionado != null && idProgramaSeleccionado == 0) {
            idProgramaSeleccionado = null;
        }

        return nuevaBajaRepository.validarPlanYProgramaPorMatricula(
                solicitud.getMatriculaUsuario(),
                idPlanSeleccionado,
                idProgramaSeleccionado);
    }

    private boolean contieneTexto(String origen, String texto) {
        return origen != null && texto != null && origen.toLowerCase().contains(texto.toLowerCase());
    }

    private boolean aplicarBajaDefinitiva(BajaSolicitudDTO solicitud, Long idPersona) {
        Integer idUsuarioMoodle = nuevaBajaRepository.obtenerIdUsuarioMoodlePorPersona(idPersona);
        Integer idPlataforma = nuevaBajaRepository.obtenerIdPlataformaMoodlePorPersona(idPersona);
        boolean moodleSuspendido = suspenderUsuarioGlobal(idUsuarioMoodle, idPlataforma);

        Long motivoId = nuevaBajaRepository.insertarMotivoBaja(solicitud.getIdTipoBaja(), solicitud.getMotivo());
        Long procesoId = nuevaBajaRepository.obtenerIdProcesoBaja();
        int contabilizar = 1;

        BajaAplicacionDTO bajaAplicacionDTO = new BajaAplicacionDTO(
                idPersona,
                motivoId,
                procesoId,
                solicitud.getIdPlan() != null ? solicitud.getIdPlan() : 0L,
                0L,
                0L,
                0L,
                0,
                solicitud.getQuienAplica(),
                contabilizar,
                solicitud.getNumeroSolicitud());

        nuevaBajaRepository.insertarBaja(bajaAplicacionDTO);
        return moodleSuspendido;
    }

    private boolean aplicarBajaEnCurso(BajaSolicitudDTO solicitud, Long idPersona, boolean esTemporalOParcial) {
        BajaMatriculacionDTO matriculacion = nuevaBajaRepository.consultarMatriculacionPorEvento(
                solicitud.getMatriculaUsuario(), solicitud.getIdEvento());
        boolean matriculado = matriculacion != null && matriculacion.getIdEvento() != null && matriculacion.getIdGrupo() != null;

        Long idPlan = matriculado ? obtenerIdPlan(matriculacion, solicitud.getIdPlan()) : solicitud.getIdPlan();
        Long idPrograma = matriculado ? obtenerIdPrograma(matriculacion, solicitud.getIdPrograma()) : solicitud.getIdPrograma();
        Long idEvento = matriculado ? matriculacion.getIdEvento() : 0L;
        Long idGrupo = matriculado ? matriculacion.getIdGrupo() : 0L;

        Integer idUsuarioMoodle = matriculado
                ? nuevaBajaRepository.obtenerIdUsuarioMoodle(idPersona, matriculacion.getIdEvento())
                : null;

        boolean suspensionRequerida = esTemporalOParcial && matriculado;
        Integer idUserEnrolmentsLms = suspenderUsuarioEnCurso(suspensionRequerida,
                matriculado ? matriculacion.getIdEvento() : null, idUsuarioMoodle);

        Long motivoId = nuevaBajaRepository.insertarMotivoBaja(solicitud.getIdTipoBaja(), solicitud.getMotivo());
        Long procesoId = nuevaBajaRepository.obtenerIdProcesoBaja();
        int contabilizar = 1;

        BajaAplicacionDTO bajaAplicacionDTO = new BajaAplicacionDTO(
                idPersona,
                motivoId,
                procesoId,
                idPlan != null ? idPlan : 0L,
                idPrograma != null ? idPrograma : 0L,
                idEvento != null ? idEvento : 0L,
                idGrupo != null ? idGrupo : 0L,
                idUserEnrolmentsLms != null ? idUserEnrolmentsLms : 0,
                solicitud.getQuienAplica(),
                contabilizar,
                solicitud.getNumeroSolicitud());

        nuevaBajaRepository.insertarBaja(bajaAplicacionDTO);
        return !suspensionRequerida || idUserEnrolmentsLms != null;
    }

    private boolean suspenderUsuarioGlobal(Integer idUsuarioMoodle, Integer idPlataforma) {
        if (idUsuarioMoodle == null) {
            LOGGER.warn("No se pudo suspender globalmente al usuario porque no existe en Moodle");
            return false;
        }

        if (idPlataforma == null) {
            LOGGER.warn("No se encontró plataforma de Moodle asociada al usuario");
            return false;
        }

        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
        if (plataforma == null) {
            LOGGER.warn("No se pudo obtener la configuración de Moodle para la suspensión global");
            return false;
        }

        try {
            UsuarioWSClient usuarioWSClient = new UsuarioWSClient(plataforma);
            Usuario usuario = new Usuario();
            usuario.setId(idUsuarioMoodle);
            usuario.setSuspended(1);
            boolean suspendido = usuarioWSClient.actualizarUsuarioSuspender(usuario);
            if (!suspendido) {
                LOGGER.warn("Moodle rechazó la suspensión global del usuario con id " + idUsuarioMoodle);
            }
            return suspendido;
        } catch (ErrorWS e) {
            LOGGER.error("Error al comunicarse con Moodle durante la aplicación de la baja definitiva", e);
            return false;
        }
    }

    private Integer suspenderUsuarioEnCurso(boolean procesarSuspension, Long idEvento, Integer idUsuarioMoodle) {
        if (!procesarSuspension) {
            return null;
        }

        if (idUsuarioMoodle == null) {
            LOGGER.warn("No se encontró el identificador de usuario en Moodle para suspenderlo en el curso");
            return null;
        }

        Integer idPlataforma = nuevaBajaRepository.obtenerIdPlataformaMoodle(idEvento);
        if (idPlataforma == null) {
            LOGGER.warn("No se encontró la plataforma de Moodle asociada al evento " + idEvento);
            return null;
        }

        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
        if (plataforma == null) {
            LOGGER.warn("No se pudo obtener la configuración de Moodle");
            return null;
        }

        Integer idCurso = nuevaBajaRepository.obtenerIdCursoMoodle(idEvento);
        if (idCurso == null) {
            LOGGER.warn("No se encontró el curso en Moodle para el evento seleccionado");
            return null;
        }

        return intentarSuspensionEnCurso(plataforma, idCurso, idUsuarioMoodle, 2);
    }

    private Integer intentarSuspensionEnCurso(ParametroWSMoodleDTO plataforma, Integer idCurso, Integer idUsuarioMoodle, int reintentos) {
        int intentos = 0;
        while (intentos < reintentos) {
            try {
                CursoWS cursoWS = new CursoWS(plataforma);
                Integer idEnrolment = cursoWS.suspenderUsuarioEnCurso(idCurso, idUsuarioMoodle, 1);
                if (idEnrolment != null && idEnrolment > 0) {
                    return idEnrolment;
                }
                LOGGER.warn("Intento " + (intentos + 1) + " falló al suspender al usuario en el curso " + idCurso);
            } catch (ErrorWS e) {
                LOGGER.error("Fallo al comunicarse con Moodle en el intento " + (intentos + 1) + " de suspensión", e);
            }
            intentos++;
        }
        return null;
    }

    private Long obtenerIdPlan(BajaMatriculacionDTO matriculacion, Long idPlanSolicitud) {
        if (matriculacion != null && matriculacion.getIdPlan() != null) {
            return matriculacion.getIdPlan();
        }
        return idPlanSolicitud != null ? idPlanSolicitud : 0L;
    }

    private Long obtenerIdPrograma(BajaMatriculacionDTO matriculacion, Long idProgramaSolicitud) {
        if (matriculacion != null && matriculacion.getIdPrograma() != null) {
            return matriculacion.getIdPrograma();
        }
        return idProgramaSolicitud != null ? idProgramaSolicitud : 0L;
    }
}
