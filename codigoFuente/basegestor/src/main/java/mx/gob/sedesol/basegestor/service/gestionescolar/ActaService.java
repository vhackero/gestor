package mx.gob.sedesol.basegestor.service.gestionescolar;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 *  ACTAS
 * @author ITTIVA
 * 
 */
public interface ActaService extends CommonService<ActaDTO, Integer>{
	
//	public void cargaActa(Acta acta);
	public ResultadoDTO<ActaDTO> guardar(ActaDTO acta);
	public ResultadoDTO<ActaDTO> eliminar(ActaDTO dto);
	public ActaDTO getActaByGrupoUser(int idGrupo, long idUser);
	public ActaDTO getActaByIdGrupo(int idGrupo) ;

}
