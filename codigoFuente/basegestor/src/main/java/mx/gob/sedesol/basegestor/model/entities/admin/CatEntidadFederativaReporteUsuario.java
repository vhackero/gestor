package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the cat_entidades_federativas database table.
 * 
 */
@Entity
@Table(name="cat_entidades_federativas")
@NamedQuery(name="CatEntidadFederativaReporteUsuario.findAll", query="SELECT c FROM CatEntidadFederativaReporteUsuario c")
public class CatEntidadFederativaReporteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_entidad_federativa")
	private Integer idEntidadFederativa;

	private String nombre;
	
	public CatEntidadFederativaReporteUsuario() {
	}

	public Integer getIdEntidadFederativa() {
		return this.idEntidadFederativa;
	}

	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

}