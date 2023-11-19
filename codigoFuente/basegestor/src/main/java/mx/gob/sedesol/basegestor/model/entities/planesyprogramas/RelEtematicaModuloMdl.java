package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_etematica_modulo_mdl database table.
 * 
 */
@Entity
@Table(name="rel_etematica_modulo_mdl")
@IdClass(RelEtematicaModuloMdlPK.class)
public class RelEtematicaModuloMdl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_estructura_tematica")
	private Integer idEstructuraTematica;

	@Id
	@Column(name="id_modulo_mdl")
	private Integer idModuloMdl;

	
	@Column(name="activo")
	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	@ManyToOne
	@JoinColumn(name="id_estructura_tematica",insertable=false, updatable=false)
	private TblEstructuraTematica tblEstructuraTematica;
//
//	@ManyToOne
//	@JoinColumn(name="id_modulo_mdl",insertable=false, updatable=false)
//	private MdlModulo mdlModulo;

	public RelEtematicaModuloMdl() {
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

//	public TblEstructuraTematica getTblEstructuraTematica() {
//		return this.tblEstructuraTematica;
//	}
//
//	public void setTblEstructuraTematica(TblEstructuraTematica tblEstructuraTematica) {
//		this.tblEstructuraTematica = tblEstructuraTematica;
//	}
//
//	public MdlModulo getMdlModulo() {
//		return this.mdlModulo;
//	}
//
//	public void setMdlModulo(MdlModulo mdlModulo) {
//		this.mdlModulo = mdlModulo;
//	}

	/**
	 * @return the idEstructuraTematica
	 */
	public Integer getIdEstructuraTematica() {
		return idEstructuraTematica;
	}

	/**
	 * @param idEstructuraTematica the idEstructuraTematica to set
	 */
	public void setIdEstructuraTematica(Integer idEstructuraTematica) {
		this.idEstructuraTematica = idEstructuraTematica;
	}

	/**
	 * @return the idModuloMdl
	 */
	public Integer getIdModuloMdl() {
		return idModuloMdl;
	}

	/**
	 * @param idModuloMdl the idModuloMdl to set
	 */
	public void setIdModuloMdl(Integer idModuloMdl) {
		this.idModuloMdl = idModuloMdl;
	}

}