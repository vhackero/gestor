package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

public class Foro {
	
	private int id;
	private int course;
	private String type;
	private String name;
	private String intro;
	private int numdiscussions;
	
	private int introformat;
	private int assessed;
	private int assesstimestart;
	private int assesstimefinish;
	private int scale;
	private int maxbytes;
	private int maxattachments;
	private int forcesubscribe;
	private int trackingtype;
	private int rsstype;
	private int rssarticles;
	private long timemodified;
	private int warnafter;
	private int blockafter;
	private int blockperiod;
	private int completiondiscussions;
	private int completionreplies;
	private int completionposts;
	private int cmid;
	//private int numdiscussions;
	private boolean cancreatediscussions;
	
	
	/**
	 * @return the introformat
	 */
	public int getIntroformat() {
		return introformat;
	}
	/**
	 * @param introformat the introformat to set
	 */
	public void setIntroformat(int introformat) {
		this.introformat = introformat;
	}
	/**
	 * @return the assessed
	 */
	public int getAssessed() {
		return assessed;
	}
	/**
	 * @param assessed the assessed to set
	 */
	public void setAssessed(int assessed) {
		this.assessed = assessed;
	}
	/**
	 * @return the assesstimestart
	 */
	public int getAssesstimestart() {
		return assesstimestart;
	}
	/**
	 * @param assesstimestart the assesstimestart to set
	 */
	public void setAssesstimestart(int assesstimestart) {
		this.assesstimestart = assesstimestart;
	}
	/**
	 * @return the assesstimefinish
	 */
	public int getAssesstimefinish() {
		return assesstimefinish;
	}
	/**
	 * @param assesstimefinish the assesstimefinish to set
	 */
	public void setAssesstimefinish(int assesstimefinish) {
		this.assesstimefinish = assesstimefinish;
	}
	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
	/**
	 * @return the maxbytes
	 */
	public int getMaxbytes() {
		return maxbytes;
	}
	/**
	 * @param maxbytes the maxbytes to set
	 */
	public void setMaxbytes(int maxbytes) {
		this.maxbytes = maxbytes;
	}
	/**
	 * @return the maxattachments
	 */
	public int getMaxattachments() {
		return maxattachments;
	}
	/**
	 * @param maxattachments the maxattachments to set
	 */
	public void setMaxattachments(int maxattachments) {
		this.maxattachments = maxattachments;
	}
	/**
	 * @return the forcesubscribe
	 */
	public int getForcesubscribe() {
		return forcesubscribe;
	}
	/**
	 * @param forcesubscribe the forcesubscribe to set
	 */
	public void setForcesubscribe(int forcesubscribe) {
		this.forcesubscribe = forcesubscribe;
	}
	/**
	 * @return the trackingtype
	 */
	public int getTrackingtype() {
		return trackingtype;
	}
	/**
	 * @param trackingtype the trackingtype to set
	 */
	public void setTrackingtype(int trackingtype) {
		this.trackingtype = trackingtype;
	}
	/**
	 * @return the rsstype
	 */
	public int getRsstype() {
		return rsstype;
	}
	/**
	 * @param rsstype the rsstype to set
	 */
	public void setRsstype(int rsstype) {
		this.rsstype = rsstype;
	}
	/**
	 * @return the rssarticles
	 */
	public int getRssarticles() {
		return rssarticles;
	}
	/**
	 * @param rssarticles the rssarticles to set
	 */
	public void setRssarticles(int rssarticles) {
		this.rssarticles = rssarticles;
	}
	/**
	 * @return the timemodified
	 */
	public long getTimemodified() {
		return timemodified;
	}
	/**
	 * @param timemodified the timemodified to set
	 */
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/**
	 * @return the warnafter
	 */
	public int getWarnafter() {
		return warnafter;
	}
	/**
	 * @param warnafter the warnafter to set
	 */
	public void setWarnafter(int warnafter) {
		this.warnafter = warnafter;
	}
	/**
	 * @return the blockafter
	 */
	public int getBlockafter() {
		return blockafter;
	}
	/**
	 * @param blockafter the blockafter to set
	 */
	public void setBlockafter(int blockafter) {
		this.blockafter = blockafter;
	}
	/**
	 * @return the blockperiod
	 */
	public int getBlockperiod() {
		return blockperiod;
	}
	/**
	 * @param blockperiod the blockperiod to set
	 */
	public void setBlockperiod(int blockperiod) {
		this.blockperiod = blockperiod;
	}
	/**
	 * @return the completiondiscussions
	 */
	public int getCompletiondiscussions() {
		return completiondiscussions;
	}
	/**
	 * @param completiondiscussions the completiondiscussions to set
	 */
	public void setCompletiondiscussions(int completiondiscussions) {
		this.completiondiscussions = completiondiscussions;
	}
	/**
	 * @return the completionreplies
	 */
	public int getCompletionreplies() {
		return completionreplies;
	}
	/**
	 * @param completionreplies the completionreplies to set
	 */
	public void setCompletionreplies(int completionreplies) {
		this.completionreplies = completionreplies;
	}
	/**
	 * @return the completionposts
	 */
	public int getCompletionposts() {
		return completionposts;
	}
	/**
	 * @param completionposts the completionposts to set
	 */
	public void setCompletionposts(int completionposts) {
		this.completionposts = completionposts;
	}
	/**
	 * @return the cmid
	 */
	public int getCmid() {
		return cmid;
	}
	/**
	 * @param cmid the cmid to set
	 */
	public void setCmid(int cmid) {
		this.cmid = cmid;
	}
	
	/**
	 * @return the cancreatediscussions
	 */
	public boolean isCancreatediscussions() {
		return cancreatediscussions;
	}
	/**
	 * @param cancreatediscussions the cancreatediscussions to set
	 */
	public void setCancreatediscussions(boolean cancreatediscussions) {
		this.cancreatediscussions = cancreatediscussions;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the courseid
	 */
	public int getCourse() {
		return course;
	}
	/**
	 * @param courseid the courseid to set
	 */
	public void setCourse(int course) {
		this.course = course;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the intro
	 */
	public String getIntro() {
		return intro;
	}
	/**
	 * @param intro the intro to set
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}
	/**
	 * @return the numdiscussions
	 */
	public int getNumdiscussions() {
		return numdiscussions;
	}
	/**
	 * @param numdiscussions the numdiscussions to set
	 */
	public void setNumdiscussions(int numdiscussions) {
		this.numdiscussions = numdiscussions;
	}
	
	public String toString(){
		return "courseid: "+this.course+"  id: "+this.id+" name: "+this.name;
	}
	
}
