package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReporteadorParametroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idParametro;
	private Long idReporte;
	private String clave;
	private String etiqueta;
	private String consultaSql;
	private Integer orden;
	private Boolean activo = Boolean.TRUE;
	private List<ReporteadorOpcionDTO> opciones = new ArrayList<>();

	public Long getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}

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

	public String getClaveSinPrefijo() {
		if (clave == null) {
			return null;
		}
		String claveNormalizada = clave.trim();
		return claveNormalizada.startsWith(":") ? claveNormalizada.substring(1) : claveNormalizada;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getConsultaSql() {
		return consultaSql;
	}

	public void setConsultaSql(String consultaSql) {
		this.consultaSql = consultaSql;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<ReporteadorOpcionDTO> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<ReporteadorOpcionDTO> opciones) {
		this.opciones = opciones;
	}
}
