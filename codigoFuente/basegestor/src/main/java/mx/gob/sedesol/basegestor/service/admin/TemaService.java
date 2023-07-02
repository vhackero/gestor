package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TemaDTO;

public interface TemaService extends CommonService<TemaDTO, Integer> {
	
	List<TemaDTO> buscarPorTipo(Integer tipoTema);
	
	ResultadoDTO<TemaDTO> activarTema(TemaDTO tema);
	
	String obtenerTemaActivo(int tipo);
	
	boolean validarTema(TemaDTO tema);

}
