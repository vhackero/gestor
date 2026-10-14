-- Texto de validación de Ver roles: admite cualquier rol elegido, exige al menos uno.
INSERT INTO tbl_textos_sistema(clave,valor,id_funcionalidad,usuario_modifico)
SELECT 'gw.adminPersona.roles.minimo','Debe asignar al menos un rol al usuario.',f.id_funcionalidad,1
FROM tbl_funcionalidades f WHERE f.clave='PERSONA'
AND NOT EXISTS(SELECT 1 FROM tbl_textos_sistema t WHERE t.clave='gw.adminPersona.roles.minimo');
