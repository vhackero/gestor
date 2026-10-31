package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoFuenteConsultaAsistente;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MetadatosConsultaAsistenteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;

public class AsistenteCurricularV2ResultadoAdapterTest {
	@Test
	public void f12A3U02NoRecomiendaUnidadRestringidaPorSeriacion() {
		TablaCurricularAsistidaBean.FilaAsistidaDTO fila = new TablaCurricularAsistidaBean.FilaAsistidaDTO();
		fila.setRecomendada(Boolean.TRUE);
		DecisionUnidadCurricularV2DTO decision = new DecisionUnidadCurricularV2DTO();
		decision.setHabilitada(Boolean.FALSE);
		decision.getMotivos().add(CodigoMotivoCurricularV2.SERIACION_DIRECTA);

		new AsistenteCurricularV2ResultadoAdapter().aplicar(fila, decision);

		assertTrue(fila.getBloqueada());
		assertFalse(fila.getSeleccionable());
		assertFalse(fila.getRecomendada());
		assertTrue(fila.getMotivoPrincipal().contains("prerrequisitos"));
	}

	@Test
	public void f12A3U03ConservaPrioridadV2ParaRegularizacion() {
		TablaCurricularAsistidaBean.FilaAsistidaDTO fila = new TablaCurricularAsistidaBean.FilaAsistidaDTO();
		DecisionUnidadCurricularV2DTO decision = new DecisionUnidadCurricularV2DTO();
		decision.setHabilitada(Boolean.TRUE);
		decision.setPrioritaria(Boolean.TRUE);
		decision.getMotivos().add(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA);

		new AsistenteCurricularV2ResultadoAdapter().aplicar(fila, decision);

		assertTrue(fila.getSeleccionable());
		assertTrue(fila.getPrioritaria());
		assertTrue(fila.getMotivoPrincipal().contains("regularizar"));
	}

	@Test
	public void f12A3U04DistingueSinOfertaDeRestriccionAcademica() {
		TablaCurricularAsistidaBean.FilaAsistidaDTO fila = new TablaCurricularAsistidaBean.FilaAsistidaDTO();
		fila.setRecomendada(Boolean.TRUE);
		DecisionUnidadCurricularV2DTO decision = new DecisionUnidadCurricularV2DTO();
		decision.setHabilitada(Boolean.FALSE);
		decision.setOfertaEvaluadaV2(Boolean.FALSE);
		decision.getMotivos().add(CodigoMotivoCurricularV2.SIN_OFERTA);

		new AsistenteCurricularV2ResultadoAdapter().aplicar(fila, decision);

		assertFalse(fila.getBloqueada());
		assertFalse(fila.getSeleccionable());
		assertFalse(fila.getRecomendada());
		assertTrue(fila.getMotivoPrincipal().contains("oferta disponible"));
	}

	@Test
	public void f12A3U05MuestraFundamentoSoloParaClasificacionIrregular() {
		ResultadoEvaluacionCurricularV2DTO resultado = new ResultadoEvaluacionCurricularV2DTO();
		resultado.setSituacionAcademicaCalculada("IRREGULAR");
		resultado.setCantidadReprobadasVigentes(Integer.valueOf(4));
		resultado.getTrazas().add(trazaCausal(CodigoReglaCurricularV2.RN_1_2));
		resultado.getTrazas().add(trazaCausal(CodigoReglaCurricularV2.RN_6_6));
		resultado.getReglasEvaluadas().add(CodigoReglaCurricularV2.RN_6_4);

		assertTrue(TablaCurricularAsistidaBean.construirResumenClasificacionNoRegularV2(resultado).contains("4 unidades didácticas reprobadas"));
		assertTrue(TablaCurricularAsistidaBean.construirDetalleClasificacionNoRegularV2(resultado).contains("RN-1.2, RN-6.6"));
		assertFalse(TablaCurricularAsistidaBean.construirDetalleClasificacionNoRegularV2(resultado).contains("RN-6.4"));

		resultado.setSituacionAcademicaCalculada("REGULAR");
		assertTrue(TablaCurricularAsistidaBean.construirResumenClasificacionNoRegularV2(resultado) == null);
	}

	@Test
	public void f12A3U06FormateaCodigoDeReglaParaLaTrazaDiscreta() {
		assertTrue(TablaCurricularAsistidaBean.formatearCodigoReglaV2(CodigoReglaCurricularV2.RN_6_4).equals("RN-6.4"));
		assertTrue(TablaCurricularAsistidaBean.formatearCodigoReglaV2(CodigoReglaCurricularV2.CTR_01).equals("CTR-01"));
	}

	@Test
	public void f12A3U07MuestraSituacionDelResumenEnFormatoOracion() {
		assertTrue(TablaCurricularAsistidaBean.formatearEstadoEnOracion("IRREGULAR").equals("Irregular"));
		assertTrue(TablaCurricularAsistidaBean.formatearEstadoEnOracion("REGULAR").equals("Regular"));
	}

	@Test
	public void f12A3U08LimitaExpectativaAlMaximoDeCargaV2() {
		assertTrue(TablaCurricularAsistidaBean.limitarPorCargaV2(Integer.valueOf(10), Integer.valueOf(6)).equals(Integer.valueOf(6)));
		assertTrue(TablaCurricularAsistidaBean.limitarPorCargaV2(Integer.valueOf(3), Integer.valueOf(6)).equals(Integer.valueOf(3)));
	}

	@Test
	public void clasificaOptativaActualComoAdicionalSoloConMarcaCurricularExplicita() {
		assertEquals(TablaCurricularAsistidaBean.calcularOptativasRequeridasActuales(1, 0), 1);
		assertEquals(TablaCurricularAsistidaBean.calcularOptativasRequeridasActuales(2, 2), 0);
	}

	@Test
	public void muestraCorteYEstadosDeFuenteSinConvertirParcialEnCero() {
		MetadatosConsultaAsistenteDTO metadatos = new MetadatosConsultaAsistenteDTO();
		Calendar fechaCorte = Calendar.getInstance();
		fechaCorte.clear();
		fechaCorte.set(2026, Calendar.OCTOBER, 30);
		metadatos.setFechaCorteTrayectoria(fechaCorte.getTime());
		metadatos.setVersionReglas("PE2027-V1");
		metadatos.setEstadoExpediente(EstadoFuenteConsultaAsistente.DISPONIBLE);
		metadatos.setEstadoTrayectoria(EstadoFuenteConsultaAsistente.PARCIAL);
		metadatos.setEstadoOferta(EstadoFuenteConsultaAsistente.VACIO);
		metadatos.setEstadoEvaluacionV2(EstadoFuenteConsultaAsistente.DISPONIBLE);

		String resumen = TablaCurricularAsistidaBean.resumirFuentesConsulta(metadatos);
		assertTrue(resumen.contains("Trayectoria: parcial (corte 30/10/2026)"));
		assertTrue(resumen.contains("Expediente: disponible"));
		assertTrue(resumen.contains("Trayectoria: parcial"));
		assertTrue(resumen.contains("Oferta: sin registros"));
	}

	@Test
	public void expresaEstadosDeFuenteSinSimularUnCorteUnico() {
		MetadatosConsultaAsistenteDTO metadatos = new MetadatosConsultaAsistenteDTO();
		metadatos.setVersionReglas("PE2027-V1");
		metadatos.setEstadoExpediente(EstadoFuenteConsultaAsistente.DESACTUALIZADO);
		metadatos.setEstadoTrayectoria(EstadoFuenteConsultaAsistente.PARCIAL);
		metadatos.setEstadoOferta(EstadoFuenteConsultaAsistente.VACIO);
		metadatos.setEstadoEvaluacionV2(EstadoFuenteConsultaAsistente.ERROR);

		String resumen = TablaCurricularAsistidaBean.resumirFuentesConsulta(metadatos);
		assertTrue(resumen.contains("Expediente: desactualizada (corte no informado)"));
		assertTrue(resumen.contains("Trayectoria: parcial (corte no informado)"));
		assertTrue(resumen.contains("Oferta: sin registros (corte no informado)"));
		assertTrue(resumen.contains("Evaluación curricular: con error (evaluada no informado)"));
	}

	@Test
	public void errorDeEvaluacionV2EsControladoSinAlterarLosCortesDeLasFuentes() {
		MetadatosConsultaAsistenteDTO metadatos = new MetadatosConsultaAsistenteDTO();
		Calendar fechaTrayectoria = Calendar.getInstance();
		fechaTrayectoria.clear();
		fechaTrayectoria.set(2026, Calendar.OCTOBER, 30);
		metadatos.setFechaCorteTrayectoria(fechaTrayectoria.getTime());
		TablaCurricularAsistidaBean.actualizarEstadoEvaluacionV2(metadatos,
				EstadoFuenteConsultaAsistente.ERROR, null);

		assertEquals(metadatos.getEstadoEvaluacionV2(), EstadoFuenteConsultaAsistente.ERROR);
		assertEquals(metadatos.getFechaCorteTrayectoria(), fechaTrayectoria.getTime());
		assertTrue(metadatos.getFechaEvaluacionV2() == null);
	}

	@Test
	public void conservaDenominadorDelPlanYNoDuplicaCreditosPorIntentos() {
		TablaCurricularAsistidaBean.CreditosResumenMallaDTO resumen = new TablaCurricularAsistidaBean.CreditosResumenMallaDTO();
		resumen.registrarUnidadPlan("UD-01", "Obligatoria", "Unidad uno");
		resumen.registrarUnidadPlan("UD-02", "Optativa", "Unidad dos");
		resumen.registrarCreditos("UD-01", 6, true);
		resumen.registrarCreditos("UD-01", 6, true); // Segundo intento de la misma UD.
		resumen.registrarCreditos("UD-02", 4, false);

		assertEquals(resumen.getTotalUnidadesPlan(), 2);
		assertEquals(resumen.getTotalObligatoriasPlan(), 1);
		assertEquals(resumen.getRequeridos(), 10);
		assertEquals(resumen.getAprobados(), 6);
	}

	@Test
	public void diferenciaEjecucionV2CorrectaDeFuentesParcialesEnIndicadores() {
		MetadatosConsultaAsistenteDTO metadatos = new MetadatosConsultaAsistenteDTO();
		metadatos.setEstadoExpediente(EstadoFuenteConsultaAsistente.DISPONIBLE);
		metadatos.setEstadoTrayectoria(EstadoFuenteConsultaAsistente.PARCIAL);
		metadatos.setEstadoOferta(EstadoFuenteConsultaAsistente.VACIO);
		TablaCurricularAsistidaBean.actualizarEstadoEvaluacionV2(metadatos,
				EstadoFuenteConsultaAsistente.DISPONIBLE, Calendar.getInstance().getTime());

		assertTrue(TablaCurricularAsistidaBean.sonIndicadoresParciales(metadatos));
		assertTrue(TablaCurricularAsistidaBean.construirNotaIndicadoresParciales(metadatos)
				.contains("Indicadores parciales"));
	}

	@Test
	public void bajaAplicadaNoCuentaComoVigenteYUnaSolicitudPendienteNoProduceEseEfecto() {
		assertFalse(TablaCurricularAsistidaBean.esEstatusPeriodoActual("Baja parcial aplicada"));
		assertTrue(TablaCurricularAsistidaBean.esEstatusPeriodoActual("En curso"));
	}

	@Test
	public void presentaReprobacionesRegistradasSinConvertirlasEnOportunidadesRn14() {
		AsistenteInscripcionContextoDTO contexto = new AsistenteInscripcionContextoDTO();
		Map<Long, Long> reprobaciones = new HashMap<Long, Long>();
		reprobaciones.put(Long.valueOf(11L), Long.valueOf(2L));
		contexto.setIntentosReprobadosPorUd(reprobaciones);
		contexto.setIntentosReprobadosDisponibles(Boolean.TRUE);
		TablaCurricularAsistidaBean.FilaAsistidaDTO fila = new TablaCurricularAsistidaBean.FilaAsistidaDTO();
		fila.setUdId(Long.valueOf(11L));

		assertEquals(TablaCurricularAsistidaBean.reprobacionesRegistradas(contexto, fila), "Reprobaciones registradas: 2");
		assertEquals(TablaCurricularAsistidaBean.situacionOportunidadesNoDisponible(), "Cómputo de oportunidades no disponible");
		assertTrue(TablaCurricularAsistidaBean.resumenReprobacionesRegistradas(contexto).contains("No permite determinar oportunidades"));
	}

	@Test
	public void fuenteDeReprobacionesNoDisponibleNoSeMuestraComoCeroNiComoOportunidad() {
		TablaCurricularAsistidaBean.FilaAsistidaDTO fila = new TablaCurricularAsistidaBean.FilaAsistidaDTO();
		fila.setUdId(Long.valueOf(12L));
		AsistenteInscripcionContextoDTO contexto = new AsistenteInscripcionContextoDTO();
		assertEquals(TablaCurricularAsistidaBean.reprobacionesRegistradas(contexto, fila), "Dato no disponible");
		assertTrue(TablaCurricularAsistidaBean.resumenReprobacionesRegistradas(contexto).contains("no está disponible"));
	}

	@Test
	public void priorizacionIncluyeReprobadaDelContextoAunqueNoEsteEnLaOfertaVisual() {
		TablaCurricularAsistidaBean.FilaAsistidaDTO reprobada = new TablaCurricularAsistidaBean.FilaAsistidaDTO();
		reprobada.setUdId(Long.valueOf(51L));
		reprobada.setClave("UD-REPROBADA");
		reprobada.setSemestre(Integer.valueOf(5));
		reprobada.setEstatusHistorico("NO_ACREDITADA");
		TablaCurricularAsistidaBean.FilaAsistidaDTO ofertada = new TablaCurricularAsistidaBean.FilaAsistidaDTO();
		ofertada.setUdId(Long.valueOf(52L));
		ofertada.setClave("UD-OFERTADA");
		ofertada.setEstatusHistorico("NO_INSCRITA");

		List<TablaCurricularAsistidaBean.FilaAsistidaDTO> resultado = TablaCurricularAsistidaBean
				.filtrarFilasReprobadasPrioritarias(Arrays.asList(ofertada, reprobada));

		assertEquals(resultado.size(), 1);
		assertEquals(resultado.get(0).getClave(), "UD-REPROBADA");
	}

	@Test
	public void exponeFirmaElParaDetalleDeFilaPrioritaria() throws Exception {
		assertEquals(TablaCurricularAsistidaBean.class
				.getMethod("detalleFilaPrioritaria", TablaCurricularAsistidaBean.FilaAsistidaDTO.class)
				.getReturnType(), String.class);
		assertEquals(TablaCurricularAsistidaBean.class
				.getMethod("reprobacionesRegistradasFilaPrioritaria", TablaCurricularAsistidaBean.FilaAsistidaDTO.class)
				.getReturnType(), String.class);
		assertEquals(TablaCurricularAsistidaBean.class
				.getMethod("situacionOportunidadesFilaPrioritaria", TablaCurricularAsistidaBean.FilaAsistidaDTO.class)
				.getReturnType(), String.class);
	}

	@Test
	public void deduplicaBajasParcialesYSeparaLasTemporalesPorTipoRegistrado() {
		InscripcionBajasDTO parcial = baja(Long.valueOf(61L), Long.valueOf(7001L), "Baja parcial");
		InscripcionBajasDTO parcialDuplicada = baja(Long.valueOf(61L), Long.valueOf(7001L), "BAJA PARCIAL");
		InscripcionBajasDTO parcialDistinta = baja(Long.valueOf(61L), Long.valueOf(7002L), "BAJA PARCIAL");
		InscripcionBajasDTO temporal = baja(Long.valueOf(62L), Long.valueOf(7003L), "Baja temporal");

		assertEquals(TablaCurricularAsistidaBean.filtrarBajasPorTipo(
				Arrays.asList(parcial, parcialDuplicada, parcialDistinta, temporal), "PARCIAL").size(), 1);
		assertEquals(TablaCurricularAsistidaBean.contarMovimientosBajaPorTipo(
				Arrays.asList(parcial, parcialDuplicada, parcialDistinta, temporal), "PARCIAL"), 2);
		assertEquals(TablaCurricularAsistidaBean.filtrarBajasPorTipo(
				Arrays.asList(parcial, parcialDuplicada, parcialDistinta, temporal), "TEMPORAL").size(), 1);
	}

	private InscripcionBajasDTO baja(Long idPrograma, Long idBaja, String tipo) {
		InscripcionBajasDTO baja = new InscripcionBajasDTO();
		baja.setIdPrograma(idPrograma);
		baja.setIdBaja(idBaja);
		baja.setTipoBaja(tipo);
		return baja;
	}

	private TrazaReglaAplicadaV2DTO trazaCausal(CodigoReglaCurricularV2 regla) {
		TrazaReglaAplicadaV2DTO traza = new TrazaReglaAplicadaV2DTO();
		traza.setCodigoRegla(regla);
		traza.setResultado("SITUACION_ACADEMICA_IRREGULAR");
		return traza;
	}
}
