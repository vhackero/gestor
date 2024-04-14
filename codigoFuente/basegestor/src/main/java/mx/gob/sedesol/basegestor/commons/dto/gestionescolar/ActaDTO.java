package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class ActaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idActa;
	private Integer grupo;
	private byte[] blob;
	private Date fechaCierre;
	private Long usuarioModifico;
	
	public Integer getIdActa() {
		return idActa;
	}
	public void setIdActa(Integer idActa) {
		this.idActa = idActa;
	}
	public Integer getGrupo() {
		return grupo;
	}
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}
	public byte[] getBlob() {
		return blob;
	}
	public void setBlob(byte[] blob) {
		this.blob = blob;
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
	@Override
	public String toString() {
		return "ActaDTO [idActa=" + idActa + ", grupo=" + grupo + ", fechaCierre=" + fechaCierre + ", usuarioModifico="
				+ usuarioModifico + "]";
	}
}
