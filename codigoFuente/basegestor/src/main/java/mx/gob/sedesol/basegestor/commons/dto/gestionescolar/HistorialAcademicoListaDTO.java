package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;



import java.util.List;


public class HistorialAcademicoListaDTO {
	
	
	private Integer grupo_participante_id;
	private String cs;
	private String clavesep;
	private String clave;
	private String creditos;
	private String periodo;
	private String tipoEvaluacion;
	private String nActa;
	


	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public String getClavesep() {
		return clavesep;
	}
	public void setClavesep(String clavesep) {
		this.clavesep = clavesep;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getCreditos() {
		return creditos;
	}
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getTipoEvaluacion() {
		return tipoEvaluacion;
	}
	public void setTipoEvaluacion(String tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}
	public String getnActa() {
		return nActa;
	}
	public void setnActa(String nActa) {
		this.nActa = nActa;
	}
	public Integer getGrupo_participante_id() {
		return grupo_participante_id;
	}
	public void setGrupo_participante_id(Integer grupo_participante_id) {
		this.grupo_participante_id = grupo_participante_id;
	}
}
