# Resultado runtime - Bloque 5 RN 20261117

## Alcance y evidencia de despliegue

| Dato | Resultado |
| --- | --- |
| Rama / commit declarado para el WAR | `feature/desacople_motor_historico` / `410b3a21` |
| WAR localizado | `/opt/wildfly/standalone/deployments/plataforma.war` |
| SHA-256 del WAR | `df87b114e4511b8a7d927cce999c3254a4c60b47176a6e99ad2eb1359cd2f122` |
| Despliegue WildFly | `2026-11-18 13:54:25` (`WFLYSRV0010`) |
| Consulta HTTP sin sesión | `302` a `/plataforma/login`; 15.4 a 19.5 ms en tres intentos |
| Modo | Sombra; no se realizaron escrituras de inscripción, bajas, grupos, oferta o calificaciones. |

No se reportan nombres, matrículas, CURP, correo ni identificadores personales.

## Ejecución runtime

La validación completa solicitada requiere una sesión autorizada o un runner de diagnóstico no productivo que permita cargar un contexto sintético y consultar los cuatro consumidores. El WAR no expone un endpoint V2 sin sesión y los intentos HTTP sin autenticación se redirigen al login. Los registros del servidor desde el despliegue no contienen invocaciones `[MALLA_V2]`, `[ASISTENTE_V2]` ni `[SIMULADOR_V2]`.

Por tanto, **no se ejecutaron GC-17, GC-18, GC-19 ni A/B/C dentro del WAR**. Presentarlos como resultados runtime sería incorrecto. Esta condición es una brecha de ejecución del ambiente, no un defecto académico identificado.

| Caso | Fuente prevista | Estado runtime | Defecto |
| --- | --- | --- | --- |
| GC-17 | Sintético | No ejecutado: sin runner autenticado. | No evaluable |
| GC-18 | Sintético | No ejecutado: sin runner autenticado. | No evaluable |
| GC-19 | Sintético | No ejecutado: sin runner autenticado. | No evaluable |
| A / GC-04 | Anonimizado | No ejecutado: no se recibió sesión autorizada. | No evaluable |
| B / GC-05 | Anonimizado | No ejecutado: no se recibió sesión autorizada. | No evaluable |
| C / GC-08 | Anonimizado | No ejecutado: no se recibió sesión autorizada. | No evaluable |

## Cobertura automatizada disponible

Se ejecutaron los casos de contrato con contextos sintéticos, fuera del WAR desplegado. El conjunto focalizado ejecutó 24 pruebas, sin fallas, errores ni omisiones. El adaptador web del Asistente ejecutó 28 pruebas, sin fallas, errores ni omisiones. Estas pruebas no sustituyen la validación runtime.

| Caso | Resultado contractual comprobado | Malla | Asistente y Situación académica | Simulador | Asistente virtual |
| --- | --- | --- | --- | --- | --- |
| GC-17 A | 2 fallidos + 1 en curso; `ULTIMA_EN_CURSO`, 1 adeudo. | Consume la decisión común; pendiente runtime. | Consume la decisión común; pendiente runtime. | Consume la evaluación común; pendiente runtime. | Deriva del resultado del Asistente; pendiente runtime. |
| GC-17 B | Baja parcial excluida y 1 fallido; `DISPONIBLE`, 1 adeudo. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. |
| GC-17 C | Reprobación seguida de acreditación; `ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA`, 0 adeudos. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. |
| GC-17 D | 2 fallidos; `ULTIMA_DISPONIBLE`, 1 adeudo. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. |
| GC-17 G | Sólo baja parcial; `DISPONIBLE`, sin adeudo. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. |
| GC-17 E | Nunca cursada; `DISPONIBLE`, sin adeudo. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. |
| GC-18 | Estados 8.1: Inscrita, Pendiente por seriación y Pendiente de confirmación. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. | Igual contrato; pendiente runtime. |
| GC-19 | Un aviso `AVISO_COMUNICADO_2027_1` sólo para `2027-1`; no modifica decisión, motivo ni carga. | No lo muestra por diseño. | Lo muestra como aviso informativo; pendiente runtime. | Lo muestra en advertencias; pendiente runtime. | Lo muestra dentro del panel del Asistente; pendiente runtime. |

Para GC-17 el contrato verifica además: 6 resultados reprobatorios informativos, 3 adeudos vigentes, RN-6.4 no aplica, situación irregular, carga máxima 6, preselección A-D-B y referencia 6 para `2027-1` con `2026-1` excluido por baja temporal. La proyección a `2027-2` fija referencia 7.

## Validaciones complementarias del Simulador

| Validación | Alcance | No contradicción con el motor |
| --- | --- | --- |
| Deduplíca la selección por ID | Presentación de la selección | No altera decisiones por UD. |
| Suma UD y créditos seleccionados | Informativa | No decide habilitación ni situación académica. |
| Una optativa por bloque/semestre | `OPTATIVA_DUPLICADA_EN_BLOQUE` | Complementa la composición; no habilita una UD bloqueada por el motor. |
| Misma optativa repetida | `OPTATIVA_MISMA_CLAVE` | Complementa la composición; no sustituye reglas V2. |
| Aviso por prioritaria ofertada omitida | Advertencia | No invalida ni habilita selección. |
| Carga mínima/máxima | Consume RN-4.1/RN-4.2 del resultado | Usa los límites del motor, no constantes propias. |
| Restricciones por UD seleccionada | Consume decisión y motivos del resultado | Sólo los agrupa con clave visible para explicación. |

No se identificó una validación complementaria que reemplace o contradiga decisión, motivo, oportunidades, adeudos o estado 8.1 del motor. La confirmación de esta afirmación en ejecución sigue pendiente por la ausencia de una sesión/runner autorizado.

## Verificación 2026-2 solicitada

| Verificación | Resultado runtime |
| --- | --- |
| UD con calificación pendiente en periodo vigente -> `EN_CURSO` | No ejecutada en WAR. Cubierta por prueba contractual del adaptador. |
| Grupo con `acta_cerrada=1`, calificación nula y periodo vigente -> `PENDIENTE_CALIFICACION` | No ejecutada en WAR. Cubierta por `PreparacionDecisionesPendientesV2Test`. |
| Ninguna de las anteriores queda en revisión | No ejecutada en WAR. Cubierta por pruebas contractuales focalizadas. |
| Comunicado único sólo en `2027-1` | No ejecutado en WAR. Cubierto por `ComunicadosInstitucionales20271Test`. |
| Malla no muestra comunicado | Validado por implementación: la Malla no adapta `AvisoEvaluacionCurricularV2DTO`; pendiente de comprobación visual runtime. |

## Tiempos de respuesta

| Componente | Medición runtime |
| --- | --- |
| Entrada HTTP sin sesión | 15.4 a 19.5 ms, redirección a login. |
| Malla curricular | No medible: no hubo invocación autenticada. |
| Asistente de inscripción | No medible: no hubo invocación autenticada. |
| Simulador | No medible: no hubo invocación autenticada. |
| Asistente virtual | No medible: depende de la evaluación autenticada del Asistente. |

## Acción requerida para cierre

Una persona autorizada debe proporcionar una sesión de pruebas controlada para los casos A/B/C, o habilitar un runner no productivo que acepte únicamente contextos sintéticos y exponga el resultado anonimizadamente. Con esa ejecución se podrán capturar por UD decisión, motivos, oportunidades, adeudos, estado 8.1, semestre de referencia, aviso y tiempos reales por componente.

## Resultado

`PENDIENTE_DE_VALIDACION_RUNTIME`: el WAR está desplegado y accesible, pero no existe evidencia de evaluación autenticada de los seis expedientes solicitados. No se declaran defectos de consistencia sin una ejecución real de los cuatro consumidores.
