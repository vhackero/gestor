package mx.gob.sedesol.basegestor.model.repositories.admin;
import java.util.*;
import javax.persistence.*;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;

@Repository
public class RegistroUsuariosMoodleRepository {
    @PersistenceContext private EntityManager entityManager;
    @SuppressWarnings("unchecked") public List<Object[]> plataformas(){return entityManager.createNativeQuery("SELECT id_parametro_wsmoodle,descripcion FROM cat_parametros_wsmoodle ORDER BY orden,id_parametro_wsmoodle").getResultList();}
    @SuppressWarnings("unchecked") public List<Object[]> planes(){return entityManager.createNativeQuery("SELECT id_plan,nombre FROM tbl_planes ORDER BY nombre,id_plan").getResultList();}
    public boolean existePlan(Integer plan){return ((Number)entityManager.createNativeQuery("SELECT COUNT(*) FROM tbl_planes WHERE id_plan=:plan").setParameter("plan",plan).getSingleResult()).longValue()>0;}
    private String filtro(Integer plan){return " FROM tbl_persona p WHERE p.activo=1 AND NOT EXISTS(SELECT 1 FROM rel_personas_plataformas_moodle m WHERE m.id_persona=p.id_persona AND m.id_plataforma_moodle=:plataforma) "
        +(plan==null?"":" AND EXISTS(SELECT 1 FROM tbl_persona_aspirante a WHERE a.id_persona=p.id_persona AND a.id_plan=:plan) ");}
    private Query parametros(Query query,Integer plataforma,Integer plan){query.setParameter("plataforma",plataforma);if(plan!=null)query.setParameter("plan",plan);return query;}
    // La consulta individual permite comprobar y reparar una relación local obsoleta.
    private String filtroIndividual(Integer plan){return filtro(plan).replace("AND NOT EXISTS(SELECT 1 FROM rel_personas_plataformas_moodle m WHERE m.id_persona=p.id_persona AND m.id_plataforma_moodle=:plataforma)","AND :plataforma IS NOT NULL");}
    public long[] resumen(Integer plataforma,Integer plan){Object[] fila=(Object[])parametros(entityManager.createNativeQuery("SELECT COALESCE(MAX(p.id_persona),0) AS corte,COUNT(*) AS total"+filtro(plan)),plataforma,plan).getSingleResult();return new long[]{((Number)fila[0]).longValue(),((Number)fila[1]).longValue()};}
    public long[] resumen(Integer plataforma,Integer plan,String usuario){Object[] fila=(Object[])parametros(entityManager.createNativeQuery("SELECT COALESCE(MAX(p.id_persona),0) AS corte,COUNT(*) AS total"+filtroIndividual(plan)+" AND LOWER(p.sso_idUsuario)=:usuario"),plataforma,plan).setParameter("usuario",usuario).getSingleResult();return new long[]{((Number)fila[0]).longValue(),((Number)fila[1]).longValue()};}
    @SuppressWarnings("unchecked") public List<Object[]> estadoUsuario(String usuario,Integer plataforma,Integer plan){return entityManager.createNativeQuery("SELECT CASE WHEN p.activo=1 THEN 1 ELSE 0 END AS usuario_activo,EXISTS(SELECT 1 FROM tbl_persona_aspirante a WHERE a.id_persona=p.id_persona AND a.id_plan=:plan) AS pertenece_plan,EXISTS(SELECT 1 FROM rel_personas_plataformas_moodle m WHERE m.id_persona=p.id_persona AND m.id_plataforma_moodle=:plataforma) AS registrado FROM tbl_persona p WHERE LOWER(p.sso_idUsuario)=:usuario")
        .setParameter("usuario",usuario).setParameter("plan",plan).setParameter("plataforma",plataforma).getResultList();}
    @SuppressWarnings("unchecked") public List<Long> pendientes(Integer plataforma,Integer plan,long ultimo,long corte){
        return pendientes(plataforma,plan,null,ultimo,corte);
    }
    @SuppressWarnings("unchecked") public List<Long> pendientes(Integer plataforma,Integer plan,String usuario,long ultimo,long corte){
        Query query=parametros(entityManager.createNativeQuery("SELECT p.id_persona"+(usuario==null?filtro(plan):filtroIndividual(plan))+(usuario==null?"":" AND LOWER(p.sso_idUsuario)=:usuario")+" AND p.id_persona>:ultimo AND p.id_persona<=:corte ORDER BY p.id_persona"),plataforma,plan);
        if(usuario!=null)query.setParameter("usuario",usuario);
        List<?> filas=query.setParameter("ultimo",ultimo).setParameter("corte",corte).setMaxResults(100).getResultList();
        List<Long> ids=new ArrayList<>();for(Object id:filas)ids.add(((Number)id).longValue());return ids;
    }
    public void bloquear(Long persona){entityManager.createNativeQuery("SELECT id_persona FROM tbl_persona WHERE id_persona=:persona FOR UPDATE").setParameter("persona",persona).getResultList();}
    @SuppressWarnings("unchecked") public PersonaDTO personaPendiente(Long persona,Integer plataforma,Integer plan){
        return personaPendiente(persona,plataforma,plan,null);
    }
    @SuppressWarnings("unchecked") public PersonaDTO personaPendiente(Long persona,Integer plataforma,Integer plan,String usuario){
        Query query=parametros(entityManager.createNativeQuery("SELECT p.id_persona,p.sso_idUsuario,p.sso_nombre,p.sso_apellidoPaterno,p.sso_apellidoMaterno"+(usuario==null?filtro(plan):filtroIndividual(plan))+" AND p.id_persona=:persona"+(usuario==null?"":" AND LOWER(p.sso_idUsuario)=:usuario")+" FOR UPDATE"),plataforma,plan).setParameter("persona",persona);
        if(usuario!=null)query.setParameter("usuario",usuario);
        List<Object[]> filas=query.getResultList();
        if(filas.isEmpty())return null;Object[] f=filas.get(0);PersonaDTO dto=new PersonaDTO();dto.setIdPersona(((Number)f[0]).longValue());dto.setUsuario((String)f[1]);dto.setNombre((String)f[2]);dto.setApellidoPaterno((String)f[3]);dto.setApellidoMaterno((String)f[4]);return dto;
    }
    public String correo(Long persona){List<?> filas=entityManager.createNativeQuery("SELECT sso_correoElectronico FROM rel_persona_correo WHERE id_persona=:persona AND id_tipo_correo=1 ORDER BY id_persona_correo")
        .setParameter("persona",persona).setMaxResults(1).getResultList();return filas.isEmpty()?null:(String)filas.get(0);}
    public String[] credencialOriginal(Long persona){Object[] fila=(Object[])entityManager.createNativeQuery("SELECT sso_unidadAdministrativa,contrasenia FROM tbl_persona WHERE id_persona=:persona").setParameter("persona",persona).getSingleResult();return new String[]{(String)fila[0],(String)fila[1]};}
    public void guardar(Long persona,Integer plataforma,Integer idMoodle,Long actor){
        if(idMoodle==null || idMoodle<=0)throw new IllegalArgumentException("ID Moodle inválido");
        entityManager.createNativeQuery("INSERT INTO rel_personas_plataformas_moodle(id_persona,id_plataforma_moodle,id_persona_moodle,usuario_modifico) VALUES(:persona,:plataforma,:idMoodle,:actor)")
            .setParameter("persona",persona).setParameter("plataforma",plataforma).setParameter("idMoodle",idMoodle).setParameter("actor",actor).executeUpdate();
    }
    public void guardarReconciliado(Long persona,Integer plataforma,Integer idMoodle,Long actor){
        if(idMoodle==null || idMoodle<=0)throw new IllegalArgumentException("ID Moodle inválido");
        entityManager.createNativeQuery("INSERT INTO rel_personas_plataformas_moodle(id_persona,id_plataforma_moodle,id_persona_moodle,usuario_modifico) VALUES(:persona,:plataforma,:idMoodle,:actor) ON DUPLICATE KEY UPDATE id_persona_moodle=VALUES(id_persona_moodle),usuario_modifico=VALUES(usuario_modifico)")
            .setParameter("persona",persona).setParameter("plataforma",plataforma).setParameter("idMoodle",idMoodle).setParameter("actor",actor).executeUpdate();
    }
}
