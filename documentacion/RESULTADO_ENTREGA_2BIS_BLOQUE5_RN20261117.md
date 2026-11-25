# Entrega 2-bis - Bloque 5 RN 2026-11-17

## GC-17: salida del caso sintético

| UD | Confirmados | En curso | En revisión | Resultados reprobatorios | Adeudo | Oportunidad | Decisión |
| --- | ---: | ---: | ---: | ---: | --- | --- | --- |
| A | 3 | Sí | 0 | 2 | Sí | `ULTIMA_EN_CURSO` | `PRIORITARIA`, preselección 1 |
| B | 1 | No | 0 | 1 | Sí | `DISPONIBLE` | `PRIORITARIA`, preselección 3 |
| C | 2 | No | 0 | 1 | No, acreditada posteriormente | `DISPONIBLE` | Sin adeudo vigente |
| D | 2 | No | 0 | 2 | Sí | `ULTIMA_DISPONIBLE` | `PRIORITARIA`, preselección 2 |
| E | 0 | No | 0 | 0 | No, pendiente por seriación cerrada | `DISPONIBLE` | Fuera de cómputos C-18/C-19/6.9 |
| G | 0 | No | 0 | 0 | No, baja parcial autorizada | `DISPONIBLE` | Pendiente por baja, no reprobación |

Salida consolidada del test: resultados reprobatorios informativos `6`; adeudos vigentes `3`; RN-6.4 no aplica; situación `IRREGULAR`; carga máxima `6`; orden de preselección `A`, `D`, `B`. El servicio de semestre de referencia cuenta seis periodos para 2027-1 y siete para 2027-2, excluyendo 2026-1 por baja temporal completa.

## GC-17d y RN-6.7

| Cobertura | Resultado |
| --- | --- |
| GC-17d positivo | Campo opcional `idAdeudoOptativaOriginal`: la sustituta concentra un solo adeudo; la original conserva su intento e historial. Sin relación, continúa `REQUIERE_REVISION`. |
| Obligatoria con baja ofertada | Prioritaria. |
| Obligatoria con baja sin oferta | Se identifica `SIN_OFERTA`, sin bloqueo absoluto de la ruta restante. |
| Optativa con baja ofertada | Prioritaria sólo si `necesariaParaRequerimientoCurricular=true`; no bloquea obligatorias. |
| Optativa con baja sin oferta | Permite las demás UD elegibles. |
| Baja durante recursamiento | La baja no borra una reprobación previa: el adeudo permanece por el historial. |
| Sanción | Sigue condicionada a resolución institucional verificable. |

## Recursamiento de quinto: respuesta actual para Q5

Una UD de quinto ya reprobada, con una obligatoria pendiente de primero a cuarto, se evalúa como candidata de quinto. Con menos de cuatro adeudos, RN-6.1 conserva la obligación pendiente ofertada y no permite omitirla para seleccionar quinto; con cuatro o más, RN-6.4 prevalece y limita la carga a regularización. No se modificó este comportamiento.

## Matriz CAMPUS 2: correcciones de interpretación

| IDs | Resultado del motor / regla prevalente |
| --- | --- |
| EXT-01 | Reinscripción extemporánea: RN-9.1 no dispensa requisitos. |
| REI-02 | RN-6.7 y, cuando falta oferta en quinto, RN-6.1.4. |
| OPT-01 | Optativa reprobada cuenta para RN-6.4; no es obligatoria pendiente de RN-6.1. |
| AV-09 | RN-6.4 prevalece aunque concurra falta de oferta. |
| AV-02, AV-07, AV-10 | Con menos de cuatro adeudos, una condición fallida de 6.1.2 bloquea con su motivo; no se deriva 6.1.6. |
| REG-06 a REG-10, CAR-02, OF-01 | RN-6.1 se evalúa sólo si la carga contiene una UD de quinto. |

## SQL C-20 y pendientes

El script C-20 se mantiene como `SELECT` agregado. Su ejecución productiva, la versión de MySQL para decidir `SUM() OVER`, y la validación de los cuatro consumidores con el mismo resultado GC-17 requieren ambiente autorizado. No se hicieron escrituras productivas.

## Pruebas

`basegestor`: `787` pruebas, `0` fallas, `0` errores, `0` omitidas.
