# Resultado Fase 7B: RN-7.2 Unidades Didácticas Electivas

## Identificación

- `FASE_IMPLEMENTADA = FASE 7B`
- `REGLA = RN-7.2`
- `VERSION_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

## Electivas y reconciliación normativa

RN-7.2 conserva la semántica de la línea base. La implementación no recupera lógica histórica transversal: consume RN-1.2 para situación académica, RN-4.x para carga, RN-6.3 para prioridad de regularización, RN-6.4 para cuatro o más reprobadas, el motor común de seriación y la clasificación normalizada de oferta/cupo.

La evaluación sólo opera sobre UD formalmente clasificadas como `ELECTIVA`. Una UD de semestres 5 o 6, o perteneciente a otro Programa Educativo, no se convierte por ello en electiva. La ausencia de tipo curricular verificable conserva `REQUIERE_REVISION`.

## Elegibilidad S7/S8 y PE de origen distinto

La elegibilidad de una electiva nueva exige:

1. Semestre objetivo curricular del estudiante 7 u 8.
2. Electiva ubicada curricularmente en semestre 5 o 6.
3. `idPlan` del estudiante y `idPlanOrigenElectiva` de la UD disponibles.
4. Programa Educativo de origen distinto al del estudiante.
5. Oferta, cupo, seriación y carga evaluables mediante las salidas V2 comunes.

El estado global diferencia `NO_ELEGIBLE_POR_SEMESTRE`, `NO_ELEGIBLE_POR_MISMO_PE`, `RESTRINGIDA_POR_RN64`, `ELEGIBLE_CON_RESTRICCIONES`, `ELEGIBLE_PARA_ELECTIVAS_EN_SOMBRA` y `REQUIERE_REVISION`. No usa número de reinscripción, calendario o antigüedad como sustituto de semestre objetivo curricular.

## Electiva reprobada y nueva electiva

Una electiva con estado vigente `REPROBADA` conserva su efecto común: contribuye a `IRREGULAR` mediante RN-1.2 y al conteo de RN-6.4. No se transforma en acreditada, sustituida, cancelada ni deja de existir por evaluar otra electiva.

RN-7.2 no crea la obligación de acreditar Electiva A antes de evaluar Electiva B. La decisión sobre B se realiza por UD y sólo se restringe por carga, prioridad de regularización, RN-6.4, oferta/cupo, seriación o reglas existentes. Por tanto, una electiva reprobada no crea por sí misma una dependencia ficticia hacia otra electiva.

La fase no implementa equivalencias, sustituciones curriculares, cancelación de reprobación ni inscripción real. Si una futura decisión requiere determinar que B satisface un componente cursado previamente con A, el motor devuelve la dependencia normativa disponible, sin inferir una equivalencia.

## Carga, prioridad y RN-6.4

RN-7.2 reutiliza RN-4.1, RN-4.2 y RN-4.3. Un estudiante regular puede tener más de una electiva si las unidades son elegibles y existe capacidad; no se codifica un límite artificial de una electiva. Una situación irregular no bloquea automáticamente todas las electivas: se aplica la carga que resulte de RN-4.x y las restricciones comunes.

La prioridad de reprobadas se consulta desde RN-6.3. Una nueva electiva no desplaza una UD reprobada prioritaria. RN-6.4 mantiene precedencia cuando el total de reprobadas vigentes reales llega a cuatro, incluyendo electivas, optativas y obligatorias. `BAJA_PARCIAL` y `NO_CURSADA` no incrementan ese conteo.

## Oferta, cupo y seriación

`SIN_OFERTA` y `SIN_CUPO` se registran separadamente. Ninguno altera oferta, grupos, capacidad ni cupos. Cuando hay seriación formal directa o indirecta, se bloquea sólo la electiva afectada; otras candidatas continúan su propia evaluación. La reprobación de una electiva previa no se interpreta por sí misma como relación de seriación.

RN-6.1 no se reactiva: RN-7.2 opera para objetivo S7/S8, mientras que el control de avance anual PE2027 sólo corresponde a objetivo S5 desde `2027-1`.

## Dependencia futura RN-9.3 y grupos combinados

La evaluación registra `DEPENDENCIA_FUTURA_RN_9_3_NO_EVALUADA_EN_FASE_7B`. No calcula porcentaje de créditos ni aplica criterios de últimos semestres de RN-9.3. La formación combinada de grupos se conserva como operación fuera de alcance: no se crean grupos, no se mezclan estudiantes, no se asignan cupos y no se modifica oferta.

## Auditoría de duplicación

| Dependencia | Tratamiento RN-7.2 |
| --- | --- |
| Situación académica | Reutiliza RN-1.2 |
| Carga | Reutiliza RN-4.1, RN-4.2 y RN-4.3 |
| Prioridad | Reutiliza RN-6.3 |
| Restricción severa | Reutiliza RN-6.4 |
| Seriación | Reutiliza motor V2 existente |
| Oferta/cupo | Reutiliza clasificación normalizada |

No se crearon evaluadores paralelos para estas reglas.

## Contrato de resultado V2

`UnidadEvaluacionCurricularV2DTO` incorpora `idPlanOrigenElectiva` para representar de forma explícita el PE de origen de la electiva. `EvaluacionCargaAcademicaV2DTO` incorpora el estado de elegibilidad RN-7.2 y listas de electivas identificadas, elegibles, del mismo PE, sin oferta, sin cupo, bloqueadas por seriación, reprobadas y sujetas a prioridad previa, además de decisiones por UD.

El resultado permite a futuros consumidores de Malla, Asistente y Simulador conocer la elegibilidad académica, sin convertirla en una selección ni una operación de reinscripción.

## Trazabilidad

RN-7.2 queda registrada en reglas evaluadas y trazas por UD. La evidencia conserva semestre objetivo, electivas, elegibles, mismo PE, reprobadas, oferta/cupo, prioridad RN-6.3, RN-6.4 y la dependencia futura RN-9.3. No se incorpora PII adicional.

## Pruebas

`EvaluacionCurricularV2Fase7BTest` cubre 16 escenarios:

1. Exclusión de UD no electivas.
2. Elegibilidad S7/S8 y exclusión S6.
3. Semestre objetivo no determinable.
4. Mismo PE y PE de origen faltante.
5. Validación de semestre curricular de origen 5/6.
6. Reprobación de electiva y situación irregular.
7. Electiva A reprobada sin prerrequisito ficticio hacia B.
8. Conteo RN-6.4 y baja parcial no reprobatoria.
9. Prioridad de obligatoria reprobada frente a nueva electiva.
10. Distinción entre falta de oferta y falta de cupo.
11. Seriación por UD sin bloqueo global.
12. Más de una electiva regular y no activación de RN-6.1 en S7.
13. Restricción por carga máxima.
14. Deduplicación y ausencia de efecto productivo.
15. Determinismo.
16. Conteo mixto y no cursada no reprobatoria.

## Riesgos

- La fuente actual no define sustitución, equivalencia o cancelación de reprobación entre electivas; cualquier decisión de ese tipo requiere norma adicional.
- RN-9.3 no está implementada y puede agregar criterios para últimos semestres; esta fase no anticipa el porcentaje de créditos.
- El PE de origen de cada electiva debe llegar al contexto V2. Su ausencia produce revisión y no una inferencia.

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoElegibilidadElectivaV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/UnidadEvaluacionCurricularV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase7BTest.java`

## Resultado

RN-7.2 queda implementada exclusivamente en el Motor V2 sombra. La evaluación identifica y clasifica electivas para S7/S8, exige PE de origen distinto, conserva reprobaciones reales, respeta carga, prioridad, RN-6.4, oferta/cupo y seriación, y no ejecuta efectos productivos. RN-7.3, RN-9.3 y Fase 7C no fueron iniciadas.
