-- Script único del módulo Cambio o asignación de plan. Integra R063, R064 y R065.
-- Ejecutar únicamente este archivo, tanto en instalaciones nuevas como actualizadas.
-- Requiere la funcionalidad PERSONA existente. Conserva permisos asignados y textos personalizados.
-- No crea tablas ni modifica asignaciones: utiliza la tabla existente tbl_persona_aspirante.
-- El permiso de eliminación permite borrar también la única asignación, previa confirmación.
INSERT INTO tbl_funcionalidades(clave,id_funcionalidad_padre,descripcion,activo,usuario_modifico)
SELECT 'USU_PLAN_VER',p.id_funcionalidad,'Consultar cambio o asignación de plan',1,1
FROM tbl_funcionalidades p WHERE p.clave='PERSONA'
AND NOT EXISTS(SELECT 1 FROM tbl_funcionalidades x WHERE x.clave='USU_PLAN_VER');
INSERT INTO tbl_funcionalidades(clave,id_funcionalidad_padre,descripcion,activo,usuario_modifico)
SELECT 'USU_PLAN_ASIGNAR',p.id_funcionalidad,'Asignar plan a estudiantes',1,1
FROM tbl_funcionalidades p WHERE p.clave='USU_PLAN_VER'
AND NOT EXISTS(SELECT 1 FROM tbl_funcionalidades x WHERE x.clave='USU_PLAN_ASIGNAR');
INSERT INTO tbl_funcionalidades(clave,id_funcionalidad_padre,descripcion,activo,usuario_modifico)
SELECT 'USU_PLAN_MODIFICAR',p.id_funcionalidad,'Modificar plan de estudiantes',1,1
FROM tbl_funcionalidades p WHERE p.clave='USU_PLAN_VER'
AND NOT EXISTS(SELECT 1 FROM tbl_funcionalidades x WHERE x.clave='USU_PLAN_MODIFICAR');
INSERT INTO tbl_funcionalidades(clave,id_funcionalidad_padre,descripcion,activo,usuario_modifico)
SELECT 'USU_PLAN_ELIMINAR',p.id_funcionalidad,'Eliminar asignaciones de plan de estudiantes',1,1
FROM tbl_funcionalidades p WHERE p.clave='USU_PLAN_VER'
AND NOT EXISTS(SELECT 1 FROM tbl_funcionalidades x WHERE x.clave='USU_PLAN_ELIMINAR');

SET @funcionalidad_plan=(SELECT id_funcionalidad FROM tbl_funcionalidades WHERE clave='USU_PLAN_VER');
INSERT INTO tbl_textos_sistema(clave,valor,id_funcionalidad,usuario_modifico)
VALUES
('gw.planUsuario.titulo','Cambio o asignación de plan',@funcionalidad_plan,1),
('gw.planUsuario.ayuda','Escriba una o varias matrículas en el campo de texto, separadas por espacios, comas o punto y coma. Máximo 500 estudiantes por operación. Después de buscar, seleccione las asignaciones y el plan y convocatoria de destino.',@funcionalidad_plan,1),
('gw.planUsuario.panel.asignar','Asignación de plan',@funcionalidad_plan,1),
('gw.planUsuario.panel.modificar','Modificación de plan',@funcionalidad_plan,1),
('gw.planUsuario.usuario','Matrícula / Usuario',@funcionalidad_plan,1),
('gw.planUsuario.nombre','Nombre',@funcionalidad_plan,1),
('gw.planUsuario.plan','Plan',@funcionalidad_plan,1),
('gw.planUsuario.planActual','Plan actual',@funcionalidad_plan,1),
('gw.planUsuario.planNuevo','Plan de destino',@funcionalidad_plan,1),
('gw.planUsuario.convocatoria','Convocatoria',@funcionalidad_plan,1),
('gw.planUsuario.convocatoriaNueva','Convocatoria de destino',@funcionalidad_plan,1),
('gw.planUsuario.seleccionar','Seleccionar',@funcionalidad_plan,1),
('gw.planUsuario.sinResultados','No se han consultado asignaciones.',@funcionalidad_plan,1),
('gw.planUsuario.boton.asignar','Asignar',@funcionalidad_plan,1),
('gw.planUsuario.boton.buscar','Buscar asignaciones',@funcionalidad_plan,1),
('gw.planUsuario.boton.modificar','Modificar',@funcionalidad_plan,1),
('gw.planUsuario.confirmar','Confirmar operación',@funcionalidad_plan,1),
('gw.planUsuario.confirmar.detalle','Se cambiarán las asignaciones seleccionadas al plan y convocatoria de destino. ¿Desea continuar?',@funcionalidad_plan,1),
('gw.planUsuario.si','Sí',@funcionalidad_plan,1),
('gw.planUsuario.no','No',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.asignado','Se asignó el plan y la convocatoria a {0} estudiantes.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.modificado','Se modificaron {0} asignaciones.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.usuarios','Capture al menos una matrícula o nombre de usuario.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.destino','Seleccione un plan disponible y una convocatoria activa vinculada a ese plan.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.duplicado','La asignación que intentas realizar ya se encuentra capturada para: {0}. No se guardaron cambios.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.sinAsignacion','No existe una asignación de plan para el usuario proporcionado: {0}.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.noEstudiante','No existe un usuario con rol Estudiante para: {0}. No se guardaron cambios.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.usuarioAmbiguo','Existen usuarios ambiguos en la base de datos. Revise su integridad antes de continuar.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.repetido','La matrícula o usuario {0} se repite en la lista.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.formato','Los nombres de usuario deben tener hasta 100 caracteres, sin espacios ni caracteres especiales ajenos a letras, números, punto, guion, guion bajo o arroba.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.limite','Puede procesar hasta 500 estudiantes por operación.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.sesion','La sesión no está disponible. Inicie sesión nuevamente.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.permiso','No tiene permiso para realizar esta operación.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.seleccion','Seleccione al menos una asignación consultada.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.seleccionDuplicada','Seleccione una sola asignación por estudiante para evitar asignaciones duplicadas al mismo destino.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.desactualizado','Una asignación cambió desde la consulta. Busque nuevamente antes de continuar. No se guardaron cambios.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.sinCambios','La asignación seleccionada ya tiene ese plan y convocatoria. No hay cambios que guardar.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.error','No fue posible guardar o consultar las asignaciones. Revise la configuración y el registro técnico.',@funcionalidad_plan,1),
('gw.planUsuario.acciones','Acciones',@funcionalidad_plan,1),
('gw.planUsuario.boton.eliminar','Eliminar asignación de plan',@funcionalidad_plan,1),
('gw.planUsuario.confirmar.eliminar','Se eliminará la asignación de {0} al plan {1}, convocatoria {2}. ¿Desea continuar?',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.asignacionExistente','Ya existe una asignación de plan para: {0}. Cada estudiante debe tener un solo registro. Utilice Modificar para cambiar su plan.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.resolverDuplicados','Existen varias asignaciones para un estudiante. Elimine las adicionales y conserve una antes de modificar el plan.',@funcionalidad_plan,1),
('gw.planUsuario.mensaje.eliminado','Se eliminó la asignación de plan seleccionada.',@funcionalidad_plan,1),
('gw.planUsuario.confirmar.eliminarUltima','Se eliminará la única asignación de {0} al plan {1}, convocatoria {2}. El estudiante quedará sin plan asignado. ¿Desea continuar?',@funcionalidad_plan,1)
ON DUPLICATE KEY UPDATE clave=VALUES(clave);

-- Actualizar únicamente el texto de ayuda original; conservar personalizaciones.
UPDATE tbl_textos_sistema
SET valor='Escriba una o varias matrículas en el campo de texto, separadas por espacios, comas o punto y coma. Máximo 500 estudiantes por operación. Después de buscar, seleccione las asignaciones y el plan y convocatoria de destino.'
WHERE clave='gw.planUsuario.ayuda'
AND valor='Escriba una o varias matrículas, separadas por espacios, comas, punto y coma o saltos de línea. Máximo 500 estudiantes por operación. La operación completa se valida antes de guardar.';



-- Compatibilidad con los valores predeterminados de versiones anteriores.
UPDATE tbl_funcionalidades SET descripcion='Eliminar asignaciones de plan de estudiantes'
WHERE clave='USU_PLAN_ELIMINAR'
AND descripcion='Eliminar asignaciones duplicadas conservando una por estudiante';
UPDATE tbl_textos_sistema SET valor='Eliminar asignación de plan'
WHERE clave='gw.planUsuario.boton.eliminar' AND valor='Eliminar asignación duplicada';
UPDATE tbl_textos_sistema SET valor='Se eliminó la asignación de plan seleccionada.'
WHERE clave='gw.planUsuario.mensaje.eliminado' AND valor='Se eliminó la asignación duplicada. Las demás asignaciones del estudiante se conservaron.';
UPDATE tbl_textos_sistema SET valor='Se eliminará la asignación de {0} al plan {1}, convocatoria {2}. ¿Desea continuar?'
WHERE clave='gw.planUsuario.confirmar.eliminar'
AND valor='Se eliminará la asignación de {0} al plan {1}, convocatoria {2}. Se conservará al menos otra asignación del estudiante. ¿Desea continuar?';

-- No otorga accesos automáticamente: seleccionar permisos desde Roles y permisos.
