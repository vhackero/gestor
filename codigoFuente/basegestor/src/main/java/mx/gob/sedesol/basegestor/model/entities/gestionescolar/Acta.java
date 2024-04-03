package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ENTITY ACTAS
 * @author ITTIVA
 * 
 */
@Entity
@Table(name="tbl_actas")
@NamedQuery(name="Acta.findAll", query="SELECT r FROM Acta r")
public class Acta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_acta")
	private Integer idActa;
	
	//bi-directional many-to-one association to TblGrupo
	@ManyToOne
	@JoinColumn(name="id_grupo")
	private TblGrupo grupo;
	
	@Column(name="acta")
	private byte[] blob;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_cierre")
	private Date fechaCierre;
	
	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	//Getter y Setter

	public Integer getIdActa() {
		return idActa;
	}

	public void setIdActa(Integer idActa) {
		this.idActa = idActa;
	}

	public TblGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(TblGrupo grupo) {
		this.grupo = grupo;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public byte[] getBlob() {
		return blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}

}
