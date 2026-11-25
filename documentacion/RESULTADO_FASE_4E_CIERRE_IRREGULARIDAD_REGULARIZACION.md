# Resultado FASE 4E — Cierre integral de irregularidad y regularización

## Estado

- `FASE_IMPLEMENTADA = FASE 4E`
- `REGLAS_VALIDADAS = RN-5.1, RN-5.2, RN-6.3, RN-6.4`
- `VERSIÓN_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`
- `SIGUIENTE_FASE = NO INICIADA`

FASE 4E no incorpora reglas ni altera el motor. Consolida por pruebas integradas que los resultados de irregularidad y regularización forman una sola interpretación para el mismo contexto V2, conservando el alcance futuro de Malla Curricular, Asistente y Simulador y excluyendo cualquier integración con inscripción/reinscripción.

## Matriz de consistencia

| Reprobadas vigentes | Situación RN-1.2 | RN-5.1 | RN-5.2 | Condición | RN-6.3 | RN-6.4 | Máximo RN-4.2 |
|---:|---|---|---|---|---|---|---:|
| 0 | Regular si no hay otra incertidumbre | No aplica | No aplica | No aplica | No aplica | No aplica | 8 cuando corresponde |
| 1–3 | Irregular | Aplica | No aplica | `REZAGO_LEVE` | Aplica | No aplica | 6 |
| 4+ | Irregular | No aplica | Aplica | `REZAGO_SEVERO` | Aplica | Aplica en sombra | 6 |
| No determinado | No determinada | No inferir | No inferir | Revisión requerida | No inferir | No inferir | No inferir |

La evidencia confirma que RN-5.1 y RN-5.2 son mutuamente excluyentes por el rango de reprobadas y que `REZAGO_LEVE` y `REZAGO_SEVERO` no coexisten en un mismo resultado.

## Transiciones verificadas

| Transición | Resultado validado |
|---|---|
| 3 → 4 | Se retira RN-5.1, se activa RN-5.2/RN-6.4 y queda `REZAGO_SEVERO`, sin residuo leve. |
| 4 → 3 | Se desactivan RN-5.2/RN-6.4, aplica RN-5.1 y queda `REZAGO_LEVE`; la situación continúa irregular. |
| 2 → 1 | Permanece RN-5.1 y condición leve. |
| 1 → 0 | RN-5.1 deja de aplicar; RN-1.3 sigue siendo responsable de la recuperación de regularidad. |
| 5 → 4 | La condición severa y RN-6.4 permanecen activas. |
| 4 → 0 | RN-6.4 deja de aplicar; no se atribuye a FASE 4 un cambio directo de regularidad. |

## Ruta única de regularización

RN-6.3 se verificó como la fuente común de reprobadas prioritarias, disponibilidad, falta de oferta/cupo, obligaciones compatibles, optativas y exclusiones. RN-5.1 y RN-5.2 sólo consumen esa ruta. FASE 4 no introdujo una lista alternativa de prioridades ni un contador paralelo de carga.

En rezago leve, las obligatorias compatibles se mantienen sujetas a trayectoria, seriación y carga. En rezago severo, RN-6.4 bloquea las obligatorias nuevas dentro de la propuesta sombra. Las optativas sólo se conservan después de las reprobadas prioritarias y cuando la capacidad/restricciones existentes lo permiten.

## Conteo y disponibilidad

Las pruebas integradas confirmaron que el umbral usa UD reprobadas vigentes únicas. No se incrementa por intentos RN-1.4, baja parcial, no cursada ni duplicados B1/B2. Una reprobada sin oferta o sin cupo sigue contando para la clasificación y la restricción; la indisponibilidad modifica la ruta, no la condición de reprobación.

La excepción de mínimo de RN-4.1 puede coexistir con rezago severo si no hay suficientes reprobadas disponibles. No elimina RN-6.4 ni habilita obligatorias nuevas para completar artificialmente el mínimo. El máximo continúa siendo el resultado de RN-4.2, con seis para situación irregular.

## Precedencia e incertidumbre

Para quinto semestre desde `2027-1`, si hay cuatro o más reprobadas y avance condicionado potencial, se verificó la prevalencia de RN-6.4 en sombra: `avanceCondicionadoBloqueadoPorRn64=true`. Para tercero y séptimo, CTR-01/RN-6.1 no procede. El Control de Avance completo no se implementó.

Cuando faltan datos de situación, reprobadas o identidad, el resultado conserva revisión requerida; no se infiere ni rezago leve ni severo.

## Contrato resultado V2

La revisión confirma que no se necesita un DTO paralelo. `ResultadoEvaluacionCurricularV2DTO` y `EvaluacionCargaAcademicaV2DTO` permiten identificar situación, conteo vigente, indicadores RN-5.1/RN-5.2/RN-6.3/RN-6.4, condición de regularización, ruta/prioridad, disponibilidad, causas, obligatorias restringidas, optativas, mínimo/máximo/carga integrada, excepciones, CTR-01, precedencia, incertidumbre, reglas, trazas y versión normativa.

Este contrato queda apto como fuente común futura para Malla, Asistente y Simulador, sin integrarlos durante esta fase.

## Trazabilidad RN-9.5

Las pruebas verifican que la cadena de reglas evaluadas permite reconstruir el flujo:

`RN-1.2 → RN-5.1/RN-5.2 → RN-6.3 → RN-6.4 → RN-4.3`.

Las decisiones por UD conservan prioridad, indisponibilidad, bloqueo sombra y trazas técnicas. Las trazas utilizan IDs/códigos técnicos y no agregan PII.

## Pruebas

`EvaluacionCurricularV2Fase4ETest` incorpora 30 escenarios integrados `F4E-I-01` a `F4E-I-30`, que cubren la matriz A–AD autorizada: rangos, transiciones, intentos, pendientes no reprobatorios, oferta/cupo, ruta única, obligaciones nuevas, optativas, mínimo, CTR-01, incertidumbre, duplicados, determinismo, trazabilidad y ausencia de efecto productivo.

Resultados finales de verificación:

| Comando | Resultado |
|---|---|
| `mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest,DiagnosticoE2EEvaluacionCurricularV2ServiceTest,EvaluacionCurricularV2Fase2ATest,EvaluacionCurricularV2Fase2BTest,EvaluacionCurricularV2Fase2CTest,EvaluacionCurricularV2Fase2DTest,EvaluacionCurricularV2Fase2ETest,EvaluacionCurricularV2Fase2FTest,EvaluacionCurricularV2Fase3ATest,EvaluacionCurricularV2Fase3BTest,EvaluacionCurricularV2Fase3CTest,EvaluacionCurricularV2Fase3DTest,EvaluacionCurricularV2Fase4ATest,EvaluacionCurricularV2Fase4BTest,EvaluacionCurricularV2Fase4CTest,EvaluacionCurricularV2Fase4DTest,EvaluacionCurricularV2Fase4ETest test` | 370 pruebas; 0 fallas; 0 errores; 0 omitidas. |
| Compilación `basegestor` | Exitosa mediante `mvn -q -DskipTests -Dmaven.test.skip=true clean compile`. |
| Compilación `gestorWeb` | Exitosa mediante `mvn -q -DskipTests -Dmaven.test.skip=true clean compile`. |

## Riesgos pendientes

1. V2 continúa sólo en modo sombra; cualquier integración visible requiere Gate E2E y autorización específica.
2. RN-6.1 completa y las fases posteriores no se iniciaron.
3. Los empates y datos insuficientes conservan revisión requerida por diseño.
