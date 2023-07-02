package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;

public interface CommonService<A,B> {
	
	List<A> findAll();
	
	A buscarPorId(B id);

	ResultadoDTO<A> guardar(A dto);
	
	ResultadoDTO<A> actualizar(A dto);
	
	ResultadoDTO<A> eliminar(A dto);
	
	ResultadoDTO<A> sonDatosRequeridosValidos(TipoAccion accion, A dto);
}
