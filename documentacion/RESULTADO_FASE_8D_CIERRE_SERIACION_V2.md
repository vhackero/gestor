# Resultado Fase 8D - Cierre Motor de Seriacion V2

FASE_IMPLEMENTADA = FASE 8D

REGLAS_VALIDADAS:

- RN-8
- RN-8.1
- RN-8.2
- RN-8.3

VERSION_NORMATIVA = PE2027-V1

CAMBIO_PRODUCTIVO = NINGUNO

## MODELO_UNICO_SERIACION

El contrato comun reutiliza `RelacionSeriacionCurricularV2DTO`, `TipoSeriacionV2`, `TipoRelacionSeriacionV2`, `EstadoCumplimientoSeriacionV2`, el resultado por UD y las trazas V2. F8D incorpora solo `EstadoCierreFase8V2`, para auditar clasificaciones contradictorias de una misma relacion formal sin recalcular reglas.

## INTERBLOQUE

RN-8.1 mantiene B1 a B2 en el mismo periodo: antecedente acreditado cumple; antecedente en curso produce `CONDICIONADA`; los demas estados no acreditados no cumplen sin transformarse en reprobacion.

## INTERSEMESTRAL

RN-8.2 conserva acreditacion previa para relaciones PE entre semestres consecutivos. `EN_CURSO` es `NO_CUMPLIDA`, no hereda el estado condicionado de RN-8.1.

## INTERANUAL

RN-8.3 PE2027 recorre exclusivamente prerrequisitos formales del PE. Evalua relaciones directas e indirectas, multiples antecedentes, caminos y ciclos; no crea bloqueo global de semestre o de ano.

## DIFERENCIAS_SEMANTICAS

TIPO_SERIACION_VS_TIPO_RELACION se mantienen independientes. `INTERBLOQUE`, `INTERSEMESTRAL` e `INTERANUAL` no se infieren desde semestre, bloque, tipo de UD, nombre ni orden de malla. FUENTE_PE = relacion formal y evidencia curricular verificable.

## RELACIONES_DIRECTAS

La decision conserva antecedente, consecuente, tipo, estado vigente, motivo y evidencia. Una relacion se clasifica una sola vez; F8D marca `REQUIERE_REVISION` si el mismo par antecedente-consecuente llega con tipos de seriacion incompatibles.

## RELACIONES_INDIRECTAS

El recorrido del grafo conserva CAMINOS, profundidad, antecedentes directos e indirectos. MULTIPLES_ANTECEDENTES se resuelven como requisitos obligatorios conjuntos salvo evidencia PE distinta.

## DETECCION_CICLOS

Un ciclo produce `REQUIERE_REVISION_CONFIGURACION_SERIACION` equivalente, traza `CICLO_DETECTADO` y no recursiona infinitamente. DEDUPLICACION evita duplicar relaciones, antecedentes logicos, caminos identicos y bloqueos.

## ESTADO_VIGENTE_VS_HISTORICO

La decision usa el estado vigente: una acreditacion supera una reprobacion historica para seriacion. El historico queda disponible para auditoria e intentos. NO_REPROBACIONES_ARTIFICIALES: `NO_CURSADA`, `BAJA_PARCIAL`, `NO_INSCRITA` y `EN_CURSO` pueden impedir acreditar antecedente, pero no se convierten en `REPROBADA`.

## ELIMINACION_CIERRE_ANUAL_LEGACY

AUDITORIA_SEMANTICA_LEGACY: el calculo RN-8.3 en `EvaluacionCurricularV2ServiceImpl` no contiene dependencia de cierre anual ni de resultado RN-6.1; opera sobre relaciones formales. Se localizaron referencias de cierre anual solo en componentes heredados o diagnosticos fuera del motor de seriacion V2, que no fueron modificados. No existe en V2 la regla "ano previo incompleto bloquea todo el ano siguiente".

## INDEPENDENCIA_RN_6_1

S2 a S3 y S6 a S7 pueden evaluar RN-8.3 sin activar RN-6.1. En S5, CONCURRENCIA_RN_6_1_S5 conserva ambas decisiones: una restriccion RN-6.1 no altera el cumplimiento de seriacion y una seriacion no cumplida no es anulada por RN-6.1.

## INTEGRACION_RN_6_4

RN-6.4 permanece independiente: una UD puede tener seriacion cumplida y estar restringida por cuatro o mas reprobadas reales. INTEGRACION_CARGA reutiliza RN-4.x y no codifica limites. INTEGRACION_FASE_6 consume el mismo resultado en rutas de reincorporacion; INTEGRACION_FASE_7 preserva `OBLIGATORIA`, `OPTATIVA` y `ELECTIVA` como tipos independientes de la seriacion.

## OFERTA_VS_SERIACION

Oferta, CUPO_VS_SERIACION, carga, prioridad y seriacion permanecen como motivos concurrentes. `SIN_CUPO` no equivale a `SIN_OFERTA` ni a seriacion no cumplida.

## RESULTADO_UNICO_POR_UD

El contrato V2 entrega una sola vista por UD con relaciones, antecedentes, caminos, estados, cumplimiento, motivos, oferta, cupo, carga, RN-6.4, RN-6.1, prioridad y evidencia. No se crean DTO ni rutas paralelas por RN-8.1, RN-8.2 o RN-8.3.

## IDEMPOTENCIA

El mismo grafo y contexto no duplica relaciones, caminos, antecedentes o decisiones. Las pruebas repiten evaluacion sin efectos productivos.

## DETERMINISMO

El resultado depende del PE, grafo, estados vigentes, periodo, semestre, bloque, oferta, cupo, carga y version normativa, no del orden tecnico de colecciones.

## AUDITORIA_DUPLICACION

| Componente | Estado |
| --- | --- |
| Modelo de relacion, enums, estados y DTO por UD | REUTILIZADO |
| Evaluacion RN-8.1, RN-8.2 y RN-8.3 | ORQUESTADO sobre el modelo comun |
| Recorrido de cadena interanual | EXTENDIDO en RN-8.3, sin motor paralelo |
| Cierre de coherencia F8D | EXTENDIDO aditivamente |
| Situacion, carga, oferta/cupo, RN-6.4 y RN-6.1 | REUTILIZADO como restricciones concurrentes |

## CONTRATO_RESULTADO_V2

`EvaluacionCargaAcademicaV2DTO` expone relaciones de seriacion comunes, resultados por regla y el estado de cierre F8D, incluyendo inconsistencias de tipo. `ResultadoEvaluacionCurricularV2DTO` y las decisiones por UD conservan motivos y trazas consumibles por Malla Curricular, Asistente y Simulador en el futuro.

## TRAZABILIDAD

RN-8, RN-8.1, RN-8.2 y RN-8.3 registran PE, relacion, antecedentes, caminos, estados, cumplimiento, ciclos, restricciones concurrentes, RN-6.1 cuando corresponde, evidencia y PE2027-V1. No se incorpora PII adicional.

## PRUEBAS

`EvaluacionCurricularV2Fase8DTest` agrega 12 pruebas integradas: diferencias semanticas de `EN_CURSO`, acreditacion vigente, no reprobaciones artificiales, historico frente a vigente, cadenas, deduplicacion, ciclos, inconsistencias de tipo, eliminacion del cierre anual legacy, concurrencia RN-6.1 S5, restricciones concurrentes, idempotencia y ausencia de efecto productivo.

## RIESGOS_PENDIENTES

La calidad de relaciones, tipos y obligatoriedad depende del catalogo del PE. Configuraciones ambiguas, incompletas o ciclicas se devuelven para revision; no se infieren ni corrigen silenciosamente. RN-9.x permanece fuera de alcance.

## ARCHIVOS_MODIFICADOS

- `codigoFuente/basegestor/.../EstadoCierreFase8V2.java`
- `codigoFuente/basegestor/.../EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/.../EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/.../EvaluacionCurricularV2Fase8DTest.java`
- `documentacion/RESULTADO_FASE_8D_CIERRE_SERIACION_V2.md`

## RESULTADO

FASE 8 queda consolidada tecnicamente en modo sombra. RN-8.1, RN-8.2 y RN-8.3 comparten un unico modelo de seriacion, preservan sus diferencias normativas y no producen efectos productivos.
