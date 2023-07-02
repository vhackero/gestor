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
public class Tablas {

    private List<Tabla> tables;
    private List<Alertas> warnings;

    public List<Tabla> getTables() {
        return tables;
    }

    public void setTables(List<Tabla> tables) {
        this.tables = tables;
    }

    public List<Alertas> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Alertas> warnings) {
        this.warnings = warnings;
    }

}
