/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;
/**
 *
 * @author annelkaren
 */
public class EnrolMents {
    
    private int roleid;
    private int userid;
    private int courseid;
    private int timestart;
    private int timeend;
    private int suspend;
    private String exception;
    private String errorcode;
    private String message;
    /**
     * @return the roleid
     */
    public int getRoleid() {
        return roleid;
    }
    /**
     * @param roleid the roleid to set
     */
    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }
    /**
     * @return the userid
     */
    public int getUserid() {
        return userid;
    }
    /**
     * @param userid the userid to set
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }
    /**
     * @return the courseid
     */
    public int getCourseid() {
        return courseid;
    }
    /**
     * @param courseid the courseid to set
     */
    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }
    /**
     * @return the timestamp
     */
    public int getTimestart() {
        return timestart;
    }
    /**
     * 
     * @param timestart 
     */
    public void setTimestart(int timestart) {
        this.timestart = timestart;
    }
    /**
     * @return the timeend
     */
    public int getTimeend() {
        return timeend;
    }
    /**
     * @param timeend the timeend to set
     */
    public void setTimeend(int timeend) {
        this.timeend = timeend;
    }
    /**
     * @return the suspend
     */
    public int getSuspend() {
        return suspend;
    }
    /**
     * @param suspend the suspend to set
     */
    public void setSuspend(int suspend) {
        this.suspend = suspend;
    }
    /**
     * @return the exception
     */
    public String getException() {
        return exception;
    }
    /**
     * @param exception the exception to set
     */
    public void setException(String exception) {
        this.exception = exception;
    }
    /**
     * @return the errorcode
     */
    public String getErrorcode() {
        return errorcode;
    }
    /**
     * @param errorcode the errorcode to set
     */
    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}