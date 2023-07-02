package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.ActividadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.EstatusActividadEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.ActividadService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "actividadesBean")
public class ActividadesBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ActividadesBean.class);

	@ManagedProperty(name = "actividadService", value = "#{actividadService}")
	private transient ActividadService actividadService;

	@ManagedProperty(name = "sistema", value = "#{sistema}")
	private SistemaBean sistema;

	private List<ActividadDTO> actividades;
	private ActividadDTO actividad;
	private String cadenaFechaMinima;

	public ActividadesBean() {
		actividad = new ActividadDTO(getUsuarioEnSession().getIdPersona());
	}

	@PostConstruct
	public void init() {
		actividades = actividadService.buscarPorUsuario(getUsuarioEnSession().getIdPersona());
	}

	public void nuevaActividad() {
		actividad = new ActividadDTO(getUsuarioEnSession().getIdPersona());

	}

	public void limpiarActividad() {
		actividad = null;
	}

	public void guardarActividad() {


		if (existeNombreActividad(actividad.getActividad(), actividades)) {
			ResultadoDTO<ActividadDTO> resultado = actividadService.guardar(actividad);
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
				actividades = actividadService.buscarPorUsuario(getUsuarioEnSession().getIdPersona());
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
				RequestContext.getCurrentInstance().execute("PF('modalActividad').hide()");
			} else {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}

	}

	/**
	 * 
	 * Valida si existe el nombre de la actividad, si existe regresara un false
	 */
	public boolean existeNombreActividad(String nombre, List<ActividadDTO> listaActividades) {
		if (!listaActividades.isEmpty()) {
			for (ActividadDTO actividad : listaActividades) {
				if (actividad.getActividad().equals(nombre)) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "El nombre de la actividad ya existe.", null));
					return false;
				}
			}
		}
		return true;
	}

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	public String obtenerTiempoRestante(Date fechaFinal) {
		long milis1, milis2, diff;

		// INSTANCIA DEL CALENDARIO GREGORIANO
		Calendar cinicio = Calendar.getInstance();
		Calendar cfinal = Calendar.getInstance();

		// ESTABLECEMOS LA FECHA DEL CALENDARIO CON EL DATE GENERADO
		// ANTERIORMENTE
		cinicio.setTime(new Date());
		cfinal.setTime(fechaFinal);
		milis1 = cinicio.getTimeInMillis();
		milis2 = cfinal.getTimeInMillis();
		diff = milis2 - milis1;

		// Calcular la diferencia en minutos
		long diffMinutos = Math.abs(diff / (60 * 1000));
		long cantidadMinutos = Math.abs(diffMinutos % 60);

		// Calcular la diferencia en horas
		long cantidadHoras = diff / (60 * 60 * 1000);

		// Calcular la cantidad de dias con base en las horas
		long cantidadDias = (long) (cantidadHoras / 24);

		StringBuilder descripcionFecha = new StringBuilder();

		if (cantidadDias > 0) {
			descripcionFecha.append(obtenerDescFecha(cantidadDias, "días", "día"));
			return descripcionFecha.toString();
		} else if(cantidadHoras >= 0 && cantidadMinutos >= 0){
			descripcionFecha.append(obtenerDescFecha(cantidadHoras, "horas", "hora"));
			descripcionFecha.append(" ");
			descripcionFecha.append(obtenerDescFecha(cantidadMinutos, "minutos", "minuto"));
			return descripcionFecha.toString();
		}else{
			//descripcionFecha.append("Retraso de: ");
			descripcionFecha.append(obtenerDescFecha(Math.abs(cantidadHoras), "horas", "hora"));
			descripcionFecha.append(" ");
			descripcionFecha.append(obtenerDescFecha(cantidadMinutos, "minutos", "minuto"));
			return descripcionFecha.toString();
		}
	}
	
	
	public Boolean compararFechaFinFechaActual(Date fechaFin){
		
		if(new Date().after(fechaFin)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
		
		
	}

	/**
	 * Regresa la decripcion de la fecha(dia, minuto o segundo) en formato de
	 * String con su descripcion en singular o plural. Ejenplo: 1 dia, 2 dias, 1
	 * hora, 2 horas, 1 minuto, 2 minutos.
	 * 
	 * @param cantidad
	 * @param descPlural
	 * @param descSingular
	 * @return
	 */
	public String obtenerDescFecha(long cantidad, String descPlural, String descSingular) {
		StringBuilder descripcion = new StringBuilder();
		descripcion.append(String.valueOf(cantidad));
		descripcion.append(" ");
		if (cantidad > 1 || cantidad == 0) {
			descripcion.append(descPlural);
		} else {
			descripcion.append(descSingular);
		}
		return descripcion.toString();
	}

	public Date obtenerFechaInicioMasHoras() {
		if (ObjectUtils.isNotNull(actividad.getFechaFin())) {
			long horas = 1;// Controla las horas
			long horasEnMilisegundos = horas * 60 * 60 * 1000;
			Date newDate = new Date(actividad.getFechaInicio().getTime() + horasEnMilisegundos);
			return newDate;
		} else {
			return new Date();
		}

	}

	public String obtenerFechaActual() {

		Calendar cal = Calendar.getInstance();

		Date fecha = cal.getTime();

		return DateUtils.convierteDateAString(fecha, "dd/MM/yyyy HH:mm");
	}
	public Date obtenerFechaHoy(){
		return new Date();
	}
	
	public void onDateChange() {
		actividad.setFechaFin(null);
		obtenerFechaFinal();
	}

	/**
	 * Establece un rango minimo en la fecha final tanto de Fechas como de Horas
	 *
	 */
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

	public Integer obtenerHoraActual() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		return new Integer(calendar.get(Calendar.HOUR_OF_DAY));
	}

	public Integer obtenerHoraMinimaMasDos() {
		if (ObjectUtils.isNotNull(actividad.getFechaInicio())) {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(actividad.getFechaInicio()); // assigns calendar to
			// given date
			Integer horaMinima = calendar.get(Calendar.HOUR_OF_DAY) + 2; // gets
			// hour
			// in
			// 24h
			// format
			calendar.get(Calendar.HOUR); // gets hour in 12h format
			calendar.get(Calendar.MONTH); // gets month number, NOTE this is
			// zero based!
			return new Integer(horaMinima);
		} else {
			return 0;
		}

	}

	public void finalizarActividad() {
		actividad.setFechaTermino(new Date());
		actividad.setEstatus(EstatusActividadEnum.FINALIZADO.getValor());
		// GUSTAVO --BitacoraUtil.llenarBitacora(actividad,
		// getUsuarioEnSession(), ConstantesBitacora.ACTIVIDAD_EDITAR, request);
		ResultadoDTO<ActividadDTO> resultado = actividadService.actualizar(actividad);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			init();
			agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
		} else {
			agregarMsgError(resultado.getMensajes(), null, sistema);
		}
	}

	/* INICIO DE GETS Y SETS */
	public ActividadService getActividadService() {
		return actividadService;
	}

	public void setActividadService(ActividadService actividadService) {
		this.actividadService = actividadService;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public List<ActividadDTO> getActividades() {
		return actividades;
	}

	public void setActividades(List<ActividadDTO> actividades) {
		this.actividades = actividades;
	}

	public ActividadDTO getActividad() {
		return actividad;
	}

	public void setActividad(ActividadDTO actividad) {
		this.actividad = actividad;
	}

	public String getCadenaFechaMinima() {
		return cadenaFechaMinima;
	}

	public void setCadenaFechaMinima(String cadenaFechaMinima) {
		this.cadenaFechaMinima = cadenaFechaMinima;
	}

}
