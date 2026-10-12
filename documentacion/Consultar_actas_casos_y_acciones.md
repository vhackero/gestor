# Consultar actas: casos y acciones

Implementación en Eventos académicos → Consultar actas. Se conserva la pantalla, el bean de eventos y los servicios/repositorios de gestión escolar existentes. No requiere cambios de esquema.

## Filtros y variantes

Los filtros describen condiciones que pueden coexistir. La columna **Estatus e incidencias** muestra todas las condiciones del grupo, independientemente del filtro seleccionado. **Todos los casos** permite consultar el universo del periodo/programas elegidos.

| Id | Filtro | Condición y variantes |
|---|---|---|
| 0 | Abierta | Grupo abierto, sin documento. Puede tener cero alumnos o calificaciones completas, parciales o inexistentes. |
| 1 | Cerrada | Grupo cerrado, exactamente un documento con estructura básica PDF reconocible, al menos un alumno evaluable y todas las calificaciones finales presentes. Excluye duplicados y documentos inválidos. |
| 2 | Cierre incompleto | Grupo cerrado con calificaciones pendientes. Incluye variantes sin documento, documento único, duplicados o documentos inválidos. |
| 3 | Más de un documento cargado | Dos o más documentos, con grupo abierto, cerrado o sin estado; con o sin alumnos y calificaciones pendientes. |
| 4 | Cerrada sin documento | Grupo cerrado sin registro en `tbl_actas`; con calificaciones completas, parciales, inexistentes o sin alumnos. |
| 5 | Abierta con documento | Grupo abierto con uno o varios documentos, válidos o inválidos, con calificaciones completas o pendientes. |
| 6 | Sin alumnos evaluables | Ningún participante con una única asignación de rol estudiante, cualquiera que sea el cierre o la cantidad/calidad de documentos. Incluye grupos vacíos y grupos que sólo tienen personas con otros roles. |
| 7 | Documento vacío o inválido | Al menos un registro con archivo nulo, vacío, mayor de 10 MB, sin cabecera PDF en sus primeros 1024 bytes o sin marcador de fin en sus últimos 1024 bytes. Incluye los tres estados del grupo y documentos únicos o duplicados. |
| 8 | Estado de cierre sin definir | `acta_cerrada` nulo o distinto de 0/1; con cualquier cantidad de documentos y calificaciones. |
| -1 | Todos los casos | No excluye grupos por su estado de acta. |

El total y los calificados usan la misma población: participaciones del grupo cuya persona tiene exactamente una asignación de rol y ésta es estudiante (rol 2). Se excluyen personas con otros roles y personas con más de una asignación, incluso si el rol de estudiante está repetido. El mismo criterio se aplica al detalle y a la validación de cierre. Una calificación nula es pendiente; cero es una calificación capturada. Las participaciones se cuentan por registro, conforme al almacenamiento de calificaciones existente.

La búsqueda incluye programas sin plan/bloque asociado y compara exactamente los dos últimos segmentos de la clave del evento (`año-periodo`), evitando mezclar el periodo 1 con 10, 11 o 12. Mantiene el rango de años y periodos que ya ofrece la pantalla.

## Acciones

| Acción | Cuándo aparece | Efecto |
|---|---|---|
| Ver alumnos y pendientes | Todos los resultados | Detalle actual de incidencias y alumnos, con filtro para localizar pendientes. Informa que la captura/corrección se realiza en calificaciones del evento y requiere grupo abierto. |
| Visualizar acta | Un documento con estructura PDF reconocible | Abre el archivo aunque el grupo esté abierto, incompleto o sin estado. |
| Ver listado | Varios documentos | Muestra identificador, fecha, usuario y estado preliminar de cada documento. Permite visualizar, retirar o reemplazar el elegido. |
| Cargar PDF | Ningún documento | Agrega el documento; conserva cierre, calificaciones y evaluaciones. |
| Reemplazar PDF | Un documento, o un documento elegido en el listado | Sustituye el contenido del mismo registro; actualiza fecha/usuario, conserva grupo, calificaciones y evaluaciones. |
| Retirar documento | Un documento, o un documento elegido en el listado | Elimina sólo el registro elegido; conserva los demás documentos y toda la información académica. Puede dejar un grupo cerrado sin documento, que queda visible en su filtro específico. |
| Eliminar acta y reabrir | Exactamente un documento | Mantiene el comportamiento previo: elimina el documento, limpia evaluaciones/calificaciones del grupo y lo abre. Se advierte y confirma explícitamente. |
| Reabrir grupo | Sin documento y grupo cerrado/sin definir | Limpia evaluaciones/calificaciones y establece grupo abierto, con confirmación. |
| Completar cierre | Grupo abierto/sin definir, PDF único válido, alumnos y ninguna calificación pendiente | Establece grupo cerrado, conservando documento y evaluación. Revalida todos los requisitos en el backend. |

En duplicados se impide la eliminación con limpieza académica hasta depurar el listado. Retirar incluso el último documento conserva las calificaciones y el estado del grupo; la reapertura es otra acción explícita.

Las operaciones de documento y cierre bloquean la fila del grupo durante su transacción. La eliminación con reapertura se ejecuta en una sola transacción con reversión ante errores. Se revisan pertenencia del documento y estado actual para rechazar selecciones desactualizadas. Las acciones de consulta mantienen el permiso existente `EC_AGR`, comprobado también en el bean.

La carga acepta PDF de hasta 10 MB, con páginas y sin contraseña. La validación completa, al cargar, visualizar o completar el cierre, comprueba que el PDF se pueda leer; no verifica firmas digitales, correspondencia de calificaciones ni contenido académico del documento.

## Validación

- Compilación de componentes modificados con Java 8 y dependencias locales; no equivale al empaquetado completo Maven ni al despliegue.
- Pruebas JUnit: combinaciones de cierre/documentos/calificaciones, PDF inválidos, retiro/reemplazo, pertenencia, duplicados, revalidación de cierre y commit/rollback mediante el interceptor real de Spring con servicios simulados.
- `ConsultaActaSqlTest`: integración opcional con MariaDB/MySQL. Configurar `ACTAS_TEST_JDBC_URL`, `ACTAS_TEST_JDBC_USER` y, si aplica, `ACTAS_TEST_JDBC_PASSWORD` fuera del código. Usa tablas temporales de la conexión y lee `tbl_malla_curricular`; no modifica registros persistentes. Comprueba roles mixtos/repetidos, exclusión de personas con varios roles o asignaciones repetidas, alumnos pendientes, ausencia de alumnos, cierre nulo y separación de periodos 1/10.
- Validación estructural de XHTML, atributos de componentes PrimeFaces 6.1 y UTF-8; `git diff --check`.
- Pendiente: prueba visual de búsqueda, diálogos, carga/visor y confirmaciones en navegador, además de la integración completa de persistencia/despliegue.

## Corrección del consumo de memoria en la búsqueda (2026-10-09)

La búsqueda anterior cargaba las entidades `Acta` y sus BLOB para validar todos los documentos de cada grupo antes de filtrar resultados. Hibernate retenía esas entidades durante la petición y podía agotar el heap.

La búsqueda ahora ejecuta una sola consulta escalar con los datos del grupo y los conteos de alumnos, calificados, documentos e incidencias. Las consultas de estado y de listado también devuelven únicamente metadatos, sin entidades `Acta` ni contenido del archivo. El retiro, reemplazo y comprobación de duplicados no cargan el PDF anterior. Una nueva búsqueda o limpieza libera el PDF seleccionado previamente en el bean de sesión.

El diagnóstico del documento en los resultados es **preliminar**: SQL comprueba tamaño, cabecera y marcador final. Esto no demuestra que todas las páginas sean legibles ni que no exista cifrado. La validación completa se realiza sólo sobre el documento seleccionado al visualizarlo o completar el cierre, además de al cargarlo. El listado muestra «Estructura PDF reconocible» para distinguir ambas comprobaciones. Un grupo cerrado con un documento que supera estas comprobaciones preliminares puede contener un PDF que la validación completa rechace.

Para el visor de esta consulta, los archivos históricos mayores de 10 MB se rechazan antes de traer el BLOB a Java, con un mensaje para reemplazarlos. Siguen disponibles en el listado para retirar o reemplazar. La consulta que obtiene el contenido impone también el límite en la base de datos. No se modificaron archivos históricos ni la configuración de memoria de WildFly.

Verificación: componentes compilados con Java 8; 21 pruebas con `-Xmx64m`, incluida integración SQL mediante tablas temporales con un BLOB de más de 12 MB. Se verifica que búsqueda/listado no devuelven archivos, que la clasificación no consulta actas por cada grupo y que el rechazo de archivos grandes ocurre antes de cargar contenido. La prueba SQL crea copias temporales por referencia para evitar la restricción de MariaDB sobre reabrir una tabla temporal en la misma consulta. Continúa pendiente la validación en el servidor WildFly con la carga y configuración reales.

## Filtro opcional por texto del evento

El campo «Clave o nombre del evento (opcional)» busca el texto contenido en `cve_evento_cap` o `nombre_ec`, combinado con plan/programa, periodo y estatus. Por ejemplo, `S1` o `B1`. No distingue mayúsculas/minúsculas y elimina espacios al principio y al final. Vacío o con sólo espacios no restringe resultados. La comparación trata `%` y `_` como texto literal. «Limpiar campos» también borra este filtro. La condición se aplica en la base de datos, sin cargar documentos.
