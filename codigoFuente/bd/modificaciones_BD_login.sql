INSERT INTO `cat_parametros_sistema` (`clave`, `valor`, `fecha_registro`, `fecha_actualizacion`, `usuario_modifico`) VALUES ('DESHABILITAR_ACCESO_LOGIN', '1', current_timestamp(), current_timestamp(), '2'); 

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico) VALUES
  ('gw.login.deshabilitado.mensaje', 'Estimado usuario,<br/><br/>
La Universidad Abierta y a Distancia de México (UnADM) está realizando mantenimiento en esta aplicación para mejorar nuestros servicios.<br/><br/>
Por favor, intenta acceder más tarde. No es necesario reportar esta situación en la mesa de servicio.<br/><br/>
Atentamente,<br/>
<b>#OrgulloyCorazónUnADM</b>', 47, 2);