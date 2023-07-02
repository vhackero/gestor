package mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Curso;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.DiscusionForo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Foro;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

public class ActividadesRecursosWS {

    private ParametroWSMoodleDTO parametroWSMoodleDTO;

    public ActividadesRecursosWS(ParametroWSMoodleDTO plataforma) {
        this.parametroWSMoodleDTO = plataforma;
    }

    /**
     * Obtiene la lista de foros a partir de un curso
     *
     * @param courseid
     * @return
     * @throws ErrorWS
     */
    public List<Foro> obtenerForos(int courseid) throws ErrorWS {
        List<Foro> foros;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseids[0]", courseid);
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        foros = ws.ejecutarServicioListGET("mod_forum_get_forums_by_courses", paramMap, new TypeReference<List<Foro>>() {
        }, paramMap);

        return foros;
    }

    /**
     * Crea un tema de discución en un foro esistente
     *
     * @param idforo El id del foro donde se creará la discución
     * @param temaDiscucion o nombre de discución
     * @param mensajeHTML
     * @return id El ID de la discución creada
     * @throws ErrorWS
     */
    public int crearDiscucionForo(int idforo, String temaDiscucion, String mensajeHTML) throws ErrorWS {
        DiscusionForo discusion = null;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("forumid", idforo);
        paramMap.put("subject", temaDiscucion);
        paramMap.put("message", mensajeHTML);
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        discusion = ws.ejecutarServicioPOST("mod_forum_add_discussion", paramMap, null, DiscusionForo.class);

        if (discusion == null || discusion.getDiscussionid() == 0) {
            throw new ErrorWS("No fue posible crear la discusión del foro");
        }
        return discusion.getDiscussionid();
    }

    /**
     * Crea un tema de discución en un foro esistente
     *
     * @param idforo El id del foro donde se creará la discución
     * @param temaDiscucion o nombre de discución
     * @param mensajeHTML
     * @return id El ID de la discución creada
     * @throws ErrorWS
     */
    public int crearDiscucionForoNovedades(int idCurso, String temaDiscucion, String mensajeHTML) throws ErrorWS {
        DiscusionForo discusion = null;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseid", idCurso);
        paramMap.put("subject", temaDiscucion);
        paramMap.put("message", mensajeHTML);
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        discusion = ws.ejecutarServicioPOST("local_add_discussion_news", paramMap, null, DiscusionForo.class);

        if (discusion == null || discusion.getDiscussionid() == 0) {
            throw new ErrorWS("No fue posible crear la discusión del foro");
        }
        return discusion.getDiscussionid();
    }

    /**
     * Elimina una discusion de un foro
     *
     * @param idDiscusion
     * @return true si fue eliminado correctamente
     * @throws ErrorWS
     */
    public boolean eliminarDiscucionForo(int idDiscusion) throws ErrorWS {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("discussionid", idDiscusion);
        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        int i = ws.ejecutarServicioPOST("local_forums_delete_discussion", paramMap, null, Integer.class);

        return true;
    }

    public int crearForo(int idCurso, int seccion, String nombre, String descripcionHTML) throws ErrorWS {
        Integer idForo = null;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseid", idCurso);
        paramMap.put("section", seccion);
        paramMap.put("name", nombre);
        paramMap.put("description", descripcionHTML);

        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        idForo = ws.ejecutarServicioPOST("local_mods_create_forum", paramMap, null, Integer.class);
        if (idForo == null || idForo == 0) {
            throw new ErrorWS("No fue posible crear el foro");
        }
        return idForo;
    }

    public int crearSCORM(int idCurso, int seccion, String nombre, String descripcionHTML) throws ErrorWS {
        Integer idForo = null;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseid", idCurso);
        paramMap.put("section", seccion);
        paramMap.put("name", nombre);
        paramMap.put("description", descripcionHTML);

        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        idForo = ws.ejecutarServicioPOST("local_mods_create_scorm", paramMap, null, Integer.class);

        if (idForo == null || idForo == 0) {
            throw new ErrorWS("No fue posible crear el scorm");
        }
        return idForo;
    }

    /**
     * Elimina cualquier recurso FORO/SCORM/CHAT
     *
     * @param idRecurso
     * @return true si fue posible eliminar el recurso
     * @throws ErrorWS
     */
    public boolean eliminarRecurso(int idRecurso) throws ErrorWS {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("modid", idRecurso);

        WSClientBase ws = new WSClientBase(parametroWSMoodleDTO);
        ws.ejecutarServicioPOST("local_mods_delete", paramMap, null, Integer.class);

        return true;
    }

    public static void main(String[] args) {

        Curso c = new Curso();

        c.setFullname("Curso JMP");
        //paramMap.put("fullname", curso.getFullname());
        c.setShortname("JMP3");
        c.setCategoryid(1);
        c.setStartdate((int) (new Date().getTime() / 1000));
        c.setSummary("Resumen de curso");

        ParametroWSMoodleDTO p = new ParametroWSMoodleDTO();
        p.setHost("http://189.206.122.67/sedesol/plataformaEducativa");
        //p.setHost("http://localhost/plataformaEducativa");
        p.setPath("/oauth/token");
        p.setService("todos");
        //p.setService("prueba");
        p.setOuth("/login/token.php");
        p.setUsername("admin");
        p.setPassword("elearning");

        //p.setPassword("M@cuco88");
        p.setServer("/webservice/rest/server.php");

        try {
            //int idDiscucion = new ActividadesRecursosWS(p).crearDiscucionForo(9, "Este es mi tema de discusión","Este es el mensaje");
            //int idDiscucion = new ActividadesRecursosWS(p).crearSCORM(2,1, "Este es mi SCORM","Esta es la descripcion del SCORM");
            //new ActividadesRecursosWS(p).crearDiscucionForoNovedades(29, "Discusión JMP", "Esta es el mensaje HTML de la discusión");
            //new ActividadesRecursosWS(p).eliminarRecurso(39);
            new ActividadesRecursosWS(p).eliminarDiscucionForo(9);
            //System.out.println(idDiscucion);
        } catch (ErrorWS e) {
            // TODO Auto-generated catch block
            //e.getCause().printStackTrace();
            e.printStackTrace();
            System.out.println(e.getJson());
        }
    }

}
