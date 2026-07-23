-- Normalización de textos V2 con acentos y ñ para catálogos ya cargados

UPDATE cat_tipo_caso_academico
SET nombre = 'Unidades didácticas no acreditadas',
    descripcion = 'Casos donde varias UD no acreditadas condicionan la trayectoria activa.'
WHERE clave = 'UNIDADES_NO_ACREDITADAS';

UPDATE cat_tipo_caso_academico
SET nombre = 'Omisión documentada',
    descripcion = 'Casos donde existe soporte documental para tratar la ausencia de registro como omisión y no como no acreditación.'
WHERE clave = 'OMISION_DOCUMENTADA';

UPDATE cat_tipo_caso_academico
SET nombre = 'Continuidad dentro del mismo año',
    descripcion = 'Casos donde la solicitud corresponde al mismo año académico y debe distinguirse de un avance indebido a un año superior.'
WHERE clave = 'CONTINUIDAD_MISMO_ANIO';

UPDATE cat_motivo_restriccion
SET nombre = 'Acumulación de no acreditadas',
    descripcion = 'La trayectoria mantiene varias UD no acreditadas que reducen la libertad de selección.'
WHERE clave = 'ACUMULACION_NO_ACREDITADAS';

UPDATE cat_motivo_restriccion
SET nombre = 'Primer año incompleto',
    descripcion = 'No procede habilitar UD del segundo año mientras el primer año permanezca incompleto.'
WHERE clave = 'PRIMER_ANIO_INCOMPLETO';

UPDATE cat_motivo_restriccion
SET nombre = 'Omisión documentada',
    descripcion = 'Existe evidencia documental suficiente para tratar el caso como omisión y permitir ajuste.'
WHERE clave = 'OMISION_DOCUMENTADA_MOTIVO';

UPDATE cat_motivo_restriccion
SET nombre = 'Sin dictamen académico',
    descripcion = 'No existe resolución DAEAE/DD o información suficiente para ejecutar el caso.'
WHERE clave = 'SIN_DICTAMEN_ACADEMICO';

UPDATE cat_motivo_restriccion
SET nombre = 'Continuidad en el mismo año académico',
    descripcion = 'La solicitud corresponde al mismo año y debe analizarse con carga, pendientes y oferta, no como avance a otro año.'
WHERE clave = 'MISMO_ANIO_ACADEMICO';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Rezago con no acreditadas',
    mensaje = 'Tu trayectoria presenta UD no acreditadas que deben atenderse primero. En este período no conviene intentar abrir unidades de un año superior si el anterior sigue incompleto.'
WHERE clave = 'EST_INS_REZAGO_NO_ACREDITADAS_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Seguimiento de no acreditadas',
    mensaje = 'Durante cursamiento enfócate en cerrar tus UD activas y prepara el siguiente período con base en las no acreditadas y la carga que el sistema realmente permita.'
WHERE clave = 'EST_CUR_REZAGO_NO_ACREDITADAS_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Solo optativas habilitadas',
    mensaje = 'Aunque esperes obligatorias, en tu situación actual el sistema puede limitarte a optativas o pendientes compatibles. Esa restricción no se corrige con un desbloqueo manual.'
WHERE clave = 'EST_INS_SOLO_OPTATIVAS_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Continuidad dentro del mismo año',
    mensaje = 'Si tu solicitud corresponde al mismo año académico, el análisis correcto no es solo avance anual: también cuentan tus pendientes, la carga máxima y la oferta disponible.'
WHERE clave = 'EST_INS_MISMO_ANIO_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Caso por omisión documentada',
    mensaje = 'Si existe evidencia institucional de omisión de registro, el caso puede corregirse como ajuste documentado y no como no acreditación.'
WHERE clave = 'EST_INS_OMISION_DOC_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Información insuficiente para resolver',
    mensaje = 'Aún no hay elementos suficientes para resolver tu caso. Se requiere descripción clara, UD involucradas y resolución académica para evitar ajustes incorrectos.'
WHERE clave = 'EST_INS_SIN_DICTAMEN_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Rezago por no acreditadas',
    mensaje = 'Si el primer año permanece incompleto por UD no acreditadas, no procede habilitar obligatorias del segundo año. La respuesta debe centrarse en prioridad, carga y restricción activa.'
WHERE clave = 'GES_INS_REZAGO_NO_ACREDITADAS_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Seguimiento de rezago con no acreditadas',
    mensaje = 'Documenta cuántas UD no acreditadas explican la restricción y comunica el siguiente paso sin prometer desbloqueos manuales.'
WHERE clave = 'GES_CUR_REZAGO_NO_ACREDITADAS_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Solo optativas habilitadas por sistema',
    mensaje = 'Cuando el sistema solo habilita optativas, no debe forzarse una apertura manual de obligatorias salvo resolución académica expresa y consistente.'
WHERE clave = 'GES_INS_SOLO_OPTATIVAS_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Continuidad del mismo año académico',
    mensaje = 'Distingue continuidad dentro del mismo año contra avance a otro año. Aun siendo continuidad, deben mantenerse carga, oferta y restricción por pendientes.'
WHERE clave = 'GES_INS_MISMO_ANIO_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Omisión documentada viable',
    mensaje = 'Si hay soporte documental suficiente, el caso puede tratarse como omisión y ajustarse sin catalogarlo como no acreditación.'
WHERE clave = 'GES_INS_OMISION_DOC_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Caso sin dictamen utilizable',
    mensaje = 'No ejecutar ajustes mientras falten descripción del caso, UD involucradas, resolución DAEAE/DD o evidencia mínima para sustentar la acción.'
WHERE clave = 'GES_INS_SIN_DICTAMEN_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Regularización condicionada por cierre anual',
    mensaje = 'En casos de cierre anual la recomendación debe aclarar si la selección es viable por avance, por carga o por regularización acotada, sin mezclar reglas.'
WHERE clave = 'GES_INS_CIERRE_ANUAL_V2';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Alto riesgo por no acreditadas acumuladas',
    mensaje = 'Tu trayectoria presenta un riesgo alto por acumulación de UD no acreditadas. En este período debes priorizar regularización, atender primero las UD críticas y evitar combinar carga que aumente el rezago.'
WHERE clave = 'EST_INS_ALTO_RIESGO_NO_ACREDITADAS_V3';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Alto riesgo en cursamiento por no acreditadas',
    mensaje = 'Durante cursamiento tu prioridad es cerrar las UD activas y preparar el siguiente período desde una estrategia de regularización. No conviene planear avance adicional mientras el rezago severo siga activo.'
WHERE clave = 'EST_CUR_ALTO_RIESGO_NO_ACREDITADAS_V3';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Riesgo alto por no acreditadas acumuladas',
    mensaje = 'Si la trayectoria muestra cinco o más UD no acreditadas, la orientación debe ser explícita: priorizar regularización, evitar aperturas incompatibles y comunicar con claridad el impacto sobre continuidad y carga.'
WHERE clave = 'GES_INS_ALTO_RIESGO_NO_ACREDITADAS_V3';

UPDATE cat_mensaje_institucional_contextual
SET titulo = 'Seguimiento de alto riesgo por rezago severo',
    mensaje = 'Documenta el número de no acreditadas, la restricción dominante y la prioridad académica. La recomendación debe centrarse en regularización intensiva y no en aperturas adicionales.'
WHERE clave = 'GES_CUR_ALTO_RIESGO_NO_ACREDITADAS_V3';

UPDATE rel_patron_caso_academico
SET nombre_patron = 'Alto riesgo por acumulación severa de no acreditadas',
    descripcion = 'Patrón para trayectorias con cinco o más UD no acreditadas donde la recomendación debe centrarse en regularización intensiva y continuidad controlada.'
WHERE nombre_patron = 'Alto riesgo por acumulacion severa de no acreditadas';
