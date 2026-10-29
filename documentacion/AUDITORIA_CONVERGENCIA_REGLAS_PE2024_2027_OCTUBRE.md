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
restringe Proyecto Terminal/Modulo Integrador por todas las seriadas S1-S6. La precision
academica recibida confirma que las UD electivas dejan de existir. El documento final requiere
todas las obligatorias y ocho optativas requeridas, mas creditos y requisitos del plan aplicable.
No existe requisito de una electiva minima ni debe mantenerse ELECTIVA como tipo curricular
vigente para PE2024-PE2027.

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
| RN-9.4 Egreso/componentes | CAMBIO_NORMATIVO_OBLIGATORIO | V2 exige una electiva y seriadas S1-S6 para componentes. | Retirar requisito/tipo vigente ELECTIVA; sustituir por obligatorias + 8 optativas requeridas + creditos/requisitos del plan; modelar requisitos especificos por componente sin inferir nombres. |
| RN-9.5 Trazabilidad | NUEVO_O_AMPLIADO | Registra contexto, UD, motivos, reglas y resultado. | Extender con fuente/instrumento, version, poblacion, vigencia, criterio institucional por NP y ticket GLPi. |
| RN-10 Titulacion | FRONTERA_OPERATIVA | Auditoria previa determino responsabilidad compartida y no localizo modulo especializado. | No implementar en Motor V2; requiere catalogo por plan, seleccion, expediente y validacion institucional. |
| RN-10.1 Servicio social | FRONTERA_OPERATIVA | No existe fuente verificable de cumplimiento ni modulo responsable. | El documento fija 60 anos, trabajador de Federacion/CDMX y enfermedad grave como supuestos de solicitud, no liberacion automatica. Requiere dato, evidencia y resolucion institucional. |

## Impacto por Consumidor

| Consumidor | Ajustes obligatorios despues de actualizar el motor | Logica complementaria que debe conservarse |
| --- | --- | --- |
| Malla Curricular | Estados, motivos, RN-6.1 por UD S5 pretendida, RN-9.3 y RN-9.4 actualizados; retirar nomenclatura/elegibilidad ELECTIVA vigente. | Bloques, creditos informativos, iconografia, tooltips y clasificacion visual de optativas. |
| Asistente de Inscripcion y Orientacion | Recomendaciones, prioridades y explicaciones deben consumir el nuevo resultado; no recomendar S7/S8 por un umbral inexistente ni usar ELECTIVA como requisito. | Narrativa, agrupacion, informacion de oferta y orientacion no normativa. |
| Simulador | Validar seleccion contra carga, prioridad, seriacion y el nuevo disparador RN-6.1; no declarar invalida S7/S8 por 50% ni modelar seleccion ELECTIVA vigente. | Una optativa por bloque, no duplicacion y otras validaciones de escenario no cubiertas por V2. |
| Asistente Virtual | Debe recibir solo hechos y motivos de la nueva version normativa; no reintroducir reglas historicas. | Presentacion, textos institucionales y acciones operativas no academicas. |

## Contratos que deben extenderse antes de cambios funcionales

1. `ContextoEvaluacionCurricularV2DTO`: UD pretendidas para la evaluacion; identificador de plan, cohorte y poblacion ya verificables; fuente/vigencia de parametros; referencias de ticket extraordinario cuando exista.
2. Estructura curricular: catalogo completo por plan/version, prerrequisitos, componentes finales y, cuando proceda, conjunto de UD previas de Proyecto Terminal.
3. Oferta: periodo, bloque, grupo activo, cupo, motivo institucional de no apertura y catalogo/distribucion de optativas.
4. Historial: causa normalizada de NP, baja formal, intentos, sustitucion de optativa adicional y rectificacion autorizada.
5. `ResultadoEvaluacionCurricularV2DTO` y trazas: fuente de regla/parametro, vigencia, poblacion, fecha de corte y referencia GLPi sin PII.

## Datos que deben confirmar las areas academicas

La siguiente solicitud separa lo que debe definir la autoridad academica de lo que debe localizar
Tecnologia en SIGIE. La respuesta debe identificar el documento o catalogo que sustenta cada dato,
la persona/area responsable y su fecha de vigencia; una respuesta narrativa sin esa evidencia no
permite automatizar una regla de forma segura.

| # | Pregunta concreta para CAI/DAEAE/RPE | Evidencia minima que debe entregarse | Uso tecnico esperado |
| --- | --- | --- | --- |
| 1 | Cual es el identificador oficial de la version final, su fecha de publicacion y la fecha desde la que sustituye a `PE2027-V1`? Aplica retroactivamente a evaluaciones ya emitidas o solo a periodos/procesos posteriores? | Oficio, acuerdo o portada versionada que indique nombre/clave de version, fecha de aprobacion, vigencia inicial y regla de transicion. | Configuracion `REGLAS_V2_VERSION`, trazas RN-9.5 y reproduccion determinista de evaluaciones historicas. |
| 2 | Que planes, versiones y cohortes estan comprendidos? Para cada PE2024 y PE2027, cual es la clave SIGIE de plan, periodo de ingreso inicial/final de cohorte y vigencia curricular? | Matriz `PE - idPlan SIGIE - version plan - cohorte - inicio - fin - vigencia`. | Evitar aplicar reglas de un plan o cohorte a otro. |
| 3 | Cual es el catalogo curricular completo de cada fila de la matriz anterior? Que UD son obligatorias u optativas, en que semestre/bloque estan, cuantos creditos tienen y que relaciones de seriacion formal poseen? | Extracto aprobado de malla por plan/version/cohorte con clave, nombre, id SIGIE, tipo, semestre, bloque, creditos y prerrequisitos. | Contexto V2, RN-6.1, RN-8, RN-9.3, RN-9.4 y conteos/indicadores de Malla. |
| 4 | La eliminacion de UD electivas aplica a todos los PE2024/PE2027 y todas las cohortes? Desde que periodo? Como deben tratarse los registros historicos ya clasificados como ELECTIVA: conservar solo historial, reclasificar a optativa o excluirlos de requisitos? | Acuerdo de eliminacion y tabla de equivalencias para registros historicos, si existe. | Retirar `ELECTIVA` de oferta, UI, recomendaciones y RN-9.4 sin corromper el historial academico. |
| 5 | Cuales son exactamente las trece optativas vigentes por plan/cohorte? Cual puede seleccionarse en B1 y cual en B2? Una misma optativa puede aparecer en ambos bloques? Que regla opera cuando no existe cupo u oferta? | Catalogo de 13 optativas con id SIGIE, clave, creditos, bloque permitido, PE/cohorte y vigencia. | RN-3.2/3.3, RN-7.1, validaciones complementarias de Simulador y explicaciones de Asistente. |
| 6 | Que poblacion debe acreditar alguna UD vinculada a Inteligencia Artificial y cual UD concreta satisface el requisito? Es obligatoria por PE, plan, cohorte o acuerdo especifico? | Acuerdo/matriz de aplicabilidad con PE, version, cohorte, UD, creditos y fecha de vigencia. | No convertir la mera presencia de una UD de IA en requisito universal. |
| 7 | Que componentes finales existen formalmente en cada plan: Seminario(s), Modulo Integrador y Proyecto Terminal I/II? Son UD curriculares, modalidad de titulacion o ambas cosas? | Matriz por plan/version/cohorte con id SIGIE, tipo de componente, prerrequisitos, creditos y fundamento. | Sustituir la inferencia por nombre y aplicar RN-9.4 solo al componente correcto. |
| 8 | Para cada Proyecto Terminal identificado, cuales son literalmente las UD previas del mapa cuya acreditacion exige? Los Seminarios o Modulos comparten algun requisito o tienen una lista distinta? | Lista explicitamente aprobada de ids de UD requeridas por componente, con fundamento y vigencia. | Evaluar RN-9.4 por componente sin bloquear genericamente por todas las seriadas S1-S6. |
| 9 | Cual es la fuente institucional que acredita incorporacion diferida, baja temporal, baja parcial, baja definitiva, falta de reinscripcion y reincorporacion? Que campos expresan causa, periodo, fecha, vigencia y resolucion? | Diccionario de datos SIGIE o especificacion de servicio: tabla/campo, significado, valores validos, responsable de actualizacion y evidencia asociada. | RN-1.1, RN-1.3, RN-6.2 y RN-6.5 a RN-6.8; nunca inferir una baja formal. |
| 10 | Cual es el criterio institucional de fecha de corte de la evaluacion previa y como se identifica el caracter preliminar de calificaciones, actas y oferta? | Calendario/procedimiento que especifique fecha de corte por periodo y evento que la actualiza. | RN-2.2 y mensajes de Malla/Asistente sin presentar datos preliminares como definitivos. |
| 11 | Que fuente regula y evidencia cada parametro de RN-9.5: instrumento, version, poblacion, vigencia, causa NP y criterio que define sus efectos? Que formato de ticket GLPi se conserva para un caso extraordinario? | Plantilla de trazabilidad con campos obligatorios y ejemplo anonimizado de ticket/resolucion. | Extender traza sin PII y enlazar la evidencia externa de RN-6.1.6. |
| 12 | Que modulo y fuente verificable son responsables de titulacion y servicio social? Que datos prueban cumplimiento, solicitud y resolucion para trabajador de Federacion/CDMX, persona de 60 anos o mas y enfermedad grave? | Documento de ownership, catalogo/tabla/API responsable y campos de evidencia/resolucion. | Mantener RN-10/RN-10.1 fuera del Motor V2 hasta que exista fuente y procedimiento; no liberar automaticamente por edad, empleo o solicitud. |

### Informacion que debe localizar Tecnologia en SIGIE

1. La relacion fisica entre los identificadores de plan, programa, unidad didactica, semestre,
   bloque, creditos y relaciones de seriacion indicados por las areas academicas.
2. La fuente real de oferta: periodo, bloque, grupo activo, cupo, cancelacion/no apertura y su
   diferencia frente a `SIN_OFERTA`.
3. La fuente real de historial: resultado, NP, causa de NP, intentos, rectificacion y relacion
   entre una optativa adicional y sus sustitutas.
4. La fuente de seleccion hipotetica del Simulador para remitir las UD pretendidas a V2 sin
   persistirlas.
5. La fuente de trayectoria efectiva que determine el semestre objetivo; no se aceptara el
   semestre maximo ofertado como reemplazo.

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
