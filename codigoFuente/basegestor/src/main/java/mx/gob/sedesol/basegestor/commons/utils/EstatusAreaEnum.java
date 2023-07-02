package mx.gob.sedesol.basegestor.commons.utils;

public enum EstatusAreaEnum {
	ACTIVO(1,"Activo"),
	INACTIVO(2, "Inactivo");

	private Integer id;
	private String estatus;

	private EstatusAreaEnum(Integer id, String estatus) {
		this.id = id;
		this.estatus = estatus;
	}
	
	public EstatusAreaEnum obtieneEstatusRecursoById(Integer id){
		
		for(EstatusAreaEnum enm : EstatusAreaEnum.values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}

	public Integer getId() {
		return this.id;
	}
	
	public String getEstatus(){
		return this.estatus;
	}
}
