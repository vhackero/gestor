package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.gestorweb.commons.dto.MallaDiagramaNodoDTO;

/** Verifica que la Malla sólo traduce las decisiones normativas recibidas de V2. */
public class MallaCurricularAlumnoBeanV2Test {

	@Test
	public void aplicaRestriccionMotivoYPrioridadSinAlterarEstadoLegacy() throws Exception {
		DecisionUnidadCurricularV2DTO decision = decision(101L, false);
		decision.getMotivos().add(CodigoMotivoCurricularV2.SERIACION_DIRECTA);
		decision.setPrioritaria(Boolean.TRUE);
		Map<Long, DecisionUnidadCurricularV2DTO> decisiones = new LinkedHashMap<Long, DecisionUnidadCurricularV2DTO>();
		decisiones.put(101L, decision);

		MallaDiagramaNodoDTO nodo = new MallaDiagramaNodoDTO();
		nodo.setEstatus("No acreditada");
		Map<Integer, MallaDiagramaNodoDTO> nodos = new LinkedHashMap<Integer, MallaDiagramaNodoDTO>();
		nodos.put(101, nodo);
		MallaCurricularV2ResultadoAdapter.AplicacionResultadoMallaV2 aplicacion = new MallaCurricularV2ResultadoAdapter()
				.aplicar(nodos, decisiones, "IRREGULAR");

		assertTrue(nodo.isBloqueada());
		assertEquals("No acreditada", nodo.getEstatus());
		assertTrue(nodo.getDetalleMotivoPrincipal().contains("Requiere acreditar previamente"));
		assertFalse(nodo.getDetalleMotivoPrincipal().contains("SERIACION_DIRECTA"));
		assertTrue(nodo.getDetalleAccionSugerida().contains("prioritaria"));
		assertEquals("IRREGULAR", aplicacion.getSituacionAcademica());
	}

	@Test
	public void conservaUnidadHabilitadaYMarcaRevisionSoloSiV2LaDeclara() throws Exception {
		DecisionUnidadCurricularV2DTO habilitada = decision(201L, true);
		DecisionUnidadCurricularV2DTO revision = decision(202L, false);
		revision.getEstadosSombra().add(EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
		Map<Long, DecisionUnidadCurricularV2DTO> decisiones = new LinkedHashMap<Long, DecisionUnidadCurricularV2DTO>();
		decisiones.put(201L, habilitada);
		decisiones.put(202L, revision);

		MallaDiagramaNodoDTO nodoHabilitado = new MallaDiagramaNodoDTO();
		nodoHabilitado.setEstatus("No inscrita");
		MallaDiagramaNodoDTO nodoRevision = new MallaDiagramaNodoDTO();
		nodoRevision.setEstatus("No inscrita");
		Map<Integer, MallaDiagramaNodoDTO> nodos = new LinkedHashMap<Integer, MallaDiagramaNodoDTO>();
		nodos.put(201, nodoHabilitado);
		nodos.put(202, nodoRevision);
		MallaCurricularV2ResultadoAdapter.AplicacionResultadoMallaV2 aplicacion = new MallaCurricularV2ResultadoAdapter()
				.aplicar(nodos, decisiones, "REGULAR");

		assertFalse(nodoHabilitado.isBloqueada());
		assertTrue(nodoRevision.isBloqueada());
		assertEquals("No inscrita", nodoRevision.getEstatus());
		assertEquals(1, aplicacion.getRequiereRevision());
	}

	@Test
	public void explicaMotivosConTextoUsuarioYReglaSinExponerCodigosInternos() {
		DecisionUnidadCurricularV2DTO decision = decision(301L, false);
		decision.getMotivos().add(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA);
		decision.getMotivos().add(CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
		TrazaReglaAplicadaV2DTO traza = new TrazaReglaAplicadaV2DTO();
		traza.setCodigoRegla(CodigoReglaCurricularV2.RN_1_2);
		decision.getTrazas().add(traza);
		Map<Long, DecisionUnidadCurricularV2DTO> decisiones = new LinkedHashMap<Long, DecisionUnidadCurricularV2DTO>();
		decisiones.put(301L, decision);
		MallaDiagramaNodoDTO nodo = new MallaDiagramaNodoDTO();
		nodo.setEstatus("No acreditada");
		Map<Integer, MallaDiagramaNodoDTO> nodos = new LinkedHashMap<Integer, MallaDiagramaNodoDTO>();
		nodos.put(301, nodo);

		new MallaCurricularV2ResultadoAdapter().aplicar(nodos, decisiones, "IRREGULAR");

		assertEquals("No acreditada", nodo.getEstatus());
		assertTrue(nodo.getDetalleMotivoPrincipal().contains("está reprobada"));
		assertTrue(nodo.getDetalleMotivoPrincipal().contains("RN-1.2"));
		assertFalse(nodo.getDetalleMotivoPrincipal().contains("REPROBACION_ACUMULADA"));
		assertFalse(nodo.getDetalleMotivoPrincipal().contains("DATOS_INSUFICIENTES"));
	}

	@Test
	public void estadoReprobadoPrevaleceSobreEtiquetaOptativaOpcional() {
		MallaDiagramaNodoDTO nodo = new MallaDiagramaNodoDTO();
		nodo.setEstatus("No acreditada");

		String estado = new MallaCurricularV2ResultadoAdapter().normalizarEstatusDetalle("OPCIONAL", nodo);

		assertEquals("No acreditada", estado);
	}

	@Test
	public void muestraReprobadaEnCursoSinAlterarSuEstadoAcademico() {
		MallaDiagramaNodoDTO nodo = new MallaDiagramaNodoDTO();
		nodo.setEstatus("No acreditada");
		nodo.setEnCurso(true);

		String estado = new MallaCurricularV2ResultadoAdapter().construirEstatusContextual(
				"No acreditada", "En curso", nodo);

		assertEquals(estado, "No acreditada (en curso)");
		assertEquals(nodo.getEstatus(), "No acreditada");
	}

	@Test
	public void bloqueoCurricularNoConvierteObligatoriaEnSeriada() {
		MallaDiagramaNodoDTO nodo = new MallaDiagramaNodoDTO();
		nodo.setBloqueada(true);

		assertFalse(MallaCurricularAlumnoBean.esObligatoriaSeriada(nodo));

		nodo.setTieneDependencias(true);
		assertTrue(MallaCurricularAlumnoBean.esObligatoriaSeriada(nodo));
	}

	private DecisionUnidadCurricularV2DTO decision(Long idPrograma, boolean habilitada) {
		DecisionUnidadCurricularV2DTO decision = new DecisionUnidadCurricularV2DTO();
		decision.setIdPrograma(idPrograma);
		decision.setHabilitada(Boolean.valueOf(habilitada));
		return decision;
	}

}
