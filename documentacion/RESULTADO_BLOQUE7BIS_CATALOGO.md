# Resultado Bloque 7-bis

## Cambios

- Se agregó al contrato `ResultadoEvaluacionCurricularV2DTO`: `adeudosVigentes`, `adeudosEnRevision` y `resultadosReprobatoriosInformativos`.
- RN-6.4 continúa usando únicamente `adeudosVigentes`. Los resultados históricos no se usan como adeudos.
- Situación académica y el bloque del Asistente virtual muestran los tres agregados sin recalcularlos.
- Se documentó la matriz por variantes con estados separados de lógica y validación real.
- `PENDIENTE_CALIFICACION` permanece sólo internamente en el intento; una UD con acta cerrada y calificación vacía se entrega en revisión. `PENDIENTE_POR_SERIACION` es el estado de salida interbloque.
- Se corrigió RN-2.3: una excepción institucional vigente genera `HABILITACION_CONCEDIDA_POR_EXCEPCION_INSTITUCIONAL` aun cuando el comunicado 2027-1 no aplique. La restricción y las oportunidades permanecen intactas.

## Pruebas nuevas o ampliadas

| Prueba | Resultado |
|---|---|
| `Bloque5HistorialRn20261117Test#gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso` | Verifica GC-17: adeudos 3, revisión 0, resultados reprobatorios informativos 6. |
| `Bloque5HistorialRn20261117Test#gc17cMantieneEnRevisionElUmbralCuandoPuedeCambiar` | Verifica exactamente 3 adeudos vigentes + 1 en revisión, `UMBRAL_64_EN_REVISION` y que RN-6.4 no se active. |
| `Bloque5HistorialRn20261117Test#cuatroAdeudosConfirmadosAplicaRn64SinConfundirRevision` | Verifica que sólo cuatro adeudos confirmados activan RN-6.4. |
| `ComunicadosInstitucionales20271Test#excepcionVigenteConservaRestriccionYConcedeHabilitacionInformativaSinReiniciarOportunidades` | Comprueba excepción vigente: restricción conservada, habilitación informativa y oportunidades sin reinicio. |
| `ConsistenciaConsumidoresBloque7Test#catalogoB7EntregaElMismoContratoALosCuatroConsumidores` | Prueba parametrizada para rezago severo, B2 seriada, acta cerrada sin calificación, comunicado 2027-1, excepción y recursamiento de quinto; compara decisión, motivos, oportunidades, recursamientos, tres agregados, 8.1 y avisos. |

## Apariciones revisadas

| Término | Ubicación | Tratamiento |
|---|---|---|
| `PENDIENTE_CALIFICACION` | Enum de intentos, adaptador y contador RN-1.4 | Técnico; no se muestra como UD en curso tras acta cerrada. |
| `PENDIENTE_DE_CONFIRMACION` | Documentación histórica | No es estado normativo de salida; el catálogo usa `PENDIENTE_POR_SERIACION`. |

## Regresión

| Módulo | Comando | Pruebas | Fallas | Errores | Omitidas | Resultado |
|---|---|---:|---:|---:|---:|---|
| `basegestor` | `mvn test` | 828 | 0 | 0 | 0 | SUCCESS |
| `gestorWeb` | `mvn test` | 41 | 0 | 0 | 0 | SUCCESS |

## Totales del catálogo

| Dimensión | Resultado |
|---|---:|
| Variantes P/N/L/D/I con lógica cubierta | 30 |
| Variantes con fuente real pendiente | 14 |
| Variantes no cubiertas por lógica | 1: tercera sustitución formal, dependiente del movimiento institucional candidato |
| Escenarios de consistencia entre cuatro consumidores | 6 |

La matriz por variante al inicio de [CATALOGO_CASOS_MOTOR_V2.md](CATALOGO_CASOS_MOTOR_V2.md) es la fuente normativa del catálogo. Las tablas de trazabilidad posteriores se mantienen sincronizadas como índice por numeral y caso; no sustituyen las variantes P/N/L/D/I.

## Pendientes

- La tercera sustitución formal requiere el movimiento candidato institucional.
- La validación real de cierres B1 sigue bloqueada por la fuente de cierre.
- Falta confirmar visualmente los agregados tras recompilar y desplegar.

## Acción requerida

Hubo cambios productivos en el contrato V2 y en la vista `tablaCurricularAsistida.xhtml`. El usuario debe recompilar y desplegar mediante sus scripts `../../scripts/*.sh`; este bloque no genera ni despliega un WAR.
