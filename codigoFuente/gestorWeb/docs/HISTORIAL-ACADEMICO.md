# Historial académico: columnas y calificación

## Flujo compartido

- Estudiante: `WebContent/views/private/gestionAprendizaje/alumnoView/constancias.xhtml`.
- Gestor: `WebContent/views/private/gestionAprendizaje/alumnoView/constanciasAdmin.xhtml`.
- Ambas vistas usan `ConstanciasBean`, `GrupoParticipanteServiceImpl.getParticipanteByActaCerradaYconstancia2` y la misma impresión.
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
- XHTML validado como XML; no se ha ejecutado prueba de navegador ni despliegue del WAR.
- Para activar: aplicar el SQL del parámetro, reconstruir basegestor y gestorWeb, desplegar el WAR y volver a abrir el historial.
