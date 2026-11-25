# Resultado FASE 6A — RN-6.2

FASE_IMPLEMENTADA = FASE 6A

REGLA = RN-6.2

CONDICION = INSCRIPCION_INICIAL_DIFERIDA

## Aplicabilidad y trayectoria

RN-6.2 se evalúa únicamente con admisión acreditada e inscripción inicial no realizada. La salida sombra conserva la condición de trayectoria, período de admisión, período de incorporación, semestre objetivo, modalidad, UD de S2 compatibles/bloqueadas y evidencia RN-9.5. No representa inscripción, reinscripción, asignación de grupo ni modificación de registros productivos.

FUENTE_BASE_RN_6_2 = línea base vigente interpretada con PE2027-V1.

SEMANTICA_PE2027_RN_6_1 = RN-6.1 no aplica para semestre objetivo 1 o 2; sólo podrá reutilizarse posteriormente cuando el contexto alcance semestre 5 y periodo normativo mayor o igual a 2027-1.

SERIACION_1_A_2 = se reutiliza el motor V2. Dependencia directa o indirecta bloquea la incorporación directa a S2; seriación no determinable conserva REQUIERE_REVISION.

LIMITES_INACTIVIDAD = el límite excedido produce NO_PROCEDE_POR_INACTIVIDAD; la falta de verificación produce REQUIERE_REVISION; la alternativa posterior es INCORPORACION_DIFERIDA_SEMESTRE_1_PROCEDE_EN_SOMBRA.

NO_INSCRIPCION_NO_ES_REPROBACION = true. La falta de inscripción inicial no crea REPROBADA, IRREGULAR, REZAGO_LEVE, REZAGO_SEVERO ni RN-6.4 sin evidencia académica real.

RN_6_2_NO_ES_RN_6_5 = true. RN-6.2 cubre a la persona admitida que no realizó inscripción inicial; una interrupción posterior a periodos cursados permanece fuera de alcance de esta fase.

TRAYECTORIA_DIFERIDA = conserva período de admisión/incorporación, modalidad, semestre objetivo, resultado de seriación, inactividad y evidencia, sin inventar una trayectoria cursada.

TRAZABILIDAD = RN-9.5 registra RN-6.2, condición, período de admisión, período de incorporación, semestre objetivo, UD S2 compatibles/bloqueadas, resultado y evidencia; sin PII adicional.

## Cobertura de pruebas

COBERTURA_PRUEBAS = 12 pruebas F6A descubiertas por Maven Surefire, sin parametrización. Incluyen: aplicabilidad/no aplica; admisión e inscripción no determinables; S2 compatible; seriación directa, indirecta y no determinable; alternativa S1; límite de inactividad excedido/no comprobado; ausencia de reprobación/RN-6.4 artificial; RN-6.1 no aplicable en S1/S2 y reutilizable en S5; distinción RN-6.2/RN-6.5; duplicados; determinismo y ausencia de efecto productivo.

PRUEBAS_F6A_DESCUBIERTAS = true

CANTIDAD_PRUEBAS_F6A = 12

REGRESION_TOTAL = 474

FALLAS = 0

ERRORES = 0

OMITIDAS = 0

COMPILACION_BASEGESTOR = SUCCESS

COMPILACION_GESTORWEB = SUCCESS

RESULTADO = cierre técnico aprobado; cobertura F6A descubierta y ejecutada, regresión F0-F6A y compilación de ambos módulos exitosas.

RIESGOS = la fuente canónica debe proporcionar evidencia de admisión, inscripción inicial, inactividad y seriación; su ausencia se conserva como REQUIERE_REVISION.

ARCHIVOS_MODIFICADOS = EvaluacionCargaAcademicaV2DTO, EvaluacionCurricularV2ServiceImpl, EvaluacionCurricularV2Fase6ACierreTest, este resultado documental.

CAMBIO_PRODUCTIVO = NINGUNO
