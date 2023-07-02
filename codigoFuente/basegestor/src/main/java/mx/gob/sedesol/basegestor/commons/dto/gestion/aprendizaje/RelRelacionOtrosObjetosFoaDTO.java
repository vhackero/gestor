package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelRelacionOtrosObjetosFoaDTO {
	private Integer idFoa;
	private Integer idCatRelacionOtrosObjetos;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO catRelacionOtrosObjeto;
	private FichaDescriptivaOaDTO tblFichaDescriptivaObjetoAprendizaje;

	public RelRelacionOtrosObjetosFoaDTO() {

	}

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public Integer getIdCatRelacionOtrosObjetos() {
		return idCatRelacionOtrosObjetos;
	}

	public void setIdCatRelacionOtrosObjetos(Integer idCatRelacionOtrosObjetos) {
		this.idCatRelacionOtrosObjetos = idCatRelacionOtrosObjetos;
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

	public CatalogoComunDTO getCatRelacionOtrosObjeto() {
		return catRelacionOtrosObjeto;
	}

	public void setCatRelacionOtrosObjeto(CatalogoComunDTO catRelacionOtrosObjeto) {
		this.catRelacionOtrosObjeto = catRelacionOtrosObjeto;
	}

	public FichaDescriptivaOaDTO getTblFichaDescriptivaObjetoAprendizaje() {
		return tblFichaDescriptivaObjetoAprendizaje;
	}

	public void setTblFichaDescriptivaObjetoAprendizaje(FichaDescriptivaOaDTO tblFichaDescriptivaObjetoAprendizaje) {
		this.tblFichaDescriptivaObjetoAprendizaje = tblFichaDescriptivaObjetoAprendizaje;
	}

}
