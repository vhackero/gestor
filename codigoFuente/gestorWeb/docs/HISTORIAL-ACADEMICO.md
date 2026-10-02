# Historial académico: columnas y calificación

## Flujo compartido

- Estudiante: `WebContent/views/private/gestionAprendizaje/alumnoView/constancias.xhtml`.
- Gestor: `WebContent/views/private/gestionAprendizaje/alumnoView/constanciasAdmin.xhtml`.
- El estudiante imprime mediante `ConstanciasBean.generarReporte`; el gestor mediante `ExpedienteAlumnoBean.generarReporte`, con su propio `historial` y `listaEventos` del estudiante consultado.
- Ambos obtienen el detalle mediante `GrupoParticipanteServiceImpl.getParticipanteByActaCerradaYconstancia2` y generan el PDF con `HistorialAcademicoReporte` y el parámetro `COLUMNAS_HISTORIAL_ACADEMICO`.
- Se corrigió la ruta del gestor, que aún cargaba la plantilla fija `.jasper` e ignoraba la selección. El arreglo contiene filas de DTO: sus campos se resuelven por nombre de propiedad, no por posición de columna.
- El contexto de trayectoria determina la persona consultada. La configuración no modifica esa autorización.
- El detalle incluye participaciones con acta cerrada y evento con constancia; el resumen se obtiene por `HistorialAcademicoRepo.consultaDatosHistorialAcademico`, con subconsultas independientes. El total de aprobadas no demuestra por sí solo que una fila del detalle tenga nota.

## Configuración

Ejecutar `codigoFuente/bd/R051_01102026_columnas_historial_academico.sql` en cada ambiente. El script no sobrescribe una selección existente; instalado en la base local `des_sisi_gestor` el 01/10/2026.

En **Administración → Configuración → Parámetros del sistema**, editar **COLUMNAS_HISTORIAL_ACADEMICO**. Las casillas permiten mostrar u ocultar las 15 columnas de datos para estudiante, gestor y PDF. Se conserva el permiso existente de edición de parámetros.

- Configuración global; se consulta al abrir/renderizar el historial y al imprimir.
- Todas las columnas se muestran por defecto; se requiere al menos una.
- El orden es el del catálogo compartido `ColumnasHistorial`; no se configura el orden.
- Sin el parámetro se conservan todas las columnas. Valores vacíos o claves desconocidas se rechazan al guardar.
- Los botones de constancias del estudiante son acciones, no columnas de datos del PDF; permanecen como antes.
- El encabezado de datos generales no depende de la selección de columnas.
- La tabla del PDF redistribuye sus anchos; adopta orientación horizontal cuando el conjunto es amplio.
- El PDF se compila desde el JRXML vigente con las columnas seleccionadas. La impresión del historial deja de depender del archivo binario `expediente_academico.jasper`.

## Calificación

La fuente es `rel_grupo_participante.calificacion_final`. Se conserva la nota numérica y se aplica NP según las bajas configuradas para el grupo. Una calificación ausente queda en blanco, sin convertirla a cero. Vista y PDF usan `EventoConstanciaDTO.calificacionFinalTexto`.

El código existente al iniciar esta revisión ya asignaba la nota antes de recorrer las bajas, y el JRXML ya evitaba imprimir null. No se dispuso de la matrícula ni del PDF reportado para atribuir su causa concreta. Debe verificarse el WAR desplegado si la instalación aún imprime null.

## Validación y despliegue

- Compilación de las clases modificadas con JDK 8 y dependencias locales.
- Pruebas de selección, validación antes de persistir, conservación de notas y NP por grupo, y generación de PDF con todas las columnas, dos columnas y una columna.
- PDF de prueba con calificación numérica, NP y ausencia de nota; revisión visual de la tabla completa.
- Regresión de la acción real `ExpedienteAlumnoBean.generarReporte` con contexto JSF simulado y generación real de PDF: selección Asignatura/Grupo y cambio a Asignatura/Calificación en la misma instancia. Se comprueba el texto del PDF, la identidad del estudiante consultado y que no se cargue el `.jasper` fijo. La prueba reprodujo el fallo antes de corregir esa ruta.
- XHTML validado como XML; no se ha ejecutado prueba de navegador ni despliegue del WAR.
- Para activar: aplicar el SQL del parámetro, reconstruir basegestor y gestorWeb, desplegar el WAR y volver a abrir el historial.

## Cabeceras editables

Las 15 cabeceras se resuelven desde **Administración → Personalización → Edición de textos** en ambas vistas, en el selector de columnas y en los PDF de estudiante y gestor. Buscar las claves con prefijo `gw.alumno.constancias.`.

| Columna | Clave de texto |
|---|---|
| Nombre de la asignatura | `gw.alumno.constancias.col.asignatura` |
| Nombre del grupo | `gw.alumno.constancias.col.nombreGrupo` |
| Estructura curricular | `gw.alumno.constancias.col.estructura` |
| Clave | `gw.alumno.constancias.col.clave` |
| Clave SEP | `gw.alumno.constancias.col.claveSep` |
| Créditos | `gw.alumno.constancias.creditos` |
| Periodo | `gw.alumno.constancias.col.periodo` |
| Tipo de evaluación | `gw.alumno.constancias.col.tipoEvaluacion` |
| No. acta | `gw.alumno.constancias.noActa` |
| Modalidad | `gw.alumno.constancias.modalidad` |
| Fecha de finalización | `gw.alumno.constancias.fechaFinalizacion` |
| Encuesta final | `gw.alumno.constancias.encuestaFinal` |
| Promedio de evaluaciones | `gw.alumno.constancias.promedioEvaluaciones` |
| Porcentaje de asistencias | `gw.alumno.constancias.porcentajeAsistencias` |
| Calificación final | `gw.alumno.constancias.calificacionFinal` |

- La estructura curricular pasa a tener una cabecera global editable, con valor inicial «Estructura curricular»; sus datos por fila no cambian.
- El botón/columna de constancias del estudiante usa la clave existente `gw.alumno.constancias.col.constancias`.
- Guardar en Edición de textos actualiza el catálogo de la aplicación; volver a consultar la vista o generar otro PDF toma el nuevo valor sin reiniciar. Un PDF ya descargado conserva su contenido.
- Aplicar `codigoFuente/bd/R052_01102026_textos_cabeceras_historial.sql` en cada ambiente. Usa la entrada existente `gw.alumno.constancias.historial` como referencia de funcionalidad y usuario, y no sobrescribe claves existentes. Instalado en la base local.
- Reconstruir basegestor y gestorWeb y desplegar el WAR. La ausencia de una clave usa la etiqueta predeterminada, para evitar mostrar identificadores internos.
- Validación: compilación JDK 8 y 5 pruebas de generación real de PDF. Ambas acciones de impresión consultan un catálogo simulado con ediciones sucesivas, comillas, acentos y símbolos; se verifica el texto del PDF. Sin validación de navegador ni despliegue.
