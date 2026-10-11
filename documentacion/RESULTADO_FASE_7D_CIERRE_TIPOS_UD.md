# Resultado Fase 7D: Cierre Integral de Reglas por Tipo de UD

## Identificación

- `FASE_IMPLEMENTADA = FASE 7D`
- `REGLAS_VALIDADAS = RN-7.1, RN-7.1.1, RN-7.2, RN-7.3`
- `VERSION_NORMATIVA = PE2027-V1`
- `MODO = SOMBRA`
- `CAMBIO_PRODUCTIVO = NINGUNO`

## Modelo de tipos de UD

Fase 7 conserva tres dimensiones curriculares independientes: `OBLIGATORIA`, `OPTATIVA` y `ELECTIVA`. El estado de una UD, por ejemplo `REPROBADA` o `ACREDITADA`, no modifica su tipo.

La auditoría F7D incorpora detección de tipo ambiguo: una etiqueta que contenga simultáneamente `OPTATIVA` y `ELECTIVA` no se procesa bajo ambas semánticas. La unidad queda fuera de las listas específicas, se registra en `udsTipoAmbiguoFase7` y el cierre devuelve `REQUIERE_REVISION`. Así se evita doble conteo o decisiones contradictorias sin inventar una clasificación institucional.

## Optativas

RN-7.1 y RN-7.1.1 permanecen integradas:

- una optativa reprobada vigente contribuye a RN-1.2 y RN-6.4;
- hasta tres no producen bloqueo autónomo por tipo;
- una optativa reprobada ofertada conserva prioridad sobre una nueva optativa, mediante RN-6.3 y la composición RN-7.1;
- ocho optativas acreditadas cumplen el requisito;
- con nueve o más, el requisito sigue cumplido y el excedente es adicional opcional cuando existe orden verificable de acreditación;
- una adicional opcional no crea requisito nuevo, pero una reprobación adicional conserva sus efectos académicos comunes.

El conteo del requisito usa sólo estados vigentes `ACREDITADA`, deduplicados. Reprobadas, bajas parciales, no cursadas, no inscritas e intentos no inflan el requisito.

## Electivas

RN-7.2 permanece integrada:

- sólo evalúa UD formalmente `ELECTIVA`;
- requiere semestre objetivo curricular S7 o S8;
- requiere PE de origen distinto al PE de la persona;
- conserva oferta/cupo, seriación y carga como dependencias comunes;
- una electiva reprobada aporta irregularidad y RN-6.4, pero no obliga por sí misma a repetir esa misma electiva antes de evaluar otra;
- evaluar una electiva distinta no borra, sustituye ni cancela la reprobación previa;
- más de una electiva puede evaluarse cuando la carga y restricciones lo permiten.

RN-9.3, equivalencias curriculares y grupos combinados no se implementan en Fase 7D.

## Principio común RN-7.3

RN-7.3 consolida, no recalcula. Consume los resultados vigentes de RN-7.1, RN-7.2, RN-1.2, RN-6.3, RN-6.4, RN-4.x, seriación y oferta/cupo. Expone las reprobadas por tipo y confirma que, con total común menor a cuatro, una optativa/electiva reprobada no genera bloqueo autónomo por su tipo.

Cuando el total común alcanza cuatro o más, la restricción deriva de RN-6.4. No se atribuye el bloqueo al tipo de UD. Si existe una obligatoria pendiente prioritaria, prevalece sobre optativas/electivas. Si hay conflicto material entre optativa y electiva reprobadas sin desempate normativo, se devuelve revisión; no se ordena por atributos técnicos.

## Estado vigente, total común y situación académica

La decisión usa estado vigente. Una reprobación histórica que ahora está acreditada no se cuenta para irregularidad actual ni para RN-6.4, aunque se preserve como evidencia histórica.

Existe un solo contador: `ResultadoEvaluacionCurricularV2DTO.cantidadReprobadasVigentes`. F7D lo referencia como `totalReprobadasComunRn73`; no crea un contador alterno. Incluye obligatorias, optativas y electivas reprobadas vigentes, sin duplicados. `NO_CURSADA`, `BAJA_PARCIAL`, `NO_INSCRITA`, `EN_CURSO` y `NO_DETERMINADA` no se transforman en reprobaciones.

La situación `REGULAR`, `IRREGULAR` o `NO_DETERMINADA` se mantiene exclusivamente como salida de RN-1.2. Fase 7 no agrega clasificadores de situación por tipo.

## Prioridad, carga, seriación y disponibilidad

| Dependencia | Fuente reutilizada | Tratamiento Fase 7 |
| --- | --- | --- |
| Situación académica | RN-1.2 | Reutilizada |
| Total de reprobadas | Resultado común V2 | Reutilizado |
| Restricción severa | RN-6.4 | Reutilizada |
| Prioridad de reprobadas | RN-6.3 | Reutilizada |
| Prioridad optativa | RN-7.1 | Orquestada |
| Carga | RN-4.1, RN-4.2, RN-4.3 | Reutilizada |
| Seriación | Motor V2 | Reutilizada por UD |
| Oferta/cupo | Contrato normalizado | Reutilizado; `SIN_OFERTA != SIN_CUPO` |

F7D no crea rutas paralelas `rutaOptativas`, `rutaElectivas` o `rutaFase7`. La ruta académica sigue siendo la común existente.

## Integración RN-6.1, RN-6.3 y RN-6.4

RN-6.1 sólo aplica a objetivo curricular S5 desde `2027-1`. Optativas y electivas no se agregan artificialmente a su base obligatoria de semestres 1 a 4. Para electivas en S7/S8, RN-6.1 no se reactiva.

RN-6.3 conserva la prioridad general. RN-7.1 agrega sólo el orden entre optativa reprobada ofertada y nueva optativa; RN-7.2 no convierte reprobación de electiva en obligación de repetirla. RN-6.4 usa el total común y prevalece cuando corresponde.

## Contrato de resultado V2

El cierre incorpora en `EvaluacionCargaAcademicaV2DTO`:

- `estadoCierreFase7`;
- `requiereRevisionFase7`;
- `udsTipoAmbiguoFase7`;
- `decisionesCierreFase7`.

El contrato existente ya concentra cumplimiento de optativas, elegibilidad de electivas, reprobadas por tipo, total común, situación, RN-6.4, prioridades, carga, seriación, oferta/cupo y decisiones por UD. F7D audita que las listas de optativas y electivas no se intersecten y que la semántica no se duplique.

El resultado queda disponible para Malla Curricular, Asistente y Simulador en una fase futura. Ninguno de esos consumidores fue modificado.

## Idempotencia y determinismo

Con el mismo contexto normalizado, la evaluación no duplica acreditadas, reprobadas, prioridades ni decisiones de cierre. Las pruebas comparan resultados de dos ejecuciones con el mismo contexto y confirman igualdad de estado y listas relevantes. El motor conserva `efectosProductivosAplicados = false`.

## Auditoría de duplicación

La revisión de Fase 7 no localizó implementaciones paralelas de situación académica, contador de reprobadas, RN-6.4, carga, seriación, oferta/cupo, prioridad o ruta académica. F7D sólo agrega validación de coherencia del contrato y trazabilidad de tipos.

## Dependencias futuras

Permanecen fuera de alcance y sin implementación:

- RN-8.x;
- RN-9.3;
- porcentaje de créditos y reglas específicas de últimos semestres;
- seminario de titulación;
- equivalencias/sustituciones productivas entre electivas;
- grupos combinados y operaciones de oferta/cupo.

## Trazabilidad

El cierre conserva RN-7.1, RN-7.1.1, RN-7.2 y RN-7.3 como reglas evaluadas y registra evidencia de tipos, estados, reprobadas por tipo, total común, RN-6.4 y estado de cierre F7D. Los tipos ambiguos se trazan como datos insuficientes. No se incorpora PII.

## Pruebas

`EvaluacionCurricularV2Fase7DTest` cubre 16 escenarios integrados:

1. Cierre regular en sombra.
2. Tipos propios e irregularidad común.
3. Transición total tres/cuatro.
4. Exclusión de baja parcial y no cursada.
5. Tipo ambiguo sin doble semántica.
6. Requisito de siete, ocho y nueve optativas.
7. Deduplicación y adicional reprobada.
8. Elegibilidad electiva S7/S8 y PE distinto.
9. Otro PE sin clasificación electiva.
10. Electiva reprobada sin repetición ficticia.
11. Prioridad obligatoria.
12. Cupo y seriación por UD.
13. Integración con RN-6.1.
14. Estado vigente y transición cuatro/tres.
15. Determinismo e idempotencia.
16. Ausencia de efecto productivo.

## Riesgos pendientes

- La clasificación curricular de una UD debe llegar no ambigua; el motor no decide entre etiquetas contradictorias.
- El orden individual de optativas adicionales requiere evidencia de acreditación.
- Conflictos materiales entre optativa y electiva reprobadas requieren regla normativa de desempate.
- Las dependencias futuras permanecen fuera de esta fase.

## Archivos modificados

- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EstadoCierreFase7V2.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/commons/dto/gestionescolar/v2/EvaluacionCargaAcademicaV2DTO.java`
- `codigoFuente/basegestor/src/main/java/mx/gob/sedesol/basegestor/service/impl/gestionescolar/v2/EvaluacionCurricularV2ServiceImpl.java`
- `codigoFuente/basegestor/src/test/java/mx/gob/sedesol/basegestor/gestionescolar/v2/EvaluacionCurricularV2Fase7DTest.java`

## Resultado

FASE 7 queda cerrada técnicamente en modo sombra. RN-7.1, RN-7.1.1, RN-7.2 y RN-7.3 conservan sus semánticas propias, comparten los motores comunes V2 y exponen un contrato único auditado. No se iniciaron Fase 8, RN-8.x, RN-9.3 ni flujos productivos.
