# Pendientes de matrícula en dispersiones existentes

## Descarga en dispersión general y grupos compartidos

### Carga inicial y cargador de descarga

La limpieza general de campos ya no consulta los procesos de matrícula existente. Esa consulta ejecuta comprobaciones de inscripciones, bajas y seriación que no se necesitan para mostrar el formulario general. Sólo se carga al entrar en matrícula existente o al usar su botón específico de limpiar; se elimina también la carga duplicada al entrar en esa modalidad.

Las tres descargas de pendientes se generan por AJAX con el diálogo Cargando visible. Sólo cuando el XLSX está preparado se inicia una segunda petición de descarga, sin repetir la consulta ni la generación. El cargador se cierra al completar la generación, sin pendientes, ante un error de servidor controlado o un error AJAX. La descarga final evita validar filtros ajenos a la acción. El XLSX generado se conserva en memoria en la vista hasta sustituirlo o cerrar la vista; la escritura de filas sigue utilizando SXSSF.

Validación de este ajuste: compilación Java 8, XML de las cuatro vistas y cuatro pruebas satisfactorias, incluida la limpieza general sin consultas de otros módulos, la limpieza de matrícula existente con sus catálogos, preparación/lectura del archivo y prevención de descarga de un archivo anterior cuando no hay pendientes. La mejora de tiempo y el comportamiento visual deben comprobarse tras desplegar; no se midió el tiempo de la pantalla en WildFly.

Las tablas de **Dispersión** y **Dispersión en grupos compartidos** también incluyen el icono **Descargar Excel de alumnos pendientes**, junto a Matricular usuarios. Se permite consultar pendientes antes de terminar de generar los grupos; la descarga no crea grupos ni matrícula y no modifica datos.

La exportación consulta nuevamente los candidatos mediante los mismos selectores que emplea la matrícula de cada modalidad (`obtenerPersonasMatriculacion` y `obtenerPersonasMatriculacionCompartidos`). Conserva sus reglas vigentes de inscripción, proceso, bajas, seriación y participación previa. No sustituye esas reglas por las del módulo de dispersión existente.

Los datos complementarios se consultan por lotes de hasta 500 alumnos, con una sola fila por persona. En compartidos se conserva el plan y la asignatura de inscripción del alumno, aunque el grupo se origine en otro plan. Si una persona tiene varias inscripciones coincidentes, se prioriza el programa de la dispersión y luego el identificador de programa, tomando la inscripción más reciente dentro de éste. Se reutiliza el formato y el generador XLSX existente. En estas modalidades el proceso origen y destino del archivo son el mismo proceso seleccionado.

No hay migración de BD ni permisos nuevos: el acceso conserva el del módulo correspondiente. Si no hay candidatos se informa que no existen alumnos elegibles pendientes y no se genera un archivo vacío.

Validación de la ampliación: compilación directa Java 8, validación XML de ambas vistas y tres pruebas satisfactorias (consultas anteriores, descarga general/compartida con dos planes y duplicados, y escritura/lectura de XLSX). Las consultas se ejecutaron en MariaDB con tablas temporales; no se matricularon usuarios ni se alteraron registros académicos persistentes. Falta desplegar y verificar la descarga desde el navegador.

## Comportamiento

La columna **Alumnos pendientes de matricular**, la selección final de matrícula y el Excel aplican el mismo criterio en `DispersionPendientesSql`:

- Inscripción dentro del intervalo del proceso destino, para el programa y plan asociados a ese proceso.
- Sin participación registrada en la asignatura equivalente por nombre normalizado en el proceso actual. Se reconoce la matrícula anterior a una reinscripción dentro del mismo intervalo y la participación en un grupo vinculado a la dispersión del proceso. Un intento histórico de otro proceso no excluye por sí solo un recursamiento.
- Sin baja activa (`contabilizar = 1`) para la persona, inscripción y proceso. Para bajas antiguas sin ID de inscripción se exige el mismo proceso, plan y programa; no se extrapolan bajas de otro proceso o asignatura.
- Seriación cumplida: antecedente aprobado con calificación final y mínimo aprobatorio. La equivalencia del antecedente entre planes se resuelve por nombre normalizado.
- Alumno distinto: las inscripciones duplicadas no multiplican el conteo ni el Excel.

El criterio se basa en las participaciones de Gestor (`rel_grupo_participante`); no realiza una auditoría independiente de inscripciones de Moodle.

## Matrícula parcial y reintentos

El proceso destino continúa disponible mientras tenga candidatos elegibles, aunque ya tenga una dispersión o grupos. La búsqueda no oculta un programa por la mera existencia de su dispersión destino.

Al matricular se reutiliza la dispersión destino y se evita duplicar las relaciones con sus grupos. La creación/reutilización adquiere un bloqueo sobre el proceso y luego vuelve a consultar los pendientes. Si no hay candidatos, no se crea una dispersión vacía.

El resumen cuenta las participaciones efectivamente guardadas para los alumnos seleccionados e informa los pendientes posteriores. Se mantiene la distribución y la política de cupo existentes de este módulo.

## Excel

Cada fila tiene un icono Excel con el título **Descargar Excel de alumnos pendientes**. Consulta nuevamente los pendientes al descargar y genera un `.xlsx` con:

Matrícula, nombre, correo institucional, plan, programa/asignatura, clave, semestre, bloque, proceso con dispersión, proceso a matricular, fecha de inscripción y total de alumnos pendientes.

Los identificadores se escriben como texto, preservando ceros y evitando que los datos se interpreten como fórmulas. Se utiliza un libro con ventana de 100 filas para limitar el consumo de memoria; sus archivos temporales se eliminan al terminar.

## Validación realizada

- Compilación de los archivos modificados con Java 8 y dependencias locales, incluidas JSF y POI 3.13.
- `DispersionPendientesSqlTest`: ejecuta las consultas reales en MariaDB sobre tablas temporales que sustituyen únicamente los nombres dentro de su conexión. Cubre 15 casos, igualdad del conteo/exportación/selección, reintento con dispersión destino existente, relaciones de grupo sin duplicados y desaparición de pendientes tras registrar la matrícula.
- `DispersionPendientesExcelTest`: genera y vuelve a abrir un XLSX de 120 alumnos, verificando matrícula con ceros, acentos, texto que comienza con `=`, valores vacíos y total.
- La consulta del Excel asigna un alias distinto a cada columna para la autodetección de Hibernate. La prueba SQL también valida los nombres reales devueltos por JDBC; evita el error `NonUniqueDiscoveredSqlAliasException` por columnas llamadas `nombre`.
- Pruebas sin cambios persistentes en los datos académicos.

La prueba SQL requiere `GESTOR_TEST_JDBC_URL`, `GESTOR_TEST_JDBC_USER` y, si aplica, `GESTOR_TEST_JDBC_PASSWORD`. Si faltan las dos primeras variables, se omite mediante JUnit Assume.

Pendiente: compilar/empacar con el flujo habitual, desplegar en WildFly y comprobar en navegador la búsqueda, la descarga y la matrícula real de Moodle. No se aplicó una migración de base de datos ni se desplegó la aplicación.
