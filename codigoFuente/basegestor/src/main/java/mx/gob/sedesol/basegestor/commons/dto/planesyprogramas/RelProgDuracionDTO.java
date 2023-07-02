package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;


import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class RelProgDuracionDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Integer idPrograma;
	private Integer idTpoCargaHoraria;
	private String horas;
	private String minutos;	
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO catTpoCargaHoraria;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	
	private Integer horasInteger;
	private Integer minutosInteger;	
	
	public RelProgDuracionDTO() {super();}

	public RelProgDuracionDTO( CatalogoComunDTO catTpoCargaHoraria) {
		super();
		this.setCatTpoCargaHoraria(catTpoCargaHoraria);
	}

	public CatalogoComunDTO getCatTpoCargaHoraria() {
		return catTpoCargaHoraria;
	}

	public void setCatTpoCargaHoraria(CatalogoComunDTO catTpoCargaHoraria) {
		this.catTpoCargaHoraria = catTpoCargaHoraria;
		if(ObjectUtils.isNotNull(this.catTpoCargaHoraria))
			this.idTpoCargaHoraria = this.catTpoCargaHoraria.getId();
		
	}
	

	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
		if(ObjectUtils.isNotNull(this.fichaDescriptivaPrograma))
			this.idPrograma = this.fichaDescriptivaPrograma.getIdPrograma();
		
		
	}


	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
		if(!ObjectUtils.isNullOrEmpty(this.horas))
			this.horasInteger = Integer.parseInt(this.horas);
	}
	public String getMinutos() {
		return minutos;
	}
	public void setMinutos(String minutos) {
		this.minutos = minutos;
		if(!ObjectUtils.isNullOrEmpty(this.minutos))
			this.minutosInteger = Integer.parseInt(this.minutos);
	}
	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public Integer getIdTpoCargaHoraria() {
		return idTpoCargaHoraria;
	}
	public void setIdTpoCargaHoraria(Integer idTpoCargaHoraria) {
		this.idTpoCargaHoraria = idTpoCargaHoraria;
	}

	/**
	 * @return the horasInteger
	 */
	public Integer getHorasInteger() {
		return horasInteger;
	}

	/**
	 * @param horasInteger the horasInteger to set
	 */
	public void setHorasInteger(Integer horasInteger) {
		this.horasInteger = horasInteger;
		if(this.horasInteger != null)
			this.horas = this.horasInteger.toString();
	}

	/**
	 * @return the minutosInteger
	 */
	public Integer getMinutosInteger() {
		return minutosInteger;
	}

	/**
	 * @param minutosInteger the minutosInteger to set
	 */
	public void setMinutosInteger(Integer minutosInteger) {
		this.minutosInteger = minutosInteger;
		if(this.minutosInteger != null)
			this.minutos = this.minutosInteger.toString();
	}
	
	
	
	
	
	
}
