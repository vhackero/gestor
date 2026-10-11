# Resultado Fase 6F - Cierre de reincorporacion y trayectoria V2

- `FASE_IMPLEMENTADA`: FASE 6F
- `REGLAS_VALIDADAS`: RN-6.2, RN-6.5, RN-6.6, RN-6.7, RN-6.8
- `VERSION`: PE2027-V1
- `CAMBIO_PRODUCTIVO`: NINGUNO
- `MODO`: sombra

## CLASIFICACION_CAUSA_PRINCIPAL

Fase 6F agrega una consolidacion posterior a las evaluaciones existentes. No evalua una regla academica adicional. El contrato devuelve una unica `causaPrincipalTrayectoriaFase6`:

- `INSCRIPCION_INICIAL_DIFERIDA`: RN-6.2.
- `REINCORPORACION_TRAS_PERIODO_NO_CURSADO`: RN-6.5.
- `REINCORPORACION_TRAS_BAJA_TEMPORAL`: RN-6.6.
- `NORMAL`, `REQUIERE_REVISION` o `NO_DETERMINADO` cuando no existe una causa principal verificable.

La consolidacion rechaza como `REQUIERE_REVISION` una combinacion contradictoria de causas principales, por ejemplo inscripcion inicial no realizada con trayectoria ya cursada, o RN-6.5 y RN-6.6 aplicables para la misma interrupcion. Una baja temporal formal conserva precedencia sobre la omision de periodo.

## CONDICIONES_CONCURRENTES

RN-6.7 no sustituye la causa principal. Si hay una UD con `BAJA_PARCIAL` verificable, se registra como condicion concurrente aun cuando la causa principal sea RN-6.5 o RN-6.6. La compuerta de RN-6.5 se limita a bajas temporales, y RN-6.7 ya no descarta una baja parcial real solo porque exista una baja temporal total en la trayectoria.

RN-6.8 se conserva como evento de actualizacion de cierre. Puede coexistir con una causa principal previa, pero no la reemplaza.

## TRAYECTORIA_EFECTIVA E HISTORICO_VS_VIGENTE

La causa principal, el semestre objetivo y la ruta consolidada consumen los resultados existentes de trayectoria efectiva. RN-6.6 conserva el ultimo periodo efectivamente cursado y no usa periodos calendario de baja para adelantar el semestre. RN-6.8 usa los estados vigentes al cierre; un antecedente historico de reprobacion o baja parcial no se conserva artificialmente como pendiente si la UD vigente ya esta acreditada.

El contrato expone `trayectoriaAntesFase6` y `trayectoriaDespuesFase6` como evidencia descriptiva, sin persistir una historia adicional ni incluir PII.

## RUTA_ACADEMICA_UNICA, PRIORIDAD, SERIACION Y CARGA

`udsRutaAcademicaConsolidadaFase6` integra sin duplicados la ruta de la causa principal, las prioridades por baja parcial RN-6.7 y el evento de cierre RN-6.8 cuando aplique. Las listas particulares de cada regla se conservan como evidencia de origen.

No se crea un motor paralelo:

| Dependencia | Tratamiento F6 |
| --- | --- |
| Situacion academica | Reutilizada desde RN-1.2 |
| Carga | Reutilizada desde RN-4.1, RN-4.2 y RN-4.3 |
| Seriacion | Reutilizada desde el motor V2 comun |
| Reprobadas prioritarias | Reutilizada desde RN-6.3 |
| Baja parcial | Orquestada con RN-6.7 por UD |
| Oferta/cupo | Reutiliza clasificacion individual; `SIN_OFERTA != SIN_CUPO` |
| RN-6.4 | Reutilizada; solo reprobadas vigentes reales |
| RN-6.1 | Reutilizada desde Fase 5F, solo objetivo S5 desde 2027-1 |

Si compiten una prioridad RN-6.3 y una RN-6.7 sin criterio normativo de desempate y esto afecta la carga, se conserva `REQUIERE_REVISION`; Fase 6F no inventa una jerarquia.

## INTEGRACION_RN_6_4 E INTEGRACION_RN_6_1

La sintesis conserva `REINCORPORACION_RESTRINGIDA_RN64` cuando RN-6.4 aplica. No cuenta `NO_CURSADA`, `BAJA_PARCIAL`, `BAJA_TEMPORAL` ni `NO_INSCRITA` como reprobacion.

RN-6.1 sigue siendo la decision unica de Fase 5F. La consolidacion no crea versiones RN-6.x especiales. La transicion RN-6.8 non a par conserva la no reactivacion de RN-6.1.

## TRANSICIONES_ENTRE_REGLAS

- RN-6.2 puede dejar de ser activa cuando existe inscripcion inicial posterior; una omision real posterior puede ser RN-6.5.
- RN-6.5 puede dar paso a RN-6.6 ante una baja temporal formal posterior.
- RN-6.6 puede coexistir con RN-6.7 si existe baja parcial por UD.
- Una UD RN-6.7 acreditada puede ser reconocida por RN-6.8 al cierre sin conservar su pendiente artificialmente.

## CONTRATO_RESULTADO_V2 Y TRAZABILIDAD

Se agregan al contrato existente `CausaPrincipalTrayectoriaV2`, `EstadoConsolidacionFase6V2`, condiciones concurrentes, ruta consolidada, decisiones de consolidacion y trayectoria antes/despues. La traza RN-9.5 registra causa, condiciones, estados de RN-6.2/RN-6.5/RN-6.6/RN-6.7/RN-6.8, ruta y evidencia. No se agregan DTO paralelos ni datos personales.

El resultado por UD permanece en las decisiones V2 existentes. La ruta consolidada usa incorporacion unica de identificadores para mantener determinismo e idempotencia.

## PRUEBAS

`EvaluacionCurricularV2Fase6FTest` agrega 16 escenarios integrados: precedencia RN-6.2/RN-6.5/RN-6.6, continuidad de baja temporal, coexistencia RN-6.7 con RN-6.5 y RN-6.6, ausencia de irregularidad artificial, RN-6.4, RN-6.1 en S5, evento RN-6.8, vigencia frente a historial, deduplicacion de ruta, determinismo e inexistencia de efectos productivos.

## RIESGOS_PENDIENTES

- La fuente que forma `ContextoEvaluacionCurricularV2DTO` debe aportar evidencia consistente de inscripcion inicial, baja temporal, periodos omitidos y cierre; ante contradiccion se conserva revision.
- La integracion con Malla, Asistente y Simulador queda fuera de alcance; el contrato queda preparado para esas fases.
- No se inicia Fase 7 ni se define semantica adicional para optativas/electivas.

## ARCHIVOS_MODIFICADOS

- `CausaPrincipalTrayectoriaV2.java`
- `EstadoConsolidacionFase6V2.java`
- `EvaluacionCargaAcademicaV2DTO.java`
- `EvaluacionCurricularV2ServiceImpl.java`
- `EvaluacionCurricularV2Fase6FTest.java`

## RESULTADO

Fase 6 queda integrada en modo sombra. No se modifican inscripcion, reinscripcion, oferta, cupo, grupos, estados productivos, Malla, Asistente ni Simulador. Fase 7 no fue iniciada.
