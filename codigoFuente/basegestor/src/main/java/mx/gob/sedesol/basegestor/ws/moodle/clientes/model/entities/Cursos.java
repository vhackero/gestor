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
public class Cursos {
    private int total;
    private List<Curso> courses;
     private List<Alertas> warnings  ;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Curso> getCourses() {
        return courses;
    }

    public void setCourses(List<Curso> courses) {
        this.courses = courses;
    }

    public List<Alertas> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Alertas> warnings) {
        this.warnings = warnings;
    }

    @Override
    public String toString() {
        return "Cursos{" + "total=" + total + ", courses=" + courses + ", warnings=" + warnings + '}';
    }

    
}
