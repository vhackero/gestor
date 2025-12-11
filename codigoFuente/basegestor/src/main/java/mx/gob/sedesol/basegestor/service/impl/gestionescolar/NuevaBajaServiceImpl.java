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
    @Transactional
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

        BajaMatriculacionDTO matriculacion = esDefinitiva ? null
                : nuevaBajaRepository.consultarMatriculacionPorEvento(solicitud.getMatriculaUsuario(), solicitud.getIdEvento());
        boolean matriculado = !esDefinitiva && matriculacion != null && matriculacion.getIdEvento() != null
                && matriculacion.getIdGrupo() != null;

        Long idPlan = esDefinitiva ? solicitud.getIdPlan() : (matriculado ? obtenerIdPlan(matriculacion, solicitud.getIdPlan()) : 0L);
        Long idPrograma = esDefinitiva ? 0L : (matriculado ? obtenerIdPrograma(matriculacion, solicitud.getIdPrograma()) : 0L);
        Long idEvento = esDefinitiva ? 0L : (matriculado ? matriculacion.getIdEvento() : 0L);
        Long idGrupo = esDefinitiva ? 0L : (matriculado ? matriculacion.getIdGrupo() : 0L);

        Long idEventoBaja = esDefinitiva ? 0L
                : (matriculado && matriculacion.getIdEvento() != null ? matriculacion.getIdEvento() : solicitud.getIdEvento());
        Integer idUsuarioMoodle = obtenerIdUsuarioMoodle(esDefinitiva, matriculado, idPersona, idEventoBaja);

        Integer idUserEnrolmentsLms = procesarSuspensionEnMoodle(esDefinitiva, esTemporalOParcial, matriculado,
                idPersona, idEventoBaja, idUsuarioMoodle);

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
                idUserEnrolmentsLms,
                solicitud.getQuienAplica(),
                contabilizar,
                solicitud.getNumeroSolicitud());

        nuevaBajaRepository.insertarBaja(bajaAplicacionDTO);
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

        if (!esDefinitiva && solicitud.getIdPlan() == null) {
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

    private Integer procesarSuspensionEnMoodle(boolean esDefinitiva, boolean esTemporalOParcial, boolean matriculado,
                                               Long idPersona, Long idEvento, Integer idUsuarioMoodle) {
        if (esDefinitiva) {
            return suspenderUsuarioGlobal(idPersona, idUsuarioMoodle);
        }

        if (!esTemporalOParcial || !matriculado) {
            return 0;
        }

        if (idUsuarioMoodle == null) {
            throw new IllegalStateException("No se pudo obtener el identificador de usuario en Moodle");
        }

        Integer idPlataforma = nuevaBajaRepository.obtenerIdPlataformaMoodle(idEvento);
        if (idPlataforma == null) {
            throw new IllegalStateException("No se encontró la plataforma de Moodle asociada al evento");
        }

        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
        if (plataforma == null) {
            throw new IllegalStateException("No se pudo obtener la configuración de Moodle");
        }

        try {
            Integer idCurso = nuevaBajaRepository.obtenerIdCursoMoodle(idEvento);
            if (idCurso == null) {
                throw new IllegalArgumentException("No se encontró el curso en Moodle para el evento seleccionado");
            }
            CursoWS cursoWS = new CursoWS(plataforma);
            Integer idEnrolment = cursoWS.suspenderUsuarioEnCurso(idCurso, idUsuarioMoodle, 1);
            if (idEnrolment == null || idEnrolment == 0) {
                throw new IllegalStateException("No fue posible suspender al usuario en el curso indicado");
            }
            return idEnrolment;
        } catch (ErrorWS e) {
            LOGGER.error("Error al comunicarse con Moodle durante la aplicación de la baja", e);
            throw new IllegalStateException("No se pudo aplicar la baja en Moodle", e);
        }
    }

    private Integer suspenderUsuarioGlobal(Long idPersona, Integer idUsuarioMoodle) {
        if (idUsuarioMoodle == null) {
            throw new IllegalStateException("No se pudo obtener el identificador de usuario en Moodle");
        }

        Integer idPlataforma = nuevaBajaRepository.obtenerIdPlataformaMoodlePorPersona(idPersona);
        if (idPlataforma == null) {
            throw new IllegalStateException("No se encontró plataforma de Moodle asociada al usuario");
        }

        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
        if (plataforma == null) {
            throw new IllegalStateException("No se pudo obtener la configuración de Moodle para la suspensión global");
        }

        try {
            UsuarioWSClient usuarioWSClient = new UsuarioWSClient(plataforma);
            Usuario usuario = new Usuario();
            usuario.setId(idUsuarioMoodle);
            usuario.setSuspended(1);
            boolean suspendido = usuarioWSClient.actualizarUsuarioSuspender(usuario);
            if (!suspendido) {
                throw new IllegalStateException("No fue posible suspender al usuario en Moodle");
            }
            return 0;
        } catch (ErrorWS e) {
            LOGGER.error("Error al comunicarse con Moodle durante la aplicación de la baja definitiva", e);
            throw new IllegalStateException("No se pudo aplicar la baja definitiva en Moodle", e);
        }
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

    private Integer obtenerIdUsuarioMoodle(boolean esDefinitiva, boolean matriculado, Long idPersona, Long idEventoBaja) {
        if (esDefinitiva) {
            return nuevaBajaRepository.obtenerIdUsuarioMoodlePorPersona(idPersona);
        }

        if (matriculado && idEventoBaja != null) {
            return nuevaBajaRepository.obtenerIdUsuarioMoodle(idPersona, idEventoBaja);
        }

        return null;
    }
}
