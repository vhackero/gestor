# Actualización inicial del Motor Curricular V2 - Octubre 2026

## Alcance

- Rama: `feature/desacople_motor_historico`.
- Fuente normativa: `P2024-2027- Reglas de negocio_Octubre 2026.docx`.
- SHA-256 calculado: `cda40765c8095b09e27026bef117338926f95da124ab8439c903faaf7baf7165`.
- Modalidad: evaluación en sombra, sin inscripción, reinscripción, oferta, grupos ni persistencia productiva.

## Correcciones aplicadas

| Regla | Ajuste aplicado |
| --- | --- |
| RN-1.5 | Una UD acreditada queda `NO_ELEGIBLE` para reinscripción solicitada únicamente por mejora de promedio. Se conserva su evidencia histórica. |
| RN-6.1 | La aplicabilidad se determina por una UD candidata de S5 desde `2027-1`, aunque el semestre objetivo sea distinto. El contrato admite `idsUnidadesPretendidas`; sin candidatos explícitos conserva la compatibilidad con `semestreObjetivo=5`. |
| RN-6.1.3 | Se retiró `SIN_DEPENDENCIA_SERIACION` como supuesto/excepción autónoma. La seriación continúa como condición de compatibilidad por UD. |
| RN-7.2 | La evaluación trata optativas reprobadas mediante RN-7.1/RN-7.1.1; no crea elegibilidad ni una categoría vigente de `ELECTIVA`. La clasificación precisa de un adeudo como adicional permanece sujeta al catálogo institucional. |
| RN-9.3 | Se retiró el bloqueo por porcentaje de créditos. S7/S8 conserva seriación, RN-6.4 y requisitos específicos verificables del PE. |
| RN-9.4 | Se retiró el requisito de una electiva mínima. El cumplimiento académico conserva obligatorias y ocho optativas requeridas. |
| Simulador | Se retiró la validación complementaria `ELECTIVA_MAXIMA_EXCEDIDA`. Se conservan las validaciones complementarias de optativas por bloque/clave y prioridades. |

## Límites pendientes de datos

- Catálogo oficial por PE, plan, versión y cohorte de las trece optativas y su distribución por bloque.
- Clasificación verificable de adeudo de optativa requerida/adicional y sus sustitutas.
- Componentes finales y sus requisitos por plan/version/cohorte.
- Vigencia institucional, población e instrumento para completar trazabilidad RN-9.5.

La ausencia de estos catálogos no reintroduce el umbral de 50 %, la electiva mínima ni la mejora de promedio; sólo impide automatizar las decisiones que dependen específicamente de ellos.

## Verificación

| Validación | Resultado |
| --- | --- |
| Pruebas `basegestor` | 762 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| Compilación `basegestor` | SUCCESS. |
| Compilación `gestorWeb` | SUCCESS. |

La cantidad total de pruebas cambia respecto de la línea previa porque se retiraron
casos que validaban la categoría normativa eliminada de `ELECTIVA` y se sustituyeron
por casos de optativas reprobadas conforme a RN-7.2 definitiva. No se suprimió
cobertura de las reglas vigentes.

## Riesgo conocido

Persisten etiquetas y pantallas históricas de configuración/visualización de `Electiva` fuera del motor V2. No se modificaron en este ajuste porque requieren migración controlada de catálogos y de la presentación de Malla/Asistente; no deben volver a gobernar una decisión académica V2.
