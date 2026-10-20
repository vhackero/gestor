package mx.gob.sedesol.basegestor.service.impl.analisisdatos;

import java.lang.reflect.Method;
import java.util.*;
import org.junit.Test;
import org.junit.Assume;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.sql.*;
import javax.sql.DataSource;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.*;

public class ReporteadorLecturaTest {
    @Test public void permiteSelectCteComentariosYLiterales() {
        for(String sql:Arrays.asList("SELECT 1", "-- ejemplo\nWITH datos AS (SELECT 1 n) SELECT * FROM datos;",
                "WITH RECURSIVE n AS (SELECT 1 n UNION ALL SELECT n+1 FROM n WHERE n<3) SELECT * FROM n",
                "SELECT 'delete; :falso --' AS texto, :periodo", "SELECT 1 /* update */", "SELECT 1 # delete\n"))
            ReporteadorSqlLectura.validar(sql);
    }
    @Test public void rechazaEscrituraArchivosBloqueosYMultiplesSentencias() {
        for(String sql:Arrays.asList("UPDATE t SET x=1", "WITH t AS (SELECT 1) DELETE FROM x",
                "SELECT 1; DELETE FROM t", "SELECT 1 INTO OUTFILE '/tmp/x'", "SELECT @x:=1",
                "SELECT * FROM t FOR UPDATE", "SELECT LOAD_FILE('/x')", "SELECT `sleep`(1)",
                "SELECT 1 /*! INTO OUTFILE '/x' */", "SELECT 1 /*M! INTO OUTFILE '/x' */",
                "CALL proc()", "SELECT 'sin cerrar", "SELECT 1 /* sin cerrar")) {
            try { ReporteadorSqlLectura.validar(sql); fail(sql); } catch(IllegalArgumentException esperado) { }
        }
    }
    @Test public void parametrosEnLiteralesYComentariosNoSeEnlazan() throws Exception {
        ReporteadorServiceImpl servicio=new ReporteadorServiceImpl();
        Method preparar=servicio.getClass().getDeclaredMethod("prepararSql",String.class);preparar.setAccessible(true);
        ReporteadorSqlPreparadoDTO sql=(ReporteadorSqlPreparadoDTO)preparar.invoke(servicio,
                "WITH t AS (SELECT :periodo p) SELECT ':ignorado' texto, :periodo FROM t -- :otro");
        assertEquals(Arrays.asList("periodo","periodo"),sql.getParametros());
        assertTrue(sql.getSql().contains("':ignorado'")); assertTrue(sql.getSql().contains("-- :otro"));
    }
    @Test public void inputNoConsultaOpcionesYSePersisteSinSql() throws Exception {
        ReporteadorServiceImpl servicio=new ReporteadorServiceImpl();
        DataSource ds=mock(DataSource.class);Connection c=mock(Connection.class);PreparedStatement st=mock(PreparedStatement.class);
        when(ds.getConnection()).thenReturn(c);when(c.prepareStatement(anyString())).thenReturn(st);servicio.setDataSource(ds);
        ReporteadorParametroDTO p=new ReporteadorParametroDTO();p.setIdParametro(2L);p.setIdReporte(1L);p.setClave(":periodociclo");p.setEtiqueta("Periodo");p.setTipoEntrada("INPUT");
        assertTrue(servicio.obtenerOpcionesParametro(p).isEmpty());verifyZeroInteractions(ds);
        servicio.guardarParametro(p,7L);
        verify(st).setString(4,"");verify(st).setString(5,"INPUT");verify(st).setLong(8,7L);verify(st).setLong(9,2L);
        assertFalse(new ReporteadorParametroDTO().isEntradaTexto());
    }
    @Test public void ejecutaCteConInputEnMariaDbSinInterpolarValor() throws Exception {
        String url=System.getenv("GESTOR_TEST_JDBC_URL");Assume.assumeNotNull(url);
        com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds=new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
        ds.setURL(url);ds.setUser("root");ReporteadorServiceImpl servicio=new ReporteadorServiceImpl();servicio.setDataSource(ds);
        ReporteadorReporteDTO reporte=new ReporteadorReporteDTO();reporte.setConsultaSql("WITH datos AS (SELECT :periodociclo AS valor) SELECT valor, ':ignorado' literal FROM datos WHERE valor=:periodociclo");
        ReporteadorParametroDTO p=new ReporteadorParametroDTO();p.setClave(":periodociclo");p.setTipoEntrada("INPUT");reporte.setParametros(Arrays.asList(p));
        String valor="2024-1'; DELETE FROM nada; --";
        ReporteadorResultadoDTO resultado=servicio.ejecutarReporte(reporte,Collections.singletonMap("periodociclo",valor));
        assertEquals(1,resultado.getFilas().size());assertEquals(valor,resultado.getFilas().get(0).get(0));
    }
    @Test public void consultasSolicitadasSeValidanYPreparanConCte() throws Exception {
        java.nio.file.Path archivo=java.nio.file.Paths.get("documentacion/sql/reportes_solictiados_SIGIE.sql");
        assertTrue("Ejecutar desde la raíz del repositorio",java.nio.file.Files.exists(archivo));
        String original=new String(java.nio.file.Files.readAllBytes(archivo),java.nio.charset.StandardCharsets.UTF_8);
        String codigo=ReporteadorSqlLectura.codigo(original);int inicio=0,consultas=0;
        Method preparar=ReporteadorServiceImpl.class.getDeclaredMethod("prepararSql",String.class);preparar.setAccessible(true);
        ReporteadorServiceImpl servicio=new ReporteadorServiceImpl();
        for(int i=0;i<codigo.length();i++) if(codigo.charAt(i)==';') {
            String sql=original.substring(inicio,i+1);inicio=i+1;ReporteadorSqlLectura.validar(sql);
            ReporteadorSqlPreparadoDTO preparado=(ReporteadorSqlPreparadoDTO)preparar.invoke(servicio,sql);
            assertTrue(preparado.getParametros().contains("periodociclo"));consultas++;
        }
        assertTrue(consultas>=5);
    }
}
