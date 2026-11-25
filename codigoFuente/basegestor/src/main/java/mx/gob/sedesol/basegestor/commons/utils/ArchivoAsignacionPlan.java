package mx.gob.sedesol.basegestor.commons.utils;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;

public final class ArchivoAsignacionPlan {
    public static final int MAX_BYTES=5*1024*1024,MAX_FILAS=15000;
    public static final List<String> COLUMNAS=Arrays.asList("usuario","nombre","id_persona","id_plan","id_convocatoria");
    private static final String PREFIJO="gw.planUsuario.masivo.";
    private ArchivoAsignacionPlan(){}
    public static List<FilaAsignacionPlanDTO> leer(String nombre,byte[] contenido)throws IOException {
        if(nombre==null || !nombre.toLowerCase(Locale.ROOT).endsWith(".xlsx") || contenido==null || contenido.length==0 || contenido.length>MAX_BYTES)
            throw new ErrorAsignacionPlan(PREFIJO+"archivo");
        try(Workbook libro=WorkbookFactory.create(new ByteArrayInputStream(contenido))){
            if(libro.getNumberOfSheets()==0)throw new ErrorAsignacionPlan(PREFIJO+"encabezados");
            Sheet sheet=libro.getSheetAt(0);DataFormatter formato=new DataFormatter(Locale.ROOT);
            if(sheet.getLastRowNum()>MAX_FILAS)throw new ErrorAsignacionPlan(PREFIJO+"limite");
            List<FilaAsignacionPlanDTO> filas=new ArrayList<>();Set<String> usuarios=new HashSet<>();
            for(int n=0;n<=sheet.getLastRowNum();n++){
                Row fila=sheet.getRow(n);if(fila==null){if(n==0)throw new ErrorAsignacionPlan(PREFIJO+"encabezados");continue;}
                String[] datos=new String[5];boolean vacia=true;
                for(int c=0;c<Math.max(5,fila.getLastCellNum());c++){
                    Cell cell=fila.getCell(c);String valor="";
                    if(cell!=null){if(cell.getCellType()==Cell.CELL_TYPE_FORMULA || cell.getCellType()==Cell.CELL_TYPE_ERROR)throw new ErrorAsignacionPlan(PREFIJO+"formula",n+1);
                        valor=formato.formatCellValue(cell).trim();}
                    if(c>=5 && !valor.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"encabezados");
                    if(c<5)datos[c]=valor;
                    if(!valor.isEmpty())vacia=false;
                }
                if(n==0){if(!Arrays.asList(datos).equals(COLUMNAS))throw new ErrorAsignacionPlan(PREFIJO+"encabezados");continue;}
                if(vacia)continue;
                String usuario=datos[0].toLowerCase(Locale.ROOT);
                if(!usuario.matches("[a-z0-9_.@-]{1,100}"))throw new ErrorAsignacionPlan(PREFIJO+"usuario",n+1);
                if(!usuarios.add(usuario))throw new ErrorAsignacionPlan(PREFIJO+"repetido",n+1,usuario);
                filas.add(new FilaAsignacionPlanDTO(n+1,usuario,datos[3],datos[4]));
            }
            if(filas.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"vacio");
            return filas;
        }catch(ErrorAsignacionPlan e){throw e;}catch(Exception e){throw new ErrorAsignacionPlan(PREFIJO+"archivo");}
    }
    public static SXSSFWorkbook libro(List<Object[]> catalogos){
        SXSSFWorkbook libro=new SXSSFWorkbook(100);libro.setCompressTempFiles(true);
        Sheet datos=libro.createSheet("Asignaciones");fila(datos,0,COLUMNAS.toArray());datos.createFreezePane(0,1);
        for(int n=0;n<5;n++)datos.setColumnWidth(n,(n==1?45:24)*256);
        Sheet catalogo=libro.createSheet("Catalogos");fila(catalogo,0,new Object[]{"id_plan","plan","id_convocatoria","convocatoria"});int n=1;
        for(Object[] registro:catalogos)fila(catalogo,n++,registro);
        for(int c=0;c<4;c++)catalogo.setColumnWidth(c,(c%2==0?20:45)*256);
        Sheet ayuda=libro.createSheet("Instrucciones");ayuda.setColumnWidth(0,110*256);
        String[] instrucciones={"Complete id_plan e id_convocatoria en la primera hoja usando los IDs de Catalogos.","usuario es el identificador utilizado para asignar; nombre e id_persona son informativos y no se utilizan para cambiar la identidad.","Conserve los cinco encabezados y su orden; puede eliminar filas que no quiera procesar.","Se omiten los usuarios que ya tienen cualquier asignación de plan al procesar el bloque.","No se admiten fórmulas ni usuarios repetidos. Máximo 15000 registros y 5 MB por archivo.","El guardado se realiza por bloques de 250. Si falla un bloque, los anteriores quedan guardados; puede corregir y volver a cargar el archivo."};
        for(int c=0;c<instrucciones.length;c++)fila(ayuda,c,new Object[]{instrucciones[c]});
        return libro;
    }
    public static void fila(Sheet hoja,int indice,Object[] valores){Row fila=hoja.createRow(indice);for(int n=0;n<valores.length;n++){Cell cell=fila.createCell(n,Cell.CELL_TYPE_STRING);cell.setCellValue(valores[n]==null?"":valores[n].toString());}}
}
