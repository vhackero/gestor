# Resultado FASE 5B — RN-6.1.2

## FASE_IMPLEMENTADA Y REGLA

`FASE 5B` — `RN-6.1.2`, condiciones comunes para continuar a una futura evaluación de avance condicionado. Versión normativa: `PE2027-V1`.

## EVALUACION_CONCURRENTE

RN-6.1.2 sólo se ejecuta cuando CTR-01 aplica y RN-6.1.1 dejó obligatorias previas pendientes. Las condiciones A–G se almacenan individualmente en `CondicionComunAvanceV2DTO`; no se corta la evaluación por la primera condición adversa. El estado integrado puede ser `NO_APLICA`, `NO_CUMPLE_CONDICIONES_COMUNES`, `REQUIERE_REVISION`, `PENDIENTE_TIPIFICACION` o `CUMPLE_CONDICIONES_COMUNES`.

`PENDIENTE_TIPIFICACION` significa que las precondiciones verificables están registradas, pero RN-6.1.3 aún debe identificar un supuesto tipificado. No equivale a `AVANCE_CONDICIONADO_PROCEDE`.

## CONDICIONES_A_G

- A. Consume RN-6.4. Cuatro o más reprobadas vigentes produce `NO_CUMPLE`; no se recalcula el conteo.
- B. Evalúa por UD de quinto la seriación ya normalizada: compatibles, bloqueadas por seriación directa, bloqueadas por indirecta o revisión. No bloquea automáticamente todas las UD de quinto por una pendiente ajena.
- C. Consume carga evaluada y máximo de RN-4.2/RN-4.3; no calcula máximos nuevos.
- D. Reutiliza la ruta RN-6.3 y conserva sus causas de oferta/cupo. Si no existe ruta verificable, solicita revisión.
- E. Requiere una confirmación verificable de compatibilidad de la estructura del PE; la ausencia de fuente no se interpreta como compatible.
- F. Conserva la causa de la pendiente y expresa `PENDIENTE_TIPIFICACION_RN_6_1_3`; no anticipa excepciones de RN-6.1.3.
- G. Distingue sanción conocida sin impedimento, sanción impeditiva y fuente no disponible. La ausencia de información produce revisión, no ausencia de sanción.

## DEPENDENCIAS

- `RN-6.1.1`: fuente de aplicabilidad, base y pendientes; no se reconstruye.
- `RN-6.4`: precedencia sobre avance condicionado preservada.
- Seriación existente por UD de quinto: no se añadió un motor paralelo.
- `RN-4.2` y `RN-4.3`: fuente de máximo y carga.
- `RN-6.3`: única ruta de prioridad de regularización.
- `CTR-02` y `RN-9.5`: la causa y trazas V2 existentes se conservan; FASE 5B no crea una excepción tipificada nueva.

## RESULTADO_POR_UD_QUINTO

El contrato de carga V2 ahora conserva listas separadas de UD de quinto compatibles, bloqueadas por seriación directa, bloqueadas por indirecta y con seriación que requiere revisión. Las decisiones siguen siendo de sombra y no alteran oferta, selección, cupo, carga ni inscripción.

## TRATAMIENTO_SANCIONES Y RIESGO

Se agregaron campos opcionales de sólo lectura al contexto para expresar si una fuente canónica conoce una sanción impeditiva y si la estructura del PE está comprobada. Los adaptadores actuales no inventan estos datos: si no los suministran, RN-6.1.2 resulta `REQUIERE_REVISION`. La integración futura deberá identificar la fuente institucional de sanciones y compatibilidad curricular.

## TRAZABILIDAD

Se añadió `RN_6_1_2` al catálogo de reglas. Las condiciones, resultados, UD relacionadas y evidencia quedan en el resultado V2 y la traza RN-9.5 con la versión normativa; no se persiste PII adicional.

## PRUEBAS

Se agregó `EvaluacionCurricularV2Fase5BTest` con 20 escenarios que cubren no aplicabilidad, RN-6.4, seriación directa/indirecta y mixta, carga, prioridad, oferta, estructura PE, tipificación pendiente, sanciones, determinismo e inmutabilidad.

- Pruebas específicas F5A–F5B: 45 ejecutadas, 0 fallas, 0 errores.
- Regresión completa basegestor F0–F5B: 415 ejecutadas, 0 fallas, 0 errores.
- Compilación basegestor: exitosa.
- Compilación gestorWeb: exitosa.

## ARCHIVOS_MODIFICADOS

- `ContextoEvaluacionCurricularV2DTO.java`.
- `EvaluacionCargaAcademicaV2DTO.java`.
- `CodigoReglaCurricularV2.java`.
- `CodigoMotivoCurricularV2.java`.
- `EvaluacionCurricularV2ServiceImpl.java`.
- `EvaluacionCurricularV2Fase5ATest.java` (la delegación bloqueada por RN-6.4 queda disponible para trazado concurrente en F5B, sin conceder avance).

## ARCHIVOS_CREADOS

- `EstadoCondicionAvanceV2.java`.
- `EstadoCondicionesComunesAvanceV2.java`.
- `CondicionComunAvanceV2DTO.java`.
- `EvaluacionCurricularV2Fase5BTest.java`.
- Este documento.

## RESULTADO

RN-6.1.2 quedó implementada exclusivamente en modo sombra. No implementa RN-6.1.3, RN-6.1.4, RN-6.1.5 ni RN-6.1.6; no concede avance condicionado final.

## CAMBIO_PRODUCTIVO

`NINGUNO`.
