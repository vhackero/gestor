package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.BadgeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ContenedorLogrosDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
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
	
	@ManagedProperty(value = "#{personaRolesService}")
	private PersonaRolesService personaRolesService;

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
private boolean showLogros;
	private String styleMisCursos;
	private final java.util.Map<String, Integer> metricInvocaciones = new java.util.concurrent.ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		long inicio = System.currentTimeMillis();
		logger.info("[MisLogrosBean] Inicio init");
		Long idPersona = getUsuarioEnSession().getIdPersona();
		long tLogros = System.currentTimeMillis();
		contenedorLogros = getGrupoParticipanteService().obtenerLogrosPorIdParticipante(idPersona);
		logger.info("[MisLogrosBean] obtenerLogrosPorIdParticipante en " + (System.currentTimeMillis() - tLogros)
				+ " ms");
		badge = contenedorLogros.getBadge();
		listaBadges = contenedorLogros.getListaBadges();
		if (ObjectUtils.isNotNull(badge)) {
			long tVerificaBadges = System.currentTimeMillis();
			verificaBadgesDisponibles();
			logger.info("[MisLogrosBean] verificaBadgesDisponibles en "
					+ (System.currentTimeMillis() - tVerificaBadges) + " ms");
			long tPinta = System.currentTimeMillis();
			pintaBadges(badge.getNombre());
			logger.info("[MisLogrosBean] pintaBadges en " + (System.currentTimeMillis() - tPinta) + " ms");
		}
		styleMisCursos="col-md-6";
		long tTieneLogros = System.currentTimeMillis();
		showLogros=tieneLogros();
		logger.info("[MisLogrosBean] tieneLogros en " + (System.currentTimeMillis() - tTieneLogros) + " ms");
		logger.info("[MisLogrosBean] Fin init en " + (System.currentTimeMillis() - inicio) + " ms");
	}

	private boolean tieneLogros() {
		long inicio = System.currentTimeMillis();
		Map<String, String> mapa ;
		Integer idRol;
		List<PersonaRolDTO> rolesPersona = personaRolesService
				.obtieneRelPersonaRolesPorUsuario(getUsuarioEnSession().getUsuario());
		
		if (ObjectUtils.isNullOrEmpty(rolesPersona)) {
			idRol = null;
			showLogros=false;
		} else {
			idRol = rolesPersona.get(0).getRol().getIdRol();
			mapa = personaRolesService.obtenerFuncionalidadesRol(idRol);
			showLogros = mapa.containsKey("MIS_LOGROS");
			if(!showLogros) {
				styleMisCursos="col-md-12";
			}
		}
		
		logger.info("[MisLogrosBean] tieneLogros? " + showLogros + ", roles=" + (rolesPersona != null ? rolesPersona.size() : 0)
				+ " en " + (System.currentTimeMillis() - inicio) + " ms");
		return showLogros;
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
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getListaBadges", inicio, "total=" + (listaBadges != null ? listaBadges.size() : 0));
		return listaBadges;
	}

	public void setListaBadges(List<BadgeDTO> listaBadges) {
		this.listaBadges = listaBadges;
	}

	public Integer getPuntosParaSigNivel() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getPuntosParaSigNivel", inicio, "puntosParaSigNivel=" + puntosParaSigNivel);
		return puntosParaSigNivel;
	}

	public void setPuntosParaSigNivel(Integer puntosParaSigNivel) {
		this.puntosParaSigNivel = puntosParaSigNivel;
	}

	public Integer getPuntosParaSigBadge() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getPuntosParaSigBadge", inicio, "puntosParaSigBadge=" + puntosParaSigBadge);
		return puntosParaSigBadge;
	}

	public void setPuntosParaSigBadge(Integer puntosParaSigBadge) {
		this.puntosParaSigBadge = puntosParaSigBadge;
	}

	public String getSigBadgeNombre() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getSigBadgeNombre", inicio, "sigBadgeNombre=" + sigBadgeNombre);
		return sigBadgeNombre;
	}

	public void setSigBadgeNombre(String sigBadgeNombre) {
		this.sigBadgeNombre = sigBadgeNombre;
	}

	public String getInsignia1() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getInsignia1", inicio, "insignia1=" + insignia1);
		return insignia1;
	}

	public void setInsignia1(String insignia1) {
		this.insignia1 = insignia1;
	}

	public String getInsignia2() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getInsignia2", inicio, "insignia2=" + insignia2);
		return insignia2;
	}

	public void setInsignia2(String insignia2) {
		this.insignia2 = insignia2;
	}

	public String getInsignia3() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getInsignia3", inicio, "insignia3=" + insignia3);
		return insignia3;
	}

	public void setInsignia3(String insignia3) {
		this.insignia3 = insignia3;
	}

	public String getInsignia4() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getInsignia4", inicio, "insignia4=" + insignia4);
		return insignia4;
	}

	public void setInsignia4(String insignia4) {
		this.insignia4 = insignia4;
	}

	public BadgeDTO getBadge() {
		long inicio = System.currentTimeMillis();
		logInvocacionVista("getBadge", inicio, "badge=" + (badge != null ? badge.getNombre() : "null"));
		return badge;
	}

	public void setBadge(BadgeDTO badge) {
		this.badge = badge;
	}


	private void logInvocacionVista(String metodo, long inicio, String extra) {
		int llamadas = metricInvocaciones.merge(metodo, 1, Integer::sum);
		if (llamadas <= 5) {
			logger.info("[MisLogrosBean] " + metodo + " #" + llamadas + " (" + extra + ") en "
					+ (System.currentTimeMillis() - inicio) + " ms");
		}
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public PersonaRolesService getPersonaRolesService() {
		return personaRolesService;
	}

	public void setPersonaRolesService(PersonaRolesService personaRolesService) {
		this.personaRolesService = personaRolesService;
	}

	public boolean isShowLogros() {
		return showLogros;
	}

	public void setShowLogros(boolean showLogros) {
		this.showLogros = showLogros;
	}

	public String getStyleMisCursos() {
		return styleMisCursos;
	}

	public void setStyleMisCursos(String styleMisCursos) {
		this.styleMisCursos = styleMisCursos;
	}
	

}
