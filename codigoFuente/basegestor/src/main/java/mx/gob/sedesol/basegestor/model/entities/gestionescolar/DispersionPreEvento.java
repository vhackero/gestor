package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

public class DispersionPreEvento implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombrePlan;
	private String nombrePrograma;
	private String clavePrograma;
	private String claveParaEvento;
	private String claveParaGrupo;
	private String bloque;
	private String objetivosGenerales;
	private String perfilEgreso;
	private String requisitosIngreso;
	private String calificacionMinAprobatoria;

	public String getNombrePlan() {
		return nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public String getClavePrograma() {
		return clavePrograma;
	}

	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
	}

	public String getClaveParaEvento() {
		return claveParaEvento;
	}

	public void setClaveParaEvento(String claveParaEvento) {
		this.claveParaEvento = claveParaEvento;
	}

	public String getClaveParaGrupo() {
		return claveParaGrupo;
	}

	public void setClaveParaGrupo(String claveParaGrupo) {
		this.claveParaGrupo = claveParaGrupo;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getObjetivosGenerales() {
		return objetivosGenerales;
	}

	public void setObjetivosGenerales(String objetivosGenerales) {
		this.objetivosGenerales = objetivosGenerales;
	}

	public String getPerfilEgreso() {
		return perfilEgreso;
	}

	public void setPerfilEgreso(String perfilEgreso) {
		this.perfilEgreso = perfilEgreso;
	}

	public String getRequisitosIngreso() {
		return requisitosIngreso;
	}

	public void setRequisitosIngreso(String requisitosIngreso) {
		this.requisitosIngreso = requisitosIngreso;
	}

	public String getCalificacionMinAprobatoria() {
		return calificacionMinAprobatoria;
	}

	public void setCalificacionMinAprobatoria(String calificacionMinAprobatoria) {
		this.calificacionMinAprobatoria = calificacionMinAprobatoria;
	}

}
