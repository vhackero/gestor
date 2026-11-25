# Resultado FASE 6D - Reglas V2

## Identificación

- `FASE_IMPLEMENTADA = FASE 6D`
- `REGLA = RN-6.7`
- `VERSION_NORMATIVA = PE2027-V1`
- `CONDICION = REINCORPORACION_TRAS_BAJA_PARCIAL`
- `CAMBIO_PRODUCTIVO = NINGUNO`
- Fuente funcional: `documentacion/V2_REGLAS_APROBADAS.md`.

## APLICABILIDAD

RN-6.7 se evalúa cuando el contexto normalizado contiene al menos una UD con estado o causa `BAJA_PARCIAL`. Una baja temporal total se conserva como condición RN-6.6 y no se reclasifica como baja parcial. Si no existen UD con baja parcial, el resultado es `NO_APLICA`.

Cada UD requiere evidencia explícita de formalidad. Una causa, formalidad, tipo, oferta, cupo, sanción o requisito optativo no determinable se registra como `REQUIERE_REVISION` para esa UD; el motor no rellena esos datos por inferencia.

## BAJA_PARCIAL_NO_ES_REPROBACION

RN-6.7 conserva `BAJA_PARCIAL` como causa propia. No la transforma en `REPROBADA`, no incrementa intentos, no modifica el conteo de reprobadas vigentes y no activa por sí misma RN-5.1, RN-5.2 o RN-6.4.

## BAJA_PARCIAL_NO_GENERA_IRREGULARIDAD_POR_SI_MISMA

La situación académica se conserva desde RN-1.2. Una persona con una baja parcial y cero reprobadas permanece `REGULAR`; si existen reprobadas reales, conserva el resultado de situación previamente calculado. RN-6.7 no recalcula esa clasificación.

## A_OBLIGATORIA_OFERTADA

Una UD obligatoria con baja parcial formal, sin sanción impeditiva, ofertada y con cupo queda como `PRIORITARIA_POR_BAJA_PARCIAL`. Puede agregarse a la ruta sombra y después complementarse con UD posteriores compatibles, dentro de la carga disponible.

## B_OBLIGATORIA_NO_OFERTADA

Una obligatoria con baja parcial y `SIN_OFERTA` conserva su causa y disponibilidad, pero no bloquea automáticamente toda la ruta. El motor puede evaluar otras UD compatibles. Cuando el objetivo es quinto y opera CTR-01, RN-6.7 consume la decisión consolidada de RN-6.1 sin reimplementar sus subreglas de falta de oferta.

## C_OPTATIVA_OFERTADA

Una optativa con baja parcial y oferta vigente sólo recibe prioridad frente a nuevas optativas cuando `necesariaParaRequerimientoCurricular = true`. Si es falsa no se le asigna prioridad obligatoria; si es desconocida se solicita revisión. Una optativa no bloquea por sí misma obligatorias elegibles.

## D_OPTATIVA_NO_OFERTADA

Una optativa con baja parcial y `SIN_OFERTA` se conserva en el resultado como pendiente no disponible. La ruta puede continuar evaluando obligatorias y otras optativas elegibles, siempre que carga, seriación y restricciones lo permitan.

## PRIORIDAD

RN-6.7 registra prioridades procedentes de bajas parciales sin reutilizar semánticamente RN-6.3. Las reprobadas permanecen en RN-6.3. Si coinciden prioridades de ambas reglas y el orden relativo afecta la selección, RN-6.7 conserva ambas condiciones y devuelve `REQUIERE_REVISION`; no inventa un desempate normativo.

## OFERTA_VS_CUPO

El resultado distingue `SIN_OFERTA` de `SIN_CUPO`. Una UD ofertada sin cupo no se procesa como falta de oferta. La fase no altera capacidad, grupos, oferta ni reservas.

## OPTATIVAS_Y_REQUERIMIENTO_CURRICULAR

La necesidad de recuperar una optativa se recibe como evidencia por UD. RN-6.7 no presupone que una optativa antes seleccionada sea obligatoria para el requisito curricular y no incorpora reglas nuevas de equivalencia o créditos del PE.

## SANCIONES

Las bajas parciales derivadas de sanción se registran por UD. Sin resolución verificable se devuelve revisión. Si la resolución no permite flexibilización, el estado global es `RESTRINGIDA_POR_SANCION`; no se habilita recuperación automática. Si la resolución permite flexibilidad, el flujo continúa bajo las demás reglas.

## SERIACION

Se reutiliza la seriación V2. Una posterior con dependencia directa o indirecta queda bloqueada; una sin dependencia continúa su evaluación. La afectación es individual por UD, no global.

## CARGA

RN-6.7 consume la carga máxima calculada por RN-4.x. No codifica límites nuevos y no reduce carga por una baja parcial aislada. Las prioridades se procesan antes de las UD posteriores compatibles.

## RN_6_3

RN-6.3 mantiene la prioridad de reprobadas. RN-6.7 mantiene la prioridad de bajas parciales. Ambas reglas se preservan en las decisiones y trazas V2; si no existe un orden aprobado entre ambas categorías, el motor no crea una jerarquía artificial.

## RN_6_4

RN-6.4 conserva precedencia y usa exclusivamente reprobadas reales vigentes. Tres reprobadas más una baja parcial no activan RN-6.4; cuatro reprobadas reales sí. Ante RN-6.4 aplicable, el resultado RN-6.7 queda `REINCORPORACION_RESTRINGIDA_RN64` y no incorpora obligatorias posteriores incompatibles con esa restricción.

## INTEGRACION_RN_6_1

RN-6.7 consume el control consolidado RN-6.1 únicamente cuando CTR-01 aplica, lo que el motor existente determina para semestre objetivo quinto desde `2027-1`. No recalcula RN-6.1.1 a RN-6.1.6 dentro de esta fase.

## CONCURRENCIA

El resultado conserva la concurrencia CTR-04: reprobadas, bajas parciales, prioridad, oferta, cupo, sanción, seriación, carga y restricciones se mantienen como condiciones separadas. No se reduce el caso a una única categoría académica.

## CONTRATO_RESULTADO_V2

Se añadieron al DTO de UD los indicadores de formalidad de baja parcial, sanción, resolución y necesidad de requisito optativo. `EvaluacionCargaAcademicaV2DTO` expone aplicabilidad, estado, bajas parciales, prioridades, indisponibilidades, sanciones, posteriores compatibles/bloqueadas, ruta, decisiones por UD y consumo de RN-6.1. Los campos permanecen sin consumidor UI durante FASE 6D.

## TRAZABILIDAD

RN-6.7 registra regla, condición, tipo de UD, formalidad, oferta/cupo, requisito optativo, sanción/resolución, prioridad, seriación, carga, RN-6.3, RN-6.4, RN-6.1, ruta y decisión por UD. La evidencia se conserva sin PII adicional.

## PRUEBAS

Se agregó `EvaluacionCurricularV2Fase6DTest` con 16 escenarios. Cubre los cuatro escenarios normativos, no equivalencia con reprobación, situación regular, formalidad, oferta/cupo, optativas necesarias/no necesarias, electivas, sanciones, RN-6.4, seriación, RN-6.1, concurrencia con RN-6.3, duplicados, determinismo y ausencia de efecto productivo.

| Ejecución | Tests run | Failures | Errors | Skipped |
| --- | ---: | ---: | ---: | ---: |
| FASE 6D | 16 | 0 | 0 | 0 |
| Regresión F0-F6D | 520 | 0 | 0 | 0 |

Compilación: `basegestor` SUCCESS y `gestorWeb` SUCCESS.

## RIESGOS

- El adaptador actual reconoce bajas desde fuentes de inscripción, pero no aporta todavía evidencia por UD de formalidad, sanción, resolución ni requisito optativo. En esos casos RN-6.7 conserva revisión en modo sombra.
- No se implementa workflow administrativo, equivalencias optativas ni reglas nuevas de créditos.
- No existen consumidores activos en Malla, Asistente o Simulador para el contrato RN-6.7.
- La composición RN-6.3/RN-6.7 requiere un criterio normativo adicional si ambas prioridades compiten por carga limitada.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/CondicionCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoReincorporacionBajaParcialV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/UnidadEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase6DTest.java`
- `documentacion/RESULTADO_FASE_6D_REGLAS_V2.md`

## RESULTADO

FASE 6D implementa RN-6.7 exclusivamente en sombra. La baja parcial permanece diferenciada de reprobación y baja temporal, se prioriza conforme a su tipo y evidencia, y se integra con carga, seriación, RN-6.3, RN-6.4 y RN-6.1 sin efectos productivos. No se inició RN-6.8 ni FASE 6E.
