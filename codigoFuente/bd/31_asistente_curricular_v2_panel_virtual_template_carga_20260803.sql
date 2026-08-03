-- Plantilla de carga para nuevos paquetes de conocimiento del panel "Asistente virtual" V2
-- Fecha: 2026-08-03
--
-- Uso recomendado:
--   1. Duplicar este bloque por escenario real definido por el experto académico.
--   2. Sustituir los valores de ejemplo entre comillas.
--   3. Cargar primero los mensajes.
--   4. Relacionarlos con el patrón académico correcto.
--
-- Nota:
--   Si el texto depende de números o periodos calculados en tiempo real,
--   conviene NO sobrescribirlo desde BD hasta contar con interpolación de variables.

-- ============================================================================
-- 1. Referencia: localizar el patrón que recibirá el paquete
-- ============================================================================
SELECT
    p.id,
    t.clave AS tipo_caso,
    p.nombre_patron,
    p.descripcion
FROM rel_patron_caso_academico p
JOIN cat_tipo_caso_academico t ON t.id = p.id_tipo_caso
WHERE p.nombre_patron = 'Trayectoria regular con continuidad plena';

-- ============================================================================
-- 2. Alta o actualización de mensajes del panel
--    Ajustar:
--    - claves
--    - perfil
--    - periodo_operativo
--    - titulo
--    - mensaje
--    - tipo
-- ============================================================================
INSERT INTO cat_mensaje_institucional_contextual
    (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('EST_INS_EJEMPLO_PANEL_OP_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Mensaje operativo', 'Revisa tu diagnóstico académico y simula la carga para tu próximo periodo de reinscripción. Durante el periodo activo, podrás validar y confirmar tu selección final.', 'PANEL_OP', 1, 1),
    ('EST_INS_EJEMPLO_PANEL_RES_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Resumen diagnóstico', 'Texto institucional estable del resumen. Si este texto requiere números reales, es preferible dejarlo en Java hasta tener interpolación.', 'PANEL_RES', 1, 1),
    ('EST_INS_EJEMPLO_PANEL_QDE_20260803', 'ESTUDIANTE', 'INSCRIPCION', '¿Qué debo elegir?', 'Texto de orientación principal para la decisión del estudiante.', 'PANEL_QDE', 1, 1),
    ('EST_INS_EJEMPLO_PANEL_SER_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Validar seriación', 'Texto institucional para validación de seriación.', 'PANEL_SER', 1, 1),
    ('EST_INS_EJEMPLO_PANEL_SIM_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Simular carga', 'Texto institucional para simulación de carga.', 'PANEL_SIM', 1, 1),
    ('EST_INS_EJEMPLO_PANEL_CON_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Confirmar selección', 'Texto institucional para confirmación de selección.', 'PANEL_CON', 1, 1)
ON DUPLICATE KEY UPDATE
    perfil = VALUES(perfil),
    periodo_operativo = VALUES(periodo_operativo),
    titulo = VALUES(titulo),
    mensaje = VALUES(mensaje),
    tipo = VALUES(tipo),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

-- ============================================================================
-- 3. Relación del paquete con un patrón existente
--    Ajustar:
--    - nombre_patron
--    - lista de claves
-- ============================================================================
INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_INS_EJEMPLO_PANEL_OP_20260803',
      'EST_INS_EJEMPLO_PANEL_RES_20260803',
      'EST_INS_EJEMPLO_PANEL_QDE_20260803',
      'EST_INS_EJEMPLO_PANEL_SER_20260803',
      'EST_INS_EJEMPLO_PANEL_SIM_20260803',
      'EST_INS_EJEMPLO_PANEL_CON_20260803'
  )
WHERE p.nombre_patron = 'Trayectoria regular con continuidad plena'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id
        AND r.id_mensaje_contextual = m.id
        AND r.perfil = m.perfil
  );

-- ============================================================================
-- 4. Verificación puntual del paquete cargado
-- ============================================================================
SELECT
    p.nombre_patron,
    m.perfil,
    m.periodo_operativo,
    m.tipo,
    m.titulo,
    m.clave
FROM rel_patron_caso_academico p
JOIN rel_patron_mensaje_contextual rpm ON rpm.id_patron_caso = p.id
JOIN cat_mensaje_institucional_contextual m ON m.id = rpm.id_mensaje_contextual
WHERE p.nombre_patron = 'Trayectoria regular con continuidad plena'
  AND m.clave LIKE 'EST_INS_EJEMPLO_PANEL_%'
ORDER BY m.tipo;

-- ============================================================================
-- 5. Plantilla mínima equivalente para gestor
-- ============================================================================
INSERT INTO cat_mensaje_institucional_contextual
    (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('GES_INS_EJEMPLO_PANEL_OP_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje operativo', 'Apoyo para interpretar el caso y documentar la atención.', 'PANEL_OP', 1, 1),
    ('GES_INS_EJEMPLO_PANEL_RES_20260803', 'GESTOR', 'INSCRIPCION', 'Resumen del caso', 'Resumen técnico estable para el escenario del estudiante.', 'PANEL_RES', 1, 1),
    ('GES_INS_EJEMPLO_PANEL_RTEC_20260803', 'GESTOR', 'INSCRIPCION', 'Resumen técnico', 'Indicadores mínimos que debe revisar el gestor.', 'PANEL_RTEC', 1, 1),
    ('GES_INS_EJEMPLO_PANEL_REGLA_20260803', 'GESTOR', 'INSCRIPCION', 'Regla aplicada', 'Regla operativa o criterio académico dominante.', 'PANEL_REGLA', 1, 1),
    ('GES_INS_EJEMPLO_PANEL_EVID_20260803', 'GESTOR', 'INSCRIPCION', 'Paquete de evidencia', 'Elementos de soporte y validación del caso.', 'PANEL_EVID', 1, 1),
    ('GES_INS_EJEMPLO_PANEL_MSG_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje sugerido', 'Mensaje breve sugerido para orientar al estudiante.', 'PANEL_MSG', 1, 1)
ON DUPLICATE KEY UPDATE
    perfil = VALUES(perfil),
    periodo_operativo = VALUES(periodo_operativo),
    titulo = VALUES(titulo),
    mensaje = VALUES(mensaje),
    tipo = VALUES(tipo),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

