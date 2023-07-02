package mx.gob.sedesol.gestorweb.beans.analisisdatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.AnalisisDatosUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.EstadisticasUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.EstatusPersonaEnum;
import mx.gob.sedesol.basegestor.commons.utils.GeneroEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.OrdenGobiernoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatEntidadFederativa;
import mx.gob.sedesol.basegestor.service.impl.analisisdatos.AnalisisDatosUsuarioFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.config.listener.SessionCounterListener;

@ManagedBean
@ViewScoped
public class ReporteUsuariosBean extends BaseBean {

	private static final Logger log = Logger.getLogger(ReporteUsuariosBean.class);

	private static final long serialVersionUID = 1L;

	ObjectMapper jsonMapper;

	@ManagedProperty("#{analisisDatosUsuarioFacade}")
	private AnalisisDatosUsuarioFacade facade;

	// busqueda
	private List<CatalogoComunDTO> entidadesFederativas;
	private List<MunicipioDTO> municipios;
	private DomicilioPersonaDTO criterios;
	private List<DomicilioPersonaDTO> personas;
	private List<AnalisisDatosUsuarioDTO> tblDatosPersona;
	private List<Boolean> visible;
	private String tipoDatoFechas;
	private String nombreEntidad;
	private String nombreMunicipio;
	private Boolean renderizarEstado;
	private Boolean renderizarMunicipio;
	private EstadisticasUsuarioDTO estadisticas;
	private List<String> rangoEdades;
	private String seleccionRangoEdad;

	public ReporteUsuariosBean() {
		jsonMapper = new ObjectMapper();
		municipios = new ArrayList<>();
		criterios = new DomicilioPersonaDTO();
		criterios.setPersona(new PersonaDTO());
		tblDatosPersona = new ArrayList<>();
		renderizarEstado = Boolean.FALSE;
		renderizarMunicipio = Boolean.FALSE;
		rangoEdades = new ArrayList<>();

		// visible = Arrays.asList(true, true, true, true, true, true, true,
		// false, false, false, false, false, false,
		// false, false, false, false);

	}

	@PostConstruct
	public void init() {
		entidadesFederativas = facade.getCatEntidadFederativaService().findAll(CatEntidadFederativa.class);
		visible = Arrays.asList(true, true, true, true, true, true, true, false, false, false, false, false, false,
				false, false, false);
		rangoEdades = obtenerRangoEdades();

	}

	public String obtenerNombreOrdenPorId(String idOrden) {
		String nombreOrden = "";
		if(ObjectUtils.isNotNull(idOrden)) {
			for(OrdenGobiernoEnum ordenEnum : OrdenGobiernoEnum.values()){
				if(idOrden.equals(ordenEnum.getValor())) {
					nombreOrden = ordenEnum.getDescripcion();
				}
			}
		}else {
			return nombreOrden;
		}
		return nombreOrden;
	}
	
	
	private List<String> obtenerRangoEdades() {
		List<String> rangoEdades = new ArrayList<>();
		rangoEdades.add("18 a 29 años");
		rangoEdades.add("30 a 39 años");
		rangoEdades.add("40 a 49 años");
		rangoEdades.add("50 a 65 años");
		return rangoEdades;
	}

	public String tipoDatoFecha(String tipoFecha) {
		String rango = "";
		switch (tipoFecha) {
		case "1":
			rango = "INICIAN Y TERMINAN ENTRE:";
			break;
		case "2":
			rango = "INICIAN ENTRE:";
			break;
		case "3":
			rango = "TERMINAN ENTRE:";
			break;

		}

		return rango;
	}

	public void buscaUsuariosCriterios() {
		personas = facade.getDomicilioPersonaService().busquedaPorCriteriosReporteUsuario(criterios, tipoDatoFechas);
		tblDatosPersona = facade.llenarTabla(personas, seleccionRangoEdad, rangoEdades);
		validarFechaInicialFinal();
		renderizadoEstadoMunicipio();
		pintarEstado();
		pintarMunicipio();
	}

	public Integer obtenerPorcentajeUsuariosEnLinea(){
		DomicilioPersonaDTO criteriosBusqueda = new DomicilioPersonaDTO();
		PersonaDTO personaCriterios = new PersonaDTO();
		personaCriterios.setActivo(Boolean.TRUE);
		criteriosBusqueda.setPersona(personaCriterios);
		
		List<DomicilioPersonaDTO> totalPersonasActivas = new ArrayList<>();
		totalPersonasActivas = facade.getDomicilioPersonaService().busquedaPorCriterios(criteriosBusqueda, null);
		
		if(!totalPersonasActivas.isEmpty()){
			int totalPersonas = totalPersonasActivas.size();
			int personasEnSesion = obtenerUsuariosEnLinea(); 
			int porcentaje = personasEnSesion * 100 / totalPersonas;
			log.info("Total personas: "+totalPersonas);
			log.info("Personas en sesion: "+personasEnSesion);
			log.info("Porcentaje: "+porcentaje);
			return porcentaje;
		}else{
			return 0;
		}
		
		
	}
	
	public int obtenerUsuariosEnLinea() {
		
		return SessionCounterListener.getTotalActiveSession();
	}

	private void validarFechaInicialFinal() {
		if (!ObjectUtils.isNullOrEmpty(tipoDatoFechas)) {

			if (ObjectUtils.isNull(criterios.getPersona().getFechaInicial())
					|| ObjectUtils.isNull(criterios.getPersona().getFechaFinal())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Datos requeridos:", "La fecha inicial y final son requeridas"));

			}
		}

	}

	public void renderizadoEstadoMunicipio() {
		if (criterios.getIdEntidadFederativa() != 0) {
			renderizarEstado = Boolean.TRUE;
		} else {
			renderizarEstado = Boolean.FALSE;
		}
		if (!ObjectUtils.isNullOrEmpty(criterios.getIdMunicipio())) {
			renderizarMunicipio = Boolean.TRUE;
		} else {
			renderizarMunicipio = Boolean.FALSE;
		}
	}

	public void generaEstadisticas() {
		estadisticas = facade.generarEstadisticas(tblDatosPersona);
		try {
			String estadisticasStr = jsonMapper.writeValueAsString(estadisticas);
			RequestContext.getCurrentInstance().addCallbackParam("estadisticas", estadisticasStr);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	public void pintarEstado() {
		if (criterios.getIdEntidadFederativa() != 0) {
			for (CatalogoComunDTO elemento : entidadesFederativas) {
				if (elemento.getId().intValue() == criterios.getIdEntidadFederativa()) {
					nombreEntidad = elemento.getNombre();
				}
			}
		}

	}

	public void pintarMunicipio() {
		if (!ObjectUtils.isNullOrEmpty(criterios.getIdMunicipio())) {
			for (MunicipioDTO municipio : municipios) {
				if (municipio.getIdMunicipio().equals(criterios.getIdMunicipio())) {
					nombreMunicipio = municipio.getNombre();
				}
			}
		}
	}

	public void scrollArriba() {
		RequestContext.getCurrentInstance().scrollTo("form:modalInformeUsuarios");
	}

	public void limpiarCampos() {
		criterios.getPersona().setTipoUsuario(null);
		criterios.getPersona().setActivo(null);
		criterios.setIdEntidadFederativa(0);
		criterios.setIdMunicipio(null);
		criterios.getPersona().setGenero(null);
		seleccionRangoEdad = null;
		tipoDatoFechas = null;
		criterios.getPersona().setFechaInicial(null);
		criterios.getPersona().setFechaFinal(null);
		tblDatosPersona = new ArrayList<>();

	}

	public void onEstadoChange(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idEntidad = (Integer) e.getNewValue();
			municipios = facade.getMunicipioService().buscarPorEntidadFederativa(idEntidad);
		} else {
			municipios = new ArrayList<>();
			criterios.setIdMunicipio(null);
		}
	}

	public void onToggle(ToggleEvent e) {
		visible.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public GeneroEnum[] generos() {
		return GeneroEnum.values();
	}

	public TipoUsuarioEnum[] tiposUsuario() {
		return TipoUsuarioEnum.values();
	}

	public EstatusPersonaEnum[] estatusPersona() {
		return EstatusPersonaEnum.values();
	}

	public List<CatalogoComunDTO> getEntidadesFederativas() {
		return entidadesFederativas;
	}

	public void setEntidadesFederativas(List<CatalogoComunDTO> entidadesFederativas) {
		this.entidadesFederativas = entidadesFederativas;
	}

	public List<MunicipioDTO> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<MunicipioDTO> municipios) {
		this.municipios = municipios;
	}

	public AnalisisDatosUsuarioFacade getFacade() {
		return facade;
	}

	public void setFacade(AnalisisDatosUsuarioFacade facade) {
		this.facade = facade;
	}

	public DomicilioPersonaDTO getCriterios() {
		return criterios;
	}

	public void setCriterios(DomicilioPersonaDTO criterios) {
		this.criterios = criterios;
	}

	public List<DomicilioPersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<DomicilioPersonaDTO> personas) {
		this.personas = personas;
	}

	public List<AnalisisDatosUsuarioDTO> getTblDatosPersona() {
		return tblDatosPersona;
	}

	public void setTblDatosPersona(List<AnalisisDatosUsuarioDTO> tblDatosPersona) {
		this.tblDatosPersona = tblDatosPersona;
	}

	public List<Boolean> getVisible() {
		return visible;
	}

	public void setVisible(List<Boolean> visible) {
		this.visible = visible;
	}

	public EstadisticasUsuarioDTO getEstadisticas() {
		return estadisticas;
	}

	public void setEstadisticas(EstadisticasUsuarioDTO estadisticas) {
		this.estadisticas = estadisticas;
	}

	public String getTipoDatoFechas() {
		return tipoDatoFechas;
	}

	public void setTipoDatoFechas(String tipoDatoFechas) {
		this.tipoDatoFechas = tipoDatoFechas;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public Boolean getRenderizarEstado() {
		return renderizarEstado;
	}

	public void setRenderizarEstado(Boolean renderizarEstado) {
		this.renderizarEstado = renderizarEstado;
	}

	public Boolean getRenderizarMunicipio() {
		return renderizarMunicipio;
	}

	public void setRenderizarMunicipio(Boolean renderizarMunicipio) {
		this.renderizarMunicipio = renderizarMunicipio;
	}

	public List<String> getRangoEdades() {
		return rangoEdades;
	}

	public void setRangoEdades(List<String> rangoEdades) {
		this.rangoEdades = rangoEdades;
	}

	public String getSeleccionRangoEdad() {
		return seleccionRangoEdad;
	}

	public void setSeleccionRangoEdad(String seleccionRangoEdad) {
		this.seleccionRangoEdad = seleccionRangoEdad;
	}


}
