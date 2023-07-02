package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionEscolarEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoResponsabilidadEc;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;
import mx.gob.sedesol.basegestor.springinit.GestionEscolarServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean
@SessionScoped
public class ResponsablesEventoCapBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ResponsablesEventoCapBean.class);

	@ManagedProperty(value = "#{gestionEscolarServiceAdapter}")
	private GestionEscolarServiceAdapter gestionEscolarServiceAdapter;

	@ManagedProperty(value = "#{personaService}")
	private PersonaService personaService;

	@ManagedProperty(value = "#{personaResponsabilidadesService}")
	private PersonaResponsabilidadesService personaResponsabilidadesService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CatalogoComunDTO> responsabilidades;
	private CatalogoComunDTO responsabilidad;

	private List<PersonaResponsabilidadesDTO> responsables;
	private List<PersonaDTO> sinResponsabilidad;
	private PersonaResponsabilidadesDTO responsable;
	private Integer idResponsabilidad;

	private List<PersonaDTO> personas;
	private PersonaDTO filtroPersona;
	private List<PersonaDTO> listaDinamicaPersonas;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		listaDinamicaPersonas = new ArrayList<PersonaDTO>();
		setFiltroPersona(new PersonaDTO());
		idResponsabilidad = 0;
		responsabilidad = new CatalogoComunDTO();
		responsabilidad.setId(0);
		responsables = new ArrayList<PersonaResponsabilidadesDTO>();
		responsable = new PersonaResponsabilidadesDTO();
		responsabilidades = getGestionEscolarServiceAdapter()
				.getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum.CAT_TIPO_RESPONSABILIDAD_EC)
				.findAll(CatTipoResponsabilidadEc.class);
	}

	public void busquedaDinamica() {
		listaDinamicaPersonas = new ArrayList<PersonaDTO>();
		personas = personaService.busquedaPorCriterios(filtroPersona);
		for (PersonaDTO p : personas) {
			for (int i = 0; i < sinResponsabilidad.size(); i++) {
				if (p.getIdPersona().equals(sinResponsabilidad.get(i).getIdPersona())) {
					listaDinamicaPersonas.add(p);
				}
			}
		}
	}

	public void eliminarResponsable() {
		try {
			ResultadoDTO<PersonaResponsabilidadesDTO> resultado = personaResponsabilidadesService.eliminar(responsable);
			responsables = new ArrayList<PersonaResponsabilidadesDTO>();
			responsables = personaResponsabilidadesService.obtienePersonasPorResponsabilidad(getIdResponsabilidad());
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ELI_RES_EVT", "", requestActual(),
					TipoServicioEnum.LOCAL);
			agregarFlashMessage("Se eliminó responsable.", null, FacesMessage.SEVERITY_INFO);
			logger.info("si entro");
		} catch (Exception e) {
			logger.info("no entro");
			agregarFlashMessage(
					"El responsable no se puede eliminar porque se encuentra asignado a un evento de capacitación.",
					null, FacesMessage.SEVERITY_INFO);
		}

	}

	public String cancelar() {
		return ConstantesGestorWeb.REPONSABLES_EVENTO;
	}

	public String agregarListaResponsables() {
		int cantidadResponsables = 0;
		for (PersonaDTO p : listaDinamicaPersonas) {
			if (p.isSeleccionado()) {
				PersonaResponsabilidadesDTO dto = new PersonaResponsabilidadesDTO();
				dto.setFechaRegistro(new Date());
				dto.setCatTipoResponsabilidadEc(responsabilidad);
				dto.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
				dto.setTblPersona(p);
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "AGR_RES_EVT", "", requestActual(),
						TipoServicioEnum.LOCAL);
				personaResponsabilidadesService.guardar(dto);
				cantidadResponsables++;
			}
		}
		sinResponsabilidad = new ArrayList<PersonaDTO>();
		responsables = new ArrayList<PersonaResponsabilidadesDTO>();
		responsables = personaResponsabilidadesService.obtienePersonasPorResponsabilidad(getIdResponsabilidad());

		if (cantidadResponsables == 0) {
			agregarMsgInfo("No se han seleccionado responsables.", null);
			return null;
		} else if (cantidadResponsables == 1) {
			agregarFlashMessage("Responsable agregado.", null, FacesMessage.SEVERITY_INFO);
			return ConstantesGestorWeb.REPONSABLES_EVENTO;
		} else {
			agregarFlashMessage("Responsables agregados", null, FacesMessage.SEVERITY_INFO);
			return ConstantesGestorWeb.REPONSABLES_EVENTO;
		}

	}

	public String navegaAgregarResponsable() {
		filtroPersona = new PersonaDTO();
		sinResponsabilidad = getPersonaService().findAll();
		listaDinamicaPersonas = new ArrayList<PersonaDTO>();
		for (PersonaResponsabilidadesDTO pr : responsables) {
			for (int i = 0; i < sinResponsabilidad.size(); i++) {
				if (pr.getTblPersona().getIdPersona().equals(sinResponsabilidad.get(i).getIdPersona())) {
					sinResponsabilidad.remove(i);
				}
			}
		}
		for (PersonaDTO p : sinResponsabilidad) {
			listaDinamicaPersonas.add(p);
		}
		return ConstantesGestorWeb.AGREGAR_RESPONSABLE;
	}

	public void onChangeTpoResponsabilidad(ValueChangeEvent e) {
		responsables = new ArrayList<PersonaResponsabilidadesDTO>();
		if (ObjectUtils.isNotNull(e)) {
			idResponsabilidad = (Integer) e.getNewValue();
			responsables = personaResponsabilidadesService.obtienePersonasPorResponsabilidad(getIdResponsabilidad());
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_RES_EVT", "", requestActual(),
					TipoServicioEnum.LOCAL);

		}
	}

	public List<CatalogoComunDTO> getResponsabilidades() {
		return responsabilidades;
	}

	public void setResponsabilidades(List<CatalogoComunDTO> responsabilidades) {
		this.responsabilidades = responsabilidades;
	}

	public CatalogoComunDTO getResponsabilidad() {
		return responsabilidad;
	}

	public void setResponsabilidad(CatalogoComunDTO responsabilidad) {
		this.responsabilidad = responsabilidad;
	}

	public GestionEscolarServiceAdapter getGestionEscolarServiceAdapter() {
		return gestionEscolarServiceAdapter;
	}

	public void setGestionEscolarServiceAdapter(GestionEscolarServiceAdapter gestionEscolarServiceAdapter) {
		this.gestionEscolarServiceAdapter = gestionEscolarServiceAdapter;
	}

	public PersonaResponsabilidadesService getPersonaResponsabilidadesService() {
		return personaResponsabilidadesService;
	}

	public void setPersonaResponsabilidadesService(PersonaResponsabilidadesService personaResponsabilidadesService) {
		this.personaResponsabilidadesService = personaResponsabilidadesService;
	}

	public List<PersonaResponsabilidadesDTO> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<PersonaResponsabilidadesDTO> responsables) {
		this.responsables = responsables;
	}

	public PersonaResponsabilidadesDTO getResponsable() {
		return responsable;
	}

	public void setResponsable(PersonaResponsabilidadesDTO responsable) {
		this.responsable = responsable;
	}

	public List<PersonaDTO> getSinResponsabilidad() {
		return sinResponsabilidad;
	}

	public void setSinResponsabilidad(List<PersonaDTO> sinResponsabilidad) {
		this.sinResponsabilidad = sinResponsabilidad;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public Integer getIdResponsabilidad() {
		return idResponsabilidad;
	}

	public void setIdResponsabilidad(Integer idResponsabilidad) {
		this.idResponsabilidad = idResponsabilidad;
	}

	public PersonaDTO getFiltroPersona() {
		return filtroPersona;
	}

	public void setFiltroPersona(PersonaDTO filtroPersona) {
		this.filtroPersona = filtroPersona;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}

	public List<PersonaDTO> getListaDinamicaPersonas() {
		return listaDinamicaPersonas;
	}

	public void setListaDinamicaPersonas(List<PersonaDTO> listaDinamicaPersonas) {
		this.listaDinamicaPersonas = listaDinamicaPersonas;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
