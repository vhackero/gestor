/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class RecursosOaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer idUObjetoAprendizaje;

	private Integer idCatTemaR;

	private Date fechaActualizacion;

	private Date fechaRegistro;
	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String instruccionesContenido;
	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;

	private BigInteger usuarioModifico;

	private CatalogoComunDTO catTemaRecurso;

	private UnidadOaAvaDTO unidadOaAva;

	private Integer idRecursoLms;

	public RecursosOaDTO() {
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

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatalogoComunDTO getCatTemaRecurso() {
		return catTemaRecurso;
	}

	public void setCatTemaRecurso(CatalogoComunDTO catTemaRecurso) {
		this.catTemaRecurso = catTemaRecurso;
		idCatTemaR = this.catTemaRecurso.getId();
	}

	public UnidadOaAvaDTO getUnidadOaAva() {
		return unidadOaAva;
	}

	public void setUnidadOaAva(UnidadOaAvaDTO unidadOaAva) {
		this.unidadOaAva = unidadOaAva;
		idUObjetoAprendizaje = this.unidadOaAva.getId();
	}

	public String getInstruccionesContenido() {
		return instruccionesContenido;
	}

	public void setInstruccionesContenido(String instruccionesContenido) {
		this.instruccionesContenido = instruccionesContenido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdUObjetoAprendizaje() {
		return idUObjetoAprendizaje;
	}

	public void setIdUObjetoAprendizaje(Integer idUObjetoAprendizaje) {
		this.idUObjetoAprendizaje = idUObjetoAprendizaje;
	}

	public Integer getIdCatTemaR() {
		return idCatTemaR;
	}

	public void setIdCatTemaR(Integer idCatTemaR) {
		this.idCatTemaR = idCatTemaR;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Metodo que se usa para comparar dos objetos del tipo recursosOA
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecursosOaDTO other = (RecursosOaDTO) obj;
		if (idCatTemaR == null) {
			if (other.idCatTemaR != null)
				return false;
		} else if (!idCatTemaR.equals(other.idCatTemaR))
			return false;
		if (idUObjetoAprendizaje == null) {
			if (other.idUObjetoAprendizaje != null)
				return false;
		} else if (!idUObjetoAprendizaje.equals(other.idUObjetoAprendizaje))
			return false;
		return true;
	}

	public Integer getIdRecursoLms() {
		return idRecursoLms;
	}

	public void setIdRecursoLms(Integer idRecursoLms) {
		this.idRecursoLms = idRecursoLms;
	}

}
