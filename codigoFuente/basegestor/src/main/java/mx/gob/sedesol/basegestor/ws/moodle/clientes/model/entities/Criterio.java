package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

public class Criterio {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Criterio [key=" + key + ", value=" + value + "\n]";
	}

}
