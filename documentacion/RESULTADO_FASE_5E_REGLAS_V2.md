# Resultado FASE 5E — RN-6.1.5 y RN-6.1.6

Implementación sombra de impedimentos y clasificación extraordinaria. RN-6.1.5 registra concurrentemente RN-6.4, seriación directa/indirecta, sanción impeditiva y restricción expresa PE/normativa con código y fuente verificables. RN-6.1.6 sólo modela la ruta Dirección de División → CAI → DAEAE → CTIE cuando no hay supuesto tipificado ni impedimento definitivo. Datos insuficientes permanecen en revisión y nunca se convierten en caso extraordinario. No se autoriza avance, no se ejecuta workflow ni se modifica comportamiento productivo.

IMPEDIMENTOS_RN_6_1_5: `RN_6_4`, `SERIACION_DIRECTA_INDIRECTA`, `SANCION_IMPEDITIVA`, `RESTRICCION_PE_NORMATIVA`.

`RN_6_1_6_NO_BYPASS_RN_6_1_5 = true`.

`DATOS_INSUFICIENTES_NO_SON_EXTRAORDINARIO = true`.

Validación de cierre: regresión F0–F5E, 445 pruebas, 0 fallas y 0 errores. Compilación `basegestor`: exitosa. Compilación `gestorWeb`: exitosa.

PRUEBAS_F5E_DESCUBIERTAS = true.

CANTIDAD_PRUEBAS_F5E = 12.

REGRESION_TOTAL = 457. FALLAS = 0. ERRORES = 0. SKIPPED = 0.

CAMBIO_PRODUCTIVO = NINGUNO.
