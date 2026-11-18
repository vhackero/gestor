package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ParametrosCurricularesV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2Service;

/**
 * Fuente temporal de configuración mientras la migración aditiva de FASE 0C no
 * sea aplicada. Los valores de sistema permiten verificación controlada sin
 * alterar catálogos existentes ni activar decisiones productivas.
 */
@Service("configuracionEvaluacionCurricularV2Service")
public class ConfiguracionEvaluacionCurricularV2ServiceImpl implements ConfiguracionEvaluacionCurricularV2Service {

    public static final String REGLAS_V2_VERSION = "RN-PE2024-2027-act-20261117";
    public static final String CTR01_PERIODO_INICIO = "2027-1";
    public static final Integer CTR01_SEMESTRE_OBJETIVO = Integer.valueOf(5);

    @Override
    public ConfiguracionEvaluacionCurricularV2DTO obtenerConfiguracion() {
        ConfiguracionEvaluacionCurricularV2DTO configuracion = new ConfiguracionEvaluacionCurricularV2DTO();
        configuracion.setVersionReglas(obtenerPropiedad("REGLAS_V2_VERSION", REGLAS_V2_VERSION));
        configuracion.setEvaluacionV2Habilitada(obtenerBooleano("EVALUACION_V2_HABILITADA", true));
        configuracion.setModoSombra(obtenerBooleano("REGLAS_V2_MODO_SOMBRA", true));
        configuracion.setCtr01PeriodoInicio(obtenerPropiedad("CTR01_PERIODO_INICIO", CTR01_PERIODO_INICIO));
        configuracion.setCtr01SemestreObjetivo(obtenerEntero("CTR01_SEMESTRE_OBJETIVO", CTR01_SEMESTRE_OBJETIVO));
        ParametrosCurricularesV2DTO parametros = new ParametrosCurricularesV2DTO();
        parametros.setCalificacionMinimaAprobatoria(obtenerEntero("V2_CALIFICACION_MINIMA_APROBATORIA", Integer.valueOf(60)));
        parametros.setOportunidadesPorUd(obtenerEntero("V2_OPORTUNIDADES_POR_UD", Integer.valueOf(3)));
        parametros.setCargaMinima(obtenerEntero("V2_CARGA_MINIMA", Integer.valueOf(3)));
        parametros.setCargaMaxRegular(obtenerEntero("V2_CARGA_MAX_REGULAR", Integer.valueOf(8)));
        parametros.setCargaMaxIrregular(obtenerEntero("V2_CARGA_MAX_IRREGULAR", Integer.valueOf(6)));
        parametros.setUmbralRezagoSevero(obtenerEntero("V2_UMBRAL_REZAGO_SEVERO", Integer.valueOf(4)));
        parametros.setMaxSemestresBajaTemporal(obtenerEntero("V2_MAX_SEMESTRES_BAJA_TEMPORAL", Integer.valueOf(3)));
        parametros.setOptativasRequeridas(obtenerEntero("V2_OPTATIVAS_REQUERIDAS", Integer.valueOf(8)));
        parametros.setCtr01PeriodoInicio(configuracion.getCtr01PeriodoInicio());
        parametros.setCtr01Semestre(configuracion.getCtr01SemestreObjetivo());
        parametros.setDocumentoRespaldo("P2024-2027- Reglas de negocio_Octubre 2026_20261117.docx");
        parametros.setVigencia("2026-11-17");
        parametros.setAmbito(obtenerPropiedad("V2_CATALOGO_OPTATIVAS_AMBITO", "PE2024/2027"));
        parametros.setCatalogoOptativas(obtenerPropiedad("V2_CATALOGO_OPTATIVAS", "USTAPREN:1,RECVIRTU:1,PRINVEST:1,PGHRATRS:2,INAVIRTU:2,ANDHUMAN:2,BAIDOCUM:3,BAICAMPO:3,REACADEM:3,INARTIFI:3,PECRITIC:4,HIMEXICO:4,MECMUNDI:4"));
        configuracion.setParametrosCurriculares(parametros);
        return configuracion;
    }

    private String obtenerPropiedad(String nombre, String valorPredeterminado) {
        String valor = System.getProperty(nombre);
        return valor == null || valor.trim().isEmpty() ? valorPredeterminado : valor.trim();
    }

    private Boolean obtenerBooleano(String nombre, Boolean valorPredeterminado) {
        String valor = System.getProperty(nombre);
        return valor == null || valor.trim().isEmpty() ? valorPredeterminado : Boolean.valueOf(valor.trim());
    }

    private Integer obtenerEntero(String nombre, Integer valorPredeterminado) {
        String valor = System.getProperty(nombre);
        if (valor == null || valor.trim().isEmpty()) return valorPredeterminado;
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            return valorPredeterminado;
        }
    }
}
