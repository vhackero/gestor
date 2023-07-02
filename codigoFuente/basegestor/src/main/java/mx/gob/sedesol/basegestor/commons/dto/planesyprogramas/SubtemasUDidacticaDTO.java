package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;


public class SubtemasUDidacticaDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idSubtemaUdidactica;
	private Integer activo;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Integer horas;
	private Integer minutos;
	private String nombre;
	private Long usuarioModifico;
	private DetEstUniDidacticaDTO detEstUnidadDidactica;
	private BitacoraDTO bitacoraDTO;
	/**
	 * @return the idSubtemaUdidactica
	 */
	public Integer getIdSubtemaUdidactica() {
		return idSubtemaUdidactica;
	}
	/**
	 * @param idSubtemaUdidactica the idSubtemaUdidactica to set
	 */
	public void setIdSubtemaUdidactica(Integer idSubtemaUdidactica) {
		this.idSubtemaUdidactica = idSubtemaUdidactica;
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
	 * @return the horas
	 */
	public Integer getHoras() {
		return horas;
	}
	/**
	 * @param horas the horas to set
	 */
	public void setHoras(Integer horas) {
		this.horas = horas;
	}
	/**
	 * @return the minutos
	 */
	public Integer getMinutos() {
		return minutos;
	}
	/**
	 * @param minutos the minutos to set
	 */
	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
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
	 * @return the detEstUnidadDidactica
	 */
	public DetEstUniDidacticaDTO getDetEstUnidadDidactica() {
		return detEstUnidadDidactica;
	}
	/**
	 * @param detEstUnidadDidactica the detEstUnidadDidactica to set
	 */
	public void setDetEstUnidadDidactica(DetEstUniDidacticaDTO detEstUnidadDidactica) {
		this.detEstUnidadDidactica = detEstUnidadDidactica;
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
