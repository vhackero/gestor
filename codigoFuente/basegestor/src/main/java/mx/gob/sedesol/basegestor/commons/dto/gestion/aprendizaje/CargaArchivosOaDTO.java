/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

/**
 * 
 *
 */
public class CargaArchivosOaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String directorio;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String idArchivo;
	private Integer idLms;
	private String nombreArchivo;
	private BigInteger usuarioModifico;
	private CatalogoComunDTO catClasificacionArchivoOa;
	private UnidadOaAvaDTO unidadOaAva;
	private Integer versionArchivo;
	private String pesoArchivo;
        
	
	public CargaArchivosOaDTO() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDirectorio() {
		return this.directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	
	public Integer getIdLms() {
		return this.idLms;
	}

	public void setIdLms(Integer idLms) {
		this.idLms = idLms;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatalogoComunDTO getCatClasificacionArchivoOa() {
		return catClasificacionArchivoOa;
	}

	public void setCatClasificacionArchivoOa(CatalogoComunDTO catClasificacionArchivoOa) {
		this.catClasificacionArchivoOa = catClasificacionArchivoOa;
	}

	public UnidadOaAvaDTO getUnidadOaAva() {
		return unidadOaAva;
	}

	public void setUnidadOaAva(UnidadOaAvaDTO unidadOaAva) {
		this.unidadOaAva = unidadOaAva;
	}

	public Integer getVersionArchivo() {
		return versionArchivo;
	}

	public void setVersionArchivo(Integer versionArchivo) {
		this.versionArchivo = versionArchivo;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getPesoArchivo() {
		return pesoArchivo;
	}

	public void setPesoArchivo(String pesoArchivo) {
		this.pesoArchivo = pesoArchivo;
	}

}
