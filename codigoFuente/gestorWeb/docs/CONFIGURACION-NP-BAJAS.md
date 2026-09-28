# Configuración de bajas que se representan como NP

El parámetro `TIPOS_BAJA_NP_ACTA` se edita en **Administración → Parámetros del sistema**.
Aunque conserva el nombre acordado para el acta, es compartido por los apartados indicados abajo.

## Instalación y operación

1. Ejecutar `codigoFuente/bd/R050_28092026_parametro_tipos_baja_np.sql` en la base del ambiente.
2. Desplegar el código actualizado y la plantilla `expediente_academico.jasper` incluida.
3. Buscar `TIPOS_BAJA_NP_ACTA` en Parámetros del sistema y editar su valor.
4. Volver a consultar la pantalla o generar el PDF para tomar el valor actualizado.

El script crea el parámetro con `1,2,7,8` y no sobrescribe configuraciones existentes.
Los IDs se verificaron en el catálogo local `cat_tipo_bajas`:

| ID | Tipo |
| --- | --- |
| 1 | Parcial |
| 2 | Temporal |
| 7 | Temporal con asignaturas |
| 8 | Temporal sin asignaturas |

En otro ambiente, verificar el catálogo antes de elegir los IDs:

```sql
SELECT id_tipo_baja, nombre FROM cat_tipo_bajas ORDER BY id_tipo_baja;
```

El valor admite enteros positivos separados por comas, espacios alrededor de los IDs y duplicados.
Los duplicados se eliminan al leer. No se aceptan listas vacías, letras, IDs negativos, cero ni separadores
incompletos. Los IDs deben corresponder al catálogo del ambiente; el formato numérico se valida al guardar.

Ejemplos: `1,2,7,8`, `1,7`, `8`.
Si el parámetro aún no existe se usa `1,2`, por compatibilidad con la regla anterior del acta.
Un valor existente inválido produce error, en lugar de generar un reporte con una lista parcial o distinta.

## Alcance

- Acta PDF: `CalificacionGpoEventoCapBean.descargarPlantillaCalificaciones` →
  `EventoCapacitacionServiceImpl.obtieneDetalleActa` → `EventoCapacitacionRepo.obtenerBajas`.
- Descarga de calificaciones por grupo desde el expediente del evento:
  `ExpedienteEventoBean.descargarPlantillaCalificaciones` → `HistorialAcademicoRepo.consultaCuerpoPdf`.
- Resumen del historial: `HistorialAcademicoRepo.consultaDatosHistorialAcademico`. Aplica la misma selección
  de bajas a no presentadas y a las exclusiones de promedio, créditos, aprobadas y reprobadas.
- Detalle del historial, constancias, expediente del alumno y sus consumidores de malla y tabla curricular:
  `GrupoParticipanteServiceImpl.getParticipanteByActaCerradaYconstancia2` →
  `HistorialAcademicoRepo.consultaTiraMateriasBaja2`.
- PDF del expediente académico: traduce el indicador interno `666` a `NP`, conserva notas numéricas
  y admite calificaciones nulas. Se incluye el JRXML y su Jasper compilado.

Solo se toman bajas con `contabilizar = 1`. Se conserva la asociación del acta por evento/curso y
la del historial y descarga por grupo por persona/grupo. Configurar un tipo no convierte automáticamente
todas las materias del estudiante en NP: debe existir la baja correspondiente al contexto consultado.

Se corrigió el caso de una materia sin baja que quedaba con calificación visible nula cuando el alumno
tenía una baja en otro grupo. También se alineó el alias `idpersonamoodle` con la proyección de bajas del acta.

Las notas persistidas en `rel_grupo_participante` no se modifican. La representación y el resumen
se recalculan al consultar. Se conservan las reglas propias de inscripción y regularidad; los consumidores
del historial reciben el indicador NP resultante de este parámetro.

## Validación

- Compilación de las clases modificadas con JDK 8 y dependencias locales del proyecto.
- Ocho pruebas JUnit en `BajasNpConfigurablesTest`: formato, actualización del valor entre consultas,
  rechazo al guardar, acta, lista vacía, vinculación SQL y notas de grupos distintos.
- MariaDB local: 30 escenarios sobre las cuatro consultas reales (historial, bajas del detalle,
  calificaciones por grupo y bajas del acta). Se variaron tipos 1, 2, 3, 7 y 8, listas configuradas
  y el indicador contabilizar. Las variaciones se realizaron exclusivamente en tablas temporales
  de una conexión, sin modificar bajas o calificaciones persistentes.
- Plantilla de expediente compilada con JasperReports 6.17.0 y PDF de prueba exportado; se comprobó NP,
  nota numérica y calificación nula.
- PDFs de acta y calificaciones por grupo exportados con sus plantillas compiladas actuales; ambos mostraron NP.
- Parámetro registrado en la base local con valor "1,2,7,8".
- Queda pendiente la validación de navegación y edición en el navegador tras desplegar.

