# Panel Asistente Virtual V2

## Objetivo
Homologar el recuadro `Asistente virtual` de `Situación académica`, `UD prioritarias` y `Orientación` para que las tres pestañas consuman una sola estructura V2.

## Contrato único
El contrato vive en `FichaIntegralCasoDTO` como `panelAsistenteVirtual`.

Estructura:
- `perfil`
- `escenario`
- `mensajeOperativo`
- `etiquetaResumen`
- `resumenTitulo`
- `resumenContenido`
- `acciones[]`
  - `clave`
  - `titulo`
  - `respuesta`
  - `orden`

## Escenarios funcionales cubiertos
- `REGULAR`
- `IRREGULAR`
- `SERIACION`
- `BAJA`

## Cómo se arma hoy
1. `TablaCurricularAsistidaBean` envía a V2:
- periodo objetivo visible para el estudiante
- conteo de no acreditadas
- conteo de bloqueadas por seriación
- conteo de antecedentes pendientes
- conteo de pendientes por baja

2. `AsistenteCurricularV2FacadeImpl`:
- resuelve el escenario
- arma el panel base por perfil y escenario
- aplica sobrescrituras desde `mensajesContextuales` si existen

## Tipos de mensaje contextual soportados
Para estudiante:
- `PANEL_OP`
- `PANEL_RES`
- `PANEL_QDE`
- `PANEL_SER`
- `PANEL_SIM`
- `PANEL_CON`

Para gestor:
- `PANEL_OP`
- `PANEL_RES`
- `PANEL_RTEC`
- `PANEL_REGLA`
- `PANEL_EVID`
- `PANEL_MSG`

## Regla de prioridad del contenido
1. Si existe mensaje contextual con `tipo` compatible con el panel, ese mensaje sobrescribe la plantilla Java.
2. Si no existe, se usa la plantilla base del escenario.
3. Si no existe ficha V2, el bean conserva fallback local.

## Regla clave para una base de conocimiento poco madura
No todos los textos conviene moverlos a BD desde el inicio.

Actualmente el backend Java es quien sí conoce y calcula en tiempo de ejecución:
- periodo inmediato objetivo (`2026-2`, `2027-1`, etc.)
- número de UD no acreditadas visibles
- número de UD bloqueadas por seriación
- número de antecedentes pendientes
- número de UD pendientes por baja

Por eso:
- si quieres conservar contadores y periodos exactos, deja que el escenario base de Java siga construyendo `resumenContenido` y respuestas dinámicas
- usa mensajes en BD para pulir tono institucional, criterios del experto académico, lenguaje para gestor y textos estables
- no sustituyas con BD un texto que dependa de números reales, salvo que aceptes un texto genérico o que después se implemente interpolación de variables

Recomendación operativa:
- Fase 1: poblar en BD mensajes estables del panel, sobre todo para gestor y para acciones no numéricas
- Fase 2: cuando la base sea más madura, agregar plantillas parametrizadas con tokens
- Fase 3: resolver tokens desde V2 antes de renderizar

Tokens recomendados para evolución futura:
- `${periodo_objetivo}`
- `${ud_no_acreditadas}`
- `${ud_bloqueadas}`
- `${ud_antecedentes}`
- `${ud_pendientes_baja}`

## Convención recomendada para `clave`
Formato sugerido:

`<PERFIL>_<PERIODO>_<ESCENARIO>_<SECCION>_<FECHA>`

Ejemplos:
- `EST_INS_REGULAR_PANEL_RES_20260803`
- `GES_INS_SERIACION_PANEL_REGLA_20260803`

## Flujo recomendado para alimentar conocimiento sin esperar el caso real
1. El experto académico define:
- escenario
- condición de entrada
- mensaje operativo
- resumen
- respuestas de las 4 acciones del estudiante o del gestor

2. Se identifica el patrón al que debe colgarse:
- si ya existe, se reutiliza
- si no existe, se crea en `rel_patron_caso_academico`

3. Se insertan mensajes en `cat_mensaje_institucional_contextual`

4. Se relacionan con el patrón en `rel_patron_mensaje_contextual`

5. Se valida cobertura con:
- [30_asistente_curricular_v2_panel_virtual_auditoria_20260803.sql](/home/abelinux/gestor/repo/gestor/codigoFuente/bd/30_asistente_curricular_v2_panel_virtual_auditoria_20260803.sql)

## Criterio mínimo por patrón
Para `ESTUDIANTE / INSCRIPCION`:
- 1 mensaje operativo
- 1 resumen
- 4 respuestas de acción

Para `GESTOR / INSCRIPCION`:
- 1 mensaje operativo
- 1 resumen
- 4 respuestas de acción

## Recomendación operativa
No cargar mensajes genéricos primero.  
Conviene poblar primero estos cuatro patrones:
- trayectoria regular
- irregularidad por no acreditadas
- seriación pendiente
- reincorporación

Después ampliar por:
- alto riesgo severo
- solo optativas habilitadas
- continuidad del mismo año
- omisión documentada

## Estrategia de carga recomendada
1. Cargar siempre el paquete mínimo por escenario:
- mensaje operativo
- resumen
- 4 acciones del panel

2. Mantener homologado el panel entre pestañas:
- `Situación académica`
- `UD prioritarias`
- `Orientación`

3. Tratar al panel como contrato único por perfil:
- para `ESTUDIANTE`, orientar decisión y reinscripción
- para `GESTOR`, documentar interpretación, regla y evidencia

4. Si el experto académico pide un ajuste que sólo cambia redacción y no cambia lógica:
- insertarlo en `cat_mensaje_institucional_contextual`
- relacionarlo al patrón
- no tocar XHTML ni Bean

5. Si el ajuste depende de números, periodos o conteos:
- primero validar si ya lo resuelve Java
- si no lo resuelve, modelar el nuevo dato en `ContextoAsistenteCurricularV2DTO`
- después decidir si el mensaje va en Java o si se evoluciona a plantillas con tokens

## Matriz mínima por escenario

### Estudiante
- `REGULAR`
- `IRREGULAR`
- `SERIACION`
- `BAJA`

### Gestor
- `REGULAR`
- `IRREGULAR`
- `SERIACION`
- `BAJA`

Cada combinación debe tener:
- 1 `PANEL_OP`
- 1 `PANEL_RES`
- 4 mensajes de acción

## Qué script usar
- [29_asistente_curricular_v2_panel_virtual_seed_20260803.sql](/home/abelinux/gestor/repo/gestor/codigoFuente/bd/29_asistente_curricular_v2_panel_virtual_seed_20260803.sql)
  Carga inicial de patrones y mensajes base.
- [30_asistente_curricular_v2_panel_virtual_auditoria_20260803.sql](/home/abelinux/gestor/repo/gestor/codigoFuente/bd/30_asistente_curricular_v2_panel_virtual_auditoria_20260803.sql)
  Revisión de cobertura y huecos.
- [31_asistente_curricular_v2_panel_virtual_template_carga_20260803.sql](/home/abelinux/gestor/repo/gestor/codigoFuente/bd/31_asistente_curricular_v2_panel_virtual_template_carga_20260803.sql)
  Plantilla editable para capturar nuevos paquetes de conocimiento del experto académico.

## Scripts asociados
- [29_asistente_curricular_v2_panel_virtual_seed_20260803.sql](/home/abelinux/gestor/repo/gestor/codigoFuente/bd/29_asistente_curricular_v2_panel_virtual_seed_20260803.sql)
- [30_asistente_curricular_v2_panel_virtual_auditoria_20260803.sql](/home/abelinux/gestor/repo/gestor/codigoFuente/bd/30_asistente_curricular_v2_panel_virtual_auditoria_20260803.sql)
- [31_asistente_curricular_v2_panel_virtual_template_carga_20260803.sql](/home/abelinux/gestor/repo/gestor/codigoFuente/bd/31_asistente_curricular_v2_panel_virtual_template_carga_20260803.sql)
