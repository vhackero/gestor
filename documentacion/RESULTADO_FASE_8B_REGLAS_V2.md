# Resultado Fase 8B: RN-8.2

## FASE_IMPLEMENTADA

`FASE 8B` implementa `RN-8.2`, seriación intersemestral, utilizando el modelo común de seriación V2 introducido en Fase 8A. La evaluación conserva `PE2027-V1`, opera exclusivamente en sombra y no produce selección, inscripción, ocultamiento de UD, modificación de oferta, cupo, grupos ni efectos sobre Malla Curricular, Asistente o Simulador.

## REGLA

Una relación intersemestral sólo se evalúa cuando existe una dependencia formal del Programa Educativo (PE) y el semestre curricular del consecuente equivale al semestre del antecedente más uno. Ejemplos posibles: S1→S2, S2→S3, S3→S4, S4→S5, S5→S6, S6→S7 y S7→S8. La consecutividad sola no crea una relación de seriación.

## SERIACION_INTERSEMESTRAL

El adaptador V2 ya recibe las relaciones `requiere` definidas por el PE. Fase 8B clasifica una de esas relaciones como `INTERSEMESTRAL` únicamente cuando sus semestres curriculares son consecutivos. Las relaciones existentes que no corresponden a B1→B2 del mismo semestre ni a semestres consecutivos conservan `NO_DETERMINADA` y se presentan para revisión; no se corrige ni se infiere el catálogo.

## MODELO_COMUN_REUTILIZADO

RN-8.2 reutiliza sin crear motor paralelo:

- `RelacionSeriacionCurricularV2DTO` para antecedente, consecuente, semestres, bloques, estado, decisión, motivo y evidencia.
- `TipoSeriacionV2` para distinguir `INTERBLOQUE`, `INTERSEMESTRAL`, `INTERANUAL` y `NO_DETERMINADA`.
- `TipoRelacionSeriacionV2` para distinguir la dependencia directa de una indirecta.
- `EstadoCumplimientoSeriacionV2` para expresar cumplimiento, incumplimiento y revisión.
- Normalización de estados de UD, decisiones por UD y trazabilidad ya existentes.

La extensión de RN-8.2 se concentra en la orquestación del mismo servicio V2 y el contrato ya expuesto por `EvaluacionCargaAcademicaV2DTO`.

## SEMESTRES_CONSECUTIVOS

La coherencia se valida usando `semestreConsecuente = semestreAntecedente + 1`. No se utilizan periodo calendario, año de inscripción, bloque, orden de consulta ni número de reinscripción. Una relación declarada `INTERSEMESTRAL` con semestres no consecutivos genera `REQUIERE_REVISION`, preservando la relación y su evidencia del PE.

## RELACION_FORMAL_PE

La relación se origina en `requiere` de la estructura curricular. S3 y S4 consecutivos sin una relación formal no activan RN-8.2. El identificador del antecedente es curricular y estable; no se resuelve por coincidencia de nombre, código similar o cualquier UD del semestre previo.

## ACREDITACION_PREVIA

El antecedente vigente `ACREDITADA` produce `CUMPLIDA`. La acreditación puede pertenecer a un periodo administrativo anterior: lo relevante es que la UD antecedente formal se encuentre acreditada al momento de evaluar. Una reprobación histórica seguida por acreditación vigente no bloquea la relación actual.

## DIFERENCIA_RN_8_1

RN-8.1 permite que el antecedente B1 `EN_CURSO` produzca `CONDICIONADA`, porque B1 y B2 forman parte del mismo periodo y la regla admite acreditación durante éste. RN-8.2 exige acreditación previa para el semestre consecuente: `EN_CURSO` produce `NO_CUMPLIDA`, no `CONDICIONADA`.

La batería F8B verifica ambos comportamientos en el mismo motor y confirma que no se confunden los tipos `INTERBLOQUE` e `INTERSEMESTRAL`.

## EN_CURSO_NO_CUMPLE

Para RN-8.2, los estados vigentes se interpretan así:

- `ACREDITADA`: `CUMPLIDA`.
- `REPROBADA`, `NO_CURSADA`, `BAJA_PARCIAL`, `EN_CURSO` y `NO_INSCRITA`: `NO_CUMPLIDA`.
- `NO_DETERMINADO`, ausencia del antecedente o configuración incompleta: `REQUIERE_REVISION`.

`NO_CURSADA`, `BAJA_PARCIAL` y `NO_INSCRITA` mantienen su causa real; RN-8.2 no las convierte en reprobación.

## MULTIPLES_ANTECEDENTES

Cada antecedente obligatorio se conserva como una relación individual. Una consecuente con varios antecedentes queda plenamente satisfecha sólo si todos están acreditados. Si una relación permanece no cumplida, el consecuente conserva ese bloqueo. No se incorporó una alternativa `OR` porque las fuentes aprobadas no la establecen. Duplicados técnicos de la misma relación se procesan una sola vez.

## ESTADO_VIGENTE_VS_HISTORICO

La evaluación actual usa el estado normalizado vigente. Las referencias históricas se mantienen para auditoría e intentos, pero una reprobación histórica ya superada no bloquea la relación. Esta regla evita arrastrar bloqueos curriculares inexistentes.

## OFERTA_VS_SERIACION

Oferta no equivale a seriación. Una UD consecuente puede estar ofertada y no cumplir su antecedente; también puede no estar ofertada y tener seriación cumplida. El motor no convierte bloqueo de seriación en `SIN_OFERTA` ni modifica la oferta de origen.

## CUPO_VS_SERIACION

`SIN_CUPO` conserva su propio motivo. Una relación intersemestral cumplida no se degrada por falta de capacidad, y una UD con cupo no supera por ello la falta de acreditación previa.

## CARGA_VS_SERIACION

RN-8.2 reutiliza los resultados de RN-4.1, RN-4.2 y RN-4.3. Cumplir seriación no autoriza exceder carga y contar con espacio no permite ignorar el antecedente. No se codifican límites de carga dentro de RN-8.2.

## RN_6_4_VS_SERIACION

RN-6.4 y seriación son restricciones concurrentes. Una relación puede quedar `CUMPLIDA` y, simultáneamente, existir restricción por cuatro o más reprobadas. La decisión no atribuye ese caso a un incumplimiento de seriación.

## CONCURRENCIA_RN_6_1

Para S4→S5 desde `2027-1`, RN-8.2 y RN-6.1 se consumen de forma independiente. Una relación cumplida no sustituye una restricción de Control de Avance Anual; una decisión favorable RN-6.1 no elimina una relación intersemestral no cumplida. Para S5→S6, RN-6.1 no se reactiva por RN-8.2.

## AUDITORIA_DUPLICACION

`REUTILIZADO`: DTO de relación, enums, normalización de estado, motivos, decisiones por UD, trazas, carga, oferta/cupo, RN-6.4 y RN-6.1.

`EXTENDIDO`: clasificación de relaciones formales consecutivas como `INTERSEMESTRAL` y listas contractuales de cumplimiento RN-8.2.

`ORQUESTADO`: evaluación de acreditación previa, concurrencia y resultado por consecuente dentro de `EvaluacionCurricularV2ServiceImpl`.

No se creó un `MotorSeriacionIntersemestralV2`, una ruta productiva paralela ni una nueva fuente de datos.

## CONTRATO_RESULTADO_V2

El contrato expone `aplicaRn82`, `requiereRevisionRn82`, consecuentes cumplidas, no cumplidas y en revisión, decisiones por UD y la lista común de relaciones. Cada relación incluye tipo `INTERSEMESTRAL`, tipo de relación, antecedente, consecuente, semestres, estado del antecedente, decisión, motivo y evidencia del PE.

Las decisiones por UD continúan incorporando oferta, cupo, carga, RN-6.4, RN-6.1 y otros motivos sin duplicar la UD ni ocultar restricciones.

## TRAZABILIDAD

Cada relación RN-8.2 registra traza con la clave de relación curricular, estado vigente del antecedente y resultado. La traza consolidada contiene listas de cumplidas, no cumplidas y en revisión. Se conserva `PE2027-V1` desde el contexto y no se incorpora PII.

## PRUEBAS

`EvaluacionCurricularV2Fase8BTest` contiene 22 escenarios: ausencia de relación, S3→S4 formal, acreditación, reprobación, no cursada, baja parcial, no inscrita, en curso, estado no determinado, histórico frente a vigente, semestres no consecutivos, consecutividad sin PE, múltiples antecedentes, oferta/cupo, RN-6.4, concurrencia S4→S5 con RN-6.1, S5→S6, relación indirecta conservada para fase posterior, deduplicación, transiciones, no regresión RN-8.1, determinismo, idempotencia y ausencia de efecto productivo.

## RIESGOS

- RN-8.2 requiere identificadores y semestres verificables desde la estructura del PE.
- Las relaciones indirectas se preservan como revisión; no se implementó su semántica interanual de RN-8.3.
- No existe lógica alternativa documentada para múltiples antecedentes.
- La visualización u ocultamiento de UD permanece fuera de alcance: Malla, Asistente y Simulador son consumidores futuros.

## ARCHIVOS_MODIFICADOS

- `ContextoEvaluacionCurricularV2AdapterImpl`: clasificación intersemestral de relaciones formales consecutivas.
- `EvaluacionCargaAcademicaV2DTO`: contrato de resultados RN-8.2.
- `EvaluacionCurricularV2ServiceImpl`: orquestación RN-8.2 con el modelo común F8A.
- `EvaluacionCurricularV2Fase8BTest`: pruebas de Fase 8B.

## RESULTADO

`CAMBIO_PRODUCTIVO = NINGUNO`.

RN-8.2 queda implementada y trazable en modo sombra. RN-8.3, Fase 8C, Fase 8D y Fase 9 no fueron iniciadas.
