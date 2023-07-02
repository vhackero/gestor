package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sso_elementos")
public class SsoElemento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String idPadreElemento;
	
	@Column
	private String idElemento;
	
	@Column
	private String nombreElemento;
	
	@Column
	private String tipoInformacion;
	
	@Column
	private String iDFuente;
	
	@Column
	private String fuente;
	
	@Column
	private String tipoFuente;
	
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;
	
	@Column(name = "usuario_modifico")
	private Long usuarioModifico;
	
	@ManyToOne
	@JoinColumn(name="id_persona")
	private TblPersona persona;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdPadreElemento() {
		return idPadreElemento;
	}

	public void setIdPadreElemento(String idPadreElemento) {
		this.idPadreElemento = idPadreElemento;
	}

	public String getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(String idElemento) {
		this.idElemento = idElemento;
	}

	public String getNombreElemento() {
		return nombreElemento;
	}

	public void setNombreElemento(String nombreElemento) {
		this.nombreElemento = nombreElemento;
	}

	public String getTipoInformacion() {
		return tipoInformacion;
	}

	public void setTipoInformacion(String tipoInformacion) {
		this.tipoInformacion = tipoInformacion;
	}

	public String getiDFuente() {
		return iDFuente;
	}

	public void setiDFuente(String iDFuente) {
		this.iDFuente = iDFuente;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getTipoFuente() {
		return tipoFuente;
	}

	public void setTipoFuente(String tipoFuente) {
		this.tipoFuente = tipoFuente;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblPersona getPersona() {
		return persona;
	}

	public void setPersona(TblPersona persona) {
		this.persona = persona;
	}
}
