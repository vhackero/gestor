package mx.gob.sedesol.gestorweb.commons.dto;

import java.io.Serializable;

public class MallaDiagramaElementoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipo;
	private String texto;
	private Integer idPrograma;

	public MallaDiagramaElementoDTO() {
	}

	public MallaDiagramaElementoDTO(String tipo, String texto) {
		this.tipo = tipo;
		this.texto = texto;
	}

	public MallaDiagramaElementoDTO(String tipo, String texto, Integer idPrograma) {
		this.tipo = tipo;
		this.texto = texto;
		this.idPrograma = idPrograma;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getTipoCss() {
		return tipo != null ? tipo.toLowerCase() : "";
	}

	@Override
	public String toString() {
		return texto != null ? texto : "";
	}
}
