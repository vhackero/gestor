# Resultado de FASE 1B — RN-2.2 Evaluación previa a reinscripción

## FASE_IMPLEMENTADA

FASE 1B. La implementación opera exclusivamente sobre `ContextoEvaluacionCurricularV2DTO` y devuelve `ResultadoEvaluacionCurricularV2DTO` en modo sombra.

## REGLA_IMPLEMENTADA

**RN-2.2 — Evaluación previa a los procesos de reinscripción.**

La evaluación V2 construye una representación individual explicable antes de una reinscripción, a partir de información ya calculada por el contexto vigente. La salida contiene situación académica sombra, semestre objetivo existente en contexto, UD normalizadas, total de reprobadas, baja parcial, no cursamiento, seriación conocida, oferta origen, carga origen cuando proviene del asistente, condiciones concurrentes y trazas. La regla se registra como `RN_2_2` con resultado `EVALUACION_PREVIA_REINSCRIPCION_SOMBRA` y `aplicada=false`.

## Modelo de decisión por UD

`DecisionUnidadCurricularV2DTO` separa los valores fuente de la representación V2:

| Dato | Propósito |
| --- | --- |
| `ofertaOrigen` | Fotografía de la oferta entregada por el componente vigente. |
| `ofertaEvaluadaV2` | Oferta individual proyectada sólo en sombra. Una seriación directa puede marcarla como no habilitada únicamente dentro del resultado V2. |
| `seleccionableOrigen` y `bloqueadaOrigen` | Valores vigentes conservados para comparación; nunca se escriben ni sustituyen. |
| `estadosSombra` | Colección concurrente: `HABILITADA`, `BLOQUEADA`, `PRIORITARIA` y/o `REQUIERE_REVISION`. |
| `motivos`, `trazas`, `referenciasFuente`, `explicacionTecnica` | Explicación técnica, reglas relacionadas y evidencia disponible de la decisión. |

La evaluación no reduce una UD a un estado excluyente: una UD ofertada y reprobada puede ser simultáneamente `HABILITADA` y `PRIORITARIA`; una UD con seriación indirecta conserva la oferta sombra y además se marca `REQUIERE_REVISION`.

## Información integrada al contexto V2

El adaptador de sólo lectura incorpora, cuando la ruta se origina en `AsistenteInscripcionContextoDTO`:

- situación académica origen;
- total de reprobadas origen;
- carga mínima y máxima origen;
- oferta, seleccionabilidad y bloqueo origen por UD;
- referencias de los estatus, prioridad y datos de oferta fuente;
- distinción de seriación directa conocida (`bloqueada`) y relación de seriación indirecta conocida (`requiere` o `desbloquea`).

Los campos de procedencia futura de CTR-01 permanecen como contrato (`ctr01ProcedenciaFutura`) sin activar CTR-01 ni RN-6.1.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/UnidadEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/DecisionUnidadCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ResultadoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoDecisionUnidadCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/ContextoEvaluacionCurricularV2AdapterImpl.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`

## PRUEBAS_AGREGADAS

Archivo: `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase1BTest.java`.

| Prueba | Caso | Resultado |
| --- | --- | --- |
| F1B-U-01 | Regular sin pendientes. | Aprobada. |
| F1B-U-02 | Una reprobada. | Aprobada. |
| F1B-U-03 | Tres reprobadas y determinismo. | Aprobada. |
| F1B-U-04 | Cuatro o más reprobadas observadas sin activar RN-6.4. | Aprobada. |
| F1B-U-05 | Baja parcial. | Aprobada. |
| F1B-U-06 | UD no cursada. | Aprobada. |
| F1B-U-07 | Seriación directa. | Aprobada. |
| F1B-U-08 | Seriación indirecta. | Aprobada. |
| F1B-U-09 | UD sin oferta. | Aprobada. |
| F1B-U-10 | Múltiples condiciones, fuentes y explicación. | Aprobada. |

## PRUEBAS_EJECUTADAS

```bash
cd codigoFuente/basegestor
mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest test
```

Resultado: **31 pruebas ejecutadas, 0 fallas, 0 errores, 0 omitidas**.

También se compiló sin pruebas:

```bash
cd codigoFuente/basegestor && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
cd codigoFuente/gestorWeb && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
```

Resultado: ambos módulos compilaron correctamente.

## RESULTADO

RN-2.2 queda disponible para evaluación sombra, con trazabilidad por UD y resultado determinístico para el mismo contexto de entrada. La salida conserva la diferencia entre oferta origen y oferta evaluada V2, sin reemplazar ni escribir datos del motor vigente.

## RIESGOS

- La validación extremo a extremo real en DEV/QA continúa pendiente; estas pruebas no sustituyen el Gate de activación productiva V2.
- La seriación indirecta se identifica con las relaciones ya presentes en el DTO vigente; la determinación institucional definitiva de efectos permanece fuera de esta fase.
- Las cargas mínima y máxima sólo se propagan cuando el contexto procede del Asistente; la evaluación no las calcula ni altera.

## CAMBIO_PRODUCTIVO

**NINGUNO.** No se modificaron oferta real, seleccionabilidad, carga, selección, bloqueos productivos, Mapa Curricular, estado persistido, CTR-01, RN-6.1 ni RN-6.4. No se ejecutaron migraciones y no se inició FASE 1C.
