package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelDiasEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelDiasEventoCapacitacionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelDiasEventoCapacitacionService;

@Service("relDiasEventoCapacitacionService")
public class RelDiasEventoCapacitacionServiceImpl extends ComunValidacionService<RelDiasEventoCapacitacionDTO> implements RelDiasEventoCapacitacionService {

    private static final Logger logger = Logger.getLogger(RelDiasEventoCapacitacionServiceImpl.class);

    private ModelMapper modelMapper = new ModelMapper();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private RelDiasEventoCapacitacionRepo relDiasEventoCapacitacionRepo;

    @Override
    public boolean existeDiaEvento(Date date, int idGrupo) {

        logger.info("********************* existeDiaEvento ************************");
        boolean encontrado = false;

        List<RelDiasEventoCapacitacion> lista = relDiasEventoCapacitacionRepo.getDiasEventoByGrupo(idGrupo);

        for (RelDiasEventoCapacitacion dia : lista) {

            logger.info("  date " + dia.getFechaEventoCapacitacion() + " <-> " + date);
            logger.info("  dia.getTime()  " + dia.getFechaEventoCapacitacion().getTime()
                    + " <-> " + date.getTime());
            if (dia.getFechaEventoCapacitacion().getTime() == date.getTime()) {

                encontrado = true;
                break;
            }
        }

        logger.info(" encontrado = " + encontrado);

        return encontrado;
    }

    @Override
    public List<RelDiasEventoCapacitacionDTO> getDiasEventoByGrupo(Integer idGrupo) {

        List<RelDiasEventoCapacitacion> lista = relDiasEventoCapacitacionRepo.getDiasEventoByGrupo(idGrupo);
        List<RelDiasEventoCapacitacionDTO> listDTO;

        Type objetoDTO = new TypeToken<List<RelDiasEventoCapacitacionDTO>>() {
        }.getType();

        listDTO = modelMapper.map(lista, objetoDTO);

        for (RelDiasEventoCapacitacionDTO relDiasEventoCapacitacionDTO : listDTO) {
            relDiasEventoCapacitacionDTO.setFechaEventoFormat(dateFormat.format(relDiasEventoCapacitacionDTO.getFechaEventoCapacitacion()));
        }

        return listDTO;
    }

    @Override
    public void save(RelDiasEventoCapacitacionDTO eventoDiaDTO) {
        RelDiasEventoCapacitacion eventoDia = new RelDiasEventoCapacitacion();

        modelMapper.map(eventoDiaDTO, eventoDia);

        relDiasEventoCapacitacionRepo.save(eventoDia);
//GUSTAVO --guardarBitacora(eventoDiaDTO.getBitacoraDTO(), String.valueOf(eventoDia.getId()));
    }

    @Override
    public List<GroupByGestionEscolarDTO> obtenerDiasEventoAgrupadoPorEvento(List<Integer> idEventoCapacitacionList) {
        return relDiasEventoCapacitacionRepo.obtenerDiasEventoAgrupadoPorEvento(idEventoCapacitacionList);

    }

    @Override
    public void validarPersistencia(RelDiasEventoCapacitacionDTO dto, ResultadoDTO<RelDiasEventoCapacitacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelDiasEventoCapacitacionDTO dto, ResultadoDTO<RelDiasEventoCapacitacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelDiasEventoCapacitacionDTO dto, ResultadoDTO<RelDiasEventoCapacitacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
