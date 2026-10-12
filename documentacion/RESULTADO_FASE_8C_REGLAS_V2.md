# Resultado Fase 8C: RN-8.3, seriación interanual PE2027

## FASE_IMPLEMENTADA

`FASE 8C` implementa `RN-8.3` para seriación interanual bajo `PE2027-V1`, exclusivamente dentro del Motor V2 en sombra. Reutiliza el modelo común de Fase 8A y Fase 8B. No modifica inscripción, reinscripción, estados reales de UD, oferta, cupo, grupos, Malla Curricular, Asistente ni Simulador.

## REGLA

RN-8.3 evalúa prerrequisitos reales definidos por el Programa Educativo entre semestres o años curriculares distintos. Verifica relaciones directas, cadenas indirectas y múltiples antecedentes obligatorios. El resultado es individual por UD y conserva evidencia de los antecedentes responsables; no representa una autorización productiva.

## REESCRITURA_PE2027

La semántica PE2027 sustituye la interpretación histórica que subordinaba la seriación interanual a un cierre anual. El motor V2 no implementa la regla general “año anterior incompleto bloquea todo el año siguiente”. Una UD sólo queda restringida por RN-8.3 cuando existe un prerrequisito formal del PE que la conecte con un antecedente no acreditado.

## ELIMINACION_SEMANTICA_CIERRE_ANUAL

Se verificó que la orquestación RN-8.3 no consulta RN-6.1 para calcular relaciones, no usa conceptos de año N/año N+1 y no bloquea por pendientes de semestre anterior sin arista de prerrequisito. Las pruebas cubren S2→S3 y S6→S7 con pendientes sin relación formal: RN-8.3 no bloquea una UD independiente.

No se modificó el código productivo heredado. La eliminación de semántica se limita al Motor V2 y a sus resultados sombra.

## INDEPENDENCIA_RN_6_1

`DEPENDENCIA = NO`. RN-8.3 resuelve seriación a partir del grafo del PE y del estado vigente de los antecedentes. RN-6.1 continúa evaluándose por sus propios criterios para objetivo S5 y no reemplaza ni es reemplazada por RN-8.3.

## CONCURRENCIA_RN_6_1_S5

`CONCURRENCIA_S5 = SÍ`. Para periodo normativo desde `2027-1` y semestre objetivo 5, ambas decisiones se conservan. Una relación interanual cumplida puede coexistir con una restricción RN-6.1; una decisión favorable RN-6.1 no elimina una relación no cumplida. En S2→S3 y S6→S7 RN-6.1 no se reactiva.

## S2_A_S3

S2→S3 puede ser una relación interanual si el PE la clasifica explícitamente como tal. El antecedente no acreditado bloquea sólo la consecuente dependiente. Una pendiente S2 sin relación con una UD S3 no produce bloqueo RN-8.3 ni activa RN-6.1.

## S6_A_S7

S6→S7 se evalúa bajo el mismo principio: sólo una arista formal `INTERANUAL` permite evaluar RN-8.3. No se recuperó la semántica de cierre anual para el acceso a S7 y RN-6.1 permanece no aplicable.

## RELACIONES_DIRECTAS

Una relación directa explícita `A -> B` clasificada por el PE como `INTERANUAL` queda cumplida sólo si el estado vigente de A es `ACREDITADA`. Los estados `REPROBADA`, `NO_CURSADA`, `BAJA_PARCIAL`, `EN_CURSO` y `NO_INSCRITA` resultan no cumplidos. `NO_DETERMINADO` o antecedente ausente produce revisión. Las causas reales se conservan y no se generan reprobaciones artificiales.

## CADENAS_INDIRECTAS

El servicio recorre el grafo dirigido de prerrequisitos desde cada consecuente con relación interanual. Para `A -> B -> C`, C conserva B como antecedente directo y A como antecedente indirecto. Ambos se evalúan; un antecedente indirecto no acreditado mantiene C no cumplida aunque B esté acreditada.

Las relaciones evaluadas conservan profundidad y camino curricular, por ejemplo `[A, B, C]`, lo que permite explicar la cadena responsable en consumidores futuros.

## MULTIPLES_ANTECEDENTES

Todos los antecedentes obligatorios deben estar acreditados. La relación utiliza `antecedenteObligatorio`, cuyo valor predeterminado es verdadero hasta que el PE proporcione una semántica alternativa explícita. No se introduce una regla `OR`. Si la obligatoriedad o el tipo de relación no puede determinarse, el resultado es `REQUIERE_REVISION`.

Los múltiples caminos hacia un mismo antecedente no duplican el antecedente lógico en la lista de no acreditados, aunque el contrato puede conservar rutas distintas como evidencia.

## GRAFO_PRERREQUISITOS

El recorrido usa las relaciones curriculares ya presentes en las UD V2. Recorre antecedentes, deduplica relaciones locales, conserva la ruta, evita recorridos infinitos y evalúa cada UD de forma independiente. No se creó un `MotorSeriacionInteranualV2`; la capacidad reside en `EvaluacionCurricularV2ServiceImpl` junto con RN-8.1 y RN-8.2.

## DETECCION_CICLOS

Si el recorrido encuentra un ciclo, por ejemplo `A -> B -> C -> A`, la relación se marca `REQUIERE_REVISION`, registra `CICLO_DETECTADO_EN_GRAFO_SERIACION` y conserva el identificador en el contrato RN-8.3. El algoritmo no selecciona un antecedente arbitrario ni entra en recursión infinita. El ciclo se trata como anomalía de configuración del PE, no como una nueva regla académica.

## ESTADO_VIGENTE_VS_HISTORICO

La decisión curricular vigente se toma con el estado normalizado actual. Una UD históricamente reprobada y actualmente acreditada satisface el requisito. El historial se preserva en referencias de la UD para trazabilidad, intentos y auditoría, sin arrastrar bloqueos superados.

## AUSENCIA_RELACION_NO_AUTORIZA_NO_BLOQUEA

Cuando no existe relación formal `INTERANUAL`, RN-8.3 queda `NO_APLICA`: no bloquea por seriación y tampoco declara habilitación global. Carga, oferta, cupo, RN-6.4, sanciones y demás reglas pueden seguir restringiendo a la UD.

## BLOQUEO_POR_UD

La clasificación se realiza por consecuente. Si UD5-A depende de X no acreditada y UD5-B no tiene esa dependencia, sólo UD5-A recibe resultado no cumplido por RN-8.3. No existe un bloqueo global del semestre ni por tipo de UD.

## OFERTA_VS_SERIACION

Oferta y seriación son dimensiones separadas. Una relación cumplida puede coexistir con `SIN_OFERTA`; una UD ofertada puede tener relación no cumplida. El motor no modifica oferta ni traduce indisponibilidad en bloqueo de seriación.

## CUPO_VS_SERIACION

`SIN_CUPO` se conserva como capacidad. No equivale a `SIN_OFERTA` ni a incumplimiento de seriación. Una UD con seriación cumplida puede continuar con restricción de cupo.

## CARGA_VS_SERIACION

RN-8.3 reutiliza RN-4.1, RN-4.2 y RN-4.3. Cumplir la cadena no permite exceder carga y disponer de carga no autoriza ignorar un antecedente. No se codifican límites dentro de RN-8.3.

## RN_6_4_VS_SERIACION

RN-8.3 no modifica el conteo común de reprobadas. Una UD puede tener seriación cumplida y RN-6.4 activa; esos resultados se conservan de forma concurrente. `NO_CURSADA` y `BAJA_PARCIAL` que impidan un antecedente no incrementan RN-6.4.

## INTEGRACION_FASE_7

El tipo de UD y la seriación son dimensiones independientes. Una optativa o electiva con relación formal puede ser evaluada por RN-8.3 sin transformarse en obligatoria. RN-7.1, RN-7.2 y RN-7.3 siguen determinando sus propias condiciones y prioridades.

## AUDITORIA_REFERENCIAS_RN_6_1

La búsqueda del código V2 confirma que las referencias a RN-6.1 se concentran en su propio evaluador y reglas previas. La orquestación RN-8.3 sólo conserva en su evidencia si RN-6.1 aplica como condición concurrente; no consulta su decisión para derivar cumplimiento, bloqueo, grafo o antecedentes.

## AUDITORIA_DUPLICACION

`REUTILIZADO`: DTO de relación, enums de tipo y resultado, normalización de estados, decisiones por UD, trazas, oferta/cupo, carga y RN-6.4.

`EXTENDIDO`: profundidad de cadena, camino curricular, obligatoriedad, ciclo y campos de contrato RN-8.3.

`ORQUESTADO`: recorrido de grafo, deduplicación de antecedentes, agregación por UD y trazabilidad de cadena en el mismo servicio V2.

No se crearon recorridos paralelos, un motor nuevo, un contador de reprobadas ni una ruta académica adicional.

## CONTRATO_RESULTADO_V2

`RelacionSeriacionCurricularV2DTO` permite expresar antecedente, consecuente, tipo de seriación, directa/indirecta, estado vigente, profundidad, camino, obligatoriedad, ciclo, decisión, motivo y evidencia. `EvaluacionCargaAcademicaV2DTO` expone aplicabilidad/revisión RN-8.3, UD cumplidas/no cumplidas/en revisión, antecedentes no acreditados, ciclos y decisiones por UD.

Las restricciones concurrentes continúan disponibles en la decisión de la UD y en los resultados comunes: oferta, cupo, carga, RN-6.4 y RN-6.1 cuando corresponde. No se agregó un DTO paralelo ni persistencia adicional.

## TRAZABILIDAD

Cada relación evaluada registra una traza `RN_8_3` con motivo, camino, estado del antecedente y evidencia del PE. La traza consolidada registra las UD cumplidas, no cumplidas, en revisión, antecedentes no acreditados, ciclos y la aplicabilidad concurrente RN-6.1. No se añade PII.

## PRUEBAS

`EvaluacionCurricularV2Fase8CTest` contiene 23 escenarios agrupados que cubren: ausencia de relación, relación directa, todos los estados del antecedente, estado vigente frente a histórico, cadenas indirectas, múltiples antecedentes y caminos, ciclos, tipo no determinable, S2→S3, S6→S7, concurrencia S5/RN-6.1, eliminación de cierre anual legacy, oferta/cupo/carga/RN-6.4 concurrentes, bloqueo individual, optativas/electivas, deduplicación, determinismo, idempotencia, ausencia de efecto productivo y no regresión RN-8.1/RN-8.2/RN-8.3.

## RIESGOS

- RN-8.3 requiere que el PE clasifique explícitamente una relación como `INTERANUAL`; semestres distintos por sí mismos no bastan.
- Relaciones alternativas, equivalencias u obligatoriedad no determinada requieren fuente normativa adicional y producen revisión.
- Los ciclos detectados exigen corrección del catálogo curricular institucional.
- Malla, Asistente y Simulador continúan como consumidores futuros; no fueron conectados.

## ARCHIVOS_MODIFICADOS

- `RelacionSeriacionCurricularV2DTO`: profundidad, camino, obligatoriedad y ciclo.
- `EvaluacionCargaAcademicaV2DTO`: contrato agregado de RN-8.3.
- `EvaluacionCurricularV2ServiceImpl`: recorrido de grafo y orquestación RN-8.3 PE2027.
- `EvaluacionCurricularV2Fase8CTest`: evidencia automatizada Fase 8C.

## RESULTADO

`CAMBIO_PRODUCTIVO = NINGUNO`.

RN-8.3 PE2027 queda implementada en modo sombra. Fase 8D, Fase 9 y reglas RN-9.x no fueron iniciadas.
