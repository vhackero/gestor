# Matriz de cuestionamientos v6 - Bloque 6

Referencia: `Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v6`, RN vigentes `2026-11-19`. Esta matriz no concede autorizaciones ni reemplaza los procesos institucionales.

| Id | Estado | Implementacion | Evidencia de prueba | Fuente o accion pendiente |
| --- | --- | --- | --- | --- |
| C1 / C1-a | Preparado | `ExcepcionInstitucionalCurricularV2DTO` permite una excepcion de poblacion, conserva la restriccion detectada y agrega habilitacion trazable cuando la excepcion vigente aplica. Sin excepcion se conserva el aviso 2027-1. | `Bloque6Rn20261119Test`; prueba de excepcion vigente, vencida y ausente. | Fuente SIGIE de beneficiarios/criterio y alcance de C1-a. |
| C2 / C2-a | Preparado | La incorporacion diferida exige referencia de convocatoria o resolucion; puede modelarse como excepcion de poblacion. | `EvaluacionCurricularV2Fase6ATest`; prueba de 2027-2 sin nota ni marcador. | Campo institucional de convocatoria, fecha o condicion. |
| Q2 | Conforme en contrato | Plan, ambito, catalogo y seriacion entran por contexto y parametros; no dependen de ids PE2024. | `PreparacionDecisionesPendientesV2Test#q2MapaPe2027SinteticoNoDependeDeIdsPe2024`. | Carga productiva de mapas y seriacion PE2027/Seminarios. |
| Q3 / G3 | Pendiente de dato | RN-8.1 conserva `PENDIENTE_POR_SERIACION`; la confirmacion tardia no se infiere. | `PreparacionDecisionesPendientesV2Test#cierreDeActaSinCalificacionMantieneInterbloquePendientePorSeriacion`. | Fecha institucional de confirmacion o ajuste por periodo/bloque. |
| Q4 | Conforme | La B2 pendiente cuenta una sola vez en minimo y maximo de carga inicial; la excepcionalidad del minimo se aplica tras cierre. | `PreparacionDecisionesPendientesV2Test#rn43B2PendienteCuentaSiempreEnMinimoYMaximo`. | Ninguna para el maximo; la ventana real de ajuste depende de G3. |
| Q5 | Conforme | Recursamiento de quinto se trata como regularizacion, incluido RN-6.4. | `PreparacionDecisionesPendientesV2Test#q5RecursamientoDeQuintoPuedePrepararseComoRutaRegularizacion`. | Ninguna. |
| Q6 | Conforme | Cero definitivo sin baja es reprobacion. Los datos incompletos quedan en revision; no alteran los adeudos confirmados. | `PreparacionDecisionesPendientesV2Test#rn22CeroSinBajaSiempreEsReprobacionDefinitiva`, `Bloque6Rn20261119Test#rn22CuatroAdeudosConfirmadosNoSeAnulanPorUnResultadoEnRevision`. | Saneamiento institucional de ceros o vacias, sin alterar el criterio normativo vigente. |
| Q7 | Conforme | Desde quinto, con 1 a 3 adeudos, se permiten optativas requeridas faltantes despues de prioridad y dentro de carga. | `Bloque3OfertaYPreseleccionRn20261106Test#referenciaCincoAOchoPermiteOptativaFaltanteTrasPrioridadConUnoATresAdeudos`. | Ninguna. |
| Q10 | Preparado | Medida vigente con alcance comprobable restringe; vencida, revocada o no registrada se retira al reevaluar. Sin vigencia queda en revision. | Pruebas de medida vigente, vencida y ausente de Bloque 4/6. | Fuente de resoluciones y medidas administrativas. |
| Q11 | Conforme | El bloque se toma de la oferta autorizada, incluso con baja o recursamiento; no existe bloque fijo. | `Bloque3OfertaYPreseleccionRn20261106Test` y contrato de parametros V2. | Calidad de la oferta institucional por periodo/bloque. |
| Q12 | Parcial | El contrato conserva `idAdeudoOptativaOriginal` y `sustitucionesUtilizadas`; sin relacion verificable no infiere una cadena. | `Bloque6Rn20261119Test#rn711ExponeSustitucionesSoloCuandoLaFuenteLasAcredita`. | Relacion original-sustituta y formalizacion de inscripcion. |
| Q13 | Pendiente de dato | Una excepcion puede registrar referencia y vigencia, sin convertir una cuarta oportunidad en regla ordinaria. | Contrato de excepciones y trazabilidad RN-9.5. | Definicion de Inscripcion/DAEAE para registrar la oportunidad excepcional. |

## Cierre de preguntas previamente resueltas

| Tema | Implementacion | Evidencia |
| --- | --- | --- |
| Q8, baja aplicada | Solo `contabilizar=1`, persona, grupo y periodo activan efecto academico. | `InscripcionRepository#consultaIntentosCurricularesV2`; control agregado de 3,181 enlaces aplicados y cero sin evento/periodo. |
| Q9, baja por seriacion | No se infiere una baja: RN-8.1 conserva el estado pendiente y la causa real. | `EvaluacionCurricularV2Fase8ATest`. |
| Extraordinarios | No existen en los PE revisados; el contrato preserva proteccion historica sin sumarlos. | Diagnostico de historial y pruebas Bloque 2. |
