package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "rel_usuario_datos_laborales")
@NamedQuery(name="RelUsuarioDatosLaboralesReporteUsuario.findAll", query="SELECT r FROM RelUsuarioDatosLaboralesReporteUsuario r")
public class RelUsuarioDatosLaboralesReporteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario_datos_laborales")
	private Long idUsuarioDatosLaborales;

	@Column(name = "area_adscripcion")
	private String areaAdscripcion;

	private String institucion;

	@Column(name = "programa_social")
	private Boolean programaSocial;

	@Column(name = "sso_cargoPuesto")
	private String puesto;

	@Column(name = "unidad_adscripcion")
	private String unidadAdscripcion;

	@Column(name = "sso_idOrdenDeGobierno")
	private String idOrdenGobierno;

	// bi-directional many-to-one association to TblPersona
	@ManyToOne
	@JoinColumn(name = "id_persona")
	private TblPersonaReporteUsuario persona;

	public RelUsuarioDatosLaboralesReporteUsuario() {
	}

	public Long getIdUsuarioDatosLaborales() {
		return this.idUsuarioDatosLaborales;
	}

	public void setIdUsuarioDatosLaborales(Long idUsuarioDatosLaborales) {
		this.idUsuarioDatosLaborales = idUsuarioDatosLaborales;
	}

	public String getAreaAdscripcion() {
		return this.areaAdscripcion;
	}

	public void setAreaAdscripcion(String areaAdscripcion) {
		this.areaAdscripcion = areaAdscripcion;
	}

	public String getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Boolean getProgramaSocial() {
		return this.programaSocial;
	}

	public void setProgramaSocial(Boolean programaSocial) {
		this.programaSocial = programaSocial;
	}

	public String getPuesto() {
		return this.puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getUnidadAdscripcion() {
		return this.unidadAdscripcion;
	}

	public void setUnidadAdscripcion(String unidadAdscripcion) {
		this.unidadAdscripcion = unidadAdscripcion;
	}

	public TblPersonaReporteUsuario getPersona() {
		return persona;
	}

	public void setPersona(TblPersonaReporteUsuario persona) {
		this.persona = persona;
	}

	public String getIdOrdenGobierno() {
		return idOrdenGobierno;
	}


	public void setIdOrdenGobierno(String idOrdenGobierno) {
		this.idOrdenGobierno = idOrdenGobierno;
	}
}