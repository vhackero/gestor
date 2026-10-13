package mx.gob.sedesol.basegestor.admin;
import java.io.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import mx.gob.sedesol.basegestor.commons.utils.ArchivoAsignacionPlan;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;

public class ArchivoAsignacionPlanTest {
    private byte[] archivo(boolean repetido,boolean formula)throws Exception{
        SXSSFWorkbook libro=ArchivoAsignacionPlan.libro(Collections.singletonList(new Object[]{3,"Plan A",17,"Convocatoria"}));
        try{Sheet sheet=libro.getSheetAt(0);ArchivoAsignacionPlan.fila(sheet,1,new Object[]{"ES1","Ana","999","3","17"});
            if(formula)sheet.getRow(1).getCell(3).setCellFormula("1+2");
            if(repetido)ArchivoAsignacionPlan.fila(sheet,2,new Object[]{"es1","Ana","10","3","17"});
            ByteArrayOutputStream out=new ByteArrayOutputStream();libro.write(out);return out.toByteArray();
        }finally{libro.close();libro.dispose();}
    }
    @Test public void plantillaSePuedeCargarSinConfiarEnIdInformativo()throws Exception{
        List<FilaAsignacionPlanDTO> filas=ArchivoAsignacionPlan.leer("plantilla.xlsx",archivo(false,false));
        assertEquals(1,filas.size());assertEquals("es1",filas.get(0).getUsuario());assertEquals("3",filas.get(0).getPlan());assertEquals(2,filas.get(0).getFila());
    }
    @Test public void usuariosRepetidosSonRechazados()throws Exception{
        try{ArchivoAsignacionPlan.leer("plantilla.xlsx",archivo(true,false));fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("repetido"));}
    }
    @Test public void formulasNoSeEjecutanNiImportan()throws Exception{
        try{ArchivoAsignacionPlan.leer("plantilla.xlsx",archivo(false,true));fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("formula"));}
    }
    @Test public void admiteQuinceMilRegistros()throws Exception{
        SXSSFWorkbook libro=ArchivoAsignacionPlan.libro(Collections.emptyList());byte[] contenido;
        try{for(int n=1;n<=15000;n++)ArchivoAsignacionPlan.fila(libro.getSheetAt(0),n,new Object[]{"es"+n,"Ana",n,"3","17"});
            ByteArrayOutputStream out=new ByteArrayOutputStream();libro.write(out);contenido=out.toByteArray();}
        finally{libro.close();libro.dispose();}
        assertEquals(15000,ArchivoAsignacionPlan.leer("datos.xlsx",contenido).size());
    }
}
