# Resultado Fase 6E - Reglas V2

- `FASE_IMPLEMENTADA`: FASE 6E
- `REGLA`: RN-6.8
- `VERSION`: PE2027-V1
- `CAMBIO_PRODUCTIVO`: NINGUNO
- `MODO`: sombra

## Alcance implementado

RN-6.8 incorpora una evaluacion de cierre academico dentro del motor V2. No modifica inscripcion, reinscripcion, grupos, cupos, oferta, estados productivos de UD, Malla Curricular, Asistente ni Simulador. El resultado se conserva exclusivamente en `ResultadoEvaluacionCurricularV2DTO` mediante `EvaluacionCargaAcademicaV2DTO`.

La evaluacion se ejecuta despues de RN-6.2, RN-6.5, RN-6.6 y RN-6.7. Reutiliza el estado vigente normalizado que ya determina RN-1.2, la carga de RN-4.x, la prioridad de reprobadas de RN-6.3, la restriccion de RN-6.4 y el motor de seriacion existente. No vuelve a clasificar situacion academica ni reimplementa dichas reglas.

## RECALCULO_AL_CIERRE

El contexto V2 recibe `cierrePeriodoAcademicoComprobado`. Sin evidencia de cierre RN-6.8 no genera un resultado de actualizacion; si el dato es desconocido devuelve `REQUIERE_REVISION`. Con cierre comprobado, el resultado conserva el delta entre los campos previos del contexto y el resultado vigente calculado:

- situacion academica anterior y vigente;
- total de reprobadas anterior y vigente;
- RN-6.4 antes y despues;
- UD acreditadas durante el cierre;
- bloqueos de continuidad resueltos y pendientes.

La trayectoria curricular usa el estado vigente. Una UD historicamente reprobada que al cierre esta `ACREDITADA` no se cuenta como reprobada vigente. Los antecedentes e intentos se conservan en los contratos existentes para reglas que los requieren.

## SEMESTRE_NON_CURRICULAR Y SEMESTRE_PAR_SIGUIENTE

La parte especifica de continuidad se activa solo cuando `semestreCurricularCierre` es non y `semestreObjetivo` es exactamente el par inmediato siguiente. La paridad se evalua sobre el semestre curricular individual, no sobre el periodo calendario ni sobre el numero de reinscripcion.

Un cierre en semestre par conserva el recalcule general, pero queda como `NO_APLICA_ESPECIFICO_NON_PAR`. Si no se conoce el semestre curricular, o el objetivo no corresponde al par siguiente de la trayectoria, la decision es `REQUIERE_REVISION`.

## REGULARIZACION_RECONOCIDA

La regularizacion requiere evidencia de los bloqueos existentes antes del cierre. Cada UD marcada como bloqueante debe contar con estado anterior y evidencia de su relacion con la continuidad. Solo se marca como bloqueo resuelto si su estado vigente es `ACREDITADA`.

Una UD `EN_CURSO`, `REPROBADA`, `NO_CURSADA`, `NO_INSCRITA` o `NO_DETERMINADO` no permite declarar regularizacion para esa UD. La evaluacion no infiere que toda pendiente fuera un bloqueo de continuidad.

## REPROBADAS_PAR_INMEDIATO_ANTERIOR Y PRIORIDAD_RN_6_3

RN-6.8 identifica las reprobadas vigentes que tienen evidencia de pertenecer al semestre par inmediato anterior. Entre una y tres reprobadas ofertadas quedan registradas como prioritarias y pueden ingresar primero a la ruta sombra. La regla consume el estado normalizado y la disponibilidad individual, sin transformar `NO_CURSADA`, `NO_INSCRITA` o `BAJA_PARCIAL` en reprobacion.

La prioridad se apoya en RN-6.3; RN-6.8 no crea otro mecanismo de prioridad. Si una reprobada no tiene evidencia de su procedencia curricular, se conserva la necesidad de revision en vez de asumir que pertenece al semestre par inmediato anterior.

## TRANSICION_RN_6_4

RN-6.4 se conserva como restriccion concurrente y usa solo reprobadas vigentes reales. Por ello una transicion de cuatro a tres reprobadas al cierre deja de mantener artificialmente RN-6.4; una transicion de tres a cero elimina la prioridad de reprobadas satisfecha. Cuatro o mas reprobadas vigentes conservan `RESTRINGIDA_POR_RN64`.

## OFERTA_VS_CUPO, SERIACION Y CARGA

`SIN_OFERTA` y `SIN_CUPO` se registran por separado. Una reprobada sin oferta sigue pendiente, y una UD ofertada sin cupo no se clasifica como falta de oferta.

Despues de la prioridad de reprobadas se evalua cada UD obligatoria `NO_INSCRITA` del semestre par objetivo. La seriacion directa o indirecta bloquea solo la UD afectada; las demas candidatas compatibles siguen evaluandose. La ruta se limita con la carga maxima ya calculada por RN-4.2/RN-4.3. Las optativas y electivas que requieran una regla especifica de RN-7 quedan en revision; Fase 6E no anticipa RN-7.x.

## NO_REACTIVACION_RN_6_1

La continuidad non a par no reactiva RN-6.1 por el solo cambio de semestre. RN-6.8 no vuelve a ejecutar CTR-01 ni RN-6.1.1 a RN-6.1.6; en la evidencia se registra `rn61=NO_REACTIVADA_NON_PAR`.

## CONTRATO_RESULTADO_V2

Se agregaron al contrato existente, sin DTO paralelo:

- en `ContextoEvaluacionCurricularV2DTO`: evidencia de cierre, semestre curricular de cierre, bloqueos previos comprobados y valores anteriores para el delta;
- en `UnidadEvaluacionCurricularV2DTO`: estado anterior, acreditacion durante el periodo, evidencia de bloqueo y pertenencia al semestre par anterior;
- en `EvaluacionCargaAcademicaV2DTO`: aplicabilidad, estado RN-6.8, delta, listas de UD, ruta, decisiones y revision propia de RN-6.8;
- `EstadoActualizacionTrayectoriaRn68V2`: estados descriptivos de cierre y continuidad en sombra.

El estado global puede ser `NO_APLICA_ESPECIFICO_NON_PAR`, `RECALCULO_REALIZADO_EN_SOMBRA`, `REGULARIZACION_RECONOCIDA_EN_SOMBRA`, `CONTINUIDAD_A_SEMESTRE_PAR_PROCEDE_EN_SOMBRA`, `CONTINUIDAD_CON_PRIORIDAD_REGULARIZACION`, `RESTRINGIDA_POR_RN64`, `REQUIERE_REVISION` o `NO_DETERMINADO`.

## TRAZABILIDAD E IDEMPOTENCIA

RN-6.8 agrega traza con la version normativa, cierre, semestre, delta de situacion y reprobadas, RN-6.4 antes/despues, bloqueos, prioridades y ruta. Cada decision por UD se conserva en `decisionesPorUdRn68`. Las listas usan incorporacion unica de identificadores, por lo que una evaluacion repetida con el mismo contexto no duplica prioridades ni ruta. No se agrega informacion personal adicional.

## PRUEBAS

`EvaluacionCurricularV2Fase6ETest` cubre 16 escenarios:

1. ausencia de cierre;
2. semestre de cierre no determinable;
3. cierre curricular par;
4. acreditacion vigente y bloqueo resuelto;
5. UD en curso sin regularizacion declarada;
6. estado vigente frente a antecedente historico;
7. prioridad de una a tres reprobadas ofertadas;
8. diferenciacion sin oferta y sin cupo;
9. transicion cuatro a tres y liberacion de RN-6.4;
10. cuatro reprobadas vigentes;
11. transicion a cero reprobadas;
12. seriacion individual y UD compatibles;
13. limite de carga;
14. no reactivacion de RN-6.1 non a par;
15. optativa pendiente de regla RN-7;
16. determinismo, idempotencia y ausencia de efecto productivo.

## Riesgos y limites

- La formalizacion del cierre, la procedencia curricular de una reprobada y los bloqueos previos deben llegar como evidencia al contexto V2; ante ausencia de dichos datos el motor conserva `REQUIERE_REVISION`.
- La semantica de optativas y electivas no se infiere durante Fase 6E y se reserva para RN-7.x.
- El contrato esta disponible solo en sombra hasta una fase posterior de integracion con Malla, Asistente y Simulador.

## Archivos modificados

- `ContextoEvaluacionCurricularV2DTO.java`
- `UnidadEvaluacionCurricularV2DTO.java`
- `EvaluacionCargaAcademicaV2DTO.java`
- `EstadoActualizacionTrayectoriaRn68V2.java`
- `EvaluacionCurricularV2ServiceImpl.java`
- `EvaluacionCurricularV2Fase6ETest.java`

## Resultado

RN-6.8 queda implementada exclusivamente en sombra. No se inicio Fase 6F, Fase 7 ni RN-7.x.
