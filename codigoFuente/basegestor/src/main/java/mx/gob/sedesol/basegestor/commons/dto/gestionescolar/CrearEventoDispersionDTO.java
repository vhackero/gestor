package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class CrearEventoDispersionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idDispersion;
	private Integer idPrograma;
	private Integer idPlan;
	private Integer tipoMatriculacion;
	private Integer gruposGenerales;
	private Integer gruposResto;
	private Integer cupoGeneral;
	private Integer cupoResto;
	private Integer noEstudiantes;
	private Integer idEstatusEvento;
	private String nombreEventoBase;
	private Boolean privado;
	private Date fechaInicial;
	private Date fechaFinal;
	private Integer idModalidad;
	private String anioPeriodo;
	private String numeroElementos;
	private Boolean generarPorClave;
	 private String nombreGrupoBase;
	private Boolean vincularAva;
	private Integer idPlataformaLms;
	private Integer idClasificacionAva;
	private Boolean eventoAutonomo;
	private String claveParaEvento;
	private String claveParaGrupo;
	private String nombrePrograma;
	private String objetivosGenerales;
	private String perfilEgreso;
	private String requisitosIngreso;
	private String calificacionMinAprobatoria;
	private Long idUsuario;
	private String bloquePrograma;

	public Integer getIdDispersion() {
		return idDispersion;
	}

	public void setIdDispersion(Integer idDispersion) {
		this.idDispersion = idDispersion;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public Integer getTipoMatriculacion() {
		return tipoMatriculacion;
	}

	public void setTipoMatriculacion(Integer tipoMatriculacion) {
		this.tipoMatriculacion = tipoMatriculacion;
	}

	public Integer getGruposGenerales() {
		return gruposGenerales;
	}

	public void setGruposGenerales(Integer gruposGenerales) {
		this.gruposGenerales = gruposGenerales;
	}

	public Integer getGruposResto() {
		return gruposResto;
	}

	public void setGruposResto(Integer gruposResto) {
		this.gruposResto = gruposResto;
	}

	public Integer getCupoGeneral() {
		return cupoGeneral;
	}

	public void setCupoGeneral(Integer cupoGeneral) {
		this.cupoGeneral = cupoGeneral;
	}

	public Integer getCupoResto() {
		return cupoResto;
	}

	public void setCupoResto(Integer cupoResto) {
		this.cupoResto = cupoResto;
	}

	public Integer getNoEstudiantes() {
		return noEstudiantes;
	}

	public void setNoEstudiantes(Integer noEstudiantes) {
		this.noEstudiantes = noEstudiantes;
	}

	public Integer getIdEstatusEvento() {
		return idEstatusEvento;
	}

	public void setIdEstatusEvento(Integer idEstatusEvento) {
		this.idEstatusEvento = idEstatusEvento;
	}

	public String getNombreEventoBase() {
		return nombreEventoBase;
	}

	public void setNombreEventoBase(String nombreEventoBase) {
		this.nombreEventoBase = nombreEventoBase;
	}

	public Boolean getPrivado() {
		return privado;
	}

	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Integer getIdModalidad() {
		return idModalidad;
	}

	public void setIdModalidad(Integer idModalidad) {
		this.idModalidad = idModalidad;
	}

	public String getAnioPeriodo() {
		return anioPeriodo;
	}

	public void setAnioPeriodo(String anioPeriodo) {
		this.anioPeriodo = anioPeriodo;
	}

	public String getNumeroElementos() {
		return numeroElementos;
	}

	public void setNumeroElementos(String numeroElementos) {
		this.numeroElementos = numeroElementos;
	}

	public Boolean getGenerarPorClave() {
		return generarPorClave;
	}

	public void setGenerarPorClave(Boolean generarPorClave) {
		this.generarPorClave = generarPorClave;
	}

	public String getNombreGrupoBase() {
		return nombreGrupoBase;
	}

	public void setNombreGrupoBase(String nombreGrupoBase) {
		this.nombreGrupoBase = nombreGrupoBase;
	}

	public Boolean getVincularAva() {
		return vincularAva;
	}

	public void setVincularAva(Boolean vincularAva) {
		this.vincularAva = vincularAva;
	}

	public Integer getIdPlataformaLms() {
		return idPlataformaLms;
	}

	public void setIdPlataformaLms(Integer idPlataformaLms) {
		this.idPlataformaLms = idPlataformaLms;
	}

	public Integer getIdClasificacionAva() {
		return idClasificacionAva;
	}

	public void setIdClasificacionAva(Integer idClasificacionAva) {
		this.idClasificacionAva = idClasificacionAva;
	}

	public Boolean getEventoAutonomo() {
		return eventoAutonomo;
	}

	public void setEventoAutonomo(Boolean eventoAutonomo) {
		this.eventoAutonomo = eventoAutonomo;
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

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getBloquePrograma() {
		return bloquePrograma;
	}
	
	public void setBloquePrograma(String bloquePrograma) {
		this.bloquePrograma = bloquePrograma;
	}

}
