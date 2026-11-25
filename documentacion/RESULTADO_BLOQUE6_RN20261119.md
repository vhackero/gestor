# Resultado Bloque 6 - RN 20261119

## Documento y alcance

| Dato | Valor |
| --- | --- |
| Documento rector | `P2024-2027- Reglas de negocio_Octubre 2026_20261119_0055.docx` |
| Fecha de modificación | `2026-11-19T06:53:31Z` |
| SHA-256 | `e93a476672d72ba5320ee3e26f14a15a11328951f8ded803290c58ff2e1322aa` |
| Versión configurada | `RN-PE2024-2027-act-20261119` |
| Modo | Sombra; sin escrituras en Inscripción, bajas, grupos, oferta ni calificaciones. |
| Commit de código y WAR | `ec2a3c09` |

## Cambios aplicados

| Criterio | Implementación | Evidencia |
| --- | --- | --- |
| 1.4 | El resultado por UD separa oportunidades consumidas, disponibles y `recursamientos`; una baja aplicada no consume oportunidad. | `Bloque6Rn20261119Test` |
| 2.2 | El adaptador cruza participación, grupo, acta, calificación y baja aplicada por persona/grupo. Un `NULL` con acta abierta queda en curso; con acta cerrada queda en revisión. La fecha de fin académica se conserva para el diagnóstico G1/G2 y no sustituye el cierre del acta. Una baja con calificación 1-59 o >=60 queda en revisión. | `Bloque2HistorialOportunidadesRn20261106Test`, `PreparacionDecisionesPendientesV2Test#g1G2ActaAbiertaConservaEnCursoAunqueTermineElEventoHastaQueCierreElActa` |
| 2.3 | Se incorporó el contrato de lectura `ExcepcionInstitucionalCurricularV2DTO`; conserva la restricción y agrega aviso trazable cuando la excepción vigente y aplicable está disponible. | Contrato y evaluación V2; la fuente SIGIE queda pendiente. |
| 4.3 | La B2 pendiente por seriación cuenta una sola vez para mínimo y máximo de la carga inicial. | `PreparacionDecisionesPendientesV2Test` |
| 6.1.5 | El recursamiento de quinto se trata como regularización, incluso con RN-6.4; se retiró el marcador de definición pendiente Q5. | `PreparacionDecisionesPendientesV2Test` |
| 7.1 / 7.1.1 | Con 1 a 3 adeudos y referencia >=5 se permiten requeridas faltantes después de prioridad; el bloque se toma de la oferta autorizada. El contrato ya expone `sustitucionesUtilizadas` si la fuente lo acredita. | `Bloque3OfertaYPreseleccionRn20261106Test`, `Bloque6Rn20261119Test` |
| 8.1 | Se retiró `PENDIENTE_DE_CONFIRMACION`: una antecedente en revisión conserva la consecuente `PENDIENTE_POR_SERIACION` fuera de carga confirmada. | `EvaluacionCurricularV2Fase8ATest` |
| 6.7 | El enlace de bajas conserva como condición `contabilizar=1`, persona, grupo y periodo; una solicitud sin esa evidencia queda en revisión. | `InscripcionRepository` y pruebas históricas. |

## Vacíos reales

| Vacío | Tratamiento vigente | Fuente requerida |
| --- | --- | --- |
| Excepciones institucionales | Contrato de sólo lectura listo; sin registro no modifica una evaluación. | Catálogo SIGIE y flujo de personal autorizado (G4). |
| Ventana de ajuste o confirmación B2 | El motor no formaliza una inscripción de ajuste ni una confirmación tardía; sólo valida la carga con la UD pendiente incluida. | Calendario institucional por periodo y bloque (G3). |
| Sustituciones de optativa adicional | El contrato expone la relación y el contador cuando la fuente lo acredita; sin relación original-sustituta no infiere la cadena. | Fuente institucional de la relación y su formalización (Q12). |

## Control G1/G2: cierre de periodo y acta

Consulta agregada ejecutada el `2026-11-19` sobre planes PE2024 `3,4,5,6,7,11`, periodos `2024-1` a `2026-2` y sin datos personales:

```sql
SELECT fd.id_plan, pi.nombre_periodo, COUNT(DISTINCT g.id) AS grupos,
       COUNT(DISTINCT rgp.id) AS participaciones,
       SUM(rgp.calificacion_final IS NULL) AS calificacion_null,
       SUM(rgp.calificacion_final = 0) AS calificacion_cero,
       SUM(rgp.calificacion_final BETWEEN 1 AND 59) AS calificacion_1_59,
       SUM(rgp.calificacion_final >= 60) AS calificacion_60_mas
FROM rel_grupo_participante rgp
JOIN tbl_grupos g ON g.id = rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento = g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
JOIN tbl_periodos_inscripcion pi
  ON SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = pi.nombre_periodo
JOIN (
  SELECT fd2.id_plan, SUBSTRING_INDEX(e2.cve_evento_cap, '-', -2) AS periodo,
         MAX(e2.fecha_final) AS fecha_fin_academica
  FROM tbl_eventos e2
  JOIN tbl_ficha_descriptiva_programa fd2 ON fd2.id_programa = e2.id_programa
  GROUP BY fd2.id_plan, SUBSTRING_INDEX(e2.cve_evento_cap, '-', -2)
) cierre ON cierre.id_plan = fd.id_plan AND cierre.periodo = pi.nombre_periodo
WHERE fd.id_plan IN (3,4,5,6,7,11)
  AND pi.nombre_periodo BETWEEN '2024-1' AND '2026-2'
  AND cierre.fecha_fin_academica < CURDATE()
  AND COALESCE(g.acta_cerrada,0) = 0
GROUP BY fd.id_plan, pi.nombre_periodo;
```

| Periodo | Grupos con acta abierta | Participaciones | NULL | 0 | 1-59 | >=60 |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| Periodos académicamente concluidos, seis PE2024 | 0 | 0 | 0 | 0 | 0 | 0 |

`tbl_periodos_inscripcion` no puede ser el cierre académico: para 2026-2 termina el `2026-07-17`, mientras los eventos académicos del mismo periodo terminan hasta `2026-12-06`. Por eso las 34,391 participaciones de 2026-2 con acta abierta y `NULL` no pertenecen a un periodo académico concluido al corte; siguen `EN_CURSO` conforme G1. Usar la ventana administrativa las habría convertido incorrectamente en revisión.

El adaptador toma el cierre académico agregado por `plan + periodo` como `MAX(tbl_eventos.fecha_final)`, unido al periodo por coincidencia exacta de `SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)` con `nombre_periodo`; no se usa `LIKE`. La validación detectó `0` eventos con más de un periodo coincidente. Para 2026-1 los eventos terminan entre `2026-03-22` y `2026-06-14`; para 2026-2, entre `2026-09-13` y `2026-12-06`, según plan. Al corte, ningún periodo académicamente concluido conserva acta abierta en el universo consultado, por lo que no procede cambiar G1/G2 ni proponer el criterio combinado.

## Parámetros fijados por RN

| Parámetro | Valor efectivo | Tratamiento |
| --- | --- | --- |
| Q6, cero sin baja | `REPROBACION` | Fijo por RN-2.2; las alternativas se normalizan y sólo sobreviven como análisis de sensibilidad SQL. |
| Q11, bloque de optativa con baja o sustituta | `OFERTA_AUTORIZADA` | Fijo por RN-7.1: la oferta autorizada del periodo decide el bloque, incluso en recursamiento. |
| Q4, B2 pendiente por seriación | `CUENTA_MIN_Y_MAX` | Cuenta una vez en carga inicial; al cierre se permite quedar bajo el mínimo sin exceder el máximo. |

## Excepciones institucionales (RN-2.3)

La revisión de código y metadatos SIGIE no localizó una fuente institucional integrada que contenga beneficiarios, proceso, periodo, vigencia, responsable y alcance de una excepción. La lectura del contrato `ExcepcionInstitucionalCurricularV2DTO` ya distingue la **restricción detectada** de la **habilitación concedida por excepción institucional**, sin cambiar inscripciones, calificaciones, adeudos u oportunidades.

Propuesta de fuente, sin crear estructura: `cfg_excepcion_institucional_curricular` con identificador, versión/regla, beneficiario por persona/lista/criterio, proceso, periodo, inicio, fin o condición de vigencia, alcance, referencia documental, responsable, fecha de captura, estado y auditoría. Debe validar periodo, vigencia, alcance y no permitir habilitar una excepción sin referencia institucional. C1 y C2 se modelan como excepciones de población. Mientras no exista una excepción aplicable, se conserva el aviso general 2027-1.

La matriz v6 con las columnas de implementación y evidencia, incluidas las preguntas cerradas, está en `MATRIZ_CUESTIONAMIENTOS_V6_BLOQUE6.md`.

## Pruebas y compilación

| Prueba | Criterio acreditado |
| --- | --- |
| `Bloque6Rn20261119Test#rn22CuatroAdeudosConfirmadosNoSeAnulanPorUnResultadoEnRevision` | Cuatro adeudos confirmados activan RN-6.4 aunque otra UD quede en revisión. |
| `PreparacionDecisionesPendientesV2Test#g1G2ActaAbiertaConservaEnCursoAunqueTermineElEventoHastaQueCierreElActa` | NULL con acta abierta permanece en curso; la fecha académica se mide, pero no sustituye el cierre del acta. |
| `PreparacionDecisionesPendientesV2Test#rn43B2PendienteCuentaSiempreEnMinimoYMaximo` | La B2 pendiente cuenta una sola vez para mínimo y máximo de la carga inicial. |
| `Bloque4TrayectoriaMedidaRn20261106Test#gc13MedidaVencidaNoImpideAvanceCondicionado` | Medida vencida se retira al reevaluar; no se conserva como bloqueo. |
| `Bloque3OfertaYPreseleccionRn20261106Test#c10IncluyeNuncaInscritaYBajaParcialEnOmitidasDeQuinto` | Una pendiente por baja entra al análisis RN-6.1 y no se convierte en reprobación. |
| `Bloque6Rn20261119Test#rn711ExponeSustitucionesSoloCuandoLaFuenteLasAcredita` | El contador de sustituciones se expone sólo si la relación fue proporcionada por la fuente. |
| `ComunicadosInstitucionales20271Test#autorizacionIndividualVigenteSeMuestraSinCambiarLaDecision` | Autorización vigente: conserva restricción y muestra habilitación/aviso trazable. |
| `ComunicadosInstitucionales20271Test#autorizacionIndividualVencidaNoSeMuestra` | Autorización vencida: se retira al reevaluar. |
| `ComunicadosInstitucionales20271Test#autorizacionIndividualAusenteNoSeInfiere` | Sin autorización: no se infiere habilitación y permanece el aviso general aplicable. |
| `ComunicadosInstitucionales20271Test#gc19gComunicadoNoModificaDecisionMotivosNiCarga` | En 2027-2 no se emite el comunicado ni se arrastra su marcador. |

Los escenarios de sustitución formalizada, baja posterior, recursamiento de sustituta y tercera sustitución requieren la fuente institucional Q12 para construir el vínculo original-sustituta. El motor no los simula desde intentos aislados ni inventa una relación.

## Validación del enlace de bajas (RN-2.2)

`rel_persona_bajas.id_grupo` se relaciona con `tbl_grupos.id`; ese grupo contiene `clave`, `id_moodle` e `id_evento`. El adaptador valida la baja aplicada por `persona + id_grupo + contabilizar=1` y obtiene el periodo mediante `tbl_grupos.id_evento -> tbl_eventos.cve_evento_cap -> tbl_periodos_inscripcion.nombre_periodo`. El control agregado devolvió `3,181` bajas aplicadas enlazadas a evento y periodo y `0` sin alguno de esos dos datos.

Esto confirma la llave técnica del grupo/Aula en SIGIE y su periodo. No se equipara `id_grupo` con una clave textual externa: la relación verificable es el identificador interno del grupo, con `id_moodle` disponible como atributo del mismo grupo.

| Módulo | Comando | Resultado |
| --- | --- | --- |
| Basegestor | `mvn -q -f codigoFuente/basegestor/pom.xml test` | 817 pruebas; 0 fallas, 0 errores, 0 omitidas. |
| GestorWeb | `mvn -q -f codigoFuente/gestorWeb/pom.xml test` | 41 pruebas; 0 fallas, 0 errores, 0 omitidas. |
| WAR | `mvn -q -f codigoFuente/gestorWeb/pom.xml -DskipTests package` | `SUCCESS` |

## Artefacto y validación en ejecución

| Dato | Valor |
| --- | --- |
| Artefacto | `codigoFuente/gestorWeb/target/plataforma.war` |
| Tamaño | `82,750,278` bytes |
| SHA-256 | `9af687c130729018cadb3355e054634b86c55954732bc9fa4ea4898ac2526236` |
| Despliegue | Pendiente de Abel; esta entrega no despliega ni usa expedientes identificables. |

El paquete operativo completo se entrega en `VALIDACION_RUNTIME_ABEL_BLOQUE6.md`. Incluye artefacto, propiedades, consulta de identificadores internos, matriz de pantallas y reglas para capturas anonimizadas.

## Conclusión

Las reglas implementables del Bloque 6 quedaron actualizadas a la versión RN 20261119 y la regresión de ambos módulos está verde. Las excepciones institucionales, sustituciones reales y ventanas de ajuste quedan explícitamente condicionadas a sus fuentes institucionales; no se infieren ni generan efectos productivos.
