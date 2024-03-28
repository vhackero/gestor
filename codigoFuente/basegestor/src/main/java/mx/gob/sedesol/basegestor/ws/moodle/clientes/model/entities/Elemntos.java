/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

/**
 *
 * @author USUARIO
 */
public class Elemntos {

    private String activityid; //The ID of the activity or "course" for the course grade item
    private int itemnumber;   //Will be 0 unless the module has multiple grades
    private int scaleid;   //The ID of the custom scale or 0
    private String name;   //The module name
    private double grademin;   //Minimum grade
    private double grademax;   //Maximum grade
    private double gradepass;   //The passing grade threshold
    private int locked;   //0 means not locked, > 1 is a date to lock until
    private int hidden;   //0 means not hidden, > 1 is a date to hide until
    private List<Grado> grades;

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public int getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(int itemnumber) {
        this.itemnumber = itemnumber;
    }

    public int getScaleid() {
        return scaleid;
    }

    public void setScaleid(int scaleid) {
        this.scaleid = scaleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrademin() {
        return grademin;
    }

    public void setGrademin(double grademin) {
        this.grademin = grademin;
    }

    public double getGrademax() {
        return grademax;
    }

    public void setGrademax(double grademax) {
        this.grademax = grademax;
    }

    public double getGradepass() {
        return gradepass;
    }

    public void setGradepass(double gradepass) {
        this.gradepass = gradepass;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public List<Grado> getGrades() {
        return grades;
    }

    public void setGrades(List<Grado> grades) {
        this.grades = grades;
    }

	@Override
	public String toString() {
		return "Elemntos [activityid=" + activityid + ", itemnumber=" + itemnumber + ", scaleid=" + scaleid + ", name="
				+ name + ", grademin=" + grademin + ", grademax=" + grademax + ", gradepass=" + gradepass + ", locked="
				+ locked + ", hidden=" + hidden + ", grades=" + grades + "]";
	}
}
