package mx.gob.sedesol.basegestor.admin;

import static org.junit.Assert.*;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.utils.ArchivoPersonaExterna;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaExternaCargaRepository;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaExternaCargaServiceImpl;

public class PersonaExternaCargaTest {
    private PersonaExternaCargaDTO fila(int numero,String usuario) {
        return new PersonaExternaCargaDTO(numero,Arrays.asList(usuario,"Ejemplo2026!","Ana","Pérez","García","3","DCSA",
            "ana@example.com","1990-01-01","PEGA900101MDFRRL09","LIC","1","1","3"));
    }
    private static class Repo extends PersonaExternaCargaRepository {
        List<PersonaExternaCargaDTO> guardadas=new ArrayList<>();
        Set<String> existentes=new HashSet<>();
        public Set<Integer> planes() {return new HashSet<>(Arrays.asList(3));}
        public Set<Integer> fuentes() {return new HashSet<>(Arrays.asList(1));}
        public Set<Integer> convocatorias() {return new HashSet<>(Arrays.asList(3));}
        public Set<String> relacionesPlanConvocatoria() {return new HashSet<>(Arrays.asList("3:3"));}
        public Set<String> usuariosExistentes(Collection<String> nombres) {return existentes;}
        public void insertar(PersonaExternaCargaDTO fila) {guardadas.add(fila);}
        public void insertarLote(List<PersonaExternaCargaDTO> filas) {guardadas.addAll(filas);}
    }
    private PersonaExternaCargaServiceImpl service(Repo repo) throws Exception {
        PersonaExternaCargaServiceImpl servicio=new PersonaExternaCargaServiceImpl();
        Field f=PersonaExternaCargaServiceImpl.class.getDeclaredField("repository");f.setAccessible(true);f.set(servicio,repo);
        return servicio;
    }
    @Test public void importaLoteValido() throws Exception {
        Repo repo=new Repo();ResultadoCargaExternaDTO r=service(repo).importar(Arrays.asList(fila(2,"ES001"),fila(3,"ES002")));
        assertTrue(r.getErrores().toString(),r.getErrores().isEmpty());assertEquals(2,r.getInsertados());assertEquals(2,repo.guardadas.size());
    }
    @Test public void csvDe15000RegistrosSeLeeYValidaCompleto() throws Exception {
        StringBuilder csv=new StringBuilder(String.join(",",PersonaExternaCargaDTO.COLUMNAS)).append('\n');
        for(int n=0;n<15000;n++) {
            PersonaExternaCargaDTO f=fila(n+2,"ES_LOTE_"+n);
            List<String> valores=new ArrayList<>();for(String col:PersonaExternaCargaDTO.COLUMNAS) valores.add(f.get(col));
            csv.append(String.join(",",valores)).append('\n');
        }
        List<PersonaExternaCargaDTO> filas=ArchivoPersonaExterna.leer("lote.csv",csv.toString().getBytes(StandardCharsets.UTF_8));
        assertEquals(15000,filas.size());assertEquals(15000,service(new Repo()).importar(filas).getInsertados());
        filas.add(fila(15002,"ES_EXTRA"));
        Repo repo=new Repo();assertFalse(service(repo).importar(filas).getErrores().isEmpty());assertTrue(repo.guardadas.isEmpty());
    }
    @Test public void duplicadosEnArchivoNoInsertanNiLaPrimeraFila() throws Exception {
        Repo repo=new Repo();ResultadoCargaExternaDTO r=service(repo).importar(Arrays.asList(fila(2,"ES001"),fila(3," es001 ")));
        assertFalse(r.getErrores().isEmpty());assertTrue(repo.guardadas.isEmpty());assertEquals(0,r.getInsertados());
    }
    @Test public void usuarioExistenteImpideTodoElLote() throws Exception {
        Repo repo=new Repo();repo.existentes.add("es001");
        ResultadoCargaExternaDTO r=service(repo).importar(Arrays.asList(fila(2,"ES001"),fila(3,"ES002")));
        assertTrue(repo.guardadas.isEmpty());assertTrue(r.getErrores().get(0).contains("ya existe"));
    }
    @Test public void referenciasFechaYCamposInvalidosNoSePersisten() throws Exception {
        Repo repo=new Repo();List<String> valores=new ArrayList<>();for(String c:PersonaExternaCargaDTO.COLUMNAS) valores.add(fila(2,"ES001").get(c));
        valores.set(5,"999");valores.set(7,"sin correo");valores.set(8,"2026-02-30");valores.set(9,"CURP");valores.set(12,"9");
        ResultadoCargaExternaDTO r=service(repo).importar(Arrays.asList(new PersonaExternaCargaDTO(2,valores)));
        assertTrue(r.getErrores().size()>=5);assertTrue(repo.guardadas.isEmpty());
    }
    @Test public void plantillaXlsxTieneEjemploCompatibleYCatalogos() throws Exception {
        List<Object[]> catalogos=Collections.singletonList(new Object[]{3,"Plan",3,"Convocatoria",1,"Fuente"});
        byte[] contenido=ArchivoPersonaExterna.plantilla(catalogos);
        List<PersonaExternaCargaDTO> filas=ArchivoPersonaExterna.leer("plantilla.xlsx",contenido);
        assertEquals(1,filas.size());assertEquals("Pérez",filas.get(0).get("apellidop"));
        assertTrue(service(new Repo()).importar(filas).getErrores().isEmpty());
        try(XSSFWorkbook libro=new XSSFWorkbook(new ByteArrayInputStream(contenido))) {assertEquals(3,libro.getNumberOfSheets());}
    }
    @Test public void csvUtf8BomSeparadorYComillasConservanValores() throws Exception {
        String header=String.join(";",PersonaExternaCargaDTO.COLUMNAS);
        List<String> valores=new ArrayList<>();for(String c:PersonaExternaCargaDTO.COLUMNAS) valores.add(fila(2,"000123").get(c));
        valores.set(1,"\" clave;especial \"");valores.set(2,"\"Ana, María\"");
        byte[] csv=("\uFEFF"+header+"\r\n"+String.join(";",valores)+"\r\n").getBytes(StandardCharsets.UTF_8);
        PersonaExternaCargaDTO row=ArchivoPersonaExterna.leer("datos.csv",csv).get(0);
        assertEquals("000123",row.get("nombre_usuario"));assertEquals(" clave;especial ",row.get("password"));assertEquals("Ana, María",row.get("nombre"));
    }
    @Test public void xlsConservaUsuarioComoTexto() throws Exception {
        byte[] contenido;
        try(HSSFWorkbook libro=new HSSFWorkbook();ByteArrayOutputStream salida=new ByteArrayOutputStream()) {
            Sheet hoja=libro.createSheet();Row cab=hoja.createRow(0),datos=hoja.createRow(1);
            for(int c=0;c<14;c++) {cab.createCell(c).setCellValue(PersonaExternaCargaDTO.COLUMNAS.get(c));datos.createCell(c).setCellValue(fila(2,"000123").get(PersonaExternaCargaDTO.COLUMNAS.get(c)));}
            libro.write(salida);contenido=salida.toByteArray();
        }
        assertEquals("000123",ArchivoPersonaExterna.leer("datos.xls",contenido).get(0).get("nombre_usuario"));
    }
    @Test(expected=IllegalArgumentException.class) public void rechazaEncabezadosIncorrectos() throws Exception {
        ArchivoPersonaExterna.leer("datos.csv","usuario,password\na,b".getBytes(StandardCharsets.UTF_8));
    }
    @Test(expected=IllegalArgumentException.class) public void rechazaUtf8Invalido() throws Exception {
        ArchivoPersonaExterna.leer("datos.csv",new byte[]{(byte)0xff});
    }
    @Test(expected=IllegalArgumentException.class) public void rechazaFormulasExcel() throws Exception {
        try(HSSFWorkbook libro=new HSSFWorkbook();ByteArrayOutputStream salida=new ByteArrayOutputStream()) {
            Sheet hoja=libro.createSheet();Row cab=hoja.createRow(0);
            for(int c=0;c<14;c++) cab.createCell(c).setCellValue(PersonaExternaCargaDTO.COLUMNAS.get(c));
            hoja.createRow(1).createCell(0).setCellFormula("1+1");libro.write(salida);
            ArchivoPersonaExterna.leer("datos.xls",salida.toByteArray());
        }
    }
}
