# Bloque 2: historial, oportunidades y reglas retiradas

## Alcance implementado

| Cambio | Resultado | Fuente o componente |
|---|---|---|
| C-04 | Se agregaron `BAJA_TEMPORAL`, atributos de preseleccion y oportunidades, cuatro estados de oportunidad y motivos trazables. | DTO V2 y evaluador |
| C-05 | Se incorporo consulta canónica por `tbl_inscripciones.id`, asociando grupo, evento, calificacion y baja contabilizable. | `tbl_inscripciones`, `rel_grupo_participante`, `tbl_grupos`, `tbl_eventos`, `tbl_periodos_inscripcion`, `rel_persona_bajas`, `rel_motivo_baja` |
| C-06 | RN-1.4 calcula consumidas/disponibles por UD; baja parcial o temporal contabilizable no consume. | `EvaluacionCurricularV2ServiceImpl` |
| C-07 | La evaluación PE2024/2027 deja de ejecutar mejora de promedio, RN-7.3 y su cierre de electivas. | `EvaluacionCurricularV2ServiceImpl` |

## Criterio de historial

Cada intento queda identificado por `tbl_inscripciones.id`. La calificación procede de la participación del mismo `groupbase`; el periodo se obtiene del evento y su clave de periodo. Una baja es aplicable únicamente cuando `rel_persona_bajas.contabilizar=1`: tipo 2 se mapea a baja temporal y los demás tipos a baja parcial. Una inscripción sin grupo/calificación queda `NO_DETERMINADO`; no se transforma en NP, reprobación ni oportunidad.

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

## Verificación

- Prueba focalizada `Bloque2HistorialOportunidadesRn20261106Test`: 5 pruebas, 0 fallas, 0 errores, 0 omitidas.
- Compilación de `basegestor`: SUCCESS.
- Regresión V2 completa: 771 pruebas, 39 fallas, 0 errores, 0 omitidas. Las fallas pertenecen a pruebas históricas que esperan el contador de mejora, RN-7.3 o los nombres anteriores de oportunidad. Deben migrarse antes de declarar el Bloque 2 aprobado técnicamente.

## Pendientes

- Ajustar las 39 pruebas heredadas a las RN vigentes; no restaurar reglas retiradas para hacerlas verdes.
- Ejecutar GC-04 y GC-05 por el adaptador productivo con tres expedientes anonimizados y desplegar WAR. Esta sesión no despliega artefactos ni consulta expedientes reales.
- No se inició Bloque 3.
