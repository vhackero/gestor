package mx.gob.sedesol.basegestor.service.cronscheduler;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.CronConfDTO;
import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.ResultadoRespuestaCronDTO;


public interface ConfiguracionCronService {
	List<CronConfDTO> obtenerTareasProgramadas();
	ResultadoDTO<CronConfDTO> guardar(CronConfDTO dto);
	ResultadoDTO<CronConfDTO> actualizar(CronConfDTO dto);
	CronConfDTO obtenerCronProClave(String clave);
	ResultadoRespuestaCronDTO actualizaPatronCronAvaWs(String claveCron);
}


