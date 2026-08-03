-- Semilla inicial del panel "Asistente virtual" V2
-- Fecha: 2026-08-03
-- Objetivo:
--   1. Crear tipos de caso y patrones mínimos para el panel lateral del asistente.
--   2. Cargar mensajes estructurados por escenario/perfil/periodo.
--   3. Asociar mensajes a patrones para que el motor V2 pueda sobrescribir el panel por conocimiento.

INSERT INTO cat_tipo_caso_academico (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('TRAYECTORIA_REGULAR', 'Trayectoria regular', 'Casos sin unidades didácticas pendientes ni bloqueo activo.', 1, 1),
    ('UNIDADES_NO_ACREDITADAS', 'Irregularidad por no acreditadas', 'Casos con unidades didácticas pendientes por regularizar.', 1, 1),
    ('SERIACION', 'Seriación pendiente', 'Casos con unidades antecedentes pendientes que bloquean unidades subsecuentes.', 1, 1),
    ('REINCORPORACION', 'Reincorporación', 'Casos de baja temporal o parcial con reintegración a la trayectoria.', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Trayectoria regular con continuidad plena',
       'Patrón base para estudiantes regulares sin pendientes ni bloqueo por seriación.', 0.95, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'TRAYECTORIA_REGULAR'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico p
      WHERE p.nombre_patron = 'Trayectoria regular con continuidad plena'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Irregularidad con no acreditadas prioritarias',
       'Patrón base para trayectoria con unidades didácticas no acreditadas que deben priorizarse.', 0.97, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'UNIDADES_NO_ACREDITADAS'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico p
      WHERE p.nombre_patron = 'Irregularidad con no acreditadas prioritarias'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Seriación pendiente bloqueante',
       'Patrón base para antecedentes pendientes que bloquean registro de unidades subsecuentes.', 0.98, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'SERIACION'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico p
      WHERE p.nombre_patron = 'Seriación pendiente bloqueante'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Reincorporación con unidades pendientes',
       'Patrón base para estudiantes que vuelven a la trayectoria después de baja temporal o parcial.', 0.94, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'REINCORPORACION'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico p
      WHERE p.nombre_patron = 'Reincorporación con unidades pendientes'
  );

INSERT INTO cat_mensaje_institucional_contextual (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('EST_INS_REGULAR_PANEL_OP_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Mensaje operativo regular', 'Revisa tu diagnóstico académico y simula la carga para tu próximo periodo de reinscripción. Durante el periodo activo, podrás validar y confirmar tu selección final.', 'PANEL_OP', 1, 1),
    ('EST_INS_REGULAR_PANEL_RES_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Situación académica regular', 'Conservas una situación académica regular sin unidades didácticas pendientes. Tu objetivo es seleccionar la carga del bloque/semestre correspondiente para mantener la continuidad de tu trayectoria académica.', 'PANEL_RES', 1, 1),
    ('EST_INS_REGULAR_PANEL_QDE_20260803', 'ESTUDIANTE', 'INSCRIPCION', '¿Qué debo elegir?', 'Presentas una situación académica regular sin unidades didácticas pendientes. Tu atención prioritaria es acreditar la totalidad de unidades didácticas de tu semestre vigente para seleccionar la totalidad de tus unidades didácticas del próximo semestre.', 'PANEL_QDE', 1, 1),
    ('EST_INS_REGULAR_PANEL_SER_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Validar seriación', 'No se identifican bloqueos por seriación en tu trayectoria actual. Puedes seleccionar la totalidad de las unidades didácticas ofertadas correspondientes a tu semestre activo.', 'PANEL_SER', 1, 1),
    ('EST_INS_REGULAR_PANEL_SIM_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Simular carga', 'Tu trayectoria se mantiene regular. Utiliza el simulador para proyectar tus unidades didácticas del próximo periodo, asegurando una distribución equilibrada de tu carga académica.', 'PANEL_SIM', 1, 1),
    ('EST_INS_REGULAR_PANEL_CON_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Confirmar selección', 'Verifica que la carga proyectada cumpla con los créditos del periodo activo. Al estar al corriente, tu selección asegura el cumplimiento en tiempo y forma de tu Programa Educativo.', 'PANEL_CON', 1, 1),

    ('EST_INS_IRREGULAR_PANEL_OP_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Mensaje operativo irregular', 'Revisa tu diagnóstico académico y simula la carga para tu próximo periodo de reinscripción. Durante el periodo activo, podrás validar y confirmar tu selección final.', 'PANEL_OP', 1, 1),
    ('EST_INS_IRREGULAR_PANEL_RES_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Situación académica con irregularidad', 'Presentas unidades didácticas pendientes por regularizar. Tu prioridad es registrar estas unidades didácticas en cuanto se oferten.', 'PANEL_RES', 1, 1),
    ('EST_INS_IRREGULAR_PANEL_QDE_20260803', 'ESTUDIANTE', 'INSCRIPCION', '¿Qué debo elegir?', 'Presentas una situación académica con irregularidad. Tu atención inmediata es dar prioridad de registro a las unidades didácticas pendientes en cuanto se oferten.', 'PANEL_QDE', 1, 1),
    ('EST_INS_IRREGULAR_PANEL_SER_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Validar seriación', 'Es necesario priorizar las unidades didácticas pendientes. Si tus unidades didácticas pendientes no se ofertan en este periodo, puedes seleccionar unidades optativas disponibles para mantener tu estatus activo.', 'PANEL_SER', 1, 1),
    ('EST_INS_IRREGULAR_PANEL_SIM_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Simular carga', 'Utiliza el simulador para integrar primero tus unidades didácticas no acreditadas; si no se ofertan, puedes registrar optativas. Considera que acumular pendientes puede limitar la selección de unidades didácticas en periodos posteriores.', 'PANEL_SIM', 1, 1),
    ('EST_INS_IRREGULAR_PANEL_CON_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Confirmar selección', 'Confirma que tu selección priorice las unidades didácticas pendientes por regularizar.', 'PANEL_CON', 1, 1),

    ('EST_INS_SERIACION_PANEL_OP_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Mensaje operativo seriación', 'Revisa tu diagnóstico académico y simula la carga para tu próximo periodo de reinscripción. Durante el periodo activo, podrás validar y confirmar tu selección final.', 'PANEL_OP', 1, 1),
    ('EST_INS_SERIACION_PANEL_RES_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Situación académica con irregularidad', 'Presentas unidades didácticas antecedentes pendientes que bloquean el registro de unidades didácticas posteriores.', 'PANEL_RES', 1, 1),
    ('EST_INS_SERIACION_PANEL_QDE_20260803', 'ESTUDIANTE', 'INSCRIPCION', '¿Qué debo elegir?', 'Presentas una situación irregular con seriación pendiente. Tu atención inmediata es dar prioridad de registro a la unidad antecedente en cuanto se oferte.', 'PANEL_QDE', 1, 1),
    ('EST_INS_SERIACION_PANEL_SER_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Validar seriación', 'Acreditar la unidad didáctica antecedente es requisito obligatorio para desbloquear las unidades didácticas subsecuentes.', 'PANEL_SER', 1, 1),
    ('EST_INS_SERIACION_PANEL_SIM_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Simular carga', 'El simulador reflejará únicamente las unidades didácticas disponibles que no requieran la seriación pendiente. Si la unidad didáctica antecedente no se oferta, puedes simular la carga con unidades didácticas optativas disponibles.', 'PANEL_SIM', 1, 1),
    ('EST_INS_SERIACION_PANEL_CON_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Confirmar selección', 'Valida que hayas incluido la unidad didáctica seriada pendiente. Acreditar esta unidad didáctica te permitirá avanzar en tu trayectoria.', 'PANEL_CON', 1, 1),

    ('EST_INS_REINC_PANEL_OP_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Mensaje operativo reincorporación', 'Revisa tu diagnóstico académico y simula la carga para tu próximo periodo de reinscripción. Durante el periodo activo, podrás validar y confirmar tu selección final.', 'PANEL_OP', 1, 1),
    ('EST_INS_REINC_PANEL_RES_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Situación académica actual: Reincorporación', 'Registras unidades didácticas pendientes por reincorporación. Revisa la oferta educativa vigente para reactivar tu avance académico.', 'PANEL_RES', 1, 1),
    ('EST_INS_REINC_PANEL_QDE_20260803', 'ESTUDIANTE', 'INSCRIPCION', '¿Qué debo elegir?', 'Te encuentras en reincorporación. Tu atención inmediata es seleccionar las unidades didácticas ofertadas en este periodo para reactivar tu trayectoria académica.', 'PANEL_QDE', 1, 1),
    ('EST_INS_REINC_PANEL_SER_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Validar seriación', 'Verifica la disponibilidad de las unidades didácticas pendientes tras tu periodo de inactividad. Si una unidad didáctica obligatoria no se oferta, selecciona optativas para mantenerte como estudiante activo.', 'PANEL_SER', 1, 1),
    ('EST_INS_REINC_PANEL_SIM_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Simular carga', 'Proyecta una carga académica de acuerdo con tu disponibilidad para asegurar una reincorporación continua.', 'PANEL_SIM', 1, 1),
    ('EST_INS_REINC_PANEL_CON_20260803', 'ESTUDIANTE', 'INSCRIPCION', 'Confirmar selección', 'Valida que hayas incluido las unidades didácticas pendientes por reincorporación. Acreditarlas te permitirá avanzar en tu trayectoria.', 'PANEL_CON', 1, 1),

    ('GES_INS_REGULAR_PANEL_OP_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje operativo gestor regular', 'Apoyo para interpretar el caso y documentar la atención.', 'PANEL_OP', 1, 1),
    ('GES_INS_REGULAR_PANEL_RES_20260803', 'GESTOR', 'INSCRIPCION', 'Trayectoria regular con continuidad', 'Caso regular sin pendientes ni bloqueo activo. La atención debe centrarse en continuidad, carga proyectada y evidencia mínima de seguimiento.', 'PANEL_RES', 1, 1),
    ('GES_INS_REGULAR_PANEL_RTEC_20260803', 'GESTOR', 'INSCRIPCION', 'Resumen técnico', 'Revisar situación académica regular, riesgo actual bajo o controlado, y validar consistencia entre trayectoria, oferta y carga viable.', 'PANEL_RTEC', 1, 1),
    ('GES_INS_REGULAR_PANEL_REGLA_20260803', 'GESTOR', 'INSCRIPCION', 'Regla aplicada', 'Validar continuidad del bloque/semestre inmediato sin restricciones de seriación ni pendientes críticas.', 'PANEL_REGLA', 1, 1),
    ('GES_INS_REGULAR_PANEL_EVID_20260803', 'GESTOR', 'INSCRIPCION', 'Paquete de evidencia', 'Integrar periodo, situación académica, riesgo actual, dictamen y propuesta de carga proyectada.', 'PANEL_EVID', 1, 1),
    ('GES_INS_REGULAR_PANEL_MSG_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje sugerido', 'Comunicar al estudiante que su trayectoria es regular y que puede proyectar la totalidad de su carga compatible con el siguiente semestre.', 'PANEL_MSG', 1, 1),

    ('GES_INS_IRREGULAR_PANEL_OP_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje operativo gestor irregular', 'Apoyo para interpretar el caso y documentar la atención.', 'PANEL_OP', 1, 1),
    ('GES_INS_IRREGULAR_PANEL_RES_20260803', 'GESTOR', 'INSCRIPCION', 'Irregularidad con pendientes', 'Caso con unidades didácticas pendientes por regularizar. La atención debe priorizar regularización, oferta compatible y riesgo de continuidad.', 'PANEL_RES', 1, 1),
    ('GES_INS_IRREGULAR_PANEL_RTEC_20260803', 'GESTOR', 'INSCRIPCION', 'Resumen técnico', 'Revisar número de no acreditadas, riesgo actual y regla de priorización de pendientes antes de ampliar carga.', 'PANEL_RTEC', 1, 1),
    ('GES_INS_IRREGULAR_PANEL_REGLA_20260803', 'GESTOR', 'INSCRIPCION', 'Regla aplicada', 'La regla dominante debe priorizar pendientes, restringir combinaciones incompatibles y evitar prometer desbloqueos no soportados.', 'PANEL_REGLA', 1, 1),
    ('GES_INS_IRREGULAR_PANEL_EVID_20260803', 'GESTOR', 'INSCRIPCION', 'Paquete de evidencia', 'Integrar expediente mínimo, unidades pendientes, riesgo actual, regla aplicada y carga propuesta.', 'PANEL_EVID', 1, 1),
    ('GES_INS_IRREGULAR_PANEL_MSG_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje sugerido', 'Comunicar al estudiante que debe priorizar regularización y usar optativas solo si los pendientes no se ofertan en el periodo.', 'PANEL_MSG', 1, 1),

    ('GES_INS_SERIACION_PANEL_OP_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje operativo gestor seriación', 'Apoyo para interpretar el caso y documentar la atención.', 'PANEL_OP', 1, 1),
    ('GES_INS_SERIACION_PANEL_RES_20260803', 'GESTOR', 'INSCRIPCION', 'Seriación pendiente bloqueante', 'Caso con antecedentes pendientes que bloquean unidades subsecuentes. La atención debe enfatizar desbloqueo por acreditación antecedente.', 'PANEL_RES', 1, 1),
    ('GES_INS_SERIACION_PANEL_RTEC_20260803', 'GESTOR', 'INSCRIPCION', 'Resumen técnico', 'Revisar antecedente pendiente, total de unidades bloqueadas y oferta vigente de la unidad crítica.', 'PANEL_RTEC', 1, 1),
    ('GES_INS_SERIACION_PANEL_REGLA_20260803', 'GESTOR', 'INSCRIPCION', 'Regla aplicada', 'No debe habilitarse la unidad subsecuente mientras la antecedente no esté acreditada o regularizada conforme a regla.', 'PANEL_REGLA', 1, 1),
    ('GES_INS_SERIACION_PANEL_EVID_20260803', 'GESTOR', 'INSCRIPCION', 'Paquete de evidencia', 'Integrar antecedente bloqueante, unidades afectadas, oferta del periodo y dictamen vigente.', 'PANEL_EVID', 1, 1),
    ('GES_INS_SERIACION_PANEL_MSG_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje sugerido', 'Comunicar al estudiante que la prioridad es registrar la unidad antecedente; si no se oferta, puede mantenerse activo con optativas compatibles.', 'PANEL_MSG', 1, 1),

    ('GES_INS_REINC_PANEL_OP_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje operativo gestor reincorporación', 'Apoyo para interpretar el caso y documentar la atención.', 'PANEL_OP', 1, 1),
    ('GES_INS_REINC_PANEL_RES_20260803', 'GESTOR', 'INSCRIPCION', 'Reincorporación con unidades pendientes', 'Caso de reincorporación con unidades didácticas pendientes. La atención debe centrarse en oferta activa, reactivación progresiva y carga viable.', 'PANEL_RES', 1, 1),
    ('GES_INS_REINC_PANEL_RTEC_20260803', 'GESTOR', 'INSCRIPCION', 'Resumen técnico', 'Revisar unidades pendientes por reincorporación, disponibilidad operativa y riesgo de continuidad.', 'PANEL_RTEC', 1, 1),
    ('GES_INS_REINC_PANEL_REGLA_20260803', 'GESTOR', 'INSCRIPCION', 'Regla aplicada', 'La regla dominante debe favorecer reactivación ordenada, respetando oferta, carga y prioridad de pendientes críticas.', 'PANEL_REGLA', 1, 1),
    ('GES_INS_REINC_PANEL_EVID_20260803', 'GESTOR', 'INSCRIPCION', 'Paquete de evidencia', 'Integrar historial de baja, unidades pendientes, carga viable y dictamen de reincorporación.', 'PANEL_EVID', 1, 1),
    ('GES_INS_REINC_PANEL_MSG_20260803', 'GESTOR', 'INSCRIPCION', 'Mensaje sugerido', 'Comunicar al estudiante que debe reactivar trayectoria con unidades ofertadas prioritarias y simular una carga realista para asegurar continuidad.', 'PANEL_MSG', 1, 1)
ON DUPLICATE KEY UPDATE
    perfil = VALUES(perfil),
    periodo_operativo = VALUES(periodo_operativo),
    titulo = VALUES(titulo),
    mensaje = VALUES(mensaje),
    tipo = VALUES(tipo),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_INS_REGULAR_PANEL_OP_20260803', 'EST_INS_REGULAR_PANEL_RES_20260803',
      'EST_INS_REGULAR_PANEL_QDE_20260803', 'EST_INS_REGULAR_PANEL_SER_20260803',
      'EST_INS_REGULAR_PANEL_SIM_20260803', 'EST_INS_REGULAR_PANEL_CON_20260803',
      'GES_INS_REGULAR_PANEL_OP_20260803', 'GES_INS_REGULAR_PANEL_RES_20260803',
      'GES_INS_REGULAR_PANEL_RTEC_20260803', 'GES_INS_REGULAR_PANEL_REGLA_20260803',
      'GES_INS_REGULAR_PANEL_EVID_20260803', 'GES_INS_REGULAR_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Trayectoria regular con continuidad plena'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_INS_IRREGULAR_PANEL_OP_20260803', 'EST_INS_IRREGULAR_PANEL_RES_20260803',
      'EST_INS_IRREGULAR_PANEL_QDE_20260803', 'EST_INS_IRREGULAR_PANEL_SER_20260803',
      'EST_INS_IRREGULAR_PANEL_SIM_20260803', 'EST_INS_IRREGULAR_PANEL_CON_20260803',
      'GES_INS_IRREGULAR_PANEL_OP_20260803', 'GES_INS_IRREGULAR_PANEL_RES_20260803',
      'GES_INS_IRREGULAR_PANEL_RTEC_20260803', 'GES_INS_IRREGULAR_PANEL_REGLA_20260803',
      'GES_INS_IRREGULAR_PANEL_EVID_20260803', 'GES_INS_IRREGULAR_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Irregularidad con no acreditadas prioritarias'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_INS_SERIACION_PANEL_OP_20260803', 'EST_INS_SERIACION_PANEL_RES_20260803',
      'EST_INS_SERIACION_PANEL_QDE_20260803', 'EST_INS_SERIACION_PANEL_SER_20260803',
      'EST_INS_SERIACION_PANEL_SIM_20260803', 'EST_INS_SERIACION_PANEL_CON_20260803',
      'GES_INS_SERIACION_PANEL_OP_20260803', 'GES_INS_SERIACION_PANEL_RES_20260803',
      'GES_INS_SERIACION_PANEL_RTEC_20260803', 'GES_INS_SERIACION_PANEL_REGLA_20260803',
      'GES_INS_SERIACION_PANEL_EVID_20260803', 'GES_INS_SERIACION_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Seriación pendiente bloqueante'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_INS_REINC_PANEL_OP_20260803', 'EST_INS_REINC_PANEL_RES_20260803',
      'EST_INS_REINC_PANEL_QDE_20260803', 'EST_INS_REINC_PANEL_SER_20260803',
      'EST_INS_REINC_PANEL_SIM_20260803', 'EST_INS_REINC_PANEL_CON_20260803',
      'GES_INS_REINC_PANEL_OP_20260803', 'GES_INS_REINC_PANEL_RES_20260803',
      'GES_INS_REINC_PANEL_RTEC_20260803', 'GES_INS_REINC_PANEL_REGLA_20260803',
      'GES_INS_REINC_PANEL_EVID_20260803', 'GES_INS_REINC_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Reincorporación con unidades pendientes'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );
