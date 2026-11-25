# Matriz regla–código SIGIE

Fecha de análisis: 2026-10-04. Fuente funcional primaria: `V2_REGLAS_VIGENTES.md`. Referencia histórica solamente: `V1_LINEA_BASE.md`. Alcance: **Asistente de inscripción y orientación educativa**, **Mapa curricular dinámico** y **motor V2**. No se modificó código.

## Leyenda de evidencia

- **Total**: se localiza código que cubre el núcleo de la regla; no equivale a prueba de aceptación ejecutada.
- **Parcial**: existe lógica relacionada, pero no todos los criterios V2, datos o trazabilidad exigidos.
- **Inexistente**: no se localizó implementación suficiente en los componentes analizados.
- **Pendiente de validación**: la propia V2 lo marca así; el estado técnico no supone una decisión académica.

## Componentes y datos transversales localizados

| Código | Evidencia técnica | Responsabilidad observada |
|---|---|---|
| C-01 | `InscripcionFacadeImpl` | Construye `InscripcionContextoDTO`, obtiene oferta, historial de cursadas/reprobadas/bajas, filtra disponibilidad, valida selección, carga, optativas, electivas y seriación. |
| C-02 | `AsistenteInscripcionServiceImpl` | Construye `AsistenteInscripcionContextoDTO`, diagnóstico, unidades de decisión, escenarios/simulación/proyección, conteos de pendientes, reprobadas, seriación, optativas y electivas. |
| C-03 | `TablaCurricularAsistidaBean` + `tablaCurricularAsistida.xhtml` | Vista y bean del Asistente; integra contexto, simulación y validación de selección. |
| C-04 | `MallaCurricularAlumnoBean`, `MallaDiagramaNodoDTO`, `mallaCurricularAlumno.xhtml` | Forma y representa nodos del mapa/trayectoria. |
| C-05 | `ClasificadorCasoServiceImpl`, `DiagnosticoAcademicoOperativoServiceImpl`, `RecomendadorResolucionOperativaServiceImpl`, `ValidadorExpedienteCasoServiceImpl`, `BaseConocimientoAcademicoServiceImpl` | Motor V2: clasificación, expediente, diagnóstico, patrón, recomendación y dictamen. |
| C-06 | `InscripcionRepository`, repositorios de inscripción/preinscripción y entidades JPA | Consultas de persona, oferta, historial, inscripciones, grupos y participaciones. |
| D-01 | `tbl_malla_curricular`, ficha/programa, `tbl_inscripciones`, `tbl_eventos`, `tbl_grupos`, `rel_grupo_participante` | Estructura curricular, oferta/cursamiento e historial. |
| D-02 | SQL `24_asistente_curricular_v2_sprint1_20260716.sql`; entidades `gestionescolar/v2` | `cat_tipo_caso_academico`, catálogos de motivo/criterio/acción/viabilidad/mensaje, patrones, caso, diagnóstico, dictamen, bitácora, evidencia, UD relacionada y caso similar. |

## Resultado ejecutivo

La base actual contiene reglas heredadas de avance anual que operan como filtros generales (`obtenerMateriasPorAvanceAnual…`) y una lógica V2 que ya calcula contexto, pendientes, reprobadas acumuladas, seriación, optativas/electivas y recomendaciones. Esto entra en tensión con CTR-01: desde 2027-1 el Control de Avance Anual debe limitarse a quinto semestre. La regla V2 aún no puede declararse total hasta parametrizar año/semestre objetivo y conservar trazabilidad de cada decisión.

## Criterios transversales

| Regla | Estado | Código/datos | Conservar / modificar / eliminar | Dependencias y ambigüedades | Pruebas necesarias |
|---|---|---|---|---|---|
| CTR-01 Control sólo acceso a quinto desde 2027-1 | **Parcial** | C-01: `obtenerMateriasPorAvanceAnualEstudianteRegular`, `…Irregulares`, `…SinTomarEnCuentaObligatorias`; C-02 proyecta semestre. | **Modificar** filtros generales para que 6.1 aplique sólo si semestre objetivo=5 y periodo>=2027-1. **Conservar** seriación y regularización. | Falta fuente de configuración/fecha de vigencia por PE/proceso; la lógica actual parece filtrar más de un cambio anual. | Objetivo 3,5,7 con/ sin pendientes; 2026-2 vs 2027-1; verificar que 2→3 y 6→7 no usen control anual. |
| CTR-02 Excepciones automatizadas | **Parcial** | C-02 escenarios, C-05 patrón/dictamen, D-02 catálogos. | **Conservar** clasificación/dictamen; **modificar** para que excepción tipificada se aplique determinísticamente y la extraordinaria escale. | No se localizó catálogo con bandera explícita `tipificada/extraordinaria` ni actor de aprobación. | Cada excepción 4.1, 6.1, 6.7; no tipificada → expediente/escalamiento sin habilitación automática. |
| CTR-03 Condición vs avance condicionado | **Parcial** | C-02 calcula situación y mensajes; C-01 usa regularidad para límites. | **Modificar** para que habilitar compatibles no cambie `Regular/Irregular`. | V2 exige separar causa de pendiente; DTO actual puede derivar situación de reprobadas pero debe auditarse. | Irregular con avance condicionado sigue máximo 6 y se muestra irregular. |
| CTR-04 Evaluación concurrente | **Parcial** | C-01 y C-02 combinan oferta, historial, seriación, carga y reprobadas; C-05 clasifica. | **Conservar** composición de contexto; **modificar** para emitir razones acumuladas, no una categoría excluyente. | Orden de prioridad y resolución de conflictos no centralizados como motor de reglas único. | Caso con 4 reprobadas + seriación + baja parcial + oferta limitada; verificar todas las razones. |

## Matriz de reglas 1 a 3

| Regla V2 | Estado | Implementación localizada | Comportamiento | Dependencias / brechas | Casos de prueba |
|---|---|---|---|---|---|
| 1.1 Inactividad | Parcial | C-01 consulta bajas; C-02 `existeBajaTemporal`, `esBajaTemporal`; D-01 bajas/inscripción. | Conservar detección de baja temporal. Modificar para revocación automática, consecutividad máxima y baja definitiva si no hay evidencia. | No se localizó job/proceso que revoque automáticamente ni modelo completo de inactividad definitiva. | Baja temporal 1, 2 y 3 periodos; vencida; omisión posterior; baja definitiva. |
| 1.2 Regular/irregular **pendiente** | Parcial | C-01 `esRegular`, `obtenerMateriasReprobadas`; C-02 `calcularTotalReprobadasAcumuladas`, diagnóstico. | Conservar cómputo de reprobadas; modificar para distinguir reprobada vs baja parcial/no cursada. | V2 pendiente define que sólo reprobadas determinan irregularidad; datos históricos pueden no guardar causa normalizada. | 0 reprobadas+no cursada; 1 optativa reprobada; baja parcial; reprobada de periodo previo. |
| 1.3 Recuperar regularidad **pendiente** | Parcial | C-01 `esRegular`; C-02 contexto/proyección. | Modificar recuperación para eliminar sólo reprobadas, no pendientes de otra causa; no cambiar por avance condicionado. | No se localizó regla explícita de transición/auditoría de estado. | Acredita última reprobada; conserva baja parcial; seriación pendiente; 4+ histórico. |
| 1.4 Tres intentos | Parcial | C-01 `alcanzoLimiteReprobaciones`, `obtenerLimiteReprobacionesPorMateria`, exclusión de reprobadas. | Conservar bloqueo por límite; modificar/validar exactamente “tres intentos” y escalamiento. | Fuente de contador y causal de baja definitiva debe verificarse en repositorio/BD. | Intentos 1,2,3,4; reprobada ofertada/no ofertada; mensaje y no selección. |
| 1.5 Recursamiento para promedio | Parcial | C-01 consulta cursadas y validación de selección; no se localizó claramente comparación “conservar máxima” ni exclusión de egreso. | No eliminar historial. Modificar para permitir acreditada, cupo/prioridad, máximo de intentos y máximo de calificación. | Falta campo/consulta confirmada de dictaminación de egreso y cálculo certificado. | Recursa acreditada 80→70/90; tercer intento; sin cupo; egreso iniciado. |
| 2 Procesos / quinto | Parcial | C-01 estado de proceso/fecha/oferta; C-02 `periodoActivo`, simulación. | Modificar alcance de avance anual; conservar validación de oferta/proceso. | Identificador de semestre objetivo y vigencia 2027-1 deben ser explícitos. | Ordinaria/extraordinaria; objetivo 5 y no 5; sin oferta activa. |
| 2.1 Ventanas | Parcial | C-01 `obtenerIdProcesoInscripcion`, estado de inscripción; entidades/proceso. | Conservar ventanas. | No se verificó configuración completa de ordinario/extraordinario ni permisos de registro directo. | Antes/durante/después ventana; proceso extraordinario; estudiante nuevo. |
| 2.2 Evaluación previa/oferta trazable | Parcial | C-02 diagnósticos/escenarios; C-05 caso/dictamen/bitácora; D-02. | Conservar V2; modificar para persistir oferta individual, restricciones y excepciones por evaluación. | Requiere confirmar si evaluaciones se guardan en todas las rutas de UI. | Evaluación previa repetida; cambio de oferta; gestor vs estudiante; recuperación de bitácora. |
| 3 Nuevo ingreso | Total parcial | C-01 identifica `esNuevoIngreso`; C-02 mensajes y selección. | Conservar identificación. | Matrícula/cuenta institucional no se revisó en estos módulos. | Admitido, primer ingreso, inscripción diferida. |
| 3.1 Carga nuevo ingreso 4+2 | Total | C-02 valida cuatro obligatorias/dos optativas; C-01 reglas de selección. | Conservar. | Validar catálogo PE y bajas posteriores. | 4+2 exacta; 3+2, 4+1, 4+3; intento cambiar carga. |
| 3.2–3.3 Dos optativas, una por bloque/no repetida | Total | C-02 `validarSeleccionOptativasSimulacion`; C-01 `validarSeleccionOptativas`. | Conservar. | Semestre/bloque debe venir consistentemente de oferta. | Dos mismo bloque; misma clave en ambos bloques; una por bloque válida. |

## Matriz carga, irregularidad y avance

| Regla V2 | Estado | Implementación localizada | Acción requerida | Dependencias / ambigüedad | Pruebas |
|---|---|---|---|---|---|
| 4, 4.1–4.3 Carga 3/8/6 y excepciones | Parcial | C-01 límites carga, validación final; C-02 simulación, selección/meta; DTO configuración. | Conservar mínimos/máximos y mensajes. Modificar prioridad V2: regularización→seriación→6.4→quinto. | La excepción <3 existe conceptualmente pero requiere causa estructurada/auditoría; carga es semestral aunque la oferta sea por bloque. | Regular 2/3/8/9; irregular 2/3/6/7; sólo una UD elegible; optativas para mantener activo; conflicto 6.1/6.4. |
| 5, 5.1 1–3 reprobadas | Parcial | C-01 irregular y avance; C-02 total reprobadas y “solo optativas por reprobadas no ofertadas”. | Conservar prioridad reprobadas; modificar criterio a total V2 y alcance de quinto. | Texto 5.1 menciona obligatorias del año inmediato, V2 transversal total para condición; no inferir cuál prevalece fuera de 6.4. | 1–3 obligatorias/optativas; ofertadas/no ofertadas; máxima 6; compatibles. |
| 5.2 / 6.4 cuatro o más | Parcial | C-02 `TOTAL_REPROBADAS_RESTRICCION_SEVERA`, `calcularTotalReprobadas…`; C-01 límite reprobadas. | Conservar restricción severa; modificar para contar cualquier tipo/año y bloquear obligatorias nuevas incluso mismo año. | Verificar que contador no excluya optativas/electivas ni duplica por bloque. | 3 vs 4 reprobadas; cuatro optativas; reprobada repetida en B1/B2; oferta sólo optativas. |
| 6.1 Quinto/avance condicionado **pendiente** | Parcial | C-01 métodos “AvanceAnual”; C-02 proyección/seriación. | **Modificar** de cierre anual general a control exclusivo de obligatorias de quinto desde 2027-1; implementar condiciones compatibles y motivos. | Definición exacta de “compatibles”, fecha y excepciones tipificadas sigue pendiente. | S4 con pendiente: quinto bloqueado/compatibles; objetivos 3/7 no bloqueados; 4+ reprobadas prevalece. |
| 6.2 Inscripción inicial diferida | Parcial | C-01 estados inscripción ordinaria/extraordinaria inicial. | Modificar para trayecto diferido y decisión S1/S2 por seriación. | Falta fuente de admisión/inactividad y persistencia de “diferida”. | Admitido omitió inicial; S2 con/sin seriación S1; ciclos posteriores. |
| 6.3 Prioridad automática reprobadas | Parcial | C-01 marca/filtra reprobadas; C-02 selección/proyección. | Conservar prioridad; modificar para diferenciar no acreditada, reprobada y no cursada como pide V2. | Debe coexistir con 6.1 y 6.4 sin ocultar explicación. | Reprobada ofertada preseleccionada; no ofertada; reprobada+UD nueva; selección manual intenta retirarla. |
| 6.5 No reinscripción inmediata | Parcial | C-01 semestres/materias cursadas, C-02 pendientes/diagnóstico. | Modificar para identificar semestre pendiente y causal de inactividad. | Falta marca inequívoca “no se reinscribió” frente a oferta no disponible/baja. | Omite un periodo; vuelve dentro/fuera límite; regular/irregular; oferta par/impar. |
| 6.6 Baja temporal **pendiente** | Parcial | C-02 baja temporal; C-01 bajas. | Conservar detección; no automatizar criterios pendientes sin validación. | Duración, última situación, inactividad y Art.37 requieren datos/autoridad. | Baja vencida; baja vigente; reincorporación inmediata; omisión; 6.1/6.4 concurrentes. |
| 6.7 Baja parcial **pendiente** | Parcial | C-01 `obtenerBajas…`; C-02 distingue parcial/temporal. | Modificar con matriz obligatoria/optativa × ofertada/no ofertada y máximo 6 cuando corresponda. | Causa de baja y tipo de UD deben ser históricos confiables; regla pendiente. | Cuatro combinaciones; bloque N+1; sólo optativas; reingreso. |
| 6.8 Regularización semestre non **pendiente** | Inexistente/Parcial | C-02 proyecciones permiten escenarios, pero no se localizó recalculo normativo explícito post-regularización non→par. | No eliminar proyección; implementar sólo tras validación académica. | Qué acredita/fecha de corte/oferta par y definición “continuidad compatible” pendientes. | Acredita pendiente en S3 y oferta S4; no acredita; cadena seriada; 6.4. |

## Tipos de UD y seriación

| Regla V2 | Estado | Implementación localizada | Acción requerida | Ambigüedad / pruebas |
|---|---|---|---|---|
| 7.1 Optativas | Parcial | C-01 `modificaMateriasOptativasDeAcuerdoAProbacion`, validadores; C-02 cobertura/rezago. | Conservar oferta/rezago; modificar regla de irregularidad y 6.4 de acuerdo con V2. | IA/grupos combinados/responsabilidad CAI no localizados. Probar 1–3 y 4 optativas reprobadas. |
| 7.1.1 Ocho optativas | Total parcial | C-02 `MINIMO_OPTATIVAS_REQUERIDAS`, `calcularCobertura…`, `aplicarReglasOptativasElectivas`; distinct por programa. | Conservar clasificación opcional tras mínimo; validar que mínimo configurado sea 8 y no constante divergente. | Probar 7/8/9 acreditadas, repetición B1/B2 y oferta adicional. |
| 7.2 Electivas | Parcial | C-01 validación electivas; C-02 habilitación por 50%, pendientes y mensajes. | Conservar 50%/prioridades; modificar/verificar PE ajeno, S7/S8, más de una y no obligación de recursar misma electiva. | Consulta de PE propio/ajeno y máximo deben revisarse. Probar créditos 49/50, pendiente obligatoria, electiva reprobada. |
| 7.3 Optativa/electiva reprobada | Parcial | C-02 mensajes/reprobadas; C-01 límites y filtros. | Modificar para que <4 no bloquee anual por sí sola y 4+ active 6.4. | Depende de 1.2 y 6.4 pendientes. |
| 8, 8.2 Seriación general/intersemestral | Parcial | C-01 `aplicarValidacionDeSeriacion`, antecedente, `esMateriaSeriada…`; C-02 relaciones `requiere/desbloquea`. | Conservar. Modificar UI para ocultar/restringir y explicar razón conforme V2. | El mapa puede mostrar cadena; validar antecedentes indirectos. |
| 8.1 Interbloque | Parcial | C-01 marca antecedente/seriadas; C-02 relación. | Modificar preselección B2 y confirmación diferida tras resultado B1. | La inscripción definitiva B2 depende de calificación aún no disponible; requiere evento de cierre. |
| 8.3 Interanual | Parcial | C-01 antecedente directo; C-02 relaciones. | Modificar: independiente de CTR-01, validar cadenas directas/indirectas y no autorizar por seriación. | Actualmente antecedente directo puede no recorrer cadena completa. Probar A→B→C. |

## Reglas finales, operación y trazabilidad

| Regla V2 | Estado | Implementación | Acción / pruebas |
|---|---|---|---|
| 9.1 Extemporánea | Parcial | C-01 proceso extraordinario; C-02 diagnósticos. | Conservar validaciones normales; probar extra fuera ordinario con seriación/carga/6.4. |
| 9.2 Dispersión | Parcial | Módulos/repositorios de inscripción/grupo fuera del núcleo analizado. | Verificar grupos activos/cupo/ticket GLPi; no afirmar creación prohibida sin consulta específica. |
| 9.3 S7/S8 **pendiente** | Parcial | C-01 porcentaje créditos/electivas; C-02 mensajes electivas. | No decidir reglas pendientes; probar 49/50%, pendientes S3–S6, seminario/electiva. |
| 9.4 Egreso | Parcial | C-02 pendientes obligatorias/optativas/electivas; no se localizó flujo completo de egreso/proyecto. | Verificar 8 optativas+1 electiva+obligatorias/seriadas; proyecto terminal. |
| 9.5 Trazabilidad | Parcial | D-02 caso, diagnóstico, dictamen, bitácora, evidencia; C-05. | Conservar tablas/servicios; modificar para registrar toda evaluación/oferta/regla/excepción de UI e inscripción. Probar auditoría reproducible. |
| 10 y 10.1 Titulación/servicio social | Inexistente en alcance | No se localizó en Asistente/Mapa/Motor V2 analizados. | No modificar estos módulos sin analizar titulación. Probar/implementar en módulo responsable. |

## Repositorios, consultas y tablas a revisar antes de cambio

1. `InscripcionRepository` y repositorios de preinscripción: trazan oferta, estados de inscripción, historial y límites; revisar SQL para que no identifique “no inscrita” como “reprobada”.
2. `rel_grupo_participante`, eventos/grupos e inscripciones: son fuente para calificación, cursamiento y causas históricas; la baja parcial requiere causa explícita.
3. `tbl_malla_curricular` y ficha descriptiva: determinan semestre/bloque/tipo de UD y relaciones necesarias para seriación/avance.
4. Tablas V2 D-02: deben añadir o utilizar atributos de resultado por regla, versión normativa, semestre objetivo, oferta evaluada y excepción tipificada; no se confirma que el esquema actual los guarde todos.

## Plan mínimo de pruebas de regresión

1. Misma persona/plan consultada desde mapa, asistente, simulador, validador e inscripción debe obtener el mismo estado de UD y oferta deduplicada.
2. Matriz de 0, 1–3 y 4+ reprobadas, separando obligatoria/optativa/electiva y causa baja/no cursada.
3. Todos los límites de carga: 1,2,3,6,7,8,9 para condición regular/irregular y excepción menor a 3.
4. Seriación B1→B2, S(n)→S(n+1), A→B→C interanual; antecedente acreditado/en curso/reprobado/no cursado.
5. Control V2: objetivos 3,5,7 y vigencia antes/después 2027-1.
6. Baja temporal/parcial, reincorporación y no reinscripción; oferta disponible/no disponible.
7. Cobertura optativas 7/8/9 y electivas con 49/50% créditos.
8. Auditoría: cada decisión debe devolver regla, datos utilizados, restricción/habilitación, mensaje y, si aplica, caso/dictamen/bitácora.

## Conclusión

La base actual es aprovechable, pero **no debe modificarse regla por regla de forma aislada**. Primero debe centralizarse la evaluación concurrente y normalizarse la causa de cada pendiente. Las reglas 1.2, 1.3, 6.1, 6.6, 6.7, 6.8 y 9.3 siguen marcadas por la fuente como pendientes; esta matriz las reporta como tales y no propone decisión académica sustitutiva.
