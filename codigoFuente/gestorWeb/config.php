<?php  // Moodle configuration file

unset($CFG);
global $CFG;
$CFG = new stdClass();

$CFG->dbtype    = 'mariadb';
$CFG->dblibrary = 'native';
$CFG->dbhost    = 'gestorweb_mysql-db';
$CFG->dbname    = 'moodle_aula_virtual';
$CFG->dbuser    = 'root';
$CFG->dbpass    = 'gestor';
$CFG->prefix    = 'mdl_';
$CFG->dboptions = array (
  'dbpersist' => 0,
  'dbport' => 3306,
  'dbsocket' => '',
  'dbcollation' => 'utf8mb4_unicode_ci',
);

@error_reporting(E_ALL | E_STRICT);
@ini_set('display_errors', '1');
define('DEBUG', true);
$CFG->debug = (E_ALL | E_STRICT);
$CFG->debugdisplay = 1;

$CFG->wwwroot   = 'http://localhost/moodle-modelo';
$CFG->dataroot  = '/var/www/moodledata';
$CFG->admin     = 'admin';
$CFG->ocultaencuestasaulacalificador = 1;
//$CFG->sslproxy  = 'false';
$CFG->direccionar = 'http://localhost:8080/plataforma/tablero';

/*$CFG->debug = (E_ALL | E_STRICT); // Activa todos los mensajes de depuración
$CFG->debugdisplay = true; // Muestra los mensajes de depuración directamente en la página*/

$CFG->url_block_digital = 'https://auth.elibro.net/'; //CTIE PEI - JPCP  Enero 25 - Para especificar la URL de la Api eLibro para el bloque de biblioteca digital
$CFG->resetcache_block_digital = 0; //CTIE PEI - JPCP  Enero 25 - Para reiniciar la caché del usuario y autenticar en una nueva URL de la Api de eLibro
$CFG->debug_block_digital = $CFG->dataroot.'/navbar_debug_log.txt';

$CFG->url_navbar_digital = 'https://auth.elibro.net/'; //CTIE PEI - JPCP  Enero 25 - Para especificar la URL de la Api eLibro
//$CFG->debug_navbar_digital = $CFG->dataroot.'/navbar_debug_log.txt'; //CTIE PEI - JPCP  Enero 25 - Para habilitar bitácoras para depuración en el menú nav bar plus
$CFG->resetcache_navbar_digital = 0; //CTIE PEI - JPCP  Enero 25 - Para reiniciar la caché del usuario y autenticar en una nueva URL de la Api de eLibro


#$CFG->wwwroot   = 'http://172.18.30.104';
$CFG->directorypermissions = 0777;
$CFG->rolespersonzalidosws = 1;
//$CFG->unencryptedpassword = 1;
$CFG->intervenciondatosregistro = 1;


require_once(__DIR__ . '/lib/setup.php');

// There is no php closing tag in this file,
// it is intentional because it prevents trailing whitespace problems!