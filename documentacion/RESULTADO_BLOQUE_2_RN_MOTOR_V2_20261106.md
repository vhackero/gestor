# Bloque 2: historial, oportunidades y reglas retiradas

## Alcance implementado

| Cambio | Resultado | Fuente o componente |
|---|---|---|
| C-04 | Se agregaron `BAJA_TEMPORAL`, atributos de preseleccion y oportunidades, cuatro estados de oportunidad y motivos trazables. | DTO V2 y evaluador |
| C-05 | Se incorporo consulta canónica por `tbl_inscripciones.id`, asociando grupo, evento, calificacion y baja contabilizable. | `tbl_inscripciones`, `rel_grupo_participante`, `tbl_grupos`, `tbl_eventos`, `tbl_periodos_inscripcion`, `rel_persona_bajas`, `rel_motivo_baja` |
| C-06 | RN-1.4 calcula consumidas/disponibles por UD; baja parcial o temporal contabilizable no consume. | `EvaluacionCurricularV2ServiceImpl` |
| C-07 | La evaluación PE2024/2027 deja de ejecutar mejora de promedio, RN-7.3 y su cierre de electivas. | `EvaluacionCurricularV2ServiceImpl` |

## Criterio de historial

Cada intento queda identificado por `tbl_inscripciones.id`. La calificación procede de la participación del mismo `groupbase`; el periodo se obtiene del evento y su clave de periodo. Una baja es aplicable únicamente cuando `rel_persona_bajas.contabilizar=1`. Una inscripción sin grupo/calificación queda `NO_DETERMINADO`; no se transforma en NP, reprobación ni oportunidad.

### Mapeo explícito de bajas

Fuente: `rel_persona_bajas.motivo_baja_id -> rel_motivo_baja(tipo_baja_id, descripcion) -> cat_tipo_bajas`. El catálogo de esquema disponible identifica los siguientes tipos. `descripcion` es texto libre del motivo; sólo se usa para `SERIACION` cuando el texto lo declara expresamente.

| Tipo real | Motivo real / condición verificable | Estado del intento | Motivo V2 | Efecto en oportunidades |
|---|---|---|---|---|
| `1 Parcial` | `solicitud=1` | `BAJA_PARCIAL` | `BAJA_PARCIAL` | Excluido del cómputo. |
| `1 Parcial` | descripción contiene `SERIACION` | `BAJA_PARCIAL` | `BAJA_PARCIAL`; causa `SERIACION` | Excluido; no se infiere solicitud. |
| `1 Parcial` | otro texto o sin evidencia | `NO_DETERMINADO` | `DATOS_INSUFICIENTES` | Requiere revisión. |
| `2 Temporal` | cualquier descripción | `BAJA_TEMPORAL` | `BAJA_PARCIAL` como causa histórica de la UD | Excluido. |
| `7 Temporal con asignaturas` | cualquier descripción | `BAJA_TEMPORAL` | `BAJA_PARCIAL` como causa histórica de la UD | Excluido. |
| `8 Temporal sin asignaturas` | cualquier descripción | `BAJA_TEMPORAL` | `BAJA_PARCIAL` como causa histórica de la UD | Excluido. |
| `3 Definitiva` | cualquier descripción | `NO_DETERMINADO` | Estado de expediente `BAJA_DEFINITIVA`; no excluye el intento | Requiere revisión. |
| `6 Definitiva sin posibilidad de reinscripción` | cualquier descripción | `NO_DETERMINADO` | Estado de expediente `BAJA_DEFINITIVA`; no excluye el intento | Requiere revisión. |
| `4 Administrativa` | cualquier descripción | `NO_DETERMINADO` | `BAJA_ADMINISTRATIVA_SEGUN_RESOLUCION` | Requiere resolución; no excluye por sí sola. |
| `5 Art 40` | cualquier descripción | `NO_DETERMINADO` | `BAJA_ADMINISTRATIVA_SEGUN_RESOLUCION` | Requiere resolución; no excluye por sí sola. |
| `9 Matrícula Docente` u otro tipo | cualquier descripción | `NO_DETERMINADO` | `DATOS_INSUFICIENTES` | Requiere revisión. |

La consulta productiva no tiene una fuente verificable que marque primer o segundo extraordinario en el historial académico. El tipo de proceso extraordinario existe en `cat_procesos_inscripcion` / `tbl_procesos_inscripcion`, pero no se liga de forma verificable a cada fila histórica consultada. Si el contrato recibe `EXTRAORDINARIO_HISTORICO`, produce `EXTRAORDINARIO_HISTORICO_REQUIERE_REVISION`, sin sumar reprobación ni oportunidad.

La calificación se admite solamente en el rango `0..100`. Fuera de rango se representa como `NP_SIN_BAJA_REQUIERE_REVISION`. El umbral aprobatorio se obtiene del parámetro JVM `V2_CALIFICACION_MINIMA_APROBATORIA` (predeterminado `60`) y se replica en `ParametrosCurricularesV2DTO`; no se toma de una etiqueta de pantalla.

No se localizó una fuente verificable para extraordinarios históricos en el alcance de esta consulta. Por contrato, si llega un registro marcado como extraordinario se devuelve `EXTRAORDINARIO_HISTORICO_REQUIERE_REVISION`; no se suma.

## Casos dorados ejecutados

| Caso | UD | Estado / decisión | Oportunidades | Aviso |
|---|---|---|---|---|
| GC-08a | X | `REPROBADA`, `ULTIMA_DISPONIBLE` | 2 consumidas, 1 disponible | Última oportunidad sujeta a oferta y condiciones. |
| GC-08b | X | `EN_CURSO`, `ULTIMA_EN_CURSO` | 3 consumidas, 0 disponibles | Está cursando la última oportunidad. |
| GC-08c | X | `REPROBADA`, `AGOTADA`, revisión/bloqueo por `LIMITE_INTENTOS` | 3 consumidas, 0 disponibles | Canalización institucional; no genera baja. |
| GC-08d | X | Baja parcial autorizada excluida | 1 consumida, 2 disponibles | No consume oportunidad. |
| GC-09 | Y | `NO_DETERMINADO`, `REQUIERE_REVISION` | 0 consumidas | `NP_SIN_BAJA_REQUIERE_REVISION`. |
| GC-11 | Z | `ACREDITADA`, no se ofrece para mejora | No aplica | Sin contador de mejora ni mejor calificación. |
| GC-15 | PE2024/2027 | Sin RN-7.3 ni categoría electiva vigente | No aplica | Las etiquetas históricas no activan una regla electiva. |

## GC-05

Un periodo con baja temporal completa se excluye como `BAJA_TEMPORAL`. Un periodo declarado sin inscripción se excluye como `SIN_INSCRIPCION`. `INSCRIPCION_SIN_EVIDENCIA_DE_CURSAMIENTO` se reserva para una inscripción o participación detectada sin evidencia final de cursamiento; no se confunden ambos motivos.

## Migración de pruebas heredadas

| Pruebas | Causa de falla previa | Acción | ¿Validaban regla vigente? |
|---|---|---|---|
| `f2DU03`, `f2DU04`, `f2DU13`, `f2FU04` | Nombres anteriores `ULTIMA_OPORTUNIDAD` / `LIMITE_AGOTADO`. | Migradas a `ULTIMA_DISPONIBLE` / `AGOTADA`. | Sí, RN-1.4. |
| `f2EU01..f2EU20` | Modelo retirado de mejora de promedio. | Eliminadas; GC-11 y `f2FU10/11` verifican que no se exponga mejora. | No, comportamiento retirado. |
| `f2FU10`, `f2FU11`, `f2FU14` | Contador, mejor calificación y traza RN-1.5 fuera de un caso de reinscripción acreditada. | Reescritas: no hay contador de mejora y RN-1.5 sólo aplica con la conducta correspondiente. | Parcial: RN-1.5 permanece, mejora no. |
| `f7CU01`, `f7CU02`, `f7CU03`, `f7CU04`, `f7CU05`, `f7CU07`, `f7CU09`, `f7CU14`, `f7DU01`, `f7DU02`, `f7DU05`, `f7DU11` | RN-7.3 y categoría `ELECTIVA` retiradas. | Eliminadas; prueba directa `rn72EsPrincipioGeneralOptativasReprobadas` sustituye el alcance vigente. | No, comportamiento retirado. |

## Verificación

- Prueba focalizada `Bloque2HistorialOportunidadesRn20261106Test`: 9 pruebas, 0 fallas, 0 errores, 0 omitidas. Incluye una prueba por tipo de baja, ordinaria reprobada más extraordinario, rango de calificación y RN-7.2.
- Caso dorado GC-04 / `OB33`: `PRIORITARIA + SIN_OFERTA`, no `HABILITADA` ni seleccionable; `OB51` permanece habilitada y `OB53` bloqueada por seriación.
- La evaluación llama `evaluarPrincipioGeneralOptativasReprobadasRn72`; el código de evaluación ya no contiene el rango legado de semestre `5..6` para ELECTIVA.
- Regresión V2 completa: 724 pruebas, 0 fallas, 0 errores, 0 omitidas.
- Compilación `basegestor`: SUCCESS con `mvn -q -DskipTests compile`.

## Pendientes

- Ejecutar GC-04 y GC-05 por el adaptador productivo con tres expedientes anonimizados y desplegar WAR. Esta sesión no despliega artefactos ni consulta expedientes reales.
- No se inició Bloque 3.
