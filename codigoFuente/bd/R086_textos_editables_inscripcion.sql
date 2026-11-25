-- Textos editables del flujo de inscripcion y de sus validaciones.
-- En mensajes con argumentos, conservar las posiciones {0}, {1}, etc.
SET NAMES utf8mb4;

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT textos.clave, textos.valor, referencia.id_funcionalidad, referencia.usuario_modifico
FROM (
    SELECT 'gw.inscripcion.adeudo.columna' AS clave, 'Adeudo que regulariza' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.adeudo.seleccionar' AS clave, 'Selecciona el adeudo' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.cupo.sinConfigurar' AS clave, 'Optativas sin cupo configurado: {0}.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.cupo.sinLugares' AS clave, 'Optativas sin lugares disponibles: {0}.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.acepta.los.terminos.y.condiciones' AS clave, 'Acepta los términos y condiciones' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.aceptarUltimaOportunidad' AS clave, 'Lee y confirma el aviso de última oportunidad antes de continuar.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.aviso.de.oportunidades' AS clave, 'Aviso de oportunidades' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.cambios.de.ud.realizados' AS clave, 'Cambios de UD realizados' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.captura.inicio.y.fin.del.proceso.el.fin' AS clave, 'Captura inicio y fin del proceso; el fin no puede ser anterior al inicio.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.captura.la.fecha.de.inicio.y.la.fecha' AS clave, 'Captura la fecha de inicio y la fecha de fin del proceso.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.captura.un.cupo.positivo.que.cubra.las.inscripciones' AS clave, 'Captura un cupo positivo que cubra las inscripciones confirmadas de {0} ({1}).' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.configuracion.de.bajas.incompleta' AS clave, 'Configuración de bajas incompleta.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.configuracion.de.oportunidades.incompleta' AS clave, 'Configuración de oportunidades incompleta.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.configuracion.eliminada' AS clave, 'Configuración eliminada' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.configuracion.guardada' AS clave, 'Configuración guardada' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.continuarConOportunidades' AS clave, 'Puedes inscribir únicamente otras UD con oportunidades disponibles.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.cupo.de.optativas.guardado' AS clave, 'Cupo de optativas guardado.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.debe.existir.un.unico.periodo.academico.vigente.para' AS clave, 'Debe existir un único periodo académico vigente para evaluar la falta de reinscripción.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.debes.dar.prioridad.a.las.unidades.didacticas.disponibles' AS clave, 'Debes dar prioridad a las unidades didácticas disponibles del semestre {0} antes de complementar con semestres posteriores por tu antecedente de baja temporal.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.debes.incluir.la.unidad.didactica.segun.tu.situacion' AS clave, 'Debes incluir la unidad didáctica {0} según tu situación académica actual. Actualiza la página.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.debes.seleccionar.al.menos.unidades.didactica' AS clave, 'Debes seleccionar al menos 1 unidades didáctica' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.debes.seleccionar.al.menos.unidades.didactica.s' AS clave, 'Debes seleccionar al menos {0} unidades didáctica(s)' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.disponibles.para.el.adeudo' AS clave, 'Disponibles para el adeudo' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.adeudo.de.optativa.requiere.conciliacion.de.su' AS clave, 'El adeudo de optativa requiere conciliación de su historial.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.adeudo.seleccionado.ya.no.esta.vigente.actualiza' AS clave, 'El adeudo seleccionado ya no está vigente. Actualiza la página.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.adeudo.ya.no.admite.otra.inscripcion' AS clave, 'El adeudo ya no admite otra inscripción.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.cupo.maximo.debe.ser.mayor.que.cero' AS clave, 'El cupo máximo debe ser mayor que cero.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.cupo.no.puede.ser.menor.que.los' AS clave, 'El cupo no puede ser menor que los {0} lugares actualmente ocupados.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.limite.de.oportunidades.por.ud.debe.estar' AS clave, 'El límite de oportunidades por UD debe estar entre 1 y 3.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.mensaje.de.optativa.solo.requerida.debe.contener' AS clave, 'El mensaje de optativa sólo requerida debe contener {unidad}, sin otras variables, y de 1 a 1000 caracteres.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.parametro.de.la.regla.debe.contener.o' AS clave, 'El parámetro {0} de la regla {1} debe contener 0 o 1 y tiene el valor ''''{2}''''' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.parametro.de.la.regla.debe.contener.un' AS clave, 'El parámetro {0} de la regla {1} debe contener un número entero y tiene el valor ''''{2}''''' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.es.obligatorio.para.eliminar.la.carga' AS clave, 'El plan es obligatorio para eliminar la carga regular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.es.obligatorio.para.eliminar.la.carga.2' AS clave, 'El plan es obligatorio para eliminar la carga irregular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.es.obligatorio.para.eliminar.la.regla' AS clave, 'El plan es obligatorio para eliminar la regla específica' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.es.obligatorio.para.la.carga.irregular' AS clave, 'El plan es obligatorio para la carga irregular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.es.obligatorio.para.la.carga.regular' AS clave, 'El plan es obligatorio para la carga regular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.es.obligatorio.para.una.regla.especifica' AS clave, 'El plan es obligatorio para una regla específica' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.seleccionado.ya.tiene.una.regla.especifica' AS clave, 'El plan seleccionado ya tiene una regla específica para todos sus usuarios. Puedes modificarla en la tabla.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.vuelve.a.utilizar.la.regla.general' AS clave, 'El plan vuelve a utilizar la regla general para estudiantes regulares.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.vuelve.a.utilizar.la.regla.general.2' AS clave, 'El plan vuelve a utilizar la regla general para estudiantes irregulares.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.plan.ya.tiene.una.configuracion.irregular.edita' AS clave, 'El plan ya tiene una configuración irregular. Edita esa regla.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.porcentaje.del.tramo.final.debe.estar.entre' AS clave, 'El porcentaje del tramo final debe estar entre 0 y 100' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.proceso.de.inscripcion.esta.inactivo.o.fuera' AS clave, 'El proceso de inscripción está inactivo o fuera de fechas, o no corresponde al plan y convocatoria. Consulta nuevamente antes de registrar.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.tratamiento.de.bajas.en.inscripcion.fue.actualizado' AS clave, 'El tratamiento de bajas en inscripción fue actualizado.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.umbral.de.reprobadas.para.el.asistente.debe' AS clave, 'El umbral de reprobadas para el asistente debe ser positivo' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.username.esta.asociado.mas.de.una.vez' AS clave, 'El username ''''{0}'''' está asociado más de una vez al plan {1}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.username.indicado.ya.tiene.una.regla.especifica' AS clave, 'El username indicado ya tiene una regla específica para el plan seleccionado. Puedes modificarla en la tabla.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.username.no.existe.o.no.corresponde.al' AS clave, 'El username ''''{0}'''' no existe o no corresponde al plan {1}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.el.usuario.no.corresponde.al.plan' AS clave, 'El usuario no corresponde al plan {0}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.error' AS clave, 'Error' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.error.al.eliminar.el.proceso.de.inscripcion.y' AS clave, 'Error al eliminar el proceso de inscripción y sus relaciones' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.error.al.intentar.eliminar.el.proceso.de.inscripcion' AS clave, 'error al intentar eliminar el proceso de inscripción' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.estimad.a.o.estudiante.debes.seleccionar.al.menos' AS clave, 'Estimad(a/o) estudiante, debes seleccionar al menos {0} unidades didácticas obligatorias.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.evaluacion' AS clave, 'Evaluación: ' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.configurar.el.cupo.de.la.optativa.contacta' AS clave, 'Falta configurar el cupo de la optativa {0}. Contacta al administrador para revisar la oferta.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.configurar.el.parametro.para.la.regla' AS clave, 'Falta configurar el parámetro {0} para la regla {1}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.configurar.omision.reinscripcion.ejecute.la.migracion.r072' AS clave, 'Falta configurar OMISION_REINSCRIPCION. Ejecute la migración R072.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.de.cupo.para.la.optativa.actualiza.tu' AS clave, 'Falta de cupo para la optativa {0}. Actualiza tu selección y elige una alternativa disponible.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.el.antecedente.de.la.optativa.sustituta' AS clave, 'Falta el antecedente de la optativa sustituta.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.el.interruptor.de.aplicacion.de.oportunidades.ejecute' AS clave, 'Falta el interruptor de aplicación de oportunidades. Ejecute R078.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.el.parametro.ejecute.r077' AS clave, 'Falta el parámetro {0}. Ejecute R077.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.el.periodo.en.el.calendario.academico.historico' AS clave, 'Falta el periodo {0} en el calendario académico histórico.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.la.configuracion.de.bajas.ejecute.r073' AS clave, 'Falta la configuración de bajas. Ejecute R073.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.la.configuracion.de.oportunidades.ejecute.r077' AS clave, 'Falta la configuración de oportunidades. Ejecute R077.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.falta.la.parametrizacion.de.adicionales.ejecute.r079' AS clave, 'Falta la parametrización de adicionales. Ejecute R079.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.faltan.parametros.de.optativas.requeridas.adicionales.ejecute.r080' AS clave, 'Faltan parámetros de optativas requeridas/adicionales. Ejecute R080.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.ha.ocurrido.un.error.inesperado' AS clave, 'Ha ocurrido un error inesperado' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.ha.ocurrido.un.error.inesperado.2' AS clave, 'Ha ocurrido un error inesperado' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.he.leido.el.aviso.de.ultima.oportunidad.de' AS clave, ' He leído el aviso de última oportunidad de las UD seleccionadas.' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.he.leido.el.aviso.de.ultima.oportunidad.de.2' AS clave, ' He leído el aviso de última oportunidad de las UD seleccionadas.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.indica.la.accion.un.numero.positivo.de.periodos' AS clave, 'Indica la acción, un número positivo de periodos y un mensaje de 1 a 1000 caracteres.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.indica.las.optativas.solo.requeridas.usa.vacio.para' AS clave, 'Indica las optativas sólo requeridas; usa vacío para no aplicar exclusiones (máximo 1000 caracteres).' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.conciliacion.periodoNoComprobado' AS clave, 'Inscripción {0}: periodo académico no comprobado.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.inscripcion.de.materias.completa' AS clave, 'Inscripción de materias completa{0}' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.inscripcion.de.unidades.didacticas.completada.con.exito' AS clave, 'Inscripción de unidades didácticas completada con éxito.{0}' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.inscripcion.de.unidades.didacticas.realizada.con.exito.por' AS clave, 'Inscripción de unidades didácticas realizada con éxito, por favor, revisa tu correo institucional.{0}' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.asignatura.electiva.fue.actualizada.correctamente' AS clave, 'La asignatura electiva fue actualizada correctamente.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.cadena.de.optativas.requiere.conciliacion.antes.de' AS clave, 'La cadena de optativas requiere conciliación antes de eliminarla.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.carga.academica.general.para.estudiantes.irregulares.fue' AS clave, 'La carga académica general para estudiantes irregulares fue actualizada.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.carga.academica.general.para.estudiantes.regulares.fue' AS clave, 'La carga académica general para estudiantes regulares fue actualizada.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.carga.configurada.debe.requerir.al.menos.una' AS clave, 'La carga configurada debe requerir al menos una unidad didáctica' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.carga.de.nuevo.ingreso.fue.actualizada.para' AS clave, 'La carga de nuevo ingreso fue actualizada para el plan seleccionado.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.carga.irregular.del.plan.fue.actualizada' AS clave, 'La carga irregular del plan fue actualizada.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.carga.regular.del.plan.fue.actualizada' AS clave, 'La carga regular del plan fue actualizada.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.clasificacion.historica.de.esta.optativa.requiere.conciliacion' AS clave, 'La clasificación histórica de esta optativa requiere conciliación antes de inscribirla como adicional.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.configuracion.contiene.una.optativa.duplicada' AS clave, 'La configuración contiene una optativa duplicada.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.configuracion.fue.creada.para.el.username.indicado' AS clave, 'La configuración fue creada para el username indicado dentro del plan.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.configuracion.fue.creada.para.todos.los.usuarios' AS clave, 'La configuración fue creada para todos los usuarios del plan seleccionado.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.configuracion.irregular.fue.creada.para.el.plan' AS clave, 'La configuración irregular fue creada para el plan.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.configuracion.irregular.y.su.estado.son.obligatorios' AS clave, 'La configuración irregular y su estado son obligatorios' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.configuracion.regular.fue.creada.para.el.plan' AS clave, 'La configuración regular fue creada para el plan.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.fecha.de.fin.no.puede.ser.anterior' AS clave, 'La fecha de fin no puede ser anterior a la fecha de inicio.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.lista.de.materias.al.construir.el.correo' AS clave, 'La lista de materias al construir el correo no puede estar vacía' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.oferta.cambio.vuelve.a.consultar.el.proceso' AS clave, 'La oferta cambió. Vuelve a consultar el proceso antes de guardar.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.oferta.o.el.proceso.de.inscripcion.cambio' AS clave, 'La oferta o el proceso de inscripción cambió. Actualiza la página.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.optativa.no.es.elegible.para.el.plan' AS clave, 'La optativa no es elegible para el plan y convocatoria de esta inscripción.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.optativa.que.conserva.este.adeudo.esta.en' AS clave, 'La optativa que conserva este adeudo está en curso.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.optativa.seleccionada.agoto.sus.oportunidades.individuales' AS clave, 'La optativa seleccionada agotó sus oportunidades individuales.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.optativa.ya.conserva.otro.adeudo.vigente' AS clave, 'La optativa ya conserva otro adeudo vigente.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.regla.de.inscripcion.fue.actualizada.correctamente' AS clave, 'La regla de inscripción fue actualizada correctamente.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.regla.de.periodos.sin.reinscripcion.fue.actualizada' AS clave, 'La regla de periodos sin reinscripción fue actualizada.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.regla.general.de.nuevo.ingreso.fue.actualizada' AS clave, 'La regla general de nuevo ingreso fue actualizada correctamente.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.seleccion.excede.las.optativas.requeridas.que.faltan' AS clave, 'La selección excede las optativas requeridas que faltan para completar el requisito configurado de {0}.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.ud.ya.agoto.sus.oportunidades' AS clave, 'La UD ya agotó sus oportunidades.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.unidad.didactica.electiva.ya.no.cuenta.con' AS clave, 'La unidad didáctica electiva {0} ya no cuenta con lugares disponibles. Actualiza tu selección.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.unidad.didactica.no.esta.habilitada.para.inscripcion' AS clave, 'La unidad didáctica {0} no está habilitada para inscripción. Actualiza la página.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.unidad.didactica.seleccionada.no.forma.parte.de' AS clave, 'La unidad didáctica seleccionada no forma parte de la oferta vigente.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.unidad.didactica.ya.no.esta.permitida.para' AS clave, 'La unidad didáctica {0} ya no está permitida para tu situación académica. Actualiza la página.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.la.validacion.de.conclusion.del.plan.fue.actualizada' AS clave, 'La validación de conclusión del plan fue actualizada.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.las.cantidades.de.unidades.didacticas.no.pueden.ser' AS clave, 'Las cantidades de unidades didácticas no pueden ser negativas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.las.restricciones.academicas.generales.fueron.actualizadas' AS clave, 'Las restricciones académicas generales fueron actualizadas.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.limite.y.mensajes.de.oportunidades.actualizados' AS clave, 'Límite y mensajes de oportunidades actualizados.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.limiteReprobaciones' AS clave, 'Estimad(a/o) estudiante, ha alcanzado el límite de {0} reprobaciones en {1}{2}. {3}' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.los.limites.de.carga.regular.no.pueden.ser' AS clave, 'Los límites de carga regular no pueden ser negativos' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.los.parametros.academicos.generales.no.son.validos' AS clave, 'Los parámetros académicos generales no son válidos' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.maximoElectivas' AS clave, 'Solo puedes seleccionar un máximo de {0} unidades didácticas electivas' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.es.posible.seleccionar.la.unidad.didactica.porque' AS clave, 'No es posible seleccionar la unidad didáctica {0} porque no se ha acreditado ni seleccionado su antecedente conforme a la seriación del plan.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.es.posible.tomar.la.misma.unidad.didactica' AS clave, 'No es posible tomar la misma unidad didáctica dos veces, por favor revisa tu selección.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.configuracion.de.carga.de.nuevo.ingreso' AS clave, 'No existe configuración de carga de nuevo ingreso para el plan {0}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.configuracion.de.carga.irregular.para.el' AS clave, 'No existe configuración de carga irregular para el plan {0}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.configuracion.de.carga.regular.para.el' AS clave, 'No existe configuración de carga regular para el plan {0}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.la.regla.general.carga.estudiante.irregular' AS clave, 'No existe la regla general CARGA_ESTUDIANTE_IRREGULAR' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.la.regla.general.carga.estudiante.regular' AS clave, 'No existe la regla general CARGA_ESTUDIANTE_REGULAR' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.la.regla.general.carga.nuevo.ingreso' AS clave, 'No existe la regla general CARGA_NUEVO_INGRESO' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.la.regla.restricciones.academicas.generales' AS clave, 'No existe la regla RESTRICCIONES_ACADEMICAS_GENERALES' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.un.proceso.de.inscripcion.activo.y' AS clave, 'No existe un proceso de inscripción activo y dentro de fechas.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.un.proceso.disponible.para.inscribir.consulta' AS clave, 'No existe un proceso disponible para inscribir. Consulta nuevamente.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.un.unico.periodo.academico.vigente' AS clave, 'No existe un único periodo académico vigente.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.una.inscripcion.disponible.para.finalizar.actualiza' AS clave, 'No existe una inscripción disponible para finalizar. Actualiza la página.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.una.regla.especifica.de.nuevo.ingreso' AS clave, 'No existe una regla específica de nuevo ingreso para el plan {0}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existe.una.regla.especifica.de.nuevo.ingreso.2' AS clave, 'No existe una regla específica de nuevo ingreso para el username ''''{0}'''' en el plan {1}' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.existen.maximos.y.minimos.configurados.en.el' AS clave, 'No existen máximos y mínimos configurados en el plan' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.agregar.la.carga.irregular' AS clave, 'No fue posible agregar la carga irregular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.agregar.la.carga.regular' AS clave, 'No fue posible agregar la carga regular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.agregar.la.regla.para.el' AS clave, 'No fue posible agregar la regla para el plan' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.aplicar.la.configuracion.de.nuevo' AS clave, 'No fue posible aplicar la configuración de nuevo ingreso para el plan {0}: {1}' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.consultar.las.reglas.de.inscripcion' AS clave, 'No fue posible consultar las reglas de inscripción' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.consultar.los.procesos.de.inscripcion' AS clave, 'No fue posible consultar los procesos de inscripción activos.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.eliminar.la.carga.irregular' AS clave, 'No fue posible eliminar la carga irregular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.eliminar.la.carga.regular' AS clave, 'No fue posible eliminar la carga regular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.eliminar.la.configuracion.especifica.del' AS clave, 'No fue posible eliminar la configuración específica del plan' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.eliminar.la.inscripcion' AS clave, 'No fue posible eliminar la inscripción' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.el.tratamiento.de.bajas' AS clave, 'No fue posible guardar el tratamiento de bajas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.irregular' AS clave, 'No fue posible guardar la carga irregular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.irregular.general' AS clave, 'No fue posible guardar la carga irregular general' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.regular' AS clave, 'No fue posible guardar la carga regular' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.carga.regular.general' AS clave, 'No fue posible guardar la carga regular general' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.configuracion' AS clave, 'No fue posible guardar la configuración.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.configuracion.de.nuevo' AS clave, 'No fue posible guardar la configuración de nuevo ingreso' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.regla.de.inscripcion' AS clave, 'No fue posible guardar la regla de inscripción' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.regla.de.omisiones' AS clave, 'No fue posible guardar la regla de omisiones' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.regla.general.de' AS clave, 'No fue posible guardar la regla general de nuevo ingreso' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.la.validacion.de.conclusion' AS clave, 'No fue posible guardar la validación de conclusión' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.las.restricciones.academicas.generales' AS clave, 'No fue posible guardar las restricciones académicas generales' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.guardar.oportunidades' AS clave, 'No fue posible guardar oportunidades' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.fue.posible.registrar.la.evidencia.del.aviso' AS clave, 'No fue posible registrar la evidencia del aviso de oportunidades.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.hay.inscripciones.anteriores' AS clave, 'No hay inscripciones anteriores.' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.hay.optativas.disponibles.en.el' AS clave, 'no hay optativas disponibles en el {0}' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.puedes.eliminar.una.inscripcion.que.es.antecedente' AS clave, 'No puedes eliminar una inscripción que es antecedente de otras optativas vigentes. Revisa primero los registros posteriores de ese adeudo.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.puedes.inscribir.dos.variantes.de.la.misma' AS clave, 'No puedes inscribir dos variantes de la misma UD.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.puedes.inscribir.dos.veces.la.misma.optativa' AS clave, 'No puedes inscribir dos veces la misma optativa.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.repitas.tipos.de.baja' AS clave, 'No repitas tipos de baja.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.capturaron.los.creditos.totales.del.plan' AS clave, 'No se capturaron los creditos totales del plan' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.comprobo.el.ultimo.semestre.del.mapa' AS clave, 'No se comprobó el último semestre del mapa curricular.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.encontro.la.matricula' AS clave, 'No se encontró la matrícula: {0}' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.pudo.identificar.el.plan.de.inscripcion' AS clave, 'No se pudo identificar el plan de inscripción.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.pudo.identificar.el.proceso.de.inscripcion' AS clave, 'No se pudo identificar el proceso de inscripción. Actualiza la página.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.pudo.identificar.el.proceso.o.plan' AS clave, 'No se pudo identificar el proceso o plan de una UD seleccionada. Consulta nuevamente.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.pudo.preparar.la.evidencia.de.evaluacion' AS clave, 'No se pudo preparar la evidencia de evaluación.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.pudo.serializar.la.evidencia.academica' AS clave, 'No se pudo serializar la evidencia académica' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.puede.finalizar.la.inscripcion.porque.la' AS clave, 'No se puede finalizar la inscripción porque la oferta de nuevo ingreso es insuficiente: {0}. {1}Contacta al administrador para revisar la oferta del plan.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.no.se.pueden.bloquear.las.obligatorias.si.no' AS clave, 'No se pueden bloquear las obligatorias si no se seleccionan automáticamente' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.noProcedeInscripcion' AS clave, 'No le es permitido realizar una nueva inscripción. Contacte a la mesa de ayuda para revisar su caso.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.oportunidades.acumuladas' AS clave, 'Oportunidades acumuladas' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.oportunidades.acumuladas.de.optativas.adicionales' AS clave, 'Oportunidades acumuladas de optativas adicionales' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.oportunidades.consumidas' AS clave, 'Oportunidades consumidas' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.oportunidades.disponibles' AS clave, 'Oportunidades disponibles' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.oportunidades.registradas.por.unidad.didactica' AS clave, 'Oportunidades registradas por unidad didáctica' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.optativa.adicional' AS clave, 'Optativa adicional' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.para.cursar.materias.de.semestre.debes.haber.aprobado' AS clave, 'Para cursar materias de semestre {0}, debes haber aprobado todas las materias seriadas de semestre {1} a semestre {2}.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.para.finalizar.la.inscripcion.debe.seleccionar.unidades.didacticas' AS clave, 'Para finalizar la inscripción, debe seleccionar {0} unidades didácticas obligatorias y {1} unidades didácticas optativas.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.para.seleccionar.unidades.didacticas.de.este.semestre.debes' AS clave, 'Para seleccionar unidades didácticas de este semestre, debes haber acreditado al menos el {0}% del total de créditos de tu plan de estudios.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.conciliacion.correspondenciaPendiente' AS clave, 'Plan de origen {0}, periodo {1}: correspondencia pendiente de conciliación.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.plan.requerido' AS clave, 'Plan requerido' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.procedeInscripcion' AS clave, 'Cuenta con autorización para realizar una nueva inscripción.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.proceso.de.inscripcion.eliminado.correctamente' AS clave, 'Proceso de inscripción eliminado correctamente.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.puedes.recursar.una.optativa.adicional.reprobada.o.elegir' AS clave, 'Puedes recursar una optativa adicional reprobada o elegir otra elegible para regularizar su adeudo. Selecciona el adeudo antes de marcar la sustituta. La inscripción conserva la deuda hasta acreditarla.' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.puedes.seleccionar.maximo.unidades.didacticas' AS clave, 'Puedes seleccionar máximo {0} unidades didácticas' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.regla.agregada' AS clave, 'Regla agregada' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.regla.existente' AS clave, 'Regla existente' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.revisa.la.configuracion' AS clave, 'Revisa la configuración' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.se.actualizaron.tu.situacion.academica.carga.y.oferta' AS clave, 'Se actualizaron tu situación académica, carga y oferta. Revisa la selección antes de confirmar.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.se.actualizaron.tu.situacion.academica.carga.y.oferta.2' AS clave, 'Se actualizaron tu situación académica, carga y oferta. Revisa la selección antes de confirmar.' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.se.requieren.ud.obligatorias.y.hay.disponibles' AS clave, 'se requieren {0} UD obligatorias y hay {1} disponibles' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.se.requieren.ud.optativas.y.hay.disponibles' AS clave, 'se requieren {0} UD optativas y hay {1} disponibles' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.seleccion.ordinaria' AS clave, 'Selección ordinaria' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.selecciona.el.adeudo.de.optativa.adicional.que.deseas' AS clave, 'Selecciona el adeudo de optativa adicional que deseas regularizar.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.selecciona.el.plan.al.que.se.aplicara.la' AS clave, 'Selecciona el plan al que se aplicará la regla.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.selecciona.primero.las.optativas.disponibles.del.semestre.de' AS clave, 'Selecciona primero las optativas disponibles del semestre de referencia ({0}) antes de elegir optativas de otros semestres.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.selecciona.tipos.de.baja.autorizados.u.separados.por' AS clave, 'Selecciona tipos de baja autorizados: 1, 2, 7 u 8, separados por comas y sin espacios.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.selecciona.una.sola.optativa.para.regularizar.cada.adeudo' AS clave, 'Selecciona una sola optativa para regularizar cada adeudo.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.semestre.de.referencia.pendiente.de.conciliacion.hay.periodos' AS clave, 'Semestre de referencia pendiente de conciliación: hay periodos, correspondencias o datos del mapa curricular sin comprobar.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.solo.los.estudiantes.regulares.pueden.complementar.su.seleccion' AS clave, 'Solo los estudiantes regulares pueden complementar su selección con optativas de otros semestres.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.solo.puedes.seleccionar.hasta.optativa.s.nueva.s' AS clave, 'Solo puedes seleccionar hasta {0} optativa(s) nueva(s) por bloque en este periodo. Revisa tu selección.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.solo.puedes.seleccionar.hasta.optativa.s.nueva.s.2' AS clave, 'Solo puedes seleccionar hasta {0} optativa(s) nueva(s) en este periodo. Revisa tu selección.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.solo.se.permite.seleccionar.un.maximo.de.unidad' AS clave, 'Solo se permite seleccionar un máximo de {0} unidad(es) didáctica(s) optativa(s) por bloque, por favor revisa tu selección.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.solo.una.optativa.adicional.puede.regularizar.este.adeudo' AS clave, 'Sólo una optativa adicional puede regularizar este adeudo.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.sustituciones.disponibles' AS clave, 'Sustituciones disponibles' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.tu.historial.o.las.reglas.cambiaron.se.actualizaron' AS clave, 'Tu historial o las reglas cambiaron. Se actualizaron tu situación, carga y oferta. {0}Revisa la selección y confirma nuevamente.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.tu.plan.de.estudios.cambio.actualiza.la.pagina' AS clave, 'Tu plan de estudios cambió. Actualiza la página para revisar tu inscripción.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.tu.usuario.no.esta.activo.en.sigie.no' AS clave, 'Tu usuario no está activo en SIGIE. No es posible realizar la inscripción. Comunícate con el área de control escolar.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.ud.que.conserva.el.adeudo' AS clave, 'UD que conserva el adeudo' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.una.optativa.requerida.no.puede.sustituir.el.adeudo' AS clave, 'Una optativa requerida no puede sustituir el adeudo de otra.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.una.optativa.requerida.reprobada.debe.recursarse.no.puede' AS clave, 'Una optativa requerida reprobada debe recursarse; no puede sustituir otro adeudo.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.unidad.didactica' AS clave, 'Unidad didáctica' AS valor, 'gw.inscripcionManual.panelInscripcion.tablaColumna.curso' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.aplicar.restriccion.de.optativas.en' AS clave, 'Valor no válido: Aplicar restricción de optativas en la ruta restante' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.exigir.seleccion.de.obligatorias.reprobadas' AS clave, 'Valor no válido: Exigir selección de obligatorias reprobadas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.exigir.seleccion.de.optativas.reprobadas' AS clave, 'Valor no válido: Exigir selección de optativas reprobadas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.exigir.todas.las.obligatorias.del' AS clave, 'Valor no válido: Exigir todas las obligatorias del semestre restringido' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.maximo.de.reprobadas.totales.para' AS clave, 'Valor no válido: Máximo de reprobadas totales para avance anual' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.minimo.de.obligatorias.seleccionadas' AS clave, 'Valor no válido: Mínimo de obligatorias seleccionadas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.optativas.requeridas.del.plan' AS clave, 'Valor no válido: Optativas requeridas del plan' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.permitir.optativas.de.otros.semestres' AS clave, 'Valor no válido: Permitir optativas de otros semestres durante el avance anual' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.permitir.semestre.complementario.del.mismo' AS clave, 'Valor no válido: Permitir semestre complementario del mismo año' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.primer.semestre.de.origen.para' AS clave, 'Valor no válido: Primer semestre de origen para electivas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.restringir.a.un.semestre.a' AS clave, 'Valor no válido: Restringir a un semestre a partir de reprobadas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.restringir.optativas.a.partir.de' AS clave, 'Valor no válido: Restringir optativas a partir de reprobadas totales' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.restringir.optativas.a.partir.de.2' AS clave, 'Valor no válido: Restringir optativas a partir de optativas reprobadas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.segundo.semestre.de.origen.para' AS clave, 'Valor no válido: Segundo semestre de origen para electivas' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.valor.no.valido.semestre.minimo.de.los.espacios' AS clave, 'Valor no válido: Semestre mínimo de los espacios electivos' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.vinculo.de.optativa.invalido' AS clave, 'Vínculo de optativa inválido.' AS valor, 'gw.ga.btn.buscar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.ya.cuenta.con.una.inscripcion.para.este.periodo' AS clave, 'Ya cuenta con una inscripción para este periodo. Actualiza la página para consultar las asignaturas inscritas.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
    UNION ALL
    SELECT 'gw.inscripcion.mensaje.ya.cuenta.con.una.inscripcion.para.este.periodo.2' AS clave, 'Ya cuenta con una inscripción para este periodo.' AS valor, 'gw.alumno.inscripcion.finalizar' AS referencia
) textos
JOIN tbl_textos_sistema referencia ON referencia.clave = textos.referencia
ON DUPLICATE KEY UPDATE tbl_textos_sistema.clave = VALUES(clave);
