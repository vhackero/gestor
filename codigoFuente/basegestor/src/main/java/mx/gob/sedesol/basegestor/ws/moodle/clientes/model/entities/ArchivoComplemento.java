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
public class ArchivoComplemento {

    private String filename;   //overview file name
    private String fileurl;   //overview file url
    private int filesize;   //overview file size

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

}
