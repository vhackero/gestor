# Carga por archivo de personas de fuente externa

## Ubicación y flujo

En `plataforma/administracion/usuarios` se agregó el panel **Importar datos a fuente externa**, siguiendo la propuesta `vista1.jpg`. Utiliza el permiso existente `IMP_FUEN_EXT`, tanto para visualizar el panel como para ejecutar la importación y obtener catálogos de la plantilla.

1. Descargar **Plantilla**: Excel con hojas Datos, Instrucciones y Catalogos. El ejemplo utiliza IDs válidos del catálogo vigente; reemplazar sus datos por los reales.
2. Seleccionar CSV, XLS o XLSX. Seleccionar el archivo sólo lo prepara: no guarda registros.
3. Consultar el icono de ayuda para ver las columnas y reglas.
4. Pulsar **Importar**. Si todo es válido se insertan los registros en `tbl_persona_externos` y se informa la cantidad. Si hay errores no se inserta ningún registro; se muestran hasta 100 mensajes con el número de fila. Corregir y volver a seleccionar el archivo.

El importador actual **Importar datos de fuente externa**, que posteriormente da de alta personas en Gestor, conserva su flujo.

## Archivo

Primera hoja de Excel o primera fila del CSV, con estos 14 encabezados en este orden:

```text
nombre_usuario,password,nombre,apellidop,apellidom,id_plan,division,correo_institucional,fecha_nacimiento,curp,nivel,id_fuente_externa,tipo_usuario,id_convocatoria
```

Todos los campos son obligatorios. `id_persona_externo` se genera automáticamente. No se incluye la columna calculada `nombre_usuario_normalizado`.

- Límite: 5 MB y 15000 registros; líneas vacías ignoradas.
- CSV: UTF-8, opcional BOM; delimitador coma o punto y coma, con soporte de campos entre comillas.
- XLS/XLSX: primera hoja; textos con formato Texto, especialmente usuario y contraseña. No se admiten fórmulas ni errores de celda. Se conservan ceros iniciales.
- Usuario: hasta 100 caracteres para compatibilidad con `tbl_persona`, letras ASCII, números, punto, guion, guion bajo o `@`. Se recortan espacios externos; la detección de duplicados ignora mayúsculas.
- Otros textos: hasta 255 caracteres, CURP de 18 con estructura válida, correo con formato válido. Se rechazan caracteres fuera del charset `utf8` de tres bytes de la tabla.
- Nacimiento: `AAAA-MM-DD` o celda fecha en Excel; fecha válida y no futura.
- IDs: enteros de catálogos existentes; fuente y convocatoria activas. El plan debe estar asociado a la convocatoria.
- Tipo de usuario: 1 Interno o 2 Externo. División y nivel son las claves textuales usadas por la fuente, por ejemplo DCSA y LIC.
- La contraseña se conserva según el esquema y el flujo existentes. No se imprime su valor en mensajes ni logs de esta funcionalidad; el archivo cargado se libera al finalizar el intento.

## Integridad en base de datos

Migración `codigoFuente/bd/R061_12102026_carga_personas_externos.sql`:

- Comprueba duplicados preexistentes; si hay conflictos se detiene y no elimina ni fusiona usuarios.
- Agrega al final la columna calculada `nombre_usuario_normalizado = LOWER(TRIM(nombre_usuario))`.
- Agrega el índice único `uq_persona_externo_usuario`. Protege también cargas simultáneas y escrituras fuera de este formulario.
- Mantiene las posiciones originales de `SELECT *` que usa el importador antiguo. La columna adicional no debe aparecer en los archivos de entrada.
- La migración puede volver a ejecutarse; no agrega otra columna ni otro índice.

Aplicada en la base local `des_sisi_gestor`; los cuatro registros anteriores siguen presentes. No se importaron personas de ejemplo a la tabla real.

El servicio usa una transacción para todas las inserciones. La validación completa precede a la primera inserción; un error de unicidad concurrente revierte el lote y no sobrescribe usuarios existentes. Las inserciones se agrupan en bloques de 250 filas y la consulta de duplicados en bloques de 500 usuarios, para evitar una consulta de inserción por alumno y un IN con 15,000 parámetros.

## Validación

Compilación con Java 8 y dependencias locales. Doce pruebas JUnit aprobadas:

`basegestor/pom.xml` declara `spring-security-core` 4.1.3.RELEASE, la misma versión que `gestorWeb`, para las anotaciones `@PreAuthorize`. Se comprobó la compilación del servicio con esa dependencia y los JAR de Spring de base, sin utilizar las librerías del WAR para resolver seguridad.

- Carga válida, duplicados dentro del archivo y contra datos existentes, referencias y campos inválidos.
- Plantilla XLSX con ejemplo compatible, catálogos e instrucciones.
- CSV UTF-8/BOM, punto y coma, campos entre comillas, acentos y contraseña preservada.
- XLS con usuario de texto y ceros iniciales; rechazo de encabezados, UTF-8 y fórmulas inválidos.
- Restricción real de MariaDB, rollback y compatibilidad del orden de columnas mediante una tabla temporal, sin modificar registros reales.
- Lectura y validación de un CSV con 15,000 registros, rechazo del registro 15,001, e inserción/consulta de duplicados de 15,000 filas en tabla temporal con rollback. La suite pasó con `-Xmx128m`; la operación SQL del lote tardó aproximadamente 1 segundo en este entorno local. Esta medición no verifica el consumo de un XLSX de 15,000 filas ni el tiempo del formulario desplegado.

Las pruebas SQL requieren `GESTOR_TEST_JDBC_URL`, `GESTOR_TEST_JDBC_USER` y, si aplica, `GESTOR_TEST_JDBC_PASSWORD`.

Pendiente: empacar/desplegar con el flujo habitual y comprobar en navegador la apariencia, la selección de archivo, la ayuda, la descarga y la importación desde JSF. La validación de XHTML fue estructural; no se realizó una prueba visual de navegador.
