# Inventario de reglas implementadas - Motor Curricular V2

## Corte

| Dato | Valor |
| --- | --- |
| Versión normativa | `RN-PE2024-2027-act-20261117` |
| Fecha de actualización | 2026-11-18 |
| Rama | `feature/desacople_motor_historico` |
| Ejecución | Sólo lectura y modo sombra; no crea inscripción, baja, grupo, oferta ni calificación. |
| Configuración | `ConfiguracionEvaluacionCurricularV2ServiceImpl` y `ParametrosCurricularesV2DTO`. |

## Estado por numeral

| Numeral | Estado | Comportamiento verificable | Prueba principal | Pregunta o dato pendiente |
| --- | --- | --- | --- | --- |
| RN-1.1 | Conforme | Identifica actividad, inactividad, baja definitiva y plazo como datos de trayectoria; no formaliza movimientos. | `EvaluacionCurricularV2Fase2ATest` | C1-a |
| RN-1.2 | Conforme | Cuenta adeudos vigentes por UD, no por intentos. | `Bloque5HistorialRn20261117Test` | Q6 para tratamiento alterno de cero sin baja |
| RN-1.3 | Conforme | Conserva no cursamiento y baja parcial fuera de reprobación. | `EvaluacionCurricularV2Fase3ATest` | C1-a |
| RN-1.4 | Pendiente de dato | Cuenta oportunidades con historial canónico y bajas aplicadas; sin evidencia queda en revisión. | `Bloque5HistorialRn20261117Test` | Q10-Q13 |
| RN-1.5 | Conforme | No permite reinscribir una UD acreditada para mejorar promedio. | `EvaluacionCurricularV2Fase2ETest` | Ninguna |
| RN-2.1 | Fuera de alcance | La confirmación material de inscripción pertenece al proceso de Inscripción. | `EvaluacionCurricularV2ServiceTest` | C2-a |
| RN-2.2 | Conforme | Evalúa en sombra antes de reinscripción sin sustituir la operación productiva. | `SimuladorCurricularV2ServiceTest` | Ninguna |
| RN-3.1 | Conforme | Valida nuevo ingreso: cuatro obligatorias y dos optativas distintas en B1/B2. | `EvaluacionCurricularV2Fase3BTest` | Q2 catálogo PE2027 real |
| RN-3.2 | Conforme | Admite hasta dos optativas nuevas desde segundo semestre; recursamiento no consume ese tope. | `EvaluacionCurricularV2Fase7ATest` | Q7 |
| RN-3.3 | Conforme | Evita repetición y más de una optativa nueva por bloque. | `EvaluacionCurricularV2Fase3BTest` | Q11 |
| RN-4.1 | Conforme | Calcula mínimo y excepciones sólo con UD elegibles. | `EvaluacionCurricularV2Fase3DTest` | Q4 |
| RN-4.2 | Conforme | Calcula máximo individual 8/6 según situación. | `EvaluacionCurricularV2Fase3DTest` | Q4 |
| RN-4.3 | Conforme | Consolida mínimo, máximo y descartes. | `EvaluacionCurricularV2Fase3DTest` | Q4 |
| RN-5.1 | Conforme | Identifica rezago leve. | `EvaluacionCurricularV2Fase4DTest` | Ninguna |
| RN-5.2 | Conforme | Identifica rezago severo y ruta de regularización. | `EvaluacionCurricularV2Fase4DTest` | Ninguna |
| RN-6.1 | Conforme | Evalúa candidatas de quinto en el periodo aplicable. | `EvaluacionCurricularV2Fase5ATest` | Q5 |
| RN-6.1.1 | Conforme | Construye base de obligatorias 1.º-4.º. | `EvaluacionCurricularV2Fase5ATest` | C1-a |
| RN-6.1.2 | Conforme | Valida condiciones comunes y pendiente omitida. | `EvaluacionCurricularV2Fase5BTest` | C1-a |
| RN-6.1.3 | Conforme | Aplica supuestos tipificados, incluida falta de oferta. | `EvaluacionCurricularV2Fase5CTest` | C1-a |
| RN-6.1.4 | Conforme | Distingue oferta, cupo y falta de oferta. | `EvaluacionCurricularV2Fase5DTest` | Q5 |
| RN-6.1.5 | Pendiente de dato | Considera seriación, RN-6.4 y medidas vigentes verificables. | `EvaluacionCurricularV2Fase5ETest` | Q10 |
| RN-6.1.6 | Pendiente de dato | Identifica ruta de resolución extraordinaria sin concederla. | `EvaluacionCurricularV2Fase5ETest` | Q10 |
| RN-6.2 | Pendiente de dato | Incorporación diferida exige referencia de convocatoria/resolución. | `EvaluacionCurricularV2Fase6ATest` | C2-a |
| RN-6.3 | Conforme | Prioriza reprobadas y separa oferta/cupo/seriación. | `EvaluacionCurricularV2Fase4ATest` | Ninguna |
| RN-6.4 | Conforme | Cuatro o más adeudos vigentes restringen a regularización. | `EvaluacionCurricularV2Fase4DTest` | Q6 |
| RN-6.5 | Pendiente de dato | Identifica periodos sin reinscripción sin conceder reincorporación. | `EvaluacionCurricularV2Fase6BTest` | C1-a |
| RN-6.6 | Pendiente de dato | Trata baja temporal acumulada y reincorporación como hechos distintos. | `EvaluacionCurricularV2Fase6DTest` | Q10-Q13 |
| RN-6.7 | Pendiente de dato | Conserva causa de baja parcial y prioriza recuperación cuando procede. | `EvaluacionCurricularV2Fase6ETest` | Q11 |
| RN-6.8 | Conforme | Proyecta trayectoria tras regularización sólo en sombra. | `EvaluacionCurricularV2Fase6ACierreTest` | Ninguna |
| RN-6.9 | Pendiente de dato | Calcula semestre de referencia por periodos, excluyendo baja temporal aplicada. | `SemestreReferenciaV2ServiceTest` | C1-a |
| RN-7.1 | Conforme | Evalúa optativas requeridas, adicionales y prioridad. | `EvaluacionCurricularV2Fase7ATest` | Q2, Q7 |
| RN-7.1.1 | Pendiente de dato | Catálogo de 13 optativas parametrizado por ámbito/vigencia. | `PreparacionDecisionesPendientesV2Test` | Q2, Q11 |
| RN-7.2 | Conforme | Principio general de optativas reprobadas; no emite ELECTIVA. | `EvaluacionCurricularV2Fase7BTest` | Ninguna |
| RN-7.3 | Fuera de alcance | No aplica: numeral retirado; no se emite como regla vigente. | `EvaluacionCurricularV2Fase7BTest` | Ninguna |
| RN-8 | Conforme | Evalúa relaciones directas e indirectas. | `EvaluacionCurricularV2Fase8DTest` | C1-a |
| RN-8.1 | Conforme | Interbloque: antecedente acreditada, en curso o pendiente de confirmación. | `PreparacionDecisionesPendientesV2Test` | Cierre institucional B1 real |
| RN-8.2 | Conforme | Seriación intersemestral. | `EvaluacionCurricularV2Fase8BTest` | C1-a |
| RN-8.3 | Conforme | Seriación interanual. | `EvaluacionCurricularV2Fase8CTest` | C1-a |
| RN-9.1 | Pendiente de dato | Reinscripción extemporánea descriptiva, sin dispensa. | `EvaluacionCurricularV2Fase9ATest` | Q10 |
| RN-9.2 | Fuera de alcance | Sin fuente/regla ejecutable identificada. | No aplica | C2-a |
| RN-9.3 | Pendiente de dato | Últimos semestres según estructura y componentes verificables. | `EvaluacionCurricularV2Fase9BTest` | C2-a |
| RN-9.4 | Pendiente de dato | Separa cumplimiento curricular de adeudo adicional. | `EvaluacionCurricularV2Fase9CTest` | Q11 |
| RN-9.5 | Conforme | Traza ámbito, parámetros, vigencia y decisiones. | `TrazabilidadEvaluacionCurricularV2ServiceTest` | Ninguna |
| RN-10 / 10.1 | Fuera de alcance | Titulación y servicio social requieren fuente y módulo responsable. | No aplica | C2-a |

## Decisiones preparadas por configuración

| Pregunta | Propiedad JVM | Predeterminado vigente | Alternativas probadas | Documento/vigencia |
| --- | --- | --- | --- | --- |
| Q6 cero sin baja | `V2_Q6_CERO_SIN_BAJA` | `REPROBACION` | `EXCLUIR_PERIODO_COMPLETO_EN_CERO`, `REVISION` | Decisiones v3 / 2026-11-17 |
| Q5 recursamiento de quinto | `V2_Q5_RECURSAMIENTO_QUINTO` | `AVANCE_CONDICIONADO` | `REGULARIZACION` | Decisiones v3 / 2026-11-17 |
| Q7 optativas faltantes 5.º-8.º | `V2_Q7_OPTATIVAS_FALTANTES_5_A_8` | `BLOQUEAR` | `PERMITIR_TRAS_PENDIENTES` | Decisiones v3 / 2026-11-17 |
| Q11 bloque de baja/sustituta | `V2_Q11_BLOQUE_OPTATIVA_BAJA` | `AMBOS_BLOQUES` | `BLOQUE_ORIGINAL` | Decisiones v3 / 2026-11-17 |
| Q4 B2 seriada en carga | `V2_Q4_UD_B2_SERIADA_CARGA` | `NO_CUENTA` | `SOLO_MAX`, `CUENTA_MIN_Y_MAX` | Decisiones v3 / 2026-11-17 |

Los parámetros incluyen ámbito `PE2024/2027`, documento de respaldo y vigencia en `ParametrosCurricularesV2DTO`. La carga de una opción alternativa no abandona modo sombra.
