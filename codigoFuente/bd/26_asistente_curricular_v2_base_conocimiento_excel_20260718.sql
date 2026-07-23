INSERT INTO cat_tipo_caso_academico (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('UNIDAD_PENDIENTE', 'Unidad didactica pendiente', 'Casos donde una UD pendiente debe priorizarse en la seleccion.', 1, 1),
    ('REGULARIZACION_CIERRE', 'Regularizacion cierre anual', 'Casos sujetos a cierre anual y regularizacion operativa.', 1, 1),
    ('OTRO_OPERATIVO', 'Otro operativo', 'Casos que requieren clasificacion manual o revaloracion adicional.', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_motivo_restriccion (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('REZAGO_ACUMULADO', 'Rezago acumulado', 'Existe acumulacion de UD pendientes o no acreditadas.', 1, 1),
    ('RESTRICCION_AVANCE_ANUAL', 'Restriccion por avance anual', 'La regla de avance anual condiciona la inscripcion.', 1, 1),
    ('OMISION_VS_NO_ACREDITACION', 'Omisión vs no acreditacion', 'Se requiere distinguir omision de registro contra no acreditacion.', 1, 1),
    ('DICTAMEN_AMBIGUO', 'Dictamen ambiguo', 'La resolucion academica no es suficiente para ejecutar el ajuste.', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_criterio_operativo (clave, nombre, descripcion, prioridad, activo, usuario_modifico)
VALUES
    ('VALIDAR_AVANCE_ANUAL', 'Validar avance anual', 'Verificar si la restriccion dominante es avance anual o una condicion distinta.', 15, 1, 1),
    ('DIFERENCIAR_OMISION_NO_ACREDITACION', 'Diferenciar omision y no acreditacion', 'No tratar una omision de registro como una no acreditacion sin evidencia.', 25, 1, 1),
    ('PRIORIZAR_OBLIGATORIAS', 'Priorizar obligatorias', 'Atender primero UD obligatorias pendientes antes de optativas.', 35, 1, 1),
    ('COMPLETAR_DICTAMEN_DAEAE', 'Completar dictamen academico', 'Solicitar resolucion academica completa antes de intervenir operativamente.', 45, 1, 1),
    ('NO_HABILITAR_MANUALMENTE', 'No habilitar manualmente', 'No forzar alta de UD bloqueadas cuando el sistema mantiene la restriccion.', 55, 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    prioridad = VALUES(prioridad),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_accion_operativa (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('COMPLETAR_EXPEDIENTE', 'Completar expediente', 'Solicitar informacion faltante y resolucion academica antes de ejecutar cambios.', 1, 1),
    ('INSCRIBIR_SOLO_HABILITADAS', 'Inscribir solo habilitadas', 'Mantener la seleccion dentro de las UD que el sistema permite registrar.', 1, 1),
    ('APLICAR_AJUSTE_CARGA', 'Aplicar ajuste de carga', 'Ajustar la carga de inscripcion conforme al dictamen academico.', 1, 1),
    ('REVALORAR_DICTAMEN', 'Revalorar dictamen', 'Escalar el caso para una nueva valoracion academica u operativa.', 1, 1),
    ('COMUNICAR_RESTRICCION', 'Comunicar restriccion', 'Explicar formalmente al estudiante la regla aplicada y el alcance del ajuste.', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_mensaje_institucional_contextual (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('EST_INS_REZAGO_V1', 'ESTUDIANTE', 'INSCRIPCION', 'Rezago con prioridad de regularizacion', 'Tu seleccion debe priorizar UD pendientes u obligatorias antes de intentar avanzar con otras unidades.', 'ORIENTACION', 1, 1),
    ('EST_CUR_REZAGO_V1', 'ESTUDIANTE', 'CURSAMIENTO', 'Seguimiento de rezago', 'Durante cursamiento da seguimiento a tus UD pendientes y prepara evidencia para el siguiente periodo si persiste un bloqueo.', 'ORIENTACION', 1, 1),
    ('GES_INS_REZAGO_V1', 'GESTOR', 'INSCRIPCION', 'Interpretacion operativa de rezago', 'Verifica si el bloqueo proviene de avance anual, carga, seriacion o acumulacion de pendientes antes de solicitar un ajuste.', 'ORIENTACION', 1, 1),
    ('GES_CUR_REZAGO_V1', 'GESTOR', 'CURSAMIENTO', 'Seguimiento de caso con rezago', 'Documenta la restriccion dominante, las UD involucradas y el mensaje que debe comunicarse al estudiante.', 'ORIENTACION', 1, 1),
    ('EST_INS_INFO_V1', 'ESTUDIANTE', 'INSCRIPCION', 'Informacion insuficiente', 'Aun no es posible dictaminar tu caso; primero se requiere completar la solicitud, las UD involucradas y la resolucion academica.', 'RESUMEN', 1, 1),
    ('GES_INS_INFO_V1', 'GESTOR', 'INSCRIPCION', 'Caso con informacion insuficiente', 'No se deben ejecutar ajustes mientras falte descripcion del caso, resolucion DAEAE/DD o detalle de las UD solicitadas.', 'RESUMEN', 1, 1),
    ('EST_INS_AVANCE_V1', 'ESTUDIANTE', 'INSCRIPCION', 'Avance anual y continuidad', 'Si tu caso corresponde al mismo anio academico, el sistema puede permitir continuidad parcial; aun asi deben respetarse carga y pendientes.', 'ORIENTACION', 1, 1),
    ('GES_INS_AVANCE_V1', 'GESTOR', 'INSCRIPCION', 'Aclaracion de avance anual', 'Distingue cuando la solicitud es continuidad del mismo anio y cuando realmente existe bloqueo por avance anual.', 'ORIENTACION', 1, 1),
    ('GES_CUR_ACLARACION_V1', 'GESTOR', 'CURSAMIENTO', 'Aclaracion de avance', 'Usa el historial, la oferta y la regla aplicada para explicar por que una UD no aparece o no puede habilitarse.', 'ORIENTACION', 1, 1)
ON DUPLICATE KEY UPDATE
    perfil = VALUES(perfil),
    periodo_operativo = VALUES(periodo_operativo),
    titulo = VALUES(titulo),
    mensaje = VALUES(mensaje),
    tipo = VALUES(tipo),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Caso con rezago por acumulacion de pendientes', 'Patron derivado del Excel para estudiantes con rezago, optativas acumuladas o UD pendientes que limitan la inscripcion.', 0.92, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'REZAGO'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Caso con rezago por acumulacion de pendientes'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Unidad didactica pendiente obligatoria', 'Patron para solicitudes donde una UD obligatoria pendiente debe atenderse antes de avanzar con otras del plan.', 0.89, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave IN ('UNIDAD_PENDIENTE', 'REZAGO')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Unidad didactica pendiente obligatoria'
  )
LIMIT 1;

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Aclaracion de avance frente a otras restricciones', 'Patron para revisar si la condicion real es avance anual, oferta, carga o una no acreditacion previa.', 0.87, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'ACLARACION_AVANCE'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Aclaracion de avance frente a otras restricciones'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Reinscripcion de unidad no aprobada del primer anio', 'Patron para casos donde una UD del primer anio no aprobada impide habilitar obligatorias del segundo anio.', 0.95, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'REINSCRIPCION_NO_APROBADA'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Reinscripcion de unidad no aprobada del primer anio'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Regularizacion condicionada por cierre anual', 'Patron para solicitudes donde la regularizacion depende del cierre anual y de mantener control sobre la carga futura.', 0.83, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave IN ('REGULARIZACION_CIERRE', 'AVANCE_ANUAL')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Regularizacion condicionada por cierre anual'
  )
LIMIT 1;

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Caso con informacion insuficiente para dictamen', 'Patron para tickets sin descripcion util, sin UD precisas o sin resolucion academica ejecutable.', 0.97, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'PENDIENTE_INFORMACION'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.id_tipo_caso = t.id
        AND r.nombre_patron = 'Caso con informacion insuficiente para dictamen'
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'PRIORIZAR_PENDIENTES'
WHERE p.nombre_patron = 'Caso con rezago por acumulacion de pendientes'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'PRIORIZAR_OBLIGATORIAS'
WHERE p.nombre_patron IN ('Caso con rezago por acumulacion de pendientes', 'Unidad didactica pendiente obligatoria')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'VALIDAR_AVANCE_ANUAL'
WHERE p.nombre_patron IN ('Aclaracion de avance frente a otras restricciones', 'Regularizacion condicionada por cierre anual')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'DIFERENCIAR_OMISION_NO_ACREDITACION'
WHERE p.nombre_patron IN ('Aclaracion de avance frente a otras restricciones', 'Reinscripcion de unidad no aprobada del primer anio')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'NO_HABILITAR_MANUALMENTE'
WHERE p.nombre_patron = 'Reinscripcion de unidad no aprobada del primer anio'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'COMPLETAR_DICTAMEN_DAEAE'
WHERE p.nombre_patron = 'Caso con informacion insuficiente para dictamen'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'INSCRIBIR_SOLO_HABILITADAS'
WHERE p.nombre_patron IN ('Caso con rezago por acumulacion de pendientes', 'Reinscripcion de unidad no aprobada del primer anio')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'APLICAR_AJUSTE_CARGA'
WHERE p.nombre_patron IN ('Unidad didactica pendiente obligatoria', 'Regularizacion condicionada por cierre anual')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'COMPLETAR_EXPEDIENTE'
WHERE p.nombre_patron = 'Caso con informacion insuficiente para dictamen'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'REVALORAR_DICTAMEN'
WHERE p.nombre_patron IN ('Aclaracion de avance frente a otras restricciones', 'Caso con informacion insuficiente para dictamen')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'COMUNICAR_RESTRICCION'
WHERE p.nombre_patron IN ('Caso con rezago por acumulacion de pendientes', 'Reinscripcion de unidad no aprobada del primer anio', 'Regularizacion condicionada por cierre anual')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_REZAGO_V1'
WHERE p.nombre_patron IN ('Caso con rezago por acumulacion de pendientes', 'Unidad didactica pendiente obligatoria')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_REZAGO_V1'
WHERE p.nombre_patron IN ('Caso con rezago por acumulacion de pendientes', 'Unidad didactica pendiente obligatoria')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_AVANCE_V1'
WHERE p.nombre_patron IN ('Aclaracion de avance frente a otras restricciones', 'Regularizacion condicionada por cierre anual')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_AVANCE_V1'
WHERE p.nombre_patron = 'Regularizacion condicionada por cierre anual'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_INFO_V1'
WHERE p.nombre_patron = 'Caso con informacion insuficiente para dictamen'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_INFO_V1'
WHERE p.nombre_patron = 'Caso con informacion insuficiente para dictamen'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );
