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
public class Calificaciones {

    private List<Elemntos> items;
    private List<Resultado> outcomes;

    public List<Elemntos> getItems() {
        return items;
    }

    public void setItems(List<Elemntos> items) {
        this.items = items;
    }

    public List<Resultado> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Resultado> outcomes) {
        this.outcomes = outcomes;
    }

}
