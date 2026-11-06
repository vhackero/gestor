# Inventario de reglas implementadas - Motor Curricular V2

## Propósito y corte

Este documento inventaría el comportamiento **efectivamente implementado** en el Motor Curricular V2 para compararlo con una actualización normativa posterior. No sustituye al documento institucional de reglas ni declara conformidad normativa futura.

| Dato | Valor |
| --- | --- |
| Fecha de corte | 2026-11-05 |
| Rama revisada | `feature/desacople_motor_historico` |
| Commit revisado | `21416718` |
| Método principal | `EvaluacionCurricularV2ServiceImpl.evaluarSombra(...)` |
| Resultado | `ResultadoEvaluacionCurricularV2DTO` |
| Efecto productivo | Ninguno: `efectosProductivosAplicados=false` |
| Etiqueta de versión actual | `PE2027-V1` |
| Configuración | Propiedades JVM `REGLAS_V2_VERSION`, `EVALUACION_V2_HABILITADA`, `REGLAS_V2_MODO_SOMBRA`, `CTR01_PERIODO_INICIO`, `CTR01_SEMESTRE_OBJETIVO` |

La etiqueta `PE2027-V1` es técnica. No acredita por sí misma vigencia, población, cohorte ni aplicabilidad institucional.

## Cómo leer el inventario

- **Implementada:** el evaluador ejecuta una decisión, clasificación o traza para el código.
- **Implementada con datos requeridos:** la conducta depende de campos que el adaptador debe entregar; sin ellos el resultado queda en revisión/no determinado.
- **Compatibilidad o revisión normativa:** existe código, pero debe compararse con especial cuidado contra el reglamento vigente.
- **No implementada:** el código está en el enum de trazabilidad, pero no tiene ejecución en el evaluador.

Las referencias de líneas corresponden al commit de corte. Los archivos de prueba identifican cobertura automatizada localizada; no se ejecutó una regresión adicional para elaborar este documento.

## Flujo común del motor

1. Normaliza las UD y conserva su estado histórico, causa pendiente, tipo, semestre, bloque, créditos, oferta y relaciones de seriación.
2. Determina situación académica, actividad académica, intentos y mejora de promedio.
3. Evalúa carga mínima, máxima e integrada; luego prioridad y regularización.
4. Evalúa control de avance de quinto, reincorporaciones, tipos de UD y seriación.
5. Evalúa últimos semestres, requisitos académicos de egreso y cobertura de trazabilidad.
6. Devuelve decisiones por UD, motivos, restricciones, prioridades y trazas; no inserta, actualiza ni elimina información escolar.

Implementación: `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java:120-243`.

## Reglas y controles implementados

| Código | Estado | Comportamiento actual | Fuente principal | Pruebas localizadas |
| --- | --- | --- | --- | --- |
| `RN-1.1` | Implementada con datos requeridos | Clasifica actividad académica y separa estado regular/irregular de baja temporal, reincorporación y límite de inactividad. | `EvaluacionCurricularV2ServiceImpl.java:1320-1395` | `EvaluacionCurricularV2Fase2ATest` |
| `RN-1.2` | Implementada | Cuenta reprobadas vigentes por UD; evita convertir otros estados en reprobación. | `...ServiceImpl.java:130-145,1053-1135` | Fases `1A`, `3A`, `4A` |
| `RN-1.3` | Implementada | Conserva `NO_CURSADA` y `BAJA_PARCIAL` como causas distintas de reprobación. | `...ServiceImpl.java:146-156` | Fases `3A`, `4A`, `5A` |
| `RN-1.4` | Implementada con datos requeridos | Evalúa intentos individualizados, excluye registros no computables cuando su evidencia lo indica y produce trazas por UD. | `...ServiceImpl.java:1165-1246` | `EvaluacionCurricularV2Fase3*Test` |
| `RN-1.5` | Implementada | Restringe reinscribir una UD acreditada sólo para mejora de promedio y usa oportunidades de mejora del contexto. | `...ServiceImpl.java:1248-1318` | `EvaluacionCurricularV2Fase2ETest` |
| `RN-2.2` | Implementada | Integra la evaluación sombra previa a reinscripción/carga; no sustituye oferta ni seleccionabilidad productiva. | `...ServiceImpl.java:236-240`; decisiones por UD | `EvaluacionCurricularV2ServiceTest`, pruebas de consumidores |
| `CTR-01` | Implementada | Activa el control de avance para candidatas de quinto desde periodo configurable; la candidata puede detonar la evaluación aunque el semestre objetivo no sea quinto. | `...ServiceImpl.java:2116-2490,4197-4220` | Fases `5A-5F`, pruebas de quinto en consumidores |
| `CTR-02` | Implementada | Registra excepciones tipificadas y ruta extraordinaria descriptiva; no concede efectos productivos. | `...ServiceImpl.java:1512-1535,4227-4305` | Fases `4*`, `5*` |
| `CTR-03` | Implementada | Mantiene separada la situación académica de una autorización de avance. | `...ServiceImpl.java:190-196` | Fases `1*`, `5*` |
| `CTR-04` | Implementada | Marca datos/categorías insuficientes y conserva resultados que sí son determinables. | `...ServiceImpl.java:157-161,236-238` | Fases `1*`, `5D`, `8*` |
| `RN-4.1` | Implementada con datos requeridos | Calcula mínimo de carga general de tres UD para semestres posteriores al primero; contempla excepciones tipificadas si no hay suficientes UD elegibles. | `...ServiceImpl.java:1397-1535` | `EvaluacionCurricularV2Fase3*Test` |
| `RN-4.2` | Implementada | Calcula máximo individual de carga y aplica restricciones concurrentes, incluyendo RN-6.4 y RN-6.1 cuando proceden. | `...ServiceImpl.java:1538-1648` | Fases `3*`, pruebas de Simulador/Asistente |
| `RN-4.3` | Implementada | Consolida carga mínima y máxima en una evaluación integrada con UD consideradas/descartadas. | `...ServiceImpl.java:1650-1725` | `EvaluacionCurricularV2Fase3DTest` |
| `RN-5.1` | Implementada | Identifica rezago leve y su interacción con prioridad de regularización. | `...ServiceImpl.java:1846-1895` | `EvaluacionCurricularV2Fase4*Test` |
| `RN-5.2` | Implementada | Evalúa rezago severo y limita la carga conforme a la ruta de regularización. | `...ServiceImpl.java:1896-2045` | `EvaluacionCurricularV2Fase4*Test` |
| `RN-6.1` | Implementada con datos requeridos | Consolida acceso ordinario, condicionado, no procedente, revisión o resolución extraordinaria para UD candidatas de quinto. | `...ServiceImpl.java:2116-2490` | Fases `5A-5F`; pruebas de Expectativa |
| `RN-6.1.1` | Implementada | Identifica las obligatorias pendientes de la base de avance y su causa. | `...ServiceImpl.java:2116-2241` | `EvaluacionCurricularV2Fase5ATest` |
| `RN-6.1.2` | Implementada | Evalúa condiciones comunes, incluida seriación de cada candidata de quinto, sin extender por sí sola un bloqueo a otras UD. | `...ServiceImpl.java:2242-2352` | Fases `5A-5F` |
| `RN-6.1.3` | Implementada | Identifica supuestos tipificados concurrentes; no agrega un máximo artificial de pendientes. | `...ServiceImpl.java:2353-2415` | `EvaluacionCurricularV2Fase5CTest` |
| `RN-6.1.4` | Implementada con datos requeridos | Clasifica oferta/cupo de pendientes base: sin oferta, fuera de bloque, sin grupo, sin cupo o revisión. | `...ServiceImpl.java:2419-2449` | `EvaluacionCurricularV2Fase5DTest` |
| `RN-6.1.5` | Implementada con datos requeridos | Determina impedimentos prevalentes: RN-6.4, seriación, sanción y restricción normativa PE. | `...ServiceImpl.java:2450-2465` | `EvaluacionCurricularV2Fase5ETest` |
| `RN-6.1.6` | Implementada como ruta descriptiva | Cuando no existe supuesto tipificado y no hay impedimento, identifica necesidad de resolución extraordinaria; no la ejecuta. | `...ServiceImpl.java:2450-2465` | `EvaluacionCurricularV2Fase5ETest` |
| `RN-6.2` | Implementada con datos requeridos | Identifica incorporación inicial diferida sin realizar inscripción. | `...ServiceImpl.java:2486-2506` | `EvaluacionCurricularV2Fase6ATest` |
| `RN-6.3` | Implementada | Prioriza UD reprobadas y conserva oferta, cupo y seriación como dimensiones independientes. | `...ServiceImpl.java:1760-1845` | `EvaluacionCurricularV2Fase4ATest` |
| `RN-6.4` | Implementada | Con cuatro o más reprobadas vigentes únicas, restringe a regularización y evita contar intentos o bajas como reprobaciones. | `...ServiceImpl.java:1896-2045` | Fases `4*`, `5A`, `6B`, `8A`, `9B` |
| `RN-6.5` | Implementada con datos requeridos | Evalúa reincorporación tras periodo no cursado, trayectoria previa y UD posteriores individualmente. | `...ServiceImpl.java:2507-2785` | Fases `6A`, `6B` |
| `RN-6.6` | Implementada con datos requeridos | Evalúa reincorporación tras baja temporal sin inferir datos ausentes. | `...ServiceImpl.java:2786-3062` | Fases `6C`, `6D` |
| `RN-6.7` | Implementada con datos requeridos | Evalúa reincorporación/caso posterior a baja parcial, conservando tipo y causa de pendiente. | `...ServiceImpl.java:3063-3315` | Fases `6E`, `6F` |
| `RN-6.8` | Implementada | Actualiza la proyección de trayectoria tras regularización en el resultado sombra. | `...ServiceImpl.java:3324-3570` | `EvaluacionCurricularV2Fase6ACierreTest` |
| `RN-7.1` | Implementada con datos requeridos | Trata optativas, su condición pendiente/reprobada y disponibilidad, sin confundirlas con obligatorias. | `...ServiceImpl.java:3723-3899` | `EvaluacionCurricularV2Fase7ATest`, `Fase7BTest` |
| `RN-7.1.1` | Implementada con datos requeridos | Clasifica optativas requeridas y adicionales; el umbral/catálogo debe provenir del contexto del plan. | `...ServiceImpl.java:3831-3899` | `EvaluacionCurricularV2Fase7BTest` |
| `RN-7.2` | Implementada con datos requeridos | Aunque el método conserva el nombre histórico `evaluarElectivasRn72`, la ruta actualmente invocada trata optativas reprobadas: requerida si aún no se cumplen ocho, o posible adeudo adicional sujeto a catálogo/RN-7.1.1. No crea una categoría `ELECTIVA`. | `...ServiceImpl.java:3901-3925` | `EvaluacionCurricularV2Fase7BTest` cubre optativa reprobada y frontera de ocho; falta una prueba nombrada explícitamente `RN-7.2`. |
| `RN-7.3` | Implementada con datos requeridos | Consolida optativas/electivas y evita conflictos materiales de prioridad; la relación verificable de sustitución sigue siendo requisito de fuente. | `...ServiceImpl.java:4042-4187` | `EvaluacionCurricularV2Fase7CTest`, `Fase7DTest` |
| `RN-8` | Implementada | Regla contenedora de seriación; diferencia relación directa e indirecta y no equipara oferta a requisito curricular. | `...ServiceImpl.java:120-185,699-756` | Fases `8A-8D` |
| `RN-8.1` | Implementada | Evalúa seriación interbloque y su resultado por relación/UD. | `...ServiceImpl.java:251-401` | `EvaluacionCurricularV2Fase8ATest` |
| `RN-8.2` | Implementada | Evalúa seriación intersemestral. | `...ServiceImpl.java:402-514` | `EvaluacionCurricularV2Fase8BTest` |
| `RN-8.3` | Implementada | Recorre y evalúa seriación interanual. | `...ServiceImpl.java:515-698` | `EvaluacionCurricularV2Fase8CTest` |
| `RN-9.1` | Implementada con datos requeridos | Evalúa reinscripción extemporánea en forma descriptiva y sin activación productiva. | `...ServiceImpl.java:757-829` | `EvaluacionCurricularV2Fase9ATest` |
| `RN-9.2` | No implementada | Está declarada en el catálogo, sin referencia en la implementación Java localizada. | `CodigoReglaCurricularV2.java:4-8` | No localizada |
| `RN-9.3` | Implementada | Para S7/S8 evalúa estructura, componentes y seriación; no usa un umbral autónomo de 50 % de créditos ni reactiva RN-6.1. | `...ServiceImpl.java:870-956` | `EvaluacionCurricularV2Fase9BTest` |
| `RN-9.4` | Implementada con datos requeridos | Evalúa requisitos académicos de egreso y componentes finales. Separa cumplimiento curricular del adeudo de optativa adicional: éste conserva regularización, pero no bloquea por sí solo cumplimiento curricular/certificación/titulación. | `...ServiceImpl.java:960-1052` | `EvaluacionCurricularV2Fase9CTest` |
| `RN-9.5` | Implementada | Genera trazas de cobertura de contexto y UD con versión, decisiones, causas y datos faltantes; la persistencia depende del servicio de trazabilidad. | `...ServiceImpl.java:843-869`; `TrazabilidadEvaluacionCurricularV2ServiceImpl` | `TrazabilidadEvaluacionCurricularV2ServiceTest` |
| `RN-10` | No implementada | Sólo figura en el enum; no se localizó evaluación ni consumidor que la ejecute. | `CodigoReglaCurricularV2.java:4-8` | No localizada |
| `RN-10.1` | No implementada | Sólo figura en el enum; no se localizó fuente ni evaluación para titulación/servicio social. | `CodigoReglaCurricularV2.java:4-8` | No localizada |

## Estados, invariantes y decisiones transversales

El motor reconoce y conserva los estados normalizados `ACREDITADA`, `REPROBADA`, `NO_CURSADA`, `BAJA_PARCIAL`, `EN_CURSO`, `NO_INSCRITA` y `NO_DETERMINADO`.

- `NO_CURSADA`, `BAJA_PARCIAL` y `NO_INSCRITA` no se convierten en `REPROBADA` por el evaluador.
- Reprobaciones, intentos y oportunidades son dimensiones diferentes: una UD puede tener varios intentos y constituir un solo pendiente vigente.
- Oferta, cupo y habilitación académica se conservan como dimensiones separadas.
- Las condiciones concurrentes se acumulan; el motor no reduce el expediente a una única etiqueta.
- Las decisiones se emiten en sombra; la reevaluación no reserva cupo, no altera oferta, grupos, selección, inscripción, trayectoria ni calificaciones.

Fuentes: `EvaluacionCurricularV2ServiceImpl.java:120-243,1165-1318,1397-1725,4439-4460`.

## Datos que condicionan la cobertura

| Dominio | Datos que requiere el motor | Si no están disponibles |
| --- | --- | --- |
| Identidad curricular | Plan, versión/cohorte, ID/clave única de UD, tipo, semestre, bloque y créditos. | `NO_DETERMINADO` o `REQUIERE_REVISION`; no debe inferirse por nombre. |
| Historial | Estado, causa, secuencia e identidad de intentos; resultados y bajas autorizadas. | No puede determinar oportunidades, recursamientos o causa con certeza. |
| Oferta | Oferta, grupo activo, cupo y causa institucional de indisponibilidad. | Separa `SIN_OFERTA`, `SIN_CUPO` y revisión; no declara selección confirmada. |
| Seriación | Relaciones directas, indirectas, interbloque, intersemestrales e interanuales. | No sustituye ausencia de relación por cumplimiento. |
| Optativas | Catálogo por plan/cohorte, requeridas/adicionales y sustituciones verificables. | No debe duplicar adeudos ni inventar una sustituta. |
| Trayectoria | Bajas temporales/parciales, reincorporación, admisión e incorporación. | Conserva el dato verificable y marca revisión para el resto. |
| Egreso | Componentes finales y sus requisitos por plan. | No emite conclusión definitiva de egreso. |

## Configuración y trazabilidad actuales

La configuración es temporal y se obtiene de propiedades del proceso en `ConfiguracionEvaluacionCurricularV2ServiceImpl.java:13-50`:

| Propiedad | Predeterminado actual | Uso |
| --- | --- | --- |
| `REGLAS_V2_VERSION` | `PE2027-V1` | Etiqueta del resultado/traza. |
| `EVALUACION_V2_HABILITADA` | `true` | Disponibilidad de la evaluación V2. |
| `REGLAS_V2_MODO_SOMBRA` | `true` | Guardia para mantener la evaluación no productiva. |
| `CTR01_PERIODO_INICIO` | `2027-1` | Inicio de vigencia técnica para CTR-01. |
| `CTR01_SEMESTRE_OBJETIVO` | `5` | Semestre configurado para CTR-01. |

El resultado registra la versión, reglas evaluadas, decisiones por UD, condiciones, excepciones y trazas. La presencia de traza no equivale a que la fuente institucional esté completa.

## Códigos no equivalentes a una regla evaluada

`RN-4` y `RN-5` son códigos contenedores del catálogo; la lógica se registra mediante sus subreglas (`RN-4.1` a `RN-4.3`, `RN-5.1` y `RN-5.2`). `RN-9.2`, `RN-10` y `RN-10.1` no tienen implementación ejecutable localizada. La existencia de cualquier código en `CodigoReglaCurricularV2` no debe usarse como evidencia de cobertura.

## Consumidores integrados

El mismo resultado V2 es consumido, mediante adaptadores/fachadas, por Malla Curricular, Simulador y Asistente de inscripción/orientación. Expectativa de logro y Proyección de carga vuelven a evaluar un contexto hipotético mediante el mismo servicio; no contienen un segundo motor académico.

Referencias principales:

- `SimuladorCurricularV2ServiceImpl.java:35-61`.
- `MallaCurricularV2FacadeImpl.java:27-39`.
- `AsistenteCurricularV2FacadeImpl.java:82-88`.
- `AsistenteInscripcionServiceImpl.java:343-360,1388,3515-3518`.

## Puntos de comparación recomendados para la actualización normativa

1. Sustituir la etiqueta técnica de versión sólo después de definir versión institucional, vigencia, planes y cohortes aplicables.
2. Comparar texto y condiciones de `RN-1.5`, `RN-6.1` completo, `RN-7.1` a `RN-7.3`, `RN-8`, `RN-9.3`, `RN-9.4` y `RN-9.5` contra el nuevo documento.
3. Confirmar si el tipo `ELECTIVA` debe retirarse de la evaluación vigente o mantenerse sólo para compatibilidad histórica; el código `RN-7.2` requiere revisión explícita.
4. Confirmar catálogo de optativas requeridas/adicionales, relaciones de sustitución y componentes finales por plan/cohorte antes de convertir resultados parciales en conclusiones definitivas.
5. Tratar `RN-10` y `RN-10.1` como fuera de implementación actual hasta contar con fuente verificable y módulo responsable.
