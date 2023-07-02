package mx.gob.sedesol.basegestor.service.impl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.LoteUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.RelLoteUsuario;
import mx.gob.sedesol.basegestor.model.repositories.admin.LoteUsuarioRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.LoteUsuarioService;
import mx.gob.sedesol.basegestor.service.admin.UsuarioDatosLaboralesService;

@Service("loteUsuarioService")
public class LoteUsuarioServiceImpl extends ComunValidacionService<LoteUsuarioDTO> implements LoteUsuarioService {
	
	private static final Logger logger = Logger.getLogger(LoteUsuarioServiceImpl.class);
	
	@Autowired
	private LoteUsuarioRepo loteUsuarioRepo;
	
	@Autowired
	private UsuarioDatosLaboralesService usuarioDatosLaboralesService;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public ResultadoDTO<LoteUsuarioDTO> guardar(LoteUsuarioDTO loteUsuario) {
		ResultadoDTO<LoteUsuarioDTO> resultado = new ResultadoDTO<>();
		
		try {
			RelLoteUsuario entidad = modelMapper.map(loteUsuario, RelLoteUsuario.class);
			loteUsuarioRepo.save(entidad);
			resultado.setDto(modelMapper.map(entidad, LoteUsuarioDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
                        //GUSTAVO --guardarBitacoraLote(loteUsuario, String.valueOf(entidad.getIdLote()));
		} catch (Exception e) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
			logger.error(e.getMessage(), e);
		}
		return resultado;
	}
	
	@Override
	public List<UsuarioDatosLaboralesDTO> obtenerUsuarioPorLote(Integer idLote) {
		List<RelLoteUsuario> usuarios = loteUsuarioRepo.usuariosPorLote(idLote);
		List<UsuarioDatosLaboralesDTO> personas = new ArrayList<>();
		for (RelLoteUsuario usuario : usuarios) {
			personas.add(usuarioDatosLaboralesService.obtenerDatosLaboralesPorPersona(usuario.getIdPersona()));
		}
		return personas;
	}
        
        private void guardarBitacoraLote(LoteUsuarioDTO dto, String registro) {
        //Usar la clave/id del registro guardado
        	//GUSTAVO --dto.getBitacoraDTO().setRegistro(registro);
        //Guarda la bitácora
//GUSTAVO --guardarBitacora(dto.getBitacoraDTO());
    }

    @Override
    public void validarPersistencia(LoteUsuarioDTO dto, ResultadoDTO<LoteUsuarioDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(LoteUsuarioDTO dto, ResultadoDTO<LoteUsuarioDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(LoteUsuarioDTO dto, ResultadoDTO<LoteUsuarioDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
