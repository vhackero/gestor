package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelActividadesReforzamientoFoaDTO {
	private Integer idActividadReforzamiento;
	private Integer idFoa;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO catActividadReforzamientoFoa;
	private FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje;

	public Integer getIdActividadReforzamiento() {
		return idActividadReforzamiento;
	}

	public void setIdActividadReforzamiento(Integer idActividadReforzamiento) {
		this.idActividadReforzamiento = idActividadReforzamiento;
	}

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
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

	public CatalogoComunDTO getCatActividadReforzamientoFoa() {
		return catActividadReforzamientoFoa;
	}

	public void setCatActividadReforzamientoFoa(CatalogoComunDTO catActividadReforzamientoFoa) {
		this.catActividadReforzamientoFoa = catActividadReforzamientoFoa;
	}

	public FichaDescriptivaOaDTO getFichaDescriptivaObjetoAprendizaje() {
		return fichaDescriptivaObjetoAprendizaje;
	}

	public void setFichaDescriptivaObjetoAprendizaje(FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje) {
		this.fichaDescriptivaObjetoAprendizaje = fichaDescriptivaObjetoAprendizaje;
	}

}
