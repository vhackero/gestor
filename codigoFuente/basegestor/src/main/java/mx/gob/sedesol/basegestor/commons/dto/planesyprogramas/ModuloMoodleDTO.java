package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;

public class ModuloMoodleDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer activo;
	private Long cron;
	private String identificador;
	private String nombre;
	private String rutaImagen;
	private Long ultimoCron;
	private Integer tipoModulo;
	private String descripcion;
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
	 * @return the cron
	 */
	public Long getCron() {
		return cron;
	}
	/**
	 * @param cron the cron to set
	 */
	public void setCron(Long cron) {
		this.cron = cron;
	}
	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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
	 * @return the rutaImagen
	 */
	public String getRutaImagen() {
		return rutaImagen;
	}
	/**
	 * @param rutaImagen the rutaImagen to set
	 */
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	/**
	 * @return the ultimoCron
	 */
	public Long getUltimoCron() {
		return ultimoCron;
	}
	/**
	 * @param ultimoCron the ultimoCron to set
	 */
	public void setUltimoCron(Long ultimoCron) {
		this.ultimoCron = ultimoCron;
	}
	/**
	 * @return the tipoModulo
	 */
	public Integer getTipoModulo() {
		return tipoModulo;
	}
	/**
	 * @param tipoModulo the tipoModulo to set
	 */
	public void setTipoModulo(Integer tipoModulo) {
		this.tipoModulo = tipoModulo;
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
