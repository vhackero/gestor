package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
	

//	@Column(name="id_grupo")
//	private Integer grupo;
	
	//bi-directional many-to-one association to TblGrupo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_grupo")
	private TblGrupo tblGrupo;
	
	@Lob
	@Column(name="acta")
	private byte[] blob;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_cierre")
	private Date fechaCierre;
	
	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
//	//bi-directional many-to-one association to TblPersona
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "usuario_modifico")
//	private TblPersona tblPersona;
	
	//Getter y Setter

	public Integer getIdActa() {
		return idActa;
	}

	public void setIdActa(Integer idActa) {
		this.idActa = idActa;
	}

	

//	public Integer getGrupo() {
//		return grupo;
//	}
//
//	public void setGrupo(Integer grupo) {
//		this.grupo = grupo;
//	}
	
	public TblGrupo getTblGrupo() {
		return this.tblGrupo;
	}

	public void setTblGrupo(TblGrupo tblGrupo) {
		this.tblGrupo = tblGrupo;
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
//	public TblPersona getTblPersona() {
//		return this.tblPersona;
//	}
//
//	public void setTblPersona(TblPersona tblPersona) {
//		this.tblPersona = tblPersona;
//	}

	public byte[] getBlob() {
		return blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}

	@Override
	public String toString() {
		return "Acta [idActa=" + idActa + ", grupo=" + tblGrupo.toString() + ", fechaCierre="
				+ fechaCierre + ", usuarioModifico=" + usuarioModifico + "]";
	}

 

}
