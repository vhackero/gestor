# Integracion SISS - Consulta de estudiante SIGIE v1

## Informacion general

- Proveedor: SIGIE.
- Consumidor: SISS.
- Version: 1.0.0.
- Proposito: consultar identidad, programa, avance y situacion escolar por matricula.
- Responsable tecnico y hosts: PENDIENTE DE CONFIGURACION.
- Ambientes: Desarrollo, QA y Produccion, con credenciales independientes.
- El servicio es de solo lectura y no decide elegibilidad para servicio social.

## Endpoint y transporte

`GET /SIGIE/v1/estudiantes/{matricula}` mediante HTTPS, TLS 1.2 o superior y JSON.

La matricula es obligatoria, sensible a mayusculas, tiene 11 caracteres y cumple `^ES[0-9]{9}$`. Valido: `ES241100064`. Invalidos: `es241100064`, `ES24110A064`, `ES2411000640`.

## Autenticacion y autorizacion

Enviar `Authorization: Basic <BASE64_CLIENT_ID_CLIENT_SECRET>`. El cliente debe estar vigente, activo y contener el scope `sigie:estudiantes:read`. Opcionalmente se restringe por IP. Las credenciales se obtienen por el proceso descrito en `CREDENCIALES-SISS.md`; no hay credenciales predeterminadas.

## Campos de respuesta

- `matricula`, `nombre`, `primer_apellido`, `curp`: obligatorios.
- `segundo_apellido`: puede ser `null`.
- `programa.clave`: `tbl_ficha_descriptiva_programa.cve_programa`, obligatorio.
- `programa.nombre`: programa importado de SIGE; puede ser `null`.
- `programa.nivel`: nivel del plan; puede ser `null`.
- `programa.plan_estudios`: primer año de cuatro digitos en `tbl_planes.identificador`; puede ser `null`.
- `avance.creditos_totales` y `creditos_cubiertos`: obligatorios.
- `avance.porcentaje_cubierto`: puede ser `null` cuando los creditos totales sean cero.
- `situacion_escolar`: baja definitiva, baja temporal, egresado o regular.
- `estatus`: activo o inactivo desde `tbl_persona.activo`.
- `correo_institucional`: puede ser `null`.
- `fecha_hora_consulta`: fecha real ISO 8601 con zona del servidor.
- `folio_consulta`: identificador unico sin informacion personal.

Precedencia provisional de situacion: baja definitiva vigente; baja temporal vigente; creditos cubiertos mayores o iguales al total; regular. Esta regla debe migrarse a catalogos cuando se definan institucionalmente.

## Ejemplo

```bash
curl --request GET \
  --url 'https://<HOST>/SIGIE/v1/estudiantes/ES241100064' \
  --user '<CLIENT_ID>:<CLIENT_SECRET>' \
  --header 'Accept: application/json' \
  --header 'X-Request-ID: <REQUEST_ID>'
```

## Errores y ejemplos

Todas las respuestas de error contienen `codigo`, `mensaje` y `folio_consulta`.

```json
// 400
{"codigo":"MATRICULA_INVALIDA","mensaje":"La matricula proporcionada no tiene un formato valido.","folio_consulta":"SIGIE-20260817-<UUID>"}
// 401
{"codigo":"NO_AUTORIZADO","mensaje":"La credencial de acceso esta ausente o no es valida.","folio_consulta":"SIGIE-20260817-<UUID>"}
// 403
{"codigo":"NO_AUTORIZADO","mensaje":"El consumidor no tiene autorizacion para consultar estudiantes.","folio_consulta":"SIGIE-20260817-<UUID>"}
// 404
{"codigo":"MATRICULA_NO_ENCONTRADA","mensaje":"No existe registro para la matricula proporcionada.","folio_consulta":"SIGIE-20260817-<UUID>"}
// 409
{"codigo":"DATOS_INCOMPLETOS","mensaje":"La informacion academica requerida del estudiante esta incompleta.","folio_consulta":"SIGIE-20260817-<UUID>"}
// 429 (incluye Retry-After)
{"codigo":"LIMITE_EXCEDIDO","mensaje":"Se ha excedido temporalmente el limite de solicitudes.","folio_consulta":"SIGIE-20260817-<UUID>"}
// 500
{"codigo":"ERROR_INTERNO","mensaje":"Ocurrio un error interno al procesar la solicitud.","folio_consulta":"SIGIE-20260817-<UUID>"}
// 503
{"codigo":"SERVICIO_NO_DISPONIBLE","mensaje":"El servicio no esta disponible temporalmente.","folio_consulta":"SIGIE-20260817-<UUID>"}
```

El ejemplo 200 completo se encuentra tanto en `openapi.yaml` como en la seccion de campos. El servicio nunca incluye SQL, stack traces, tablas, columnas, secretos o datos de otro estudiante.

## Trazabilidad y seguridad del consumidor

SISS puede enviar `X-Request-ID` con 1 a 64 caracteres alfanumericos, punto, guion o guion bajo. SIGIE lo devuelve y siempre genera `folio_consulta`. SISS debe proteger el secreto, no escribirlo en logs, validar certificados TLS, respetar `Retry-After` y usar timeouts y reintentos con espera exponencial solo para 429/503.
