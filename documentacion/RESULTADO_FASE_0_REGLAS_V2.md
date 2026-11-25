# Resultado de implementación — FASE 0 de Reglas V2

**Fecha de cierre técnico:** 2026-10-05
**Rama:** `feature/reglas-negocio-pe2027`
**Fuente funcional:** `documentacion/V2_REGLAS_APROBADAS.md`
**Plan autorizado:** `documentacion/PLAN_FASE_0_REGLAS_V2.md`

## FASE_IMPLEMENTADA

**FASE 0 — infraestructura transversal, en modo sombra.**

Se construyeron contratos de dominio V2, normalización en memoria, evaluación sombra, adaptación de sólo lectura desde el Asistente, configuración separada y pruebas automatizadas. No se implementaron decisiones académicas de las fases 1 a 10.

## REGLAS_IMPLEMENTADAS

No se activa una regla académica de elegibilidad, carga, oferta o selección en esta fase. Se implementó únicamente el sustrato técnico para trazarlas posteriormente:

- códigos estables `CTR-01` a `CTR-04` y `RN-1.1` a `RN-10.1`;
- estados y causas normalizados por UD;
- conservación de motivos concurrentes de evaluación sombra;
- configuración de versión normativa y vigencia sin activar CTR-01;
- distinción entre `EVALUACION_V2_HABILITADA` y `REGLAS_V2_MODO_SOMBRA`.

## ARCHIVOS_MODIFICADOS

### Archivos existentes

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/gestionescolar/AsistenteInscripcionService.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/AsistenteInscripcionServiceImpl.java`

### Archivos nuevos de contratos y servicios V2

- `.../commons/dto/gestionescolar/v2/EstadoUnidadCurricularV2.java`
- `.../commons/dto/gestionescolar/v2/CausaPendienteUnidadV2.java`
- `.../commons/dto/gestionescolar/v2/CodigoReglaCurricularV2.java`
- `.../commons/dto/gestionescolar/v2/CodigoMotivoCurricularV2.java`
- `.../commons/dto/gestionescolar/v2/ConfiguracionEvaluacionCurricularV2DTO.java`
- `.../commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java`
- `.../commons/dto/gestionescolar/v2/UnidadEvaluacionCurricularV2DTO.java`
- `.../commons/dto/gestionescolar/v2/DecisionUnidadCurricularV2DTO.java`
- `.../commons/dto/gestionescolar/v2/ResultadoEvaluacionCurricularV2DTO.java`
- `.../commons/dto/gestionescolar/v2/TrazaReglaAplicadaV2DTO.java`
- `.../service/gestionescolar/v2/ConfiguracionEvaluacionCurricularV2Service.java`
- `.../service/gestionescolar/v2/ContextoEvaluacionCurricularV2Adapter.java`
- `.../service/gestionescolar/v2/EvaluacionCurricularV2Service.java`
- `.../service/impl/gestionescolar/v2/ConfiguracionEvaluacionCurricularV2ServiceImpl.java`
- `.../service/impl/gestionescolar/v2/ContextoEvaluacionCurricularV2AdapterImpl.java`
- `.../service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`

### Pruebas y documentación de cierre

- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2ServiceTest.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/ContextoEvaluacionCurricularV2AdapterTest.java`
- `documentacion/REPORTE_DIFERENCIAS_VIGENTE_SOMBRA_FASE_0.md`
- `documentacion/RESULTADO_FASE_0_REGLAS_V2.md`

## MIGRACIONES

**Script propuesto, no ejecutado:**

`codigoFuente/bd/39_fase0_evaluacion_curricular_v2_sombra_20261005.sql`

El script es aditivo. Propone:

- la regla configurable `EVALUACION_CURRICULAR_V2` y sus parámetros;
- `tbl_evaluacion_curricular_v2` para encabezado de evaluación;
- `tbl_evaluacion_curricular_ud_v2` para detalle por UD;
- `tbl_evaluacion_curricular_regla_v2` para trazas por regla;
- índices para persona/plan, periodo/origen, proceso, oferta, UD y regla;
- FK hacia persona, plan, proceso de inscripción y ficha descriptiva de programa.

No contiene correcciones masivas de historial, calificaciones, participaciones o inscripciones. Antes de ejecutarlo debe verificarse que el ambiente destino ya tiene R047–R059 y que sus FK coinciden con el esquema local.

## PRUEBAS_AGREGADAS

| ID | Clase | Propósito | Resultado |
|---|---|---|---|
| F0-U-01 | `EvaluacionCurricularV2ServiceTest` | Estado acreditada y reprobada. | Aprobada |
| F0-U-02 | `EvaluacionCurricularV2ServiceTest` | En curso no se reclasifica como reprobada. | Aprobada |
| F0-U-03 | `EvaluacionCurricularV2ServiceTest` | Baja y no cursada conservan causa. | Aprobada |
| F0-U-04 | `EvaluacionCurricularV2ServiceTest` | Datos insuficientes se mantienen indeterminados. | Aprobada |
| F0-U-05 | `EvaluacionCurricularV2ServiceTest` | Deduplicación por UD con referencias preservadas. | Aprobada |
| F0-U-06 | `EvaluacionCurricularV2ServiceTest` | Motivos concurrentes en resultado sombra. | Aprobada |
| F0-U-07 | `EvaluacionCurricularV2ServiceTest` | Separación versión, vigencia y modo sombra. | Aprobada |
| F0-U-08 | `EvaluacionCurricularV2ServiceTest` | Reevaluación equivalente estable. | Aprobada |
| F0-I-01 | `ContextoEvaluacionCurricularV2AdapterTest` | Conservación de clave, persona, plan y estado. | Aprobada |
| F0-I-02 | `ContextoEvaluacionCurricularV2AdapterTest` | No modifica selección u oferta de origen. | Aprobada |
| F0-I-03 | `ContextoEvaluacionCurricularV2AdapterTest` | Contexto sin proceso activo. | Aprobada |
| F0-I-04 | `ContextoEvaluacionCurricularV2AdapterTest` | Sin elegibilidad nueva para caso representativo. | Aprobada |
| F0-I-05 | `ContextoEvaluacionCurricularV2AdapterTest` | Seriación trazable, sin cambio al origen. | Aprobada |
| F0-I-06 | `ContextoEvaluacionCurricularV2AdapterTest` | Diferenciación reprobada/baja/no cursada. | Aprobada |

## PRUEBAS_EJECUTADAS

```text
cd codigoFuente/basegestor
mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest test
Resultado: Tests run: 14, Failures: 0, Errors: 0, Skipped: 0

cd codigoFuente/basegestor
mvn -q -DskipTests -Dmaven.test.skip=true clean compile
Resultado: exitoso

cd codigoFuente/gestorWeb
mvn -q -DskipTests -Dmaven.test.skip=true clean compile
Resultado: exitoso
```

La primera ejecución de las pruebas con anotaciones JUnit no fue descubierta por el Surefire configurado con TestNG; las clases nuevas fueron ajustadas al patrón efectivo de TestNG y la ejecución posterior fue satisfactoria. No representa una falla funcional.

## RESULTADO

La evaluación V2 está disponible como consulta explícita mediante `obtenerEvaluacionCurricularSombra(Long idPersona)`. Su ejecución construye un contexto nuevo en memoria y devuelve trazas/motivos V2. No se invoca desde UI, mapa, simulador, validador ni proceso real de inscripción.

El reporte detallado se encuentra en `documentacion/REPORTE_DIFERENCIAS_VIGENTE_SOMBRA_FASE_0.md`.

## RIESGOS_PENDIENTES

1. La migración no fue ejecutada; por tanto no existe aún auditoría persistente de evaluaciones V2.
2. No se validó con expedientes reales ni contra una BD de integración, para respetar el modo sombra y la restricción de no ejecutar migraciones.
3. La clasificación de vocabularios históricos no reconocidos queda como `NO_DETERMINADO`; se requiere catálogo de equivalencias cuando aparezcan fuentes nuevas.
4. Las prioridades académicas de reglas 1.x, 4.x, 5.x, 6.x, 7.x, 8.x, 9.x y CTR-01 no se materializan hasta sus fases autorizadas.
5. No se inició FASE 1.

## RESTRICCIONES VERIFICADAS

- Sin cambio de elegibilidad real.
- Sin cambio de oferta real.
- Sin cambio de límites de carga ni selección.
- Sin activación de CTR-01.
- Sin alteración visible del Mapa curricular dinámico.
- Sin sustitución de validadores productivos.
- Sin ejecución de migración.
