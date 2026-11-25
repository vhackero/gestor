# Registrar usuarios en Moodle — R031

Se agrega un panel en `plataforma/administracion/usuarios`, antes de la búsqueda avanzada, siguiendo `C:\Users\luisf\Pictures\capturas\R031_01012026.drawio`. Sus estilos están en la hoja general `resources/Privado/base/css/sb-admin-2.css`, limitados por `.registro-usuarios-moodle`.

## Uso

Seleccione una plataforma y, opcionalmente, un plan. El botón Registrar se habilita al seleccionar plataforma y se deshabilita mientras el trabajo está en curso. La plataforma debe existir y estar activa. Las opciones proceden de `cat_parametros_wsmoodle`; los planes proceden de `tbl_planes`.

Sin plan se consideran todos los usuarios activos del Gestor sin relación con la plataforma elegida, aunque no tengan asignación de plan. Con plan se exige una relación en `tbl_persona_aspirante` con ese identificador. No se exige rol Estudiante: el diagrama contempla usuarios activos en general.

El filtro usa `NOT EXISTS` sobre persona y plataforma. Una relación con otra plataforma no excluye al usuario. La pertenencia al plan se comprueba con `EXISTS`, evitando duplicar candidatos cuando haya varias relaciones de plan. Una relación existente con la plataforma seleccionada se excluye, incluso si el registro local tiene un ID Moodle incorrecto: el intento individual permite recuperar una cuenta remota inexistente, mientras el proceso masivo conserva esta exclusión.

## Alta y vinculación

Para registrar una persona específica, capture **Nombre de usuario** y seleccione su plan. La búsqueda es exacta, ignora mayúsculas/minúsculas y espacios al inicio o final. El servidor verifica que exista, esté activa y pertenezca al plan elegido; vuelve a comprobar usuario y plan al procesarla.

En el intento individual se verifica la pertenencia al plan antes de consultar Moodle. Si existe la relación local pero falta la cuenta remota, se intenta crearla y se actualiza el ID en la misma relación, sin duplicarla. Si existen tanto la relación como la cuenta remota, muestra: **El usuario ya se encuentra registrado en la plataforma seleccionada. No se realizaron cambios.** El aviso aparece en los resultados del trabajo individual. Si la cuenta existe únicamente en Moodle pero falta la relación local, se conserva el flujo de vinculación descrito abajo.

La ampliación se validó mediante compilación directa con Java 8 y doce pruebas satisfactorias, incluyendo filtros SQL en tablas temporales de MariaDB y notificación de registro existente.

El método antiguo `obtenerIdMoodle(PersonaDTO, Integer, Long)` contiene una condición de persistencia invertida, que intenta guardar cuando el ID es nulo. No se modificó el método utilizado por otros módulos. El flujo nuevo reutiliza `UsuarioWSClient` mediante un adaptador propio y sólo inserta en la tabla existente `rel_personas_plataformas_moodle` después de recibir un ID positivo.

Antes de cada usuario, el servidor bloquea la persona y vuelve a comprobar activo, plan y ausencia de relación. Si ya no cumple, se omite. Cada usuario se procesa en una transacción independiente, con el operador que inició el proceso como `usuario_modifico`.

- Si el nombre de usuario existe en Moodle, se reutiliza su ID y se guarda la relación pendiente. No se cambian sus credenciales ni sus datos.
- Para una cuenta nueva se requiere nombre, apellidos, correo institucional y contraseña original. Se toma la contraseña original de `sso_unidadAdministrativa` únicamente si coincide, mediante BCrypt, con `tbl_persona.contrasenia`. No se envía el hash como contraseña ni se inventa una contraseña. Si no está disponible o no coincide, se informa el error para ese usuario.
- Si el correo pertenece a otra cuenta de Moodle, se informa el conflicto y no se vincula por correo.
- Si el alta falla después de que Moodle haya creado la cuenta, se vuelve a buscar por nombre de usuario antes de declarar el error.
- Se rechazan respuestas sin un ID positivo. No se insertan relaciones con ID nulo o cero.

No matricula en cursos, modifica roles ni cambia planes. No crea tablas. El alta remota en Moodle y el guardado local no forman una transacción distribuida: si Moodle crea la cuenta y falla el guardado local, el siguiente intento podrá recuperarla por su nombre de usuario.

## Ejecución y resultados

Se ejecuta en segundo plano, con páginas de 100 identificadores y un corte máximo capturado al iniciar. Se muestra progreso y conteos de creados, vinculados a cuentas existentes, omitidos y fallidos. Los errores individuales no detienen a los siguientes candidatos; se muestran hasta 100 detalles con el identificador de persona.

Existe un trabajador y una cola de dos trabajos por instancia de aplicación. Se impide iniciar otro trabajo de la misma plataforma dentro de esa instancia. El estado sólo lo consulta su operador y se guarda en memoria; reiniciar la aplicación pierde el avance visual, pero conserva las cuentas y relaciones confirmadas. Puede volver a ejecutar después de corregir los errores: el filtro excluye a los ya relacionados.

## Instalación y permiso

Aplicar `codigoFuente/bd/R067_14102026_registro_usuarios_moodle.sql` y compilar/desplegar `basegestor` y `gestorWeb`. La migración crea únicamente el permiso `USU_MOODLE_REG` y los textos configurables `gw.registroMoodle.*`; debe asignarse desde Roles y permisos. No concede accesos automáticamente ni registra cuentas al ejecutar el SQL.

La plataforma seleccionada debe tener configurados sus servicios y acceso al WS. Se utilizan las funciones existentes `core_user_get_users` y `core_user_create_users` a través de `UsuarioWSClient`. Los errores que indique Moodle, incluidas sus políticas de contraseña, se reportan como fallos del usuario correspondiente.

## Validación

Compilación directa con Java 8 y nueve pruebas: alta y persistencia de ID válido, reutilización sin cambiar credenciales, exclusión de usuarios ya relacionados/inactivos, conflicto de correo, respuesta cero, contraseña original incorrecta, recuperación tras timeout, filtro SQL real en tablas temporales de MariaDB y trabajo asíncrono con un error individual.

No se crearon cuentas reales ni se ejecutó un registro masivo en Moodle durante estas pruebas. Falta comprobar el panel, las credenciales y el WS de la plataforma seleccionada después del despliegue.
