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
  ON e.cve_evento_cap LIKE CONCAT('%',pi.nombre_periodo,'%')
WHERE fd.id_plan IN (3,4,5,6,7,11);
```

| Participaciones origen | Granos estudiante–UD–periodo | Sin periodo |
|---:|---:|---:|
| 241,066 | 233,099 | 0 |

Hay 1,128 granos con más de una participación y 7,967 participaciones adicionales. La consulta de conflicto de calificación devolvió **0 granos conflictivos**. Por eso la consolidación no descarta resultados opuestos en la base de pruebas; aun así, el adaptador conserva el control para datos futuros.

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

## 5. Validación ejecutada

| Cobertura | Resultado |
|---|---|
| Casos dorados sintéticos GC-04, GC-05 y GC-08 mediante adaptador/motor | Verificados en `Bloque1FundamentosRn20261105Test` y `Bloque2HistorialOportunidadesRn20261106Test`. |
| Calificación 0 con/sin baja, NULL cerrado/vigente y conflicto de grano | Pruebas directas nuevas, verdes. |
| Historial vacío | Prueba del adaptador: no se presenta como historial completo. |
| Regresión completa `basegestor` | 778 pruebas, 0 fallas, 0 errores, 0 omitidas. |

### Pendiente: tres expedientes reales anonimizados

No se ejecutaron GC-04, GC-05 y GC-08 sobre tres expedientes reales a través del artefacto corregido. El WAR desplegado no contiene todavía esta modificación y el entorno de trabajo no expone una invocación JPA/servicio de solo lectura al artefacto compilado. No se debe presentar esta parte como validada.

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
| `tipo_relacion` | Directa o indirecta. |
| `tipo_seriacion` | Interbloque, intersemestral, interanual u otro catálogo aprobado. |
| `vigencia_inicio`, `vigencia_fin`, `activo` | Aplicación temporal controlada. |
| `documento_fuente`, `numeral_fuente`, `fecha_aprobacion` | Trazabilidad académica para auditoría. |
| `observacion` | Precisión aprobada que no altere el significado normativo. |

CAI debe entregar el catálogo poblado y aprobar las relaciones por plan/cohorte. El motor sólo consume la relación vigente; no debe deducir seriación por nombre, semestre o cercanía entre UD.
