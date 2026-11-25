# Resultado FASE 4A — RN-6.3 prioridad académica de regularización

## Resultado

- `FASE_IMPLEMENTADA = FASE 4A`
- `REGLA = RN-6.3`
- `INTERPRETACION_EN_ALCANCE_V2 = PRIORIDAD_ACADEMICA_DE_REGULARIZACION`
- `SIN_EFECTO_DE_REINSCRIPCION_PRODUCTIVA = true`
- `VERSIÓN_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

RN-6.3 se implementó como una evaluación académica explicable para consumidores futuros del Motor Curricular V2: Malla Curricular, Asistente y Simulador. Aunque la denominación normativa incluye reinscripción prioritaria, esta fase no ejecuta reinscripción, no reserva cupos y no modifica oferta, selección, grupos, carga ni validadores del proceso productivo. La inscripción/reinscripción sigue fuera del alcance de integración definido en `documentacion/ALCANCE_MOTOR_REGLAS_V2.md`.

## Dependencias reutilizadas

| Dependencia | Reutilización en RN-6.3 |
|---|---|
| RN-1.2 | `situacionAcademicaCalculada` y las reprobaciones vigentes son la fuente de aplicabilidad y prioridad; RN-6.3 no vuelve a calcular regularidad. |
| RN-2.2 | Se reutilizan oferta origen y decisiones sombra por UD. |
| RN-4.1, RN-4.2 y RN-4.3 | Se reutiliza `EvaluacionCargaAcademicaV2DTO`, su máximo normativo, estado integrado, prioridades, exclusiones y evidencia. No se crea otro contador de carga. |
| CTR-02 | Excepciones tipificadas y la información de indisponibilidad continúan visibles en el mismo resultado. |
| Seriación | `motivoDescarteCargaMinima` y la decisión sombra existente distinguen seriación de oferta/cupo. |
| RN-9.5 | La evaluación global y cada decisión por UD reciben trazas `RN_6_3`, que el servicio de trazabilidad existente puede persistir sin recalcular datos. |

## Modelo de prioridad y ruta de regularización

Se extendió de forma aditiva `EvaluacionCargaAcademicaV2DTO`, que ya era el resultado integrador de carga. No se creó un DTO paralelo. Los nuevos campos describen si RN-6.3 aplica, reprobadas prioritarias, reprobadas ofertadas/no disponibles, obligatorias y optativas compatibles, causas de indisponibilidad, candidatas empatadas, carga disponible de regularización y necesidad de revisión.

Para situación `IRREGULAR`, la ruta usa el siguiente orden descriptivo:

1. UD reprobadas vigentes y disponibles: `REPROBADA_PRIORITARIA`.
2. Otras UD obligatorias compatibles con la trayectoria.
3. Optativas compatibles cuando existe capacidad y no desplazan el nivel anterior.

Para `REGULAR`, `aplicaRn63=false` y se registra `REGULARIZACION_NO_APLICA`. Para `NO_DETERMINADA`, no se infiere una ruta: se marca revisión requerida y se traza insuficiencia de datos.

Cada reprobada vigente mantiene una sola prioridad aunque conserve varios intentos históricos; la consolidación previa de UD evita que bloques B1/B2, grupos o representaciones repetidas incrementen artificialmente el número de prioridades.

## Oferta, cupo, seriación e incertidumbre

Una reprobada no ofertada permanece en la ruta como reprobada prioritaria y además se registra como no disponible. La causa se conserva por separado:

- `SIN_OFERTA`: no hay oferta/grupo institucional disponible.
- `SIN_CUPO`: hay oferta pero no existe cupo conocido disponible.
- `SERIACION`: una relación de seriación bloquea o deja pendiente de revisión la compatibilidad.
- `DATOS_INSUFICIENTES`: el origen no permite determinar la disponibilidad de forma confiable.

Estas causas no transforman una reprobación en acreditada, no cursada o baja parcial. Una baja parcial, una no cursada, una no inscrita o los intentos históricos por sí solos tampoco generan una prioridad de RN-6.3.

## Carga y empates

La capacidad se deriva del máximo que ya calculó RN-4.2/RN-4.3. El método de RN-6.3 no selecciona unidades productivas ni introduce otra regla de carga. Si un mismo nivel de prioridad contiene más candidatas que espacios disponibles, no elige arbitrariamente: conserva todas en `udsEmpatadasRegularizacion`, marca `requiereRevisionRegularizacion=true` y deja la decisión para un criterio académico posterior.

La excepción de mínimo de RN-4.1 sigue permitiendo una carga menor a tres sólo en sombra; no incrementa el máximo aplicable ni elimina seriación, prioridades o restricciones observacionales.

## RN-6.4 y RN-6.1 / CTR-01

RN-6.3 conserva las detecciones existentes, sin activarlas:

- Cuatro o más UD reprobadas vigentes mantienen `rn64AplicableEnFuturaFase=true`. La fase no bloquea nuevas obligatorias ni limita la ruta exclusivamente a regularización.
- `ctr01Rn61Procedente=true` sólo puede provenir de quinto semestre y `periodoNormativo >= 2027-1`. Para tercero y séptimo es falso. No se aplica el Control de Avance Anual en esta fase.

## Trazabilidad

Además de la traza global de RN-6.3, cada UD prioritaria, compatible o no disponible recibe una traza por decisión con código de regla `RN_6_3`, motivo cuando procede y explicación técnica. Con RN-9.5, esta información permite reconstruir por qué una UD quedó en los niveles de prioridad, como compatible, no disponible o en revisión; se conserva el uso de identificadores técnicos y no se agrega PII.

## Pruebas agregadas

`EvaluacionCurricularV2Fase4ATest` contiene 23 pruebas `F4A-U-01` a `F4A-U-23`:

| Cobertura | Casos |
|---|---|
| No aplica para regular, una/dos/tres reprobadas | U-01 a U-04 |
| Orden reprobada → obligatoria → optativa | U-05 a U-07 |
| Reprobada sin oferta, sin cupo y con seriación | U-08 a U-10 |
| Empates y múltiples intentos | U-11 a U-12 |
| Baja parcial/no cursada no son reprobación | U-13 a U-14 |
| Detección observacional RN-6.4 | U-15 a U-16 |
| Detección CTR-01/RN-6.1 | U-17 a U-19 |
| Incertidumbre, duplicados, determinismo y ausencia de efecto | U-20 a U-23 |

Resultados finales de verificación:

| Comando | Resultado |
|---|---|
| `mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest,EvaluacionCurricularV2Fase1BTest,EvaluacionCurricularV2Fase1CTest,TrazabilidadEvaluacionCurricularV2ServiceTest,DiagnosticoE2EEvaluacionCurricularV2ServiceTest,EvaluacionCurricularV2Fase2ATest,EvaluacionCurricularV2Fase2BTest,EvaluacionCurricularV2Fase2CTest,EvaluacionCurricularV2Fase2DTest,EvaluacionCurricularV2Fase2ETest,EvaluacionCurricularV2Fase2FTest,EvaluacionCurricularV2Fase3ATest,EvaluacionCurricularV2Fase3BTest,EvaluacionCurricularV2Fase3CTest,EvaluacionCurricularV2Fase3DTest,EvaluacionCurricularV2Fase4ATest test` | 263 pruebas; 0 fallas; 0 errores; 0 omitidas. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/basegestor` | Compilación exitosa. |
| `mvn -q -DskipTests -Dmaven.test.skip=true clean compile` en `codigoFuente/gestorWeb` | Compilación exitosa. |

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase4ATest.java`
- `documentacion/RESULTADO_FASE_4A_REGLAS_V2.md`

## Riesgos pendientes

1. La integración visible con Malla, Asistente y Simulador permanece pendiente del Gate E2E y de una fase de integración autorizada.
2. RN-5.1 puede reutilizar esta ruta para los casos de una a tres reprobadas; no se implementó en F4A.
3. Los efectos completos de RN-6.4 y RN-6.1 siguen pendientes de sus fases específicas.
4. Los empates sin criterio académico aprobado continúan requiriendo revisión, por diseño.
