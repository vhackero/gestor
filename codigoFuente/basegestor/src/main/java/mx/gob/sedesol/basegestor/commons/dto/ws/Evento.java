package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class Evento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2241733173380425596L;
	private String id_evento;
	private String calificacion;
	private String fecha_intento;
	private String unidad_responsable;

	public Evento() {

	}

	public Evento(String id_evento, String calificacion, String fecha_intento) {
		this.id_evento = id_evento;
		this.calificacion = calificacion;
		this.fecha_intento = fecha_intento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_evento == null) ? 0 : id_evento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (id_evento == null) {
			if (other.id_evento != null)
				return false;
		} else if (!id_evento.equals(other.id_evento))
			return false;
		return true;
	}

	public String getId_evento() {
		return id_evento;
	}

	public void setId_evento(String id_evento) {
		this.id_evento = id_evento;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getFecha_intento() {
		return fecha_intento;
	}

	public void setFecha_intento(String fecha_intento) {
		this.fecha_intento = fecha_intento;
	}

	public String getUnidad_responsable() {
		return unidad_responsable;
	}

	public void setUnidad_responsable(String unidad_responsable) {
		this.unidad_responsable = unidad_responsable;
	}

}
