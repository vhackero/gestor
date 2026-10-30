# Primera entrega: contrato de lectura y Situacion academica

## Alcance

Esta entrega incorpora metadatos de consulta para la pestaña **Situacion academica** del Asistente de inscripcion y orientacion educativa. Es una ampliacion de lectura: no modifica inscripcion, historial, oportunidades, oferta ni decisiones productivas.

Quedan fuera de esta entrega la construccion de **UD prioritarias**, **Expectativa de logro** y **Proyeccion de carga**. Esos componentes deberan consumir el mismo contrato y no sintetizar escenarios con datos incompletos.

## Verificacion previa de RN de octubre

| Cambio normativo | Estado | Evidencia de implementacion | Prueba que lo acredita |
| --- | --- | --- | --- |
| RN-1.5: no reinscribir una UD acreditada solo para mejorar promedio | Implementado | `EvaluacionCurricularV2ServiceImpl.evaluarMejoraUnidad` | `EvaluacionCurricularV2Fase2ETest.f2EU18...`; ejecutada en la suite focalizada de 82 pruebas |
| RN-6.1: se activa por una UD candidata de S5, aunque `semestreObjetivo` sea distinto | Implementado | `procedenciaCtr01`, `tieneCandidataQuinto`, `esCandidataQuinto` | `EvaluacionCurricularV2Fase5CTest.f5CU01...` y `EvaluacionCurricularV2Fase5DTest.candidata...`; suite focalizada ejecutada |
| RN-6.1.3: supuestos tipificados se resuelven automaticamente cuando cumplen condiciones | Implementado con cobertura puntual pendiente | `evaluarSupuestosTipificadosAvance` | Las pruebas de F5C/F5D ejercitan los supuestos; falta una prueba nominada que demuestre expresamente ausencia de autorizacion individual |
| Retiro de `SIN_DEPENDENCIA_SERIACION` como excepcion | Implementado | `CodigoSupuestoAvanceCondicionadoV2` y `evaluarSupuestosTipificadosAvance` no lo generan | `EvaluacionCurricularV2Fase5CTest`; ejecutada |
| RN-9.3: no existe umbral de 50 % de creditos | Implementado | `evaluarAvanceUltimosSemestresRn93`, evidencia `sinUmbralPorcentualCreditos=true` | `EvaluacionCurricularV2Fase9BTest`; ejecutada |
| RN-9.4: no exige electiva minima | Implementado | `evaluarRequisitosAcreditacionEgresoRn94` | `EvaluacionCurricularV2Fase9CTest`; ejecutada |
| RN-7.2 definitivo: optativas requeridas/adicionales, no categoria activa ELECTIVA | Implementado en la evaluacion; limpieza de contrato pendiente | `evaluarElectivasRn72` | `EvaluacionCurricularV2Fase7BTest`; ejecutada. Persisten nombres legacy en DTO/metodos que deben retirarse de pantallas sin alterar registros historicos |
| RN-9.4: cumplimiento curricular separado de adeudo de optativa adicional | Implementado parcialmente | `evaluarRequisitosAcreditacionEgresoRn94` no bloquea por electiva; la ruta de sustitucion requiere catalogo institucional | `EvaluacionCurricularV2Fase9CTest`; falta prueba de sustitucion con catalogo real |

La suite focalizada ejecutada para estas verificaciones reporto: **82 pruebas, 0 fallas, 0 errores, 0 omitidas**. Esta evidencia no sustituye la regresion completa posterior a las entregas restantes.

## Contrato comun de lectura

`AsistenteInscripcionContextoDTO.metadatosConsulta` agrega un contrato aditivo para comunicar calidad y corte de datos:

| Campo | Fuente y significado | Estado de fuente |
| --- | --- | --- |
| `periodoActual` | Contexto de inscripcion activo | No convierte un periodo ausente en un periodo supuesto |
| `fechaCorte` | `TrayectoriaPermanenciaDTO.fechaCorte` | Fecha desde la que se interpreta la trayectoria |
| `versionReglas` | Configuracion V2 (`ConfiguracionEvaluacionCurricularV2DTO`) | Es etiqueta tecnica; no sustituye vigencia ni poblacion institucional |
| `estadoExpediente` | Estado academico disponible en `InscripcionContextoDTO` | `DISPONIBLE` o `PARCIAL` |
| `estadoTrayectoria` | Determinabilidad y mensaje de revision de trayectoria | `DISPONIBLE` o `PARCIAL` |
| `estadoOferta` | Presencia de la fuente de oferta, independiente de que haya oferta vigente | `DISPONIBLE` o `PARCIAL` |
| `estadoEvaluacionV2` | Ejecucion del resultado V2 por el bean | `DISPONIBLE` o `ERROR` |
| `camposPendientes` | Campos concretos que no pudieron comprobarse | Evita sustituir faltantes por cero |

Los valores posibles son `DISPONIBLE`, `VACIO`, `PARCIAL`, `DESACTUALIZADO` y `ERROR`. `VACIO` significa que la consulta concluyo sin registros; no significa error ni inexistencia de la fuente.

## Indicadores y unidades de computo

Los indicadores existentes conservan su calculo actual. Esta entrega no altera numeradores ni denominadores:

| Indicador | Unidad | Denominador / fuente |
| --- | --- | --- |
| Avance curricular acreditado | Creditos y UD distintas acreditadas | Total curricular del plan, no solo oferta vigente |
| Resumen actual | UD vigentes distintas por tipo | Inscripciones/UD en curso del periodo, sin contar bajas parciales como vigentes |
| Resumen global | UD distintas acreditadas y pendientes | Malla del plan y estado academico vigente, deduplicados por UD |
| Pendientes de regularizacion | UD distintas reprobadas vigentes | Resultado V2 y estado normalizado; no incluye `NO_CURSADA`, `BAJA_PARCIAL` o `NO_INSCRITA` como reprobacion |

El resultado V2 sigue siendo la fuente de decisiones normativas. El contrato de lectura solo informa el corte, las fuentes y si una ausencia debe revisarse.

## Correspondencia V2 y presentacion

`TablaCurricularAsistidaBean.cargarResultadoEvaluacionV2` actualiza el estado de evaluacion del contexto y sigue adaptando `ResultadoEvaluacionCurricularV2DTO` para la vista. `getResumenFuentesSituacion` presenta una leyenda discreta sin datos personales: corte, version tecnica y estado de Expediente, Trayectoria, Oferta y Evaluacion curricular.

No se genera una seleccion, escenario hipotetico ni traslado al Simulador en esta entrega.

## Pruebas y validacion pendiente

Prueba nueva: `AsistenteCurricularV2ResultadoAdapterTest.muestraCorteYEstadosDeFuenteSinConvertirParcialEnCero` verifica que fecha y estados se presenten sin transformar una fuente parcial o vacia en cero.

Pruebas focalizadas del adaptador: **9 pruebas, 0 fallas, 0 errores, 0 omitidas**.

Compilacion verificada:

- `basegestor`: `mvn -q -DskipTests package` exitoso.
- `gestorWeb`: prueba focalizada y `mvn -q -DskipTests package` exitosos.

Pendiente concreto antes de aprobar visualmente esta entrega:

1. Ejecutar regresion completa despues de integrar las restantes pantallas.
2. Validar en WildFly la leyenda en escritorio y movil, incluyendo expediente con fuente parcial, oferta disponible sin unidades ofertadas y error controlado del evaluador V2.
3. Agregar cobertura nominada del supuesto tipificado sin autorizacion individual y de sustitucion de optativa adicional con catalogo institucional verificable.
4. Retirar de las nuevas pantallas la categoria activa `ELECTIVA`; la limpieza de nombres legacy en contratos internos requiere una migracion separada y pruebas de compatibilidad historica.

## Resultado

**ENTREGA_TECNICA_PARCIAL**. El contrato comun y la lectura de Situacion academica quedan listos para ser reutilizados. No se declara aprobacion visual ni se han construido aun los escenarios de logro o las alternativas de carga.
