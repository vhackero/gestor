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
class NombreElemento {

  // private String class;  //class
    private int colspan;   //col span
    private String celltype;   //cell type
    private String id;   //id
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
  

//    public String getClass_() {
//        return class_;
//    }
//
//    public void setClass_(String class_) {
//        this.class_ = class_;
//    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public String getCelltype() {
        return celltype;
    }

    public void setCelltype(String celltype) {
        this.celltype = celltype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
