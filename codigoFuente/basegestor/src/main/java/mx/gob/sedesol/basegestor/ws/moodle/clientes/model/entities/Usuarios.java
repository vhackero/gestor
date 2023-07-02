package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

public class Usuarios {
	private int total;
    private List<Usuario> users;
    private List<Alertas> warnings  ;
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the users
	 */
	public List<Usuario> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<Usuario> users) {
		this.users = users;
	}
	/**
	 * @return the warnings
	 */
	public List<Alertas> getWarnings() {
		return warnings;
	}
	/**
	 * @param warnings the warnings to set
	 */
	public void setWarnings(List<Alertas> warnings) {
		this.warnings = warnings;
	}
    
    

}
