# Resultado Fase 9A - RN-9.1

FASE_IMPLEMENTADA = FASE 9A

REGLA = RN-9.1

VERSION_NORMATIVA = PE2027-V1

CAMBIO_PRODUCTIVO = NINGUNO

## REINSCRIPCION_EXTEMPORANEA

RN-9.1 se implementa exclusivamente en sombra como orquestación administrativa. El contexto V2 distingue `ORDINARIO`, `EXTEMPORANEO` y `NO_DETERMINADO` mediante `TipoProcesoAdministrativoV2`; esta dimensión es independiente de la trayectoria y de `TipoProcesoReincorporacionV2`.

## MOMENTO_ADMINISTRATIVO

El proceso extemporáneo únicamente describe el momento del registro. No crea una autorización global, no modifica selección real ni ejecuta reinscripción.

## MISMAS_REGLAS_ACADEMICAS

RN-9.1 consume resultados preexistentes de situación académica, RN-4.x, RN-6.3, RN-6.4, RN-6.1, FASE 6, FASE 7, FASE 8 y oferta/cupo. No recalcula ni reemplaza dichos motores.

## EXTEMPORANEO_NO_ES_EXCEPCION

Un proceso extemporáneo sin excepción no genera una excepción. Las excepciones existentes se leen desde CTR-02; el momento administrativo no altera su taxonomía.

## EXTEMPORANEO_NO_ES_FLEXIBILIZACION

El proceso extemporáneo no cambia REGULAR/IRREGULAR, no eleva ni reduce carga, no elimina pendientes, no modifica prioridad y no habilita una UD restringida por seriación, RN-6.4, oferta, cupo u otra regla concurrente.

## SITUACION_ACADEMICA

RN-1.x continúa determinando la situación. Las pruebas metamórficas ordinario/extemporáneo verifican igualdad de situación y total de reprobadas con el mismo contexto académico.

## CARGA

CARGA reutiliza RN-4.x y conserva los límites calculados. RN-9.1 no contiene valores hardcodeados ni agrega excepciones de carga.

## PRIORIDAD

PRIORIDAD reutiliza RN-6.3, rutas de FASE 6 y RN-7.1 cuando corresponde. Una UD nueva no desplaza una pendiente prioritaria por tratarse de un trámite extemporáneo.

## SERIACION

SERIACION reutiliza RN-8.1, RN-8.2 y RN-8.3. Una relación no cumplida permanece no cumplida en proceso extemporáneo; no existe una exención de seriación por momento administrativo.

## RN_6_4

Con cuatro o más reprobadas vigentes, RN-6.4 permanece aplicable en ordinario y extemporáneo. El proceso no constituye una excepción automática.

## RN_6_1

RN-6.1 conserva su semántica PE2027: solo aplica en objetivo S5 desde 2027-1. En S3 y S7 no aplica, incluso cuando el proceso sea extemporáneo.

## EXCEPCIONES_TIPIFICADAS

INTEGRACION_CTR_02 reutiliza las excepciones `TIPIFICADA` ya estructuradas. RN-9.1 las identifica como automatizables y no las envía automáticamente a valoración individual.

## CASOS_EXTRAORDINARIOS_NO_TIPIFICADOS

Una excepción `EXTRAORDINARIA` o con estado `REQUIERE_REVISION` activa `REQUIERE_VALORACION_INDIVIDUAL` y exige `RESOLUCION_DOCUMENTADA` en el resultado sombra. RN-9.1 no ejecuta ni inventa esa resolución.

## PRUEBAS_METAMORFICAS

Se comparan pares ordinario/extemporáneo para contextos regular, irregular, RN-6.4 activa, seriación no cumplida y objetivo S5. Las decisiones académicas se conservan; solo cambia la clasificación y traza administrativa RN-9.1.

## AUDITORIA_DUPLICACION

| Dependencia | Estado |
| --- | --- |
| Situación académica RN-1.x | REUTILIZADO |
| Carga RN-4.x | REUTILIZADO |
| Prioridad RN-6.3, FASE 6 y FASE 7 | REUTILIZADO |
| RN-6.4 y RN-6.1 | REUTILIZADO |
| Seriación FASE 8 | REUTILIZADO |
| Excepciones CTR-02 | REUTILIZADO |
| Clasificación administrativa RN-9.1 | ORQUESTADO |
| Trazabilidad RN-9.5 | REUTILIZADO, no reimplementado |

## CONTRATO_RESULTADO_V2

`ContextoEvaluacionCurricularV2DTO` incorpora el tipo y evidencia del proceso administrativo. `EvaluacionCargaAcademicaV2DTO` expone aplicabilidad RN-9.1, estado administrativo, excepción tipificada, valoración individual, resolución documentada requerida y decisiones sin crear DTO paralelo.

## TRAZABILIDAD_RN_9_5

La traza RN-9.1 registra tipo de proceso, evidencia, situación, semestre objetivo, carga, prioridades, seriación, RN-6.4, RN-6.1, excepción y estado administrativo. No se agrega PII.

## PRUEBAS

`EvaluacionCurricularV2Fase9ATest` agrega 13 pruebas: aplicabilidad ordinaria/extemporánea, datos insuficientes, pares metamórficos, RN-6.4, seriación, S5/S3/S7, CTR-02 tipificado, caso extraordinario, disponibilidad, determinismo, idempotencia y ausencia de efecto productivo.

## RIESGOS

La clasificación del proceso administrativo requiere evidencia canónica. Si no está disponible, RN-9.1 devuelve revisión propia sin inferir flexibilidad. RN-9.2, RN-9.3, RN-9.4 y FASE 9B continúan fuera de alcance.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/.../TipoProcesoAdministrativoV2.java`
- `codigoFuente/basegestor/.../EstadoReinscripcionExtemporaneaV2.java`
- `codigoFuente/basegestor/.../ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/.../EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/.../EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/.../EvaluacionCurricularV2Fase9ATest.java`
- `documentacion/RESULTADO_FASE_9A_REGLAS_V2.md`

## RESULTADO

RN-9.1 queda implementada en sombra: extemporáneo modifica únicamente el momento administrativo y conserva las mismas decisiones académicas del proceso ordinario.
