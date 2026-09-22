-- Conciliación idempotente de catálogos requeridos por el motor V2.
-- Ejecutar después de los scripts 24, 25 y 27. No elimina ni modifica casos existentes.

INSERT INTO cat_tipo_caso_academico (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('UNIDADES_NO_ACREDITADAS', 'Unidades didácticas no acreditadas', 'Casos donde varias UD no acreditadas condicionan la trayectoria.', 1, 1),
    ('OMISION_DOCUMENTADA', 'Omisión documentada', 'Casos con soporte para tratar una ausencia de registro como omisión.', 1, 1),
    ('CONTINUIDAD_MISMO_ANIO', 'Continuidad dentro del mismo año', 'Casos de continuidad que no corresponden a avance improcedente.', 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), activo = VALUES(activo), usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_motivo_restriccion (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('ACUMULACION_NO_ACREDITADAS', 'Acumulación de no acreditadas', 'La trayectoria mantiene varias UD no acreditadas que reducen la libertad de selección.', 1, 1),
    ('PRIMER_ANIO_INCOMPLETO', 'Primer año incompleto', 'No procede habilitar UD del segundo año mientras el primero permanezca incompleto.', 1, 1),
    ('SOLO_OPTATIVAS_HABILITADAS', 'Solo optativas habilitadas', 'El sistema solo permite optativas o pendientes compatibles con la trayectoria.', 1, 1),
    ('OMISION_DOCUMENTADA_MOTIVO', 'Omisión documentada', 'Existe evidencia documental suficiente para tratar el caso como omisión.', 1, 1),
    ('MISMO_ANIO_ACADEMICO', 'Continuidad en el mismo año académico', 'La solicitud corresponde al mismo año académico.', 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), activo = VALUES(activo), usuario_modifico = VALUES(usuario_modifico);

INSERT INTO cat_accion_operativa (clave, nombre, descripcion, activo, usuario_modifico)
VALUES
    ('PRIORIZAR_REGULARIZACION', 'Priorizar regularización', 'Priorizar las UD no acreditadas antes de ampliar la carga académica.', 1, 1),
    ('MANTENER_RESTRICCION', 'Mantener restricción', 'No ejecutar cambios fuera de las selecciones permitidas por el sistema.', 1, 1),
    ('PERMITIR_CONTINUIDAD_MISMO_ANIO', 'Permitir continuidad del mismo año', 'Orientar selección compatible dentro del mismo año académico.', 1, 1),
    ('REGISTRAR_SOLO_OPTATIVAS_HABILITADAS', 'Registrar solo optativas habilitadas', 'Registrar únicamente optativas o pendientes habilitadas.', 1, 1),
    ('APLICAR_AJUSTE_POR_OMISION', 'Aplicar ajuste por omisión', 'Aplicar ajuste cuando la omisión esté documentada.', 1, 1),
    ('DAR_SEGUIMIENTO', 'Dar seguimiento', 'Monitorear el caso durante cursamiento.', 1, 1),
    ('ESCALAR_REVISION', 'Escalar revisión', 'Canalizar el caso a revisión académica.', 1, 1),
    ('VALIDAR_SELECCION', 'Validar selección', 'Validar la selección final en periodo activo.', 1, 1),
    ('SOLICITAR_INFORMACION', 'Solicitar información', 'Solicitar información faltante del expediente.', 1, 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), descripcion = VALUES(descripcion), activo = VALUES(activo), usuario_modifico = VALUES(usuario_modifico);
