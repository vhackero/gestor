# Resultado FASE 2F — Cierre integral de situación académica

## Estado de la fase

- **FASE_IMPLEMENTADA:** FASE 2F.
- **REGLAS_VALIDADAS:** RN-1.1, RN-1.2, RN-1.3, RN-1.4 y RN-1.5.
- **Versión normativa:** `PE2027-V1`.
- **Modo de operación:** SOMBRA.
- **CAMBIO_PRODUCTIVO:** NINGUNO.

Esta fase no incorpora una nueva decisión académica. Comprueba que las reglas de situación académica comparten el mismo `ContextoEvaluacionCurricularV2DTO`, se expresan en un único `ResultadoEvaluacionCurricularV2DTO` y no recalculan de forma incompatible los mismos datos.

## Matriz de consistencia

| Dimensión | Fuente canónica dentro de V2 | Resultado conservado | Invariante verificado |
|---|---|---|---|
| Actividad académica (RN-1.1) | Evidencia explícita del contexto: actividad, baja temporal, reincorporación y límites | `estadoActividadAcademica` | No se infiere inactividad por una UD no cursada o baja parcial. |
| Situación académica (RN-1.2) | UD normalizadas con `REPROBADA` y causa `REPROBACION`, vigente y de periodo previo | `situacionAcademicaCalculada`, `cantidadReprobadasVigentes`, UD origen | Baja parcial, no cursada, no inscrita, en curso y no determinado no se cuentan como reprobación. |
| Recuperación (RN-1.3) | Resultado de RN-1.2, actividad, permanencia y seriación conocida | condiciones, impedimentos y `recuperacionRegularidadProcede` | No vuelve a contar reprobadas; una pendiente no reprobatoria no impide por sí misma recuperación. |
| Intentos por UD (RN-1.4) | `intentosAcreditacion` identificados por ejecución/participación | `evaluacionesIntentos` | Tres intentos de una UD equivalen a una sola UD reprobada vigente, no a tres UD. |
| Mejora de promedio (RN-1.5) | Contador global explícito o eventos marcados `recursamientoMejora` | oportunidades globales y `evaluacionesMejoraPromedio` | No comparte contador con RN-1.4 ni afecta situación académica. |

## Escenarios integrados

La suite `EvaluacionCurricularV2Fase2FTest` cubre F2F-U-01 a F2F-U-14:

1. Activo, sin reprobadas: regularidad y actividad activa.
2. Una reprobada: irregularidad y oportunidad de intento independiente.
3. Tres UD reprobadas distintas: conteo por UD, no por intentos acumulados.
4. Tres intentos fallidos de una UD: una reprobada vigente y límite agotado.
5. Acreditación posterior: elimina la reprobación vigente, conserva los tres intentos.
6. Baja temporal con cero reprobadas: actividad y situación se mantienen separadas.
7. Baja temporal con reprobadas: conserva la irregularidad sin sobrescribir actividad.
8. Baja parcial y no cursada: no generan irregularidad sin reprobación.
9. Avance condicionado candidato: no convierte irregular en regular.
10. Mejora de promedio: conserva regularidad, contador global y resultado favorable.
11. Tres mejoras globales: agotan solamente RN-1.5.
12. Datos no determinados: preservan incertidumbre y no aplican efectos productivos.
13. Duplicados por bloque: no duplican reprobaciones ni intentos.
14. Contrato completo: conserva decisiones, evaluaciones por UD y trazas de RN-1.1 a RN-1.5.

## Contrato de resultado V2

`ResultadoEvaluacionCurricularV2DTO` ya contiene, sin campos contradictorios, las dimensiones requeridas para una futura lectura unificada por Malla Curricular, Asistente y Simulador:

- actividad: `estadoActividadAcademica`;
- situación: `situacionAcademicaCalculada`, cantidad y UD origen;
- recuperación: condiciones cumplidas/no cumplidas, impedimentos y resultado;
- intentos: `evaluacionesIntentos` por UD;
- mejora: oportunidades globales y `evaluacionesMejoraPromedio` por UD;
- concurrencia: condiciones, restricciones, excepciones, decisiones y mensajes;
- auditoría: `trazas`, con una entrada de evaluación por cada regla y por cada UD cuando aplica.

No se requirió rediseño del DTO: modificarlo en esta fase habría creado duplicidad sin mejorar el contrato. La consistencia se valida a través de la suite integrada.

## Trazabilidad

La ejecución sombra registra en `ResultadoEvaluacionCurricularV2DTO.getTrazas()` las evaluaciones RN-1.1, RN-1.2, RN-1.3, RN-1.4 y RN-1.5. Las decisiones por UD conservan sus trazas específicas. RN-9.5, implementada previamente, persiste este resultado sólo cuando el adaptador de auditoría autorizado se invoca; F2F no modifica ese mecanismo.

## Pruebas y regresión

- Suite integrada añadida: `EvaluacionCurricularV2Fase2FTest` (14 escenarios).
- Regresión ejecutada: suites F0, F1A–F1E y F2A–F2F.
- Resultado: **158 pruebas ejecutadas; 0 fallos; 0 errores; 0 omitidas**.
- Compilación ejecutada: `basegestor` y `gestorWeb` con `mvn -q -DskipTests -Dmaven.test.skip=true clean compile`.
- Resultado de compilación: correcto en ambos módulos.

La ejecución se limitó al motor sombra y a compilación. No se ejecutó integración visible ni se activó una decisión productiva V2.

## Riesgos pendientes

1. La calidad de los estados y de las identidades de intentos depende de los adaptadores y fuentes reales; si la identidad es insuficiente, V2 conserva `REQUIERE_REVISION` o `NO_DETERMINADO`.
2. La integración futura de Malla, Asistente y Simulador requiere el Gate E2E definido en el alcance vigente; esta fase no lo cierra ni conecta componentes visibles.
3. La trazabilidad persistida requiere que la migración aditiva de FASE 0 esté aplicada en DEV/QA; esta fase no ejecuta migraciones.
