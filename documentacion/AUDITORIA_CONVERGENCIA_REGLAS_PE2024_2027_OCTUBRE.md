# Auditoria de Convergencia Normativa PE2024-2027 Octubre

## Objetivo

Contrastar el Motor Curricular V2, sus adaptadores y los consumidores Malla Curricular,
Asistente de Inscripcion y Orientacion Educativa, Simulador y Asistente Virtual contra
`PE2024-2027- Reglas de negocio_Octubre.docx`, entregado como version oficial final.

Esta auditoria no modifica inscripcion, reinscripcion, matriculacion, grupos, dispersion,
titulacion ni servicio social productivos. La evaluacion se limita a decisiones y
explicaciones curriculares en modo sombra.

## Fuentes y metodo

| Fuente | Uso |
| --- | --- |
| `PE2024-2027- Reglas de negocio_Octubre.docx` | Fuente normativa prioritaria. |
| `documentacion/V2_REGLAS_APROBADAS.md` | Base que implemento el Motor V2. Es un borrador anterior y queda subordinada al documento de octubre ante conflicto. |
| `documentacion/V1_LINEA_BASE.md` | Referencia legacy; no prevalece sobre la fuente oficial. |
| `EvaluacionCurricularV2ServiceImpl` y contratos V2 | Cobertura tecnica actual. |
| Adaptadores y consumidores V2 | Verificacion de propagacion hacia Malla, Asistente, Simulador y Asistente Virtual. |

Clasificacion:

- `CONFORME`: coincide materialmente con el documento oficial.
- `CONFORME_CON_AJUSTE_DE_DATOS`: el motor lo representa, pero el adaptador o la fuente real debe aportar evidencia completa.
- `CAMBIO_NORMATIVO_OBLIGATORIO`: la implementacion actual contradice la fuente oficial.
- `NUEVO_O_AMPLIADO`: la regla final agrega conducta no cubierta de forma completa.
- `FRONTERA_OPERATIVA`: requiere proceso productivo u otro modulo; no se incorpora al Motor V2 sin fuente y ownership.
- `REQUIERE_DEFINICION_ACADEMICA`: falta parametro, plan, poblacion o evidencia institucional para automatizar sin inferir.

## Hallazgos Criticos

### 1. RN-6.1 cambia su disparador

El motor actual activa CTR-01 cuando `semestreObjetivo == 5` y `periodoNormativo >= 2027-1`.
El documento final dispone que aplica cuando **se pretende inscribir al menos una UD de quinto**,
incluso si la trayectoria tiene otro semestre objetivo y la carga es mixta.

Impacto: se requiere agregar al contexto V2 una coleccion de UD pretendidas/seleccionadas para
evaluacion y derivar el disparador desde el semestre curricular de esas UD. No puede deducirse
del semestre objetivo ni de toda la malla.

### 2. RN-9.3 ya no contiene umbral de 50% de creditos

La implementacion actual bloquea S7/S8 si el porcentaje de creditos acreditados es menor a 50.
El documento final de octubre no establece ese umbral. Para S7/S8 exige evaluar plan y version,
situacion, prioridad, carga, oferta, prerrequisitos y RN-6.4; RN-6.1 no aplica por el mero hecho
de ser S7/S8.

Impacto: el umbral de 50%, sus bloqueos por UD y sus textos deben retirarse de la version
normativa oficial. Los creditos pueden conservarse como indicador informativo, no como restriccion
RN-9.3, salvo que exista un instrumento institucional adicional identificado y versionado.

### 3. RN-9.4 fue sustituida materialmente

La implementacion actual exige todas las obligatorias, ocho optativas y una electiva; tambien
restringe Proyecto Terminal/Modulo Integrador por todas las seriadas S1-S6. El documento final
requiere todas las obligatorias y ocho optativas requeridas, mas creditos y requisitos del plan
aplicable. No exige una electiva minima.

Para seminarios, modulos u otros componentes exige requisitos expresos del plan; para UD de
Proyecto Terminal identificadas por plan/instrumento exige todas las UD previas del mapa. No puede
inferirse la equivalencia por nombre ni trasladarse automaticamente a seminarios o modulos.

Ademas, un adeudo de optativa adicional conserva efectos de regularidad y regularizacion, pero no
bloquea por si solo certificacion o titulacion ni reduce creditos ya acreditados.

### 4. RN-1.5 no conserva la mejora de promedio anterior

El documento final establece que no se reinscribe una UD acreditada solamente para mejorar el
promedio ni se sustituye la calificacion por el mejor resultado. El Motor V2 aun contiene la
evaluacion historica de mejora de promedio para 8.5. Esa conducta debe retirarse de la version
normativa oficial o quedar aislada para una version historica explicita; no puede seguir emitiendo
una recomendacion PE2024-2027.

### 5. La version normativa no esta nombrada en la fuente

El codigo y trazas usan `PE2027-V1`; el documento final cubre PE2024 y PE2027 pero no declara un
identificador tecnico de version. No se debe inventar una cadena definitiva. Mientras se confirma,
la configuracion debe permitir un identificador externo y la traza debe registrar documento,
fecha de corte, poblacion y vigencia. La activacion de una nueva version requiere la cadena
oficial que indiquen CAI/DAEAE.

### 6. El adaptador no puede inferir semestre objetivo desde la oferta

`ContextoEvaluacionCurricularV2AdapterImpl` obtiene actualmente el semestre objetivo como el
semestre mas alto de las UD ofertadas. Esto contradice el glosario final: el avance curricular se
determina por trayectoria efectiva, no solo por oferta ni por tiempo transcurrido. Una oferta con
UD de varios semestres puede activar RN-6.1 o RN-9.3 incorrectamente.

Impacto: el adaptador debe recibir el semestre/avance curricular desde la fuente canonica de
trayectoria. Si esa fuente no puede demostrarlo, debe marcar `REQUIERE_REVISION`; no usar el
maximo de la oferta como sustituto.

### 7. El Simulador no propaga la seleccion al contexto V2

`SimuladorCurricularV2ServiceImpl` recibe los identificadores seleccionados, pero construye y
evalua el contexto antes de incorporarlos. Por ello no puede aplicar la version final de RN-6.1 a
una carga mixta que contiene una UD de quinto semestre, ni conservar en traza las UD que motivaron
la evaluacion.

Impacto: se requiere una coleccion inmutable de identificadores de UD pretendidas en el contexto
V2, poblada solo por el Simulador. Malla y Asistente, que no representan una seleccion, no deben
inventarla.

## Matriz Regla por Regla

| Regla | Estado | Cobertura actual | Ajuste o evidencia requerida |
| --- | --- | --- | --- |
| RN-1.1 Inactividad | CONFORME_CON_AJUSTE_DE_DATOS | V2 representa actividad, inactividad, baja temporal y baja definitiva. | Adaptador debe entregar periodos no reinscritos, causa y movimiento institucional; detectar no equivale a ejecutar baja. |
| RN-1.2 Regular/irregular | CONFORME | Distingue reprobacion vigente, no cursada y bajas; carga se deriva despues. | Mantener el corte institucional como dato de entrada trazable. |
| RN-1.3 Recuperacion | CONFORME | Recalcula situacion con estado vigente y reprobadas unicas. | Verificar que el adaptador entregue rectificaciones y regularizaciones actualizadas. |
| RN-1.4 Oportunidades | CONFORME_CON_AJUSTE_DE_DATOS | Cuenta intentos por UD y los separa de RN-6.4. | Para optativas adicionales falta la cadena real de sustitucion/adeudo. |
| RN-1.5 Acreditada/rectificacion | CAMBIO_NORMATIVO_OBLIGATORIO | V2 conserva una ruta de mejora de promedio heredada. | Retirar su uso de la version oficial; conservar rectificacion solo con registro institucional. |
| RN-2.1 Configuracion de procesos | FRONTERA_OPERATIVA | V2 modela tipo de proceso y RN-9.1 en sombra. | Fechas, periodo extendido, registro manual y autorizacion pertenecen al proceso productivo. |
| RN-2.2 Evaluacion previa | CONFORME_CON_AJUSTE_DE_DATOS | Motor comun, trazas y consumidores ya generan oferta/explicacion sombra. | Agregar fecha de corte, caracter preliminar y fuente/version de parametro a traza. El bloqueo del simulador durante cursamiento es comportamiento de UI/calendario, no regla del motor. |
| RN-3.1 Nuevo ingreso | NUEVO_O_AMPLIADO | No existe regla V2 completa de asignacion fija de 4 obligatorias y 2 optativas. | Requiere estructura verificable del PE y pertenece a inscripcion inicial; V2 puede diagnosticar, no asignar productivamente. |
| RN-3.2-3.3 Optativas de nuevo ingreso | NUEVO_O_AMPLIADO | La validacion legacy de una optativa por bloque existe en consumidores, no como contrato normativo comun V2. | Incorporar como validacion de escenario V2 solo tras recibir catalogo/distribucion de optativas por bloque y aplicar unicamente a primer semestre. |
| RN-4.1 Carga minima | CONFORME | V2 calcula minimo 3 y excepciones tipificadas. | Confirmar por PE la carga final inferior al minimo mediante estructura curricular. |
| RN-4.2 Carga maxima | CONFORME | 8 regular, 6 irregular; Simulador ya consume el resultado. | Mantener el motivo RN-4.2 visible en todos los consumidores. |
| RN-4.3 Carga integrada | CONFORME_CON_AJUSTE_DE_DATOS | Orquesta carga, prioridad, seriacion, RN-6.1/RN-6.4. | Debe recibir seleccion hipotetica en Simulador para evaluar carga mixta real. |
| RN-5.1 Rezago leve | CONFORME | 1-3 reprobadas vigentes, prioridad y maximo 6. | Oferta real debe conservar la causa de indisponibilidad. |
| RN-5.2 Rezago severo | CONFORME | RN-6.4 integra solo regularizacion con cuatro o mas. | Mantener exclusion de NP por bajas y deduplicacion de adeudos sustituidos. |
| RN-6.1-6.1.1 | CAMBIO_NORMATIVO_OBLIGATORIO | Hoy depende de semestre objetivo=5. | Disparar por UD de S5 pretendida; evaluar obligatorias S1-S4 y la carga mixta. |
| RN-6.1.2 | CONFORME_CON_AJUSTE_DE_DATOS | Condiciones concurrentes, RN-6.4, seriacion, carga y prioridad estan modeladas. | Registrar evidencia de plan, medida administrativa, compatibilidad PE y seleccion pretendida. |
| RN-6.1.3 | CAMBIO_NORMATIVO_OBLIGATORIO | V2 aun trata ausencia de seriacion como supuesto tipificado. | El documento final la define como compatibilidad, no como excepcion; conservar concurrencia sin maximo artificial de pendientes. |
| RN-6.1.4 | CONFORME_CON_AJUSTE_DE_DATOS | Separa sin oferta, sin grupo y sin cupo. | Adaptador debe aportar bloque vigente y causa institucional de no apertura/cancelacion. |
| RN-6.1.5-6.1.6 | CONFORME_CON_AJUSTE_DE_DATOS | Impedimentos y excepcion extraordinaria estan representados en sombra. | Trazar ticket GLPi como unica referencia escolar; fundamentacion y resolucion permanecen en expediente externo. |
| RN-6.2 Incorporacion diferida | CONFORME_CON_AJUSTE_DE_DATOS | V2 contiene evaluacion en sombra. | Falta fuente canonica de convocatoria/resolucion, vigencia y periodo autorizado. |
| RN-6.3 Prioridad | CONFORME | Prioriza reprobadas vigentes ofertadas y explica indisponibilidad. | Consumidores deben conservar prioridad aunque no exista oferta. |
| RN-6.4 Restriccion severa | CONFORME | Cuatro o mas reprobadas unicas restringen a regularizacion. | Mantener sustituta de optativa adicional como regularizacion, no UD nueva. |
| RN-6.5-6.8 Reincorporacion y trayectoria | CONFORME_CON_AJUSTE_DE_DATOS | V2 posee evaluadores en sombra para no cursamiento, baja temporal/parcial y cierre non. | Validar datos reales de bajas, periodos y resoluciones; no se debe inferir baja formal. |
| RN-7.1 Optativas | CONFORME_CON_AJUSTE_DE_DATOS | Distingue requeridas/adicionales y usa ocho requeridas. | Falta catalogo verificable de trece optativas, distribucion vigente por bloque, cupo y cohortes obligadas a IA. |
| RN-7.1.1 Sustitucion adicional | CONFORME_CON_AJUSTE_DE_DATOS | Contrato contempla oportunidades y trazabilidad. | Falta relacion persistida/canonica entre adeudo original y sustitutas para no duplicar. |
| RN-7.2 Reprobadas | CONFORME | Mantiene irregularidad, RN-6.4 y diferencia requerida/adicional. | Confirmar la oferta de recursamiento/sustitucion por PE. |
| RN-8.1 Interbloque | CONFORME | Motor comun PE, B1/B2 y condicion de acreditacion definitiva. | Preseleccion y bloqueo de desmarcado son responsabilidades del flujo productivo, no de Malla/Simulador. |
| RN-8.2 Intersemestral | CONFORME | Requiere acreditacion previa. | Mantener nota explicativa en consumidores. |
| RN-8.3 Interanual | CONFORME | Usa relaciones formales PE, caminos directos/indirectos y ciclos. | Ninguna inferencia por semestre o ano. |
| RN-9.1 Extemporanea | CONFORME_CON_AJUSTE_DE_DATOS | Trata el momento administrativo sin flexibilizar reglas. | Las autorizaciones, fecha efectiva y cierre ordinario se obtienen del proceso productivo. |
| RN-9.2 Dispersion | FRONTERA_OPERATIVA | Clasificada fuera de Motor V2. | Requiere matriculacion, grupos activos, capacidad y GLPi; no integrar en consumidores curriculares. |
| RN-9.3 S7/S8 | CAMBIO_NORMATIVO_OBLIGATORIO | V2 exige 50% de creditos y aplica restricciones adicionales heredadas. | Retirar el umbral como condicion RN-9.3; evaluar plan/version, carga, oferta, prerrequisitos, prioridad y RN-6.4. |
| RN-9.4 Egreso/componentes | CAMBIO_NORMATIVO_OBLIGATORIO | V2 exige electiva y seriadas S1-S6 para componentes. | Sustituir por obligatorias + 8 optativas requeridas + creditos/requisitos del plan; modelar requisitos especificos por componente sin inferir nombres. |
| RN-9.5 Trazabilidad | NUEVO_O_AMPLIADO | Registra contexto, UD, motivos, reglas y resultado. | Extender con fuente/instrumento, version, poblacion, vigencia, criterio institucional por NP y ticket GLPi. |
| RN-10 Titulacion | FRONTERA_OPERATIVA | Auditoria previa determino responsabilidad compartida y no localizo modulo especializado. | No implementar en Motor V2; requiere catalogo por plan, seleccion, expediente y validacion institucional. |
| RN-10.1 Servicio social | FRONTERA_OPERATIVA | No existe fuente verificable de cumplimiento ni modulo responsable. | El documento fija 60 anos, trabajador de Federacion/CDMX y enfermedad grave como supuestos de solicitud, no liberacion automatica. Requiere dato, evidencia y resolucion institucional. |

## Impacto por Consumidor

| Consumidor | Ajustes obligatorios despues de actualizar el motor | Logica complementaria que debe conservarse |
| --- | --- | --- |
| Malla Curricular | Estados, motivos, RN-6.1 por UD S5 pretendida, RN-9.3 y RN-9.4 actualizados. | Bloques, creditos informativos, iconografia, tooltips y clasificacion visual de optativas. |
| Asistente de Inscripcion y Orientacion | Recomendaciones, prioridades y explicaciones deben consumir el nuevo resultado; no recomendar S7/S8 por un umbral inexistente. | Narrativa, agrupacion, informacion de oferta y orientacion no normativa. |
| Simulador | Validar seleccion contra carga, prioridad, seriacion y el nuevo disparador RN-6.1; no declarar invalida S7/S8 por 50%. | Una optativa por bloque, no duplicacion y otras validaciones de escenario no cubiertas por V2. |
| Asistente Virtual | Debe recibir solo hechos y motivos de la nueva version normativa; no reintroducir reglas historicas. | Presentacion, textos institucionales y acciones operativas no academicas. |

## Contratos que deben extenderse antes de cambios funcionales

1. `ContextoEvaluacionCurricularV2DTO`: UD pretendidas para la evaluacion; identificador de plan, cohorte y poblacion ya verificables; fuente/vigencia de parametros; referencias de ticket extraordinario cuando exista.
2. Estructura curricular: catalogo completo por plan/version, prerrequisitos, componentes finales y, cuando proceda, conjunto de UD previas de Proyecto Terminal.
3. Oferta: periodo, bloque, grupo activo, cupo, motivo institucional de no apertura y catalogo/distribucion de optativas.
4. Historial: causa normalizada de NP, baja formal, intentos, sustitucion de optativa adicional y rectificacion autorizada.
5. `ResultadoEvaluacionCurricularV2DTO` y trazas: fuente de regla/parametro, vigencia, poblacion, fecha de corte y referencia GLPi sin PII.

## Datos que deben confirmar las areas academicas

1. Identificador tecnico oficial y fecha de vigencia de la version final para sustituir `PE2027-V1` en configuracion y trazas.
2. Matriz de aplicabilidad por plan, version y cohorte para los PE2024 y PE2027, incluidos los catalogos de UD, credtos y relaciones de seriacion.
3. Catalogo vigente de las trece optativas, su distribucion por bloque y las cohortes/planes para los que alguna UD de IA es obligatoria.
4. Identificacion formal, por plan/version/cohorte, de Seminarios, Modulos Integradores, Proyecto Terminal I/II y sus requisitos; conjunto de UD previas que exige cada Proyecto Terminal.
5. Fuente institucional de convocatoria/resolucion para incorporacion diferida, medida administrativa, baja y reincorporacion.
6. Criterio institucional para fuentes de trazabilidad RN-9.5: instrumento, poblacion, vigencia, fecha de corte y ticket GLPi.
7. Ownership y fuente verificable de titulación/servicio social. La edad de 60, condicion laboral o solicitud no habilitan una liberacion sin resolucion institucional.

## Estrategia de Ajuste

1. Crear una version normativa configurable una vez que las areas academicas entreguen su identificador oficial; preservar `PE2027-V1` solo para reproducir evaluaciones historicas.
2. Extender adaptador/contexto con datos de seleccion pretendida y evidencia de plan/version/cohorte, sin tocar inscripcion productiva.
3. Corregir RN-6.1 y retirar la ausencia de seriacion como supuesto tipificado; agregar pruebas para carga mixta con UD de S5.
4. Sustituir RN-9.3: retirar el bloqueo por 50% y conservar creditos como informacion; probar S7/S8 con las restricciones que siguen vigentes.
5. Sustituir RN-9.4: retirar electiva minima y la regla generica de seriadas S1-S6; integrar requisitos explicitamente aportados por plan.
6. Desactivar la ruta de mejora de promedio RN-1.5 para la nueva version, sin borrar evidencia historica ni alterar calificaciones.
7. Extender RN-9.5 y los adaptadores de los cuatro consumidores; agregar pruebas de consistencia cruzada.
8. Mantener RN-3, RN-9.2, RN-10 y RN-10.1 en sus fronteras de ownership hasta contar con las fuentes/datos indicados.

## Resultado de Auditoria

El Motor V2 es una base reutilizable y ya concentra la mayoria de las reglas curriculares. No puede declararse alineado con la version oficial de octubre sin resolver los cambios obligatorios de RN-1.5, RN-6.1, RN-6.1.3, RN-9.3, RN-9.4 y RN-9.5.

La prioridad normativa es el documento de octubre. Las reglas anteriores solo se conservaran bajo una version historica explicita o como logica complementaria que no contradiga la nueva fuente. Ninguna brecha de proceso productivo se resolvera por inferencia en Malla, Asistente, Simulador, Asistente Virtual ni Motor V2.
