# Reporte de diferencias: comportamiento vigente vs. evaluación sombra V2

**Fecha:** 2026-10-05
**Rama:** `feature/reglas-negocio-pe2027`
**Versión normativa trazada:** `PE2027-V1`
**Alcance:** FASE 0A, 0B, 0C y 0D. Este reporte no activa ni reemplaza reglas productivas.

## 1. Propósito

La comparación de FASE 0 no cambia la decisión del módulo de inscripción. Su objetivo es comprobar que una misma representación de unidades didácticas puede convertirse en un contexto V2 trazable y evaluarse sin modificar el contexto, la selección, la oferta, la carga ni el renderizado vigente.

La fuente de lectura es `AsistenteInscripcionContextoDTO`, que a su vez conserva el `InscripcionContextoDTO` y sus `UnidadDecisionInscripcionDTO`. El adaptador `ContextoEvaluacionCurricularV2AdapterImpl` no persiste ni altera esas fuentes.

## 2. Diferencias intencionales de FASE 0

| Aspecto | Comportamiento vigente | Resultado sombra V2 | Efecto productivo |
|---|---|---|---|
| Estado de UD | Los textos se consumen en DTO de decisión y en cada componente vigente. | Se normalizan a `ACREDITADA`, `REPROBADA`, `NO_CURSADA`, `BAJA_PARCIAL`, `EN_CURSO`, `NO_INSCRITA` o `NO_DETERMINADO`. | Ninguno; la normalización está sólo en memoria. |
| Baja / no cursamiento | El contexto vigente conserva sus estatus y reglas heredadas. | `BAJA_PARCIAL` y `NO_CURSADA` generan causas separadas; no se convierten automáticamente en reprobación. | Ninguno; no se alimenta al validador vigente. |
| Duplicados por bloque | El Asistente puede contener representaciones por bloque para explicar alternativas de oferta. | El normalizador agrupa por `idPrograma`; si no existe, por clave. Conserva las referencias de las representaciones agrupadas. | Ninguno; no se modifica la lista del Asistente. |
| Seriación | El motor vigente decide bloqueo/oferta mediante sus validadores existentes. | Se registra `SERIACION_DIRECTA` como motivo concurrente cuando la fuente ya la marca conocida. | Ninguno; no se bloquea ni habilita una UD por V2. |
| Oferta y habilitación | La inscripción mantiene su propia decisión vigente. | `habilitada` se refleja únicamente como fotografía del atributo `ofertada` de origen. | Ninguno; no se modifica la oferta real. |
| Configuración | Reglas vigentes usan sus catálogos y parámetros actuales. | Se expone una configuración V2 independiente con versión, bandera, modo sombra y vigencia CTR-01. | Ninguno; no se consulta desde pantallas ni validadores. |

## 3. Resultados de comparación ejecutados

| ID | Verificación | Resultado |
|---|---|---|
| F0-I-01 | La clave, persona, plan y estado normalizado del contexto del Asistente se conservan al generar el contexto V2. | Aprobada. |
| F0-I-02 | La evaluación sombra no altera `seleccionable` ni `ofertada` del DTO origen. | Aprobada. |
| F0-I-03 | Un contexto sin proceso de inscripción activo se construye sin excepción y conserva proceso nulo. | Aprobada. |
| F0-I-04 | La evaluación de un caso representativo de nuevo ingreso no añade una decisión funcional de elegibilidad; deja mensaje de modo sombra. | Aprobada. |
| F0-I-05 | La seriación conocida se registra en el contexto V2 sin cambiar el valor `bloqueada` de origen. | Aprobada. |
| F0-I-06 | Reprobada, baja parcial y no cursada quedan diferenciadas en el contexto V2. | Aprobada. |

## 4. Comparación con datos reales

No se ejecutó comparación contra expedientes reales ni contra la base de datos local/preproducción en FASE 0D. La migración de auditoría no fue ejecutada por restricción expresa, y el adaptador no fue conectado a una pantalla ni a un proceso de inscripción. Por ello, no se declara equivalencia académica de casos reales todavía.

La primera comparación operativa con expedientes deberá realizarse en una fase posterior, después de autorizar la migración y de definir un mecanismo de consulta de resultados sombra. Esa ejecución deberá contrastar, para la misma persona, plan y periodo: estado por UD, oferta vigente, bloqueos vigentes y motivos V2, sin usar el resultado V2 para decidir inscripción.

## 5. Diferencias no permitidas y verificación

No se detectó en las pruebas de FASE 0 cambio sobre:

- elegibilidad real;
- oferta real;
- límites de carga;
- selección del usuario;
- validadores de inscripción;
- renderizado del Mapa curricular dinámico;
- activación de CTR-01.

La garantía está limitada a las pruebas automatizadas en memoria y a la revisión de integración: `obtenerEvaluacionCurricularSombra` es un método de consulta explícito y ninguna vista ni validador productivo lo invoca en este cambio.

## 6. Riesgos y siguientes controles

1. Los valores V2 se resuelven temporalmente desde propiedades JVM con valores predeterminados. El script `39_fase0_evaluacion_curricular_v2_sombra_20261005.sql` propone la persistencia de los parámetros, pero no fue aplicado.
2. Algunos estatus históricos pueden llegar en vocabularios no contemplados por el adaptador. Estos se clasifican como `NO_DETERMINADO`, sin inferir reprobación.
3. La deduplicación conserva referencias, pero la regla de precedencia académica entre intentos o registros contradictorios se reservará para las fases de reglas aprobadas.
4. CTR-01 sólo se registra como configuración (`2027-1`, semestre objetivo `5`); no se evalúa en FASE 0.
