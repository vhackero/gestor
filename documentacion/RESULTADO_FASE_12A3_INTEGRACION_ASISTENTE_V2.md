# Resultado FASE 12A.3 - Integracion Asistente V2

## OBJETIVO

Integrar el Asistente Curricular con el Motor Curricular V2 PE2027-V1 sin modificar los flujos productivos de inscripcion, reinscripcion, matriculacion u oferta. El Asistente conserva su responsabilidad de explicar, agrupar y orientar; las decisiones curriculares que V2 ya resuelve provienen de `ResultadoEvaluacionCurricularV2DTO`.

## ENTRY_POINTS_ASISTENTE

- `TablaCurricularAsistidaBean` obtiene el contexto asistido actual y construye las filas de la vista.
- `AsistenteInscripcionServiceImpl` mantiene la construccion legacy del contexto, la oferta operativa, bloques y narrativas complementarias.
- `AsistenteCurricularV2Facade` adapta el mismo contexto asistido al contrato V2 y ejecuta el evaluador comun en modo sombra.
- `AsistenteCurricularV2ResultadoAdapter` transforma una decision V2 por UD a las propiedades de presentacion del Asistente, sin recalcular reglas.

## ARQUITECTURA

`AsistenteInscripcionContextoDTO -> ContextoEvaluacionCurricularV2Adapter -> ContextoEvaluacionCurricularV2DTO -> EvaluacionCurricularV2Service -> ResultadoEvaluacionCurricularV2DTO -> AsistenteCurricularV2ResultadoAdapter -> filas y recomendaciones del Asistente`.

La ruta es read-only respecto de los datos academicos y no crea una segunda evaluacion curricular.

## LOGICA_LEGACY_AUDITADA

| Logica | Accion |
| --- | --- |
| Construccion de oferta, bloques y escenarios | CONSERVAR_COMPLEMENTARIA |
| Mensajes, diagnostico narrativo y agrupaciones | CONSERVAR_EXPLICACION |
| Optativas, electivas y filtros operativos no cubiertos por V2 | CONSERVAR_COMPLEMENTARIA |
| Situacion, prioridad, habilitacion y motivos curriculares | ADAPTAR_A_V2 |
| Seriacion, RN-6.1, RN-6.4 y carga curricular | REEMPLAZAR_POR_V2 cuando el resultado lo determine |

## LOGICA_REEMPLAZADA_POR_V2

Las filas del Asistente consumen de V2 la habilitacion, restriccion, prioridad y motivos de cada UD. Una UD restringida por seriacion deja de ser seleccionable o recomendada. La prioridad V2 de regularizacion se conserva para orientar la recomendacion.

## LOGICA_CONSERVADA

Se conservan la construccion del contexto asistido, la oferta operativa, bloques, orden narrativo, agrupaciones, mensajes y reglas complementarias de optativas/electivas que no son una decision normativa ya cubierta por V2.

## VALIDACIONES_COMPLEMENTARIAS_ASISTENTE

Las validaciones legacy de presentacion, filtros, escenarios y oferta siguen aplicando como complemento. No sustituyen ni contradicen una restriccion curricular V2.

## SITUACION_ACADEMICA

La situacion usada en el resumen de recomendaciones es la calculada por V2. No se agrega una clasificacion academica paralela en el Asistente.

## ESTADOS

V2 conserva los estados normalizados `ACREDITADA`, `REPROBADA`, `NO_CURSADA`, `BAJA_PARCIAL`, `EN_CURSO`, `NO_INSCRITA` y `NO_DETERMINADO`. El adaptador no transforma `NO_CURSADA`, `BAJA_PARCIAL` o `NO_INSCRITA` en `REPROBADA`; el estado historico se mantiene separado de la recomendacion operativa.

## RECOMENDACIONES

El Asistente no recomienda una UD restringida por V2. Si V2 marca una UD como prioritaria, el Asistente la conserva como prioridad de regularizacion. Las recomendaciones restantes pueden incluir explicaciones y criterios complementarios existentes.

## SERIACION

Las restricciones directas e indirectas de seriacion se obtienen de V2. El Asistente las explica sin volver a recorrer el grafo curricular.

## RN_6_4

La restriccion y prioridad derivadas de reprobadas vigentes unicas se consumen desde V2. El Asistente no usa intentos como sustituto del conteo de reprobadas.

## RN_6_1

La aplicabilidad se consume desde V2: solo en S5 y periodo normativo aplicable. S3 y S7 permanecen `NO_APLICA`; no se reintroduce control anual legacy.

## CARGA

Las decisiones de carga provienen de V2. El Asistente no recomienda combinaciones por encima de la carga permitida que expone el resultado comun.

## OFERTA

`SIN_OFERTA` se mantiene separada de una restriccion academica: la UD puede estar curricularmente habilitada, no ser recomendada para el periodo y conservar la explicacion de que no existe oferta. No se presenta como fallo de seriacion u otra regla curricular.

## OPTATIVAS

La presentacion, bloques y validaciones complementarias existentes de optativas se preservan cuando V2 no entrega una decision normativa equivalente.

## ELECTIVAS

La presentacion y reglas complementarias existentes de electivas se preservan. Cuando V2 entrega una restriccion aplicable, esta prevalece sobre la recomendacion legacy.

## PRIORIDADES

Las prioridades V2 se trasladan a las filas sin alterar la responsabilidad del Asistente de ordenar y explicar las recomendaciones.

## EXPLICABILIDAD

El adaptador transforma motivos V2 en mensajes comprensibles, por ejemplo prerrequisitos pendientes, regularizacion prioritaria o falta de oferta. Conserva la evidencia de motivo sin exponer datos personales.

## MOTIVOS

Los motivos V2 no se descartan al adaptar una fila. La disponibilidad (`SIN_OFERTA` o `SIN_CUPO`) se marca como condicion operativa no bloqueante; una razon curricular bloqueante mantiene la UD restringida.

## TRAZABILIDAD_RUNTIME

La ruta registra, sin PII:

- `[ASISTENTE_V2] Inicio evaluacion curricular con Motor V2`
- `[ASISTENTE_V2] Invocando Motor Curricular V2 - version=PE2027-V1 - modo=SOMBRA`
- `[ASISTENTE_V2] ResultadoEvaluacionCurricularV2DTO obtenido correctamente`
- `[ASISTENTE_V2] Recomendaciones construidas utilizando resultado V2`
- Un resumen de situacion, habilitadas, restringidas, prioritarias y recomendadas.

## CONSISTENCIA_SIMULADOR_MALLA_ASISTENTE

Los tres consumidores utilizan el mismo resultado V2 para decisiones normativas. Una UD restringida por V2 no se recomienda en el Asistente; las decisiones de seriacion, RN-6.4, RN-6.1 y carga conservan el contrato comun. La oferta se mantiene como dimension operativa independiente.

## PRUEBAS_NUEVAS

- `AsistenteCurricularV2FacadeTest`: verifica que el Asistente reutiliza el adaptador y evaluador V2 comunes.
- `AsistenteCurricularV2ResultadoAdapterTest`: verifica seriacion bloqueante, prioridad de regularizacion y separacion `SIN_OFERTA`/restriccion academica.

## REGRESION

- `basegestor`: 766 pruebas, 0 fallas, 0 errores, 0 omitidas.
- `gestorWeb`: 7 pruebas, 0 fallas, 0 errores, 0 omitidas.

## COMPILACION

- `basegestor`: SUCCESS con `mvn -q -DskipTests package`.
- `gestorWeb`: SUCCESS con `mvn -q -DskipTests package`.

## VALIDACION_MANUAL_PENDIENTE

Despues del despliegue local en WildFly, observar:

```bash
sudo tail -f /opt/wildfly/standalone/log/server.log | grep --line-buffered 'ASISTENTE_V2'
```

Abrir el Asistente con un estudiante irregular y confirmar que no recomienda UD restringidas, que respeta la carga V2, conserva prioridades de regularizacion y explica separadamente una UD habilitada sin oferta.

## RIESGOS

La cobertura automatizada valida el contrato de consumo y el adaptador de presentacion. La validacion visual completa de textos, agrupaciones de optativas/electivas y escenarios reales queda pendiente de runtime manual. No se modifica inscripcion, reinscripcion, grupos, oferta, Malla ni Simulador.

## RESULTADO

`APROBADO_TECNICAMENTE`.
