# Resultado FASE 6B - Reglas V2

## Identificación

- `FASE_IMPLEMENTADA = FASE 6B`
- `REGLA = RN-6.5`
- `VERSION_NORMATIVA = PE2027-V1`
- `CONDICION = REINCORPORACION_TRAS_PERIODO_NO_CURSADO`
- `CAMBIO_PRODUCTIVO = NINGUNO`
- Fuente funcional: `documentacion/V2_REGLAS_APROBADAS.md`.
- Alcance de integración futura: Malla Curricular, Asistente y Simulador. No existe consumidor visible habilitado en esta fase.

## Objetivo implementado

Se incorporó en modo sombra la evaluación de reincorporación para personas que ya cursaron al menos un periodo y después no cursaron el periodo inmediato siguiente. La evaluación determina aplicabilidad, continuidad de la trayectoria, pendientes, prioridad, compatibilidad curricular y restricciones sin ejecutar reinscripción, selección real, asignación de grupos, reserva de cupo ni cambios de oferta.

## APLICABILIDAD

RN-6.5 requiere evidencia explícita de `haCursadoUnoOMasPeriodos = true` y `noCursoPeriodoInmediatoSiguiente = true`. Si la persona nunca cursó, o si no hay interrupción inmediata, el resultado es `NO_APLICA`; el primer caso se mantiene separado para RN-6.2. Si faltan los datos de aplicabilidad, el motor no infiere la trayectoria y devuelve `REQUIERE_REVISION`.

Las interrupciones respaldadas por baja temporal total o baja parcial no se resuelven como RN-6.5: se conservan fuera del resultado ordinario para las fases RN-6.6 y RN-6.7, respectivamente.

## PERIODOS_NO_CURSADOS

El contexto V2 incorpora último periodo cursado, primer periodo omitido, lista de periodos omitidos y periodo de reincorporación. RN-6.5 exige que esta evidencia exista antes de formar una ruta. La lista se conserva sin sustituirla por una estimación calendárica y se expone junto con su cantidad en el resultado de carga.

Si no se puede determinar el último periodo cursado, los omitidos, el periodo de reincorporación o el semestre objetivo curricular, la decisión es `REQUIERE_REVISION`.

## UD_PENDIENTES

La evaluación reutiliza los estados normalizados V2. Distingue la condición pendiente de las causas `REPROBADA`, `NO_CURSADA`, `NO_INSCRITA`, `BAJA_PARCIAL`, `EN_CURSO`, `NO_DETERMINADA` y `ACREDITADA` cuando estén disponibles en el contexto normalizado.

## NO_CURSADA_NO_ES_REPROBADA

RN-6.5 no transforma una UD `NO_CURSADA` o `NO_INSCRITA` en reprobada. Por tanto, esas unidades pueden participar como pendientes obligatorias de reincorporación sin aumentar el conteo de reprobadas de RN-6.4. Una reprobada real previa conserva su estado y su tratamiento existente.

## PRIORIDAD_OBLIGATORIAS_PENDIENTES

Primero se evalúan las UD obligatorias pendientes correspondientes a la trayectoria. Una UD se prioriza únicamente cuando la oferta individual indica que está ofertada y con cupo. La implementación conserva tres resultados distintos:

- `SIN_OFERTA`: la UD permanece pendiente, no se agrega a la ruta.
- `SIN_CUPO`: la UD permanece pendiente y se conserva la causa de capacidad.
- `DISPONIBLE`: la UD puede entrar a la ruta sombra, respetando la carga disponible.

Los duplicados técnicos se deduplican por clave de UD antes de priorizar o construir decisiones.

## RUTA_REGULARIZACION

La ruta de reincorporación se construye en una sola colección sombra: primero pendientes obligatorias disponibles y después UD posteriores compatibles. Cada inclusión y exclusión se registra con su causa. La ruta no altera inscripciones ni oferta y no construye una variante paralela de RN-6.3 para reprobadas.

## UD_POSTERIORES_COMPATIBLES

Después de atender las pendientes prioritarias, el motor puede analizar candidatas posteriores no inscritas. La compatibilidad se decide por UD, por lo que una candidata bloqueada no impide agregar otra compatible. La decisión considera oferta/cupo, carga, RN-6.4 y seriación; las unidades sin evidencia suficiente quedan fuera de la ruta con decisión de revisión.

## SERIACION

RN-6.5 reutiliza la información de seriación normalizada V2. Una candidata posterior se excluye si existe dependencia directa o indirecta respecto de una pendiente. Si la relación es no determinable o la evidencia de seriación no está comprobada, se marca revisión para esa decisión, sin convertir una relación desconocida en compatible.

## CARGA

La fase consume el máximo de carga ya calculado por RN-4.x. No recalcula mínimos o máximos. Cuando no existe un máximo determinado, no construye una ruta presuntiva y devuelve revisión. Cuando se alcanza el máximo, no agrega UD posteriores.

## RN_6_4

RN-6.4 conserva precedencia. El resultado ya calculado se consulta sin cambiar su contador: cuatro o más reprobadas reales mantienen la restricción; `NO_CURSADA`, `NO_INSCRITA` y `BAJA_PARCIAL` no se convierten en reprobadas. Ante restricción RN-6.4, las candidatas obligatorias posteriores se excluyen conforme a esa decisión existente.

## SEMESTRE_OBJETIVO

RN-6.5 consume el semestre objetivo curricular del contexto V2. No lo deriva de calendario, reinscripción o número de periodos transcurridos. Si el valor no está determinado, se devuelve `REQUIERE_REVISION`.

## INTEGRACION_RN_6_1

RN-6.1 no se recalcula. RN-6.5 sólo consume la decisión consolidada existente cuando el semestre objetivo es quinto y el periodo normativo es `2027-1` o posterior. Para semestres distintos, o para quinto previo a la vigencia PE2027, RN-6.1 no se aplica desde RN-6.5.

## LIMITES_INACTIVIDAD

La fase requiere evidencia explícita de que el límite de inactividad fue comprobado. Si el límite está excedido, el resultado global es `NO_PROCEDE_POR_INACTIVIDAD`. Si no fue comprobado, se devuelve `REQUIERE_REVISION`. No se inventan plazos ni se configura una política administrativa nueva.

## PROCESO_ORDINARIO_EXTRAORDINARIO

El contrato de contexto y resultado conserva `ORDINARIO`, `EXTRAORDINARIO` o `NO_DETERMINADO`. La fase no deduce el tipo de proceso ni ejecuta un workflow de autorización.

## DIFERENCIA_RN_6_2

RN-6.2 trata inscripción inicial diferida de personas que nunca cursaron. RN-6.5 exige historial efectivo de al menos un periodo y una interrupción posterior. Los resultados y condiciones de trayectoria se mantienen separados.

## DIFERENCIA_RN_6_6

RN-6.6 corresponde a reincorporación posterior a baja temporal formal. Cuando se detecta baja temporal formal, RN-6.5 no emite una reincorporación ordinaria; RN-6.6 no se implementó en esta fase.

## CONTRATO_RESULTADO_V2

`ContextoEvaluacionCurricularV2DTO` recibe evidencia de trayectoria e inactividad para RN-6.5. `EvaluacionCargaAcademicaV2DTO` expone:

- aplicabilidad y estado global RN-6.5;
- condición de trayectoria, periodos cursados/omitidos y reincorporación;
- causa de interrupción, tipo de proceso e inactividad;
- pendientes prioritarias, sin oferta y sin cupo;
- posteriores compatibles, bloqueadas o en revisión;
- ruta sombra y decisiones por UD;
- indicador de consumo de RN-6.1.

Los contratos son consumibles por futuras integraciones de Malla, Asistente y Simulador, sin conectarlos en esta fase.

## TRAZABILIDAD

La evaluación registra trazas y decisiones V2 con regla, evidencia y causa: aplicabilidad, condición de trayectoria, periodos, interrupción, inactividad, tipo de proceso, oferta/cupo, prioridad, seriación, carga, RN-6.4, semestre objetivo y consumo de RN-6.1. No incorpora PII adicional.

## PRUEBAS

Se agregó `EvaluacionCurricularV2Fase6BTest` con 15 escenarios que cubren, entre otros:

- separación de RN-6.2, RN-6.5, baja temporal y baja parcial;
- interrupción y datos insuficientes;
- varios periodos omitidos y proceso extraordinario;
- preservación de `NO_CURSADA` y `NO_INSCRITA` sin convertirlas en reprobadas;
- prioridad de obligatorias, diferencia entre oferta y cupo;
- seriación directa, evidencia de seriación insuficiente y compatibilidad individual;
- precedencia RN-6.4;
- consumo condicional de RN-6.1;
- inactividad, carga máxima, duplicados y determinismo.

Resultados ejecutados:

| Ejecución | Tests run | Failures | Errors | Skipped |
| --- | ---: | ---: | ---: | ---: |
| FASE 6B | 15 | 0 | 0 | 0 |
| Regresión F0-F6B | 489 | 0 | 0 | 0 |

Compilación: `basegestor` SUCCESS y `gestorWeb` SUCCESS.

## RIESGOS

- La clasificación depende de que el adaptador de datos proporcione evidencia explícita de trayectoria, causa de interrupción, periodos e inactividad. Sin ella, el resultado es revisión y no una ruta inferida.
- No se implementa el workflow administrativo ordinario/extraordinario ni se determina el límite normativo por sí mismo.
- La fase consume resultados de carga, seriación, RN-6.4 y RN-6.1; una integración futura debe preservar ese orden de evaluación.
- No hay consumidor visible en Malla, Asistente ni Simulador durante el modo sombra.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/CondicionCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoReincorporacionPeriodoNoCursadoV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/TipoProcesoReincorporacionV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase6BTest.java`
- `documentacion/RESULTADO_FASE_6B_REGLAS_V2.md`

## RESULTADO

FASE 6B implementa RN-6.5 exclusivamente en sombra. El motor conserva la distinción entre periodos no cursados y reprobación, prioriza obligatorias pendientes ofertadas, incorpora posteriores sólo cuando son compatibles y registra decisiones trazables. No se inició FASE 6C ni se implementaron RN-6.6, RN-6.7 o RN-6.8.
