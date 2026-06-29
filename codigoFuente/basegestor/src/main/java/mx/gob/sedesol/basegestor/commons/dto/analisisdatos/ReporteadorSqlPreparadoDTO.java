package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReporteadorSqlPreparadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sql;
	private List<String> parametros = new ArrayList<>();

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<String> getParametros() {
		return parametros;
	}

	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}
}
