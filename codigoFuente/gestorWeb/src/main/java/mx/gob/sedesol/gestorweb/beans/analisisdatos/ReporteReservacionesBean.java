package mx.gob.sedesol.gestorweb.beans.analisisdatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.CritBusquedaReporteReservDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.EstadisticasEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.EstadisticasReservacionesDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteReservacionesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.utils.EstatusReservacionEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoEventoEnum;
import mx.gob.sedesol.basegestor.service.impl.analisisdatos.AnalisisDatosReservacionesFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean
@ViewScoped
public class ReporteReservacionesBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(ReporteReservacionesBean.class);
	
	ObjectMapper jsonMapper;

	@ManagedProperty("#{analisisDatosReservacionesFacade}")
	private AnalisisDatosReservacionesFacade facade;

	private CritBusquedaReporteReservDTO criteriosBusq;
	private List<SedeDTO> sedes;
	private List<ConfiguracionAreaDTO> areasConfiguradas;
	private List<Boolean> visible;
	private List<ReporteReservacionesDTO> tblReservaciones;
	private List<RelSolicitudEventoCapacitacionDTO> reservaciones; 
	private List<RelSolicitudEventoGeneralDTO> reservacionesGen;
	
	private EstadisticasReservacionesDTO estadisticas;
	
	public ReporteReservacionesBean() {
		criteriosBusq = new CritBusquedaReporteReservDTO();
		visible = Arrays.asList(true, true, true, true, true, false, false, false, false, false, false, false, false);
		jsonMapper = new ObjectMapper();
	}

	@PostConstruct
	public void init() {
		sedes = facade.getSedeService().consultaSedesPorOrgGubDepedencia(ConstantesGestorWeb.ORG_GUB_SEDESOL);
	}

	public void buscaPorCriterios() {
		tblReservaciones = new ArrayList<>();
		reservaciones = new ArrayList<>();
		
		if (ObjectUtils.isNull(criteriosBusq.getTipoEvento()) || criteriosBusq.getTipoEvento().equals(TipoEventoEnum.CAPACITACION.getValor())) {
			reservaciones = facade.getSolicitudEventoCapacitacionService().consultaPorCriterios(criteriosBusq);
			tblReservaciones.addAll(facade.llenaTablaReservaciones(reservaciones));
		}
		
		if (ObjectUtils.isNull(criteriosBusq.getTipoEvento()) || criteriosBusq.getTipoEvento().equals(TipoEventoEnum.GENERAL.getValor())) {
			reservacionesGen = facade.getSolicitudEventoGeneralService().consultaPorCriterios(criteriosBusq);
			tblReservaciones.addAll(facade.llenaTablaReservacionesGen(reservacionesGen));
		}
	}

	public void generaEstadisticas(){
		estadisticas = facade.generarEstadisticas(reservaciones, reservacionesGen);
		try {
			String estadisticasStr = jsonMapper.writeValueAsString(estadisticas);
			RequestContext.getCurrentInstance().addCallbackParam("estadisticas", estadisticasStr);
			log.info("JSON: " + estadisticasStr);
		} catch (JsonProcessingException e) {
			log.error("No se pudo convertir el objeto a JSON");
			e.printStackTrace();
		}
		
	}
	
	/*private EstadisticasReservacionesDTO generarEstadisticas(List<EstadisticasReservacionesDTO> reservaciones) {
		EstadisticasReservacionesDTO estadisticas = new EstadisticasReservacionesDTO();

		estadisticas.setTotalReservaciones(reservaciones.size());
		

		return estadisticas;
	}*/
	
	public void onChangeSede(ValueChangeEvent e) {
		System.out.println("Cambio de sede...");
		if (ObjectUtils.isNotNull(e)) {
			Integer idSede = (Integer) e.getNewValue();
			CritBusquedaAreasConfigDTO criterios = new CritBusquedaAreasConfigDTO();
			criterios.setIdSede(idSede);
			areasConfiguradas = facade.getConfiguracionAreaService().busquedaAreasConfiguradasCriterios(criterios);
		} else {
			areasConfiguradas = new ArrayList<>();
			criteriosBusq.setIdArea(null);
		}

	}
	
	public void scrollArriba() {
		RequestContext.getCurrentInstance().scrollTo("form:modalInformeReservaciones");
	}
	public EstatusReservacionEnum[] estatusReservacion() {
		return EstatusReservacionEnum.values();
	}

	public TipoEventoEnum[] tiposEvento() {
		return TipoEventoEnum.values();
	}

	public AnalisisDatosReservacionesFacade getFacade() {
		return facade;
	}

	public void setFacade(AnalisisDatosReservacionesFacade facade) {
		this.facade = facade;
	}

	public List<SedeDTO> getSedes() {
		return sedes;
	}

	public void setSedes(List<SedeDTO> sedes) {
		this.sedes = sedes;
	}

	public CritBusquedaReporteReservDTO getCriteriosBusq() {
		return criteriosBusq;
	}

	public void setCriteriosBusq(CritBusquedaReporteReservDTO criteriosBusq) {
		this.criteriosBusq = criteriosBusq;
	}

	public List<ConfiguracionAreaDTO> getAreasConfiguradas() {
		return areasConfiguradas;
	}

	public void setAreasConfiguradas(List<ConfiguracionAreaDTO> areasConfiguradas) {
		this.areasConfiguradas = areasConfiguradas;
	}

	public List<Boolean> getVisible() {
		return visible;
	}

	public void setVisible(List<Boolean> visible) {
		this.visible = visible;
	}

	public List<RelSolicitudEventoCapacitacionDTO> getReservaciones() {
		return reservaciones;
	}

	public void setReservaciones(List<RelSolicitudEventoCapacitacionDTO> reservaciones) {
		this.reservaciones = reservaciones;
	}

	public List<ReporteReservacionesDTO> getTblReservaciones() {
		return tblReservaciones;
	}

	public void setTblReservaciones(List<ReporteReservacionesDTO> tblReservaciones) {
		this.tblReservaciones = tblReservaciones;
	}

	public List<RelSolicitudEventoGeneralDTO> getReservacionesGen() {
		return reservacionesGen;
	}

	public void setReservacionesGen(List<RelSolicitudEventoGeneralDTO> reservacionesGen) {
		this.reservacionesGen = reservacionesGen;
	}

	public EstadisticasReservacionesDTO getEstadisticas() {
		return estadisticas;
	}

	public void setEstadisticas(EstadisticasReservacionesDTO estadisticas) {
		this.estadisticas = estadisticas;
	}

}
