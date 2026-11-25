# Resultado FASE 4C — RN-6.4 restricción por acumulación de UD reprobadas

## Resultado

- `FASE_IMPLEMENTADA = FASE 4C`
- `REGLA = RN-6.4`
- `CONDICION = REZAGO_SEVERO`
- `UMBRAL_REPROBADAS = 4`
- `VERSIÓN_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

RN-6.4 pasa de ser una detección observacional a una restricción efectiva únicamente dentro de la propuesta V2. No cambia comportamiento productivo de SIGIE: no bloquea inscripción, no altera oferta/cupo/selección/carga reales, no ejecuta bajas y no integra aún Malla, Asistente ni Simulador.

## Dependencias reutilizadas

| Dependencia | Uso en RN-6.4 |
|---|---|
| RN-1.2 | `cantidadReprobadasVigentes` y situación académica son la única fuente de aplicabilidad. |
| RN-6.3 | Se reutiliza la ruta, las reprobadas prioritarias, disponibilidad e indisponibilidades. |
| RN-4.1 | La excepción de mínimo se mantiene, pero no habilita obligatorias nuevas bajo RN-6.4. |
| RN-4.2 y RN-4.3 | Se reutilizan máximo, carga, exclusiones y resultado integrado. RN-6.4 no crea otro máximo. |
| RN-5.1 | Se desactiva para el rango de cuatro o más reprobadas. |
| RN-2.2, CTR-02 y seriación | Se conservan oferta individual, excepciones y restricciones existentes. |
| RN-9.5 | Se registran trazas globales y por UD bloqueada en la estructura que persiste la auditoría V2. |

## Aplicabilidad y clasificación

RN-6.4 aplica si y sólo si el conteo consolidado ya calculado por RN-1.2 es mayor o igual a cuatro. Al aplicar, el contrato conserva `situacionAcademica = IRREGULAR`, fija `condicionRegularizacion = REZAGO_SEVERO`, `aplicaRn64=true` y `aplicaRn51=false`. No se usa número de intentos, mejoras, bajas parciales, no cursadas, no inscritas, unidades en curso ni duplicados técnicos para alcanzar el umbral.

| Reprobadas vigentes | RN-5.1 | RN-6.4 | Condición |
|---:|---|---|---|
| 0 | No aplica | No aplica | No aplica |
| 1–3 | Aplica | No aplica | `REZAGO_LEVE` |
| 4 o más | No aplica | Aplica en sombra | `REZAGO_SEVERO` |
| No determinado | No se infiere | No se infiere | Revisión requerida |

Cuando el total baja de cuatro a tres, RN-6.4 deja de aplicar y RN-5.1 puede volver a clasificar `REZAGO_LEVE`; la situación sigue siendo irregular mientras persista cualquier reprobada. RN-6.4 no declara recuperación de regularidad, pues ésta sigue bajo RN-1.3.

## Bloqueo conceptual y ruta de regularización

Para una evaluación severa, las UD reprobadas vigentes siguen siendo prioritarias mediante RN-6.3. Las UD obligatorias pendientes que no pertenezcan a ese conjunto se anotan en `udsObligatoriasNuevasBloqueadasRn64`, se retiran de la propuesta sombra incluida y reciben:

- estado sombra `BLOQUEADA`;
- motivo `REZAGO_SEVERO`;
- exclusión `RN_6_4_REZAGO_SEVERO`;
- traza técnica individual `OBLIGATORIA_NUEVA_BLOQUEADA_RN64`.

La modificación de `ofertaEvaluadaV2` sólo describe la propuesta V2. `ofertaOrigen`, `seleccionableOrigen` y el proceso productivo no son escritos ni sustituidos.

Las optativas sólo aparecen en `udsOptativasAdicionalesElegiblesRn64` si ya son compatibles, no desplazan reprobadas prioritarias y existen espacios bajo el máximo reutilizado de RN-4.2. Si un conjunto de candidatas no cabe y no hay desempate aprobado, se conserva `REQUIERE_REVISION`; no se selecciona arbitrariamente.

Las reprobadas sin oferta, sin cupo o con seriación/datos insuficientes permanecen en la ruta y en las causas de indisponibilidad de RN-6.3. La falta de oferta no elimina la reprobación ni desactiva la restricción.

## Carga, mínimo y prevalencia

RN-6.4 conserva el máximo de seis de RN-4.2 para situación irregular. Una excepción de carga mínima puede permitir una carga sombra menor de tres, pero no elimina RN-6.4 ni permite llenar el mínimo con obligatorias nuevas bloqueadas.

La fase implementa la precedencia autorizada sobre avance condicionado sólo cuando CTR-01/RN-6.1 ya es procedente: quinto semestre y `periodoNormativo >= 2027-1`. Si simultáneamente existe `avanceCondicionado=true` y RN-6.4 aplica, el resultado sombra establece `avanceCondicionado=false` y `avanceCondicionadoBloqueadoPorRn64=true`. No se implementa el Control de Avance Anual completo. Para tercero y séptimo, RN-6.1 no procede.

## Trazabilidad

La traza RN-6.4 incluye total e IDs técnicos de reprobadas, ruta RN-6.3, reprobadas no disponibles, obligatorias nuevas bloqueadas, optativas adicionales, máximo, excepción mínima, procedencia CTR-01 y prevalencia sobre avance. Cada obligatoria bloqueada recibe además una traza por decisión. La estructura utiliza códigos e identificadores técnicos y no incorpora PII.

## Pruebas agregadas

`EvaluacionCurricularV2Fase4CTest` implementa 25 casos `F4C-U-01` a `F4C-U-25`:

- Transición entre rezago leve y severo, con tres, cuatro, cinco y seis reprobadas.
- Prioridad de reprobadas, bloqueo de obligatorias nuevas y optativas adicionales compatibles.
- Separación de no cursada, baja parcial, intentos, oferta, cupo y duplicados.
- Excepción mínima, máximo seis, empate, persistencia/retirada de la restricción y recuperación bajo RN-1.3.
- Prevalencia RN-6.4 sobre avance condicionado en quinto semestre, no procedencia para tercero/séptimo.
- Incertidumbre, determinismo y ausencia de efecto productivo.

Resultados finales de verificación:

| Comando | Resultado |
|---|---|
| `mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest,DiagnosticoE2EEvaluacionCurricularV2ServiceTest,EvaluacionCurricularV2Fase2ATest,EvaluacionCurricularV2Fase2BTest,EvaluacionCurricularV2Fase2CTest,EvaluacionCurricularV2Fase2DTest,EvaluacionCurricularV2Fase2ETest,EvaluacionCurricularV2Fase2FTest,EvaluacionCurricularV2Fase3ATest,EvaluacionCurricularV2Fase3BTest,EvaluacionCurricularV2Fase3CTest,EvaluacionCurricularV2Fase3DTest,EvaluacionCurricularV2Fase4ATest,EvaluacionCurricularV2Fase4BTest,EvaluacionCurricularV2Fase4CTest test` | 315 pruebas; 0 fallas; 0 errores; 0 omitidas. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/basegestor` | Compilación exitosa. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/gestorWeb` | Compilación exitosa. |

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/CondicionCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/CodigoMotivoCurricularV2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase4CTest.java`
- `documentacion/RESULTADO_FASE_4C_REGLAS_V2.md`

## Riesgos pendientes

1. La restricción se aplica sólo en el resultado V2; falta una fase autorizada y Gate E2E para cualquier integración visible futura.
2. RN-5.2 y el Control de Avance completo RN-6.1 no se implementan en F4C.
3. Empates y datos insuficientes continúan requiriendo revisión, sin una decisión automática implícita.
