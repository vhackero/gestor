-- Refuerzo de conocimiento V2 para rezago severo / alto riesgo por no acreditadas

INSERT INTO cat_mensaje_institucional_contextual (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('EST_INS_ALTO_RIESGO_NO_ACREDITADAS_V3', 'ESTUDIANTE', 'INSCRIPCION', 'Alto riesgo por no acreditadas acumuladas', 'Tu trayectoria presenta un riesgo alto por acumulación de UD no acreditadas. En este periodo debes priorizar regularización, atender primero las UD críticas y evitar combinar carga que aumente el rezago.', 'ORIENTACION', 1, 1),
    ('EST_CUR_ALTO_RIESGO_NO_ACREDITADAS_V3', 'ESTUDIANTE', 'CURSAMIENTO', 'Alto riesgo en cursamiento por no acreditadas', 'Durante cursamiento tu prioridad es cerrar las UD activas y preparar el siguiente periodo desde una estrategia de regularización. No conviene planear avance adicional mientras el rezago severo siga activo.', 'ORIENTACION', 1, 1),
    ('GES_INS_ALTO_RIESGO_NO_ACREDITADAS_V3', 'GESTOR', 'INSCRIPCION', 'Riesgo alto por no acreditadas acumuladas', 'Si la trayectoria muestra cinco o más UD no acreditadas, la orientación debe ser explícita: priorizar regularización, evitar aperturas incompatibles y comunicar con claridad el impacto sobre continuidad y carga.', 'ORIENTACION', 1, 1),
    ('GES_CUR_ALTO_RIESGO_NO_ACREDITADAS_V3', 'GESTOR', 'CURSAMIENTO', 'Seguimiento de alto riesgo por rezago severo', 'Documenta el número de no acreditadas, la restricción dominante y la prioridad académica. La recomendación debe centrarse en regularización intensiva y no en aperturas adicionales.', 'ORIENTACION', 1, 1)
ON DUPLICATE KEY UPDATE
    perfil = VALUES(perfil),
    periodo_operativo = VALUES(periodo_operativo),
    titulo = VALUES(titulo),
    mensaje = VALUES(mensaje),
    tipo = VALUES(tipo),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Alto riesgo por acumulación severa de no acreditadas', 'Patrón para trayectorias con cinco o más UD no acreditadas donde la recomendación debe centrarse en regularización intensiva y continuidad controlada.', 0.99, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'UNIDADES_NO_ACREDITADAS'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas'
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'PRIORIZAR_OBLIGATORIAS'
WHERE p.nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'RECHAZAR_DESBLOQUEO_MANUAL'
WHERE p.nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'MANTENER_RESTRICCION'
WHERE p.nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_ALTO_RIESGO_NO_ACREDITADAS_V3'
WHERE p.nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_CUR_ALTO_RIESGO_NO_ACREDITADAS_V3'
WHERE p.nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_ALTO_RIESGO_NO_ACREDITADAS_V3'
WHERE p.nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_CUR_ALTO_RIESGO_NO_ACREDITADAS_V3'
WHERE p.nombre_patron = 'Alto riesgo por acumulacion severa de no acreditadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );
