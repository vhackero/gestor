/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template f    ile, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

/**
 *
 * @author annelkaren
 */
public class Grupo {
    
    public Grupo(){}
    
    private int id;
    private String name;
    private String description;
    private int descriptionformat;
    private String enrolmentkey;
    private String idnumber;
    private int courseid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDescriptionformat() {
        return descriptionformat;
    }

    public void setDescriptionformat(int descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    public String getEnrolmentkey() {
        return enrolmentkey;
    }

    public void setEnrolmentkey(String enrolmentkey) {
        this.enrolmentkey = enrolmentkey;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

   

    @Override
    public String toString() {
        return "Grupo{" + "id=" + id + ", \n name=" + name + ", \n description=" + description + ",\n  descriptionformat=" + descriptionformat + ", \n enrolmentkey=" + enrolmentkey + ", \n idnumber=" + idnumber + ", \n courseid=" + courseid + "}  \n";
    }


}