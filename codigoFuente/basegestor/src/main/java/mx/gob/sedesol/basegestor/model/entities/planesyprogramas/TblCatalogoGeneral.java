package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_catalogos_generales database table.
 * 
 */
@Entity
@Table(name="tbl_catalogos_generales")
@NamedQuery(name="TblCatalogoGeneral.findAll", query="SELECT t FROM TblCatalogoGeneral t")
public class TblCatalogoGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cat_general")
	private Integer idCatGeneral;
	
	@Column(name="clave_catalogo")
	private String claveCatalogo;
	
	@Column(name="activo")
	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelCatalogoGeneralValore
	@OneToMany(mappedBy="catalogoGeneral", fetch=FetchType.LAZY)
	private List<RelCatalogoGeneralValor> datosValorCatGeneral;

	public TblCatalogoGeneral() {
	}

	
	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getClaveCatalogo() {
		return this.claveCatalogo;
	}

	public void setClaveCatalogo(String claveCatalogo) {
		this.claveCatalogo = claveCatalogo;
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

	/**
	 * @return the datosValorCatGeneral
	 */
	public List<RelCatalogoGeneralValor> getDatosValorCatGeneral() {
		return datosValorCatGeneral;
	}

	/**
	 * @param datosValorCatGeneral the datosValorCatGeneral to set
	 */
	public void setDatosValorCatGeneral(List<RelCatalogoGeneralValor> datosValorCatGeneral) {
		this.datosValorCatGeneral = datosValorCatGeneral;
	}


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

}