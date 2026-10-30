# Primera entrega: contrato de lectura y Situacion academica

## Identificacion de la entrega

- Rama: `feature/desacople_motor_historico`.
- Base de la primera entrega: `f8adc866 feat: agrega contrato de lectura asistente curricular`.
- Correcciones normativas preexistentes, fuera de la primera entrega: `732dfcd6 fix: actualiza reglas motor curricular v2 octubre 2026`.
- Esta ampliacion de evidencia incorpora pruebas y la correccion de composicion RN-9.4 para el adeudo adicional; debe confirmarse en el commit de cierre posterior.

Archivos de implementacion de esta entrega:

- `AsistenteInscripcionContextoDTO`, `MetadatosConsultaAsistenteDTO` y `EstadoFuenteConsultaAsistente`.
- `AsistenteInscripcionServiceImpl`.
- `TablaCurricularAsistidaBean` y `tablaCurricularAsistida.xhtml`.
- `EvaluacionCurricularV2ServiceImpl` para la composicion RN-9.4.
- Pruebas `EvaluacionCurricularV2Fase5CTest`, `EvaluacionCurricularV2Fase9CTest`, `TrayectoriaPermanenciaAsistenteTest` y `AsistenteCurricularV2ResultadoAdapterTest`.

Comandos ejecutados durante la entrega:

```bash
(cd codigoFuente/basegestor && mvn -q -Dtest=EvaluacionCurricularV2Fase5CTest,EvaluacionCurricularV2Fase9CTest test)
(cd codigoFuente/gestorWeb && mvn -q -Dtest=AsistenteCurricularV2ResultadoAdapterTest test)
(cd codigoFuente/basegestor && mvn -q test)
(cd codigoFuente/gestorWeb && mvn -q test)
```

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

La suite focalizada ejecutada para las correcciones normativas reporto: **82 pruebas, 0 fallas, 0 errores, 0 omitidas**. Esta evidencia no sustituye la regresion completa posterior a las entregas restantes.

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

### Reglas para estados y cortes

| Fuente | DISPONIBLE | VACIO | PARCIAL | DESACTUALIZADO | ERROR | Corte conservado |
| --- | --- | --- | --- | --- | --- | --- |
| Expediente | Existe `EstadoAcademicoDTO` en el contexto | No se produce actualmente: un expediente sin estado es incompleto, no un expediente vacio | Falta `EstadoAcademicoDTO` | No se puede inferir sin marca temporal de la fuente | El servicio actual propaga el error; no se disfraza como cero | La fuente revisada no expone fecha de actualizacion; se muestra `no informado` |
| Trayectoria | Indicadores determinables y sin mensaje de revision | No se produce actualmente | Falta evidencia para calcular la trayectoria o existe mensaje de revision | No se puede clasificar automaticamente sin vigencia institucional | El servicio actual propaga el error | `TrayectoriaPermanenciaDTO.fechaCorte` |
| Oferta | Consulta realizada con oferta vigente | Consulta realizada sin UD vigentes (`ofertaVigenteReal=false`) | `ofertaVigenteReal=null` | No se puede clasificar automaticamente sin fecha de extraccion/vigencia | El servicio actual propaga el error | La fuente revisada no expone fecha de extraccion; se muestra `no informado` |
| Evaluacion V2 | El facade devuelve `ResultadoEvaluacionCurricularV2DTO` | No aplica | Estado inicial previo a la ejecucion | No aplica mientras la configuracion no aporte vigencia/poblacion | Resultado nulo o excepcion controlada en `TablaCurricularAsistidaBean` | Instante de evaluacion V2, separado de las fuentes academicas |

`DESACTUALIZADO` se conserva en el contrato y la presentacion, pero no se genera automaticamente: no existe todavia un campo de fecha de actualizacion ni una regla de vigencia para Expediente u Oferta. Inventar ese estado seria incorrecto. La pantalla no presenta un “corte unico”; muestra el corte de cada fuente o `no informado`.

## Indicadores y unidades de computo

Los indicadores existentes conservan su calculo actual. Esta entrega no altera numeradores ni denominadores:

| Indicador | Unidad | Denominador / fuente |
| --- | --- | --- |
| Avance curricular acreditado | Creditos y UD distintas acreditadas | Total curricular del plan, no solo oferta vigente |
| Resumen actual | UD vigentes distintas por tipo | Inscripciones/UD en curso del periodo, sin contar bajas parciales como vigentes |
| Resumen global | UD distintas acreditadas y pendientes | Malla del plan y estado academico vigente, deduplicados por UD |
| Pendientes de regularizacion | UD distintas reprobadas vigentes | Resultado V2 y estado normalizado; no incluye `NO_CURSADA`, `BAJA_PARCIAL` o `NO_INSCRITA` como reprobacion |

El resultado V2 sigue siendo la fuente de decisiones normativas. El contrato de lectura solo informa el corte, las fuentes y si una ausencia debe revisarse.

### Reglas de conteo verificadas

| Indicador | Numerador | Denominador | Llave de deduplicacion | Exclusiones | Fuente | Cobertura |
| --- | --- | --- | --- | --- | --- | --- |
| Avance curricular | Creditos de UD acreditadas vigentes | Creditos totales del plan | `idPrograma` / clave curricular, no oferta | Reprobada, no cursada, baja parcial, no inscrita y en curso | Estructura curricular y estado vigente | El motor valida creditos del PE; falta una prueba de integracion del indicador visual contra una oferta parcial |
| UD actuales | UD inscritas o en curso distintas | No aplica | Clave/`udId` | Baja parcial aplicada no es vigente | Inscripcion actual y materias en curso | El contrato no recalcula el indicador |
| Reprobadas vigentes | UD distintas con reprobacion vigente | No aplica | `idPrograma` | NP por baja autorizada, baja parcial, no cursada y no inscrita | Historial normalizado por V2 | Cubierto por F4/F5/F7; la baja no se convierte en reprobacion |
| Optativas requeridas | Optativas acreditadas hasta ocho | 8 segun RN-7.1.1 | `idPrograma` | Duplicados tecnicos | Estructura e historial | Cubierto por F7D y F9C |
| Adeudo de optativa adicional | UD distinta posterior al requisito de ocho | No aplica | Requiere ruta de sustitucion verificable; no se infiere por representaciones repetidas | No duplica adeudos por la cadena | Historial; catalogo de sustituciones pendiente | RN-9.4 ya no lo usa para bloquear cumplimiento; falta fuente de sustituciones |

No hay fuente integrada de rutas de sustitucion. Por ello no se declara probada una cadena real ni se muestra un total definitivo de sustituciones; es una limitacion explicita, no un cero.

## Correspondencia V2 y presentacion

`TablaCurricularAsistidaBean.cargarResultadoEvaluacionV2` actualiza el estado de evaluacion del contexto y sigue adaptando `ResultadoEvaluacionCurricularV2DTO` para la vista. `getResumenFuentesSituacion` presenta una leyenda discreta sin datos personales: corte, version tecnica y estado de Expediente, Trayectoria, Oferta y Evaluacion curricular.

No se genera una seleccion, escenario hipotetico ni traslado al Simulador en esta entrega.

## Pruebas y validacion pendiente

Prueba nueva: `AsistenteCurricularV2ResultadoAdapterTest.muestraCorteYEstadosDeFuenteSinConvertirParcialEnCero` verifica que fecha y estados se presenten sin transformar una fuente parcial o vacia en cero.

Pruebas adicionales:

- `AsistenteCurricularV2ResultadoAdapterTest.expresaEstadosDeFuenteSinSimularUnCorteUnico` cubre `VACIO`, `PARCIAL`, `DESACTUALIZADO` y `ERROR` en la presentacion.
- `AsistenteCurricularV2ResultadoAdapterTest.errorDeEvaluacionV2EsControladoSinAlterarLosCortesDeLasFuentes` conserva los cortes de las fuentes ante el error V2.
- `TrayectoriaPermanenciaAsistenteTest.distingueConsultaDeOfertaVaciaDeFuenteParcial` diferencia una consulta exitosa sin oferta de una fuente incompleta.
- `EvaluacionCurricularV2Fase5CTest.f5CU19SupuestoTipificadoProcedeSinResolucionIndividualYConCausasConcurrentes` cubre baja parcial y falta de oferta concurrentes sin instancia ni folio extraordinario y sin imponer un maximo de pendientes.
- `EvaluacionCurricularV2Fase9CTest.f9CU13AdeudoOptativaAdicionalNoBloqueaCumplimientoCurricularPeroConservaRegularizacion` prueba ocho optativas requeridas y obligatorias acreditadas mas una optativa adicional reprobada: RN-9.4 queda cumplida, la reprobada conserva irregularidad y no se ejecuta ningun efecto productivo.

Pruebas focalizadas del adaptador: **11 pruebas, 0 fallas, 0 errores, 0 omitidas**.

Regresion ejecutada para esta ampliacion:

- `basegestor`: **765 pruebas, 0 fallas, 0 errores, 0 omitidas**.
- `gestorWeb`: **20 pruebas, 0 fallas, 0 errores, 0 omitidas**.

Compilacion verificada:

- `basegestor`: `mvn -q -DskipTests package` exitoso.
- `gestorWeb`: `mvn -q -DskipTests package` exitoso.

Pendiente concreto antes de aprobar visualmente esta entrega:

1. Repetir regresion completa despues de integrar las restantes pantallas.
2. Validar en WildFly la leyenda en escritorio y movil, incluyendo expediente con fuente parcial, oferta disponible sin unidades ofertadas y error controlado del evaluador V2.
3. Agregar cobertura nominada del supuesto tipificado sin autorizacion individual y de sustitucion de optativa adicional con catalogo institucional verificable.
4. Retirar de las nuevas pantallas la categoria activa `ELECTIVA`; la limpieza de nombres legacy en contratos internos requiere una migracion separada y pruebas de compatibilidad historica.

La presentacion actual de Situacion academica no incorpora `ELECTIVA` como categoria vigente. Los nombres internos heredados no se eliminan en esta entrega porque son compatibilidad tecnica; no implican reclasificar ni modificar registros historicos.

5. La validacion visual requiere desplegar el artefacto de esta ampliacion en WildFly y usar una sesion autenticada de prueba. El servidor local existe, pero no se ha desplegado este commit ni se producirian capturas autenticas desde una sesion no controlada; por tanto no se declara evidencia de escritorio ni movil.

## Resultado

**ENTREGA_TECNICA_PARCIAL**. El contrato comun y la lectura de Situacion academica quedan listos para ser reutilizados. No se declara aprobacion visual ni se han construido aun los escenarios de logro o las alternativas de carga.
