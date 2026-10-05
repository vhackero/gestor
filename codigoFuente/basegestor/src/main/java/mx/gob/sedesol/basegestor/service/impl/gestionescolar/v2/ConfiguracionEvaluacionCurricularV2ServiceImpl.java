package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2Service;

/**
 * Fuente temporal de configuración mientras la migración aditiva de FASE 0C no
 * sea aplicada. Los valores de sistema permiten verificación controlada sin
 * alterar catálogos existentes ni activar decisiones productivas.
 */
@Service("configuracionEvaluacionCurricularV2Service")
public class ConfiguracionEvaluacionCurricularV2ServiceImpl implements ConfiguracionEvaluacionCurricularV2Service {

    public static final String REGLAS_V2_VERSION = "PE2027-V1";
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
