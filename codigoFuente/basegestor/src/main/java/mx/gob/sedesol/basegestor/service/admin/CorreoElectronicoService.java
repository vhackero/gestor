package mx.gob.sedesol.basegestor.service.admin;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;

public interface CorreoElectronicoService extends Serializable {

	public CorreoDTO asignaParametrosConfigCorreo();
	
	public boolean enviaCorreoElectronico(CorreoDTO correoDTO);
}
