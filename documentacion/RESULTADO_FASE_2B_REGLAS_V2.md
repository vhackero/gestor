# Resultado FASE 2B — RN-1.2 Estudiantes regulares e irregulares

**Fecha:** 2026-10-06
**Rama:** `feature/reglas-negocio-pe2027`
**Versión normativa:** `PE2027-V1`

## FASE_IMPLEMENTADA

`FASE 2B` implementa RN-1.2 exclusivamente en el Motor Curricular V2 en modo sombra. No existe integración visible con Malla, Asistente o Simulador, y no se alteró inscripción/reinscripción.

## MODELO_SITUACION_ACADEMICA

`ResultadoEvaluacionCurricularV2DTO` incorpora `situacionAcademicaCalculada`, `cantidadReprobadasVigentes`, UD origen de irregularidad, evidencias técnicas, datos descartados y `cargaMaximaFuturaSombra`. Este último campo sólo expresa la consecuencia normativa futura 8/6; no aplica límites de carga.

`UnidadEvaluacionCurricularV2DTO` conserva banderas de reprobación vigente y de periodo previo, además de intentos y referencias históricas. El adaptador aprovecha la lista vigente de reprobadas del contexto canónico cuando está disponible. Una representación histórica reprobada sin evidencia vigente se descarta de RN-1.2 y se registra como tal.

## CRITERIOS

- **REGULAR:** cero UD reprobadas vigentes de periodos previos.
- **IRREGULAR:** una o más UD con `REPROBADA` y causa `REPROBACION`, vigentes y previas, sin importar tipo.
- **NO_DETERMINADA:** existen UD cuyo estado/cause histórico no permite establecer una clasificación confiable y no hay una reprobación vigente que determine irregularidad.

Las UD en baja parcial, no cursadas, no inscritas o en curso no se convierten en reprobadas. La consolidación por ID/clave evita doble conteo por bloque; una acreditación posterior prevalece sobre una representación reprobada, conservando intentos y referencias para RN-1.4.

## TRAZABILIDAD

RN-1.2 genera una traza con situación, número de reprobadas vigentes, IDs técnicos, evidencia, descartes, versión y carga futura descriptiva. RN-9.5 conserva la traza mediante la auditoría V2 existente. Situación académica y avance condicionado permanecen independientes.

## PRUEBAS Y RESULTADO

Se agregaron F2B-U-01 a F2B-U-16 para tipos de UD, 0/1/3/4 reprobadas, bajas/no cursamiento/en curso, datos indeterminados, acreditación posterior, duplicidad, intentos, avance condicionado y actividad.

La regresión F0, F1A–F1E, F2A y F2B ejecutó 90 pruebas TestNG: **90 exitosas, 0 fallas, 0 errores y 0 omitidas**. `basegestor` compiló como parte de Maven Test y `gestorWeb` compiló con Maven Clean Compile.

## RIESGOS

La fuente de contexto no conserva actualmente el periodo detallado de cada intento dentro de `UnidadDecisionInscripcionDTO`; cuando la lista canónica de reprobadas está disponible se usa como evidencia vigente/previamente pendiente. La carga 8/6 es sólo informativa hasta FASE 4. No se activó RN-6.4.

`CAMBIO_PRODUCTIVO = NINGUNO`.
