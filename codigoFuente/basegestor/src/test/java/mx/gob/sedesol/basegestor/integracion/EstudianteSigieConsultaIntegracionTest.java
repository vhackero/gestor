package mx.gob.sedesol.basegestor.integracion;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import mx.gob.sedesol.basegestor.model.repositories.integracion.EstudianteSigieRepositoryImpl;

/**
 * Regresion optativa contra una copia local de datos. Solo SELECT sobre tablas persistentes;
 * la prueba de respaldo crea una tabla temporal de SIGE exclusiva de su conexion.
 * Propiedades: sigie.test.jdbc.url, sigie.test.jdbc.user, sigie.test.jdbc.password
 * y, opcionalmente, sigie.test.matricula para reproducir un caso reportado.
 */
public class EstudianteSigieConsultaIntegracionTest {
    @Test
    public void conservaTodasLasColumnasYRespondeDentroDelLimite() throws Exception {
        String url = System.getProperty("sigie.test.jdbc.url");
        assumeTrue("Requiere base local de pruebas", url != null && !url.isEmpty());
        String anterior = cargarReferencia();
        String optimizada = obtenerConsultaActual();
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conexion = DriverManager.getConnection(url,
                System.getProperty("sigie.test.jdbc.user", ""),
                System.getProperty("sigie.test.jdbc.password", ""))) {
            conexion.setReadOnly(true);
            conexion.setAutoCommit(false);
            Map<String, String> casos = new LinkedHashMap<>();
            String reportada = System.getProperty("sigie.test.matricula");
            if (reportada != null) casos.put("caso_reportado", reportada);
            agregarCaso(conexion, casos, "baja_temporal",
                    "EXISTS (SELECT 1 FROM rel_persona_bajas b JOIN rel_motivo_baja m "
                    + "ON m.id_motivo_baja=b.motivo_baja_id WHERE b.id_persona=p.id_persona "
                    + "AND b.contabilizar=1 AND m.tipo_baja_id IN (2,7,8))");
            agregarCaso(conexion, casos, "baja_definitiva",
                    "EXISTS (SELECT 1 FROM rel_persona_bajas b JOIN rel_motivo_baja m "
                    + "ON m.id_motivo_baja=b.motivo_baja_id WHERE b.id_persona=p.id_persona "
                    + "AND b.contabilizar=1 AND m.tipo_baja_id IN (3,6))");
            agregarCaso(conexion, casos, "con_aprobacion",
                    "EXISTS (SELECT 1 FROM rel_grupo_participante rg JOIN tbl_grupos g ON g.id=rg.id_grupo "
                    + "JOIN tbl_eventos e ON e.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa f "
                    + "ON f.id_programa=e.id_programa WHERE rg.id_persona_participante=p.id_persona "
                    + "AND rg.calificacion_final>=CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2)))");
            agregarCaso(conexion, casos, "con_intento_reprobado",
                    "EXISTS (SELECT 1 FROM rel_grupo_participante rg JOIN tbl_grupos g ON g.id=rg.id_grupo "
                    + "JOIN tbl_eventos e ON e.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa f "
                    + "ON f.id_programa=e.id_programa WHERE rg.id_persona_participante=p.id_persona "
                    + "AND rg.calificacion_final<CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2)))");
            agregarCaso(conexion, casos, "sin_calificaciones",
                    "NOT EXISTS (SELECT 1 FROM rel_grupo_participante rg "
                    + "WHERE rg.id_persona_participante=p.id_persona AND rg.calificacion_final IS NOT NULL)");
            casos.put("inexistente", "__SIGIE_NO_EXISTE__");
            for (Map.Entry<String, String> caso : casos.entrySet()) {
                long inicio = System.nanoTime();
                List<List<String>> esperado = ejecutar(conexion, anterior, caso.getValue(), 20);
                double msAnterior = (System.nanoTime() - inicio) / 1000000d;
                inicio = System.nanoTime();
                // El mismo limite efectivo de 5 segundos que utiliza el servicio por defecto.
                List<List<String>> obtenido = ejecutar(conexion, optimizada, caso.getValue(), 5);
                double msOptimizada = (System.nanoTime() - inicio) / 1000000d;
                assertTrue("Diferencia en " + caso.getKey(), esperado.equals(obtenido));
                if ("inexistente".equals(caso.getKey())) assertTrue(obtenido.isEmpty());
                else assertFalse("Falta caso " + caso.getKey(), obtenido.isEmpty());
                System.out.printf(Locale.ROOT, "%s: mismas %d filas, original=%.1f ms, optimizada=%.1f ms%n",
                        caso.getKey(), obtenido.size(), msAnterior, msOptimizada);
            }
            conexion.rollback();
        }
    }

    @Test
    public void usaUsuarioYPlanCuandoNoExisteRegistroSige() throws Exception {
        String url = System.getProperty("sigie.test.jdbc.url");
        assumeTrue("Requiere base local de pruebas", url != null && !url.isEmpty());
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection cn = DriverManager.getConnection(url,
                System.getProperty("sigie.test.jdbc.user", ""),
                System.getProperty("sigie.test.jdbc.password", ""))) {
            String usuario;
            String nombrePlan;
            try (Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT p.sso_idUsuario, pl.nombre FROM tbl_persona p "
                    + "JOIN tbl_persona_aspirante a ON a.id_persona=p.id_persona "
                    + "AND a.id_persona_aspirante=(SELECT MAX(a2.id_persona_aspirante) "
                    + "FROM tbl_persona_aspirante a2 WHERE a2.id_persona=p.id_persona) "
                    + "JOIN tbl_planes pl ON pl.id_plan=a.id_plan "
                    + "WHERE p.sso_idUsuario IS NOT NULL AND pl.nombre IS NOT NULL LIMIT 1")) {
                assertTrue("Falta estudiante con plan para la prueba", rs.next());
                usuario = rs.getString(1);
                nombrePlan = rs.getString(2);
            }
            String sql = obtenerConsultaActual();
            List<List<String>> original = ejecutar(cn, sql, usuario, 5);
            assertEquals(1, original.size());
            // Sombrea SIGE solo en esta conexion; no modifica la tabla persistente.
            try (Statement st = cn.createStatement()) {
                st.execute("CREATE TEMPORARY TABLE tbl_persona_sige ("
                        + "matricula_sige VARCHAR(255), programa_educativo_sige VARCHAR(255))");
            }
            List<List<String>> sinSige = ejecutar(cn, sql, usuario, 5);
            assertEquals(1, sinSige.size());
            assertEquals(usuario, sinSige.get(0).get(0));
            assertEquals(nombrePlan, sinSige.get(0).get(6));
            for (int i = 0; i < 15; i++) {
                if (i != 0 && i != 6) {
                    assertEquals("Cambio inesperado en columna " + i,
                            original.get(0).get(i), sinSige.get(0).get(i));
                }
            }
            try (PreparedStatement st = cn.prepareStatement(
                    "INSERT INTO tbl_persona_sige VALUES (?, ?)")) {
                st.setString(1, usuario);
                st.setString(2, "PROGRAMA DE PRUEBA SIGE");
                st.executeUpdate();
            }
            List<List<String>> conSige = ejecutar(cn, sql, usuario, 5);
            assertEquals(1, conSige.size());
            assertEquals(usuario, conSige.get(0).get(0));
            assertEquals("PROGRAMA DE PRUEBA SIGE", conSige.get(0).get(6));
            assertTrue(ejecutar(cn, sql, "__SIGIE_NO_EXISTE__", 5).isEmpty());
            // La tabla temporal desaparece al cerrar la conexion.
        }
    }

    private void agregarCaso(Connection cn, Map<String, String> casos, String etiqueta, String condicion) throws Exception {
        String sql = "SELECT p.sso_idUsuario FROM tbl_persona p "
                + "JOIN tbl_persona_sige ps ON ps.matricula_sige=p.sso_idUsuario "
                + "JOIN tbl_persona_aspirante a ON a.id_persona=p.id_persona "
                + "AND a.id_persona_aspirante=(SELECT MAX(a2.id_persona_aspirante) FROM tbl_persona_aspirante a2 WHERE a2.id_persona=p.id_persona) "
                + "JOIN tbl_planes pl ON pl.id_plan=a.id_plan WHERE " + condicion + " LIMIT 1";
        try (Statement st = cn.createStatement()) {
            st.setQueryTimeout(20);
            try (ResultSet rs = st.executeQuery(sql)) {
                assertTrue("No hay datos para " + etiqueta, rs.next());
                casos.put(etiqueta, rs.getString(1));
            }
        }
    }

    private List<List<String>> ejecutar(Connection cn, String sql, String matricula, int timeout) throws Exception {
        int parametros = sql.split(":matricula", -1).length - 1;
        try (PreparedStatement st = cn.prepareStatement(sql.replace(":matricula", "?"))) {
            for (int i = 1; i <= parametros; i++) st.setString(i, matricula);
            st.setQueryTimeout(timeout);
            List<List<String>> filas = new ArrayList<>();
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    List<String> fila = new ArrayList<>();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) fila.add(rs.getString(i));
                    filas.add(fila);
                }
            }
            return filas;
        }
    }

    private String obtenerConsultaActual() throws Exception {
        EntityManager em = mock(EntityManager.class);
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());
        EstudianteSigieRepositoryImpl repo = new EstudianteSigieRepositoryImpl();
        Field campo = EstudianteSigieRepositoryImpl.class.getDeclaredField("entityManager");
        campo.setAccessible(true);
        campo.set(repo, em);
        assertFalse(repo.consultarPorMatricula("PRUEBA").isPresent());
        ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
        verify(em).createNativeQuery(sql.capture());
        verify(query).setParameter("matricula", "PRUEBA");
        return sql.getValue();
    }

    private String cargarReferencia() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/sigie/consulta_estudiante_antes_optimizacion.sql")) {
            assertNotNull("Falta consulta anterior de referencia", in);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] bloque = new byte[4096];
            int n;
            while ((n = in.read(bloque)) != -1) bytes.write(bloque, 0, n);
            return new String(bytes.toByteArray(), StandardCharsets.UTF_8).trim();
        }
    }
}
