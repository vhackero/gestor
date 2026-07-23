INSERT INTO cat_tipo_caso_academico (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('AVANCE_ANUAL', 'Avance anual', 'Casos vinculados con reglas de avance anual', 1, 1),
    ('REZAGO', 'Rezago academico', 'Casos con unidades pendientes o no acreditadas', 1, 1),
    ('OMISION_REGISTRO', 'Omision de registro', 'Casos donde no se registro una unidad esperada', 1, 1),
    ('OFERTA_NO_DISPONIBLE', 'Oferta no disponible', 'Casos sin oferta vigente para la unidad requerida', 1, 1),
    ('SERIACION', 'Restriccion por seriacion', 'Casos con bloqueos por seriacion academica', 1, 1),
    ('CARGA_LIMITADA', 'Carga limitada', 'Casos sujetos a carga maxima o minima', 1, 1),
    ('ACLARACION_AVANCE', 'Aclaracion de avance', 'Casos de revision por inconsistencia de avance', 1, 1),
    ('REINSCRIPCION_NO_APROBADA', 'Reinscripcion de unidad no aprobada', 'Casos de regularizacion por unidades no aprobadas', 1, 1),
    ('PENDIENTE_INFORMACION', 'Pendiente de informacion', 'Casos que requieren expediente o evidencia adicional', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_motivo_restriccion (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('UD_NO_ACREDITADA', 'Unidad no acreditada', 'La unidad fue cursada pero no acreditada', 1, 1),
    ('UD_NO_CURSADA', 'Unidad no cursada', 'La unidad no fue inscrita o no fue cursada', 1, 1),
    ('SIN_OFERTA', 'Sin oferta vigente', 'No existe oferta vigente para la unidad requerida', 1, 1),
    ('BLOQUEO_SERIACION', 'Bloqueo por seriacion', 'Existe una dependencia academica incumplida', 1, 1),
    ('EXCESO_CARGA', 'Exceso de carga', 'La seleccion supera la carga maxima permitida', 1, 1),
    ('CIERRE_ANUAL', 'Regla de cierre anual', 'La regla de cierre anual restringe el avance', 1, 1),
    ('INFORMACION_INSUFICIENTE', 'Informacion insuficiente', 'No se cuenta con evidencia suficiente para dictaminar', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_criterio_operativo (clave, nombre, descripcion, prioridad, activo, usuario_modifico)
VALUES
    ('PRIORIZAR_PENDIENTES', 'Priorizar pendientes', 'Atender primero unidades pendientes ofertadas', 10, 1, 1),
    ('RESPETAR_SERIACION', 'Respetar seriacion', 'No permitir avance sin cumplir seriacion', 20, 1, 1),
    ('VALIDAR_OFERTA', 'Validar oferta', 'Confirmar oferta vigente antes de dictaminar', 30, 1, 1),
    ('VALIDAR_CARGA_MAXIMA', 'Validar carga maxima', 'Corroborar carga maxima por periodo', 40, 1, 1),
    ('ESCALAR_EVIDENCIA', 'Escalar con evidencia', 'Escalar caso cuando la evidencia es insuficiente o conflictiva', 50, 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    prioridad = VALUES(prioridad),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_accion_operativa (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('DAR_SEGUIMIENTO', 'Dar seguimiento', 'Monitorear el caso durante cursamiento', 1, 1),
    ('REGISTRAR_PRIMERO', 'Registrar primero', 'Registrar primero las unidades prioritarias', 1, 1),
    ('VALIDAR_SELECCION', 'Validar seleccion', 'Validar seleccion final en periodo activo', 1, 1),
    ('ESCALAR_REVISION', 'Escalar revision', 'Enviar a revision con evidencia institucional', 1, 1),
    ('SOLICITAR_INFORMACION', 'Solicitar informacion', 'Solicitar informacion faltante del expediente', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_viabilidad_tecnica (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('VIABLE', 'Viable', 'El caso es viable sin ajustes adicionales', 1, 1),
    ('VIABLE_CON_AJUSTE', 'Viable con ajuste', 'El caso es viable si se ajusta la seleccion o el orden de atencion', 1, 1),
    ('NO_VIABLE', 'No viable', 'El caso no es viable bajo las reglas actuales', 1, 1),
    ('PENDIENTE_INFORMACION', 'Pendiente de informacion', 'No se puede cerrar el dictamen sin informacion adicional', 1, 1),
    ('REQUIERE_REVALORACION', 'Requiere revaloracion', 'El caso requiere revaloracion por instancia superior', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_mensaje_institucional_contextual (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('EST_CUR_RESUMEN', 'ESTUDIANTE', 'CURSAMIENTO', 'Seguimiento de trayectoria', 'Durante cursamiento activo puedes revisar tu avance, identificar riesgos y preparar tu siguiente periodo.', 'RESUMEN', 1, 1),
    ('EST_INS_RESUMEN', 'ESTUDIANTE', 'INSCRIPCION', 'Seleccion guiada', 'Tu seleccion debe priorizar unidades pendientes ofertadas y respetar la carga maxima permitida.', 'RESUMEN', 1, 1),
    ('GES_CUR_RESUMEN', 'GESTOR', 'CURSAMIENTO', 'Apoyo a seguimiento', 'La vista de gestor debe interpretar el caso, documentar la causa y orientar la atencion institucional.', 'RESUMEN', 1, 1),
    ('GES_INS_RESUMEN', 'GESTOR', 'INSCRIPCION', 'Apoyo a validacion', 'La vista de gestor debe validar la regla aplicada, la viabilidad y la necesidad de escalar el caso.', 'RESUMEN', 1, 1),
    ('EST_CUR_ORIENTA', 'ESTUDIANTE', 'CURSAMIENTO', 'Orientacion contextual', 'Da seguimiento a tus unidades de atencion y prepara el siguiente periodo con evidencia de tu avance.', 'ORIENTACION', 1, 1),
    ('EST_INS_ORIENTA', 'ESTUDIANTE', 'INSCRIPCION', 'Orientacion de seleccion', 'Selecciona primero las unidades obligatorias o pendientes antes de completar tu carga con opciones disponibles.', 'ORIENTACION', 1, 1),
    ('GES_CUR_ORIENTA', 'GESTOR', 'CURSAMIENTO', 'Respuesta sugerida', 'Documenta la causa principal, la regla aplicada y el mensaje mostrado al estudiante.', 'ORIENTACION', 1, 1),
    ('GES_INS_ORIENTA', 'GESTOR', 'INSCRIPCION', 'Escalamiento operativo', 'Si el caso no es resoluble en primer nivel, integra evidencia y registra el dictamen institucional.', 'ORIENTACION', 1, 1)
ON DUPLICATE KEY UPDATE
    perfil = VALUES(perfil),
    periodo_operativo = VALUES(periodo_operativo),
    titulo = VALUES(titulo),
    mensaje = VALUES(mensaje),
    tipo = VALUES(tipo),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Rezago por pendientes ofertadas', 'Patron base para estudiantes con unidades pendientes ofertadas durante inscripcion o seguimiento.', 0.80, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'REZAGO'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Rezago por pendientes ofertadas'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Bloqueo por seriacion activa', 'Patron base para restricciones de seriacion que impiden seleccionar una unidad.', 0.90, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'SERIACION'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Bloqueo por seriacion activa'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Pendiente por informacion faltante', 'Patron base para casos sin evidencia suficiente para emitir dictamen final.', 0.75, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'PENDIENTE_INFORMACION'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Pendiente por informacion faltante'
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'PRIORIZAR_PENDIENTES'
WHERE p.nombre_patron = 'Rezago por pendientes ofertadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'RESPETAR_SERIACION'
WHERE p.nombre_patron = 'Bloqueo por seriacion activa'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'ESCALAR_EVIDENCIA'
WHERE p.nombre_patron = 'Pendiente por informacion faltante'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'REGISTRAR_PRIMERO'
WHERE p.nombre_patron = 'Rezago por pendientes ofertadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'DAR_SEGUIMIENTO'
WHERE p.nombre_patron = 'Bloqueo por seriacion activa'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'SOLICITAR_INFORMACION'
WHERE p.nombre_patron = 'Pendiente por informacion faltante'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, 'ESTUDIANTE'
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_ORIENTA'
WHERE p.nombre_patron = 'Rezago por pendientes ofertadas'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = 'ESTUDIANTE'
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, 'GESTOR'
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_CUR_ORIENTA'
WHERE p.nombre_patron = 'Bloqueo por seriacion activa'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = 'GESTOR'
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, 'GESTOR'
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_ORIENTA'
WHERE p.nombre_patron = 'Pendiente por informacion faltante'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = 'GESTOR'
  );
