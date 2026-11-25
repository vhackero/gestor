# Resultado FASE 4D — RN-5.2 carga académica con cuatro o más UD reprobadas

## Resultado

- `FASE_IMPLEMENTADA = FASE 4D`
- `REGLA = RN-5.2`
- `CONDICION = REZAGO_SEVERO`
- `RANGO_REPROBADAS = 4+`
- `VERSIÓN_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

RN-5.2 se implementó como clasificación, composición y validación de consistencia sobre los resultados existentes de RN-6.4, RN-6.3 y RN-4.3. No replica el umbral, bloqueo de obligatorias, prioridad, tratamiento de optativas, máximo, carga ni precedencia sobre RN-6.1.

## Dependencias y no duplicación

| Dependencia | Resultado reutilizado por RN-5.2 |
|---|---|
| RN-1.2 | Situación académica y cantidad consolidada de reprobadas vigentes. |
| RN-6.4 | Restricción severa efectiva en sombra, bloqueo de obligatorias nuevas, optativas permitidas y prevalencia sobre avance condicionado. |
| RN-6.3 | Ruta de regularización, reprobadas prioritarias y no disponibles. |
| RN-4.1/RN-4.2/RN-4.3 | Excepción de mínimo, máximo, carga evaluada y resultado integrado. |
| RN-2.2, CTR-02, seriación | Oferta individual, excepciones y compatibilidades ya calculadas. |
| RN-9.5 | Traza RN-5.2 que compone el resultado anterior sin recalcularlo. |

`EvaluacionCargaAcademicaV2DTO` se extendió sólo con `aplicaRn52`. Los demás datos se consumen de los campos ya producidos por las fases anteriores. No se creó un DTO paralelo ni una segunda ruta de rezago severo.

## Aplicabilidad y consistencia

RN-5.2 aplica si y sólo si RN-1.2 reporta `IRREGULAR` y cuatro o más reprobadas vigentes. Cuando aplica, exige que la composición existente sea coherente:

- `aplicaRn64 = true`;
- `aplicaRn63 = true`;
- `condicionRegularizacion = REZAGO_SEVERO`;
- `cargaMaximaNormativa = 6` proveniente de RN-4.2.

Si alguna de estas condiciones no está presente, RN-5.2 conserva su aplicabilidad por rango pero marca `REQUIERE_REVISION` y registra una traza de inconsistencia. La prueba controlada F4D-U-06 valida este mecanismo sin alterar el motor productivo.

| Reprobadas | RN-5.1 | RN-5.2 | RN-6.4 | Condición |
|---:|---|---|---|---|
| 0 | No aplica | No aplica | No aplica | No aplica |
| 1–3 | Aplica | No aplica | No aplica | `REZAGO_LEVE` |
| 4+ | No aplica | Aplica | Aplica en sombra | `REZAGO_SEVERO` |
| No determinado | No inferir | No inferir | No inferir | Revisión requerida |

Por ello las condiciones leve y severa no pueden coexistir para el mismo contexto. La transición de cuatro a tres desactiva RN-5.2/RN-6.4 y permite RN-5.1, sin cambiar automáticamente la situación irregular; la recuperación corresponde a RN-1.3.

## Tratamiento de carga y disponibilidad

RN-5.2 refleja la composición ya resuelta:

1. Reprobadas vigentes disponibles y prioritarias.
2. Reprobadas no disponibles, con causa `SIN_OFERTA`, `SIN_CUPO`, seriación o datos insuficientes, conservadas en el conteo y la ruta.
3. Obligatorias nuevas bloqueadas por RN-6.4.
4. Optativas sólo si RN-6.4/RN-4.3 ya las consideran compatibles y existe espacio.

La excepción de mínimo de RN-4.1 puede coexistir con rezago severo, pero no habilita obligatorias nuevas. El máximo se conserva en seis y RN-5.2 no codifica un máximo independiente.

La precedencia sobre RN-6.1 se consume desde RN-6.4. Cuando CTR-01 es procedente para quinto semestre desde 2027-1, la composición documenta el resultado de prevalencia ya aplicado por RN-6.4; RN-5.2 no vuelve a implementar el Control de Avance.

## Trazabilidad

La traza RN-5.2 conserva situación, reprobadas, activación de RN-6.4, ruta RN-6.3, máximo y carga, reprobadas prioritarias/no disponibles, obligatorias bloqueadas, optativas, excepción mínima, procedencia CTR-01 y prevalencia de RN-6.4. No se agrega PII.

## Pruebas agregadas

`EvaluacionCurricularV2Fase4DTest` contiene 25 pruebas `F4D-U-01` a `F4D-U-25`, incluyendo:

- Rangos cero, uno, tres, cuatro y cinco o más reprobadas.
- Exclusión mutua RN-5.1/RN-5.2.
- Consistencia RN-5.2 → RN-6.4 y prueba controlada de inconsistencia.
- Bloqueo existente de obligatorias, optativas, oferta/cupo, carga máxima y excepción mínima.
- CTR-01, transición severo a leve, intentos, bajas/no cursadas, duplicados, incertidumbre, determinismo y ausencia de efectos productivos.

Resultados finales de verificación:

| Comando | Resultado |
|---|---|
| `mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest,DiagnosticoE2EEvaluacionCurricularV2ServiceTest,EvaluacionCurricularV2Fase2ATest,EvaluacionCurricularV2Fase2BTest,EvaluacionCurricularV2Fase2CTest,EvaluacionCurricularV2Fase2DTest,EvaluacionCurricularV2Fase2ETest,EvaluacionCurricularV2Fase2FTest,EvaluacionCurricularV2Fase3ATest,EvaluacionCurricularV2Fase3BTest,EvaluacionCurricularV2Fase3CTest,EvaluacionCurricularV2Fase3DTest,EvaluacionCurricularV2Fase4ATest,EvaluacionCurricularV2Fase4BTest,EvaluacionCurricularV2Fase4CTest,EvaluacionCurricularV2Fase4DTest test` | 340 pruebas; 0 fallas; 0 errores; 0 omitidas. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/basegestor` | Compilación exitosa. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/gestorWeb` | Compilación exitosa. |

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase4DTest.java`
- `documentacion/RESULTADO_FASE_4D_REGLAS_V2.md`

## Riesgos pendientes

1. RN-5.2 y RN-6.4 siguen operando sólo en sombra; una futura integración visible requiere autorización y Gate E2E.
2. El Control de Avance completo RN-6.1 no se implementó en esta fase.
3. Datos insuficientes e igualdad de prioridades continúan requiriendo revisión, sin decisiones implícitas.
