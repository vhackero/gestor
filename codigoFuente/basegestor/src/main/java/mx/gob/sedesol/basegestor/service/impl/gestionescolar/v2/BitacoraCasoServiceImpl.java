package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.BitacoraCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoBitacoraV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoBitacoraV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.BitacoraCasoService;

@Service("bitacoraCasoService")
public class BitacoraCasoServiceImpl implements BitacoraCasoService {

    @Autowired
    private TblCasoBitacoraV2Repo bitacoraRepo;

    @Override
    @Transactional
    public void registrarEvento(Long idCaso, String evento, String detalle, String usuario) throws InscripcionException {
        if (idCaso == null) {
            throw new InscripcionException("No se puede registrar bitacora sin idCaso.");
        }
        BitacoraCasoDTO dto = new BitacoraCasoDTO();
        dto.setEvento(evento);
        dto.setDetalle(detalle);
        dto.setUsuario(usuario);
        bitacoraRepo.save(AsistenteCurricularV2Mapper.toBitacoraEntity(idCaso, dto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitacoraCasoDTO> obtenerBitacora(Long idCaso) throws InscripcionException {
        List<BitacoraCasoDTO> resultado = new ArrayList<BitacoraCasoDTO>();
        bitacoraRepo.findByIdCasoOrderByFechaAsc(idCaso)
                .forEach(item -> resultado.add(AsistenteCurricularV2Mapper.toBitacoraDto(item)));
        return resultado;
    }

    @Override
    @Transactional
    public BitacoraCasoDTO actualizarBitacora(Long idCaso, BitacoraCasoDTO bitacora) throws InscripcionException {
        if (idCaso == null || bitacora == null || bitacora.getId() == null) {
            throw new InscripcionException("La bitácora requiere idCaso e idBitacora para actualizarse.");
        }
        TblCasoBitacoraV2 existente = bitacoraRepo.findByIdAndIdCaso(bitacora.getId(), idCaso);
        if (existente == null) {
            throw new InscripcionException("No existe la bitácora indicada para el caso.");
        }
        existente.setDetalle(bitacora.getDetalle());
        existente.setUsuario(bitacora.getUsuario());
        return AsistenteCurricularV2Mapper.toBitacoraDto(bitacoraRepo.save(existente));
    }

    @Override
    @Transactional
    public void eliminarBitacora(Long idCaso, Long idBitacora) throws InscripcionException {
        if (idCaso == null || idBitacora == null) {
            throw new InscripcionException("La eliminación de bitácora requiere idCaso e idBitacora.");
        }
        TblCasoBitacoraV2 existente = bitacoraRepo.findByIdAndIdCaso(idBitacora, idCaso);
        if (existente == null) {
            throw new InscripcionException("No existe la bitácora indicada para el caso.");
        }
        bitacoraRepo.delete(existente);
    }
}
