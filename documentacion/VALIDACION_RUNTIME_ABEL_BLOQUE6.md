# Validacion Runtime Abel - Bloque 6 RN 20261119

## Artefacto

| Dato | Valor |
| --- | --- |
| Rama | `feature/desacople_motor_historico` |
| Commit de código | `ec2a3c09` |
| WAR autorizado | Artefacto entregado a Abel, identificado por la huella siguiente; no usar el `target` local como sustituto. |
| Tamaño autorizado | `82,750,278` bytes |
| SHA-256 | `9af687c130729018cadb3355e054634b86c55954732bc9fa4ea4898ac2526236` |
| Modo requerido | Sombra; no formaliza inscripciones ni modifica bajas, grupos, oferta o calificaciones. |

**Control de artefacto:** el `target/plataforma.war` presente en este espacio de trabajo no coincide con la huella autorizada y no es desplegable para esta validación. Abel debe usar exclusivamente la copia entregada con la huella `9af687c130729018cadb3355e054634b86c55954732bc9fa4ea4898ac2526236`.

## Despliegue

1. Abel debe recibir el WAR indicado en la tabla de artefacto. **No debe recompilarlo.**
2. Ejecutar `sha256sum plataforma.war`. El resultado debe ser exactamente `9af687c130729018cadb3355e054634b86c55954732bc9fa4ea4898ac2526236`.
3. Si el hash no coincide, detener el despliegue, conservar el artefacto recibido y reportar la diferencia. No sustituirlo por un WAR recompilado localmente.
4. Respaldar el WAR actualmente desplegado conforme al procedimiento del ambiente y copiar el artefacto validado a la ruta configurada de WildFly.
5. Esperar la confirmacion de despliegue en `server.log`. Iniciar sesion con una cuenta de prueba autorizada. No usar ni capturar datos personales en la evidencia.

## Propiedades JVM V2

| Propiedad | Valor por omision |
| --- | --- |
| `REGLAS_V2_VERSION` | `RN-PE2024-2027-act-20261119` |
| `EVALUACION_V2_HABILITADA` | `true` |
| `REGLAS_V2_MODO_SOMBRA` | `true` |
| `CTR01_PERIODO_INICIO` | `2027-1` |
| `CTR01_SEMESTRE_OBJETIVO` | `5` |
| `V2_CALIFICACION_MINIMA_APROBATORIA` | `60` |
| `V2_OPORTUNIDADES_POR_UD` | `3` |
| `V2_CARGA_MINIMA` | `3` |
| `V2_CARGA_MAX_REGULAR` | `8` |
| `V2_CARGA_MAX_IRREGULAR` | `6` |
| `V2_UMBRAL_REZAGO_SEVERO` | `4` |
| `V2_MAX_SEMESTRES_BAJA_TEMPORAL` | `3` |
| `V2_OPTATIVAS_REQUERIDAS` | `8` |
| `V2_CATALOGO_OPTATIVAS_AMBITO` | `PE2024/2027` |
| `V2_CATALOGO_OPTATIVAS` | Catalogo Tabla 2 configurado en el servicio V2 |
| `V2_DECISIONES_PENDIENTES_DOCUMENTO` | `Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v6` |
| `V2_DECISIONES_PENDIENTES_VIGENCIA` | `2026-11-19` |
| `V2_COMUNICADO_INSTITUCIONAL_PERIODO` | `2027-1` |
| `V2_COMUNICADO_INSTITUCIONAL_TEXTO` | `Esta simulación aplica la normativa y reglas generales vigentes. En un proceso de Inscripción efectivo puede aplicar autorizaciones institucionales específicas. La inscripción efectiva puede diferir del resultado que se muestra aquí.` |
| `V2_COMUNICADO_INSTITUCIONAL_REFERENCIA` | `Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v6, secciones A y C` |
| `V2_COMUNICADO_INSTITUCIONAL_VIGENCIA` | `2027-1` |

Q6 y Q11 no son propiedades variables: el motor los normaliza a `REPROBACION` y `OFERTA_AUTORIZADA`, respectivamente, por las RN vigentes. Los valores efectivos fijos adicionales son: Q4 `CUENTA_MIN_Y_MAX`, Q5 `REGULARIZACION` y `REGULARIZACION_64`, y Q7 `PERMITIR_TRAS_PENDIENTES`.

## Consulta de expedientes de prueba

La siguiente consulta es solo lectura y fue ejecutada en `des_sisi_gestor` con MySQL `5.7.42-0ubuntu0.18.04.1` el `2026-11-19 10:49:15`. Devuelve exclusivamente identificadores internos y la categoria; no obtiene nombre, matricula, CURP, correo ni calificaciones individuales. Cada caso limita su salida a cinco expedientes. Resultado de la ejecucion: cinco filas para `NULL_ACTA_CERRADA`, cinco para `TERCERA_OPORTUNIDAD_EN_CURSO` y cinco para `CUATRO_O_MAS_ADEUDOS`.

El grano intermedio es persona + UD (`fd.id_programa`) + periodo. La baja aplicada se reconoce exclusivamente por `rel_persona_bajas.id_baja` ligado por persona y grupo, con `contabilizar=1`. La tercera oportunidad usa el cierre academico `MAX(tbl_eventos.fecha_final)` por plan y periodo; no usa la ventana administrativa `tbl_periodos_inscripcion.fecha_finalizacion`.

```sql
SELECT * FROM (
  SELECT 'NULL_ACTA_CERRADA' AS caso, rgp.id_persona_participante AS id_persona
  FROM rel_grupo_participante rgp
  JOIN tbl_grupos g ON g.id = rgp.id_grupo
  JOIN tbl_eventos e ON e.id_evento = g.id_evento
  JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
  WHERE fd.id_plan IN (3,4,5,6,7,11)
    AND rgp.calificacion_final IS NULL AND COALESCE(g.acta_cerrada,0) = 1
  GROUP BY rgp.id_persona_participante
  LIMIT 5
) null_acta
UNION ALL
SELECT * FROM (
  SELECT 'TERCERA_OPORTUNIDAD_EN_CURSO' AS caso, actual.id_persona
  FROM (
    SELECT rgp.id_persona_participante AS id_persona, fd.id_programa,
           SUBSTRING_INDEX(e.cve_evento_cap,'-',-2) AS periodo,
           MAX(rgp.calificacion_final) AS calificacion_final,
           MAX(COALESCE(g.acta_cerrada,0)) AS acta_cerrada,
           MAX(CASE WHEN baja.id_baja IS NOT NULL THEN 1 ELSE 0 END) AS baja_aplicada
    FROM rel_grupo_participante rgp
    JOIN tbl_grupos g ON g.id=rgp.id_grupo
    JOIN tbl_eventos e ON e.id_evento=g.id_evento
    JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
    LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante
      AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
    WHERE fd.id_plan IN (3,4,5,6,7,11)
    GROUP BY rgp.id_persona_participante, fd.id_programa, SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)
  ) actual
  JOIN (
    SELECT fd2.id_plan, SUBSTRING_INDEX(e2.cve_evento_cap,'-',-2) AS periodo,
           MAX(e2.fecha_final) AS cierre_academico
    FROM tbl_eventos e2
    JOIN tbl_ficha_descriptiva_programa fd2 ON fd2.id_programa=e2.id_programa
    WHERE fd2.id_plan IN (3,4,5,6,7,11)
    GROUP BY fd2.id_plan, SUBSTRING_INDEX(e2.cve_evento_cap,'-',-2)
  ) cierre ON cierre.id_plan=(SELECT fd3.id_plan FROM tbl_ficha_descriptiva_programa fd3
                               WHERE fd3.id_programa=actual.id_programa)
       AND cierre.periodo=actual.periodo
  JOIN (
    SELECT previo.id_persona, previo.id_programa, actual2.periodo AS periodo_actual,
           COUNT(*) AS fallidos_previos
    FROM (
      SELECT rgp3.id_persona_participante AS id_persona, fd3.id_programa,
             SUBSTRING_INDEX(e3.cve_evento_cap,'-',-2) AS periodo,
             MAX(rgp3.calificacion_final) AS calificacion_final,
             MAX(CASE WHEN baja3.id_baja IS NOT NULL THEN 1 ELSE 0 END) AS baja_aplicada
      FROM rel_grupo_participante rgp3
      JOIN tbl_grupos g3 ON g3.id=rgp3.id_grupo
      JOIN tbl_eventos e3 ON e3.id_evento=g3.id_evento
      JOIN tbl_ficha_descriptiva_programa fd3 ON fd3.id_programa=e3.id_programa
      LEFT JOIN rel_persona_bajas baja3 ON baja3.id_persona=rgp3.id_persona_participante
        AND baja3.id_grupo=rgp3.id_grupo AND baja3.contabilizar=1
      WHERE fd3.id_plan IN (3,4,5,6,7,11)
      GROUP BY rgp3.id_persona_participante, fd3.id_programa, SUBSTRING_INDEX(e3.cve_evento_cap,'-',-2)
    ) previo
    JOIN (
      SELECT rgp4.id_persona_participante AS id_persona, fd4.id_programa,
             SUBSTRING_INDEX(e4.cve_evento_cap,'-',-2) AS periodo
      FROM rel_grupo_participante rgp4
      JOIN tbl_grupos g4 ON g4.id=rgp4.id_grupo
      JOIN tbl_eventos e4 ON e4.id_evento=g4.id_evento
      JOIN tbl_ficha_descriptiva_programa fd4 ON fd4.id_programa=e4.id_programa
      WHERE fd4.id_plan IN (3,4,5,6,7,11)
      GROUP BY rgp4.id_persona_participante, fd4.id_programa, SUBSTRING_INDEX(e4.cve_evento_cap,'-',-2)
    ) actual2 ON actual2.id_persona=previo.id_persona
       AND actual2.id_programa=previo.id_programa AND previo.periodo<actual2.periodo
    WHERE previo.calificacion_final BETWEEN 0 AND 59 AND previo.baja_aplicada=0
    GROUP BY previo.id_persona, previo.id_programa, actual2.periodo
  ) previos ON previos.id_persona=actual.id_persona
       AND previos.id_programa=actual.id_programa AND previos.periodo_actual=actual.periodo
       AND previos.fallidos_previos=2
  WHERE actual.calificacion_final IS NULL AND actual.acta_cerrada=0
    AND actual.baja_aplicada=0 AND cierre.cierre_academico>=CURDATE()
  GROUP BY actual.id_persona
  LIMIT 5
) tercera
UNION ALL
SELECT * FROM (
  SELECT 'CUATRO_O_MAS_ADEUDOS' AS caso, adeudo.id_persona
  FROM (
    SELECT h.id_persona, h.id_programa
    FROM (
      SELECT rgp.id_persona_participante AS id_persona, fd.id_programa,
             SUBSTRING_INDEX(e.cve_evento_cap,'-',-2) AS periodo,
             MAX(rgp.calificacion_final) AS calificacion_final,
             MAX(CASE WHEN baja.id_baja IS NOT NULL THEN 1 ELSE 0 END) AS baja_aplicada
      FROM rel_grupo_participante rgp
      JOIN tbl_grupos g ON g.id=rgp.id_grupo
      JOIN tbl_eventos e ON e.id_evento=g.id_evento
      JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
      LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante
        AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
      WHERE fd.id_plan IN (3,4,5,6,7,11)
      GROUP BY rgp.id_persona_participante, fd.id_programa, SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)
    ) h
    GROUP BY h.id_persona, h.id_programa
    HAVING MAX(CASE WHEN h.calificacion_final>=60 THEN 1 ELSE 0 END)=0
       AND MAX(CASE WHEN h.calificacion_final BETWEEN 0 AND 59 AND h.baja_aplicada=0 THEN 1 ELSE 0 END)=1
  ) adeudo
  GROUP BY adeudo.id_persona
  HAVING COUNT(*)>=4
  LIMIT 5
) cuatro_adeudos;
```

El caso **recursamiento de quinto** no se consulta directamente aquí porque `tbl_ficha_descriptiva_programa` no contiene el semestre curricular. Debe obtenerse con la misma fuente de mapa que alimenta `unidadesMapaPlan` y cruzarse por `id_programa`; no se infiere desde oferta ni desde nombre. El caso **pendiente interbloque** tampoco tiene fuente relacional disponible en esta base y se valida con el GC sintético.

`UNA_A_TRES_ADEUDOS_REFERENCIA_5_O_MAS` no se incorpora a la consulta: el historial canónico no contiene el semestre curricular ni la estructura del mapa. El semestre procede del servicio de Malla (`unidadesMapaPlan` / `InscripcionMateriasDTO.estructura`), por lo que una consulta SQL aislada no puede acreditarlo sin reconstruir esa fuente. Validar Q7 con una cuenta de prueba de la matriz que tenga referencia 5--8 o con el caso sintético; no interpretar una ausencia de filas como cero casos.

## Acceso a las pantallas

El acceso directo está implementado para el rol activo `Estudiante`: navega con la sesión de la propia persona y el contexto toma su `id_persona`. Para un rol distinto, `MenuGestorBean` dirige primero a la búsqueda de expediente; el flujo debe seleccionar el expediente autorizado y cargar `TrayectoriaAcademicaContextoBean.idPersonaObjetivo` antes de abrir las cuatro pantallas. No hay una cuenta RPE/DAEAE ni una contraseña definida en el código para este paquete. Abel debe usar una cuenta institucional con permiso vigente de consulta de expediente, confirmada por RPE/DAEAE, o la sesión de la cuenta de prueba del estudiante correspondiente al caso CAMPUS 2.

| Pantalla | Ruta funcional de validación |
| --- | --- |
| Mapa curricular | Sesión de estudiante, o búsqueda autorizada de expediente y después `navegaMallaCurricularAlumno`. |
| Asistente / Situación académica / Simulador | Sesión de estudiante, o búsqueda autorizada de expediente y después `navegaTablaCurricularAsistida`. Las tres son pestañas de la misma vista. |
| Asistente virtual | Abrir desde la misma sesión y contexto de expediente; verificar que conserva `idPersonaObjetivo`. |

Los casos **pendiente interbloque** y **recursamiento de quinto** no tienen una fuente garantizada en la base de pruebas. Deben validarse con una cuenta de prueba que RPE/DAEAE confirme como los alias `SER-01` y `AV-xx` de CAMPUS 2; si no se dispone de esas cuentas o de datos controlados equivalentes, quedan validados únicamente por las pruebas sintéticas GC correspondientes.

## Pruebas menores incorporadas

| Prueba | Resultado | Evidencia |
| --- | --- | --- |
| `Bloque6Rn20261119Test#rn711ConservaSustitucionFormalAnteBajaPosteriorYRecursamiento` | Verde | Una relación formal con una sustitución conserva el contador cuando la sustituta tiene una baja posterior o se recursa; el recursamiento no aumenta las sustituciones. |
| Tercera sustitución formal | Pendiente de contrato | El DTO actual solo recibe el total de sustituciones ya formalizadas; no recibe el movimiento candidato ni su estado de formalización. No se infiere una tercera sustitución desde un recursamiento. Para rechazarla en el motor se requiere que la fuente entregue el movimiento candidato formalizado y su identidad de adeudo. No bloquea la validación del WAR actual. |
| `PreparacionDecisionesPendientesV2Test#rn43AjusteYConfirmacionPosteriorNoExcedenMaximo` | Verde | La carga de ajuste y su nueva evaluación conservan tres UD ante un máximo de tres; no aumenta por una confirmación posterior. |
| `Bloque4TrayectoriaMedidaRn20261106Test#gc13MedidaRevocadaNoConservaRestriccionVigente` | Verde | La fuente canónica representa una medida revocada como no registrada; la reevaluación no conserva motivos de medida vigente ni de vigencia incompleta. |

Las tres pruebas ejecutaron junto con la cobertura existente: `24` pruebas, `0` fallas, `0` errores y `0` omitidas. Son cambios de pruebas y documentación; no autorizan sustituir el WAR entregado ni modificar la huella indicada en este documento.

## Lista de verificacion

| Caso | Mapa curricular | Asistente y Situacion academica | Simulador | Asistente virtual | Escritorio y movil |
| --- | --- | --- | --- | --- | --- |
| Oportunidad en curso | Estado `EN_CURSO`, sin agotamiento anticipado | Distingue oportunidad en curso de reprobacion | No propone cuarta oportunidad | Explica que el resultado no concluye | Ver etiqueta completa, sin recorte ni superposicion |
| NULL con acta cerrada o periodo concluido | No aparece como aprobada o reprobada | Advierte informacion en revision | Restriccion revisable, no carga autorizada | Mensaje de dato pendiente | Mismo significado en ambas resoluciones |
| Pendiente interbloque | Consecuente `Pendiente por seriacion` | No se muestra como inscrita confirmada | Cuenta una vez en min/max inicial | Describe antecedente pendiente | Accesible sin scroll horizontal global |
| Recursamiento de quinto | Muestra adeudo e intentos separados | Regularizacion prioritaria conforme RN-6.1.5 | Respeta oferta, oportunidades y carga | Explica regularizacion sin prometer inscripcion | Revisar foco y lectura de etiquetas |
| Cuatro o mas adeudos | Prioridades sin duplicar UD | Situacion irregular y carga maxima 6 | Solo regularizacion | Orientacion consistente | Sin datos personales visibles en captura |
| Comunicado 2027-1 | No se muestra en Malla | No duplica el aviso | Una sola advertencia si aplica C1/C2 y no hay excepcion | No convierte advertencia en autorizacion | Confirmar una sola ocurrencia |

## Evidencia anonima

1. Antes de capturar, ocultar encabezados, paneles o URL que contengan nombre, matricula, correo, CURP o identificadores personales.
2. Usar el identificador de caso y un alias de prueba no reversible, por ejemplo `CASO-A`.
3. Registrar fecha, tamano de pantalla, pantalla validada y resultado esperado/obtenido fuera de la captura.
4. No incluir consola, consultas SQL con filas individuales ni archivos de sesion.
