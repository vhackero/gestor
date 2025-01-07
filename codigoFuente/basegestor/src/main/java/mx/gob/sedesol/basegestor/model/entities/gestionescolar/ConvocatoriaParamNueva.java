package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ConvocatoriaParamNueva implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String altaNombreConvocatoria;
	private String altaNombreCorto;
	private String altaDescripcion;
	private Date altaFechaApertura;
	private Date altaFechaCierre;
	//private String altaNivelEducativo;
	private List<Modalidad> listaNivelEducativoCompl;
	List<ConvocatoriaNivelEducativoCompl> listaPlanPrograma;
	List<ConvocatoriaNivelEducativoCompl> listaPlanProgramaNivel;
	private String altaUrl;
	private String altaEstatus;
	private Date altaFechaAlta;
	private String altaCupoLimite;
	
	public List<Modalidad> getListaNivelEducativoCompl() {
	    return listaNivelEducativoCompl;
	}

	public List<ConvocatoriaNivelEducativoCompl> getListaPlanProgramaNivel() {
		return listaPlanProgramaNivel;
	}

	public void setListaPlanProgramaNivel(List<ConvocatoriaNivelEducativoCompl> listaPlanProgramaNivel) {
		this.listaPlanProgramaNivel = listaPlanProgramaNivel;
	}

	public void setListaNivelEducativoCompl(List<Modalidad> listaNivelEducativoCompl) {
	    this.listaNivelEducativoCompl = listaNivelEducativoCompl;
	}

	
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
	
	
	public String getAltaUrl() {
		return altaUrl;
	}
	public void setAltaUrl(String altaUrl) {
		this.altaUrl = altaUrl;
	}
	public String getAltaEstatus() {
		return altaEstatus;
	}
	public void setAltaEstatus(String altaEstatus) {
		this.altaEstatus = altaEstatus;
	}
	
	public String getAltaCupoLimite() {
		return altaCupoLimite;
	}
	public void setAltaCupoLimite(String altaCupoLimite) {
		this.altaCupoLimite = altaCupoLimite;
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
	public Date getAltaFechaAlta() {
		return altaFechaAlta;
	}
	public void setAltaFechaAlta(Date altaFechaAlta) {
		this.altaFechaAlta = altaFechaAlta;
	}
	
	public List<ConvocatoriaNivelEducativoCompl> getListaPlanPrograma() {
		return listaPlanPrograma;
	}

	public void setListaPlanPrograma(List<ConvocatoriaNivelEducativoCompl> listaPlanPrograma) {
		this.listaPlanPrograma = listaPlanPrograma;
	}
//	public String getAltaNivelEducativo() {
//		return altaNivelEducativo;
//	}
//	public void setAltaNivelEducativo(String altaNivelEducativo) {
//		this.altaNivelEducativo = altaNivelEducativo;
//	}
	

}
