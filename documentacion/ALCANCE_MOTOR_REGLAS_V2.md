# Alcance del Motor Curricular V2

**Versión normativa:** `PE2027-V1`
**Fuente funcional única:** `documentacion/V2_REGLAS_APROBADAS.md`
**Fecha de actualización:** 2026-10-06

## Decisión de alcance

El Motor Curricular V2 se desarrolla como evaluador académico central en modo sombra. Su alcance de integración futura comprende exclusivamente:

1. Malla Curricular;
2. Asistente;
3. Simulador.

El proceso productivo de inscripción y reinscripción no forma parte de este desarrollo. Ese módulo es actualizado por otro equipo bajo las mismas reglas aprobadas. En consecuencia, este repositorio no debe usar V2 para decidir oferta, seleccionabilidad, carga, bloqueos, confirmación, inscripción o reinscripción.

## Contrato de consistencia futuro

Para la misma combinación de persona técnica, plan, periodo y versión normativa, Malla Curricular, Asistente y Simulador deberán consumir una única salida de:

```text
EvaluacionCurricularV2Service
  → ResultadoEvaluacionCurricularV2DTO
```

Los componentes no deberán reimplementar las reglas de forma independiente. La integración se realizará posteriormente, después de validar el Gate E2E correspondiente, y deberá conservar la trazabilidad de reglas, motivos, decisiones por UD y excepciones.

## Estado actual

V2 continúa exclusivamente en modo sombra. Los adaptadores de lectura existentes pueden construir contexto desde información ya disponible, siempre que no produzcan efectos productivos. No existe consumidor visible de V2 en Malla, Asistente ni Simulador en este alcance.

La FASE 2A implementó RN-1.1 dentro del motor interno: clasifica actividad académica de forma separada de la situación académica y sólo con evidencia explícita. No integra el resultado con componentes visibles ni con inscripción.

## Gate E2E

El Gate E2E pendiente deja de ser un bloqueo para el desarrollo interno de reglas. Es requisito previo para integrar V2 con Malla Curricular, Asistente o Simulador y para activar cualquier comportamiento visible basado en el resultado V2.

## Restricciones vigentes

- No modificar código productivo de inscripción/reinscripción.
- No sustituir validadores productivos de inscripción.
- No utilizar V2 para decisiones de inscripción.
- No integrar aún V2 con Malla, Asistente ni Simulador.
- Mantener `PE2027-V1` y modo sombra hasta autorización de integración futura.
