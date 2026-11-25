# Resultado: comunicados institucionales 2027-1

## Alcance

Implementación en modo sombra, sin escrituras en Inscripción, bajas, grupos, oferta ni calificaciones. Base revisada: `feature/desacople_motor_historico`, commit inicial `6a0d15b0`.

El Motor conserva las decisiones generales de RN-1.4, RN-6.4 y RN-6.2. El comunicado es un aviso trazable separado: no identifica personas beneficiarias, no autoriza una inscripción y no altera decisión, motivos, carga ni habilitación.

## Configuración

| Parámetro | Valor predeterminado | Configuración JVM | Vigencia/ámbito |
| --- | --- | --- | --- |
| Periodo del comunicado | `2027-1` | `V2_COMUNICADO_INSTITUCIONAL_PERIODO` | Solo coincide con el periodo normativo evaluado. |
| Texto | Comunicado institucional aprobado para consulta | `V2_COMUNICADO_INSTITUCIONAL_TEXTO` | `2027-1` por defecto. |
| Referencia | `Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v4, secciones A y C` | `V2_COMUNICADO_INSTITUCIONAL_REFERENCIA` | Trazabilidad del aviso. |
| Vigencia | `2027-1` | `V2_COMUNICADO_INSTITUCIONAL_VIGENCIA` | Conservada en el resultado V2. |

Texto emitido una sola vez por evaluación:

> Esta simulación aplica las reglas generales. Para el proceso de Inscripción puede aplicar autorizaciones institucionales específicas. La inscripción efectiva puede diferir del resultado que se muestra aquí.

## Contrato y consumidores

El resultado V2 expone `AvisoEvaluacionCurricularV2DTO` con código `AVISO_COMUNICADO_2027_1`, texto, referencia, periodo y vigencia. No se agrega a `motivos`, `restricciones` ni a una decisión por UD.

| Componente | Presentación | Fuente |
| --- | --- | --- |
| Motor | Marca el intento agotado con `AUTORIZACION_INSTITUCIONAL_POSIBLE_2027_1` solo durante `2027-1` y emite un aviso único cuando concurre RN-1.4 o RN-6.2 sin referencia. | `EvaluacionCurricularV2ServiceImpl`. |
| Simulador | Muestra el texto en Advertencias junto con el resultado, sin convertirlo en restricción. | `SimuladorCurricularV2ServiceImpl`. |
| Asistente de inscripción, Situación académica | Muestra el mismo texto como aviso informativo. | `TablaCurricularAsistidaBean` y `tablaCurricularAsistida.xhtml`. |
| Asistente virtual lateral | Muestra el mismo texto dentro del panel contextual. | `TablaCurricularAsistidaBean` y `tablaCurricularAsistida.xhtml`. |
| Mapa curricular | No consume el aviso; conserva únicamente la restricción general por UD. | Sin cambio en `MallaCurricularV2ResultadoAdapter`. |

## GC-19 sintético

| Variante | Entrada resumida | Salida comprobada |
| --- | --- | --- |
| a | `2027-1`, una UD con tres intentos reprobados. | `AGOTADA`, `BLOQUEADA`, `LIMITE_INTENTOS`, marcador de trazabilidad y un aviso. |
| b | `2027-1`, una UD con cuatro intentos reprobados. | Mantiene `AGOTADA`, `BLOQUEADA`, `LIMITE_INTENTOS`, `LIMITE_SUPERADO_HISTORIAL_PREVIO` y un aviso. |
| c | `2027-2`, mismo caso de tres fallidos. | Mantiene la restricción general, sin marcador ni aviso. |
| d | `2027-1`, admisión 2024, sin inscripción inicial ni referencia de convocatoria/resolución. | RN-6.2 queda en `REQUIERE_REVISION`, semestre de referencia `1`, sin alerta de inactividad de tres periodos y con un aviso. |
| e | `2027-1`, concurren límite de intentos y RN-6.2 sin referencia. | Un solo `AVISO_COMUNICADO_2027_1`. |
| f | `2027-1`, sin límite de intentos ni RN-6.2 aplicable. | Sin aviso. |
| g | Tres fallidos comparados entre `2027-1` y `2027-2`. | Misma habilitación, mismos motivos y misma carga; solo difiere el aviso de consulta. |
| adicional | Cuarto intento en curso después de tres fallidos, `2027-1`. | Conserva `AGOTADA` y `BLOQUEADA`; muestra el aviso y no infiere autorización. |

## Pruebas y compilación

| Módulo | Comando | Resultado |
| --- | --- | --- |
| Focalizadas basegestor | `mvn -q -Dtest=ComunicadosInstitucionales20271Test,SimuladorCurricularV2ServiceTest,Bloque5HistorialRn20261117Test test` | 33 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| Regresión basegestor | `mvn -q test` | 798 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| Regresión gestorWeb | `mvn -q test` | 39 pruebas, 0 fallas, 0 errores, 0 omitidas. |
| Compilación | `mvn -q -DskipTests compile` en ambos módulos | SUCCESS. |

## Límites y pendiente institucional

- El comunicado no registra, aprueba ni rechaza una inscripción. Esa determinación continúa siendo responsabilidad del proceso de Inscripción y DAEAE.
- No se implementó Q13 ni se realizan escrituras sobre expedientes.
- La validación runtime visual en WildFly queda pendiente de despliegue autorizado.

## Resultado

`APROBADO_TECNICAMENTE`: las restricciones generales se preservan, el comunicado es configurable y trazable, y los cuatro consumidores mantienen la separación entre información de consulta y decisión académica.
