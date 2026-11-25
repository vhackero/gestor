# Resultado FASE 5D — RN-6.1.4 Falta de oferta

## FASE_IMPLEMENTADA Y REGLA

`FASE 5D` implementa `RN-6.1.4` como fuente única de clasificación de disponibilidad para cada UD pendiente de la base CTR-01, en modo sombra y versión `PE2027-V1`.

## CAUSAS_FALTA_OFERTA

La evaluación individual distingue:

- `NO_OFERTADA_EN_PERIODO`;
- `FUERA_DE_BLOQUE_VIGENTE`;
- `SIN_GRUPO_ACTIVO_DECISION_INSTITUCIONAL`;
- `OTRA_CONDICION_INSTITUCIONAL_OBJETIVA`;
- `SIN_CUPO_CAPACIDAD`;
- `DISPONIBLE`;
- `REQUIERE_REVISION`.

Cada resultado conserva UD, estado y causa curricular, causa de disponibilidad, banderas separadas de falta de oferta/cupo y evidencia normalizada.

## SIN_CUPO_NO_ES_FALTA_OFERTA

`SIN_CUPO_NO_ES_FALTA_OFERTA = true`. Una oferta con grupo activo y capacidad agotada se clasifica como `SIN_CUPO_CAPACIDAD`; no activa el supuesto `FALTA_OFERTA_SUFICIENTE` de RN-6.1.3.

## FUENTE_UNICA_CLASIFICACION_OFERTA E INTEGRACION_RN_6_1_3

RN-6.1.3 ya no clasifica directamente oferta/cupo. Consume las evaluaciones RN-6.1.4 y sólo registra `FALTA_OFERTA_SUFICIENTE` cuando la clasificación formal tiene `faltaOfertaRN614 = true`. Las clasificaciones ambiguas preservan revisión y no inventan falta de oferta.

## TRATAMIENTO_BLOQUE, GRUPO, CAPACIDAD E INCERTIDUMBRE

Bloque vigente, grupo activo institucional, otra indisponibilidad objetiva y cupo se distinguen usando `CausaIndisponibilidadOfertaV2`. Si falta evidencia verificable para oferta/grupo/cupo, el resultado es `REQUIERE_REVISION`; la ausencia de datos no se trata como ausencia de oferta.

## CONCURRENCIA, SERIACION Y DEPENDENCIAS

La indisponibilidad no modifica el estado curricular, no elimina la prioridad RN-6.3, no elimina la seriación y no desactiva RN-6.4. Una UD puede seguir `REPROBADA` o `BAJA_PARCIAL` y tener simultáneamente una clasificación RN-6.1.4. RN-6.1.4 no decide avance condicionado, RN-6.1.5 ni RN-6.1.6.

## TRAZABILIDAD

Se agregó `RN_6_1_4` al catálogo y el DTO por UD `EvaluacionOfertaPendienteRn614V2DTO`. La traza RN-9.5 conserva la clasificación por pendiente y su relación con RN-6.1.3, sin PII adicional.

## PRUEBAS

Se agregó `EvaluacionCurricularV2Fase5DTest` con 12 casos de no aplicabilidad, oferta disponible, cada causa institucional, cupo, integración RN-6.1.3, incertidumbre, conservación de estado y determinismo.

- FASE 5D: 12 pruebas, 0 fallas, 0 errores.
- Regresión F0–F5D: 445 pruebas, 0 fallas, 0 errores.
- Compilación `basegestor`: exitosa.
- Compilación `gestorWeb`: exitosa.

## ARCHIVOS_MODIFICADOS

- `CodigoReglaCurricularV2.java`.
- `EvaluacionCargaAcademicaV2DTO.java`.
- `EvaluacionCurricularV2ServiceImpl.java`.

## ARCHIVOS_CREADOS

- `EstadoOfertaPendienteRn614V2.java`.
- `EvaluacionOfertaPendienteRn614V2DTO.java`.
- `EvaluacionCurricularV2Fase5DTest.java`.
- Este documento.

## RESULTADO

RN-6.1.4 es la fuente formal y única de falta de oferta dentro de RN-6.1. No hay efectos productivos.

## RIESGOS

La clasificación depende de que los adaptadores futuros entreguen causas institucionales verificables; si no lo hacen, el resultado correcto permanece `REQUIERE_REVISION`.

## CAMBIO_PRODUCTIVO

`NINGUNO`.
