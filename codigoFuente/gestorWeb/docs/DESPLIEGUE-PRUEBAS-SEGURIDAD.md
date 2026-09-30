# Despliegue, pruebas y seguridad

## Despliegue

1. Respaldar y aplicar `codigoFuente/bd/R049_17082026_servicio_sigie_siss.sql` en el esquema activo `des_sisi_gestor`.
2. Configurar `SIGIE_API_QUERY_TIMEOUT_MS`, `SIGIE_API_RATE_LIMIT` y `SIGIE_API_RATE_WINDOW_SECONDS` en el gestor de configuracion del servidor.
3. Crear el cliente SISS sin guardar el secreto en scripts o historial de consola compartido.
4. Compilar `basegestor`, instalarlo en el repositorio Maven institucional y compilar `gestorWeb` con el perfil del ambiente.
5. Desplegar el WAR en WildFly/JBoss.
6. Publicar `/SIGIE/v1/...` mediante la IP del servidor. Si existe proxy, preservar el Host original y bloquear las rutas SIGIE en el virtual host del dominio. No reescribir un Host de dominio como IP interna.
7. Obligar HTTPS y TLS 1.2+, con certificado valido para la IP de destino. Deshabilitar HTTP o redirigirlo antes de aceptar credenciales.
8. Verificar datasource, pool, timeout, zona horaria, certificados, logs y conectividad desde la IP de SISS.

Antes de produccion, ejecutar `SHOW INDEX FROM tbl_persona_sige` y `EXPLAIN` sobre la consulta. El volcado inspeccionado no declara indice para `matricula_sige`; si la base activa confirma su ausencia, crear un indice no unico para esa columna mediante una migracion aprobada. La revision local del 2026-09-30 confirmo la ausencia del indice de matricula_sige. La optimizacion por estudiante se valido sin agregar indices; revisar el plan y los tiempos de cada ambiente antes de proponer cambios de esquema.

La configuracion productiva de TLS, certificados, proxy, secretos, hosts y despliegue QA esta PENDIENTE DE CONFIGURACION. El perfil Maven `qa` referencia `DeployConfig/QA/web.xml`, pero ese archivo no existe en el repositorio y debe proporcionarse antes de compilar QA.

## Pruebas

Ejecutar `mvn test` primero en `basegestor` y despues en `gestorWeb`. Las pruebas incluidas cubren formato, opcionales nulos, porcentaje, irregularidad, precedencia de bajas, datos incompletos y estabilidad basica del OpenAPI. En QA deben añadirse pruebas con base controlada para encontrado, inexistente, reintentos aprobados, minima aprobatoria distinta, asignatura reprobada posteriormente aprobada, baja de asignatura en el mismo evento, baja definitiva historica, baja temporal del ultimo periodo, error DB, 401, 403 y 429.

La validacion final exige comparar API, base y logs mediante el mismo folio, sin buscar por CURP/nombre. No usar datos personales reales en fixtures.

## Acceso de SIGIE por IP (2026-09-29)

- La regla esta implementada en `SigieSecurityFilter` con `SigieHostValidator`; no requiere cambios en base ni parametros.
- Ambos descriptores disponibles (`WebContent/WEB-INF/web.xml` y `DeployConfig/Desarrollo/web.xml`)
  cubren `/SIGIE/*` y `/ws/v1/estudiantes[/...]` antes de Spring Security, incluidos FORWARD y ASYNC.
- Las exclusiones de Spring Security para estas rutas delegan la autenticacion al filtro SIGIE;
  nunca deben desplegarse separadas de sus mappings.
- Verificar en el ambiente: IP con credenciales validas, IP sin credenciales (401), dominio
  con/sin credenciales (403 `ACCESO_SOLO_POR_IP`), alias, IPv6 si aplica y funcionamiento
  del portal y demas servicios por dominio.
- Una cabecera Host es informacion HTTP proporcionada por el cliente. Esta regla no sustituye
  autenticacion, firewall ni la lista de IP de origen, y no cierra el puerto TCP/TLS del dominio.
  Bloquea la solicitud HTTP a SIGIE; el dominio debe seguir disponible para el portal.
- Si el proxy o el contenedor reescriben tanto Host como el nombre del servidor, la aplicacion
  pierde el destino original. Validar la configuracion del proxy y el acceso directo al backend.
- No deshabilitar la comprobacion del certificado TLS en el cliente para permitir el uso de IP.
- Referencias: [URI de Java 8](https://docs.oracle.com/javase/8/docs/api/java/net/URI.html),
  [identidad TLS por IP](https://www.rfc-editor.org/rfc/rfc9525.html#section-6.4).

### Validación local del cambio por IP

- Paquete Java de SIGIE compilado con JDK 8 y dependencias locales del proyecto.
- 28 pruebas JUnit correctas: host IPv4/IPv6, puertos, rechazo de dominios y Host inválido,
  cabeceras reenviadas, autoridad del contenedor, rutas de ambos descriptores, autenticación,
  scope, IP de origen, rate limit, matrícula, mapeo y contrato OpenAPI.
- Los descriptores XML se analizaron en las pruebas y se verificó el alcance exclusivo de SIGIE.
- No se desplegó un WAR ni se probó el proxy/TLS del ambiente. Esa validación continúa pendiente.

## Optimizacion SQL por matricula (2026-09-30)

`EstudianteSigieRepositoryImpl.consultarPorMatricula` limita los agregados de creditos, bajas
e irregularidad mediante JOIN con `tbl_persona` filtrado por `:matricula` antes de agrupar.
Se conservan las reglas, los 15 campos de respuesta, una sola consulta con parametros
enlazados y el timeout predeterminado de 5000 ms. No se cambiaron datos ni indices.

Validacion con MariaDB local y JDBC del proyecto, comparando todas las columnas
contra la consulta anterior en la misma transaccion de solo lectura:

| Escenario | Anterior (ms) | Optimizada (ms) | Resultado |
| --- | ---: | ---: | --- |
| Caso reportado | 19334.0 | 50.9 | Identico |
| Con baja temporal | 19349.3 | 81.5 | Identico |
| Con baja definitiva | 16279.0 | 59.1 | Identico |
| Con aprobacion | 14291.0 | 32.9 | Identico |
| Con intento reprobado | 13102.3 | 33.0 | Identico |
| Sin calificaciones | 13182.7 | 41.5 | Identico |
| Matricula inexistente | 12.7 | 10.1 | Sin filas en ambas |

Son tiempos de consulta JDBC de esta ejecucion, no tiempos HTTP garantizados.
La variacion frente a mediciones anteriores depende de carga, cache y modo de ejecucion.

La prueba `EstudianteSigieConsultaIntegracionTest` es optativa: sin `sigie.test.jdbc.url` se omite.
Para repetirla en una copia local, proporcionar tambien `sigie.test.jdbc.user`,
`sigie.test.jdbc.password` y opcionalmente `sigie.test.matricula`. No guardar credenciales
en el repositorio. Selecciona casos de la copia de datos, ejecuta solo SELECT y compara
todos los campos; cada consulta optimizada tiene un limite de 5 segundos.
La consulta anterior esta en `basegestor/src/test/resources/sigie/consulta_estudiante_antes_optimizacion.sql`
exclusivamente como referencia de regresion.

Compilacion del repositorio modificado y de la prueba con JDK 8: correcta.
Siete escenarios dentro de una prueba de integracion: correctos.
Para verificar desde Postman, recompilar basegestor y gestorWeb y desplegar el WAR actualizado.
El despliegue y la medicion HTTP quedan pendientes.

## Seguridad

- No registrar Authorization, secreto, CURP, nombre, correo ni matricula completa.
- Limitar acceso por scope y opcionalmente por IP; no confiar en `X-Forwarded-For` sin proxy confiable.
- El rate limiter en memoria es valido para una instancia. En cluster debe moverse a API Gateway o almacenamiento compartido.
- CORS no se habilita porque el consumidor es servidor a servidor.
- La consulta usa parametros enlazados y selecciona columnas concretas.
- La aplicacion no puede garantizar TLS por si sola; debe validarse en proxy/WildFly.
- Los secretos heredados ya presentes en otros archivos del repositorio no forman parte de esta API y requieren remediacion separada.
