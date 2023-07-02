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
 * The persistent class for the cat_municipios database table.
 * 
 */
@Entity
@Table(name="cat_municipios")
@NamedQuery(name="CatMunicipioReporteUsuario.findAll", query="SELECT c FROM CatMunicipioReporteUsuario c")
public class CatMunicipioReporteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_municipio")
	private String idMunicipio;

	//bi-directional many-to-one association to CatEntidadesFederativa
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_entidad_federativa")
	private CatEntidadFederativaReporteUsuario entidadFederativa;

	public CatMunicipioReporteUsuario() {
	}

	public String getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public CatEntidadFederativaReporteUsuario getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(CatEntidadFederativaReporteUsuario entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}


}