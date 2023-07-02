package mx.gob.sedesol.basegestor.service.impl.analisisdatos;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.CommonGroupByDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.CritBusquedaReporteReservDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.EstadisticasReservacionesDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteReservacionesDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.PersonalizacionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.utils.EstatusReservacionEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoEventoEnum;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusReservacion;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaSedeService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ConfiguracionAreaService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.PersonalizacionAreaService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SedeService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudEventoGeneralService;

@Service("analisisDatosReservacionesFacade")
public class AnalisisDatosReservacionesFacade {

	private static final Logger log = Logger.getLogger(AnalisisDatosReservacionesFacade.class);

	@Autowired
	private SedeService sedeService;

	@Autowired
	private ConfiguracionAreaService configuracionAreaService;

	@Autowired
	private CatalogoComunService<CatEstatusReservacion, Integer> catEstatusReservacionService;

	@Autowired
	private SolicitudEventoCapacitacionService solicitudEventoCapacitacionService;

	@Autowired
	private SolicitudEventoGeneralService solicitudEventoGeneralService;

	@Autowired
	private AreaSedeService areaSedeService;

	@Autowired
	private PersonalizacionAreaService personalizacionAreaService;

	public EstadisticasReservacionesDTO generarEstadisticas(List<RelSolicitudEventoCapacitacionDTO> reservacionesCap,
			List<RelSolicitudEventoGeneralDTO> reservacionesGen) {

		EstadisticasReservacionesDTO estadisticas = new EstadisticasReservacionesDTO();

		estadisticas.setTotalReservaciones(reservacionesCap.size() + reservacionesGen.size());
		estadisticas.setFechaCreacion(new Date());
		estadisticas.setEventosGenerales(reservacionesGen.size());
		estadisticas.setEventosCapacitacion(reservacionesCap.size());

		Integer eventosPublicos = 0;
		Integer eventosPrivados = 0;
		estadisticas.setEventosAprobados(0);
		estadisticas.setEventosNoAprobados(0);
		estadisticas.setConCafeteria(0);
		estadisticas.setSinCafeteria(0);
		Map<String, Integer> mapDist = new HashMap<>();
		List<CommonGroupByDTO> dist = new ArrayList<>();

		for (RelSolicitudEventoCapacitacionDTO reservacion : reservacionesCap) {

			if (reservacion.getTblSolicitudReservacion().getPrivado() == null
					|| reservacion.getTblSolicitudReservacion().getPrivado().intValue() == 0) {
				eventosPublicos++;
			} else {
				eventosPrivados++;
			}

			if (reservacion.getTblReservacionEventoCapacitacion().getCatEstatusReservacion().getId()
					.equals(EstatusReservacionEnum.APROBADO.getValor())) {
				estadisticas.setEventosAprobados(estadisticas.getEventosAprobados() + 1);
			} else {
				estadisticas.setEventosNoAprobados(estadisticas.getEventosNoAprobados() + 1);
			}

			PersonalizacionAreaDTO perso = personalizacionAreaService
					.buscarPorId(reservacion.getTblReservacionEventoCapacitacion().getIdPersonalizacionArea());

			if (!ObjectUtils.isNullOrCero(perso.getServicioCafeteria())) {
				estadisticas.setConCafeteria(estadisticas.getConCafeteria() + 1);
			} else {
				estadisticas.setSinCafeteria(estadisticas.getSinCafeteria() + 1);
			}

			if (!mapDist.containsKey(perso.getDistribucionArea().getNombre())) {
				mapDist.put(perso.getDistribucionArea().getNombre(), 1);
			} else {
				mapDist.put(perso.getDistribucionArea().getNombre(),
						mapDist.get(perso.getDistribucionArea().getNombre()) + 1);
			}

		}

		for (RelSolicitudEventoGeneralDTO reservacion : reservacionesGen) {

			if (reservacion.getTblSolicitudReservacion().getPrivado() == null
					|| reservacion.getTblSolicitudReservacion().getPrivado().intValue() == 0) {
				eventosPublicos++;
			} else {
				eventosPrivados++;
			}

			if (reservacion.getTblReservacionEventoGeneral().getCatEstatusReservacion().getId()
					.equals(EstatusReservacionEnum.APROBADO.getValor())) {
				estadisticas.setEventosAprobados(estadisticas.getEventosAprobados() + 1);
			} else {
				estadisticas.setEventosNoAprobados(estadisticas.getEventosNoAprobados() + 1);
			}

			PersonalizacionAreaDTO perso = personalizacionAreaService
					.buscarPorId(reservacion.getTblReservacionEventoGeneral().getIdPersonalizacionArea());

			if (!ObjectUtils.isNullOrCero(perso.getServicioCafeteria())) {
				estadisticas.setConCafeteria(estadisticas.getConCafeteria() + 1);
			} else {
				estadisticas.setSinCafeteria(estadisticas.getSinCafeteria() + 1);
			}

			if (!mapDist.containsKey(perso.getDistribucionArea().getNombre())) {
				mapDist.put(perso.getDistribucionArea().getNombre(), 1);
			} else {
				mapDist.put(perso.getDistribucionArea().getNombre(),
						mapDist.get(perso.getDistribucionArea().getNombre()) + 1);
			}

		}
		
		estadisticas.setEventosPrivados(eventosPrivados);
		estadisticas.setEventosPublicos(eventosPublicos);

		for (Entry<String, Integer> distribucion : mapDist.entrySet()) {
			CommonGroupByDTO d = new CommonGroupByDTO();
			d.setNombreCampo(distribucion.getKey());
			d.setValorNumerico(distribucion.getValue().longValue());

			dist.add(d);
		}

		estadisticas.setDistribuciones(dist);

		final LocalDate hoy = LocalDate.now();

		final LocalDate primeroEnero = hoy.withMonth(1).withDayOfMonth(1);
		Date dPEnero = Date.from(primeroEnero.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoEnero = primeroEnero.withDayOfMonth(primeroEnero.lengthOfMonth());
		Date dFEnero = Date.from(ultimoEnero.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critEnero = new CritBusquedaReporteReservDTO();
		critEnero.setFechaInicial(dPEnero);
		critEnero.setFechaFinal(dFEnero);
		estadisticas.setReservacionesEnero(solicitudEventoCapacitacionService.consultaPorCriterios(critEnero).size());
		estadisticas.setReservacionesEnero(estadisticas.getReservacionesEnero()
				+ solicitudEventoGeneralService.consultaPorCriterios(critEnero).size());

		final LocalDate primeroFebrero = hoy.withMonth(2).withDayOfMonth(1);
		Date dPFebrero = Date.from(primeroFebrero.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoFebrero = primeroFebrero.withDayOfMonth(primeroFebrero.lengthOfMonth());
		Date dFFebrero = Date.from(ultimoFebrero.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critFebrero = new CritBusquedaReporteReservDTO();
		critFebrero.setFechaInicial(dPFebrero);
		critFebrero.setFechaFinal(dFFebrero);
		estadisticas
				.setReservacionesFebrero(solicitudEventoCapacitacionService.consultaPorCriterios(critFebrero).size());
		estadisticas.setReservacionesFebrero(estadisticas.getReservacionesFebrero()
				+ solicitudEventoGeneralService.consultaPorCriterios(critFebrero).size());

		final LocalDate primeroMarzo = hoy.withMonth(3).withDayOfMonth(1);
		Date dPMarzo = Date.from(primeroMarzo.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoMarzo = primeroMarzo.withDayOfMonth(primeroMarzo.lengthOfMonth());
		Date dFMarzo = Date.from(ultimoMarzo.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critMarzo = new CritBusquedaReporteReservDTO();
		critMarzo.setFechaInicial(dPMarzo);
		critMarzo.setFechaFinal(dFMarzo);
		estadisticas.setReservacionesMarzo(solicitudEventoCapacitacionService.consultaPorCriterios(critMarzo).size());
		estadisticas.setReservacionesMarzo(estadisticas.getReservacionesMarzo()
				+ solicitudEventoGeneralService.consultaPorCriterios(critMarzo).size());

		final LocalDate primeroAbril = hoy.withMonth(4).withDayOfMonth(1);
		Date dPAbril = Date.from(primeroAbril.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoAbril = primeroAbril.withDayOfMonth(primeroAbril.lengthOfMonth());
		Date dFAbril = Date.from(ultimoAbril.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		CritBusquedaReporteReservDTO critAbril = new CritBusquedaReporteReservDTO();
		critAbril.setFechaInicial(dPAbril);
		critAbril.setFechaFinal(dFAbril);
		estadisticas.setReservacionesAbril(solicitudEventoCapacitacionService.consultaPorCriterios(critAbril).size());
		estadisticas.setReservacionesAbril(estadisticas.getReservacionesAbril()
				+ solicitudEventoGeneralService.consultaPorCriterios(critAbril).size());

		final LocalDate primeroMayo = hoy.withMonth(5).withDayOfMonth(1);
		Date dPMayo = Date.from(primeroMayo.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoMayo = primeroMayo.withDayOfMonth(primeroMayo.lengthOfMonth());
		Date dFMayo = Date.from(ultimoMayo.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critMayo = new CritBusquedaReporteReservDTO();
		critMayo.setFechaInicial(dPMayo);
		critMayo.setFechaFinal(dFMayo);
		estadisticas.setReservacionesMayo(solicitudEventoCapacitacionService.consultaPorCriterios(critMayo).size());
		estadisticas.setReservacionesMayo(estadisticas.getReservacionesMayo()
				+ solicitudEventoGeneralService.consultaPorCriterios(critMayo).size());

		final LocalDate primeroJunio = hoy.withMonth(6).withDayOfMonth(1);
		Date dPJunio = Date.from(primeroJunio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoJunio = primeroJunio.withDayOfMonth(primeroJunio.lengthOfMonth());
		Date dFJunio = Date.from(ultimoJunio.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critJunio = new CritBusquedaReporteReservDTO();
		critJunio.setFechaInicial(dPJunio);
		critJunio.setFechaFinal(dFJunio);
		estadisticas.setReservacionesJunio(solicitudEventoCapacitacionService.consultaPorCriterios(critJunio).size());
		estadisticas.setReservacionesJunio(estadisticas.getReservacionesJunio()
				+ solicitudEventoGeneralService.consultaPorCriterios(critJunio).size());

		final LocalDate primeroJulio = hoy.withMonth(7).withDayOfMonth(1);
		Date dPJulio = Date.from(primeroJulio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoJulio = primeroJulio.withDayOfMonth(primeroJulio.lengthOfMonth());
		Date dFJulio = Date.from(ultimoJulio.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critJulio = new CritBusquedaReporteReservDTO();
		critJulio.setFechaInicial(dPJulio);
		critJulio.setFechaFinal(dFJulio);
		estadisticas.setReservacionesJulio(solicitudEventoCapacitacionService.consultaPorCriterios(critJulio).size());
		estadisticas.setReservacionesJulio(estadisticas.getReservacionesJulio()
				+ solicitudEventoGeneralService.consultaPorCriterios(critJulio).size());

		final LocalDate primeroAgosto = hoy.withMonth(8).withDayOfMonth(1);
		Date dPAgosto = Date.from(primeroAgosto.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoAgosto = primeroAgosto.withDayOfMonth(primeroAgosto.lengthOfMonth());
		Date dFAgosto = Date.from(ultimoAgosto.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critAgosto = new CritBusquedaReporteReservDTO();
		critAgosto.setFechaInicial(dPAgosto);
		critAgosto.setFechaFinal(dFAgosto);
		estadisticas.setReservacionesAgosto(solicitudEventoCapacitacionService.consultaPorCriterios(critAgosto).size());
		estadisticas.setReservacionesAgosto(estadisticas.getReservacionesAgosto()
				+ solicitudEventoGeneralService.consultaPorCriterios(critAgosto).size());

		final LocalDate primeroSeptiembre = hoy.withMonth(9).withDayOfMonth(1);
		Date dPSeptiembre = Date.from(primeroSeptiembre.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoSeptiembre = primeroSeptiembre.withDayOfMonth(primeroSeptiembre.lengthOfMonth());
		Date dFSeptiembre = Date.from(ultimoSeptiembre.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critSeptiembre = new CritBusquedaReporteReservDTO();
		critSeptiembre.setFechaInicial(dPSeptiembre);
		critSeptiembre.setFechaFinal(dFSeptiembre);
		estadisticas.setReservacionesSeptiembre(
				solicitudEventoCapacitacionService.consultaPorCriterios(critSeptiembre).size());
		estadisticas.setReservacionesSeptiembre(estadisticas.getReservacionesSeptiembre()
				+ solicitudEventoGeneralService.consultaPorCriterios(critSeptiembre).size());

		final LocalDate primeroOctubre = hoy.withMonth(10).withDayOfMonth(1);
		Date dPOctubre = Date.from(primeroOctubre.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoOctubre = primeroOctubre.withDayOfMonth(primeroOctubre.lengthOfMonth());
		Date dFOctubre = Date.from(ultimoOctubre.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critOctubre = new CritBusquedaReporteReservDTO();
		critOctubre.setFechaInicial(dPOctubre);
		critOctubre.setFechaFinal(dFOctubre);
		estadisticas
				.setReservacionesOctubre(solicitudEventoCapacitacionService.consultaPorCriterios(critOctubre).size());
		estadisticas.setReservacionesOctubre(estadisticas.getReservacionesOctubre()
				+ solicitudEventoGeneralService.consultaPorCriterios(critOctubre).size());

		final LocalDate primeroNoviembre = hoy.withMonth(11).withDayOfMonth(1);
		Date dPNoviembre = Date.from(primeroNoviembre.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoNoviembre = primeroNoviembre.withDayOfMonth(primeroNoviembre.lengthOfMonth());
		Date dFNoviembre = Date.from(ultimoNoviembre.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critNoviembre = new CritBusquedaReporteReservDTO();
		critNoviembre.setFechaInicial(dPNoviembre);
		critNoviembre.setFechaFinal(dFNoviembre);
		estadisticas.setReservacionesNoviembre(
				solicitudEventoCapacitacionService.consultaPorCriterios(critNoviembre).size());
		estadisticas.setReservacionesNoviembre(estadisticas.getReservacionesNoviembre()
				+ solicitudEventoGeneralService.consultaPorCriterios(critNoviembre).size());

		final LocalDate primeroDiciembre = hoy.withMonth(12).withDayOfMonth(1);
		Date dPDiciembre = Date.from(primeroDiciembre.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final LocalDate ultimoDiciembre = primeroDiciembre.withDayOfMonth(primeroDiciembre.lengthOfMonth());
		Date dFDiciembre = Date.from(ultimoDiciembre.atStartOfDay(ZoneId.systemDefault()).toInstant());

		CritBusquedaReporteReservDTO critDiciembre = new CritBusquedaReporteReservDTO();
		critDiciembre.setFechaInicial(dPDiciembre);
		critDiciembre.setFechaFinal(dFDiciembre);
		estadisticas.setReservacionesDiciembre(
				solicitudEventoCapacitacionService.consultaPorCriterios(critDiciembre).size());
		estadisticas.setReservacionesDiciembre(estadisticas.getReservacionesDiciembre()
				+ solicitudEventoGeneralService.consultaPorCriterios(critDiciembre).size());

		log.info(estadisticas.getTotalReservaciones() + " ####total reservaciones");
		log.info(estadisticas.getEventosPublicos() + " ####publicos");
		log.info(estadisticas.getEventosPrivados() + " ####privados");
		log.info(estadisticas.getFechaCreacion() + " ####fecha creacion");
		log.info(estadisticas.getEventosAprobados() + " ####eventos aprobados");
		log.info(estadisticas.getEventosNoAprobados() + " ####no aprobados");
		log.info(estadisticas.getEventosGenerales() + " ####eventos generales");
		log.info(estadisticas.getEventosCapacitacion() + " ####eventos capacitacion");
		log.info(estadisticas.getConCafeteria() + " ####con cafeteria");
		log.info(estadisticas.getSinCafeteria() + " ####sin cafeteria");

		for (CommonGroupByDTO d : estadisticas.getDistribuciones()) {
			log.info(d.getNombreCampo() + " " + d.getValorNumerico() + "#### distribuciones");
		}

		log.info(estadisticas.getReservacionesEnero() + " ####enero");
		log.info(estadisticas.getReservacionesFebrero() + " ####febrero");
		log.info(estadisticas.getReservacionesMarzo() + " ####marzo");
		log.info(estadisticas.getReservacionesAbril() + " ####abril");
		log.info(estadisticas.getReservacionesMayo() + " ####mayo");
		log.info(estadisticas.getReservacionesJunio() + " ####junio");
		log.info(estadisticas.getReservacionesJulio() + " ####julio");
		log.info(estadisticas.getReservacionesAgosto() + " ####agosto");
		log.info(estadisticas.getReservacionesSeptiembre() + " ####septiembre");
		log.info(estadisticas.getReservacionesOctubre() + " ####octubre");
		log.info(estadisticas.getReservacionesNoviembre() + " ####noviembre");
		log.info(estadisticas.getReservacionesDiciembre() + " ####diciembre");

		return estadisticas;
	}

	public List<ReporteReservacionesDTO> llenaTablaReservacionesGen(List<RelSolicitudEventoGeneralDTO> reservaciones) {
		List<ReporteReservacionesDTO> listaReporte = new ArrayList<>();

		for (RelSolicitudEventoGeneralDTO reservacion : reservaciones) {
			ReporteReservacionesDTO reporte = new ReporteReservacionesDTO();

			reporte.setRelSolicitudResGeneral(reservacion);
			reporte.setAreaSede(
					areaSedeService.buscarPorId(reservacion.getTblReservacionEventoGeneral().getIdAreaSede()));
			reporte.setPersonalizacion(personalizacionAreaService
					.buscarPorId(reservacion.getTblReservacionEventoGeneral().getIdPersonalizacionArea()));

			reporte.setSolicitud(reservacion.getTblSolicitudReservacion());
			reporte.setNombreEvento(reservacion.getTblReservacionEventoGeneral().getTblEventoGeneral().getNombre());
			reporte.setTipoEvento(TipoEventoEnum.GENERAL.getDescripcion());
			reporte.setEstatusReservacion(reservacion.getTblReservacionEventoGeneral().getCatEstatusReservacion());
			reporte.setFechaCreacion(reservacion.getTblReservacionEventoGeneral().getFechaRegistro());
			reporte.setFechaInicio(reservacion.getTblReservacionEventoGeneral().getFechaInicialReservacion());
			reporte.setFechaFinal(reservacion.getTblReservacionEventoGeneral().getFechaFinalReservacion());

			listaReporte.add(reporte);
		}

		return listaReporte;
	}

	public List<ReporteReservacionesDTO> llenaTablaReservaciones(
			List<RelSolicitudEventoCapacitacionDTO> reservaciones) {

		List<ReporteReservacionesDTO> listaReporte = new ArrayList<>();
		for (RelSolicitudEventoCapacitacionDTO reservacion : reservaciones) {
			ReporteReservacionesDTO reporte = new ReporteReservacionesDTO();

			reporte.setRelSolicitudReservacion(reservacion);
			reporte.setAreaSede(
					areaSedeService.buscarPorId(reservacion.getTblReservacionEventoCapacitacion().getIdAreaSede()));
			reporte.setPersonalizacion(personalizacionAreaService
					.buscarPorId(reservacion.getTblReservacionEventoCapacitacion().getIdPersonalizacionArea()));

			reporte.setSolicitud(reservacion.getTblSolicitudReservacion());
			reporte.setNombreEvento(reservacion.getTblReservacionEventoCapacitacion().getTblEvento().getNombreEc());
			reporte.setTipoEvento(TipoEventoEnum.CAPACITACION.getDescripcion());
			reporte.setEstatusReservacion(reservacion.getTblReservacionEventoCapacitacion().getCatEstatusReservacion());
			reporte.setFechaCreacion(reservacion.getTblReservacionEventoCapacitacion().getFechaRegistro());
			reporte.setFechaInicio(reservacion.getTblReservacionEventoCapacitacion().getFechaInicialReservacion());
			reporte.setFechaFinal(reservacion.getTblReservacionEventoCapacitacion().getFechaFinalReservacion());

			listaReporte.add(reporte);
		}

		return listaReporte;
	}

	public SedeService getSedeService() {
		return sedeService;
	}

	public void setSedeService(SedeService sedeService) {
		this.sedeService = sedeService;
	}

	public ConfiguracionAreaService getConfiguracionAreaService() {
		return configuracionAreaService;
	}

	public void setConfiguracionAreaService(ConfiguracionAreaService configuracionAreaService) {
		this.configuracionAreaService = configuracionAreaService;
	}

	public CatalogoComunService<CatEstatusReservacion, Integer> getCatEstatusReservacionService() {
		return catEstatusReservacionService;
	}

	public void setCatEstatusReservacionService(
			CatalogoComunService<CatEstatusReservacion, Integer> catEstatusReservacionService) {
		this.catEstatusReservacionService = catEstatusReservacionService;
	}

	public SolicitudEventoCapacitacionService getSolicitudEventoCapacitacionService() {
		return solicitudEventoCapacitacionService;
	}

	public void setSolicitudEventoCapacitacionService(
			SolicitudEventoCapacitacionService solicitudEventoCapacitacionService) {
		this.solicitudEventoCapacitacionService = solicitudEventoCapacitacionService;
	}

	public AreaSedeService getAreaSedeService() {
		return areaSedeService;
	}

	public void setAreaSedeService(AreaSedeService areaSedeService) {
		this.areaSedeService = areaSedeService;
	}

	public PersonalizacionAreaService getPersonalizacionAreaService() {
		return personalizacionAreaService;
	}

	public void setPersonalizacionAreaService(PersonalizacionAreaService personalizacionAreaService) {
		this.personalizacionAreaService = personalizacionAreaService;
	}

	public SolicitudEventoGeneralService getSolicitudEventoGeneralService() {
		return solicitudEventoGeneralService;
	}

	public void setSolicitudEventoGeneralService(SolicitudEventoGeneralService solicitudEventoGeneralService) {
		this.solicitudEventoGeneralService = solicitudEventoGeneralService;
	}
}
