# Resultado Bloque 7 - Catálogo, cobertura y deuda

## Alcance

Se consolidó el catálogo de casos del motor curricular V2 para RN 1.1 a 9.5, comunicado 2027-1, excepciones 2.3, CAMPUS 2 y GC-01 a GC-19. No se desplegaron ni generaron artefactos para despliegue.

## Totales de catálogo

| Estado | Familias/casos documentados | Alcance |
|---|---:|---|
| Cubierto | 44 | Numerales, CAMPUS, dorados e interacciones que pueden demostrarse sintéticamente con contrato actual. |
| Bloqueado por fuente | 13 | Excepción, GLPi, convocatoria, sustitución real, cierre real, relaciones PE2027 y reinscripción extemporánea. |
| Fuera de alcance | 3 | Confirmación material de inscripción, dispersión, titulación/servicio social. |

Cada familia incluye variantes positiva, negativa, límite, dato faltante e interacción. El detalle y su método de prueba está en `CATALOGO_CASOS_MOTOR_V2.md`.

## Prueba nueva

| Prueba | Resultado | Qué acredita |
|---|---|---|
| `ConsistenciaConsumidoresBloque7Test#catalogoB7CasoRepresentativoEntregaElMismoContratoALosCuatroConsumidores` | Verde | Malla, Asistente, Simulador y la fachada que alimenta el Asistente virtual reciben el mismo contrato V2: situación, reprobadas, UD origen de irregularidad, decisión, motivos, oportunidades, recursamientos, estado 8.1 y avisos. Usa adaptador y evaluador reales; sólo simula el servicio externo que entrega la fuente al simulador. |

## Correcciones y ambigüedades

No se detectó una diferencia entre el comportamiento existente y la RN que exigiera cambiar reglas. La corrección realizada fue de cobertura: el resultado público no expone un método `cantidadAdeudosVigentes`; la prueba transversal usa la lista pública de UD origen de irregularidad junto con reprobadas vigentes, sin inventar otra API.

La ambigüedad o falta de fuente se mantiene explícita: una excepción institucional, una sustitución adicional, un cierre registral o una resolución no se infieren desde historial parcial. Sus casos quedan bloqueados por la fuente correspondiente y se registran en `DEUDA_TECNICA_MOTOR_V2.md`.

## Regresión

| Módulo | Comando | Pruebas | Fallas | Errores | Omitidas | Resultado |
|---|---|---:|---:|---:|---:|---|
| `basegestor` | `mvn test` | 821 | 0 | 0 | 0 | SUCCESS |
| `gestorWeb` | `mvn test` | 41 | 0 | 0 | 0 | SUCCESS |

También se ejecutó el conjunto dirigido del catálogo: 71 pruebas, 0 fallas, 0 errores y 0 omitidas.

## Código modificado

Se agregó una prueba en `basegestor`; no cambia comportamiento productivo. Commit funcional: `eace4ff8` (`test: consolida catalogo y cobertura motor v2`). El usuario deberá compilar y desplegar con sus scripts habituales `../../scripts/*.sh`; este bloque no genera ni despliega WAR.
