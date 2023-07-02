package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.CronConfDTO;
import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.ResultadoRespuestaCronDTO;
import mx.gob.sedesol.basegestor.commons.utils.CicloEjecucionEnum;
import mx.gob.sedesol.basegestor.commons.utils.DiasDeLaSemanaEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.cronscheduler.ConfiguracionCronService;

import javax.faces.bean.ViewScoped;

import javax.faces.event.ValueChangeEvent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

@ManagedBean
@ViewScoped
public class SchedulerBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = -4460702469039176608L;

	private static final String ETIQUETA_TITULO_CREAR_TAREAPROGRAMADA = "gw.tareasprogramadas.modal.header.creartareaprogramada";
	private static final String ETIQUETA_TITULO_EDITAR_TAREAPROGRAMADA = "gw.tareasprogramadas.modal.header.editartareaprogramada";
	private static final String SELECCIONAR_MES_DIA_DE_EJECUCION = "gw.tareasprogramadas.formatofecha.selectmesdiaeje";
	private static final String SELECCIONAR_DIA_Y_TODO_MESES_EJECUCION = "gw.tareasprogramadas.formatofecha.selectdiatodosmeseseje";

	private static final String CADENA_CERO = "0";
	private static final String ESPACIO_EN_BLANCO = " ";
	private static final String ASTERISCO = "*";
	private static final String INTERROGACION = "?";

	private static final String SEGUNDOS = "SEGUNDOS";
	private static final String MINUTOS = "MINUTOS";
	private static final String HORAS = "HORAS";
	private static final String DIA_DEL_MES = "DIA_DEL_MES";
	private static final String MES_DEL_ANIO = "MES_DEL_ANIO";
	private static final String DIA_DE_LA_SEMANA = "DIA_DE_LA_SEMANA";

	private static final Logger logger = Logger.getLogger(SchedulerBean.class);

	@ManagedProperty(value = "#{configuracionCronService}")
	private ConfiguracionCronService configuracionCronService;

	/**
	 * Inyeccion del servicio de sistema
	 */
	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CronConfDTO> tareaProgramadaList;
	private CronConfDTO tareaProgramadaSeleccionda;
	private Date horaDeEjecucion;
	private Date mesDeEjecucion;
	private CicloEjecucionEnum cicloEjecucionSeleccionado;
	private DiasDeLaSemanaEnum diaDeLaSemanaSeleccionado;
	private Boolean esElmSelMensualVisible;
	private Boolean esElmSelSemanalVisible;
	private Boolean esProcesarTodosLosMesesActivo;
	private String esTareaSeleccionadaActiva;
	private String tituloMdlGestionarTProgramada;
	private Boolean esCampoClaveEditable;
	private String formatoFechaMesSeleccionado;

	@PostConstruct
	public void init() {
		logger.info("El Bean SchedulerBean se esta inicializando ");

		/**
		 * Obtener todas las tareas programadas
		 */
		this.obtenerTareasProgramadas();

		tareaProgramadaSeleccionda = new CronConfDTO();

		esCampoClaveEditable = Boolean.FALSE;
		esTareaSeleccionadaActiva = "false";

		esElmSelMensualVisible = Boolean.FALSE;
		esElmSelSemanalVisible = Boolean.FALSE;
		logger.info("Termino de cargar el bean SchedulerBean ");
	}

	public void obtenerTareasProgramadas() {

		tareaProgramadaList = configuracionCronService.obtenerTareasProgramadas();
	}

	public void crearNuevaTareaProgramada() {

		esCampoClaveEditable = Boolean.TRUE;
		tituloMdlGestionarTProgramada = ETIQUETA_TITULO_CREAR_TAREAPROGRAMADA;
		tareaProgramadaSeleccionda = new CronConfDTO();
	}

	public void asignaTareaSeleccionada(CronConfDTO cronConfDTO) {
		esCampoClaveEditable = Boolean.FALSE;
		tituloMdlGestionarTProgramada = ETIQUETA_TITULO_EDITAR_TAREAPROGRAMADA;
		tareaProgramadaSeleccionda = cronConfDTO;

		if (ObjectUtils.isNotNull(tareaProgramadaSeleccionda.getActivo())) {
			esTareaSeleccionadaActiva = tareaProgramadaSeleccionda.getActivo().toString();
		}

		this.representarPatronEjecucion(tareaProgramadaSeleccionda.getPatronEjecucion());

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_TAR_PRO",
				String.valueOf(tareaProgramadaSeleccionda.getIdCfgCron()), requestActual(), TipoServicioEnum.LOCAL);

	}

	public void onChangeTpoCicloEjecucion() {

		esElmSelMensualVisible = Boolean.FALSE;
		esElmSelSemanalVisible = Boolean.FALSE;

		this.procesaCicloEjecucion(cicloEjecucionSeleccionado);

		logger.info("Termino el onchange");

	}

	public void persisteTareaProgramada() {
		logger.info("Iniciar persistencia de la tarea programadas");
		if (ObjectUtils.isNull(tareaProgramadaSeleccionda.getIdCfgCron())) {
			this.guardarTareaProgramada();
			logger.info("llegue a la linea 151");

		} else {
			this.actualizarTareasProgramada();
		}
		logger.info("Termina persistencia de la tarea programada");
	}

	public void guardarTareaProgramada() {
		logger.info("Llegue a la linea 160");
		this.anadirValoresParaPersistencia();
		logger.info("Llegue a la linea 162");
		tareaProgramadaSeleccionda.setFechaRegistro(new Date());

		tareaProgramadaSeleccionda.setClave(this.generaClaveCron());

		ResultadoDTO<CronConfDTO> resultado = configuracionCronService.guardar(tareaProgramadaSeleccionda);

		if (resultado.esCorrecto()) {
			logger.info("Guardo  correctamente la informacion de la tarea programada");
			this.obtenerTareasProgramadas();
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_TAR_PRO",
					String.valueOf(resultado.getDto().getIdCfgCron()), requestActual(), TipoServicioEnum.LOCAL);
		}
		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());

		this.reiniciarValores();
	}

	private String generaClaveCron() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}

	public void actualizarTareasProgramada() {

		//ResultadoRespuestaCronDTO resultadoRespuestaCronDTO = null;
		this.anadirValoresParaPersistencia();
		ResultadoDTO<CronConfDTO> resultado = configuracionCronService.actualizar(tareaProgramadaSeleccionda);
		/*resultadoRespuestaCronDTO = configuracionCronService.actualizaPatronCronAvaWs(resultado.getDto().getClave());

		if (ObjectUtils.isNull(resultadoRespuestaCronDTO) || 0 != resultadoRespuestaCronDTO.getEstatus()) {
			logger.error("Fallo la invocacion del Ws la respuesta del Ws fue diferente de 0");
			if (ObjectUtils.isNotNull(resultadoRespuestaCronDTO)) {
				logger.error("La respuesta descripcion del error invocacion ws : "
						+ resultadoRespuestaCronDTO.getDescripcionError());
			}

			logger.error("Se desactivara el Cron ");

			tareaProgramadaSeleccionda.setActivo(Boolean.FALSE);

			resultado = null;
			resultado = configuracionCronService.actualizar(tareaProgramadaSeleccionda);
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_TAR_PRO",
						String.valueOf(resultado.getDto().getIdCfgCron()), requestActual(), TipoServicioEnum.LOCAL);
			} else {
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
			}

			logger.info("la tarea programada es esta activa ? " + tareaProgramadaSeleccionda.getActivo());
		}
*/
		logger.info("Actualizo  correctamente la informacion de la tarea programada");

		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());
		this.reiniciarValores();

	}

	public void reiniciarValores() {
		logger.info("reiniciando valores del cron");

		horaDeEjecucion = null;
		cicloEjecucionSeleccionado = null;
		diaDeLaSemanaSeleccionado = null;
		mesDeEjecucion = null;
		tareaProgramadaSeleccionda = new CronConfDTO();
		esElmSelMensualVisible = Boolean.FALSE;
		esElmSelSemanalVisible = Boolean.FALSE;
	}

	private void anadirValoresParaPersistencia() {
		logger.info("llegue a la linea 235");
		tareaProgramadaSeleccionda.setPatronEjecucion(this.generarPatronEjecucion());
		tareaProgramadaSeleccionda.setFechaActualizacion(new Date());
		tareaProgramadaSeleccionda.setUsuarioModifico(this.getUsuarioEnSession().getIdPersona());
		logger.info("llegue a la linea 239");
	}
	
	public void dateChange(SelectEvent event) 
	{   
		horaDeEjecucion = (Date)event.getObject();
	}

	private String generarPatronEjecucion() {
		logger.info("Llegue a la linea 243");
		logger.info("Hora de ejecucion: " + horaDeEjecucion);
		Calendar calendar = GregorianCalendar.getInstance();
		logger.info("calendar: " + calendar);
//		if(ObjectUtils.isNull(horaDeEjecucion)){
//			calendar.setTime(new Date());
//		}else{
			calendar.setTime(horaDeEjecucion);
		//}
		

		
		
		StringBuilder patronEjecucion = new StringBuilder(CADENA_CERO);
		patronEjecucion.append(ESPACIO_EN_BLANCO);
		patronEjecucion.append(calendar.get(Calendar.MINUTE));
		patronEjecucion.append(ESPACIO_EN_BLANCO);
		patronEjecucion.append(calendar.get(Calendar.HOUR_OF_DAY));
		patronEjecucion.append(ESPACIO_EN_BLANCO);

		patronEjecucion.append(this.obtenerPatronCicloSeleccionado(cicloEjecucionSeleccionado, mesDeEjecucion,
				esProcesarTodosLosMesesActivo, diaDeLaSemanaSeleccionado));

		return patronEjecucion.toString();
	}

	private String obtenerPatronCicloSeleccionado(CicloEjecucionEnum cicloEjecucionEnum, Date mesEjecucion,
			Boolean esProcesarTodosLosMesesActivo, DiasDeLaSemanaEnum diaDeLaSemanaSeleccionado) {

		StringBuilder patron = new StringBuilder();
		switch (cicloEjecucionEnum) {
		case DIARIO:
			patron.append(ASTERISCO);
			patron.append(ESPACIO_EN_BLANCO);
			patron.append(ASTERISCO);
			patron.append(ESPACIO_EN_BLANCO);
			patron.append(INTERROGACION);
			break;

		case SEMANAL:

			patron.append(INTERROGACION);
			patron.append(ESPACIO_EN_BLANCO);
			patron.append(ASTERISCO);
			patron.append(ESPACIO_EN_BLANCO);
			patron.append(diaDeLaSemanaSeleccionado.getId());
			break;

		case MENSUAL:

			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(mesDeEjecucion);

			patron.append(calendar.get(Calendar.DAY_OF_MONTH));
			patron.append(ESPACIO_EN_BLANCO);

			if (esProcesarTodosLosMesesActivo) {
				patron.append(ASTERISCO);
			} else {

				patron.append((calendar.get(Calendar.MONTH) + 1));
			}

			patron.append(ESPACIO_EN_BLANCO);
			patron.append(INTERROGACION);

			break;

		default:
			logger.info("No se ha encontrado el ciclo de ejecucion");
		}

		return patron.toString();
	}

	/**
	 *
	 * (*) segundos OK (*) minnutos (*) horas (*) dia del mes (*) Mes (*) Dia de
	 * la semana
	 */
	private void validaMensajeResultadoTransaccion(List<String> mensajes,
			ResultadoTransaccionEnum resultadoTransaccionEnum) {

		if (resultadoTransaccionEnum == ResultadoTransaccionEnum.EXITOSO) {
			if (ObjectUtils.isNotNull(mensajes)) {
				agregarMsgInfo(mensajes.get(0), null, sistema);
			}
		} else {
			if (ObjectUtils.isNotNull(mensajes)) {
				agregarMsgError(mensajes, null, sistema);
			}
		}
	}

	public void onchangeTareaProgramadaEstatus(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e)) {
			tareaProgramadaSeleccionda.setActivo(Boolean.valueOf((Boolean) e.getNewValue()));
			esTareaSeleccionadaActiva = tareaProgramadaSeleccionda.getActivo().toString();
		}

	}

	private void representarPatronEjecucion(String patronEjecucion) {
		patronEjecucion = patronEjecucion.trim();

		Map<String, String> mapElementos = null;
		String[] elementos = this.parteCadenaAArreglosString(patronEjecucion);
		logger.info("El tamanio del array obtenido es " + elementos.length);
		this.validaLongitudDelArreglo(6, elementos);

		mapElementos = this.obtenerMapDeArray(elementos);

		this.validaPatronEjecucion(mapElementos);

		this.asignarHoraDeEjecucion(mapElementos);

		String diaDeLaSemana = mapElementos.get(DIA_DE_LA_SEMANA);

		Boolean esdiaDeLaSemanaEjecutable = validaSiEsDiaDeLaSemanaEsEjecutable(diaDeLaSemana);

		if (!esdiaDeLaSemanaEjecutable) {

			this.asignarMesYDiaDeEjecucion(mapElementos);
		}

		this.procesaCicloEjecucion(cicloEjecucionSeleccionado);
	}

	private void procesaCicloEjecucion(CicloEjecucionEnum cicloEjecucion) {

		switch (cicloEjecucion) {
		case DIARIO:
			esElmSelMensualVisible = Boolean.FALSE;
			esElmSelSemanalVisible = Boolean.FALSE;
			break;

		case SEMANAL:
			esElmSelMensualVisible = Boolean.FALSE;
			esElmSelSemanalVisible = Boolean.TRUE;

			mesDeEjecucion = null;
			esProcesarTodosLosMesesActivo = null;

			break;

		case MENSUAL:
			esElmSelMensualVisible = Boolean.TRUE;
			esElmSelSemanalVisible = Boolean.FALSE;
			diaDeLaSemanaSeleccionado = null;
			break;

		}

	}

	private Boolean validaSiEsDiaDeLaSemanaEsEjecutable(String diaDeLaSemana) {

		Boolean esDiaDeLaSemanaEjecutable = Boolean.FALSE;

		if (this.esUnValorNumerico(diaDeLaSemana)) {

			this.cicloEjecucionSeleccionado = cicloEjecucionSeleccionado.SEMANAL;
			diaDeLaSemanaSeleccionado = DiasDeLaSemanaEnum.getDiasDeLaSemanaEnum(Integer.parseInt(diaDeLaSemana));
			// esElmSelSemanalVisible = Boolean.TRUE;
			// esElmSelMensualVisible = Boolean.FALSE;

			esDiaDeLaSemanaEjecutable = Boolean.TRUE;
		}

		return esDiaDeLaSemanaEjecutable;
	}

	private void validaPatronEjecucion(Map<String, String> mapElementos) {

		String minutos = mapElementos.get(MINUTOS);
		String horas = mapElementos.get(HORAS);
		String diaDelMes = mapElementos.get(DIA_DEL_MES);
		String mes = mapElementos.get(MES_DEL_ANIO);
		String diaDeLaSemana = mapElementos.get(DIA_DE_LA_SEMANA);

		this.validaMinutos(minutos);
		this.validaHoras(horas);
		this.validaDiaDelMes(diaDelMes);
		this.validaMes(mes);
		this.validaDiaDeLaSemana(diaDeLaSemana);

		this.validaCoherenciaDiaSemanaYDiaMes(diaDelMes, diaDeLaSemana);

	}

	private void validaDiaDeLaSemana(String diaDeLaSemana) {

		if (INTERROGACION.equals(diaDeLaSemana) || this.esUnValorNumerico(diaDeLaSemana)) {

			if (this.esUnValorNumerico(diaDeLaSemana)) {

				Integer diaDelMesNumerico = Integer.parseInt(diaDeLaSemana);
				if (diaDelMesNumerico < 0 || diaDelMesNumerico > 31) {
					throw new NullPointerException("Patron de ejecucion inconsistente"
							+ " el elemento del dia del mes no es reconocido como val" + "ido (dia del mes = "
							+ diaDeLaSemana + ") ");
				}
			}
		} else {
			throw new NullPointerException(
					"Patron de ejecucion inconsistente" + " el elemento del dia de la semana no es reconocido como val"
							+ "ido (dia de la semana = " + diaDeLaSemana + ") ");
		}

	}

	private void validaMes(String mes) {

		if (ASTERISCO.equals(mes) || this.esUnValorNumerico(mes)) {

			if (this.esUnValorNumerico(mes)) {

				Integer diaDelMesNumerico = Integer.parseInt(mes);
				if (diaDelMesNumerico < 0 || diaDelMesNumerico > 12) {
					throw new NullPointerException("Patron de ejecucion inconsistente"
							+ " el elemento mes no es reconocido como val" + "ido ( mes = " + mes + ") ");
				}
			}

		} else {
			throw new NullPointerException("Patron de ejecucion inconsistente"
					+ " el elemento mes no es reconocido como val" + "ido (mes = " + mes + ") ");
		}

	}

	private void validaDiaDelMes(String diaDelMes) {

		if (ASTERISCO.equals(diaDelMes) || INTERROGACION.equals(diaDelMes) || this.esUnValorNumerico(diaDelMes)) {

			if (this.esUnValorNumerico(diaDelMes)) {

				Integer diaDelMesNumerico = Integer.parseInt(diaDelMes);
				if (diaDelMesNumerico < 0 || diaDelMesNumerico > 31) {
					throw new NullPointerException("Patron de ejecucion inconsistente"
							+ " el elemento del dia del mes no es reconocido como val" + "ido (dia del mes = "
							+ diaDelMes + ") ");
				}
			}
		} else {
			throw new NullPointerException(
					"Patron de ejecucion inconsistente" + " el elemento del dia del mes no es reconocido como val"
							+ "ido (dia del mes = " + diaDelMes + ") ");
		}
	}

	private void validaCoherenciaDiaSemanaYDiaMes(String diaDelMes, String diaDeLaSemana) {
		if (ASTERISCO.equals(diaDeLaSemana) && ASTERISCO.equals(diaDelMes)) {
			throw new NullPointerException(
					"Patron de ejecucion inconsistente no se puede tener el día de la semana y el dia del mes activo");
		}

		if (INTERROGACION.equals(diaDeLaSemana) && INTERROGACION.equals(diaDelMes)) {
			throw new NullPointerException(
					"Patron de ejecucion inconsistente no se puede tener el día de la semana y el dia del mes inactivo");
		}
	}

	private void validaHoras(String horas) {
		if (!this.esUnValorNumerico(horas)) {
			throw new NumberFormatException("Error en la " + "respresentacion del patron de "
					+ "ejecucion, error en la validacion  minutos no puede ser parseado a un valor numerico " + horas);
		}

		Integer horasNumerico = Integer.parseInt(horas);

		if (horasNumerico >= 60) {

			throw new NullPointerException("Error en la " + "respresentacion del patron de "
					+ "ejecucion, minutos no puede ser " + "mayor a 23 Horas: " + horas);
		}

	}

	private void validaMinutos(String minutos) {

		if (!this.esUnValorNumerico(minutos)) {
			throw new NumberFormatException("Error en la " + "respresentacion del patron de "
					+ "ejecucion, error en la validacion  minutos no puede ser parseado a un valor numerico "
					+ minutos);
		}

		Integer minutorNumerico = Integer.parseInt(minutos);

		if (minutorNumerico >= 60) {

			throw new NullPointerException("Error en la " + "respresentacion del patron de "
					+ "ejecucion, minutos no puede ser " + "mayor a 59 Minutos: " + minutos);
		}

	}

	private void asignarMesYDiaDeEjecucion(Map<String, String> mapElementos) {

		Calendar calendar = Calendar.getInstance();

		calendar = setEmptyDate(calendar);

		String diaDelMes = mapElementos.get(DIA_DEL_MES);

		calendar = this.asignarDiaDelMes(diaDelMes, calendar);

		String mes = mapElementos.get(MES_DEL_ANIO);

		calendar = this.asignarMes(mes, calendar);

		mesDeEjecucion = calendar.getTime();

		calendar = null;

	}

	private Calendar asignarMes(String mes, Calendar calendar) {

		esElmSelMensualVisible = Boolean.TRUE;
		esElmSelSemanalVisible = Boolean.FALSE;
		formatoFechaMesSeleccionado = SELECCIONAR_MES_DIA_DE_EJECUCION;

		switch (mes) {

		case ASTERISCO:
			if (ObjectUtils.isNull(cicloEjecucionSeleccionado)
					|| !cicloEjecucionSeleccionado.equals(CicloEjecucionEnum.DIARIO)) {
				cicloEjecucionSeleccionado = CicloEjecucionEnum.MENSUAL;
			}
			esProcesarTodosLosMesesActivo = Boolean.TRUE;
			formatoFechaMesSeleccionado = SELECCIONAR_DIA_Y_TODO_MESES_EJECUCION;
			break;

		default:
			cicloEjecucionSeleccionado = CicloEjecucionEnum.MENSUAL;
			calendar.set(Calendar.MONTH, (Integer.parseInt(mes) - 1));
			break;
		}

		return calendar;
	}

	private Calendar asignarDiaDelMes(String diaDelMes, Calendar calendar) {

		switch (diaDelMes) {
		case INTERROGACION:

			logger.info("Se asigna dia del mes nulo");

			break;

		case ASTERISCO:
			cicloEjecucionSeleccionado = CicloEjecucionEnum.DIARIO;

			break;

		default:
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaDelMes));
			break;
		}

		return calendar;
	}

	private Map<String, String> obtenerMapDeArray(String[] elementos) {

		String[] tipoElementos = { SEGUNDOS, MINUTOS, HORAS, DIA_DEL_MES, MES_DEL_ANIO, DIA_DE_LA_SEMANA };

		Map<String, String> mapaDeElementos = new HashMap<String, String>();
		for (int i = 0; i < elementos.length; i++) {

			mapaDeElementos.put(tipoElementos[i], elementos[i]);
			logger.info("Se coloco el siguiente valor en el mapa: ");
			logger.info("llave : <" + tipoElementos[i] + "> valor : <" + elementos[i] + ">");
		}

		return mapaDeElementos;
	}

	private void validaLongitudDelArreglo(Integer pocisiones, String[] elementos) {

		if (elementos.length < pocisiones) {

			throw new NullPointerException("Error en representacion de patron de ejecucion"
					+ " error al obtener array de cadena se esperaba un arreglo de " + pocisiones
					+ "  posiciones tamanio del arreglo:" + elementos.length);

		}

	}

	private void asignarHoraDeEjecucion(Map<String, String> mapElementos) {

		Calendar calendar = Calendar.getInstance();

		calendar = setEmptyDate(calendar);

		calendar.set(Calendar.MINUTE, Integer.valueOf(mapElementos.get(MINUTOS)));

		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(mapElementos.get("HORAS")));

		horaDeEjecucion = calendar.getTime();
	}

	private Calendar setEmptyDate(Calendar calendar) {

		calendar.set(Calendar.YEAR, 1900);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	public void onChangeCheckEjecutarCadaMes() {

		formatoFechaMesSeleccionado = SELECCIONAR_MES_DIA_DE_EJECUCION;
		if (esProcesarTodosLosMesesActivo) {
			formatoFechaMesSeleccionado = SELECCIONAR_DIA_Y_TODO_MESES_EJECUCION;

		}

	}

	private String[] parteCadenaAArreglosString(String patronEjecucion) {
		return patronEjecucion.split(ESPACIO_EN_BLANCO);
	}

	public boolean esUnValorNumerico(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Setter s y getter s
	 */
	public ConfiguracionCronService getConfiguracionCronService() {
		return configuracionCronService;
	}

	public void setConfiguracionCronService(ConfiguracionCronService configuracionCronService) {
		this.configuracionCronService = configuracionCronService;
	}

	public List<CronConfDTO> getTareaProgramadaList() {
		return tareaProgramadaList;
	}

	public void setTareaProgramadaList(List<CronConfDTO> tareaProgramadaList) {
		this.tareaProgramadaList = tareaProgramadaList;
	}

	public CronConfDTO getTareaProgramadaSeleccionda() {
		return tareaProgramadaSeleccionda;
	}

	public void setTareaProgramadaSeleccionda(CronConfDTO tareaProgramadaSeleccionda) {
		this.tareaProgramadaSeleccionda = tareaProgramadaSeleccionda;
	}

	public Date getHoraDeEjecucion() {
		return horaDeEjecucion;
	}

	public void setHoraDeEjecucion(Date horaDeEjecucion) {
		this.horaDeEjecucion = horaDeEjecucion;
	}

	public CicloEjecucionEnum[] getCiclosDeEjecucion() {
		return CicloEjecucionEnum.values();
	}

	public DiasDeLaSemanaEnum[] getDiasDeLaSemana() {
		return DiasDeLaSemanaEnum.values();
	}

	public CicloEjecucionEnum getCicloEjecucionSeleccionado() {
		return cicloEjecucionSeleccionado;
	}

	public void setCicloEjecucionSeleccionado(CicloEjecucionEnum cicloEjecucionSeleccionado) {
		this.cicloEjecucionSeleccionado = cicloEjecucionSeleccionado;
	}

	public Boolean getEsElmSelMensualVisible() {
		return esElmSelMensualVisible;
	}

	public void setEsElmSelMensualVisible(Boolean esElmSelMensualVisible) {
		this.esElmSelMensualVisible = esElmSelMensualVisible;
	}

	public DiasDeLaSemanaEnum getDiaDeLaSemanaSeleccionado() {
		return diaDeLaSemanaSeleccionado;
	}

	public void setDiaDeLaSemanaSeleccionado(DiasDeLaSemanaEnum diaDeLaSemanaSeleccionado) {
		this.diaDeLaSemanaSeleccionado = diaDeLaSemanaSeleccionado;
	}

	public Date getMesDeEjecucion() {
		return mesDeEjecucion;
	}

	public void setMesDeEjecucion(Date mesDeEjecucion) {
		this.mesDeEjecucion = mesDeEjecucion;
	}

	public Boolean getEsProcesarTodosLosMesesActivo() {
		return esProcesarTodosLosMesesActivo;
	}

	public void setEsProcesarTodosLosMesesActivo(Boolean esProcesarTodosLosMesesActivo) {
		this.esProcesarTodosLosMesesActivo = esProcesarTodosLosMesesActivo;
	}

	public Boolean getEsElmSelSemanalVisible() {
		return esElmSelSemanalVisible;
	}

	public void setEsElmSelSemanalVisible(Boolean esElmSelSemanalVisible) {
		this.esElmSelSemanalVisible = esElmSelSemanalVisible;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public String getEsTareaSeleccionadaActiva() {
		return esTareaSeleccionadaActiva;
	}

	public void setEsTareaSeleccionadaActiva(String esTareaSeleccionadaActiva) {
		this.esTareaSeleccionadaActiva = esTareaSeleccionadaActiva;
	}

	public String getTituloMdlGestionarTProgramada() {
		return tituloMdlGestionarTProgramada;
	}

	public void setTituloMdlGestionarTProgramada(String tituloMdlGestionarTProgramada) {
		this.tituloMdlGestionarTProgramada = tituloMdlGestionarTProgramada;
	}

	public Boolean getEsCampoClaveEditable() {
		return esCampoClaveEditable;
	}

	public void setEsCampoClaveEditable(Boolean esCampoClaveEditable) {
		this.esCampoClaveEditable = esCampoClaveEditable;
	}

	public String getFormatoFechaMesSeleccionado() {
		return formatoFechaMesSeleccionado;
	}

	public void setFormatoFechaMesSeleccionado(String formatoFechaMesSeleccionado) {
		this.formatoFechaMesSeleccionado = formatoFechaMesSeleccionado;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
