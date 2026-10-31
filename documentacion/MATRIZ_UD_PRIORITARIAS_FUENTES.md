# Matriz de fuentes: UD prioritarias

## Identificacion

- Rama: `feature/desacople_motor_historico`.
- Entrega base: `c448a213 feat: integra fuentes verificables ud prioritarias`.
- Alcance: consulta y orientacion. Ningun bloque modifica historial, oportunidades, regularidad, plazo, reincorporacion ni inscripcion.
- Estado visual: pendiente de despliegue y validacion manual; empaquetar un WAR no constituye evidencia de despliegue.

## Cobertura de los seis bloques

| Bloque | Fuente y campos usados | Significado y unidad del contador | Deduplicacion | Estado y limitaciones | Prueba |
| --- | --- | --- | --- | --- | --- |
| UD reprobadas pendientes | `EstadoAcademicoDTO.materiasReprobadas`: `idPrograma`, `clavePrograma`, `nombrePrograma`, tipo, estructura y subestructura. `AsistenteInscripcionServiceImpl.incorporarMateriasReprobadasNoPresentes` las integra aunque no tengan oferta. | **UD curriculares distintas** con reprobacion vigente. No cuenta intentos, personas ni movimientos. | `udId`; si falta, clave curricular. | Fuente institucional integrada en el contexto. No expone periodo ni calificacion de ultima reprobacion en este contrato. | Preparada: `AsistenteCurricularV2ResultadoAdapterTest.priorizacionIncluyeReprobadaDelContextoAunqueNoEsteEnLaOfertaVisual`. Integracion real: pendiente de validacion visual. |
| UD con antecedentes de recursamiento | Reprobadas anteriores y `InscripcionPreviaMateriasDTO` con estado de cursamiento, relacionados por `idPrograma`, clave o nombre cuando estan disponibles. | **UD distintas** reprobadas historicamente y actualmente en curso. Reinscribirse no regulariza el adeudo. | Identidad curricular de UD. | Fuente institucional integrada; fecha y calificacion del intento en curso no estan disponibles. | Preparada: `EvaluacionCurricularV2Fase2DTest.f2DU10EnCursoNoCuentaFallo`. Integracion real: pendiente. |
| Registros de baja parcial | `EstadoAcademicoDTO.materiasBajas`, procedente de `rel_persona_bajas`, `rel_motivo_baja` y `cat_tipo_bajas`; campos `idPrograma`, `clavePrograma`, `idTipoBaja`, `tipoBaja`, `fechaModificacion`. | Tarjeta principal: **UD distintas afectadas**. Texto secundario: **movimientos registrados**. Ninguno equivale automaticamente a solicitud, autorizacion o baja aplicada. | UD afectadas: `idPrograma`, o clave si falta. Movimientos: sin deduplicar, una fila de fuente por movimiento. | Fuente institucional integrada, pero no expone estado de solicitud, evidencia de autorizacion ni aplicacion final. La fecha de modificacion no se presenta como periodo academico. | Preparada: `AsistenteCurricularV2ResultadoAdapterTest.deduplicaBajasParcialesYSeparaLasTemporalesPorTipoRegistrado`; acredita 1 UD y 2 movimientos. Integracion real: pendiente. |
| Bajas temporales registradas | Mismo conjunto `materiasBajas`, filtrado por `tipoBaja` que contiene `TEMPORAL`. | **Registros distintos de UD** asociados a baja temporal. No son semestres autorizados, saldo de permanencia ni una reincorporacion. | `idPrograma`, o clave si falta. | Fuente institucional integrada, pero autorizacion, vigencia, periodos consumidos, renovaciones y reincorporacion no se verifican. Se presentan como antecedentes pendientes de verificacion. No alteran V2 ni efectos escolares. | Preparada: `AsistenteCurricularV2ResultadoAdapterTest.deduplicaBajasParcialesYSeparaLasTemporalesPorTipoRegistrado`. Integracion real: pendiente. |
| Sustituciones de optativas adicionales | No hay fuente integrada de cadena entre adeudo original y UD sustitutas. | Sin contador acumulado. Un dato individual no puede representar el adeudo total. | No aplica hasta contar con relacion verificable. | No localizado en el alcance revisado. No se infieren equivalencias, reinicios de oportunidades ni regularizacion. | Presentacion: `TablaCurricularAsistidaBean.getResumenSustitucionesOptativasPrioritarias`. Prueba de fuente real: pendiente. |
| Reprobaciones registradas y oportunidades RN-1.4 | `InscripcionRepository.consultarIntentosAsignaturas`: agregado `calificacion_final < 60`, `id_plan`, `id_programa`. | **Reprobaciones registradas por UD**, dato informativo. No son oportunidades consumidas, restantes, agotadas ni oportunidad en curso. | Una fila por `idPrograma` de la consulta agregada; la vista conserva una UD por identidad. | Fuente institucional integrada pero incompleta para RN-1.4: faltan primera inscripcion computable, id de intento, bajas autorizadas y estado en curso. | Preparada: `AsistenteCurricularV2ResultadoAdapterTest.presentaReprobacionesRegistradasSinConvertirlasEnOportunidadesRn14`. Motor con intento normalizado: `EvaluacionCurricularV2Fase2DTest`. Integracion real del detalle: pendiente. |

## Reglas de presentacion

1. Los registros de baja parcial no se etiquetan como “aplicados” ni como solicitudes autorizadas sin evidencia adicional.
2. Los antecedentes de baja temporal se muestran como “Registros asociados a baja temporal; autorizacion y vigencia no verificadas”. No muestran periodos consumidos, saldo, renovacion ni reincorporacion autorizada.
3. Los registros informativos de baja no se envian como cambios a la evaluacion V2 y no modifican por inferencia regularidad, oportunidades, plazo o efectos escolares.
4. `Reprobaciones registradas: N` permanece exclusivamente informativo. El calculo completo de RN-1.4 sigue pendiente de su fuente detallada.
5. La sustitucion de optativas adicionales requiere catalogo institucional verificable: UD origen, sustituta, vigencia y relacion con el adeudo.

## Validacion pendiente

El WAR debe desplegarse en el ambiente autorizado y validarse manualmente con datos anonimizados. Se requieren al menos: una reprobada no ofertada, una recursada en curso, baja parcial con mas de un movimiento sobre la misma UD, antecedente temporal y ausencia de fuente de sustituciones. La validacion debe confirmar etiquetas, contadores, ausencia de ceros simulados y que la pantalla no declare autorizaciones ni efectos no acreditados.
