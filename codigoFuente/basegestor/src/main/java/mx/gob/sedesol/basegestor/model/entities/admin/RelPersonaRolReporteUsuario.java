package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="rel_persona_roles")
@NamedQuery(name="RelPersonaRolReporteUsuario.findAll", query="SELECT r FROM RelPersonaRolReporteUsuario r")
public class RelPersonaRolReporteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona_rol")
	private Long idPersonaRol;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_persona")
	private TblPersonaReporteUsuario persona;

	//bi-directional many-to-one association to CatRole
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_rol")
	private CatRolReporteUsuario rol;

	
	public RelPersonaRolReporteUsuario() {
	}
	
	public Long getIdPersonaRol() {
		return this.idPersonaRol;
	}

	public void setIdPersonaRol(Long idPersonaRol) {
		this.idPersonaRol = idPersonaRol;
	}

	public TblPersonaReporteUsuario getPersona() {
		return this.persona;
	}

	public void setPersona(TblPersonaReporteUsuario persona) {
		this.persona = persona;
	}

	public CatRolReporteUsuario getRol() {
		return this.rol;
	}

	public void setRol(CatRolReporteUsuario rol) {
		this.rol = rol;
	}

}