package mx.gob.sedesol.gestorweb.commons.utils;

public enum ModalidadEnum {

	ENLINEA(1,"En línea"),
	PRESENCIAL(2, "Presencial"),
	MIXTO(3, "Mixto");

	private Integer id;
	private String nombre;

	private ModalidadEnum(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public static ModalidadEnum obtieneModalidadById(Integer id){
		
		for(ModalidadEnum enm : ModalidadEnum.values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}

	public Integer getId() {
		return this.id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
}