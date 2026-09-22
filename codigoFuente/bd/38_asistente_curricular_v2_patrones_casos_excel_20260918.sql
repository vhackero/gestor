-- Patrones complementarios derivados de los escenarios de analisis_de_casos.xlsx.
-- Son reglas de conocimiento reutilizables; no inserta los renglones del Excel en
-- tbl_caso_academico_operativo para no convertir expedientes de referencia en
-- casos activos de estudiantes reales.

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, v.nombre_patron, v.descripcion, v.confianza_base, 1, 1
FROM cat_tipo_caso_academico t
JOIN (
    SELECT 'UNIDADES_NO_ACREDITADAS' clave_tipo, 'No acreditadas con calificación definitiva pendiente' nombre_patron,
           'Una o más UD mantienen calificación no aprobatoria o sin cierre definitivo y deben priorizarse sin duplicarlas por bloque.' descripcion, 0.97 confianza_base
    UNION ALL SELECT 'CARGA_LIMITADA', 'Carga inicial y carga máxima validadas',
           'La carga propuesta se valida contra máximos de UD y créditos, respetando obligatorias y optativas requeridas.', 0.96
    UNION ALL SELECT 'AVANCE_ANUAL', 'Avance anual con primer semestre pendiente ofertado',
           'La trayectoria regular avanza con las UD del primer semestre obligatorio pendiente, sólo cuando la oferta vigente las habilita.', 0.95
    UNION ALL SELECT 'TRAYECTORIA_REGULAR', 'Trayectoria regular sin restricciones activas',
           'No existen no acreditadas, omisiones obligatorias ni bloqueos de seriación; la orientación se basa en la oferta vigente.', 0.98
    UNION ALL SELECT 'REINCORPORACION', 'Baja temporal conciliada con acreditación posterior',
           'Una baja histórica no conserva efecto restrictivo cuando existe acreditación posterior válida para la misma UD.', 0.98
    UNION ALL SELECT 'SERIACION', 'Antecedente acreditado sin bloqueo vigente',
           'Una seriación histórica se libera al acreditarse el antecedente; el motor debe recalcular y no sostener un bloqueo obsoleto.', 0.98
    UNION ALL SELECT 'OFERTA_NO_DISPONIBLE', 'Pendiente sin oferta en el periodo vigente',
           'Una UD pendiente se orienta para seguimiento cuando no existe oferta vigente compatible.', 0.96
    UNION ALL SELECT 'REINSCRIPCION_NO_APROBADA', 'Límite de intentos o primer año incompleto',
           'La reinscripción requiere validar la regla de intentos y el cierre de obligatorias del primer año antes de ampliar el avance.', 0.95
) v ON v.clave_tipo = t.clave
WHERE NOT EXISTS (
    SELECT 1 FROM rel_patron_caso_academico p
    WHERE p.id_tipo_caso = t.id AND p.nombre_patron = v.nombre_patron
);

-- Criterios asociados a los patrones anteriores.
INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'PRIORIZAR_PENDIENTES'
WHERE p.nombre_patron = 'No acreditadas con calificación definitiva pendiente'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id);

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'VALIDAR_CARGA_MAXIMA'
WHERE p.nombre_patron = 'Carga inicial y carga máxima validadas'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id);

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'VALIDAR_OFERTA'
WHERE p.nombre_patron IN ('Avance anual con primer semestre pendiente ofertado', 'Trayectoria regular sin restricciones activas', 'Pendiente sin oferta en el periodo vigente')
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id);

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'RESPETAR_SERIACION'
WHERE p.nombre_patron IN ('Baja temporal conciliada con acreditación posterior', 'Antecedente acreditado sin bloqueo vigente', 'Límite de intentos o primer año incompleto')
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id);

-- Acciones coherentes con cada escenario, evitando que una baja o seriación ya
-- resuelta mantenga una restricción académica activa.
INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'PRIORIZAR_REGULARIZACION'
WHERE p.nombre_patron = 'No acreditadas con calificación definitiva pendiente'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id);

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'VALIDAR_SELECCION'
WHERE p.nombre_patron IN ('Carga inicial y carga máxima validadas', 'Avance anual con primer semestre pendiente ofertado')
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id);

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'DAR_SEGUIMIENTO'
WHERE p.nombre_patron IN ('Trayectoria regular sin restricciones activas', 'Baja temporal conciliada con acreditación posterior', 'Antecedente acreditado sin bloqueo vigente', 'Pendiente sin oferta en el periodo vigente')
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id);

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'MANTENER_RESTRICCION'
WHERE p.nombre_patron = 'Límite de intentos o primer año incompleto'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id);
