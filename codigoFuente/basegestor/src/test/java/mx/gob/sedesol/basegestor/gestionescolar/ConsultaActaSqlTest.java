package mx.gob.sedesol.basegestor.gestionescolar;

import static org.junit.Assert.*;
import java.sql.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.Assume;
import org.springframework.data.jpa.repository.Query;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IActaRepository;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.EventoCapacitacionRepo;

/** Integración opcional: sólo usa tablas temporales de la conexión, sin modificar datos persistentes. */
public class ConsultaActaSqlTest {
	@Test public void rolesMultiplesNoCompensanAlumnosPendientesYPeriodoEsExacto() throws Exception {
		String url = System.getenv("ACTAS_TEST_JDBC_URL");
		String usuario = System.getenv("ACTAS_TEST_JDBC_USER");
		Assume.assumeNotNull(url, usuario);
		Class.forName("com.mysql.jdbc.Driver");
		String password = System.getenv("ACTAS_TEST_JDBC_PASSWORD");
		try (Connection conexion = DriverManager.getConnection(url, usuario, password == null ? "" : password);
			 Statement sql = conexion.createStatement()) {
			sql.execute("CREATE TEMPORARY TABLE tbl_grupos (id INT, id_evento INT, nombre VARCHAR(40), acta_cerrada TINYINT) ENGINE=InnoDB");
			sql.execute("CREATE TEMPORARY TABLE rel_grupo_participante (id INT, id_grupo INT, id_persona_participante INT, calificacion_final DECIMAL(5,2)) ENGINE=InnoDB");
			sql.execute("CREATE TEMPORARY TABLE rel_persona_roles (id_persona_rol INT, id_persona INT, id_rol INT) ENGINE=InnoDB");
			sql.execute("CREATE TEMPORARY TABLE tbl_eventos (id_evento INT, id_programa INT, nombre_ec VARCHAR(40), cve_evento_cap VARCHAR(80)) ENGINE=InnoDB");
			sql.execute("CREATE TEMPORARY TABLE tbl_ficha_descriptiva_programa (id_programa INT, nombre_tentativo VARCHAR(40), id_plan INT, id_eje_capacitacion INT) ENGINE=InnoDB");
			sql.execute("CREATE TEMPORARY TABLE tbl_planes (id_plan INT, nombre VARCHAR(40)) ENGINE=InnoDB");
			sql.execute("CREATE TEMPORARY TABLE tbl_actas (id_acta INT, id_grupo INT, fecha_cierre DATETIME, usuario_modifico BIGINT, acta LONGBLOB) ENGINE=InnoDB");
			sql.execute("INSERT INTO tbl_actas VALUES (20,1,NOW(),100,CONCAT('%PDF-1.4 ',REPEAT('X',12582912),' %%EOF')), (21,3,NOW(),100,'%PDF-1.4 texto %%EOF')");
			sql.execute("INSERT INTO tbl_grupos VALUES (1,10,'Alumnos',1),(2,11,'Docente',0),(3,12,'Sin estado',NULL)");
			sql.execute("INSERT INTO rel_grupo_participante VALUES (1,1,100,NULL),(2,1,200,9),(3,2,300,10),(4,1,400,8),(5,1,500,10)");
			sql.execute("INSERT INTO rel_persona_roles VALUES (1,100,2),(2,200,2),(3,200,3),(4,200,2),(5,300,3),(6,400,2),(7,400,2),(8,500,2)");
			sql.execute("INSERT INTO tbl_eventos VALUES (10,1000,'Periodo 1','ASIG-S1-B1-2026-1'),(11,1000,'Periodo 10','ASIG-2026-10'),(12,1000,'Sin plan','OTRA-2026-1')");
			sql.execute("INSERT INTO tbl_ficha_descriptiva_programa VALUES (1000,'Asignatura',NULL,NULL)");
			String estado = IActaRepository.class.getMethod("consultarEstadoGrupo", Integer.class).getAnnotation(Query.class).value();
			try (ResultSet fila = sql.executeQuery(temporales(sql, estado.replace(":idGrupo", "1")))) {
				assertTrue(fila.next()); assertEquals(2, fila.getInt(3)); assertEquals(1, fila.getInt(4));
			}
			try (ResultSet fila = sql.executeQuery(temporales(sql, estado.replace(":idGrupo", "2")))) {
				assertTrue(fila.next()); assertEquals(0, fila.getInt(3)); assertEquals(0, fila.getInt(4));
			}
			try (ResultSet fila = sql.executeQuery(temporales(sql, estado.replace(":idGrupo", "3")))) {
				assertTrue(fila.next()); assertNull(fila.getObject(2));
			}
			String ids = IActaRepository.class.getMethod("consultarIdsAlumnos", Integer.class).getAnnotation(Query.class).value();
			java.util.Set<Integer> alumnos = new java.util.HashSet<Integer>();
			try (ResultSet fila = sql.executeQuery(temporales(sql, ids.replace(":idGrupo", "1")))) {
				while (fila.next()) { alumnos.add(fila.getInt(1)); }
			}
			assertEquals(new java.util.HashSet<Integer>(java.util.Arrays.asList(100, 500)), alumnos);
			String consulta = EventoCapacitacionRepo.class.getMethod("consultarActasPorFiltros", String.class, String.class, List.class, String.class).getAnnotation(Query.class).value();
			consulta = consulta.replace(":anioPeriodo", "'2026'").replace(":numeroElementos", "'1'").replace(":idprogramaSelecionado", "1000");
			String consultaConTexto = consulta;
			consulta = consulta.replace(":textoEvento", "''");
			int registros = 0;
			try (ResultSet fila = sql.executeQuery(temporales(sql, consulta))) {
				while (fila.next()) {
					assertNotEquals(2, fila.getInt(9));
					assertEquals(16, fila.getMetaData().getColumnCount());
					if (fila.getInt(9) == 1) { assertEquals(2, fila.getInt(13)); assertEquals(1, fila.getInt(14)); assertEquals(1, fila.getInt(15)); assertEquals(1, fila.getInt(16)); }
					for (int columna = 1; columna <= 16; columna++) { assertFalse(fila.getObject(columna) instanceof byte[]); }
					registros++;
				}
			}
			String[] textos = { "S1", "s1", "B1", "Sin plan", "no-coincide", "%" };
			int[] esperados = { 1, 1, 1, 1, 0, 0 };
			for (int indice = 0; indice < textos.length; indice++) {
				int coincidencias = 0;
				try (ResultSet fila = sql.executeQuery(temporales(sql, consultaConTexto.replace(":textoEvento", "'" + textos[indice] + "'")))) {
					while (fila.next()) { coincidencias++; }
				}
				assertEquals("Filtro por clave o nombre: " + textos[indice], esperados[indice], coincidencias);
			}
			String metadatos = IActaRepository.class.getMethod("consultarMetadatos", Integer.class).getAnnotation(Query.class).value();
			try (ResultSet fila = sql.executeQuery(metadatos.replace(":idGrupo", "1"))) {
				assertTrue(fila.next()); assertTrue(fila.getLong(5) > 10485760L); assertEquals(0, fila.getInt(6));
				for (int columna = 1; columna <= 6; columna++) { assertFalse(fila.getObject(columna) instanceof byte[]); }
			}
			assertEquals("Incluye grupos sin plan y excluye el periodo 10", 2, registros);
		}
	}

	/** MariaDB no permite reabrir una tabla temporal en una consulta; cada referencia usa una copia aislada. */
	private String temporales(Statement sql, String consulta) throws SQLException {
		Matcher matcher = Pattern.compile("\\b(tbl_actas|rel_grupo_participante|rel_persona_roles)\\b").matcher(consulta);
		Map<String, Integer> referencias = new HashMap<String, Integer>();
		StringBuffer resultado = new StringBuffer();
		while (matcher.find()) {
			String original = matcher.group();
			int numero = referencias.containsKey(original) ? referencias.get(original) + 1 : 1;
			referencias.put(original, numero);
			String copia = "actas_test_" + original + numero;
			sql.execute("DROP TEMPORARY TABLE IF EXISTS " + copia);
			sql.execute("CREATE TEMPORARY TABLE " + copia + " LIKE " + original);
			sql.execute("INSERT INTO " + copia + " SELECT * FROM " + original);
			matcher.appendReplacement(resultado, copia);
		}
		matcher.appendTail(resultado);
		return resultado.toString();
	}
}
