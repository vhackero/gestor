package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaAplicacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaSolicitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.ws.moodle.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.ws.moodle.Usuario;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.INuevaBajaRepository;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.gestionescolar.NuevaBajaService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.ErrorWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.UsuarioWSClient;

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

        if (!matriculado && esDefinitiva) {
            idPrograma = 0L;
        }

        Integer idUsuarioMoodle = matriculado
                ? nuevaBajaRepository.obtenerIdUsuarioMoodlePorPersonaYEvento(idPersona, idEvento)
                : 0;
        Integer idUserEnrolmentsLms = 0;

        if (matriculado && idUsuarioMoodle != null && idUsuarioMoodle > 0 && idEvento != null) {
            try {
                Long idCursoLms = nuevaBajaRepository.obtenerIdCursoMoodlePorEvento(idEvento);
                Integer idPlataformaLms = nuevaBajaRepository.obtenerIdPlataformaMoodlePorEvento(idEvento);
                ParametroWSMoodleDTO plataforma = idPlataformaLms != null
                        ? parametroWSMoodleService.buscarPorId(idPlataformaLms)
                        : null;

                if (plataforma != null) {
                    if (esDefinitiva) {
                        Usuario usuario = new Usuario();
                        usuario.setId(idUsuarioMoodle);
                        usuario.setSuspended(1);
                        boolean suspendido = new UsuarioWSClient(plataforma).actualizarUsuarioSuspender(usuario);
                        idUserEnrolmentsLms = suspendido ? 0 : idUserEnrolmentsLms;
                    } else if (idCursoLms != null) {
                        Integer idEnrolment = new CursoWS(plataforma)
                                .suspenderUsuarioEnCurso(idCursoLms.intValue(), idUsuarioMoodle, 1);
                        idUserEnrolmentsLms = idEnrolment != null ? idEnrolment : 0;
                    }
                }
            } catch (ErrorWS e) {
                LOGGER.error("Error al suspender usuario en Moodle", e);
            }
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
}
