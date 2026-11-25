# Resultado FASE 3D — Cierre integral de carga académica V2

## Estado de la fase

- `FASE_IMPLEMENTADA = FASE 3D`
- `REGLAS_VALIDADAS = RN-4.1, RN-4.2, RN-4.3`
- `VERSIÓN_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`
- `SIGUIENTE_FASE = NO INICIADA`

La FASE 3D no incorpora una regla académica nueva ni modifica el motor vigente. Su finalidad es comprobar que la evaluación integrada de carga reutiliza los resultados de carga mínima y máxima ya producidos por el mismo `ContextoEvaluacionCurricularV2DTO`, y que el resultado consolidado puede servir como contrato futuro para Malla Curricular, Asistente y Simulador. La inscripción/reinscripción y sus validadores permanecen fuera del alcance de integración del Motor V2, conforme a `documentacion/ALCANCE_MOTOR_REGLAS_V2.md`.

## Componentes verificados

| Componente | Responsabilidad comprobada | Evidencia |
|---|---|---|
| `EvaluacionCurricularV2ServiceImpl` | Ejecuta en una misma evaluación RN-1.2, RN-2.2, RN-4.1, RN-4.2, CTR-02 y RN-4.3; el resultado permanece en sombra. | `evaluarSombra`, `evaluarCargaMinima`, `evaluarCargaMaxima`, `evaluarExcepcionesTipificadas`, `evaluarCargaAcademicaIntegrada` |
| `EvaluacionCargaMinimaCurricularV2DTO` | Conserva el mínimo normativo, elegibilidad, descarte y excepción tipificada de mínimo. | Contrato V2 existente de FASE 3A |
| `EvaluacionCargaMaximaCurricularV2DTO` | Conserva situación usada, máximo, prioridades, inclusiones y exclusiones por máximo. | Contrato V2 existente de FASE 3B |
| `EvaluacionCargaAcademicaV2DTO` | Consolida mínimo, máximo, carga evaluada, prioridades, exclusiones, evidencias, RN-6.1 y RN-6.4 observacionales. | Contrato V2 existente de FASE 3C |
| `ResultadoEvaluacionCurricularV2DTO` | Expone la situación académica, decisiones por UD, excepciones, reglas, trazas y la evaluación integrada. | Contrato único de resultado V2 |
| `TrazabilidadEvaluacionCurricularV2ServiceImpl` | La trazabilidad RN-9.5 persiste genéricamente las trazas y decisiones contenidas en el resultado V2. | Pruebas RN-9.5 existentes; F3D verifica la presencia de la cadena de reglas en el resultado generado |

## Matriz de consistencia

| Invariante | Resultado de F3D | Evidencia de prueba |
|---|---|---|
| Mínimo posterior al primer semestre = 3 UD | Se conserva como valor normativo en RN-4.1. | F3D-U-01, 07, 08, 09, 10 |
| Regular = máximo 8 | RN-4.2 reutiliza RN-1.2 sin recalcular situación. | F3D-U-02, 03 |
| Irregular = máximo 6 | RN-4.2 reutiliza la reprobación vigente determinada por RN-1.2. | F3D-U-04, 05, 06, 20 |
| Situación no determinada no recibe máximo supuesto | El resultado integrado requiere revisión y el máximo queda nulo. | F3D-U-14 |
| Excepción de mínimo no amplía máximo | La excepción permite carga inferior a tres y conserva máximo seis para irregular. | F3D-U-08, 09, 20 |
| Prioridad de regularización | Una obligatoria reprobada se registra como prioritaria e incluida antes que optativas. | F3D-U-12 |
| Seriación | Una optativa con seriación directa no completa artificialmente el mínimo. | F3D-U-10 |
| Oferta y cupo | `SIN_CUPO` se conserva como causa distinta de `SIN_OFERTA`. | F3D-U-11 |
| Empate sin criterio | No se selecciona arbitrariamente: se devuelve `REQUIERE_REVISION`. | F3D-U-13 |
| Duplicados | La misma UD representada en B1/B2 se consolida y cuenta una vez. | F3D-U-21 |
| Baja parcial/no cursada | Mantienen sus condiciones y no se cuentan como reprobación. | F3D-U-22 |
| RN-6.4 observacional | Sólo se detecta con cuatro UD reprobadas vigentes; no cambia la decisión productiva. | F3D-U-15, 16 |
| CTR-01 / RN-6.1 observacional | Sólo procede para quinto semestre y periodo normativo `>= 2027-1`; tercero y séptimo no lo activan. | F3D-U-17, 18, 19 |
| Determinismo | El mismo contexto conserva estado e inclusiones sombra. | F3D-U-23 |
| No efecto productivo | Resultado marca `efectosProductivosAplicados=false`; oferta y selección origen permanecen intactas. | F3D-U-24 |

## Casos integrados ejecutados

La clase `EvaluacionCurricularV2Fase3DTest` incorpora 24 casos `F3D-U-01` a `F3D-U-24`. Corresponden a la matriz A–X autorizada:

| Matriz | Caso F3D | Escenario comprobado |
|---|---|---|
| A | U-01 | Regular con tres obligatorias: carga válida. |
| B | U-02 | Regular con ocho unidades: máximo alcanzado. |
| C | U-03 | Regular con nueve candidatas: no se supera el máximo. |
| D | U-04 | Irregular con tres prioritarias: resultado válido y situación irregular. |
| E | U-05 | Irregular con seis: máximo seis. |
| F | U-06 | Irregular con siete: máximo no cumplido. |
| G | U-07 | Dos obligatorias y una optativa: mínimo cumplido. |
| H | U-08 | Dos obligatorias sin alternativas: excepción tipificada. |
| I | U-09 | Única obligatoria pendiente: excepción y carga evaluada de una UD. |
| J | U-10 | Optativa seriada: no completa el mínimo. |
| K | U-11 | Una UD sin cupo: causa diferenciada. |
| L | U-12 | Obligatoria reprobada tiene prioridad sobre optativas. |
| M | U-13 | Empate sin criterio aprobado: revisión requerida. |
| N | U-14 | Situación no determinada: incertidumbre propagada. |
| O | U-15 | Cuatro reprobadas: RN-6.4 observacional. |
| P | U-16 | Tres reprobadas: RN-6.4 no procede. |
| Q | U-17 | Quinto semestre desde 2027-1: CTR-01/RN-6.1 procedente sólo como indicio. |
| R | U-18 | Tercer semestre: CTR-01/RN-6.1 no procede. |
| S | U-19 | Séptimo semestre: CTR-01/RN-6.1 no procede. |
| T | U-20 | Excepción mínima de irregular conserva máximo seis. |
| U | U-21 | Duplicado técnico: una sola UD para carga. |
| V | U-22 | Baja parcial y no cursada no se convierten en reprobación. |
| W | U-23 | Repetición del mismo contexto: resultado determinista. |
| X | U-24 | Contrato y trazas disponibles sin efecto sobre la fuente productiva. |

## Contrato resultado V2

La revisión no requirió crear un DTO paralelo. `ResultadoEvaluacionCurricularV2DTO` contiene `evaluacionCargaMinima`, `evaluacionCargaMaxima` y `evaluacionCargaAcademica`; esta última expresa, sin volver a calcular, situación académica, mínimo, máximo, carga evaluada, excepción de mínimo, prioridades, UD incluidas/excluidas, causas de exclusión, evidencia, reglas relacionadas, revisión requerida y los indicadores observacionales `rn64AplicableEnFuturaFase` y `ctr01Rn61Procedente`.

Las decisiones por UD siguen disponibles en `ResultadoEvaluacionCurricularV2DTO.decisiones`, con estado normalizado, causas, oferta origen, seleccionabilidad origen, oferta evaluada V2, motivos, prioridad y referencias históricas. Por tanto, el contrato conserva la información necesaria para consumidores futuros sin acoplar todavía Malla, Asistente o Simulador.

## Trazabilidad RN-9.5

La evaluación integrada añade la traza `RN_4_3` y conserva las trazas generadas antes por RN-1.2, RN-2.2, RN-4.1 y RN-4.2. F3D-U-24 comprueba que la evaluación contiene explícitamente esa cadena de reglas y la traza de RN-4.3. La implementación de RN-9.5 ya persiste de manera genérica las trazas, decisiones por UD y excepciones que contiene el resultado; por ello no se recalcula información para auditarla.

La comprobación realizada es de contrato y regresión unitaria/integrada. No se ejecutó una persistencia contra una base DEV/QA en esta fase y no se afirma una validación E2E nueva. La información persistible utiliza IDs técnicos, códigos y evidencia técnica; F3D no introduce nombres, correo, CURP ni otros datos personales.

## Pruebas y compilación

La ejecución F3D inicial registró un fallo de aserción en el caso de cupo debido a que la prueba esperaba una representación literal de la clave; se corrigió la aserción para verificar el código funcional `SIN_CUPO` y su diferencia respecto de `SIN_OFERTA`. No se modificó código productivo ni de motor por este ajuste.

Ejecuciones finales completadas:

| Comando | Resultado |
|---|---|
| `mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest,DiagnosticoE2EEvaluacionCurricularV2ServiceTest,EvaluacionCurricularV2Fase2ATest,EvaluacionCurricularV2Fase2BTest,EvaluacionCurricularV2Fase2CTest,EvaluacionCurricularV2Fase2DTest,EvaluacionCurricularV2Fase2ETest,EvaluacionCurricularV2Fase2FTest,EvaluacionCurricularV2Fase3ATest,EvaluacionCurricularV2Fase3BTest,EvaluacionCurricularV2Fase3CTest,EvaluacionCurricularV2Fase3DTest test` | 240 pruebas ejecutadas; 0 fallas; 0 errores; 0 omitidas. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/basegestor` | Compilación exitosa. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/gestorWeb` | Compilación exitosa. |

## Riesgos pendientes

1. El Motor V2 permanece en modo sombra; no existe activación ni integración visible con Malla, Asistente o Simulador.
2. El Gate E2E real previo a una integración futura sigue sujeto a disponer de un ambiente DEV/QA y de expedientes reales autorizados.
3. RN-6.1/CTR-01 y RN-6.4 sólo se detectan observacionalmente. F3D no activa sus consecuencias académicas ni productivas.
4. La selección entre unidades de igual prioridad sin criterio aprobado conserva `REQUIERE_REVISION`; no se introduce un desempate implícito.

## Conclusión

La FASE 3 queda cerrada para el Motor Curricular V2 en sombra: RN-4.1, RN-4.2 y RN-4.3 producen una representación integrada, preservan excepciones, prioridades, incertidumbre y trazabilidad, y no modifican la oferta, seleccionabilidad, carga, mapa, simulador, asistente ni inscripción productivos.
