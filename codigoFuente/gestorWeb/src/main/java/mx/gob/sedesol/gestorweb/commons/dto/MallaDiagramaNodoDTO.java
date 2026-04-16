package mx.gob.sedesol.gestorweb.commons.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MallaDiagramaNodoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String texto;
	private String tipo;
	private String tipoPrograma;
	private String tipoBackgroundColor;
	private String tipoBorderColor;
	private String estatus;
	private String estatusBackgroundColor;
	private String estatusBorderColor;
	private String calificacion;
	private Integer creditos;
	private boolean enCurso;
	private Integer semestre;
	private String textoCompacto;
	private boolean bloqueada;
	private String bloqueId;
	private boolean tieneDependencias;
	private String dependenciaTooltip;
	private String estatusIconClass;
	private int x;
	private int y;
	private int width;
	private int height;
	private List<String> incomingColors;
	private List<String> outgoingColors;

	public MallaDiagramaNodoDTO() {
	}

	public MallaDiagramaNodoDTO(String id, String texto, String tipo, int x, int y, int width, int height) {
		this.id = id;
		this.texto = texto;
		this.tipo = tipo;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.incomingColors = new ArrayList<>();
		this.outgoingColors = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoPrograma() {
		return tipoPrograma;
	}

	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}

	public String getTipoBackgroundColor() {
		return tipoBackgroundColor;
	}

	public void setTipoBackgroundColor(String tipoBackgroundColor) {
		this.tipoBackgroundColor = tipoBackgroundColor;
	}

	public String getTipoBorderColor() {
		return tipoBorderColor;
	}

	public void setTipoBorderColor(String tipoBorderColor) {
		this.tipoBorderColor = tipoBorderColor;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstatusBackgroundColor() {
		return estatusBackgroundColor;
	}

	public void setEstatusBackgroundColor(String estatusBackgroundColor) {
		this.estatusBackgroundColor = estatusBackgroundColor;
	}

	public String getEstatusBorderColor() {
		return estatusBorderColor;
	}

	public void setEstatusBorderColor(String estatusBorderColor) {
		this.estatusBorderColor = estatusBorderColor;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public boolean isEnCurso() {
		return enCurso;
	}

	public void setEnCurso(boolean enCurso) {
		this.enCurso = enCurso;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public String getTextoCompacto() {
		return textoCompacto;
	}

	public void setTextoCompacto(String textoCompacto) {
		this.textoCompacto = textoCompacto;
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public boolean isTieneDependencias() {
		return tieneDependencias;
	}

	public void setTieneDependencias(boolean tieneDependencias) {
		this.tieneDependencias = tieneDependencias;
	}

	public String getDependenciaTooltip() {
		return dependenciaTooltip;
	}

	public void setDependenciaTooltip(String dependenciaTooltip) {
		this.dependenciaTooltip = dependenciaTooltip;
	}

	public String getEstatusIconClass() {
		return estatusIconClass;
	}

	public void setEstatusIconClass(String estatusIconClass) {
		this.estatusIconClass = estatusIconClass;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<String> getIncomingColors() {
		return incomingColors;
	}

	public void setIncomingColors(List<String> incomingColors) {
		this.incomingColors = incomingColors;
	}

	public List<String> getOutgoingColors() {
		return outgoingColors;
	}

	public void setOutgoingColors(List<String> outgoingColors) {
		this.outgoingColors = outgoingColors;
	}

	public String getTipoCss() {
		return tipo != null ? tipo.toLowerCase() : "";
	}

	public String getTipoProgramaCss() {
		if (tipoPrograma == null) {
			return "";
		}
		String normalizado = tipoPrograma.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-");
		return "tipo-" + normalizado;
	}

	public String getTipoStyle() {
		if (tipoBorderColor == null) {
			return "";
		}
		return "border-color:" + tipoBorderColor + ";border-left-color:" + tipoBorderColor + ";";
	}

	public String getEnCursoCss() {
		return enCurso ? "en-curso" : "";
	}

	public String getBloqueadaCss() {
		return bloqueada ? "bloqueada" : "";
	}

	public String getEstatusCss() {
		if (estatus == null) {
			return "";
		}
		String normalizado = estatus.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-");
		return "estatus-" + normalizado;
	}

	public String getEstatusStyle() {
		if (estatusBackgroundColor == null || estatusBorderColor == null) {
			return "";
		}
		return "background:" + estatusBackgroundColor + ";border-color:" + estatusBorderColor + ";";
	}
}
