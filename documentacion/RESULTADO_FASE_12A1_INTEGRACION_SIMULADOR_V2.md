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

TablaCurricularAsistidaBean.simularSeleccion delega la evaluación académica
final a SimuladorCurricularV2Service. RN-6.1, RN-6.4, seriación, situación y
carga proceden del resultado V2. La validación final de inscripción legacy
permanece en una ruta productiva separada y no participa en la simulación.

## CONTRATO_V2

Se reutilizan ContextoEvaluacionCurricularV2DTO y
ResultadoEvaluacionCurricularV2DTO. No existe motor académico paralelo ni DTO
académico alterno.

## VALIDACIONES_COMPLEMENTARIAS_DE_ESCENARIO

El contrato V2 no representa todavía restricciones de composición de selección
heredadas. El Simulador las conserva explícitamente como complementarias, sin
sustituir decisiones V2 ni mutar datos: una optativa por bloque/semestre,
prohibición de repetir la misma clave optativa, máximo configurado de
electivas y advertencia por UD prioritaria ofertada omitida.

La carga mínima y máxima se aplican desde el resultado V2: RN-4.1 impide una
selección inferior al mínimo y RN-4.2 impide exceder el máximo. Los mensajes
identifican el código de regla aplicable; no atribuyen genéricamente una
restricción al Motor V2.

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

PRUEBAS_ESPECIFICAS_CONSUMIDOR: 17 pruebas, 0 fallas, 0 errores, 0 omitidas.
Cubren consumo del resultado V2, no mutación, determinismo, carga máxima
regular/irregular, carga mínima, exceso de carga, carga no determinable,
propagación de motivos V2, optativas repetidas por clave/bloque, máximo de
electivas y prioridad omitida.

## REGRESION

REGRESION_COMPLETA: 764 pruebas, 0 fallas, 0 errores, 0 omitidas.

## COMPILACION

basegestor: SUCCESS.
gestorWeb: SUCCESS.

## VALIDACION_FUNCIONAL_RUNTIME

DEFECTO_DETECTADO_RN42: una selección de ocho UD para un contexto irregular
podía aparecer válida porque el consumidor sólo adjuntaba la evaluación V2.

CAUSA_RAIZ: SimuladorCurricularV2ServiceImpl no aplicaba
evaluacionCargaMaxima.cargaMaximaNormativa ni
cargaMaximaFuturaSombra del ResultadoEvaluacionCurricularV2DTO sobre la
selección hipotética.

CORRECCION: el consumidor obtiene la carga máxima exclusivamente del contrato
V2, compara la cantidad de UD seleccionadas y agrega una restricción RN_4_2
cuando se excede o no puede determinarse. Las decisiones por UD continúan
proveniendo de V2 y sus códigos de motivo se preservan para presentación.

REGLAS_VALIDADAS: RN-4.1 para carga mínima, RN-4.2 para regular (8) e irregular (6), exceso de carga,
RN-6.4/reprobación acumulada, seriación directa e indirecta, NO_CURSADA,
BAJA_PARCIAL, SIN_OFERTA y requisito curricular pendiente. RN-6.1, tipos de
UD, S7/S8 y demás reglas se consumen por sus decisiones V2 consolidadas, sin
reimplementación en el Simulador.

MATRIZ_RESULTADO_V2_VS_SIMULADOR: la carga máxima, habilitación y motivos se
obtienen del ResultadoEvaluacionCurricularV2DTO. La matriz focal confirma que
una decisión V2 restringida permanece restringida y que el código del motivo
se conserva en ResultadoSimulacionDTO.

PRUEBAS_NUEVAS: 14 ejecuciones adicionales, para 17 focalizadas en total.

## RUNTIME

RUNTIME_LOCAL: no iniciado. El proyecto empaqueta WAR y declara plugin WildFly,
pero este entorno no ofrece servidor local iniciado ni URL de despliegue.

PRUEBA_MANUAL_PENDIENTE: con WildFly local configurado, desplegar el WAR
generado, abrir la ruta privada de tabla curricular asistida y, para un
contexto IRREGULAR, intentar seleccionar siete y ocho UD. En ambos casos el
panel debe indicar escenario restringido y server.log debe registrar
cargaMaxima=6, cargaSeleccionada=7/8 y seleccionValida=false. Confirmar además
que no se modifica inscripción, oferta, grupo ni selección persistida.

También verificar una selección vacía cuando RN-4.1 exige mínimo, dos
optativas del mismo bloque y la misma optativa en bloques distintos. Deben
mostrarse respectivamente RN_4_1, OPTATIVA_DUPLICADA_EN_BLOQUE y
OPTATIVA_MISMA_CLAVE.

VALIDACION_MANUAL_UI: PENDIENTE.

## RIESGOS

La validación final productiva continúa legacy y está fuera de esta subfase.
La interacción visual requiere evidencia manual sobre un WildFly local
configurado; no se realizó en este entorno.

## RESULTADO

RESULTADO = APROBADO_FUNCIONALMENTE

CAMBIO_PRODUCTIVO = NINGUNO
