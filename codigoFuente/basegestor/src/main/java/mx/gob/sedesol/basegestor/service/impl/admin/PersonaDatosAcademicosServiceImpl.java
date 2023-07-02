package mx.gob.sedesol.basegestor.service.impl.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaDatosAcademico;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaDatosAcademicosRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaDatosAcademicosService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.EjeCompetenciaServiceImpl;

@Service("personaDatosAcademicosService")
public class PersonaDatosAcademicosServiceImpl extends ComunValidacionService<PersonaDatosAcademicoDTO> implements PersonaDatosAcademicosService {

    private static final Logger logger = Logger.getLogger(EjeCompetenciaServiceImpl.class);
    @Autowired
    private PersonaDatosAcademicosRepo personaDatosAcademicosRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<PersonaDatosAcademicoDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersonaDatosAcademicoDTO buscarPorId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<PersonaDatosAcademicoDTO> guardar(PersonaDatosAcademicoDTO dto) {
        ResultadoDTO<PersonaDatosAcademicoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelPersonaDatosAcademico datAca = mapper.map(dto, RelPersonaDatosAcademico.class);
                datAca = personaDatosAcademicosRepo.save(datAca);

                res = new ResultadoDTO<PersonaDatosAcademicoDTO>();
                res.setDto(mapper.map(datAca, PersonaDatosAcademicoDTO.class));

            //GUSTAVO --guardarBitacoraPersonaDatos(dto, String.valueOf(datAca));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }
        return res;
    }

    @Override
    public ResultadoDTO<PersonaDatosAcademicoDTO> actualizar(PersonaDatosAcademicoDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<PersonaDatosAcademicoDTO> eliminar(PersonaDatosAcademicoDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoDTO<PersonaDatosAcademicoDTO> sonDatosRequeridosValidos(TipoAccion accion,
            PersonaDatosAcademicoDTO dto) {

        ResultadoDTO<PersonaDatosAcademicoDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<PersonaDatosAcademicoDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getIdDatosAcademicos())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EJE_COMPETENCIA_ID_COMP_ESPECIFICA_REQ);
                    }

                    if (ObjectUtils.isNullOrEmpty(dto.getIdPersona())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EJE_COMPETENCIA_ID_MALLA_CURR_REQ);
                    }
                    break;
            }
        }
        return resultado;
    }

    public PersonaDatosAcademicosRepo getPersonaDatosAcademicosRepo() {
        return personaDatosAcademicosRepo;
    }

    public void setPersonaDatosAcademicosRepo(PersonaDatosAcademicosRepo personaDatosAcademicosRepo) {
        this.personaDatosAcademicosRepo = personaDatosAcademicosRepo;
    }

    @Override
    public void validarPersistencia(PersonaDatosAcademicoDTO dto, ResultadoDTO<PersonaDatosAcademicoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(PersonaDatosAcademicoDTO dto, ResultadoDTO<PersonaDatosAcademicoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(PersonaDatosAcademicoDTO dto, ResultadoDTO<PersonaDatosAcademicoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
