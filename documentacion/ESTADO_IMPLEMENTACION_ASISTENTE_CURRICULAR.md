# Estado de implementación del Asistente curricular

## Base revisada

- Rama: `feature/desacople_motor_historico`.
- Base funcional: Motor Curricular V2 y el Asistente de inscripción existentes.
- Guía aplicada: `Guia_de_desarrollo_Codex_Asistente_curricular.docx` (archivo local no rastreado).
- Alcance: consulta y evaluación hipotética en sombra. No formaliza inscripción ni modifica historial, oferta, oportunidades o bajas.

## Avance por pestaña

| Pestaña | Implementado | Pendiente para cumplir la guía |
| --- | --- | --- |
| Situación académica | Indicadores curriculares, estados de fuentes, corte expuesto, avance, temporalidad, resumen actual/global y vínculo a pendientes. Consume hechos V2 y conserva datos registrados. | Validación visual controlada en WildFly para fuentes completas, parcial, sin oferta, error V2 y cortes desalineados; evidencia de escritorio y móvil. |
| UD prioritarias | Seis bloques visibles: reprobadas, oportunidades, recursamiento, sustituciones, bajas parciales y bajas temporales. Las reprobadas y el recursamiento usan identidad de UD (`idPrograma` o clave curricular verificable), no nombre. | Integrar fuente institucional de oportunidades computables; relación de caso original/sustitutas; autorización/aplicación de baja parcial; autorización, vigencia y reincorporación de baja temporal. Mientras faltan, la pantalla debe conservar cada dato verificable y marcar el campo como parcial, no como cero definitivo. Falta completar la fidelidad visual de las seis tablas al boceto y su validación en móvil. |
| Expectativa de logro | La ruta del Asistente construye una copia profunda, evalúa con adaptador y Motor V2, conserva la referencia registrada y genera escenarios de sólo lectura. La identificación de UD en curso usa ID o clave curricular verificable. | Controles por cada UD en curso (`Acredito`/`No acredito`), restablecer, conservación de sesión por estudiante/plan/periodo/versión, periodo simulado separado del periodo de elegibilidad, publicación de la última solicitud y tabla de diferencias. También faltan pruebas de recorrido para candidata de quinto, créditos de recursamiento, adeudos ajenos, fuente parcial y orden alterno de escenarios. |
| Proyección de carga académica | Existe presentación legacy, pero no consume todavía una base registrada o un escenario V2 identificado. | No debe integrarse hasta cerrar Expectativa. Requiere generador determinista que construya sólo alternativas válidas y distintas, valida oportunidades, seriación, oferta, bloque, mínimos/máximos y RN-6.4; debe transferir al Simulador sólo un identificador de escenario vigente, con revalidación de ventana, oferta, versión y límites. |

## Correcciones incorporadas en esta iteración

1. La construcción de Expectativa dejó de relacionar una UD en curso por nombre. Usa `InscripcionPreviaMateriasDTO.idPrograma` y, si no está disponible, `clavePrograma` frente a la clave curricular de la UD.
2. El escenario ahora conserva `reprobadasVigentes` calculadas por el Motor V2; no se infiere desde contadores de interfaz.
3. Las pruebas recorren `ContextoEvaluacionCurricularV2AdapterImpl` y `EvaluacionCurricularV2ServiceImpl` reales para comprobar las transiciones 3 a 4 y 4 a 3 reprobadas. La segunda no presenta recuperación automática de regularidad cuando persisten adeudos.

## Dependencias concretas

- **Oportunidades RN-1.4:** detalle de cursamientos computables, causa de NP/baja autorizada y marca de cursamiento en curso. Sin ello sólo se muestra `Reprobaciones registradas`, no oportunidades consumidas, restantes o agotadas.
- **Sustituciones de adicionales:** relación estable de caso de adeudo entre UD original y sustitutas. Sin ella no se puede consolidar el caso ni reiniciar o descontar oportunidades.
- **Bajas parciales:** llave de movimiento más evidencia de autorización y aplicación. Una UD afectada y una solicitud son unidades de conteo distintas.
- **Bajas temporales:** resolución/autorización, periodos cubiertos, vigencia y reincorporación. Los registros detectados permanecen informativos y no alteran regularidad, plazo ni oportunidades.
- **Expectativa y Proyección:** periodo objetivo, oferta futura confirmada y universo de UD candidatas. La oferta del periodo actual no se presenta como confirmación futura.
- **Proyección:** fuente de ventana operativa del Simulador para habilitar el traslado; el botón nunca confirma inscripción.

## Criterio para concluir las tres pestañas

UD prioritarias y Expectativa podrán cerrarse técnicamente cuando sus contratos distingan `DISPONIBLE`, `VACIO`, `PARCIAL`, `DESACTUALIZADO` y `ERROR`, las pruebas de recorrido acordadas estén verdes y exista validación visual. Proyección sólo podrá iniciarse después de que Expectativa entregue un identificador de escenario vigente con sus supuestos, causas, corte y versión.
