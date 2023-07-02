/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;
import java.util.List;
/**
 *
 * @author annelkaren
 */
public class Seccion {
    
    private int id;
    private int section;
    private int course;
    private String name;
    private String summary;
    private int summaryformat;
    private String availability;
    private int visible;
    private String sequence;
    private String usedefaultname;
    private List<Modulo> modules;
    private Actividades activities;
    
    
    
    
    /**
	 * @return the section
	 */
	public int getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(int section) {
		this.section = section;
	}
	public Actividades getActivities() {
		return activities;
	}
	public void setActivities(Actividades activities) {
		this.activities = activities;
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
     * @return the course
     */
    public int getCourse() {
        return course;
    }
    /**
     * @param course the course to set
     */
    public void setCourse(int course) {
        this.course = course;
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
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }
    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
    /**
     * @return the summaryformat
     */
    public int getSummaryformat() {
        return summaryformat;
    }
    /**
     * @param summaryformat the summaryformat to set
     */
    public void setSummaryformat(int summaryformat) {
        this.summaryformat = summaryformat;
    }
    /**
     * @return the availability
     */
    public String getAvailability() {
        return availability;
    }
    /**
     * @param availability the availability to set
     */
    public void setAvailability(String availability) {
        this.availability = availability;
    }
    /**
     * @return the visible
     */
    public int getVisible() {
        return visible;
    }
    /**
     * @param visible the visible to set
     */
    public void setVisible(int visible) {
        this.visible = visible;
    }
    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }
    /**
     * @param sequence the sequence to set
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    /**
     * @return the usedefaultname
     */
    public String getUsedefaultname() {
        return usedefaultname;
    }
    /**
     * @param usedefaultname the usedefaultname to set
     */
    public void setUsedefaultname(String usedefaultname) {
        this.usedefaultname = usedefaultname;
    }
    /**
     * @return the modules
     */
    public List<Modulo> getModules() {
        return modules;
    }
    /**
     * @param modules the modules to set
     */
    public void setModules(List<Modulo> modules) {
        this.modules = modules;
    }
   
    
}