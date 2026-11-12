package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.*;
import java.text.Normalizer;
import javax.persistence.Query;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.*;

/** Registro de carácter y vínculos al confirmar, sin inferir reclasificaciones históricas. */
public final class OptativasAdicionalesRepository {
    private final InscripcionRepository repo;
    public OptativasAdicionalesRepository(InscripcionRepository repo) { this.repo=repo; }
    private long n(Object v) { return v==null?0:((Number)v).longValue(); }
    private String texto(Object v) { return v==null?null:v.toString(); }
    private String identidad(String v) {
        return v==null?"":Normalizer.normalize(v,Normalizer.Form.NFD).replaceAll("\\p{M}","")
            .replaceAll("(?i)\\s*\\(opcional\\)\\s*$", "").trim().replaceAll("\\s+"," ").toUpperCase(Locale.ROOT);
    }
    private boolean misma(Long id,String nombre,Long otro,String otroNombre) {
        return Objects.equals(id,otro) || (!identidad(nombre).isEmpty() && identidad(nombre).equals(identidad(otroNombre)));
    }

    public String sinRegistroAdicional(String persona,String evento,String programa) {
        return "NOT EXISTS (SELECT 1 FROM rel_inscripcion_optativa ox JOIN tbl_inscripciones ix ON ix.id=ox.id_inscripcion "
            + "WHERE ox.caracter='ADICIONAL' AND ix.Idpersona="+persona+" AND ix.idevento="+evento
            + " AND ix.idprograma="+programa+")";
    }

    /** Requeridas acreditadas y elegibles en plan/convocatoria; adicionales no cubren otro espacio. */
    public int requeridasAcreditadas(Long persona,Long plan,Long convocatoria) {
        String sql="SELECT COUNT(*) FROM (SELECT LOWER(TRIM(dest.nombre_tentativo)) identidad "
            + "FROM rel_grupo_participante gp JOIN tbl_grupos g ON g.id=gp.id_grupo "
            + "JOIN tbl_eventos e ON e.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa f ON f.id_programa=e.id_programa "
            + "JOIN tbl_ficha_descriptiva_programa dest ON dest.id_plan=:plan AND "+repo.mismaUnidad("dest","f")
            + " JOIN rel_convocatoria_planesyprogramas cp ON cp.id_programa=dest.id_programa AND cp.id_plan=:plan AND cp.id_convocatoria=:conv "
            + "WHERE gp.id_persona_participante=:persona AND f.tipo='Optativa' AND dest.tipo='Optativa' "
            + "AND dest.creditos>0 AND gp.calificacion_final>=f.calificacion_min_aprobatoria "
            + "AND NULLIF(TRIM(dest.nombre_tentativo),'') IS NOT NULL AND "+repo.participacionSinBaja("gp","g","e","f")
            + " AND "+sinRegistroAdicional("gp.id_persona_participante","e.id_evento","f.id_programa")
            + " GROUP BY LOWER(TRIM(dest.nombre_tentativo))) acreditadas";
        return (int)n(repo.entityManager.createNativeQuery(sql).setParameter("plan",plan).setParameter("conv",convocatoria)
            .setParameter("persona",persona).getSingleResult());
    }

    /** Cada fila de la cadena conserva su resultado; sólo la última representa la deuda vigente. */
    @SuppressWarnings("unchecked")
    public List<AdeudoOptativaDTO> adeudos(Long persona,Long plan) {
        String sinBaja=repo.participacionSinBaja("gp","g","e","f");
        String sql="SELECT o.id_inscripcion,COALESCE(o.id_adeudo_origen,o.id_inscripcion),o.id_inscripcion_anterior,"
            + "i.idprograma,f.nombre_tentativo,f.cve_programa,b.nombre,s.nombre,f.id_programa_antecedente,"
            + "MAX(CASE WHEN gp.calificacion_final>=f.calificacion_min_aprobatoria AND "+sinBaja+" THEN 1 ELSE 0 END),"
            + "MAX(CASE WHEN gp.calificacion_final<f.calificacion_min_aprobatoria AND "+sinBaja+" THEN 1 ELSE 0 END),"
            + "CASE WHEN COUNT(gp.id)>0 AND MAX(CASE WHEN "+sinBaja+" THEN 1 ELSE 0 END)=0 THEN 1 ELSE 0 END,"
            + "COUNT(gp.calificacion_final) "
            + "FROM rel_inscripcion_optativa o JOIN tbl_inscripciones i ON i.id=o.id_inscripcion "
            + "JOIN tbl_ficha_descriptiva_programa f ON f.id_programa=i.idprograma "
            + "JOIN tbl_malla_curricular b ON b.id=f.id_eje_capacitacion JOIN tbl_malla_curricular s ON s.id=b.id_padre "
            + "LEFT JOIN tbl_eventos e ON e.id_evento=i.idevento LEFT JOIN tbl_grupos g ON g.id_evento=e.id_evento "
            + "LEFT JOIN rel_grupo_participante gp ON gp.id_grupo=g.id AND gp.id_persona_participante=i.Idpersona "
            + "WHERE i.Idpersona=:persona AND i.idplan=:plan AND o.caracter='ADICIONAL' "
            + "GROUP BY o.id_inscripcion,o.id_adeudo_origen,o.id_inscripcion_anterior,i.idprograma,f.nombre_tentativo,"
            + "f.cve_programa,b.nombre,s.nombre,f.id_programa_antecedente ORDER BY o.id_inscripcion";
        List<Object[]> rows=repo.entityManager.createNativeQuery(sql).setParameter("persona",persona).setParameter("plan",plan).getResultList();
        Map<Long,List<Object[]>> cadenas=new LinkedHashMap<>();
        for(Object[] row:rows)cadenas.computeIfAbsent(n(row[1]),k->new ArrayList<>()).add(row);
        List<AdeudoOptativaDTO> result=new ArrayList<>();
        for(Map.Entry<Long,List<Object[]>> entry:cadenas.entrySet()) {
            List<Object[]> cadena=entry.getValue();
            if(cadena.stream().anyMatch(x->n(x[9])==1) || cadena.stream().noneMatch(x->n(x[10])==1))continue;
            Set<Long> anteriores=new HashSet<>();Map<Long,Object[]> porId=new HashMap<>();
            for(Object[] row:cadena){porId.put(n(row[0]),row);if(row[2]!=null)anteriores.add(n(row[2]));}
            List<Object[]> ultimas=new ArrayList<>();for(Object[] row:cadena)if(!anteriores.contains(n(row[0])))ultimas.add(row);
            if(ultimas.size()!=1 || !porId.containsKey(entry.getKey()))throw new IllegalStateException("El adeudo de optativa requiere conciliación de su historial.");
            Object[] actual=ultimas.get(0);int cambios=0;long consumidas=0;
            for(Object[] row:cadena) {
                if(n(row[11])==0)consumidas++;
                if(row[2]!=null) {
                    Object[] anterior=porId.get(n(row[2]));
                    if(anterior==null)throw new IllegalStateException("Falta el antecedente de la optativa sustituta.");
                    if(!misma(n(row[3]),texto(row[4]),n(anterior[3]),texto(anterior[4])))cambios++;
                }
            }
            AdeudoOptativaDTO d=new AdeudoOptativaDTO();d.setIdOrigen(entry.getKey());d.setIdInscripcionActual(n(actual[0]));
            d.setIdPlan(plan);d.setIdPrograma(n(actual[3]));d.setNombrePrograma(texto(actual[4]));d.setClavePrograma(texto(actual[5]));
            d.setSubestructura(texto(actual[6]));d.setEstructura(texto(actual[7]));d.setIdProgramaAntecedente(actual[8]==null?null:n(actual[8]));
            d.setTipoPrograma("Optativa");d.setIntentosReprobados(consumidas);d.setSustituciones(cambios);
            d.setEnCurso(n(actual[12])==0 && n(actual[11])==0);result.add(d);
        }
        if(!result.isEmpty()) {
            ConfiguracionOportunidadesDTO config=new OportunidadesInscripcionRepository(repo).configuracion();
            for(AdeudoOptativaDTO deuda:result) {
                deuda.setLimiteOportunidadesActivo(config.isAplicarLimite());deuda.setLimiteAcumulado(config.getLimiteAcumulado());
                deuda.setLimiteSustitucionesActivo(config.isAplicarLimiteSustituciones());deuda.setLimiteSustituciones(config.getLimiteSustituciones());
            }
        }
        return result;
    }

    public long inscripcionesPorUnidad(Long persona,Long plan,Long programa) {
        Object id=repo.entityManager.createNativeQuery("SELECT MIN(d.id_programa) FROM tbl_ficha_descriptiva_programa d "
            + "JOIN tbl_ficha_descriptiva_programa f ON f.id_programa=:programa AND "+repo.mismaUnidad("d","f")+" WHERE d.id_plan=:plan")
            .setParameter("programa",programa).setParameter("plan",plan).getSingleResult();
        if(id==null)return 0;
        return new OportunidadesInscripcionRepository(repo).contar(persona,plan,3).stream().filter(d->d.getIdPrograma()==n(id))
            .mapToLong(d->d.getConsumidas()).findFirst().orElse(0);
    }

    public void registrar(Long idInscripcion,InscripcionInsertDTO dto) {
        if(!dto.isOptativa())return;
        long elegible=n(repo.entityManager.createNativeQuery("SELECT COUNT(*) FROM tbl_ficha_descriptiva_programa f JOIN rel_convocatoria_planesyprogramas cp ON cp.id_programa=f.id_programa "
            + "WHERE f.id_programa=:ud AND f.id_plan=:plan AND f.tipo='Optativa' AND f.creditos>0 AND cp.id_plan=:plan AND cp.id_convocatoria=:conv")
            .setParameter("ud",dto.getIdPrograma()).setParameter("plan",dto.getIdPlan()).setParameter("conv",dto.getIdConvocatoria()).getSingleResult());
        if(elegible==0 || dto.getIdProcesoInscripcion()==null)throw new IllegalArgumentException("La optativa no es elegible para el plan y convocatoria de esta inscripción.");
        int aprobadas=requeridasAcreditadas(dto.getIdPersona(),dto.getIdPlan(),dto.getIdConvocatoria());
        AdeudoOptativaDTO deuda=null;
        if(dto.getIdAdeudoOptativa()!=null) {
            for(AdeudoOptativaDTO d:adeudos(dto.getIdPersona(),dto.getIdPlan()))if(d.getIdOrigen().equals(dto.getIdAdeudoOptativa()))deuda=d;
            if(deuda==null || deuda.isEnCurso())throw new IllegalArgumentException("El adeudo ya no admite otra inscripción.");
            ConfiguracionOportunidadesDTO config=new OportunidadesInscripcionRepository(repo).configuracion();
            if(!deuda.isAdmiteOtraOportunidad())throw new IllegalArgumentException(config.mensajeAdeudo(config.getMensajeAgotadaAcumulada(),deuda,dto.getAsignatura()));
            if(!deuda.isAdmiteSustitucion() && !misma(deuda.getIdPrograma(),deuda.getNombrePrograma(),dto.getIdPrograma(),dto.getAsignatura()))throw new IllegalArgumentException(config.mensajeAdeudo(config.getMensajeSustituciones(),deuda,dto.getAsignatura()));
            if(deuda.isLimiteOportunidadesActivo() && inscripcionesPorUnidad(dto.getIdPersona(),dto.getIdPlan(),dto.getIdPrograma())>new OportunidadesInscripcionRepository(repo).configuracion().getLimite())throw new IllegalArgumentException("La UD ya agotó sus oportunidades.");
        }
        boolean requeridaPrevia=n(repo.entityManager.createNativeQuery("SELECT COUNT(*) FROM rel_inscripcion_optativa op JOIN tbl_inscripciones ip ON ip.id=op.id_inscripcion "
            + "JOIN tbl_ficha_descriptiva_programa fp ON fp.id_programa=ip.idprograma JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=:ud "
            + "WHERE ip.Idpersona=:persona AND ip.idplan=:plan AND op.caracter='REQUERIDA' AND "+repo.mismaUnidad("fp","fd"))
            .setParameter("ud",dto.getIdPrograma()).setParameter("persona",dto.getIdPersona()).setParameter("plan",dto.getIdPlan()).getSingleResult())>0;
        if(deuda!=null && requeridaPrevia)throw new IllegalArgumentException("Una optativa requerida no puede sustituir el adeudo de otra.");
        boolean adicional=deuda!=null || (aprobadas>=8 && !requeridaPrevia && !Integer.valueOf(1).equals(dto.getRecursamiento()));
        if(adicional && deuda==null) {
            long sinClasificar=n(repo.entityManager.createNativeQuery("SELECT COUNT(*) FROM tbl_inscripciones ip JOIN tbl_ficha_descriptiva_programa fp ON fp.id_programa=ip.idprograma "
                + "JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=:ud WHERE ip.Idpersona=:persona AND ip.idplan=:plan AND ip.id<:nueva "
                + "AND "+repo.mismaUnidad("fp","fd")+" AND NOT EXISTS(SELECT 1 FROM rel_inscripcion_optativa op WHERE op.id_inscripcion=ip.id)")
                .setParameter("ud",dto.getIdPrograma()).setParameter("persona",dto.getIdPersona()).setParameter("plan",dto.getIdPlan()).setParameter("nueva",idInscripcion).getSingleResult());
            if(sinClasificar>0)throw new IllegalArgumentException("La clasificación histórica de esta optativa requiere conciliación antes de inscribirla como adicional.");
        }
        if(adicional && dto.getAsignatura()!=null && dto.getAsignatura().toLowerCase(Locale.ROOT).contains("inteligencia artificial"))
            throw new IllegalArgumentException("Inteligencia Artificial sólo puede cursarse como optativa requerida.");
        if(deuda!=null && Objects.equals(deuda.getIdInscripcionActual(),idInscripcion))throw new IllegalArgumentException("Vínculo de optativa inválido.");
        Query q=repo.entityManager.createNativeQuery("INSERT INTO rel_inscripcion_optativa (id_inscripcion,caracter,id_adeudo_origen,id_inscripcion_anterior,id_proceso,optativas_requeridas_acreditadas) "
            + "VALUES(:id,:caracter,:origen,:anterior,:proceso,:aprobadas)");
        q.setParameter("id",idInscripcion).setParameter("caracter",adicional?"ADICIONAL":"REQUERIDA")
            .setParameter("origen",deuda==null?null:deuda.getIdOrigen()).setParameter("anterior",deuda==null?null:deuda.getIdInscripcionActual())
            .setParameter("proceso",dto.getIdProcesoInscripcion()).setParameter("aprobadas",aprobadas).executeUpdate();
    }
}
