# Resultado FASE 4B — RN-5.1 carga académica con una a tres UD reprobadas

## Resultado

- `FASE_IMPLEMENTADA = FASE 4B`
- `REGLA = RN-5.1`
- `CONDICION = REZAGO_LEVE`
- `RANGO_REPROBADAS = 1..3`
- `VERSIÓN_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

RN-5.1 se implementó como una clasificación académica concurrente para el Motor Curricular V2. No sustituye `situacionAcademica`: cuando aplica, el resultado conserva `IRREGULAR` determinado por RN-1.2 y agrega `condicionRegularizacion = REZAGO_LEVE`. No realiza reinscripción ni modifica inscripción, oferta, cupo, selección, carga, Malla, Asistente, Simulador o validadores productivos.

## Dependencias reutilizadas

| Dependencia | Uso en RN-5.1 |
|---|---|
| RN-1.2 | Se consumen `situacionAcademicaCalculada` y `cantidadReprobadasVigentes`; RN-5.1 no recalcula reprobaciones ni regularidad. |
| RN-6.3 | Se consume la ruta de prioridad de regularización ya construida en `EvaluacionCargaAcademicaV2DTO`. |
| RN-4.2 | Se valida el máximo previamente calculado. Para un caso aplicable debe ser seis, sin volver a deducirlo. |
| RN-4.3 | Se conserva el resultado integrado, las exclusiones, la carga y la incertidumbre existentes. |
| RN-2.2 y seriación | Oferta individual, disponibilidad y seriación provienen de las decisiones y ruta existentes. |
| CTR-02 | Excepciones tipificadas permanecen en el resultado concurrente. |
| RN-9.5 | Se agrega una traza RN-5.1 con situación, reprobadas, ruta RN-6.3, máximo, indisponibilidades y detecciones observacionales. |

## Aplicabilidad

RN-5.1 aplica si y sólo si la situación ya calculada es `IRREGULAR` y las reprobadas vigentes están entre una y tres, inclusive. En dicho rango se registra `REZAGO_LEVE` en `CondicionCurricularV2` y en el contrato integrado de carga.

| Situación / reprobadas | Aplica RN-5.1 | Resultado |
|---|---:|---|
| REGULAR / 0 | No | `NO_APLICA` |
| IRREGULAR / 1..3 | Sí | `REZAGO_LEVE` |
| IRREGULAR / 4 o más | No | `NO_APLICA`; RN-6.4 permanece sólo observacional para su fase posterior |
| NO_DETERMINADA | No | `NO_DETERMINADA` y `REQUIERE_REVISION` |

La condición no puede cambiar por sí misma `IRREGULAR` a `REGULAR`. La recuperación continúa bajo responsabilidad de RN-1.3 y requiere que no existan reprobaciones vigentes.

## Prioridad, oferta y carga

RN-5.1 no construye listas nuevas. Expone la ruta RN-6.3 en el contrato existente:

1. UD reprobadas vigentes ofertadas y prioritarias.
2. Otras obligatorias compatibles.
3. Optativas compatibles, cuando haya capacidad y compatibilidad académica.

Una reprobada sin oferta conserva la condición de reprobación y prioridad, pero se registra como no disponible con causa `SIN_OFERTA`, `SIN_CUPO`, seriación o datos insuficientes. La falta de oferta nunca se interpreta como acreditación. Las bajas parciales, no cursadas, no inscritas, oportunidades de mejora e intentos históricos no elevan por sí mismos el número de reprobadas para RN-5.1.

El máximo procede de RN-4.2. En un caso `REZAGO_LEVE`, se verifica que sea seis; si no lo es o RN-6.3 no está disponible, el resultado conserva la incertidumbre y requiere revisión. No se implementa un contador adicional de carga ni una selección productiva.

## RN-6.4 y CTR-01/RN-6.1

- Con una, dos o tres reprobadas, `rn64AplicableEnFuturaFase=false`.
- Con cuatro o más, RN-5.1 no aplica y RN-6.4 sigue siendo una detección observacional sin bloqueo ni efecto productivo.
- La procedencia CTR-01/RN-6.1 se conserva únicamente para quinto semestre y `periodoNormativo >= 2027-1`. Para tercero o séptimo se mantiene en falso. La evaluación de Control de Avance no se activa en esta fase.

## Contrato y trazabilidad

No se creó un modelo paralelo. `EvaluacionCargaAcademicaV2DTO` se extendió de forma aditiva con `aplicaRn51` y `condicionRegularizacion`; las listas de reprobadas prioritarias/no disponibles, compatibles, causas, carga y revisión continúan siendo las que aporta RN-6.3/RN-4.3.

La traza RN-5.1 registra la versión normativa, situación RN-1.2, total de reprobadas, máximo RN-4.2, disponibilidad de la ruta RN-6.3, UD prioritarias/no disponibles y los indicadores observacionales de RN-6.4 y CTR-01. No se agrega PII.

## Pruebas agregadas

`EvaluacionCurricularV2Fase4BTest` implementa 27 casos `F4B-U-01` a `F4B-U-27`, que cubren:

- Aplicabilidad con cero, una, dos, tres y cuatro reprobadas.
- Máximo seis y no activación de RN-6.4 en el rango uno a tres.
- Reutilización de prioridades RN-6.3 y orden reprobadas → obligatorias → optativas.
- Falta de oferta, falta de cupo y seriación.
- Separación entre intentos, bajas parciales/no cursadas y reprobaciones vigentes.
- Máximo de carga, CTR-01/RN-6.1, acreditación posterior, datos insuficientes, duplicados, determinismo y ausencia de efecto productivo.

Resultados finales de verificación:

| Comando | Resultado |
|---|---|
| `mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest,DiagnosticoE2EEvaluacionCurricularV2ServiceTest,EvaluacionCurricularV2Fase2ATest,EvaluacionCurricularV2Fase2BTest,EvaluacionCurricularV2Fase2CTest,EvaluacionCurricularV2Fase2DTest,EvaluacionCurricularV2Fase2ETest,EvaluacionCurricularV2Fase2FTest,EvaluacionCurricularV2Fase3ATest,EvaluacionCurricularV2Fase3BTest,EvaluacionCurricularV2Fase3CTest,EvaluacionCurricularV2Fase3DTest,EvaluacionCurricularV2Fase4ATest,EvaluacionCurricularV2Fase4BTest test` | 290 pruebas; 0 fallas; 0 errores; 0 omitidas. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/basegestor` | Compilación exitosa. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/gestorWeb` | Compilación exitosa. |

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/CondicionCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase4BTest.java`
- `documentacion/RESULTADO_FASE_4B_REGLAS_V2.md`

## Riesgos pendientes

1. RN-5.2, RN-6.4 y RN-6.1 conservan sus efectos para fases posteriores autorizadas.
2. La integración visible permanece fuera de alcance hasta una fase específica y el Gate E2E correspondiente.
3. Cuando falte información de situación, máximo o ruta de prioridad, la evaluación conserva `REQUIERE_REVISION` y no aplica criterios implícitos.
