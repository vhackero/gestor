# Deuda técnica - Motor Curricular V2

**Corte:** 2026-11-23. El motor opera en modo sombra y no sustituye las fuentes institucionales pendientes.

| ID | Descripción | Regla | Causa | Efecto actual | Qué la resuelve | Dependencia |
|---|---|---|---|---|---|---|
| DT-1 | No llega el movimiento candidato de la tercera sustitución de optativa adicional. | 7.1.1 | Contrato/fuente | Se conserva el adeudo conocido; sin relación queda en revisión. | Tabla/servicio que entregue adeudo original, sustituta, fecha y estado formal. | Q12 / DAEAE-Inscripción |
| DT-2 | Excepciones 2.3 no tienen catálogo ni administración SIGIE. | 2.3 | Fuente/decisión | Sólo se muestra restricción y comunicado; no concede habilitación. | Configuración institucional con población, vigencia, proceso, responsable y alcance. | G4, C1, C2 |
| DT-3 | No hay fuente productiva confirmada de cierre de acta y del registro formal pendiente por seriación. | 2.2, 8.1 | Fuente | NULL cerrado se revisa; B2 condicionada no se confirma registralmente. | Cierre por grupo/bloque y movimiento de confirmación. | Q3/G3 |
| DT-4 | Parámetros se leen de JVM, aunque llevan ámbito y vigencia. | Transversal | Configuración | Cambiar valor exige configuración de servidor, no catálogo por cohorte. | Servicio/configuración institucional por plan, cohorte y vigencia. | CTIE |
| DT-5 | Consistencia entre consumidores se prueba por servicio, no aún contra las cuatro pantallas desplegadas. | Transversal | Validación | Riesgo residual sólo de presentación/adaptación UI. | Validación runtime controlada en escritorio y móvil. | Usuario/Abel |
| DT-6 | No se localizaron expedientes de prueba reales de interbloque, recursamiento quinto y Q7. | 7.1, 8.1 | Datos de prueba | Casos cubiertos sintéticamente. | Cuentas de prueba CAMPUS o datos controlados. | DAEAE |
| DT-7 | Catálogo único de cobertura no existía. | Transversal | Documentación | Sin rastreo central de brechas. | `CATALOGO_CASOS_MOTOR_V2.md`. | Cerrada en Bloque 7 |
| DT-8 | RN 6.1.6 requiere ticket/resolución GLPi verificable. | 6.1.6 | Fuente externa | El motor sólo solicita revisión. | Integración de referencia y resolución. | GLPi/DAEAE |
| DT-9 | RN 6.2 necesita convocatoria o resolución de incorporación diferida. | 6.2 | Fuente externa | Sin referencia queda en revisión. | Fuente institucional verificable. | C2-a |
| DT-10 | RN 9.1 necesita registro formal de reinscripción extemporánea. | 9.1 | Fuente de Inscripción | No puede confirmar autorización; nunca dispensa reglas. | Campo/servicio de proceso extemporáneo. | Inscripción |
| DT-11 | Relaciones de seriación de PE2027 y Seminarios no están cargadas productivamente. | 8, 8.2, 8.3 | Catálogo | Sólo se evalúan relaciones entregadas por el contexto. | Carga CAI por plan/vigencia; directas, con transitividad en motor. | Q2 |
| DT-12 | Validación de cierre de catálogo CAMPUS 2 frente a RN queda documental, no sustituye validación institucional. | Transversal | Gobernanza | Los casos se trazan y prueban, pero no aprueban la RN. | Revisión del analista de reglas. | Claude/áreas académicas |
| DT-13 | Agregados de adeudo se exponen en el contrato y consumidores, pero falta validación visual runtime de sus textos. | 1.2, 6.4 | Presentación | El motor entrega `adeudosVigentes`, `adeudosEnRevision` y `resultadosReprobatoriosInformativos`; Situación académica y Asistente virtual los muestran. | Validación controlada en escritorio y móvil. | Usuario/Abel |
