# Inventario de reglas implementadas - Motor Curricular V2

## Corte

| Dato | Valor |
| --- | --- |
| Versión normativa | `RN-PE2024-2027-act-20261119` |
| Fecha de actualización | 2026-11-19 |
| Rama | `feature/desacople_motor_historico` |
| Ejecución | Sólo lectura y modo sombra; no crea inscripción, baja, grupo, oferta ni calificación. |
| Configuración | `ConfiguracionEvaluacionCurricularV2ServiceImpl` y `ParametrosCurricularesV2DTO`. |

## Estado por numeral

| Numeral | Estado | Comportamiento verificable | Prueba principal | Pregunta o dato pendiente |
| --- | --- | --- | --- | --- |
| RN-1.1 | Conforme | Identifica actividad, inactividad, baja definitiva y plazo como datos de trayectoria; no formaliza movimientos. | `EvaluacionCurricularV2Fase2ATest` | Ninguna |
| RN-1.2 | Conforme | Cuenta adeudos vigentes por UD, no por intentos. | `Bloque5HistorialRn20261117Test` | Q6 |
| RN-1.3 | Conforme | Conserva no cursamiento y baja parcial fuera de reprobación. | `EvaluacionCurricularV2Fase3ATest` | Ninguna |
| RN-1.4 | Conforme | Cuenta oportunidades desde la primera inscripción comprobada, separa la inscripción en curso y expone recursamientos por separado; bajas aplicadas no consumen oportunidad. | `PreparacionDecisionesPendientesV2Test` | C1-a, Q13 |
| RN-1.5 | Conforme | No permite reinscribir una UD acreditada para mejorar promedio. | `EvaluacionCurricularV2Fase2ETest` | Ninguna |
| RN-2.1 | Fuera de alcance | La confirmación material de inscripción pertenece al proceso de Inscripción. | `EvaluacionCurricularV2ServiceTest` | Ninguna |
| RN-2.2 | Conforme | Cruza calificación, acta y baja aplicada: 0 definitivo sin baja es reprobación; NULL con acta abierta queda en curso y con acta cerrada queda en revisión. | `Bloque2HistorialOportunidadesRn20261106Test` | Ninguna |
| RN-2.3 | Pendiente de dato | Lee el contrato de excepción institucional parametrizable sin eliminar restricciones detectadas. | `PreparacionDecisionesPendientesV2Test` | Fuente SIGIE y flujo de administración de excepciones |
| RN-3.1 | Conforme | Valida nuevo ingreso: cuatro obligatorias y dos optativas distintas en B1/B2. | `EvaluacionCurricularV2Fase3BTest` | Q2 |
| RN-3.2 | Conforme | Admite hasta dos optativas nuevas desde segundo semestre; recursamiento no consume ese tope. | `EvaluacionCurricularV2Fase7ATest` | Ninguna |
| RN-3.3 | Conforme | Evita repetición y más de una optativa nueva por bloque. | `EvaluacionCurricularV2Fase3BTest` | Q11 |
| RN-4.1 | Conforme | Calcula mínimo y excepciones sólo con UD elegibles. | `EvaluacionCurricularV2Fase3DTest` | Q4, sólo mínimo |
| RN-4.2 / RN-4.3 | Conforme | La UD pendiente por seriación cuenta una vez en mínimo y máximo de la carga inicial. | `PreparacionDecisionesPendientesV2Test` | Fecha institucional de ajuste B2 |
| RN-5.1 / RN-5.2 | Conforme | Identifica rezago leve, severo y ruta de regularización. | `EvaluacionCurricularV2Fase4DTest` | Ninguna |
| RN-6.1 | Conforme | Evalúa candidatas de quinto en el periodo aplicable; recursamiento de quinto se trata como regularización incluso con RN-6.4. | `PreparacionDecisionesPendientesV2Test` | Ninguna |
| RN-6.1.1 / RN-6.1.2 / RN-6.1.3 | Conforme | Construye la base, valida condiciones comunes y aplica supuestos tipificados. | `EvaluacionCurricularV2Fase5ATest` | Ninguna |
| RN-6.1.4 | Conforme | Distingue oferta, cupo y falta de oferta. | `EvaluacionCurricularV2Fase5DTest` | Ninguna |
| RN-6.1.5 | Conforme con dato pendiente | Considera seriación, RN-6.4 y medidas vigentes verificables. | `EvaluacionCurricularV2Fase5ETest` | Q10 |
| RN-6.1.6 | Pendiente de dato | Identifica ruta de resolución extraordinaria sin concederla. | `EvaluacionCurricularV2Fase5ETest` | Ninguna; ticket GLPi externo |
| RN-6.2 | Pendiente de dato | Incorporación diferida exige referencia de convocatoria/resolución. | `EvaluacionCurricularV2Fase6ATest` | C2-a |
| RN-6.3 | Conforme | Prioriza reprobadas y separa oferta/cupo/seriación. | `EvaluacionCurricularV2Fase4ATest` | Ninguna |
| RN-6.4 | Conforme | Cuatro o más adeudos vigentes restringen a regularización. | `EvaluacionCurricularV2Fase4DTest` | Q6 |
| RN-6.5 / RN-6.6 | Conforme | Identifica inactividad y baja temporal acumulada sin conceder reincorporación. | `EvaluacionCurricularV2Fase6DTest` | Ninguna |
| RN-6.7 | Conforme | Conserva causa de baja parcial y prioriza recuperación cuando procede. El subtipo de baja por seriación de una UD formalmente inscrita, sin motivo inequívoco en catálogo, sólo limita la trazabilidad; no cambia la decisión. | `EvaluacionCurricularV2Fase6ETest` | Q11, bloque de reinscripción |
| RN-6.8 | Conforme | Proyecta trayectoria tras regularización sólo en sombra. | `EvaluacionCurricularV2Fase6ACierreTest` | Ninguna |
| RN-6.9 | Conforme | Calcula semestre de referencia por periodos, excluyendo baja temporal aplicada. | `SemestreReferenciaV2ServiceTest` | Q3, cierres de RN-8.1 |
| RN-7.1 | Conforme | Desde quinto permite optativas requeridas faltantes después de regularización con 1 a 3 adeudos; el bloque depende de la oferta autorizada. | `Bloque3OfertaYPreseleccionRn20261106Test` | Q2 |
| RN-7.1.1 | Pendiente de dato | Conserva el contrato de adeudo trasladado; la relación real de sustituciones aún requiere fuente. | `PreparacionDecisionesPendientesV2Test` | Q12 |
| RN-7.2 | Conforme | Principio general de optativas reprobadas; no emite ELECTIVA. | `EvaluacionCurricularV2Fase7BTest` | Ninguna |
| RN-7.3 | No aplica (retirada) | No se emite como regla vigente. | `EvaluacionCurricularV2Fase7BTest` | Ninguna |
| RN-8 / RN-8.2 / RN-8.3 | Conforme | Evalúa relaciones directas, intersemestrales e interanuales. | `EvaluacionCurricularV2Fase8DTest` | Q2, seriación de Seminarios y PE2027 |
| RN-8.1 | Conforme con dato pendiente | Interbloque: antecedente acreditada da `INSCRITA`; antecedente en curso o en revisión deja `PENDIENTE_POR_SERIACION`, fuera de carga confirmada. | `EvaluacionCurricularV2Fase8ATest` | Fecha institucional de confirmación o ajuste B2 |
| RN-9.1 | Pendiente de dato | Reinscripción extemporánea descriptiva, sin dispensa. | `EvaluacionCurricularV2Fase9ATest` | Ninguna; requiere registro extemporáneo de Inscripción |
| RN-9.2 | Fuera de alcance | Sin fuente/regla ejecutable identificada. | No aplica | Ninguna |
| RN-9.3 | Conforme | Últimos semestres según estructura y componentes verificables. | `EvaluacionCurricularV2Fase9BTest` | Ninguna |
| RN-9.4 | Conforme | Separa cumplimiento curricular de adeudo adicional. | `EvaluacionCurricularV2Fase9CTest` | Ninguna; presentación en certificado fuera de alcance |
| RN-9.5 | Conforme | Traza ámbito, parámetros, vigencia y decisiones. | `TrazabilidadEvaluacionCurricularV2ServiceTest` | Ninguna |
| RN-10 / RN-10.1 | Fuera de alcance | Titulación y servicio social requieren fuente y módulo responsable. | No aplica | Ninguna |

## Decisiones institucionales cerradas por RN 20261119

| Pregunta | Propiedad JVM | Predeterminado vigente | Alternativas probadas | Documento/vigencia |
| --- | --- | --- | --- | --- |
| RN-2.2 cero sin baja | `V2_Q6_CERO_SIN_BAJA` | `REPROBACION` | Retiradas por RN | `P2024-2027- Reglas de negocio_Octubre 2026_20261119_0055.docx` / 2026-11-19 |
| RN-6.1.5 recursamiento de quinto | `V2_Q5_RECURSAMIENTO_QUINTO` | `REGULARIZACION` | Retiradas por RN | Documento rector / 2026-11-19 |
| RN-6.1.5 con RN-6.4 | `V2_Q5_RECURSAMIENTO_QUINTO_CON_64` | `REGULARIZACION_64` | Retiradas por RN | Documento rector / 2026-11-19 |
| RN-7.1 optativas faltantes 5.º-8.º | `V2_Q7_OPTATIVAS_FALTANTES_5_A_8` | `PERMITIR_TRAS_PENDIENTES` | Retiradas por RN | Documento rector / 2026-11-19 |
| RN-7.1 bloque de baja/sustituta | `V2_Q11_BLOQUE_OPTATIVA_BAJA` | `OFERTA_AUTORIZADA` | Retiradas por RN | Documento rector / 2026-11-19 |
| RN-4.3 B2 seriada en carga | `V2_Q4_UD_B2_SERIADA_CARGA` | `CUENTA_MIN_Y_MAX` | `NO_CUENTA` retirado | Documento rector / 2026-11-19 |

Los parámetros incluyen ámbito `PE2024/2027`, documento de respaldo y vigencia en `ParametrosCurricularesV2DTO`. La carga de una opción alternativa no abandona modo sombra.

`NO_CUENTA` queda retirado: RN-4.2 obliga a que una UD B2 pendiente por seriación ocupe lugar en la carga máxima. Una propiedad histórica con ese valor se normaliza a `SOLO_MAX`; Q4 sólo permite decidir si también aporta al mínimo.

## Comunicados 2027-1

| Elemento | Fuente / parámetro | Alcance |
| --- | --- | --- |
| Aviso | `AVISO_COMUNICADO_2027_1` | Mensaje institucional visible sólo para el periodo configurado. |
| Marcador | `AUTORIZACION_INSTITUCIONAL_POSIBLE_2027_1` | Trazabilidad de que puede existir autorización externa; no concede ni registra una autorización. |
| Periodo | `V2_COMUNICADO_INSTITUCIONAL_PERIODO` | Predeterminado `2027-1`. |
| Texto | `V2_COMUNICADO_INSTITUCIONAL_TEXTO` | Comunicado visible en resultados de consulta. |
| Referencia | `V2_COMUNICADO_INSTITUCIONAL_REFERENCIA` | `Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v5, secciones A y C`. |
| Vigencia | `V2_COMUNICADO_INSTITUCIONAL_VIGENCIA` | Predeterminado `2027-1`. |

## Q8: evidencia de `contabilizar`

`NuevaBajaServiceImpl.aplicarBaja(...)` calcula `contabilizar=1` cuando la baja definitiva o sin asignaturas se aplica, cuando existe evento o inscripción afectada, o al actualizar una baja con datos completos de plan, UD, evento y grupo. El valor se persiste mediante `NuevaBajaRepository.insertarBaja(...)` o `actualizarBaja(...)`; la única llamada web localizada entra por `NuevaBajaUsuarioBean` y el servicio. No se localizó otro flujo de aplicación que asigne directamente ese campo.

Los registros con `contabilizar` distinto de `1` no se transforman en baja aplicada: el adaptador los conserva como historial en revisión. Consulta agregada de corte 2026-11-18 en la base de pruebas: `3,399` registros con valor `1`, `6` con valor `0` y ninguno nulo.
