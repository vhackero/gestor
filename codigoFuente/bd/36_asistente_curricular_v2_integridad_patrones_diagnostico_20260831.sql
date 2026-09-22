-- Estabilización del motor V2: ejecutar después de 34 y 35.
-- No elimina casos; completa el catálogo, la evidencia persistente y la cobertura de patrones.

INSERT INTO cat_motivo_restriccion (clave, nombre, descripcion, activo, usuario_modifico)
VALUES ('SIN_RESTRICCION', 'Sin restricción académica',
        'La trayectoria no presenta pendientes o bloqueos académicos activos.', 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), activo = VALUES(activo);

-- MySQL 5.7 no soporta ADD COLUMN IF NOT EXISTS; se usa information_schema para conservar idempotencia.
SET @db := DATABASE();
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN restriccion_dominante VARCHAR(50) NULL AFTER resumen_motor';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='restriccion_dominante');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN motivo_bloqueo_principal TEXT NULL AFTER restriccion_dominante';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='motivo_bloqueo_principal');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN comparativo_avance_restricciones TEXT NULL AFTER motivo_bloqueo_principal';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='comparativo_avance_restricciones');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN interpretacion_omisiones TEXT NULL AFTER comparativo_avance_restricciones';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='interpretacion_omisiones');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN total_no_acreditadas INT NOT NULL DEFAULT 0 AFTER interpretacion_omisiones';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='total_no_acreditadas');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN total_omisiones INT NOT NULL DEFAULT 0 AFTER total_no_acreditadas';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='total_omisiones');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN total_bloqueadas INT NOT NULL DEFAULT 0 AFTER total_omisiones';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='total_bloqueadas');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @add_column := 'ALTER TABLE tbl_caso_diagnostico ADD COLUMN total_pendientes_criticas INT NOT NULL DEFAULT 0 AFTER total_bloqueadas';
SET @exists := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='tbl_caso_diagnostico' AND COLUMN_NAME='total_pendientes_criticas');
SET @sql := IF(@exists=0, @add_column, 'SELECT 1'); PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Se conserva el patrón histórico, pero se desactiva para que no compita con el patrón V2 completo de seriación.
UPDATE rel_patron_caso_academico p
JOIN cat_tipo_caso_academico t ON t.id = p.id_tipo_caso
SET p.activo = 0, p.fecha_actualizacion = NOW(), p.usuario_modifico = 1
WHERE t.clave = 'SERIACION' AND p.nombre_patron = 'Bloqueo por seriación activa';

-- Mensajes de ejemplo no deben competir con el contenido institucional aprobado.
DELETE rpm
FROM rel_patron_mensaje_contextual rpm
JOIN cat_mensaje_institucional_contextual m ON m.id = rpm.id_mensaje_contextual
WHERE m.clave LIKE 'EST_INS_EJEMPLO_%';

-- Todo tipo activo debe tener por lo menos un patrón activo.
INSERT INTO rel_patron_caso_academico (id_tipo_caso, nombre_patron, descripcion, confianza_base, activo, usuario_modifico)
SELECT t.id, CONCAT('Patrón V2 ', t.clave),
       CONCAT('Patrón base para resolver el tipo de caso ', t.clave, '.'), 0.90, 1, 1
FROM cat_tipo_caso_academico t
WHERE t.activo = 1
  AND NOT EXISTS (SELECT 1 FROM rel_patron_caso_academico p WHERE p.id_tipo_caso=t.id AND p.activo=1);

-- Cada patrón activo obtiene criterio y acción consistentes con su tipo de caso.
INSERT INTO rel_patron_criterio_operativo (id_patron_caso, id_criterio_operativo)
SELECT p.id, c.id
FROM rel_patron_caso_academico p
JOIN cat_tipo_caso_academico t ON t.id=p.id_tipo_caso
JOIN cat_criterio_operativo c ON c.clave = CASE
    WHEN t.clave='SERIACION' THEN 'RESPETAR_SERIACION'
    WHEN t.clave IN ('OFERTA_NO_DISPONIBLE','REINCORPORACION') THEN 'VALIDAR_OFERTA'
    WHEN t.clave='CARGA_LIMITADA' THEN 'VALIDAR_CARGA_MAXIMA'
    WHEN t.clave IN ('ACLARACION_AVANCE','PENDIENTE_INFORMACION','OMISION_DOCUMENTADA') THEN 'ESCALAR_EVIDENCIA'
    ELSE 'PRIORIZAR_PENDIENTES' END
WHERE p.activo=1
  AND NOT EXISTS (SELECT 1 FROM rel_patron_criterio_operativo r WHERE r.id_patron_caso=p.id AND r.id_criterio_operativo=c.id);

INSERT INTO rel_patron_accion_operativa (id_patron_caso, id_accion_operativa)
SELECT p.id, a.id
FROM rel_patron_caso_academico p
JOIN cat_tipo_caso_academico t ON t.id=p.id_tipo_caso
JOIN cat_accion_operativa a ON a.clave = CASE
    WHEN t.clave IN ('SERIACION','AVANCE_ANUAL','REINSCRIPCION_NO_APROBADA') THEN 'MANTENER_RESTRICCION'
    WHEN t.clave IN ('REZAGO','UNIDADES_NO_ACREDITADAS') THEN 'PRIORIZAR_REGULARIZACION'
    WHEN t.clave='OMISION_DOCUMENTADA' THEN 'APLICAR_AJUSTE_POR_OMISION'
    WHEN t.clave IN ('OMISION_REGISTRO','CARGA_LIMITADA') THEN 'VALIDAR_SELECCION'
    WHEN t.clave='CONTINUIDAD_MISMO_ANIO' THEN 'PERMITIR_CONTINUIDAD_MISMO_ANIO'
    WHEN t.clave IN ('ACLARACION_AVANCE') THEN 'ESCALAR_REVISION'
    WHEN t.clave='PENDIENTE_INFORMACION' THEN 'SOLICITAR_INFORMACION'
    ELSE 'DAR_SEGUIMIENTO' END
WHERE p.activo=1
  AND NOT EXISTS (SELECT 1 FROM rel_patron_accion_operativa r WHERE r.id_patron_caso=p.id AND r.id_accion_operativa=a.id);

-- Cobertura mínima de mensajes: sólo se crean cuando el patrón no tiene texto para ese perfil y periodo.
INSERT INTO cat_mensaje_institucional_contextual
    (clave, perfil, periodo_operativo, titulo, mensaje, tipo, activo, usuario_modifico)
SELECT CONCAT('V2P_', p.id, '_', x.perfil_corto, '_', x.periodo_corto), x.perfil, x.periodo,
       t.nombre,
       CASE t.clave
         WHEN 'TRAYECTORIA_REGULAR' THEN 'La trayectoria no presenta unidades didácticas pendientes que limiten el avance actual.'
         WHEN 'SERIACION' THEN 'Existen unidades didácticas subsecuentes condicionadas por una unidad antecedente pendiente.'
         WHEN 'UNIDADES_NO_ACREDITADAS' THEN 'Las unidades didácticas no acreditadas deben priorizarse cuando cuenten con oferta académica.'
         WHEN 'REINCORPORACION' THEN 'Revisa la oferta vigente para reactivar tu trayectoria académica de forma continua.'
         WHEN 'PENDIENTE_INFORMACION' THEN 'Se requiere información adicional para completar la valoración académica.'
         ELSE CONCAT('El caso se atiende conforme a las reglas académicas aplicables a: ', t.nombre, '.') END,
       'ORIENTACION', 1, 1
FROM rel_patron_caso_academico p
JOIN cat_tipo_caso_academico t ON t.id=p.id_tipo_caso
JOIN (
    SELECT 'ESTUDIANTE' perfil, 'E' perfil_corto, 'INSCRIPCION' periodo, 'I' periodo_corto
    UNION ALL SELECT 'ESTUDIANTE','E','CURSAMIENTO','C'
    UNION ALL SELECT 'GESTOR','G','INSCRIPCION','I'
    UNION ALL SELECT 'GESTOR','G','CURSAMIENTO','C'
) x
WHERE p.activo=1
  AND NOT EXISTS (
      SELECT 1 FROM rel_patron_mensaje_contextual r
      JOIN cat_mensaje_institucional_contextual m ON m.id=r.id_mensaje_contextual
      WHERE r.id_patron_caso=p.id AND r.perfil=x.perfil
        AND m.activo=1 AND m.perfil=x.perfil AND m.periodo_operativo=x.periodo);

INSERT INTO rel_patron_mensaje_contextual (id_patron_caso, id_mensaje_contextual, perfil)
SELECT p.id, m.id, m.perfil
FROM rel_patron_caso_academico p
JOIN cat_mensaje_institucional_contextual m ON m.clave IN (
    CONCAT('V2P_', p.id, '_E_I'), CONCAT('V2P_', p.id, '_E_C'),
    CONCAT('V2P_', p.id, '_G_I'), CONCAT('V2P_', p.id, '_G_C'))
WHERE p.activo=1
  AND NOT EXISTS (SELECT 1 FROM rel_patron_mensaje_contextual r
                  WHERE r.id_patron_caso=p.id AND r.id_mensaje_contextual=m.id AND r.perfil=m.perfil);
