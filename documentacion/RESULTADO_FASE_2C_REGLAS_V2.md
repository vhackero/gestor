# Resultado FASE 2C — RN-1.3 Recuperación de regularidad

**Fecha:** 2026-10-07
**Rama:** `feature/reglas-negocio-pe2027`
**Versión normativa:** `PE2027-V1`

## FASE_IMPLEMENTADA

`FASE 2C` incorpora RN-1.3 en el Motor Curricular V2 en modo sombra. La evaluación reutiliza `situacionAcademicaCalculada` y `cantidadReprobadasVigentes` de RN-1.2; no calcula de nuevo regularidad ni altera el estado productivo.

## CRITERIO_RECUPERACION E IMPEDIMENTOS

El resultado declara `PROCEDE_EN_SOMBRA` cuando no existen reprobaciones vigentes, seriación pendiente conocida, observación 4+ de RN-6.4, impedimento de actividad/permanencia y sí existe evidencia suficiente de actividad y permanencia. Declara `NO_PROCEDE` ante reprobación vigente, seriación aplicable, condición severa observada, límite de permanencia o actividad incompatible. Declara `REQUIERE_REVISION` si falta evidencia necesaria o RN-1.2 es `NO_DETERMINADA`.

La condición 4+ se usa sólo como impedimento observacional. RN-6.4 no se activa ni modifica carga, oferta o bloqueos.

## TRATAMIENTO DE PENDIENTES Y AVANCE

Las UD con `BAJA_PARCIAL` o `NO_CURSADA` se reportan como pendientes no reprobatorios y no impiden por sí mismas la recuperación. `EN_CURSO` tampoco se convierte en reprobación. `avanceCondicionado` permanece independiente: no elimina reprobaciones ni crea por sí solo regularidad.

## MODELO Y TRAZABILIDAD

`ResultadoEvaluacionCurricularV2DTO` incorpora campos de recuperación evaluada, procedencia, resultado, condiciones cumplidas/no cumplidas, UD reprobadas impeditivas y pendientes no reprobatorios. `ContextoEvaluacionCurricularV2DTO` recibe evidencia explícita de permanencia dentro de límite o límite excedido. RN-1.3 deja una traza RN-9.5 con resultado RN-1.2, condiciones, UD relacionadas y versión normativa.

## PRUEBAS

Se agregaron F2C-U-01 a F2C-U-16 para reprobaciones, acreditación posterior, bajas/no cursamiento, duplicados, seriación, 4+, avance condicionado, actividad, datos insuficientes, determinismo e inmutabilidad.

La regresión F0, F1A–F1E, F2A, F2B y F2C ejecutó 106 pruebas TestNG: **106 exitosas, 0 fallas, 0 errores y 0 omitidas**. `basegestor` compiló como parte de Maven Test y `gestorWeb` compiló con Maven Clean Compile.

## RIESGOS

El adaptador vigente aún no proporciona un indicador canónico de permanencia. En ausencia de ese dato, RN-1.3 devuelve `REQUIERE_REVISION` y no infiere recuperación. No se integró V2 con Malla, Asistente, Simulador ni inscripción.

`CAMBIO_PRODUCTIVO = NINGUNO`.
