package mx.gob.sedesol.gestorweb.commons.dto;

import java.io.Serializable;

public class MallaDiagramaConexionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private String path;
	private String color;
	private String markerId;

	public MallaDiagramaConexionDTO() {
	}

	public MallaDiagramaConexionDTO(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.path = buildPath(x1, y1, x2, y2);
	}

	public MallaDiagramaConexionDTO(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x4;
		this.y2 = y4;
		this.path = "M " + x1 + " " + y1
				+ " L " + x2 + " " + y2
				+ " L " + x3 + " " + y3
				+ " L " + x4 + " " + y4;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMarkerId() {
		return markerId;
	}

	public void setMarkerId(String markerId) {
		this.markerId = markerId;
	}

	private String buildPath(int x1, int y1, int x2, int y2) {
		int midX = x1 + ((x2 - x1) / 2);
		return "M " + x1 + " " + y1 + " L " + midX + " " + y1 + " L " + midX + " " + y2 + " L " + x2 + " " + y2;
	}
}
