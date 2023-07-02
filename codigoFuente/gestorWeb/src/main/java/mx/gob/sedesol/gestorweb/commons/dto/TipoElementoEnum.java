package mx.gob.sedesol.gestorweb.commons.dto;

public enum TipoElementoEnum {
	MODULO(1, "Módulo"), COMPONENTE(2, "Componente"), FUNCIONALIDAD(3, "Funcionalidad");
	private Integer id;
	private String nombre;

	private TipoElementoEnum(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}
}
