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

## LOGICA_PARALELA_AUDITADA

TablaCurricularAsistidaBean.simularSeleccion delega una sola evaluación
académica final a SimuladorCurricularV2Service. No recalcula RN-6.1, RN-6.4,
seriación, situación, carga, optativas ni electivas. La validación final de
inscripción legacy permanece en una ruta productiva separada y no participa en
la decisión de simulación; se conserva fuera de alcance.

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

PRUEBAS_ESPECIFICAS_CONSUMIDOR: 3 pruebas, 0 fallas, 0 errores, 0 omitidas.
Cubren consumo del resultado V2, restricción por UD, no mutación de la fuente
y determinismo. La validación manual UI permanece pendiente.

## REGRESION

REGRESION_COMPLETA: 749 pruebas, 0 fallas, 0 errores, 0 omitidas.

## COMPILACION

basegestor: SUCCESS.
gestorWeb: SUCCESS.

## RUNTIME

RUNTIME_LOCAL: no iniciado. El proyecto empaqueta WAR y declara plugin WildFly,
pero este entorno no ofrece servidor local iniciado ni URL de despliegue.

VALIDACION_MANUAL_UI: PENDIENTE. Con WildFly local configurado, desplegar el
WAR generado, abrir la ruta privada de tabla curricular asistida, seleccionar
UD y usar "Simular selección". Confirmar que el panel muestra los motivos V2,
que una UD restringida no se valida y que no se modifica inscripción, oferta,
grupo ni selección persistida.

## RIESGOS

La validación final productiva continúa legacy y está fuera de esta subfase.
La interacción visual requiere evidencia manual sobre un WildFly local
configurado; no se realizó en este entorno.

## RESULTADO

RESULTADO_TECNICO = APROBADO_TECNICAMENTE

CAMBIO_PRODUCTIVO = NINGUNO
