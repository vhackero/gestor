# Resultado Fase 12A.2: Integracion Malla Curricular V2

## OBJETIVO

Integrar la Malla Curricular existente con el Motor Curricular V2 PE2027-V1 sin reconstruirla ni modificar inscripcion, reinscripcion, grupos, matriculacion o Asistente.

## ENTRY_POINTS_MALLA

- `MallaCurricularAlumnoBean`: carga el contexto existente de solo lectura, construye el modelo y aplica el resultado V2.
- `MallaCurricularV2Facade`: reutiliza el adaptador `ContextoEvaluacionCurricularV2Adapter` y `EvaluacionCurricularV2Service` comunes.
- `MallaCurricularV2ResultadoAdapter`: traduce decisiones V2 hacia nodos visuales ya existentes, sin introducir un DTO academico paralelo.
- `mallaCurricularAlumno.xhtml`: conserva la presentacion vigente de nodos, bloques, estados, iconos y tooltips.

## ARQUITECTURA

`AsistenteInscripcionContextoDTO -> ContextoEvaluacionCurricularV2Adapter -> EvaluacionCurricularV2Service -> ResultadoEvaluacionCurricularV2DTO -> MallaCurricularV2ResultadoAdapter -> Malla existente`.

La evaluacion se ejecuta en modo sombra y no escribe datos productivos.

## LOGICA_LEGACY_AUDITADA

| Logica | Accion |
| --- | --- |
| Estados historicos, en curso, baja y no inscrita | CONSERVAR_PRESENTACION |
| Bloques, semestres, relaciones graficas y navegacion | CONSERVAR_LEGACY |
| Creditos y porcentajes informativos | CONSERVAR_COMPLEMENTARIA |
| Optativas, electivas y conteos visuales | CONSERVAR_COMPLEMENTARIA |
| Restricciones, prioridad y motivos normativos | ADAPTAR_A_V2 |
| Situacion academica calculada | REEMPLAZAR_POR_V2 cuando V2 determina REGULAR o IRREGULAR |

## LOGICA_REEMPLAZADA_POR_V2

- La habilitacion o restriccion por UD proviene de `DecisionUnidadCurricularV2DTO`.
- Los motivos y prioridades se trasladan sin reinterpretacion al detalle de la Malla.
- La situacion academica V2 prevalece cuando es `REGULAR` o `IRREGULAR`.
- RN-6.1, RN-6.4 y seriacion se consumen desde la decision V2 por UD; la Malla no los recalcula.

## LOGICA_LEGACY_CONSERVADA

Se conservan los estados visuales de trayectoria y los calculos de creditos, bloques, optativas, electivas, iconos, colores, tooltips y relaciones graficas. Una restriccion V2 marca el nodo como restringido sin convertir `No inscrita`, `Baja`, `En curso`, `No acreditada` o `Aprobada` en una reprobacion artificial.

## VALIDACIONES_COMPLEMENTARIAS_MALLA

La Malla conserva la informacion visual e informativa no resuelta por V2. No se eliminaron conteos, agrupaciones, creditos ni componentes graficos por el solo hecho de ser legacy.

## ESTADOS_V2

La decision normativa se aplica de V2. La semantica visual heredada conserva las distinciones entre acreditada, reprobada, no inscrita, baja y en curso. Los datos no determinables permanecen sujetos a revision V2; no se inventa una reprobacion.

## SERIACION

Las restricciones y motivos de seriacion directa, indirecta, interbloque, intersemestral e interanual provienen del Motor V2. La Malla conserva el dibujo de dependencias y muestra la restriccion mediante el nodo y su detalle.

## RN_6_4

La prioridad y restricciones asociadas se toman de V2. La Malla no cuenta intentos como reprobadas.

## RN_6_1

La aplicabilidad se toma de V2. La Malla no reintroduce controles anuales legacy para S3 o S7.

## OFERTA

Oferta y restriccion curricular permanecen como dimensiones separadas: `SIN_OFERTA` se conserva como motivo o informacion de disponibilidad y no se presenta como fallo academico distinto.

## OPTATIVAS

La logica visual, de bloques, conteos y creditos de optativas permanece como validacion complementaria de Malla mientras no contradiga una decision V2.

## ELECTIVAS

La visualizacion y conteos existentes se conservan. Las decisiones curriculares que V2 ya entrega se muestran como motivo o restriccion, sin crear reglas nuevas en la Malla.

## CREDITOS

Se preservan los calculos existentes de creditos acumulados, obligatorios y no obligatorios. La integracion no reemplaza esos conteos informativos.

## COLORES_ICONOS

Se reutiliza la convencion visual existente. La restriccion V2 se expresa por el estado `bloqueada` y el motivo; los estados historicos conservan su color e icono para no perder semantica de trayectoria.

## MOTIVOS

Los codigos V2 se conservan para trazabilidad interna, pero el panel muestra explicaciones legibles y las reglas aplicadas cuando existen trazas. No se exponen listas como `REPROBACION_ACUMULADA` o `DATOS_INSUFICIENTES` al estudiante.

Una UD reprobada conserva el estado visual `No acreditada`, incluso si es optativa y ya se cubrio el minimo de optativas. La condicion de optativa opcional solo se aplica a UD sin un estado historico pendiente.

## TRAZABILIDAD_RUNTIME

La Malla registra, sin PII:

- `[MALLA_V2] Inicio evaluacion curricular con Motor V2`
- `[MALLA_V2] Invocando Motor Curricular V2 - version=PE2027-V1 - modo=SOMBRA`
- `[MALLA_V2] ResultadoEvaluacionCurricularV2DTO obtenido correctamente`
- `[MALLA_V2] Resultado V2 aplicado a Malla Curricular`
- Resumen anonimizado de acreditadas, reprobadas, restringidas, habilitadas y revisiones.

## COMPARACION_LEGACY_VS_V2

- Decisiones normativas cubiertas por V2: V2 prevalece.
- Presentacion, creditos, bloques, conteos y relaciones visuales: `LEGACY_COMPLEMENTARIO`.
- No se detecto una segunda evaluacion normativa activa dentro de la nueva aplicacion V2 de Malla.

## PRUEBAS_NUEVAS

- `MallaCurricularV2FacadeTest`: verifica adaptador real y evaluador comun V2.
- `MallaCurricularAlumnoBeanV2Test`: verifica restriccion, motivo, prioridad, situacion, unidad habilitada y preservacion visual de `No inscrita` con revision V2.

## REGRESION

`basegestor`: Tests run: 765, Failures: 0, Errors: 0, Skipped: 0.

`gestorWeb`: Tests run: 3, Failures: 0, Errors: 0, Skipped: 0.

## COMPILACION

`basegestor`: SUCCESS.

`gestorWeb`: SUCCESS.

## VALIDACION_MANUAL_PENDIENTE

Desplegar en WildFly local y observar:

```bash
sudo tail -f /opt/wildfly/standalone/log/server.log | grep --line-buffered 'MALLA_V2'
```

Validar visualmente estados, seriacion, oferta, optativas, electivas, creditos, prioridades, RN-6.4 y RN-6.1 donde aplique. Esta validacion no se declara ejecutada desde Codex.

## RIESGOS

- La cobertura de estados visuales depende de los valores legacy disponibles en la Malla; V2 no reemplaza los catalogos visuales.
- Cualquier divergencia observada en runtime debe clasificarse primero como contexto, adaptacion, presentacion o diferencia normativa PE2027 antes de cambiar el motor.

## RESULTADO

`APROBADO_TECNICAMENTE`.

`CAMBIO_PRODUCTIVO = NINGUNO`.
