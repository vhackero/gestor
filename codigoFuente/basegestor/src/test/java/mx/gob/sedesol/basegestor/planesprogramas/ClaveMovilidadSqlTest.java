package mx.gob.sedesol.basegestor.planesprogramas;

import static org.junit.Assert.*;
import java.sql.*;
import org.junit.Assume;
import org.junit.Test;
import org.springframework.data.jpa.repository.Query;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.FichaDescProgramaRepo;

public class ClaveMovilidadSqlTest {
    private Connection conexion() throws Exception {
        String url = System.getenv("GESTOR_TEST_JDBC_URL"), usuario = System.getenv("GESTOR_TEST_JDBC_USER");
        Assume.assumeNotNull(url, usuario);
        Class.forName("com.mysql.jdbc.Driver");
        String password = System.getenv("GESTOR_TEST_JDBC_PASSWORD");
        return DriverManager.getConnection(url, usuario, password == null ? "" : password);
    }

    @Test public void nullPorDefectoYClaveCompartidaSoloConIgualNombre() throws Exception {
        try (Connection conexion = conexion(); Statement sql = conexion.createStatement()) {
            sql.execute("CREATE TEMPORARY TABLE movilidad_test LIKE tbl_ficha_descriptiva_programa");
            sql.execute("INSERT INTO movilidad_test (id_programa,nombre_tentativo,id_estatus_programa,fecha_vigencia_inicial,fecha_vigencia_final) VALUES (1,' Asignatura A ',1,NOW(),NOW()),(2,'asignatura a',1,NOW(),NOW())");
            try (ResultSet fila = sql.executeQuery("SELECT COUNT(*) FROM movilidad_test WHERE clave_movilidad IS NULL")) {
                assertTrue(fila.next()); assertEquals(2, fila.getInt(1));
            }
            sql.execute("UPDATE movilidad_test SET clave_movilidad='AAB'");
            String consulta = FichaDescProgramaRepo.class.getMethod("buscarConflictoClaveMovilidad", String.class, Integer.class, String.class)
                    .getAnnotation(Query.class).value().replace("tbl_ficha_descriptiva_programa", "movilidad_test")
                    .replace(":clave", "'aab'").replace(":idExcluido", "2");
            try (ResultSet fila = sql.executeQuery(consulta.replace(":nombre", "'Asignatura A'"))) { assertFalse(fila.next()); }
            try (ResultSet fila = sql.executeQuery(consulta.replace(":nombre", "'Asignatura B'"))) {
                assertTrue(fila.next()); assertEquals(1, fila.getInt(1)); assertEquals(" Asignatura A ", fila.getString(2));
            }
            sql.execute("UPDATE movilidad_test SET clave_movilidad=NULL WHERE id_programa=2");
        }
    }

    @Test public void bloqueoSeMantieneHastaFinalizarLaTransaccion() throws Exception {
        try (Connection primera = conexion(); Connection segunda = conexion();
             Statement a = primera.createStatement(); Statement b = segunda.createStatement()) {
            primera.setAutoCommit(false); segunda.setAutoCommit(false);
            b.execute("SET SESSION innodb_lock_wait_timeout=1");
            String bloqueo = FichaDescProgramaRepo.class.getMethod("bloquearClavesMovilidad").getAnnotation(Query.class).value();
            try (ResultSet fila = a.executeQuery(bloqueo)) { assertTrue(fila.next()); }
            try { b.executeQuery(bloqueo).close(); fail("El segundo guardado debe esperar"); }
            catch (SQLException esperado) { assertEquals(1205, esperado.getErrorCode()); }
            primera.rollback(); segunda.rollback();
            try (ResultSet fila = b.executeQuery(bloqueo)) { assertTrue(fila.next()); }
            segunda.rollback();
        }
    }
}
