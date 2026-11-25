# Resultado FASE 6C - Reglas V2

## Identificación

- `FASE_IMPLEMENTADA = FASE 6C`
- `REGLA = RN-6.6`
- `VERSION_NORMATIVA = PE2027-V1`
- `CONDICION = REINCORPORACION_TRAS_BAJA_TEMPORAL`
- `CAMBIO_PRODUCTIVO = NINGUNO`
- Fuente funcional: `documentacion/V2_REGLAS_APROBADAS.md`.
- Alcance futuro: Malla Curricular, Asistente y Simulador. No existe consumidor visible habilitado durante esta fase.

## Objetivo implementado

Se implementó, exclusivamente en sombra, la evaluación de reincorporación posterior a baja temporal formal. RN-6.6 conserva la trayectoria efectiva previa a la baja, identifica pendientes existentes y construye una ruta académica descriptiva sin ejecutar inscripción, reincorporación, asignación de grupos, reserva de cupo, cambios de oferta ni modificación de estados académicos reales.

## APLICABILIDAD

RN-6.6 requiere dos evidencias explícitas: `bajaTemporalFormalComprobada = true` y `haCursadoUnoOMasPeriodos = true`. Si la baja no es formal o no existe trayectoria previa, el resultado es `NO_APLICA`. Si cualquiera de las dos condiciones no puede comprobarse, se devuelve `REQUIERE_REVISION` y no se infiere una baja formal.

La interrupción sin baja formal permanece separada como RN-6.5. RN-6.2 continúa reservado para inscripción inicial diferida sin trayectoria previa.

## BAJA_FORMAL

El contrato V2 recibe la formalidad de baja como evidencia independiente de los estados de UD. La existencia de una baja por UD en una fuente vigente no se eleva automáticamente a baja temporal total formal para RN-6.6. Esto evita confundir baja parcial, interrupción no formal y baja temporal institucional.

## BAJA_VIGENTE_CONCLUIDA

La evaluación distingue `bajaTemporalConcluida` en tres estados:

- `true`: puede continuar la evaluación de reincorporación.
- `false`: devuelve `BAJA_TEMPORAL_AUN_VIGENTE` y no forma ruta ordinaria.
- `null`: devuelve `REQUIERE_REVISION`.

No se implementan mecanismos de conclusión anticipada ni workflows administrativos.

## SUSPENSION_AVANCE_CURRICULAR

RN-6.6 conserva el principio central de la fase: la baja temporal suspende el cómputo del avance curricular. El resultado registra el último periodo efectivamente cursado y el semestre efectivo previo a la baja, junto con el semestre objetivo proporcionado por la trayectoria. No calcula avance por fecha actual, periodos calendario transcurridos o duración de la baja.

Por tanto, los periodos incluidos entre el inicio y fin de la baja no generan por sí mismos UD `NO_CURSADA`, `NO_INSCRITA` ni `REPROBADA`.

## ULTIMO_PERIODO_EFECTIVAMENTE_CURSADO

La ruta exige evidencia de:

- último periodo efectivamente cursado;
- periodo de inicio y fin de baja;
- semestre efectivo previo a la baja;
- semestre objetivo curricular.

Si falta cualquiera de estos datos, RN-6.6 devuelve `REQUIERE_REVISION`. El motor no reemplaza trayectoria con calendario.

## UD_PENDIENTES

Las pendientes se reutilizan desde los estados normalizados V2 y mantienen su causa individual. Se distinguen `REPROBADA`, `NO_CURSADA`, `BAJA_PARCIAL`, `NO_INSCRITA`, `EN_CURSO`, `NO_DETERMINADA` y `ACREDITADA` cuando estén presentes. La condición de baja temporal pertenece a la trayectoria, no sustituye la causa académica de una UD pendiente.

Una UD `EN_CURSO` sin cierre institucional verificable genera revisión para esa UD. No se transforma automáticamente en reprobada.

## BAJA_TEMPORAL_NO_ES_REPROBACION

RN-6.6 no crea ni incrementa reprobaciones por la baja temporal. Sólo se usan reprobaciones vigentes respaldadas por el estado normalizado y su evidencia. Como resultado:

- la baja temporal no incrementa RN-6.4;
- las UD de periodos suspendidos no se agregan al contador de reprobadas;
- una reprobada previa real conserva su causa y efectos ya calculados;
- una baja parcial previa conserva su causa distinta.

## SEMESTRE_OBJETIVO

El semestre objetivo se consume desde la trayectoria curricular efectiva. Se registra junto con el semestre efectivo previo a la baja y no se deriva del número de periodos de baja. Si no existe un semestre objetivo verificable, la decisión es revisión.

## CALENDARIO_INSTITUCIONAL

El contexto V2 incorpora evidencia explícita de calendario y de que la reincorporación ocurre dentro de él. Si el calendario no es comprobable, se devuelve `REQUIERE_REVISION`; si está comprobado pero se encuentra fuera de calendario, también se conserva revisión y no se deduce un procedimiento ordinario o extraordinario.

## LIMITES_PERMANENCIA

La fase incorpora confirmación explícita de que el límite de permanencia fue evaluado. Si está excedido, el resultado es `NO_PROCEDE_POR_PERMANENCIA`. Si la información no está comprobada, devuelve `REQUIERE_REVISION`. No se introducen plazos institucionales ni se sustituyen contratos existentes de RN-1.1/permanencia.

## SERIACION

RN-6.6 reutiliza el motor V2 de seriación. Las UD posteriores se resuelven individualmente: una dependencia directa o indirecta las excluye; una relación no determinable queda en revisión; una UD sin dependencia puede ser compatible si carga, oferta/cupo y restricciones lo permiten. Una UD bloqueada no bloquea globalmente a otras compatibles.

## CARGA

La ruta consume la carga máxima ya calculada por RN-4.x. No recodifica cargas mínimas, máximas o excepciones. Si no existe máximo determinado, RN-6.6 no infiere una ruta. Las pendientes prioritarias consumen espacio antes de que se incorporen UD posteriores compatibles.

## RN_6_4

RN-6.4 conserva precedencia y usa únicamente reprobadas reales vigentes. Con cuatro o más reprobadas reales, la decisión RN-6.6 pasa a `REINCORPORACION_RESTRINGIDA_RN64` y no incorpora obligatorias posteriores que contradigan dicha restricción. La baja temporal, las no cursadas derivadas de la suspensión y las bajas parciales no elevan el contador.

## INTEGRACION_RN_6_1

RN-6.6 consume el resultado consolidado de RN-6.1 sin recalcular sus subreglas. Sólo marca dicho consumo cuando la infraestructura existente determina `CTR-01`, equivalente a semestre objetivo quinto y vigencia `2027-1` o posterior. Para otros semestres, o quinto previo a PE2027, no se consume RN-6.1 desde RN-6.6.

## RUTA_REGULARIZACION

La ruta sombra RN-6.6 tiene una sola secuencia:

1. UD obligatorias pendientes reales, ofertadas y con cupo;
2. UD posteriores no inscritas compatibles, sólo si queda carga disponible.

Cada UD registra inclusión, exclusión, oferta, cupo, seriación, carga, causa y regla. `SIN_OFERTA` y `SIN_CUPO` permanecen diferenciados. La fase reutiliza la prioridad ya existente para reprobadas y no transforma las demás pendientes en reprobación.

## DIFERENCIA_RN_6_5

RN-6.5 cubre una interrupción por no cursar el periodo inmediato siguiente sin baja formal. RN-6.6 cubre baja temporal formal concluida. Una baja temporal formal tiene prioridad de clasificación; RN-6.5 permanece `NO_APLICA` para ese contexto. Ambas reutilizan infraestructura V2, pero entregan condiciones, resultados y trazas separados.

## CONTRATO_RESULTADO_V2

`ContextoEvaluacionCurricularV2DTO` se extendió con evidencia de formalidad y conclusión de baja, periodos de baja, semestre efectivo previo, calendario y permanencia. `EvaluacionCargaAcademicaV2DTO` expone para RN-6.6:

- aplicabilidad y estado global;
- condición de trayectoria;
- inicio/fin de baja, último periodo efectivo y semestres efectivo/objetivo;
- estado de calendario;
- pendientes prioritarias, sin oferta y sin cupo;
- posteriores compatibles, bloqueadas o en revisión;
- ruta sombra y decisiones por UD;
- indicador de consumo de RN-6.1.

Estos campos preparan futuros consumidores de Malla, Asistente y Simulador, sin conectarlos durante FASE 6C.

## TRAZABILIDAD

RN-6.6 registra trazas V2 para aplicabilidad, formalidad, vigencia, trayectoria previa, permanencia, calendario, carga, oferta/cupo, seriación, RN-6.4, RN-6.1 y decisiones por UD. La evidencia incorpora la versión del contexto V2 y no agrega PII.

## PRUEBAS

Se añadió `EvaluacionCurricularV2Fase6CTest` con 15 escenarios, incluyendo:

- ausencia o incertidumbre de baja formal;
- trayectoria previa y baja vigente/concluida;
- suspensión de avance curricular y ausencia de salto por calendario;
- baja temporal sin reprobación/RN-6.4;
- tres y cuatro reprobadas reales;
- causas de pendientes y UD en curso sin cierre;
- diferencia entre oferta y cupo;
- seriación por UD;
- integración condicional RN-6.1;
- permanencia, calendario, carga, duplicados, determinismo y ausencia de efectos productivos.

| Ejecución | Tests run | Failures | Errors | Skipped |
| --- | ---: | ---: | ---: | ---: |
| FASE 6C | 15 | 0 | 0 | 0 |
| Regresión F0-F6C | 504 | 0 | 0 | 0 |

Compilación: `basegestor` SUCCESS y `gestorWeb` SUCCESS.

## RIESGOS

- Las fuentes adaptadas actualmente pueden reportar bajas por UD, pero no acreditan por sí mismas la formalidad total, conclusión, periodos, calendario ni permanencia requeridos por RN-6.6. Ante esa ausencia, la evaluación responde revisión.
- No se implementa un workflow administrativo de reincorporación ni se determinan plazos institucionales en código.
- La ruta depende de resultados previos de RN-4.x, seriación, RN-6.4 y RN-6.1; una futura integración debe conservar ese orden.
- Malla, Asistente y Simulador no consumen RN-6.6 en esta fase.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/CondicionCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoReincorporacionBajaTemporalV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase6CTest.java`
- `documentacion/RESULTADO_FASE_6C_REGLAS_V2.md`

## RESULTADO

FASE 6C implementa RN-6.6 exclusivamente en sombra. Preserva la baja temporal como suspensión de avance, mantiene las causas reales de pendientes, reutiliza los controles V2 existentes y sólo forma una ruta académica explicable cuando la evidencia es suficiente. No se iniciaron RN-6.7, RN-6.8 ni FASE 6D.
