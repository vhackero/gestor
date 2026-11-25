# Runbook E2E Sombra V2 DEV/QA

## PRERREQUISITOS

- Ambiente SIGIE DEV o QA con datos reales autorizados.
- Contexto Spring desplegado y datasource JNDI java:jboss/jdbc_elearning disponible.
- Identificador técnico autorizado, sin PII en el reporte.
- Esquema V2 de auditoría disponible si se requiere RN-9.5 persistida.
- Owner del ambiente disponible para configurar propiedades JVM y ejecutar el bean interno.

El archivo spring/applicationContext.xml usa H2 en memoria para pruebas locales; no
es un ambiente E2E real y no debe usarse para aprobar el gate.

## CONFIGURACION

El código usa propiedades JVM, no un perfil Spring V2 definido en el repositorio.

    -Dsigie.v2.diagnostico.e2e.habilitado=true
    -Dsigie.v2.diagnostico.e2e.ambiente=QA
    -DEVALUACION_V2_HABILITADA=true
    -DREGLAS_V2_MODO_SOMBRA=true
    -DREGLAS_V2_VERSION=PE2027-V1
    -DCTR01_PERIODO_INICIO=2027-1
    -DCTR01_SEMESTRE_OBJETIVO=5

Para DEV sólo sustituir QA por DEV. El ejecutor rechaza PROD y cualquier valor
de modo sombra distinto de true.

## VARIABLES_REQUERIDAS

| Propiedad | Valor requerido |
| --- | --- |
| sigie.v2.diagnostico.e2e.habilitado | true |
| sigie.v2.diagnostico.e2e.ambiente | DEV o QA |
| EVALUACION_V2_HABILITADA | true |
| REGLAS_V2_MODO_SOMBRA | true |
| REGLAS_V2_VERSION | PE2027-V1 |
| CTR01_PERIODO_INICIO | 2027-1 |
| CTR01_SEMESTRE_OBJETIVO | 5 |

No registrar URL, usuario, contraseña, token ni secret del datasource.

## PERMISOS_MINIMOS

| Fuente | Permiso | Propósito |
| --- | --- | --- |
| Fuentes SIGIE consumidas por el Asistente | SELECT | Construir el contexto académico real. |
| tbl_evaluacion_curricular_v2 | SELECT, INSERT | Encabezado y hash RN-9.5. |
| tbl_evaluacion_curricular_ud_v2 | INSERT | Unidades y decisiones V2. |
| tbl_evaluacion_curricular_regla_v2 | INSERT | Trazas de reglas. |
| tbl_evaluacion_curricular_excepcion_v2 | INSERT | Excepciones evaluadas. |

No conceder UPDATE, DELETE, DDL ni privilegios generales sobre inscripción,
oferta, grupos, trayectoria o calificaciones. La única escritura permitida es
AUDITORIA_V2.

## FUENTES_DATOS

| Fuente localizada | Acceso | Obligatoria |
| --- | --- | --- |
| AsistenteInscripcionService.obtenerContextoAsistido | READ | Sí |
| InscripcionFacade.obtenerContextoInscripcionConsulta | READ | Sí |
| InscripcionService | READ por composición | Sí |
| FichaDescProgramaService y FichaDescProgramaRepo | READ | Sí |
| MallaCurricularService y MallaCurricularRepo | READ | Sí |
| InscripcionPreviaMateriasService y repositorio | READ | Opcional según perfil |
| ContextoEvaluacionCurricularV2Adapter | En memoria | Sí |
| EvaluacionCurricularV2Service | En memoria | Sí |
| TrazabilidadEvaluacionCurricularV2Service | WRITE sólo V2 | Sí para evidencia completa |

Las tablas subyacentes a los servicios SIGIE de lectura deben ser confirmadas
por el owner DEV/QA; el repositorio no las infiere en este runbook.

## FLAGS_V2

La ejecución sólo procede si las dos guardas propias del diagnóstico y las dos
guardas V2 de sombra están activas. Cualquier incumplimiento debe detener la
ejecución de forma controlada. Modo sombra no autoriza una operación productiva.

## COMANDO_EJECUCION

No existe CLI ni endpoint público en el repositorio. Dentro del contenedor
Spring DEV/QA, mediante consola o harness interno autorizado, invocar:

    diagnosticoE2EEvaluacionCurricularV2Service.ejecutar(ID_PERSONA_TECNICO)

No crear endpoint, no usar H2, fixture, mock o DTO manual.

## SELECCION_PERFILES

| Perfil | Criterio técnico |
| --- | --- |
| PERFIL_E2E_01 | Regular sin pendientes. |
| PERFIL_E2E_02 | Una a tres reprobadas vigentes. |
| PERFIL_E2E_03 | Cuatro o más reprobadas. |
| PERFIL_E2E_04 | Seriación formal pendiente. |
| PERFIL_E2E_05 | Objetivo S5 desde 2027-1. |
| PERFIL_E2E_06 | Objetivo S7 u S8. |
| PERFIL_E2E_07 | Reincorporación o baja con evidencia. |

Si no existe un perfil, registrar CASO_NO_LOCALIZADO_EN_AMBIENTE. No crear
personas, trayectorias ni inscripciones.

## VALIDACION_READ_ONLY

1. Capturar huella técnica previa de oferta, selección, bloqueo y estados.
2. Ejecutar el diagnóstico una sola vez.
3. Verificar que la huella posterior sea idéntica.
4. Verificar que efectosProductivosAplicados sea false.
5. Confirmar que sólo existan escrituras en las cuatro tablas V2.
6. Identificar dichas filas como AUDITORIA_V2, no como mutación productiva.

## EJECUCION

1. Confirmar ambiente, datasource, propiedades y permisos.
2. Recuperar un contexto con el Asistente en lectura.
3. Resolver el alias de perfil técnico.
4. Invocar el bean interno.
5. Capturar alias, hash, conteos, resultado y motivos sin PII.
6. Consultar RN-9.5 y comparar la huella read-only.
7. Clasificar cada perfil.

## CAPTURA_RESULTADO

DiagnosticoE2EEvaluacionCurricularV2DTO contiene el
ResultadoEvaluacionCurricularV2DTO, identificador técnico, identificador/hash
de auditoría, conteos y verificación de inmutabilidad.

## TRAZABILIDAD

Validar versión PE2027-V1, semestre objetivo, situación, pendientes y causas,
reprobadas, seriación, oferta/cupo, RN-6.1, RN-6.4, excepciones, decisiones por
UD, resultado y timestamp.

## ANONIMIZACION

Usar sólo alias PERFIL_E2E_nn y hashes o identificadores técnicos autorizados.
Excluir nombre, matrícula completa, CURP, correo, domicilio, teléfono y
credenciales.

## CLASIFICACION_DIFERENCIAS

| Clasificación | Uso |
| --- | --- |
| CORRECTO o COINCIDE | Resultado verificable. |
| DIFERENCIA_ESPERADA_PE2027 | Diferencia justificada por PE2027-V1. |
| DIFERENCIA_INESPERADA | Revisar fuente, adaptador y regla. |
| DATOS_INSUFICIENTES | Falta evidencia. |
| NO_COMPARABLE | No hay resultado legacy recuperable. |

No cambiar reglas por un único expediente.

## ROLLBACK

No aplica rollback de negocio porque el diagnóstico no muta SIGIE. La
trazabilidad V2 se conserva o se administra sólo por el procedimiento del owner
de auditoría; no ejecutar borrados manuales productivos.

## TROUBLESHOOTING

| Síntoma | Acción |
| --- | --- |
| Diagnóstico deshabilitado | Revisar las dos propiedades sigie.v2.diagnostico.e2e. |
| Rechazo por sombra | Confirmar EVALUACION_V2_HABILITADA y REGLAS_V2_MODO_SOMBRA. |
| Sin contexto SIGIE | Confirmar perfil técnico y SELECT del Asistente. |
| Hash RN-9.5 nulo | Revisar las tablas V2 y permisos de auditoría. |
| Estado no reconocido | Registrar NO_DETERMINADO; no convertir a reprobada. |

## CHECKLIST_GATE

El gate sólo es APROBADO con conexión real DEV/QA, contexto real, adaptador,
motor, resultado V2, RN-9.5 y no mutación comprobada. En ausencia de alguno,
usar BLOQUEADO_POR_AMBIENTE.
