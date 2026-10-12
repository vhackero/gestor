# Resultado FASE 9D - Cierre de criterios complementarios

FASE_IMPLEMENTADA = FASE 9D
CAMBIO_PRODUCTIVO = NINGUNO

## REGLAS_REVISADAS
RN-9.1, RN-9.3 y RN-9.4 se mantienen implementadas en el Motor Curricular V2 en modo sombra. RN-9.5 se audita como infraestructura transversal. RN-9.2 se clasifica como frontera operativa.

## RN_9_1_CIERRE
REUTILIZADO: situación RN-1.2, carga RN-4.x, prioridad, RN-6.1, RN-6.4 y seriación FASE 8. Ordinario y extemporáneo conservan el mismo resultado académico; sólo cambia la traza administrativa.

## RN_9_2_FRONTERA_OPERATIVA
RN-9.2 es una regla de dispersión, matriculación y gestión de grupos activos. No es una regla de evaluación curricular.

## RN_9_2_NO_IMPLEMENTADA_EN_MOTOR_CURRICULAR
RN-9.2 queda `FUERA_DEL_ALCANCE_DEL_MOTOR_CURRICULAR_V2` y `PENDIENTE_DE_INTEGRACION_OPERATIVA`. El V2 no selecciona grupos, no crea grupos, no reserva cupos, no distribuye estudiantes, no modifica matrícula ni crea tickets GLPi. `SIN_CUPO` y `SIN_OFERTA` son restricciones académicas normalizadas, no asignación física de grupo.

## RN_9_3_CIERRE
S7/S8 consume avance crediticio de 50 %, FASE 8, RN-6.4 y requisitos PE; RN-6.1 permanece explícitamente `NO_APLICA` en S7/S8. No se introduce bloqueo global por pendientes no relacionadas.

## RN_9_4_CIERRE
El cumplimiento académico de egreso exige todas las obligatorias, ocho optativas y una electiva. Proyecto Terminal y Módulo Integrador consumen el universo verificable de seriadas S1-S6. El resultado nunca equivale a título, servicio social, inscripción ni ejecución administrativa.

## RN_9_5_AUDITORIA
La auditoría añade una fotografía RN-9.5 por contexto y por UD, compuesta de datos ya evaluados. No recalcula situación, carga, seriación, prioridad, excepciones ni decisiones.

## COBERTURA_RN_9_5_A_J
| Inciso | Cobertura |
| --- | --- |
| A situación académica | CUBIERTO por RN-1.2 y resumen RN-9.5 |
| B semestre objetivo | CUBIERTO por contexto curricular único |
| C pendientes y causa | CUBIERTO por unidad normalizada y decisión V2 |
| D total reprobadas | CUBIERTO por contador común consolidado |
| E seriación | CUBIERTO_POR_COMPOSICION mediante FASE 8 y trazas por relación |
| F oferta/cupo | CUBIERTO por fotografía RN-9.5, preservando SIN_OFERTA y SIN_CUPO |
| G RN-6.1 | CUBIERTO con APLICA/NO_APLICA explícito |
| H excepción tipificada | CUBIERTO por CTR-02 y contrato de excepciones |
| I UD habilitadas/restringidas | CUBIERTO por `DecisionUnidadCurricularV2DTO` |
| J fecha y resultado | CUBIERTO por traza, versión normativa y fecha de evaluación |

## CASOS_EXTRAORDINARIOS
Los metadatos no PII de resolución existente se admiten en el contexto: instancia resolutora, fundamentación y folio institucional. Si falta cualquiera, la evidencia indica `PENDIENTE_DE_RESOLUCION`; no se inventan valores ni se ejecuta un dictamen.

## INSTANCIA_FUNDAMENTACION_FOLIO
EXTENDIDO aditivamente en el contexto de evaluación y consumido sólo como evidencia RN-9.1/RN-9.5 para excepciones extraordinarias.

## NO_PII
No se agregan datos personales a trazas. Los metadatos de resolución son institucionales y no incluyen identidad del estudiante.

## MATRIZ_RESPONSABILIDADES
| Regla | Responsabilidad | Motor V2 | Sistema operativo |
| --- | --- | --- | --- |
| RN-9.1 | Contexto administrativo y evaluación académica | Sombra | Sin reinscripción productiva |
| RN-9.2 | Dispersión, grupos, matrícula y capacidad | Fuera de alcance | Integración operativa futura/existente |
| RN-9.3 | Avance curricular S7/S8 | Sombra | Sin inscripción |
| RN-9.4 | Egreso académico y componentes finales | Sombra | Sin egreso/titulación |
| RN-9.5 | Trazabilidad | Transversal V2 | Persistencia sombra existente |

## AUDITORIA_DUPLICACION
REUTILIZADO: situación, carga, prioridad, contador de reprobadas, RN-6.1, RN-6.4, FASE 7, FASE 8, CTR-02 y trazabilidad base.
EXTENDIDO: evidencia RN-9.5 de contexto/UD y resolución extraordinaria no PII.
ORQUESTADO: cierre FASE 9. No hay motores ni DTO paralelos para RN-9.2.

## CONTRATO_RESULTADO_V2
`ContextoEvaluacionCurricularV2DTO`, `ResultadoEvaluacionCurricularV2DTO`, decisiones por UD, trazas y excepciones cubren la evaluación académica. No se añadieron grupos, tickets GLPi ni datos de dispersión al contrato curricular.

## IDEMPOTENCIA
Una evaluación repetida conserva reglas, trazas lógicas, causas y decisiones sin duplicación dentro del mismo resultado.

## DETERMINISMO
Con el mismo contexto, versión normativa y orden lógico, el contenido académico y las decisiones se mantienen. La fecha proviene del contexto o del mecanismo de auditoría existente.

## PRUEBAS
`EvaluacionCurricularV2Fase9DTest` cubre RN-9.5 A-J, seriación directa/indirecta, oferta/cupo, RN-6.1, CTR-02, extraordinarios documentados y pendientes, equivalencia ordinario/extemporáneo, frontera RN-9.2, RN-9.3/RN-9.4, determinismo e idempotencia.

## RIESGOS_PENDIENTES
RN-9.2 requiere el componente responsable de grupos activos, matriculación, dispersión, capacidad y autorización/ticket GLPi. FASE 10, titulación y servicio social permanecen fuera de alcance.

## ARCHIVOS_MODIFICADOS
- Contexto V2 de evaluación.
- Orquestación y auditoría RN-9.5 del Motor V2.
- Pruebas de cierre FASE 9D.
- Esta documentación.

## RESULTADO
FASE 9 queda cerrada para el Motor Curricular V2 en modo sombra. RN-9.2 queda documentada como frontera operativa y no como regla académica omitida.
