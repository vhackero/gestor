# Resultado FASE 3B — RN-4.2 requisitos de carga máxima

## Estado

- **FASE_IMPLEMENTADA:** FASE 3B.
- **REGLA:** RN-4.2.
- **Versión normativa:** `PE2027-V1`.
- **MAXIMO_REGULAR:** 8 UD por periodo académico.
- **MAXIMO_IRREGULAR:** 6 UD por periodo académico.
- **Modo:** SOMBRA.
- **CAMBIO_PRODUCTIVO:** NINGUNO.

La fase agrega una propuesta descriptiva de carga máxima dentro del motor curricular V2. La propuesta no escribe ni modifica oferta, selección, carga, grupos, cupos, inscripción, reinscripción, Malla, Asistente ni Simulador.

## Dependencia de RN-1.2

RN-4.2 consume `ResultadoEvaluacionCurricularV2DTO.getSituacionAcademicaCalculada()`; no calcula de nuevo la regularidad. La asignación normativa es:

| Situación usada | Máximo sombra |
|---|---:|
| `REGULAR` | 8 |
| `IRREGULAR` | 6 |
| `NO_DETERMINADA` u otra no clasificable | No se infiere máximo; `REQUIERE_REVISION` / `NO_DETERMINADO` |

También se ajustó el campo previo `cargaMaximaFuturaSombra`: queda en `null` cuando RN-1.2 produce `NO_DETERMINADA`, evitando que el contrato heredado sugiera implícitamente el máximo de 8.

## Modelo implementado

Se incorporaron:

- `EstadoCargaMaximaCurricularV2`;
- `EvaluacionCargaMaximaCurricularV2DTO`;
- `ResultadoEvaluacionCurricularV2DTO.getEvaluacionCargaMaxima()`.

El resultado conserva situación utilizada, máximo normativo, carga evaluada, espacios disponibles, cantidades por prioridad, excedencia, marca de empate, UD prioritarias, UD incluidas sólo como propuesta sombra, UD fuera por máximo, descartes, motivos, reglas relacionadas y evidencia.

## Prioridad de regularización y propuesta sombra

La evaluación reutiliza el mismo filtro de elegibilidad de RN-4.1/RN-2.2: una UD debe estar pendiente, ofertada, con grupo/cupo verificables, seleccionable en origen y sin seriación bloqueante para considerarse candidata. Los descartes se conservan con motivo, por ejemplo: `SIN_OFERTA`, `SIN_CUPO`, `SERIACION`, restricción origen o datos insuficientes.

La propuesta ordena grupos sin alterar la selección productiva:

1. obligatorias reprobadas y vigentes, como regularización prioritaria;
2. obligatorias compatibles pendientes;
3. optativas compatibles.

Si la frontera del máximo corta un grupo de UD de igual prioridad y no existe criterio aprobado para desempatar, V2 no elige arbitrariamente: retorna `REQUIERE_REVISION`, marca `requiereRevisionPorEmpate` y registra las UD en `udsFueraPorMaximo`. Si un grupo posterior queda por completo fuera tras llenar el máximo, el estado es `EXCEDE_MAXIMO` y el motivo queda explícito.

## Relación con RN-4.1, RN-6.4 y RN-6.1

RN-4.1 sigue calculando el mínimo y sus excepciones. Una excepción que permita menos de tres UD en sombra no habilita superar el máximo de RN-4.2.

Cuando existen cuatro o más reprobadas, RN-4.2 conserva `RN_6_4` como dependencia observacional, sin activar sus efectos. Cuando el semestre objetivo es quinto, registra `RN_6_1` como procedencia futura; no activa CTR-01 ni aplica restricciones de avance condicionado.

## Trazabilidad

Se registra una traza RN-4.2 con situación RN-1.2, máximo, carga candidata, UD incluidas, UD fuera y descartadas. El resultado conserva referencia a RN-1.2, RN-2.2, RN-4.1 y RN-4.2; agrega RN-6.4 o RN-6.1 sólo cuando las condiciones observadas lo justifican. RN-9.5 no se modifica: puede persistir la traza sombra mediante su mecanismo existente.

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoCargaMaximaCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaMaximaCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ResultadoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase3BTest.java`

## Pruebas

La suite nueva `EvaluacionCurricularV2Fase3BTest` cubre F3B-U-01 a F3B-U-18: máximos regular/irregular; situación no determinada; prioridad de regularización; compatibilidad y optativas; coexistencia con excepción de mínimo; observación 6.4; quinto semestre; seriación; duplicados; empate; determinismo e inmutabilidad.

Regresión F0–F3B ejecutada:

- **192 pruebas ejecutadas**;
- **0 fallos**;
- **0 errores**;
- **0 omitidas**.

`basegestor` y `gestorWeb` compilaron correctamente.

## Riesgos pendientes

1. Los criterios de desempate entre UD de la misma prioridad no están definidos por la regla aprobada; V2 conserva `REQUIERE_REVISION` en vez de tomar una selección sombra arbitraria.
2. La calidad de oferta, grupo y cupo proviene de adaptadores aún no integrados visualmente. Datos incompletos se descartan de la carga y permanecen trazados.
3. RN-6.1 y RN-6.4 siguen sin activación productiva; esta fase sólo conserva sus dependencias.
