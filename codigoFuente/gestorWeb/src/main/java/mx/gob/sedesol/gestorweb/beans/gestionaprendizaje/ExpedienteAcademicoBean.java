package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;

@ManagedBean
@ViewScoped
public class ExpedienteAcademicoBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ExpedienteAcademicoBean.class);

	@ManagedProperty("#{personaServiceFacade}")
	private PersonaServiceFacade personaServiceFacade;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<PersonaDTO> personas;

	private PersonaDTO personaFiltros;

	public ExpedienteAcademicoBean() {
		personaFiltros = new PersonaDTO();
		personas = new ArrayList<>();
	}

	@PostConstruct
	public void init() {

	}

	public void buscarPersonaPorCriterios() {
		personas = personaServiceFacade.buscarPersonaPorCriterios(personaFiltros);
		if (!personas.isEmpty()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_EXP_ALM", "", requestActual(),
					TipoServicioEnum.LOCAL);
		}

	}

	public String obtenerTipoUsuario(int tipo) {
		TipoUsuarioEnum tipoUsuarioEnum = TipoUsuarioEnum.getTipoUsuarioEnum(tipo);
		if (ObjectUtils.isNull(tipoUsuarioEnum)) {
			return "";
		} else {
			return tipoUsuarioEnum.getDescripcion();
		}
	}

	public PersonaDTO getPersonaFiltros() {
		return personaFiltros;
	}

	public void setPersonaFiltros(PersonaDTO personaFiltros) {
		this.personaFiltros = personaFiltros;
	}

	public TipoUsuarioEnum[] getTiposUsuarios() {
		return TipoUsuarioEnum.values();
	}

	public PersonaServiceFacade getPersonaServiceFacade() {
		return personaServiceFacade;
	}

	public void setPersonaServiceFacade(PersonaServiceFacade personaServiceFacade) {
		this.personaServiceFacade = personaServiceFacade;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
