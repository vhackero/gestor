package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelReponsableProduccionOa;


@Entity
@Table(name="rel_persona_responsabilidades")
@NamedQuery(name="RelPersonaResponsabilidades.findAll", query="SELECT r FROM RelPersonaResponsabilidades r")
public class RelPersonaResponsabilidades implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;



	//bi-directional many-to-one association to RelReponsableProduccionOa
	@OneToMany(mappedBy="personaResponsabilidade")
	private List<RelReponsableProduccionOa> relReponsableProduccionOas;
	

	//bi-directional many-to-one association to TblPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_persona")
	private TblPersona tblPersona;

	//bi-directional many-to-one association to CatTipoResponsabilidadEc
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_tipo_responsabilidad")
	private CatTipoResponsabilidadEc catTipoResponsabilidadEc;
	
	//bi-directional many-to-one association to RelReponsableProduccionEc
	@OneToMany(mappedBy="personaResponsabilidad")
	private List<RelReponsableProduccionEc> relReponsableProduccionEcs;
	
	
	public RelPersonaResponsabilidades() {
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

	public TblPersona getTblPersona() {
		return this.tblPersona;
	}

	public void setTblPersona(TblPersona tblPersona) {
		this.tblPersona = tblPersona;
	}

	public CatTipoResponsabilidadEc getCatTipoResponsabilidadEc() {
		return this.catTipoResponsabilidadEc;
	}

	public void setCatTipoResponsabilidadEc(CatTipoResponsabilidadEc catTipoResponsabilidadEc) {
		this.catTipoResponsabilidadEc = catTipoResponsabilidadEc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<RelReponsableProduccionOa> getRelReponsableProduccionOas() {
		return relReponsableProduccionOas;
	}

	public void setRelReponsableProduccionOas(List<RelReponsableProduccionOa> relReponsableProduccionOas) {
		this.relReponsableProduccionOas = relReponsableProduccionOas;
	}

	public List<RelReponsableProduccionEc> getRelReponsableProduccionEcs() {
		return relReponsableProduccionEcs;
	}

	public void setRelReponsableProduccionEcs(List<RelReponsableProduccionEc> relReponsableProduccionEcs) {
		this.relReponsableProduccionEcs = relReponsableProduccionEcs;
	}

	

}