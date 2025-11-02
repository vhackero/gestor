USE des_sisi_gestor;
UPDATE cat_parametros_sistema SET valor = 'http://localhost:8080/plataforma/login' where clave = 'PAGINA_INICIO';
UPDATE cat_parametros_wsmoodle SET host = 'http://localhost/moodle-modelo' where id_parametro_wsmoodle = 1;

USE moodle_aula_virtual;
UPDATE mdl_config SET value = 'http://localhost:8080/plataforma/login' where name = 'alternateloginurl';