# Matriz Final Seccion 1 - Motor Curricular V2

## Identificacion

- Rama: `feature/desacople_motor_historico`.
- Base revisada: `3eed6ca0` mas los cambios de cierre antes de commit.
- Fecha: 2026-11-07.
- Alcance: evaluacion en sombra PE2024/2027. No escribe inscripciones, bajas, calificaciones, grupos ni resoluciones.

## Auditoria C-16 y C-17

| Regla | Comportamiento anterior | Exigencia | Resultado aplicado / evidencia |
|---|---|---|---|
| C-16 / RN-9.3 y RN-9.4, seminarios | El motor ya distinguia `SEMINARIO_TITULACION` de `PROYECTO_TERMINAL` y `MODULO_INTEGRADOR`; los requisitos de PE se revisaban solo cuando estaban expresamente cargados. | Un seminario solo atiende seriacion I->II->III y requisitos expresos; no se bloquea por adeudos ajenos, nombre, ubicacion ni reglas historicas de Proyecto Terminal I o Practica Profesional I. | Sin bloqueo heredado localizado. Se conserva la clasificacion explicita y se agrega `gc14SeminarioNoSeBloqueaPorAdeudoAjenoNiPorSuUbicacion`: adeudo OB4-2 `PRIORITARIA`; STIT01 S7 `HABILITADA`, sin `REPROBACION_ACUMULADA`. Archivo: `EvaluacionCurricularV2ServiceImpl.evaluarAvanceUltimosSemestresRn93`; prueba: `EvaluacionCurricularV2Fase9BTest`. |
| C-17 / RN-9.5, trazabilidad | La traza tenia situacion, semestre simple, RN-6.1, RN-6.4 y decisiones por UD. | Ambito; plan/cohorte; semestre de referencia con periodos contados/excluidos; valor, documento, ambito y vigencia de cada parametro; criterio por UD. | Se amplio `auditarCoberturaTrazabilidadRn95` con `detalleSemestreReferenciaRn95` y `detalleParametrosRn95`. La traza `RN95_COBERTURA_UD` incluye reprobacion vigente, intentos y oportunidades. Prueba `f9BU15TrazaRn95IncluyeAmbitoSemestreYParametros`. |

La clasificacion de seminario no se deriva del nombre ni del semestre: usa `TipoComponenteCurricularV2.SEMINARIO_TITULACION` y/o la bandera curricular entregada por el plan. Si el plan no aporta sus relaciones I->II->III o requisitos expresos, el motor conserva la incertidumbre como dato curricular, sin inventar un requisito por nombre.

## GC-14 ejecutado

| UD | Estado de entrada | Decision V2 | Motivos | Resultado observable |
|---|---|---|---|---|
| OB4-2 | REPROBADA, S4, ofertada | PRIORITARIA | `REPROBACION_ACUMULADA` | Permanece visible como pendiente de regularizacion. |
| STIT01 | NO_INSCRITA, S7, ofertada, tipo `SEMINARIO_TITULACION` | HABILITADA | Ningun motivo derivado del adeudo OB4-2 | Puede evaluarse por su seriacion y requisitos expresos; el adeudo ajeno no la bloquea. |

Caso ejecutado en `EvaluacionCurricularV2Fase9BTest.gc14SeminarioNoSeBloqueaPorAdeudoAjenoNiPorSuUbicacion`.

## Matriz final de reglas

`Conforme` significa implementada y con evidencia automatizada en el motor. `Pendiente de dato` significa que el contrato esta listo, pero requiere una fuente institucional verificable para resolver casos productivos. `Fuera de alcance` significa que corresponde a un acto productivo, resolucion o tramite que el motor en sombra no puede ejecutar.

| RN | Estado | Alcance actual / evidencia |
|---|---|---|
| RN-1.1 | Conforme | Identifica baja definitiva, inactividad y plazo maximo desde evidencia canonica; evita proponer carga ante baja definitiva y conserva el resultado real del intento. La identificacion de inactividad/plazo no sustituye una resolucion institucional. |
| RN-1.2 | Conforme | Determina regularidad e irregularidad por reprobadas vigentes unicas. |
| RN-1.3 | Conforme | Conserva pendientes de no cursamiento y baja parcial sin convertirlos en reprobacion. |
| RN-1.4 | Conforme | Cuenta oportunidades por UD con historial canonico; NP autorizado y extraordinario historico no se infieren como oportunidad. |
| RN-1.5 | Conforme | Prohibe reinscribir UD acreditada solo para mejorar promedio. |
| RN-2.1 | Fuera de alcance | La administracion de la oferta y sus actos institucionales no se ejecutan desde la evaluacion en sombra. |
| RN-2.2 | Conforme | Evalua elegibilidad curricular previa a reinscripcion y carga, incluyendo seriacion mediante relaciones verificables. La traza usa `RN_2_2`; evidencia: `EvaluacionCurricularV2Fase1BTest`. |
| RN-3.1 | Fuera de alcance | La programacion/operacion institucional correspondiente no se infiere ni se ejecuta desde el motor. |
| RN-3.2 | Fuera de alcance | La asignacion operativa de oferta, grupo o cupo corresponde al proceso institucional; V2 solo consume su disponibilidad. |
| RN-3.3 | Fuera de alcance | La formalizacion operativa posterior a la evaluacion curricular no es una decision del motor en sombra. |
| RN-4 | Conforme | Integra carga academica en sombra. |
| RN-4.1 | Conforme | Evalua minimo y excepciones tipificadas, sin inventar una carga. |
| RN-4.2 | Conforme | Aplica maximo individual desde parametros y situacion V2. |
| RN-4.3 | Conforme | Integra minimo, maximo, seriacion y disponibilidad ya evaluados; cuando falta oferta/calendario devuelve revision o disponibilidad incompleta, sin inferir simultaneidad productiva. Traza `RN_4_3`; prueba `EvaluacionCurricularV2Fase3DTest`. |
| RN-5 | Conforme | Clasifica rezago y prioridad sin modificar la inscripcion. |
| RN-5.1 | Conforme | Identifica rezago leve a partir del expediente normalizado. |
| RN-5.2 | Conforme | Identifica rezago severo y restringe la propuesta a regularizacion conforme al umbral parametrico. |
| RN-6.1 | Conforme | Evalua avance por UD candidata de quinto, no por semestre maximo de oferta. |
| RN-6.1.1 | Conforme | Evalua las condiciones comunes de avance para cada candidata de quinto, incluidos pendientes, seriacion y evidencia del PE. Emite trazas `RN_6_1_1`; prueba `EvaluacionCurricularV2Fase5ATest`. |
| RN-6.1.2 | Conforme | Determina los supuestos tipificados de avance condicionado, sin requerir autorizacion individual cuando la evidencia esta completa. Emite trazas `RN_6_1_2`; prueba `EvaluacionCurricularV2Fase5BTest`. |
| RN-6.1.3 | Conforme | Consolida causas concurrentes y falta de oferta de los pendientes; no incorpora un maximo de pendientes no previsto. Emite trazas `RN_6_1_3`; prueba `EvaluacionCurricularV2Fase5CTest`. |
| RN-6.1.4 | Conforme | Distingue falta de oferta de restriccion academica. |
| RN-6.1.5 | Conforme | Medida sin alcance/vigencia queda `REQUIERE_REVISION`; una medida vigente con alcance de inscripcion/reinscripcion impide el avance condicionado y una vencida no. Trazas `RN_6_1_5`; prueba `Bloque4TrayectoriaMedidaRn20261106Test`. |
| RN-6.1.6 | Pendiente de dato | Requiere fuente institucional de resolucion extraordinaria para resolver casos reales. |
| RN-6.2 | Pendiente de dato | El contrato exige referencia de convocatoria/resolucion; sin ella devuelve `REQUIERE_REVISION`. |
| RN-6.3 | Conforme | Prioriza regularizacion sin transformar otros estados en reprobacion. |
| RN-6.4 | Conforme | Cuatro o mas reprobadas vigentes activa regularizacion exclusiva. |
| RN-6.5 | Conforme | Identifica periodos sin reinscripcion; no los convierte en baja ni reinscripcion automatica. |
| RN-6.6 | Conforme | Cuenta bajas temporales acumuladas tipos 2, 7 y 8; una cuarta se identifica con `MAXIMO_BAJAS_TEMPORALES_EXCEDIDO`, sin negar reincorporacion ni carga. |
| RN-6.7 | Conforme | Conserva baja parcial y su causa separada de reprobacion. |
| RN-6.8 | Conforme | Actualiza trayectoria en sombra sin escritura productiva. |
| RN-6.9 | Conforme | `SemestreReferenciaV2` cuenta B1/B2 una vez y expone periodos contados y excluidos. |
| RN-7.1 | Conforme | Catalogo de optativas parametrico por ambito/vigencia y reglas de seleccion vigentes. |
| RN-7.1.1 | Conforme | Distingue optativas requeridas de adicionales y bloque fijo; un irregular solo por optativa reprobada puede completar requeridas faltantes sin pendiente obligatorio. Prueba `Bloque3OfertaYPreseleccionRn20261106Test`. |
| RN-7.2 | Conforme | Principio general de optativas reprobadas; no recupera reglas retiradas de ELECTIVA. |
| RN-7.3 | No aplica (retirada) | El flujo vigente no la invoca ni emite trazas `RN_7_3`; `Bloque2HistorialOportunidadesRn20261106Test` verifica tanto `reglasEvaluadas` como la lista de trazas. El identificador se conserva solo por compatibilidad de contratos. |
| RN-8 | Conforme | Seriacion general por relaciones curriculares, no por coincidencia de nombres. |
| RN-8.1 | Conforme | Seriacion interbloque y preseleccion condicionada. |
| RN-8.2 | Conforme | Seriacion intersemestral. |
| RN-8.3 | Conforme | Seriacion interanual. |
| RN-9.1 | Pendiente de dato | Reinscripcion extemporanea requiere calendario y acto institucional verificable. |
| RN-9.2 | Fuera de alcance | Procedimiento/acto institucional no automatizable desde la evaluacion en sombra. |
| RN-9.3 | Conforme | S7/S8 y seminarios: solo seriacion y requisitos expresos; sin umbral 50 %. GC-14. |
| RN-9.4 | Conforme | Evalua requisitos academicos del PE sin electiva minima ni bloqueo automatico por adeudo adicional. No autoriza certificacion/titulacion. |
| RN-9.5 | Conforme | Traza contexto, ambito, plan/cohorte, parametros, semestre con periodos y criterios por UD. |
| RN-10 | Fuera de alcance | Tramites/decisiones institucionales posteriores al dictamen curricular. |
| RN-10.1 | Fuera de alcance | Tramite institucional; el motor solo puede aportar evidencia curricular. |

## Regresion

Comando ejecutado:

```bash
cd codigoFuente/basegestor
mvn -q -Dtest='mx.gob.sedesol.basegestor.gestionescolar.v2.*Test' test
```

Resultado: `Tests run: 749, Failures: 0, Errors: 0, Skipped: 0`.

La evidencia de GC-13 (medida vigente/vencida y cuarta baja), GC-14 y RN-9.5 esta en `Bloque4TrayectoriaMedidaRn20261106Test` y `EvaluacionCurricularV2Fase9BTest`, incluidas en la regresion.
