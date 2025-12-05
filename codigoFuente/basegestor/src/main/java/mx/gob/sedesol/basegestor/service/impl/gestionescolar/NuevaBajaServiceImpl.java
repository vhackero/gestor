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
    public List<NodoDTO> obtenerEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma) {
        return nuevaBajaRepository.consultarEventosPorPeriodoYPrograma(nombrePeriodo, idPrograma);
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

        BajaMatriculacionDTO matriculacion = nuevaBajaRepository.consultarMatriculacionPorEvento(
                solicitud.getMatriculaUsuario(), solicitud.getIdEvento());
        boolean matriculado = matriculacion != null && matriculacion.getIdEvento() != null && matriculacion.getIdGrupo() != null;

        Long idPlan = matriculacion != null && matriculacion.getIdPlan() != null ? matriculacion.getIdPlan() : solicitud.getIdPlan();
        Long idPrograma = matriculacion != null && matriculacion.getIdPrograma() != null ? matriculacion.getIdPrograma() : solicitud.getIdPrograma();
        Long idEvento = matriculado ? matriculacion.getIdEvento() : 0L;
        Long idGrupo = matriculado ? matriculacion.getIdGrupo() : 0L;

        boolean esDefinitiva = contieneTexto(solicitud.getNombreTipoBaja(), "definitiva");
        boolean esSinAsignaturas = contieneTexto(solicitud.getNombreTipoBaja(), "sin asignaturas");
        boolean esTemporalOParcial = contieneTexto(solicitud.getNombreTipoBaja(), "temporal")
                || contieneTexto(solicitud.getNombreTipoBaja(), "parcial");

        if (!matriculado && esDefinitiva) {
            idPrograma = 0L;
        }

        Long idEventoBaja = matriculado && matriculacion.getIdEvento() != null ? matriculacion.getIdEvento() : solicitud.getIdEvento();
        Integer idUsuarioMoodle = idEventoBaja != null ? nuevaBajaRepository.obtenerIdUsuarioMoodle(idPersona, idEventoBaja) : null;
        Integer idUserEnrolmentsLms = procesarSuspensionEnMoodle(esDefinitiva, esTemporalOParcial, idEventoBaja, idUsuarioMoodle);
        idUserEnrolmentsLms = idUserEnrolmentsLms != null ? idUserEnrolmentsLms : 0;
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

    private Integer procesarSuspensionEnMoodle(boolean esDefinitiva, boolean esTemporalOParcial, Long idEvento,
                                               Integer idUsuarioMoodle) {
        if (idEvento == null || idEvento == 0 || idUsuarioMoodle == null) {
            return 0;
        }

        Integer idPlataforma = nuevaBajaRepository.obtenerIdPlataformaMoodle(idEvento);
        if (idPlataforma == null) {
            return 0;
        }

        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
        if (plataforma == null) {
            return 0;
        }

        try {
            if (esTemporalOParcial) {
                Integer idCurso = nuevaBajaRepository.obtenerIdCursoMoodle(idEvento);
                if (idCurso == null) {
                    throw new IllegalArgumentException("No se encontró el curso en Moodle para el evento seleccionado");
                }
                CursoWS cursoWS = new CursoWS(plataforma);
                return cursoWS.suspenderUsuarioEnCurso(idCurso, idUsuarioMoodle, 1);
            }

            if (esDefinitiva) {
                UsuarioWSClient usuarioWSClient = new UsuarioWSClient(plataforma);
                Usuario usuario = new Usuario();
                usuario.setId(idUsuarioMoodle);
                usuario.setSuspended(1);
                boolean suspendido = usuarioWSClient.actualizarUsuarioSuspender(usuario);
                if (!suspendido) {
                    throw new IllegalStateException("No fue posible suspender al usuario en Moodle");
                }
                return 0;
            }
        } catch (ErrorWS e) {
            LOGGER.error("Error al comunicarse con Moodle durante la aplicación de la baja", e);
            throw new IllegalStateException("No se pudo aplicar la baja en Moodle", e);
        }

        return 0;
    }
}
