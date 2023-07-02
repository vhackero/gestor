package mx.gob.sedesol.basegestor.commons.utils;

public enum ClasificacionBadgeEnum {
	BASICO(1, "Básico"), INTERMEDIO(2, "Intermedio"), AVANZADO(3, "Avanzado");
	private Integer id;
	private String desc;

	private ClasificacionBadgeEnum(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public static String getClasificacionBadgeEnum(Integer id) {
		for (ClasificacionBadgeEnum elemento : ClasificacionBadgeEnum.values()) {
			if (elemento.getId().equals(id)) {
				return elemento.getDesc();
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
