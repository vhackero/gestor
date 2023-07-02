package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_lotes_carga_usuarios")
public class TblLoteCargaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_lote_carga_usuarios")
	private int idLoteCargaUsuarios;

	private String nombre;

	@Column(name="ruta_archivo")
	private String rutaArchivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private long usuarioModifico;

	public TblLoteCargaUsuario() {
	}

	public int getIdLoteCargaUsuarios() {
		return this.idLoteCargaUsuarios;
	}

	public void setIdLoteCargaUsuarios(int idLoteCargaUsuarios) {
		this.idLoteCargaUsuarios = idLoteCargaUsuarios;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaArchivo() {
		return this.rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
}