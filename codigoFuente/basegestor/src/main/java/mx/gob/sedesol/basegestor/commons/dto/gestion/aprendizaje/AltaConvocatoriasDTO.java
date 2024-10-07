package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import java.util.Date;

public class AltaConvocatoriasDTO implements Serializable{

	private static final long serialVersionUID = 1L;

    private String altaNombreConvocatoria;
	private String altaNombreCorto;
	private String altaDescripcion;
	private Date altaFechaApertura;
	private Date altaFechaCierre;
	private Integer altaNivelEducativo;
	private String altaUrl;
	private Integer altaEstatus;
	private Date altaFechaAlta;
	private Integer altaCupoLimite;
	public String getAltaNombreConvocatoria() {
		return altaNombreConvocatoria;
	}
	public void setAltaNombreConvocatoria(String altaNombreConvocatoria) {
		this.altaNombreConvocatoria = altaNombreConvocatoria;
	}
	public String getAltaNombreCorto() {
		return altaNombreCorto;
	}
	public void setAltaNombreCorto(String altaNombreCorto) {
		this.altaNombreCorto = altaNombreCorto;
	}
	public String getAltaDescripcion() {
		return altaDescripcion;
	}
	public void setAltaDescripcion(String altaDescripcion) {
		this.altaDescripcion = altaDescripcion;
	}
	public Date getAltaFechaApertura() {
		return altaFechaApertura;
	}
	public void setAltaFechaApertura(Date altaFechaApertura) {
		this.altaFechaApertura = altaFechaApertura;
	}
	public Date getAltaFechaCierre() {
		return altaFechaCierre;
	}
	public void setAltaFechaCierre(Date altaFechaCierre) {
		this.altaFechaCierre = altaFechaCierre;
	}
	public Integer getAltaNivelEducativo() {
		return altaNivelEducativo;
	}
	public void setAltaNivelEducativo(Integer altaNivelEducativo) {
		this.altaNivelEducativo = altaNivelEducativo;
	}
	public String getAltaUrl() {
		return altaUrl;
	}
	public void setAltaUrl(String altaUrl) {
		this.altaUrl = altaUrl;
	}
	public Integer getAltaEstatus() {
		return altaEstatus;
	}
	public void setAltaEstatus(Integer altaEstatus) {
		this.altaEstatus = altaEstatus;
	}
	public Date getAltaFechaAlta() {
		return altaFechaAlta;
	}
	public void setAltaFechaAlta(Date altaFechaAlta) {
		this.altaFechaAlta = altaFechaAlta;
	}
	public Integer getAltaCupoLimite() {
		return altaCupoLimite;
	}
	public void setAltaCupoLimite(Integer altaCupoLimite) {
		this.altaCupoLimite = altaCupoLimite;
	}	

}
