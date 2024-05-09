package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class TiraMateriaBajaDTO {
	
	
	
	private String nombreMateria;
	private String periodo;
	private String tipoBaja;

	public String getNombreMateria() {
		return nombreMateria;
	}
	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getTipoBaja() {
		return tipoBaja;
	}
	public void setTipoBaja(String tipoBaja) {
		this.tipoBaja = tipoBaja;
	}
}
