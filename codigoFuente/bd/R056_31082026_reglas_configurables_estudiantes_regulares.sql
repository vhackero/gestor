SET NAMES utf8mb4;

START TRANSACTION;

INSERT INTO cat_reglas_inscripcion
    (clave, nombre, descripcion, categoria, tipo_regla, activo, usuario_modifico)
VALUES
    ('CARGA_ESTUDIANTE_REGULAR',
     'Carga académica de estudiantes regulares',
     'Configura por plan el avance anual, el semestre adyacente, la carga obligatoria mínima y el máximo de electivas.',
     'REGULAR', 'RESTRICCION', 1, 1),
    ('RESTRICCIONES_ACADEMICAS_GENERALES',
     'Restricciones académicas generales',
     'Configura las validaciones transversales de créditos, tramo final, rezagos seriados y optativas.',
     'GENERAL', 'RESTRICCION', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    categoria = VALUES(categoria),
    tipo_regla = VALUES(tipo_regla);

INSERT INTO cat_parametros_regla_inscripcion
    (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
VALUES
    ('CARGA_ESTUDIANTE_REGULAR', 'RESTRINGIR_AVANCE_ANUAL', 'Restringir por avance anual',
     'Muestra las unidades del primer semestre incompleto y, cuando se permite, de su semestre adyacente.', 'BOOLEANO', '1', 1),
    ('CARGA_ESTUDIANTE_REGULAR', 'PERMITIR_SEMESTRE_ADYACENTE', 'Permitir semestre adyacente',
     'Permite cursar el semestre complementario del mismo año académico.', 'BOOLEANO', '1', 2),
    ('CARGA_ESTUDIANTE_REGULAR', 'MINIMO_OBLIGATORIAS', 'Mínimo de obligatorias',
     'Cantidad mínima de unidades didácticas obligatorias cuando existe oferta suficiente.', 'ENTERO', '2', 3),
    ('CARGA_ESTUDIANTE_REGULAR', 'MAXIMO_ELECTIVAS_POR_PERIODO', 'Máximo de electivas por periodo',
     'Valor 0 conserva como máximo la cantidad de espacios electivos del plan; un valor positivo limita esa cantidad.', 'ENTERO', '0', 4),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'PORCENTAJE_MINIMO_TRAMO_FINAL', 'Porcentaje mínimo del tramo final',
     'Porcentaje de créditos acreditados requerido para seleccionar unidades del tramo final.', 'ENTERO', '50', 1),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'SEMESTRE_INICIO_TRAMO_FINAL', 'Inicio del tramo final',
     'Primer semestre al que se aplica el porcentaje mínimo de créditos.', 'ENTERO', '7', 2),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'VALIDAR_REZAGOS_SERIADOS', 'Validar rezagos seriados',
     'Impide seleccionar el semestre destino cuando existen unidades seriadas reprobadas en el rango configurado.', 'BOOLEANO', '1', 3),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'SEMESTRE_DESTINO_REZAGOS', 'Semestre destino de rezagos',
     'Semestre cuya selección exige no tener rezagos seriados en el rango configurado.', 'ENTERO', '8', 4),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'SEMESTRE_INICIAL_ANTECEDENTES', 'Inicio del rango de antecedentes',
     'Primer semestre que se revisa para localizar rezagos seriados.', 'ENTERO', '3', 5),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'SEMESTRE_FINAL_ANTECEDENTES', 'Fin del rango de antecedentes',
     'Último semestre que se revisa para localizar rezagos seriados.', 'ENTERO', '6', 6),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'MAXIMO_OPTATIVAS_POR_BLOQUE', 'Máximo de optativas por bloque',
     'Cantidad máxima de unidades optativas que pueden seleccionarse en el mismo bloque y semestre.', 'ENTERO', '1', 7),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'OPTATIVAS_APROBADAS_PARA_OPCIONALES', 'Optativas para habilitar opcionales',
     'Cantidad de optativas aprobadas en el semestre necesaria para marcar las siguientes como opcionales.', 'ENTERO', '1', 8),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'IMPEDIR_CLAVE_OPTATIVA_REPETIDA', 'Impedir clave optativa repetida',
     'Evita seleccionar dos unidades optativas con la misma clave.', 'BOOLEANO', '1', 9)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    tipo_dato = VALUES(tipo_dato),
    valor_default = VALUES(valor_default),
    orden = VALUES(orden);

COMMIT;
