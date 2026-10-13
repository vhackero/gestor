# Importar datos de fuente externa

Ruta: `plataforma/administracion/usuarios`, panel **Importar datos de fuente externa**.

La importación de usuarios desde `tbl_persona_externos` se ejecuta en segundo plano con consulta y guardado en bloques de 250. La pantalla muestra el avance cada tres segundos. La contraseña se cifra una vez por usuario y los catálogos se consultan una vez por trabajo. Cada persona conserva sus propias relaciones y el rol Estudiante.

Se conservan la selección de fuente, convocatoria, la vinculación opcional y los datos de alta existentes. El método de guardado utilizado por los demás módulos no cambia. El panel de carga de archivos a la tabla externa es una funcionalidad distinta.

Cada bloque utiliza una transacción independiente. Si falla, se revierte ese bloque; los anteriores permanecen guardados. El mensaje indica cuántos usuarios se importaron. Al reintentar se excluyen los nombres de usuario existentes en `tbl_persona`. Los registros agregados a la fuente después del inicio se procesan en una nueva importación.

Existe un trabajador y una cola de dos trabajos por instancia de aplicación. Se impide iniciar simultáneamente la misma fuente y convocatoria dentro de esa instancia. El permiso sigue siendo `IMP_FUEN_EXT`; sólo el usuario que inició el trabajo puede consultar su estado. El avance se conserva en memoria, por lo que un reinicio pierde la visualización del trabajo; los bloques confirmados permanecen en la base de datos.

## Instalación

Compilar y desplegar `basegestor` y `gestorWeb`. Ejecutar `codigoFuente/bd/R062_12102026_indice_importacion_fuente.sql` en los demás ambientes. Es idempotente y añade únicamente el índice por fuente, convocatoria e identificador de registro en `tbl_persona_externos`. Ya se aplicó en la base local.

## Validación

Compilación directa con Java 8 de las clases modificadas. Once pruebas verifican el cifrado único, relaciones por persona, ejecución asíncrona con y sin vinculación, exclusión de trabajos duplicados, avance de bloques confirmados, mensajes de error, lectura transaccional de catálogos y consultas paginadas sobre tablas temporales de MariaDB. La relación plan/convocatoria acepta resultados `Integer`, `Long`, `BigInteger`, `BigDecimal` y `Short`; se comprobó además la consulta con Hibernate 5.0.1 y MariaDB reales. Estas pruebas no importan personas en las tablas reales.

Queda validar la pantalla y los tiempos con una importación representativa después de desplegar en WildFly. No se ha ejecutado una compilación Maven completa ni una importación real de 15 000 usuarios como parte de esta validación.
