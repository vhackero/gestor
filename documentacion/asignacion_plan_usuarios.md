# Cambio o asignación de plan

Propuesta: `C:\Users\luisf\Pictures\capturas\R029_01012026.drawio`.
Ruta nueva: `plataforma/administracion/usuarios/asignacion-plan`.
Botón de acceso desde Administración / Usuarios.

## Estado de implementación

Implementados la vista, el acceso, los catálogos dependientes, la búsqueda y la asignación y modificación individual o múltiple. La modificación actualiza la relación existente en `tbl_persona_aspirante`, conservando su identificador. No inserta una nueva relación.

## Asignación y consulta

Ambas secciones usan campos `inputText`. Se aceptan hasta 500 nombres de usuario por operación, separados por espacios, comas o punto y coma. Se normalizan las mayúsculas y se rechazan usuarios repetidos. Cada usuario debe existir y tener rol Estudiante (2). No se exige que ese sea su único rol.

Los planes disponibles tienen `id_estatus_plan=3` y una relación en `rel_convocatoria_planesyprogramas`. Las convocatorias deben estar activas y vinculadas específicamente al plan seleccionado. La consulta del diagrama se corrigió para relacionar la convocatoria del combo con la fila correspondiente, además del plan.

La asignación inserta únicamente en `tbl_persona_aspirante`; rechaza cualquier registro previo de la persona, incluso si corresponde a otro plan o convocatoria, antes de guardar cualquier registro del lote. Cada estudiante puede tener como máximo una asignación. Para cambiarla debe utilizar Modificar. Puede quedar sin asignación si un operador autorizado elimina su único registro. Se ejecuta en una transacción y bloquea las personas en orden; las lecturas de asignaciones para guardar o eliminar usan `FOR UPDATE` para consultar el estado vigente después de adquirir los bloqueos, también con aislamiento REPEATABLE READ. Esta coordinación no sustituye restricciones de integridad para escrituras realizadas por otros módulos.

La búsqueda usa proyecciones escalares y convierte números mediante `Number`; no devuelve contraseñas ni entidades Hibernate con relaciones pendientes de inicializar. Si alguno de los usuarios no tiene asignación, informa cuáles son.

Después de Buscar asignaciones, seleccione las filas que desea modificar. Para una sola relación se precargan su plan y convocatoria. Elija el plan de destino y una convocatoria activa vinculada: si la convocatoria actual también corresponde al nuevo plan, se conserva seleccionada. Pulse Modificar para actualizar las relaciones seleccionadas.

Antes de guardar se rechazan destinos duplicados, selecciones repetidas por persona y relaciones cambiadas después de la búsqueda. Las actualizaciones usan el identificador de relación y los valores originales como control de concurrencia. El lote se guarda en una sola transacción; un fallo revierte el conjunto.

Cada relación consultada muestra el icono de eliminación a los operadores con permiso, incluso si es la única asignación. Sólo elimina el registro elegido y requiere confirmación con matrícula, plan y convocatoria. Si es la última, la confirmación indica que el estudiante quedará sin plan asignado. Se exige resolver las relaciones adicionales antes de Modificar. No se eliminaron asignaciones reales de forma automática. Después de eliminar el último registro la tabla queda vacía sin intentar consultar una asignación que ya no existe; se puede utilizar Asignar para registrar un plan nuevamente.

No se migran inscripciones, bajas, calificaciones, participaciones ni usuarios Moodle. El cambio de pertenencia sí modifica qué plan consultan otros módulos; no altera el historial previo.

## Permisos y textos

Aplicar `codigoFuente/bd/R063_13102026_asignacion_plan_usuarios.sql`, ya aplicado en la base local. Crea tres permisos:

- `USU_PLAN_VER`: botón, vista y consultas.
- `USU_PLAN_ASIGNAR`: asignación; requiere también el permiso de consulta.
- `USU_PLAN_MODIFICAR`: mostrar el destino y guardar modificaciones; requiere también el permiso de consulta.
- `USU_PLAN_ELIMINAR`: eliminar asignaciones, incluida la única relación; requiere también consulta.

Ejecutar únicamente `codigoFuente/bd/R063_13102026_asignacion_plan_usuarios.sql`. Integra los cambios de los antiguos R063, R064 y R065: cuatro permisos, textos finales y actualización de los textos predeterminados anteriores. Los dos archivos adicionales se retiraron para evitar ejecutar versiones redundantes. Puede aplicarse aunque los tres scripts originales ya se hayan ejecutado: conserva los permisos concedidos a los roles y los textos personalizados. No crea tablas ni elimina asignaciones.

No se conceden automáticamente a ningún rol. Se administran desde Roles y permisos. Los textos `gw.planUsuario.*` se administran mediante los textos del sistema. Se preservan los valores existentes al volver a aplicar la migración.

No se crean tablas ni se agregan columnas o índices a las tablas académicas actuales. Las asignaciones se almacenan exclusivamente en `tbl_persona_aspirante`.

## Validación

Compilación directa con Java 8. Once pruebas comprobaron normalización, asignación única, usuarios sin rol Estudiante, destino incompatible, permisos declarados, transacciones declaradas y SQL sobre tablas temporales de MariaDB. La prueba SQL verificó inserción, consulta, modificación del mismo identificador, rechazo de datos desactualizados, rechazo de un segundo plan, eliminación de duplicados, eliminación del último registro, conservación de otros estudiantes, nueva asignación posterior y reversión. No se asignaron, cambiaron ni eliminaron planes de estudiantes reales. Se validó la estructura XML de las vistas; queda validar visualmente en pantalla después del despliegue.
