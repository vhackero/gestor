# Pendientes de matrícula en dispersiones existentes

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
