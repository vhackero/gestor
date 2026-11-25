# Resultado de FASE 1A — CTR-03 y CTR-04

## Alcance ejecutado

Esta fase amplía exclusivamente el resultado de la evaluación curricular V2 en modo sombra. La fuente de las decisiones productivas permanece en el motor vigente. No se modificaron la oferta real, seleccionabilidad, carga, selección, bloqueos productivos, estado académico persistido ni la representación visible del mapa curricular.

## Reglas y criterios representados

| Identificador | Implementación en FASE 1A | Efecto productivo |
| --- | --- | --- |
| CTR-03 | Se agregan `situacionAcademica` y `avanceCondicionado` como atributos independientes del resultado. La situación se deriva de UD normalizadas reprobadas; el avance condicionado se lee del contexto. | Ninguno; las trazas tienen `aplicada=false`. |
| CTR-04 | Las condiciones, motivos, reglas evaluadas, restricciones, excepciones, decisiones por UD y trazas se acumulan en colecciones independientes. | Ninguno; no se emite decisión de elegibilidad. |
| RN-1.2, RN-1.3 y RN-8 | Se registran como referencias técnicas de reprobación, no cursamiento/baja parcial y seriación cuando el contexto ya contiene esos hechos. | Ninguno. |
| RN-6.4 | Cuatro o más UD reprobadas se registran como condición concurrente de prioridad técnica 100. | Ninguno; la traza declara explícitamente `CONDICION_REGISTRADA_SIN_EFECTO_PRODUCTIVO`. |

La prioridad técnica es informativa para trazabilidad y no sustituye prioridades de negocio productivas. La máxima prioridad registrada se conserva en `prioridadTecnicaDominante`.

## Modelo resultante

`ResultadoEvaluacionCurricularV2DTO` conserva de forma separada:

- `situacionAcademica`: representación sombra `REGULAR` o `IRREGULAR` a partir de reprobaciones normalizadas.
- `avanceCondicionado`: dato independiente proporcionado por el contexto; no modifica la situación académica.
- `condicionesDetectadas`: condiciones concurrentes, entre ellas reprobación, baja parcial, pendiente no cursada, seriación, datos insuficientes, avance condicionado y cuatro o más reprobadas.
- `reglasEvaluadas`, `restricciones`, `excepciones`, `decisiones` y `trazas`: colecciones acumulativas, sin categoría excluyente.
- `efectosProductivosAplicados`: se fija en `false` para toda evaluación de esta fase.

Cada decisión por UD conserva `habilitada` como copia de la oferta recibida en el contexto. Este campo no es una decisión V2 ni modifica los validadores vigentes.

## Pruebas incorporadas

Archivo: `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase1ATest.java`.

| Prueba | Escenario | Resultado |
| --- | --- | --- |
| F1A-U-01 | Estudiante regular sin condición adicional. | Aprobada. |
| F1A-U-02 | Una UD reprobada. | Aprobada. |
| F1A-U-03 | Reprobación y baja parcial concurrentes. | Aprobada. |
| F1A-U-04 | Restricciones múltiples: reprobación, baja parcial, no cursamiento y datos insuficientes. | Aprobada. |
| F1A-U-05 | Seriación y pendiente sin cambio a la oferta fuente. | Aprobada. |
| F1A-U-06 | Avance condicionado sin cambio de situación académica. | Aprobada. |
| F1A-U-07 | Cuatro reprobadas registradas sin activar RN-6.4 productivamente. | Aprobada. |

Comando ejecutado:

```bash
cd codigoFuente/basegestor
mvn -q -Dtest=EvaluacionCurricularV2ServiceTest,ContextoEvaluacionCurricularV2AdapterTest,EvaluacionCurricularV2Fase1ATest test
```

Resultado: 21 pruebas ejecutadas, 0 fallas, 0 errores, 0 omitidas.

## Compilación

Se ejecutó compilación sin pruebas en ambos módulos:

```bash
cd codigoFuente/basegestor && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
cd codigoFuente/gestorWeb && mvn -q -DskipTests -Dmaven.test.skip=true clean compile
```

Resultado: ambas compilaciones finalizaron correctamente.

## Límites confirmados

- No se ejecutaron migraciones.
- No se modificaron adaptadores ni validadores productivos.
- No se activó CTR-01, RN-6.1 ni RN-6.4.
- No se inició FASE 1B.
- La validación E2E real del Gate 0→1 continúa pendiente y no fue sustituida por estas pruebas unitarias.
