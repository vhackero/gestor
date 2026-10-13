package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.*;
import javax.persistence.*;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsignacionPlanUsuarioDTO;

@Repository
public class AsignacionPlanUsuarioRepository {
    @PersistenceContext private EntityManager entityManager;
    private static final String SIN_PLAN=" FROM tbl_persona p WHERE EXISTS(SELECT 1 FROM rel_persona_roles r WHERE r.id_persona=p.id_persona AND r.id_rol=2) AND NOT EXISTS(SELECT 1 FROM tbl_persona_aspirante a WHERE a.id_persona=p.id_persona) ";
    public long[] resumenSinPlan(){Object[] fila=(Object[])entityManager.createNativeQuery("SELECT COALESCE(MAX(p.id_persona),0) AS corte,COUNT(*) AS total"+SIN_PLAN).getSingleResult();return new long[]{((Number)fila[0]).longValue(),((Number)fila[1]).longValue()};}
    @SuppressWarnings("unchecked")
    public List<Object[]> sinPlan(long ultimo,long corte,int limite){return entityManager.createNativeQuery("SELECT p.sso_idUsuario,CONCAT_WS(' ',p.sso_nombre,p.sso_apellidoPaterno,p.sso_apellidoMaterno) AS nombre_completo,p.id_persona"+SIN_PLAN+" AND p.id_persona>:ultimo AND p.id_persona<=:corte ORDER BY p.id_persona")
        .setParameter("ultimo",ultimo).setParameter("corte",corte).setMaxResults(limite).getResultList();}
    @SuppressWarnings("unchecked")
    public List<Object[]> catalogosMasivos(){return entityManager.createNativeQuery("SELECT DISTINCT p.id_plan,p.nombre AS nombre_plan,c.convocatoria_id,c.nombre AS nombre_convocatoria FROM tbl_planes p JOIN rel_convocatoria_planesyprogramas r ON r.id_plan=p.id_plan JOIN tbl_convocatoria c ON c.convocatoria_id=r.id_convocatoria WHERE p.id_estatus_plan=3 AND c.activo=1 ORDER BY p.nombre,c.convocatoria_id").getResultList();}
    public void insertarLote(List<AsignacionPlanUsuarioDTO> datos){
        if(datos.isEmpty())return;List<String> valores=new ArrayList<>();
        for(int n=0;n<datos.size();n++)valores.add("(:persona"+n+",:plan"+n+",:convocatoria"+n+")");
        Query query=entityManager.createNativeQuery("INSERT INTO tbl_persona_aspirante(id_persona,id_plan,id_convocatoria) VALUES"+String.join(",",valores));
        for(int n=0;n<datos.size();n++)query.setParameter("persona"+n,datos.get(n).getIdPersona()).setParameter("plan"+n,datos.get(n).getIdPlan()).setParameter("convocatoria"+n,datos.get(n).getIdConvocatoria());
        query.executeUpdate();
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> planes(){
        return entityManager.createNativeQuery("SELECT p.id_plan,p.nombre FROM tbl_planes p WHERE p.id_estatus_plan=3 AND EXISTS(SELECT 1 FROM rel_convocatoria_planesyprogramas r WHERE r.id_plan=p.id_plan) ORDER BY p.nombre,p.id_plan").getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> convocatorias(Integer plan){
        return entityManager.createNativeQuery("SELECT c.convocatoria_id,c.nombre FROM tbl_convocatoria c WHERE c.activo=1 AND EXISTS(SELECT 1 FROM rel_convocatoria_planesyprogramas r WHERE r.id_convocatoria=c.convocatoria_id AND r.id_plan=:plan) ORDER BY c.convocatoria_id DESC")
            .setParameter("plan",plan).getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<AsignacionPlanUsuarioDTO> personas(Collection<String> usuarios){
        List<Object[]> filas=entityManager.createNativeQuery("SELECT p.id_persona,p.sso_idUsuario,CONCAT_WS(' ',p.sso_nombre,p.sso_apellidoPaterno,p.sso_apellidoMaterno) AS nombre_completo FROM tbl_persona p WHERE LOWER(p.sso_idUsuario) IN (:usuarios) AND EXISTS(SELECT 1 FROM rel_persona_roles r WHERE r.id_persona=p.id_persona AND r.id_rol=2) ORDER BY p.id_persona")
            .setParameter("usuarios",usuarios).getResultList();
        List<AsignacionPlanUsuarioDTO> resultado=new ArrayList<>();
        for(Object[] fila:filas){AsignacionPlanUsuarioDTO dto=new AsignacionPlanUsuarioDTO();dto.setIdPersona(((Number)fila[0]).longValue());dto.setUsuario((String)fila[1]);dto.setNombre((String)fila[2]);resultado.add(dto);}
        return resultado;
    }
    @SuppressWarnings("unchecked")
    public List<AsignacionPlanUsuarioDTO> asignaciones(Collection<Long> personas){return consultarAsignaciones(personas,false);}
    public List<AsignacionPlanUsuarioDTO> asignacionesBloqueadas(Collection<Long> personas){return consultarAsignaciones(personas,true);}
    @SuppressWarnings("unchecked")
    private List<AsignacionPlanUsuarioDTO> consultarAsignaciones(Collection<Long> personas,boolean bloquear){
        List<Object[]> filas=entityManager.createNativeQuery("SELECT a.id_persona_aspirante,a.id_persona,p.sso_idUsuario,CONCAT_WS(' ',p.sso_nombre,p.sso_apellidoPaterno,p.sso_apellidoMaterno) AS nombre_completo,a.id_plan,pl.nombre AS nombre_plan,a.id_convocatoria,c.nombre AS nombre_convocatoria FROM tbl_persona_aspirante a JOIN tbl_persona p ON p.id_persona=a.id_persona LEFT JOIN tbl_planes pl ON pl.id_plan=a.id_plan LEFT JOIN tbl_convocatoria c ON c.convocatoria_id=a.id_convocatoria WHERE a.id_persona IN (:personas) ORDER BY a.id_persona,a.id_persona_aspirante"+(bloquear?" FOR UPDATE":""))
            .setParameter("personas",personas).getResultList();
        List<AsignacionPlanUsuarioDTO> resultado=new ArrayList<>();
        for(Object[] fila:filas){AsignacionPlanUsuarioDTO d=new AsignacionPlanUsuarioDTO();d.setIdAsignacion(((Number)fila[0]).longValue());d.setIdPersona(((Number)fila[1]).longValue());d.setUsuario((String)fila[2]);d.setNombre((String)fila[3]);d.setIdPlan(((Number)fila[4]).intValue());d.setPlan((String)fila[5]);d.setIdConvocatoria(((Number)fila[6]).intValue());d.setConvocatoria((String)fila[7]);resultado.add(d);}
        return resultado;
    }
    public boolean destinoValido(Integer plan,Integer convocatoria){
        Number n=(Number)entityManager.createNativeQuery("SELECT COUNT(*) FROM tbl_planes p JOIN rel_convocatoria_planesyprogramas r ON r.id_plan=p.id_plan JOIN tbl_convocatoria c ON c.convocatoria_id=r.id_convocatoria WHERE p.id_plan=:plan AND p.id_estatus_plan=3 AND c.convocatoria_id=:convocatoria AND c.activo=1")
            .setParameter("plan",plan).setParameter("convocatoria",convocatoria).getSingleResult();return n.longValue()>0;
    }
    public void bloquearPersonas(Collection<Long> personas){
        entityManager.createNativeQuery("SELECT id_persona FROM tbl_persona WHERE id_persona IN (:personas) ORDER BY id_persona FOR UPDATE")
            .setParameter("personas",personas).getResultList();
    }
    public void insertar(Long persona,Integer plan,Integer convocatoria){
        entityManager.createNativeQuery("INSERT INTO tbl_persona_aspirante(id_persona,id_plan,id_convocatoria) VALUES(:persona,:plan,:convocatoria)")
            .setParameter("persona",persona).setParameter("plan",plan).setParameter("convocatoria",convocatoria).executeUpdate();
    }
    public int actualizar(AsignacionPlanUsuarioDTO anterior,Integer plan,Integer convocatoria){
        return entityManager.createNativeQuery("UPDATE tbl_persona_aspirante SET id_plan=:plan,id_convocatoria=:convocatoria WHERE id_persona_aspirante=:asignacion AND id_persona=:persona AND id_plan=:planAnterior AND id_convocatoria=:convocatoriaAnterior")
            .setParameter("plan",plan).setParameter("convocatoria",convocatoria).setParameter("asignacion",anterior.getIdAsignacion())
            .setParameter("persona",anterior.getIdPersona()).setParameter("planAnterior",anterior.getIdPlan()).setParameter("convocatoriaAnterior",anterior.getIdConvocatoria()).executeUpdate();
    }
    public int eliminar(AsignacionPlanUsuarioDTO anterior){
        return entityManager.createNativeQuery("DELETE FROM tbl_persona_aspirante WHERE id_persona_aspirante=:asignacion AND id_persona=:persona AND id_plan=:planAnterior AND id_convocatoria=:convocatoriaAnterior")
            .setParameter("asignacion",anterior.getIdAsignacion()).setParameter("persona",anterior.getIdPersona())
            .setParameter("planAnterior",anterior.getIdPlan()).setParameter("convocatoriaAnterior",anterior.getIdConvocatoria()).executeUpdate();
    }
}
