-- Carga ampliada de base de conocimiento V2
-- Fuente funcional principal:
--   casos_avance_anual_2026_2 (2).xlsx
-- Patrones observados:
--   - Caso con rezago
--   - Reinscripción de unidad no aprobada
--   - Unidad didáctica pendiente
--   - Regularización cierre anual
--   - Casos con información insuficiente
--   - Casos viables por omisión documentada o continuidad dentro del mismo año

INSERT INTO cat_tipo_caso_academico (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('UNIDADES_NO_ACREDITADAS', 'Unidades didácticas no acreditadas', 'Casos donde varias UD no acreditadas condicionan la trayectoria activa.', 1, 1),
    ('OMISION_DOCUMENTADA', 'Omisión documentada', 'Casos donde existe soporte documental para tratar la ausencia de registro como omisión y no como no acreditación.', 1, 1),
    ('CONTINUIDAD_MISMO_ANIO', 'Continuidad dentro del mismo año', 'Casos donde la solicitud corresponde al mismo año académico y debe distinguirse de un avance indebido a un año superior.', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_motivo_restriccion (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('ACUMULACION_NO_ACREDITADAS', 'Acumulación de no acreditadas', 'La trayectoria mantiene varias UD no acreditadas que reducen la libertad de selección.', 1, 1),
    ('PRIMER_ANIO_INCOMPLETO', 'Primer año incompleto', 'No procede habilitar UD del segundo año mientras el primer año permanezca incompleto.', 1, 1),
    ('SOLO_OPTATIVAS_HABILITADAS', 'Solo optativas habilitadas', 'El sistema solo permite optativas o pendientes compatibles con la trayectoria.', 1, 1),
    ('OMISION_DOCUMENTADA_MOTIVO', 'Omisión documentada', 'Existe evidencia documental suficiente para tratar el caso como omisión y permitir ajuste.', 1, 1),
    ('SIN_DICTAMEN_ACADEMICO', 'Sin dictamen académico', 'No existe resolución DAEAE/DD o información suficiente para ejecutar el caso.', 1, 1),
    ('MISMO_ANIO_ACADEMICO', 'Continuidad en el mismo año académico', 'La solicitud corresponde al mismo año y debe analizarse con carga, pendientes y oferta, no como avance a otro año.', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_criterio_operativo (clave, nombre, descripcion, prioridad, activo, usuario_modifico)
VALUES
    ('DISTINGUIR_MISMO_ANIO', 'Distinguir mismo anio academico', 'Separar continuidad del mismo anio academico contra avance improcedente a un anio superior.', 12, 1, 1),
    ('NO_HABILITAR_SEGUNDO_ANIO', 'No habilitar segundo anio con primero incompleto', 'No abrir obligatorias del segundo anio cuando el primero esta incompleto por no acreditadas u omisiones no aclaradas.', 18, 1, 1),
    ('LIMITAR_A_OPTATIVAS_HABILITADAS', 'Limitar a optativas habilitadas', 'Permitir solo optativas o pendientes compatibles con la restriccion vigente del sistema.', 28, 1, 1),
    ('VALIDAR_OMISION_DOCUMENTADA', 'Validar omision documentada', 'Aplicar ajuste solo si existe soporte documental suficiente para tratar el caso como omision.', 32, 1, 1),
    ('EXIGIR_DICTAMEN_COMPLETO', 'Exigir dictamen completo', 'No ejecutar movimientos operativos sin descripcion del caso, UD involucradas y resolucion academica usable.', 48, 1, 1),
    ('RECHAZAR_DESBLOQUEO_MANUAL', 'Rechazar desbloqueo manual', 'No liberar manualmente UD bloqueadas cuando el sistema mantiene la restriccion por regla academica.', 58, 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    prioridad = VALUES(prioridad),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_accion_operativa (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('MANTENER_RESTRICCION', 'Mantener restriccion', 'No ejecutar cambios fuera de la selecciones que el sistema ya permite.', 1, 1),
    ('PERMITIR_CONTINUIDAD_MISMO_ANIO', 'Permitir continuidad del mismo anio', 'Orientar la seleccion dentro del mismo anio academico, respetando carga, oferta y pendientes.', 1, 1),
    ('REGISTRAR_SOLO_OPTATIVAS_HABILITADAS', 'Registrar solo optativas habilitadas', 'Registrar solo las optativas o pendientes que el sistema tenga disponibles para el caso.', 1, 1),
    ('APLICAR_AJUSTE_POR_OMISION', 'Aplicar ajuste por omision documentada', 'Permitir ajuste solo cuando la omision este sustentada documentalmente.', 1, 1),
    ('DEVOLVER_PARA_REVALORACION', 'Devolver para revaloracion', 'Canalizar el caso a nueva valoracion cuando el dictamen o la solicitud no son consistentes.', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_mensaje_institucional_contextual (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
VALUES
    ('EST_INS_REZAGO_NO_ACREDITADAS_V2', 'ESTUDIANTE', 'INSCRIPCION', 'Rezago con no acreditadas', 'Tu trayectoria presenta UD no acreditadas que deben atenderse primero. En este periodo no conviene intentar abrir unidades de un anio superior si el anterior sigue incompleto.', 'ORIENTACION', 1, 1),
    ('EST_CUR_REZAGO_NO_ACREDITADAS_V2', 'ESTUDIANTE', 'CURSAMIENTO', 'Seguimiento de no acreditadas', 'Durante cursamiento enfocate en cerrar tus UD activas y prepara el siguiente periodo con base en las no acreditadas y la carga que el sistema realmente permita.', 'ORIENTACION', 1, 1),
    ('EST_INS_SOLO_OPTATIVAS_V2', 'ESTUDIANTE', 'INSCRIPCION', 'Solo optativas habilitadas', 'Aunque esperes obligatorias, en tu situacion actual el sistema puede limitarte a optativas o pendientes compatibles. Esa restriccion no se corrige con un desbloqueo manual.', 'RESUMEN', 1, 1),
    ('EST_INS_MISMO_ANIO_V2', 'ESTUDIANTE', 'INSCRIPCION', 'Continuidad dentro del mismo anio', 'Si tu solicitud corresponde al mismo anio academico, el analisis correcto no es solo avance anual: tambien cuentan tus pendientes, la carga maxima y la oferta disponible.', 'ORIENTACION', 1, 1),
    ('EST_INS_OMISION_DOC_V2', 'ESTUDIANTE', 'INSCRIPCION', 'Caso por omision documentada', 'Si existe evidencia institucional de omision de registro, el caso puede corregirse como ajuste documentado y no como no acreditacion.', 'ORIENTACION', 1, 1),
    ('EST_INS_SIN_DICTAMEN_V2', 'ESTUDIANTE', 'INSCRIPCION', 'Informacion insuficiente para resolver', 'Aun no hay elementos suficientes para resolver tu caso. Se requiere descripcion clara, UD involucradas y resolucion academica para evitar ajustes incorrectos.', 'RESUMEN', 1, 1),
    ('GES_INS_REZAGO_NO_ACREDITADAS_V2', 'GESTOR', 'INSCRIPCION', 'Rezago por no acreditadas', 'Si el primer anio permanece incompleto por UD no acreditadas, no procede habilitar obligatorias del segundo anio. La respuesta debe centrarse en prioridad, carga y restriccion activa.', 'ORIENTACION', 1, 1),
    ('GES_CUR_REZAGO_NO_ACREDITADAS_V2', 'GESTOR', 'CURSAMIENTO', 'Seguimiento de rezago con no acreditadas', 'Documenta cuantas UD no acreditadas explican la restriccion y comunica el siguiente paso sin prometer desbloqueos manuales.', 'ORIENTACION', 1, 1),
    ('GES_INS_SOLO_OPTATIVAS_V2', 'GESTOR', 'INSCRIPCION', 'Solo optativas habilitadas por sistema', 'Cuando el sistema solo habilita optativas, no debe forzarse una apertura manual de obligatorias salvo resolucion academica expresa y consistente.', 'RESUMEN', 1, 1),
    ('GES_INS_MISMO_ANIO_V2', 'GESTOR', 'INSCRIPCION', 'Continuidad del mismo anio academico', 'Distingue continuidad dentro del mismo anio contra avance a otro anio. Aun siendo continuidad, deben mantenerse carga, oferta y restriccion por pendientes.', 'ORIENTACION', 1, 1),
    ('GES_INS_OMISION_DOC_V2', 'GESTOR', 'INSCRIPCION', 'Omision documentada viable', 'Si hay soporte documental suficiente, el caso puede tratarse como omision y ajustarse sin catalogarlo como no acreditacion.', 'ORIENTACION', 1, 1),
    ('GES_INS_SIN_DICTAMEN_V2', 'GESTOR', 'INSCRIPCION', 'Caso sin dictamen utilizable', 'No ejecutar ajustes mientras falten descripcion del caso, UD involucradas, resolucion DAEAE/DD o evidencia minima para sustentar la accion.', 'RESUMEN', 1, 1),
    ('GES_INS_CIERRE_ANUAL_V2', 'GESTOR', 'INSCRIPCION', 'Regularizacion condicionada por cierre anual', 'En casos de cierre anual la recomendacion debe aclarar si la seleccion es viable por avance, por carga o por regularizacion acotada, sin mezclar reglas.', 'ORIENTACION', 1, 1)
ON DUPLICATE KEY UPDATE
    perfil = VALUES(perfil),
    periodo_operativo = VALUES(periodo_operativo),
    titulo = VALUES(titulo),
    mensaje = VALUES(mensaje),
    tipo = VALUES(tipo),
    activo = VALUES(activo),
    usuario_modifico = VALUES(usuario_modifico);

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Rezago con no acreditadas y restriccion de avance real', 'Patron derivado de tickets donde varias UD no acreditadas impiden avanzar con normalidad y fuerzan una orientacion de regularizacion.', 0.96, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave IN ('REZAGO', 'UNIDADES_NO_ACREDITADAS')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Rezago con no acreditadas y restriccion de avance real'
  )
LIMIT 1;

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Continuidad del mismo anio con carga restringida', 'Patron para casos donde la solicitud corresponde al mismo anio academico pero la seleccion sigue limitada por pendientes, carga y oferta.', 0.91, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave IN ('CONTINUIDAD_MISMO_ANIO', 'AVANCE_ANUAL')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Continuidad del mismo anio con carga restringida'
  )
LIMIT 1;

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Primer anio incompleto impide abrir segundo anio', 'Patron para casos como reinscripcion de unidad no aprobada donde el primer anio incompleto bloquea obligatorias de un anio superior.', 0.98, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'REINSCRIPCION_NO_APROBADA'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Primer anio incompleto impide abrir segundo anio'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Solo optativas habilitadas por rezago acumulado', 'Patron para casos donde la trayectoria permite solo optativas o pendientes compatibles y no procede desbloqueo manual de obligatorias.', 0.94, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave IN ('REZAGO', 'CARGA_LIMITADA')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Solo optativas habilitadas por rezago acumulado'
  )
LIMIT 1;

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Omision documentada viable con ajuste', 'Patron para casos donde la trayectoria puede ajustarse porque existe evidencia documental suficiente de una omision.', 0.90, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'OMISION_DOCUMENTADA'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Omision documentada viable con ajuste'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Solicitud sin dictamen ni detalle de UD', 'Patron para casos donde la solicitud no trae resolucion academica suficiente ni detalle util de las UD pedidas.', 0.99, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave = 'PENDIENTE_INFORMACION'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Solicitud sin dictamen ni detalle de UD'
  );

INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, 'Regularizacion condicionada por cierre anual y seriacion', 'Patron para solicitudes donde la regularizacion depende de cierre anual y debe leerse junto con seriacion y carga.', 0.88, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.clave IN ('REGULARIZACION_CIERRE', 'SERIACION')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_caso_academico r
      WHERE r.nombre_patron = 'Regularizacion condicionada por cierre anual y seriacion'
  )
LIMIT 1;

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'PRIORIZAR_OBLIGATORIAS'
WHERE p.nombre_patron IN ('Rezago con no acreditadas y restriccion de avance real', 'Primer anio incompleto impide abrir segundo anio')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'DISTINGUIR_MISMO_ANIO'
WHERE p.nombre_patron = 'Continuidad del mismo anio con carga restringida'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'NO_HABILITAR_SEGUNDO_ANIO'
WHERE p.nombre_patron = 'Primer anio incompleto impide abrir segundo anio'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'LIMITAR_A_OPTATIVAS_HABILITADAS'
WHERE p.nombre_patron = 'Solo optativas habilitadas por rezago acumulado'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'VALIDAR_OMISION_DOCUMENTADA'
WHERE p.nombre_patron = 'Omision documentada viable con ajuste'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'EXIGIR_DICTAMEN_COMPLETO'
WHERE p.nombre_patron = 'Solicitud sin dictamen ni detalle de UD'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_criterio_operativo c ON c.clave = 'RECHAZAR_DESBLOQUEO_MANUAL'
WHERE p.nombre_patron IN ('Solo optativas habilitadas por rezago acumulado', 'Primer anio incompleto impide abrir segundo anio')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_criterio_operativo r
      WHERE r.id_patron_caso = p.id AND r.id_criterio_operativo = c.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'MANTENER_RESTRICCION'
WHERE p.nombre_patron IN ('Rezago con no acreditadas y restriccion de avance real', 'Primer anio incompleto impide abrir segundo anio')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'PERMITIR_CONTINUIDAD_MISMO_ANIO'
WHERE p.nombre_patron = 'Continuidad del mismo anio con carga restringida'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'REGISTRAR_SOLO_OPTATIVAS_HABILITADAS'
WHERE p.nombre_patron = 'Solo optativas habilitadas por rezago acumulado'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'APLICAR_AJUSTE_POR_OMISION'
WHERE p.nombre_patron = 'Omision documentada viable con ajuste'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_accion_operativa a ON a.clave = 'DEVOLVER_PARA_REVALORACION'
WHERE p.nombre_patron IN ('Solicitud sin dictamen ni detalle de UD', 'Regularizacion condicionada por cierre anual y seriacion')
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_accion_operativa r
      WHERE r.id_patron_caso = p.id AND r.id_accion_operativa = a.id
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_REZAGO_NO_ACREDITADAS_V2'
WHERE p.nombre_patron = 'Rezago con no acreditadas y restriccion de avance real'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_REZAGO_NO_ACREDITADAS_V2'
WHERE p.nombre_patron = 'Rezago con no acreditadas y restriccion de avance real'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_MISMO_ANIO_V2'
WHERE p.nombre_patron = 'Continuidad del mismo anio con carga restringida'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_MISMO_ANIO_V2'
WHERE p.nombre_patron = 'Continuidad del mismo anio con carga restringida'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_SOLO_OPTATIVAS_V2'
WHERE p.nombre_patron = 'Solo optativas habilitadas por rezago acumulado'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_SOLO_OPTATIVAS_V2'
WHERE p.nombre_patron = 'Solo optativas habilitadas por rezago acumulado'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_OMISION_DOC_V2'
WHERE p.nombre_patron = 'Omision documentada viable con ajuste'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_OMISION_DOC_V2'
WHERE p.nombre_patron = 'Omision documentada viable con ajuste'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'EST_INS_SIN_DICTAMEN_V2'
WHERE p.nombre_patron = 'Solicitud sin dictamen ni detalle de UD'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_SIN_DICTAMEN_V2'
WHERE p.nombre_patron = 'Solicitud sin dictamen ni detalle de UD'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave = 'GES_INS_CIERRE_ANUAL_V2'
WHERE p.nombre_patron = 'Regularizacion condicionada por cierre anual y seriacion'
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      WHERE r.id_patron_caso = p.id AND r.id_mensaje_contextual = m.id AND r.perfil = m.perfil
  );
