# Resultado FASE 2E — RN-1.5 Recursamiento para mejora de promedio

**Fecha:** 2026-10-07
**Rama:** `feature/reglas-negocio-pe2027`
**Versión normativa:** `PE2027-V1`

## FASE_IMPLEMENTADA

RN-1.5 se implementa exclusivamente en modo sombra. Evalúa solicitudes voluntarias de mejora sobre UD acreditadas sin modificar oferta, cupo, grupos, promedio oficial, certificado ni historial productivo.

## CRITERIO_ELEGIBILIDAD Y LÍMITE GLOBAL

La evaluación requiere UD acreditada, estado de actividad `ACTIVO`, egreso conocido y no iniciado, oportunidades globales disponibles, UD ofertada, grupo activo y cupo. El límite es **3 oportunidades de mejora en todo el Programa Educativo**, no por UD. El resultado conserva `oportunidadesMejoraUtilizadas` y `oportunidadesMejoraDisponibles` separados de los intentos de RN-1.4.

Los estados sombra incluyen elegible, no elegible, sin cupo, límite agotado, egreso iniciado, estudiante no activo y revisión requerida. La prioridad se registra como inferior a primera inscripción y recursamiento por reprobación; no reserva lugar alguno.

## CALIFICACIÓN FAVORABLE

Los intentos históricos admiten calificación final y marca de recursamiento de mejora. La evaluación identifica la calificación favorable como el máximo histórico sin borrar ni sustituir registros. El objetivo de referencia 8.5 se registra como contexto, no como requisito de elegibilidad.

## RELACIÓN CON RN-1.4

RN-1.4 cuenta intentos para acreditar una UD. RN-1.5 cuenta recursamientos voluntarios de mejora de forma global. Los contadores no se suman ni alteran RN-1.2/RN-6.4.

## TRAZABILIDAD Y PRUEBAS

Se registra RN-1.5 por UD con acreditación, calificación favorable, oportunidades, actividad, egreso, oferta, cupo, prioridad, motivos y evidencia. Se agregaron F2E-U-01 a F2E-U-21 para elegibilidad, calificaciones, límite global, coexistencia con RN-1.4, actividad, egreso, oferta, cupo, prioridad, duplicidad, determinismo e inmutabilidad.

La regresión F0, F1A–F1E, F2A, F2B, F2C, F2D y F2E ejecutó 144 pruebas TestNG: **144 exitosas, 0 fallas, 0 errores y 0 omitidas**. `basegestor` compiló como parte de Maven Test y `gestorWeb` compiló con Maven Clean Compile.

## RIESGOS

El adaptador actual no expone todavía una fuente canónica de dictaminación de egreso, oportunidades globales ni calificaciones detalladas. Cuando no estén disponibles, V2 devuelve revisión requerida y no infiere elegibilidad. No existe integración visible con Malla, Asistente o Simulador.

`CAMBIO_PRODUCTIVO = NINGUNO`.
