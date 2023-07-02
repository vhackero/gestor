package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.badges.BadgeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ContenedorLogrosDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;

@ManagedBean
@ViewScoped
public class MisLogrosBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MisLogrosBean.class);

	@ManagedProperty(value = "#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private ContenedorLogrosDTO contenedorLogros;
	private List<BadgeDTO> listaBadges;
	private BadgeDTO badge;
	private Integer puntosParaSigNivel = 0;
	private Integer puntosParaSigBadge = 0;
	private String sigBadgeNombre;
	private String insignia1 = "insigniaBloqueada";
	private String insignia2 = "insigniaBloqueada";
	private String insignia3 = "insigniaBloqueada";
	private String insignia4 = "insigniaBloqueada";

	@PostConstruct
	public void init() {
		Long idPersona = getUsuarioEnSession().getIdPersona();
		contenedorLogros = getGrupoParticipanteService().obtenerLogrosPorIdParticipante(idPersona);
		badge = contenedorLogros.getBadge();
		listaBadges = contenedorLogros.getListaBadges();
		if (ObjectUtils.isNotNull(badge)) {
			verificaBadgesDisponibles();
			pintaBadges(badge.getNombre());
		}
	}

	private void verificaBadgesDisponibles() {
		int contadorPosicionBadge = 0;
		int posicionBadgeEnLista = 0;
		/* Buscamos el badge actual en la lista de badges */
		for (BadgeDTO badgeAuxiliar : listaBadges) {
			if (badge.getCalificacionMinima().intValue() == badgeAuxiliar.getCalificacionMinima().intValue()) {
				posicionBadgeEnLista = contadorPosicionBadge;
			}
			contadorPosicionBadge++;
		}

		/* Buscamos la siguiente calificacion minima y el siguiente badge */

		/* Verificamos que no se desborde */
		if ((posicionBadgeEnLista + 1) < listaBadges.size()) {

			/*
			 * Si el nombre del badge del siguiente nivel es el mismo, obtenemos
			 * los puntos para el siguiente nivel
			 */
			if (badge.getNombre().equals(listaBadges.get(posicionBadgeEnLista + 1).getNombre())) {
				puntosParaSigNivel = listaBadges.get(posicionBadgeEnLista + 1).getCalificacionMinima().intValue()
						- contenedorLogros.getTotalPuntosCompetencias();
			}
			/*
			 * Nos ubicamos en la posicion de la lista de badges y a partir de
			 * ahi buscamos el siguiente badge
			 */
			for (int i = posicionBadgeEnLista; i < listaBadges.size() - 1; i++) {

				/*
				 * Si encontramos un badge con un nombre distinto, ese es el
				 * siguiente badge y obtenemos los puntos faltantes para el
				 * mismo
				 */
				if (!badge.getNombre().equals(listaBadges.get(i).getNombre())) {
					puntosParaSigBadge = listaBadges.get(i).getCalificacionMinima()
							- contenedorLogros.getTotalPuntosCompetencias();
					sigBadgeNombre = listaBadges.get(i).getNombre();
					i = listaBadges.size();
				}
			}
		}

	}

	private void pintaBadges(String nombreBadgeLimite) {
		/* Pintamos los badges que ya conseguimos */

		for (int i = 0; i < listaBadges.size() - 1; i++) {

			if (i == 0) {
				insignia1 = "insigniaDesbloqueada";
			} else if (i == 3) {
				insignia2 = "insigniaDesbloqueada";
			} else if (i == 6) {
				insignia3 = "insigniaDesbloqueada";
			} else if (i == 9) {
				insignia4 = "insigniaDesbloqueada";
			}

			if (nombreBadgeLimite.equals(listaBadges.get(i).getNombre())) {
				i = listaBadges.size();
			}
		}
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	public ContenedorLogrosDTO getContenedorLogros() {
		return contenedorLogros;
	}

	public void setContenedorLogros(ContenedorLogrosDTO contenedorLogros) {
		this.contenedorLogros = contenedorLogros;
	}

	public List<BadgeDTO> getListaBadges() {
		return listaBadges;
	}

	public void setListaBadges(List<BadgeDTO> listaBadges) {
		this.listaBadges = listaBadges;
	}

	public Integer getPuntosParaSigNivel() {
		return puntosParaSigNivel;
	}

	public void setPuntosParaSigNivel(Integer puntosParaSigNivel) {
		this.puntosParaSigNivel = puntosParaSigNivel;
	}

	public Integer getPuntosParaSigBadge() {
		return puntosParaSigBadge;
	}

	public void setPuntosParaSigBadge(Integer puntosParaSigBadge) {
		this.puntosParaSigBadge = puntosParaSigBadge;
	}

	public String getSigBadgeNombre() {
		return sigBadgeNombre;
	}

	public void setSigBadgeNombre(String sigBadgeNombre) {
		this.sigBadgeNombre = sigBadgeNombre;
	}

	public String getInsignia1() {
		return insignia1;
	}

	public void setInsignia1(String insignia1) {
		this.insignia1 = insignia1;
	}

	public String getInsignia2() {
		return insignia2;
	}

	public void setInsignia2(String insignia2) {
		this.insignia2 = insignia2;
	}

	public String getInsignia3() {
		return insignia3;
	}

	public void setInsignia3(String insignia3) {
		this.insignia3 = insignia3;
	}

	public String getInsignia4() {
		return insignia4;
	}

	public void setInsignia4(String insignia4) {
		this.insignia4 = insignia4;
	}

	public BadgeDTO getBadge() {
		return badge;
	}

	public void setBadge(BadgeDTO badge) {
		this.badge = badge;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
