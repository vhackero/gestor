package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.math.BigInteger;

public class ConvocatoriaTableroResumen implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombreConvocatoria;
	private String nombrePlan;
	private BigInteger total;
	
	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}
	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}
	public String getNombrePlan() {
		return nombrePlan;
	}
	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}
	public BigInteger getTotal() {
		return total;
	}
	public void setTotal(BigInteger total) {
		this.total = total;
	}
	

}
