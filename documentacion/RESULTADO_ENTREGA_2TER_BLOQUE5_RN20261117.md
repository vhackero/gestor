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

- La versión distribuida es compatible con MySQL 5.7: usa tablas derivadas y totales por UD, sin CTE ni funciones de ventana.
- Una variante MySQL 8 con `SUM() OVER (PARTITION BY persona, UD ORDER BY periodo)` puede construirse como optimización separada, pero no se mezcla con el archivo ejecutable en MySQL 5.7.
- Consolida primero persona + UD + periodo.
- Excluye de límite y adeudo las UD con acreditación posterior verificable.
- Reporta `id_plan` y nombre del PE, estudiantes distintos y UD distintas.
- Separa tercera oportunidad en curso y revisiones por causa de la consulta de límites.

La versión instalada localmente es MySQL 5.7.42. El script se ejecutó el 2026-11-17 contra `des_sisi_gestor` con una cuenta autorizada y devolvió únicamente agregados. El script no devuelve datos personales.

## Resultados C-20

Fecha de corte de la ejecución: `2026-11-17 22:55:55`. Versión: `5.7.42-0ubuntu0.18.04.1`. Los resultados siguientes no contienen identificadores personales.

### Límites por PE

| PE | Estudiantes con 3 fallidos sin acreditar | UD con 3 fallidos sin acreditar | Estudiantes con más de 3 | UD con más de 3 | Estudiantes con al menos una UD en cualquiera de las categorías |
|---|---:|---:|---:|---:|---:|
| Capacitación UnADM | 0 | 0 | 0 | 0 | 0 |
| Capacitación UnADM 2024 | 0 | 0 | 0 | 0 | 0 |
| Contaduría y Finanzas Públicas | 27 | 6 | 0 | 0 | 27 |
| Administración y Gestión Pública | 34 | 6 | 0 | 0 | 34 |
| Enseñanza de la Matemática | 15 | 6 | 0 | 0 | 15 |
| Gestión Industrial | 34 | 6 | 0 | 0 | 34 |
| Seguridad Alimentaria | 18 | 6 | 0 | 0 | 18 |
| Derecho | 41 | 6 | 0 | 0 | 41 |
| Capacitación docente 2024 | 0 | 0 | 0 | 0 | 0 |
| Capacitación docente 2025 | 0 | 0 | 0 | 0 | 0 |
| **Total global distinto** | **169** | **36** | **0** | **0** | **169** |

La fila total usa estudiantes distintos en todos los PE; las UD se suman por PE.

### Tercera oportunidad en curso

No se obtuvieron filas por PE y periodo. El control de corte confirmó `0` participaciones con `calificacion_final IS NULL` y `fecha_final >= CURDATE()`, `0` estudiantes distintos y `NULL` como fecha final máxima de eventos PE2024. Por ello, el cero corresponde al corte de datos sin eventos/participaciones en curso, no a una conclusión de que no haya trayectorias con oportunidades históricas.

### Revisiones

La consulta asigna una sola causa por registro, conforme al primer caso que cumple en este orden: calificación vacía en periodo cerrado, baja sin `contabilizar=1`, evento sin periodo. Los conteos siguientes suman registros de todos los periodos; los estudiantes y UD son distintos dentro de cada PE y causa.

| PE | Causa | Registros agregados | Estudiantes distintos | UD distintas |
|---|---|---:|---:|---:|
| Capacitación UnADM | Calificación vacía en periodo cerrado | 10 | 10 | 1 |
| Capacitación UnADM 2024 | Calificación vacía en periodo cerrado | 5 | 5 | 1 |
| Contaduría y Finanzas Públicas | Calificación vacía en periodo cerrado | 10,223 | 2,565 | 47 |
| Administración y Gestión Pública | Calificación vacía en periodo cerrado | 9,412 | 2,327 | 45 |
| Administración y Gestión Pública | Baja sin contabilizar=1 | 2 | 1 | 2 |
| Administración y Gestión Pública | Evento sin periodo | 6 | 1 | 6 |
| Enseñanza de la Matemática | Calificación vacía en periodo cerrado | 1,652 | 479 | 43 |
| Gestión Industrial | Calificación vacía en periodo cerrado | 7,234 | 1,955 | 45 |
| Seguridad Alimentaria | Calificación vacía en periodo cerrado | 1,593 | 444 | 40 |
| Derecho | Calificación vacía en periodo cerrado | 15,353 | 4,042 | 45 |
| Capacitación docente 2024 | Calificación vacía en periodo cerrado | 201 | 97 | 1 |
| Capacitación docente 2025 | Calificación vacía en periodo cerrado | 2 | 2 | 1 |

Detalle por periodo para `CALIFICACION_VACIA_PERIODO_CERRADO`:

| PE | Periodo | Estudiantes distintos | UD distintas |
|---|---|---:|---:|
| Capacitación UnADM | 2024-1 | 10 | 1 |
| Capacitación UnADM 2024 | 2024-1 | 5 | 1 |
| Contaduría y Finanzas Públicas | 2024-1 / 2024-2 / 2025-1 / 2025-2 / 2026-1 / 2026-2 | 31 / 28 / 66 / 70 / 121 / 2,514 | 6 / 6 / 12 / 12 / 28 / 13 |
| Administración y Gestión Pública | 2024-1 / 2024-2 / 2025-1 / 2025-2 / 2026-1 / 2026-2 | 32 / 30 / 81 / 75 / 97 / 2,293 | 6 / 6 / 12 / 12 / 26 / 13 |
| Enseñanza de la Matemática | 2024-1 / 2024-2 / 2025-1 / 2025-2 / 2026-1 / 2026-2 | 19 / 21 / 24 / 24 / 21 / 457 | 6 / 6 / 12 / 12 / 24 / 13 |
| Gestión Industrial | 2024-1 / 2024-2 / 2025-1 / 2025-2 / 2026-1 / 2026-2 | 26 / 29 / 68 / 63 / 84 / 1,921 | 6 / 6 / 12 / 12 / 26 / 13 |
| Seguridad Alimentaria | 2024-1 / 2024-2 / 2025-1 / 2025-2 / 2026-1 / 2026-2 | 15 / 24 / 18 / 18 / 25 / 415 | 6 / 6 / 12 / 12 / 22 / 11 |
| Derecho | 2024-1 / 2024-2 / 2025-1 / 2025-2 / 2026-1 / 2026-2 | 41 / 40 / 88 / 93 / 167 / 3,933 | 6 / 6 / 12 / 12 / 26 / 13 |
| Capacitación docente 2024 | 2024-1 | 97 | 1 |
| Capacitación docente 2025 | 2025-1 | 2 | 1 |

### Comparación de adeudos y resultados reprobatorios

| PE | Universo de estudiantes | 4 o más adeudos vigentes | % universo | 4 o más resultados reprobatorios | % universo |
|---|---:|---:|---:|---:|---:|
| Capacitación UnADM | 10 | 0 | 0.00% | 0 | 0.00% |
| Capacitación UnADM 2024 | 5 | 0 | 0.00% | 0 | 0.00% |
| Contaduría y Finanzas Públicas | 4,575 | 1,929 | 42.16% | 1,987 | 43.43% |
| Administración y Gestión Pública | 4,221 | 1,866 | 44.21% | 1,924 | 45.58% |
| Enseñanza de la Matemática | 1,043 | 496 | 47.56% | 513 | 49.19% |
| Gestión Industrial | 3,628 | 1,748 | 48.18% | 1,806 | 49.78% |
| Seguridad Alimentaria | 1,063 | 592 | 55.69% | 610 | 57.38% |
| Derecho | 6,105 | 2,022 | 33.12% | 2,121 | 34.74% |
| Capacitación docente 2024 | 97 | 0 | 0.00% | 0 | 0.00% |
| Capacitación docente 2025 | 2 | 0 | 0.00% | 0 | 0.00% |
| **Total global distinto** | **20,513** | **8,653** | **42.18%** | **8,961** | **43.68%** |

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
- `C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql`: consulta agregada ejecutable en MySQL 5.7.

## Limitaciones

- La consistencia dinámica entre los cuatro consumidores sigue pendiente de ejecutar en el ambiente autorizado.
