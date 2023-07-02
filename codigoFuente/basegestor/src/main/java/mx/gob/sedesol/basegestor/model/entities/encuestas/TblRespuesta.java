package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbl_respuestas database table.
 * 
 */
@Entity
@Table(name="tbl_respuestas")
@NamedQuery(name="TblRespuesta.findAll", query="SELECT t FROM TblRespuesta t")
public class TblRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_respuesta")
	private Integer idRespuesta;

	@Column(name="id_pregunta")
	private Integer idPregunta;

	@Column(name = "ponderacion")
	private Integer ponderacion;

	//bi-directional many-to-one association to RelRespuestaUsuario
	@ManyToOne
	@JoinColumn(name="id_respuesta_usuario")
	private RelRespuestaUsuario relRespuestaUsuario;

	public TblRespuesta() {
	}


    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Integer getIdPregunta() {
		return this.idPregunta;
	}

	public void setIdPregunta(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}

	public int getPonderacion() {
		return this.ponderacion;
	}

	public void setPonderacion(int ponderacion) {
		this.ponderacion = ponderacion;
	}

	public RelRespuestaUsuario getRelRespuestaUsuario() {
		return this.relRespuestaUsuario;
	}

	public void setRelRespuestaUsuario(RelRespuestaUsuario relRespuestaUsuario) {
		this.relRespuestaUsuario = relRespuestaUsuario;
	}

}