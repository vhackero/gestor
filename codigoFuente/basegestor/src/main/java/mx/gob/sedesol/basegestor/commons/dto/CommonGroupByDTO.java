package mx.gob.sedesol.basegestor.commons.dto;

public class CommonGroupByDTO {

	private String nombreCampo;
	
	private Long valorNumerico;

	
	public CommonGroupByDTO(){}
	
	public CommonGroupByDTO(String nombreCampo, Long valorNumerico) {
		super();
		this.nombreCampo = nombreCampo;
		this.valorNumerico = valorNumerico;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public Long getValorNumerico() {
		return valorNumerico;
	}

	public void setValorNumerico(Long valorNumerico) {
		this.valorNumerico = valorNumerico;
	}
	
	
	
	
}
