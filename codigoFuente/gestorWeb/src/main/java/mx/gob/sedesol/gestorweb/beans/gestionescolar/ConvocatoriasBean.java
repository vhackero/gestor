package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.ActividadDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ConvocatoriasBean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(ConvocatoriasBean.class);

	@ManagedProperty("#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;

	// REDIRECCION OPCIONES
	private String paginaActual;

	// nivel educativo
	private List<ConvocatoriaNivelEducativo> listaNivelEducativo;
	int valueConvocatoriaNivel;

	// CONSULTA TABLERO
	List<Convocatoria> listaConvocatoria;
	List<Convocatoria> listaConvocatoria2;
	List<ConvocatoriaTableroResumen> listaTableResumen;
	int valueConvocatoria;

	// CONSULTA CONVOCATORIA
	private List<EstatusDTO> estatusLista;

	// ALTA CONVOCATORIA
	private String altaNombreConvocatoria;
	private String altaNombreCorto;
	private String altaDescripcion;
	private Date altaFechaApertura;
	private Date altaFechaCierre;
	private Integer altaNivelEducativo;
	private String altaUrl;
	private Integer altaEstatus;
	private Date altaFechaAlta;
	private Integer altaCupoLimite;
	
	

	// CONSULTA CONVOCATORIA
	private ConvocatoriaParamConsulta convocatoriaParamConsulta = new ConvocatoriaParamConsulta();
	
	public String consulNombreConvocatoria;
	private String consulNombreCorto;
	private String consulFechaApertura;
	private String consulFechaCierre;
	private Integer consulNivelEducativo;
	private Integer valueConvocatoriaEstatus;

	// redireccion opciones y llenado de campos

	public String navegaNuevoConvocatoria() {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaConvocatoria.xhtml";
		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();
		valueConvocatoria = 0;
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		return null; // Mantener en la misma página
	}

	public String navegaConsultaConvocatoria() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaConvocatoria.xhtml";
		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();
		valueConvocatoria = 0;

		estatusLista = new ArrayList<>();
		// Crear los objetos EstatusDTO
		EstatusDTO activo = new EstatusDTO(1, "ACTIVO");
		EstatusDTO inactivo = new EstatusDTO(0, "INACTIVO");
		estatusLista.add(activo);
		estatusLista.add(inactivo);

		consultarNivelEducativo();
		

		return null;
	}

	public String navegaConsultaTablero() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaTableroConvocatoria.xhtml";
		consultarConvocatorias();
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		return null;
	}
	
	
	
	// alta convocatoria convocatorias
	
	
	public void altaConvocatorias() throws Exception {

		

		

	}
	
	
	

	// consulta convocatorias

	public void consultarConvocatoriasFiltros() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

	}

	public String obtenerFechaActual() {

		Calendar cal = Calendar.getInstance();

		Date fecha = cal.getTime();

		return DateUtils.convierteDateAString(fecha, "dd/MM/yyyy HH:mm");
	}

	private ActividadDTO actividad;
	private String cadenaFechaMinima;

	public void onDateChange() {
		actividad.setFechaFin(null);
		obtenerFechaFinal();
	}

	public void obtenerFechaFinal() {
		if (ObjectUtils.isNotNull(actividad.getFechaInicio())) {
			Calendar fechaInicio = Calendar.getInstance();
			fechaInicio.setTime(actividad.getFechaInicio());
			Calendar fechaMinimaCalendar = Calendar.getInstance();
			fechaMinimaCalendar.setTime(actividad.getFechaInicio());
			fechaMinimaCalendar.set(Calendar.HOUR_OF_DAY, fechaInicio.get(Calendar.HOUR_OF_DAY) + 2);
			fechaMinimaCalendar.set(Calendar.MINUTE, fechaInicio.get(Calendar.MINUTE));
			cadenaFechaMinima = DateUtils.convierteDateAString(fechaMinimaCalendar.getTime(), "dd/MM/yyyy HH:mm");

		} else {
			cadenaFechaMinima = DateUtils.convierteDateAString(new Date(), "dd/MM/yyyy HH:mm");
		}
	}
	
	public void limpiarCampos() {
		
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		convocatoriaParamConsulta = new ConvocatoriaParamConsulta();
		
	}
	
	
	public void consultarFiltros() {
		
		logger.info("***********************Inicio Consulta convocatorias filtros***********************");
		
		logger.info("NOMBRE CONVOCATORIA " + convocatoriaParamConsulta.getConsulNombreConvocatoria());
		logger.info("NOMBRE CONVOCATORIA " + convocatoriaParamConsulta.getConsulNombreCorto());
		logger.info("NOMBRE CONVOCATORIA " + convocatoriaParamConsulta.getValueConvocatoriaEstatus());
		logger.info("NOMBRE CONVOCATORIA " + convocatoriaParamConsulta.getConsulFechaApertura());
		logger.info("NOMBRE CONVOCATORIA " + convocatoriaParamConsulta.getConsulFechaCierre());
		logger.info("NOMBRE CONVOCATORIA " + convocatoriaParamConsulta.getConsulNivelEducativo());
			
		listaConvocatoria2 = convocatoriaService.consultarConvocatoriasFiltros();
			
			
		logger.info("***********************Termina Consulta convocatorias filtros***********************");
			
		}


	// consutlar tablero metodos

	public void consultarConvocatorias() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

	}

	public void consultarTableroResumen() throws Exception {

		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();

		listaTableResumen = convocatoriaService.consultarTableroResumen(valueConvocatoria);

		logger.info("Termina consulta lista tabla resumenn tabla");

	}

	/// varios

	public void consultarNivelEducativo() throws Exception {

		listaNivelEducativo = convocatoriaService.consultarNivelEducativo();

		logger.info("Termina consulta lista nivel select");

	}

	// set y get

	public ConvocatoriaService getConvocatoriaService() {
		return convocatoriaService;
	}

	public void setConvocatoriaService(ConvocatoriaService convocatoriaService) {
		this.convocatoriaService = convocatoriaService;
	}

	public String getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	public List<Convocatoria> getListaConvocatoria() {
		return listaConvocatoria;
	}

	public void setListaConvocatoria(List<Convocatoria> listaConvocatoria) {
		this.listaConvocatoria = listaConvocatoria;
	}

	public int getValueConvocatoria() {
		return valueConvocatoria;
	}

	public void setValueConvocatoria(int valueConvocatoria) {
		this.valueConvocatoria = valueConvocatoria;
	}

	public List<ConvocatoriaTableroResumen> getListaTableResumen() {
		return listaTableResumen;
	}

	public void setListaTableResumen(List<ConvocatoriaTableroResumen> listaTableResumen) {
		this.listaTableResumen = listaTableResumen;
	}

	public List<EstatusDTO> getEstatusLista() {
		return estatusLista;
	}

	public void setEstatusLista(List<EstatusDTO> estatusLista) {
		this.estatusLista = estatusLista;
	}

	public List<ConvocatoriaNivelEducativo> getListaNivelEducativo() {
		return listaNivelEducativo;
	}

	public void setListaNivelEducativo(List<ConvocatoriaNivelEducativo> listaNivelEducativo) {
		this.listaNivelEducativo = listaNivelEducativo;
	}

	public int getValueConvocatoriaNivel() {
		return valueConvocatoriaNivel;
	}

	public void setValueConvocatoriaNivel(int valueConvocatoriaNivel) {
		this.valueConvocatoriaNivel = valueConvocatoriaNivel;
	}

	public String getCadenaFechaMinima() {
		return cadenaFechaMinima;
	}

	public void setCadenaFechaMinima(String cadenaFechaMinima) {
		this.cadenaFechaMinima = cadenaFechaMinima;
	}

	public ConvocatoriaParamConsulta getConvocatoriaParamConsulta() {
		return convocatoriaParamConsulta;
	}

	public void setConvocatoriaParamConsulta(ConvocatoriaParamConsulta convocatoriaParamConsulta) {
		this.convocatoriaParamConsulta = convocatoriaParamConsulta;
	}

	public String getConsulNombreConvocatoria() {
		return consulNombreConvocatoria;
	}

	public void setConsulNombreConvocatoria(String consulNombreConvocatoria) {
		this.consulNombreConvocatoria = consulNombreConvocatoria;
	}

	public String getConsulNombreCorto() {
		return consulNombreCorto;
	}

	public void setConsulNombreCorto(String consulNombreCorto) {
		this.consulNombreCorto = consulNombreCorto;
	}

	public String getConsulFechaApertura() {
		return consulFechaApertura;
	}

	public void setConsulFechaApertura(String consulFechaApertura) {
		this.consulFechaApertura = consulFechaApertura;
	}

	public String getConsulFechaCierre() {
		return consulFechaCierre;
	}

	public void setConsulFechaCierre(String consulFechaCierre) {
		this.consulFechaCierre = consulFechaCierre;
	}

	public Integer getConsulNivelEducativo() {
		return consulNivelEducativo;
	}

	public void setConsulNivelEducativo(Integer consulNivelEducativo) {
		this.consulNivelEducativo = consulNivelEducativo;
	}

	public Integer getValueConvocatoriaEstatus() {
		return valueConvocatoriaEstatus;
	}

	public void setValueConvocatoriaEstatus(Integer valueConvocatoriaEstatus) {
		this.valueConvocatoriaEstatus = valueConvocatoriaEstatus;
	}
	
	public String getAltaNombreConvocatoria() {
		return altaNombreConvocatoria;
	}

	public void setAltaNombreConvocatoria(String altaNombreConvocatoria) {
		this.altaNombreConvocatoria = altaNombreConvocatoria;
	}

	public String getAltaNombreCorto() {
		return altaNombreCorto;
	}

	public void setAltaNombreCorto(String altaNombreCorto) {
		this.altaNombreCorto = altaNombreCorto;
	}

	public String getAltaDescripcion() {
		return altaDescripcion;
	}

	public void setAltaDescripcion(String altaDescripcion) {
		this.altaDescripcion = altaDescripcion;
	}

	public Date getAltaFechaApertura() {
		return altaFechaApertura;
	}

	public void setAltaFechaApertura(Date altaFechaApertura) {
		this.altaFechaApertura = altaFechaApertura;
	}

	public Date getAltaFechaCierre() {
		return altaFechaCierre;
	}

	public void setAltaFechaCierre(Date altaFechaCierre) {
		this.altaFechaCierre = altaFechaCierre;
	}

	public Integer getAltaNivelEducativo() {
		return altaNivelEducativo;
	}

	public void setAltaNivelEducativo(Integer altaNivelEducativo) {
		this.altaNivelEducativo = altaNivelEducativo;
	}

	public String getAltaUrl() {
		return altaUrl;
	}

	public void setAltaUrl(String altaUrl) {
		this.altaUrl = altaUrl;
	}

	public Integer getAltaEstatus() {
		return altaEstatus;
	}

	public void setAltaEstatus(Integer altaEstatus) {
		this.altaEstatus = altaEstatus;
	}

	public Date getAltaFechaAlta() {
		return altaFechaAlta;
	}

	public void setAltaFechaAlta(Date altaFechaAlta) {
		this.altaFechaAlta = altaFechaAlta;
	}

	public Integer getAltaCupoLimite() {
		return altaCupoLimite;
	}

	public void setAltaCupoLimite(Integer altaCupoLimite) {
		this.altaCupoLimite = altaCupoLimite;
	}

	public ActividadDTO getActividad() {
		return actividad;
	}

	public void setActividad(ActividadDTO actividad) {
		this.actividad = actividad;
	}

	public List<Convocatoria> getListaConvocatoria2() {
		return listaConvocatoria2;
	}

	public void setListaConvocatoria2(List<Convocatoria> listaConvocatoria2) {
		this.listaConvocatoria2 = listaConvocatoria2;
	}

	

}
