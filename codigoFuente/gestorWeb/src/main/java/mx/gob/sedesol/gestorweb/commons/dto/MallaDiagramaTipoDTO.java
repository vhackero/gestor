package mx.gob.sedesol.gestorweb.commons.dto;

import java.io.Serializable;

public class MallaDiagramaTipoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String color;
	private String backgroundColor;
	private String borderColor;
	private String iconClass;

	public MallaDiagramaTipoDTO() {
	}

	public MallaDiagramaTipoDTO(String nombre, String color, String backgroundColor, String borderColor) {
		this.nombre = nombre;
		this.color = color;
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
	}

	public MallaDiagramaTipoDTO(String nombre, String color, String backgroundColor, String borderColor, String iconClass) {
		this.nombre = nombre;
		this.color = color;
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
		this.iconClass = iconClass;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
}
