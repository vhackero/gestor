package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.FuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.Elemento;

@SessionScoped
@ManagedBean(name = "consultaBitacoraBean")
public class ConsultaBitacoraBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ConsultaBitacoraBean.class);

	@ManagedProperty(value = "#{bitacoraService}")
	private BitacoraService bitacoraService;

	@ManagedProperty(value = "#{personaService}")
	private PersonaService personaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private BitacoraDTO bitacoraFiltro;

	private List<BitacoraDTO> listaBitacora;

	private List<PersonaDTO> personas;
	private PersonaDTO personaSeleccionada;
	private PersonaDTO filtroPersona;

	private List<Boolean> columnasVisibles;

	private String claveModulo;
	private String claveComponente;
	private String claveFuncionalidad;

	private List<Elemento> modulos;
	private List<Elemento> componentes;
	private List<Elemento> funcionalidades;

	public ConsultaBitacoraBean() {
		bitacoraFiltro = new BitacoraDTO();
		filtroPersona = new PersonaDTO();
		listaBitacora = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		columnasVisibles = Arrays.asList(false, false, true, false, false, false, true, false, true, true, false, false);
		modulos = bitacoraBean.getArbolFuncionalidades().getSubElementos();
	}

	public void cargarComponentes() {
		if (ObjectUtils.isNullOrEmpty(claveModulo)) {
			claveModulo = null;
			bitacoraFiltro.setClaveModulo(claveModulo);
			claveComponente = null;
			bitacoraFiltro.setClaveComponente(claveComponente);
			claveFuncionalidad = null;
			bitacoraFiltro.setClaveFuncionalidad(claveFuncionalidad);
		} else {
			componentes = bitacoraBean.obtenerSubElementosPorClave(claveModulo);
			bitacoraFiltro.setClaveModulo(claveModulo);
		}

	}

	public void cargarFuncionalidades() {
		if (ObjectUtils.isNullOrEmpty(claveComponente)) {
			claveComponente = null;
			bitacoraFiltro.setClaveFuncionalidad(claveComponente);
			claveFuncionalidad = null;
			bitacoraFiltro.setClaveFuncionalidad(claveFuncionalidad);
		} else {
			funcionalidades = bitacoraBean.obtenerSubElementosPorClave(claveComponente);
			bitacoraFiltro.setClaveComponente(claveComponente);
		}

	}

	public void llenarFiltroFuncionalidad() {
		if (ObjectUtils.isNullOrEmpty(claveFuncionalidad)) {
			claveFuncionalidad = null;
			bitacoraFiltro.setClaveFuncionalidad(claveFuncionalidad);
		} else {
			bitacoraFiltro.setClaveFuncionalidad(claveFuncionalidad);
		}
	}

	public String cargarBusquedaPersona() {
		filtroPersona.setUsuario(null);
		filtroPersona.setNombre(null);
		filtroPersona.setApellidoPaterno(null);
		filtroPersona.setApellidoMaterno(null);
		filtroPersona.setCurp(null);
		personas = new ArrayList<>();
		return ConstantesGestorWeb.NAVEGA_CONSULTA_BITACORA_PERSONAS;
	}

	public TipoUsuarioEnum[] getTiposUsuarios() {
		return TipoUsuarioEnum.values();
	}

	public void buscarPersonas() {
		personas = personaService.busquedaPorCriterios(filtroPersona);
	}

	public String seleccionarPersona() {
		bitacoraFiltro.setIdUsuario(String.valueOf(personaSeleccionada.getIdPersona()));
		bitacoraFiltro.setNombreCompleto(personaSeleccionada.getNombreCompleto());
		return ConstantesGestorWeb.NAVEGA_CONSULTA_BITACORA;
	}

	public void limpiarCampos() {
		bitacoraFiltro.setFechaInicio(null);
		bitacoraFiltro.setFechaFin(null);
		bitacoraFiltro.setIdUsuario(null);
		bitacoraFiltro.setNombreCompleto(null);
		bitacoraFiltro.setModulo(null);
		bitacoraFiltro.setComponente(null);
		bitacoraFiltro.setFuncionalidad(null);
		listaBitacora = new ArrayList<>();
	}

	public void cargarBitacora() {
		
		listaBitacora = bitacoraService.obtenerBitacora(bitacoraFiltro);
		if (!listaBitacora.isEmpty()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "BUS_BIT", "", requestActual(), TipoServicioEnum.LOCAL);
		}

	}

	public void listenerPostProcesamiento() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EXP_BIT", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void onToggle(ToggleEvent e) {
		columnasVisibles.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/* INICIO DE GETS Y SETS */
	public BitacoraService getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

	public List<BitacoraDTO> getListaBitacora() {
		return listaBitacora;
	}

	public void setListaBitacora(List<BitacoraDTO> listaBitacora) {
		this.listaBitacora = listaBitacora;
	}

	public BitacoraDTO getBitacoraFiltro() {
		return bitacoraFiltro;
	}

	public void setBitacoraFiltro(BitacoraDTO bitacoraFiltro) {
		this.bitacoraFiltro = bitacoraFiltro;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}

	public PersonaDTO getFiltroPersona() {
		return filtroPersona;
	}

	public void setFiltroPersona(PersonaDTO filtroPersona) {
		this.filtroPersona = filtroPersona;
	}

	public PersonaDTO getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(PersonaDTO personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public List<Boolean> getColumnasVisibles() {
		return columnasVisibles;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public String getClaveModulo() {
		return claveModulo;
	}

	public void setClaveModulo(String claveModulo) {
		this.claveModulo = claveModulo;
	}

	public String getClaveComponente() {
		return claveComponente;
	}

	public void setClaveComponente(String claveComponente) {
		this.claveComponente = claveComponente;
	}

	public String getClaveFuncionalidad() {
		return claveFuncionalidad;
	}

	public void setClaveFuncionalidad(String claveFuncionalidad) {
		this.claveFuncionalidad = claveFuncionalidad;
	}

	public List<Elemento> getModulos() {
		return modulos;
	}

	public void setModulos(List<Elemento> modulos) {
		this.modulos = modulos;
	}

	public List<Elemento> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Elemento> componentes) {
		this.componentes = componentes;
	}

	public List<Elemento> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Elemento> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

}
