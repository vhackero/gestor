package mx.gob.sedesol.basegestor.commons.dto.encuestas;

import java.io.Serializable;

/**
 * Created by jhcortes on 15/12/16.
 */
public class RespuestaDTO implements Serializable{
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idRespuesta;
    private int idPregunta;
    private int ponderacion;
    private RespuestaUsuarioDTO relRespuestaUsuario;

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
    }

    public RespuestaUsuarioDTO getRelRespuestaUsuario() {
    	if(relRespuestaUsuario==null)
    		relRespuestaUsuario = new RespuestaUsuarioDTO();
        return relRespuestaUsuario;
    }

    public void setRelRespuestaUsuario(RespuestaUsuarioDTO relRespuestaUsuario) {
        this.relRespuestaUsuario = relRespuestaUsuario;
    }

	@Override
	public String toString() {
		return "RespuestaDTO [idRespuesta=" + idRespuesta + ", idPregunta=" + idPregunta + ", ponderacion="
				+ ponderacion + "]";
	}
    
}
