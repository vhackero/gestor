package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.*;
import javax.persistence.Query;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Evidencia reproducible de 1.3/6.9/9.5; nunca deduce periodos desde la fecha de captura. */
public final class EvaluacionInscripcionRepository {

    private final InscripcionRepository repo;
    public EvaluacionInscripcionRepository(InscripcionRepository repo) { this.repo=repo; }
    @SuppressWarnings("unchecked")
    private List<Object[]> filas(String sql, Long persona, Long plan) {
        Query q=repo.entityManager.createNativeQuery(sql);
        if(sql.contains(":persona"))q.setParameter("persona",persona);
        if(sql.contains(":plan"))q.setParameter("plan",plan);
        return q.getResultList();
    }
    private long n(Object v) { return v==null?0:((Number)v).longValue(); }
    public String evidencia(Long persona, Long plan, Date fecha) {
        Map<String,Object> e=new LinkedHashMap<>();
        e.put("fuente","P2024-2027- Reglas de negocio_Octubre 2026.docx");
        e.put("sha256Fuente","bc10a51804a36ae461de90bff241d88ab794541e55dfa052dfd4dbedc5dbb7f8");
        e.put("numerales",Arrays.asList("1.2","1.3","4.2","6.9","7.1.1","9.5"));
        e.put("persona",persona); e.put("plan",plan);
        List<?> actuales=repo.entityManager.createNativeQuery("SELECT DISTINCT nombre_periodo FROM tbl_periodos_inscripcion "
            + "WHERE :fecha BETWEEN fecha_inicio AND fecha_finalizacion AND nombre_periodo REGEXP '^[0-9]{4}-[12]$' ORDER BY nombre_periodo")
            .setParameter("fecha",fecha).getResultList();
        String actual=actuales.size()==1?actuales.get(0).toString():null;
        e.put("periodoEvaluado",actual);
        String periodo="COALESCE(NULLIF(SUBSTRING_INDEX(ev.cve_evento_cap,'-',-2),''),SUBSTRING_INDEX(i.groupbase,'-',-2))";
        List<Object[]> ins=filas("SELECT i.id AS inscripcion_id,i.idplan AS plan_id,i.idprograma AS programa_id,i.idevento AS evento_id,"+periodo+" AS periodo_inscripcion, "
            + "CASE WHEN i.idplan=:plan THEN 1 ELSE EXISTS(SELECT 1 FROM rel_periodo_origen_validado v "
            + "WHERE v.id_persona=:persona AND v.id_plan_destino=:plan AND v.id_plan_origen=i.idplan AND BINARY v.periodo=BINARY "+periodo+") END AS correspondencia_validada "
            + "FROM tbl_inscripciones i LEFT JOIN tbl_eventos ev ON ev.id_evento=i.idevento WHERE i.Idpersona=:persona ORDER BY i.id",persona,plan);
        e.put("inscripciones_columnas",Arrays.asList("id","plan","UD","evento","periodo","correspondenciaValidada")); e.put("inscripciones",ins);
        List<Object[]> bajas=filas("SELECT b.id_baja AS baja_id,b.id_plan AS plan_id,b.id_programa AS programa_id,b.id_evento AS evento_id,b.id_grupo AS grupo_id,b.id_inscripcion AS inscripcion_id,m.tipo_baja_id AS tipo_baja_id,b.contabilizar AS contabilizar,"
            + "COALESCE(p.nombre_periodo,SUBSTRING_INDEX(ev.cve_evento_cap,'-',-2)) AS periodo_baja,SUBSTRING_INDEX(ev.cve_evento_cap,'-',-2) AS periodo_evento "
            + "FROM rel_persona_bajas b JOIN rel_motivo_baja m ON m.id_motivo_baja=b.motivo_baja_id "
            + "LEFT JOIN tbl_periodos_inscripcion p ON p.id_periodo=b.id_periodo LEFT JOIN tbl_eventos ev ON ev.id_evento=b.id_evento "
            + "WHERE b.id_persona=:persona ORDER BY b.id_baja",persona,plan);
        e.put("bajas_columnas",Arrays.asList("id","plan","UD","evento","grupo","inscripcion","tipo","contabilizar","periodo","periodoEvento"));e.put("bajas",bajas);
        Set<String> incluidos=new TreeSet<>(), excluidos=new TreeSet<>(); List<String> motivos=new ArrayList<>();
        if(actual==null)motivos.add(repo.textosInscripcion().obtenerTexto("gw.inscripcion.mensaje.no.existe.un.unico.periodo.academico.vigente"));
        Map<String,List<Object[]>> porPeriodo=new TreeMap<>();
        for(Object[] i:ins) {
            String per=Objects.toString(i[4],"");
            if(!per.matches("[0-9]{4}-[12]")) { motivos.add(repo.textosInscripcion().obtenerTextoFormateado("gw.inscripcion.mensaje.conciliacion.periodoNoComprobado", i[0]));continue; }
            if(actual!=null && per.compareTo(actual)>=0)continue;
            if(n(i[5])!=1) { motivos.add(repo.textosInscripcion().obtenerTextoFormateado("gw.inscripcion.mensaje.conciliacion.correspondenciaPendiente", i[1], per)); continue; }
            porPeriodo.computeIfAbsent(per,k->new ArrayList<>()).add(i);
        }
        for(Map.Entry<String,List<Object[]>> entry:porPeriodo.entrySet()) {
            String per=entry.getKey(); boolean todas=true;
            for(Object[] i:entry.getValue()) {
                // Temporal por periodo completo (2/8 sin UD), o temporal con UD (2/7)
                // cubriendo cada inscripción formal; una baja parcial nunca excluye el semestre.
                boolean cubierta=false;
                for(Object[] b:bajas) {
                    if(n(b[7])!=1 || n(b[1])!=n(i[1]) || !per.equals(Objects.toString(b[8],"")))continue;
                    if((n(b[6])==2 || n(b[6])==8) && n(b[2])==0) cubierta=true;
                    if((n(b[6])==2 || n(b[6])==7) && per.equals(Objects.toString(b[9],"")) && n(b[2])==n(i[2]) && n(b[3])==n(i[3])
                        && (n(b[5])==0 || n(b[5])==n(i[0]))) cubierta=true;
                }
                todas &= cubierta;
            }
            if(todas)excluidos.add(per);else incluidos.add(per);
        }
        Object ultimo=repo.entityManager.createNativeQuery("SELECT MAX(CAST(SUBSTRING_INDEX(s.nombre,' ',-1) AS UNSIGNED)) "
            + "FROM tbl_ficha_descriptiva_programa f JOIN tbl_malla_curricular b ON b.id=f.id_eje_capacitacion "
            + "JOIN tbl_malla_curricular s ON s.id=b.id_padre WHERE f.id_plan=:plan AND s.nombre REGEXP '^Semestre [0-9]+$'")
            .setParameter("plan",plan).getSingleResult();
        if(n(ultimo)<1)motivos.add(repo.textosInscripcion().obtenerTexto("gw.inscripcion.mensaje.no.se.comprobo.el.ultimo.semestre.del.mapa"));
        e.put("periodosIncluidos",incluidos); e.put("periodosExcluidosBajaTemporalCompleta",excluidos);
        e.put("semestreReferencia",motivos.isEmpty()?Math.min(incluidos.size()+1,(int)n(ultimo)):null);
        e.put("conciliacionDetalles",new LinkedHashSet<>(motivos));
        e.put("conciliacion",motivos.isEmpty()?"":repo.textosInscripcion().obtenerTexto("gw.inscripcion.mensaje.semestre.de.referencia.pendiente.de.conciliacion.hay.periodos"));
        e.put("correspondenciasOrigen",filas("SELECT id_plan_origen,periodo,documento,usuario_valido,fecha_validacion FROM rel_periodo_origen_validado WHERE id_persona=:persona AND id_plan_destino=:plan ORDER BY id_plan_origen,periodo",persona,plan));
        e.put("historial_columnas",Arrays.asList("participacion","grupo","evento","UD","plan","periodo","nota","minimaAprobatoria","sinBajaAplicable","nombreUD"));
        // Hibernate descubre las columnas por etiqueta: cada proyección debe tener un alias único.
        e.put("historial",filas("SELECT gp.id AS participacion_id,g.id AS grupo_id,ev.id_evento AS evento_id,f.id_programa AS programa_id,f.id_plan AS plan_id,SUBSTRING_INDEX(ev.cve_evento_cap,'-',-2) AS periodo_evento,gp.calificacion_final AS calificacion_final,f.calificacion_min_aprobatoria AS minima_aprobatoria,"
            + repo.participacionSinBaja("gp","g","ev","f")+" AS sin_baja_aplicable,f.nombre_tentativo AS nombre_ud FROM rel_grupo_participante gp JOIN tbl_grupos g ON g.id=gp.id_grupo "
            + "JOIN tbl_eventos ev ON ev.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa f ON f.id_programa=ev.id_programa "
            + "WHERE gp.id_persona_participante=:persona ORDER BY gp.id",persona,plan));
        e.put("obligatoriasSinInscripcion_columnas",Arrays.asList("UD","nombre","semestre"));
        e.put("obligatoriasSinInscripcion",filas("SELECT f.id_programa,f.nombre_tentativo,s.nombre "
            + "FROM tbl_ficha_descriptiva_programa f JOIN tbl_malla_curricular b ON b.id=f.id_eje_capacitacion "
            + "JOIN tbl_malla_curricular s ON s.id=b.id_padre WHERE f.id_plan=:plan AND f.tipo='Obligatoria' "
            + "AND NOT EXISTS(SELECT 1 FROM tbl_inscripciones i JOIN tbl_ficha_descriptiva_programa origen ON origen.id_programa=i.idprograma "
            + "WHERE i.Idpersona=:persona AND "+repo.mismaUnidad("f","origen")+") "
            + "AND NOT EXISTS(SELECT 1 FROM rel_grupo_participante gp JOIN tbl_grupos g ON g.id=gp.id_grupo "
            + "JOIN tbl_eventos ev ON ev.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa origen ON origen.id_programa=ev.id_programa "
            + "WHERE gp.id_persona_participante=:persona AND "+repo.mismaUnidad("f","origen")+") ORDER BY f.id_programa",persona,plan));
        e.put("reglas_columnas",Arrays.asList("clave","activoGlobal","fechaConfiguracion","activoPlan","activoPersona","activoEfectivo"));
        e.put("reglas",filas("SELECT r.clave AS clave_regla,r.activo AS activo_global,r.fecha_actualizacion AS fecha_configuracion,p.activo AS activo_plan,pp.activo AS activo_persona,"
            + "CASE WHEN r.clave='VALIDAR_CONCLUSION_ESTUDIOS' THEN (r.activo=1 AND COALESCE(p.activo,1)=1) ELSE COALESCE(pp.activo,p.activo,r.activo) END AS activo_efectivo "
            + "FROM cat_reglas_inscripcion r LEFT JOIN rel_regla_inscripcion_plan p ON p.clave_regla=r.clave AND p.id_plan=:plan "
            + "LEFT JOIN rel_regla_inscripcion_plan_persona pp ON pp.clave_regla=r.clave AND pp.id_plan=:plan AND pp.id_persona=:persona ORDER BY r.clave",persona,plan));
        e.put("parametros_columnas",Arrays.asList("regla","parametro","default","plan","persona","valorEfectivo"));
        e.put("parametros",filas("SELECT p.clave_regla AS clave_regla,p.clave_parametro AS clave_parametro,p.valor_default AS valor_default,v.valor AS valor_plan,vp.valor AS valor_persona,COALESCE(vp.valor,v.valor,p.valor_default) AS valor_efectivo FROM cat_parametros_regla_inscripcion p "
            + "LEFT JOIN rel_valor_parametro_regla_plan v ON v.clave_regla=p.clave_regla AND v.clave_parametro=p.clave_parametro AND v.id_plan=:plan "
            + "LEFT JOIN rel_valor_parametro_regla_plan_persona vp ON vp.clave_regla=p.clave_regla AND vp.clave_parametro=p.clave_parametro AND vp.id_plan=:plan AND vp.id_persona=:persona "
            + "ORDER BY p.clave_regla,p.clave_parametro",persona,plan));
        e.put("criterios",Arrays.asList("NP: baja contabilizable relacionada con participación y periodo; no consume oportunidad ni genera reprobación.",
            "NULL sin baja: en curso; no se convierte en reprobación.","Acreditación válida: resuelve adeudo equivalente conforme a la identidad del módulo.",
            "Adicional: una deuda por cadena, conserva oportunidades y causa.","6.9: periodos formales distintos anteriores; excluye temporal de semestre completo; no usa generación ni fecha de captura."));
        try { return new ObjectMapper().writeValueAsString(e); }
        catch(java.io.IOException ex) { throw new IllegalStateException(repo.textosInscripcion().obtenerTexto("gw.inscripcion.mensaje.no.se.pudo.serializar.la.evidencia.academica"),ex); }
    }
}
