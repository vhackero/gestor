package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.badges.BadgeDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.ClasificacionBadgeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LogrosDTO;

public class ContenedorLogrosDTO {
	
	private List<String> competencias;
	private List<LogrosDTO> logros;
	private List<BadgeDTO> listaBadges;
	private Integer totalPuntosCompetencias;
	private int[] cantTrofeosOroPlata;
	private BadgeDTO badge;
	private ClasificacionBadgeDTO clasificacionBadge;
	
	public List<String> getCompetencias() {
		return competencias;
	}
	public void setCompetencias(List<String> competencias) {
		this.competencias = competencias;
	}
	public List<LogrosDTO> getLogros() {
		return logros;
	}
	public void setLogros(List<LogrosDTO> logros) {
		this.logros = logros;
	}
	public Integer getTotalPuntosCompetencias() {
		return totalPuntosCompetencias;
	}
	public void setTotalPuntosCompetencias(Integer totalPuntosCompetencias) {
		this.totalPuntosCompetencias = totalPuntosCompetencias;
	}
	public int[] getCantTrofeosOroPlata() {
		return cantTrofeosOroPlata;
	}
	public void setCantTrofeosOroPlata(int[] cantTrofeosOroPlata) {
		this.cantTrofeosOroPlata = cantTrofeosOroPlata;
	}
	public BadgeDTO getBadge() {
		return badge;
	}
	public void setBadge(BadgeDTO badge) {
		this.badge = badge;
	}
	public ClasificacionBadgeDTO getClasificacionBadge() {
		return clasificacionBadge;
	}
	public void setClasificacionBadge(ClasificacionBadgeDTO clasificacionBadge) {
		this.clasificacionBadge = clasificacionBadge;
	}
	public List<BadgeDTO> getListaBadges() {
		return listaBadges;
	}
	public void setListaBadges(List<BadgeDTO> listaBadges) {
		this.listaBadges = listaBadges;
	}

}
