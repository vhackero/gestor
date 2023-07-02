package mx.gob.sedesol.basegestor.service.impl.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.utils.CorreoUtil;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;

@Service("correoElectronicoService")
public class CorreoElectronicoServiceImpl implements CorreoElectronicoService {
	
	private static final long serialVersionUID = 3814346919648321025L;

	private static final Logger logger = Logger.getLogger(CorreoElectronicoServiceImpl.class);
	
	@Autowired
	private transient ParametroSistemaService parametroSistemaService;

	@Override
	public CorreoDTO asignaParametrosConfigCorreo() {
		
		CorreoDTO correoDto = new CorreoDTO();
		
		correoDto.setHost(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_HOST.getClave()));
		correoDto.setPort(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_PUERTO.getClave()));
		correoDto.setUsuarioCorreo(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
		correoDto.setPasswordCorreo(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_PASSWORD.getClave()));
		
		return correoDto;
	}

	
	@Override
	public boolean enviaCorreoElectronico(CorreoDTO correoDTO) {
		boolean bnd = Boolean.FALSE;
		try{
			 CorreoUtil.sendMail(correoDTO);
			 bnd = Boolean.TRUE;
		}catch (Exception e) {
			bnd = Boolean.FALSE;
			logger.error(e.getMessage(),e);
		}
		return bnd;
	}
	
	

}
