package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the cat_asentamientos database table.
 * 
 */
@Entity
@Table(name="cat_asentamientos")
@NamedQuery(name="CatAsentamientoReporteUsuario.findAll", query="SELECT c FROM CatAsentamientoReporteUsuario c")
public class CatAsentamientoReporteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_asentamiento")
	private String idAsentamiento;

	//bi-directional many-to-one association to CatMunicipio
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_municipio")
	private CatMunicipioReporteUsuario municipio;

	public CatAsentamientoReporteUsuario() {
	}

	public String getIdAsentamiento() {
		return this.idAsentamiento;
	}

	public void setIdAsentamiento(String idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}

	
	public CatMunicipioReporteUsuario getMunicipio() {
		return municipio;
	}

	public void setMunicipio(CatMunicipioReporteUsuario municipio) {
		this.municipio = municipio;
	}

}