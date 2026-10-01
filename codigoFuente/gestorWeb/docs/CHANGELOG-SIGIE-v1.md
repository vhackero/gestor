# Changelog SIGIE API

## Estudiantes sin registro SIGE - 2026-09-30

- La relacion con tbl_persona_sige es opcional (LEFT JOIN).
- Sin registro SIGE, la matricula proviene del nombre de usuario y el nombre del programa del plan asociado al ultimo registro de aspirante.
- Se conservan los datos SIGE cuando existen y los agregados optimizados por estudiante.
- Prueba optativa de respaldo y prioridad de SIGE mediante una tabla temporal exclusiva de la conexion, sin cambiar datos persistentes.

## Optimizacion de consulta por estudiante - 2026-09-30

- Los agregados de creditos, bajas e irregularidad se filtran por la matricula solicitada antes de agrupar.
- Se conserva una sola consulta SQL, con parametros enlazados y las mismas reglas academicas.
- Se mantiene el timeout predeterminado de 5000 ms y su variable SIGIE_API_QUERY_TIMEOUT_MS.
- Se agrega una prueba de integracion optativa que compara las 15 columnas con la consulta anterior
  en una base local, sin modificar datos ni indices.

## Restriccion de acceso por IP - 2026-09-29

- Por solicitud expresa, SIGIE exige una IP literal en el host de la URL, sin lista de destinos ni parametro.
- Rechazo HTTP 403 `ACCESO_SOLO_POR_IP` antes de autenticar cuando se usa dominio o Host invalido.
- La autenticacion y la lista opcional de IP de origen se conservan.
- Proteccion del alias `/ws/v1/estudiantes[/...]` con el mismo filtro.
- Los demas servicios web y el portal no adoptan esta restriccion.
- Actualizados los dos descriptores disponibles, manual de integracion y OpenAPI.

## 1.0.0 - 2026-08-17

- Endpoint GET `/SIGIE/v1/estudiantes/{matricula}`.
- Validacion estricta `ES` mas nueve digitos.
- Consulta relacionada de `tbl_persona` y `tbl_persona_sige`.
- Avance por asignaturas aprobadas y total del plan.
- Situacion provisional con bajas, egreso y regularidad.
- Autenticacion de cliente con secreto BCrypt, scope e IP opcional.
- Folio, X-Request-ID, errores homogeneos y rate limiting configurable.
- OpenAPI 3.1, manuales, ejemplos y pruebas iniciales.

Los cambios incompatibles futuros requieren `/v2`; no deben alterarse silenciosamente nombres, tipos, codigos, autenticacion o semantica de v1.
