-- mysql-init.sql
CREATE DATABASE IF NOT EXISTS des_sisi_gestor;
CREATE DATABASE IF NOT EXISTS sisi_plataforma;
CREATE DATABASE IF NOT EXISTS moodle_aula_virtual;

CREATE USER IF NOT EXISTS 'elearning'@'%' IDENTIFIED BY 'elearning';

GRANT ALL PRIVILEGES ON des_sisi_gestor.* TO 'elearning'@'%';
GRANT ALL PRIVILEGES ON sisi_plataforma.* TO 'elearning'@'%';

FLUSH PRIVILEGES;