package mx.gob.sedesol.gestorweb.gestionescolar;

import java.util.*;
import javax.faces.context.FacesContext;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.primefaces.context.RequestContext;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import mx.gob.sedesol.gestorweb.beans.gestionescolar.DispersionesBean;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionesService;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.*;

public class DispersionCargaDescargaTest {
    private FacesContext faces;
    @Before public void prepararContexto() {
        faces=mock(FacesContext.class);when(faces.getAttributes()).thenReturn(new HashMap<>());
        javax.faces.context.ExternalContext externo=mock(javax.faces.context.ExternalContext.class);
        when(faces.getExternalContext()).thenReturn(externo);
        Contexto.instalar(faces);
    }
    @After public void cerrarContexto() { RequestContext.setCurrentInstance(null,faces);Contexto.instalar(null); }
    private abstract static class Contexto extends FacesContext {
        static void instalar(FacesContext c) { setCurrentInstance(c); }
    }
    @Test public void limpiarGeneralNoBuscaPendientesDeOtrosModulos() {
        DispersionesService servicio=mock(DispersionesService.class);
        DispersionesBean bean=new DispersionesBean();bean.setDispersionesService(servicio);
        bean.limpiarCampos();verifyZeroInteractions(servicio);
    }
    @Test public void limpiarExistentesConservaCargaDeSusProcesos() {
        DispersionesService servicio=mock(DispersionesService.class);
        when(servicio.consultarProcesosConDispersion()).thenReturn(Collections.emptyList());
        when(servicio.consultarProcesosSinDispersion()).thenReturn(Collections.emptyList());
        DispersionesBean bean=new DispersionesBean();bean.setDispersionesService(servicio);
        bean.limpiarCamposMatriculaExistente();
        verify(servicio).consultarProcesosConDispersion();verify(servicio).consultarProcesosSinDispersion();
    }
    @Test public void generaArchivoAntesDeAvisarDescargaYVacioNoReutilizaAnterior() throws Exception {
        RequestContext peticion=mock(RequestContext.class);RequestContext.setCurrentInstance(peticion,faces);
        try {
            DispersionesService servicio=mock(DispersionesService.class);
            DispersionesBean bean=new DispersionesBean();bean.setDispersionesService(servicio);bean.limpiarCampos();
            DispersionMatriculaExistenteDTO dispersion=new DispersionMatriculaExistenteDTO();
            dispersion.setIdDispersion(1);dispersion.setIdProcesoInscripcionMatricular(17);dispersion.setIdPrograma(114);
            DispersionPendienteDTO alumno=new DispersionPendienteDTO();alumno.setMatricula("00001");alumno.setNombre("Alumno prueba");
            when(servicio.consultarPendientesDispersionExistente(1,17,114)).thenReturn(Arrays.asList(alumno));
            bean.descargarPendientesDispersionExistente(dispersion);
            assertNotNull(bean.getArchivoPendientes());verify(peticion).addCallbackParam("excelPendientesListo",true);
            try(XSSFWorkbook libro=new XSSFWorkbook(bean.getArchivoPendientes().getStream())) {
                assertEquals("00001",libro.getSheetAt(0).getRow(1).getCell(0).getStringCellValue());
            }
            reset(peticion);when(servicio.consultarPendientesDispersionExistente(1,17,114)).thenReturn(Collections.emptyList());
            bean.descargarPendientesDispersionExistente(dispersion);
            assertNull(bean.getArchivoPendientes());verify(peticion,never()).addCallbackParam("excelPendientesListo",true);
        } finally { RequestContext.setCurrentInstance(null,faces);Contexto.instalar(null); }
    }
}
