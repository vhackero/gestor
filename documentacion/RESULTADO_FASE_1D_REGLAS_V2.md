# Resultado de FASE 1D — RN-9.5 Trazabilidad de evaluación V2

## FASE_IMPLEMENTADA

FASE 1D implementa la persistencia de trazabilidad para las evaluaciones V2 en modo sombra. La ejecución se mantiene en la rama `feature/reglas-negocio-pe2027`; el motor vigente sigue siendo la única fuente de decisiones productivas y el Gate E2E de activación continúa pendiente.

## REGLA_IMPLEMENTADA

**RN-9.5 — Trazabilidad de reglas y excepciones.** Cada evaluación V2 invocada por la ruta explícita de sombra puede registrar un encabezado técnico, UD normalizadas, reglas evaluadas, decisiones sombra y excepciones. La traza se ejecuta en una transacción independiente (`REQUIRES_NEW`); si falla, el llamador conserva el resultado sombra y agrega un mensaje técnico sin alterar la inscripción vigente.

## MODELO_DE_TRAZABILIDAD

| Nivel | Datos registrados | Privacidad |
| --- | --- | --- |
| Encabezado | ID técnico de persona, plan, periodo, proceso opcional, semestre/bloque, origen, versión, modo sombra, huella de oferta, hash de contexto, situación y total de reprobadas. | No se guardan nombre, correo, CURP ni credenciales. |
| UD | ID/clave, tipo, semestre, bloque, estado, causa, oferta y seleccionabilidad origen, oferta/decisión sombra, prioridad, seriación y referencias técnicas. | No se persiste el nombre de UD ni atributos personales. |
| Regla | Código, motivo, prioridad, evaluada, aplicada, resultado, detalle y versión. | Sólo códigos y detalle técnico de evaluación. |
| Excepción | Código, tipo, estado, regla origen, condiciones, motivos, IDs de UD, evidencia técnica, explicación, versión y fecha. | No utiliza texto libre como identificador ni contiene PII. |

La huella `hash_contexto` se calcula con SHA-256 a partir de identificadores técnicos, versión, origen, oferta y estados normalizados. No incluye nombres ni mensajes de usuario. La idempotencia reutiliza un encabezado con la misma persona, plan, versión, origen y hash; por ello una reevaluación idéntica no duplica accidentalmente UD, reglas ni excepciones.

## TABLAS_UTILIZADAS

- `tbl_evaluacion_curricular_v2`: encabezado de evaluación.
- `tbl_evaluacion_curricular_ud_v2`: representación por UD.
- `tbl_evaluacion_curricular_regla_v2`: reglas y trazas concurrentes.
- `tbl_evaluacion_curricular_excepcion_v2`: nueva tabla mínima necesaria para excepciones estructuradas de CTR-02; evita mezclar su información en texto libre de reglas.

## CAMBIOS_BD

Script: `codigoFuente/bd/40_fase1d_trazabilidad_evaluacion_curricular_v2_20261006.sql`.

Se verificó la estructura vigente en DEV local antes de cambiarla. La migración se ejecutó únicamente en esa base DEV confirmada y fue exclusivamente aditiva:

- agrega hash, resultado, estado y total de reprobadas al encabezado;
- agrega origen/decisión/seriación detallada a la traza por UD;
- agrega marca de evaluación y variables técnicas a la traza por regla;
- crea la tabla estructurada de excepciones;
- no contiene `UPDATE` ni `DELETE`;
- no modifica tablas de inscripción, calificaciones, participaciones o historial académico.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/AsistenteInscripcionServiceImpl.java`
- `codigoFuente/basegestor/src/main/resources/META-INF/persistence.xml`

## ARCHIVOS_CREADOS

- `codigoFuente/bd/40_fase1d_trazabilidad_evaluacion_curricular_v2_20261006.sql`
- `TblEvaluacionCurricularV2.java`
- `TblEvaluacionCurricularUdV2.java`
- `TblEvaluacionCurricularReglaV2.java`
- `TblEvaluacionCurricularExcepcionV2.java`
- `TblEvaluacionCurricularV2Repo.java`
- `TblEvaluacionCurricularUdV2Repo.java`
- `TblEvaluacionCurricularReglaV2Repo.java`
- `TblEvaluacionCurricularExcepcionV2Repo.java`
- `TrazabilidadEvaluacionCurricularV2Service.java`
- `TrazabilidadEvaluacionCurricularV2ServiceImpl.java`
- `TrazabilidadEvaluacionCurricularV2ServiceTest.java`

## PRUEBAS_AGREGADAS

F1D-U-01 a F1D-U-14 validan: encabezado, reglas concurrentes, decisiones múltiples, excepción tipificada, excepción extraordinaria, proceso nulo, motivos múltiples, idempotencia, fallo de persistencia sin alterar resultado sombra, ausencia de PII, reconstrucción por campos técnicos, oferta origen intacta, evaluación sin excepción y estado `NO_DETERMINADO` sin inferencia.

## PRUEBAS_EJECUTADAS

```bash
cd codigoFuente/basegestor
mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest test
```

Resultado: **59 pruebas ejecutadas, 0 fallas, 0 errores, 0 omitidas**.

Compilación ejecutada:

```bash
cd codigoFuente/basegestor && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
cd codigoFuente/gestorWeb && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
```

Resultado: ambos módulos compilaron correctamente.

## RESULTADO

RN-9.5 queda implementada para el flujo de evaluación V2 en sombra. Se conserva la traza suficiente para reconstruir contexto, reglas, UD, decisiones y excepciones sin recalcular información ni modificar la oferta o inscripción vigentes.

## RIESGOS

- La ruta Spring/JPA completa debe validarse tras desplegar el WAR en DEV, con una evaluación sombra real y consulta de las cuatro tablas. La migración y las pruebas unitarias ya validaron el esquema y el mapeo aislado.
- El mecanismo de idempotencia evita duplicados accidentales para el mismo hash de contexto; una reevaluación deliberada con cambios de contexto genera una nueva ejecución.
- El Gate E2E productivo sigue pendiente; esta fase no habilita activación V2.

## CAMBIO_PRODUCTIVO

**NINGUNO.** No se modificaron oferta real, seleccionabilidad, carga, inscripción, bloqueos productivos, mapa, CTR-01, RN-4.1, RN-6.1 o RN-6.4. No se inició FASE 1E.
