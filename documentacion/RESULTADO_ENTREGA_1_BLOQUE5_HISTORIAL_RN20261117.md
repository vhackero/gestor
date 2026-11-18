# Entrega 1 - Bloque 5: historial RN 2026-11-17

## Alcance y evidencia

| Elemento | Valor |
| --- | --- |
| Rama | `feature/desacople_motor_historico` |
| Documento normativo | `P2024-2027- Reglas de negocio_Octubre 2026_20261117.docx` |
| SHA-256 del documento normativo | `71d38ed4b29fba658c51b7083c79a0320a8b4014a5381a62089f5e4982bdc7ce` |
| Matriz de casos | `CAMPUS 2- Casos de prueba_ 2027-1_20261111.xlsx` |
| SHA-256 de la matriz | `32721f5535f1e0d77872d332a1d6bf2aded566f9c26756d94c7b8293a0dd1095` |
| Versión configurada | `RN-PE2024-2027-act-20261117` |
| Modo | Sombra; `efectosProductivosAplicados=false` |
| Datos personales | No usados ni incluidos |

Los dos insumos se conservaron en `documentacion/` como material de trabajo no versionado. La matriz contiene datos de acceso de pruebas; no se copió a pruebas, reportes ni fixtures.

## 6. Dudas técnicas verificadas

| Duda | Evidencia revisada | Resultado | Límite o acción pendiente |
| --- | --- | --- | --- |
| Significado de `contabilizar` en bajas | `NuevaBajaServiceImpl` calcula y persiste `contabilizar=1` cuando la baja queda aplicada a evento/inscripción o se completa el movimiento; el adaptador filtra `rel_persona_bajas.contabilizar=1`. | Es evidencia verificable de baja aplicada en SIGE para excluir el intento de RN-1.4. | La aprobación externa no tiene un campo distinto localizado; DAEAE debe confirmar que `contabilizar=1` equivale también a autorización institucional para todos los motivos. |
| Participación formal vs. preselección | `GrupoParticipanteServiceImpl.almacenarParticipante(s)` persiste `rel_grupo_participante` después de asignar persona y grupo, desde dispersión/matriculación. | El historial sólo toma `rel_grupo_participante`; una preselección sin participación no cuenta como intento. | No se localizó una tabla de preselección con llave homologable para auditar cierres interbloque; si se entrega, se integrará como exclusión explícita. |
| Estado RN-8.1 B1/B2 | El motor conserva el motivo técnico `PRESELECCION_CONDICIONADA_INTERBLOQUE`; no se localizó columna institucional que guarde los estados operativos `Inscrita`, `Pendiente por seriación` y `Pendiente de confirmación`. | No se infiere ni persiste ese estado. | Requiere fuente/contrato de Inscripción para la representación operativa de B2. |
| Tipos 2, 7 y 8 de baja temporal | El adaptador mapea los tres a `BAJA_TEMPORAL`; el motor los usa para periodos excluidos y acumulado. | Cobertura de tipo técnica existente. | Falta confirmación de que cada uno representa periodo completo para toda población; no se asume fuera de los registros aplicados. |
| Seriación | Malla y adaptador leen `tbl_ficha_descriptiva_programa.id_programa_antecedente`; el motor recibe relaciones directas y deriva las indirectas por transitividad. | El contrato propuesto es directo: `UD`, `prerrequisito`, `tipo`, `plan`, `vigencia`; no se duplica la relación indirecta. | La tabla actual sólo expresa un antecedente directo por fila; CAI debe proporcionar la carga normativa de relaciones cuando haya más de una por UD o tipos explícitos. |

## 1. Actualización normativa 2026-11-17

| Cambio | Aplicación | Evidencia |
| --- | --- | --- |
| Versión vigente y fecha de vigencia | Configuración cambia a `RN-PE2024-2027-act-20261117`, documento y vigencia `2026-11-17`. | `ConfiguracionEvaluacionCurricularV2ServiceImpl`. |
| RN-8.1 | Se conserva como pendiente de fuente para los estados operativos de bloque B2. No se resolvió por inferencia desde el documento de decisiones v3. | Duda técnica anterior. |
| RN-8.2 | El motor ya separa seriación interbloque de otras relaciones; no se alteró el estado productivo ni se escribió en Inscripción. | Evaluador y contrato de seriación existente. |

## 2. C-18, C-19 y C-20

### C-18 - oportunidades por UD

| Comportamiento | Implementación |
| --- | --- |
| Unidad de cómputo | Participación formal, deduplicada por `id_participacion`; el adaptador agrupa por UD y periodo. |
| Exclusiones | `BAJA_PARCIAL` y `BAJA_TEMPORAL` aplicadas no consumen oportunidad. |
| Datos en revisión | `NO_DETERMINADO` y extraordinario histórico se exponen en `intentosEnRevision`; el estado se vuelve `REQUIERE_REVISION` y el aviso incluye `OPORTUNIDADES_EN_REVISION:<causa>`. |
| Salidas nuevas | `intentosConfirmados`, `intentoEnCurso`, `intentosEnRevision`, `oportunidadesDisponiblesMinimas`, `oportunidadesDisponiblesMaximas`. |
| Límite | 0/1 fallidos: `DISPONIBLE`; 2: `ULTIMA_DISPONIBLE`; tercera en curso: `ULTIMA_EN_CURSO`; 3 concluidos: `AGOTADA`; más de 3: `AGOTADA` con `LIMITE_SUPERADO_HISTORIAL_PREVIO` y `PENDIENTE_DISPOSICION_TRANSITORIA`. |

### C-19 - adeudos y RN-6.4

| Comportamiento | Implementación |
| --- | --- |
| Adeudo vigente | Se cuenta una UD con resultado reprobado verificable y sin acreditación posterior; una recursada en curso mantiene un único adeudo. |
| Acreditación posterior | Elimina el adeudo de la UD, aunque conserve reprobaciones históricas. |
| Bajas | La baja aplicada no crea reprobación; una baja durante recursamiento no borra una reprobación previa. |
| Umbral RN-6.4 | Se calcula con UD con adeudo, no con la suma de reprobaciones. Si hay menos de cuatro confirmadas pero confirmadas más revisión pueden llegar a cuatro, se registra `UMBRAL_64_EN_REVISION` y no se activa el bloqueo. |
| Sustituciones de optativa adicional | Sin relación institucional verificable, el resultado es `REQUIERE_REVISION`; no se inventa ni duplica un adeudo. |

### C-20 - consulta agregada de control

La consulta de producción es sólo de lectura y debe ejecutarse con una cuenta autorizada. La conexión local configurada rechazó el acceso (`Access denied`), por lo que este reporte no presenta cifras inventadas.

```sql
SELECT fd.id_plan, pi.nombre_periodo,
       SUM(CASE WHEN rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS resultados_reprobados,
       COUNT(DISTINCT CASE WHEN rgp.calificacion_final IS NULL THEN rgp.id END) AS participaciones_sin_calificacion
FROM rel_grupo_participante rgp
JOIN tbl_grupos g ON g.id = rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento = g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
LEFT JOIN tbl_periodos_inscripcion pi
  ON SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = pi.nombre_periodo
GROUP BY fd.id_plan, pi.nombre_periodo;
```

Para el reporte C-20 definitivo faltan los agregados, por PE y periodo, de: tres fallidos concluidos, más de tres, tercera oportunidad en curso, causa de revisión y comparación entre cuatro adeudos y cuatro resultados reprobados. La consulta deberá extenderse con las exclusiones de baja aplicada y el grano `persona + UD + periodo`; nunca debe incluir datos personales.

## GC-17 y variantes

| Caso | Entrada sintética resumida | Salida comprobada por UD |
| --- | --- | --- |
| GC-17 | A: dos fallidos y tercera en curso; B: baja parcial y un fallido; C: fallido y acreditado; D: dos fallidos; G: baja parcial; E: no cursada. | A `ULTIMA_EN_CURSO`, 3 confirmados y un solo adeudo; B disponible con 1 consumida; C acreditada sin adeudo; D `ULTIMA_DISPONIBLE`; G y E sin adeudo. Total: 3 adeudos, RN-6.4 no aplica. |
| GC-17b | F: tres fallidos; F': cuatro fallidos históricos. | F `AGOTADA`; F' `AGOTADA` con `LIMITE_SUPERADO_HISTORIAL_PREVIO` y `PENDIENTE_DISPOSICION_TRANSITORIA`. |
| GC-17c | Tres adeudos confirmados y una UD con resultado no confirmado. | La cuarta posible no activa RN-6.4: `UMBRAL_64_EN_REVISION`; la UD queda `REQUIERE_REVISION` y presenta rango de 2 a 3 oportunidades disponibles. |
| GC-17d | Optativa adicional sin relación verificable de sustitución. | `REQUIERE_REVISION`; cero oportunidades consumidas confirmadas y aviso `OPORTUNIDADES_EN_REVISION`. |

## Pruebas ejecutadas

```text
mvn -Dtest=Bloque5HistorialRn20261117Test,Bloque2HistorialOportunidadesRn20261106Test test
Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
```

No se ejecutó la regresión completa: corresponde a la Entrega 2 según la instrucción de alcance. No se ejecutaron GC-18, sección 3, sección 5 ni los entregables de la sección 7.

## Resultado de Entrega 1

**PENDIENTE DE VALIDACIÓN NORMATIVA.** C-18 y C-19 tienen cobertura sintética; C-20 conserva consultas agregadas pendientes de ejecutar con acceso de sólo lectura autorizado. Los estados operativos de RN-8.1 siguen pendientes de fuente institucional y no se resolvieron por inferencia.
