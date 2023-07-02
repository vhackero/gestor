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
public class Modulo {
    
    private int id;
    private String url;
    private String name;
    private int instance;
    private int visible;
    private String modicon;
    private String modname;
    private String modplural;
    private int indent; 
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the instance
     */
    public int getInstance() {
        return instance;
    }
    /**
     * @param instance the instance to set
     */
    public void setInstance(int instance) {
        this.instance = instance;
    }
    /**
     * @return the visible
     */
    public int getVisible() {
        return visible;
    }
    /**
     * @param visible the visible to set
     */
    public void setVisible(int visible) {
        this.visible = visible;
    }
    /**
     * @return the modicon
     */
    public String getModicon() {
        return modicon;
    }
    /**
     * @param modicon the modicon to set
     */
    public void setModicon(String modicon) {
        this.modicon = modicon;
    }
    /**
     * @return the modname
     */
    public String getModname() {
        return modname;
    }
    /**
     * @param modname the modname to set
     */
    public void setModname(String modname) {
        this.modname = modname;
    }
    /**
     * @return the modplural
     */
    public String getModplural() {
        return modplural;
    }
    /**
     * @param modplural the modplural to set
     */
    public void setModplural(String modplural) {
        this.modplural = modplural;
    }
    /**
     * @return the indent
     */
    public int getIndent() {
        return indent;
    }
    /**
     * @param indent the indent to set
     */
    public void setIndent(int indent) {
        this.indent = indent;
    }
    
}