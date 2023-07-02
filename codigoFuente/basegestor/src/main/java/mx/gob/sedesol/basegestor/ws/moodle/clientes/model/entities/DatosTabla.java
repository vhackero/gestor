/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

/**
 *
 * @author robert o_O
 */
class DatosTabla {

    private NombreElemento itemname;//Opcional //The item returned data
    private Lider leader;//Opcional //The item returned data
    private DatosGenericos weight;//Opcional //weight column
    private DatosGenericos grade;// Opcional //grade column
    private DatosGenericos range;//Opcional //range column
    private DatosGenericos percentage;//Opcional //percentage column
    private DatosGenericos lettergrade;//Opcional //lettergrade column
    private DatosGenericos rank;//Opcional //rank column
    private DatosGenericos average; // Opcional //average column
    private DatosGenericos feedback;// Opcional //feedback column

    public NombreElemento getItemname() {
        return itemname;
    }

    public void setItemname(NombreElemento itemname) {
        this.itemname = itemname;
    }

    public Lider getLeader() {
        return leader;
    }

    public void setLeader(Lider leader) {
        this.leader = leader;
    }

    public DatosGenericos getWeight() {
        return weight;
    }

    public void setWeight(DatosGenericos weight) {
        this.weight = weight;
    }

    public DatosGenericos getGrade() {
        return grade;
    }

    public void setGrade(DatosGenericos grade) {
        this.grade = grade;
    }

    public DatosGenericos getRange() {
        return range;
    }

    public void setRange(DatosGenericos range) {
        this.range = range;
    }

    public DatosGenericos getPercentage() {
        return percentage;
    }

    public void setPercentage(DatosGenericos percentage) {
        this.percentage = percentage;
    }

    public DatosGenericos getLettergrade() {
        return lettergrade;
    }

    public void setLettergrade(DatosGenericos lettergrade) {
        this.lettergrade = lettergrade;
    }

    public DatosGenericos getRank() {
        return rank;
    }

    public void setRank(DatosGenericos rank) {
        this.rank = rank;
    }

    public DatosGenericos getAverage() {
        return average;
    }

    public void setAverage(DatosGenericos average) {
        this.average = average;
    }

    public DatosGenericos getFeedback() {
        return feedback;
    }

    public void setFeedback(DatosGenericos feedback) {
        this.feedback = feedback;
    }

    public DatosGenericos getContributiontocoursetotal() {
        return contributiontocoursetotal;
    }

    public void setContributiontocoursetotal(DatosGenericos contributiontocoursetotal) {
        this.contributiontocoursetotal = contributiontocoursetotal;
    }
    private DatosGenericos contributiontocoursetotal;// Opcional //contributiontocoursetotal column
}
