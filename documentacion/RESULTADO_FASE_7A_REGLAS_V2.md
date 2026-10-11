# Resultado Fase 7A: RN-7.1 y RN-7.1.1

## Identificación

- `FASE_IMPLEMENTADA = FASE 7A`
- `VERSION_NORMATIVA = PE2027-V1`
- `REGLAS = RN-7.1, RN-7.1.1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

## Alcance y reconciliación normativa

La implementación consume la actualización PE2027 de los numerales 1 a 3 de RN-7.1 y conserva RN-7.1.1 de la línea base: el requisito curricular se cumple con ocho UD optativas acreditadas. Los numerales operativos de la línea base relacionados con temáticas, grupos y gestión académica se reconocen como dependencias informativas; no se ejecutan ni producen cambios de oferta, grupos, cupos o inscripciones.

La evaluación se limita a unidades cuyo `tipoUd` identifica `OPTATIVA`. Una unidad sin tipo verificable marca revisión y una obligatoria o electiva no recibe semántica de optativa.

## Optativas reprobadas y situación académica

RN-7.1 no crea un clasificador paralelo. Las optativas con estado vigente `REPROBADA` y causa de reprobación alimentan el conteo común que ya utiliza RN-1.2; por ello contribuyen a la situación `IRREGULAR` de forma trazable. Las unidades `NO_CURSADA`, `NO_INSCRITA` y `BAJA_PARCIAL` no se convierten en reprobadas.

Hasta tres optativas reprobadas no generan por sí solas un bloqueo autónomo de continuidad ni de acceso a quinto. La evaluación mantiene las restricciones comunes: carga, seriación, permanencia, RN-6.1 y RN-6.4. Las optativas no se agregan a la base obligatoria de semestres 1 a 4 que consume RN-6.1.1.

## Integración con RN-6.4 y prioridad

RN-6.4 continúa usando el total común de reprobadas vigentes reales. Por tanto, cuatro optativas reprobadas, o tres optativas más una obligatoria reprobada, activan RN-6.4 sin introducir una regla especial de conteo.

Una optativa reprobada sólo se marca como prioritaria cuando está disponible y ya fue reconocida por la prioridad común de reprobación RN-6.3. RN-7.1 añade únicamente la relación específica `OPTATIVA_REPROBADA_PRIORITARIA > OPTATIVA_NUEVA`; no desplaza obligatorias pendientes ni crea una segunda ruta académica. Si RN-6.3 no puede determinar la prioridad, se conserva `REQUIERE_REVISION`.

`SIN_OFERTA` y `SIN_CUPO` se conservan como resultados distintos. La primera mantiene una prioridad potencial futura; la segunda conserva una restricción de capacidad. La fase no modifica oferta, grupos, cupos ni capacidad.

## Cumplimiento del requerimiento optativo

RN-7.1.1 cuenta únicamente UD optativas con estado vigente `ACREDITADA`, deduplicadas por identificador de programa. El conteo no incluye reprobadas, en curso, no cursadas, bajas parciales, no inscritas ni intentos históricos.

| Optativas acreditadas | Resultado sombra |
| --- | --- |
| 0 a 7 | `REQUERIMIENTO_OPTATIVO_PENDIENTE` |
| 8 | `REQUERIMIENTO_OPTATIVO_CUMPLIDO` |
| 9 o más | El requisito continúa cumplido; el excedente es adicional opcional |

Para identificar individualmente las unidades adicionales se incorporó `ordenAcreditacionOptativa`. Cuando hay más de ocho acreditadas y dicho orden no es verificable, la evaluación no inventa qué unidad es adicional y devuelve revisión. La condición de adicional afecta exclusivamente el requisito curricular: una optativa adicional reprobada sigue siendo una reprobación real mientras no exista una excepción normativa.

Si el contexto aporta un requisito del Programa Educativo distinto de ocho, se preserva la discrepancia como `REQUIERE_REVISION`; no se sustituye silenciosamente la norma PE2027-V1.

## Carga, seriación y RN-6.1

RN-7.1 consume los resultados ya calculados por RN-4.1, RN-4.2 y RN-4.3; no replica límites de carga. También reutiliza el motor de seriación V2: una optativa sólo queda sujeta a seriación si el Programa Educativo registra esa relación.

RN-6.1 permanece aplicable exclusivamente para semestre objetivo cinco desde `2027-1`. RN-7.1 no recalcula CTR-01 ni agrega optativas a su base de obligatorias. RN-6.4 mantiene precedencia cuando el total de reprobadas vigentes es cuatro o mayor.

## Contrato de resultado V2

`EvaluacionCargaAcademicaV2DTO` incorpora, sin crear DTO paralelo:

- aplicabilidad de RN-7.1 y RN-7.1.1;
- estado del requerimiento optativo;
- cantidad requerida, acreditada, faltante y adicional;
- listas de optativas identificadas, acreditadas, adicionales, reprobadas, prioritarias, sin oferta, sin cupo y nuevas;
- decisiones por UD y marca de revisión.

`UnidadEvaluacionCurricularV2DTO` incorpora el orden verificable de acreditación optativa para distinguir el excedente de ocho. `ContextoEvaluacionCurricularV2DTO` permite reportar una configuración del PE que contradiga el umbral normativo y, en ese caso, conservar revisión.

Estos datos son consumibles posteriormente por Malla Curricular, Asistente y Simulador. No se conectaron dichos consumidores en esta fase.

## Trazabilidad

Cada evaluación registra RN-7.1 y RN-7.1.1 en reglas evaluadas y conserva evidencia de cantidades, prioridad, oferta, cupo, RN-6.4 y RN-6.1. Las decisiones por UD contienen el identificador, la clasificación de optativa y su motivo. La información se mantiene sin PII adicional.

## Pruebas

`EvaluacionCurricularV2Fase7ATest` cubre 16 escenarios:

1. Exclusión de unidades no optativas.
2. Aporte de optativa reprobada a la situación común.
3. Tres optativas reprobadas sin RN-6.4.
4. Cuatro optativas reprobadas con RN-6.4.
5. Total mixto de tres optativas y una obligatoria.
6. No equiparación de baja parcial y no cursada con reprobación.
7. Prioridad sobre optativa nueva.
8. Diferencia entre falta de oferta y falta de cupo.
9. Requisito pendiente con cero y siete acreditadas.
10. Cumplimiento con ocho acreditadas.
11. Identificación de una novena adicional con orden verificable.
12. Deduplicación técnica.
13. Prevalencia de estado vigente acreditado.
14. Discrepancia de configuración del PE.
15. Exclusión de optativas de la base RN-6.1.1.
16. Determinismo y ausencia de efecto productivo.

## Riesgos y límites pendientes

- La fuente normativa fija ocho optativas; una configuración distinta del PE exige resolución normativa o de configuración antes de automatizar un valor alterno.
- La individualización de la novena o posteriores requiere evidencia de orden de acreditación; sin ella se mantiene revisión.
- La fase no implementa RN-7.2, RN-7.3, reglas específicas de electivas, workflow de grupos ni decisiones productivas de gestión académica.

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoRequerimientoOptativoV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/UnidadEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase7ATest.java`

## Resultado

RN-7.1 y RN-7.1.1 quedan implementadas exclusivamente en el Motor V2 sombra. La evaluación conserva la semántica de reprobación vigente, el requisito de ocho optativas acreditadas, la prioridad frente a nuevas optativas y la integración con motores comunes sin modificar inscripción, reinscripción, malla, asistente, simulador ni datos productivos.
