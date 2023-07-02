package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

public class DiscusionForo {
	
	private int discussionid;
	private List<Alertas> warnings;
	/**
	 * @return the discussionid
	 */
	public int getDiscussionid() {
		return discussionid;
	}
	/**
	 * @param discussionid the discussionid to set
	 */
	public void setDiscussionid(int discussionid) {
		this.discussionid = discussionid;
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
