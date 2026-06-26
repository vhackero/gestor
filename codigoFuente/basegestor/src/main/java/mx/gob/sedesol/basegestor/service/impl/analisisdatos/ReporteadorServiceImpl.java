package mx.gob.sedesol.basegestor.service.impl.analisisdatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
		String sql = "SELECT id_reporte, clave, nombre, consulta_sql FROM tbl_reporteador_reportes "
				+ "WHERE activo = 1 ORDER BY nombre";
		List<ReporteadorReporteDTO> reportes = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				ReporteadorReporteDTO reporte = new ReporteadorReporteDTO();
				reporte.setIdReporte(rs.getLong("id_reporte"));
				reporte.setClave(rs.getString("clave"));
				reporte.setNombre(rs.getString("nombre"));
				reporte.setConsultaSql(rs.getString("consulta_sql"));
				reporte.setParametros(obtenerParametrosPorReporte(reporte.getIdReporte()));
				reportes.add(reporte);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("No fue posible consultar los reportes configurados.", e);
		}
		return reportes;
	}

	@Override
	public List<ReporteadorParametroDTO> obtenerParametrosPorReporte(Long idReporte) {
		String sql = "SELECT id_parametro, id_reporte, clave, etiqueta, consulta_sql, orden "
				+ "FROM tbl_reporteador_parametros WHERE id_reporte = ? AND activo = 1 ORDER BY orden, etiqueta";
		List<ReporteadorParametroDTO> parametros = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, idReporte);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					ReporteadorParametroDTO parametro = new ReporteadorParametroDTO();
					parametro.setIdParametro(rs.getLong("id_parametro"));
					parametro.setIdReporte(rs.getLong("id_reporte"));
					parametro.setClave(rs.getString("clave"));
					parametro.setEtiqueta(rs.getString("etiqueta"));
					parametro.setConsultaSql(rs.getString("consulta_sql"));
					parametro.setOrden(rs.getInt("orden"));
					parametros.add(parametro);
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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
