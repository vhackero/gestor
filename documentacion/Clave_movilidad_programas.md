# Clave de movilidad de programas

Campo informativo y opcional en Datos generales de la ficha del programa, para captura y edición. Se almacena en `tbl_ficha_descriptiva_programa.clave_movilidad` y se mapea como `claveMovilidad` en entidad y DTO. Admite hasta 100 caracteres; espacios al principio/final se eliminan y vacío se guarda como `NULL`. Los programas existentes mantienen `NULL`.

## Regla de compatibilidad de claves

La clave puede repetirse únicamente entre programas con el mismo nombre (`nombre_tentativo`). Se comparan nombres con `LOWER(TRIM(...))`, usando la colación de la base (`utf8mb4_unicode_ci` en la base local). No se exige que programas con igual nombre usen la misma clave: sólo se impide compartir una clave entre nombres diferentes. El programa que se está editando se excluye de la búsqueda de conflictos.

| Caso | Resultado |
|---|---|
| Dos programas «Asignatura A» con clave `AAB` | Permitido |
| «Asignatura A» con `AAB` y «Asignatura B» intenta `AAB` | Rechazado; mensaje con nombre e ID del programa en conflicto |
| Programa conserva su propia clave | Permitido |
| Programa cambia su nombre y comparte clave con otros programas del nombre anterior | Rechazado hasta cambiar o retirar su clave |
| Campo vacío o sólo espacios | Se guarda `NULL` |
| Cambio de estado o actualización que no incluye el campo | Conserva la clave vigente |
| Clonación | La clave se limpia; se puede capturar una nueva, o reutilizarla si el nombre coincide |

El AJAX del campo muestra el conflicto con el otro programa. El guardado comprueba nuevamente la regla en el servicio. Los conflictos impiden continuar el guardado/navegación; la comprobación AJAX no sustituye a la del backend.

## Persistencia y concurrencia

No se utiliza un índice único simple sobre la clave, porque impediría compartirla para nombres iguales. Tampoco `UNIQUE(clave,nombre)` implementa la regla: permitiría una clave con nombres diferentes.

El servicio usa la fila de control de `tbl_control_clave_movilidad` como exclusión transaccional y hace la consulta de conflicto mediante lectura actual `FOR UPDATE`. Los guardados/actualizaciones de fichas se serializan hasta commit o rollback, evitando que dos solicitudes del servicio autoricen al mismo tiempo una clave para nombres incompatibles. El índice `idx_programa_clave_movilidad` acelera la búsqueda.

La regla se aplica al guardar mediante `FichaDescProgramaService`. Las escrituras SQL directas o procesos externos que eviten ese servicio deben respetar la misma validación y bloqueo; el índice no impone por sí solo esta condición entre filas.

Los DTO que no incluyen el nuevo campo conservan la clave actual. El DTO diferencia omisión y limpieza explícita del campo para permitir guardar `NULL` desde la captura. También se conserva el nombre actual cuando una actualización parcial no lo proporciona, para comprobar la relación clave/nombre.

`identificador_final` y `cve_programa` conservan su uso actual. No se cambiaron inscripción, dispersión, historial, reportes ni integraciones para utilizar la clave informativa.

## Migración

Ejecutar `documentacion/sql/20261012_clave_movilidad_programas.sql` antes de desplegar el código. La migración es repetible: agrega la columna con `NULL DEFAULT NULL`, elimina el índice único simple si se aplicó la versión inicial, registra el índice no único y la fila de control.

`DEFAULT NULL` permite omitir el campo en inserciones que especifican sus columnas. No corrige los `INSERT INTO tabla VALUES (...)` de respaldos antiguos que dependan del número original de columnas. Restaurar esos respaldos antes de aplicar la migración, o adaptar su inserción con lista explícita de columnas.

## Verificación

Compilación de componentes con Java 8 y 12 pruebas aprobadas. Incluye alta opcional, normalización, conflicto con nombre/ID, edición sin conflicto consigo mismo, conservación en DTO parcial, limpieza explícita, longitud, comprobación bajo bloqueo, nombres iguales/diferentes y cambio de nombre. La integración SQL usa copia temporal del esquema real y comprueba la exclusión entre dos conexiones; no modifica programas existentes. La clonación y el XHTML fueron revisados estructuralmente; queda pendiente validar el flujo completo en navegador/WildFly.
