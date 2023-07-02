package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelSolicitudEnrolamientoEvtCapDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelSolicitudEnrolamientoEvtCap;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelSolicitudEnrolEvtCapRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelSolicitudEnrolEvtCapService;

@Service("relSolicitudEnrolEvtCapService")
public class RelSolicitudEnrolEvtCapServiceImpl 
        extends ComunValidacionService<RelSolicitudEnrolamientoEvtCapDTO>
        implements RelSolicitudEnrolEvtCapService {

    @Autowired
    private RelSolicitudEnrolEvtCapRepo relSolicitudEnrolEvtCapRepo;

    private ModelMapper mapper = new ModelMapper();

    private static final Logger logger = Logger.getLogger(RelSolicitudEnrolEvtCapServiceImpl.class);

    public List<EventoCapacitacionDTO> obtenerSolicitudesEnrolamientoPorIdPersona(Long idPersona, Boolean esActivo) {

        List<TblEvento> tbleventoList = relSolicitudEnrolEvtCapRepo
                .obtenerSolicitudesEnrolamientoPorIdPersona(idPersona, esActivo);

        Type objetoDTO = new TypeToken<List<EventoCapacitacionDTO>>() {
        }.getType();

        return mapper.map(tbleventoList, objetoDTO);

    }

    @Override
    public List<EventoCapacitacionDTO> obtenerSolicitudesPorIdPersonaPrograma(Long idPersona, Boolean esActivo,
            Integer idPrograma) {
        List<TblEvento> tbleventoList = relSolicitudEnrolEvtCapRepo.obtenerSolicitudesPorIdPersonaPrograma(idPersona,
                esActivo, idPrograma);

        Type objetoDTO = new TypeToken<List<EventoCapacitacionDTO>>() {
        }.getType();

        return mapper.map(tbleventoList, objetoDTO);
    }

    @Override
    public ResultadoDTO<RelSolicitudEnrolamientoEvtCapDTO> crearSolicitud(RelSolicitudEnrolamientoEvtCapDTO solicitud) {
        ResultadoDTO<RelSolicitudEnrolamientoEvtCapDTO> resultado = new ResultadoDTO<>();
        RelSolicitudEnrolamientoEvtCap entidadSolicitud = mapper.map(solicitud, RelSolicitudEnrolamientoEvtCap.class);
        relSolicitudEnrolEvtCapRepo.save(entidadSolicitud);
//GUSTAVO --guardarBitacora(solicitud.getBitacoraDTO(), String.valueOf(entidadSolicitud.getIdEventoCapacitacion()));
        resultado.setDto(mapper.map(entidadSolicitud, RelSolicitudEnrolamientoEvtCapDTO.class));
        return resultado;
    }

    @Override
    public void validarPersistencia(RelSolicitudEnrolamientoEvtCapDTO dto, ResultadoDTO<RelSolicitudEnrolamientoEvtCapDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelSolicitudEnrolamientoEvtCapDTO dto, ResultadoDTO<RelSolicitudEnrolamientoEvtCapDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelSolicitudEnrolamientoEvtCapDTO dto, ResultadoDTO<RelSolicitudEnrolamientoEvtCapDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
