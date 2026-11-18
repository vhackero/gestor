# Resultado Entrega 2-ter - Bloque 5 RN 20261117

## Alcance

Rama analizada: `feature/desacople_motor_historico`.

Esta entrega conserva modo sombra. No escribe en inscripción, bajas, grupos, oferta ni calificaciones. Los casos añadidos son sintéticos y no contienen datos personales.

## Estado de oportunidad de UD acreditada

Una UD acreditada ahora conserva el estado técnico `ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA` y expone cero oportunidades disponibles, mínimas y máximas. No puede aparecer como `DISPONIBLE` ni como reinscribible por saldo residual.

| Caso GC-17 | Resultado comprobado |
|---|---|
| A | `intentosConfirmados=3`: 2 resultados concluidos reprobatorios + 1 cursamiento en curso; estado `ULTIMA_EN_CURSO`. |
| C | Acreditada; estado `ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA`; oportunidades disponibles `0`. |
| Adeudos vigentes | `3`; no se duplica la recursada en curso. |

Evidencia: `Bloque5HistorialRn20261117Test.gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso`.

## Q5 - UD de quinto ya reprobada con cuatro o más adeudos

Salida real de la prueba sintética, sin alterar comportamiento:

```text
UD=OB5-1
Estados=[HABILITADA, PRIORITARIA, REQUIERE_REVISION]
Motivos=[REPROBACION_ACUMULADA, DATOS_INSUFICIENTES]
Decisión efectiva para regularización=PRIORITARIA
Bloqueada=false
```

Por tanto, el motor actual prioriza la UD de quinto como regularización mediante RN-6.4; no emite `BLOQUEADA` por RN-6.1.5 para ese caso. La marca `REQUIERE_REVISION` proviene de la evidencia insuficiente del control de avance en el contexto sintético. Evidencia: `Bloque3OfertaYPreseleccionRn20261106Test.q5ReprobadaDeQuintoConCuatroAdeudosReportaDecisionVigente`.

## SQL C-20

El archivo [C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql](/home/abelinux/gestor/repo/gestor/documentacion/C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql) contiene exclusivamente `SELECT` y comienza con `SELECT VERSION()`.

- Variante MySQL 8: usa `SUM() OVER (PARTITION BY persona, UD ORDER BY periodo)`.
- Variante MySQL 5.7: usa totales por UD sin funciones de ventana.
- Consolida primero persona + UD + periodo.
- Excluye de límite y adeudo las UD con acreditación posterior verificable.
- Reporta `id_plan` y nombre del PE, estudiantes distintos y UD distintas.
- Separa tercera oportunidad en curso y revisiones por causa de la consulta de límites.

La versión del motor de base de datos no se consultó en esta entrega: su ejecución requiere una cuenta autorizada. El script no devuelve datos personales.

## Evidencia de integración de consumidores

| Consumidor | Invocación y contexto | Mapeo de resultado | Reinterpretación identificada |
|---|---|---|---|
| Malla curricular | `MallaCurricularAlumnoBean.cargarResultadoEvaluacionV2` construye `contextoAsistente` y llama `MallaCurricularV2FacadeImpl.evaluarSombra`; el facade adapta con `ContextoEvaluacionCurricularV2Adapter` y llama al evaluador. | `MallaCurricularV2ResultadoAdapter.aplicar` recibe decisiones por `idPrograma` y situación para representar nodos. | Decide icono/CSS y conteos visuales; no recalcula decisión, motivos, oportunidades ni adeudos. |
| Asistente de inscripción y orientación educativa | `TablaCurricularAsistidaBean.cargarResultadoEvaluacionV2` llama `AsistenteCurricularV2FacadeImpl.evaluarSombra` con el contexto del expediente. | `AsistenteCurricularResultadoAdapter.adaptar` proyecta situación, carga, motivos, reglas y alertas; la misma bean usa la carga V2 para proyección. | Convierte códigos a textos y narrativas. No debe alterar decisiones; las leyendas son presentación. |
| Simulador | `TablaCurricularAsistidaBean.simularSeleccion` llama `SimuladorCurricularV2ServiceImpl.simularSombra(idPersona, idsSeleccionados)`. | El servicio construye el contexto con la selección hipotética y transforma el resultado en `ResultadoSimulacionDTO`. | Mantiene validaciones complementarias de selección; la decisión académica, motivos y carga proceden de la evaluación. |
| Asistente virtual | El panel se alimenta en `TablaCurricularAsistidaBean.cargarResultadoEvaluacionV2` mediante `AsistenteCurricularResultadoAdapter.adaptar(resultadoEvaluacionV2)`. | Consume situación, reglas aplicadas, restricciones y alertas del mismo resultado del Asistente. | Resume y redacta; no calcula oportunidades ni adeudos. |

Referencias de código: `MallaCurricularAlumnoBean:251`, `MallaCurricularV2FacadeImpl:36`, `TablaCurricularAsistidaBean:248`, `TablaCurricularAsistidaBean:1233`, `AsistenteCurricularV2FacadeImpl:85`, `SimuladorCurricularV2ServiceImpl:48` y `AsistenteCurricularResultadoAdapter:11`.

La prueba contractual de los cuatro consumidores contra un despliegue requiere el ambiente de pruebas. Esta entrega aporta evidencia estática de la ruta y no la declara ejecutada en WildFly.

## Pruebas y regresión

| Comando | Alcance | Resultado |
|---|---|---|
| `mvn -Dtest=Bloque5HistorialRn20261117Test,Bloque3OfertaYPreseleccionRn20261106Test test` | GC-17 y Q5 sintéticos | 21 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| `mvn test` en `basegestor` | Regresión completa | 788 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| `mvn test` en `gestorWeb` | Regresión completa web | 38 pruebas, 0 fallas, 0 errores, 0 omitidas. |

## Archivos modificados

- `EvaluacionCurricularV2ServiceImpl`: saldo cero para UD acreditada.
- `Bloque5HistorialRn20261117Test`: aserción GC-17 de estado acreditado y saldo cero.
- `Bloque3OfertaYPreseleccionRn20261106Test`: evidencia Q5 sin cambio de regla.
- `C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql`: variantes MySQL 8/5.7 y agregados solicitados.

## Limitaciones

- La consistencia dinámica entre los cuatro consumidores sigue pendiente de ejecutar en el ambiente autorizado.
- El SQL C-20 está preparado para ejecución autorizada; no se ejecutó contra una base de datos en esta entrega.
