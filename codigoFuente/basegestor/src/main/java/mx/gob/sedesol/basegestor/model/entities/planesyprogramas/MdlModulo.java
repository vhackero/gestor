package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mdl_modulos database table.
 * 
 */
@Entity
@Table(name="mdl_modulos")
@NamedQuery(name="MdlModulo.findAll", query="SELECT m FROM MdlModulo m")
public class MdlModulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="activo")
	private Integer activo;
	
	@Column(name="cron")
	private Long cron;
	
	@Column(name="identificador")
	private String identificador;

	@Column(name="nombre")
	private String nombre;

	@Column(name="ruta_imagen")
	private String rutaImagen;

	@Column(name="ultimo_cron")
	private Long ultimoCron;
	
	@Column(name="tipo_modulo")
	private Integer tipoModulo;
	
	@Column(name="descripcion", columnDefinition="TEXT")
	private String descripcion;

	public MdlModulo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Long getCron() {
		return this.cron;
	}

	public void setCron(Long cron) {
		this.cron = cron;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaImagen() {
		return this.rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public Long getUltimoCron() {
		return this.ultimoCron;
	}

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

}