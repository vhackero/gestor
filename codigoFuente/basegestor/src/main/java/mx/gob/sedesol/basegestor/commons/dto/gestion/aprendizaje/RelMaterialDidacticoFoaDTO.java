package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.util.Date;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelMaterialDidacticoFoaDTO {
	private Integer idFoa;

	private Integer idCatRecursoDidactico;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private Long usuarioModifico;

	private CatalogoComunDTO catRecursoDidacticoOa;

	private FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje;

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public Integer getIdCatRecursoDidactico() {
		return idCatRecursoDidactico;
	}

	public void setIdCatRecursoDidactico(Integer idCatRecursoDidactico) {
		this.idCatRecursoDidactico = idCatRecursoDidactico;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the catRecursoDidacticoOa
	 */
	public CatalogoComunDTO getCatRecursoDidacticoOa() {
		return catRecursoDidacticoOa;
	}

	/**
	 * @param catRecursoDidacticoOa
	 *            the catRecursoDidacticoOa to set
	 */
	public void setCatRecursoDidacticoOa(CatalogoComunDTO catRecursoDidacticoOa) {
		this.catRecursoDidacticoOa = catRecursoDidacticoOa;
	}

	/**
	 * @return the fichaDescriptivaObjetoAprendizaje
	 */
	public FichaDescriptivaOaDTO getFichaDescriptivaObjetoAprendizaje() {
		return fichaDescriptivaObjetoAprendizaje;
	}

	/**
	 * @param fichaDescriptivaObjetoAprendizaje
	 *            the fichaDescriptivaObjetoAprendizaje to set
	 */
	public void setFichaDescriptivaObjetoAprendizaje(FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje) {
		this.fichaDescriptivaObjetoAprendizaje = fichaDescriptivaObjetoAprendizaje;
	}
}
