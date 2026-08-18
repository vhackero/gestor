package mx.gob.sedesol.basegestor.commons.dto.integracion;

import java.io.Serializable;
import java.math.BigDecimal;

public class EstudianteSigieConsultaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String matricula;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String curp;
    private String clavePrograma;
    private String nombrePrograma;
    private String nivel;
    private String identificadorPlan;
    private BigDecimal creditosTotales;
    private BigDecimal creditosCubiertos;
    private String correoInstitucional;
    private Boolean activo;
    private Integer prioridadBaja;

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }
    public String getClavePrograma() { return clavePrograma; }
    public void setClavePrograma(String clavePrograma) { this.clavePrograma = clavePrograma; }
    public String getNombrePrograma() { return nombrePrograma; }
    public void setNombrePrograma(String nombrePrograma) { this.nombrePrograma = nombrePrograma; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getIdentificadorPlan() { return identificadorPlan; }
    public void setIdentificadorPlan(String identificadorPlan) { this.identificadorPlan = identificadorPlan; }
    public BigDecimal getCreditosTotales() { return creditosTotales; }
    public void setCreditosTotales(BigDecimal creditosTotales) { this.creditosTotales = creditosTotales; }
    public BigDecimal getCreditosCubiertos() { return creditosCubiertos; }
    public void setCreditosCubiertos(BigDecimal creditosCubiertos) { this.creditosCubiertos = creditosCubiertos; }
    public String getCorreoInstitucional() { return correoInstitucional; }
    public void setCorreoInstitucional(String correoInstitucional) { this.correoInstitucional = correoInstitucional; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public Integer getPrioridadBaja() { return prioridadBaja; }
    public void setPrioridadBaja(Integer prioridadBaja) { this.prioridadBaja = prioridadBaja; }
}
