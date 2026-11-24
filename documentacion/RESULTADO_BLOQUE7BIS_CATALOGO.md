# Resultado Bloque 7-bis

## Cambios

- Se agregó al contrato `ResultadoEvaluacionCurricularV2DTO`: `adeudosVigentes`, `adeudosEnRevision` y `resultadosReprobatoriosInformativos`.
- RN-6.4 continúa usando únicamente `adeudosVigentes`. Los resultados históricos no se usan como adeudos.
- Situación académica y el bloque del Asistente virtual muestran los tres agregados sin recalcularlos.
- Se documentó la matriz por variantes con estados separados de lógica y validación real.
- `PENDIENTE_CALIFICACION` permanece sólo internamente en el intento; una UD con acta cerrada y calificación vacía se entrega en revisión. `PENDIENTE_POR_SERIACION` es el estado de salida interbloque.

## Pruebas nuevas o ampliadas

| Prueba | Resultado |
|---|---|
| `Bloque5HistorialRn20261117Test#gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso` | Verifica GC-17: adeudos 3, revisión 0, resultados reprobatorios informativos 6. |
| `Bloque5HistorialRn20261117Test#gc17cMantieneEnRevisionElUmbralCuandoPuedeCambiar` | Verifica 4 adeudos confirmados lógicos mediante 3 vigentes y 1 en revisión, sin convertir la revisión en adeudo. |
| `ConsistenciaConsumidoresBloque7Test#catalogoB7CasoRepresentativoEntregaElMismoContratoALosCuatroConsumidores` | Amplía la huella común con los tres agregados. |

## Apariciones revisadas

| Término | Ubicación | Tratamiento |
|---|---|---|
| `PENDIENTE_CALIFICACION` | Enum de intentos, adaptador y contador RN-1.4 | Técnico; no se muestra como UD en curso tras acta cerrada. |
| `PENDIENTE_DE_CONFIRMACION` | Documentación histórica | No es estado normativo de salida; el catálogo usa `PENDIENTE_POR_SERIACION`. |

## Regresión

| Módulo | Comando | Pruebas | Fallas | Errores | Omitidas | Resultado |
|---|---|---:|---:|---:|---:|---|
| `basegestor` | `mvn test` | 821 | 0 | 0 | 0 | SUCCESS |
| `gestorWeb` | `mvn test` | 41 | 0 | 0 | 0 | SUCCESS |

## Pendientes

- La tercera sustitución formal requiere el movimiento candidato institucional.
- La validación real de cierres B1 sigue bloqueada por la fuente de cierre.
- Falta confirmar visualmente los agregados tras recompilar y desplegar.

## Acción requerida

Hubo cambios productivos en el contrato V2 y en la vista `tablaCurricularAsistida.xhtml`. El usuario debe recompilar y desplegar mediante sus scripts `../../scripts/*.sh`; este bloque no genera ni despliega un WAR.
