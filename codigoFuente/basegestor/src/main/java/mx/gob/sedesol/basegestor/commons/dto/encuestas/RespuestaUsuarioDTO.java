package mx.gob.sedesol.basegestor.commons.dto.encuestas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by jhcortes on 15/12/16.
 */
public class RespuestaUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idRespuestaUsuario;
    private Date fechaRegistro;
    private Integer idEncuesta;
    private Integer idUsuario;
    private List<RespuestaDTO> tblRespuestas;

    public Integer getIdRespuestaUsuario() {
		return idRespuestaUsuario;
	}
	public void setIdRespuestaUsuario(Integer idRespuestaUsuario) {
		this.idRespuestaUsuario = idRespuestaUsuario;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Integer getIdEncuesta() {
		return idEncuesta;
	}
	public void setIdEncuesta(Integer idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public List<RespuestaDTO> getTblRespuestas() {
		return tblRespuestas;
	}
	public void setTblRespuestas(List<RespuestaDTO> tblRespuestas) {
		this.tblRespuestas = tblRespuestas;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
    
}
