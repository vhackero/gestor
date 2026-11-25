package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertSame;

import java.lang.reflect.Field;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ContextoEvaluacionCurricularV2Adapter;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.MallaCurricularV2FacadeImpl;

/** La Malla reutiliza el adaptador y el evaluador V2; no crea una evaluación paralela. */
public class MallaCurricularV2FacadeTest {

	@Test
	public void f12A2U01ConstruyeContextoConAdaptadorYEjecutaMotorComun() throws Exception {
		AsistenteInscripcionContextoDTO fuente = new AsistenteInscripcionContextoDTO();
		ContextoEvaluacionCurricularV2DTO contextoV2 = new ContextoEvaluacionCurricularV2DTO();
		ResultadoEvaluacionCurricularV2DTO esperado = new ResultadoEvaluacionCurricularV2DTO();
		ContextoEvaluacionCurricularV2Adapter adaptador = Mockito.mock(ContextoEvaluacionCurricularV2Adapter.class);
		EvaluacionCurricularV2Service evaluador = Mockito.mock(EvaluacionCurricularV2Service.class);
		Mockito.when(adaptador.construirDesdeAsistente(fuente)).thenReturn(contextoV2);
		Mockito.when(evaluador.evaluarSombra(contextoV2)).thenReturn(esperado);
		MallaCurricularV2FacadeImpl fachada = new MallaCurricularV2FacadeImpl();
		inyectar(fachada, "adaptador", adaptador);
		inyectar(fachada, "evaluador", evaluador);

		assertSame(fachada.evaluarSombra(fuente), esperado);
		Mockito.verify(adaptador).construirDesdeAsistente(fuente);
		Mockito.verify(evaluador).evaluarSombra(contextoV2);
	}

	private void inyectar(Object objetivo, String nombre, Object valor) throws Exception {
		Field campo = objetivo.getClass().getDeclaredField(nombre);
		campo.setAccessible(true);
		campo.set(objetivo, valor);
	}
}
