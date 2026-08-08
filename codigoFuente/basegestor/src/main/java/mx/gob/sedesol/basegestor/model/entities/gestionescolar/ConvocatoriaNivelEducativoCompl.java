package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Objects;

public class ConvocatoriaNivelEducativoCompl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idNivelEnsenanza;
	private String nombreivelEnsenanza;
	private Integer idPlan;
	private String nombrePlan;
        private Integer idPrograma;
        private String nombrePrograma;
        private String nombreBloque;
        private String nombreSemestre;
           private Boolean seleccionado;

	    public Boolean getSeleccionado() {
	        return seleccionado;
	    }

	    public void setSeleccionado(Boolean seleccionado) {
	        this.seleccionado = seleccionado;
	    }
	
	 @Override
	    public String toString() {
	        return "ConvocatoriaNivelEducativoCompl [idNivelEnsenanza=" + idNivelEnsenanza 
	                + ", nombreNivelEnsenanza=" + nombreivelEnsenanza 
	                + ", idPlan=" + idPlan 
	                + ", nombrePlan=" + nombrePlan 
                        + ", idPrograma=" + idPrograma
                        + ", nombrePrograma=" + nombrePrograma
                        + ", nombreBloque=" + nombreBloque
                        + ", nombreSemestre=" + nombreSemestre + "]";
	    }

	@Override
	public int hashCode() {
		return Objects.hash(idNivelEnsenanza, idPlan, idPrograma);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ConvocatoriaNivelEducativoCompl)) {
			return false;
		}
		ConvocatoriaNivelEducativoCompl other = (ConvocatoriaNivelEducativoCompl) obj;
		return Objects.equals(idNivelEnsenanza, other.idNivelEnsenanza)
				&& Objects.equals(idPlan, other.idPlan)
				&& Objects.equals(idPrograma, other.idPrograma);
	}
	 
	public Integer getIdNivelEnsenanza() {
		return idNivelEnsenanza;
	}
	public void setIdNivelEnsenanza(Integer idNivelEnsenanza) {
		this.idNivelEnsenanza = idNivelEnsenanza;
	}
	public String getNombreivelEnsenanza() {
		return nombreivelEnsenanza;
	}
	public void setNombreivelEnsenanza(String nombreivelEnsenanza) {
		this.nombreivelEnsenanza = nombreivelEnsenanza;
	}
	public Integer getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	public String getNombrePlan() {
		return nombrePlan;
	}
	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}
	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getNombrePrograma() {
		return nombrePrograma;
	}
        public void setNombrePrograma(String nombrePrograma) {
                this.nombrePrograma = nombrePrograma;
        }

        public String getNombreBloque() {
                return nombreBloque;
        }

        public void setNombreBloque(String nombreBloque) {
                this.nombreBloque = nombreBloque;
        }

		public String getNombreSemestre() {
			return nombreSemestre;
		}

		public void setNombreSemestre(String nombreSemestre) {
			this.nombreSemestre = nombreSemestre;
		}
	
	

}
