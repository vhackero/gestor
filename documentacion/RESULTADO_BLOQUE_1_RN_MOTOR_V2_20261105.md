# Bloque 1 - Fundamentos del Motor Curricular V2

## Alcance ejecutado

Se implementaron exclusivamente C-01, C-02 y C-03 de `Codex_INSTRUCCIONES_actualizacion_RN_motor_v2.md`. El motor permanece en modo sombra y no ejecuta escrituras de inscripción, oferta, grupos, bajas ni historial.

## Matriz de la sección 1 actualizada

| RN / transversal | Estado tras Bloque 1 | Evidencia implementada | Pendiente concreto |
|---|---|---|---|
| Universo de evaluación | Parcial, con contrato implementado | `unidadesMapaPlan` transporta el mapa completo y `idsUnidadesCandidatas` conserva la oferta como subconjunto. Si el consumidor aún entrega solo oferta, el resultado añade aviso de evaluación parcial. | Integrar la fuente institucional del mapa completo e historial en los cuatro consumidores. |
| Semestre de referencia (RN-6.9) | Conforme para contextos con evidencia de periodos; parcial en adaptadores heredados | `SemestreReferenciaV2Service` cuenta periodos formales anteriores, excluye baja temporal completa y periodos sin evidencia; B1/B2 no se duplican porque el insumo es el periodo. | Conectar `tbl_inscripciones -> tbl_eventos/tbl_periodos_inscripcion` y bajas temporales agrupadas por periodo en el adaptador productivo. |
| Ámbito, parámetros y versión (RN-9.5) | Parcial | Resolutor de ámbito sin homologación por nombre, parámetros configurables por JVM y versión `RN-PE2024-2027-act-20261105`. | Catálogo institucional de plan/cohorte/vigencia, población PE2024/2027 y documento de respaldo por parámetro. |
| RN-6.1 | Parcial | CTR-01 toma su periodo de parámetro y detecta candidatas de quinto mediante `idsUnidadesPretendidas` o `idsUnidadesCandidatas`; no usa semestre de referencia como sustituto. | C-10 validará la carga propuesta y las pendientes ofertadas/eligibles. |

## Casos dorados ejecutados

### GC-04 - Obligatoria nunca cursada y no ofertada

Entrada sintética: referencia 5, una obligatoria S3 (`OB33`) `NO_CURSADA` sin oferta, candidatas S5 `OB51` y `OB53`; `OB33 -> OB53` es seriación directa.

| UD | Estado de entrada | Oferta | Salida observada | Motivo / alcance |
|---|---|---|---|---|
| `OB33` | `NO_CURSADA` | No | Permanece en el universo; no incrementa reprobadas | Pendiente no cursada; la falta de oferta no se interpreta como ausencia de pendiente. |
| `OB51` | `NO_INSCRITA` | Sí | Candidata S5 evaluada | La evaluación conserva candidatas separadas del universo. |
| `OB53` | `NO_INSCRITA` | Sí | Candidata S5 con relación a `OB33` | La relación de seriación se entrega al motor; su decisión concreta depende de la cobertura completa de seriación del plan. |

Resultado global: `totalReprobadas=0`; RN-6.1 se evalúa sobre el universo completo disponible. La regla específica de bloqueo por falta de oferta de C-10 no forma parte de este bloque.

### GC-05 - Semestre de referencia

| Periodo | Evidencia | Salida |
|---|---|---|
| `2025-1` | Inscripción formal | Contado |
| `2025-2` | Inscripción formal | Contado |
| `2026-1` | Baja temporal completa | Excluido: `BAJA_TEMPORAL` |
| `2026-2` | Sin inscripción ni evidencia de cursamiento | Excluido: `INSCRIPCION_SIN_EVIDENCIA_DE_CURSAMIENTO` |
| `2027-1` | Inscripción formal | Contado |

Salida: `semestreReferencia=4`, `estado=DISPONIBLE`, `calidadDato=VERIFICADA`. Incluir una UD de quinto no cambia esa referencia.

### GC-12 - Fuera de ámbito

Entrada sintética: plan `612301` sin pertenencia comprobada a PE2024/2027.

| UD | Salida |
|---|---|
| Cualquier UD del contexto | No se ejecutan RN PE2024/2027; resultado global `REQUIERE_REVISION`. |

Motivo funcional: `NO_APLICA_RN_PE2024_2027` como estado de ámbito. El resultado no infiere homologación por nombre ni aplica RN-6.1 o RN-6.4.

## Pruebas

`Bloque1FundamentosRn20261105Test` cubre C-01 y los tres casos dorados solicitados. La suite V2 completa se ejecutó después de actualizar la expectativa de versión normativa.

## Fuera de alcance deliberado

No se implementaron C-04 a C-17, en particular estados de oportunidad, NP, optativas, preselección, rezago severo, validación de carga, seriación de salida, trayectorias, medidas administrativas ni trazabilidad ampliada.
