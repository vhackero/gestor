package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.*;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.*;

/** Cuenta inscripciones formales; las notas y participaciones no multiplican una oportunidad. */
public final class OportunidadesInscripcionRepository {
    private final InscripcionRepository repo;
    public static final String REGLA="PERMITE_INSCRIPCION_LIMITE_REPROBADAS";
    public OportunidadesInscripcionRepository(InscripcionRepository r) { repo=r; }
    private long n(Object x) { return x==null?0:x instanceof Boolean?((Boolean)x?1:0):x instanceof Number?((Number)x).longValue():Long.parseLong(x.toString()); }
    @SuppressWarnings("unchecked")
    public ConfiguracionOportunidadesDTO configuracion() {
        List<Object[]> rows=repo.entityManager.createNativeQuery("SELECT r.activo, "
            + "(SELECT valor_default FROM cat_parametros_regla_inscripcion WHERE clave_regla='RESTRICCIONES_ACADEMICAS_GENERALES' AND clave_parametro='LIMITE_REPROBACIONES_POR_MATERIA'),"
            + "MAX(CASE WHEN p.clave_parametro='MENSAJE_ULTIMA_OPORTUNIDAD' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='MENSAJE_OPORTUNIDADES_AGOTADAS' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='APLICAR_LIMITE_OPORTUNIDADES' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='LIMITE_OPORTUNIDADES_ADICIONALES' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='APLICAR_LIMITE_SUSTITUCIONES' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='LIMITE_SUSTITUCIONES_ADICIONALES' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='MENSAJE_ULTIMA_OPORTUNIDAD_ADICIONAL' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='MENSAJE_AGOTAMIENTO_ADICIONAL' THEN p.valor_default END),"
            + "MAX(CASE WHEN p.clave_parametro='MENSAJE_LIMITE_SUSTITUCIONES' THEN p.valor_default END) "
            + "FROM cat_reglas_inscripcion r LEFT JOIN cat_parametros_regla_inscripcion p ON p.clave_regla=r.clave "
            + "WHERE r.clave='"+REGLA+"' GROUP BY r.activo").getResultList();
        if(rows.isEmpty())throw new IllegalStateException("Falta la configuración de oportunidades. Ejecute R077.");
        Object[] r=rows.get(0);ConfiguracionOportunidadesDTO d=new ConfiguracionOportunidadesDTO();
        d.setPermiteContinuar(n(r[0])==1);d.setLimite(Integer.parseInt(Objects.toString(r[1],"0")));
        if(!"1".equals(r[4]) && !"0".equals(r[4]))throw new IllegalStateException("Falta el interruptor de aplicación de oportunidades. Ejecute R078.");
        if(r[5]==null || r[7]==null || (!"1".equals(r[6]) && !"0".equals(r[6])))throw new IllegalStateException("Falta la parametrización de adicionales. Ejecute R079.");
        d.setAplicarLimite("1".equals(r[4]));d.setMensajeUltima((String)r[2]);d.setMensajeAgotada((String)r[3]);
        d.setLimiteAcumulado(Integer.parseInt(r[5].toString()));d.setAplicarLimiteSustituciones("1".equals(r[6]));d.setLimiteSustituciones(Integer.parseInt(r[7].toString()));
        d.setMensajeUltimaAcumulada((String)r[8]);d.setMensajeAgotadaAcumulada((String)r[9]);d.setMensajeSustituciones((String)r[10]);d.validar();return d;
    }
    public void guardar(ConfiguracionOportunidadesDTO d,Long usuario) {
        if(d==null || usuario==null)throw new IllegalArgumentException("Configuración de oportunidades incompleta.");
        d.validar();ReglaInscripcionDTO regla=new ReglaInscripcionDTO();regla.setClave(REGLA);regla.setActiva(d.isPermiteContinuar());repo.guardarReglaInscripcion(regla,usuario);
        parametro(REGLA,"APLICAR_LIMITE_OPORTUNIDADES",d.isAplicarLimite()?"1":"0");
        parametro("RESTRICCIONES_ACADEMICAS_GENERALES","LIMITE_REPROBACIONES_POR_MATERIA",String.valueOf(d.getLimite()));
        parametro(REGLA,"MENSAJE_ULTIMA_OPORTUNIDAD",d.getMensajeUltima());parametro(REGLA,"MENSAJE_OPORTUNIDADES_AGOTADAS",d.getMensajeAgotada());
        parametro(REGLA,"LIMITE_OPORTUNIDADES_ADICIONALES",String.valueOf(d.getLimiteAcumulado()));
        parametro(REGLA,"APLICAR_LIMITE_SUSTITUCIONES",d.isAplicarLimiteSustituciones()?"1":"0");
        parametro(REGLA,"LIMITE_SUSTITUCIONES_ADICIONALES",String.valueOf(d.getLimiteSustituciones()));
        parametro(REGLA,"MENSAJE_ULTIMA_OPORTUNIDAD_ADICIONAL",d.getMensajeUltimaAcumulada());
        parametro(REGLA,"MENSAJE_AGOTAMIENTO_ADICIONAL",d.getMensajeAgotadaAcumulada());
        parametro(REGLA,"MENSAJE_LIMITE_SUSTITUCIONES",d.getMensajeSustituciones());
    }
    private void parametro(String regla,String clave,String valor) {
        int cambios=repo.entityManager.createNativeQuery("UPDATE cat_parametros_regla_inscripcion SET valor_default=:valor WHERE clave_regla=:regla AND clave_parametro=:clave")
            .setParameter("valor",valor).setParameter("regla",regla).setParameter("clave",clave).executeUpdate();
        if(cambios==0) {
            Number existe=(Number)repo.entityManager.createNativeQuery("SELECT COUNT(*) FROM cat_parametros_regla_inscripcion WHERE clave_regla=:regla AND clave_parametro=:clave")
                .setParameter("regla",regla).setParameter("clave",clave).getSingleResult();
            if(existe.intValue()!=1)throw new IllegalStateException("Falta el parámetro "+clave+". Ejecute R077.");
        }
    }
    @SuppressWarnings("unchecked")
    public List<OportunidadesUnidadDTO> contar(Long persona,Long plan,int limite) {
        boolean aplicar=configuracion().isAplicarLimite();
        String periodo="SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)";
        String baja="EXISTS(SELECT 1 FROM rel_persona_bajas b JOIN rel_motivo_baja m ON m.id_motivo_baja=b.motivo_baja_id "
            + "WHERE b.id_persona=i.Idpersona AND b.id_plan=i.idplan AND b.contabilizar=1 AND ("
            + "(m.tipo_baja_id IN ("+repo.tiposBajaInscripcion("CON_UD")+") AND b.id_programa=i.idprograma AND ("
            + "(b.id_inscripcion=i.id AND ((e.id_evento IS NOT NULL AND b.id_evento=e.id_evento AND "+repo.periodoBajaCompatible("b","e")+") "
            + "OR (e.id_evento IS NULL AND EXISTS(SELECT 1 FROM tbl_periodos_inscripcion pb WHERE pb.id_periodo=b.id_periodo AND pb.nombre_periodo REGEXP '^[0-9]{4}-[12]$')))) "
            + "OR (b.id_evento=e.id_evento AND "+repo.periodoBajaCompatible("b","e")+" AND EXISTS(SELECT 1 FROM rel_grupo_participante gp "
            + "JOIN tbl_grupos g ON g.id=gp.id_grupo WHERE gp.id_persona_participante=i.Idpersona AND g.id_evento=e.id_evento AND g.id=b.id_grupo)))) "
            + "OR (m.tipo_baja_id IN ("+repo.tiposBajaInscripcion("SIN_UD")+") AND COALESCE(b.id_programa,0)=0 AND "
            + periodo+" REGEXP '^[0-9]{4}-[12]$' AND EXISTS(SELECT 1 FROM tbl_periodos_inscripcion pb WHERE pb.id_periodo=b.id_periodo AND pb.nombre_periodo="+periodo+"))))";
        String aprobada="EXISTS(SELECT 1 FROM rel_grupo_participante agp JOIN tbl_grupos ag ON ag.id=agp.id_grupo "
            + "JOIN tbl_eventos ae ON ae.id_evento=ag.id_evento JOIN tbl_ficha_descriptiva_programa af ON af.id_programa=ae.id_programa "
            + "WHERE agp.id_persona_participante=:persona AND agp.calificacion_final>=af.calificacion_min_aprobatoria "
            + "AND "+repo.mismaUnidad("destino","af")+" AND "+repo.participacionSinBaja("agp","ag","ae","af")+")";
        String sql="SELECT destino.id_programa,destino.nombre_tentativo,i.id,COALESCE(e.id_evento,0),i.groupbase,i.fecha_registro,"+baja+","+aprobada+" "
            + "FROM tbl_inscripciones i JOIN tbl_ficha_descriptiva_programa f ON f.id_programa=i.idprograma "
            + "JOIN tbl_ficha_descriptiva_programa destino ON destino.id_programa=(SELECT MIN(d.id_programa) "
            + "FROM tbl_ficha_descriptiva_programa d WHERE d.id_plan=:plan AND "+repo.mismaUnidad("d","f")+") "
            + "LEFT JOIN tbl_eventos e ON e.id_evento=i.idevento WHERE i.Idpersona=:persona ORDER BY destino.id_programa,i.id";
        List<Object[]> rows=repo.entityManager.createNativeQuery(sql).setParameter("plan",plan).setParameter("persona",persona).getResultList();
        Map<Long,OportunidadesUnidadDTO> unidades=new LinkedHashMap<>();Map<Long,Map<String,List<Object[]>>> grupos=new LinkedHashMap<>();
        for(Object[] r:rows) {
            long id=n(r[0]);OportunidadesUnidadDTO d=unidades.get(id);
            if(d==null){d=new OportunidadesUnidadDTO();d.setIdPrograma(id);d.setNombre(Objects.toString(r[1],"UD "+id));d.setLimite(limite);d.setLimiteActivo(aplicar);unidades.put(id,d);grupos.put(id,new LinkedHashMap<>());}
            if(n(r[7])==1)d.setAcreditada(true);
            // Sin evento sólo se colapsan duplicados con igual identificador académico y fecha exacta.
            String clave=n(r[3])>0?"EVENTO:"+r[3]:r[4]!=null && !r[4].toString().trim().isEmpty() && r[5]!=null?
                "SIN_EVENTO:"+r[4].toString().trim().toLowerCase(Locale.ROOT)+":"+r[5]:"REGISTRO:"+r[2];
            grupos.get(id).computeIfAbsent(clave,k->new ArrayList<>()).add(r);
        }
        for(Map.Entry<Long,Map<String,List<Object[]>>> entry:grupos.entrySet()) {
            OportunidadesUnidadDTO d=unidades.get(entry.getKey());int usadas=0;
            for(List<Object[]> grupo:entry.getValue().values()) {
                boolean excluida=grupo.stream().anyMatch(r->n(r[6])==1);
                for(Object[] r:grupo)(excluida?d.getExcluidasPorBaja():d.getInscripciones()).add(n(r[2]));
                if(!excluida)usadas++;
            }
            d.setConsumidas(usadas);
        }
        return new ArrayList<>(unidades.values());
    }
}
