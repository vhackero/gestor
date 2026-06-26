package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReporteadorReporteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idReporte;
	private String clave;
	private String nombre;
	private String consultaSql;
	private List<ReporteadorParametroDTO> parametros = new ArrayList<>();

	public Long getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Long idReporte) {
		this.idReporte = idReporte;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getConsultaSql() {
		return consultaSql;
	}

	public void setConsultaSql(String consultaSql) {
		this.consultaSql = consultaSql;
	}

	public List<ReporteadorParametroDTO> getParametros() {
		return parametros;
	}

	public void setParametros(List<ReporteadorParametroDTO> parametros) {
		this.parametros = parametros;
	}
}
