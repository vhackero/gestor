# Resultado FASE 5A — CTR-01 / RN-6.1.1

## FASE_IMPLEMENTADA

FASE 5A — aplicabilidad y base de evaluación del Control de Avance Anual para acceso a quinto semestre.

## REGLAS

- `CTR-01`
- `RN-6.1`
- `RN-6.1.1`

Versión normativa: `PE2027-V1`.

## PERIODO_INICIO Y SEMESTRE_OBJETIVO

- `PERIODO_INICIO = 2027-1`.
- `SEMESTRE_OBJETIVO = 5`.

El motor V2 aplica CTR-01 sólo cuando ambas condiciones se cumplen. Para un semestre objetivo nulo, o un periodo normativo ausente o con formato no verificable, produce `REQUIERE_REVISION`. Para cualquier semestre objetivo distinto de cinco, produce `NO_APLICA`; por tanto no aplica a las transiciones 2→3 ni 6→7.

## CRITERIO_APLICABILIDAD

La fuente de aplicabilidad es `ContextoEvaluacionCurricularV2DTO.periodoNormativo` junto con `semestreObjetivo`. No se usa el año de ingreso, número administrativo de reinscripciones ni semestre calendario. El periodo histórico de una evidencia no sustituye al periodo normativo de la evaluación.

## BASE_OBLIGATORIAS_1_A_4

Cuando CTR-01 aplica y el plan técnico está disponible, `EvaluacionCurricularV2ServiceImpl` construye una base única con las UD cuyo tipo contiene `OBLIGATORIA` y cuyo semestre curricular está entre 1 y 4, inclusive. La base se normaliza antes de evaluarse; las representaciones duplicadas de la misma UD no incrementan el conteo.

El resultado conserva:

- `baseRn611`;
- `obligatoriasAcreditadasRn611`;
- `obligatoriasPendientesRn611`;
- `detallePendientesRn611` con UD, semestre, estado, causa y referencias históricas;
- `totalObligatoriasSemestres1A4`.

Optativas y electivas se excluyen de esa base. Si falta plan, identidad técnica, tipo de UD, semestre o estado necesario para definir la base, no se infiere un resultado: se conserva `REQUIERE_REVISION`.

## TRATAMIENTO_ACREDITADAS

Una UD de la base satisface RN-6.1.1 únicamente con estado curricular normalizado `ACREDITADA`. La normalización consolidada conserva referencias históricas: una reprobación anterior que fue posteriormente acreditada no queda como pendiente vigente de la base.

## TRATAMIENTO_PENDIENTES

Toda obligatoria de la base que no está acreditada se conserva como pendiente con su estado y causa propios. Se distinguen `REPROBADA`, `BAJA_PARCIAL`, `NO_CURSADA`, `EN_CURSO`, `NO_INSCRITA` y `NO_DETERMINADO`.

### PENDIENTE_NO_EQUIVALE_REPROBADA

La condición de pendiente de CTR-01 no altera RN-1.2. En particular, `BAJA_PARCIAL`, `NO_CURSADA`, `NO_INSCRITA` y `EN_CURSO` no se convierten en reprobación. Sólo una reprobación vigente ya determinada por RN-1.2 contribuye a irregularidad y, cuando corresponda, a RN-6.4.

## ACCESO_ORDINARIO

Si todas las obligatorias 1–4 de la base están acreditadas y no existe restricción concurrente conocida, el estado sombra es `ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA` y `accesoOrdinarioQuinto = true`.

Este resultado es explicativo. No habilita una inscripción, no modifica oferta, selección, carga, mapa, asistente ni simulador.

## DELEGACION_AVANCE_CONDICIONADO

Si existe una obligatoria pendiente y no hay una restricción concurrente prevalente, el estado es `REQUIERE_EVALUACION_AVANCE_CONDICIONADO`. FASE 5A no decide si procede el avance condicionado y no implementa RN-6.1.2, RN-6.1.3, RN-6.1.4, RN-6.1.5 ni RN-6.1.6.

## PRECEDENCIA_RN_6_4

Si CTR-01 aplica y RN-6.4 está activa, se conserva la precedencia ya implementada: el estado resulta `BLOQUEADO_POR_RESTRICCION_CONCURRENTE`, `avanceCondicionadoBloqueadoPorRn64 = true` y no se concede acceso ordinario ni avance condicionado. La fase no reimplementa RN-6.4; reutiliza su resultado.

## TRAZABILIDAD

Se incorporó `RN_6_1_1` a los códigos estables y el enum `EstadoControlAvanceAnualV2`. Cada evaluación registra CTR-01, RN-6.1 y RN-6.1.1 en reglas evaluadas y genera una traza con periodo, semestre objetivo, resultado, base, pendientes y evidencia disponible. No se incorpora PII.

## PRUEBAS

Se agregó `EvaluacionCurricularV2Fase5ATest` con 25 escenarios: vigencia temporal, objetivos no aplicables, objetivo indeterminado, base completa, estados pendientes, acreditación posterior, exclusión de optativas/electivas, deduplicación, RN-6.4 concurrente, restricciones concurrentes, plan/tipo indeterminados, determinismo y ausencia de efecto productivo.

Resultados ejecutados:

- `mvn -q -Dtest=EvaluacionCurricularV2Fase5ATest test`: 25 pruebas, 0 fallas, 0 errores.
- `mvn -q test` en `codigoFuente/basegestor`: 395 pruebas, 0 fallas, 0 errores.
- `mvn -q -DskipTests compile` en `codigoFuente/basegestor`: exitoso.
- `mvn -q -DskipTests compile` en `codigoFuente/gestorWeb`: exitoso.

## ARCHIVOS_MODIFICADOS

- `CodigoReglaCurricularV2.java`.
- `EvaluacionCargaAcademicaV2DTO.java`.
- `EvaluacionCurricularV2ServiceImpl.java`.

## ARCHIVOS_CREADOS

- `EstadoControlAvanceAnualV2.java`.
- `EvaluacionCurricularV2Fase5ATest.java`.
- Este documento.

## RESULTADO

CTR-01 y la base RN-6.1.1 se evaluan formalmente en modo sombra, con resultados deterministas, trazables y sin aplicar efectos sobre componentes productivos.

## RIESGOS

- La base depende de que el adaptador futuro proporcione plan, tipo y semestre curricular verificables por UD.
- La decisión de avance condicionado queda explícitamente pendiente para las subreglas RN-6.1.2 a RN-6.1.6.
- La integración visible con Malla, Asistente y Simulador permanece fuera de esta fase y requiere el Gate E2E definido para integraciones futuras.

## CAMBIO_PRODUCTIVO

`NINGUNO`.
