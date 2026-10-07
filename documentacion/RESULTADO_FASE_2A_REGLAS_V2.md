# Resultado FASE 2A — RN-1.1 Inactividad

**Fecha:** 2026-10-06
**Rama:** `feature/reglas-negocio-pe2027`
**Versión normativa:** `PE2027-V1`

## FASE_IMPLEMENTADA

`FASE 2A` implementa la evaluación sombra de `RN-1.1 — De la inactividad`. La evaluación se mantiene separada de `situacionAcademica`: no reclasifica a la persona como regular o irregular, no cambia inscripciones, oferta, carga, selección, bloqueos, mapa ni UI.

## MODELO_IMPLEMENTADO

Se agregó `EstadoActividadAcademicaV2` con los estados autorizados: `ACTIVO`, `BAJA_TEMPORAL_VIGENTE`, `INACTIVO`, `REINCORPORACION_PENDIENTE`, `LIMITE_INACTIVIDAD_ALCANZADO` y `NO_DETERMINADO`.

`ContextoEvaluacionCurricularV2DTO` incorpora evidencia explícita de actividad: baja temporal total, baja parcial, reincorporación, baja definitiva, actividad comprobada, límite de inactividad, contradicción de datos, periodos autorizados/transcurridos y referencias técnicas. `ResultadoEvaluacionCurricularV2DTO` registra el estado de actividad sin sustituir la situación académica.

El adaptador sólo obtiene del contexto vigente evidencia de materias cursadas y bajas por UD. Una baja de UD se conserva como baja parcial; no se eleva a baja temporal total. El contexto vigente no expone de forma confiable duración autorizada, inicio, reincorporación ni límite institucional; por tanto, el motor retorna `NO_DETERMINADO` cuando esos datos son necesarios y no están presentes.

## EVALUACIÓN Y TRAZABILIDAD

`EvaluacionCurricularV2ServiceImpl` registra `RN_1_1` para cada evaluación sombra con versión, resultado, motivo, periodos y evidencias técnicas. Una baja parcial y el no cursamiento de una UD no generan inactividad. La baja temporal total sólo se clasifica como vigente, reincorporación pendiente o límite alcanzado cuando sus datos explícitos están disponibles. La baja definitiva registrada se representa como `INACTIVO`, sin mutar estado productivo.

RN-9.5 persiste esta traza como regla evaluada en la auditoría existente; no se creó ni ejecutó migración, porque la tabla de trazas ya admite código, resultado, detalle y versión.

## PRUEBAS

Se agregaron F2A-U-01 a F2A-U-14: estudiante activo; baja vigente y renovada; límite; reincorporación; ausencia de evidencia; concurrencia regular/irregular con baja temporal; baja parcial; no cursamiento; contradicción; determinismo e inmutabilidad.

La regresión F0, F1A–F1E y F2A ejecutó 74 pruebas TestNG: **74 exitosas, 0 fallas, 0 errores y 0 omitidas**. `basegestor` compiló como parte de Maven Test y `gestorWeb` compiló con Maven Clean Compile.

## RIESGOS

La fuente vigente no aporta una marca canónica de baja temporal total ni todos los periodos autorizados/transcurridos. El modelo permite recibirlos cuando una fuente posterior esté disponible, pero no los infiere a partir de ausencia de inscripción. La integración productiva y la automatización de revocación/reincorporación quedan fuera de FASE 2A.

`CAMBIO_PRODUCTIVO = NINGUNO`.
