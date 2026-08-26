ALTER TABLE cat_reglas_inscripcion
  ADD COLUMN IF NOT EXISTS nombre VARCHAR(200) NULL AFTER clave,
  ADD COLUMN IF NOT EXISTS categoria VARCHAR(50) NOT NULL DEFAULT 'GENERAL' AFTER descripcion,
  ADD COLUMN IF NOT EXISTS tipo_regla VARCHAR(20) NOT NULL DEFAULT 'RESTRICCION' AFTER categoria;

UPDATE cat_reglas_inscripcion
SET nombre = 'Autorización por límite de reprobaciones',
    categoria = 'GENERAL',
    tipo_regla = 'PERMISO'
WHERE clave = 'PERMITE_INSCRIPCION_LIMITE_REPROBADAS';

ALTER TABLE cat_reglas_inscripcion
  MODIFY nombre VARCHAR(200) NOT NULL;

CREATE TABLE IF NOT EXISTS cat_parametros_regla_inscripcion (
  clave_regla VARCHAR(100) NOT NULL,
  clave_parametro VARCHAR(100) NOT NULL,
  nombre VARCHAR(200) NOT NULL,
  descripcion VARCHAR(500) NOT NULL,
  tipo_dato VARCHAR(20) NOT NULL,
  valor_default VARCHAR(100) NOT NULL,
  orden INT NOT NULL DEFAULT 0,
  PRIMARY KEY (clave_regla, clave_parametro),
  CONSTRAINT fk_parametro_regla_inscripcion
    FOREIGN KEY (clave_regla) REFERENCES cat_reglas_inscripcion (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS rel_regla_inscripcion_plan (
  clave_regla VARCHAR(100) NOT NULL,
  id_plan INT NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 0,
  usuario_modifico BIGINT NOT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (clave_regla, id_plan),
  CONSTRAINT fk_regla_plan_regla
    FOREIGN KEY (clave_regla) REFERENCES cat_reglas_inscripcion (clave),
  CONSTRAINT fk_regla_plan_plan
    FOREIGN KEY (id_plan) REFERENCES tbl_planes (id_plan)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS rel_valor_parametro_regla_plan (
  clave_regla VARCHAR(100) NOT NULL,
  id_plan INT NOT NULL,
  clave_parametro VARCHAR(100) NOT NULL,
  valor VARCHAR(100) NOT NULL,
  usuario_modifico BIGINT NOT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (clave_regla, id_plan, clave_parametro),
  CONSTRAINT fk_valor_regla_plan
    FOREIGN KEY (clave_regla, id_plan)
    REFERENCES rel_regla_inscripcion_plan (clave_regla, id_plan),
  CONSTRAINT fk_valor_parametro
    FOREIGN KEY (clave_regla, clave_parametro)
    REFERENCES cat_parametros_regla_inscripcion (clave_regla, clave_parametro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO cat_reglas_inscripcion
  (clave, nombre, descripcion, categoria, tipo_regla, activo, usuario_modifico,
   fecha_registro, fecha_actualizacion)
VALUES
  ('CARGA_NUEVO_INGRESO',
   'Carga académica de nuevo ingreso',
   'Configura por plan la oferta y la selección requerida para estudiantes regulares de nuevo ingreso.',
   'NUEVO_INGRESO', 'RESTRICCION', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre), descripcion = VALUES(descripcion),
  categoria = VALUES(categoria), tipo_regla = VALUES(tipo_regla);

INSERT INTO cat_parametros_regla_inscripcion
  (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
VALUES
  ('CARGA_NUEVO_INGRESO', 'OBLIGATORIAS_REQUERIDAS',
   'Obligatorias requeridas',
   'Cantidad exacta de unidades didácticas obligatorias que debe seleccionar el estudiante de nuevo ingreso para el plan.',
   'ENTERO', '4', 1),
  ('CARGA_NUEVO_INGRESO', 'OPTATIVAS_REQUERIDAS',
   'Optativas requeridas',
   'Cantidad exacta de unidades didácticas optativas que debe seleccionar el estudiante de nuevo ingreso para el plan.',
   'ENTERO', '2', 2),
  ('CARGA_NUEVO_INGRESO', 'RESTRINGIR_PRIMER_SEMESTRE',
   'Mostrar sólo primer semestre',
   'Al activarse, oculta al estudiante de nuevo ingreso las unidades didácticas ofertadas para semestres distintos del primero.',
   'BOOLEANO', '1', 3),
  ('CARGA_NUEVO_INGRESO', 'MOSTRAR_SEGUNDO_SIN_OFERTA_PRIMERO',
   'Mostrar semestre 2 si no hay oferta del semestre 1',
   'Al activarse, muestra las unidades didácticas del Semestre 2 solamente cuando no existe oferta disponible del Semestre 1 y está activa la restricción de primer semestre.',
   'BOOLEANO', '1', 4),
  ('CARGA_NUEVO_INGRESO', 'AUTOSELECCIONAR_OBLIGATORIAS',
   'Autoseleccionar obligatorias',
   'Al activarse, las unidades didácticas obligatorias disponibles aparecen seleccionadas al cargar la inscripción.',
   'BOOLEANO', '1', 5),
  ('CARGA_NUEVO_INGRESO', 'BLOQUEAR_OBLIGATORIAS',
   'Impedir desmarcar obligatorias',
   'Al activarse, el estudiante no puede retirar la selección de las unidades didácticas obligatorias.',
   'BOOLEANO', '1', 6)
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre), descripcion = VALUES(descripcion),
  tipo_dato = VALUES(tipo_dato), valor_default = VALUES(valor_default), orden = VALUES(orden);
