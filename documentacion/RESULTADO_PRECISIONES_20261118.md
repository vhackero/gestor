# Resultado de precisiones institucionales 2026-11-18

## Alcance

Se aplicaron las precisiones del documento `Codex_INSTRUCCIONES_bloque5c_precisiones_20261118_v4.md` en modo sombra. No se escriben inscripciones, bajas, grupos, oferta ni calificaciones.

## Cambios aplicados

| Precisión | Resultado | Evidencia |
| --- | --- | --- |
| Comunicado 2027-1 | `V2_COMUNICADO_INSTITUCIONAL_TEXTO` usa literalmente el texto aprobado. Se genera una vez sólo para C1 (RN-1.4 por límite) o C2 (RN-6.2 en revisión), durante `2027-1`. | `ConfiguracionEvaluacionCurricularV2ServiceImpl`; `ComunicadosInstitucionales20271Test`. |
| Autorización individual | Se agregó `autorizacionIndividual` opcional: referencia, autoridad, UD o supuesto e inicio/fin de vigencia. Si está acreditada y vigente se publica `AUTORIZACION_INDIVIDUAL_ACREDITADA`; no cambia decisiones en sombra. | `ContextoEvaluacionCurricularV2DTO`; `AutorizacionIndividualCurricularV2DTO`. |
| C1/C2 excluyentes | RN-6.2 deja de aplicar si existe cualquier intento formal comprobado. Un expediente no puede producir a la vez los marcadores de C1 y C2. | Traza `RN62_NO_APLICA_INSCRIPCION_FORMAL`; prueba `gc19eUnMismoExpedienteNoPuedeTenerMarcadoresC1YC2`. |
| C2 sin respaldo | Sin convocatoria o resolución, RN-6.2 entrega `REQUIERE_REVISION`; nunca una decisión definitiva de bloqueo. | `evaluarInscripcionInicialDiferida`; `gc19dIncorporacionDiferidaSinReferenciaConservaSemestreUnoSinInactividad`. |
| Q5 con RN-6.4 | Nuevo parámetro `V2_Q5_RECURSAMIENTO_QUINTO_CON_64`: `REGULARIZACION_64` vigente o `AVANCE_CONDICIONADO_NO_PROCEDE`. Ambos dejan traza `DEFINICION_INSTITUCIONAL_PENDIENTE_Q5`. | `PreparacionDecisionesPendientesV2Test`. |

## Datos faltantes: tratamiento seguro

| Regla | Dato insuficiente | Resultado actual |
| --- | --- | --- |
| RN-1.4 / C1 | Historial de intentos no verificable | `REQUIERE_REVISION`; no se afirma agotamiento. |
| RN-6.1.5 | Alcance o vigencia de medida administrativa ausente | `REQUIERE_REVISION` y motivo `MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA`; no bloqueo indefinido. |
| RN-6.2 / C2 | Convocatoria o resolución ausente | `REQUIERE_REVISION`; no bloqueo. |
| RN-7.1.1 | Catálogo, bloque fijo u origen de sustitución no verificable | `REQUIERE_REVISION`; no se inventa optativa elegible. |
| RN-8.1 | Antecedente, relación o cierre de acta no verificable | `REQUIERE_REVISION` o `PENDIENTE_DE_CONFIRMACION`; no se concluye seriación incumplida. |

## Q6: sensibilidad de calificación cero sin baja

Población: estudiantes distintos de los planes PE2024 `3, 4, 5, 6, 7 y 11`, base de pruebas, corte previo documentado en `RESULTADO_ENTREGA_2TER_BLOQUE5_RN20261117.md`.

| Escenario SQL | Estudiantes con 4 o más adeudos | Porcentaje sobre el escenario `REPROBACION` |
| --- | ---: | ---: |
| `REPROBACION`: cuenta 1–59 y 0 sin baja aplicada | 8,653 | 100.00 % |
| `EXCLUIR_PERIODO_COMPLETO_EN_CERO`: excluye un periodo si todas sus UD calificadas son 0 sin baja aplicada | 5,585 | 64.55 % |
| `REVISION`: excluye todo 0 sin baja aplicada del cómputo automático | 1,367 | 15.80 % |

Los escenarios son sensibilidad de datos, no una decisión normativa. El valor por omisión sigue siendo `REPROBACION` hasta determinación institucional.

## Q8: `contabilizar`

La asignación está en `NuevaBajaServiceImpl.aplicarBaja(...)`: determina `1` cuando la baja se aplica a evento o inscripción, es definitiva/sin asignaturas, o la actualización ya tiene los datos curriculares completos. `NuevaBajaRepository` sólo persiste el valor recibido. La ruta web localizada es `NuevaBajaUsuarioBean -> NuevaBajaServiceImpl`; no se encontró otra asignación de negocio.

Los registros sin `contabilizar=1` quedan en revisión y no excluyen un intento. Conteo agregado en la base de pruebas al 2026-11-18: 3,399 con `1`, 6 con `0`, 0 nulos.

## Pruebas

`mvn -Dtest=ComunicadosInstitucionales20271Test,PreparacionDecisionesPendientesV2Test test`

Resultado: 22 pruebas, 0 fallas, 0 errores, 0 omitidas.

Regresión completa `basegestor`: 812 pruebas, 0 fallas, 0 errores, 0 omitidas.
