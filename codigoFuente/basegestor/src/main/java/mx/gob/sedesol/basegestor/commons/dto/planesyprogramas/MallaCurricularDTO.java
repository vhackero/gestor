package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class MallaCurricularDTO implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private MallaCurricularDTO mallaCurricularPadre;
	private List<MallaCurricularDTO> lstHijosMallaCurr;
	private Integer idPlan;
	private String nombre;
	private Long usuarioModifico;
	private CatalogoComunDTO objetoCurricular;
	private String nombreUsuarioMod;
	private Integer idCategoriaMdl;
	private Integer idCategoriaMdlPadre;
	private BitacoraDTO bitacoraDTO;
        
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the activo
	 */
	public Integer getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the mallaCurricularPadre
	 */
	public MallaCurricularDTO getMallaCurricularPadre() {
		return mallaCurricularPadre;
	}
	/**
	 * @param mallaCurricularPadre the mallaCurricularPadre to set
	 */
	public void setMallaCurricularPadre(MallaCurricularDTO mallaCurricularPadre) {
		this.mallaCurricularPadre = mallaCurricularPadre;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	/**
	 * @param usuarioModifico the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	/**
	 * @return the objetoCurricular
	 */
	public CatalogoComunDTO getObjetoCurricular() {
		return objetoCurricular;
	}
	/**
	 * @param objetoCurricular the objetoCurricular to set
	 */
	public void setObjetoCurricular(CatalogoComunDTO objetoCurricular) {
		this.objetoCurricular = objetoCurricular;
	}
	/**
	 * @return the nombreUsuarioMod
	 */
	public String getNombreUsuarioMod() {
		return nombreUsuarioMod;
	}
	/**
	 * @param nombreUsuarioMod the nombreUsuarioMod to set
	 */
	public void setNombreUsuarioMod(String nombreUsuarioMod) {
		this.nombreUsuarioMod = nombreUsuarioMod;
	}
	/**
	 * @return the lstHijosMallaCurr
	 */
	public List<MallaCurricularDTO> getLstHijosMallaCurr() {
		return lstHijosMallaCurr;
	}
	/**
	 * @param lstHijosMallaCurr the lstHijosMallaCurr to set
	 */
	public void setLstHijosMallaCurr(List<MallaCurricularDTO> lstHijosMallaCurr) {
		this.lstHijosMallaCurr = lstHijosMallaCurr;
	}
	/**
	 * @return the idPlan
	 */
	public Integer getIdPlan() {
		return idPlan;
	}
	/**
	 * @param idPlan the idPlan to set
	 */
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	/**
	 * @return the idCategoriaMdl
	 */
	public Integer getIdCategoriaMdl() {
		return idCategoriaMdl;
	}
	/**
	 * @param idCategoriaMdl the idCategoriaMdl to set
	 */
	public void setIdCategoriaMdl(Integer idCategoriaMdl) {
		this.idCategoriaMdl = idCategoriaMdl;
	}
	/**
	 * @return the idCategoriaMdlPadre
	 */
	public Integer getIdCategoriaMdlPadre() {
		return idCategoriaMdlPadre;
	}
	/**
	 * @param idCategoriaMdlPadre the idCategoriaMdlPadre to set
	 */
	public void setIdCategoriaMdlPadre(Integer idCategoriaMdlPadre) {
		this.idCategoriaMdlPadre = idCategoriaMdlPadre;
	}

    /**
     * @return the bitacoraDTO
     */
    public BitacoraDTO getBitacoraDTO() {
        return bitacoraDTO;
    }

    /**
     * @param bitacoraDTO the bitacoraDTO to set
     */
    public void setBitacoraDTO(BitacoraDTO bitacoraDTO) {
        this.bitacoraDTO = bitacoraDTO;
    }

}
