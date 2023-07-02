package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelRecursoPredominanteFoaDTO {
	private Integer idFoa;

	private Integer idCatRecursoPredominante;
	
	private Date fechaActualizacion;

	private Date fechaRegistro;

	private Long usuarioModifico;

	private CatalogoComunDTO catRecursoPredominanteFoa;

	private FichaDescriptivaOaDTO tblFichaDescriptivaObjetoAprendizaje;

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public Integer getIdCatRecursoPredominante() {
		return idCatRecursoPredominante;
	}

	public void setIdCatRecursoPredominante(Integer idCatRecursoPredominante) {
		this.idCatRecursoPredominante = idCatRecursoPredominante;
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

	public CatalogoComunDTO getCatRecursoPredominanteFoa() {
		return catRecursoPredominanteFoa;
	}

	public void setCatRecursoPredominanteFoa(CatalogoComunDTO catRecursoPredominanteFoa) {
		this.catRecursoPredominanteFoa = catRecursoPredominanteFoa;
	}

	public FichaDescriptivaOaDTO getTblFichaDescriptivaObjetoAprendizaje() {
		return tblFichaDescriptivaObjetoAprendizaje;
	}

	public void setTblFichaDescriptivaObjetoAprendizaje(FichaDescriptivaOaDTO tblFichaDescriptivaObjetoAprendizaje) {
		this.tblFichaDescriptivaObjetoAprendizaje = tblFichaDescriptivaObjetoAprendizaje;
	}

}
