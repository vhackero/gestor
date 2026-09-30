# Changelog SIGIE API

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
