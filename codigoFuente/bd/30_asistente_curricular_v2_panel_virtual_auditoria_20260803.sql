-- Auditoría de cobertura para el panel "Asistente virtual" V2
-- Fecha: 2026-08-03

-- 1. Cobertura de mensajes por patrón / perfil / tipo
SELECT
    p.nombre_patron,
    m.perfil,
    m.periodo_operativo,
    m.tipo,
    m.clave
FROM rel_patron_caso_academico p
JOIN rel_patron_mensaje_contextual rpm ON rpm.id_patron_caso = p.id
JOIN cat_mensaje_institucional_contextual m ON m.id = rpm.id_mensaje_contextual
WHERE m.tipo LIKE 'PANEL_%'
ORDER BY p.nombre_patron, m.perfil, m.periodo_operativo, m.tipo;

-- 2. Patrones sin mensaje operativo del panel
SELECT p.nombre_patron
FROM rel_patron_caso_academico p
WHERE NOT EXISTS (
    SELECT 1
    FROM rel_patron_mensaje_contextual rpm
    JOIN cat_mensaje_institucional_contextual m ON m.id = rpm.id_mensaje_contextual
    WHERE rpm.id_patron_caso = p.id
      AND m.tipo = 'PANEL_OP'
      AND m.activo = 1
);

-- 3. Patrones sin resumen del panel
SELECT p.nombre_patron
FROM rel_patron_caso_academico p
WHERE NOT EXISTS (
    SELECT 1
    FROM rel_patron_mensaje_contextual rpm
    JOIN cat_mensaje_institucional_contextual m ON m.id = rpm.id_mensaje_contextual
    WHERE rpm.id_patron_caso = p.id
      AND m.tipo = 'PANEL_RES'
      AND m.activo = 1
);

-- 4. Cobertura mínima esperada para estudiante en inscripción
SELECT
    p.nombre_patron,
    SUM(CASE WHEN m.tipo = 'PANEL_OP' THEN 1 ELSE 0 END) AS tiene_operativo,
    SUM(CASE WHEN m.tipo = 'PANEL_RES' THEN 1 ELSE 0 END) AS tiene_resumen,
    SUM(CASE WHEN m.tipo = 'PANEL_QDE' THEN 1 ELSE 0 END) AS tiene_qde,
    SUM(CASE WHEN m.tipo = 'PANEL_SER' THEN 1 ELSE 0 END) AS tiene_seriacion,
    SUM(CASE WHEN m.tipo = 'PANEL_SIM' THEN 1 ELSE 0 END) AS tiene_simular,
    SUM(CASE WHEN m.tipo = 'PANEL_CON' THEN 1 ELSE 0 END) AS tiene_confirmar
FROM rel_patron_caso_academico p
LEFT JOIN rel_patron_mensaje_contextual rpm ON rpm.id_patron_caso = p.id
LEFT JOIN cat_mensaje_institucional_contextual m
       ON m.id = rpm.id_mensaje_contextual
      AND m.perfil = 'ESTUDIANTE'
      AND m.periodo_operativo = 'INSCRIPCION'
      AND m.tipo IN (
          'PANEL_OP',
          'PANEL_RES',
          'PANEL_QDE',
          'PANEL_SER',
          'PANEL_SIM',
          'PANEL_CON'
      )
GROUP BY p.nombre_patron
ORDER BY p.nombre_patron;

-- 5. Cobertura mínima esperada para gestor en inscripción
SELECT
    p.nombre_patron,
    SUM(CASE WHEN m.tipo = 'PANEL_OP' THEN 1 ELSE 0 END) AS tiene_operativo,
    SUM(CASE WHEN m.tipo = 'PANEL_RES' THEN 1 ELSE 0 END) AS tiene_resumen,
    SUM(CASE WHEN m.tipo = 'PANEL_RTEC' THEN 1 ELSE 0 END) AS tiene_resumen_tecnico,
    SUM(CASE WHEN m.tipo = 'PANEL_REGLA' THEN 1 ELSE 0 END) AS tiene_regla,
    SUM(CASE WHEN m.tipo = 'PANEL_EVID' THEN 1 ELSE 0 END) AS tiene_evidencia,
    SUM(CASE WHEN m.tipo = 'PANEL_MSG' THEN 1 ELSE 0 END) AS tiene_mensaje
FROM rel_patron_caso_academico p
LEFT JOIN rel_patron_mensaje_contextual rpm ON rpm.id_patron_caso = p.id
LEFT JOIN cat_mensaje_institucional_contextual m
       ON m.id = rpm.id_mensaje_contextual
      AND m.perfil = 'GESTOR'
      AND m.periodo_operativo = 'INSCRIPCION'
      AND m.tipo IN (
          'PANEL_OP',
          'PANEL_RES',
          'PANEL_RTEC',
          'PANEL_REGLA',
          'PANEL_EVID',
          'PANEL_MSG'
      )
GROUP BY p.nombre_patron
ORDER BY p.nombre_patron;

-- 6. Mensajes panel que todavía no están relacionados con ningún patrón
SELECT
    m.clave,
    m.perfil,
    m.periodo_operativo,
    m.tipo,
    m.titulo
FROM cat_mensaje_institucional_contextual m
LEFT JOIN rel_patron_mensaje_contextual rpm ON rpm.id_mensaje_contextual = m.id
WHERE m.tipo LIKE 'PANEL_%'
  AND m.activo = 1
  AND rpm.id IS NULL
ORDER BY m.perfil, m.periodo_operativo, m.tipo, m.clave;

-- 7. Mensajes panel con posible riesgo de sustituir contenido dinámico
--    Esta consulta ayuda a detectar textos en BD que ya incluyen cifras o periodos
--    y que podrían desalinearse del cálculo vivo del backend.
SELECT
    m.clave,
    m.perfil,
    m.tipo,
    m.mensaje
FROM cat_mensaje_institucional_contextual m
WHERE m.tipo LIKE 'PANEL_%'
  AND (
      m.mensaje REGEXP '[0-9]{4}-[12]'
      OR m.mensaje REGEXP '[0-9]+'
  )
ORDER BY m.perfil, m.tipo, m.clave;
