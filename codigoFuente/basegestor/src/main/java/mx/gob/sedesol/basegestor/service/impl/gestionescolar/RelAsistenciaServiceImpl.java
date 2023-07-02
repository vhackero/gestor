package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaAuxDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelAsistenciaDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelAsistencia;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelAsistenciaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelAsistenciaService;

@Service("relAsistenciaService")
public class RelAsistenciaServiceImpl extends ComunValidacionService<RelAsistenciaDTO> implements RelAsistenciaService {

    @Autowired
    private RelAsistenciaRepo relAsistenciaRepo;

    private ModelMapper modelMapper = new ModelMapper();

    private static final Logger logger = Logger.getLogger(RegistroAsistenciaServiceImpl.class);

    @Override
    public List<RelAsistenciaDTO> getAsistenciaByIdGrupoParticipante(List<Integer> listIdGrupoPart) {
        List<RelAsistencia> lista = relAsistenciaRepo.getAsistenciaByGrupoParticipante(listIdGrupoPart);

        Type objetoDTO = new TypeToken<List<RelAsistenciaDTO>>() {
        }.getType();

        return modelMapper.map(lista, objetoDTO);
    }

    @Override
    public void save(AsistenciaAuxDTO asistenciaAuxDTO) {
        RelAsistencia relAsistencia = new RelAsistencia();

        modelMapper.map(asistenciaAuxDTO, relAsistencia);

        relAsistencia.setIdDiasEventoCap(asistenciaAuxDTO.getIdDiaCapacitacion());
        relAsistencia.setIdTpoAsistencia(asistenciaAuxDTO.getIdtipoAsistencia());

        logger.info("   ID         " + relAsistencia.getId());
        logger.info("   IdGpoPart  " + relAsistencia.getIdGrupoParticipante());
        logger.info("   ID dia     " + relAsistencia.getIdDiasEventoCap());
        logger.info("   idTipoAsis " + relAsistencia.getIdTpoAsistencia());
        logger.info("---------------------------------");

        relAsistenciaRepo.save(relAsistencia);
//GUSTAVO --guardarBitacora(asistenciaAuxDTO.getBitacoraDTO(), String.valueOf(relAsistencia.getId()));
    }

    public List<GroupByGestionEscolarDTO> obtenerAsistenciaAgrupadaPorEventoCapacitacion(List<Integer> idEventoCapacitacionList, Integer idTipoAsistencia) {
        return relAsistenciaRepo.obtenerAsistenciaAgrupadaPorEventoCapacitacion(idEventoCapacitacionList, idTipoAsistencia);

    }

    @Override
    public void validarPersistencia(RelAsistenciaDTO dto, ResultadoDTO<RelAsistenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelAsistenciaDTO dto, ResultadoDTO<RelAsistenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelAsistenciaDTO dto, ResultadoDTO<RelAsistenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
