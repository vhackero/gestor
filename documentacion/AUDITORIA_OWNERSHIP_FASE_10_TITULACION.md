# Auditoria Ownership Fase 10: Titulacion

## OBJETIVO

Determinar el ownership tecnico de RN-10 (opciones de titulacion) y RN-10.1
(Servicio Social) para PE2027-V1, sin implementar reglas, flujos, contratos,
persistencia ni comportamiento productivo.

## FUENTES_NORMATIVAS

- `documentacion/V1_LINEA_BASE.md`: define las cuatro modalidades y la
  condicion de Servicio Social.
- `documentacion/V2_REGLAS_APROBADAS.md`: conserva RN-10 y RN-10.1 sin una
  reescritura PE2027.
- `documentacion/MATRIZ_REGLA_CODIGO.md`: clasifico ambas reglas como
  inexistentes en el alcance previamente auditado y requirio analizar
  titulacion antes de modificar modulos.

## RN_10

La regla relaciona modalidad de titulacion y Servicio Social:

| Modalidad | Servicio Social |
| --- | --- |
| Experiencia laboral | Requerido |
| Proyecto Terminal | No requerido |
| Examen CENEVAL | Requerido |
| Tesina | Requerido |

No se localizo un catalogo tecnico verificable de estas modalidades, ni un
flujo de seleccion, expediente, validacion, autorizacion o resolucion de
titulacion en el repositorio inspeccionado.

## RN_10_1

Servicio Social es obligatorio para las vias que RN-10 indique, con excepcion
para personas adultas mayores y funcionarios publicos. La fuente no define el
umbral de edad de "adulto mayor". No se localizo un estado, expediente,
documento, validacion ni fuente institucional de cumplimiento de Servicio
Social, ni evidencia verificable de la calidad de funcionario publico.

## COMPONENTES_LOCALIZADOS

| Archivo o clase | Responsabilidad observada | Estado | Relacion con RN-10/RN-10.1 |
| --- | --- | --- | --- |
| `EvaluacionCurricularV2ServiceImpl` | Evalua hechos curriculares en modo sombra, incluidos RN-9.3 y RN-9.4. | V2 | Proveedor potencial de hechos academicos; no selecciona modalidades ni evalua Servicio Social. |
| `TipoComponenteCurricularV2` | Clasifica `PROYECTO_TERMINAL`, `MODULO_INTEGRADOR` y `SEMINARIO_TITULACION` como componentes curriculares. | V2 | No constituye catalogo de modalidades de titulacion. |
| `UnidadEvaluacionCurricularV2DTO` | Transporta componente curricular y requisitos especificos del PE. | V2 | Puede explicar elegibilidad curricular previa, no acreditacion de Servicio Social. |
| `ExpedienteCurricularV2Facade`, `MallaCurricularV2Facade`, `AsistenteCurricularV2Facade` | Exponen informacion curricular V2 para consumidores futuros. | V2 | Pueden informar hechos academicos; no ejecutan titulacion. |
| `TblCasoAcademicoOperativoV2`, `TblCasoDictamenV2` | Gestion generica de casos y dictamenes operativos. | Parcial | No hay tipificacion localizada que los convierta en expediente de titulacion o Servicio Social. |
| `Modalidad` | Representa nivel de ensenanza, plan y programa. | Productivo/legacy | No representa modalidad de titulacion. |
| Servicios de grupos, dispersion e inscripcion | Matriculacion y operacion de grupos. | Productivo | Sin responsabilidad demostrada sobre RN-10 o RN-10.1. |

## TABLAS_LOCALIZADAS

| Tabla | Datos observados | Uso en esta auditoria |
| --- | --- | --- |
| `tbl_evaluacion_curricular_v2` | Resultado, situacion, semestre objetivo, reprobadas, version y fecha de evaluacion en sombra. | Evidencia academica reutilizable; no es expediente de titulacion. |
| `tbl_caso_academico_operativo` | Caso generico, folio externo, solicitud y estado. | No demuestra flujo de titulacion ni Servicio Social. |
| `tbl_caso_dictamen_v2` | Dictamen generico asociado a caso. | No demuestra modalidad ni resolucion de titulacion. |
| `tbl_persona` | Incluye fecha de nacimiento. | Dato personal existente, insuficiente para aplicar una excepcion sin umbral normativo. |
| `rel_usuario_datos_laborales` | Incluye cargo o puesto laboral. | No acredita por si mismo la condicion de funcionario publico. |

No se localizaron tablas verificables de modalidades de titulacion, Servicio
Social, constancias, expediente de titulacion, CENEVAL, tesina o experiencia
laboral aplicables a estas reglas.

## SERVICIOS_LOCALIZADOS

Se localizaron servicios V2 de evaluacion curricular y fachadas informativas,
asi como servicios productivos de inscripcion, grupos y dispersion. Ninguno
implementa una operacion de alta, consulta, validacion o resolucion de
modalidad de titulacion o Servicio Social.

## FLUJOS_LOCALIZADOS

El flujo V2 localizado es:

`contexto curricular -> evaluacion en sombra -> resultado/traza curricular`.

RN-9.4 usa ese flujo para informar requisitos academicos de egreso y la
elegibilidad curricular de Proyecto Terminal o Modulo Integrador. No se
localizo flujo de:

- seleccion de modalidad de titulacion;
- integracion de expediente;
- validacion de Servicio Social;
- autorizacion o dictamen de titulacion;
- ejecucion administrativa de egreso o emision documental.

## INTEGRACIONES_LOCALIZADAS

Las fachadas V2 preparan informacion para Malla Curricular, Asistente y
Simulador. No se localizo integracion con un sistema de titulacion, Servicio
Social, CENEVAL, repositorio documental ni servicio externo de validacion
laboral.

## MODALIDADES_TITULACION

No hay identificadores, catalogo, estados, configuracion por PE ni
persistencia localizada para EXPERIENCIA_LABORAL, PROYECTO_TERMINAL como
modalidad, CENEVAL o TESINA. La clase llamada `Modalidad` corresponde a
nivel/plan/programa, por lo que no debe reutilizarse como evidencia de un
catalogo de titulacion.

## SERVICIO_SOCIAL

Resultado de la busqueda: `DATO_NO_LOCALIZADO`.

No se encontro fuente verificable de cumplimiento, estatus, fecha,
documentos, area responsable o excepciones de Servicio Social. La ausencia de
un dato en los modulos inspeccionados no permite concluir incumplimiento.

## EXCEPCIONES

Existe fecha de nacimiento en el modelo de persona y cargo o puesto en datos
laborales. Ninguno permite automatizar RN-10.1:

- La norma no define una edad numerica para adulto mayor.
- Un cargo o puesto no prueba la calidad juridica de funcionario publico.
- No se localizo documento, catalogo, evidencia institucional ni resolucion
  de exencion.

No se debe copiar estos datos personales a trazas V2 ni inferir excepciones.

## PROYECTO_TERMINAL_CURRICULAR_VS_TITULACION

`PROYECTO_TERMINAL` existe en V2 como componente curricular para RN-9.4. RN-10
lo nombra como modalidad de titulacion. No se localizo una relacion tecnica
que pruebe que ambos sean el mismo objeto. Hasta contar con catalogo y mapeo
normativo verificable deben tratarse como conceptos potencialmente relacionados
pero distintos.

## EGRESO_VS_TITULACION

RN-9.4 entrega hechos academicos de egreso: obligatorias completas, ocho
optativas, al menos una electiva y componentes curriculares finales. No emite
titulo ni ejecuta egreso administrativo. RN-10 requiere una capa posterior de
modalidad, expediente, Servicio Social y resolucion institucional.

## RELACION_CON_RN_9_4

RN-10 no debe recalcular obligatorias, optativas, electivas, seriacion ni
requisitos academicos de egreso. Un modulo especializado debe consumir los
hechos ya evaluados por RN-9.4 y el Motor V2, manteniendo una sola fuente para
la evaluacion curricular.

## OWNERSHIP_RN_10

Clasificacion: `RESPONSABILIDAD_COMPARTIDA`.

- Motor Curricular V2: proveer hechos academicos en sombra, en especial el
  resultado de RN-9.4 y elegibilidad curricular de componentes.
- Modulo de Titulacion no localizado: catalogo de modalidades, seleccion,
  expediente, requisitos administrativos, validacion, autorizacion,
  resolucion y ejecucion.

No es seguro implementar RN-10 completamente en V2 mientras el owner
operativo, el catalogo y el contrato de integracion sigan sin identificar.

## OWNERSHIP_RN_10_1

Clasificacion: `REQUIERE_DEFINICION`.

La evaluacion depende de una fuente de Servicio Social y de evidencias de
excepcion que no fueron localizadas. V2 no debe crear un booleano ni inferir
adulto mayor o funcionario publico. El owner debe definirse entre un modulo
especializado de Servicio Social, Titulacion o una integracion institucional
externa antes de implementar la regla.

## DATOS_DISPONIBLES

- Hechos academicos RN-9.4 y trazabilidad curricular V2.
- Componentes curriculares Proyecto Terminal, Modulo Integrador y Seminario de
  Titulacion para propositos curriculares.
- Fecha de nacimiento y cargo o puesto laboral como datos crudos, sin valor
  normativo suficiente para excepciones.
- Casos y dictamenes genericos que no estan clasificados como titulacion.

## DATOS_FALTANTES

- Owner y flujo de titulacion.
- Catalogo de modalidades RN-10 y aplicabilidad por PE.
- Fuente verificable de Servicio Social y su estado.
- Regla de asociacion modalidad -> Servicio Social configurable/auditable.
- Umbral normativo de adulto mayor.
- Evidencia institucional de funcionario publico.
- Expediente, documentos, autorizacion, resolucion y estado administrativo.
- Mapeo entre Proyecto Terminal curricular y modalidad de titulacion.
- Contrato de integracion con el modulo responsable.

## RIESGOS

- Implementar en V2 crearia un segundo owner de titulacion y duplicaria
  RN-9.4.
- Inferir excepciones desde datos personales o laborales produciria decisiones
  no normativas y tratamiento innecesario de PII.
- Reutilizar la clase `Modalidad` confundiria plan/programa con modalidad de
  titulacion.
- Equiparar Proyecto Terminal curricular con modalidad sin mapeo verificable
  podria aplicar requisitos incorrectos.

## MATRIZ_RESPONSABILIDADES

| Regla | Responsabilidad | Motor V2 | Sistema operativo/especializado |
| --- | --- | --- | --- |
| RN-9.1 | Contexto administrativo y hechos academicos en sombra | Orquesta reglas existentes | Sin ejecucion productiva en V2 |
| RN-9.2 | Dispersion, matriculacion y grupos | Fuera de alcance | Modulo operativo de grupos/matriculacion |
| RN-9.3 | Avance curricular S7/S8 | Evalua en sombra | Consume resultados si corresponde |
| RN-9.4 | Requisitos academicos de egreso y componentes | Evalua en sombra | No ejecuta egreso/titulacion |
| RN-9.5 | Trazabilidad curricular | Infraestructura transversal V2 | Puede recibir referencias externas sin duplicar PII |
| RN-10 | Modalidad y expediente de titulacion | Aporta hechos academicos | Modulo de Titulacion por identificar |
| RN-10.1 | Servicio Social y exenciones | No evalua sin fuente valida | Modulo/servicio de Servicio Social o integracion por identificar |

## ESCENARIO_ARQUITECTONICO_RECOMENDADO

Escenario C, condicionado por la evidencia: el Motor V2 entrega hechos
academicos reutilizables y un modulo especializado de Titulacion/Servicio
Social, aun no localizado, evalua requisitos administrativos y ejecuta sus
procesos. Antes de una Fase 10B se debe identificar ese owner y acordar un
contrato de solo lectura para los hechos V2 y para los estados institucionales
de Servicio Social/exencion.

## FASES_POSTERIORES_PROPUESTAS

No se definen como autorizadas. Si se identifica el owner, la secuencia
tecnica minima seria:

1. Acordar catalogo de modalidades, PE aplicables y relacion con Servicio
   Social.
2. Identificar la fuente institucional de Servicio Social y las evidencias de
   excepcion.
3. Definir el contrato de integracion con hechos RN-9.4 sin duplicar reglas.
4. Delimitar una fase de implementacion en el modulo responsable, no por
   defecto en V2.

## CONCLUSION

La auditoria no encontro un modulo identificable de Titulacion ni de Servicio
Social en el repositorio inspeccionado. RN-10 requiere responsabilidad
compartida: V2 aporta hechos academicos, mientras la modalidad y el expediente
deben pertenecer a un owner especializado por identificar. RN-10.1 requiere
definicion de fuente y criterios institucionales; no es implementable de forma
segura con los datos localizados.

`CAMBIO_PRODUCTIVO = NINGUNO`

`CAMBIO_MOTOR_V2 = NINGUNO`

`CAMBIO_BD = NINGUNO`

`CAMBIO_DTO = NINGUNO`

`CAMBIO_SERVICIOS = NINGUNO`
