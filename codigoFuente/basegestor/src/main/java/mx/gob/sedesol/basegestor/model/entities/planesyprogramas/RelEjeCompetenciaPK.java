package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

/**
 * The primary key class for the rel_ejes_competencias database table.
 * 
 */
public class RelEjeCompetenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
		private static final long serialVersionUID = 1L;
		
		private Integer idMallaCurricular;
		
		private Integer idCompetenciaEspecifica;
		
		public RelEjeCompetenciaPK(){}

		public Integer getIdMallaCurricular() {
			return idMallaCurricular;
		}
		public void setIdMallaCurricular(Integer idMallaCurricular) {
			this.idMallaCurricular = idMallaCurricular;
		}
		public Integer getIdCompetenciaEspecifica() {
			return idCompetenciaEspecifica;
		}
		public void setIdCompetenciaEspecifica(Integer idCompetenciaEspecifica) {
			this.idCompetenciaEspecifica = idCompetenciaEspecifica;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((idMallaCurricular == null) ? 0 : idMallaCurricular.hashCode());
			result = prime * result + ((idCompetenciaEspecifica == null) ? 0 : idCompetenciaEspecifica.hashCode());
			return result;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RelEjeCompetenciaPK other = (RelEjeCompetenciaPK) obj;
			if (idMallaCurricular == null) {
				if (other.idMallaCurricular != null)
					return false;
			} else if (!idMallaCurricular.equals(other.idMallaCurricular))
				return false;
			if (idCompetenciaEspecifica == null) {
				if (other.idCompetenciaEspecifica != null)
					return false;
			} else if (!idCompetenciaEspecifica.equals(other.idCompetenciaEspecifica))
				return false;
			return true;
		}
}
