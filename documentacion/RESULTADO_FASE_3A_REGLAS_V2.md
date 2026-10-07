# Resultado FASE 3A — RN-4.1 requisitos de carga mínima

## Estado

- **FASE_IMPLEMENTADA:** FASE 3A.
- **REGLA:** RN-4.1.
- **Versión normativa:** `PE2027-V1`.
- **CARGA_MINIMA:** 3 UD por periodo académico, a partir del segundo semestre.
- **Modo:** SOMBRA.
- **CAMBIO_PRODUCTIVO:** NINGUNO.

La fase construye una evaluación semestral de carga mínima dentro de `ResultadoEvaluacionCurricularV2DTO`. No modifica la carga, oferta, selección, cupo, grupos, bloqueos ni validadores del proceso vigente.

## Modelo implementado

Se agregó `EvaluacionCargaMinimaCurricularV2DTO`, expuesto por `ResultadoEvaluacionCurricularV2DTO.getEvaluacionCargaMinima()`, y el enum `EstadoCargaMinimaCurricularV2`.

El contrato conserva:

- aplicabilidad normativa;
- mínimo normativo y carga evaluada en sombra;
- cantidades de obligatorias, optativas y total elegible;
- alcanzabilidad del mínimo;
- excepción tipificada aplicable;
- UD consideradas por tipo;
- UD descartadas con motivo técnico;
- evidencia, motivos y reglas relacionadas.

Los estados descriptivos son `CUMPLE_MINIMO`, `CUMPLE_MEDIANTE_OPTATIVAS`, `EXCEPCION_MINIMO_APLICABLE`, `NO_CUMPLE_POR_RESTRICCION`, `REQUIERE_REVISION` y `NO_DETERMINADO`. La implementación actual usa `REQUIERE_REVISION` cuando faltan datos de disponibilidad que impedirían inferir elegibilidad, y preserva `NO_DETERMINADO` para primer semestre o semestre objetivo no verificable.

## Criterio de obligatorias y optativas

La evaluación reutiliza la normalización de UD, las decisiones V2 de RN-2.2 y la seriación ya registrada. La selección de candidatos sigue esta secuencia:

1. Considera UD pendientes: excluye acreditadas y en curso.
2. Exige oferta, grupo activo y cupo verificables.
3. Descarta explícitamente UD no ofertadas, sin cupo, no seleccionables en origen, bloqueadas por seriación o con datos insuficientes.
4. Cuenta primero las obligatorias elegibles.
5. Incorpora optativas únicamente para completar las tres UD, sin desplazar la información de prioridad de las obligatorias.

El cálculo no depende de que la situación sea regular o irregular: ambas observan el mismo mínimo normativo de tres UD. RN-1.2 continúa siendo la fuente de esa situación; RN-4.1 no la recalcula.

## Excepción de carga mínima

Cuando las UD elegibles son menos de tres y los datos son suficientes, V2 registra `EXCEPCION_MINIMO_APLICABLE` sin alterar la carga productiva. Reutiliza los códigos CTR-02 existentes:

- `CARGA_MINIMA_OBLIGATORIAS_INSUFICIENTES`;
- `CARGA_MINIMA_OPTATIVAS_INSUFICIENTES`;
- `CARGA_MINIMA_UNICA_OBLIGATORIA_PENDIENTE`, si existe sólo una obligatoria elegible.

En el último caso la carga evaluada puede ser una UD exclusivamente en sombra. No se agregan UD artificiales para llegar a tres.

Las optativas con seriación conocida, falta de oferta, falta de grupo o falta de cupo no se usan para completar el mínimo. La falta de cupo se conserva como `SIN_CUPO`, distinta de `SIN_OFERTA`.

## Primer semestre y dependencias futuras

Para `semestreObjetivo <= 1`, RN-4.1 no aplica automáticamente el mínimo general. El resultado marca la evaluación como no aplicable y conserva la evidencia `PRIMER_SEMESTRE_REQUIERE_REGLA_ESPECIFICA_NUEVO_INGRESO`; no sustituye la regla de nuevo ingreso.

La evaluación registra RN-2.2 y CTR-02 como dependencias. La seriación se conserva como restricción. RN-6.4 continúa sólo observacional y RN-6.1/CTR-01 no se activan ni se utilizan para habilitar UD en esta fase.

## Trazabilidad

RN-4.1 agrega una traza con mínimo, conteos, descartes y excepción. Las excepciones tipificadas existentes se asocian a las decisiones por UD disponibles, sin sustituir su oferta origen. La persistencia de RN-9.5 se conserva sin cambios: F3A sólo incrementa la información que el resultado sombra puede aportar a la auditoría.

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoCargaMinimaCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaMinimaCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ResultadoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase1CTest.java` (se completa evidencia de grupo/cupo en fixtures de oferta heredados)
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase3ATest.java`

## Pruebas

La suite nueva `EvaluacionCurricularV2Fase3ATest` cubre F3A-U-01 a F3A-U-16: tres o cuatro obligatorias; combinaciones con optativas; excepciones; una única obligatoria; optativas bloqueadas; oferta insuficiente; cupo; baja/no cursada; regularidad/irregularidad; datos insuficientes; primer semestre; determinismo e inmutabilidad.

Regresión F0–F3A ejecutada:

- **174 pruebas ejecutadas**;
- **0 fallos**;
- **0 errores**;
- **0 omitidas**.

Compilación ejecutada y correcta en `basegestor` y `gestorWeb`.

## Riesgos pendientes

1. La elegibilidad depende de que los adaptadores aporten oferta, grupo y cupo con calidad suficiente. Si un valor no es verificable, V2 no lo contabiliza y devuelve revisión cuando esa ausencia impide resolver el mínimo.
2. La priorización operativa completa de regularización y los efectos de RN-6.1/RN-6.4 pertenecen a fases posteriores; en F3A sólo se preservan como dependencias/condiciones.
3. No se integró el resultado con Malla, Asistente ni Simulador. El Gate E2E de integración futura se mantiene pendiente conforme al alcance vigente.
