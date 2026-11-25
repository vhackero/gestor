# Resultado FASE 1E — cierre, regresión y validación E2E

**Fecha:** 2026-10-06
**Rama:** `feature/reglas-negocio-pe2027`
**Versión normativa:** `PE2027-V1`
**Modo:** sombra; sin efectos productivos.

## Resultado ejecutivo

`FASE_1 = NO_APROBADA PARA ACTIVACIÓN PRODUCTIVA` y `GATE_E2E_V2 = NO_APROBADO`.

Las suites de regresión FASE 0 a FASE 1D, más la prueba F1E de seguridad del diagnóstico, pasan en compilación local. La condición no satisfecha es la corrida E2E real en un contenedor SIGIE DEV/QA con el artefacto de esta rama. Esta conclusión no presupone fallo académico ni técnico: evita declarar aprobada una validación de datos reales que no fue ejecutada.

## Implementación F1E

Se agregó un servicio Spring interno `DiagnosticoE2EEvaluacionCurricularV2Service`; no se agregó endpoint, ruta web, vista ni consumidor productivo. Su ruta de datos es:

```text
AsistenteInscripcionService.obtenerContextoAsistido(id técnico)
  → ContextoEvaluacionCurricularV2Adapter
  → EvaluacionCurricularV2Service.evaluarSombra
  → TrazabilidadEvaluacionCurricularV2Service.registrarSombra
  → TrazabilidadEvaluacionCurricularV2Service.obtenerHashContexto
```

El método exige `sigie.v2.diagnostico.e2e.habilitado=true` y `sigie.v2.diagnostico.e2e.ambiente=DEV|QA`. El valor por defecto es deshabilitado; `PROD` no es admitido. También exige `EVALUACION_V2_HABILITADA=true` y `REGLAS_V2_MODO_SOMBRA=true`. Por ello no cambia oferta, seleccionabilidad, carga, bloqueos, inscripción o la representación del mapa.

La salida técnica expone exclusivamente ID técnico de persona, ID de evaluación, hash, conteos y banderas de reconstrucción/inmutabilidad. Las referencias detalladas permanecen en auditoría V2; no se incluyen nombres, correos, matrícula ni otros datos personales en el DTO diagnóstico.

## Inmutabilidad e idempotencia

Antes y después de evaluar se calcula una huella de las UD fuente formada por identificador técnico, oferta, seleccionabilidad y bloqueo. La evaluación V2 sólo consume el contexto; la escritura permitida es RN-9.5. La persistencia ya implementada en F1D usa hash de contexto para reutilizar un encabezado equivalente y evitar duplicados accidentales.

La inmutabilidad E2E de oferta, selección, carga, bloqueos, inscripción y mapa no se declara validada todavía: requiere comparar las fuentes del contenedor real antes y después de ejecutar el diagnóstico. No se fabricaron casos ni se usaron mocks para sustituir esta validación principal.

## Pruebas y compilación

| Grupo | Resultado | Evidencia |
|---|---|---|
| Regresión F0–F1D | PASÓ | 59 pruebas TestNG seleccionadas: normalización, adaptador, concurrencia, oferta sombra, excepciones y trazabilidad. |
| F1E-U-01 | PASÓ | Diagnóstico rechazado por defecto y con ambiente `PROD`; no consulta servicios antes del rechazo. |
| Compilación basegestor | PASÓ | Maven `test` dirigido a la suite V2; Maven compiló las fuentes y pruebas requeridas. |
| Compilación gestorWeb | PASÓ | Maven `clean compile` sin pruebas. |
| E2E real DEV/QA | NO EJECUTADO | No se desplegó/invocó el servicio desde un contenedor identificado con el artefacto actual. |

Comandos ejecutados:

```bash
cd codigoFuente/basegestor
mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest,DiagnosticoE2EEvaluacionCurricularV2ServiceTest test
cd ../gestorWeb
mvn -q -DskipTests -Dmaven.test.skip=true clean compile
```

## Casos reales y auditoría

No se ejecutó ningún expediente real con el mecanismo F1E. Los casos A–I solicitados quedan como `CASO_NO_DISPONIBLE` para esta corrida, no porque se haya confirmado su inexistencia, sino porque falta invocar el diagnóstico desde DEV/QA con el artefacto actual. Por el mismo motivo:

- UD comparadas: `0`;
- registros de auditoría creados por F1E: `0`;
- diferencias E2E: no cuantificables;
- reconstrucción de auditoría E2E: pendiente.

## Riesgo pendiente y corrección requerida

El único riesgo de cierre es documental y de validación: no existe evidencia de la ruta completa contra el contenedor SIGIE DEV/QA. Para cerrar el gate se requiere una ejecución administrada con un ID técnico autorizado, propiedades de diagnóstico habilitadas sólo en DEV/QA, consulta de `tbl_evaluacion_curricular_v2` y sus detalles, y comparación antes/después contra el contexto fuente. Si esa comparación detecta cualquier modificación productiva originada por V2, el gate debe permanecer no aprobado.

## Cambio productivo

`CAMBIO_PRODUCTIVO = NINGUNO`.

No se inició FASE 2, no se activó CTR-01 y no se sustituyó ningún validador vigente.
