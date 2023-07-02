package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoConstanciaEnum {
	ACREDITACION(1,"Acreditaci\u00F3n"),
	PARTICIPACION(2,"Participaci\u00F3n");
	
	TipoConstanciaEnum(Integer id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}
	
	private Integer id;
	private String nombre;

	public Integer getId() {
		return this.id;
	}
	public String getNombre() {
		return this.nombre;
	}
	public static TipoConstanciaEnum getTipoConstanciaById(Integer id){
		for(TipoConstanciaEnum enm: values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}

}
