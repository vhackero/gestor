package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the rel_catalogo_general_valores database table.
 * 
 */
@Entity
@IdClass(RelCatalogoGeneralValorPK.class)
@Table(name="rel_catalogo_general_valores")
@NamedQuery(name="RelCatalogoGeneralValor.findAll", query="SELECT r FROM RelCatalogoGeneralValor r")
public class RelCatalogoGeneralValor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="id_cat_general")
	private Integer idCatGeneral;
	
	@Id
	@Column(name="etiqueta")
	private String etiqueta;
	
	@Column(name="activo")
	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="orden")
	private Integer orden;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Column(name="valor")
	private String valor;
	
	@ManyToOne
	@JoinColumn(name="id_cat_general", insertable=false, updatable=false)
	private TblCatalogoGeneral catalogoGeneral;

	public RelCatalogoGeneralValor() {
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the catalogoGeneral
	 */
	public TblCatalogoGeneral getCatalogoGeneral() {
		return catalogoGeneral;
	}

	/**
	 * @param catalogoGeneral the catalogoGeneral to set
	 */
	public void setCatalogoGeneral(TblCatalogoGeneral catalogoGeneral) {
		this.catalogoGeneral = catalogoGeneral;
	}

}

class RelCatalogoGeneralValorPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idCatGeneral;
	private String etiqueta;
	/**
	 * @return the idCatGeneral
	 */
	public Integer getIdCatGeneral() {
		return idCatGeneral;
	}
	/**
	 * @param idCatGeneral the idCatGeneral to set
	 */
	public void setIdCatGeneral(Integer idCatGeneral) {
		this.idCatGeneral = idCatGeneral;
	}
	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}
	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etiqueta == null) ? 0 : etiqueta.hashCode());
		result = prime * result + ((idCatGeneral == null) ? 0 : idCatGeneral.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelCatalogoGeneralValorPK other = (RelCatalogoGeneralValorPK) obj;
		if (etiqueta == null) {
			if (other.etiqueta != null)
				return false;
		} else if (!etiqueta.equals(other.etiqueta))
			return false;
		if (idCatGeneral == null) {
			if (other.idCatGeneral != null)
				return false;
		} else if (!idCatGeneral.equals(other.idCatGeneral))
			return false;
		return true;
	}
}

