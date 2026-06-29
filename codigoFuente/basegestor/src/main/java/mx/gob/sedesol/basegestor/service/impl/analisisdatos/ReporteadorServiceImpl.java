package mx.gob.sedesol.basegestor.service.impl.analisisdatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorOpcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorParametroDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorReporteDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.ReporteadorSqlPreparadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.analisisdatos.ReporteadorService;

@Service("reporteadorService")
public class ReporteadorServiceImpl implements ReporteadorService {

	private static final Pattern PARAMETRO = Pattern.compile(":([A-Za-z][A-Za-z0-9_]*)");
	private static final Pattern COMENTARIO_BLOQUE = Pattern.compile("(?s)/\\*.*?\\*/");
	private static final Pattern COMENTARIO_LINEA = Pattern.compile("(?m)--.*?$");
	private static final Pattern SENTENCIAS_NO_PERMITIDAS = Pattern.compile(
			"(?i)\\b(insert|update|delete|drop|alter|truncate|create|replace|merge|call|grant|revoke|commit|rollback)\\b");

	@Autowired
	@Qualifier("elearnigDS")
	private DataSource dataSource;

	@Override
	public List<ReporteadorReporteDTO> obtenerReportesActivos() {
		String sql = "SELECT id_reporte, clave, nombre, consulta_sql, activo FROM tbl_reporteador_reportes "
				+ "WHERE activo = 1 ORDER BY nombre";
		return obtenerReportes(sql, true);
	}

	@Override
	public List<ReporteadorReporteDTO> obtenerReportes() {
		String sql = "SELECT id_reporte, clave, nombre, consulta_sql, activo FROM tbl_reporteador_reportes "
				+ "ORDER BY nombre";
		return obtenerReportes(sql, false);
	}

	private List<ReporteadorReporteDTO> obtenerReportes(String sql, boolean soloParametrosActivos) {
		List<ReporteadorReporteDTO> reportes = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				ReporteadorReporteDTO reporte = construirReporte(rs);
				if (soloParametrosActivos) {
					reporte.setParametros(obtenerParametrosPorReporte(reporte.getIdReporte()));
				} else {
					reporte.setParametros(obtenerParametrosPorReporteAdmin(reporte.getIdReporte()));
				}
				reportes.add(reporte);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible consultar los reportes configurados.", e);
		}
		return reportes;
	}

	@Override
	public List<ReporteadorParametroDTO> obtenerParametrosPorReporte(Long idReporte) {
		String sql = "SELECT id_parametro, id_reporte, clave, etiqueta, consulta_sql, orden, activo "
				+ "FROM tbl_reporteador_parametros WHERE id_reporte = ? AND activo = 1 ORDER BY orden, etiqueta";
		return obtenerParametrosPorReporte(idReporte, sql);
	}

	@Override
	public List<ReporteadorParametroDTO> obtenerParametrosPorReporteAdmin(Long idReporte) {
		String sql = "SELECT id_parametro, id_reporte, clave, etiqueta, consulta_sql, orden, activo "
				+ "FROM tbl_reporteador_parametros WHERE id_reporte = ? ORDER BY orden, etiqueta";
		return obtenerParametrosPorReporte(idReporte, sql);
	}

	private List<ReporteadorParametroDTO> obtenerParametrosPorReporte(Long idReporte, String sql) {
		List<ReporteadorParametroDTO> parametros = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, idReporte);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					parametros.add(construirParametro(rs));
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible consultar los parametros del reporte.", e);
		}
		return parametros;
	}

	@Override
	public List<ReporteadorOpcionDTO> obtenerOpcionesParametro(ReporteadorParametroDTO parametro) {
		validarConsultaSelect(parametro.getConsultaSql());
		List<ReporteadorOpcionDTO> opciones = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(normalizarSql(parametro.getConsultaSql()));
				ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				opciones.add(new ReporteadorOpcionDTO(rs.getString("valor"), rs.getString("nombre")));
			}
		} catch (SQLException e) {
			throw new IllegalStateException("La consulta del parametro " + parametro.getClave()
					+ " debe regresar las columnas valor y nombre.", e);
		}
		return opciones;
	}

	@Override
	public ReporteadorResultadoDTO ejecutarReporte(ReporteadorReporteDTO reporte, Map<String, String> valoresParametros) {
		validarConsultaSelect(reporte.getConsultaSql());
		validarParametrosRegistrados(reporte);
		ReporteadorSqlPreparadoDTO sqlPreparado = prepararSql(reporte.getConsultaSql());
		validarValoresParametros(sqlPreparado, valoresParametros);

		ReporteadorResultadoDTO resultado = new ReporteadorResultadoDTO();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlPreparado.getSql())) {
			for (int i = 0; i < sqlPreparado.getParametros().size(); i++) {
				statement.setString(i + 1, valoresParametros.get(sqlPreparado.getParametros().get(i)));
			}
			try (ResultSet rs = statement.executeQuery()) {
				ResultSetMetaData metaData = rs.getMetaData();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					resultado.getColumnas().add(metaData.getColumnLabel(i));
				}
				while (rs.next()) {
					List<Object> fila = new ArrayList<>();
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						fila.add(rs.getObject(i));
					}
					resultado.getFilas().add(fila);
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible generar el reporte.", e);
		}
		return resultado;
	}

	@Override
	public ReporteadorReporteDTO guardarReporte(ReporteadorReporteDTO reporte, Long usuarioModifico) {
		validarReporte(reporte);
		if (ObjectUtils.isNull(reporte.getIdReporte())) {
			insertarReporte(reporte, usuarioModifico);
		} else {
			actualizarReporte(reporte, usuarioModifico);
		}
		return reporte;
	}

	private void insertarReporte(ReporteadorReporteDTO reporte, Long usuarioModifico) {
		String sql = "INSERT INTO tbl_reporteador_reportes "
				+ "(clave, nombre, consulta_sql, activo, usuario_modifico, fecha_registro) VALUES (?, ?, ?, ?, ?, NOW())";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			setReporteStatement(statement, reporte, usuarioModifico);
			statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					reporte.setIdReporte(generatedKeys.getLong(1));
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible guardar el reporte. Verifique que la clave no exista.", e);
		}
	}

	private void actualizarReporte(ReporteadorReporteDTO reporte, Long usuarioModifico) {
		String sql = "UPDATE tbl_reporteador_reportes SET clave = ?, nombre = ?, consulta_sql = ?, activo = ?, "
				+ "usuario_modifico = ?, fecha_actualizacion = NOW() WHERE id_reporte = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			setReporteStatement(statement, reporte, usuarioModifico);
			statement.setLong(6, reporte.getIdReporte());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible actualizar el reporte. Verifique que la clave no exista.", e);
		}
	}

	private void setReporteStatement(PreparedStatement statement, ReporteadorReporteDTO reporte, Long usuarioModifico)
			throws SQLException {
		statement.setString(1, reporte.getClave().trim());
		statement.setString(2, reporte.getNombre().trim());
		statement.setString(3, normalizarSql(reporte.getConsultaSql()));
		statement.setInt(4, Boolean.FALSE.equals(reporte.getActivo()) ? 0 : 1);
		if (usuarioModifico == null) {
			statement.setNull(5, java.sql.Types.BIGINT);
		} else {
			statement.setLong(5, usuarioModifico);
		}
	}

	@Override
	public void eliminarReporte(Long idReporte) {
		if (ObjectUtils.isNull(idReporte)) {
			return;
		}
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			try (PreparedStatement parametros = connection
					.prepareStatement("DELETE FROM tbl_reporteador_parametros WHERE id_reporte = ?");
					PreparedStatement reporte = connection
							.prepareStatement("DELETE FROM tbl_reporteador_reportes WHERE id_reporte = ?")) {
				parametros.setLong(1, idReporte);
				parametros.executeUpdate();
				reporte.setLong(1, idReporte);
				reporte.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible eliminar el reporte.", e);
		}
	}

	@Override
	public ReporteadorParametroDTO guardarParametro(ReporteadorParametroDTO parametro, Long usuarioModifico) {
		validarParametro(parametro);
		if (ObjectUtils.isNull(parametro.getIdParametro())) {
			insertarParametro(parametro, usuarioModifico);
		} else {
			actualizarParametro(parametro, usuarioModifico);
		}
		return parametro;
	}

	private void insertarParametro(ReporteadorParametroDTO parametro, Long usuarioModifico) {
		String sql = "INSERT INTO tbl_reporteador_parametros "
				+ "(id_reporte, clave, etiqueta, consulta_sql, orden, activo, usuario_modifico, fecha_registro) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			setParametroStatement(statement, parametro, usuarioModifico);
			statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					parametro.setIdParametro(generatedKeys.getLong(1));
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible guardar el parametro. Verifique que la clave no exista.", e);
		}
	}

	private void actualizarParametro(ReporteadorParametroDTO parametro, Long usuarioModifico) {
		String sql = "UPDATE tbl_reporteador_parametros SET id_reporte = ?, clave = ?, etiqueta = ?, consulta_sql = ?, "
				+ "orden = ?, activo = ?, usuario_modifico = ?, fecha_actualizacion = NOW() WHERE id_parametro = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			setParametroStatement(statement, parametro, usuarioModifico);
			statement.setLong(8, parametro.getIdParametro());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible actualizar el parametro. Verifique que la clave no exista.", e);
		}
	}

	private void setParametroStatement(PreparedStatement statement, ReporteadorParametroDTO parametro, Long usuarioModifico)
			throws SQLException {
		statement.setLong(1, parametro.getIdReporte());
		statement.setString(2, formatearClaveParametro(parametro.getClave()));
		statement.setString(3, parametro.getEtiqueta().trim());
		statement.setString(4, normalizarSql(parametro.getConsultaSql()));
		statement.setInt(5, parametro.getOrden() != null ? parametro.getOrden() : 1);
		statement.setInt(6, Boolean.FALSE.equals(parametro.getActivo()) ? 0 : 1);
		if (usuarioModifico == null) {
			statement.setNull(7, java.sql.Types.BIGINT);
		} else {
			statement.setLong(7, usuarioModifico);
		}
	}

	@Override
	public void eliminarParametro(Long idParametro) {
		if (ObjectUtils.isNull(idParametro)) {
			return;
		}
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("DELETE FROM tbl_reporteador_parametros WHERE id_parametro = ?")) {
			statement.setLong(1, idParametro);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible eliminar el parametro.", e);
		}
	}

	private ReporteadorReporteDTO construirReporte(ResultSet rs) throws SQLException {
		ReporteadorReporteDTO reporte = new ReporteadorReporteDTO();
		reporte.setIdReporte(rs.getLong("id_reporte"));
		reporte.setClave(rs.getString("clave"));
		reporte.setNombre(rs.getString("nombre"));
		reporte.setConsultaSql(rs.getString("consulta_sql"));
		reporte.setActivo(rs.getInt("activo") == 1);
		return reporte;
	}

	private ReporteadorParametroDTO construirParametro(ResultSet rs) throws SQLException {
		ReporteadorParametroDTO parametro = new ReporteadorParametroDTO();
		parametro.setIdParametro(rs.getLong("id_parametro"));
		parametro.setIdReporte(rs.getLong("id_reporte"));
		parametro.setClave(rs.getString("clave"));
		parametro.setEtiqueta(rs.getString("etiqueta"));
		parametro.setConsultaSql(rs.getString("consulta_sql"));
		parametro.setOrden(rs.getInt("orden"));
		parametro.setActivo(rs.getInt("activo") == 1);
		return parametro;
	}

	private void validarReporte(ReporteadorReporteDTO reporte) {
		if (reporte == null || esCadenaVacia(reporte.getClave()) || esCadenaVacia(reporte.getNombre())
				|| esCadenaVacia(reporte.getConsultaSql())) {
			throw new IllegalArgumentException("Clave, nombre y consulta SQL son obligatorios.");
		}
		validarConsultaSelect(reporte.getConsultaSql());
	}

	private void validarParametro(ReporteadorParametroDTO parametro) {
		if (parametro == null || ObjectUtils.isNull(parametro.getIdReporte()) || esCadenaVacia(parametro.getClave())
				|| esCadenaVacia(parametro.getEtiqueta()) || esCadenaVacia(parametro.getConsultaSql())) {
			throw new IllegalArgumentException("Reporte, clave, etiqueta y consulta SQL son obligatorios.");
		}
		validarConsultaSelect(parametro.getConsultaSql());
	}

	private void validarParametrosRegistrados(ReporteadorReporteDTO reporte) {
		Set<String> parametrosConsulta = obtenerParametrosSql(reporte.getConsultaSql());
		Set<String> parametrosRegistrados = new HashSet<>();
		for (ReporteadorParametroDTO parametro : reporte.getParametros()) {
			parametrosRegistrados.add(normalizarClaveParametro(parametro.getClave()));
		}
		for (String parametroConsulta : parametrosConsulta) {
			if (!parametrosRegistrados.contains(parametroConsulta)) {
				throw new IllegalArgumentException("Falta capturar el parámetro :" + parametroConsulta
						+ " para poder ser utilizado");
			}
		}
	}

	private void validarValoresParametros(ReporteadorSqlPreparadoDTO sqlPreparado, Map<String, String> valoresParametros) {
		for (String parametro : sqlPreparado.getParametros()) {
			String valor = valoresParametros.get(parametro);
			if (ObjectUtils.isNullOrEmpty(valor)) {
				throw new IllegalArgumentException("Falta capturar el parámetro :" + parametro
						+ " para poder ser utilizado");
			}
		}
	}

	private ReporteadorSqlPreparadoDTO prepararSql(String sql) {
		ReporteadorSqlPreparadoDTO preparado = new ReporteadorSqlPreparadoDTO();
		Matcher matcher = PARAMETRO.matcher(normalizarSql(sql));
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			preparado.getParametros().add(matcher.group(1));
			matcher.appendReplacement(buffer, "?");
		}
		matcher.appendTail(buffer);
		preparado.setSql(buffer.toString());
		return preparado;
	}

	private Set<String> obtenerParametrosSql(String sql) {
		Set<String> parametros = new HashSet<>();
		Matcher matcher = PARAMETRO.matcher(sql);
		while (matcher.find()) {
			parametros.add(matcher.group(1));
		}
		return parametros;
	}

	private String normalizarClaveParametro(String clave) {
		if (clave == null) {
			return null;
		}
		String claveNormalizada = clave.trim();
		return claveNormalizada.startsWith(":") ? claveNormalizada.substring(1) : claveNormalizada;
	}

	private String formatearClaveParametro(String clave) {
		String claveNormalizada = normalizarClaveParametro(clave);
		return ":" + claveNormalizada;
	}

	private String normalizarSql(String sql) {
		String sqlNormalizado = sql != null ? sql.trim() : "";
		while (sqlNormalizado.endsWith(";")) {
			sqlNormalizado = sqlNormalizado.substring(0, sqlNormalizado.length() - 1).trim();
		}
		return sqlNormalizado;
	}

	private void validarConsultaSelect(String sql) {
		String sqlNormalizado = normalizarSql(sql);
		String sqlSinComentarios = COMENTARIO_LINEA.matcher(COMENTARIO_BLOQUE.matcher(sqlNormalizado).replaceAll(" "))
				.replaceAll(" ").trim();
		if (!sqlSinComentarios.toLowerCase().startsWith("select")) {
			throw new IllegalArgumentException("El reporteador solo permite consultas SELECT.");
		}
		if (sqlSinComentarios.contains(";") || SENTENCIAS_NO_PERMITIDAS.matcher(sqlSinComentarios).find()) {
			throw new IllegalArgumentException("El reporteador solo permite consultas SELECT.");
		}
	}

	private boolean esCadenaVacia(String valor) {
		return valor == null || valor.trim().isEmpty();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
