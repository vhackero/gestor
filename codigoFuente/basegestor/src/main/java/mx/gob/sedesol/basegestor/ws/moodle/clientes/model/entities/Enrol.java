package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

public class Enrol {
	
	private int roleId;
	private int userId;
	private int courseId;
	private long timestart;
	private long timeend;
	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the userId de mooodle
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set de moodle
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the courseId de moodle
	 */
	public int getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set de moodle
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the timestart
	 */
	public long getTimestart() {
		return timestart;
	}
	/**
	 * @param timestart the timestart to set  OPTIO[NAL
	 */
	public void setTimestart(long timestart) {
		this.timestart = timestart;
	}
	/**
	 * @return the timeend
	 */
	public long getTimeend() {
		return timeend;
	}
	/**
	 * @param timeend the timeend to set OPTIONAL
	 */
	public void setTimeend(long timeend) {
		this.timeend = timeend;
	}

}
