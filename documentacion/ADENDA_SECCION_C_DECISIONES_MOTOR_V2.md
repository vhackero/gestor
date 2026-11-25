# Adenda C - Cómo decide actualmente el Motor Curricular V2

## Identificación

| Dato | Valor |
| --- | --- |
| Fecha de revisión | 2026-11-05 |
| Rama | `feature/desacople_motor_historico` |
| Commit base | `946c5d98` |
| Alcance | Código y pruebas localizadas; sin cambios funcionales ni ejecución adicional de pruebas. |

Esta adenda responde a cinco preguntas técnicas. Describe el comportamiento existente, no valida todavía su conformidad con una actualización normativa.

## 1. Constantes normativas escritas en el código

| Valor | Significado actual | Ubicación |
| --- | --- | --- |
| `PE2027-V1` | Versión técnica predeterminada de reglas. | `ConfiguracionEvaluacionCurricularV2ServiceImpl.java:16` |
| `2027-1` | Inicio codificado para la procedencia de CTR-01. Aunque existe la propiedad `CTR01_PERIODO_INICIO`, el método del evaluador compara directamente con `2027-1`. | `ConfiguracionEvaluacionCurricularV2ServiceImpl.java:17`; `EvaluacionCurricularV2ServiceImpl.java:4197-4203` |
| `5` | Semestre de CTR-01 y candidatas de quinto. | `ConfiguracionEvaluacionCurricularV2ServiceImpl.java:18`; `EvaluacionCurricularV2ServiceImpl.java:4205-4220` |
| `3` UD | Carga mínima general para semestres posteriores al primero, con excepciones tipificadas. | `EvaluacionCurricularV2ServiceImpl.java:1397-1535` |
| `8` UD / `6` UD | Carga máxima: regular/irregular, respectivamente. | `EvaluacionCurricularV2ServiceImpl.java:1053-1063,1538-1615` |
| `4` reprobadas vigentes únicas | Umbral de RN-6.4. | `EvaluacionCurricularV2ServiceImpl.java:197-202,1912-2045` |
| `3` intentos fallidos | Límite de RN-1.4 por UD: con dos fallidos se marca última oportunidad; con tres, límite agotado. | `EvaluacionCurricularV2ServiceImpl.java:1222-1238` |
| `3` oportunidades de mejora | Restante calculado como `3 - utilizadas`; actualmente la misma RN-1.5 prohíbe reinscripción de una UD acreditada sólo para mejorar promedio. | `EvaluacionCurricularV2ServiceImpl.java:1248-1318` |
| `8` optativas acreditadas | Umbral actual para requisito de optativas y para clasificar adicionales. | `EvaluacionCurricularV2ServiceImpl.java:974-1005,3723-3849` |
| `1..4`, `5..6`, `7..8` | Rangos usados por reglas de avance, componentes y código heredado de electivas. | `EvaluacionCurricularV2ServiceImpl.java:1015,2164,3937,3995` |

Las constantes de carga, intentos, optativas, periodo y semestre están mezcladas entre configuración y evaluador. Por tanto, cualquier actualización normativa debe decidir para cada una si se vuelve parametrizable, si cambia de valor o si se elimina.

## 2. Cómo calcula hoy el semestre del alumno

El adaptador `ContextoEvaluacionCurricularV2AdapterImpl` no calcula el semestre desde trayectoria, créditos acreditados ni última inscripción histórica. Ejecuta este criterio:

1. Mapea las UD disponibles en la consulta de inscripción.
2. Filtra las que tienen `ofertada=true` y semestre no nulo.
3. Toma el **mayor semestre curricular** entre ellas.
4. Lo asigna a `ContextoEvaluacionCurricularV2DTO.semestreObjetivo`.

Fuente: `ContextoEvaluacionCurricularV2AdapterImpl.java:53,165-174`.

Consecuencias actuales:

- Es el semestre objetivo de la oferta/candidatura recibida, no una determinación independiente del semestre real cursado por la persona.
- Si la oferta contiene una UD de semestre más alto, el resultado puede elevar el semestre objetivo aunque la trayectoria no lo justifique.
- Si no hay UD ofertadas, devuelve `null`; las reglas dependientes pasan a `NO_DETERMINADO` o `REQUIERE_REVISION`.
- CTR-01 tiene una salvaguarda adicional: puede aplicarse si el contexto trae candidatas de quinto, aun cuando el semestre objetivo sea distinto.

Este criterio debe revisarse antes de cambiar RN que dependen de semestre, en especial RN-4.1, RN-6.1, RN-6.5 a RN-6.8, RN-7.2, RN-9.3 y RN-9.4.

## 3. Cómo cuenta intentos, NP y extraordinarios

### Intentos

La evaluación de RN-1.4 es por UD y necesita una identidad de ejecución: `idEjecucionTecnico` o `idParticipacion`.

- Cuenta un intento identificado sólo una vez; descarta duplicados por esa llave.
- Incrementa `intentosFallidos` únicamente para `EstadoIntentoAcreditacionV2.REPROBADO`.
- Incrementa `intentosAcreditados` únicamente para `ACREDITADO`.
- Si no hay detalle de intentos, acepta como respaldo el agregado `intentosFallidosHistoricosConfiables`.
- Si sólo existe el conteo legado `intentos` sin identidad/calidad de intento, marca `REQUIERE_REVISION`.
- Una UD acreditada no requiere oportunidad adicional, aunque tenga antecedentes fallidos.

Fuente: `EvaluacionCurricularV2ServiceImpl.java:1165-1246`.

### NP

No existe `NP` como estado de `EstadoIntentoAcreditacionV2`. El modelo V2 distingue `BAJA_PARCIAL`, `NO_CURSADA`, `NO_INSCRITA`, `EN_CURSO` y `NO_DETERMINADO` de `REPROBADO`.

Por tanto, un NP sólo queda excluido del cómputo de intentos si el adaptador lo entrega con una de esas causas no reprobatorias, en especial `BAJA_PARCIAL` autorizada. Si un origen registra NP como reprobación, el motor no tiene por sí solo datos para corregir esa clasificación.

Fuentes: `EstadoIntentoAcreditacionV2.java:4-13`; `EvaluacionCurricularV2ServiceImpl.java:146-156,1207-1238`.

### Extraordinarios

El motor no incrementa ni crea una oportunidad extraordinaria. Sólo clasifica una excepción como `EXTRAORDINARIA` cuando el contexto ya la aporta y la ruta ordinaria requiere resolución documentada. La salida es descriptiva (`REQUIERE_RESOLUCION_EXTRAORDINARIA`), sin autorización, inscripción ni cambio de historial.

Fuente: `EvaluacionCurricularV2ServiceImpl.java:757-829,2450-2485,4227-4305`.

## 4. Si el código genera o propone bajas

El evaluador `EvaluacionCurricularV2ServiceImpl` no crea, autoriza, aplica ni propone solicitudes de baja. No consulta repositorios y su resultado se inicializa con `efectosProductivosAplicados=false`.

Lo que sí hace:

- Normaliza una baja parcial ya recibida y la conserva como causa distinta de reprobación.
- Describe baja temporal, reincorporación y trayectoria cuando el contexto contiene evidencia explícita.
- Genera prioridades o resultados “procede en sombra” para UD con baja parcial; esto es una evaluación, no un movimiento escolar.
- Registra excepciones/condiciones de baja en trazas.

Fuentes: `EvaluacionCurricularV2ServiceImpl.java:120-123,146-156,1320-1395,2782-3315`.

La única escritura relacionada con el flujo V2 localizado es la **auditoría RN-9.5**, cuando el consumidor llama expresamente `TrazabilidadEvaluacionCurricularV2Service.registrarSombra(...)`. Esa escritura persiste el resultado y trazas en tablas V2; no toca tablas de bajas, inscripción, grupos, oferta, trayectoria ni calificaciones.

Fuente: `TrazabilidadEvaluacionCurricularV2ServiceImpl.java:33-76`.

## 5. Qué son las “oportunidades de mejora”

Son un contador global separado de las oportunidades/intententos ordinarios de RN-1.4. El motor lo obtiene así:

1. Usa `ContextoEvaluacionCurricularV2DTO.oportunidadesMejoraUtilizadas` si se proporciona.
2. Si no, cuenta intentos con `recursamientoMejora=true`, deduplicados por identidad técnica/participación.
3. Calcula disponibles como `max(0, 3 - utilizadas)`.
4. Por cada UD, conserva la mejor calificación histórica como información, pero para una UD acreditada devuelve `NO_ELEGIBLE` con motivo `RN15_UD_ACREDITADA_NO_REINSCRIBIBLE_POR_MEJORA_PROMEDIO`.

En el estado actual, el contador no habilita una reinscripción para mejorar promedio: es información/trazabilidad de un concepto legado que quedó incompatible con la prohibición explícita de RN-1.5.

Fuentes: `EvaluacionCurricularV2ServiceImpl.java:1248-1318`; `IntentoAcreditacionUnidadV2DTO.java:8-31`.

## Riesgos que deben resolverse antes de modificar reglas

1. Corregir o confirmar la fuente de `semestreObjetivo`: hoy refleja la mayor UD ofertada, no necesariamente semestre efectivo ni siguiente semestre de trayectoria.
2. Definir el mapeo institucional de NP a `REPROBADO`, `BAJA_PARCIAL` u otra causa antes de usarlo para RN-1.4.
3. Confirmar si las tres oportunidades de mejora deben eliminarse del modelo, quedar como dato histórico o tener otra semántica; RN-1.5 ya bloquea la acción de mejora de promedio.
4. Centralizar/parametrizar los valores que la actualización normativa cambie, en lugar de modificar sólo un consumidor o una vista.
5. Mantener separada la auditoría RN-9.5 de cualquier cambio productivo escolar.
