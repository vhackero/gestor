# Resultado Fase 8A: RN-8 y RN-8.1

## FASE_IMPLEMENTADA

`FASE 8A` consolida el modelo común de seriación V2 y evalúa exclusivamente la seriación interbloque de `RN-8.1`. La evaluación permanece en modo sombra bajo `PE2027-V1`; no modifica inscripción, reinscripción, oferta, grupos, cupos, Malla Curricular, Asistente ni Simulador.

## REGLAS

- `RN-8`: una dependencia curricular sólo se reconoce a partir de una relación explícita del Programa Educativo (PE).
- `RN-8.1`: relación interbloque directa entre un antecedente de bloque 1 y un consecuente de bloque 2 del mismo semestre curricular.
- `RN-8.2` y `RN-8.3` no fueron implementadas. Las relaciones con ámbito aún no tipificado se conservan como revisión y no se reinterpretan como intersemestrales o interanuales.

## AUDITORIA_MOTOR_SERIACION_EXISTENTE

La auditoría identificó infraestructura V2 reutilizable en `UnidadEvaluacionCurricularV2DTO`, `ContextoEvaluacionCurricularV2AdapterImpl`, `EvaluacionCurricularV2ServiceImpl`, decisiones por UD y trazas. Las fases previas consumían banderas de seriación directa, indirecta y conocida para RN-6.1, RN-6.2, RN-6.5 a RN-6.8 y RN-7.2. Esa infraestructura se conserva.

La brecha identificada era contractual: el adaptador conocía `requiere` desde la estructura del PE, pero no trasladaba al contexto V2 el identificador, semestre y bloque del antecedente. Se extendió aditivamente el DTO de unidad con relaciones explícitas. No se modificó `InscripcionFacadeImpl`, ni se reutilizaron sus validaciones productivas como fuente de decisión V2.

Clasificación de auditoría:

- `REUTILIZABLE`: normalización de UD, estado vigente, decisiones sombra, trazas, oferta/cupo, carga, RN-6.4 y relación directa/indirecta existente.
- `REQUIERE_EXTENSION`: transporte de antecedente/consecuente, bloques y resultado por relación.
- `OBSOLETO PARA V2`: ninguna pieza fue sustituida; los flujos productivos heredados quedan fuera del motor V2.
- `DUPLICADO`: no se creó un segundo motor de seriación; RN-8.1 orquesta la infraestructura común.

## MODELO_COMUN_SERIACION

`RelacionSeriacionCurricularV2DTO` representa una relación de sólo lectura con: identificadores de antecedente y consecuente, semestre y bloque de ambos extremos, tipo de seriación, tipo de relación, estado vigente del antecedente, decisión, motivo y evidencia del PE.

Los valores del contrato son independientes:

- `TipoSeriacionV2`: `INTERBLOQUE`, `INTERSEMESTRAL`, `INTERANUAL`, `NO_DETERMINADA`.
- `TipoRelacionSeriacionV2`: `DIRECTA`, `INDIRECTA`, `NO_EXISTE`, `NO_DETERMINADA`.
- `EstadoCumplimientoSeriacionV2`: `CUMPLIDA`, `NO_CUMPLIDA`, `CONDICIONADA`, `NO_APLICA`, `REQUIERE_REVISION`.

## TIPO_SERIACION_VS_TIPO_RELACION

El tipo de seriación identifica el ámbito temporal curricular; la relación directa o indirecta describe la dependencia. El contrato no presupone que todo ámbito corresponda a un único tipo de relación. Fase 8A evalúa únicamente relaciones explícitas `INTERBLOQUE` y `DIRECTA`; conserva las demás para fases autorizadas posteriores.

## SERIACION_INTERBLOQUE

RN-8.1 sólo se activa cuando el PE aporta una relación directa y verificable `B1 -> B2` del mismo semestre. La ausencia de relación no crea bloqueo: RN-8.1 queda `NO_APLICA`, sin declarar elegibilidad global. Una relación declarada interbloque pero con identificadores, bloques o semestres incompletos queda en `REQUIERE_REVISION`.

## ANTECEDENTE_B1 Y CONSECUENTE_B2

El adaptador convierte cada entrada `requiere` de la estructura de la UD en una relación V2. Se marca `INTERBLOQUE` sólo si el antecedente pertenece al bloque 1 y el consecuente al bloque 2 del mismo semestre. La evaluación usa el identificador curricular del antecedente, no una coincidencia por nombre, código parecido, semestre o una UD cualquiera del bloque 1.

## ACREDITACION_ANTECEDENTE

La inscripción definitiva no se ejecuta en esta fase. En sombra, el antecedente `ACREDITADA` produce `CUMPLIDA`, tanto si la acreditación ocurrió en periodo previo como en el actual. Si existen varios antecedentes, cada relación se evalúa y la consecuente conserva todas las decisiones; cualquier antecedente no acreditado mantiene una relación `NO_CUMPLIDA`.

## ESTADO_CONDICIONADO

Un antecedente `EN_CURSO` del bloque 1 produce `CONDICIONADA_A_ACREDITACION_ANTECEDENTE_B1`. No se declara seriación cumplida ni autorización definitiva. Estados `REPROBADA`, `NO_CURSADA` y `BAJA_PARCIAL` producen `NO_CUMPLIDA`; las dos últimas causas no se convierten en reprobación. `NO_DETERMINADO` o un antecedente ausente del contexto produce `REQUIERE_REVISION`.

## ESTADO_VIGENTE_VS_HISTORICO

La decisión actual se determina con el estado normalizado vigente del antecedente. Una reprobación histórica seguida por acreditación vigente deja la relación `CUMPLIDA`. El historial no se elimina y continúa disponible en las referencias de la UD para auditoría e intentos.

## MULTIPLES_ANTECEDENTES

Las relaciones se deduplican por antecedente, consecuente, ámbito y tipo de relación. No se duplica una dependencia por repeticiones técnicas. Para una misma consecuente, se conservan las relaciones individuales: dos antecedentes acreditados cumplen; uno pendiente conserva el incumplimiento correspondiente. No se introdujo lógica alternativa `OR` porque no existe evidencia normativa de ella.

## OFERTA_VS_SERIACION

Oferta y seriación se preservan como dimensiones independientes. Una B2 ofertada puede quedar `NO_CUMPLIDA` por antecedente pendiente. Una B2 sin oferta puede tener la relación `CUMPLIDA`. La evaluación no modifica oferta ni convierte `SIN_OFERTA` en un resultado de seriación.

## CUPO_VS_SERIACION

`SIN_CUPO` no equivale a incumplimiento de seriación. Una relación cumplida conserva dicha decisión aunque exista una restricción de capacidad; los motivos coexisten en la decisión sombra por UD.

## CARGA_VS_SERIACION

RN-8.1 no recalcula carga. RN-4.1, RN-4.2 y RN-4.3 continúan siendo la única fuente V2 de carga. Cumplir seriación no habilita superar la carga máxima y contar con carga disponible no elimina un antecedente pendiente.

## RESTRICCIONES_CONCURRENTES

RN-6.4 y la seriación son independientes. Una B2 puede tener la relación `CUMPLIDA` y, simultáneamente, estar restringida por RN-6.4, falta de cupo, carga o cualquier motivo ya calculado. Del mismo modo, una relación no cumplida no borra los demás motivos. Esta composición conserva CTR-04.

## AUDITORIA_DUPLICACION

RN-8.1 reutiliza los DTO V2 de unidad, decisión y carga, el normalizador, el registro de evidencias y el mecanismo de trazas. No se agregaron rutas paralelas de selección ni contadores de bloqueo. Las banderas directas/indirectas existentes siguen siendo consumidas por reglas previas; la relación explícita añade la información necesaria para explicar RN-8.1 sin alterar esos consumidores.

## CONTRATO_RESULTADO_V2

`EvaluacionCargaAcademicaV2DTO` expone: aplicabilidad y revisión de RN-8.1, relaciones evaluadas, consecuentes cumplidas, condicionadas, no cumplidas y en revisión, además de decisiones por UD. Cada relación contiene antecedente, consecuente, bloques, semestre, tipo de seriación, tipo de relación, estado del antecedente, decisión, motivo y evidencia.

La decisión por UD conserva en paralelo oferta, cupo, carga, RN-6.4 y demás restricciones previamente calculadas. No se creó un DTO paralelo de resultado ni se agrega persistencia.

## TRAZABILIDAD

Cada decisión individual incorpora traza `RN_8_1` con la relación del PE y el estado del antecedente. Las relaciones de ámbito no determinado quedan trazadas por `RN_8` como `REQUIERE_REVISION`. La traza consolidada de RN-8.1 contiene listas de relaciones cumplidas, condicionadas, no cumplidas y en revisión, con versión normativa tomada del contexto.

## PRUEBAS

`EvaluacionCurricularV2Fase8ATest` cubre 22 escenarios: ausencia de relación, acreditación previa y actual, estado en curso, reprobación, no cursada, baja parcial, estado no determinado, histórico frente a vigente, independencia de oferta/cupo/RN-6.4, múltiples antecedentes, configuración incompleta, tipo no determinado, periodo inconsistente, duplicados, transiciones, determinismo, idempotencia y ausencia de efecto productivo.

## RIESGOS

- La fuente V2 sólo puede declarar RN-8.1 cuando la relación explícita del PE incluye identificadores, bloques y semestre verificables.
- Las relaciones sin ámbito tipificado se preservan para revisión; no se deduce RN-8.2 o RN-8.3.
- El modelo no resuelve lógicas alternativas entre múltiples antecedentes porque no existe regla aprobada que las defina.
- Malla Curricular, Asistente y Simulador permanecen como consumidores futuros; no se conectaron en esta fase.

## ARCHIVOS_MODIFICADOS

- `UnidadEvaluacionCurricularV2DTO`: relaciones de seriación del PE.
- `ContextoEvaluacionCurricularV2AdapterImpl`: transporte de antecedentes desde `requiere`.
- `EvaluacionCargaAcademicaV2DTO`: contrato consolidado RN-8.1.
- `EvaluacionCurricularV2ServiceImpl`: orquestación RN-8/RN-8.1 en sombra.
- Nuevos enums y DTO de relación de seriación V2.
- `EvaluacionCurricularV2Fase8ATest`: evidencia automatizada de Fase 8A.

## RESULTADO

`CAMBIO_PRODUCTIVO = NINGUNO`.

RN-8 y RN-8.1 quedan implementadas en sombra mediante un modelo común reutilizable. RN-8.2, RN-8.3, Fase 8B, Fase 8C, Fase 8D y Fase 9 permanecen fuera de alcance.
