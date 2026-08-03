-- Semilla inicial del panel "Asistente virtual" V2 para periodo operativo CURSAMIENTO
-- Fecha: 2026-08-03
-- Requiere:
--   - 29_asistente_curricular_v2_panel_virtual_seed_20260803.sql
-- Objetivo:
--   1. Cargar mensajes estructurados del panel lateral para CURSAMIENTO.
--   2. Reutilizar los mismos patrones base definidos para INSCRIPCION.
--   3. Permitir que el motor V2 sobrescriba el panel durante seguimiento en cursamiento.

INSERT INTO cat_mensaje_institucional_contextual
    (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('EST_CUR_REGULAR_PANEL_OP_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Mensaje operativo regular', 'Revisa tu avance académico actual y da seguimiento a tu carga en curso. Durante cursamiento puedes identificar riesgos y preparar con anticipación tu siguiente reinscripción.', 'PANEL_OP', 1, 1),
    ('EST_CUR_REGULAR_PANEL_RES_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Situación académica regular', 'Mantienes una trayectoria académica regular durante el cursamiento activo. Tu prioridad es acreditar las unidades didácticas inscritas para conservar continuidad en el siguiente periodo.', 'PANEL_RES', 1, 1),
    ('EST_CUR_REGULAR_PANEL_QDE_20260803', 'ESTUDIANTE', 'CURSAMIENTO', '¿Qué debo elegir?', 'Durante este periodo tu atención principal es sostener el avance de las unidades didácticas en curso y evitar incidencias que afecten tu siguiente reinscripción.', 'PANEL_QDE', 1, 1),
    ('EST_CUR_REGULAR_PANEL_SER_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Validar seriación', 'No se identifican bloqueos de seriación activos sobre tu trayectoria actual. Si acreditas tu carga en curso, podrás avanzar al siguiente bloque previsto.', 'PANEL_SER', 1, 1),
    ('EST_CUR_REGULAR_PANEL_SIM_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Simular carga', 'Aún no confirmas una nueva selección, pero puedes usar el simulador como referencia para preparar el siguiente periodo con una distribución equilibrada.', 'PANEL_SIM', 1, 1),
    ('EST_CUR_REGULAR_PANEL_CON_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Confirmar selección', 'En esta etapa el foco no es confirmar una nueva carga, sino acreditar satisfactoriamente tus unidades actuales para conservar una reinscripción sin restricciones.', 'PANEL_CON', 1, 1),

    ('EST_CUR_IRREGULAR_PANEL_OP_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Mensaje operativo irregular', 'Durante cursamiento revisa tu avance, da seguimiento a tus unidades activas y prepara desde ahora la regularización de pendientes para el siguiente periodo.', 'PANEL_OP', 1, 1),
    ('EST_CUR_IRREGULAR_PANEL_RES_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Situación académica con irregularidad', 'Mantienes unidades didácticas pendientes por regularizar mientras cursas tu carga activa. Tu prioridad es concluir lo inscrito y preparar la reincorporación de pendientes en cuanto exista oferta.', 'PANEL_RES', 1, 1),
    ('EST_CUR_IRREGULAR_PANEL_QDE_20260803', 'ESTUDIANTE', 'CURSAMIENTO', '¿Qué debo elegir?', 'Durante el cursamiento actual debes centrarte en acreditar las unidades didácticas activas y documentar cuáles pendientes deberán priorizarse en la siguiente reinscripción.', 'PANEL_QDE', 1, 1),
    ('EST_CUR_IRREGULAR_PANEL_SER_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Validar seriación', 'Si existen unidades didácticas pendientes, es importante revisar si alguna de ellas puede bloquear el avance posterior. Concluir tu carga actual reduce presión para el siguiente periodo.', 'PANEL_SER', 1, 1),
    ('EST_CUR_IRREGULAR_PANEL_SIM_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Simular carga', 'Utiliza el simulador como apoyo para prever cómo integrarás primero tus pendientes y después las unidades compatibles que permitan mantener continuidad académica.', 'PANEL_SIM', 1, 1),
    ('EST_CUR_IRREGULAR_PANEL_CON_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Confirmar selección', 'La confirmación del siguiente periodo dependerá de cómo cierres tu carga actual y de la oferta de las unidades pendientes por regularizar.', 'PANEL_CON', 1, 1),

    ('EST_CUR_SERIACION_PANEL_OP_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Mensaje operativo seriación', 'Durante cursamiento revisa el avance de tus unidades activas y da seguimiento a la unidad antecedente que condiciona tu siguiente reinscripción.', 'PANEL_OP', 1, 1),
    ('EST_CUR_SERIACION_PANEL_RES_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Situación académica con irregularidad', 'Mantienes una seriación pendiente durante el cursamiento. La acreditación de la unidad antecedente es clave para desbloquear unidades subsecuentes en el siguiente periodo.', 'PANEL_RES', 1, 1),
    ('EST_CUR_SERIACION_PANEL_QDE_20260803', 'ESTUDIANTE', 'CURSAMIENTO', '¿Qué debo elegir?', 'Tu atención principal es acreditar la unidad antecedente o verificar su avance actual para no trasladar el bloqueo al siguiente periodo.', 'PANEL_QDE', 1, 1),
    ('EST_CUR_SERIACION_PANEL_SER_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Validar seriación', 'La seriación sigue vigente mientras no se acredite la unidad antecedente. Debes monitorear ese resultado antes de proyectar nuevas unidades bloqueadas.', 'PANEL_SER', 1, 1),
    ('EST_CUR_SERIACION_PANEL_SIM_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Simular carga', 'El simulador te ayudará a visualizar únicamente las unidades que podrían quedar habilitadas si acreditas la antecedente y las alternativas compatibles si el bloqueo continúa.', 'PANEL_SIM', 1, 1),
    ('EST_CUR_SERIACION_PANEL_CON_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Confirmar selección', 'La siguiente confirmación de carga debe considerar primero el resultado de la unidad seriada antecedente.', 'PANEL_CON', 1, 1),

    ('EST_CUR_REINC_PANEL_OP_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Mensaje operativo reincorporación', 'Durante cursamiento da seguimiento a tu reincorporación académica y verifica que la carga actual sea sostenible para asegurar continuidad en el siguiente periodo.', 'PANEL_OP', 1, 1),
    ('EST_CUR_REINC_PANEL_RES_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Situación académica actual: Reincorporación', 'Te encuentras en proceso de reincorporación durante el cursamiento activo. La prioridad es sostener una carga viable y consolidar el regreso continuo a tu trayectoria académica.', 'PANEL_RES', 1, 1),
    ('EST_CUR_REINC_PANEL_QDE_20260803', 'ESTUDIANTE', 'CURSAMIENTO', '¿Qué debo elegir?', 'Tu atención actual es mantener el ritmo de las unidades inscritas y preparar una siguiente selección que fortalezca tu reincorporación sin sobrecargar tu avance.', 'PANEL_QDE', 1, 1),
    ('EST_CUR_REINC_PANEL_SER_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Validar seriación', 'Verifica si alguna unidad pendiente tras la reincorporación condiciona tu siguiente reinscripción y considera alternativas compatibles para mantener continuidad.', 'PANEL_SER', 1, 1),
    ('EST_CUR_REINC_PANEL_SIM_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Simular carga', 'Proyecta con anticipación una carga realista para el siguiente periodo, considerando tu disponibilidad y las unidades prioritarias para consolidar la reincorporación.', 'PANEL_SIM', 1, 1),
    ('EST_CUR_REINC_PANEL_CON_20260803', 'ESTUDIANTE', 'CURSAMIENTO', 'Confirmar selección', 'La siguiente confirmación de carga debe reforzar tu continuidad académica y evitar interrupciones adicionales.', 'PANEL_CON', 1, 1),

    ('GES_CUR_REGULAR_PANEL_OP_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje operativo gestor regular', 'Apoyo para seguimiento del caso durante cursamiento y preparación del siguiente periodo.', 'PANEL_OP', 1, 1),
    ('GES_CUR_REGULAR_PANEL_RES_20260803', 'GESTOR', 'CURSAMIENTO', 'Seguimiento de trayectoria regular', 'Caso en cursamiento sin pendientes críticas. La atención del gestor debe centrarse en continuidad, monitoreo de avance y preparación ordenada del siguiente periodo.', 'PANEL_RES', 1, 1),
    ('GES_CUR_REGULAR_PANEL_RTEC_20260803', 'GESTOR', 'CURSAMIENTO', 'Resumen técnico', 'Revisar estatus en curso, riesgo vigente, continuidad del bloque y ausencia de restricciones críticas.', 'PANEL_RTEC', 1, 1),
    ('GES_CUR_REGULAR_PANEL_REGLA_20260803', 'GESTOR', 'CURSAMIENTO', 'Regla aplicada', 'En cursamiento regular debe priorizarse el seguimiento de avance y la preparación anticipada de reinscripción sin introducir restricciones artificiales.', 'PANEL_REGLA', 1, 1),
    ('GES_CUR_REGULAR_PANEL_EVID_20260803', 'GESTOR', 'CURSAMIENTO', 'Paquete de evidencia', 'Integrar avance actual, carga en curso, riesgo vigente y señales de continuidad para el siguiente periodo.', 'PANEL_EVID', 1, 1),
    ('GES_CUR_REGULAR_PANEL_MSG_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje sugerido', 'Comunicar al estudiante que debe concentrarse en acreditar su carga actual y preparar con anticipación la siguiente reinscripción.', 'PANEL_MSG', 1, 1),

    ('GES_CUR_IRREGULAR_PANEL_OP_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje operativo gestor irregular', 'Apoyo para seguimiento del caso durante cursamiento y preparación del siguiente periodo.', 'PANEL_OP', 1, 1),
    ('GES_CUR_IRREGULAR_PANEL_RES_20260803', 'GESTOR', 'CURSAMIENTO', 'Seguimiento de irregularidad por pendientes', 'Caso con pendientes activos durante cursamiento. La atención debe combinar seguimiento de carga actual con preparación de regularización posterior.', 'PANEL_RES', 1, 1),
    ('GES_CUR_IRREGULAR_PANEL_RTEC_20260803', 'GESTOR', 'CURSAMIENTO', 'Resumen técnico', 'Revisar pendientes, riesgo actual, desempeño de la carga en curso y presión esperada para la siguiente reinscripción.', 'PANEL_RTEC', 1, 1),
    ('GES_CUR_IRREGULAR_PANEL_REGLA_20260803', 'GESTOR', 'CURSAMIENTO', 'Regla aplicada', 'Durante cursamiento la orientación debe evitar prometer desbloqueos inmediatos y preparar una regularización priorizada para el siguiente periodo.', 'PANEL_REGLA', 1, 1),
    ('GES_CUR_IRREGULAR_PANEL_EVID_20260803', 'GESTOR', 'CURSAMIENTO', 'Paquete de evidencia', 'Integrar unidades pendientes, avance en curso, riesgo y proyección operativa del siguiente periodo.', 'PANEL_EVID', 1, 1),
    ('GES_CUR_IRREGULAR_PANEL_MSG_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje sugerido', 'Comunicar al estudiante que la prioridad inmediata es concluir la carga activa y preparar una reinscripción enfocada en regularización.', 'PANEL_MSG', 1, 1),

    ('GES_CUR_SERIACION_PANEL_OP_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje operativo gestor seriación', 'Apoyo para seguimiento del caso durante cursamiento y preparación del siguiente periodo.', 'PANEL_OP', 1, 1),
    ('GES_CUR_SERIACION_PANEL_RES_20260803', 'GESTOR', 'CURSAMIENTO', 'Seguimiento de seriación pendiente', 'Caso con antecedente pendiente o en seguimiento durante cursamiento. La atención debe centrarse en el desbloqueo académico condicionado.', 'PANEL_RES', 1, 1),
    ('GES_CUR_SERIACION_PANEL_RTEC_20260803', 'GESTOR', 'CURSAMIENTO', 'Resumen técnico', 'Revisar unidad antecedente, resultado esperado del periodo y posibles unidades subsecuentes afectadas.', 'PANEL_RTEC', 1, 1),
    ('GES_CUR_SERIACION_PANEL_REGLA_20260803', 'GESTOR', 'CURSAMIENTO', 'Regla aplicada', 'La seriación no se levanta durante el seguimiento sin acreditación de la antecedente; la comunicación debe apegarse a esa regla.', 'PANEL_REGLA', 1, 1),
    ('GES_CUR_SERIACION_PANEL_EVID_20260803', 'GESTOR', 'CURSAMIENTO', 'Paquete de evidencia', 'Integrar avance de la antecedente, unidades bloqueadas y oferta esperada para el siguiente periodo.', 'PANEL_EVID', 1, 1),
    ('GES_CUR_SERIACION_PANEL_MSG_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje sugerido', 'Comunicar al estudiante que el resultado de la unidad antecedente condicionará su siguiente reinscripción.', 'PANEL_MSG', 1, 1),

    ('GES_CUR_REINC_PANEL_OP_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje operativo gestor reincorporación', 'Apoyo para seguimiento del caso durante cursamiento y preparación del siguiente periodo.', 'PANEL_OP', 1, 1),
    ('GES_CUR_REINC_PANEL_RES_20260803', 'GESTOR', 'CURSAMIENTO', 'Seguimiento de reincorporación', 'Caso de reincorporación activa durante cursamiento. La atención del gestor debe validar sostenibilidad de la carga, continuidad y riesgo de nueva interrupción.', 'PANEL_RES', 1, 1),
    ('GES_CUR_REINC_PANEL_RTEC_20260803', 'GESTOR', 'CURSAMIENTO', 'Resumen técnico', 'Revisar carga vigente, pendientes por reincorporación, señales de continuidad y viabilidad de la siguiente reinscripción.', 'PANEL_RTEC', 1, 1),
    ('GES_CUR_REINC_PANEL_REGLA_20260803', 'GESTOR', 'CURSAMIENTO', 'Regla aplicada', 'Durante la reincorporación debe privilegiarse continuidad progresiva, carga viable y seguimiento preventivo.', 'PANEL_REGLA', 1, 1),
    ('GES_CUR_REINC_PANEL_EVID_20260803', 'GESTOR', 'CURSAMIENTO', 'Paquete de evidencia', 'Integrar historial de baja, avance actual, continuidad del cursamiento y preparación del siguiente periodo.', 'PANEL_EVID', 1, 1),
    ('GES_CUR_REINC_PANEL_MSG_20260803', 'GESTOR', 'CURSAMIENTO', 'Mensaje sugerido', 'Comunicar al estudiante que debe consolidar su reincorporación acreditando la carga actual y proyectando un siguiente periodo sostenible.', 'PANEL_MSG', 1, 1)
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
      'EST_CUR_REGULAR_PANEL_OP_20260803', 'EST_CUR_REGULAR_PANEL_RES_20260803',
      'EST_CUR_REGULAR_PANEL_QDE_20260803', 'EST_CUR_REGULAR_PANEL_SER_20260803',
      'EST_CUR_REGULAR_PANEL_SIM_20260803', 'EST_CUR_REGULAR_PANEL_CON_20260803',
      'GES_CUR_REGULAR_PANEL_OP_20260803', 'GES_CUR_REGULAR_PANEL_RES_20260803',
      'GES_CUR_REGULAR_PANEL_RTEC_20260803', 'GES_CUR_REGULAR_PANEL_REGLA_20260803',
      'GES_CUR_REGULAR_PANEL_EVID_20260803', 'GES_CUR_REGULAR_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Trayectoria regular con continuidad plena'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id
        AND r.id_mensaje_contextual = m.id
        AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_CUR_IRREGULAR_PANEL_OP_20260803', 'EST_CUR_IRREGULAR_PANEL_RES_20260803',
      'EST_CUR_IRREGULAR_PANEL_QDE_20260803', 'EST_CUR_IRREGULAR_PANEL_SER_20260803',
      'EST_CUR_IRREGULAR_PANEL_SIM_20260803', 'EST_CUR_IRREGULAR_PANEL_CON_20260803',
      'GES_CUR_IRREGULAR_PANEL_OP_20260803', 'GES_CUR_IRREGULAR_PANEL_RES_20260803',
      'GES_CUR_IRREGULAR_PANEL_RTEC_20260803', 'GES_CUR_IRREGULAR_PANEL_REGLA_20260803',
      'GES_CUR_IRREGULAR_PANEL_EVID_20260803', 'GES_CUR_IRREGULAR_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Irregularidad con no acreditadas prioritarias'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id
        AND r.id_mensaje_contextual = m.id
        AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_CUR_SERIACION_PANEL_OP_20260803', 'EST_CUR_SERIACION_PANEL_RES_20260803',
      'EST_CUR_SERIACION_PANEL_QDE_20260803', 'EST_CUR_SERIACION_PANEL_SER_20260803',
      'EST_CUR_SERIACION_PANEL_SIM_20260803', 'EST_CUR_SERIACION_PANEL_CON_20260803',
      'GES_CUR_SERIACION_PANEL_OP_20260803', 'GES_CUR_SERIACION_PANEL_RES_20260803',
      'GES_CUR_SERIACION_PANEL_RTEC_20260803', 'GES_CUR_SERIACION_PANEL_REGLA_20260803',
      'GES_CUR_SERIACION_PANEL_EVID_20260803', 'GES_CUR_SERIACION_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Seriación pendiente bloqueante'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id
        AND r.id_mensaje_contextual = m.id
        AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m
  ON m.clave IN (
      'EST_CUR_REINC_PANEL_OP_20260803', 'EST_CUR_REINC_PANEL_RES_20260803',
      'EST_CUR_REINC_PANEL_QDE_20260803', 'EST_CUR_REINC_PANEL_SER_20260803',
      'EST_CUR_REINC_PANEL_SIM_20260803', 'EST_CUR_REINC_PANEL_CON_20260803',
      'GES_CUR_REINC_PANEL_OP_20260803', 'GES_CUR_REINC_PANEL_RES_20260803',
      'GES_CUR_REINC_PANEL_RTEC_20260803', 'GES_CUR_REINC_PANEL_REGLA_20260803',
      'GES_CUR_REINC_PANEL_EVID_20260803', 'GES_CUR_REINC_PANEL_MSG_20260803'
  )
WHERE p.nombre_patron = 'Reincorporación con unidades pendientes'
  AND NOT EXISTS (
      SELECT 1
      FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id
        AND r.id_mensaje_contextual = m.id
        AND r.perfil = m.perfil
  );

