package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;
import java.util.Date;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.*;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Casos dorados GC-01, GC-02, GC-03, GC-03b, GC-06, GC-07, GC-10 y GC-16 del Bloque 3. */
public class Bloque3OfertaYPreseleccionRn20261106Test {
    @Test public void gc01PreseleccionaReprobadasYSoloHabilitaRutaDeRezago() {
        ContextoEvaluacionCurricularV2DTO c=base(4);
        UnidadEvaluacionCurricularV2DTO r1=reprobada(1L,"OB2-1",2,2), r2=reprobada(2L,"OB2-2",2,1), r3=reprobada(3L,"OB3-2",3,1), r4=reprobada(4L,"OB3-4",3,1);
        UnidadEvaluacionCurricularV2DTO nunca=pendiente(5L,"OB3-3",3,"Obligatoria"), nueva=pendiente(6L,"OB4-1",4,"Obligatoria");
        c.getUnidades().add(r1);c.getUnidades().add(r2);c.getUnidades().add(r3);c.getUnidades().add(r4);c.getUnidades().add(nunca);c.getUnidades().add(nueva);
        ResultadoEvaluacionCurricularV2DTO r=evaluar(c);
        assertTrue(r1.getPreseleccionada()); assertEquals(r1.getOrdenPreseleccion(),Integer.valueOf(1)); assertFalse(r1.getDesmarcable());
        assertTrue(decision(r,5L).getMotivos().contains(CodigoMotivoCurricularV2.RUTA_REGULARIZACION_NUNCA_INSCRITA));
        assertTrue(decision(r,6L).getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA));
        assertTrue(decision(r,6L).getMotivos().contains(CodigoMotivoCurricularV2.REZAGO_SEVERO_SOLO_REGULARIZACION));
    }
    @Test public void gc02SeriacionBloqueadaPrevaleceSobrePrioridad() {
        ContextoEvaluacionCurricularV2DTO c=base(3); UnidadEvaluacionCurricularV2DTO anterior=reprobada(1L,"OB2-1",2,1), consecuente=reprobada(2L,"OB3-1",3,1);
        RelacionSeriacionCurricularV2DTO rel=new RelacionSeriacionCurricularV2DTO();rel.setIdAntecedente(1L);rel.setIdConsecuente(2L);rel.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); consecuente.getRelacionesSeriacion().add(rel);
        c.getUnidades().add(anterior);c.getUnidades().add(consecuente); ResultadoEvaluacionCurricularV2DTO r=evaluar(c);
        assertTrue(anterior.getPreseleccionada()); assertFalse(consecuente.getPreseleccionada());
        assertTrue(decision(r,2L).getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA));
        assertTrue(decision(r,2L).getMotivos().contains(CodigoMotivoCurricularV2.SERIACION_DIRECTA));
    }
    @Test public void gc03CargaQuintoNoPuedeOmitirPendienteOfertada() {
        ContextoEvaluacionCurricularV2DTO c=base(5);c.setAvanceCondicionado(Boolean.TRUE); UnidadEvaluacionCurricularV2DTO pendiente=reprobada(1L,"OB2-2",2,1), quinto=pendiente(2L,"OB5-1",5,"Obligatoria");
        c.getUnidades().add(pendiente);c.getUnidades().add(quinto);c.getIdsUnidadesPretendidas().add(2L);
        ResultadoEvaluacionCurricularV2DTO r=evaluar(c); assertTrue(r.getRestricciones().contains(CodigoMotivoCurricularV2.PENDIENTE_OMITIDA_AVANCE_CONDICIONADO));
        c.getIdsUnidadesPretendidas().add(1L); r=evaluar(c); assertFalse(r.getRestricciones().contains(CodigoMotivoCurricularV2.PENDIENTE_OMITIDA_AVANCE_CONDICIONADO));
    }
    @Test public void gc03bFueraDeQuintoPermiteDesmarcarPreseleccion() { ContextoEvaluacionCurricularV2DTO c=base(3);UnidadEvaluacionCurricularV2DTO u=reprobada(1L,"OB2-2",2,1);c.getUnidades().add(u);c.getIdsUnidadesPretendidas().add(2L);evaluar(c);assertTrue(u.getPreseleccionada());assertTrue(u.getDesmarcable()); }
    @Test public void gc06Ygc07NoCambianReprobadaRequeridaPorSustituta() { ContextoEvaluacionCurricularV2DTO c=base(3);UnidadEvaluacionCurricularV2DTO ia=reprobada(1L,"IA",3,1);ia.setTipoUd("Optativa requerida");c.getUnidades().add(ia);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertTrue(decision(r,1L).getMotivos().contains(CodigoMotivoCurricularV2.OPTATIVA_REQUERIDA_SIN_SUSTITUCION)); }
    @Test public void gc10InterbloqueEsPreseleccionCondicionadaNoBaja() { ContextoEvaluacionCurricularV2DTO c=base(1);UnidadEvaluacionCurricularV2DTO b2=pendiente(1L,"OB1-4",1,"Obligatoria");b2.setCondicionInterbloque(Boolean.TRUE);c.getUnidades().add(b2);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertTrue(b2.getPreseleccionada());assertFalse(b2.getDesmarcable());assertTrue(decision(r,1L).getMotivos().contains(CodigoMotivoCurricularV2.PRESELECCION_CONDICIONADA_INTERBLOQUE)); }
    @Test public void gc16NuevoIngresoSinCatalogoConservaRevision() { ContextoEvaluacionCurricularV2DTO c=base(1);c.setAdmitido(Boolean.TRUE);c.setInscripcionInicialRealizada(Boolean.FALSE);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertTrue(r.getRestricciones().contains(CodigoMotivoCurricularV2.DATOS_INSUFICIENTES)); }
    @Test public void gc06RespetaCatalogoPrioridadSemestreYRegularidad() { ContextoEvaluacionCurricularV2DTO c=base(3);UnidadEvaluacionCurricularV2DTO s1=pendiente(1L,"USTAPREN",1,"Optativa"),s3=pendiente(2L,"BAIDOCUM",3,"Optativa");c.getUnidades().add(s1);c.getUnidades().add(s3);c.getUnidades().add(reprobada(3L,"OB2",2,1));c.getIdsUnidadesPretendidas().add(1L);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertTrue(decision(r,1L).getMotivos().contains(CodigoMotivoCurricularV2.OPTATIVA_OTRO_SEMESTRE_SOLO_REGULAR));c.getIdsUnidadesPretendidas().clear();c.getIdsUnidadesPretendidas().add(1L);c.getIdsUnidadesPretendidas().add(2L);c.getUnidades().remove(2);r=evaluar(c);assertFalse(r.getRestricciones().contains(CodigoMotivoCurricularV2.OPTATIVA_SEMESTRE_REFERENCIA_PRIMERO)); }
    @Test public void gc07bNoOfreceIaComoAdicionalTrasOchoRequeridas() { ContextoEvaluacionCurricularV2DTO c=base(3);for(int i=0;i<8;i++){UnidadEvaluacionCurricularV2DTO u=pendiente((long)i+1,"BAIDOCUM",3,"Optativa");u.setEstado(EstadoUnidadCurricularV2.ACREDITADA);c.getUnidades().add(u);}UnidadEvaluacionCurricularV2DTO ia=pendiente(20L,"INARTIFI",3,"Optativa");c.getUnidades().add(ia);c.getIdsUnidadesPretendidas().add(20L);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertTrue(decision(r,20L).getMotivos().contains(CodigoMotivoCurricularV2.OPTATIVA_LIMITE_SELECCION)); }
    @Test public void c10IncluyeNuncaInscritaYBajaParcialEnOmitidasDeQuinto() { ContextoEvaluacionCurricularV2DTO c=base(5);c.setAvanceCondicionado(Boolean.TRUE);UnidadEvaluacionCurricularV2DTO noCursada=pendiente(1L,"OB3-3",3,"Obligatoria"),baja=pendiente(2L,"OB4-1",4,"Obligatoria"),quinto=pendiente(3L,"OB5-1",5,"Obligatoria");baja.setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL);c.getUnidades().add(noCursada);c.getUnidades().add(baja);c.getUnidades().add(quinto);c.getIdsUnidadesPretendidas().add(3L);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertTrue(r.getRestricciones().contains(CodigoMotivoCurricularV2.PENDIENTE_OMITIDA_AVANCE_CONDICIONADO)); }
    @Test public void gc16ExigeDosOptativasDistintasEnBloquesDistintos() { ContextoEvaluacionCurricularV2DTO c=base(1);c.setAdmitido(Boolean.TRUE);c.setInscripcionInicialRealizada(Boolean.FALSE);for(int i=0;i<4;i++){UnidadEvaluacionCurricularV2DTO o=pendiente((long)i+1,"OB1-"+i,1,"Obligatoria");c.getUnidades().add(o);c.getIdsUnidadesPretendidas().add(o.getIdPrograma());}UnidadEvaluacionCurricularV2DTO b1=pendiente(10L,"USTAPREN",1,"Optativa"),b2=pendiente(11L,"RECVIRTU",1,"Optativa");b1.setBloque("1");b2.setBloque("2");c.getUnidades().add(b1);c.getUnidades().add(b2);c.getIdsUnidadesPretendidas().add(10L);c.getIdsUnidadesPretendidas().add(11L);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertFalse(r.getRestricciones().contains(CodigoMotivoCurricularV2.DATOS_INSUFICIENTES));b2.setBloque("1");r=evaluar(c);assertTrue(r.getRestricciones().contains(CodigoMotivoCurricularV2.DATOS_INSUFICIENTES)); }
    private ContextoEvaluacionCurricularV2DTO base(int referencia){ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO();c.setPeriodoNormativo("2027-2");c.setAmbitoRnPe2024_2027("APLICA");c.setSemestreReferencia(referencia);c.setFechaEvaluacion(new Date(0));c.setActividadAcademicaComprobada(Boolean.TRUE);c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);c.setSeriacionComprobada(Boolean.TRUE);c.setEstructuraProgramaEducativoComprobada(Boolean.TRUE);c.setSancionImpedimentoConocida(Boolean.TRUE);c.setSancionImpedimentoRegistrada(Boolean.FALSE);ParametrosCurricularesV2DTO p=new ParametrosCurricularesV2DTO();p.setUmbralRezagoSevero(4);c.setParametrosCurriculares(p);return c;}
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id,String clave,int semestre,int intentos){UnidadEvaluacionCurricularV2DTO u=pendiente(id,clave,semestre,"Obligatoria");u.setEstado(EstadoUnidadCurricularV2.REPROBADA);u.setCausa(CausaPendienteUnidadV2.REPROBACION);u.setReprobacionVigenteConocida(Boolean.TRUE);u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);for(int i=0;i<intentos;i++){IntentoAcreditacionUnidadV2DTO x=new IntentoAcreditacionUnidadV2DTO();x.setIdEjecucionTecnico(clave+i);x.setEstado(EstadoIntentoAcreditacionV2.REPROBADO);u.getIntentosAcreditacion().add(x);}return u;}
    private UnidadEvaluacionCurricularV2DTO pendiente(Long id,String clave,int semestre,String tipo){UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO();u.setIdPrograma(id);u.setClave(clave);u.setSemestre(semestre);u.setTipoUd(tipo);u.setEstado(EstadoUnidadCurricularV2.NO_CURSADA);u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO);u.setOfertada(Boolean.TRUE);u.setCupoDisponibleConocido(Boolean.TRUE);return u;}
    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO c){return new EvaluacionCurricularV2ServiceImpl().evaluarSombra(c);}
    private DecisionUnidadCurricularV2DTO decision(ResultadoEvaluacionCurricularV2DTO r,Long id){for(DecisionUnidadCurricularV2DTO d:r.getDecisiones())if(id.equals(d.getIdPrograma()))return d;return null;}
}
