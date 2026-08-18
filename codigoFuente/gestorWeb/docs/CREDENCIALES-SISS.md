# Administracion de credenciales SISS

SIGIE entrega conceptualmente `Client ID`, `Client Secret`, scope `sigie:estudiantes:read` y API Base URL. No se entrega un Token Endpoint porque esta version usa HTTP Basic entre servidores; una migracion futura a OAuth 2.0 Client Credentials no debe romper el contrato funcional v1.

## Ambientes

Desarrollo, QA y Produccion deben tener Client ID, secreto, vigencia, IP y URL independientes. Hosts y responsables estan PENDIENTES DE CONFIGURACION.

## Ciclo de vida

1. SIGIE genera un secreto criptograficamente aleatorio fuera del repositorio.
2. SIGIE entrega el valor una sola vez por el gestor institucional de secretos.
3. Solo el hash BCrypt se guarda en `tbl_clientes_api_sigie.secret_hash`.
4. Se activa el cliente con scope minimo, vigencia e IP cuando corresponda.
5. La rotacion crea y entrega un secreto nuevo; nunca recupera el anterior desde la base.
6. La revocacion establece `activo=0` y registra fechas de revocacion/reemplazo.
7. La baja elimina el acceso, no el historial operativo requerido.

SIGIE provee y opera el servicio, autoriza clientes y revoca accesos. SISS protege sus credenciales, restringe su acceso, rota cuando se solicite y reporta incidentes.

No enviar secretos por correo sin proteccion, Git, documentos publicos o tickets visibles. Usar el proveedor institucional de secretos; su producto y procedimiento estan PENDIENTES DE CONFIGURACION.
