package mx.gob.sedesol.basegestor.service.analisisdatos;

import java.util.List;
import java.util.Map;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorOpcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorParametroDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorReporteDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorResultadoDTO;

public interface ReporteadorService {

	List<ReporteadorReporteDTO> obtenerReportesActivos();

	List<ReporteadorReporteDTO> obtenerReportes();

	List<ReporteadorParametroDTO> obtenerParametrosPorReporte(Long idReporte);

	List<ReporteadorParametroDTO> obtenerParametrosPorReporteAdmin(Long idReporte);

	List<ReporteadorOpcionDTO> obtenerOpcionesParametro(ReporteadorParametroDTO parametro);

	ReporteadorResultadoDTO ejecutarReporte(ReporteadorReporteDTO reporte, Map<String, String> valoresParametros);

	ReporteadorReporteDTO guardarReporte(ReporteadorReporteDTO reporte, Long usuarioModifico);

	void eliminarReporte(Long idReporte);

	ReporteadorParametroDTO guardarParametro(ReporteadorParametroDTO parametro, Long usuarioModifico);

	void eliminarParametro(Long idParametro);
}
