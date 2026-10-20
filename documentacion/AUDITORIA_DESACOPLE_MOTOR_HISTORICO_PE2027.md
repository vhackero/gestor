# Auditoría de desacople del motor histórico — PE2027-V1

## OBJETIVO

`ResultadoEvaluacionCurricularV2DTO` (PE2027-V1) es la única fuente normativa de orientación académica. El modelo de casos conserva únicamente bitácora, evidencia, folio y seguimiento humano; no puede clasificar, habilitar UD, calcular carga ni emitir un dictamen académico.

## ARQUITECTURA_ANTES

SIGIE → contexto asistido → motor de casos/patrones (`FichaIntegralCasoDTO`, `PanelAsistenteVirtualDTO`) y, en paralelo, evaluación PE2027. Esto permitía dos narrativas y decisiones distintas.

## ARQUITECTURA_OBJETIVO

SIGIE → `ContextoEvaluacionCurricularV2DTO` → Motor PE2027 → `ResultadoEvaluacionCurricularV2DTO` → adaptadores de presentación → Simulador / Asistente / Malla / Expediente. La escritura de auditoría queda fuera de los beans.

## DEPENDENCIAS_HISTORICAS Y CONSUMIDORES_LOCALIZADOS

|Componente|Archivo|Responsabilidad / datos históricos|Decisión|Reemplazo PE2027|Acción|
|---|---|---|---|---|---|
|Asistente|`TablaCurricularAsistidaBean`|Ficha, panel, mensajes, patrones y dictamen|Sí, antes de D1|Resultado + adaptador|MIGRAR_A_PE2027|
|Malla|`MallaCurricularAlumnoBean`|Ficha, estado de caso y mensaje preventivo|Sí, antes de D1|Resultado + adaptador malla|MIGRAR_A_PE2027|
|Expediente|`ExpedienteAlumnoBean`|Ficha, casos, bitácora y evidencia|Mixto|Resultado para orientación; caso para seguimiento|CONSERVAR_OPERATIVO_SEPARADO|
|Fachadas|`Asistente/Malla/ExpedienteCurricularV2FacadeImpl`|`obtenerFichaIntegral`, contexto de malla/expediente|Sí|`evaluarSombra`|DEPRECAR para orientación|
|Casos|`CasoAcademicoOperativoServiceImpl`|`tbl_caso_*`|Operativo|referencia/hash de evaluación|CONSERVAR_OPERATIVO_SEPARADO|
|Conocimiento|`BaseConocimientoAcademicoServiceImpl`, `ClasificadorCasoServiceImpl`, `RecomendadorResolucionOperativaServiceImpl`, `ValidadorExpedienteCasoServiceImpl`|patrones, mensajes, clasificación y dictamen|Sí|Resultado PE2027|DEPRECAR como fuente académica|
|SQL histórico|`codigoFuente/bd/*asistente_curricular_v2*`|`tbl_caso_*`, `rel_patron_*`, `cat_mensaje_institucional_contextual`|No en UI tras D1|Sólo conservación operativa|DEPRECAR|

La búsqueda también localizó entidades y repositorios de `tbl_caso_*`, `rel_patron_*` y catálogos. No se eliminan ni se ejecuta ningún `DROP`.

## MODELO_ER_HISTORICO

`tbl_caso_academico_operativo` concentra diagnóstico, dictamen, bitácora, evidencia, UD relacionadas y casos similares. `rel_patron_*` relaciona tipos de caso con criterios, acciones y mensajes. El modelo es histórico/operativo; no es normativo PE2027.

## TRAZABILIDAD_PE2027 Y TABLAS_TRAZABILIDAD

Las tablas `tbl_evaluacion_curricular_v2`, `_ud_v2`, `_regla_v2` y `_excepcion_v2` cuentan con `TrazabilidadEvaluacionCurricularV2ServiceImpl`. Estaban vacías porque los consumidores invocan `evaluarSombra` (lectura) y no llaman explícitamente `registrarSombra`. El servicio deduplica por SHA-256 del contexto.

Política: apertura de asistente y malla es *read-only*; simulación y cambios de selección son *read-only*; la auditoría se registra sólo por solicitud explícita o por un caso operativo, mediante una puerta de servicio que primero evalúa y luego llama una vez a `registrarSombra`. No se escribirá desde beans.

## DIFERENCIA_MOTOR_HISTORICO_VS_PE2027

Las diferencias se registran como `COINCIDE`, `DIFERENCIA_ESPERADA_PE2027`, `DIFERENCIA_INESPERADA` o `DATO_INSUFICIENTE`. Nunca se usa el histórico como fallback. Ante datos insuficientes se presenta revisión segura conservando motivo y regla PE2027.

## ADAPTADOR_ORIENTACION Y ESCENARIOS_PE2027

El adaptador consume exclusivamente resultado, decisiones, motivos y trazas PE2027. Escenarios permitidos: `REGULAR`, `IRREGULAR_POR_REPROBACION`, `IRREGULAR_POR_NO_CURSAMIENTO`, `IRREGULAR_POR_BAJA_PARCIAL`, `IRREGULAR_POR_SERIACION`, `REINCORPORACION`, `RESTRINGIDO_RN64`, `REQUIERE_REVISION`. Las plantillas no inspeccionan textos, dictámenes ni patrones históricos.

## MIGRACION_ASISTENTE, MIGRACION_MALLA Y MIGRACION_SIMULADOR

Fase D1 desactiva lecturas de ficha/panel en los consumidores académicos. Las filas se adaptan desde decisiones PE2027; una UD con seriación nunca se recomienda y `SIN_OFERTA` permanece distinta de una restricción curricular. Simulador y malla conservan sólo presentación no normativa.

## MIGRACION_EXPEDIENTE Y GESTION_OPERATIVA_SEPARADA

El expediente puede listar casos, evidencia y bitácora, pero no debe cargar la ficha para orientar ni usar su dictamen para decisiones. Todo caso nuevo o asociado debe contener referencia, fecha y hash de una evaluación PE2027; el seguimiento humano no sobrescribe el resultado.

## MATRIZ_MIGRACION Y PLAN_DEPRECACION

R1: consumidores académicos dejan de leer ficha. R2: malla, simulador y asistente se orientan sólo por PE2027. R3: expediente separa decisión y gestión. R4: desactivar escrituras nuevas de caso. R5: verificar cero referencias académicas. R6: marcar código/SQL histórico deprecado. R7: proponer respaldo y retiro físico. Esta entrega llega a R1 y prepara R2; no retira tablas ni datos.

## PLAN_ROLLBACK

La rama de rollback es `feature/e2e-reglas-pe2027-consumidores` en `170f3648`. El modelo histórico y sus scripts permanecen intactos. Revertir los commits D1 restaura únicamente las lecturas de presentación.

## PRUEBAS

Se cubren consistencia de situación/carga, seriación, RN-6.1/RN-6.4, estados no reprobatorios, oferta, prioridades y determinismo del adaptador. Los casos de regularidad, 1–3/4+ reprobadas, seriación directa/indirecta, bajas, carga, optativas, datos insuficientes y expediente operativo se mantienen como matriz de regresión.

## RIESGOS Y RESULTADO

Riesgo principal: vistas JSF aún enlazadas a getters históricos. Se mitiga dejando DTOs y gestión operativa disponibles, pero sin lectura para decisión académica. No hay retiro físico autorizado.
