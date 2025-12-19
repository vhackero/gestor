package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class DatosMoodlePersonaDTO implements Serializable {

    private static final long serialVersionUID = -8924154704307450597L;

    private Integer idPersonaMoodle;
    private Integer idPlataformaMoodle;

    public DatosMoodlePersonaDTO() {
    }

    public DatosMoodlePersonaDTO(Integer idPersonaMoodle, Integer idPlataformaMoodle) {
        this.idPersonaMoodle = idPersonaMoodle;
        this.idPlataformaMoodle = idPlataformaMoodle;
    }

    public Integer getIdPersonaMoodle() {
        return idPersonaMoodle;
    }

    public void setIdPersonaMoodle(Integer idPersonaMoodle) {
        this.idPersonaMoodle = idPersonaMoodle;
    }

    public Integer getIdPlataformaMoodle() {
        return idPlataformaMoodle;
    }

    public void setIdPlataformaMoodle(Integer idPlataformaMoodle) {
        this.idPlataformaMoodle = idPlataformaMoodle;
    }
}
