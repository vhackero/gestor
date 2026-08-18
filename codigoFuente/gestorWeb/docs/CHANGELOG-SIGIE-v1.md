# Changelog SIGIE API

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
