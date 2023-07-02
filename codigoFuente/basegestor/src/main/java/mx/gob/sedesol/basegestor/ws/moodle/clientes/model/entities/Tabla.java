/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

/**
 *
 * @author robert o_O
 */
class Tabla {

    private int courseid;   //course id
    private int userid;   //user id
    private String userfullname;   //user fullname
    private int maxdepth;   //table max depth (needed for printing it)
   private List<Object> tabledata;

    public List<Object> getTabledata() {
        return tabledata;
    }

    public void setTabledata(List<Object> tabledata) {
        this.tabledata = tabledata;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public int getMaxdepth() {
        return maxdepth;
    }

    public void setMaxdepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }

//    public List<DatosTabla> getTabledata() {
//        return tabledata;
//    }
//
//    public void setTabledata(List<DatosTabla> tabledata) {
//        this.tabledata = tabledata;
//    }

    @Override
    public String toString() {
        return "Tabla{" + "courseid=" + courseid + ", userid=" + userid + ", userfullname=" + userfullname + ", maxdepth=" + maxdepth + ", tabledata=" + tabledata + '}';
    }
    
    
    
}
