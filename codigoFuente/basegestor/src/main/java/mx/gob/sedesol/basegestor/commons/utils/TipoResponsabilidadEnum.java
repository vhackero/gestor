package mx.gob.sedesol.basegestor.commons.utils;

public enum TipoResponsabilidadEnum {
	

	FACILITADOR(1,"Facilitador"),
	COORDINADOR_ACADEMICO(2,"Coordinador académico"),
	RESPONSABLE_DE_PRODUCCION(3,"Responsable de producción"),
	EXPERTO_EN_CONTENIDO(4,"Experto en contenido"),
	DISENADOR_INSTRUCCIONAL(5,"Diseñador instruccional"),
	DISENADOR_ELEARNING(6,"Diseñador elearning"),
	DESARROLLADOR_ELEARNING(7,"Desarrollador elearning");
	
	private String valor;	
	private Integer id;
	
	private TipoResponsabilidadEnum(Integer id,String valor) {
		this.valor = valor;
		this.id = id;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public static TipoResponsabilidadEnum getTipoResponsabilidadEnum(Integer id) {
		for (TipoResponsabilidadEnum elemento : TipoResponsabilidadEnum.values()) {
			if (elemento.getId().equals(id)) {
				return elemento;
			}
		}
		return null;
	}

	

}
