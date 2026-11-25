# Resultado FASE 3C — RN-4.3 validación general de carga académica

## Estado

- **FASE_IMPLEMENTADA:** FASE 3C.
- **REGLA:** RN-4.3.
- **Versión normativa:** `PE2027-V1`.
- **Modo:** SOMBRA.
- **CAMBIO_PRODUCTIVO:** NINGUNO.

RN-4.3 se implementó como orquestación del resultado de carga existente. No vuelve a calcular la situación académica, la elegibilidad, la carga mínima, la carga máxima ni la seriación. Tampoco conecta V2 con Malla Curricular, Asistente, Simulador o inscripción.

## Dependencias RN-4.1 y RN-4.2

La nueva `EvaluacionCargaAcademicaV2DTO`, expuesta desde `ResultadoEvaluacionCurricularV2DTO.getEvaluacionCargaAcademica()`, copia y consolida:

- situación académica calculada por RN-1.2;
- mínimo, excepción y descartes de `EvaluacionCargaMinimaCurricularV2DTO` (RN-4.1);
- máximo, carga candidata, prioridades, propuesta y exclusiones de `EvaluacionCargaMaximaCurricularV2DTO` (RN-4.2);
- condiciones concurrentes, excepciones CTR-02 y trazas previas;
- indicios observacionales de RN-6.4 y CTR-01/RN-6.1.

No se creó un cálculo paralelo de mínimo o máximo. La evaluación integrada sólo transforma las salidas ya disponibles en un dictamen descriptivo común.

## Orden de prioridad

La prioridad utilizada sigue la propuesta de RN-4.2:

1. UD obligatorias reprobadas que requieren regularización;
2. demás obligatorias compatibles y pendientes;
3. optativas compatibles.

Las UD excluidas conservan su causa. Una optativa no desplaza a una obligatoria prioritaria. Si la frontera del máximo corta UD de igual prioridad y no existe criterio académico aprobado, RN-4.3 conserva `REQUIERE_REVISION` y no convierte la propuesta en selección arbitraria.

## Tratamiento de seriación, oferta y cupo

RN-4.3 reutiliza la elegibilidad ya definida por RN-4.1/RN-4.2. Sus causas permanecen diferenciadas:

- `SIN_OFERTA` para UD fuera de oferta o sin grupo activo institucional;
- `SIN_CUPO` para capacidad insuficiente;
- `SERIACION` para relación directa o indirecta conocida bloqueante;
- restricciones de origen o V2;
- `DATOS_INSUFICIENTES`.

La nueva bandera de contexto `seriacionComprobada` permite declarar explícitamente evidencia insuficiente de seriación. Al ser `false`, la carga integrada queda en `REQUIERE_REVISION`; no infiere que la ausencia de datos equivale a ausencia de seriación.

## Excepciones y límites

La excepción tipificada de RN-4.1 puede permitir en sombra una carga inferior a tres UD. RN-4.3 la representa como `VALIDA_CON_EXCEPCION_MINIMO` sólo si el máximo permanece satisfecho. La excepción nunca aumenta el máximo de 8 para regular o 6 para irregular, ni elimina seriación, RN-6.4 o CTR-01/RN-6.1.

Se verifica explícitamente el invariante `cargaMinimaNormativa <= cargaMaximaNormativa` cuando ambos valores son determinables. Si falta situación académica o uno de los resultados previos no permite resolver carga, el estado integrado no infiere límites y queda en revisión/no determinado según corresponda.

## Detección RN-6.4 y RN-6.1

Para cuatro o más UD reprobadas vigentes, el contrato expone `rn64AplicableEnFuturaFase = true` y conserva `RN_6_4` como regla relacionada. No bloquea UD nuevas ni aplica el tratamiento completo de RN-6.4.

Se añadió `periodoNormativo` al contexto V2, separado de `idPeriodo`, para que la procedencia normativa no se infiera de un identificador técnico. `ctr01Rn61Procedente` sólo se evalúa como verdadero cuando:

- `semestreObjetivo = 5`; y
- `periodoNormativo >= 2027-1` con formato verificable `AAAA-[1|2]`.

Para semestres 3, 7 u otros distintos de 5 devuelve `false`; no afecta la carga. Para quinto sin periodo normativo verificable conserva el dato como no determinado, sin activar RN-6.1.

## Estados integrados

`EstadoCargaAcademicaV2` expresa: `VALIDA`, `VALIDA_CON_EXCEPCION_MINIMO`, `INCOMPLETA_POR_OFERTA`, `INCOMPLETA_POR_CUPO`, `RESTRINGIDA`, `REQUIERE_REVISION` y `NO_DETERMINADA`.

La evaluación consolidada conserva además UD prioritarias, UD incluidas sólo como propuesta sombra, UD excluidas y causas, mínimo/máximo, carga evaluada, excepción mínima, máximo cumplido, revisión, reglas y evidencia.

## Trazabilidad

RN-4.3 añade su traza con situación, mínimo, máximo, carga candidata, excepción, indicio RN-6.4, procedencia CTR-01 y exclusiones. RN-9.5 no se modifica: el mecanismo de persistencia existente puede reconstruir la nueva traza junto con las trazas de RN-1.2, RN-2.2, RN-4.1 y RN-4.2.

## Casos integrados y pruebas

`EvaluacionCurricularV2Fase3CTest` cubre F3C-U-01 a F3C-U-24: mínimos/máximos regular e irregular; excepción; prioridad; oferta/cupo; seriación; RN-6.4 observacional; procedencia de quinto desde 2027-1; no procedencia para tercero/séptimo; situación no determinada; empates; duplicados; determinismo e inmutabilidad.

Regresión F0–F3C ejecutada:

- **216 pruebas ejecutadas**;
- **0 fallos**;
- **0 errores**;
- **0 omitidas**.

`basegestor` y `gestorWeb` compilaron correctamente.

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoCargaAcademicaV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ResultadoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase3CTest.java`

## Riesgos pendientes

1. Los adaptadores reales todavía deben proporcionar `periodoNormativo` y la calidad de evidencia de seriación al integrar V2 con componentes visuales. En ausencia de esos datos, el motor no los inventa.
2. RN-6.1 y RN-6.4 siguen pendientes de sus fases específicas; F3C sólo detecta procedencia e indicios.
3. No se ejecutó una integración E2E visible ni se activó V2 productivamente.
