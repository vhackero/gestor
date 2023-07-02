package mx.gob.sedesol.basegestor.commons.utils;



public enum EstatusAmbienteVirtualAprendizajeEnum {

	EN_SOLICITUD(1,"En solicitud"),
	EN_CONSTRUCCION(2,"En construcción"),
	ACTIVO(3,"Activo"),
	INACTIVO(4,"Inactivo");

	
	private Integer id;
	private String etiqueta;
	
	
	private EstatusAmbienteVirtualAprendizajeEnum(Integer id, String etiqueta){
		this.id = id;
		this.etiqueta = etiqueta;
	}
	
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public EstatusAmbienteVirtualAprendizajeEnum obtieneEstatusAVAById(Integer id){
		
		for(EstatusAmbienteVirtualAprendizajeEnum enm : values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}

	
}



