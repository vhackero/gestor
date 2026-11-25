# Resultado Fase 11A: E2E Sombra V2

`FASE_IMPLEMENTADA = FASE 11A`

## OBJETIVO

Cerrar la deuda de ejecución extremo a extremo en modo sombra mediante el
ejecutor interno DEV/QA existente, sin integrarlo en Malla Curricular,
Asistente, Simulador ni flujos de inscripción productiva.

## ARQUITECTURA_E2E

`SIGIE/Asistente de solo lectura -> ContextoEvaluacionCurricularV2Adapter -> ContextoEvaluacionCurricularV2DTO -> EvaluacionCurricularV2Service -> ResultadoEvaluacionCurricularV2DTO -> Trazabilidad RN-9.5`.

El ejecutor reutilizado es
`DiagnosticoE2EEvaluacionCurricularV2ServiceImpl`. No se creó endpoint,
pipeline ni motor alterno.

## EJECUTOR

El servicio interno:

- sólo se habilita con `sigie.v2.diagnostico.e2e.habilitado=true`;
- sólo acepta ambiente `DEV` o `QA`;
- exige `EVALUACION_V2_HABILITADA=true` y
  `REGLAS_V2_MODO_SOMBRA=true`;
- obtiene el contexto con `AsistenteInscripcionService`;
- construye el DTO mediante el adaptador real;
- evalúa el motor V2 consolidado;
- registra la trazabilidad RN-9.5 ya existente;
- devuelve el `ResultadoEvaluacionCurricularV2DTO` completo dentro del
  diagnóstico interno;
- rechaza de forma controlada un expediente sin contexto SIGIE.

No existe endpoint público ni invocación desde componentes de estudiante.

## FUENTE_CONTEXTO_REAL

Fuente prevista: `AsistenteInscripcionService.obtenerContextoAsistido`, usada
como lectura del contexto SIGIE vigente. El adaptador
`ContextoEvaluacionCurricularV2AdapterImpl` ya declara que no consulta BD, no
escribe BD y no modifica el objeto de origen.

Clasificación de infraestructura:

| Elemento | Clasificación | Decisión |
| --- | --- | --- |
| Contexto/resultado V2 | REUTILIZABLE | Consumidos sin duplicación. |
| Adaptador V2 | REUTILIZABLE | Invocado por el ejecutor. |
| Evaluador V2 | REUTILIZABLE | Ejecutado una vez por contexto. |
| Trazabilidad RN-9.5 | REUTILIZABLE | Persistencia propia de auditoría, sólo bajo guardas DEV/QA. |
| Diagnóstico E2E F1E | REQUIERE_EXTENSION | Se añadió el resultado V2 completo y el rechazo de contexto inexistente. |
| Endpoint técnico | NO_APLICA | No fue creado. |

## ADAPTADOR

La prueba técnica ejecuta el adaptador real contra un contexto realista de su
fuente SIGIE y verifica que el resultado no altera oferta, selección ni bloqueo
de la fuente. La validación real pendiente debe contrastar, por perfil, PE,
semestre, UD, tipo, estado, bloque, créditos, oferta, causas, reprobadas y
seriación antes de clasificar el caso.

## MODO_SOMBRA

El diagnóstico falla si V2 está deshabilitado o si el modo sombra no está
activo. El resultado expuesto conserva
`efectosProductivosAplicados=false`. No existe ruta de selección, alta,
inscripción, reinscripción, grupo, oferta o carga real.

## READ_ONLY

La ejecución se declara transaccional de lectura respecto de los datos SIGIE.
La única escritura posible es la trazabilidad V2 existente, condicionada a la
habilitación explícita DEV/QA del diagnóstico. La huella de origen antes y
después de evaluar verifica que las propiedades de unidades que podrían afectar
un flujo productivo no se modifican.

## PERFILES_EVALUADOS_ANONIMIZADOS

No se ejecutó ningún perfil real DEV/QA. El entorno no proporciona propiedades
DEV/QA habilitadas, conexión/credenciales de ambiente ni identificadores
técnicos anonimizados autorizados.

| Perfil | Estado |
| --- | --- |
| PERFIL_E2E_01 a PERFIL_E2E_07 | CASO_NO_DISPONIBLE |

No se construyeron DTOs inventados para declarar una ejecución real. Las
pruebas automatizadas usan un contexto técnico aislado y no cuentan como perfil
E2E DEV/QA.

## NORMALIZACION_REAL

Pendiente de validar contra datos reales. El adaptador mantiene la normalización
ya implementada: `REPROBADA`, `NO_CURSADA`, `BAJA_PARCIAL`,
`NO_INSCRITA` y `NO_DETERMINADO` son estados diferentes. Los valores
desconocidos no se convierten a reprobación.

## DEDUPLICACION_REAL

Pendiente de validar contra expedientes DEV/QA con duplicados reales por
historial, bloque, oferta o grupo. El motor ya conserva deduplicación lógica;
la ejecución E2E no crea un contador o regla paralela.

## REGLAS_EJECUTADAS

El ejecutor invoca `EvaluacionCurricularV2Service.evaluarSombra`, por lo que
atraviesa el motor consolidado F0-F9: situación académica, carga, prioridad,
RN-6.1, RN-6.4, tipos de UD, seriación, últimos semestres, egreso académico y
trazabilidad aplican según los datos del perfil. No llama reglas individuales.

## TRAZABILIDAD_RN_9_5

El diagnóstico registra o recupera:

- versión normativa;
- semestre objetivo;
- situación, pendientes y causas;
- reprobadas;
- seriación;
- oferta/cupo;
- RN-6.1;
- excepciones;
- decisiones por UD;
- resultado, hash y timestamp de auditoría.

La salida técnica no agrega PII a la documentación. La auditoría se considera
reconstruida cuando existen identificador de evaluación y hash de contexto.

## RESULTADOS_E2E

No hubo resultados E2E reales por ausencia de ambiente DEV/QA habilitado.
La ruta queda preparada para recibir un identificador técnico autorizado y
emitir un diagnóstico sin activar consumidores productivos.

## DIFERENCIAS_VS_LEGACY

No evaluable sin perfiles reales. Una futura ejecución clasificará cada
diferencia como `COINCIDE`, `DIFERENCIA_ESPERADA_PE2027`,
`DIFERENCIA_INESPERADA`, `NO_COMPARABLE` o
`DATOS_INSUFICIENTES`. No se alteró V2 para igualar semántica legacy.

## DIFERENCIAS_ESPERADAS_PE2027

No evaluadas por falta de perfiles reales. Permanecen sujetas a la evidencia de
cada expediente y a la versión `PE2027-V1`.

## DIFERENCIAS_INESPERADAS

No evaluadas. No se identificó defecto normativo mediante datos reales en este
ambiente.

## DATOS_INSUFICIENTES

Bloqueantes para el gate:

- acceso a DEV/QA;
- habilitación explícita del diagnóstico;
- configuración V2 sombra del ambiente;
- uno o más identificadores técnicos anonimizados autorizados;
- evidencia de perfiles representativos y de sus fuentes SIGIE.

## RIESGOS

- El gate no puede aprobarse con mocks o compilación.
- La trazabilidad configurada para DEV/QA requiere que sus repositorios estén
  disponibles en el ambiente destino.
- Los perfiles reales pueden exponer catálogos o estados no normalizados; deben
  clasificarse antes de cambiar una regla.
- La clasificación de seriación debe provenir de la información verificable
  del PE; datos incompletos deben producir revisión, no inferencia.

## PRUEBAS_AUTOMATIZADAS

Se agregaron pruebas técnicas del ejecutor:

- rechazo por omisión y fuera de DEV/QA;
- ejecución con fuente, adaptador y motor reales, sin mutar el origen;
- retorno del resultado completo en sombra;
- trazabilidad reconstruida;
- rechazo controlado cuando no existe contexto SIGIE.

Resultado focalizado: 3 pruebas, 0 fallas, 0 errores, 0 omitidas.

## REGRESION

F0-F11A ejecutada contra el HEAD final:

| Tests run | Failures | Errors | Skipped |
| --- | --- | --- | --- |
| 746 | 0 | 0 | 0 |

El resultado automatizado se reporta separado de los perfiles E2E reales y no
convierte el gate en aprobado.

## COMPILACION

| Módulo | Resultado |
| --- | --- |
| basegestor | SUCCESS |
| gestorWeb | SUCCESS |

## CAMBIO_PRODUCTIVO

`CAMBIO_PRODUCTIVO = NINGUNO`

No se modificaron Malla, Asistente, Simulador, inscripción, reinscripción,
oferta, grupos ni persistencia de selección.

## RESULTADO_GATE

`BLOQUEADO_POR_AMBIENTE`

El ejecutor existe y sus guardas/pruebas técnicas están validadas, pero no hay
acceso ni perfiles reales DEV/QA para cumplir el criterio E2E. No declarar
`APROBADO` hasta ejecutar al menos un expediente real mediante esta ruta.
