package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.*;
import javax.persistence.Query;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConclusionEstudiosDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReglaInscripcionDTO;

public final class ConclusionEstudiosRepository {
    public static final String REGLA="VALIDAR_CONCLUSION_ESTUDIOS";
    private final InscripcionRepository repo;
    public ConclusionEstudiosRepository(InscripcionRepository repo) { this.repo=repo; }
    private long n(Object o) { return o==null?0:((Number)o).longValue(); }
    @SuppressWarnings("unchecked")
    public ConclusionEstudiosDTO evaluar(Long persona, Long plan, Long convocatoria) {
        ConclusionEstudiosDTO d=new ConclusionEstudiosDTO();
        d.setReglaActiva(n(repo.entityManager.createNativeQuery("SELECT COUNT(*) FROM cat_reglas_inscripcion r "
            + "LEFT JOIN rel_regla_inscripcion_plan p ON p.clave_regla=r.clave AND p.id_plan=:plan "
            + "WHERE r.clave='"+REGLA+"' AND r.activo=1 AND COALESCE(p.activo,1)=1")
            .setParameter("plan",plan).getSingleResult())==1);
        List<?> totales=repo.entityManager.createNativeQuery("SELECT total_creditos FROM rel_creditos_totales_por_plan WHERE id_plan=:plan")
            .setParameter("plan",plan).getResultList();
        if(totales.size()==1 && n(totales.get(0))>0)d.setCreditosRequeridos(n(totales.get(0)));
        String aprobada="SELECT 1 FROM rel_grupo_participante gp JOIN tbl_grupos g ON g.id=gp.id_grupo "
            + "JOIN tbl_eventos e ON e.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa origen ON origen.id_programa=e.id_programa "
            + "WHERE gp.id_persona_participante=:persona AND gp.calificacion_final>=origen.calificacion_min_aprobatoria "
            + "AND "+repo.mismaUnidad("f","origen")+" AND "+repo.participacionSinBaja("gp","g","e","origen");
        String curricular=aprobada+" AND "+new OptativasAdicionalesRepository(repo).sinRegistroAdicional("gp.id_persona_participante","e.id_evento","origen.id_programa");
        // Una fila por UD del mapa elegible; EXISTS evita multiplicar por participaciones o por catálogo de convocatoria.
        String sql="SELECT f.id_programa,f.nombre_tentativo,f.tipo,f.creditos,f.id_eje_capacitacion,"
            + "EXISTS("+aprobada+"),EXISTS("+curricular+"),"
            + "(SELECT MIN(c.id_programa) FROM tbl_ficha_descriptiva_programa c WHERE c.id_plan=:plan AND "+repo.mismaUnidad("f","c")
            + " AND (c.tipo='Obligatoria' OR EXISTS(SELECT 1 FROM rel_convocatoria_planesyprogramas cc WHERE cc.id_programa=c.id_programa AND cc.id_plan=:plan AND cc.id_convocatoria=:conv))) "
            + "FROM tbl_ficha_descriptiva_programa f WHERE f.id_plan=:plan AND (f.tipo='Obligatoria' OR EXISTS("
            + "SELECT 1 FROM rel_convocatoria_planesyprogramas cp WHERE cp.id_programa=f.id_programa AND cp.id_plan=:plan AND cp.id_convocatoria=:conv)) "
            + "ORDER BY f.id_programa";
        List<Object[]> rows=repo.entityManager.createNativeQuery(sql).setParameter("plan",plan).setParameter("persona",persona)
            .setParameter("conv",convocatoria).getResultList();
        // Se usa la misma identidad por nombre del módulo; el ID conserva separadas las UD sin nombre.
        Map<String,Object[]> unicas=new LinkedHashMap<>();
        Set<Long> bloques=new TreeSet<>(),cubiertos=new TreeSet<>(); boolean mapa=!rows.isEmpty();
        Map<Long,Set<String>> candidatas=new LinkedHashMap<>();
        for(Object[] r:rows) {
            if("Optativa".equals(r[2]) && n(r[3])>0) {
                if(r[4]==null)mapa=false;else bloques.add(n(r[4]));
            }
            String clave="ID:"+r[7];
            if("Optativa".equals(r[2]) && n(r[3])>0 && r[4]!=null && n(r[6])==1)
                candidatas.computeIfAbsent(n(r[4]),k->new LinkedHashSet<>()).add(clave);
            Object[] previa=unicas.get(clave);
            if(previa==null)unicas.put(clave,r);
            else {
                // Los bloques alternativos de una misma optativa son variantes válidas del catálogo.
                if(!Objects.equals(previa[2],r[2]) || n(previa[3])!=n(r[3]))mapa=false;
                if(n(r[5])==1)previa[5]=r[5];if(n(r[6])==1)previa[6]=r[6];
            }
        }
        long optativasPlan=n(repo.entityManager.createNativeQuery("SELECT COUNT(*) FROM tbl_ficha_descriptiva_programa WHERE id_plan=:plan AND tipo='Optativa' AND creditos>0")
            .setParameter("plan",plan).getSingleResult());
        if(optativasPlan>0 && bloques.isEmpty())mapa=false;
        long creditos=0;int obligatorias=0,acreditadas=0;
        for(Object[] r:unicas.values()) {
            if(n(r[5])==1) { creditos+=Math.max(0,n(r[3])); d.getUnidadesAprobadas().add(n(r[0])); }
            if("Obligatoria".equals(r[2])) {
                obligatorias++;
                if(n(r[6])==1)acreditadas++;else d.getObligatoriasPendientes().add(n(r[0]));
            }
        }
        // Una misma UD aprobada no puede cubrir dos espacios de optativa requeridos.
        Map<String,Long> asignadas=new LinkedHashMap<>();
        for(Long bloque:bloques)asignar(bloque,candidatas,asignadas,new HashSet<String>());
        for(Map.Entry<String,Long> asignada:asignadas.entrySet()) {
            cubiertos.add(asignada.getValue());d.getOptativaPorBloque().put(asignada.getValue(),Long.valueOf(asignada.getKey().substring(3)));
        }
        d.setCreditosAprobados(creditos);d.setObligatoriasRequeridas(obligatorias);d.setObligatoriasAcreditadas(acreditadas);
        d.setBloquesRequeridos(bloques.size());d.setBloquesCubiertos(cubiertos.size());
        bloques.removeAll(cubiertos);d.getBloquesPendientes().addAll(bloques);d.setMapaComprobado(mapa);
        return d;
    }
    private boolean asignar(Long bloque,Map<Long,Set<String>> candidatas,Map<String,Long> asignadas,Set<String> visitadas) {
        for(String unidad:candidatas.getOrDefault(bloque,Collections.<String>emptySet())) {
            if(!visitadas.add(unidad))continue;
            Long anterior=asignadas.get(unidad);
            if(anterior==null || asignar(anterior,candidatas,asignadas,visitadas)) { asignadas.put(unidad,bloque);return true; }
        }
        return false;
    }
    @SuppressWarnings("unchecked")
    public List<ReglaInscripcionDTO> configuraciones() {
        List<Object[]> rows=repo.entityManager.createNativeQuery("SELECT p.id_plan,p.nombre,COALESCE(r.activo,1) FROM tbl_planes p "
            + "LEFT JOIN rel_regla_inscripcion_plan r ON r.id_plan=p.id_plan AND r.clave_regla='"+REGLA+"' "
            + "WHERE EXISTS(SELECT 1 FROM rel_creditos_totales_por_plan c WHERE c.id_plan=p.id_plan) ORDER BY p.nombre,p.id_plan").getResultList();
        List<ReglaInscripcionDTO> result=new ArrayList<>();
        for(Object[] r:rows) { ReglaInscripcionDTO d=new ReglaInscripcionDTO();d.setClave(REGLA);d.setIdPlan(n(r[0]));d.setNombre(r[1].toString());d.setActiva(n(r[2])==1);result.add(d); }
        return result;
    }
    public void guardar(ReglaInscripcionDTO d,Long usuario) {
        if(d==null || d.getIdPlan()==null || usuario==null || d.getActiva()==null || !REGLA.equals(d.getClave()))
            throw new IllegalArgumentException("Indica el plan, el estado y el usuario de la configuración de conclusión.");
        Number cuenta=(Number)repo.entityManager.createNativeQuery("SELECT COUNT(*) FROM tbl_planes WHERE id_plan=:plan").setParameter("plan",d.getIdPlan()).getSingleResult();
        if(cuenta.intValue()!=1)throw new IllegalArgumentException("El plan de estudios no existe.");
        repo.entityManager.createNativeQuery("INSERT INTO rel_regla_inscripcion_plan(clave_regla,id_plan,activo,usuario_modifico) "
            + "VALUES (:clave,:plan,:activo,:usuario) ON DUPLICATE KEY UPDATE activo=VALUES(activo),usuario_modifico=VALUES(usuario_modifico),fecha_actualizacion=CURRENT_TIMESTAMP")
            .setParameter("clave",REGLA).setParameter("plan",d.getIdPlan()).setParameter("activo",d.getActiva()?1:0)
            .setParameter("usuario",usuario).executeUpdate();
    }
}
