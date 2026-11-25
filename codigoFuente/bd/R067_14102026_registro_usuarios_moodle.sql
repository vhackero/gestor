-- R031: registro por plataforma y filtro opcional de plan. No crea tablas ni registra usuarios.
INSERT INTO tbl_funcionalidades(clave,id_funcionalidad_padre,descripcion,activo,usuario_modifico)
SELECT 'USU_MOODLE_REG',p.id_funcionalidad,'Registrar usuarios pendientes en Moodle por plataforma y plan',1,1
FROM tbl_funcionalidades p WHERE p.clave='PERSONA'
AND NOT EXISTS(SELECT 1 FROM tbl_funcionalidades x WHERE x.clave='USU_MOODLE_REG');
SET @funcionalidad_moodle=(SELECT id_funcionalidad FROM tbl_funcionalidades WHERE clave='USU_MOODLE_REG');
INSERT INTO tbl_textos_sistema(clave,valor,id_funcionalidad,usuario_modifico) VALUES
('gw.registroMoodle.titulo','Registrar usuarios en Moodle',@funcionalidad_moodle,1),
('gw.registroMoodle.plataforma','Plataforma',@funcionalidad_moodle,1),
('gw.registroMoodle.plan','Plan',@funcionalidad_moodle,1),
('gw.registroMoodle.nombreUsuario','Nombre de usuario (opcional)',@funcionalidad_moodle,1),
('gw.registroMoodle.seleccionar','Seleccionar',@funcionalidad_moodle,1),
('gw.registroMoodle.todosPlanes','Todos los usuarios activos sin registro en esta plataforma',@funcionalidad_moodle,1),
('gw.registroMoodle.registrar','Registrar',@funcionalidad_moodle,1),
('gw.registroMoodle.ayuda','Seleccione la plataforma y, opcionalmente, el plan. Para registrar un usuario específico, capture su nombre de usuario y seleccione el plan al que pertenece. Sólo se procesan usuarios activos sin relación con esa plataforma. Si la cuenta ya existe en Moodle se vincula su ID; si no existe, se crea. No se matricula en cursos.',@funcionalidad_moodle,1),
('gw.registroMoodle.plataformaRequerida','Seleccione una plataforma.',@funcionalidad_moodle,1),
('gw.registroMoodle.plataformaInvalida','La plataforma seleccionada no existe o está inactiva.',@funcionalidad_moodle,1),
('gw.registroMoodle.planInvalido','El plan seleccionado no existe.',@funcionalidad_moodle,1),
('gw.registroMoodle.enCurso','Ya existe un registro en curso para esa plataforma. Espere a que termine.',@funcionalidad_moodle,1),
('gw.registroMoodle.ocupado','Hay otros registros en curso. Intente nuevamente más tarde.',@funcionalidad_moodle,1),
('gw.registroMoodle.sinPendientes','No hay usuarios activos pendientes para la plataforma y el plan seleccionados.',@funcionalidad_moodle,1),
('gw.registroMoodle.progreso','Creados: {0}. Vinculados a cuentas existentes: {1}. Omitidos: {2}. Con error: {3}. Procesados: {4} de {5}.',@funcionalidad_moodle,1),
('gw.registroMoodle.finalizado','Proceso terminado. Creados: {0}. Vinculados a cuentas existentes: {1}. Omitidos: {2}. Con error: {3}. Procesados: {4} de {5}.',@funcionalidad_moodle,1),
('gw.registroMoodle.detenido','Proceso detenido. Se conservaron {0} cuentas nuevas y {1} vínculos con cuentas existentes. Omitidos: {2}. Con error: {3}. Procesados: {4} de {5}.',@funcionalidad_moodle,1),
('gw.registroMoodle.erroresAyuda','Se muestran hasta 100 errores. Los usuarios con error permanecen pendientes; corrija su información y vuelva a ejecutar. No se volverán a procesar quienes ya tienen relación con la plataforma.',@funcionalidad_moodle,1),
('gw.registroMoodle.detalleError','Persona {0}: {1}',@funcionalidad_moodle,1),
('gw.registroMoodle.usuarioInvalido','El nombre de usuario no tiene un formato válido para Moodle.',@funcionalidad_moodle,1),
('gw.registroMoodle.planRequeridoUsuario','Seleccione el plan al que pertenece el usuario que desea registrar.',@funcionalidad_moodle,1),
('gw.registroMoodle.usuarioNoExiste','No existe un usuario con el nombre proporcionado.',@funcionalidad_moodle,1),
('gw.registroMoodle.usuarioAmbiguo','El nombre de usuario corresponde a varios registros. Revise la integridad de sus datos.',@funcionalidad_moodle,1),
('gw.registroMoodle.usuarioInactivo','El usuario está inactivo y no puede registrarse mediante esta opción.',@funcionalidad_moodle,1),
('gw.registroMoodle.usuarioPlan','El usuario no pertenece al plan seleccionado.',@funcionalidad_moodle,1),
('gw.registroMoodle.usuarioRegistrado','El usuario ya se encuentra registrado en la plataforma seleccionada. No se realizaron cambios.',@funcionalidad_moodle,1),
('gw.registroMoodle.correoInvalido','No dispone de un correo institucional válido.',@funcionalidad_moodle,1),
('gw.registroMoodle.correoExistente','El correo ya pertenece a otra cuenta de Moodle. No se vinculó esa cuenta ni se creó otra.',@funcionalidad_moodle,1),
('gw.registroMoodle.contrasenaOriginal','No se dispone de una contraseña original que coincida con la contraseña cifrada del Gestor. Revise los datos de acceso del usuario.',@funcionalidad_moodle,1),
('gw.registroMoodle.nombreIncompleto','El nombre o los apellidos están incompletos.',@funcionalidad_moodle,1),
('gw.registroMoodle.respuestaInvalida','Moodle no devolvió un identificador válido. No se guardó una relación local.',@funcionalidad_moodle,1),
('gw.registroMoodle.errorServicio','No fue posible completar la operación con Moodle o guardar su relación. Revise la conectividad y el registro técnico.',@funcionalidad_moodle,1),
('gw.registroMoodle.permiso','No tiene permiso para registrar usuarios en Moodle.',@funcionalidad_moodle,1)
ON DUPLICATE KEY UPDATE clave=VALUES(clave);
UPDATE tbl_textos_sistema SET valor='Plan' WHERE clave='gw.registroMoodle.plan' AND valor='Plan (opcional)';
UPDATE tbl_textos_sistema SET valor='Seleccione la plataforma y, opcionalmente, el plan. Para registrar un usuario específico, capture su nombre de usuario y seleccione el plan al que pertenece. Sólo se procesan usuarios activos sin relación con esa plataforma. Si la cuenta ya existe en Moodle se vincula su ID; si no existe, se crea. No se matricula en cursos.'
WHERE clave='gw.registroMoodle.ayuda' AND valor='Seleccione la plataforma y, opcionalmente, el plan. Sólo se procesan usuarios activos sin relación registrada en esa plataforma. Si el usuario ya existe en Moodle se vincula su ID; si no existe, se crea y se guarda la relación. No se matricula en cursos.';
-- Asignar USU_MOODLE_REG desde Roles y permisos; no se conceden accesos automáticamente.
