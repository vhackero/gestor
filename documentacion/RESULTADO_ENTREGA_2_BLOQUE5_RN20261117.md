# Entrega 2 - Bloque 5 RN act. 2026-11-17

## Alcance y resguardo

La implementación se mantiene en modo sombra: `efectosProductivosAplicados=false`. No se escribieron registros de inscripción, bajas, grupos, oferta ni calificaciones. Las pruebas usan solamente contextos sintéticos.

## Control de la matriz CAMPUS 2

| Verificación | Resultado |
| --- | --- |
| Archivo versionado | `CAMPUS 2- Casos de prueba_ 2027-1_20261111.xlsx` |
| SHA-256 | `74223e8add9b26cbafa811f275ada445b975ed48067a1fc25ec4b189914fb235` |
| Revisión de todas las celdas XML | Sin coincidencias de `es` seguido de dígitos, `password`, `passwd`, `contraseña`, `usuario` ni etiquetas de credenciales. |
| Copia anterior | La ruta no aparece en commits ni stashes. La copia raíz se sustituyó por la revisada. No se creó ningún archivo temporal versionado; la verificación no permite afirmar la inexistencia de copias externas al repositorio. |

## Sección 3 - verificación y corrección

| Regla | Estado | Implementación y prueba |
| --- | --- | --- |
| 6.9, semestre de referencia | Implementado | `SemestreReferenciaV2ServiceImpl` cuenta un periodo con participación formal, consolida B1/B2 y excluye únicamente baja temporal completa tipos 2, 7 y 8. `Bloque1FundamentosRn20261105Test`. |
| 8.1, seriación interbloque | Implementado en sombra | `EvaluacionCurricularV2ServiceImpl` propaga `INSCRITA`, `PENDIENTE_POR_SERIACION` o `PENDIENTE_DE_CONFIRMACION` a la decisión por UD; conserva preselección no desmarcable sin ejecutar cierre, alta, baja, NP u oportunidad. `EvaluacionCurricularV2Fase8ATest#gc18ExponeEstadoOperativoInterbloqueSinEjecutarCierres`. |
| 6.1, 6.3 y 6.4 | Implementado en sombra | C-19 usa adeudos distintos y C-18 conserva resultados reprobatorios para el orden de preselección. RN-6.1 se sigue evaluando por UD de quinto propuesta. `Bloque5HistorialRn20261117Test`. El recursamiento de quinto posterior al acceso previo queda sujeto a la definición institucional ya documentada. |
| 6.7, recuperación tras baja parcial | Parcial | La fuente identifica baja aplicada y su UD; la prioridad respeta oferta. El subtipo se conserva sin inferir cuando falta una causa verificable. La cobertura exhaustiva de incisos por subtipo sigue pendiente del catálogo institucional completo. |
| Consistencia de cuatro consumidores | Pendiente de runtime | El contrato común ahora expone estado de RN-8.1, intentos y adeudos. Falta ejecutar GC-17 en Malla, Asistente, Simulador y Asistente virtual contra WAR desplegado. |

### GC-18 - resultado por estado RN-8.1

| Variante | Estado expuesto | Efecto del motor |
| --- | --- | --- |
| Antecedente acreditada | `INSCRITA` | La consecuente puede presentarse como inscrita; el motor no formaliza el movimiento. |
| Antecedente B1 en curso | `PENDIENTE_POR_SERIACION` | Consecuente preseleccionada y no desmarcable en sombra. |
| Antecedente sin resultado definitivo al inicio B2 | `PENDIENTE_DE_CONFIRMACION` | Consecuente fuera de la carga hasta confirmación. |
| Cierre de B1 con antecedente no acreditada | Lectura pendiente de Inscripción | El motor no ejecuta el cierre. Cuando Inscripción lo reporte cerrado, el adaptador debe excluirlo de C-18, C-19 y 6.9. |
| Consecuente ya inscrita formalmente | Sin baja automática | El motor conserva la inscripción leída y no produce un movimiento escolar. |

## C-18, C-19 y GC-17

| Caso | Resultado validado |
| --- | --- |
| GC-17 | Adeudos se cuentan por UD distinta, sin duplicar el recursamiento; tres adeudos vigentes no activan 6.4. |
| GC-17b | F y F' con tres o más fallidos históricos resultan `AGOTADA`, `BLOQUEADA` y con `PENDIENTE_DISPOSICION_TRANSITORIA`; el mensaje orienta a DAEAE y no produce baja automática. |
| GC-17c | Un intento no conciliado queda en revisión y, si puede modificar el umbral, se emite `UMBRAL_64_EN_REVISION`. |
| GC-17d sin relación | No se inventa sustitución: oportunidades y cómputo acumulado quedan en revisión. |
| Baja sin `contabilizar=1` | Queda en revisión, sin sumar ni excluir el intento. |
| Evento sin periodo | Queda en revisión por identidad de inscripción. |
| Participaciones repetidas UD/periodo | El repositorio consolida una fila por persona + UD + periodo. |

La relación de sustitución positiva requiere el registro institucional de correspondencia; sin esa fuente la prueba de integración no puede afirmar la secuencia ni ubicar el único adeudo en la sustituta. El contrato conserva la protección: la ausencia de relación genera revisión y no duplica adeudos.

## Sección 5 - matriz CAMPUS 2

| IDs | Prueba sintética del motor | Resultado conforme a RN |
| --- | --- | --- |
| AV-01 a AV-10, REG-04 a REG-10, CAR-01, CAR-02, OF-01 | `Bloque1FundamentosRn20261105Test`, `Bloque3OfertaYPreseleccionRn20261106Test` | RN-6.1 se activa por UD de quinto propuesta, no por semestre de referencia. |
| SER-01 y SER-02 | `EvaluacionCurricularV2Fase8ATest`, `EvaluacionCurricularV2Fase8DTest`, GC-18 | Seriación directa e indirecta; RN-8.1 expone los tres estados. |
| REI-01, REI-02 y TR-01 | `SemestreReferenciaV2ServiceImpl` y `Bloque1FundamentosRn20261105Test` | Trayectoria de lectura; una baja temporal completa excluye el periodo. |
| OPT-01 | `Bloque3OfertaYPreseleccionRn20261106Test` | Catálogo de optativas vigente; no hay categoría ELECTIVA. |
| EXT-01 | Contrato C-18 | Extraordinario histórico queda en revisión y no suma reprobación ni oportunidad. |
| AMB-01 y OM-01 | `Bloque2HistorialOportunidadesRn20261106Test`, GC-17c | Dato ambiguo u omitido produce `REQUIERE_REVISION`, no una inscripción inferida. |

## C-20

El script [C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql](C20_HISTORIAL_RN20261117_SOLO_LECTURA.sql) contiene únicamente `SELECT`; devuelve sólo agregados por PE y periodo, incorpora la fecha de corte y consolida el grano persona + UD + periodo antes de calcular límites. Debe ejecutarlo una cuenta autorizada; no se ejecutó contra datos reales en esta entrega.

## Sección 6 - dudas técnicas cerradas en repositorio

| Duda | Evidencia | Conclusión |
| --- | --- | --- |
| `contabilizar` | `NuevaBajaServiceImpl` asigna `1` al aplicar la baja; repositorios operativos filtran `contabilizar=1`. | Se trata como baja aplicada/autorizada para el cómputo; un registro sin ese valor queda en revisión. |
| Inscripción formal | El historial usa `rel_grupo_participante → tbl_grupos → tbl_eventos → tbl_ficha_descriptiva_programa`. | La participación en grupo es la evidencia disponible de inscripción formal; preselección sin participación no cuenta. |
| Estados de 8.1 | No se localizó fuente de Inscripción que persista separadamente pendiente, confirmación y cierre. | El motor expone estados de consulta; el cierre real sigue pendiente de la fuente de Inscripción. |
| Tipos temporales 2, 7 y 8 | Glosario RN y parámetros ya los clasifican como baja temporal de semestre completo. | Los tres se tratan igual para 6.9 y acumulado; no queda duda técnica. |
| Seriación | La estructura curricular ya transporta relaciones directas; el motor deriva indirectas por transitividad. | Para los PE2024 aplica cadena lineal; no requiere una nueva consulta a CAI. |

## Entregables y pendiente operativo

- Pruebas focalizadas: `42` ejecutadas, `0` fallas, `0` errores, `0` omitidas.
- Regresión completa: `basegestor` `785/0/0/0`; `gestorWeb` `38/0/0/0`.
- Despliegue/validación runtime: pendiente. Requiere que una persona autorizada despliegue el WAR desde `d391a42f` hasta el commit final y ejecute GC-17, GC-18 y A/B/C anonimizados. No se hicieron escrituras productivas.
