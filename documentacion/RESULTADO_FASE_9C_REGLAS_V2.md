# Resultado FASE 9C - Reglas V2

FASE_IMPLEMENTADA = FASE 9C
REGLA = RN-9.4
VERSION = PE2027-V1
CAMBIO_PRODUCTIVO = NINGUNO

## REQUISITOS_ACADEMICOS_EGRESO
RN-9.4 se evalúa exclusivamente en sombra. El resultado es `REQUISITOS_ACADEMICOS_EGRESO_CUMPLIDOS`, `REQUISITOS_ACADEMICOS_EGRESO_PENDIENTES` o `REQUIERE_REVISION`; nunca autoriza egreso institucional, inscripción, certificado o título.

## TOTALIDAD_OBLIGATORIAS
El contexto declara el universo completo del PE mediante `idsObligatoriasRequeridasEgreso`. Cada UD debe estar acreditada en su estado vigente; una pendiente conserva su causa real. Un catálogo no comprobado, una UD ausente o tipo no verificable requiere revisión.

## OCHO_OPTATIVAS
Se reutiliza RN-7.1/RN-7.1.1: ocho acreditadas cumplen, siete quedan pendientes y nueve o más no aumentan el requisito.

## AL_MENOS_UNA_ELECTIVA
Se reutiliza la clasificación formal de FASE 7. Una electiva acreditada satisface el mínimo; optativas y obligatorias no se cuentan como electivas. La deduplicación usa el identificador curricular.

## INTEGRACION_RN_7_1_1
REUTILIZADO: contador, faltantes y adicionales de optativas. No se creó un contador RN-9.4 paralelo.

## INTEGRACION_RN_7_2
REUTILIZADO: tipo curricular ELECTIVA y su normalización V2. RN-9.4 no habilita ni inscribe electivas.

## ESTADO_VIGENTE_VS_HISTORICO
El estado vigente decide acreditación actual. La historia se conserva como evidencia y no duplica créditos, optativas, electivas ni obligatorias.

## DEDUPLICACION
Obligatorias, optativas, electivas y seriadas se consolidan por identificador académico antes de contar cumplimiento.

## PROYECTO_TERMINAL
La elegibilidad curricular sombra sólo se evalúa para componentes clasificados formalmente como `PROYECTO_TERMINAL`.

## MODULO_INTEGRADOR
La misma evaluación sombra aplica a componentes clasificados formalmente como `MODULO_INTEGRADOR`; un componente solicitado sin clasificación verificable requiere revisión.

## SERIADAS_S1_S6
El universo explícito `idsSeriadasPrimerosSeisSemestres` se valida contra FASE 8 y se limita a S1-S6. Sólo esas UD seriadas deben estar acreditadas para el requisito de componentes finales. Una UD no seriada S1-S6 o una seriada S7/S8 no se incorpora artificialmente.

## INTEGRACION_FASE_8
REUTILIZADO: identificación de seriación y relaciones interbloque, intersemestral e interanual. No existe motor nuevo de seriación.

## REFERENCIA_RN_1_5
RN-1.5 permanece como infraestructura de mejora de promedio para el contexto de posgrado. FASE 9C no la reimplementa.

## PROMEDIO_8_5_NO_ES_REQUISITO_GENERAL_EGRESO
Un promedio inferior a 8.5 no altera por sí solo el resultado académico RN-9.4.

## NO_CONFUSION_EGRESO_TITULACION
Cumplir RN-9.4 no equivale a título, servicio social, modalidad de titulación, expediente ni ejecución administrativa de egreso.

## AUDITORIA_DUPLICACION
REUTILIZADO: RN-7.1.1, tipo de UD FASE 7, motor FASE 8, RN-1.5 y trazabilidad RN-9.5.
ORQUESTADO: universo curricular verificable, resultado de categorías y requisito sombra de componentes finales.
No se agregaron rutas académicas, contadores de optativas, clasificadores de electivas ni motores de seriación paralelos.

## CONTRATO_RESULTADO_V2
`ContextoEvaluacionCurricularV2DTO` declara los universos de obligatorias, seriadas S1-S6 y componentes solicitados. `EvaluacionCargaAcademicaV2DTO` publica cantidades, pendientes, resultados de egreso académico y resultados por componentes. `UnidadEvaluacionCurricularV2DTO` incorpora el tipo curricular verificable de componente final.

## TRAZABILIDAD
RN-9.4 registra PE/estructura, obligatorias, optativas, electivas, seriadas S1-S6, componentes, pendientes y referencia a RN-1.5, sin PII adicional.

## PRUEBAS
`EvaluacionCurricularV2Fase9CTest` valida totalidad de obligatorias, fronteras 7/8/9, mínimo de una electiva, estado vigente, deduplicación, catálogos incompletos, Proyecto Terminal, Módulo Integrador, tipos de seriación, revisión de componente, no suficiencia RN-9.3/RN-1.5, determinismo e idempotencia.

## RIESGOS
La evaluación requiere catálogos y clasificaciones curriculares verificables del PE. Modalidades de titulación, servicio social y procesos administrativos permanecen fuera de alcance.

## ARCHIVOS_MODIFICADOS
- Contratos V2 de contexto, unidad y evaluación de carga.
- Enums de resultado RN-9.4 y tipo de componente curricular.
- Orquestación `EvaluacionCurricularV2ServiceImpl`.
- Pruebas FASE 9C.

## RESULTADO
RN-9.4 queda implementada en modo sombra, sin efectos productivos y sin iniciar RN-9.2, FASE 9D o FASE 10.
