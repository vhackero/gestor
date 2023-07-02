package mx.gob.sedesol.gestorweb.commons.dto;

import java.util.ArrayList;
import java.util.List;

public class Elemento {

	private String clave;
	private String nombreElemento;
	private TipoElementoEnum tipoElemento;
	private List<Elemento> subElementos;

	public Elemento() {
	}
	
	public Elemento(String clave, String nombreElemento, TipoElementoEnum tipoElemento) {
		this.clave = clave;
		this.nombreElemento = nombreElemento;
		this.tipoElemento = tipoElemento;
		this.subElementos = new ArrayList<>();
	}

	public String getNombreElemento() {
		return nombreElemento;
	}

	public void setNombreElemento(String nombreElemento) {
		this.nombreElemento = nombreElemento;
	}

	public TipoElementoEnum getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(TipoElementoEnum tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}


	public List<Elemento> getSubElementos() {
		return subElementos;
	}


	public void setSubElementos(List<Elemento> subElementos) {
		this.subElementos = subElementos;
	}

}
