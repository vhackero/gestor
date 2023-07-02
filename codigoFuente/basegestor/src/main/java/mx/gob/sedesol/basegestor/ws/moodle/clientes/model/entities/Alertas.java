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
public class Alertas {

    private String item;  //Opcional item
    private int itemid; // Opcional item id
    private String warningcode;   //the warning code can be used by the client app to implement specific behaviour
    private String message;   //untranslated english message to explain the warning

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getWarningcode() {
        return warningcode;
    }

    public void setWarningcode(String warningcode) {
        this.warningcode = warningcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
