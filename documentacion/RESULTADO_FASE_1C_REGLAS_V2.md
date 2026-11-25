# Resultado de FASE 1C — CTR-02 Excepciones tipificadas y extraordinarias

## FASE_IMPLEMENTADA

FASE 1C, ejecutada en `feature/reglas-negocio-pe2027` y exclusivamente en modo sombra. La fuente de decisión productiva continúa siendo el motor vigente y el Gate E2E para activación productiva permanece pendiente.

## CRITERIO_IMPLEMENTADO

**CTR-02 — Automatización de excepciones.** La evaluación V2 identifica y estructura supuestos tipificados verificables automáticamente. Cuando se identifica una causa no cubierta por un supuesto tipificado que impide una resolución automática, genera una excepción extraordinaria con estado `REQUIERE_REVISION`. Ninguna de las dos clases de excepción habilita, bloquea, selecciona o modifica UD en esta fase.

## EXCEPCIONES_TIPIFICADAS_MODELADAS

Los identificadores principales son valores de `CodigoExcepcionCurricularV2`; no se utiliza texto libre como identificador normativo.

| Familia | Código | Regla origen | Estado en FASE 1C |
| --- | --- | --- | --- |
| Carga mínima | `CARGA_MINIMA_OBLIGATORIAS_INSUFICIENTES` | RN-4.1 | `APLICABLE` como detección sombra. |
| Carga mínima | `CARGA_MINIMA_OPTATIVAS_INSUFICIENTES` | RN-4.1 | `APLICABLE` cuando obligatorias y optativas elegibles/ofertadas no alcanzan el mínimo origen. |
| Carga mínima | `CARGA_MINIMA_UNICA_OBLIGATORIA_PENDIENTE` | RN-4.1 | `APLICABLE` como registro sombra. |
| Avance condicionado de quinto | `AVANCE_UNA_OBLIGATORIA_PENDIENTE` | RN-6.1 | `CANDIDATA`; no se activa RN-6.1. |
| Avance condicionado de quinto | `AVANCE_REZAGO_LEVE` | RN-6.1 | `CANDIDATA` para 1–3 reprobadas. |
| Avance condicionado de quinto | `AVANCE_BAJA_TEMPORAL` | RN-6.1 | `CANDIDATA`; no cuenta la baja como reprobación. |
| Avance condicionado de quinto | `AVANCE_BAJA_PARCIAL` | RN-6.1 | `CANDIDATA`; conserva baja parcial como causa propia. |
| Avance condicionado de quinto | `AVANCE_FALTA_OFERTA` | RN-6.1 | `CANDIDATA`; excluye explícitamente `SIN_CUPO`. |
| Avance condicionado de quinto | `AVANCE_AUSENCIA_SERIACION` | RN-6.1 | `CANDIDATA`; no autoriza UD por sí sola. |

Los supuestos de RN-6.1 se evalúan únicamente cuando el semestre objetivo ya aportado por el contexto es quinto. La fase no activa CTR-01 ni los efectos de avance condicionado.

## EXCEPCIONES_EXTRAORDINARIAS

`EXTRAORDINARIA_NO_TIPIFICADA` se emite con tipo `EXTRAORDINARIA` y estado `REQUIERE_REVISION` para causas institucionales distintas de los supuestos tipificados o para límite de intentos. Incluye regla origen, condiciones, evidencia disponible y UD relacionadas para un futuro escalamiento institucional. No se creó workflow humano, autorización, actor resolutor ni escritura persistente.

Para `NO_DETERMINADO`/datos insuficientes, la evaluación no inventa una excepción automática ni infiere una autorización: conserva la condición de datos insuficientes del motor sombra.

## Modelo y trazabilidad

`ExcepcionCurricularV2DTO` registra por excepción:

- código estable, tipo, regla origen y estado;
- condiciones evaluadas, cumplidas y no cumplidas;
- evidencias y motivos;
- UD relacionadas;
- explicación técnica;
- fecha de evaluación y versión normativa.

El contexto amplía la información de baja temporal registrada y las UD pueden distinguir causas de indisponibilidad: no ofertada, fuera de bloque vigente, sin grupo activo institucional, otra indisponibilidad institucional, sin cupo y no determinada. El adaptador sólo clasifica como `UD_NO_OFERTADA_PERIODO` cuando la oferta fuente ya indica que no está ofertada; no inventa datos de grupos o cupos.

Las decisiones V2 por UD incorporan códigos de excepciones relacionadas. Oferta, seleccionabilidad y bloqueos de origen se mantienen sin modificaciones para comparación.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/UnidadEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/DecisionUnidadCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ResultadoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/ContextoEvaluacionCurricularV2AdapterImpl.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`

## ARCHIVOS_CREADOS

- `TipoExcepcionCurricularV2.java`
- `EstadoExcepcionCurricularV2.java`
- `CodigoExcepcionCurricularV2.java`
- `CausaIndisponibilidadOfertaV2.java`
- `ExcepcionCurricularV2DTO.java`
- `EvaluacionCurricularV2Fase1CTest.java`
- `documentacion/RESULTADO_FASE_1C_REGLAS_V2.md`

## PRUEBAS_AGREGADAS

F1C-U-01 a F1C-U-14 cubren: ausencia de excepción, carga mínima, concurrencia, una obligatoria pendiente, rezago leve, baja temporal, baja parcial, falta de oferta diferenciada de cupo, ausencia de seriación, caso extraordinario, datos insuficientes, determinismo, inmutabilidad de oferta origen y no habilitación por excepción extraordinaria.

## PRUEBAS_EJECUTADAS

```bash
cd codigoFuente/basegestor
mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest test
```

Resultado: **45 pruebas ejecutadas, 0 fallas, 0 errores, 0 omitidas**.

Compilación ejecutada:

```bash
cd codigoFuente/basegestor && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
cd codigoFuente/gestorWeb && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
```

Resultado: ambos módulos compilaron correctamente.

## RESULTADO

CTR-02 queda disponible para detectar y trazar excepciones concurrentes en el resultado V2. Las condiciones de carga mínima y de avance condicionado se representan sin aplicar sus efectos académicos posteriores. El motor conserva evidencia suficiente para que una fase posterior determine prioridades, restricciones y, si corresponde, escalamiento institucional.

## RIESGOS

- La detección de grupo activo, bloque vigente y cupo depende de que esas fuentes se incorporen al contexto V2; la fase no inventa esos datos cuando no están disponibles.
- RN-4.1 y RN-6.1 no están activas productivamente; la marca `APLICABLE`/`CANDIDATA` es exclusivamente descriptiva.
- La validación E2E DEV/QA sigue pendiente para cualquier activación productiva.

## CAMBIO_PRODUCTIVO

**NINGUNO.** No se modificaron oferta real, seleccionabilidad real, carga productiva, bloqueos/habilitaciones de inscripción, mapa, estado persistido, CTR-01, RN-4.1, RN-6.1 ni RN-6.4. No se ejecutaron migraciones y no se inició FASE 1D.
