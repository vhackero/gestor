package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class HistorialAcademicoDTO {
	
	private String nombre;
	private String matricula;
	private String programaEducativo;
	private BigDecimal promedio;
	private BigDecimal creditos;
	private BigInteger aprobadas;
	private BigInteger reprobadas;
	private BigInteger nopresentadas;
	private String estatus;
	private String nivel;
	private Timestamp fechaConsulta;
	private BigInteger total;
	
	
	
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
	public String getProgramaEducativo() {
		return programaEducativo;
	}
	public void setProgramaEducativo(String programaEducativo) {
		this.programaEducativo = programaEducativo;
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
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public Timestamp getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(Timestamp fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public BigInteger getTotal() {
		return total;
	}
	public void setTotal(BigInteger total) {
		this.total = total;
	}


}
