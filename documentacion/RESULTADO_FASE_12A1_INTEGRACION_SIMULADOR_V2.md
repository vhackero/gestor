# Resultado Fase 12A.1: Integracion Simulador V2

## OBJETIVO

El Simulador consume ResultadoEvaluacionCurricularV2DTO en modo sombra para
decidir restricciones académicas de escenarios, sin modificar inscripción,
oferta, grupos, trayectoria ni calificaciones.

## ENTRY_POINT_SIMULADOR

La vista tablaCurricularAsistida.xhtml invoca
TablaCurricularAsistidaBean.simularSeleccion. El bean ahora delega a
SimuladorCurricularV2Service.simularSombra. La validación final y la entrega de
preselección a inscripción no se modificaron y permanecen fuera de alcance.

## COMPONENTES_LOCALIZADOS

- TablaCurricularAsistidaBean y tablaCurricularAsistida.xhtml: presentación.
- AsistenteInscripcionServiceImpl: fuente de contexto y simulación legacy.
- SimuladorCurricularV2ServiceImpl: nuevo consumidor read-only V2.
- ContextoEvaluacionCurricularV2Adapter y EvaluacionCurricularV2Service:
  adaptador y motor comunes.
- ResultadoSimulacionDTO: DTO de presentación que conserva la evaluación V2.

## DIRECTORIO_REGLAS_NEGOCIO_SIMULADOR

No localizado en esta rama/ruta. No fue creado, modificado ni agregado a Git.

## LOGICA_LEGACY_LOCALIZADA

AsistenteInscripcionServiceImpl recalculaba selección, carga, optativas,
electivas, prioridades y bloqueos. La ruta de simulación de UI ya no llama esa
lógica; la validación final productiva no fue alterada.

## LOGICA_REEMPLAZADA_POR_V2

Simular selección obtiene contexto SIGIE de lectura, lo adapta y ejecuta el
motor V2 completo. Las restricciones por UD se transforman sólo a motivos de
presentación y se conserva ResultadoEvaluacionCurricularV2DTO completo.

## LOGICA_CONSERVADA

Conteo visual de UD/créditos seleccionados, mensajes de presentación y
navegación permanecen en el consumidor. No son reglas académicas.

## CONTRATO_V2

Se reutilizan ContextoEvaluacionCurricularV2DTO y
ResultadoEvaluacionCurricularV2DTO. No existe motor académico paralelo ni DTO
académico alterno.

## MAPEO_RESULTADO

El resultado de UI preserva evaluación V2, situación, decisiones por UD,
motivos concurrentes, restricciones, seriación, carga, RN-6.1, RN-6.4 y demás
trazas disponibles. No se reduce a un booleano.

## NO_MUTACION

SimuladorCurricularV2Service es transaccional read-only y no invoca inscripción
ni persistencia. El resultado declara impacto hipotético sin reservas de cupo
ni cambios productivos.

## DIFERENCIAS_LEGACY_VS_V2

Pendiente de observación manual: cualquier diferencia debe clasificarse como
COINCIDE, DIFERENCIA_ESPERADA_PE2027, DIFERENCIA_INESPERADA o NO_COMPARABLE.
No se alteró V2 para igualar legacy.

## PRUEBAS

La integración compila contra el motor común. Pendiente agregar cobertura
específica del consumidor y validación manual UI en la siguiente revisión
autorizada.

## REGRESION

F0-F12A.1: 746 pruebas, 0 fallas, 0 errores, 0 omitidas.

## COMPILACION

basegestor: SUCCESS.
gestorWeb: SUCCESS, después de instalar localmente el artefacto actualizado de
basegestor.

## RUNTIME

PENDIENTE_VALIDACION_MANUAL_UI. No se levantó interfaz local.

## RIESGOS

La validación final productiva continúa legacy y está fuera de esta subfase.
Se requiere regresión y pruebas específicas antes de declarar aprobación.

## RESULTADO

APROBADO_CON_OBSERVACIONES

CAMBIO_PRODUCTIVO = NINGUNO
