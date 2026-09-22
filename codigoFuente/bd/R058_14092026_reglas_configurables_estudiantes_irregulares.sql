SET NAMES utf8mb4;
START TRANSACTION;

INSERT INTO cat_reglas_inscripcion (clave,nombre,descripcion,categoria,tipo_regla,activo,usuario_modifico)
VALUES ('CARGA_ESTUDIANTE_IRREGULAR','Carga académica de estudiantes irregulares',
'Evalúa en orden el avance anual, solo optativas reprobadas, reprobadas concentradas y recuperación restante.','IRREGULAR','RESTRICCION',1,1)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);

INSERT INTO cat_parametros_regla_inscripcion
(clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES
('CARGA_ESTUDIANTE_IRREGULAR','MAX_REPROBADAS_AVANCE_ANUAL','Máximo de reprobadas totales para avance anual','Aplica cuando existe al menos una obligatoria reprobada.','ENTERO','3',1),
('CARGA_ESTUDIANTE_IRREGULAR','MIN_REPROBADAS_MISMO_SEMESTRE','Restringir a un semestre a partir de reprobadas','Cuenta reprobadas de cualquier tipo concentradas en un mismo semestre ofertado.','ENTERO','5',2),
('CARGA_ESTUDIANTE_IRREGULAR','MIN_REPROBADAS_RESTRINGIR_OPTATIVAS','Restringir optativas a partir de reprobadas totales','Solo se evalúa en la ruta restante de recuperación.','ENTERO','6',3),
('CARGA_ESTUDIANTE_IRREGULAR','MIN_OPTATIVAS_REPROBADAS_RESTRINGIR','Restringir optativas a partir de optativas reprobadas','Es una condición alternativa al total de reprobadas; solo en la ruta restante.','ENTERO','3',4),
('CARGA_ESTUDIANTE_IRREGULAR','MINIMO_OBLIGATORIAS','Mínimo de obligatorias seleccionadas','Se exige cuando existen suficientes obligatorias disponibles. 0 desactiva este mínimo.','ENTERO','2',5),
('CARGA_ESTUDIANTE_IRREGULAR','PERMITIR_SEMESTRE_ADYACENTE','Permitir semestre complementario del mismo año','Aplica al avance anual y al rango de optativas restringidas.','BOOLEANO','1',6),
('CARGA_ESTUDIANTE_IRREGULAR','PERMITIR_OPTATIVAS_OTROS_SEMESTRES_AVANCE','Permitir optativas de otros semestres durante el avance anual','Solo aplica a la primera ruta, con pocas reprobadas y al menos una obligatoria.','BOOLEANO','1',7),
('CARGA_ESTUDIANTE_IRREGULAR','RESTRINGIR_OPTATIVAS_POR_REZAGO','Aplicar restricción de optativas en la ruta restante','Al alcanzar cualquiera de sus umbrales, limita incluso optativas reprobadas al semestre de referencia y su complementario permitido.','BOOLEANO','1',8),
('CARGA_ESTUDIANTE_IRREGULAR','MARCAR_REPROBADAS_OBLIGATORIAS','Exigir selección de obligatorias reprobadas','Marca y bloquea su desmarcado, hasta el máximo de carga irregular existente.','BOOLEANO','1',9),
('CARGA_ESTUDIANTE_IRREGULAR','MARCAR_REPROBADAS_OPTATIVAS','Exigir selección de optativas reprobadas','Marca y bloquea su desmarcado, compartiendo el máximo de carga irregular.','BOOLEANO','1',10),
('CARGA_ESTUDIANTE_IRREGULAR','MARCAR_OBLIGATORIAS_SEMESTRE_RESTRINGIDO','Exigir todas las obligatorias del semestre restringido','En la ruta de reprobadas concentradas, incluye también obligatorias pendientes que no están reprobadas.','BOOLEANO','1',11)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion),tipo_dato=VALUES(tipo_dato),orden=VALUES(orden);

COMMIT;
