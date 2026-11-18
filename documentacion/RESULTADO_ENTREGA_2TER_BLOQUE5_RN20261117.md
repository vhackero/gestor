# Resultado Entrega 2-ter - Bloque 5 RN 20261117

## Alcance

Rama: `feature/desacople_motor_historico`.

La entrega conserva modo sombra. No escribe en inscripción, bajas, grupos, oferta ni calificaciones. Las pruebas funcionales usan datos sintéticos; C-20 usa únicamente consultas `SELECT` con salidas agregadas y sin datos personales.

## Corrección de detección de periodo en curso

### Hallazgo

El adaptador canónico de historial evaluaba una participación con calificación `NULL` usando `MAX(tbl_eventos.fecha_final)` de **su bloque**. Eso es insuficiente: cada bloque puede cerrar antes que el periodo académico completo.

| Periodo | Eventos PE2024 | `fecha_final` nula | Mínima | Máxima |
|---|---:|---:|---|---|
| 2026-1 | 906 | 0 | 2026-03-22 | 2026-06-14 |
| 2026-2 | 432 | 0 | 2026-09-13 | 2026-12-06 23:59:59 |

Los periodos se identifican con el sufijo exacto `AAAA-N` de `tbl_eventos.cve_evento_cap`, contrastado contra `tbl_periodos_inscripcion.nombre_periodo`. No se usa `LIKE`.

### Criterio aplicado

`tbl_periodos_inscripcion.fecha_finalizacion` es una ventana administrativa de inscripción, no la duración académica del semestre: para `2026-2` termina el 2026-07-17. Por ello no es fuente válida para cerrar el cursamiento.

El adaptador ahora obtiene `fecha_fin_periodo_v2 = MAX(tbl_eventos.fecha_final)` por `id_plan + periodo`. Conserva la fecha del evento individual como evidencia, pero para una calificación `NULL` decide así:

| Condición | Estado de intento |
|---|---|
| Cierre de periodo conocido y mayor o igual a la fecha de evaluación | `EN_CURSO` |
| Cierre de periodo anterior o no determinable | `NO_DETERMINADO` con revisión |

Este criterio es una fuente temporal académica verificable mientras no exista un calendario institucional separado por bloque/cierre de acta. No interpreta la ventana de inscripción como calendario.

### Evidencia de ejecución

Para `2026-2`, sin baja aplicada y con calificación `NULL`:

| Criterio de clasificación | Participaciones | `EN_CURSO` | `NO_DETERMINADO` |
|---|---:|---:|---:|
| Antes: fecha final del evento individual | 34,391 | 0 | 34,391 |
| Después: cierre agregado del periodo | 34,391 | 34,391 | 0 |

Desglose después de la corrección:

| PE | Participaciones `EN_CURSO` 2026-2 | Estudiantes distintos | Cierre de periodo |
|---|---:|---:|---|
| Contaduría y Finanzas Públicas | 7,620 | 2,514 | 2026-12-06 23:59:59 |
| Administración y Gestión Pública | 6,858 | 2,293 | 2026-12-06 23:59:59 |
| Enseñanza de la Matemática | 1,128 | 457 | 2026-12-06 23:59:59 |
| Gestión Industrial | 5,650 | 1,921 | 2026-12-06 23:59:59 |
| Seguridad Alimentaria | 1,138 | 415 | 2026-12-06 23:59:59 |
| Derecho | 11,997 | 3,933 | 2026-12-06 23:59:59 |
| **Total** | **34,391** | **11,533** | — |

La corrección está en `InscripcionRepository.consultaIntentosCurricularesV2` y `mapearIntentoCurricularV2`. La prueba `Bloque2HistorialOportunidadesRn20261106Test.nuloCerradoRequiereRevisionYNuloVigenteEstaEnCurso` comprueba ambos casos: evento de bloque terminado con periodo vigente produce `EN_CURSO`; periodo cerrado con `NULL` produce revisión.

## Estado de oportunidad de UD acreditada

Una UD acreditada expone `ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA` y cero oportunidades disponibles; no aparece como disponible ni reinscribible por saldo residual.

| Caso GC-17 | Resultado comprobado |
|---|---|
| A | `intentosConfirmados=3`: dos resultados concluidos reprobatorios y un cursamiento en curso; `ULTIMA_EN_CURSO`. |
| C | Acreditada; `ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA`; oportunidades disponibles `0`. |
| Adeudos vigentes | `3`; la recursada en curso no se duplica. |

## Q5 - UD de quinto ya reprobada con cuatro o más adeudos

Salida de la prueba sintética, sin cambio de comportamiento:

```text
UD=OB5-1
Estados=[HABILITADA, PRIORITARIA, REQUIERE_REVISION]
Motivos=[REPROBACION_ACUMULADA, DATOS_INSUFICIENTES]
Decisión efectiva para regularización=PRIORITARIA
Bloqueada=false
```

El motor prioriza la UD de quinto como regularización por RN-6.4; no emite `BLOQUEADA` por RN-6.1.5 en ese caso. La revisión procede de evidencia insuficiente del control de avance del contexto sintético.

## SQL C-20

El archivo [C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql](/home/abelinux/gestor/repo/gestor/documentacion/C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql) es compatible con MySQL 5.7: no usa CTE ni funciones de ventana. Su universo se limita a los planes `3, 4, 5, 6, 7 y 11`; excluye capacitación y cualquier otro plan.

La ejecución contra `des_sisi_gestor` fue el `2026-11-18 10:04:58`, con MySQL `5.7.42-0ubuntu0.18.04.1`. Devuelve únicamente agregados.

### Límites por PE

| PE | Estudiantes con 3 fallidos sin acreditar | UD con 3 fallidos | Estudiantes con más de 3 | UD con más de 3 |
|---|---:|---:|---:|---:|
| Contaduría y Finanzas Públicas | 27 | 6 | 0 | 0 |
| Administración y Gestión Pública | 34 | 6 | 0 | 0 |
| Enseñanza de la Matemática | 15 | 6 | 0 | 0 |
| Gestión Industrial | 34 | 6 | 0 | 0 |
| Seguridad Alimentaria | 18 | 6 | 0 | 0 |
| Derecho | 41 | 6 | 0 | 0 |
| **Total distinto** | **169** | **36** | **0** | **0** |

### Tercera oportunidad en curso

| PE | Periodo | Estudiantes | UD |
|---|---|---:|---:|
| Contaduría y Finanzas Públicas | 2026-2 | 20 | 3 |
| Administración y Gestión Pública | 2026-2 | 20 | 3 |
| Enseñanza de la Matemática | 2026-2 | 14 | 3 |
| Gestión Industrial | 2026-2 | 31 | 3 |
| Seguridad Alimentaria | 2026-2 | 11 | 3 |
| Derecho | 2026-2 | 12 | 3 |

### Revisiones posteriores a la corrección

`2026-2` ya no aparece como `CALIFICACION_VACIA_PERIODO_CERRADO`. La consulta asigna una sola causa por registro, en este orden: calificación vacía en periodo cerrado, baja sin `contabilizar=1`, evento sin periodo.

| PE | Calificación vacía en periodos cerrados | Baja sin contabilizar | Evento sin periodo |
|---|---:|---:|---:|
| Contaduría y Finanzas Públicas | 2,603 | 0 | 0 |
| Administración y Gestión Pública | 2,554 | 2 | 6 |
| Enseñanza de la Matemática | 524 | 0 | 0 |
| Gestión Industrial | 1,584 | 0 | 0 |
| Seguridad Alimentaria | 455 | 0 | 0 |
| Derecho | 3,356 | 0 | 0 |

El detalle de calificación vacía permanece en los periodos `2024-1`, `2024-2`, `2025-1`, `2025-2` y `2026-1`; no hay filas de esa causa para `2026-2`.

### Sensibilidad Q6: efecto de calificación cero sin baja

Cada columna cuenta estudiantes con cuatro o más adeudos vigentes. La segunda excluye reprobaciones de un periodo en que **todas** las UD de la persona están en cero y sin baja aplicada. La tercera excluye cualquier reprobación con cero sin baja, aunque coexistiera con otros resultados del periodo.

| PE | Total | Sin periodos completos en cero | Sin ningún cero sin baja |
|---|---:|---:|---:|
| Contaduría y Finanzas Públicas | 1,929 | 1,254 | 256 |
| Administración y Gestión Pública | 1,866 | 1,183 | 283 |
| Enseñanza de la Matemática | 496 | 351 | 100 |
| Gestión Industrial | 1,748 | 1,126 | 297 |
| Seguridad Alimentaria | 592 | 367 | 99 |
| Derecho | 2,022 | 1,304 | 332 |

Estos resultados son sensibilidad de la base de pruebas, no un cambio de regla: mientras DAEAE no determine otra interpretación, `0` sin baja aplicada sigue siendo reprobación conforme al adaptador.

### UD donde se concentra el límite

Las seis UD siguientes por PE acumulan los 169 estudiantes del umbral. El semestre proviene del mapa curricular (`tmp_claves_programas`), no de la oferta.

| PE | Clave | Unidad didáctica | Semestre | Estudiantes |
|---|---|---|---:|---:|
| Contaduría y Finanzas Públicas | CCGEP | Contabilidad gubernamental y los entes públicos | 1 | 23 |
| Contaduría y Finanzas Públicas | CSPAG | El servidor público en la administración gubernamental | 1 | 22 |
| Contaduría y Finanzas Públicas | CRCV | Redacción en contextos virtuales | 1 | 20 |
| Contaduría y Finanzas Públicas | CTO | Tipos de organizaciones | 1 | 18 |
| Contaduría y Finanzas Públicas | CISP | Integridad en el servicio público | 1 | 14 |
| Contaduría y Finanzas Públicas | CUTA | Uso de la tecnología en el aprendizaje | 1 | 14 |
| Administración y Gestión Pública | AAGPC | Administrar y gestionar un proyecto comunitario | 1 | 28 |
| Administración y Gestión Pública | ARCV | Redacción en contextos virtuales | 1 | 23 |
| Administración y Gestión Pública | ADP | División de poderes | 1 | 18 |
| Administración y Gestión Pública | AUTA | Uso de la tecnología en el aprendizaje | 1 | 18 |
| Administración y Gestión Pública | AISP | Integridad en el servicio público | 1 | 16 |
| Administración y Gestión Pública | AGP | Gestión pública | 1 | 15 |
| Enseñanza de la Matemática | EFDA | Fundamentos y didáctica de la aritmética | 1 | 13 |
| Enseñanza de la Matemática | ETP | Teorías pedagógicas | 1 | 12 |
| Enseñanza de la Matemática | ERCV | Redacción en contextos virtuales | 1 | 11 |
| Enseñanza de la Matemática | EHM | Historia de la matemática | 1 | 10 |
| Enseñanza de la Matemática | ETEE | Teorías educativas de la enseñanza | 1 | 10 |
| Enseñanza de la Matemática | EUTA | Uso de la tecnología en el aprendizaje | 1 | 9 |
| Gestión Industrial | GGA | Geometría analítica | 1 | 31 |
| Gestión Industrial | GE | Economía | 1 | 26 |
| Gestión Industrial | GRCV | Redacción en contextos virtuales | 1 | 26 |
| Gestión Industrial | GFGI | Fundamentos de la gestión industrial | 1 | 15 |
| Gestión Industrial | GUTA | Uso de la tecnología en el aprendizaje | 1 | 15 |
| Gestión Industrial | GLM | Lenguaje matemático | 1 | 13 |
| Seguridad Alimentaria | SAA | Antropología de la alimentación | 1 | 14 |
| Seguridad Alimentaria | SUTA | Uso de la tecnología en el aprendizaje | 1 | 14 |
| Seguridad Alimentaria | SADH | Alimentación y derechos humanos | 1 | 11 |
| Seguridad Alimentaria | SSA | Sustentabilidad y alimentación | 1 | 11 |
| Seguridad Alimentaria | SRCV | Redacción en contextos virtuales | 1 | 10 |
| Seguridad Alimentaria | SISA | Introducción a la seguridad alimentaria | 1 | 9 |
| Derecho | DRCV | Redacción en contextos virtuales | 1 | 31 |
| Derecho | DFD | Filosofía del Derecho | 1 | 28 |
| Derecho | DEMJ | Ética y Moral jurídica | 1 | 27 |
| Derecho | DISP | Integridad en el servicio público | 1 | 21 |
| Derecho | DHDM | Historia del Derecho en México | 1 | 19 |
| Derecho | DUTA | Uso de la tecnología en el aprendizaje | 1 | 17 |

## Evidencia de integración de consumidores

| Consumidor | Ruta de evaluación | Alcance de presentación |
|---|---|---|
| Malla curricular | `MallaCurricularAlumnoBean` → `MallaCurricularV2FacadeImpl` → adaptador y evaluador | Mapea decisión y motivos a icono/CSS; no recalcula oportunidades ni adeudos. |
| Asistente de inscripción | `TablaCurricularAsistidaBean` → `AsistenteCurricularV2FacadeImpl` | Proyecta situación, carga, motivos y alertas; solo redacta códigos. |
| Simulador | `TablaCurricularAsistidaBean.simularSeleccion` → `SimuladorCurricularV2ServiceImpl` | Añade validaciones complementarias, pero obtiene decisión académica y carga de la evaluación. |
| Asistente virtual | `TablaCurricularAsistidaBean` → `AsistenteCurricularResultadoAdapter` | Resume la misma evaluación; no calcula oportunidades ni adeudos. |

La consistencia dinámica entre consumidores continúa pendiente de comprobar en WildFly autorizado.

## Archivos modificados

- `InscripcionRepository`: clasificación de calificación ausente por cierre académico del periodo.
- `Bloque2HistorialOportunidadesRn20261106Test`: casos sintéticos de periodo vigente y cerrado con calificación ausente.
- `C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql`: universo PE2024, cierre por periodo, control de cursamiento, sensibilidad y UD del umbral.

## Pruebas y regresión

| Comando | Resultado |
|---|---|
| `mvn -Dtest=Bloque2HistorialOportunidadesRn20261106Test test` en `basegestor` | 16 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| `mvn test` en `basegestor` | 788 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| `mvn test` en `gestorWeb` | 38 pruebas, 0 fallas, 0 errores, 0 omitidas. |

## Limitaciones

- La fecha de cierre se deriva de los eventos de cada plan y periodo. Si el calendario institucional define un cierre de acta distinto, debe integrarse como fuente preferente.
- La clasificación de `0` sin baja aplicada sigue parametrizada como reprobación; las cifras de sensibilidad no cambian esa regla.
- La consistencia en UI requiere despliegue y validación controlada en WildFly.
