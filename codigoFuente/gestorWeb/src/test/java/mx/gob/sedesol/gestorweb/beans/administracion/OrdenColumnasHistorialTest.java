package mx.gob.sedesol.gestorweb.beans.administracion;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import org.mockito.ArgumentCaptor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ColumnasHistorial;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

public class OrdenColumnasHistorialTest {
    private abstract static class Contexto extends FacesContext {
        static void activar(FacesContext faces) { setCurrentInstance(faces); }
    }
    @Before public void prepararContexto() {
        FacesContext faces = mock(FacesContext.class);
        when(faces.getExternalContext()).thenReturn(mock(ExternalContext.class));
        Contexto.activar(faces);
    }
    @After public void liberarContexto() { Contexto.activar(null); }
    @Test public void cargaOrdenYOcultasYEnviaNuevoOrdenAlServicio() {
        ParametroSistemaBean bean = spy(new ParametroSistemaBean());
        UsuarioSessionDTO usuario = mock(UsuarioSessionDTO.class);
        when(usuario.getIdPersona()).thenReturn(1L);
        doReturn(usuario).when(bean).getUsuarioEnSession();
        ParametroSistemaDTO dto = new ParametroSistemaDTO(1L);
        dto.setClave(ColumnasHistorial.PARAMETRO);
        dto.setValor("CALIFICACION,ASIGNATURA,GRUPO");
        bean.setParametroModificar(dto);
        bean.cargarParametro();
        assertEquals(Arrays.asList("CALIFICACION","ASIGNATURA","GRUPO"), bean.getColumnasHistorialModelo().getTarget());
        assertEquals(12, bean.getColumnasHistorialModelo().getSource().size());
        assertFalse(bean.getColumnasHistorialModelo().getSource().contains("GRUPO"));

        bean.getColumnasHistorialModelo().setTarget(Arrays.asList("GRUPO","ASIGNATURA"));
        ParametroSistemaService service = mock(ParametroSistemaService.class);
        bean.setParametroSistemaService(service);
        SistemaBean sistema = new SistemaBean();
        bean.setSistema(sistema);
        // Aisla los mensajes JSF: aqui se verifica exactamente lo enviado a persistencia.
        ResultadoDTO<ParametroSistemaDTO> resultado = new ResultadoDTO<>();
        resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
        when(service.actualizar(any(ParametroSistemaDTO.class))).thenReturn(resultado);
        doNothing().when(bean).agregarMsgError(anyListOf(String.class), isNull(String.class), same(sistema));
        bean.guardarParametro();
        ArgumentCaptor<ParametroSistemaDTO> guardado = ArgumentCaptor.forClass(ParametroSistemaDTO.class);
        verify(service).actualizar(guardado.capture());
        assertEquals("GRUPO,ASIGNATURA", guardado.getValue().getValor());
        bean.setParametroModificar(guardado.getValue());
        bean.cargarParametro();
        assertEquals(Arrays.asList("GRUPO","ASIGNATURA"), bean.getColumnasHistorialModelo().getTarget());
        assertTrue(bean.getColumnasHistorialModelo().getSource().contains("CALIFICACION"));
    }
}
