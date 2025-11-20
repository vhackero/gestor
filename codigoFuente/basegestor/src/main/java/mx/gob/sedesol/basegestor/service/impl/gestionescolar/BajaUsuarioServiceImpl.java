package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatalogoOpcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.NuevaBajaDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IBajaUsuarioRepository;
import mx.gob.sedesol.basegestor.service.ServiceException;
import mx.gob.sedesol.basegestor.service.gestionescolar.BajaUsuarioService;

@Service("bajaUsuarioService")
public class BajaUsuarioServiceImpl implements BajaUsuarioService {

    @Autowired
    private IBajaUsuarioRepository bajaUsuarioRepository;

    @Override
    public List<CatalogoOpcionDTO> obtenerTiposBaja() {
        return bajaUsuarioRepository.obtenerTiposBaja();
    }

    @Override
    public Optional<Long> buscarPersonaPorMatricula(String matricula) {
        return bajaUsuarioRepository.buscarPersonaPorMatricula(matricula);
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerPlanesPorPersona(Long idPersona) {
        return bajaUsuarioRepository.obtenerPlanesPorPersona(idPersona);
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerSemestres(Long idPersona, Integer idPlan) {
        return bajaUsuarioRepository.obtenerSemestres(idPersona, idPlan);
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerBloques(Long idPersona, Integer idPlan, Integer semestre) {
        return bajaUsuarioRepository.obtenerBloques(idPersona, idPlan, semestre);
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerProgramas(Long idPersona, Integer idPlan, Integer semestre, String bloque) {
        return bajaUsuarioRepository.obtenerProgramas(idPersona, idPlan, semestre, bloque);
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerPeriodos(Integer idPlan) {
        return bajaUsuarioRepository.obtenerPeriodos(idPlan);
    }

    @Override
    public List<CatalogoOpcionDTO> obtenerEventos(Integer idPlan, Integer idPrograma) {
        return bajaUsuarioRepository.obtenerEventos(idPlan, idPrograma);
    }

    @Override
    public BajaDetalleDTO obtenerDetalleBaja(Long idPersona, Integer idPlan, Integer idPrograma, Integer idEvento) {
        return bajaUsuarioRepository.obtenerDetalleBaja(idPersona, idPlan, idPrograma, idEvento);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void aplicarBaja(NuevaBajaDTO nuevaBaja, Long usuarioSesion) throws ServiceException {
        try {
            if (StringUtils.isBlank(nuevaBaja.getMatricula())) {
                throw new ServiceException("La matrícula o usuario es obligatoria", null);
            }

            Optional<Long> persona = bajaUsuarioRepository.buscarPersonaPorMatricula(nuevaBaja.getMatricula());
            if (!persona.isPresent()) {
                throw new ServiceException("No se encontró información de la matrícula proporcionada", null);
            }
            nuevaBaja.setIdPersona(persona.get());

            if (nuevaBaja.getIdPlan() == null) {
                throw new ServiceException("El plan es obligatorio para aplicar la baja", null);
            }

            Integer procesoId = bajaUsuarioRepository.obtenerProcesoBajas();
            nuevaBaja.setProcesoId(procesoId != null ? procesoId : 0);

            BajaDetalleDTO detalle = bajaUsuarioRepository.obtenerDetalleBaja(nuevaBaja.getIdPersona(), nuevaBaja.getIdPlan(),
                    nuevaBaja.getIdPrograma(), nuevaBaja.getIdEvento());
            if (detalle == null || detalle.getIdPlan() == null || detalle.getIdPrograma() == null || detalle.getIdEvento() == null) {
                throw new ServiceException("El usuario no cuenta con matriculación para aplicar la baja solicitada", null);
            }

            nuevaBaja.setIdPlan(detalle.getIdPlan());
            nuevaBaja.setIdPrograma(detalle.getIdPrograma());
            nuevaBaja.setIdEvento(detalle.getIdEvento());
            nuevaBaja.setIdGrupo(detalle.getIdGrupo());

            String descripcion = StringUtils.defaultString(nuevaBaja.getMotivo());
            if (StringUtils.isNotBlank(nuevaBaja.getNumeroSolicitud())) {
                descripcion = descripcion + " | Número de solicitud: " + nuevaBaja.getNumeroSolicitud();
            }
            if (StringUtils.isNotBlank(nuevaBaja.getQuienAplica())) {
                descripcion = descripcion + " | Quién aplica: " + nuevaBaja.getQuienAplica();
            }
            nuevaBaja.setMotivo(descripcion);

            Integer idMotivo = bajaUsuarioRepository.registrarMotivo(nuevaBaja);
            nuevaBaja.setIdMotivo(idMotivo);

            bajaUsuarioRepository.registrarBaja(nuevaBaja, usuarioSesion);

        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            throw new ServiceException("Ocurrió un error al aplicar la baja", e);
        }
    }
}
