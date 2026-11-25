# Instrucciones para Codex – Precisiones de las áreas (18-11-2026)

**Base:** commit 410b3a21 · **Referencia:** `Decisiones_institucionales_motor_v2_RN_PE2024_2027.md` v5.
Se mantienen: modo sombra, sin escrituras, solo datos sintéticos o agregados.

1. **Texto de la nota de 2027-1:** cambia `V2_COMUNICADO_INSTITUCIONAL_TEXTO` por este texto exacto:
   > Esta simulación aplica la normativa y reglas generales vigentes. En un proceso de Inscripción efectivo puede aplicar autorizaciones institucionales específicas. La inscripción efectiva puede diferir del resultado que se muestra aquí.
   Debe aparecer solo cuando la restricción **o el resultado en revisión** corresponda a C1 (RN 1.4) o C2 (RN 6.2), y solo en 2027-1.
2. **Advertencia general frente a autorización individual acreditada:**
   - Agrega al contrato una entrada opcional `autorizacionIndividual` (referencia, autoridad, UD o supuesto, vigencia).
   - Sin ese dato: solo se muestra el `AVISO_COMUNICADO_2027_1`, como hoy.
   - Con el dato acreditado y vigente: se muestra como aviso separado `AUTORIZACION_INDIVIDUAL_ACREDITADA`, con su referencia. **No** cambia la decisión en sombra; la inscripción la formaliza Inscripción.
   - Nunca infieras la autorización (depende de Q13).
   - Pruebas: con autorización vigente, con autorización vencida y sin autorización.
3. **C1 y C2 son excluyentes en una misma trayectoria:**
   - El marcador de C2 solo procede si el expediente no tiene ninguna inscripción formal. El de C1 requiere intentos computables.
   - Reemplaza GC-19e ("persona en ambos supuestos") por una prueba que confirme que un mismo expediente no puede tener ambos marcadores.
   - No relaciones expedientes distintos de la misma persona.
4. **C2 con dato faltante → en revisión:**
   - Confirma que la 6.2 sin referencia de convocatoria o resolución devuelve `REQUIERE_REVISION` / no determinado y **nunca** `BLOQUEADA`.
   - Revisa todas las rutas del evaluador donde un dato faltante produzca una restricción definitiva (1.4, 6.1.5, 6.2, 7.1.1 y 8.1) y repórtalas.
   - Una restricción definitiva solo se emite si sus condiciones están acreditadas.
5. **Periodos posteriores a 2027-1:** confirma con una prueba que en 2027-2 no aparecen ni la nota ni el marcador si no hay otro comunicado configurado con vigencia acreditada.
6. **Q5 reabierta también para 4 o más adeudos:**
   - Agrega el parámetro `V2_Q5_RECURSAMIENTO_QUINTO_CON_64`, con los valores `REGULARIZACION_64` (comportamiento actual) y `AVANCE_CONDICIONADO_NO_PROCEDE` (aplica la 6.1.5 → `BLOQUEADA`).
   - En ambos parámetros de Q5, la traza debe indicar `DEFINICION_INSTITUCIONAL_PENDIENTE_Q5`.
   - Pruebas de los dos valores.
7. **Evidencia de Q8:** documenta en el inventario:
   - la clase, el método y las condiciones exactas en que `NuevaBajaServiceImpl` asigna `contabilizar = 1`;
   - si existe algún otro flujo que lo asigne o lo modifique;
   - el tratamiento de los registros sin el indicador (en revisión) y su conteo agregado.
8. **Documentación de Q6:**
   - Confirma la población exacta del corte de sensibilidad (estudiantes distintos de los planes 3, 4, 5, 6, 7 y 11) y los porcentajes de los tres escenarios sobre esa población.
   - Agrega al reporte la definición de cada escenario tal como la implementa el SQL.
9. **Q9:** sin cambios de código. En el inventario, en el renglón 6.7/8.1, aclara que el subtipo "baja por seriación de UD formalmente inscrita" sin motivo en el catálogo solo afecta la trazabilidad.
10. Regresión completa y reporte en `documentacion/RESULTADO_PRECISIONES_20261118.md`.
