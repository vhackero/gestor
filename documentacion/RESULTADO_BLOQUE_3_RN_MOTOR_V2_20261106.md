# Resultado Bloque 3 RN Motor V2

## Alcance

Implementación en `feature/desacople_motor_historico`, sobre `2a4a14e5`, para C-08 a C-13. La evaluación permanece en modo sombra y no crea ni modifica inscripciones, bajas, grupos, oferta ni calificaciones.

## Correcciones previas

| Cambio | Implementación | Evidencia |
|---|---|---|
| Tipos 2, 7 y 8 | Se clasifican como `BAJA_TEMPORAL`; la trayectoria los agrupa por periodo. | `InscripcionRepository.obtenerPeriodosTrayectoriaV2` y `Bloque2HistorialOportunidadesRn20261106Test`. |
| Art. 40 tipo 5 | Emite `BAJA_ART40_SUBTIPO_NO_DETERMINADO`; no se equipara a sanción. El subtipo requiere una fuente que identifique si `rel_persona_bajas.id_inscripcion` afecta UD específicas o si el evento comprende el periodo completo. | `mapearBajaContabilizable`. |
| Tipos 3 y 6 | Conservan resultado/calificación del intento y registran `BAJA_DEFINITIVA` a nivel expediente; RN-1.1 marca inactividad, por lo que no se propone carga. | Adaptador V2 y `evaluarActividadAcademica`. |
| GC-08c | Oportunidad agotada deja la UD `BLOQUEADA` con `LIMITE_INTENTOS`. | `evaluarLimiteIntentos`. |

## Matriz C-08 a C-13

| Cambio | Estado | Comportamiento verificable |
|---|---|---|
| C-08 preselección | Implementado | Reprobadas vigentes ofertadas se marcan prioritarias, se preseleccionan por reprobaciones descendentes y clave; sin oferta no se seleccionan. |
| C-09 rezago severo | Implementado | Con umbral vigente, permite reprobadas y obligatorias nunca inscritas anteriores a la referencia; bloquea el resto con `REZAGO_SEVERO_SOLO_REGULARIZACION`. |
| C-10 avance condicionado | Implementado | Carga con candidata de quinto que omite una reprobada ofertada previa recibe `PENDIENTE_OMITIDA_AVANCE_CONDICIONADO`; fuera de quinto la prioridad sigue desmarcable. |
| C-11 optativas | Parcial con fuente | Requerida reprobada mantiene recursamiento, sin sustitución. Las reglas completas por bloque/plan requieren catálogo institucional cargado por plan y cohorte. |
| C-12 nuevo ingreso | Parcial con fuente | Sin catálogo o unidades estructurales, resultado en revisión; la validación 4 obligatorias + dos optativas distintas depende de catálogo y bloques institucionales. |
| C-13 seriación | Implementado | Una relación de antecedente no acreditado bloquea y prevalece sobre prioridad; B2 interbloque queda preseleccionada no desmarcable. |

## Casos dorados ejecutados

| Caso | Salida por UD / simulador |
|---|---|
| GC-01 | Las cuatro reprobadas quedan `PRIORITARIA`, preseleccionadas; la de mayor número de intentos toma orden 1. La obligatoria nunca inscrita anterior se habilita con `RUTA_REGULARIZACION_NUNCA_INSCRITA`; la nueva queda `BLOQUEADA` con `REZAGO_SEVERO_SOLO_REGULARIZACION`. |
| GC-02 | Antecedente `PRIORITARIA`; consecuente reprobada `BLOQUEADA` por `SERIACION_DIRECTA`, sin preselección. |
| GC-03 | Propuesta de quinto sin pendiente ofertada: inválida por `PENDIENTE_OMITIDA_AVANCE_CONDICIONADO`; al incluirla se elimina ese motivo. |
| GC-03b | Propuesta sin quinto: no aplica la restricción C-10 y la reprobada continúa desmarcable. |
| GC-06 / GC-07 | Optativa requerida reprobada conserva recursamiento y `OPTATIVA_REQUERIDA_SIN_SUSTITUCION`; no se inventa sustituta. |
| GC-10 | UD B2 interbloque: preseleccionada, no desmarcable y con `PRESELECCION_CONDICIONADA_INTERBLOQUE`. |
| GC-16 | Nuevo ingreso sin catálogo/unidades: `REQUIERE_REVISION` por datos insuficientes, no una carga inventada. |

Cada salida expone estado curricular, decisión, motivos, `preseleccionada`, `ordenPreseleccion` y `desmarcable` en `UnidadEvaluacionCurricularV2DTO` y `DecisionUnidadCurricularV2DTO`. La validación de cargas GC-03 y GC-03b se ejecuta sobre `idsUnidadesPretendidas`, el mismo contrato usado por Simulador; GC-06 queda condicionado a catálogo institucional.

## Pruebas

`Bloque2HistorialOportunidadesRn20261106Test`: 10 pruebas, 0 fallas, 0 errores, 0 omitidas.

`Bloque3OfertaYPreseleccionRn20261106Test`: 7 pruebas, 0 fallas, 0 errores, 0 omitidas.

Pendiente para aceptación funcional: integrar el catálogo oficial de optativas por plan/cohorte y la fuente verificable de rutas de sustitución. No se inició Bloque 4.
