package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the tbl_datos_sociodemograficos_persona database table.
 * 
 */
@Entity
@Table(name="tbl_datos_sociodemograficos_persona")
@NamedQuery(name="TblDatosSociodemograficosPersona.findAll", query="SELECT t FROM TblDatosSociodemograficosPersona t")
public class TblDatosSociodemograficosPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_dato_sociodemografico_persona")
	private Long idDatoSociodemograficoPersona;

	@Column(name="fecha_modificacion", insertable = false, updatable=false)
	private Timestamp fechaModificacion;

	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Timestamp fechaRegistro;
	
	@Column(name="lengua_indigena")
	private Boolean lenguaIndigena;
	
	@Column(name="tiene_discapacidad")
	private Boolean tieneDiscapacidad;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;
	
	//bi-directional many-to-one association to TblPersona
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_persona")
	private TblPersona persona;
		
	//bi-directional many-to-one association to CatLenguajeIndigena
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_lenguaje")
	private CatLenguajeIndigena lenguajeIndigena;
		
	//bi-directional many-to-one association to CatTipoDiscapacidad
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_discapacidad")
	private CatTipoDiscapacidad tipoDiscapacidad;

	public TblDatosSociodemograficosPersona() {
	}

	public Long getIdDatoSociodemograficoPersona() {
		return idDatoSociodemograficoPersona;
	}

	public void setIdDatoSociodemograficoPersona(Long idDatoSociodemograficoPersona) {
		this.idDatoSociodemograficoPersona = idDatoSociodemograficoPersona;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}
 
	
	public Boolean getLenguaIndigena() {
		return lenguaIndigena;
	}

	public void setLenguaIndigena(Boolean lenguaIndigena) {
		this.lenguaIndigena = lenguaIndigena;
	}

	public Boolean getTieneDiscapacidad() {
		return tieneDiscapacidad;
	}

	public void setTieneDiscapacidad(Boolean tieneDiscapacidad) {
		this.tieneDiscapacidad = tieneDiscapacidad;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblPersona getPersona() {
		return persona;
	}

	public void setPersona(TblPersona persona) {
		this.persona = persona;
	}

	public CatLenguajeIndigena getLenguajeIndigena() {
		return lenguajeIndigena;
	}

	public void setLenguajeIndigena(CatLenguajeIndigena lenguajeIndigena) {
		this.lenguajeIndigena = lenguajeIndigena;
	}

	public CatTipoDiscapacidad getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}

	public void setTipoDiscapacidad(CatTipoDiscapacidad tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}

	
}