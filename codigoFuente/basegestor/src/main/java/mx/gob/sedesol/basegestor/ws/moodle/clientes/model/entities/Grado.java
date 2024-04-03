/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

/**
 *
 * @author USUARIO
 */
public class Grado {

    private int userid;   //Student ID
    private double grade;   //Student grade
    private int locked;   //0 means not locked, > 1 is a date to lock until
    private int hidden;   //0 means not hidden, 1 hidden, > 1 is a date to hide until
    private int overridden;   //0 means not overridden, > 1 means overridden
    private String feedback;   //Feedback from the grader
    private int feedbackformat;   //The format of the feedback
    private int usermodified;   //The ID of the last user to modify this student grade
    private int datesubmitted;   //A timestamp indicating when the student submitted the activity
    private int dategraded;   //A timestamp indicating when the assignment was grades
    private String str_grade;   //A string representation of the grade
    private String str_long_grade;   //A nicely formatted string representation of the grade
    private String str_feedback;   //A formatted string representation of the feedback from the grader

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
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

    public int getOverridden() {
        return overridden;
    }

    public void setOverridden(int overridden) {
        this.overridden = overridden;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getFeedbackformat() {
        return feedbackformat;
    }

    public void setFeedbackformat(int feedbackformat) {
        this.feedbackformat = feedbackformat;
    }

    public int getUsermodified() {
        return usermodified;
    }

    public void setUsermodified(int usermodified) {
        this.usermodified = usermodified;
    }

    public int getDatesubmitted() {
        return datesubmitted;
    }

    public void setDatesubmitted(int datesubmitted) {
        this.datesubmitted = datesubmitted;
    }

    public int getDategraded() {
        return dategraded;
    }

    public void setDategraded(int dategraded) {
        this.dategraded = dategraded;
    }

    public String getStr_grade() {
        return str_grade;
    }

    public void setStr_grade(String str_grade) {
        this.str_grade = str_grade;
    }

    public String getStr_long_grade() {
        return str_long_grade;
    }

    public void setStr_long_grade(String str_long_grade) {
        this.str_long_grade = str_long_grade;
    }

    public String getStr_feedback() {
        return str_feedback;
    }

    public void setStr_feedback(String str_feedback) {
        this.str_feedback = str_feedback;
    }

}
