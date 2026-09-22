-- Complemento idempotente derivado de analisis_de_casos.xlsx.
-- El catálogo académico-operativo proporcionado coincide con los 14 tipos
-- existentes. Se agregan únicamente tres etiquetas de casos de prueba que
-- requieren tratamiento operativo específico y no deben reclasificar por sí
-- solas una trayectoria académica regular.

INSERT INTO cat_tipo_caso_academico (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('EQUIVALENCIA_PE', 'Equivalencia entre programas educativos', 'Acreditaciones de otro plan que deben reconocerse y excluirse de la oferta del plan actual.', 1, 1),
    ('CONFIGURACION_PLAN', 'Configuración de reglas del plan', 'Caso operativo para revisar o probar parámetros de inscripción sin alterar la clasificación académica del estudiante.', 1, 1),
    ('ESTADO_INSCRIPCION', 'Estado de inscripción', 'Caso operativo sobre concurrencia, vigencia o consistencia de la inscripción final.', 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), activo = VALUES(activo), usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_motivo_restriccion (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('EQUIVALENCIA_ACREDITADA', 'Equivalencia acreditada', 'Existe una UD acreditada en otro plan que corresponde a una UD del plan vigente.', 1, 1),
    ('REGLA_PLAN_CONFIGURADA', 'Regla del plan en revisión', 'La resolución depende de validar la configuración vigente del plan educativo.', 1, 1),
    ('ESTADO_INSCRIPCION_VIGENTE', 'Estado de inscripción vigente', 'La resolución depende de validar la inscripción previa, su vigencia o concurrencia.', 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), activo = VALUES(activo), usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_criterio_operativo (clave, nombre, descripcion, prioridad, activo, usuario_modifico)
VALUES
    ('VALIDAR_EQUIVALENCIA_PE', 'Validar equivalencia entre planes', 'Reconocer equivalencias acreditadas por clave y nombre normalizado antes de construir la oferta.', 30, 1, 1),
    ('VALIDAR_CONFIGURACION_PLAN', 'Validar configuración del plan', 'Verificar reglas, umbrales y excepciones del plan antes de emitir un resultado operativo.', 35, 1, 1),
    ('VALIDAR_ESTADO_INSCRIPCION', 'Validar estado de inscripción', 'Comprobar vigencia y existencia de inscripción previa antes de confirmar la selección.', 35, 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), prioridad = VALUES(prioridad), activo = VALUES(activo), usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_accion_operativa (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('RECONOCER_EQUIVALENCIA_PE', 'Reconocer equivalencia acreditada', 'Excluir de la oferta las UD ya acreditadas por equivalencia válida.', 1, 1),
    ('REVISAR_CONFIGURACION_PLAN', 'Revisar configuración del plan', 'Revisar parámetros y excepciones sin modificar la trayectoria del estudiante.', 1, 1),
    ('VERIFICAR_INSCRIPCION_PREVIA', 'Verificar inscripción previa', 'Validar transaccionalmente la inscripción existente antes de continuar.', 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), activo = VALUES(activo), usuario_modifico = VALUES(usuario_modifico);

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Equivalencia acreditada de otro plan', 'Patrón para evitar ofertar nuevamente UD acreditadas en un plan anterior mediante equivalencia por nombre normalizado.', 0.95, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'EQUIVALENCIA_PE'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_caso_academico p WHERE p.id_tipo_caso = t.id AND p.nombre_patron = 'Equivalencia acreditada de otro plan');

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Validación de configuración por plan', 'Patrón para escenarios de prueba o revisión de umbrales, marcado y excepciones de un plan educativo.', 0.90, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'CONFIGURACION_PLAN'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_caso_academico p WHERE p.id_tipo_caso = t.id AND p.nombre_patron = 'Validación de configuración por plan');

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Validación transaccional de inscripción', 'Patrón para prevenir doble inscripción y revalidar la oferta antes de confirmar.', 0.94, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'ESTADO_INSCRIPCION'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_caso_academico p WHERE p.id_tipo_caso = t.id AND p.nombre_patron = 'Validación transaccional de inscripción');

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id FROM rel_patron_caso_academico p JOIN cat_criterio_operativo c ON c.clave = 'VALIDAR_EQUIVALENCIA_PE'
WHERE p.nombre_patron = 'Equivalencia acreditada de otro plan'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id);

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id FROM rel_patron_caso_academico p JOIN cat_criterio_operativo c ON c.clave = 'VALIDAR_CONFIGURACION_PLAN'
WHERE p.nombre_patron = 'Validación de configuración por plan'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id);

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id FROM rel_patron_caso_academico p JOIN cat_criterio_operativo c ON c.clave = 'VALIDAR_ESTADO_INSCRIPCION'
WHERE p.nombre_patron = 'Validación transaccional de inscripción'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id);

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id FROM rel_patron_caso_academico p JOIN cat_accion_operativa a ON a.clave = 'RECONOCER_EQUIVALENCIA_PE'
WHERE p.nombre_patron = 'Equivalencia acreditada de otro plan'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id);

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id FROM rel_patron_caso_academico p JOIN cat_accion_operativa a ON a.clave = 'REVISAR_CONFIGURACION_PLAN'
WHERE p.nombre_patron = 'Validación de configuración por plan'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id);

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id FROM rel_patron_caso_academico p JOIN cat_accion_operativa a ON a.clave = 'VERIFICAR_INSCRIPCION_PREVIA'
WHERE p.nombre_patron = 'Validación transaccional de inscripción'
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id);
