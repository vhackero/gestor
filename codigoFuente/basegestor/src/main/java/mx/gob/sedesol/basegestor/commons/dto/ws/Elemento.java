package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class Elemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idPadreElemento;
	private String idElemento;
	private String nombreElemento;
	private String tipoInformacion;
	private String iDFuente;
	private String fuente;
	private String tipoFuente;

	public String getIdPadreElemento() {
		return idPadreElemento;
	}

	public void setIdPadreElemento(String idPadreElemento) {
		this.idPadreElemento = idPadreElemento;
	}

	public String getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(String idElemento) {
		this.idElemento = idElemento;
	}

	public String getNombreElemento() {
		return nombreElemento;
	}

	public void setNombreElemento(String nombreElemento) {
		this.nombreElemento = nombreElemento;
	}

	public String getTipoInformacion() {
		return tipoInformacion;
	}

	public void setTipoInformacion(String tipoInformacion) {
		this.tipoInformacion = tipoInformacion;
	}

	public String getiDFuente() {
		return iDFuente;
	}

	public void setiDFuente(String iDFuente) {
		this.iDFuente = iDFuente;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getTipoFuente() {
		return tipoFuente;
	}

	public void setTipoFuente(String tipoFuente) {
		this.tipoFuente = tipoFuente;
	}

}
