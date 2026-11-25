package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class DispersionPendienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idPersona;
    private String matricula;
    private String nombre;
    private String correo;
    private String plan;
    private String programa;
    private String clave;
    private String semestre;
    private String bloque;
    private String proceso;
    private Date fechaInscripcion;
    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long valor) { idPersona = valor; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String valor) { matricula = valor; }
    public String getNombre() { return nombre; }
    public void setNombre(String valor) { nombre = valor; }
    public String getCorreo() { return correo; }
    public void setCorreo(String valor) { correo = valor; }
    public String getPlan() { return plan; }
    public void setPlan(String valor) { plan = valor; }
    public String getPrograma() { return programa; }
    public void setPrograma(String valor) { programa = valor; }
    public String getClave() { return clave; }
    public void setClave(String valor) { clave = valor; }
    public String getSemestre() { return semestre; }
    public void setSemestre(String valor) { semestre = valor; }
    public String getBloque() { return bloque; }
    public void setBloque(String valor) { bloque = valor; }
    public String getProceso() { return proceso; }
    public void setProceso(String valor) { proceso = valor; }
    public Date getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(Date valor) { fechaInscripcion = valor; }
}
