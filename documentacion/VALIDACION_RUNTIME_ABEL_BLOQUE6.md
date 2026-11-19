# Validacion Runtime Abel - Bloque 6 RN 20261119

## Artefacto

| Dato | Valor |
| --- | --- |
| Rama | `feature/desacople_motor_historico` |
| Commit de código y WAR | `ec2a3c09` |
| WAR | `codigoFuente/gestorWeb/target/plataforma.war` |
| Tamaño | `82,750,278` bytes |
| SHA-256 | `9af687c130729018cadb3355e054634b86c55954732bc9fa4ea4898ac2526236` |
| Modo requerido | Sombra; no formaliza inscripciones ni modifica bajas, grupos, oferta o calificaciones. |

## Despliegue

1. Compilar el WAR del commit indicado con `mvn -q -f codigoFuente/gestorWeb/pom.xml -DskipTests package`.
2. Verificar `sha256sum codigoFuente/gestorWeb/target/plataforma.war` y registrar el valor junto con el commit.
3. Respaldar el WAR actualmente desplegado conforme al procedimiento del ambiente.
4. Copiar el artefacto a la ruta configurada de WildFly y esperar la confirmacion de despliegue en `server.log`.
5. Iniciar sesion con una cuenta de prueba autorizada. No usar ni capturar datos personales en la evidencia.

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
| `V2_COMUNICADO_INSTITUCIONAL_TEXTO` | Texto institucional configurado |
| `V2_COMUNICADO_INSTITUCIONAL_REFERENCIA` | `Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v6, secciones A y C` |
| `V2_COMUNICADO_INSTITUCIONAL_VIGENCIA` | `2027-1` |

Q6 y Q11 no son propiedades variables: el motor los normaliza a `REPROBACION` y `OFERTA_AUTORIZADA`, respectivamente, por las RN vigentes.

## Consulta de expedientes de prueba

La siguiente consulta es solo lectura. Devuelve exclusivamente identificadores internos y la categoria; no obtiene nombre, matricula, CURP, correo ni calificaciones individuales. La seriacion interbloque no tiene una fuente relacional en la base actual, por lo que ese caso debe validarse con GC sintetico.

```sql
SELECT 'NULL_ACTA_CERRADA' AS caso, rgp.id_persona_participante AS id_persona
FROM rel_grupo_participante rgp
JOIN tbl_grupos g ON g.id = rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento = g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
WHERE fd.id_plan IN (3,4,5,6,7,11)
  AND rgp.calificacion_final IS NULL AND COALESCE(g.acta_cerrada,0) = 1
GROUP BY rgp.id_persona_participante
UNION ALL
SELECT 'OPORTUNIDAD_EN_CURSO', rgp.id_persona_participante
FROM rel_grupo_participante rgp
JOIN tbl_grupos g ON g.id = rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento = g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
WHERE fd.id_plan IN (3,4,5,6,7,11)
  AND rgp.calificacion_final IS NULL AND COALESCE(g.acta_cerrada,0)=0
  AND pi.fecha_finalizacion >= CURDATE()
GROUP BY rgp.id_persona_participante
SELECT 'CUATRO_O_MAS_ADEUDOS', t.id_persona_participante
FROM (
  SELECT rgp.id_persona_participante, fd.id_programa
  FROM rel_grupo_participante rgp
  JOIN tbl_grupos g ON g.id=rgp.id_grupo
  JOIN tbl_eventos e ON e.id_evento=g.id_evento
  JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
  LEFT JOIN rel_persona_bajas b ON b.id_persona=rgp.id_persona_participante
    AND b.id_grupo=rgp.id_grupo AND b.contabilizar=1
  WHERE fd.id_plan IN (3,4,5,6,7,11)
  GROUP BY rgp.id_persona_participante, fd.id_programa
  HAVING MAX(CASE WHEN b.id_baja IS NULL AND rgp.calificacion_final BETWEEN 0 AND 59 THEN 1 ELSE 0 END)=1
) t
GROUP BY t.id_persona_participante
HAVING COUNT(*) >= 4;
```

El caso **recursamiento de quinto** no se consulta directamente aquí porque `tbl_ficha_descriptiva_programa` no contiene el semestre curricular. Debe obtenerse con la misma fuente de mapa que alimenta `unidadesMapaPlan` y cruzarse por `id_programa`; no se infiere desde oferta ni desde nombre. El caso **pendiente interbloque** tampoco tiene fuente relacional disponible en esta base y se valida con el GC sintético.

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
