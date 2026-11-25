# Paquete de contexto funcional - Motor Curricular V2

## Propósito y corte

Este documento permite comparar el comportamiento **actual** del Motor Curricular V2 contra las RN PE2024-PE2027 actualizadas al 2026-11-05, sin requerir lectura de código. Describe lo que el sistema hace hoy; no declara que una regla esté aprobada por el área académica.

| Dato | Valor |
| --- | --- |
| Repositorio / rama | `gestor` / `feature/desacople_motor_historico` |
| Commit revisado | `7cbb683880ca153bde48bf765f5f0c9758c07ec8` |
| Fecha de revisión | 2026-11-06 |
| Versión técnica informada por el motor | `PE2027-V1` |
| Modo de operación | Sombra: informa decisiones, no modifica datos escolares |
| Evidencia de pruebas ejecutada | Suite V2 de `basegestor`: 758 pruebas, 0 fallas, 0 errores, 0 omitidas |
| Documento RN de contraste | `P2024-2027- Reglas de negocio_Octubre 2026.docx` (disponible localmente; su contenido normativo debe validarlo el analista) |

> **Importante:** `PE2027-V1` es una etiqueta técnica configurable. No prueba por sí misma vigencia institucional, plan, cohorte ni población a la que aplican las RN.

Referencias técnicas de apoyo, no necesarias para la lectura funcional: `EvaluacionCurricularV2ServiceImpl`, `ContextoEvaluacionCurricularV2AdapterImpl` y `ConfiguracionEvaluacionCurricularV2ServiceImpl`.

## 1. Alcance y responsabilidades

### Qué hace el Motor V2

El motor recibe un contexto curricular ya reunido y genera un dictamen descriptivo. Para cada UD puede indicar estado académico, prioridad, restricción, motivos y reglas relacionadas. Además calcula situación académica, carga mínima y máxima, prioridades de regularización, seriación, control de avance a quinto, reincorporación, optativas, últimos semestres, egreso académico y trazabilidad.

El resultado acumula causas: por ejemplo, una UD puede estar académicamente restringida por seriación y, al mismo tiempo, no tener oferta. No reduce el expediente a una sola explicación.

### Qué no hace

| Responsabilidad fuera del motor | Responsable principal | Tratamiento actual |
| --- | --- | --- |
| Crear, modificar o cancelar inscripción/reinscripción | Proceso de Inscripción | El motor no inserta ni actualiza inscripciones. |
| Reservar cupo, abrir grupo o publicar oferta | SIGE / Oferta / Grupos | Sólo interpreta disponibilidad, grupo y cupo cuando llegan en el contexto. |
| Aplicar una baja, aprobarla o emitir reincorporación | Proceso institucional de bajas | Puede describir antecedentes recibidos; no autoriza ni aplica movimientos. |
| Capturar calificaciones, NP, rectificaciones o historial | SIGE / Control escolar | Consume su resultado normalizado; no altera el historial. |
| Resolver equivalencias o migraciones entre planes | Área académica / Control escolar | No hay una correspondencia institucional integrada que permita inferirlas por nombre. |
| Emitir certificación, titulación o servicio social | Áreas responsables y resoluciones institucionales | El motor sólo evalúa requisitos académicos que estén presentes en el contexto. |
| Autorizar excepciones o dictámenes extraordinarios | Instancia académica competente | Puede señalar que se requiere resolución, no concederla. |

### Consumidores

| Componente | Pregunta que responde | Qué muestra | Acción permitida | Datos V2 que usa | Qué conserva o decide fuera de V2 |
| --- | --- | --- | --- | --- | --- |
| Mapa curricular | “¿Cómo está cada UD de mi plan?” | Estado, prioridad, restricción, oferta, seriación, motivos, iconos y detalle contextual. | Consulta y navegación por la malla. | Resultado por UD, estado normalizado, motivos, prioridad, seriación y oferta. | Organización visual, créditos acumulados, bloques, iconos, colores, filtros y elementos informativos. |
| Asistente de inscripción y orientación educativa | “¿Qué debo atender y qué me conviene hacer?” | Situación académica, UD prioritarias, expectativa de logro, proyección de carga, explicaciones y alertas. | Consultar escenarios hipotéticos y enviar una propuesta al simulador. | Situación, carga, reprobadas, prioridades, estados, seriación, oferta y resultados de escenarios V2. | Narrativa, agrupación, indicadores, cortes de datos y disponibilidad visual. No formaliza inscripción. |
| Simulador / validador | “¿La carga que propongo es académicamente válida?” | Carga seleccionada, máximo, restricciones, motivos y resultado por UD. | Probar una selección hipotética; no reserva ni registra. | Resultado V2 completo, especialmente carga, seriación, prioridades y motivos. | Validaciones complementarias de selección ya existentes, por ejemplo duplicados y optativas por bloque, cuando V2 no cubre exactamente el concepto. |
| Asistente virtual | “¿Cuál es mi diagnóstico y orientación?” | Resumen, mensajes, alertas, acciones y recomendaciones. | Consulta y acceso a pantallas de apoyo. | Cuando se integra por el contrato asistido, situación, prioridades, restricciones y motivos V2. | Tono, orden narrativo, mensajes operativos y funcionalidades históricas no académicas. |

### Diferencia frente a la inscripción real

La inscripción real sigue validando disponibilidad operativa, periodos, grupos, cupo, selección, reglas legacy y persistencia. El Motor V2 en sombra no reemplaza esas validaciones ni cambia los resultados productivos. Por ello, una UD puede ser curricularmente habilitable y no poder inscribirse aún por falta de oferta, cupo o ventana administrativa.

## 2. Contrato del motor en lenguaje de negocio

### Entradas

| Dato recibido | Significado de negocio | Fuente habitual | Si falta |
| --- | --- | --- | --- |
| Persona, plan, versión y periodo normativo | Identifican el expediente, el plan y la versión evaluada. | Contexto de Inscripción / Asistente; parámetros de evaluación. | No debe suponerse población o vigencia; algunas decisiones quedan en revisión. |
| UD candidatas | Conjunto de UD que se quiere consultar o evaluar. | Oferta publicada, UD en curso y reprobadas agregadas por el asistente. | El motor no recibe automáticamente toda la malla ni todo el historial como UD evaluables. |
| Identidad curricular de la UD | ID, clave, tipo, semestre, bloque y créditos. | Catálogo/malla del plan y oferta. | `NO_DETERMINADO` o `REQUIERE_REVISION`; no se debe homologar por nombre. |
| Estado histórico y de periodo | Acreditada, reprobada, no cursada, baja parcial, en curso, no inscrita o no determinado. | Historial y carga vigente de SIGE. | Se conserva lo conocido; no se transforma la ausencia en reprobación. |
| Reprobación vigente y causas | Define pendientes e irregularidad. | Historial normalizado. | No puede afirmar total de reprobadas ni aplicar con certeza RN-6.4. |
| Intentos y oportunidades | Secuencia computable por UD, incluidas exclusiones por baja autorizada. | Historial de inscripciones, resultados y bajas. | No calcula oportunidades restantes como si fueran reprobaciones. |
| Oferta, grupo y cupo | Distingue habilitación curricular de disponibilidad operativa. | Oferta y grupos SIGE. | Puede mostrar `SIN_OFERTA`, `SIN_CUPO` o revisión; nunca reserva cupo. |
| Seriación | Relaciones formales entre UD: directa, indirecta, interbloque, intersemestral e interanual. | Malla/plan. | No infiere relación por cercanía de semestre ni por nombre. |
| Bajas, inactividad y reincorporación | Antecedentes y movimientos que afectan la trayectoria. | `rel_persona_bajas`, motivos, evento, periodo e inscripción vinculada. | Debe separar baja parcial, temporal, autorización y reincorporación; no los inventa. |
| Optativas y sustituciones | Catálogo requerido/adicional, ruta de adeudo y sustitutas. | Malla por plan/cohorte y relación institucional verificable. | No duplica adeudos ni inventa sustitutas. |
| Componentes finales | Requisitos formales de seminarios, módulos o proyecto terminal. | Catálogo institucional por plan/cohorte. | No emite conclusión definitiva de egreso. |

**Universo actualmente enviado al motor.** El adaptador común recibe oferta disponible, añade UD en curso ausentes de la oferta y añade reprobadas ausentes. El historial completo permanece disponible como contexto de origen, pero no todas sus UD se convierten automáticamente en decisiones V2. Este límite es relevante para cualquier RN que dependa de una UD no ofertada ni reprobada.

**Semestre objetivo actual.** Se calcula hoy como el semestre mayor entre las UD marcadas como ofertadas. Es distinto del semestre efectivo de trayectoria del estudiante. La trayectoria temporal usa inscripciones, eventos y calendario académico, pero todavía no alimenta este valor del motor.

### Salidas por UD

| Salida | Significado | Presentación esperada |
| --- | --- | --- |
| `HABILITADA` | No se detectó una restricción curricular V2 para la UD con los datos recibidos. | Malla: habilitable. Asistente: opción posible. Simulador: puede integrarse si pasa las demás validaciones. |
| `BLOQUEADA` | Existe una restricción curricular directa, por ejemplo seriación conocida. | Malla: indicador de restricción y motivo. Asistente: no recomendar como cursable. Simulador: selección no válida. |
| `PRIORITARIA` | La UD requiere atención, típicamente por reprobación vigente. | Malla y Asistente: prioridad de regularización. Simulador: se conserva al formar cargas. |
| `REQUIERE_REVISION` | Falta evidencia o hay una condición que el motor no puede concluir. | Todos: explicación prudente; no afirmar autorización ni rechazo definitivo. |

### Estados académicos de UD

| Estado | Significado | Regla de presentación |
| --- | --- | --- |
| `ACREDITADA` | Cuenta como acreditación cuando el historial lo prueba. | No se recomienda como pendiente. |
| `REPROBADA` | Existe resultado reprobatorio vigente. | Puede ser prioritaria y afectar irregularidad; recursar no borra el antecedente hasta acreditar. |
| `NO_CURSADA` | La UD no fue cursada. | No se convierte en reprobada. |
| `BAJA_PARCIAL` | Existe baja por UD. | No se convierte en reprobada ni consume oportunidades si la autorización es verificable. |
| `EN_CURSO` | Está inscrita/cursándose y no tiene resultado final. | No equivale a acreditada ni a reprobada. |
| `NO_INSCRITA` | No hay inscripción en el periodo. | No equivale a reprobación. |
| `NO_DETERMINADO` | La fuente no permite clasificar. | Mostrar revisión/dato insuficiente, nunca cero o aprobación implícita. |

### Motivos principales

| Motivo | Lectura de negocio |
| --- | --- |
| `REPROBACION_ACUMULADA` | Hay reprobación vigente que afecta situación, prioridad o carga. |
| `PENDIENTE_NO_CURSADA` | Hay pendiente por no cursamiento; no es una reprobación automática. |
| `BAJA_PARCIAL` | Hay efecto de baja por UD, separado del resultado reprobatorio. |
| `SIN_OFERTA` / `SIN_CUPO` | La UD puede tener una condición curricular distinta de su disponibilidad operativa. |
| `SERIACION_DIRECTA` / `SERIACION_INDIRECTA` | Falta acreditar un antecedente o la relación requiere revisión. |
| `LIMITE_INTENTOS` | Se alcanzó el límite computable de la UD con datos suficientes. |
| `INACTIVIDAD` | La trayectoria presenta inactividad relevante para la regla evaluada. |
| `CARGA_MINIMA_EXCEPCION` | Se permite o requiere revisar una carga inferior al mínimo por causa tipificada. |
| `DATOS_INSUFICIENTES` / `NO_DETERMINADO` | Falta una fuente esencial para concluir. |
| `REQUISITO_CURRICULAR_PENDIENTE` | Falta requisito formal del plan o componente final. |

### Parámetros actuales

| Parámetro | Valor predeterminado | Aplicación |
| --- | --- | --- |
| `EVALUACION_V2_HABILITADA` | `true` | Permite ejecutar evaluación V2. |
| `REGLAS_V2_MODO_SOMBRA` | `true` | Impide usar el resultado como efecto productivo. |
| `REGLAS_V2_VERSION` | `PE2027-V1` | Se registra en resultado y trazabilidad. |
| `CTR01_PERIODO_INICIO` | `2027-1` | Inicio técnico de evaluación de control a quinto. |
| `CTR01_SEMESTRE_OBJETIVO` | `5` | Referencia técnica del control CTR-01. |

Los valores proceden de propiedades JVM; pueden sobrescribirse al desplegar. No existe todavía una matriz institucional integrada de población, plan, versión y cohorte.

## 3. Matriz regla → comportamiento observable

Abreviaturas: **M** Mapa curricular, **A** Asistente de inscripción, **S** Simulador, **V** Asistente virtual. “Visible” significa que el adaptador puede presentar el resultado; no garantiza que todas las pantallas tengan cobertura visual para cada dato.

| RN | Estado actual | Comportamiento del motor | M / A / S / V | Responsable principal |
| --- | --- | --- | --- | --- |
| 1.1 | Parcial | Actividad e inactividad con evidencia de trayectoria. | M: estado; A/V: alerta; S: no autoriza. | Motor + SIGE. |
| 1.2 | Implementada | Regularidad por reprobadas vigentes únicas. | M: estado; A/V: diagnóstico; S: máximo de carga. | Motor. |
| 1.3 | Implementada | Conserva no cursada y baja como causas separadas. | M/A/V: explicación; S: motivo de restricción cuando aplica. | Motor. |
| 1.4 | Parcial | Intentos por UD; distingue registros computables. | A: oportunidades; S: restricción si hay evidencia; M/V: informativo. | Motor + historial/bajas. |
| 1.5 | Implementada | Restringe reinscribir acreditada sólo para mejorar promedio; rectificación requiere evidencia. | A/V: explicación; S: no propone esa reinscripción; M: estado acreditado. | Motor + control escolar. |
| 2.1 | No implementada localizada | No hay evaluación identificada con ese numeral. | No hay comportamiento V2 atribuible. | Confirmar RN / área académica. |
| 2.2 | Implementada | Evaluación previa descriptiva a carga/reinscripción. | M/A/V: orientación; S: aplica al escenario. | Motor; inscripción confirma. |
| 3.1 | No implementada localizada | No hay evaluación identificada con ese numeral. | No hay comportamiento V2 atribuible. | Confirmar RN / área académica. |
| 4 | Implementada como contenedor | Agrupa reglas de carga. | A/S muestran mínimos y máximos; M/V informan. | Motor. |
| 4.1 | Parcial | Mínimo general y excepciones tipificadas. | A/S: carga mínima y excepción; M/V: aviso. | Motor + oferta/plan. |
| 4.2 | Implementada | Máximo individual: regular 8, irregular 6, sujeto al contexto. | S invalida exceso; A proyecta; M/V explican. | Motor. |
| 4.3 | Implementada | Integra mínimo, máximo, elegibles y descartadas. | A/S muestran propuesta/motivos. | Motor. |
| 5 | Implementada como contenedor | Agrupa reglas de rezago. | A/V explican; M/S reflejan prioridades. | Motor. |
| 5.1 | Implementada | Rezago leve y prioridad de regularización. | M/A/V priorizan; S conserva la prioridad. | Motor. |
| 5.2 | Implementada | Rezago severo y ruta de regularización. | M/A/V alertan; S sólo admite la ruta permitida. | Motor. |
| 6.1 | Parcial | Evalúa candidatas de quinto desde vigencia técnica. | M/A/V explican; S valida candidata/carga. | Motor + malla/oferta. |
| 6.1.1 | Implementada | Identifica obligatorias pendientes de la base de avance. | M/A/V muestran causa; S aplica restricción. | Motor. |
| 6.1.2 | Implementada | Condiciones comunes y seriación por candidata de quinto. | M/A/V muestran por UD; S no propaga bloqueo a otras UD. | Motor. |
| 6.1.3 | Implementada | Supuestos tipificados concurrentes, sin máximo artificial de pendientes. | A/V explican; S conserva restricciones. | Motor; autorización institucional cuando corresponda. |
| 6.1.4 | Parcial | Separa sin oferta, bloque, grupo o cupo. | M/A/V distinguen causa; S no reserva. | Oferta/Grupos + Motor. |
| 6.1.5 | Parcial | Identifica impedimentos prevalentes. | Todos muestran motivo; S restringe. | Motor + fuente de sanción/PE. |
| 6.1.6 | Parcial | Indica necesidad de resolución extraordinaria, no la concede. | A/V orientan; M/S muestran revisión. | Instancia académica. |
| 6.2 | Parcial | Incorporación diferida descriptiva. | A/V orientan; S/M informan. | Motor + convocatoria/resolución. |
| 6.3 | Implementada | Prioriza reprobadas, incluso si no hay oferta. | M/A/V prioridad; S ordena la propuesta. | Motor. |
| 6.4 | Implementada | Cuatro o más reprobadas únicas: ruta de regularización. | M/A/V alerta; S restringe carga. | Motor. |
| 6.5 | Parcial | Reincorporación tras periodo no cursado. | A/V orientación; M/S informativo/restrictivo según evidencia. | Motor + trayectoria. |
| 6.6 | Parcial | Reincorporación después de baja temporal. | A/V seguimiento; M/S no infieren autorización. | Motor + bajas/reincorporación. |
| 6.7 | Parcial | Efectos tras baja parcial sin convertirla en reprobación. | M/A/V muestran causa; S mantiene distinción. | Motor + bajas. |
| 6.8 | Implementada | Proyecta trayectoria tras regularización. | A: expectativa/proyección; M/V resumen; S escenario. | Motor. |
| 6.9 | No implementada localizada | No hay método ni prueba localizada para este numeral. | No hay comportamiento V2 atribuible. | Confirmar RN / área académica. |
| 7.1 | Parcial | Distingue optativas, pendientes y disponibilidad. | M/A/V clasifican; S conserva reglas complementarias de bloque. | Motor + catálogo por plan. |
| 7.1.1 | Parcial | Distingue requeridas y adicionales; requiere catálogo/ruta verificable. | A muestra adeudos; M/S/V explican. | Motor + catálogo/sustituciones. |
| 7.2 | Parcial | Trata optativas reprobadas; conserva nombre técnico histórico del método. | M/A/V muestran estado; S valida sin crear categoría vigente ELECTIVA. | Motor + catálogo. |
| 7.3 | Parcial | Consolida tipos y evita conflicto de prioridades. | Todos mantienen causa y prioridad. | Motor + relación de sustitución. |
| 8 | Implementada como contenedor | Seriación por relaciones formales del PE. | M muestra restricción; A/V explican; S invalida selección. | Motor. |
| 8.1 | Implementada | Seriación interbloque. | Igual que RN-8, con motivo específico. | Motor. |
| 8.2 | Implementada | Seriación intersemestral. | Igual que RN-8, con motivo específico. | Motor. |
| 8.3 | Implementada | Seriación interanual. | Igual que RN-8, con motivo específico. | Motor. |
| 9.1 | Parcial | Reinscripción extemporánea descriptiva. | A/V orientan; S/M no autorizan. | Inscripción + Motor. |
| 9.2 | No implementada | Sólo existe el código de catálogo. | No hay comportamiento V2. | Confirmar área responsable. |
| 9.3 | Parcial | S7/S8 por estructura, componentes y seriación; no usa 50 % autónomo. | M/A/V explican; S restringe según resultado. | Motor + catálogo de componentes. |
| 9.4 | Parcial | Requisitos académicos de egreso y componentes finales; adeudo adicional no bloquea por sí solo cumplimiento curricular. | M/A/V informan; S no concluye trámite. | Motor + catálogo/resolución de egreso. |
| 9.5 | Implementada | Traza versión, contexto, UD, motivos, decisiones y faltantes. | No es una acción estudiantil; soporte/auditoría la consulta. | Motor + auditoría. |
| 10 | No implementada | Sólo existe código de catálogo. | No hay comportamiento V2. | Área responsable. |
| 10.1 | No implementada | No hay fuente/evaluación para titulación o servicio social. | No hay comportamiento V2. | Titulación / Servicio social. |

## 4. Ejecución de casos en sombra

### Evidencia ejecutada

Comando ejecutado el 2026-11-06:

```bash
cd codigoFuente/basegestor
mvn -q -Dtest='mx.gob.sedesol.basegestor.gestionescolar.v2.*Test' test
```

Resultado: **758 pruebas, 0 fallas, 0 errores, 0 omitidas**. Son contextos sintéticos, no consultan expedientes personales ni escriben información productiva.

### Matriz CAMPUS 2

No se localizó en el repositorio ni entre los documentos disponibles un artefacto llamado **“CAMPUS 2 - Casos y criterios de prueba 2027-20261104”**, ni una tabla con sus entradas esperadas. Por tanto no se puede declarar ejecutada esa matriz ni inventar sus casos. Para ejecutarla se requiere el archivo o, como mínimo, una tabla versionada con ID de caso, plan/cohorte, periodo, UD, historial, oferta, seriación y resultado esperado.

### Casos adicionales solicitados

| Caso | Entradas requeridas / resumidas | Cobertura ejecutada hoy | Salida V2 comprobada o límite | Qué verían los componentes |
| --- | --- | --- | --- | --- |
| Luis: 4 reprobadas + 1 obligatoria anterior nunca inscrita | Cuatro UD con reprobación vigente única; una obligatoria `NO_CURSADA`; oferta y seriación. | **Parcial**: pruebas independientes verifican RN-6.4 y que `NO_CURSADA` no es reprobación. No hay caso sintético único con el nombre Luis y esa composición completa. | Cuatro reprobadas activan RN-6.4; la no cursada conserva causa propia. La elegibilidad de la obligatoria exige datos de oferta/seriación. | M/A/V: prioridad y causa separada. S: sólo regularización para las cuatro reprobadas; no suma la no cursada como quinta reprobación. |
| Marta: POO I y POO II reprobadas; II depende de I | Dos reprobadas y relación formal POO I → POO II. | **Parcial**: pruebas de seriación directa/intersemestral y reprobadas múltiples. | Ambas son reprobadas; POO II queda además restringida por seriación si la relación llega en el contexto. | M muestra ambos motivos; A/V no recomiendan POO II como avance libre; S rechaza selección incompatible. |
| Carlos: pretende quinto con 1 reprobada de segundo ofertada | Reprobada vigente S2, candidata S5 ofertada, periodo desde 2027-1 y condiciones comunes. | **Sí, por equivalente sintético**: prueba `f5CU01CandidataQuintoAplicaAunqueObjetivoSeaOtro` y pruebas RN-6.1. | La candidata S5 activa evaluación RN-6.1 aun si el semestre objetivo es distinto. El resultado final depende de seriación, oferta, carga e impedimentos. | M/A/V explican candidata y condición; S evalúa la carga, sin extender el bloqueo a UD ajenas. |
| Obligatoria S3 nunca cursada y no ofertada; pretende S5 | UD S3 `NO_CURSADA`, causa `NO_CURSAMIENTO`, oferta falsa; candidata S5 y relaciones. | **Parcial**: pruebas de `NO_CURSADA`, `SIN_OFERTA` y control de quinto. | No se cuenta como reprobada. El motor marca pendiente y falta de oferta; no puede afirmar que la UD S5 proceda sin relaciones y condiciones completas. | M/A/V distinguen pendiente de falta de oferta; S no la presenta como cupo reservado. |
| Baja temporal completa + un periodo sin inscripción | Baja temporal aprobada, periodo afectado, reincorporación y evidencia de no inscripción. | **Parcial**: pruebas RN-6.6 de baja temporal con/sin reincorporación. | Con baja y reincorporación verificadas se evalúa el supuesto; sin reincorporación el resultado requiere revisión. No infiere autorización desde la ausencia de inscripción. | A/V muestran seguimiento; M/S no convierten el antecedente en reprobación. |
| Irregular intenta optativa de otro semestre; regular en el mismo supuesto | Situación académica, optativa candidata, semestre/bloque, oferta, seriación y selección. | **Parcial**: pruebas de máximo 6/8, optativas y seriación. No existe caso sintético que combine exactamente “otro semestre” con ambos perfiles. | La irregularidad limita máximo a 6; la optativa debe pasar seriación, oferta y reglas de tipo. El motor no debe rechazarla únicamente por ser de otro semestre si no existe esa restricción formal. | M/A/V muestran motivos concretos; S valida la selección completa y las reglas complementarias de bloque. |

### Salida por UD que puede documentarse hoy

La salida individual disponible para cada caso es: estado académico, `HABILITADA`/`BLOQUEADA`/`PRIORITARIA`/`REQUIERE_REVISION`, motivo, prioridad, oferta y trazas de regla. Sin la matriz CAMPUS o una especificación sintética completa, no es responsable atribuir a Luis, Marta o Carlos una clave, decisión final y regla concreta no ejecutada.

## 5. Limitaciones conocidas

1. **Universo de evaluación:** no se envía automáticamente todo el mapa y todo el historial como decisiones por UD; se parte de oferta, UD en curso y reprobadas agregadas.
2. **Semestre efectivo:** `semestreObjetivo` se obtiene actualmente del semestre mayor de la oferta, no de una trayectoria canónica del estudiante.
3. **Oportunidades:** requiere primera inscripción computable, resultados, bajas autorizadas y estado en curso; no puede deducirse de un contador de reprobaciones.
4. **Bajas temporales:** la fuente puede identificar movimientos aprobados mediante `contabilizar=1`, pero autorización, agrupación por periodo y reincorporación requieren la relación verificable con evento, periodo e inscripción.
5. **Sustituciones de optativas adicionales:** falta una relación institucional integrada entre adeudo original y sustitutas; sin ella sólo pueden mostrarse datos parciales, nunca un acumulado definitivo.
6. **Equivalencias/migraciones:** no hay correspondencia institucional integrada de claves entre planes; comparar nombres es heurístico y no debe resolver identidad curricular.
7. **Oferta futura:** la oferta actual es referencia; no prueba cupo, grupo, periodo ni disponibilidad futura.
8. **Componentes finales y RN-9.4:** depende de identificar formalmente componentes y requisitos por plan/cohorte. No concluye trámites de certificación, titulación o servicio social.
9. **RN-9.2, RN-10 y RN-10.1:** están catalogadas pero no tienen evaluación ejecutable localizada.
10. **Inscripción formal frente a preselección:** no hay una marca única localizada en `tbl_inscripciones`; la distinción actual usa condiciones operativas del proceso y participación activa.
11. **Modo sombra:** el resultado no reserva cupo, no modifica oferta, grupos, inscripción, selección, trayectoria, calificaciones ni bajas. La persistencia de trazabilidad, si se habilita, es auditoría V2 separada.

## Recomendación para la comparación normativa

El analista debe contrastar cada fila de la matriz con el texto oficial y clasificarla como: conforme, requiere ajuste de lógica, requiere dato/catálogo o fuera de alcance. Los ajustes expresamente normativos pueden desarrollarse sin esperar catálogos; los que dependen de plan, cohorte, relaciones de UD, bajas, sustituciones o componentes finales deben preservar un resultado de revisión en lugar de inferir una decisión académica.
