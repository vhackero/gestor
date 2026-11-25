# Tarea 3 - C-14 y C-15

## Alcance

Revisión e implementación en modo sombra del Motor Curricular V2. No crea
reinscripciones, bajas, autorizaciones ni movimientos de historial.

## Auditoría y cambio aplicado

| Regla | Comportamiento previo | Exigencia C-14/C-15 | Cambio aplicado / evidencia |
|---|---|---|---|
| RN-1.1 / RN-6.6 - baja temporal acumulada | El parámetro `maxSemestresBajaTemporal` existía, pero RN-6.6 no comparaba el total acumulado. | Máximo de tres periodos acumulados por programa; tipos 2, 7 y 8; no consecutivos. | RN-6.6 compara `periodosBajaTemporalTranscurridos` contra el parámetro y devuelve `NO_PROCEDE_POR_MAXIMO_BAJAS_TEMPORALES` al cuarto. La fuente agrupa periodos en `InscripcionRepository.obtenerPeriodosTrayectoriaV2`, con `contabilizar=1` y tipos `IN (2,7,8)`. Pruebas `gc05TresBajasNoConsecutivasSeExcluyenUnaVezYNoReinscriben` y `gc05CuartaBajaTemporalAcumuladaEsExcedente`. |
| RN-6.6 - conclusión de baja temporal | La ruta ya era sólo descriptiva, pero no había prueba directa del no efecto. | La conclusión no reinscribe. | Sin escritura productiva; la prueba GC-05 verifica que `reincorporacionRegistrada` se conserva en `false` y que la evaluación no aplica efectos productivos. |
| RN-1.1 / RN-6.5 / RN-6.6 - tres periodos sin reinscripción y plazo de ocho años | `limiteInactividadAlcanzado` y `limitePermanenciaExcedido` podían negar automáticamente rutas de reincorporación. | Sólo identificación; no producir baja o negativa automática. | RN-6.2, RN-6.5, RN-6.6, recuperación y control de quinto registran trazas de identificación, sin usarlas como bloqueo automático. Las decisiones institucionales siguen requiriendo su fuente/resolución. Pruebas `inactividadSeIdentificaSinDenegacionAutomatica`, `f6BU13InactividadSeIdentificaYDatoNoComprobadoRequiereRevision` y `f6CU14PermanenciaYCalendarioNoSeInfieren`. |
| RN-6.2 - incorporación diferida | Admitido sin inscripción podía continuar sin una referencia documental. | Sin referencia de convocatoria o resolución: `REQUIERE_REVISION`. | Nuevo campo contractual `referenciaConvocatoriaOResolucionRn62`; RN-6.2 exige texto verificable. Prueba `sinReferenciaRequiereRevision`. La integración productiva de esa fuente queda pendiente. |
| RN-6.1.5 - medida administrativa | Sólo existían banderas de sanción/restricción sin alcance ni vigencia. | Consumir alcance, inicio y fin o condición; sin vigencia: revisión y motivo explícito. | Se agregan campos de contrato para medida, alcance e inicio/fin o condición. Si se declara una medida sin esos datos, RN-6.1.5 entrega `REQUIERE_REVISION` y `MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA`. Prueba `gc13MedidaAdministrativaSinVigenciaRequiereRevision`. Con datos completos no se bloquea por inferencia: `gc13MedidaConAlcanceYVigenciaNoSeBloqueaPorInferencia`. |

## Casos ejecutados

| Caso | Entrada resumida | Resultado |
|---|---|---|
| GC-05 | Tres bajas temporales no consecutivas, agrupadas por periodo. | Los tres periodos se excluyen una vez de la referencia; no se genera reinscripción. |
| GC-05 variante | Cuarta baja temporal acumulada. | RN-6.6: `NO_PROCEDE_POR_MAXIMO_BAJAS_TEMPORALES`; motivo `INACTIVIDAD`. |
| GC-13 | Candidata de quinto y medida administrativa sin fin ni condición. | RN-6.1.5: `REQUIERE_REVISION`; motivo `MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA`. |
| GC-13 variante | Medida con alcance, inicio y fin verificables. | No se añade bloqueo o motivo por inferencia. |

## Fuentes y límites

- Bajas temporales: `rel_persona_bajas` con `contabilizar=1`, unidas a `rel_motivo_baja`; tipos 2, 7 y 8. La consulta productiva agrupa B1/B2 por periodo de `tbl_eventos` / `tbl_periodos_inscripcion`.
- La fuente productiva de convocatoria/resolución de RN-6.2 y de medidas administrativas aún no está conectada. Si una medida se declara sin vigencia, el motor conserva revisión; si no se declara medida, no inventa una.
- El parámetro de máximo se toma de `ParametrosCurricularesV2DTO`; el valor actual por defecto es 3.
