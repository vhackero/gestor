# Ajuste de componentes academicos del Asistente

## Objetivo

Aplicar los lineamientos visuales y de contenido de las pestañas **Situacion academica** y **UD prioritarias** del Asistente de inscripcion y orientacion educativa. El ajuste conserva al Motor Curricular V2 como fuente de decisiones normativas y mantiene los calculos legacy que son informativos o complementarios.

## Situacion academica

- Se reorganizo la vista en avance curricular acreditado, tiempo y plazo, trayectoria y permanencia, resumen actual y resumen global.
- Los indicadores de avance muestran explictamente que el porcentaje representa creditos acreditados y no tiempo utilizado.
- El resumen actual separa UD registradas/en curso, obligatorias, optativas requeridas, optativas adicionales y bajas parciales.
- El resumen global muestra UD acreditadas, obligatorias acreditadas, optativas requeridas acreditadas, optativas adicionales acreditadas y UD pendientes de regularizar.
- Las electivas no se muestran en los nuevos componentes de resumen, conforme al lineamiento recibido.
- La trayectoria conserva la duracion nominal y el plazo de permanencia ya calculados con las fuentes institucionales existentes.

## UD prioritarias

La tabla extensa fue sustituida por seis tarjetas de consulta progresiva:

1. UD reprobadas pendientes.
2. UD con antecedentes de recursamiento.
3. Bajas parciales aplicadas.
4. Bajas temporales autorizadas.
5. Sustituciones de optativas adicionales.
6. Oportunidades de acreditacion.

Cada detalle por UD usa clave, nombre, estado, oferta y prioridad disponibles. `REPROBADA` y `NO_ACREDITADA` se consideran el mismo grupo visual de regularizacion, sin transformar `BAJA_PARCIAL` en reprobacion.

## Datos verificables y pendientes

- Los intentos exactos por UD no se reciben en el contexto de esta vista; por ello la tarjeta de recursamiento no afirma oportunidades consumidas ni disponibles. RN-1.4 sigue siendo la regla aplicable.
- La vista recibe bajas por UD, pero no la autorizacion completa, motivo institucional, periodos consumidos o fechas de una baja temporal. Esos datos se indican como consulta de expediente, no se inventan.
- No existe una fuente verificable en el contexto actual para sustituciones de optativas adicionales. La tarjeta se mantiene como referencia y declara la necesidad de consulta institucional.

## Precedencia y no mutacion

Las decisiones de estado, prioridad, restriccion y seriacion siguen proviniendo de `ResultadoEvaluacionCurricularV2DTO`. Los nuevos indicadores son de presentacion y no modifican inscripcion, oferta, seleccion, grupos ni trayectoria.

## Archivos modificados

- `codigoFuente/gestorWeb/WebContent/views/private/gestionAprendizaje/alumnoView/tablaCurricularAsistida.xhtml`
- `codigoFuente/gestorWeb/src/main/java/mx/gob/sedesol/gestorweb/beans/gestionaprendizaje/alumnoview/TablaCurricularAsistidaBean.java`

## Riesgos y validacion manual

Debe validarse en WildFly con perfiles que incluyan UD acreditadas, reprobadas, bajas parciales y baja temporal. La vista no debe usar los valores ilustrativos del documento como topes normativos ni mostrar datos no disponibles como hechos.

## Validacion tecnica

- `basegestor`: 773 pruebas, 0 fallas, 0 errores, 0 omitidas.
- `gestorWeb`: 13 pruebas, 0 fallas, 0 errores, 0 omitidas.
- Empaquetado de ambos modulos: `SUCCESS`.
