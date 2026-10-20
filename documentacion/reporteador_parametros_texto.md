# Reporteador: parámetros de texto y consultas WITH

En Administración de Reporteador, el detalle del parámetro permite elegir **Lista** o **Texto**. Lista conserva la consulta SQL de opciones con columnas `valor` y `nombre`. Texto muestra un input al ejecutar el reporte y no requiere consulta de opciones.

Por ejemplo, configure la clave `:periodociclo`, etiqueta `Periodo o ciclo` y tipo Texto. En el Reporteador podrá capturar `2024-1` o `2024`; el SQL recibe el valor mediante un parámetro JDBC, sin concatenarlo. Los parámetros siguen siendo obligatorios.

La validación admite una sola consulta `SELECT` o `WITH ... SELECT`, incluidos comentarios ordinarios y un punto y coma final. Rechaza instrucciones de modificación o administración, consultas múltiples, escritura/lectura de archivos, asignación a variables, bloqueos y comentarios ejecutables. Las cadenas de texto y comentarios no se interpretan como instrucciones ni como parámetros. La ejecución de reportes y opciones utiliza una conexión marcada de sólo lectura.

Aplicar `codigoFuente/bd/R068_20102026_reporteador_parametros_texto.sql` antes de desplegar los cambios en basegestor y gestorWeb. Agrega `tipo_entrada` a la tabla existente; las configuraciones previas quedan como SELECT (Lista). Los parámetros INPUT (Texto) guardan la consulta de opciones vacía. No cambia permisos ni otros módulos.

Validación: compilación directa con Java 8, pruebas del validador, enlace seguro de parámetros, persistencia del tipo, aceptación de los reportes SQL solicitados y ejecución real de una CTE con parámetro de texto en MariaDB. La revisión visual en el servidor requiere desplegar la aplicación.
