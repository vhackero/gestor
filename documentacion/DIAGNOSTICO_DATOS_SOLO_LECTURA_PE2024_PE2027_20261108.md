# Diagnóstico de datos de solo lectura: PE2024 y PE2027

**Fecha de consulta:** 2026-11-08  
**Base consultada:** instancia local `des_sisi_gestor` de WildFly.  
**Alcance:** únicamente sentencias `SELECT` sobre catálogos y resultados agregados. No se consultaron ni se incluyen nombres, matrículas, CURP, correos ni identificadores de personas. No se modificó código, configuración ni datos.

## Advertencia transversal de cobertura

Las claves solicitadas `622381`, `642338`, `612301`, `111308`, `567318`, `128305`, `202402`, `202403`, `202404`, `202406`, `202408` y `202422` no están registradas como `id_plan` en esta instancia. Tampoco hay planes cuyo identificador contenga `2027`, ni catálogo de los 17 PE2027. Por tanto, los resultados verificables corresponden a los seis planes PE2024 presentes; no permiten extrapolar conclusiones a los PE2027 ni hacer una correspondencia entre ambos conjuntos.

Los seis planes detectados son:

| id_plan local | Identificador | Programa |
|---:|---|---|
| 3 | CFP-2024-1 | Contaduría y Finanzas Públicas |
| 4 | AGP-2024-1 | Administración y Gestión Pública |
| 5 | EM-2024-1 | Enseñanza de la Matemática |
| 6 | GI-2024-1 | Gestión Industrial |
| 7 | SA-2024-1 | Seguridad Alimentaria |
| 11 | DE-2024-1 | Derecho |

---

## P1. Población y correspondencia

### P1.a. Plan, versión, cohorte/generación y fecha de ingreso

**Campos localizados**

| Concepto | Campo/fuente localizado | Observación |
|---|---|---|
| Plan | `tbl_inscripciones.idplan` → `tbl_planes.id_plan` | Identifica el plan técnico local. |
| Versión | `tbl_planes.version` | Los seis planes encontrados tienen valor `1`. |
| Programa/UD ofertada | `tbl_inscripciones.idprograma`, `tbl_ficha_descriptiva_programa.id_programa` | Es una referencia operativa del programa/evento. |
| Cohorte o generación ES24/ES25/ES26 | **No localizada** | No hay campo con ese nombre o semántica en las tablas revisadas. |
| Aproximación de ingreso | `MIN(tbl_inscripciones.fecha_registro)` por persona-plan | Es primera inscripción registrada; no debe presentarse como fecha institucional de ingreso/admisión. |
| Nuevo ingreso | `tbl_inscripciones.nuevoingreso` | Se repite por inscripción; no equivale por sí solo a cohorte. |

**Consulta usada**

```sql
SELECT pl.id_plan, pl.identificador,
       COUNT(DISTINCT ti.Idpersona) AS estudiantes_activos,
       DATE_FORMAT(MIN(ti.fecha_registro), '%Y-%m-%d') AS primera_inscripcion,
       DATE_FORMAT(MAX(ti.fecha_registro), '%Y-%m-%d') AS ultima_inscripcion,
       SUM(ti.nuevoingreso = 1) AS filas_nuevo_ingreso
FROM tbl_planes pl
LEFT JOIN tbl_inscripciones ti ON ti.idplan = pl.id_plan
LEFT JOIN tbl_persona p ON p.id_persona = ti.Idpersona AND p.activo = 1
WHERE pl.id_plan IN (3,4,5,6,7,11)
GROUP BY pl.id_plan, pl.identificador;
```

**Resultado**

| Plan | Estudiantes activos | Primera inscripción registrada | Última inscripción registrada | Filas marcadas como nuevo ingreso |
|---|---:|---|---|---:|
| CFP-2024-1 | 4,410 | 2024-01-17 | 2026-10-26 | 18,932 |
| AGP-2024-1 | 4,100 | 2024-01-17 | 2026-09-21 | 15,718 |
| EM-2024-1 | 892 | 2024-01-17 | 2026-07-13 | 3,902 |
| GI-2024-1 | 3,510 | 2024-01-17 | 2026-07-13 | 12,122 |
| SA-2024-1 | 992 | 2024-01-17 | 2026-07-13 | 4,336 |
| DE-2024-1 | 5,828 | 2024-01-17 | 2026-09-11 | 25,541 |

La distribución mensual de la primera inscripción puede obtenerse sin exponer personas mediante:

```sql
SELECT pl.identificador,
       DATE_FORMAT(ti.fecha_registro, '%Y-%m') AS periodo_primera_inscripcion,
       COUNT(DISTINCT ti.Idpersona) AS estudiantes
FROM tbl_planes pl
JOIN tbl_inscripciones ti ON ti.idplan = pl.id_plan
JOIN tbl_persona p ON p.id_persona = ti.Idpersona AND p.activo = 1
JOIN (
  SELECT Idpersona, idplan, MIN(fecha_registro) AS primera
  FROM tbl_inscripciones
  WHERE idplan IN (3,4,5,6,7,11)
  GROUP BY Idpersona, idplan
) primera ON primera.Idpersona = ti.Idpersona
        AND primera.idplan = ti.idplan
        AND primera.primera = ti.fecha_registro
WHERE pl.id_plan IN (3,4,5,6,7,11)
GROUP BY pl.identificador, DATE_FORMAT(ti.fecha_registro, '%Y-%m');
```

El resultado se concentra en enero/diciembre de 2024 y 2025, con registros posteriores hasta julio de 2026. Es una **aproximación operativa**, no una cohorte ES24/ES25/ES26 formal.

### P1.b. Equivalencias o correspondencia de planes/UD

**Consulta usada**

```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = DATABASE()
  AND table_name REGEXP 'equiv|corresp|sustit|homolog|seri|prerrequis';
```

**Resultado:** no se localizó una tabla de equivalencias, correspondencias, homologación, sustitución curricular o seriación con ese nombre en la base revisada. `tmp_claves_programas` y `cat_nombres_planesyprogramas` son catálogos de claves, no una relación verificable entre una UD anterior y una UD nueva.

### P1.c. Coincidencia de claves entre clave anterior y plan nuevo

**Consulta usada**

```sql
SELECT COUNT(*) AS claves_solicitadas_localizadas
FROM tbl_planes
WHERE CAST(id_plan AS CHAR) IN
 ('622381','642338','612301','111308','567318','128305',
  '202402','202403','202404','202406','202408','202422');

SELECT id_plan, identificador
FROM tbl_planes
WHERE identificador LIKE '%2027%';
```

**Resultado:** `0` claves solicitadas localizadas y `0` planes 2027 localizados. En consecuencia, no es posible contar coincidencias/no coincidencias de UD ni listar diferencias para cada clave anterior. Se requiere una fuente que contenga los 23 planes nuevos y una clave de correspondencia curricular verificable.

---

## P2. Historial previo: población PE2024 activa

### Fuente usada y limitación

El historial que sí está poblado se obtiene de `rel_grupo_participante` → `tbl_grupos` → `tbl_eventos` → `tbl_ficha_descriptiva_programa`. En los seis planes hay participaciones y calificaciones finales. Sin embargo, `tbl_inscripciones.groupbase` no coincide con `tbl_grupos.clave` en los datos locales: las **260,608** inscripciones de los seis planes quedan sin grupo por ese enlace. Por ello, este apartado usa el historial de participaciones por evento/plan; no certifica que cada intento sea la misma inscripción formal de `tbl_inscripciones`.

| Plan local | Estudiantes con participación | Participaciones | Calificaciones finales |
|---:|---:|---:|---:|
| 3 | 4,550 | 51,338 | 41,142 |
| 4 | 4,210 | 52,066 | 42,654 |
| 5 | 1,035 | 12,048 | 10,399 |
| 6 | 3,615 | 44,466 | 37,232 |
| 7 | 1,052 | 10,716 | 9,168 |
| 11 | 6,080 | 70,432 | 55,811 |

### P2.a. Al menos una UD con 3 o más intentos reprobados

**Consulta usada (sin extraordinarios):**

```sql
SELECT COUNT(*) AS estudiantes
FROM (
  SELECT rgp.id_persona_participante, g.clave
  FROM rel_grupo_participante rgp
  JOIN tbl_persona p ON p.id_persona = rgp.id_persona_participante AND p.activo = 1
  JOIN tbl_grupos g ON g.id = rgp.id_grupo
  JOIN tbl_eventos e ON e.id_evento = g.id_evento
  JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
  WHERE fd.id_plan IN (3,4,5,6,7,11)
    AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60
  GROUP BY rgp.id_persona_participante, g.clave
  HAVING COUNT(*) >= 3
) x;
```

**Resultado:** **6,675 estudiantes**.

**Contando extraordinarios:** no es calculable. No existe campo/tipo de evaluación que identifique primer o segundo extraordinario; `cat_tipo_calificacion_ec` sólo contiene `Por Promedio`, `Por Suma` y `Cuantitativa`, y `rel_grupo_evaluacion.nombre_evaluacion` no tiene registros que contengan “extraordin”. Por tanto, no se puede separar ni sumar extraordinarios sin inventar una regla de clasificación.

### P2.b. Cuatro o más UD reprobadas vigentes, sin acreditación posterior

**Consulta usada**

```sql
SELECT COUNT(*) AS estudiantes
FROM (
  SELECT a.id_persona
  FROM (
    SELECT rgp.id_persona_participante AS id_persona, g.clave,
           MAX(rgp.calificacion_final >= 60) AS acreditada,
           MAX(rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60) AS reprobada
    FROM rel_grupo_participante rgp
    JOIN tbl_persona p ON p.id_persona = rgp.id_persona_participante AND p.activo = 1
    JOIN tbl_grupos g ON g.id = rgp.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
    WHERE fd.id_plan IN (3,4,5,6,7,11)
    GROUP BY rgp.id_persona_participante, g.clave
  ) a
  WHERE a.reprobada = 1 AND a.acreditada = 0
  GROUP BY a.id_persona HAVING COUNT(*) >= 4
) z;
```

**Resultado:** **3,948 estudiantes**. Es un indicador exploratorio: no excluye con certeza NP por baja autorizada, porque la relación inscripción-grupo de los datos locales no es verificable para todas las filas.

### P2.c. Alguna UD con más de tres intentos totales

**Consulta usada:** misma fuente, agrupando por persona y `tbl_grupos.clave`, con `HAVING COUNT(*) > 3`.

**Resultado:** **10,390 estudiantes**. Incluye todos los registros de participación disponibles; no puede desagregar extraordinarios ni excluir de manera fiable los intentos afectados por baja autorizada.

---

## P4. NP y calificaciones no numéricas

### P4.a. Forma de almacenamiento de NP

`rel_grupo_participante.calificacion_final` es `DECIMAL`. No hay un código textual `NP` ni un campo específico de NP en las tablas revisadas. Se observan `0`, `NULL` y valores numéricos. La presencia de `0` no permite, por sí sola, afirmar que sea NP: puede representar reprobación, NP u otro tratamiento operativo.

**Consulta usada**

```sql
SELECT SUM(rgp.calificacion_final = 0) AS valor_cero,
       SUM(rgp.calificacion_final IS NULL) AS valor_nulo,
       SUM(rgp.calificacion_final BETWEEN 1 AND 59) AS reprobatoria_numerica,
       SUM(rgp.calificacion_final BETWEEN 60 AND 100) AS aprobatoria_numerica,
       SUM(rgp.calificacion_final < 0 OR rgp.calificacion_final > 100) AS fuera_rango
FROM rel_grupo_participante rgp
JOIN tbl_grupos g ON g.id = rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento = g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
WHERE fd.id_plan IN (3,4,5,6,7,11);
```

| 0 | NULL | 1–59 | 60–100 | Fuera de 0–100 |
|---:|---:|---:|---:|---:|
| 50,882 | 45,467 | 23,614 | 122,711 | 0 |

### P4.b. Valor 0 con baja ligada y sin baja ligada, por periodo

**Consulta usada**

```sql
SELECT DATE_FORMAT(e.fecha_inicial,'%Y-%m') AS periodo_aproximado,
       CASE WHEN EXISTS (
         SELECT 1 FROM rel_persona_bajas pb
         WHERE pb.id_persona = rgp.id_persona_participante
           AND pb.id_grupo = rgp.id_grupo
           AND pb.contabilizar = 1
       ) THEN 'con_baja_contabilizable' ELSE 'sin_baja_contabilizable' END AS clasificacion,
       COUNT(*) AS ocurrencias
FROM rel_grupo_participante rgp
JOIN tbl_grupos g ON g.id = rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento = g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
WHERE fd.id_plan IN (3,4,5,6,7,11) AND rgp.calificacion_final = 0
GROUP BY DATE_FORMAT(e.fecha_inicial,'%Y-%m'), clasificacion;
```

**Resultado agregado:** de 50,882 valores `0`, **2,297** tienen baja contabilizable ligada por persona+grupo y **48,585** no. Los periodos con mayor volumen son abril de 2025 y abril de 2026. Esto sólo describe la relación disponible; no convierte automáticamente `0` en NP.

### P4.c. Fuera de 0–100 o no numéricas

La columna es decimal, por lo que no admite valores no numéricos. No se encontraron valores menores de 0 ni mayores de 100. Hay **45,467 valores nulos**, cuyo significado no puede determinarse sin un catálogo de estados/resultados de evaluación.

---

## P5. Extraordinarios

### P5.a. Fuente/campo que los distingue

**Consulta usada**

```sql
SELECT table_name, column_name
FROM information_schema.columns
WHERE table_schema = DATABASE()
  AND (column_name LIKE '%extra%' OR column_name LIKE '%ordinari%' OR column_name LIKE '%oportunidad%');
```

**Resultado:** sólo se localizó `tbl_eventos.oportunidades_evaluacion`; no identifica un intento individual como ordinario, primer extraordinario o segundo extraordinario. `rel_grupo_evaluacion` y `rel_evaluacion_calificacion` no contienen una columna de tipo/proceso extraordinario. Además, no existen registros con “extraordin” en `rel_grupo_evaluacion.nombre_evaluacion`.

### P5.b. Conteo de extraordinarios

**Resultado:** **no calculable** con evidencia verificable. El conteo por nombre de evaluación “extraordin” es `0`, pero eso no demuestra que no existan extraordinarios; demuestra que la fuente revisada no los etiqueta de esa forma.

---

## P6. Periodos cursados e inscripción formal

### P6.a. Inscripción sin grupo/participación/calificación final

**Consulta usada**

```sql
SELECT COUNT(*) AS inscripciones,
       COUNT(DISTINCT CONCAT(Idpersona, ':', idevento)) AS periodos_estudiante
FROM tbl_inscripciones ti
WHERE ti.idplan IN (3,4,5,6,7,11)
  AND NOT EXISTS (
    SELECT 1 FROM tbl_grupos g
    WHERE g.id_evento = ti.idevento AND g.clave = ti.groupbase
  );
```

**Resultado:** **260,608 inscripciones** y **42,130 pares persona-evento** sin grupo por la llave `id_evento + groupbase → tbl_grupos.clave`. Como el enlace no coincide en ninguna de las filas de los seis planes, no es válido derivar los subconjuntos “sin participación” o “sin calificación final” desde `tbl_inscripciones`. Se requiere una llave de correspondencia entre `groupbase` e `tbl_grupos`.

### P6.b. Estatus de inscripción

**Resultado de esquema:** `tbl_inscripciones` no tiene campos llamados estatus, preselección, confirmación o cancelación. Sólo se localizó `alta`; sus combinaciones con `nuevoingreso` y `recursamiento` son:

| alta | nuevoingreso | recursamiento | Inscripciones |
|---:|---:|---:|---:|
| 0 | 0 | 0 | 170,717 |
| 0 | 1 | 0 | 80,510 |
| 0 | 0 | 1 | 9,340 |
| 0 | 1 | 1 | 41 |

No hay base suficiente para interpretar esos campos como “preseleccionada”, “confirmada” o “cancelada”.

---

## P7/P8/P9/P10. Bajas

### P7.a. Catálogos de tipo y motivo

**Consulta completa usada**

```sql
SELECT tb.id_tipo_baja, tb.nombre, tb.descripcion,
       mb.id_motivo_baja, mb.descripcion AS motivo
FROM cat_tipo_bajas tb
LEFT JOIN rel_motivo_baja mb ON mb.tipo_baja_id = tb.id_tipo_baja
ORDER BY tb.id_tipo_baja, mb.id_motivo_baja;
```

**Resultado:** 9 tipos y **3,417 motivos**. El catálogo completo es recuperable mediante la consulta anterior; por su tamaño no se reproduce íntegramente en este diagnóstico. Tipos localizados: `1 Parcial`, `2 Temporal`, `3 Definitiva`, `4 Administrativa`, `5 Art 40`, `6 Definitiva sin posibilidad de reinscripción`, `7 Temporal con asignaturas`, `8 Temporal sin asignaturas`, `9 Matrícula Docente`.

### P7.b. Conteos por tipo, contabilizar y solicitud

**Consulta usada:** agrupación de `rel_persona_bajas` por tipo, `contabilizar` y `solicitud`, unida a personas activas y planes 3,4,5,6,7,11.

| Tipo | Registros | Movimientos (`id_baja`) | Personas afectadas |
|---|---:|---:|---:|
| Parcial (1) | 135 | 135 | 79 |
| Temporal (2) | 2,815 | 2,815 | 621 |

`contabilizar=1` aparece en **2,944** registros; `contabilizar=0`, en **6**. `solicitud` es `VARCHAR` y tiene **126 valores distintos**, por lo que no puede presentarse como estatus aceptada/rechazada/pendiente sin catálogo o semántica institucional adicional.

### P7.c. Tipo 5, Art. 40

**Consulta usada:** conteo de bajas de `tipo_baja_id=5`, con y sin `id_grupo`.

**Resultado:** **0 registros** en la población. La tabla no contiene `id_inscripcion`; sí tiene `id_grupo`, por lo que no se puede responder literalmente cuántas se ligan a `id_inscripcion`.

### P7.d. Parcial, contabilizar=1 y solicitud distinta de 1

**Consulta usada**

```sql
SELECT COUNT(*) AS registros, COUNT(DISTINCT pb.id_baja) AS movimientos
FROM rel_persona_bajas pb
JOIN tbl_persona p ON p.id_persona = pb.id_persona AND p.activo = 1
JOIN rel_motivo_baja mb ON mb.id_motivo_baja = pb.motivo_baja_id
WHERE pb.id_plan IN (3,4,5,6,7,11)
  AND mb.tipo_baja_id = 1 AND pb.contabilizar = 1
  AND COALESCE(pb.solicitud, '0') <> '1';
```

**Resultado:** **133 registros/movimientos**. La condición compara contra el literal solicitado; no interpreta los valores alfanuméricos de `solicitud` como estado.

### P7.e. Motivos de seriación, prerrequisito o académica

**Consulta usada**

```sql
SELECT id_motivo_baja, tipo_baja_id, descripcion
FROM rel_motivo_baja
WHERE LOWER(descripcion) REGEXP 'seriaci|prerrequis|academica';
```

**Resultado:** un motivo localizado: `id_motivo_baja=1136`, tipo `1`, descripción `ACADEMICA`. No se localizó una descripción que contenga “seriación” ni “prerrequisito”. Por tanto, el catálogo no acredita por sí mismo una causa inequívoca de seriación.

### P7.f. Bajas administrativas, tipo 4

**Resultado:** **0 registros** tipo 4 en la población. `rel_persona_bajas` sólo tiene `fecha_modificacion`; no contiene fecha de inicio, fecha de término ni vigencia de una medida administrativa. No puede inferirse vigencia desde la fecha de modificación.

---

## P11. Seriación

### P11.a. Relaciones cargadas por plan

**Consulta usada**

```sql
SELECT table_name, column_name
FROM information_schema.columns
WHERE table_schema = DATABASE()
  AND (table_name REGEXP 'seri|prerrequis' OR column_name REGEXP 'seri|prerrequis');
```

**Resultado:** no se localizó una tabla relacional de seriación/prerrequisitos. Sólo aparecen campos de resultado del motor (`tbl_evaluacion_curricular_ud_v2.seriacion_*`) y un indicador de caso histórico. `tbl_malla_curricular` tiene un nodo por cada uno de los seis planes locales y no contiene relaciones de UD cargadas.

No se puede contar relaciones por los 23 planes nuevos ni confirmar la cadena Seminario de Titulación I → II → III. La información no está disponible en esta base ni hay PE2027 para consultarla.

### P11.b. Enseñanza de la Matemática (202406 y 111308)

No se localizaron los IDs `202406` ni `111308`; el plan local equivalente por nombre tiene `id_plan=5`. Al no existir fuente de relaciones de seriación, no se puede listar relaciones para ese plan sin una fuente adicional.

---

## P12. Optativas

### P12.a. Estudiantes con 5+ periodos, obligatoria pendiente y menos de 8 optativas acreditadas

**Resultado:** no calculable con evidencia suficiente. Las tablas de participación permiten contar calificaciones por evento, pero no se puede enlazar de manera verificable cada inscripción formal con grupo/participación, ni se cuenta con cohortes ni planes PE2027 solicitados. Un resultado numérico en estas condiciones mezclaría participación, oferta e inscripción y no sería institucionalmente sustentable.

### P12.b. Espacios “Optativa” de los 17 PE2027

**Consulta usada**

```sql
SELECT COUNT(DISTINCT programa_educativo) AS programas, COUNT(*) AS filas
FROM cat_nombres_planesyprogramas
WHERE programa_educativo LIKE '%2027%';
```

**Resultado:** `0 programas`, `0 filas`. En los seis PE2024 locales, el catálogo actual contiene 144 filas de tipo Optativa y 72 claves distintas; no responde cómo están cargados los 17 PE2027.

---

## P13. UD “Tecnologías ambientales de residuos, suelos y energía”

**Consulta usada**

```sql
SELECT plan, programa, clave_programa, semestre, bloque, creditos, tipo
FROM tmp_claves_programas
WHERE LOWER(CONCAT_WS(' ', plan, programa, clave_programa)) LIKE '%residuos%'
UNION ALL
SELECT programa_educativo, asignatura, clave_asig, semestre, bloque, NULL, tipo
FROM cat_nombres_planesyprogramas
WHERE LOWER(CONCAT_WS(' ', programa_educativo, asignatura, clave_asig)) LIKE '%residuos%';
```

**Resultado:** `0` coincidencias en ambas fuentes. No se pueden reportar plan, clave, semestre, bloque, créditos o tipo para esa UD a partir de esta instancia.

---

## P15/P16. Convocatorias, resoluciones y sustituciones

### P15.a. Convocatorias o resoluciones ligadas al expediente

**Consulta usada**

```sql
SELECT (SELECT COUNT(*) FROM tbl_convocatoria) AS convocatorias,
       (SELECT COUNT(*) FROM rel_convocatoria_planesyprogramas) AS relaciones_plan_programa,
       (SELECT COUNT(*) FROM rel_personasige_convocatoria) AS relaciones_persona_convocatoria;
```

**Resultado:** `2` convocatorias, `646` relaciones convocatoria-plan/programa y `4,650` relaciones persona-convocatoria. Existen `fecha_apertura`, `fecha_cierre`, `activo` y URL en `tbl_convocatoria`. No se localizó una tabla de resoluciones/dictámenes ligada a convocatoria y expediente dentro del alcance revisado.

### P16.b. Sustitución entre UD optativas

**Consulta usada**

```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = DATABASE()
  AND table_name REGEXP 'sustit|equiv|corresp|homolog';
```

**Resultado:** no se localizó una tabla de sustituciones o de equivalencias de UD. No es posible reconstruir una cadena original → sustituta ni acreditar oportunidades acumuladas del adeudo con esta instancia.

---

## Conclusiones para decisión institucional

1. La instancia local permite analizar de forma agregada los seis PE2024 por IDs locales 3, 4, 5, 6, 7 y 11, pero no contiene las claves solicitadas ni los PE2027.
2. No existe una cohorte formal ES24/ES25/ES26 localizada. La primera inscripción es una aproximación y no debe sustituir un dato de ingreso institucional.
3. Hay historial de participaciones con calificaciones, pero falta una correspondencia verificable entre la inscripción (`groupbase`) y el grupo/participación. Esto limita los resultados sobre intentos, NP por baja y periodos cursados.
4. Los extraordinarios no están identificados en el modelo disponible. No deben contarse como ordinarios ni como oportunidades sin una fuente adicional.
5. El catálogo de bajas tiene tipos y motivos, pero `solicitud` no es un estado normalizado y no hay vigencias administrativas. `contabilizar=1` es el único indicador operativo observable solicitado para bajas aplicadas.
6. No se localizaron fuentes de correspondencia PE2024↔PE2027, seriación, sustituciones de optativas, ni la UD ambiental consultada. Estas fuentes son necesarias antes de sostener decisiones normativas sobre esos temas.
