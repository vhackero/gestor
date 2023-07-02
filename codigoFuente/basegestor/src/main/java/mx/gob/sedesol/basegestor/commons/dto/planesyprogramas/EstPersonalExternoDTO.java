package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;



public class EstPersonalExternoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer activo;
	private EstPersonalExternoDTO estPersonalExternoPadre;
	private Integer idNivelEstructural;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private Integer orden;
	private List<EstPersonalExternoDTO> estPersExternoHijos;
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
	 * @return the estPersonalExternoPadre
	 */
	public EstPersonalExternoDTO getEstPersonalExternoPadre() {
		return estPersonalExternoPadre;
	}
	/**
	 * @param estPersonalExternoPadre the estPersonalExternoPadre to set
	 */
	public void setEstPersonalExternoPadre(EstPersonalExternoDTO estPersonalExternoPadre) {
		this.estPersonalExternoPadre = estPersonalExternoPadre;
	}
	/**
	 * @return the idNivelEstructural
	 */
	public Integer getIdNivelEstructural() {
		return idNivelEstructural;
	}
	/**
	 * @param idNivelEstructural the idNivelEstructural to set
	 */
	public void setIdNivelEstructural(Integer idNivelEstructural) {
		this.idNivelEstructural = idNivelEstructural;
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
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	/**
	 * @return the estPersExternoHijos
	 */
	public List<EstPersonalExternoDTO> getEstPersExternoHijos() {
		return estPersExternoHijos;
	}
	/**
	 * @param estPersExternoHijos the estPersExternoHijos to set
	 */
	public void setEstPersExternoHijos(List<EstPersonalExternoDTO> estPersExternoHijos) {
		this.estPersExternoHijos = estPersExternoHijos;
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
