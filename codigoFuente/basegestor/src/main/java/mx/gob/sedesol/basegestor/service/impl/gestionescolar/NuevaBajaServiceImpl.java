package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;
import java.util.Objects;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaAplicacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaSolicitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DatosMoodlePersonaDTO;
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
        procesarBaja(solicitud, null, null);
    }

    @Override
    @Transactional
    public void actualizarBaja(Long idBaja, BajaSolicitudDTO solicitud, Long idMotivoBaja) {
        if (idBaja == null) {
            throw new IllegalArgumentException("El identificador de la baja es obligatorio para actualizarla");
        }
        ConsultaBajaDTO bajaActual = nuevaBajaRepository.consultarBajaPorId(idBaja);
        if (bajaActual == null) {
            throw new IllegalArgumentException("No se encontró la baja a actualizar");
        }

        Long idTipoActual = bajaActual.getIdTipoBaja() != null ? bajaActual.getIdTipoBaja().longValue() : null;
        Long idTipoSolicitud = solicitud.getIdTipoBaja() != null ? solicitud.getIdTipoBaja() : idTipoActual;
        solicitud.setIdTipoBaja(idTipoSolicitud);

        boolean cambioDeTipo = !Objects.equals(idTipoSolicitud, idTipoActual);

        if (cambioDeTipo) {
            procesarBaja(solicitud, idBaja, idMotivoBaja);
            return;
        }

        Long idPersona = bajaActual.getIdPersona();
        if (idPersona == null) {
            throw new IllegalArgumentException("No se encontró la persona asociada a la baja");
        }

        Long motivoId = idMotivoBaja != null ? idMotivoBaja : bajaActual.getIdMotivoBaja();
        if (motivoId == null) {
            motivoId = nuevaBajaRepository.insertarMotivoBaja(solicitud.getIdTipoBaja(), solicitud.getMotivo());
        } else {
            nuevaBajaRepository.actualizarMotivoBaja(motivoId, solicitud.getIdTipoBaja(), solicitud.getMotivo());
        }

        Long procesoId = nuevaBajaRepository.obtenerIdProcesoBaja();
        BajaAplicacionDTO bajaAplicacionDTO = new BajaAplicacionDTO(
                idPersona,
                motivoId,
                procesoId,
                bajaActual.getIdPlan(),
                bajaActual.getIdPrograma(),
                bajaActual.getIdEvento(),
                bajaActual.getIdGrupo(),
                bajaActual.getIdUserEnrolmentsLms(),
                solicitud.getQuienAplica(),
                bajaActual.getEstatus(),
                solicitud.getNumeroSolicitud());

        nuevaBajaRepository.actualizarBaja(idBaja, bajaAplicacionDTO);
    }

    @Override
    @Transactional
    public void eliminarBaja(ConsultaBajaDTO baja) {
        Long idPersona = baja.getIdPersona() != null ? baja.getIdPersona()
                : nuevaBajaRepository.obtenerIdPersonaPorMatricula(baja.getMatricula());
        if (idPersona == null) {
            throw new IllegalArgumentException("No se encontró la matrícula proporcionada");
        }

        boolean esDefinitiva = contieneTexto(baja.getTipoBaja(), "definitiva");
        boolean esTemporalOParcial = contieneTexto(baja.getTipoBaja(), "temporal")
                || contieneTexto(baja.getTipoBaja(), "parcial");

        nuevaBajaRepository.reactivarPersona(idPersona);

        if (esTemporalOParcial && baja.getIdEvento() != null) {
            reactivarUsuarioEnCurso(baja.getIdEvento(), idPersona);
        } else if (esDefinitiva) {
            try {
                reactivarUsuarioDefinitivamente(idPersona);
            } catch (Exception e) {
                LOGGER.error("No se pudo reactivar al usuario en Moodle", e);
                throw e;
            }
        }

        if (baja.getIdBaja() != null) {
            nuevaBajaRepository.eliminarBaja(baja.getIdBaja().longValue());
        }
    }

    private void procesarBaja(BajaSolicitudDTO solicitud, Long idBaja, Long idMotivoBaja) {
        Long idPersona = nuevaBajaRepository.obtenerIdPersonaPorMatricula(solicitud.getMatriculaUsuario());
        if (idPersona == null) {
            throw new IllegalArgumentException("No se encontró la matrícula proporcionada");
        }

        nuevaBajaRepository.actualizarPersonaInactiva(idPersona);

        Long motivoId = idMotivoBaja != null ? idMotivoBaja
                : nuevaBajaRepository.insertarMotivoBaja(solicitud.getIdTipoBaja(), solicitud.getMotivo());

        if (idMotivoBaja != null) {
            nuevaBajaRepository.actualizarMotivoBaja(idMotivoBaja, solicitud.getIdTipoBaja(), solicitud.getMotivo());
        }

        Long procesoId = nuevaBajaRepository.obtenerIdProcesoBaja();

        BajaMatriculacionDTO matriculacion = solicitud.getIdEvento() != null
                ? nuevaBajaRepository.consultarMatriculacionPorEvento(solicitud.getMatriculaUsuario(), solicitud.getIdEvento())
                : null;

        Long idPlan = matriculacion != null && matriculacion.getIdPlan() != null ? matriculacion.getIdPlan() : solicitud.getIdPlan();
        Long idPrograma = matriculacion != null && matriculacion.getIdPrograma() != null ? matriculacion.getIdPrograma() : solicitud.getIdPrograma();


        boolean esDefinitiva = contieneTexto(solicitud.getNombreTipoBaja(), "definitiva");
        boolean esSinAsignaturas = contieneTexto(solicitud.getNombreTipoBaja(), "sin asignaturas");
        boolean esTemporalOParcial = contieneTexto(solicitud.getNombreTipoBaja(), "temporal")
                || contieneTexto(solicitud.getNombreTipoBaja(), "parcial");

        if (esTemporalOParcial) {
            if (idPlan == null || idPrograma == null) {
                throw new IllegalArgumentException("Debe seleccionar un plan y programa válidos para aplicar la baja parcial/temporal");
            }

            if (!nuevaBajaRepository.validarPlanProgramaPorPersona(idPersona, idPlan, idPrograma)) {
                throw new IllegalArgumentException("El usuario no pertenece al plan y programa seleccionados");
            }
        }
        // Para bajas definitivas: NO validar plan/programa

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
            // BAJA DEFINITIVA: intentar suspender en Moodle
            try {
                DatosMoodlePersonaDTO datosMoodle = obtenerDatosMoodle(idPersona);
                if (datosMoodle != null && datosMoodle.getIdPersonaMoodle() != null) {
                    suspenderUsuarioDefinitivamente(datosMoodle.getIdPersonaMoodle(), datosMoodle.getIdPlataformaMoodle());
                    LOGGER.info("Usuario suspendido en Moodle para baja definitiva - ID Moodle: " + datosMoodle.getIdPersonaMoodle());
                } else {
                    LOGGER.warn("Usuario no tiene cuenta Moodle, continuando con baja en SIGIE");
                }
            } catch (Exception e) {
                LOGGER.warn("Error al suspender en Moodle para baja definitiva, continuando: " + e.getMessage());

            }

            idPrograma = 0L;
            idEvento = 0L;
            idGrupo = 0L;
        }

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

        if (idBaja == null) {
            nuevaBajaRepository.insertarBaja(bajaAplicacionDTO);
        } else {
            bajaAplicacionDTO.setMotivoBajaId(motivoId);
            bajaAplicacionDTO.setQuienAplicaBaja(solicitud.getQuienAplica());
            nuevaBajaRepository.actualizarBaja(idBaja, bajaAplicacionDTO);
        }

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

    private void reactivarUsuarioEnCurso(Long idEvento, Long idPersona) {
        Integer idUsuarioMoodle = nuevaBajaRepository.obtenerIdUsuarioMoodle(idPersona, idEvento);
        if (idUsuarioMoodle == null) {
            throw new IllegalArgumentException("No se encontró el usuario en Moodle para el evento seleccionado");
        }
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
            Integer respuesta = cursoWS.suspenderUsuarioEnCurso(idCurso, idUsuarioMoodle, 0);
            if (respuesta == null) {
                throw new IllegalStateException("No fue posible reactivar al usuario en el curso de Moodle");
            }
        } catch (ErrorWS e) {
            LOGGER.error("Error al reactivar al usuario en el curso de Moodle", e);
            throw new IllegalStateException("No se pudo reactivar al usuario en el curso de Moodle", e);
        }
    }

    private void reactivarUsuarioDefinitivamente(Long idPersona) {
        DatosMoodlePersonaDTO datosMoodle = obtenerDatosMoodle(idPersona);
        if (datosMoodle == null || datosMoodle.getIdPersonaMoodle() == null) {
            throw new IllegalArgumentException("No se encontró el usuario en Moodle para reactivarlo");
        }
        ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(datosMoodle.getIdPlataformaMoodle());
        if (plataforma == null) {
            throw new IllegalArgumentException("No se encontró la configuración de la plataforma Moodle");
        }
        UsuarioWSClient usuarioWSClient = new UsuarioWSClient(plataforma);
        Usuario usuario = new Usuario();
        usuario.setId(datosMoodle.getIdPersonaMoodle());
        usuario.setSuspended(0);
        try {
            usuarioWSClient.actualizarUsuarioSuspender(usuario);
        } catch (ErrorWS e) {
            LOGGER.error("Error al reactivar al usuario en Moodle", e);
            throw new IllegalStateException("No se pudo reactivar al usuario en Moodle", e);
        }
    }
}
