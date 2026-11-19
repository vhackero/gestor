# Resultado Bloque 6 - RN 20261119

## Documento y alcance

| Dato | Valor |
| --- | --- |
| Documento rector | `P2024-2027- Reglas de negocio_Octubre 2026_20261119_0055.docx` |
| Fecha de modificación | `2026-11-19T06:53:31Z` |
| SHA-256 | `e93a476672d72ba5320ee3e26f14a15a11328951f8ded803290c58ff2e1322aa` |
| Versión configurada | `RN-PE2024-2027-act-20261119` |
| Modo | Sombra; sin escrituras en Inscripción, bajas, grupos, oferta ni calificaciones. |

## Cambios aplicados

| Criterio | Implementación | Evidencia |
| --- | --- | --- |
| 1.4 | El resultado por UD separa oportunidades consumidas, disponibles y `recursamientos`; una baja aplicada no consume oportunidad. | `Bloque6Rn20261119Test` |
| 2.2 | El adaptador cruza participación, grupo, acta, calificación y baja aplicada por persona/grupo. Acta cerrada con NULL queda en revisión; acta abierta con NULL queda en curso; baja con calificación 1-59 o >=60 queda en revisión. | `Bloque2HistorialOportunidadesRn20261106Test` |
| 2.3 | Se incorporó el contrato de lectura `ExcepcionInstitucionalCurricularV2DTO`; conserva la restricción y agrega aviso trazable cuando la excepción vigente y aplicable está disponible. | Contrato y evaluación V2; la fuente SIGIE queda pendiente. |
| 4.3 | La B2 pendiente por seriación cuenta una sola vez para mínimo y máximo de la carga inicial. | `PreparacionDecisionesPendientesV2Test` |
| 6.1.5 | El recursamiento de quinto se trata como regularización, incluso con RN-6.4; se retiró el marcador de definición pendiente Q5. | `PreparacionDecisionesPendientesV2Test` |
| 7.1 / 7.1.1 | Con 1 a 3 adeudos y referencia >=5 se permiten requeridas faltantes después de prioridad; el bloque se toma de la oferta autorizada. El contrato ya expone `sustitucionesUtilizadas` si la fuente lo acredita. | `Bloque3OfertaYPreseleccionRn20261106Test`, `Bloque6Rn20261119Test` |
| 8.1 | Se retiró `PENDIENTE_DE_CONFIRMACION`: una antecedente en revisión conserva la consecuente `PENDIENTE_POR_SERIACION` fuera de carga confirmada. | `EvaluacionCurricularV2Fase8ATest` |
| 6.7 | El enlace de bajas conserva como condición `contabilizar=1`, persona, grupo y periodo; una solicitud sin esa evidencia queda en revisión. | `InscripcionRepository` y pruebas históricas. |

## Vacíos reales

| Vacío | Tratamiento vigente | Fuente requerida |
| --- | --- | --- |
| Excepciones institucionales | Contrato de sólo lectura listo; sin registro no modifica una evaluación. | Catálogo SIGIE y flujo de personal autorizado (G4). |
| Ajuste o confirmación tardía B2 | Permanece en revisión cuando no hay fecha institucional. | Calendario por periodo/bloque (G3). |
| Sustituciones de optativa adicional | El adeudo no se duplica; sin relación original-sustituta el cómputo acumulado queda en revisión. | Relación institucional de sustituciones (Q12). |
| Cierre operativo de acta | `tbl_grupos.acta_cerrada=1` define resultado definitivo; acta abierta con NULL queda en curso incluso tras cierre calendario (G1/G2). | Confirmación académica de fuente/calendario si se requiere un cierre adicional. |

## Pruebas y compilación

| Módulo | Comando | Resultado |
| --- | --- | --- |
| Basegestor | `mvn -q -f codigoFuente/basegestor/pom.xml test` | 816 pruebas; 0 fallas, 0 errores, 0 omitidas. |
| GestorWeb | `mvn -q -f codigoFuente/gestorWeb/pom.xml test` | 41 pruebas; 0 fallas, 0 errores, 0 omitidas. |
| WAR | `mvn -q -f codigoFuente/gestorWeb/pom.xml -DskipTests package` | `SUCCESS` |

## Artefacto y validación en ejecución

| Dato | Valor |
| --- | --- |
| Artefacto | `codigoFuente/gestorWeb/target/plataforma.war` |
| Tamaño | `82,750,278` bytes |
| SHA-256 | `25126b850882e71a7571d13e10e4a53019daf0960ed916d6a9077e97e49e287a` |
| Despliegue | Pendiente de Abel; esta entrega no despliega ni usa expedientes identificables. |

Para la validación de Abel: desplegar el WAR, mantener `REGLAS_V2_MODO_SOMBRA=true`, verificar GC-17/GC-18/GC-19 en escritorio y móvil, y capturar sin datos personales: oportunidades/recursamientos, NULL con acta cerrada, interbloque pendiente, recursamiento de quinto y aviso de excepción configurada. Las capturas deben ocultar matrícula, nombre, CURP y correo.

## Conclusión

Las reglas implementables del Bloque 6 quedaron actualizadas a la versión RN 20261119 y la regresión de ambos módulos está verde. Las excepciones institucionales, sustituciones reales y ventanas de ajuste quedan explícitamente condicionadas a sus fuentes institucionales; no se infieren ni generan efectos productivos.
