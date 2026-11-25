# Resultado FASE 2D — RN-1.4 Límite de intentos

**Fecha:** 2026-10-07
**Rama:** `feature/reglas-negocio-pe2027`
**Versión normativa:** `PE2027-V1`

## FASE_IMPLEMENTADA

`FASE 2D` implementa RN-1.4 en modo sombra. El límite configurado es **3 intentos fallidos por UD**. La métrica es independiente de la cantidad de UD reprobadas vigentes de RN-1.2 y de la condición 4+ de RN-6.4.

## CRITERIO_DE_INTENTO

Se agregan contratos para registrar intentos técnicos con identidad de ejecución o participación, periodo, grupo, estado y evidencia. Sólo los intentos `REPROBADO` consumen oportunidades; baja parcial, no cursada, no inscrita y en curso se conservan pero no se cuentan como fallidos. El resumen canónico de intentos fallidos se admite cuando la fuente lo identifica como confiable. Un conteo legado sin calidad de intento resulta en `REQUIERE_REVISION`.

## LÍMITE Y ACREDITACIÓN POSTERIOR

- 0–1 fallidos: `DISPONIBLE`.
- 2 fallidos: `ULTIMA_OPORTUNIDAD`.
- 3 fallidos: `LIMITE_AGOTADO`, con escalamiento técnico requerido.
- Más de 3 registros/fallidos: `REQUIERE_REVISION`; no se normaliza silenciosamente un cuarto intento.
- Si existe acreditación posterior o la UD está acreditada: `ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA`; conserva el historial pero no la considera bloqueada por RN-1.4.

Los duplicados de la misma ejecución se descartan con trazabilidad. RN-1.4 no modifica el conteo de reprobadas de RN-1.2, la recuperación de RN-1.3 ni la observación RN-6.4.

## TRAZABILIDAD

Cada UD recibe una `EvaluacionIntentosUnidadV2DTO` y una traza RN-1.4 asociada a su decisión sombra: conteos, evidencia, duplicados, estado del límite, última oportunidad, agotamiento y escalamiento. RN-9.5 persiste las trazas existentes sin cambio productivo.

## PRUEBAS

Se agregaron F2D-U-01 a F2D-U-17 para el límite, acreditación posterior, tipos no consumibles, duplicados, bloques, UD independientes, relación con RN-1.2, ambigüedad, determinismo e inmutabilidad.

La regresión F0, F1A–F1E, F2A, F2B, F2C y F2D ejecutó 123 pruebas TestNG: **123 exitosas, 0 fallas, 0 errores y 0 omitidas**. `basegestor` compiló como parte de Maven Test y `gestorWeb` compiló con Maven Clean Compile.

## RIESGOS

El contexto de lectura actual sólo aporta un resumen canónico de reprobaciones cuando existe; aún no entrega identidad de todos los intentos históricos. El motor no inventa esa identidad: marca revisión cuando recibe un conteo legado ambiguo. No se bloquea ni se ejecuta baja definitiva productiva.

`CAMBIO_PRODUCTIVO = NINGUNO`.
