package mx.gob.sedesol.basegestor.gestionescolar;

import static org.junit.Assert.*;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Assume;
import org.junit.Test;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.DispersionesRepository;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionPendienteDTO;

/** Ejecuta las consultas reales sobre tablas TEMPORARY que no alteran datos persistentes. */
public class DispersionPendientesSqlTest {
    private Connection conexion() throws Exception {
        String url = System.getenv("GESTOR_TEST_JDBC_URL");
        String user = System.getenv("GESTOR_TEST_JDBC_USER");
        Assume.assumeNotNull(url, user);
        Class.forName("com.mysql.jdbc.Driver");
        String password = System.getenv("GESTOR_TEST_JDBC_PASSWORD");
        return DriverManager.getConnection(url, user, password == null ? "" : password);
    }

    private void preparar(Connection c) throws Exception {
        String[] tablas = {
            "tbl_inscripciones (id INT, Idpersona BIGINT, idprograma INT, idplan INT, asignatura VARCHAR(255), fecha_registro DATETIME)",
            "tbl_persona (id_persona BIGINT, sso_idUsuario VARCHAR(100), sso_nombre VARCHAR(100), sso_apellidoPaterno VARCHAR(100), sso_apellidoMaterno VARCHAR(100))",
            "tbl_ficha_descriptiva_programa (id_programa INT, id_plan INT, nombre_tentativo VARCHAR(255), id_programa_antecedente INT, calificacion_min_aprobatoria VARCHAR(3), cve_programa VARCHAR(20), id_eje_capacitacion INT)",
            "tbl_procesos_inscripcion (proceso_inscripcion_id INT, convocatoria_id INT, id_tipo_proceso INT, nombre VARCHAR(100), fecha_inicio DATETIME, fecha_fin DATETIME, estatus INT, id_categoria_proceso INT)",
            "rel_proceso_inscipcion_planesyprogramas (id_proceso_inscripcion INT, id_plan INT, id_programa INT)",
            "tbl_planes (id_plan INT, nombre VARCHAR(100))",
            "tbl_malla_curricular (id INT, id_padre INT, nombre VARCHAR(100))",
            "tbl_convocatoria (convocatoria_id INT, nombre VARCHAR(100))",
            "tbl_dispersiones (id_dispersion INT, id_programa INT, id_proceso_inscripcion INT)",
            "rel_dispersiones_grupo (id_dispersion INT, id_grupo INT, id_evento INT, id_persona BIGINT)",
            "tbl_eventos (id_evento INT, id_programa INT, calificacion_min_aprobatoria VARCHAR(3))",
            "tbl_grupos (id INT, id_evento INT)",
            "rel_grupo_participante (id_persona_participante BIGINT, id_grupo INT, fecha_registro DATETIME, calificacion_final DECIMAL(10,2))",
            "rel_persona_bajas (id_persona BIGINT, id_inscripcion INT, id_proceso_inscripcion INT, contabilizar INT, id_plan INT, id_programa INT)",
            "rel_persona_correo (id_persona BIGINT, id_tipo_correo INT, activo INT, sso_correoElectronico VARCHAR(200))"
        };
        try (Statement s = c.createStatement()) {
            for (String tabla : tablas) s.execute("CREATE TEMPORARY TABLE " + tabla);
            s.execute("INSERT INTO tbl_ficha_descriptiva_programa VALUES (114,3,'Interaprendizaje',900,'60','CIAV',24),(119,4,' interaprendizaje ',NULL,'60','AIAV',24),(900,3,'Antecedente',NULL,'60','ANT',24),(901,4,' antecedente ',NULL,'60','ANT',24),(120,4,'Interaprendizaje extra',NULL,'60','EXT',24)");
            s.execute("INSERT INTO tbl_procesos_inscripcion VALUES (17,3,2,'Proceso prueba','2026-10-12','2026-10-12 23:59:59',1,1),(16,3,2,'Origen','2025-01-01','2025-12-31',1,1)");
            s.execute("INSERT INTO rel_proceso_inscipcion_planesyprogramas VALUES (17,3,114),(16,3,114)");
            s.execute("INSERT INTO tbl_planes VALUES (3,'Plan A')");
            s.execute("INSERT INTO tbl_malla_curricular VALUES (24,20,'Bloque 2'),(20,NULL,'Semestre 2')");
            s.execute("INSERT INTO tbl_convocatoria VALUES (3,'Convocatoria')");
            // Existe ya una dispersion de destino: los pendientes deben seguir visibles.
            s.execute("INSERT INTO tbl_dispersiones VALUES (297,114,16),(324,114,17)");
            s.execute("INSERT INTO rel_dispersiones_grupo VALUES (297,1,1,2),(324,3,1,2)");
            s.execute("INSERT INTO tbl_eventos VALUES (1,119,'60'),(2,901,''),(3,120,'60')");
            s.execute("INSERT INTO tbl_grupos VALUES (1,1),(2,2),(3,1),(4,3)");
            for (int n = 1; n <= 15; n++) {
                s.execute("INSERT INTO tbl_persona VALUES ("+n+",'000"+n+"','Alumno','Prueba',NULL)");
                s.execute("INSERT INTO tbl_inscripciones VALUES ("+n+","+n+",114,3,'Interaprendizaje','2026-10-12 12:00:00')");
                s.execute("INSERT INTO rel_grupo_participante VALUES ("+n+",2,'2025-06-01',"+(n==4 ? "50" : n==8 ? "NULL" : "80")+")");
            }
            s.execute("INSERT INTO tbl_inscripciones VALUES (99,9,114,3,'Interaprendizaje','2026-10-12 13:00:00')");
            // Bajas parcial/temporal activas; desactivada; historica sin id; baja de otro proceso/programa.
            s.execute("INSERT INTO rel_persona_bajas VALUES (2,2,17,1,3,114),(3,3,17,1,3,114),(7,7,17,0,3,114),(13,NULL,17,1,3,114),(14,14,16,1,3,114),(15,NULL,17,1,3,999)");
            // Matricula antes de la reinscripcion; intento historico; grupo vinculado al proceso; nombre diferente.
            s.execute("INSERT INTO rel_grupo_participante VALUES (6,1,'2026-10-12 08:00:00',NULL),(10,1,'2025-07-01',50),(11,3,'2025-07-01',NULL),(12,4,'2026-10-12 08:00:00',NULL)");
        }
    }

    private DispersionesRepository repositorio(Connection c) {
        DispersionesRepository repo = new DispersionesRepository();
        repo.entityManager = (EntityManager) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{EntityManager.class},
            (proxy, method, args) -> {
                if (method.getName().equals("flush")) return null;
                if (!method.getName().equals("createNativeQuery")) throw new UnsupportedOperationException(method.getName());
                String sql = (String) args[0];
                Map<String,Object> parametros = new HashMap<>();
                return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Query.class}, (q, m, a) -> {
                    if (m.getName().equals("setParameter")) { parametros.put((String)a[0], a[1]); return q; }
                    String sentencia = sql;
                    for (Map.Entry<String,Object> p : parametros.entrySet()) {
                        String valor = p.getValue() instanceof Collection ? p.getValue().toString().replace("[", "").replace("]", "") : p.getValue().toString();
                        sentencia = sentencia.replace(":"+p.getKey(), valor);
                    }
                    try (Statement s = c.createStatement()) {
                        if (m.getName().equals("executeUpdate")) return s.executeUpdate(sentencia);
                        try (ResultSet r = s.executeQuery(sentencia)) {
                            List<Object> filas = new ArrayList<>();
                            int columnas = r.getMetaData().getColumnCount();
                            Set<String> alias = new HashSet<>();
                            for (int i=1; i<=columnas; i++) {
                                String etiqueta = r.getMetaData().getColumnLabel(i).toLowerCase(Locale.ROOT);
                                assertTrue("Alias SQL duplicado: " + etiqueta, alias.add(etiqueta));
                            }
                            while (r.next()) {
                                Object[] fila = new Object[columnas];
                                for (int i=0; i<columnas; i++) fila[i]=r.getObject(i+1);
                                filas.add(columnas==1 ? fila[0] : fila);
                            }
                            return m.getName().equals("getSingleResult") ? filas.get(0) : filas;
                        }
                    }
                });
            });
        return repo;
    }

    @Test public void conteoExportacionYSeleccionCoincidenConBajasSeriacionYMatriculas() throws Exception {
        try (Connection c = conexion()) {
            preparar(c);
            DispersionesRepository repo = repositorio(c);
            List<Long> esperados = Arrays.asList(1L,5L,7L,9L,10L,12L,14L,15L);
            assertEquals(esperados, repo.obtenerPersonasMatriculaExistente(17,114));
            assertEquals(Integer.valueOf(8), repo.consultarDispersionesExistentes(16,17).get(0).getNoEstudiantesInscritos());
            List<DispersionPendienteDTO> excel = repo.consultarPendientesDispersionExistente(297,17,114);
            List<Long> exportados = new ArrayList<>();
            for (DispersionPendienteDTO p : excel) exportados.add(p.getIdPersona());
            Collections.sort(exportados);
            assertEquals(esperados, exportados);
            assertEquals("0001", excel.get(0).getMatricula());
            assertEquals(1, repo.consultarProcesosSinDispersion().size());
            assertEquals(Integer.valueOf(324), repo.crearDispersionBasica(17,114,8,0,2L));
            repo.guardarRelacionDispersionGrupo(324,1,3,2L);
            try (Statement s=c.createStatement(); ResultSet r=s.executeQuery("SELECT COUNT(*) FROM rel_dispersiones_grupo WHERE id_dispersion=324 AND id_grupo=3")) {
                assertTrue(r.next()); assertEquals(1,r.getInt(1));
            }
            try (Statement s=c.createStatement()) {
                for (Long persona : esperados) s.execute("INSERT INTO rel_grupo_participante VALUES ("+persona+",3,'2026-10-12 14:00:00',NULL)");
            }
            assertEquals(8, repo.contarParticipantesRegistrados(324,esperados));
            assertTrue(repo.obtenerPersonasMatriculaExistente(17,114).isEmpty());
            assertTrue(repo.consultarDispersionesExistentes(16,17).isEmpty());
            assertTrue(repo.consultarPendientesDispersionExistente(297,17,114).isEmpty());
        }
    }
}
