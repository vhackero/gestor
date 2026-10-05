# Plan concreto — FASE 0 de Reglas V2 aprobadas

**Fecha:** 2026-10-05  
**Rama de trabajo confirmada:** `feature/reglas-negocio-pe2027`  
**Fuentes de decisión:** `V2_REGLAS_APROBADAS.md` (normativa), `MATRIZ_REGLA_CODIGO.md` (estado técnico). `V1_LINEA_BASE.md` sólo se utilizará para pruebas de regresión del comportamiento heredado no conflictivo.

## 1. Propósito y límite de la fase

FASE 0 construye el sustrato técnico para que las fases posteriores evalúen reglas de forma concurrente, consistente y explicable. No cambia todavía la elegibilidad de una UD, los límites de carga ni la oferta presentada al usuario. En particular, **no activa CTR-01 ni reinterpreta las reglas de 1.2, 1.3, 5.x, 6.x, 7.x, 8.x o 9.x**: sólo registra y normaliza los datos que esas fases consumirán.

Resultado de salida de FASE 0: para una misma persona, plan y periodo, Mapa, Asistente, simulador, validador e inscripción pueden obtener un mismo `ContextoEvaluacionCurricularV2DTO` y comparar su resultado en modo sombra contra el comportamiento vigente.

## 2. Inventario de activos que se reutilizarán

| Activo actual | Uso en FASE 0 | Restricción |
|---|---|---|
| `InscripcionFacadeImpl` | Fuente de persona, plan, proceso/oferta, cursadas, reprobadas, bajas, límites y seriación. | No sustituir `construirContextoInscripcion`, `obtenerEstadoAcademico` ni validadores actuales. |
| `AsistenteInscripcionServiceImpl` | Fuente de unidades de decisión, diagnóstico, pendientes, proyección, optativas/electivas y relaciones de seriación. | No modificar decisiones visibles todavía. |
| `MallaCurricularAlumnoBean` y `MallaDiagramaNodoDTO` | Fuente/consumidor para comprobar estado mostrado de cada UD. | No cambiar color, leyenda ni estatus de la malla en esta fase. |
| Servicios `gestionescolar/v2` | Clasificación, diagnóstico, patrón, dictamen, bitácora y evidencia. | Reutilizar trazabilidad; no duplicar el concepto de caso. |
| `cat_reglas_inscripcion`, `cat_parametros_regla_inscripcion` (`R047`, `R053`, `R058`, `R059`) | Configuración de reglas y parámetros existentes. | No alterar scripts R047–R059; crear migración aditiva propia si se requieren nuevos valores. |
| `tbl_caso_*`, `tbl_caso_patron_aplicado` | Auditoría de caso y patrón aplicado. | No usar como sustituto de una ejecución de evaluación si falta granularidad por UD. |

## 3. Diseño de contratos de dominio

### 3.1 Estado y causa de cada UD

Se crearán tipos Java en `basegestor/commons/dto/gestionescolar/v2` (o paquete `commons/enums/gestionescolar/v2` si el patrón del proyecto separa enums):

```text
EstadoUnidadCurricularV2
  ACREDITADA | REPROBADA | NO_CURSADA | BAJA_PARCIAL | EN_CURSO | NO_INSCRITA | NO_DETERMINADO

CausaPendienteUnidadV2
  REPROBACION | NO_CURSAMIENTO | BAJA_PARCIAL | SIN_OFERTA | SERIACION | LIMITE_INTENTOS |
  INACTIVIDAD | OTRA_INSTITUCIONAL | NO_DETERMINADA
```

Reglas de mapeo inicial, sin inferencia silenciosa:

| Evidencia origen | Estado normalizado | Causa | Observación |
|---|---|---|---|
| Calificación definitiva aprobatoria | `ACREDITADA` | n/a | Mantener fuente/calificación. |
| Calificación definitiva no aprobatoria | `REPROBADA` | `REPROBACION` | Cuenta para reglas posteriores de reprobación. |
| Participación/grupo activo sin calificación final | `EN_CURSO` | n/a | No cuenta como reprobada. |
| Registro institucional de baja parcial | `BAJA_PARCIAL` | `BAJA_PARCIAL` | No se transforma en reprobada. |
| UD curricular sin inscripción/cursamiento comprobable | `NO_CURSADA` | `NO_CURSAMIENTO` | Sólo si la fuente permite identificar que correspondía a trayectoria. |
| Datos contradictorios o insuficientes | `NO_DETERMINADO` | `NO_DETERMINADA` | No habilita ni restringe por una regla nueva; genera traza. |

`NO_INSCRITA` se conservará como estado de representación de oferta/historial si la fuente actual lo utiliza; no será sinónimo de pendiente ni de reprobación.

### 3.2 Contexto único

Se crearán DTOs inmutables o de construcción controlada:

- `ContextoEvaluacionCurricularV2DTO`: `idPersona`, `idPlan`, `idPeriodo`, `idProcesoInscripcion`, `semestreObjetivo`, `bloqueObjetivo`, perfil/origen (`ESTUDIANTE`/`GESTOR`), fecha-hora, versión de reglas, huella de oferta, límites de carga, historial normalizado, relaciones de seriación y bajas.
- `UnidadEvaluacionCurricularV2DTO`: identificador y clave UD, tipo, semestre/bloque, créditos, estado, causa, fuente de evidencia, oferta/grupo/cupo, antecedentes directos y relaciones conocidas.
- `ResultadoEvaluacionCurricularV2DTO`: situación calculada **sin sustituir todavía la vigente**, reglas evaluadas, decisiones por UD, restricciones, excepciones, mensajes, oferta elegible resultante y trazas.
- `DecisionUnidadCurricularV2DTO`: habilitada/bloqueada/prioritaria/requiere revisión, motivos y reglas; permite múltiples motivos concurrentes.
- `TrazaReglaAplicadaV2DTO`: código, versión, prioridad, resultado, variables relevantes no sensibles, fecha y origen.

### 3.3 Códigos y prioridad técnica

Se añadirán constantes/catálogos para `CTR-01`…`CTR-04`, `RN-1.1`…`RN-10.1`, y motivos como `REPROBACION_ACUMULADA`, `PENDIENTE_NO_CURSADA`, `BAJA_PARCIAL`, `SERIACION_DIRECTA`, `SERIACION_INDIRECTA`, `OFERTA_NO_VIGENTE`, `CARGA_MINIMA_EXCEPCION` y `DATOS_INSUFICIENTES`.

En FASE 0 se registra la prioridad declarada en V2 —en particular **6.4 prevalece sobre 6.1 y la seriación nunca se omite**— pero el evaluador sólo la materializará en Fases 3, 5 y 8. Esto evita un cambio funcional prematuro.

## 4. Archivos y métodos que se modificarán

| Tipo | Archivo/clase | Cambio FASE 0 | Métodos afectados o nuevos |
|---|---|---|---|
| Nuevo DTO | `.../commons/dto/gestionescolar/v2/ContextoEvaluacionCurricularV2DTO.java` | Contrato de entrada unificado. | builder/getters. |
| Nuevo DTO | `UnidadEvaluacionCurricularV2DTO`, `ResultadoEvaluacionCurricularV2DTO`, `DecisionUnidadCurricularV2DTO`, `TrazaReglaAplicadaV2DTO` | Estado/causa, resultado concurrente y auditoría. | builders/validación básica. |
| Nuevo enum | `EstadoUnidadCurricularV2`, `CausaPendienteUnidadV2`, `CodigoReglaCurricularV2`, `CodigoMotivoCurricularV2` | Vocabulario estable. | n/a. |
| Nueva interfaz | `service/gestionescolar/v2/EvaluacionCurricularV2Service.java` | Contrato `evaluar(Contexto…)` y `construirContexto(...)`. | nuevos. |
| Nueva implementación | `service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java` | Adaptador en modo sombra; no cambia oferta real. | `construirContexto`, `normalizarUnidades`, `evaluarSinActivarReglasNuevas`, `registrarTraza`. |
| Adaptador | `InscripcionFacadeImpl.java` | Exponer datos existentes al constructor de contexto, sin cambiar filtros actuales. | nuevo método de lectura, no cambiar validadores. |
| Adaptador | `AsistenteInscripcionServiceImpl.java` | Solicitar evaluación en modo sombra y adjuntarla al contexto interno; no usarla para decidir UI. | nuevo método privado de diagnóstico comparativo. |
| Adaptador | `MallaCurricularAlumnoBean.java` y/o servicio de malla | Asociar clave/estado actual para comparación. | sólo lectura; no alterar renderizado. |
| DTO existente | `AsistenteInscripcionContextoDTO.java` | Campo opcional de resultado/traza resumida **sólo si no rompe serialización JSF**; de lo contrario mantenerlo fuera del DTO de vista. | getter/setter. |
| Repositorios V2 | nuevos repositorios para evaluación si la entidad se persiste. | Persistencia aditiva de encabezado/detalle/traza. | guardar/consultar por huella. |

Antes de crear archivos se validará el patrón de paquetes, estilo de DTO y `persistence.xml`; no se modificarán módulos fuera de este ámbito.

## 5. Migración de base de datos propuesta

### 5.1 Alcance aditivo

Se preparará un **nuevo script**, con numeración posterior a los scripts V2 propios vigentes y sin modificar R047–R059 ni scripts de otros equipos. El script no se ejecutará hasta revisión.

Tablas candidatas:

1. `tbl_evaluacion_curricular_v2`: ejecución/encabezado: persona, plan, periodo/proceso, semestre/bloque objetivo, perfil/origen, versión, huella de oferta, fecha, resultado global y estado.
2. `tbl_evaluacion_curricular_ud_v2`: detalle por UD: programa/clave, tipo, semestre/bloque, estado normalizado, causa, oferta, elegibilidad y fuente de estado.
3. `tbl_evaluacion_curricular_regla_v2`: una fila por regla evaluada/aplicada, prioridad, motivo, resultado y mensaje técnico.
4. Opcional `cat_regla_curricular_v2` / `cat_motivo_curricular_v2`: sólo si los catálogos existentes no admiten versión, prioridad y ámbito; se preferirá extender mediante claves configurables existentes.

### 5.2 Integridad y retención

- FK a persona/plan/proceso/caso sólo cuando los IDs existan; una evaluación de consulta sin oferta activa no debe fallar por proceso nulo.
- Índice compuesto para persona+plan+periodo+origen+huella, destinado a idempotencia.
- No almacenar nombres personales, calificaciones completas ni selección sensible en texto de bitácora; referenciar IDs/UD y resumen técnico.
- No habrá `UPDATE` masivo de registros históricos ni reclasificación retrospectiva en esta fase.

### 5.3 Impacto histórico

Los datos antiguos con baja/no cursamiento ambiguos quedarán `NO_DETERMINADO`. Se creará reporte de calidad, no una conversión automática. Una migración destructiva o de corrección histórica requerirá autorización específica y se detendrá antes de ejecutarse.

## 6. Configuración y vigencia

Se aprovechará `cat_reglas_inscripcion` y `cat_parametros_regla_inscripcion`, verificando primero sus entidades/servicios. Parámetros propuestos:

| Clave | Valor inicial | Uso |
|---|---|---|
| `REGLAS_V2_VERSION` | `2027-1` | Identificador normativo de evaluación. |
| `REGLAS_V2_MODO_SOMBRA` | `true` | Genera/contrasta resultado sin afectar decisión productiva. |
| `CTR01_PERIODO_INICIO` | `2027-1` | Vigencia de Control de Avance Anual. |
| `CTR01_SEMESTRE_OBJETIVO` | `5` | Ámbito exclusivo del control. |
| `EVALUACION_V2_TRAZABILIDAD_ACTIVA` | `true` en DEV/QA, `false` o controlada en PROD | Registro gradual. |

La presencia de estas claves no activa Fase 2 ni Fases posteriores: sólo permite que el contexto las lea y las deje trazadas.

## 7. Pruebas a agregar antes de cambiar comportamiento

### 7.1 Unitarias

| ID | Unidad | Escenario | Resultado esperado |
|---|---|---|---|
| F0-U-01 | Normalizador | Calificación aprobatoria/reprobatoria | `ACREDITADA`/`REPROBADA` con causa correcta. |
| F0-U-02 | Normalizador | En curso sin calificación | `EN_CURSO`; no incrementa reprobadas. |
| F0-U-03 | Normalizador | Baja parcial/no cursada | Causa explícita; no se convierte en reprobación. |
| F0-U-04 | Normalizador | Información contradictoria | `NO_DETERMINADO`, traza de calidad y sin inferencia. |
| F0-U-05 | Contexto | Misma UD en B1/B2 o múltiples participaciones | Identificación distinct por UD/programa conforme fuente; sin doble conteo. |
| F0-U-06 | Resultado | Dos o más motivos concurrentes | Conserva todas las reglas/motivos, no sólo categoría dominante. |
| F0-U-07 | Vigencia | `2026-2`, `2027-1`, semestre 3/5/7 | Sólo registra configuración; no cambia elegibilidad F0. |
| F0-U-08 | Trazabilidad | Reevaluación idéntica | Idempotente por huella o nueva versión explícita, según diseño aprobado. |

### 7.2 Integración/regresión

| ID | Flujo | Verificación |
|---|---|---|
| F0-I-01 | Mapa vs Asistente | Mismo estado normalizado por UD y misma clave/nombre; la vista permanece sin cambio funcional. |
| F0-I-02 | Simulador/validador/inscripción | La evaluación sombra no cambia la selección, oferta o resultado vigente. |
| F0-I-03 | Sin oferta activa | Se genera contexto/traza sin excepción de proceso inexistente. |
| F0-I-04 | Nuevo ingreso | La regla vigente 4 obligatorias+2 optativas mantiene resultado actual. |
| F0-I-05 | Seriación actual | La oferta y bloqueo actuales no cambian; se registra antecedente. |
| F0-I-06 | Baja/reprobada/no cursada | Se comparan causas contra fuentes y se reportan ambiguos. |

No se declarará una prueba aprobada sin ejecución y resultado almacenado en el reporte de FASE 0.

## 8. Riesgos y controles

| Riesgo | Impacto | Control |
|---|---|---|
| Duplicidad por bloque, grupo o intentos | Sobreconteo de reprobadas/pedientes. | Llave de normalización definida, tests F0-U-05 y fuente por fila. |
| Bajas/no cursamiento ambiguos | Aplicación indebida de irregularidad futura. | `NO_DETERMINADO`; no inferencia; reporte de calidad. |
| Consulta sin proceso/oferta | Error de UI como el ya observado históricamente. | `idProceso` opcional en contexto/traza; pruebas F0-I-03. |
| Cambiar resultado vigente por integración temprana | Regresión de inscripción. | Modo sombra obligatorio y adaptadores de sólo lectura. |
| Trazabilidad excesiva/datos personales | Riesgo de privacidad y crecimiento. | IDs técnicos, retención configurada, sin PII en mensajes técnicos. |
| Divergencia de configuración por entorno | Resultado distinto DEV/PRE/PROD. | Script aditivo, consulta de configuración y reporte de versión al evaluar. |

## 9. Estrategia de rollback

1. Desactivar `REGLAS_V2_MODO_SOMBRA`/consumidor mediante configuración.
2. Mantener intactos `InscripcionFacadeImpl`, validadores actuales y renderizado del mapa mientras no exista activación formal.
3. No eliminar tablas de trazabilidad ni scripts aplicados; dejar de escribir/consumir y conservar evidencia para diagnóstico.
4. Si un DTO de vista genera incompatibilidad JSF, retirar sólo el adaptador de vista y conservar el servicio interno sin tocar reglas existentes.

## 10. Criterios de aceptación para cerrar FASE 0

- Compilación de `basegestor` y `gestorWeb` exitosa.
- Tests F0-U-01 a F0-U-08 ejecutados exitosamente.
- Tests de integración F0-I-01 a F0-I-06 sin cambio observable no autorizado de oferta, restricciones, selección ni mapa.
- Una evaluación persistida o en memoria identifica versión, persona, plan, periodo, semestre objetivo, UD/causa, reglas evaluadas, motivos, timestamp y huella de oferta.
- Los casos ambiguos aparecen como tales y no son reclasificados como reprobados.
- Reporte de diferencias entre resultado vigente y sombra revisado antes de iniciar FASE 1.

## 11. Entregables de cierre de fase

Al concluir la implementación se generará `documentacion/RESULTADO_FASE_0_REGLAS_V2.md` con el formato obligatorio:

```text
FASE_IMPLEMENTADA: FASE 0
REGLAS_IMPLEMENTADAS: infraestructura transversal; sin cambio de elegibilidad
ARCHIVOS_MODIFICADOS: lista exacta
MIGRACIONES: script y estado de aplicación
PRUEBAS_AGREGADAS: IDs y rutas
PRUEBAS_EJECUTADAS: comando/fecha/resultado
RESULTADO: comparación vigente vs sombra
RIESGOS_PENDIENTES: datos ambiguos y decisiones pendientes
```

No se iniciará FASE 1 mientras estos criterios no se cumplan.
