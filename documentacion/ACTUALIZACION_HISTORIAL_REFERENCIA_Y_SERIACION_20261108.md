# Actualización de historial, referencia RN-6.9 y contrato de seriación

**Fecha:** 2026-11-08  
**Rama:** `feature/desacople_motor_historico`  
**Alcance:** corrección del adaptador productivo de historial curricular y documentación del contrato pendiente de seriación. No se modificaron expedientes ni datos de la base.

## 1. Enlace inscripción-calificación e historial canónico

### Decisión aplicada

La relación `tbl_inscripciones.groupbase = tbl_grupos.clave` no es fiable en la base de pruebas. El adaptador ya no depende de ella. La fuente canónica ahora parte de:

```text
rel_grupo_participante
  -> tbl_grupos
  -> tbl_eventos
  -> tbl_ficha_descriptiva_programa
  -> tbl_periodos_inscripcion
```

La baja se liga exclusivamente por:

```text
rel_persona_bajas.id_persona = rel_grupo_participante.id_persona_participante
AND rel_persona_bajas.id_grupo = rel_grupo_participante.id_grupo
AND rel_persona_bajas.contabilizar = 1
```

No se usa `id_inscripcion`, `groupbase` ni `solicitud` para clasificar una baja aplicada.

### Grano

El intento canónico es **estudiante + UD (`tbl_ficha_descriptiva_programa.id_programa`) + periodo**. No se une a `rel_grupo_evaluacion` ni a `rel_evaluacion_calificacion`, por lo que una evaluación parcial no multiplica intentos. Las participaciones repetidas del mismo grano se consolidan. Si dentro de ese mismo grano aparecen calificaciones distintas, se emite `NO_DETERMINADO` con evidencia `HISTORIAL_PARTICIPACION_CALIFICACION_CONFLICTIVA_REQUIERE_REVISION`.

### Verificación agregada de grano en la base de pruebas

```sql
SELECT COUNT(*) AS participaciones_origen,
       COUNT(DISTINCT CONCAT(rgp.id_persona_participante, ':', fd.id_programa,
                             ':', COALESCE(pi.nombre_periodo,'SIN_PERIODO'))) AS granos,
       SUM(pi.nombre_periodo IS NULL) AS sin_periodo
FROM rel_grupo_participante rgp
JOIN tbl_persona p ON p.id_persona = rgp.id_persona_participante AND p.activo = 1
JOIN tbl_grupos g ON g.id = rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento = g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
LEFT JOIN tbl_periodos_inscripcion pi
  ON SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = pi.nombre_periodo
WHERE fd.id_plan IN (3,4,5,6,7,11);
```

| Participaciones origen | Granos estudiante–UD–periodo | Sin periodo |
|---:|---:|---:|
| 241,066 | 233,099 | 0 |

Hay 1,128 granos con más de una participación y 7,967 participaciones adicionales. La consulta de conflicto de calificación devolvió **0 granos conflictivos**. Por eso la consolidación no descarta resultados opuestos en la base de pruebas; aun así, el adaptador conserva el control para datos futuros.

### Precisión de periodo

La coincidencia anterior por `LIKE '%nombre_periodo%'` no era segura: **101 eventos** coincidían con más de un periodo del catálogo. Por ejemplo, una clave que contiene `2024-2024-1` también contenía accidentalmente `2024-2`. La relación productiva usa ahora sólo el sufijo `AAAA-N` de `cve_evento_cap` mediante `SUBSTRING_INDEX(..., '-', -2) = nombre_periodo`.

| Control agregado en base de pruebas | Resultado |
|---|---:|
| Eventos con más de un periodo por coincidencia `LIKE` | 101 |
| Eventos con más de un periodo con el sufijo exacto | 0 |
| Eventos que no encuentran periodo con la coincidencia histórica `LIKE` | 2 |

Si una clave futura no termina con un periodo catalogado, el historial no la convierte en un intento completo: queda sin periodo verificable y debe llegar como `REQUIERE_REVISION`.

### Identidad de la UD

`fd.id_programa` es la misma identidad que consume el universo del mapa: `FichaDescProgramaDTO.idPrograma` se convierte en `UnidadDecisionInscripcionDTO.udId` y luego en `UnidadEvaluacionCurricularV2DTO.idPrograma`. La verificación agregada sobre la base de pruebas encontró **0 UD históricas fuera del universo del plan**; se cotejaron 273 identidades distintas de UD del historial contra 273 identidades presentes en el universo de sus planes. No se usó nombre ni clave como equivalencia.

### Archivos modificados

| Archivo | Cambio |
|---|---|
| `InscripcionRepository.java` | Historial y periodos desde participación verificable; baja por persona+grupo; control de grano y cierre de evento. |
| `SemestreReferenciaV2ServiceImpl.java` | RN-6.9 cuenta sólo calificación final o NP por baja; excluye participación sin resultado final. |
| `ContextoEvaluacionCurricularV2AdapterImpl.java` | Un historial vacío ya no se declara completo. |
| Pruebas Bloque 1, Bloque 2 y adaptador | Cobertura de la nueva fuente, bajas, calificación cero, nulos y conflictos. |

## 2. Estados de calificación y bajas

| Evidencia en el intento | Estado del adaptador | Efecto |
|---|---|---|
| Calificación `0`, sin baja aplicada ligada | `REPROBADO` | Cuenta como reprobación, sujeto al umbral parametrizado. |
| Calificación `0`, con baja aplicada ligada | `BAJA_PARCIAL` o `BAJA_TEMPORAL` según tipo | NP por baja; no se convierte en reprobación. |
| Calificación `NULL`, evento cerrado | `NO_DETERMINADO` | Requiere revisión; no se inventa resultado. |
| Calificación `NULL`, evento vigente | `EN_CURSO` | No cuenta como resultado final. |
| Fuera de 0–100 | `NO_DETERMINADO` | Evidencia `NP_SIN_BAJA_REQUIERE_REVISION`. |

La regla de baja aplicada usa `contabilizar=1`. La confirmación institucional solicitada a DAEAE sobre esa semántica queda explícitamente pendiente como parámetro/política, aunque es el criterio operativo que usa hoy el sistema.

Los extraordinarios permanecen sólo como protección del contrato (`EXTRAORDINARIO_HISTORICO`): no se buscaron ni se generan fuentes de extraordinario, porque se confirmó que no existen para estos planes.

## 3. Semestre de referencia RN-6.9

La referencia se calcula por periodos, no por bloque. Un periodo se cuenta una sola vez cuando tiene al menos:

1. una participación con calificación final, o
2. un NP atribuible a baja aplicada.

Las bajas temporales contabilizables se excluyen por periodo. Una participación sin calificación final se reporta como `PARTICIPACION_SIN_CALIFICACION_FINAL`; no se cuenta silenciosamente. Una inscripción sin evidencia conserva el motivo `INSCRIPCION_SIN_EVIDENCIA_DE_CURSAMIENTO` cuando la fuente lo aporta.

## 4. Recuento P2 recalculado con el grano corregido

Los siguientes datos corresponden a la base de pruebas, seis planes PE2024 locales y personas activas. No son indicadores institucionales de producción.

| Indicador | Resultado anterior por grupo | Resultado con grano estudiante–UD–periodo |
|---|---:|---:|
| Estudiantes con una UD y 3+ reprobaciones | 6,675 | 651 |
| Estudiantes con 4+ UD reprobadas vigentes | 3,948 | 8,953 |
| Estudiantes con una UD y más de 3 intentos | 10,390 | 0 |

La diferencia confirma que `tbl_grupos.clave` no era una identidad curricular suficiente. El nuevo conteo identifica UD por `id_programa` y evita duplicar la misma UD por participaciones del mismo periodo. Antes de usar estos números para una decisión institucional deben repetirse sobre producción con los PE2027 cargados y validar el catálogo de periodo.

### Sensibilidad de calificación cero sin baja

El valor histórico de 8,953 no debe interpretarse como el mismo cálculo que realiza hoy el adaptador: incluía cualquier `0`, incluso cuando existía baja aplicada. Con el grano y la clasificación actuales, `0` sin baja ligada es reprobación y `0` con baja ligada es NP por baja.

| Definición agregada, base de pruebas | Estudiantes con 4+ UD vigentes |
|---|---:|
| Conteo histórico: calificación 0–59, sin separar baja | 9,012 |
| Clasificación actual: 1–59, más 0 sin baja aplicada | 8,698 |
| Sensibilidad: sólo 1–59; ningún 0 cuenta como reprobación | 1,394 |
| Personas que bajarían de 4 al retirar sólo 0 sin baja | 7,304 |

Desglose de estudiantes con al menos un `0` sin baja por periodo; el patrón “todas” compara únicamente UD con calificación final del mismo periodo, no identifica abandono por sí solo:

| Periodo | Con algún 0 sin baja | Todas las UD calificadas en 0 | Sólo algunas UD en 0 |
|---|---:|---:|---:|
| 2024-1 | 2,005 | 629 | 1,376 |
| 2024-2 | 894 | 411 | 483 |
| 2025-1 | 3,277 | 1,230 | 2,047 |
| 2025-2 | 1,563 | 660 | 903 |
| 2026-1 | 4,464 | 1,788 | 2,676 |

Esta sensibilidad sirve para la decisión institucional P6; no cambia por sí sola la regla del motor. Confirmar con DAEAE el significado de `0` sin baja antes de modificar la política de reprobación.

## 5. Validación ejecutada

| Cobertura | Resultado |
|---|---|
| Casos dorados sintéticos GC-04, GC-05 y GC-08 mediante adaptador/motor | Verificados en `Bloque1FundamentosRn20261105Test` y `Bloque2HistorialOportunidadesRn20261106Test`. |
| Calificación 0 con/sin baja, NULL cerrado/vigente y conflicto de grano | Pruebas directas nuevas, verdes. |
| Historial vacío | Prueba del adaptador: no se presenta como historial completo. |
| Regresión completa `basegestor` | 778 pruebas, 0 fallas, 0 errores, 0 omitidas. |

### Pendiente: tres expedientes reales anonimizados

No se ejecutaron GC-04, GC-05 y GC-08 sobre tres expedientes reales a través del artefacto corregido. El WAR detectado en el ambiente de pruebas fue compilado el **2026-11-07 22:30:59 -06:00**, anterior a los commits `d391a42f` y `b1da50da`; además, su manifiesto no contiene SHA de Git. Por tanto no acredita incluir la corrección de historial ni el enlace exacto de periodo. No se debe presentar esta parte como validada.

Para cerrarlo: desplegar el WAR compilado en el ambiente de pruebas autorizado y ejecutar los tres casos mediante el Asistente/Adaptador, registrando sólo etiquetas anonimizadas `A`, `B` y `C`, plan, periodo, número de intentos, resultado por UD y resultado del motor; sin nombre, matrícula ni identificadores personales.

## 6. Contrato de seriación del Motor Curricular

### Contrato actual

El motor recibe relaciones dentro de cada `UnidadEvaluacionCurricularV2DTO` mediante `RelacionSeriacionCurricularV2DTO`. El adaptador actual las construye desde `UnidadDecisionInscripcionDTO.requiere`. Cada relación requiere al menos:

| Campo | Significado |
|---|---|
| `idAntecedente` | UD que debe acreditarse. |
| `idConsecuente` | UD cuya elegibilidad se evalúa. |
| `semestreAntecedente` / `semestreConsecuente` | Ubicación curricular. |
| `bloqueAntecedente` / `bloqueConsecuente` | Bloque curricular cuando aplique. |
| `tipoRelacion` | Relación directa o indirecta. |
| `tipoSeriacion` | Interbloque, intersemestral, interanual o no determinada. |
| `evidencia` | Fuente documental/técnica de la relación. |

En la base de pruebas no existe tabla de relaciones entre UD. La malla actual no sustituye ese contrato: dibujar UD por semestre o bloque no prueba una dependencia normativa.

### Estructura propuesta para presentar a CAI

Propuesta de catálogo relacional con vigencia, sin crearla todavía:

| Campo propuesto | Propósito |
|---|---|
| `id_relacion_seriacion` | Identidad técnica de la regla. |
| `id_plan`, `version_plan`, `cohorte_desde`, `cohorte_hasta` | Ámbito curricular y población aplicable. |
| `id_ud_antecedente`, `clave_ud_antecedente` | Prerrequisito verificable. |
| `id_ud_consecuente`, `clave_ud_consecuente` | UD evaluada. |
| `tipo_relacion` | Sólo `DIRECTA`: es la relación aprobada y almacenada. |
| `tipo_seriacion` | Interbloque, intersemestral, interanual u otro catálogo aprobado. |
| `vigencia_inicio`, `vigencia_fin`, `activo` | Aplicación temporal controlada. |
| `documento_fuente`, `numeral_fuente`, `fecha_aprobacion` | Trazabilidad académica para auditoría. |
| `observacion` | Precisión aprobada que no altere el significado normativo. |

CAI debe entregar el catálogo poblado y aprobar las relaciones directas por plan/cohorte. El motor deriva las relaciones indirectas por transitividad sobre ese grafo directo, conservando el camino de evidencia; no se cargan relaciones indirectas en el catálogo para evitar contradicciones. El motor no debe deducir seriación por nombre, semestre o cercanía entre UD.
