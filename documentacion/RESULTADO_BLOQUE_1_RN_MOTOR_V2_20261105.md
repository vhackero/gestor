# Bloque 1 - Fundamentos del Motor Curricular V2

## Alcance ejecutado

Se implementaron C-01, C-02 y C-03 y su integración de lectura productiva (Bloque 1-bis). El motor permanece en modo sombra y no ejecuta escrituras de inscripción, oferta, grupos, bajas ni historial.

## Matriz de la sección 1 actualizada

| RN / transversal | Estado tras Bloque 1 | Evidencia implementada | Pendiente concreto |
|---|---|---|---|
| Universo de evaluación | Integrado en el contexto común | `AsistenteInscripcionServiceImpl` obtiene todas las UD del mismo plan/malla que usa el Mapa y las separa de la oferta candidata. Malla, Asistente, Simulador y Asistente virtual consumen ese mismo `AsistenteInscripcionContextoDTO`. | El detalle de cada intento por UD sigue pendiente de una consulta histórica canónica; no se declara completo por una lista agregada. |
| Semestre de referencia (RN-6.9) | Integrado para periodos con clave de evento verificable | `InscripcionRepository` lee `tbl_inscripciones -> tbl_eventos -> tbl_periodos_inscripcion`; las bajas temporales aprobadas (`rel_persona_bajas.contabilizar=1`, tipo 2) se agrupan por periodo. B1/B2 comparten una sola clave. | La marca de NP por baja parcial requiere incorporarse desde su fuente de resultado para cerrar todos los casos históricos. |
| Ámbito, parámetros y versión (RN-9.5) | Parcial | Resolutor de ámbito sin homologación por nombre, parámetros configurables por JVM y versión `RN-PE2024-2027-act-20261105`. El DTO admite plan y cohorte, pero el origen actual de parámetros aún es JVM. | Catálogo institucional de plan/cohorte/vigencia, población PE2024/2027 y documento de respaldo por parámetro. |
| RN-6.1 | Parcial | CTR-01 toma su periodo de parámetro y detecta candidatas de quinto mediante `idsUnidadesPretendidas` o `idsUnidadesCandidatas`; no usa semestre de referencia como sustituto. | C-10 validará la carga propuesta y las pendientes ofertadas/eligibles. |

## Casos dorados ejecutados

### GC-04 - Obligatoria nunca cursada y no ofertada

Entrada sintética: referencia 5, una obligatoria S3 (`OB33`) `NO_CURSADA` sin oferta, candidatas S5 `OB51` y `OB53`; `OB33 -> OB53` es seriación directa.

| UD | Estado de entrada | Oferta | Salida observada | Motivo / alcance |
|---|---|---|---|---|
| `OB33` | `NO_CURSADA` | No | Habilitada para consulta, pendiente y sin oferta | `SIN_OFERTA`; no incrementa reprobadas. |
| `OB51` | `NO_INSCRITA` | Sí | Habilitada | Candidata S5 sin antecedente pendiente. |
| `OB53` | `NO_INSCRITA` | Sí | Bloqueada | `SERIACION_DIRECTA` por `OB33`. |

Resultado global: `totalReprobadas=0`; RN-6.1 se evalúa sobre el universo completo disponible. El acceso es condicionado: la falta de oferta mantiene pendiente a `OB33`; no se propaga esa condición a `OB51`.

### GC-05 - Semestre de referencia

| Periodo | Evidencia | Salida |
|---|---|---|
| `2025-1` | Inscripción formal | Contado |
| `2025-2` | Inscripción formal | Contado |
| `2026-1` | Baja temporal completa | Excluido: `BAJA_TEMPORAL` |
| `2026-2` | Registro de periodo sin evidencia de cursamiento | Excluido: `INSCRIPCION_SIN_EVIDENCIA_DE_CURSAMIENTO` |
| `2027-1` | Inscripción formal | Contado |

Salida: `semestreReferencia=4`, `estado=DISPONIBLE`, `calidadDato=VERIFICADA`. Incluir una UD de quinto no cambia esa referencia.

### GC-12 - Fuera de ámbito

Entrada sintética: plan `612301` sin pertenencia comprobada a PE2024/2027.

| UD | Salida |
|---|---|
| Cualquier UD del contexto | No se ejecutan RN PE2024/2027; resultado global `REQUIERE_REVISION`. |

Motivo funcional: `NO_APLICA_RN_PE2024_2027` como estado de ámbito. El resultado no infiere homologación por nombre ni aplica RN-6.1 o RN-6.4.

## Pruebas

`Bloque1FundamentosRn20261105Test` cubre C-01, C-02, C-03 y los tres casos dorados. Comprueba que el evaluador no invoca `semestreObjetivo` ni `semestreMaximoCandidatas`. La suite V2 completa reportó `766` pruebas, `0` fallas, `0` errores y `0` omitidas.

## Ejecución productiva anonimizada

La base local responde, pero el artefacto actualmente desplegado es anterior a estos cambios y no existe un ejecutor autenticado de servicio para invocar el adaptador productivo contra un expediente anonimizado desde la terminal. Por ello GC-04/GC-05 se ejecutaron con datos preparados a través del adaptador y motor reales; queda pendiente desplegar el WAR de este commit y ejecutar el runner controlado con identificadores anonimizados. No se reporta esa ejecución como realizada.

## Fuera de alcance deliberado

No se implementaron C-04 a C-17, en particular estados de oportunidad, NP, optativas, preselección, rezago severo, validación de carga, seriación de salida, trayectorias, medidas administrativas ni trazabilidad ampliada.
