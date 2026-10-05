# Validación GATE FASE 0 → FASE 1

**Fecha:** 2026-10-05
**Rama:** `feature/reglas-negocio-pe2027`
**Versión de reglas:** `PE2027-V1`
**Resultado del gate:** **CONDICIONAL — no autoriza iniciar FASE 1 automáticamente.**

## 1. Control de versión

La revisión se realizó sobre una rama limpia antes de los ajustes documentales de este gate. La FASE 0 se encuentra en el commit `1d42da60 feat: implementa fase 0 del motor curricular V2 en sombra`; los documentos de reglas y plan se encuentran en `7745a0f8`.

## 2. Validación de la migración

Se revisó `codigoFuente/bd/39_fase0_evaluacion_curricular_v2_sombra_20261005.sql` y se corrigió durante el gate para que no contenga operaciones de actualización ni eliminación, incluidas cascadas de eliminación por FK.

| Criterio | Resultado | Evidencia |
|---|---|---|
| Dependencias R047–R059 | Cumple | Los trece archivos R047 a R059 están presentes en `codigoFuente/bd/`. |
| Tablas de referencia | Cumple | En DEV se verificaron `tbl_persona`, `tbl_planes`, `tbl_procesos_inscripcion` y `tbl_ficha_descriptiva_programa`. |
| Tipos PK/FK | Cumple | Persona `BIGINT`; plan, proceso y programa `INT`; coinciden con las columnas propuestas. |
| Proceso opcional | Cumple | `id_proceso_inscripcion INT NULL` y FK conservadora. |
| Índices | Cumple | Se crearon índices por persona/plan, periodo/origen, proceso, huella, UD y regla. |
| Operaciones no aditivas | Cumple | La revisión textual no localizó `UPDATE` ni `DELETE`; las inserciones usan `WHERE NOT EXISTS`. |
| Impacto sobre historial | Cumple | El script no modifica calificaciones, participaciones, bajas, inscripciones ni selección. |

### Ejecución autorizada en DEV

La conexión se verificó como local de desarrollo antes de ejecutar: base `des_sisi_gestor`, host local `abelinux-lap`, puerto `3306`, conexión no marcada como sólo lectura. No se utilizó un ambiente de producción.

La migración se ejecutó correctamente en DEV. Posteriormente se verificaron:

- regla `EVALUACION_CURRICULAR_V2` activa;
- cinco parámetros V2 con sus valores esperados;
- tablas `tbl_evaluacion_curricular_v2`, `tbl_evaluacion_curricular_ud_v2` y `tbl_evaluacion_curricular_regla_v2`;
- siete FK hacia persona, plan, proceso, programa y detalle de evaluación;
- índices definidos por el script.

La ejecución no se realizó en PRE ni en PROD.

## 3. Expedientes reales consultados en modo de sólo lectura

Se consultaron fuentes históricas de DEV sin modificar registros. Para proteger datos personales, los expedientes se anonimizan como casos de gate. Los conteos se obtuvieron de calificaciones finales, bajas registradas y relaciones de seriación; no sustituyen las consultas canónicas de `InscripcionFacadeImpl`.

| Caso | Perfil requerido | Reprobadas detectadas | Bajas activas detectadas | Seriación histórica detectada | Estado de cobertura |
|---|---|---:|---:|---:|---|
| GATE-A | Regular | 0 | 0 | 0 | Fuente real localizada. |
| GATE-B | 1–3 reprobadas | 1 | 0 | 0 | Fuente real localizada. |
| GATE-C | 4 o más reprobadas | 6 | 6 | 0 | Fuente real localizada; también cubre baja. |
| GATE-D | Baja parcial/no cursada | 6 | 6 | 0 | Fuente real localizada. |
| GATE-E | Seriación | 3 | 0 | 2 | Fuente real localizada. |

Los estados que FASE 0 puede normalizar sin aplicar reglas nuevas son: `ACREDITADA`, `REPROBADA`, `BAJA_PARCIAL`, `NO_CURSADA`, `EN_CURSO`, `NO_INSCRITA` y `NO_DETERMINADO`. La fuente real confirma que los perfiles necesarios existen para la validación posterior de las fases académicas.

## 4. Comparación vigente contra sombra

| Elemento requerido | Resultado | Observación verificable |
|---|---|---|
| Estado por UD | Parcial | F0-I-01 y F0-I-06 prueban la conversión en memoria. Aún no existe un ejecutor de gate que tome automáticamente estos expedientes del contexto de aplicación y persista/imprima el detalle por UD. |
| Causa normalizada | Parcial | F0-U-01 a F0-U-04 y F0-I-06 verifican reprobación, baja y no cursamiento en memoria. Falta corrida automática sobre los cinco expedientes reales. |
| Número de reprobadas | Parcial | Los conteos reales anteriores fueron obtenidos en lectura; FASE 0 no cambia ni recalcula el contador vigente. |
| Oferta vigente | No validado extremo a extremo | El adaptador sombra recibe `UnidadDecisionInscripcionDTO`, pero no se añadió un consumidor operativo ni reporte de la oferta de cada expediente. |
| Estado vigente | Parcial | Las consultas reales y el contrato de origen están disponibles; falta invocar el Asistente desplegado contra los cinco casos y capturar el contexto. |
| Resultado sombra | Parcial | F0-U-06 y F0-I-01 a F0-I-06 prueban `EvaluacionCurricularV2ServiceImpl` y el adaptador; no se ejecutó todavía la ruta completa con datos reales de la aplicación. |
| Diferencias por caso | No validado extremo a extremo | Existe el formato en `REPORTE_DIFERENCIAS_VIGENTE_SOMBRA_FASE_0.md`, pero no hay persistencia/consulta de ejecuciones por expediente implementada en FASE 0. |

## 5. No afectación al comportamiento vigente

La evidencia disponible confirma que FASE 0 no conecta el resultado V2 a las vistas, al simulador, al validador o al proceso real de inscripción. Las pruebas F0-I-02, F0-I-04 y F0-I-05 verifican en memoria que la evaluación no modifica selección, oferta o marca de bloqueo del objeto origen.

No se ejecutó una reinscripción real ni se modificó una selección de estudiante durante este gate. Por ello, la no afectación en UI y en operación real queda **parcialmente validada**: está respaldada por la ausencia de integración productiva y por pruebas aisladas, pero no por una sesión completa de aplicación con los cinco expedientes.

## 6. Conclusión y condición para abrir FASE 1

La infraestructura FASE 0, la migración DEV y la suite de 14 pruebas están correctas. Sin embargo, el requisito de comparación real por expediente aún no puede declararse cumplido porque el motor sombra no tiene en FASE 0 un ejecutor de integración que:

1. obtenga el contexto canónico del Asistente para una persona real;
2. llame a `obtenerEvaluacionCurricularSombra`;
3. guarde o exponga el resultado por UD sin afectar flujos productivos;
4. compare oferta, estado vigente y resultado sombra por cada caso.

**Decisión del gate:** detenerse para revisión. No se inicia FASE 1 hasta autorizar un mecanismo de ejecución/consulta sombra de sólo lectura para esos cinco casos, o hasta aceptar formalmente que la validación de datos reales se ejecute en una fase posterior.
