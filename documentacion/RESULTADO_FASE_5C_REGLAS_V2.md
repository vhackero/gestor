# Resultado FASE 5C — RN-6.1.3

## FASE_IMPLEMENTADA Y REGLA

`FASE 5C` implementa `RN-6.1.3` en modo sombra para identificar supuestos tipificados de avance condicionado. Versión: `PE2027-V1`.

## SUPUESTOS_TIPIFICADOS

Se incorporaron códigos y trazas estructuradas para los siguientes supuestos, todos concurrentes:

- `UNA_OBLIGATORIA_PENDIENTE`: exactamente una UD obligatoria pendiente de la base RN-6.1.1.
- `REZAGO_LEVE`: reutiliza RN-5.1, sin volver a contar reprobadas.
- `BAJA_TEMPORAL`: requiere baja temporal y reincorporación formalmente registradas.
- `BAJA_PARCIAL`: conserva la causa sin convertirla en reprobación.
- `FALTA_OFERTA_SUFICIENTE`: sólo ante `SIN_OFERTA` verificable de una pendiente; `SIN_CUPO` no se convierte en oferta insuficiente y queda pendiente de la precisión de RN-6.1.4.
- `SIN_DEPENDENCIA_SERIACION`: por UD de quinto ya clasificada compatible por RN-6.1.2.

## CONCURRENCIA_DE_SUPUESTOS Y TRATAMIENTO_MAS_RESTRICTIVO

Los supuestos no se excluyen entre sí. RN-6.1.3 conserva todos los identificados aun si RN-6.1.2 no cumple o RN-6.4 prevalece. Las restricciones existentes siguen siendo la fuente de tratamiento: RN-6.4, seriación por UD, carga RN-4.2/RN-4.3, prioridad RN-6.3 y revisión por datos insuficientes. Esta fase no crea una jerarquía académica nueva.

## DEPENDENCIAS

- CTR-01/RN-6.1.1 aporta aplicabilidad y pendientes.
- RN-6.1.2 aporta condiciones A–G y el resultado por UD de quinto.
- RN-5.1 aporta REZAGO_LEVE; RN-6.4 mantiene precedencia impeditiva.
- RN-6.3, carga y seriación se consumen, sin recalcularse.
- CTR-02/RN-9.5 conservan las evidencias y trazas; no se crea una excepción extraordinaria ni una autorización.

## TRATAMIENTO_OFERTA_PENDIENTE_RN_6_1_4

La falta de oferta sólo se tipifica cuando la fuente ya indica inequívocamente `UD_NO_OFERTADA_PERIODO` o ausencia de oferta. Cupo y causas ambiguas no se reinterpretan y requieren la definición posterior de RN-6.1.4.

## SERIACION_POR_UD_QUINTO

La relación se conserva por UD candidata de quinto. Sólo las UD en la lista compatible de RN-6.1.2 reciben `SIN_DEPENDENCIA_SERIACION`; las bloqueadas directa o indirectamente no reciben ese supuesto. No se infiere que la ausencia de una relación conocida habilite otra UD fuera del resultado de seriación existente.

## TRATAMIENTO_SIN_SUPUESTO

Cuando no existe coincidencia válida, el resultado es `SIN_SUPUESTO_TIPIFICADO` o `REQUIERE_REVISION`. No se inventan supuestos y los casos permanecen disponibles para la fase posterior correspondiente.

## TRAZABILIDAD

Se agregó `RN_6_1_3` al catálogo de reglas, `SupuestoAvanceCondicionadoV2DTO`, sus códigos y el estado global de supuestos. La traza conserva resultado de RN-6.1.2, RN-6.4, causas, UD relacionadas y versión normativa. Un supuesto identificado no altera `avanceCondicionado` ni el comportamiento productivo.

## PRUEBAS

Se agregó `EvaluacionCurricularV2Fase5CTest` con 18 casos de aplicabilidad, supuesto único/múltiple, rezago leve/severo, baja temporal/parcial, oferta/cupo, seriación, concurrencia, deduplicación, determinismo y ausencia de efectos productivos.

- RN-6.1.3: 18 pruebas, 0 fallas, 0 errores.
- Regresión F0–F5C: 433 pruebas, 0 fallas, 0 errores.
- Compilación `basegestor`: exitosa.
- Compilación `gestorWeb`: exitosa.

## ARCHIVOS_MODIFICADOS

- `CodigoReglaCurricularV2.java`.
- `EvaluacionCargaAcademicaV2DTO.java`.
- `EvaluacionCurricularV2ServiceImpl.java`.

## ARCHIVOS_CREADOS

- `CodigoSupuestoAvanceCondicionadoV2.java`.
- `EstadoSupuestosAvanceV2.java`.
- `SupuestoAvanceCondicionadoV2DTO.java`.
- `EvaluacionCurricularV2Fase5CTest.java`.
- Este documento.

## RESULTADO

RN-6.1.3 identifica y conserva supuestos tipificados concurrentes en sombra. No implementa RN-6.1.4, RN-6.1.5 o RN-6.1.6 y no concede avance condicionado.

## RIESGOS

La relación detallada pendiente→prerrequisito depende de la información de seriación entregada al contexto. La integración futura debe conservar esa granularidad y proporcionar evidencia de disponibilidad institucional para cerrar RN-6.1.4.

## CAMBIO_PRODUCTIVO

`NINGUNO`.
