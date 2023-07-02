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
public class MiembrosGrupo {
    private int groupid ;
    List<Integer> userids ;

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public List<Integer> getUserids() {
        return userids;
    }

    public void setUserids(List<Integer> userids) {
        this.userids = userids;
    }

    @Override
    public String toString() {
        return "MiembrosGrupo{" + "groupid=" + groupid + ", userids=" + userids + '}'+"\n\n";
    }
    
}
