package mx.gob.sedesol.basegestor.commons.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaExternaCargaDTO;

public final class ArchivoPersonaExterna {
    public static final int MAX_BYTES = 5 * 1024 * 1024;
    private ArchivoPersonaExterna() { }

    public static List<PersonaExternaCargaDTO> leer(String nombre,byte[] contenido) throws Exception {
        if (contenido == null || contenido.length == 0 || contenido.length > MAX_BYTES) throw new IllegalArgumentException("Seleccione un archivo de hasta 5 MB.");
        String extension = nombre == null ? "" : nombre.toLowerCase(Locale.ROOT);
        List<List<String>> registros = new ArrayList<>();
        List<Integer> numeros = new ArrayList<>();
        if (extension.endsWith(".csv")) {
            String texto;
            try {
                texto = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(contenido)).toString();
            } catch (CharacterCodingException e) { throw new IllegalArgumentException("Guarde el CSV con codificación UTF-8."); }
            if (texto.startsWith("\uFEFF")) texto=texto.substring(1);
            registros = csv(texto);
            for (int n=1;n<=registros.size();n++) numeros.add(n);
        } else if (extension.endsWith(".xls") || extension.endsWith(".xlsx")) {
            try (Workbook libro = WorkbookFactory.create(new ByteArrayInputStream(contenido))) {
                Sheet hoja = libro.getSheetAt(0);
                if (hoja.getLastRowNum() > 16000) throw new IllegalArgumentException("El archivo supera el límite de filas; máximo 15000 registros.");
                DataFormatter formatter = new DataFormatter(Locale.ROOT);
                for (int n=0;n<=hoja.getLastRowNum();n++) {
                    Row fila=hoja.getRow(n);
                    if (fila==null) continue;
                    List<String> datos = new ArrayList<>();
                    int columnas=Math.max(PersonaExternaCargaDTO.COLUMNAS.size(),fila.getLastCellNum());
                    for (int col=0;col<columnas;col++) {
                        Cell celda=fila.getCell(col);
                        String dato="";
                        if (celda!=null) {
                            if (celda.getCellType()==Cell.CELL_TYPE_FORMULA || celda.getCellType()==Cell.CELL_TYPE_ERROR)
                                throw new IllegalArgumentException("Fila "+(n+1)+": no se admiten fórmulas ni celdas con errores.");
                            if (n>0 && celda.getCellType()==Cell.CELL_TYPE_NUMERIC && col==8 && DateUtil.isCellDateFormatted(celda))
                                dato=new SimpleDateFormat("yyyy-MM-dd").format(celda.getDateCellValue());
                            else {
                                if (n>0 && celda.getCellType()==Cell.CELL_TYPE_NUMERIC && col!=5 && col!=8 && col!=11 && col!=12 && col!=13)
                                    throw new IllegalArgumentException("Fila "+(n+1)+": capture los textos, matrículas y contraseñas como Texto en Excel.");
                                dato=formatter.formatCellValue(celda);
                            }
                        }
                        datos.add(dato);
                    }
                    while (datos.size()>14 && datos.get(datos.size()-1).trim().isEmpty()) datos.remove(datos.size()-1);
                    if (n==0 || !vacia(datos)) { registros.add(datos); numeros.add(n+1); }
                }
            }
        } else throw new IllegalArgumentException("Sólo se admiten archivos CSV, XLS y XLSX.");
        if (registros.isEmpty()) throw new IllegalArgumentException("El archivo está vacío.");
        List<String> encabezados=new ArrayList<>();
        for (String celda:registros.get(0)) encabezados.add(celda.trim().toLowerCase(Locale.ROOT));
        if (!encabezados.equals(PersonaExternaCargaDTO.COLUMNAS)) throw new IllegalArgumentException("Los encabezados y su orden deben coincidir con la plantilla (14 columnas).");
        List<PersonaExternaCargaDTO> filas=new ArrayList<>();
        for (int n=1;n<registros.size();n++) {
            List<String> datos=registros.get(n);
            if (vacia(datos)) continue;
            if (datos.size()!=14) throw new IllegalArgumentException("Fila "+numeros.get(n)+": se requieren exactamente 14 columnas.");
            filas.add(new PersonaExternaCargaDTO(numeros.get(n),datos));
            if (filas.size()>15000) throw new IllegalArgumentException("Se admiten como máximo 15000 registros por archivo.");
        }
        if (filas.isEmpty()) throw new IllegalArgumentException("El archivo no contiene registros para importar.");
        return filas;
    }
    private static boolean vacia(List<String> fila) { for(String v:fila) if(!v.trim().isEmpty()) return false; return true; }

    private static List<List<String>> csv(String texto) {
        String primera=texto.split("\\r?\\n",2)[0];
        char separador=primera.indexOf(';')>=0 && primera.indexOf(',')<0 ? ';' : ',';
        List<List<String>> filas=new ArrayList<>();
        List<String> fila=new ArrayList<>();
        StringBuilder celda=new StringBuilder();
        boolean comillas=false,cerrada=false;
        for (int n=0;n<texto.length();n++) {
            char c=texto.charAt(n);
            if (comillas) {
                if (c=='"') {
                    if(n+1<texto.length() && texto.charAt(n+1)=='"') {celda.append('"');n++;}
                    else {comillas=false;cerrada=true;}
                } else celda.append(c);
            } else if (c==separador || c=='\n' || c=='\r') {
                fila.add(celda.toString());celda.setLength(0);cerrada=false;
                if(c!=separador) {
                    filas.add(fila);fila=new ArrayList<>();
                    if(c=='\r' && n+1<texto.length() && texto.charAt(n+1)=='\n') n++;
                    if(filas.size()>16001) throw new IllegalArgumentException("El CSV supera el límite de filas.");
                }
            } else if(c=='"' && celda.length()==0 && !cerrada) comillas=true;
            else {
                if(cerrada || c=='"') throw new IllegalArgumentException("El CSV contiene comillas incorrectas.");
                celda.append(c);
            }
        }
        if(comillas) throw new IllegalArgumentException("El CSV contiene comillas sin cerrar.");
        if(celda.length()>0 || !fila.isEmpty() || cerrada) {fila.add(celda.toString());filas.add(fila);}
        return filas;
    }

    public static byte[] plantilla(List<Object[]> catalogos) throws IOException {
        try(XSSFWorkbook libro=new XSSFWorkbook();ByteArrayOutputStream salida=new ByteArrayOutputStream()) {
            Sheet datos=libro.createSheet("Datos");
            Row encabezado=datos.createRow(0);
            for(int c=0;c<14;c++) {encabezado.createCell(c).setCellValue(PersonaExternaCargaDTO.COLUMNAS.get(c));datos.setColumnWidth(c,25*256);}
            if(!catalogos.isEmpty()) {
                Object[] ref=catalogos.get(0);
                String[] ejemplo={"ES_EJEMPLO_001","Ejemplo2026!","Ana","Pérez","García",ref[0].toString(),"DCSA",
                    "es_ejemplo_001@example.com","1990-01-01","PEGA900101MDFRRL09","LIC",ref[4].toString(),"1",ref[2].toString()};
                Row row=datos.createRow(1);
                for(int c=0;c<14;c++) row.createCell(c).setCellValue(ejemplo[c]);
            }
            datos.createFreezePane(0,1);
            Sheet ayuda=libro.createSheet("Instrucciones");
            String[] instrucciones={"Reemplace el ejemplo por los datos reales antes de importar.",
                "Primera hoja Datos: 14 encabezados en el orden indicado. Todos los campos son obligatorios.",
                "No incluya id_persona_externo: se genera automáticamente. No incluya la columna calculada de control.",
                "Nombre de usuario: único, máximo 100 caracteres; letras, números, punto, guion, guion bajo o @.",
                "Nombre de usuario y todos los textos: formato Texto; no se admiten fórmulas. No altere los ceros de matrículas.",
                "fecha_nacimiento: AAAA-MM-DD o celda de fecha Excel; debe ser válida y no futura. CURP: formato de 18 caracteres.",
                "id_plan, id_fuente_externa, id_convocatoria: IDs del catálogo; el plan debe pertenecer a la convocatoria.",
                "tipo_usuario: 1 = Interno; 2 = Externo. division y nivel: claves usadas por su fuente (ejemplo DCSA y LIC).",
                "password: contraseña del usuario requerido por el flujo actual de importación; no la comparta en reportes.",
                "Límite: 5 MB y 15000 registros. CSV: UTF-8, separado por coma o punto y coma.",
                "Se rechazan usuarios duplicados dentro del archivo o en la tabla. Si hay errores no se inserta ningún registro."};
            for(int n=0;n<instrucciones.length;n++) ayuda.createRow(n).createCell(0).setCellValue(instrucciones[n]);
            ayuda.setColumnWidth(0,100*256);
            Sheet catalogo=libro.createSheet("Catalogos");
            String[] titulos={"id_plan","Plan","id_convocatoria","Convocatoria","id_fuente_externa","Fuente externa"};
            catalogo.createRow(0);
            for(int c=0;c<6;c++) {catalogo.getRow(0).createCell(c).setCellValue(titulos[c]);catalogo.setColumnWidth(c,(c%2==0?22:45)*256);}
            int n=1;for(Object[] ref:catalogos) {Row row=catalogo.createRow(n++);for(int c=0;c<6;c++) row.createCell(c).setCellValue(ref[c]==null?"":ref[c].toString());}
            libro.write(salida);return salida.toByteArray();
        }
    }
}
