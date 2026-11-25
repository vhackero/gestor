# Resultado Fase 7C: RN-7.3 Principio Común de Optativas y Electivas Reprobadas

## Identificación

- `FASE_IMPLEMENTADA = FASE 7C`
- `REGLA = RN-7.3`
- `VERSION_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

## Principio común optativas/electivas

RN-7.3 se implementa como una consolidación transversal de las salidas de RN-7.1/RN-7.1.1 y RN-7.2. No crea un tercer clasificador académico, contador de reprobadas, ruta de regularización ni evaluador de carga.

La consolidación identifica únicamente UD con tipo `OPTATIVA` o `ELECTIVA` y estado vigente `REPROBADA` respaldado por la normalización existente. Conserva por separado las obligatorias reprobadas relevantes para explicar el total común y la prioridad, pero no las reinterpreta como optativas o electivas.

## Situación irregular y total común

Las optativas y electivas reprobadas contribuyen a `IRREGULAR` por medio de RN-1.2. RN-7.3 verifica que el resultado común sea consistente, sin producir situaciones paralelas como `IRREGULAR_OPTATIVA` o `IRREGULAR_ELECTIVA`.

El campo `totalReprobadasComunRn73` replica la salida común `cantidadReprobadasVigentes`; no ejecuta un segundo conteo. Incluye obligatorias, optativas y electivas reprobadas vigentes, sin duplicados. No incorpora `ACREDITADA`, `NO_CURSADA`, `BAJA_PARCIAL`, `NO_INSCRITA`, `EN_CURSO` ni estados indeterminados como reprobaciones vigentes.

El estado vigente prevalece sobre la historia: una UD históricamente reprobada y actualmente acreditada no entra a RN-7.3 ni al total común actual.

## No bloqueo autónomo menor a cuatro

Cuando hay una o más optativas/electivas reprobadas y el total común es menor a cuatro, RN-7.3 expone `IRREGULAR_SIN_BLOQUEO_AUTONOMO` y `continuidadPotencialSujetaReglasRn73 = true`. Los indicadores de bloqueo autónomo por tipo para continuidad y acceso a quinto se mantienen en falso.

Esto no declara una inscripción procedente. Permanecen aplicables seriación, carga, permanencia, oferta/cupo, sanciones, RN-6.1 y cualquier otra restricción V2. Para objetivo S5, optativas/electivas no se agregan a la base obligatoria de semestres 1 a 4 de RN-6.1.1.

## Integración RN-6.4

RN-6.4 sigue siendo la única restricción severa por cuatro o más reprobadas reales. Si el total llega a cuatro, RN-7.3 informa `SUJETO_RN64`; el motivo del bloqueo es RN-6.4 y no el tipo de UD.

Ejemplos cubiertos:

| Composición vigente | Resultado común |
| --- | --- |
| 2 optativas + 1 electiva | Total 3; RN-6.4 no aplica |
| 2 optativas + 2 electivas | Total 4; RN-6.4 aplica |
| 1 obligatoria + 1 optativa + 2 electivas | Total 4; RN-6.4 aplica |

## Prioridad de obligatorias pendientes

RN-7.3 conserva la prioridad de reprobadas y pendientes obligatorias desde RN-6.3 y las rutas de reincorporación RN-6.5, RN-6.6 y RN-6.7. Cuando una obligatoria pendiente está marcada como prioritaria por cualquiera de esas fuentes, el resultado transversal es `PRIORIDAD_OBLIGATORIA_PREVALECE`; una optativa/electiva no desplaza esa prioridad.

La prioridad específica de RN-7.1 permanece: optativa reprobada ofertada precede a nueva optativa. RN-7.2 permanece sin obligar a repetir la misma electiva reprobada antes de evaluar otra. RN-7.3 no cambia ninguna de ambas reglas.

Si optativa y electiva reprobadas ofertadas compiten materialmente por carga insuficiente y no existe un desempate normativo, el resultado es `REQUIERE_REVISION`. No se ordena por identificador, nombre, semestre ni posición de colección.

## Carga, seriación, oferta y cupo

RN-7.3 consume RN-4.1, RN-4.2 y RN-4.3. No codifica límites de carga. Mantiene la seriación V2 por UD: el no bloqueo autónomo por tipo no ignora un prerrequisito formal. También conserva `SIN_OFERTA != SIN_CUPO`; no modifica disponibilidad, grupos, cupos ni oferta.

## Auditoría de duplicación

| Dependencia | Tratamiento RN-7.3 |
| --- | --- |
| Situación académica | Reutilizada: RN-1.2 |
| Total de reprobadas | Reutilizado: resultado común V2 |
| Restricción severa | Reutilizada: RN-6.4 |
| Prioridad de regularización | Reutilizada: RN-6.3 y RN-7.1 |
| Carga | Reutilizada: RN-4.x |
| Seriación | Reutilizada: motor V2 |
| Oferta/cupo | Reutilizado: contrato normalizado |

RN-7.3 solamente orquesta, clasifica y traza estas salidas.

## Contrato de resultado V2

`EvaluacionCargaAcademicaV2DTO` incorpora:

- aplicabilidad y estado del principio RN-7.3;
- conteos por tipo y referencia al total común;
- listas de reprobadas optativas, electivas y obligatorias relevantes;
- obligatorias prioritarias conservadas desde RN-6.3;
- indicadores de no bloqueo autónomo por tipo;
- continuidad potencial sujeta a otras reglas;
- decisiones por UD y revisión por conflicto material.

No se creó un DTO paralelo. Los datos quedan disponibles para Malla Curricular, Asistente y Simulador en una integración futura, sin conectar dichos consumidores en esta fase.

## Trazabilidad

RN-7.3 se agrega a reglas evaluadas, evidencias y trazas por UD de optativas/electivas reprobadas. La evidencia registra tipos, total común, RN-6.4, obligatorias prioritarias y los indicadores de no bloqueo autónomo. No se incorpora PII.

## Pruebas

`EvaluacionCurricularV2Fase7CTest` cubre 14 escenarios:

1. Ausencia de optativas/electivas reprobadas.
2. Irregularidad común por una optativa y una electiva.
3. Total combinado de tres sin RN-6.4 ni bloqueo autónomo.
4. Dos optativas y dos electivas con RN-6.4.
5. Conteo mixto con obligatoria sin contador paralelo.
6. Exclusión de baja parcial, no cursada y acreditada vigente.
7. Deduplicación técnica.
8. Exclusión de optativa de la base obligatoria de quinto.
9. Prevalencia de obligatoria prioritaria.
10. Conservación de RN-7.1 y semántica de RN-7.2.
11. No reactivación de RN-6.1 en S7 y distinción de disponibilidad.
12. Transición de cuatro a tres reprobadas.
13. Determinismo y ausencia de efectos productivos.
14. Conflicto material optativa/electiva sin jerarquía inventada.

## Riesgos

- La fuente no define desempate general entre optativa y electiva reprobadas; con carga insuficiente y conflicto material se requiere revisión.
- RN-7.3 no implementa sustituciones, equivalencias ni operaciones administrativas.
- Las fases RN-8.x y RN-9.x continúan fuera de alcance.

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoPrincipioOptativaElectivaV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase7CTest.java`

## Resultado

RN-7.3 queda implementada exclusivamente en sombra como principio de consolidación para optativas y electivas reprobadas. Se preservan RN-1.2, RN-6.3, RN-6.4, RN-7.1, RN-7.2, carga, seriación y disponibilidad como fuentes únicas. No se iniciaron Fase 7D, RN-8.x ni RN-9.x, y no se produjo ningún efecto productivo.
