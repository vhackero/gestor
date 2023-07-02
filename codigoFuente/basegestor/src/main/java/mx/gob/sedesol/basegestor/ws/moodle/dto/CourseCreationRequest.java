package mx.gob.sedesol.basegestor.ws.moodle.dto;

import java.util.List;

public class CourseCreationRequest {

	private String wsToken;
	private String wsFunction;
	private String moodleWsRestFormat;
	private List<Course> courses;
	public String getWsToken() {
		return wsToken;
	}
	public void setWsToken(String wsToken) {
		this.wsToken = wsToken;
	}
	public String getWsFunction() {
		return wsFunction;
	}
	public void setWsFunction(String wsFunction) {
		this.wsFunction = wsFunction;
	}
	public String getMoodleWsRestFormat() {
		return moodleWsRestFormat;
	}
	public void setMoodleWsRestFormat(String moodleWsRestFormat) {
		this.moodleWsRestFormat = moodleWsRestFormat;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}
