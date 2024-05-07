package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

public class HistorialAcademicoDTO {

    private String nombre;
    private String matricula;
    private String claveInst;
    private String programaEducativo;
    private String clave;
    private String nivel;
    private BigDecimal promedio;
    private BigDecimal creditos;
    private BigInteger totalCreditos;
    private BigInteger aprobadas;
    private BigInteger reprobadas;
    private BigInteger nopresentadas;
    private BigInteger total;
    private String fechaConsulta;
    private String estatus;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getClaveInst() {
        return claveInst;
    }

    public void setClaveInst(String claveInst) {
        this.claveInst = claveInst;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }

    public BigDecimal getCreditos() {
        return creditos;
    }

    public void setCreditos(BigDecimal creditos) {
        this.creditos = creditos;
    }

    public BigInteger getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(BigInteger totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public BigInteger getAprobadas() {
        return aprobadas;
    }

    public void setAprobadas(BigInteger aprobadas) {
        this.aprobadas = aprobadas;
    }

    public BigInteger getReprobadas() {
        return reprobadas;
    }

    public void setReprobadas(BigInteger reprobadas) {
        this.reprobadas = reprobadas;
    }

    public BigInteger getNopresentadas() {
        return nopresentadas;
    }

    public void setNopresentadas(BigInteger nopresentadas) {
        this.nopresentadas = nopresentadas;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
