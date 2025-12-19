package mx.gob.sedesol.basegestor.commons.dto.inscripcion;

public class CatProcesoInscripcionDTO {
	private Long id;
	private String nombre;

	public CatProcesoInscripcionDTO() {
	}

	public CatProcesoInscripcionDTO(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "CatProcesoInscripcionDTO [id=" + id + ", nombre=" + nombre + "]";
	}

}
