package mx.gob.sedesol.gestorweb.commons.utils;

public enum EstatusReservacionEnum {

	RESERVADO_SIN_APROBAR(2,"Reservado - Sin Aprobar","evnt_no_aprobado"),
	RESERVADO_APROBADO(3,"Aprobado y con reservaciones", "evnt_aprobado");
	

	private Integer id;
	private String etiqueta;
	private String styleSchedule;
	
	
	private EstatusReservacionEnum(Integer id, String etiqueta, String styleSchedule){
		this.id = id;
		this.etiqueta = etiqueta;
		this.styleSchedule = styleSchedule;
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
	 * @return the styleSchedule
	 */
	public String getStyleSchedule() {
		return styleSchedule;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static EstatusReservacionEnum obtieneEstatusReservacionById(Integer id){
		
		for(EstatusReservacionEnum enm : values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}
}
