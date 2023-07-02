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
public class Rol {
    
    private int roleid;
    private String name;
    private String shortname;
    private int sortorder;

    /**
     * @return the roleId
     */
    public int getRoleid() {
        return roleid;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleid(int roleId) {
        this.roleid = roleId;
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
     * @return the shortName
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortname(String shortName) {
        this.shortname = shortName;
    }

    /**
     * @return the sortOrder
     */
    public int getSortOrder() {
        return sortorder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortorder(int sortOrder) {
        this.sortorder = sortOrder;
    }
}
