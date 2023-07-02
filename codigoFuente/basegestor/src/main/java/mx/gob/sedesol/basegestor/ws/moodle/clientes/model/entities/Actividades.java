package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

public class Actividades {
	private List<Datos> forums;
	private List<Datos> quizs;
	private List<Datos> lessons;
	private List<Datos> assignments;
	private List<Datos> chats;
	private List<Datos> choices;
	private List<Datos> datas;
	private List<Datos> tools;
	private List<Datos> glossarys;
	private List<Datos> scorms;
	private List<Datos> surveys;
	private List<Datos> wikis;

	@Override
	public String toString() {
		return "Actividades [forums=" + forums + ", quizs=" + quizs + ", lessons=" + lessons + ", assignments="
				+ assignments + ", chats=" + chats + ", choices=" + choices + ", datas=" + datas + ", tools=" + tools
				+ ", glossarys=" + glossarys + ", scorms=" + scorms + ", surveys=" + surveys + ", wikis=" + wikis
				+ ", workshopsM=" + workshopsM + "]";
	}

	public List<Datos> getForums() {
		return forums;
	}

	public void setForums(List<Datos> forums) {
		this.forums = forums;
	}

	public List<Datos> getQuizs() {
		return quizs;
	}

	public void setQuizs(List<Datos> quizs) {
		this.quizs = quizs;
	}

	public List<Datos> getLessons() {
		return lessons;
	}

	public void setLessons(List<Datos> lessons) {
		this.lessons = lessons;
	}

	public List<Datos> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Datos> assignments) {
		this.assignments = assignments;
	}

	public List<Datos> getChats() {
		return chats;
	}

	public void setChats(List<Datos> chats) {
		this.chats = chats;
	}

	public List<Datos> getChoices() {
		return choices;
	}

	public void setChoices(List<Datos> choices) {
		this.choices = choices;
	}

	public List<Datos> getDatas() {
		return datas;
	}

	public void setDatas(List<Datos> datas) {
		this.datas = datas;
	}

	public List<Datos> getTools() {
		return tools;
	}

	public void setTools(List<Datos> tools) {
		this.tools = tools;
	}

	public List<Datos> getGlossarys() {
		return glossarys;
	}

	public void setGlossarys(List<Datos> glossarys) {
		this.glossarys = glossarys;
	}

	public List<Datos> getScorms() {
		return scorms;
	}

	public void setScorms(List<Datos> scorms) {
		this.scorms = scorms;
	}

	public List<Datos> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Datos> surveys) {
		this.surveys = surveys;
	}

	public List<Datos> getWikis() {
		return wikis;
	}

	public void setWikis(List<Datos> wikis) {
		this.wikis = wikis;
	}

	public List<Datos> getWorkshopsM() {
		return workshopsM;
	}

	public void setWorkshopsM(List<Datos> workshopsM) {
		this.workshopsM = workshopsM;
	}

	private List<Datos> workshopsM;
}
