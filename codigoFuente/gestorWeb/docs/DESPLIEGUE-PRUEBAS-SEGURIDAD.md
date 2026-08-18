# Despliegue, pruebas y seguridad

## Despliegue

1. Respaldar y aplicar `codigoFuente/bd/R049_17082026_servicio_sigie_siss.sql` en el esquema activo `des_sisi_gestor`.
2. Configurar `SIGIE_API_QUERY_TIMEOUT_MS`, `SIGIE_API_RATE_LIMIT` y `SIGIE_API_RATE_WINDOW_SECONDS` en el gestor de configuracion del servidor.
3. Crear el cliente SISS sin guardar el secreto en scripts o historial de consola compartido.
4. Compilar `basegestor`, instalarlo en el repositorio Maven institucional y compilar `gestorWeb` con el perfil del ambiente.
5. Desplegar el WAR en WildFly/JBoss.
6. Configurar proxy/context path para publicar `/SIGIE/v1/...` sin prefijos adicionales.
7. Obligar HTTPS y TLS 1.2+, deshabilitar HTTP o redirigirlo antes de aceptar credenciales.
8. Verificar datasource, pool, timeout, zona horaria, certificados, logs y conectividad desde la IP de SISS.

Antes de produccion, ejecutar `SHOW INDEX FROM tbl_persona_sige` y `EXPLAIN` sobre la consulta. El volcado inspeccionado no declara indice para `matricula_sige`; si la base activa confirma su ausencia, crear un indice no unico para esa columna mediante una migracion aprobada. No se incluye automaticamente porque no fue posible verificar los indices de la base activa.

La configuracion productiva de TLS, certificados, proxy, secretos, hosts y despliegue QA esta PENDIENTE DE CONFIGURACION. El perfil Maven `qa` referencia `DeployConfig/QA/web.xml`, pero ese archivo no existe en el repositorio y debe proporcionarse antes de compilar QA.

## Pruebas

Ejecutar `mvn test` primero en `basegestor` y despues en `gestorWeb`. Las pruebas incluidas cubren formato, opcionales nulos, porcentaje, precedencia de bajas, datos incompletos y estabilidad basica del OpenAPI. En QA deben añadirse pruebas con base controlada para encontrado, inexistente, reintentos aprobados, minima aprobatoria distinta, bajas vigentes, error DB, 401, 403 y 429.

La validacion final exige comparar API, base y logs mediante el mismo folio, sin buscar por CURP/nombre. No usar datos personales reales en fixtures.

## Seguridad

- No registrar Authorization, secreto, CURP, nombre, correo ni matricula completa.
- Limitar acceso por scope y opcionalmente por IP; no confiar en `X-Forwarded-For` sin proxy confiable.
- El rate limiter en memoria es valido para una instancia. En cluster debe moverse a API Gateway o almacenamiento compartido.
- CORS no se habilita porque el consumidor es servidor a servidor.
- La consulta usa parametros enlazados y selecciona columnas concretas.
- La aplicacion no puede garantizar TLS por si sola; debe validarse en proxy/WildFly.
- Los secretos heredados ya presentes en otros archivos del repositorio no forman parte de esta API y requieren remediacion separada.
