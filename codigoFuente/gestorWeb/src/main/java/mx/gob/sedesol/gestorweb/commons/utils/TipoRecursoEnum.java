package mx.gob.sedesol.gestorweb.commons.utils;

public enum TipoRecursoEnum {
	TECNOLOGICOS(1,"Tecnológicos"),
	MOBILIARIOS(2, "Mobiliarios");

	private Integer id;
	private String tipo;

	private TipoRecursoEnum(Integer id, String tipo) {
		this.id = id;
		this.tipo = tipo;
	}
	
	public TipoRecursoEnum obtieneTipoRecursoById(Integer id){
		
		for(TipoRecursoEnum enm : TipoRecursoEnum.values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}

	public Integer getId() {
		return this.id;
	}
	
	public String getTipo(){
		return this.tipo;
	}
}
