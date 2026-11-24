# Catálogo de casos - Motor Curricular V2

## Corte y criterio

| Dato | Valor |
|---|---|
| RN | `RN-PE2024-2027-act-20261119` |
| Base de código | `4570fdab` + ajustes 7-bis pendientes de commit |
| Ejecución | Sólo lectura y modo sombra; no crea ni modifica inscripción, bajas, grupos, oferta o calificaciones. |
| Convención | Cada fila enumera las variantes **P**ositiva, **N**egativa, **L**ímite, **D**ato faltante e **I**nteracción de su numeral. El resultado siempre conserva las conclusiones que sí pueden comprobarse. |

`Dec.` significa decisión por UD; `Opp.` oportunidades; `Ade.` adeudos vigentes; `8.1` estado interbloque; `Nota` aviso institucional. Las pruebas son sintéticas salvo que se indique fuente. **Lógica** identifica cobertura del motor; **validación real** indica si existe un expediente o fuente institucional utilizable. Un estado bloqueado nunca se sustituye por una inferencia.

## Matriz por variante

Cada familia anterior se desglosa en estas cinco filas operativas; la prueba indicada en la familia cubre la variante salvo que se anote `No cubierto`. `D` siempre conserva conclusiones comprobables y agrega revisión.

| ID | Numeral | Tipo | Precondición | Entrada | Resultado esperado (decisión, motivo, oportunidades, adeudos, estado 8.1, nota) | Componentes donde se verifica | Prueba (clase#método) | Lógica | Validación con datos reales |
|---|---|---|---|---|---|---|---|---|---|
| VAR-12-P | 1.2 | P | Adeudo confirmado | Una UD reprobada | Ade.=1; Opp. individual; decisión prioritaria | Todos | `Bloque5HistorialRn20261117Test#gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso` | Cubierto | Disponible |
| VAR-12-N | 1.2 | N | Acreditación posterior | Reprobada y luego acreditada | Ade.=0; sin pendiente | Todos | `Bloque5HistorialRn20261117Test#gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso` | Cubierto | Disponible |
| VAR-12-L | 1.2 | L | Umbral | Cuatro adeudos | 6.4; sólo regularización | Todos | `Bloque6Rn20261119Test#rn22CuatroAdeudosConfirmadosNoSeAnulanPorUnResultadoEnRevision` | Cubierto | Disponible |
| VAR-12-D | 1.2 | D | Evidencia inconsistente | 3 confirmados + 1 incierto | 3 vigentes/1 revisión; `UMBRAL_64_EN_REVISION`; RN-6.4 no se aplica | Todos | `Bloque5HistorialRn20261117Test#gc17cMantieneEnRevisionElUmbralCuandoPuedeCambiar` | Cubierto | Disponible |
| VAR-12-D2 | 1.2 | D | Umbral confirmado | 4 confirmados | 4 vigentes; RN-6.4 sí se aplica | Todos | `Bloque5HistorialRn20261117Test#cuatroAdeudosConfirmadosAplicaRn64SinConfundirRevision` | Cubierto | Disponible |
| VAR-12-I | 1.2 | I | Historial repetido | Recursa en curso | Un adeudo, no dos; resultados informativos separados | Todos | `Bloque5HistorialRn20261117Test#gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso` | Cubierto | Disponible |
| VAR-22-P | 2.2 | P | Acta abierta | NULL | EN_CURSO; Opp. en curso | Todos | `Bloque2HistorialOportunidadesRn20261106Test#nuloCerradoRequiereRevisionYNuloVigenteEstaEnCurso` | Cubierto | Bloqueado por fuente: cierre real |
| VAR-22-N | 2.2 | N | Acta cerrada | NULL | UD/adeudo en revisión, no EN_CURSO | Todos | `Bloque2HistorialOportunidadesRn20261106Test#nuloCerradoRequiereRevisionYNuloVigenteEstaEnCurso` | Cubierto | Bloqueado por fuente: cierre real |
| VAR-22-L | 2.2 | L | Tercera | 2 fallidos + NULL | Rango de Opp.; sin agotamiento inferido | Todos | `PreparacionDecisionesPendientesV2Test` | Cubierto | Bloqueado por fuente: cierre real |
| VAR-22-D | 2.2 | D | Baja no aplicada | NULL/0 con baja sin contabilizar | Revisión; no consume | Todos | `Bloque2HistorialOportunidadesRn20261106Test#bajaSinContabilizarQuedaEnRevision` | Cubierto | Disponible |
| VAR-22-I | 2.2 | I | Interbloque | Antecedente en revisión | Consecuente `PENDIENTE_POR_SERIACION`, fuera de carga confirmada | Malla, Simulador | `PreparacionDecisionesPendientesV2Test` | Cubierto | Bloqueado por fuente: cierre real |
| VAR-61-P | 6.1 | P | Base acreditada | Candidata quinto | HABILITADA ordinaria si seriación/oferta/carga cumplen | Todos | `EvaluacionCurricularV2Fase5ATest#f5AU08BaseCompletaDaAccesoOrdinarioSombra` | Cubierto | Disponible |
| VAR-61-N | 6.1 | N | Pendiente sin supuesto | Candidata quinto | BLOQUEADA con motivo de condición no satisfecha | Todos | `Bloque3OfertaYPreseleccionRn20261106Test#c10IncluyeNuncaInscritaYBajaParcialEnOmitidasDeQuinto` | Cubierto | Disponible |
| VAR-61-L | 6.1 | L | Referencia 5 | Una candidata quinto | Evalúa sólo esa candidata, no otras UD | Todos | `EvaluacionCurricularV2Fase5ATest#f5AU02DesdeVigenciaEnQuintoAplica` | Cubierto | Disponible |
| VAR-61-D | 6.1 | D | Referencia incierta | Sin periodo verificable | REQUIERE_REVISION | Todos | `EvaluacionCurricularV2Fase6BTest#f6BU04DatosTrayectoriaOCausaIncompletosRequierenRevision` | Cubierto | Disponible |
| VAR-61-I | 6.1 | I | 4 adeudos/recursa quinto | Candidata reprobada | PRIORITARIA de regularización; 6.4 prevalece | Todos | `Bloque3OfertaYPreseleccionRn20261106Test#q5ReprobadaDeQuintoConCuatroAdeudosReportaDecisionVigente` | Cubierto | Disponible |
| VAR-62-P | 6.2 | P | Resolución válida | Incorporación diferida | Identifica incorporación sin inferir inactividad | Todos | `EvaluacionCurricularV2Fase6ATest` | Cubierto | Bloqueado por fuente: resolución |
| VAR-62-N | 6.2 | N | Sin resolución | Incorporación alegada | REQUIERE_REVISION | Todos | `ComunicadosInstitucionales20271Test#gc19dIncorporacionDiferidaSinReferenciaConservaSemestreUnoSinInactividad` | Cubierto | Bloqueado por fuente: convocatoria/resolución |
| VAR-711-P | 7.1.1 | P | Relación formal | Original -> sustituta | Un adeudo en sustituta, Opp. acumuladas | Todos | `Bloque5HistorialRn20261117Test#gc17dUbicaUnSoloAdeudoEnLaSustitutaCuandoLaRelacionEsVerificable` | Cubierto | Bloqueado por fuente: relación real |
| VAR-711-N | 7.1.1 | N | Tercera sustitución | Candidata formal | No procede; sin reinicio | Simulador | No cubierto | Bloqueado por fuente: movimiento candidato |
| VAR-711-D | 7.1.1 | D | Sin relación | Adeudo adicional | REQUIERE_REVISION; no duplica ni inventa ruta | Todos | `Bloque5HistorialRn20261117Test#gc17dNoInfiereUnaSustitucionAdicionalSinFuenteVerificable` | Cubierto | Bloqueado por fuente: relación real |
| VAR-81-P | 8.1 | P | Antecedente acreditada | B2 | `INSCRITA` | Malla, Simulador | `PreparacionDecisionesPendientesV2Test` | Cubierto | Bloqueado por fuente: cierre B1 |
| VAR-81-N | 8.1 | N | Antecedente no acreditada | B2 | BLOQUEADA/PENDIENTE_POR_SERIACION según etapa | Malla, Simulador | `PreparacionDecisionesPendientesV2Test` | Cubierto | Bloqueado por fuente: cierre B1 |
| VAR-81-L | 8.1 | L | B1 vigente | Sin resultado | `PENDIENTE_POR_SERIACION`, fuera de carga confirmada | Malla, Simulador | `PreparacionDecisionesPendientesV2Test` | Cubierto | Bloqueado por fuente: cierre B1 |
| VAR-81-D | 8.1 | D | Cierre desconocido | Antecedente NULL | REQUIERE_REVISION/PENDIENTE_POR_SERIACION; no habilita | Malla, Simulador | `PreparacionDecisionesPendientesV2Test` | Cubierto | Bloqueado por fuente: cierre B1 |
| VAR-81-I | 8.1 | I | Carga ajustada | B2 seriada | Cuenta máximo y no excede 4.3 | Simulador | `PreparacionDecisionesPendientesV2Test#rn43AjusteYConfirmacionPosteriorNoExcedenMaximo` | Cubierto | Bloqueado por fuente: cierre B1 |
| VAR-67-I | 6.7 | I | Baja de UD quinto | Pendiente no reprobatorio | Aplica 6.1; no clasifica como 6.1.5 | Todos | `Bloque3OfertaYPreseleccionRn20261106Test#c10IncluyeNuncaInscritaYBajaParcialEnOmitidasDeQuinto` | Cubierto | Disponible |
| VAR-71-I | 7.1 | I | Regular/irregular 5-8 | Faltante requerida | Aplica prioridad y reglas de optativa, sin bloqueo fijo | Todos | `Bloque3OfertaYPreseleccionRn20261106Test#referenciaCincoAOchoPermiteOptativaFaltanteTrasPrioridadConUnoATresAdeudos` | Cubierto | Disponible |
| VAR-93-P | 9.3 | P | Séptimo/octavo | Requisitos presentes | Decisión según plan; no aplica 6.1 | Todos | `EvaluacionCurricularV2Fase9BTest` | Cubierto | Bloqueado por fuente: mapas PE2027 |
| VAR-94-I | 9.4 | I | Adeudo adicional | Cumplimiento curricular | No bloquea por sí mismo cumplimiento | Asistente, Virtual | `EvaluacionCurricularV2Fase9CTest` | Cubierto | Bloqueado por fuente: sustitución real |

`PENDIENTE_CALIFICACION` sólo se conserva como estado técnico del intento para el cómputo de oportunidades. La UD se proyecta como `NO_DETERMINADO`/revisión cuando existe acta cerrada sin calificación. La denominación de salida vigente para interbloque es `PENDIENTE_POR_SERIACION`; no se usa “pendiente de confirmación” como estado normativo.

## Casos por numeral

| ID | Numeral | Tipo | Precondición | Entrada | Resultado esperado (decisión, motivo, oportunidades, adeudos, estado 8.1, nota) | Componentes donde se verifica | Prueba (clase#método) | Estado |
|---|---|---|---|---|---|---|---|---|
| RN11 | 1.1 | P/N/L/D/I | Actividad, inactividad, baja definitiva y plazo contrastables | P activo; N baja definitiva; L plazo máximo; D sin evidencia; I baja temporal | Dec. sin carga ante baja definitiva; actividad/plazo identificados, no formalizados; D revisión; Ade. no se muta | Todos | `EvaluacionCurricularV2Fase2ATest#f2AU01EstudianteActivo`, `#f2AU14NoHayCambioProductivo` | Cubierto |
| RN12 | 1.2 | P/N/L/D/I | Historial por UD disponible | P un adeudo; N acreditada posterior; L 4; D intento ambiguo; I 1.4 | Ade. se deduplica por UD; 4 activa 6.4; D queda en revisión | Todos | `Bloque5HistorialRn20261117Test#gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso`, `Bloque6Rn20261119Test#rn22CuatroAdeudosConfirmadosNoSeAnulanPorUnResultadoEnRevision` | Cubierto |
| RN13 | 1.3 | P/N/L/D/I | Estado histórico recibido | P no cursada/baja; N no convertir a reprobada; L concurrencia; D estado desconocido; I 6.1 | No suma reprobación; conserva causa; D revisión | Todos | `EvaluacionCurricularV2Fase1ATest#f1AU03ReprobadaYBajaParcialSeConservanConcurrentes` | Cubierto |
| RN14 | 1.4 | P/N/L/D/I | Intentos, acta y baja aplicada verificables | P primera/segunda; N agotada; L tercera en curso; D baja/evento ambiguo; I 2.2/6.7 | Opp. consumidas separadas de recursamientos; baja aplicada no consume; D revisión | Todos | `Bloque2HistorialOportunidadesRn20261106Test#gc08DistingueUltimaDisponibleUltimaEnCursoAgotadaYBajaAutorizada`, `Bloque6Rn20261119Test#rn14SeparaRecursamientosDeOportunidades` | Cubierto |
| RN15 | 1.5 | P/N/L/D/I | UD acreditada | P acreditada no candidata; N intento de mejora; L acreditación tras recursa; D historial contradictorio; I 7.2 | Dec. no reinscribir; Opp. no disponible para mejora; D revisión | Malla, Asistente, Simulador | `EvaluacionCurricularV2Fase2ETest#f2EU01AcreditadaNoSeReinscribeParaMejorarPromedio` | Cubierto |
| RN21 | 2.1 | P/N/L/D/I | Confirmación material requerida | Cualquier selección | Motor informa evaluación; Inscripción confirma o rechaza | Simulador | No aplica | Fuera de alcance |
| RN22 | 2.2 | P/N/L/D/I | Calificación, acta y baja | P 0 sin baja; N NP con baja; L tercera NULL con acta abierta; D NULL con acta cerrada; I 1.4 | P reprobada; NP no consume; L `EN_CURSO`; D `REQUIERE_REVISION`; el estado técnico del intento no se expone como estado de UD | Todos | `Bloque2HistorialOportunidadesRn20261106Test#ceroSinBajaEsReprobadaYCeroConBajaEsNpPorBaja`, `#nuloCerradoRequiereRevisionYNuloVigenteEstaEnCurso` | Cubierto |
| RN23 | 2.3 | P/N/L/D/I | Excepción institucional registrada | P autorización vigente; N vencida; L C1/C2 excluyentes; D sin fuente; I 1.4/6.2 | Restricción se detecta siempre; habilitación sólo con fuente vigente; Nota 2027-1 sin excepción | Asistente, Simulador, Virtual | `ComunicadosInstitucionales20271Test#autorizacionIndividualVigenteSeMuestraSinCambiarLaDecision`, `#gc19eUnMismoExpedienteNoPuedeTenerMarcadoresC1YC2` | Bloqueado por fuente: configuración SIGIE de excepciones |
| RN31 | 3.1 | P/N/L/D/I | Nuevo ingreso | P 4 obligatorias+2 optativas; N incompleta; L B1/B2; D mapa ausente; I 3.3 | Dec. válida sólo con selección completa y distinta; motivo específico | Simulador | `Bloque3OfertaYPreseleccionRn20261106Test#gc16ExigeDosOptativasDistintasEnBloquesDistintos` | Cubierto |
| RN32 | 3.2 | P/N/L/D/I | Segundo semestre o posterior | P 0-2 nuevas; N tercera; L dos + recursa; D tipo ausente; I 7.1 | Tope cuenta nuevas, no recursamiento; D revisión | Simulador | `Bloque3OfertaYPreseleccionRn20261106Test#gc06dRecursamientoNoCuentaComoNuevaOptativa` | Cubierto |
| RN33 | 3.3 | P/N/L/D/I | Optativas seleccionadas | P una por B1/B2; N repetida; L mismo bloque; D bloque no verificable; I 3.1 | Motivo `OPTATIVA_LIMITE_SELECCION`; no `REQUIERE_REVISION` cuando el dato existe | Simulador | `Bloque3OfertaYPreseleccionRn20261106Test#gc16ExigeDosOptativasDistintasEnBloquesDistintos` | Cubierto |
| RN41 | 4.1 | P/N/L/D/I | Unidades elegibles | P mínimo; N debajo; L excepción; D oferta parcial; I 6.4 | Carga mínima sólo con elegibles; excepción explicada; D revisión | Simulador | `EvaluacionCurricularV2Fase3DTest#f3DU08DosObligatoriasSinAlternativaUsaExcepcion` | Cubierto |
| RN42 | 4.2 | P/N/L/D/I | Situación y selección | P 8 regular/6 irregular; N excede; L máximo; D límite ausente; I 8.1 | Máximo individual, B2 seriada cuenta para máximo; D revisión | Simulador | `EvaluacionCurricularV2Fase3DTest#f3DU02RegularOchoUnidadesAlcanzaMaximo`, `#f3DU06IrregularSieteCandidatasNoSuperaSeis` | Cubierto |
| RN43 | 4.3 | P/N/L/D/I | Selección inicial y ajuste | P válida; N incompatible; L ajuste/B2; D cierre no publicado; I 8.1 | No supera máximo; B2 condicionada conserva `PENDIENTE_POR_SERIACION`; D requiere revisión | Simulador | `PreparacionDecisionesPendientesV2Test#rn43AjusteYConfirmacionPosteriorNoExcedenMaximo` | Cubierto |
| RN51 | 5.1 | P/N/L/D/I | Adeudos confirmados | P 1-3; N 4+; L 4; D incierto; I 6.4 | Regular/irregular y prioridad sin contar incertidumbre como adeudo | Todos | `EvaluacionCurricularV2Fase4DTest#f4DU15CuatroReprobadasDetectaRn64SoloObservacional` | Cubierto |
| RN52 | 5.2 | P/N/L/D/I | Adeudos y causa | P rezago leve; N severo; L cuatro; D ambigüedad; I 6.3 | Ruta de regularización y motivos concurrentes | Todos | `EvaluacionCurricularV2Fase4DTest#f4DU16TresReprobadasNoDetectaRn64` | Cubierto |
| RN61 | 6.1 | P/N/L/D/I | Candidata de quinto desde vigencia | P base completa; N no quinto; L referencia 5; D referencia incierta; I 6.4 | Evalúa sólo candidata de quinto; 6.4 prevalece | Todos | `EvaluacionCurricularV2Fase5ATest#f5AU02DesdeVigenciaEnQuintoAplica`, `#f5AU18Rn64PrevaleceConCuatroReprobadas` | Cubierto |
| RN611 | 6.1.1 | P/N/L/D/I | Obligatorias 1-4 | P acreditadas; N pendiente; L última pendiente; D mapa incompleto; I 6.1.2 | Base ordinaria o pendientes con causa; D revisión | Todos | `EvaluacionCurricularV2Fase5ATest#f5AU08BaseCompletaDaAccesoOrdinarioSombra`, `#f5AU09ReprobadaEsPendienteNoAcceso` | Cubierto |
| RN612 | 6.1.2 | P/N/L/D/I | Condiciones comunes | P satisfechas; N omitida; L una pendiente; D prerrequisito ausente; I 8 | Bloqueo por `PENDIENTE_OMITIDA_AVANCE_CONDICIONADO` o causa concreta | Todos | `Bloque3OfertaYPreseleccionRn20261106Test#c10IncluyeNuncaInscritaYBajaParcialEnOmitidasDeQuinto` | Cubierto |
| RN613 | 6.1.3 | P/N/L/D/I | Supuesto tipificado | P falta oferta/concurrencia; N causa no tipificada; L uno; D evidencia ausente; I 6.1.4 | Acceso condicionado sólo bajo supuesto; D revisión/6.1.6 | Todos | `EvaluacionCurricularV2Fase5DTest#f5DU*`, `Bloque1FundamentosRn20261105Test#gc04NuncaCursadaNoOfertadaPermaneceEnUniversoYNoCuentaComoReprobada` | Cubierto |
| RN614 | 6.1.4 | P/N/L/D/I | Pendiente no ofertada | P sin oferta; N sin cupo; L una UD; D oferta inconsistente; I 6.7 | Prioritaria+SIN_OFERTA no seleccionable; no bloqueo absoluto de compatibles | Todos | `EvaluacionCurricularV2Fase6BTest#f6BU08SinOfertaYSinCupoSeDistinguen` | Cubierto |
| RN615 | 6.1.5 | P/N/L/D/I | Medida verificable/recursa quinto | P medida vigente impeditiva; N vencida/revocada; L 4 adeudos; D sin vigencia; I 6.4 | Bloquea sólo medida vigente; recursa quinto es regularización | Todos | `Bloque4TrayectoriaMedidaRn20261106Test#gc13MedidaVigenteDeReinscripcionImpideAvanceCondicionado`, `Bloque3OfertaYPreseleccionRn20261106Test#q5ReprobadaDeQuintoConCuatroAdeudosReportaDecisionVigente` | Cubierto |
| RN616 | 6.1.6 | P/N/L/D/I | Caso no tipificado | Caso extraordinario | `REQUIERE_REVISION`, sin habilitación automática; ticket externo | Asistente, Virtual | `EvaluacionCurricularV2Fase5ETest` | Bloqueado por fuente: GLPi/resolución |
| RN62 | 6.2 | P/N/L/D/I | Incorporación diferida | P resolución; N no aplica; L S1; D sin referencia; I 2.3 | Sólo identifica; sin referencia queda revisión | Todos | `ComunicadosInstitucionales20271Test#gc19dIncorporacionDiferidaSinReferenciaConservaSemestreUnoSinInactividad` | Bloqueado por fuente: convocatoria/resolución |
| RN63 | 6.3 | P/N/L/D/I | Pendiente ofertada | P reprobada elegible; N seriada; L empate; D oferta ausente; I 6.4 | Prioridad por regularización, sin desplazar regla superior | Todos | `EvaluacionCurricularV2Fase3DTest#f3DU12ObligatoriaPrioritariaNoEsDesplazadaPorOptativas` | Cubierto |
| RN64 | 6.4 | P/N/L/D/I | Adeudos vigentes | P <4; N >=4; L 4; D uno incierto; I 6.1/7.1 | Sólo regularización desde cuatro; incierto no impide aplicar cuatro confirmados | Todos | `Bloque6Rn20261119Test#rn22CuatroAdeudosConfirmadosNoSeAnulanPorUnResultadoEnRevision` | Cubierto |
| RN65 | 6.5 | P/N/L/D/I | Periodos sin inscripción | P identifica; N no sanciona; L tres; D periodo desconocido; I baja temporal | Sólo identificación de inactividad, no reinscripción | Todos | `EvaluacionCurricularV2Fase6FTest#f6FU04PeriodoNoCursadoClasificaRn65SinRn66` | Cubierto |
| RN66 | 6.6 | P/N/L/D/I | Bajas temporales aplicadas | P <=3; N cuarta; L 3; D baja sin contabilizar; I 6.9 | Acumulado identifica máximo excedido sin negar carga/reincorporación | Todos | `Bloque4TrayectoriaMedidaRn20261106Test#gc05CuartaBajaTemporalAcumuladaEsExcedente` | Cubierto |
| RN67 | 6.7 | P/N/L/D/I | Baja parcial aplicada | P obligatoria ofertada; N no ofertada; L optativa requerida; D subtipo ambiguo; I 1.4 | Prioritaria si corresponde; NP no consume; baja en recursa conserva adeudo previo | Todos | `EvaluacionCurricularV2Fase6ETest`, `Bloque2HistorialOportunidadesRn20261106Test#c05BajaTemporalNoEsReprobacionNiOportunidad` | Cubierto |
| RN68 | 6.8 | P/N/L/D/I | Escenario sombra | P regulariza; N no acredita; L última; D datos insuficientes; I expectativa | Sólo proyección sin efectos productivos | Asistente, Simulador, Virtual | `EvaluacionCurricularV2Fase6ACierreTest` | Cubierto |
| RN69 | 6.9 | P/N/L/D/I | Participaciones y bajas | P periodos contados; N baja excluida; L B1/B2 una vez; D evento sin periodo; I 6.1 | Referencia por periodo efectivo; D revisión | Todos | `Bloque1FundamentosRn20261105Test#rn69CuentaParticipacionFormalAunqueElResultadoEstePendiente` | Cubierto |
| RN71 | 7.1 | P/N/L/D/I | Catálogo requerido | P regular con faltantes; N irregular fuera regla; L 2 nuevas; D catálogo ausente; I 6.4 | Aplica prioridad, semestre y bloque autorizado | Todos | `Bloque3OfertaYPreseleccionRn20261106Test#referenciaCincoAOchoPermiteOptativaFaltanteTrasPrioridadConUnoATresAdeudos` | Cubierto |
| RN711 | 7.1.1 | P/N/L/D/I | Adeudo adicional y sustitución | P relación formal; N tercera; L 2 sustituciones; D relación ausente; I 1.4/6.4 | Un adeudo en sustituta; no reinicia Opp.; sin relación, revisión | Todos | `Bloque5HistorialRn20261117Test#gc17dUbicaUnSoloAdeudoEnLaSustitutaCuandoLaRelacionEsVerificable`, `Bloque6Rn20261119Test#rn711ConservaSustitucionFormalAnteBajaPosteriorYRecursamiento` | Bloqueado por fuente: movimientos reales de sustitución |
| RN72 | 7.2 | P/N/L/D/I | Optativa reprobada | P requerida; N no sustituye requerida; L oportunidad; D tipo ausente; I 6.4 | Principio general; no tipo ELECTIVA | Todos | `Bloque2HistorialOportunidadesRn20261106Test#rn72EsPrincipioGeneralDeOptativasReprobadas` | Cubierto |
| RN73 | 7.3 | P/N/L/D/I | Regla retirada | Cualquier entrada | No se emite código ni restricción | Todos | `EvaluacionCurricularV2Fase7BTest` | Cubierto |
| RN8 | 8 | P/N/L/D/I | Relación directa | P antecedente acreditada; N pendiente; L cadena; D catálogo ausente; I 6.1 | Habilita/bloquea por relación, no por semestre | Todos | `EvaluacionCurricularV2Fase8DTest` | Bloqueado por fuente: relaciones PE2027/Seminarios |
| RN81 | 8.1 | P/N/L/D/I | Interbloque B1/B2 | P antecedente acreditada; N simultánea; L acta cerrada NULL; D cierre no publicado; I 4.3 | `INSCRITA` o `PENDIENTE_POR_SERIACION`; no crea adeudo | Malla, Simulador | `PreparacionDecisionesPendientesV2Test` | Bloqueado por fuente: cierre real B1 |
| RN82 | 8.2 | P/N/L/D/I | Intersemestral | P acreditada; N pendiente; L cadena; D relación ausente; I 6.1 | Decisión individual con motivo de seriación | Todos | `EvaluacionCurricularV2Fase8DTest` | Bloqueado por fuente: catálogo PE2027 |
| RN83 | 8.3 | P/N/L/D/I | Interanual | P antecedente; N pendiente; L primer año; D relación ausente; I 6.1 | Igual: no propaga bloqueo a otra UD | Todos | `EvaluacionCurricularV2Fase8DTest` | Bloqueado por fuente: catálogo PE2027 |
| RN91 | 9.1 | P/N/L/D/I | Reinscripción extemporánea | Con/sin autorización | No dispensa requisitos; sin registro sólo revisión descriptiva | Simulador | `EvaluacionCurricularV2Fase9ATest` | Bloqueado por fuente: registro de Inscripción |
| RN92 | 9.2 | P/N/L/D/I | Dispersión | Cualquier entrada | No evaluado por este motor | Ninguno | No aplica | Fuera de alcance |
| RN93 | 9.3 | P/N/L/D/I | Últimos semestres | P requisitos; N adeudos; L umbral; D componentes ausentes; I 6.4 | Evalúa plan y componentes verificables; 6.4 prevalece | Todos | `EvaluacionCurricularV2Fase9BTest` | Cubierto |
| RN94 | 9.4 | P/N/L/D/I | Cumplimiento/adeudo adicional | P currículum completo; N adeudo adicional; L 8 requeridas; D sustitución ausente; I certificación | Adeudo adicional no bloquea por sí mismo cumplimiento, certificación o titulación | Asistente, Virtual | `EvaluacionCurricularV2Fase9CTest` | Cubierto |
| RN95 | 9.5 | P/N/L/D/I | Parámetros y contexto | Resultado evaluado | Traza ámbito, valor, documento, vigencia y periodos; sin PII | Todos | `TrazabilidadEvaluacionCurricularV2ServiceTest` | Cubierto |
| COM20271 | Comunicado 2027-1 | P/N/L/D/I | Periodo y restricción | P 2027-1; N otro periodo; L una nota; D texto ausente; I excepción | Nota una vez sólo en 2027-1; no cambia decisión ni motivos | Asistente, Simulador, Virtual | `ComunicadosInstitucionales20271Test#gc19gComunicadoNoModificaDecisionMotivosNiCarga` | Cubierto |

## Interacciones obligatorias

| ID | Numeral | Tipo | Precondición | Entrada | Resultado esperado (decisión, motivo, oportunidades, adeudos, estado 8.1, nota) | Componentes donde se verifica | Prueba (clase#método) | Estado |
|---|---|---|---|---|---|---|---|---|
| INT-641 | 6.4 x 6.1 | Interacción | Cuatro adeudos y candidata quinto | Carga mixta | Sólo regularización; 6.4 prevalece | Todos | `EvaluacionCurricularV2Fase5ATest#f5AU18Rn64PrevaleceConCuatroReprobadas` | Cubierto |
| INT-6415 | 6.4 x 6.1.5 | Interacción | Recursa UD quinto y cuatro adeudos | UD quinto reprobada | Prioritaria para regularización, no bloqueada por 6.1.5 | Todos | `Bloque3OfertaYPreseleccionRn20261106Test#q5ReprobadaDeQuintoConCuatroAdeudosReportaDecisionVigente` | Cubierto |
| INT-647 | 6.4 x 7.1/7.1.1 | Interacción | Cuatro adeudos y optativa | Requerida/adicional | Sin nuevas; sólo regularización o sustituta formal | Simulador | `Bloque3OfertaYPreseleccionRn20261106Test#gc01PreseleccionaReprobadasYSoloHabilitaRutaDeRezago` | Cubierto |
| INT-8143 | 8.1 x 4.3 | Interacción | B2 seriada pendiente | Carga inicial/ajuste | Pendiente de seriación cuenta en máximo; confirmación posterior no excede | Simulador | `PreparacionDecisionesPendientesV2Test#rn43AjusteYConfirmacionPosteriorNoExcedenMaximo` | Cubierto |
| INT-2214 | 2.2 x 1.4 | Interacción | Tercera con 0/NULL/acta | Historial | 0 sin baja reprobada; NULL vigente en curso; NULL cerrado revisión | Todos | `Bloque2HistorialOportunidadesRn20261106Test#nuloCerradoRequiereRevisionYNuloVigenteEstaEnCurso` | Cubierto |
| INT-6714 | 6.7 x 1.4 | Interacción | Baja en recursamiento | Baja aplicada | Conserva adeudo previo, NP no consume | Todos | `EvaluacionCurricularV2Fase6ETest` | Cubierto |
| INT-69BT | 6.9 x baja temporal | Interacción | Periodo con baja aplicada | B1/B2 | Excluye periodo una vez | Todos | `Bloque1FundamentosRn20261105Test#gc05ReferenciaExcluyeBajaTemporalYSinInscripcion` | Cubierto |
| INT-231462 | 2.3 x 1.4/6.2 | Interacción | Excepción sin fuente | Oportunidad/incorporación | Restricción detectada; no habilitación | Asistente, Virtual | `ComunicadosInstitucionales20271Test#autorizacionIndividualAusenteNoSeInfiere` | Bloqueado por fuente: excepción institucional |
| INT-COM | Comunicado x periodo | Interacción | 2027-1 y 2027-2 | Restricción equivalente | Nota sólo 2027-1 | Asistente, Simulador, Virtual | `ComunicadosInstitucionales20271Test#gc19cFueraDe20271ConservaRestriccionSinComunicado` | Cubierto |
| INT-C1C2 | C1 x C2 | Interacción | Marcadores concurrentes | Contexto 2027-1 | Marcadores mutuamente excluyentes; sin concesión | Todos | `ComunicadosInstitucionales20271Test#gc19eUnMismoExpedienteNoPuedeTenerMarcadoresC1YC2` | Cubierto |

## Matriz CAMPUS 2 y casos dorados

| ID | Numeral prevalente | Prueba (clase#método) | Estado |
|---|---|---|---|
| AV-01, AV-06 | 6.1.1-6.1.3 | `EvaluacionCurricularV2Fase5ATest#f5AU08BaseCompletaDaAccesoOrdinarioSombra` | Cubierto |
| AV-02, AV-07, AV-10 | 6.1.2 | `Bloque3OfertaYPreseleccionRn20261106Test#c10IncluyeNuncaInscritaYBajaParcialEnOmitidasDeQuinto` | Cubierto |
| AV-03, AV-09 | 6.4 | `EvaluacionCurricularV2Fase5ATest#f5AU18Rn64PrevaleceConCuatroReprobadas` | Cubierto |
| AV-04 | 6.1.4 | `Bloque1FundamentosRn20261105Test#gc04NuncaCursadaNoOfertadaPermaneceEnUniversoYNoCuentaComoReprobada` | Cubierto |
| AV-05 | 6.1.5 | `Bloque4TrayectoriaMedidaRn20261106Test#gc13MedidaVigenteDeReinscripcionImpideAvanceCondicionado` | Cubierto |
| AV-08 | 6.1.6 | `EvaluacionCurricularV2Fase5ETest` | Bloqueado por fuente: GLPi |
| REG-04, REG-05, REG-09, REG-10 | 6.4 | `Bloque3OfertaYPreseleccionRn20261106Test#gc01PreseleccionaReprobadasYSoloHabilitaRutaDeRezago` | Cubierto |
| REG-06, REG-07, REG-08, CAR-02, OF-01 | 6.1 no aplica fuera de quinto | `EvaluacionCurricularV2Fase5ATest#f5AU03TerceroNoAplica` | Cubierto |
| SER-01, SER-02 | 8.1-8.3 | `EvaluacionCurricularV2Fase8DTest` | Bloqueado por fuente: relaciones PE2027 |
| REI-01, REI-02 | 6.7/6.9 | `Bloque4TrayectoriaMedidaRn20261106Test#gc05TresBajasNoConsecutivasSeExcluyenUnaVezYNoReinscriben` | Cubierto |
| CAR-01 | 4.1 | `EvaluacionCurricularV2Fase3DTest#f3DU09UnaUnicaObligatoriaPendienteUsaExcepcion` | Cubierto |
| OPT-01 | 7.1/6.4 | `Bloque3OfertaYPreseleccionRn20261106Test#irregularSoloPorOptativaReprobadaPuedeCompletarRequeridasEnReferenciaSeis` | Cubierto |
| TR-01 | 6.9 | `EvaluacionCurricularV2Fase6BTest#f6BU04DatosTrayectoriaOCausaIncompletosRequierenRevision` | Cubierto |
| EXT-01 | 9.1 | `EvaluacionCurricularV2Fase9ATest` | Bloqueado por fuente: registro extemporáneo |
| AMB-01 | 6.1.6 | `EvaluacionCurricularV2Fase5ETest` | Bloqueado por fuente: resolución GLPi |
| OM-01 | 6.1.3 | `Bloque3OfertaYPreseleccionRn20261106Test#c10IncluyeNuncaInscritaYBajaParcialEnOmitidasDeQuinto` | Cubierto |
| GC-01, GC-02, GC-03, GC-03b, GC-06, GC-07, GC-10, GC-16 | 3, 6.3, 6.4, 7.1, 8.1 | `Bloque3OfertaYPreseleccionRn20261106Test` | Cubierto |
| GC-04, GC-05, GC-12 | 6.1.4, 6.9, ámbito | `Bloque1FundamentosRn20261105Test` | Cubierto |
| GC-08, GC-09, GC-11, GC-15 | 1.4, 2.2, 7.2 | `Bloque2HistorialOportunidadesRn20261106Test` | Cubierto |
| GC-13, GC-14 | 6.1.5, 6.6, 8 | `Bloque4TrayectoriaMedidaRn20261106Test`, `EvaluacionCurricularV2Fase8DTest` | Cubierto |
| GC-17 | 1.2, 1.4, 6.4, 6.9 | `Bloque5HistorialRn20261117Test#gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso` | Cubierto |
| GC-18 | 8.1 | `PreparacionDecisionesPendientesV2Test` | Bloqueado por fuente: cierres reales |
| GC-19 | comunicado 2027-1 | `ComunicadosInstitucionales20271Test` | Cubierto |

## Consistencia de consumidores

| ID | Numeral | Tipo | Precondición | Entrada | Resultado esperado (decisión, motivo, oportunidades, adeudos, estado 8.1, nota) | Componentes donde se verifica | Prueba (clase#método) | Estado |
|---|---|---|---|---|---|---|---|---|
| CONS-B7-01 | Contrato común | Interacción | Contexto con una reprobada y candidato seleccionado | Adaptador y evaluador reales | Misma situación, reprobadas/UD origen de irregularidad, decisión, motivos, Opp., recursamientos, 8.1 y avisos | Malla, Asistente, Simulador, Asistente virtual | `ConsistenciaConsumidoresBloque7Test#catalogoB7CasoRepresentativoEntregaElMismoContratoALosCuatroConsumidores` | Cubierto |

## Casos bloqueados y fuera de alcance

Los casos que requieren fuente institucional se mantienen en el catálogo para evitar que se omitan: excepción individual (2.3), convocatoria/resolución (6.2), ticket GLPi (6.1.6), sustitución real de adicional (7.1.1), mapa y seriación PE2027/Seminarios (8), cierre registral B1 (8.1) y reinscripción extemporánea (9.1). Titulación, servicio social, dispersión y confirmación material pertenecen a sus módulos responsables.
