# Resultado - Preparación de decisiones pendientes

## Alcance

Se prepararon configuraciones de lectura para Q4, Q5, Q6, Q7 y Q11. Sus valores por omisión preservan el comportamiento vigente y la evaluación continúa exclusivamente en modo sombra.

| Pregunta | Valor vigente | Alternativas listas | Aplicación |
| --- | --- | --- | --- |
| Q6 | `REPROBACION` | `EXCLUIR_PERIODO_COMPLETO_EN_CERO`, `REVISION` | Un periodo se excluye sólo si todas sus calificaciones verificables son cero sin baja; revisión no lo convierte en reprobación. |
| Q5 | `AVANCE_CONDICIONADO` | `REGULARIZACION` | La alternativa registra el impedimento de regularización para recursamiento candidato de quinto con menos de cuatro adeudos. |
| Q7 | `BLOQUEAR` | `PERMITIR_TRAS_PENDIENTES` | Aplica a referencia 5.º-8.º y conserva las demás validaciones de optativas. |
| Q11 | `AMBOS_BLOQUES` | `BLOQUE_ORIGINAL` | El segundo requiere evidencia `BLOQUE_ORIGINAL:B1/B2`; sin ella no se inventa un bloque. |
| Q4 | `NO_CUENTA` | `SOLO_MAX`, `CUENTA_MIN_Y_MAX` | Controla si una B2 seriada participa en mínimo/máximo; el estado de seriación no se modifica. |

Todos incluyen documento `Decisiones_institucionales_motor_v2_RN_PE2024_2027_20261117.md` y vigencia `2026-11-17` dentro de los parámetros.

## PE2027 (Q2)

`PreparacionDecisionesPendientesV2Test.q2MapaPe2027SinteticoNoDependeDeIdsPe2024` evalúa 17 planes sintéticos PE2027. Cada uno contiene, para S1-S4, cuatro obligatorias, dos optativas, el catálogo de trece optativas y una relación de seriación de ejemplo.

Resultado: el motor usa `idPlan`, ámbito, parámetros y mapa entregados por contexto; no se localizó ninguna dependencia fija de IDs `3,4,5,6,7,11` en los servicios V2. El catálogo deja de tener respaldo fijo en el evaluador y se lee solamente desde parámetros. RN-6.9, RN-6.1 y RN-8.1 reciben el contexto del plan, no una lista de planes PE2024.

## Cierre de acta B1

La fuente localizada es `tbl_grupos.acta_cerrada`, ligada a `rel_grupo_participante.id_grupo`. El adaptador de historial ahora la incorpora: calificación `NULL` con acta cerrada se clasifica como `PENDIENTE_CALIFICACION`, no `EN_CURSO`; una consecuente B2 interbloque recibe `PENDIENTE_DE_CONFIRMACION`.

No existe una fuente separada de cierre de bloque institucional: la marca es por grupo. Por ello no se usa para declarar cerrado todo el periodo ni grupos distintos del B1 consultado.

## Pruebas añadidas

`PreparacionDecisionesPendientesV2Test`:

| Caso | Resultado |
| --- | --- |
| Q6 tres valores | Pasa |
| Q4 tres valores | Pasa |
| Q5 valor alternativo | Pasa |
| Q7 valor alternativo | Pasa |
| Q11 valor alternativo | Pasa |
| PE2027 sintético, 17 planes | Pasa |
| Acta cerrada sin calificación y B2 | Pasa |

## Regresión

| Módulo | Ejecución | Pruebas | Fallas | Errores | Omitidas |
| --- | --- | ---: | ---: | ---: | ---: |
| `basegestor` | `mvn -q test` | 805 | 0 | 0 | 0 |
| `gestorWeb` | `mvn -q test` | 39 | 0 | 0 | 0 |

La ejecución se realizó sin escrituras de datos y no cambia `REGLAS_V2_MODO_SOMBRA=true`.

## Limitaciones

- La opción Q11 `BLOQUE_ORIGINAL` requiere que la fuente institucional entregue el bloque original o de sustitución; sin esa evidencia se conserva el comportamiento vigente `AMBOS_BLOQUES`.
- Q6 no convierte automáticamente un patrón de ceros en baja: sólo aplica la opción institucional configurada.
- Las relaciones de seriación continúan siendo datos del mapa; el motor no las infiere por plan, nombre o semestre.
- El cierre B1 depende de la marca real `acta_cerrada`; no sustituye los cierres institucionales que aún no se expongan en la fuente.
