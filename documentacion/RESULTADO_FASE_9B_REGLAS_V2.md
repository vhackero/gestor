# Resultado Fase 9B - RN-9.3

FASE_IMPLEMENTADA = FASE 9B

REGLA = RN-9.3

VERSION_NORMATIVA = PE2027-V1

CAMBIO_PRODUCTIVO = NINGUNO

## ULTIMOS_SEMESTRES

RN-9.3 se evalúa solo en modo sombra para S7 y S8. En S1-S6 devuelve `NO_APLICA` y no sustituye controles previos.

## S7_S8

El semestre objetivo es curricular. S7/S8 activan la evaluación crediticia y de componentes finales; periodo calendario, antigüedad y número de reinscripción no se usan para inferir aplicabilidad.

## UMBRAL_50_PORCIENTO_CREDITOS

El umbral es `porcentajeCreditosAcreditados >= 50`. El 50 exacto cumple; valores inferiores no cumplen sin tolerancias añadidas.

## CALCULO_CREDITOS

`creditosTotalesPrograma` se recibe desde estructura curricular verificable. Solo las UD vigentemente `ACREDITADA` con crédito curricular válido aportan al numerador, una sola vez por identificador técnico. El cálculo usa `BigDecimal`; cantidad de UD no sustituye créditos.

## ESTADO_VIGENTE_VS_HISTORICO

Una acreditación vigente cuenta aunque exista reprobación histórica. Reprobada, en curso, no cursada, baja parcial, no inscrita y no determinada no aportan créditos acreditados. El histórico se preserva para trazabilidad e intentos.

## RN_6_1_NO_APLICA

RN-6.1 no se reactiva en S7/S8. S5 conserva su evaluación independiente de FASE 5F.

## SERIACION_DIRECTA

RN-9.3 consume resultados de FASE 8. Un antecedente directo no acreditado restringe únicamente la UD afectada aunque el umbral de créditos se cumpla.

## SERIACION_INDIRECTA

Las cadenas y antecedentes indirectos continúan siendo responsabilidad del motor RN-8.3. RN-9.3 no crea otro recorrido de grafo.

## INTEGRACION_RN_6_4

Con cuatro o más reprobadas reales vigentes, RN-6.4 permanece aplicable en S7/S8. Un porcentaje de créditos alto no sobreescribe esta restricción.

## INTEGRACION_RN_7_2

ELECTIVAS reutilizan su elegibilidad RN-7.2, incluidos tipo, S7/S8, PE de origen, carga, oferta y cupo. RN-9.3 suma avance crediticio, seriación y requisitos PE sin sobrescribir RN-7.2.

## ELECTIVAS

Una electiva que cumpla RN-7.2 aún puede ser restringida por crédito insuficiente, seriación o requisito PE. Una electiva no elegible por RN-7.2 no se habilita por RN-9.3.

## SEMINARIO_TITULACION

Solo se evalúa elegibilidad curricular en sombra mediante `componenteSeminarioTitulacion`. No se implementan opciones de titulación, servicio social, egreso, expediente ni emisión de título.

## REQUISITOS_ESPECIFICOS_PE

Cada electiva o seminario debe declarar requisitos PE verificables. La falta de configuración devuelve revisión; no se inventan requisitos comunes entre programas.

## NUMERAL_6_POR_ESTRUCTURA_PE

Los requisitos se modelan explícitamente por componente. Una pendiente solo restringe la electiva o seminario si figura como requisito curricular de ese componente.

## NO_BLOQUEO_GLOBAL_POR_PENDIENTES

No existe bloqueo por la mera presencia de cualquier obligatoria pendiente. La causa de pendiente se conserva y no se transforma en reprobación.

## OFERTA_VS_CUPO

Oferta y cupo continúan independientes de créditos, seriación y requisitos PE. `SIN_OFERTA` no equivale a `SIN_CUPO` ni a incumplimiento RN-9.3.

## CARGA

CARGA reutiliza RN-4.x. Cumplir el 50 por ciento o requisitos PE no permite exceder la carga aplicable.

## PRIORIDAD

PRIORIDAD reutiliza RN-6.3, FASE 6 y FASE 7. El acceso a S7/S8 con umbral cumplido no desplaza pendientes priorizadas.

## AUDITORIA_DUPLICACION

| Dependencia | Estado |
| --- | --- |
| Situación académica | REUTILIZADO desde RN-1.2 |
| Carga | REUTILIZADO desde RN-4.x |
| Prioridad | REUTILIZADO desde RN-6.3, FASE 6 y FASE 7 |
| RN-6.4 | REUTILIZADO |
| Electivas | REUTILIZADO desde RN-7.2 |
| Seriación | REUTILIZADO desde FASE 8 |
| Trazabilidad | REUTILIZADO desde RN-9.5 |
| Cálculo crediticio y requisitos por componente | ORQUESTADO en RN-9.3 |

## CONTRATO_RESULTADO_V2

El contexto incorpora créditos totales verificables. La UD incorpora créditos válidos, marcador de seminario y requisitos específicos PE. `EvaluacionCargaAcademicaV2DTO` expone total, acreditados, porcentaje, umbral, estado RN-9.3, componentes restringidos/revisables y decisiones, sin DTO paralelo.

## TRAZABILIDAD

Se registra RN-9.3, semestre objetivo, créditos totales y acreditados, porcentaje, umbral, RN-6.1 no aplicable en S7/S8, seriación, RN-6.4, requisitos PE, componentes, oferta/cupo, carga, decisiones y PE2027-V1. No se agrega PII.

## PRUEBAS

`EvaluacionCurricularV2Fase9BTest` agrega 14 pruebas: aplicabilidad S7/S8, datos insuficientes, fronteras 49/50/51, deduplicación, estado vigente, RN-6.1, seriación, RN-6.4, electivas, seminario, requisito PE, ausencia de bloqueo global, disponibilidad, determinismo e inmutabilidad productiva.

## RIESGOS

El umbral depende de créditos y estructura curricular canónicos. Si el catálogo no acredita créditos, requisitos o relaciones, el resultado es revisión, no una inferencia. RN-9.2, RN-9.4, FASE 9C y FASE 10 continúan fuera de alcance.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/.../EstadoAvanceUltimosSemestresV2.java`
- `codigoFuente/basegestor/.../ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/.../UnidadEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/.../EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/.../CodigoMotivoCurricularV2.java`
- `codigoFuente/basegestor/.../EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/.../EvaluacionCurricularV2Fase9BTest.java`
- `documentacion/RESULTADO_FASE_9B_REGLAS_V2.md`

## RESULTADO

RN-9.3 queda implementada en sombra para últimos semestres, con umbral crediticio verificable y restricciones concurrentes por seriación, RN-6.4 y estructura específica del PE.
