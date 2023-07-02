--
-- Dumping data for table `mdl_modulos`
--

INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (1,'Tarea','assign',60,0,1,'',1,'La actividad de tarea de Moodle proporciona un espacio en el que los estudiantes pueden enviar sus trabajos para que los profesores los califiquen y proporcionen retroalimentación. Esto ahorra papel y es más eficiente que el Email. También puede usarse para recordarles a los estudiantes sobre tareas de la vida real  que ellos necesitan completar fuera-de-línea, como por ejemplo actividades artísticas, y que no requieren de contenidos digitales.  Los envíos de los estudiantes están juntos en una pantalla en su curso. Usted puede pedirles que envíen uno o varios archivos y/o que escriban ensayos de texto. Es posible que envíen un trabajo en equipo y Usted puede elegir calificarles su trabajo de forma  ciega , lo que significa que no ve las identidades de quienes hayan enviado tareas. Las tareas pueden tener fechas finales y fechas fatales - que Usted podría extender en caso necesario.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (2,'Tarea','assignment',60,0,0,'',1,'La actividad de tarea de Moodle proporciona un espacio en el que los estudiantes pueden enviar sus trabajos para que los profesores los califiquen y proporcionen retroalimentación. Esto ahorra papel y es más eficiente que el Email. También puede usarse para recordarles a los estudiantes sobre tareas  de la vida real  que ellos necesitan completar fuera-de-línea, como por ejemplo actividades artísticas, y que no requieren de contenidos digitales.  Los envíos de los estudiantes están juntos en una pantalla en su curso. Usted puede pedirles que envíen uno o varios archivos y/o que escriban ensayos de texto. Es posible que envíen un trabajo en equipo y Usted puede elegir calificarles su trabajo de forma  ciega , lo que significa que no ve las identidades de quienes hayan enviado tareas. Las tareas pueden tener fechas finales y fechas fatales - que Usted podría extender en caso necesario.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (3,'Libro','book',0,0,1,'',2,'El módulo libro le permite a un profesor crear un recurso multi-página en un formato similar-a-libro, con capítulos y subcapítulos. Los libros pueden contener archivos multimedia y texto y son útiles para mostrar pasajes largos de información que puede dividirse en secciones.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (4,'Chat','chat',300,0,1,'',1,'El módulo de actividad de chat les permite a los participantes tener una discusión sincrónica en tiempo real dentro de un curso Moodle.  Esta es una forma útil de obtener una comprensión diferente acerca de cada uno y de los tópicos que se discuten - el modo de usar una sala de chat es muy diferente de los foros asincrónicos. El módulo de chat contiene varias características para gestionar y evisar las discusiones del chat.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (5,'Elección','choice',0,0,1,'',1,'Una actividad de elección le permite a Usted hacer una pregunta y configurar botones elegibles que los estudiantes pueden seleccionar de un número de respuestas posibles. Los etudiantes pueden elegir una o más opciones y pueden actualizar su elección si Usted se los permite. Las elecciones pueden ser útiles como encuestas rápidas para estimular el pensamiento sobre un tópico/tema; para permitirle a los alumnos que voten sobre la dircción que tomará el curso, o para sopesar el progreso.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (6,'Base de Datos','data',0,0,1,'',1,'El módulo de actividad BasedeDatos le permite al maestro o al estudiante, mostrar y buscar un banco de entradas de registros acerca de cualquier tópico concebible. El formato y la estructura de estas entradas puede ser casi ilimitada, incluyendo a imágenes, archivos, URLs, números y texto, entre otras cosas.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (7,'feedback','feedback',0,0,0,'',1,'La actividad de Retroalimentación le permite crear y aplicar encuestas, con el propósito de conocer la opinión de sus Alumnos. Su alcance es más pequeño, por lo tanto es más simple que el módulo Cuestionario y, a diferencia de la herramienta de encuesta predefinida, le permite escribir sus propias preguntas, en lugar de escoger de una lista de encuestas pre-fabricadas. A diferencia del Cuestionario / (Examen), Usted puede crear preguntas sin calificación. La actividad feedback (retroalimentación) es ideal para evaluaciones al curso o al profesor.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (8,'Folder','folder',0,0,1,'',2,'El módulo carpeta (folder) le permite al profesor mostrar un número de archivos relacionados que están dentro de una sola carpeta, reduciendo el arrastrado (scrolling) de la página del curso. Un archivo comprimido en ZIP puede subirse y luego descomprimirse para ser mostrado, o se puede crear una carpeta vacía y se le suben archivos dentro de ella.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (9,'Foro','forum',0,0,1,'',1,'La actividad de Foro le permite a los estudiantes y profesores intercambiar ideas al publicar comentarios como parte de un  hilo  de una discusión. Se pueden incluir archivos tales como imágenes y multimedios dentro de las publicaciones en foro. El profesor puede elegir valorar publicaciones en foros y también es posible darles permiso a los estudiantes para que valoren las publicaciones de unos a otros.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (10,'Glosario','glossary',0,0,1,'',1,'El módulo de actividad glosario les permite a los participantes crear y mantener una lista de definiciones, similar a un diccionario.  El glosario puede emplearse en varias formas. Se pueden buscar y ojear las entradas en diferentes formatos. Un glosario puede ser una actividad colaborativa o estar restringida a las entradas hechas por el maestro. Las entradas pueden ponerse en categorías. La característica de enlace automático resalará cualquier palabra dentro de un curso que esté localizada en el glosario. ');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (11,'Paquete contenido IMS','imscp',0,0,1,'',2,'Un paquete de contenidos IMS es una colección de archivos que están empaquetados de acuerdo a un stándard acordado, de forma tal que puede ser re-utilizado en diferentes sistemas. El módulo de paquete de contenidos IMS permite que dichos paquetes de contenidos se suban como un archivo ZIP y se añadan al curso como recurso. El contenido generalmente se muestra en varias páginas, con navegación entre estas páginas. Hay varias opciones para mostrar el contenido en ventana emergente, con un menú de navegación por botones, etc. Un paquete de contenidos IMS puede usarse para presentar animaciones y contenidos multimedia.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (12,'Etiqueta','label',0,0,1,'',2,'El módulo etiqueta permite insertar texto y multimedia en una página de curso junto con enlaces a otros recursos y actividades. Las etiquetas son muy versátiles y pueden ayudar a mejorar la apariencia de un curso si se emplean inteligentemente.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (13,'Lección','lesson',0,0,1,'',1,'La actividad de lección presenta una serie de páginas HTML al estudiante, a quien generalmente se le pregunta que elija una opción entre varias al final del contenido. La opción que elija le llevará a una página específica de la lección. En su forma más simple, el estudiante puede elegir el botón para  Continuar  al fondo de la página, lo que lo manda a la siguiente página de la lección.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (14,'Iti','lti',0,0,1,'',2,'');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (15,'Pagina','page',0,0,1,'',2,'El módulo Página le permite al profesor crear una página web empleando el editor de texto. En una página se pueden mostrar texto, imágenes, sonido, video, enlaces de internet y código incrustado (como los mapas de Google). Las ventajas de emplear el módulo página, en lugar del módulo archivo incluyen que el recurso será más accesible (por ejemplo, para los usuarios de dispositivos móviles como tabletas y teléfonos inteligentes), y más fáciles de actualizar. Para mayores contenidos, se recomienda emplear el módulo libro en lugar de una página.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (16,'Examen','quiz',60,0,1,'',1,'El módulo de actividad de Examen le permite al maestro diseñar y construir exámenes que consisten de una gran variedad de Tipos de preguntas, incluyendo preguntas de opción múltiple, falso-verdadero y respuesta corta. Estas preguntas se mantienen en el Banco de preguntas y pueden ser re-utilizadas en diferentes exámenes.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (17,'Archivo','resource',0,0,1,'',2,'El módulo archivo (file) le permite al profesor proporcionar un archivo como recurso para un curso. Donde sea posible, el archivo se mostrará dentro de la interfaz del curso; de otra forma, se les pedirá a los estudiantes que descarguen el archivo. Este archivo puede incluir archivos de soporte tal como una página HTML que tenga incrustadas imágenes u objetos Flash. Observe que los estudiantes DEBEN tener el software apropiado en sus computadoras para poder abrir el archivo.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (18,'Paquete SCORM','scorm',300,0,1,'',1,'Un paquete SCORM es una colección de archivos que están empaquetados de acuerdo a el stándard acordado para objetos de aprendizaje. El módulo de actividad SCORM permite que los paquetes SCORM o AICC se suban como un archivo ZIP y se añadan al curso. El contenido generalmente se muestra sobre varias páginas, con navegación entre ellas. Hay varias opciones para mostrar contenidos; en ventana emergente, con una tabla de contenidos, con botones de navegación, etc. Las actividades SCORM generalmente incluyen preguntas, con calificaciones que se guardan en el libro de calificación.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (19,'Encuesta predefinida','survey',0,0,1,'',1,'El módulo de actividad Encuesta predefinida proporciona un número de instrumentos de encuesta verificada que han demostrado utilidad para evaluar y estimular el aprendizaje en el entorno en línea. El profesor puede utilizarlos para recopilar información que le ayude a conocer mejor su clase y reflexionar sobre su propia enseñanza. Observe que estas herramientas de encuesta están pre-pobladas con preguntas. Los profesores que deseen crear sus propias encuestas deberían emplear mejor el módulo de actividad retroalimentación.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (20,'URL','url',0,0,1,'',2,'El módulo URL le permite al profesor proporcionar un enlace de internet para un recurso del curso. Cualquier cosa libremente disponible en línea, como documentos o imágenes, puede enlazarse a un URL y la URL de una página web en particular no requiere ser forzosamente la página principal del sitio web. Se puede copiar y pegar el URL de una página web en particular o un maestro podría emplear el selector de archivos de Moodle y elegir un enlace de un repositorio como Flickr, YouTube o Wikimedia (dependiendo de cuales repositorios están habilitados en el sitio). Existen varias opciones de visualización para el URL, como incrustado, abrir en ventana nueva emergente y opciones avanzadas para pasar información, como el nombre del estudiante, si la URL lo necesitara.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (21,'Wiki','wiki',0,0,1,'',1,'El módulo de actividad Wiki le permite a los participantes añadir y editar una colección de páginas web. Un wiki puede ser colaborativo, donde todos pueden editarlo, o puede ser individual, donde cada quien tiene su propio wiki que solamente esa persona puede editar. Se conserva una historia de las versiones previas de cada página del wiki, que enlista los cambios hechos por cada participante. Los Wikis tienen muchos usos, tales como Para los apuntes de clase o guías de estudio para el grupo. Para los profesores de una escuela que planean una estrategia o reunión de trabajo en equipo.');
INSERT INTO `mdl_modulos` (`id`,`nombre`,`identificador`,`cron`,`ultimo_cron`,`activo`,`ruta_imagen`,`tipo_modulo`,`descripcion`) VALUES (22,'Taller','workshop',60,0,1,'',1,'El módulo de actividad Taller permite la colecta, revisión y evaluación por pares del trabajo de los estudiantes. Los estudiantes pueden enviar cualquier contenido digital (archivos), tales como documentos de procesador de texto o de hojas de cálculo y también pueden escribir el texto directamente en un campo empleando un editor de texto (dentro de Moodle).');



--
-- Dumping data for table `tbl_catalogos_generales`
--
INSERT INTO `tbl_catalogos_generales` VALUES (1,'cat_tpo_plataforma_lms',1,'2016-10-20 16:39:55',1),(2,'cat_tpos_competencia',1,'2016-12-07 16:56:46',1),(3,'cat_ejes_capacitacion_estrategicos',1,'2016-12-07 16:56:46',1),(4,'cat_ejes_capacitacion_gestion',1,'2016-12-07 17:06:54',1);




--
-- Dumping data for table `rel_catalogo_general_valores`
--

INSERT INTO `rel_catalogo_general_valores` VALUES (1,'Libre','2',1,NULL,'2016-10-20 16:46:19',NULL,1),(1,'Propietario','1',1,NULL,'2016-10-20 16:46:19',NULL,1),(2,'Estrategicos','3',1,NULL,'2016-12-07 17:06:54',NULL,1),(2,'Gestion','4',1,NULL,'2016-12-07 17:06:54',NULL,1),(3,'Institucionales','1',1,NULL,'2016-12-07 17:06:54',NULL,1),(3,'Regulatorios','2',1,NULL,'2016-12-07 17:06:54',NULL,1),(3,'Servicios','3',1,NULL,'2016-12-07 17:06:54',NULL,1),(4,'Capital Humano','2',1,NULL,'2016-12-07 17:06:55',NULL,1),(4,'Procesos','1',1,NULL,'2016-12-07 17:06:54',NULL,1);

--
-- Dumping data for table `tbl_planes`
--
INSERT INTO `tbl_planes` VALUES (1,'01-17','SEDESOL',1,3,1,5,'2017-01-11 12:05:36','2017-01-11 12:05:36',13,NULL,NULL,NULL,NULL,NULL,NULL,1,2,1,'2017-01-11 12:05:36','2017-01-11 12:05:36',1),(47,'PL-090117-001','PLAN GENERICO I',1,3,1,5,'2017-01-09 00:00:00','2017-03-31 00:00:00',13,'plan generico para fines de prueba UP','objetivos generales del plan','el alumno debera contar con conocimientos basicos','el egresado sera capaz de analizar detalladamente los sucesos ',1,1,1,2,1,'2017-01-06 11:13:23','2017-01-06 12:10:02',2),(52,'PL-090117-001','PLAN GENERICO I',1,3,1,5,'2017-01-09 00:00:00','2017-03-31 00:00:00',13,'plan generico para fines de prueba UP','objetivos generales del plan','el alumno debera contar con conocimientos basicos','el egresado sera capaz de analizar detalladamente los sucesos ',1,1,2,2,2,'2017-01-06 13:37:01','2017-01-06 13:41:44',2),(53,'PL-090117-001','PLAN VERSIONADO II',1,3,1,5,'2017-01-09 00:00:00','2017-03-31 00:00:00',13,'plan generico para fines de prueba UP','objetivos generales del plan','el alumno debera contar con conocimientos basicos','el egresado sera capaz de analizar detalladamente los sucesos ',1,1,3,2,3,'2017-01-06 13:57:21','2017-01-06 15:45:57',2);



--
-- Dumping data for table `tbl_malla_curricular`
--

INSERT INTO `tbl_malla_curricular` VALUES (1,'Modelo de capacitación por competencias',NULL,NULL,2,NULL,'2016-09-13 12:33:24',1,NULL,1),(2,'Estratégicos',NULL,1,3,NULL,'2016-09-13 12:39:42',1,NULL,1),(3,'De gestión','estructura 2 del plan sedesol test',1,3,NULL,'2016-09-13 12:45:32',1,NULL,1),(4,'Institucionales','sub estructura 1.1',2,4,NULL,'2016-09-13 13:25:01',1,NULL,1),(7,'Regulatorios',NULL,2,4,NULL,'2016-09-13 13:51:02',1,NULL,1),(8,'Servicios',NULL,2,4,NULL,'2016-09-13 13:51:02',1,NULL,1),(9,'Procesos',NULL,3,4,NULL,'2016-09-13 13:51:02',1,NULL,1),(10,'Capital humano',NULL,3,4,NULL,'2016-09-13 13:51:02',1,NULL,1),(47,'PLAN GENERICO I',NULL,NULL,2,47,'2017-01-06 11:13:23',2,NULL,1),(49,'SUB_ESTRUCTURA_II',NULL,47,3,NULL,'2017-01-06 11:15:16',2,'2017-01-06 13:28:46',1),(53,'SUB_ESTRUCTURA_I',NULL,47,3,NULL,'2017-01-06 13:21:54',2,'2017-01-06 13:28:46',1),(54,'PLAN GENERICO I',NULL,NULL,2,52,'2017-01-06 11:13:23',2,NULL,1),(55,'SUB_ESTRUCTURA_II',NULL,54,3,NULL,'2017-01-06 11:15:16',2,'2017-01-06 13:37:13',1),(56,'SUB_ESTRUCTURA_I',NULL,54,3,NULL,'2017-01-06 13:21:54',2,'2017-01-06 13:37:14',1),(57,'PLAN VERSIONADO II',NULL,NULL,2,53,'2017-01-06 11:13:23',2,'2017-01-06 15:45:57',1),(58,'SUB_ESTRUCTURA_II',NULL,57,3,NULL,'2017-01-06 11:15:16',2,'2017-01-06 13:57:32',1),(59,'SUB_ESTRUCTURA_I',NULL,57,3,NULL,'2017-01-06 13:21:54',2,'2017-01-06 13:57:32',1);



--
-- Dumping data for table `rel_ejes_competencias`
--

INSERT INTO `rel_ejes_competencias` VALUES (4,1,'2017-02-03 11:07:41',2),(4,2,'2017-02-03 11:07:41',2),(4,4,'2017-02-03 11:07:41',2),(4,5,'2017-02-03 11:07:41',2),(4,6,'2017-02-03 11:07:41',2),(4,7,'2017-02-03 11:07:41',2),(4,8,'2017-02-03 11:07:41',2),(4,9,'2017-02-03 11:07:41',2),(7,10,'2017-02-03 11:09:41',2),(7,11,'2017-02-03 11:09:41',2),(7,12,'2017-02-03 11:09:41',2),(7,13,'2017-02-03 11:09:41',2),(7,14,'2017-02-03 11:09:41',2),(7,15,'2017-02-03 11:09:41',2),(7,16,'2017-02-03 11:09:41',2),(7,17,'2017-02-03 11:09:41',2),(8,18,'2017-02-03 11:10:53',2),(8,19,'2017-02-03 11:10:53',2),(8,20,'2017-02-03 11:10:53',2),(8,21,'2017-02-03 11:10:53',2),(8,22,'2017-02-03 11:10:53',2),(8,23,'2017-02-03 11:10:53',2),(8,24,'2017-02-03 11:10:53',2),(9,25,'2017-02-03 11:31:55',2),(9,26,'2017-02-03 11:31:55',2),(9,27,'2017-02-03 11:31:55',2),(9,28,'2017-02-03 11:31:55',2),(9,29,'2017-02-03 11:31:55',2),(9,30,'2017-02-03 11:31:55',2),(9,31,'2017-02-03 11:31:55',2),(9,32,'2017-02-03 11:31:55',2),(10,33,'2017-02-03 11:33:52',2),(10,34,'2017-02-03 11:33:52',2),(10,35,'2017-02-03 11:33:52',2),(10,36,'2017-02-03 11:33:52',2),(10,37,'2017-02-03 11:33:52',2),(10,38,'2017-02-03 11:33:52',2),(10,39,'2017-02-03 11:33:52',2),(10,40,'2017-02-03 11:33:52',2);


--
-- Dumping data for table `tbl_funcionalidades`
--

INSERT INTO `tbl_funcionalidades` VALUES 
(1,'ADMIN',NULL,'Administración','2014-12-24 11:13:28','2016-10-18 11:30:11',1,39),
(2,'CATALOGOS',1,'Catalogos','2014-12-24 11:13:28',NULL,1,0),
(3,'MODULOS',2,'Módulos','2014-12-24 11:13:28','2016-09-06 12:33:16',1,2),
(9,'ROLES',2,'Roles','2016-06-28 16:43:28',NULL,1,0),
(31,'FUN_AGR',3,'Agregar funcionalidad','2016-06-30 13:04:43',NULL,1,0),
(33,'ROL_AGR',9,'Agregar rol','2016-06-30 13:08:41','2016-07-25 17:40:57',1,1),
(35,'EST_TERR',2,'Localización','2016-07-05 10:55:21','2016-10-19 11:04:42',1,39),
(36,'MUNICIPIOS',35,'Municipios','2016-07-05 10:55:54','2016-07-26 11:47:48',1,1),
(37,'MUN_AGR',36,'Agregar municipios','2016-07-05 10:56:21',NULL,1,0),
(38,'MUN_EDI',36,'Editar municipio','2016-07-05 10:57:13',NULL,1,0),
(39,'ASENTA',35,'Asentamientos','2016-07-05 10:58:19','2016-07-28 17:32:08',1,1),
(40,'ASEN_AGR',39,'Agregar asentamiento','2016-07-05 10:58:44',NULL,1,0),
(41,'ASEN_EDI',39,'Editar asentamiento','2016-07-05 11:01:51',NULL,1,0),
(42,'FUN_EDI',3,'Editar funcionalidad','2016-07-05 11:03:07',NULL,1,0),
(44,'PERSONA',1,'Usuarios','2016-07-19 16:06:52','2016-09-06 12:43:55',1,2),
(45,'PER_AGR',44,'Agregar persona','2016-07-19 16:07:47',NULL,1,1),
(46,'PER_EDI',44,'Editar persona','2016-07-19 16:08:44',NULL,1,1),
(47,'LOGIN',1,'Acceso al sistema','2016-07-20 15:33:59','2016-09-29 13:32:19',1,1),
(49,'FUN_TEX',3,'Textos del sistema','2016-07-25 12:55:28',NULL,1,1),
(50,'TEX_SIS_AGR',49,'Agregar Texto','2016-07-25 12:56:27','2016-07-28 11:30:37',1,1),
(51,'TEX_SIS_EDI',49,'Editar texto','2016-07-25 12:57:12','2016-07-28 11:31:09',1,1),
(52,'ROL_EDI',9,'Editar rol','2016-07-25 17:41:21',NULL,1,1),
(53,'ROL_PERM',9,'Permisos del rol','2016-07-25 17:41:57',NULL,1,1),
(54,'ROL_PERM_GRD',53,'Guardar permisos del rol','2016-07-25 17:42:31',NULL,1,1),
(56,'REG_PUBLICO',44,'Registro Publico','2016-07-26 11:02:26',NULL,1,2),
(57,'BUSQ_AVAN_PER',44,'Busqueda Avanzada','2016-07-26 11:22:19',NULL,1,2),
(58,'CON_BIT',1,'Consultar bitácora','2016-07-26 12:38:31',NULL,1,1),
(60,'PER_VER',44,'Ver Info persona','2016-07-28 16:39:40',NULL,1,2),
(61,'ROLES_PER',1,'Roles Persona','2016-07-28 17:13:56',NULL,1,2),
(62,'PER_ROLES_AGR',61,'Agrega roles a persona','2016-07-28 17:15:39','2017-02-10 13:01:27',1,39),
(64,'VER_ROL_PER',61,'Ver roles persona','2016-07-28 17:19:03',NULL,1,2),
(65,'MENSAJES',1,'Mensajes','2016-08-09 12:04:34',NULL,1,1),
(66,'MSG_ENV',65,'Enviar mensaje','2016-08-09 12:40:46',NULL,1,1),
(67,'MSG_ELI',65,'Eliminar mensaje','2016-08-09 12:41:30',NULL,1,1),
(68,'MSG_DET',65,'Detalle mensaje','2016-08-09 12:42:22',NULL,1,1),
(69,'PLANES_PROGRAMAS',NULL,'Planes y Programas de capacitación','2016-08-09 17:38:54',NULL,1,1),
(70,'PLANES',69,'Planes de estudio','2016-08-09 17:39:30',NULL,1,1),
(71,'GES_ESC',NULL,'Gestión Escolar','2016-08-09 17:40:53',NULL,1,1),
(72,'EVEN_CAP',71,'Evento de Capacitación','2016-08-09 17:41:37',NULL,1,1),
(73,'ASIS_CALIF',71,'Asistencias y calificaciones','2016-08-09 17:46:11',NULL,1,1),
(74,'REG_ASIS',73,'Registro de asistencia','2016-08-09 17:46:37',NULL,1,1),
(75,'REG_CALIF',73,'Registro de Calificaciones','2016-08-09 17:47:11',NULL,1,1),
(76,'CONSTANCIAS',71,'Constancias','2016-08-09 17:48:01',NULL,1,1),
(77,'KARDEX',71,'Kárdex','2016-08-09 17:54:51',NULL,1,1),
(78,'EXP_ACAD',71,'Expediente Académico','2016-08-09 17:55:09',NULL,1,1),
(79,'GES_APREN',NULL,'Gestión del Aprendizaje','2016-08-09 17:55:29',NULL,1,1),
(80,'AVA_CONS',79,'AVA en construcción','2016-08-09 17:55:55',NULL,1,1),
(81,'AVA_SOL',79,'AVA en solicitud','2016-08-09 17:56:50',NULL,1,1),
(82,'AVA_ACT',79,'AVA activos','2016-08-09 17:57:11',NULL,1,1),
(83,'AVA_INACT',79,'AVA inactivos','2016-08-09 17:57:55',NULL,1,1),
(84,'ACC_AVA',79,'Acceso a los AVA','2016-08-09 17:58:11',NULL,1,1),
(85,'SEG_APREN',79,'Seguimiento del aprendizaje','2016-08-09 17:58:34',NULL,1,1),
(86,'ENC_EVAL',NULL,'Encuestas y Evaluaciones','2016-08-09 17:58:56',NULL,1,1),
(87,'CAT_ENCUESTA',86,'Administrar catálogos','2016-08-09 17:59:11','2017-01-19 13:51:00',1,2),
(88,'ADM_ENCUESTA',86,'Administración de encuesta','2016-08-09 17:59:31','2016-11-16 14:22:02',1,4),
(89,'LOG_ACA_INF',NULL,'Logística Académica e Infraestructura','2016-08-09 18:00:01',NULL,1,1),
(90,'LOG_ACAD',89,'Logística Académica','2016-08-09 18:00:23',NULL,1,1),
(91,'CONF_AREAS',89,'Configuración de Áreas','2016-08-09 18:00:50',NULL,1,1),
(92,'ANA_DAT',NULL,'Análisis de datos','2016-08-09 18:01:13',NULL,1,1),
(93,'ANA_DAT_ACA',92,'Análisis de datos Académicos','2016-08-09 18:01:28',NULL,1,1),
(94,'ANA_DAT_PLN_PRO',92,'Análisis de datos de Planes y Programas de Capacitación','2016-08-09 18:02:07',NULL,1,1),
(95,'ANA_DAT_GES_APRE',92,'Análisis de datos de Gestión del Aprendizaje','2016-08-09 18:02:38',NULL,1,1),
(100,'PLNT_MSGS',65,'Plantillas de mensajes','2016-08-17 13:10:10',NULL,1,1),
(101,'VAR_MGS',65,'Variables de mensajes','2016-08-17 13:11:00',NULL,1,1),
(102,'PLNT_MSG_AGR',100,'Agregar plantilla de mensaje','2016-08-17 13:11:34',NULL,1,1),
(103,'PLNT_MSG_EDI',100,'Editar plantilla de mensaje','2016-08-17 13:12:55',NULL,1,1),
(104,'VAR_MSG_AGR',101,'Agregar variable de mensaje','2016-08-17 13:13:24',NULL,1,1),
(105,'VAR_MSG_EDI',101,'Editar variable de mensaje','2016-08-17 13:13:52',NULL,1,1),
(109,'BIT_ENT_SIS',47,'Ingreso al sistema','2016-09-29 13:32:51',NULL,1,1),
(110,'BIT_SAL_SIS',47,'Salida del sistema','2016-09-29 13:34:45',NULL,1,1),
(111,'BIT_PRI_ENT',47,'Primer ingreso al sistema','2016-09-29 13:35:39',NULL,1,1),
(112,'BIT_ULT_ENT',47,'Ultimo ingreso al sistema','2016-09-29 13:36:26',NULL,1,1),
(113,'ADMIN_CAT_PLAN_PROG',69,'Admin Catalogos Planes y Programas','2016-09-29 16:38:51','2017-01-05 15:35:26',1,2),
(114,'TEM',1,'Temas','2016-10-11 15:48:09',NULL,1,39),
(115,'TEM_AGR',114,'Agregar tema','2016-10-11 15:48:28',NULL,1,39),
(116,'TEM_EDI',114,'Editar tema','2016-10-11 15:49:14',NULL,1,39),
(117,'PAR_SIS',1,'Parámetros del sistema','2016-10-28 16:25:30',NULL,1,39),
(118,'PAR_SIS_AGR',117,'Agregar parámetro','2016-10-28 16:26:24',NULL,1,39),
(119,'PAR_SIS_EDI',117,'Editar parámetro','2016-10-28 16:27:18',NULL,1,39),
(120,'PROGRAMA_CAPACIT',69,'Programa de Capacitación','2016-12-20 17:36:03',NULL,1,2),
(121,'BUSQ_PROG',120,'Busqueda de Programas','2016-12-20 18:03:18',NULL,1,2),
(122,'NUEVO_PROG',120,'Nuevo Programa','2016-12-21 12:28:51',NULL,1,2),
(123,'SCHEDULE',89,'Schedule','2016-12-26 13:02:25',NULL,1,2),
(124,'ACT_USU',1,'Actividades del usuario','2016-12-29 10:38:13',NULL,1,39),
(125,'ACT_USU_AGR',124,'Agregar actividad','2016-12-29 10:41:10',NULL,1,39),
(126,'ACT_USU_EDI',124,'Editar actividad','2016-12-29 10:41:36',NULL,1,39),
(127,'BUSQ_PLAN',70,'Busqueda de Planes','2016-12-29 13:44:18',NULL,1,2),
(128,'NUEVO_PLAN',70,'Nuevo plan','2017-01-05 11:05:20',NULL,1,2),
(129,'ADMIN_CAT_PLAN_PROG1',1,'Catalogos Planes y Programas','2017-01-05 15:46:09','2017-01-05 16:02:32',0,2),
(130,'PLN_PROG',2,'Catalogos Planes y Programas','2017-01-05 17:12:34','2017-01-05 17:14:31',1,2),
(131,'CAR_MAS_USU',1,'Carga masiva de usuarios','2017-01-05 17:30:34',NULL,1,39),
(132,'RESP_EV_CAP',71,'Responsables de evento de capacitacion','2017-02-10 12:14:14','2017-02-10 12:14:37',1,2),
(133,'PER_ROLES_ELIM',61,'Eliminar roles a persona','2017-02-10 13:01:56',NULL,1,39),
(134,'CAT_GEST_ESCOLAR',2,'Catálogos Gestión Escolar','2017-02-15 11:03:36',NULL,1,2),
(135,'CAT_LOGIST_INFRA',2,'Catalogos Logistica e Infraestructura','2017-02-23 13:19:50',NULL,1,2),
(136,'BUSQ_AREAS_CONFIG',89,'Busqueda Áreas Configuradas','2017-02-23 18:02:26',NULL,1,2),
(137,'RECURSOS',2,'Recursos','2017-03-08 09:15:31',NULL,1,2),
(138,'MODULOS_',NULL,'Módulos_','2013-12-24 11:13:28','2017-09-06 12:33:16',1,2);


--
-- Dumping data for table `rel_mensajes_operacion`
--

INSERT INTO `rel_mensajes_operacion` VALUES (1,'Mensaje agregar rol','Se ha agregado el rol {rol}<br>',33,'2016-08-16 12:53:00','2016-08-26 12:48:49',1,0,1),(2,'Mensaje agregar rol','Se ha agregado el rol $rol',33,'2016-08-18 13:11:25','2016-08-18 16:45:54',1,0,1),(3,'Mensaje agregar rol2','$rolss',33,'2016-08-18 13:12:23','2016-08-25 12:23:44',1,0,1),(4,'Mensaje agregar rol3','Se ha agrgado el rol {rol} para atender la solicitud %nueva% en base al elemento {elemento}.<br>',33,'2016-08-19 11:30:47','2016-08-25 12:22:43',1,1,2),(5,'test 01','test prueba<br>',33,'2016-09-14 13:20:49',NULL,1,0,2),(6,'SALUDAR A USUARIOS','<span style=\"color: rgb(57, 60, 62); font-family: \"Open Sans\", Helvetica, sans-serif; font-size: 14px; line-height: 20px; background-color: rgb(255, 255, 255);\">saluda a todos </span><span style=\"color: rgb(51, 51, 51); font-family: \"Open Sans\", Helvetica, sans-serif; font-size: 14px; line-height: 20px; background-color: rgb(255, 255, 255);\">mensaje_catalogos </span>',2,'2016-11-29 20:30:21','2017-01-05 15:39:57',5,0,1),(7,'REGISTRO DE USUARIOS','<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">REGISTRO DE USUARIOS mensaje_ saluda</span></font>',2,'2016-11-29 20:37:31','2016-11-29 20:45:32',4,0,2),(8,'Despedida del curso','<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">Hola alumno @usuario_alumno</span></font><div><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">Esperando que te encuentres, el profesor&nbsp;@usuario_profesor te desea lo mejor y que&nbsp;aproveches el conocimiento que te fue asignado en el curso \"{nombre_curso}\"&nbsp;.</span></font></div><div><span style=\"font-size: 13.3333px; font-family: Arial, Verdana;\"><br></span></div><div><span style=\"font-size: 13.3333px; font-family: Arial, Verdana;\">{curso_despedida}</span></div><div><span style=\"font-size: 13.3333px; font-family: Arial, Verdana;\"><br></span></div><div><span style=\"font-size: 13.3333px; font-family: Arial, Verdana;\"><br></span></div>',2,'2016-11-29 20:39:37','2016-11-29 20:46:26',4,0,2),(9,'Editar palntilla','sldfsdlfnsdf&nbsp;%EditarPlantilla%&nbsp;',116,'2016-12-15 13:15:43',NULL,39,0,3),(10,'el','prueba de erick',2,'2017-01-05 15:39:11','2017-01-05 15:39:57',5,1,1),(11,'prueba erick_alerta','pruebaprueba erick_alerta',2,'2017-01-05 15:41:01',NULL,5,0,1),(12,'prueba erick_aviso','<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">prueba erick_aviso</span></font>',2,'2017-01-05 15:41:37',NULL,5,0,2),(13,'rgerg','gsdgs',1,'2017-01-05 16:10:55',NULL,5,0,3),(14,'Asignación de Rol','Estimado(a) ${nombre_usuario} le han asignado el rol ${rol}',62,'2017-02-13 09:31:34','2017-02-13 09:32:03',39,1,2),(15,'Reasignación de rol','Estimado(a) ${nombre_usuario} le han removido el rol ${rol}',133,'2017-02-13 09:34:24',NULL,39,0,2);


--
-- Dumping data for table `tbl_persona`
--

INSERT INTO `tbl_persona` VALUES 
(1,'system','$2a$10$iq8ndRl1.bML8J7OjHeGe.v1RMwxWNwwsAojae3BQVPwNwqNfvJVG','system','elearning','root','1981-10-27','M',1,'2014-12-24 11:13:28','2017-02-16 16:12:40',2,'LOLC811027QL5','LOLC811027HTLPPR01',NULL,NULL,NULL,2,'MX'),
(2,'userDesa','$2a$10$Tv3ZiWNmflP1ysNCKjPes.WEvCHbm4NAKgG1RxQvWsfKV/QyOGhfS','Desarrollo','Planet','Media','1987-11-15','M',1,'2016-07-01 13:38:23','2017-03-07 08:49:38',39,'','LOLC811027HTLPPR05','014d829e-88df-472c-9264-d4a360eaaffa',NULL,NULL,2,'MX'),
(4,'administrador','$2a$10$OjxLJ7342aSjKHntY5g3munSLlsp/kPk7L1aQO9I7WXUm3.AL7rHq','Administrador','Planet','Media','1987-11-15','M',1,'2016-07-01 13:38:23','2017-02-16 11:32:16',39,'','administrador',NULL,NULL,NULL,2,'MX'),
(5,'estudiante','$2a$10$7pN87HNqk9kZ4hcmLl6JYenKV/kmo9LMyHh2U1agUm05aCeP7xUNS','Estudiante','Planet','Media','1987-11-15','M',1,'2016-07-01 13:38:23','2017-02-17 16:46:34',39,'','LOLC811027HTLPPR06','3b545f8c-0774-49af-9ca7-400db42dafd6',NULL,NULL,2,'MX'),
(6,'invitado','$2a$10$/s.HimWdiHAYfvyUUR5yluHIUMtWVlrK3WNCRLn9PwsHuekoIZtZO','Invitado','Planet','Media','1987-11-15','M',1,'2016-07-01 13:38:23','2017-02-16 11:33:54',39,'','invitado',NULL,NULL,NULL,2,'MX'),
(11,'profesor','$2a$10$.18wHWZjv4z.Ovdm8cULwuBvTLIG/14di6BmKcVg9hBxoS6LDtQUi','Profesor','Planet','Media','2016-09-29','M',1,'2016-09-06 13:19:21','2017-02-16 11:34:47',39,'','profesor',NULL,NULL,NULL,2,'MX'),
(12,'dydia.perianez@planetmedia.com.mx','$2a$10$Qj5Ad8/GznXJ.B65sEzlfOwSonWp/pv/JekTUL1EB61CsC6pOtv62','Dydia','Periañez','Martínez','2015-02-12','F',0,'2016-09-06 13:22:34','2017-02-16 11:37:42',39,NULL,'qety866y34gf',NULL,NULL,'4YM8KSHI8O',2,'MX'),
(24,'ing.oscarmtzg@gmail.com','$2a$10$EErkkhimM6u5TQiLU08OHuaP.DznHVsmA6wn3zACUuth68K8jR3.C','Developer','BackEnd','Elearning','2016-11-02','M',1,'2016-09-30 13:43:14','2017-02-16 11:38:07',39,'MAGO871102','MAGO871102',NULL,NULL,NULL,2,'MX'),
(34,'usuariorevision','$2a$10$Z/G4zTNHV3jCb2KOE5B2J.SoUyd1/h56rOUB5t.B3gNA/hWJLpy4S','Usuario','Revisión','Test','1986-08-08','M',1,'2016-10-03 14:14:29','2017-02-16 11:42:18',39,'RARG8608084P5','RARG860808HTLMGS05',NULL,NULL,'TB5Q1RTY',2,'MX'),
(37,'Elizabeth','$2a$10$58B3EZVjLb0Su/WR2CIqU.c9cRpqQFcZ5ZJjxZ0qIrYJQdIL7JHq.','Elizabeth','Alegría','Durán','1998-10-15','F',1,'2016-10-03 17:50:31','2017-02-16 11:46:18',39,'TESTCURP','TESTCURP',NULL,NULL,'D5YLMRRA',2,'MX'),
(38,'juan.perez@testmail.com','$2a$10$bbx4IJ2wmSCgYRuKQudJ3eZSikIfNoVOazyDQV/wdyO7a/FfvjeU2','Juanchito','morales','sandoval','1981-10-22','M',0,'2016-10-04 10:15:30','2017-02-16 11:47:05',39,'','juan.perez@',NULL,NULL,'35FPD590',2,'MX'),
(39,'carlosl','$2a$10$bskEAliW84WttIJNanSvxuq6tn3.PIwm1YClJlnmKtRRiAxdrdDrO','Carlos Alberto','López','López','2016-10-04','M',1,'2016-10-04 13:08:27','2017-02-17 17:31:22',39,'','LOLC811027HTLPPR04','27a487b6-4dd5-403b-82cd-c569886b4d28',NULL,NULL,2,'MX'),
(165,'gabriel.molina@planetmedia.com.mx','$2a$10$hEMJyAtt4s73JkbIIPmsx.TQ9urpFS6RxbEFl9n3Temzf/vBSB9me','Gabriel','Molina','','2017-02-24',NULL,0,'2016-10-20 13:12:37','2017-02-16 11:49:33',39,NULL,'gabriel.molina@',NULL,NULL,'UI93FCQ7',2,'MX'),
(169,'oscar.martinez@planetmedia.com.mx','$2a$10$twe4rwWfOQOh1W2qlgrKf.ShvXDP93RbEtQt8Y79SWPhOxt89x.Mu','Maria','Rosales','Perez','2017-02-16',NULL,0,'2016-10-20 16:23:46','2017-02-16 11:50:28',39,NULL,'oscar.martinez@p',NULL,NULL,'028PRTTB',2,'MX'),
(170,'skiping@gmail.com','$2a$10$vwABN2norbqgURjBjnw20O.WXlebYfWzYSymZGE8TZaoewBqdDhXu','Juan','Lopez','','2017-02-08',NULL,1,'2016-10-20 16:29:48','2017-02-16 11:51:23',39,NULL,'skiping@gmail.com',NULL,NULL,NULL,2,'MX'),
(171,'doswaldo74@gmail.com','$2a$10$N5V74BLi7ZjaoxRVAlRG5OwB3JMJXx6fx75yQ3bV/dBr.B61N36YK','Daniel','Sierra','Garcia','2017-02-17',NULL,0,'2016-12-01 20:59:46','2017-02-16 11:53:02',39,NULL,'doswaldo74@',NULL,NULL,'SFDZJHAM',2,'MX'),
(172,'ralvarado@hopewellsystem.com','$2a$10$KZIpKmc.z2QvhfFJIkoN7OXaoGFEpeyTKpaXOU907XwEOqIbMbPfC','ruben','alvarado','molina','2017-02-15',NULL,0,'2016-12-08 16:15:30','2017-02-16 11:54:20',39,NULL,'ralvarado@h',NULL,NULL,NULL,2,'MX'),
(173,'carlosl2','$2a$10$IfB9MRpPUsLBEUxNOL/Kk..OaOWHLoSJV9ThVy4UtirFfhpO8RnWi','calos','garvia','alvarado','2017-01-09','M',0,'2017-01-05 13:14:03','2017-02-16 11:55:03',39,' hs  hs ','htnttn',NULL,NULL,'TP3YFPUF',2,'MX'),
(176,'plopez','$2a$10$fenegSunwnRBqhnOzhxNuuq9kGJDUAbe2226b5YBzz8vgh7z8zbEG','Pedro','Lopez','','2017-01-07','M',0,'2017-01-06 11:11:21','2017-02-16 11:56:19',39,'','plopez',NULL,NULL,'ULAVT6FF',2,'MX'),
(177,'gramirez','$2a$10$YfwVQOCe17NsQImAOxDrCOmdun32JjrR5jeWhZtwZk9XqUnBeZ1ee','Gustavo','Ramirez','','2008-01-09','M',0,'2017-01-06 11:24:34','2017-02-16 11:57:18',39,'','gramirez',NULL,NULL,'O0EZE5UB',2,'MX'),
(178,'rasec.ap@me.com','$2a$10$S2xYaAHn/YbwHpooL8LOH.FykZRyv2ucKotEPndvDjm0d4z33PAgi','Cesar','Arteaga','','2017-02-09',NULL,1,'2017-01-06 12:54:05','2017-02-16 11:59:20',39,NULL,'rasec.ap@me.com',NULL,NULL,NULL,2,'MX'),
(179,'ignacioMiranda','$2a$10$sOl9IvLln0wbksOwu/80VufgZqJg/xt1zeX.dd6H0beL6IdgR.L06','Ignacio','Miranda','Ugalde','1980-11-16','M',0,'2017-01-06 14:20:24','2017-02-16 12:00:34',39,'SDFSDF346456456','DGFSFSE345345',NULL,NULL,'P9C9JN12',2,'MX'),
(180,'administradorSISI','$2a$10$D1qu5BBqAHe52kxrPiBw9u9tdyPwlaau6gd0gnAiJaLumL4O9Msta','administradorSISI','administradorSISI','administradorSISI','2017-01-01','M',0,'2017-01-06 15:07:40','2017-02-16 12:01:59',39,'','administradorSISI',NULL,NULL,'ZA5HIVNH',2,'MX'),
(181,'cordinadorCapacitacionSISI','$2a$10$PTpN1eLwWvp7htkI6NwE0.RLLoi/uJvChLh6LUB8Wd7Y9zzZm59H.','cordinadorCapacitacionSISI','cordinadorCapacitacionSISI','cordinadorCapacitacionSISI','1998-01-01','M',0,'2017-01-06 15:14:41','2017-02-16 12:06:14',39,'cordinador','cordinador',NULL,NULL,'S66SFHQ7',2,'MX'),
(182,'supervisorSISI','$2a$10$9J1Tfk0eYGN34QfAai4MYuh0IK7B342dMaJbtCrgzFuM8QUDilM.K','supervisorSISI','supervisorSISI','supervisorSISI','1963-01-01','M',0,'2017-01-06 15:16:37','2017-02-16 12:07:24',39,'supervisorSISI','supervisorSISI',NULL,NULL,'WJZS1KUG',2,'MX'),
(183,'supervisorProgramasSISI','$2a$10$JN7TpHFtxlx/B5QbEhsQfutxTyoQXbYkOoGVfiBuJcEoRSUaEv3kq','supervisorProgramasSISI','supervisorProgramasSISI','supervisorProgramasSISI','2000-01-01','M',0,'2017-01-06 15:18:08','2017-02-16 12:07:56',39,'supervisor','supervisor',NULL,NULL,'D4XY57J0',2,'MX'),
(184,'DesarrolladorSISI','$2a$10$O9TJJ7aABVqyrDW/zFvquOCQzP4ZJG1tPuHcc/Hr.mMU7qjN2EhWq','DesarrolladorSISI','DesarrolladorSISI','DesarrolladorSISI','1977-01-04','M',0,'2017-01-06 15:19:51','2017-02-16 12:08:32',39,'DesarrolladorSISI','DesarrolladorSISI',NULL,NULL,'E32UCOBL',2,'MX'),
(185,'profesorSISI','$2a$10$o1q06uZHZB4aN8iZztZByuosFHbHWf3adqpNSOGhWxkBf1g0gUXFi','profesorSISI','profesorSISI','profesorSISI','1987-01-05','M',0,'2017-01-06 15:20:58','2017-02-16 12:09:05',39,'profesorSISI','profesorSISI',NULL,NULL,'LDPKE1DU',2,'MX'),
(186,'estudianteSISI','$2a$10$jwuJNPFmKsyzPZYcrv2OQ.XuEdoPrfJnR3vbLTG4zJs5kYVLZpL6q','estudianteSISI','estudianteSISI','estudianteSISI','1974-01-01','M',0,'2017-01-06 15:22:15','2017-02-16 12:09:35',39,'estudianteSISI','estudianteSISI',NULL,NULL,'ZP9S5G3F',2,'MX'),
(187,'calopez','','Carlos Alberto','López','López','1981-10-27','M',0,'2017-02-13 09:21:06','2017-02-16 12:10:12',39,NULL,'lolc811027htlppr03',NULL,NULL,NULL,1,'MX'),
(198,'alumno 20','','magaly','martinez','','1987-01-10','F',1,'2017-02-13 12:23:17','2017-02-16 12:10:33',39,NULL,'PEMD870110MVZRRY00',NULL,NULL,NULL,2,'MX'),
(199,'alumno21','','susana','martinez','','2001-02-15','F',1,'2017-02-13 12:38:15','2017-02-16 12:10:57',39,NULL,'PDMD870110MVZRRY00',NULL,NULL,NULL,1,'MX'),
(205,'alumno22','123','rtrt','wrtwrt','rtrt','1985-02-07','M',1,'2017-02-13 15:21:49','2017-02-16 12:11:21',39,NULL,'werteyutr',NULL,NULL,NULL,1,'MX'),
(206,'calopezl','$2a$10$I.sJkPfIcPgi1Dqb2xrq5ui3psjxkyWYSECsghB0hb5XaxwuWYPku','Carlos Alberto','López','López','1981-10-27','M',1,'2017-02-13 15:36:45','2017-03-03 13:48:33',2,NULL,'LOLC811027HTLPPR02','372dc170-7176-49a6-bc7a-0651619016fc',NULL,NULL,1,'MX'),
(207,'Alumno3','$2a$10$B.mAODn/Z0p.PjBaaZKkvO0nWCX9UQSRTm62dbaBxc1PPp/XQNgGS','Maria','Maria','','2010-09-10','F',1,'2017-02-15 16:57:35','2017-02-16 12:11:54',39,NULL,'eryttyt24',NULL,NULL,NULL,1,'MX'),
(208,'usuarioprueba','$2a$10$pWniS0oi9X2wYH78rOiAnOs5RCyHMeHLgvhsC8DozlQ2V/VfT0xVy','alumno ','prueba','revisión','2012-02-08','F',1,'2017-02-16 09:51:34','2017-02-16 12:12:07',39,NULL,'eyrurigf934056',NULL,NULL,NULL,2,'MX'),
(209,'alumnaprueba','$2a$10$jJfWGF.oCkI/whDtx/M.fOLVQ2xplMOhKpqWp.g3iPaFEA0oZXWRK','alumna','probando','casos','1999-03-19','F',1,'2017-02-16 09:56:33','2017-02-16 12:19:53',39,NULL,'tuwor09723sd',NULL,NULL,NULL,2,'MX'),
(210,'Bianca','$2a$10$YAmIfZutCLhX1JdaaX8Dee9HdpCJkeYOrSUNiwpNrs5hTVUQKsxTS','Bianca','solorio','quiensabe','2017-02-16','F',1,'2017-02-16 12:22:06','2017-02-16 12:41:09',2,NULL,'ertysdje887878berd',NULL,NULL,NULL,2,'MX'),
(211,'daniel01','$2a$10$RooBXaiw6LmnVKbopIRRyeoOsn7HmVxoUxmAQrZAmJ0ta4jwojVCG','raul','zamora','castro','1981-10-27',NULL,1,'2017-02-24 16:23:12',NULL,2,NULL,'ZACR901212HDFDDT04','57b73808-c025-44a9-858d-a19c96d25b12','a67f938d-2027-4df9-a956-93ce008ca437',NULL,2,'MX'),
(212,'rogelio02','$2a$10$QF5T5gvo/NtYEDlmTv08z.gQN88Q7ge78nsAVPqraHnGia0XBwPY2','raul','zamora','castro','1981-10-27',NULL,1,'2017-02-24 16:23:12',NULL,2,NULL,'ZACR901212HDFDDT05','20ac74b2-31ed-4859-9470-54e8fcfd5fff','43d8913d-5daa-4751-a42c-024b2c832eb2',NULL,1,'MX'),
(213,'fernando03','$2a$10$Twxb3HDCxIW0p0icZMJ44.c1vhoTfPtctdW9l3jyy5LRpkjWKVJSC','raul','zamora','castro','1981-10-27',NULL,1,'2017-02-24 16:23:13',NULL,2,NULL,'ZACR901212HDFDDT06','97ef232b-a2bf-4536-8da6-82a7d4c75b54','4590fa10-ff83-42ea-a035-16f0ca11c77d',NULL,2,'MX'),
(214,'revisorencuestas','$2a$10$sHUWGQpOQTIHQCBLx9z9DOIuv9w02wmLOrORhhi7JLMkFtMgh6fge','usuario','usuario','','2017-03-02','M',1,'2017-03-03 13:20:23',NULL,2,NULL,'MADJ911223HBCRRN05','3db4cd40-776a-4a8a-85e4-724caa78f8fb','a0f8bc7b-2d06-4be5-baab-ed6d66b871cd',NULL,1,'MX'),
(215,'Roberto Martinez','$2a$10$U2G.iOK9fBHIR/tW6GG79OQVrPeKajBAawnj/3bhq8YIr7EOuH1fO','Roberto ','Martinez ','Ruiz','1976-03-18','M',1,'2017-03-07 09:34:41','2017-03-07 11:05:03',2,NULL,'MERJ931107HOCNZN01','c9645ab4-3ed3-4784-9080-21332a608852','22b93664-3fbc-47fe-9928-bc1ed424769f',NULL,1,'MX');



--
-- Dumping data for table `rel_persona_correo`
--

INSERT INTO `rel_persona_correo` VALUES (6,11,1,'profesor@planetmedia.com.mx',39,'2016-09-06 13:19:21','2017-02-16 11:34:47',1,NULL),(7,12,2,'dydia.perianez@planetmedia.com.mx',6,'2016-09-06 13:22:34',NULL,1,NULL),(8,2,1,'test@planetmedia.com.mx',39,NULL,'2017-03-07 08:49:38',NULL,NULL),(13,4,1,'administrador@planetmedia.com.mx',39,NULL,'2017-02-16 11:32:16',NULL,NULL),(14,5,1,'estudiante@planetmedia.com.mx',39,NULL,'2017-02-17 16:46:34',NULL,NULL),(15,6,1,'invitado@planetmedia.com.mx',39,NULL,'2017-02-16 11:33:54',NULL,NULL),(16,1,1,'carlos.lopez@planetmedia.com.mx',39,NULL,'2017-02-16 11:27:22',NULL,NULL),(17,1,1,'carlos.lopez@planetmedia.com.mx',2,NULL,'2017-01-06 10:30:01',NULL,NULL),(24,24,2,'ing.oscarmtzg@gmail.com',2,'2016-09-30 13:43:15','2016-10-05 13:11:10',1,NULL),(34,34,2,'1gustavoramirezrugerio@gmail.com',2,'2016-10-03 14:14:29','2017-01-24 11:22:01',1,1),(37,37,1,'gustavoramirezrugerio@gmail.com',39,'2016-10-03 17:50:31','2017-02-16 11:46:18',1,1),(38,38,2,'juan.perez@testmail.com',2,'2016-10-04 10:15:30','2016-10-18 10:09:41',1,NULL),(39,39,2,'carlos.alberto.lopez@outlook.com',2,'2016-10-04 13:08:27','2016-10-04 13:41:16',1,NULL),(110,165,2,'gabriel.molina@planetmedia.com.mx',1,'2016-10-20 13:12:37',NULL,1,NULL),(114,169,2,'oscar.martinez@planetmedia.com.mx',1,'2016-10-20 16:23:46',NULL,1,NULL),(115,170,2,'skiping@gmail.com',1,'2016-10-20 16:29:49',NULL,1,NULL),(116,171,2,'doswaldo74@gmail.com',1,'2016-12-01 20:59:47',NULL,1,NULL),(117,172,2,'ralvarado@hopewellsystem.com',1,'2016-12-08 16:15:31',NULL,1,NULL),(118,173,1,'blavla@fgerg.com',39,'2017-01-05 13:14:03','2017-02-16 11:55:03',1,2),(121,176,1,'plopez@gmail.com',39,'2017-01-06 11:11:21','2017-02-16 11:56:19',1,1),(122,177,2,'gramirez@gmail.com',2,'2017-01-06 11:24:34',NULL,1,1),(123,178,2,'rasec.ap@me.com',1,'2017-01-06 12:54:06',NULL,1,NULL),(124,179,1,'ignacio.miranda@sedesol.gob.mx',39,'2017-01-06 14:20:24','2017-02-16 12:00:34',1,1),(125,180,1,'administradorSISI@sisi.mx',39,'2017-01-06 15:07:40','2017-02-16 12:01:59',1,1),(126,181,1,'cordinadorCapacitacionSISI@sisi.mx',39,'2017-01-06 15:14:41','2017-02-16 12:06:14',1,1),(127,182,1,'supervisorSISI@sisi.mx',39,'2017-01-06 15:16:37','2017-02-16 12:07:24',1,1),(128,183,1,'supervisorProgramasSISI@sisi.mx',39,'2017-01-06 15:18:08','2017-02-16 12:07:56',1,1),(129,184,1,'DesarrolladorSISI@sisi.mx',39,'2017-01-06 15:19:51','2017-02-16 12:08:32',1,1),(130,185,1,'profesorSISI@sisi.mx',39,'2017-01-06 15:20:58','2017-02-16 12:09:05',1,1),(131,186,1,'estudianteSISI@sisi.mx',39,'2017-01-06 15:22:15','2017-02-16 12:09:35',1,1),(132,187,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-13 09:21:06','2017-02-16 12:10:12',1,NULL),(133,205,1,'we',39,'2017-02-13 15:21:49','2017-02-16 12:11:21',1,NULL),(134,206,1,'carlos.lopez@planetmedia.com.mx',2,'2017-02-13 15:36:45','2017-03-03 13:48:33',1,NULL),(135,12,1,'hola no se',39,'2017-02-15 16:52:35','2017-02-16 11:37:42',1,NULL),(136,207,1,'algo.com',39,'2017-02-15 16:57:35','2017-02-16 12:11:54',1,NULL),(137,208,1,'hola@algo.com',39,'2017-02-16 09:51:34','2017-02-16 12:12:07',1,NULL),(138,209,1,'dfgdsg',39,'2017-02-16 09:56:33','2017-02-16 12:19:53',1,NULL),(139,39,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 10:39:45','2017-02-17 17:31:22',1,NULL),(140,24,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:38:07',NULL,1,NULL),(141,34,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:42:18',NULL,1,NULL),(143,38,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:47:05',NULL,1,NULL),(144,165,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:49:33',NULL,1,NULL),(145,169,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:50:28',NULL,1,NULL),(146,170,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:51:23',NULL,1,NULL),(147,171,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:53:02',NULL,1,NULL),(148,172,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:54:20',NULL,1,NULL),(149,177,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:57:18',NULL,1,NULL),(150,178,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 11:59:20',NULL,1,NULL),(151,198,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 12:10:33',NULL,1,NULL),(152,199,1,'carlos.lopez@planetmedia.com.mx',39,'2017-02-16 12:10:57',NULL,1,NULL),(153,210,1,'ignaciomiranda80@hotmail.com',2,'2017-02-16 12:22:06','2017-02-16 12:41:09',1,NULL),(154,211,1,'correo@correo.com',2,'2017-02-24 16:23:12',NULL,1,NULL),(155,212,1,'correo@correo.com',2,'2017-02-24 16:23:12',NULL,1,NULL),(156,213,1,'correo@correo.com',2,'2017-02-24 16:23:13',NULL,1,NULL),(157,214,1,'alberto.marind@gmail.com',2,'2017-03-03 13:20:23',NULL,1,NULL),(158,215,1,'roberto.m@domain.com',2,'2017-03-07 09:34:41',NULL,1,NULL);

--
-- Dumping data for table `rel_persona_responsabilidades`
--

INSERT INTO `rel_persona_responsabilidades` VALUES (2,2,1,'2017-02-15 12:27:20',NULL,2),(3,5,1,'2017-02-15 15:42:37',NULL,2),(4,6,1,'2017-02-15 15:42:37',NULL,2),(5,5,2,'2017-03-02 15:50:10',NULL,2),(6,6,2,'2017-03-02 15:50:10',NULL,2),(7,11,2,'2017-03-02 15:50:10',NULL,2),(8,1,1,'2017-03-02 16:37:20',NULL,2),(12,1,2,'2017-03-03 08:44:15',NULL,2),(14,37,7,'2017-03-08 16:42:58',NULL,4),(15,11,1,'2017-03-08 16:44:46',NULL,4),(16,170,1,'2017-03-08 16:50:28',NULL,4);
INSERT INTO `rel_persona_responsabilidades` (`id`,`id_persona`,`id_tipo_responsabilidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (17,5,2,'2017-03-19 09:54:44',NULL,2);
INSERT INTO `rel_persona_responsabilidades` (`id`,`id_persona`,`id_tipo_responsabilidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (18,12,2,'2017-03-19 09:54:44',NULL,2);
INSERT INTO `rel_persona_responsabilidades` (`id`,`id_persona`,`id_tipo_responsabilidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (19,24,2,'2017-03-19 09:54:44',NULL,2);
INSERT INTO `rel_persona_responsabilidades` (`id`,`id_persona`,`id_tipo_responsabilidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (20,215,2,'2017-03-19 09:54:44',NULL,2);

--
-- Dumping data for table `rel_persona_roles`
--

INSERT INTO `rel_persona_roles` VALUES 
(1,1,1,1,'2014-12-24 11:13:28',NULL,1),
(44,4,1,1,'2016-09-29 17:09:11',NULL,1),
(45,5,2,2,'2016-09-29 17:19:08',NULL,1),
(46,11,4,2,'2016-09-29 17:38:40',NULL,1),
(54,24,2,1,'2016-09-30 13:43:15',NULL,1),
(59,11,1,4,'2016-10-03 13:48:20',NULL,1),
(60,11,2,4,'2016-10-03 13:48:20',NULL,1),
(62,34,2,4,'2016-10-03 14:14:32',NULL,1),
(63,38,2,1,'2016-10-04 10:15:30',NULL,1),
(64,39,2,1,'2016-10-04 13:08:28',NULL,1),
(65,39,1,2,'2016-10-04 13:48:54',NULL,1),
(86,165,2,1,'2016-10-20 13:12:37',NULL,1),
(90,169,2,1,'2016-10-20 16:23:46',NULL,1),
(91,170,2,1,'2016-10-20 16:29:49',NULL,1),
(92,171,2,1,'2016-12-01 20:59:47',NULL,1),
(93,172,2,1,'2016-12-08 16:15:31',NULL,1),
(94,173,2,39,'2017-01-05 13:14:03',NULL,1),
(97,176,2,2,'2017-01-06 11:11:23',NULL,1),
(98,177,2,2,'2017-01-06 11:24:36',NULL,1),
(99,178,2,1,'2017-01-06 12:54:06',NULL,1),
(100,179,2,39,'2017-01-06 14:20:24',NULL,1),
(101,180,2,4,'2017-01-06 15:07:40',NULL,1),
(102,181,2,4,'2017-01-06 15:14:41',NULL,1),
(103,182,2,4,'2017-01-06 15:16:37',NULL,1),
(104,183,2,4,'2017-01-06 15:18:08',NULL,1),
(105,184,2,4,'2017-01-06 15:19:52',NULL,1),
(106,185,2,4,'2017-01-06 15:20:59',NULL,1),
(107,186,2,4,'2017-01-06 15:22:16',NULL,1),
(108,34,1,4,'2017-01-16 18:16:28',NULL,1),
(109,34,4,4,'2017-01-16 18:16:28',NULL,1),
(110,34,5,4,'2017-01-16 18:16:28',NULL,1),
(111,34,6,4,'2017-01-16 18:16:28',NULL,1),
(112,34,7,4,'2017-01-16 18:16:28',NULL,1),
(113,34,8,4,'2017-01-16 18:16:28',NULL,1),
(114,34,9,4,'2017-01-16 18:16:28',NULL,1),
(115,34,10,4,'2017-01-16 18:16:28',NULL,1),
(116,34,11,4,'2017-01-16 18:16:28',NULL,1),
(120,34,12,2,'2017-01-24 11:23:07',NULL,1),
(121,34,13,2,'2017-01-24 11:23:07',NULL,1),
(122,34,14,2,'2017-01-24 11:23:07',NULL,1),
(123,34,15,2,'2017-01-24 11:23:07',NULL,1),
(124,34,16,2,'2017-01-24 11:23:07',NULL,1),
(125,187,2,39,'2017-02-13 09:24:14',NULL,1),
(126,39,6,39,'2017-02-13 10:39:24',NULL,1),
(127,39,9,39,'2017-02-13 11:24:51',NULL,1),
(138,205,2,39,'2017-02-13 15:32:41',NULL,1),
(139,206,2,39,'2017-02-13 15:40:50',NULL,1),
(141,207,2,39,'2017-02-15 17:00:05',NULL,1),
(142,208,2,39,'2017-02-16 09:56:02',NULL,1),
(143,210,2,2,'2017-02-16 12:37:13',NULL,1),
(144,210,6,2,'2017-02-16 12:37:13',NULL,1),
(145,211,2,2,'2017-02-24 16:23:12',NULL,1),
(146,212,2,2,'2017-02-24 16:23:13',NULL,1),
(147,213,2,2,'2017-02-24 16:23:13',NULL,1),
(149,214,17,2,'2017-03-03 13:29:45',NULL,1),
(151,2,1,39,'2017-03-07 08:50:36',NULL,1);

--
-- Dumping data for table `rel_persona_telefono`
--

INSERT INTO `rel_persona_telefono` VALUES (1,187,1,'2411152868','2017-02-13 09:21:06','2017-02-16 12:10:12',39,1,'115'),(2,187,2,'2461426839','2017-02-13 09:21:06','2017-02-16 12:10:12',39,1,NULL),(8,205,1,'we','2017-02-13 15:21:49','2017-02-16 12:11:21',39,1,'we'),(9,205,2,'ew','2017-02-13 15:21:49','2017-02-16 12:11:21',39,1,NULL),(10,206,1,'2411152868','2017-02-13 15:36:45','2017-03-03 13:48:33',2,1,'115'),(11,206,2,'2461426839','2017-02-13 15:36:45','2017-03-03 13:48:33',2,1,NULL),(12,208,1,'12456','2017-02-16 09:51:34','2017-02-16 12:12:07',39,1,'569'),(13,208,2,'2354','2017-02-16 09:51:34','2017-02-16 12:12:07',39,1,NULL),(14,210,1,'66476575','2017-02-16 12:22:06','2017-02-16 12:41:09',2,1,'3546'),(15,210,2,'63647478','2017-02-16 12:22:06','2017-02-16 12:41:09',2,1,NULL),(16,211,1,'5.55555555E8','2017-02-24 16:23:12',NULL,2,1,NULL),(17,211,2,'5.566448877E9','2017-02-24 16:23:12',NULL,2,1,NULL),(18,212,1,'5.55555555E8','2017-02-24 16:23:12',NULL,2,1,NULL),(19,212,2,'5.566448877E9','2017-02-24 16:23:12',NULL,2,1,NULL),(20,213,1,'5.55555555E8','2017-02-24 16:23:13',NULL,2,1,NULL),(21,213,2,'5.566448877E9','2017-02-24 16:23:13',NULL,2,1,NULL),(22,215,1,'5546378','2017-03-07 09:34:41',NULL,2,1,'566');

--
-- Dumping data for table `rel_plan_aptitudes`
--

INSERT INTO `rel_plan_aptitudes` VALUES (47,1,'2017-01-06 12:10:02',2,1),(52,1,'2017-01-06 13:41:44',2,1),(53,1,'2017-01-06 15:45:57',2,1);

--
-- Dumping data for table `rel_plan_conocimientos`
--

INSERT INTO `rel_plan_conocimientos` VALUES (47,1,'2017-01-06 11:13:23',2,1),(47,2,'2017-01-06 11:13:23',2,1);

--
-- Dumping data for table `rel_plan_habilidades`
--

INSERT INTO `rel_plan_habilidades` VALUES (47,2,'2017-01-06 12:10:02',2,1),(52,2,'2017-01-06 13:41:44',2,1),(53,2,'2017-01-06 15:45:57',2,1);


--
-- Dumping data for table `tbl_ficha_descriptiva_programa`
--
INSERT INTO `tbl_ficha_descriptiva_programa` VALUES (40,'IDPROG_NVO_03','MATEMATICAS AVANZADAS','Programa para poder cursar programaojción estructurada',2,NULL,'2016-10-27 13:02:12','2017-01-07 00:00:00','2017-01-12 00:00:00','Curso para personas interesadas en programación',NULL,1,'Certificado de preparatoria e identificacion como IFE,CURP o Acta de Nacimiento',NULL,'MATE-19-10-16',NULL,'Matematicas,Ciencias Naturales, Algebra, ecuaciones diferenciales, programacion',NULL,'Desarrolla tus habilidades mentales','Proveer al alumno un ambiente agradable y conocimiento de alto nivel','Desarrollar el potencial del alumno a modo de dominar los procesos matematicos requeridos para el siguiente nivel','Metodologia ACID',4,2,'windows xp','LIBROS E INTERNET',3,1,'2016-10-27 13:02:12','2016-11-18 16:02:50',1,'2016-10-27 13:02:12','2016-10-27 13:02:12',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(47,NULL,'PROGRAMA 2',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,NULL,3,1,'2016-11-30 12:19:33','2016-11-30 12:20:05',4,'2016-10-27 13:02:12','2016-10-27 13:02:12',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(61,NULL,'PROGRAMA TEST 3',NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,NULL,4,2,'2016-11-30 12:30:53','2016-11-30 12:31:34',4,'2016-10-27 13:02:12','2016-10-27 13:02:12',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(62,NULL,'PROGRAMA TEST 4',NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,NULL,4,2,'2016-11-30 12:30:53',NULL,1,'2016-10-27 13:02:12','2016-10-27 13:02:12',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(63,NULL,'PROGRAMA TEST 5','',2,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,3,2,'2016-11-30 12:30:53',NULL,1,'2016-10-27 13:02:12','2016-10-27 13:02:12',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(64,'MATES PARA DOCENTES','MATES PARA DOCENTES','PROGRAMA PARA DOCENTES',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,2,4,'2017-02-13 17:54:51',NULL,NULL,'2017-02-13 17:54:51','2017-02-13 17:54:51',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(65,'PMI PARA LIDERES','PMI PARA LIDERES','PROGRAMA PARA DOCENTES',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,2,7,'2017-02-13 17:54:51',NULL,NULL,'2017-02-13 17:54:51','2017-02-13 17:54:51',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(66,'ARQ DE APLICACIONES','ARQ DE APLICACIONES','PROGRAMA PARA DOCENTES',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,3,9,'2017-02-13 17:54:51',NULL,NULL,'2017-02-13 17:54:51','2017-02-13 17:54:51',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(67,'LENGUAS DEL PAIS','LENGUAS DEL PAIS','PROGRAMA PARA DOCENTES',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,3,10,'2017-02-13 17:54:51',NULL,NULL,'2017-02-13 17:54:51','2017-02-13 17:54:51',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_ficha_descriptiva_programa` (`id_programa`,`identificador_final`,`nombre_tentativo`,`descripcion`,`id_nivel_programa`,`id_alcance_programa`,`fecha_solicitud`,`fecha_produccion`,`fecha_arranque`,`justificacion_academica`,`id_tipo_evento_programa`,`id_modalidad_programa`,`requisitos_ingreso`,`id_nivel_dominio_programa`,`cve_programa`,`id_programa_antecedente`,`conocimietos_previos`,`id_institucion_certifica`,`presentacion`,`propositos`,`objetivos_generales`,`metodologia`,`id_estatus_programa`,`id_ambiente_aprendizaje`,`requerimiento_equipo`,`instrumento_aprendizaje`,`id_tpo_competencia`,`id_eje_capacitacion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`fecha_vigencia_inicial`,`fecha_vigencia_final`,`id_nivel_conocimiento`,`encuestas_kp`,`id_org_gubernamental`,`id_dirigido_a`,`perfil_ingreso`,`perfil_egreso`,`calificacion_min_aprobatoria`,`id_area_resp_org_gub`) VALUES (81,'Programa_Informatica_1','Programa_Informatica_1','Programa para la enseñanza de la informatica ',2,NULL,'2017-03-22 00:00:01','2017-03-22 00:00:01','2017-03-22 00:00:01','Sin justificación',2,1,NULL,NULL,'INFO_01',NULL,'No son ncesarios conocimientos previos',NULL,'Sin presentacion','Sin proposito','Sin objetivo','ADDIE',1,NULL,NULL,NULL,3,1,'2017-03-23 13:38:01','2017-03-23 13:38:01',2,'2017-03-23 13:38:01','2017-03-23 13:38:01',2,NULL,20,NULL,'Sin perfil','Sin perfil','80',21);
--
-- Dumping data for table `rel_programa_actividades_aprendizaje`
--

INSERT INTO `rel_programa_actividades_aprendizaje` VALUES (40,1,'2016-10-27 13:02:12',NULL,1),(40,3,'2016-10-27 13:02:12',NULL,1);

--
-- Dumping data for table `rel_programa_areas_conocimiento`
--

INSERT INTO `rel_programa_areas_conocimiento` VALUES (40,1,'2016-10-27 13:02:12',NULL,1),(40,2,'2016-10-27 13:02:12',NULL,1);

--
-- Dumping data for table `rel_programa_carga_horaria`
--

INSERT INTO `rel_programa_carga_horaria` VALUES (40,'4','',1,'2016-10-27 13:02:12',NULL,1),(40,'180','',2,'2016-10-27 13:02:12',NULL,1);

--
-- Dumping data for table `rel_programa_creador_programa`
--

INSERT INTO `rel_programa_creador_programa` VALUES (40,'Jose',1,1,'2016-10-27 13:02:12',NULL,1),(40,'Pedro',1,1,'2016-10-27 13:02:12',NULL,1);

--
-- Dumping data for table `rel_programa_tecnicas_didacticas`
--
INSERT INTO `rel_programa_tecnicas_didacticas` VALUES (40,1,'2016-10-27 13:02:12',NULL,1),(40,3,'2016-10-27 13:02:12',NULL,1);

--
-- Dumping data for table `rel_respuesta_usuario`
--
INSERT INTO `rel_respuesta_usuario` VALUES (3,41,4,'2017-01-31 09:48:19'),(4,44,4,'2017-02-07 17:31:18'),(5,47,39,'2017-02-08 16:37:49'),(6,49,39,'2017-02-08 16:53:14'),(7,51,39,'2017-02-08 17:00:35'),(8,52,39,'2017-02-08 17:19:30'),(9,50,39,'2017-02-08 17:24:50'),(10,60,39,'2017-02-09 13:47:44'),(11,63,2,'2017-02-10 11:21:47'),(12,64,2,'2017-02-10 11:32:59'),(13,48,2,'2017-02-22 16:07:05'),(14,48,2,'2017-03-06 11:39:36'),(15,50,2,'2017-03-07 00:33:08');

--
-- Dumping data for table `rel_rol_funcionalidad`
--
INSERT INTO `rel_rol_funcionalidad` VALUES 
(84,1,86,1,'2016-08-10 11:14:23',NULL,NULL),
(85,1,71,1,'2016-08-10 11:14:23',NULL,NULL),
(86,1,92,1,'2016-08-10 11:14:23',NULL,NULL),
(87,1,79,1,'2016-08-10 11:14:23',NULL,NULL),
(88,1,89,1,'2016-08-10 11:14:23',NULL,NULL),
(89,4,79,1,'2016-08-10 11:15:12',NULL,NULL),
(91,5,1,4,'2016-11-29 20:20:51',NULL,NULL),
(92,5,69,4,'2016-11-29 20:20:51',NULL,NULL),
(93,5,86,4,'2016-11-29 20:20:51',NULL,NULL),
(94,5,71,4,'2016-11-29 20:20:51',NULL,NULL),
(95,5,89,4,'2016-11-29 20:20:51',NULL,NULL),
(97,5,92,4,'2016-11-29 20:20:51',NULL,NULL),
(99,5,79,4,'2016-11-29 20:20:51',NULL,NULL),
(108,1,1,2,'2016-12-17 19:22:13',NULL,NULL),
(109,1,69,2,'2016-12-17 19:22:13',NULL,NULL),
(112,2,86,39,'2016-12-20 10:25:58',NULL,NULL),
(116,8,86,4,'2017-01-06 14:32:00',NULL,NULL),
(117,8,71,4,'2017-01-06 14:32:00',NULL,NULL),
(118,8,89,4,'2017-01-06 14:32:00',NULL,NULL),
(119,8,92,4,'2017-01-06 14:32:00',NULL,NULL),
(120,8,79,4,'2017-01-06 14:32:00',NULL,NULL),
(121,9,86,4,'2017-01-06 14:34:16',NULL,NULL),
(122,9,89,4,'2017-01-06 14:34:16',NULL,NULL),
(123,9,92,4,'2017-01-06 14:34:16',NULL,NULL),
(124,9,79,4,'2017-01-06 14:34:16',NULL,NULL),
(125,10,86,4,'2017-01-06 14:35:03',NULL,NULL),
(126,10,89,4,'2017-01-06 14:35:03',NULL,NULL),
(127,10,92,4,'2017-01-06 14:35:03',NULL,NULL),
(128,10,79,4,'2017-01-06 14:35:03',NULL,NULL),
(129,11,86,4,'2017-01-06 14:37:17',NULL,NULL),
(130,11,71,4,'2017-01-06 14:37:17',NULL,NULL),
(131,11,89,4,'2017-01-06 14:37:17',NULL,NULL),
(132,11,92,4,'2017-01-06 14:37:17',NULL,NULL),
(133,11,79,4,'2017-01-06 14:37:17',NULL,NULL),
(134,12,86,4,'2017-01-06 14:38:12',NULL,NULL),
(135,12,89,4,'2017-01-06 14:38:12',NULL,NULL),
(136,12,92,4,'2017-01-06 14:38:12',NULL,NULL),
(137,13,86,4,'2017-01-06 14:39:10',NULL,NULL),
(138,13,89,4,'2017-01-06 14:39:10',NULL,NULL),
(139,13,92,4,'2017-01-06 14:39:10',NULL,NULL),
(140,14,1,39,'2017-01-16 16:17:26',NULL,NULL),
(141,14,69,39,'2017-01-16 16:17:27',NULL,NULL),
(142,14,92,39,'2017-01-16 16:17:27',NULL,NULL),
(143,16,85,4,'2017-01-17 13:58:03',NULL,NULL),
(144,11,1,2,'2017-02-23 10:58:13',NULL,NULL),
(145,11,69,2,'2017-02-23 10:58:13',NULL,NULL),
(149,17,86,2,'2017-03-03 13:39:56',NULL,NULL),
(150,18,86,2,'2017-03-03 13:46:21',NULL,NULL),
(151,19,65,2,'2017-03-07 00:26:12',NULL,NULL),
(152,19,129,2,'2017-03-07 00:26:12',NULL,NULL),
(153,19,130,2,'2017-03-07 00:26:12',NULL,NULL),
(154,19,3,2,'2017-03-07 00:26:12',NULL,NULL),
(155,19,35,2,'2017-03-07 00:26:12',NULL,NULL),
(156,19,131,2,'2017-03-07 00:26:12',NULL,NULL),
(157,19,134,2,'2017-03-07 00:26:12',NULL,NULL),
(158,19,135,2,'2017-03-07 00:26:12',NULL,NULL),
(159,19,44,2,'2017-03-07 00:26:12',NULL,NULL),
(160,19,47,2,'2017-03-07 00:26:12',NULL,NULL),
(161,19,114,2,'2017-03-07 00:26:12',NULL,NULL),
(162,19,52,2,'2017-03-07 00:26:12',NULL,NULL),
(163,19,53,2,'2017-03-07 00:26:12',NULL,NULL),
(164,19,117,2,'2017-03-07 00:26:12',NULL,NULL),
(165,19,58,2,'2017-03-07 00:26:12',NULL,NULL),
(166,19,124,2,'2017-03-07 00:26:12',NULL,NULL),
(167,19,92,2,'2017-03-07 00:26:12',NULL,NULL),
(168,19,61,2,'2017-03-07 00:26:12',NULL,NULL);

--
-- Dumping data for table `rel_usuario_datos_laborales`
--

INSERT INTO `rel_usuario_datos_laborales` VALUES (1,'156','PM','Sedesol','Desarrollo','Desarrollador',1,29,'1001','ESTATAL','Temporal','2016-06-01 00:00:00',187),(12,'','PM','','','',1,30,'30118',NULL,'',NULL,198),(13,'12345678','sedesol','','','',1,30,'30118','ESTATAL','',NULL,199),(19,'12','wew','wew','wew','wew',1,3,'3003','ESTATAL','we','2017-02-01 00:00:00',205),(20,'156','PM','Sedesol','Desarrollo','Desarrollador',1,29,'29031','FEDERAL','Temporal','2017-02-13 00:00:00',206),(21,'','asdf','','','',1,9,'9014','ESTATAL','',NULL,12),(22,'123','No se','','','',1,1,'1004','MUNICIPAL','',NULL,207),(23,'1235','sedesol','dos','área','capacitador',1,2,'2005','FEDERAL','honorarios','2014-02-01 00:00:00',208),(24,'123','sedesol','wetr','sdg','fg',1,8,'8002','ESTATAL','',NULL,209),(25,'','PM','','','',1,1,'1003',NULL,'',NULL,39),(26,'','PM','','','',1,4,'4011',NULL,'',NULL,1),(27,'','PM','','','',1,2,'2003',NULL,'',NULL,2),(28,'','PM','','','',1,2,'2001',NULL,'',NULL,4),(29,'','PM','','','',1,4,'4002',NULL,'',NULL,5),(30,'','PM','','','',1,2,'2001',NULL,'',NULL,6),(31,'','PM','','','',1,3,'3003',NULL,'',NULL,11),(32,'','PM','','','',1,2,'2005',NULL,'',NULL,24),(34,'','PM','','','',1,3,'3003',NULL,'',NULL,34),(35,'','PM','','','',1,2,'2005',NULL,'',NULL,37),(37,'','PM','','','',1,7,'7002',NULL,'',NULL,38),(38,'','PM','','','',1,2,'2002',NULL,'',NULL,165),(39,'','PM','','','',1,4,'4002',NULL,'',NULL,169),(40,'','PM','','','',1,2,'2002',NULL,'',NULL,170),(41,'','PM','','','',1,2,'2002',NULL,'',NULL,171),(42,'','PM','','','',1,2,'2002',NULL,'',NULL,172),(43,'','PM','','','',1,3,'3003',NULL,'',NULL,173),(44,'','PM','','','',1,7,'7005',NULL,'',NULL,176),(45,'','PM','','','',1,2,'2005',NULL,'',NULL,177),(46,'','PM','','','',1,7,'7002',NULL,'',NULL,178),(47,'','PM','','','',1,2,'2005',NULL,'',NULL,179),(48,'','PM','','','',1,2,'2002',NULL,'',NULL,180),(49,'','PM','','','',1,2,'2005',NULL,'',NULL,181),(50,'','PM','','','',1,2,'2002',NULL,'',NULL,182),(51,'','PM','','','',1,4,'4001',NULL,'',NULL,183),(52,'','PM','','','',1,1,'1004',NULL,'',NULL,184),(53,'','PM','','','',1,4,'4003',NULL,'',NULL,185),(54,'','PM','','','',1,3,'3008',NULL,'',NULL,186),(55,'12342','SEDESOL','612','dirección general','',1,29,'29004','FEDERAL','honorarios','2017-02-16 00:00:00',210),(56,NULL,'sedesol',NULL,NULL,'promotor',1,29,'29005','ESTATAL',NULL,NULL,211),(57,NULL,'sedesol',NULL,NULL,'promotor',1,1,'29033','ESTATAL',NULL,NULL,212),(58,NULL,'sedesol',NULL,NULL,'promotor',1,30,'27009','ESTATAL',NULL,NULL,213),(59,'','planet','','','',1,9,'9010',NULL,'',NULL,214),(60,'5357','Institución Privada','PYMES','Finanzas','Gerente',1,9,'9003','FEDERAL','Sindicato','2017-03-10 00:00:00',215);

--
-- Dumping data for table `rel_variables_mensaje_operacion`
--

INSERT INTO `rel_variables_mensaje_operacion` VALUES (1,'Nombre del rol','{rol}',33,'2016-08-17 10:32:32','2016-08-24 10:54:57',1),(2,'Prueba','{prueba}',33,'2016-08-17 11:29:44',NULL,1),(3,'Elemento','{elemento}',33,'2016-08-18 11:59:22','2016-08-22 16:56:49',1),(4,'Variable','{variable}',33,'2016-08-18 11:59:43',NULL,1),(5,'Nueva','%nueva%',33,'2016-08-18 13:10:17',NULL,1),(6,'Enviar una alerta a los usuarios','mensaje_catalogos',2,'2016-11-29 20:34:18',NULL,4),(7,'Despedida de curso ','{curso_despedida}',2,'2016-11-29 20:38:58','2017-01-05 15:40:19',5),(8,'Es para realziar ajustes al tema ','%EditarPlantilla%',116,'2016-12-15 13:15:07',NULL,39),(9,'Nombre del rol','${rol}',62,'2017-02-13 09:32:19',NULL,39),(10,'Nombre del usuario','${nombre_usuario}',62,'2017-02-13 09:33:08',NULL,39),(11,'	Nombre del rol','${rol}',133,'2017-02-13 09:33:46',NULL,39),(12,'Nombre del usuario','${nombre_usuario}',133,'2017-02-13 09:34:05',NULL,39);

-- Dumping data for table `tbl_configuracion_area`
--
INSERT INTO `tbl_configuracion_area` VALUES (1,1,NULL,'exampleRes@mail.com','example@mail.com',1,'Pedro Paramo Gonzales','solicitante@example.com',1,'2017-03-07 11:09:58','ruta/1/','ruta/2/','ruta/3/');

--
-- Dumping data for table `tbl_domicilios_persona`
--
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (1,'Felipe Muñoz, coyoacan','14','100010409',2,'2016-07-07 12:25:30','2017-03-07 08:49:38',39,NULL,'');
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (2,'Felipe Muñoz, coyoacan','14','100010409',2,'2016-07-19 12:21:08',NULL,1,NULL,NULL);
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (3,'Felipe Muoz, coyoacan','14','100010409',2,'2016-08-09 12:51:10',NULL,1,NULL,NULL);
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (4,'Felipe Muoz, coyoacan','14','100010409',2,'2016-08-09 15:30:50',NULL,1,NULL,NULL);
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (5,'Felipe Muñoz, coyoacan','14','100010409',2,'2016-08-10 13:05:32',NULL,1,NULL,NULL);
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (6,'Felipe Muñoz, coyoacan','14','100010409',2,'2016-08-10 13:06:01',NULL,1,NULL,NULL);
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (7,'Felipe Muñoz, coyoacan','14','100010409',2,'2016-08-10 13:09:37',NULL,1,NULL,NULL);
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (8,'Felipe Muñoz, coyoacan','14','100010409',2,'2016-08-10 18:03:10',NULL,1,NULL,NULL);
INSERT INTO `tbl_domicilios_persona` (`id_domicilio_persona`,`calle`,`numero_exterior`,`id_asentamiento`,`id_persona`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`activo`,`numero_interior`) VALUES (9,'Felipe Muñoz, coyoacan','14','100010409',2,'2016-08-29 16:09:41',NULL,1,NULL,NULL);

--
-- Dumping data for table `tbl_estructura_personal_externo`
--
INSERT INTO `tbl_estructura_personal_externo` VALUES (1,'Encuestadores',NULL,NULL,1,'2017-02-01 16:25:39',1,NULL,1,NULL),(2,'Promotores',NULL,NULL,1,'2017-02-01 16:25:40',1,NULL,1,NULL),(3,'Supervisores',NULL,NULL,1,'2017-02-01 16:25:40',1,NULL,1,NULL),(4,'Programas sociales',NULL,NULL,1,'2017-02-01 16:25:40',1,NULL,1,NULL),(5,'Otras secretarías',NULL,NULL,1,'2017-02-01 16:25:40',1,NULL,1,NULL);


--
-- Dumping data for table `tbl_eventos`
--

INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,64,1,'EVENTO INTRODUCTORIO',1,'2017-02-20 18:02:15','2017-02-24 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (2,64,2,'EVENTO DESARROLLO',1,'2017-02-01 18:02:15','2017-02-10 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (3,64,3,'EVENTO DOCENTES',1,'2017-03-01 18:02:15','2017-03-05 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (4,64,4,'EVENTO ALUMNOS',1,'2017-03-06 18:02:15','2017-03-08 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (5,64,5,'EVENTO EXALUMNOS',1,'2017-03-15 18:02:15','2017-03-17 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (6,65,1,'EVENTO VOL 1',1,'2017-02-13 18:02:15','2017-02-13 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (7,65,2,'EVENTO VOL 2',1,'2017-02-13 18:02:15','2017-02-13 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (8,65,3,'EVENTO VOL 3',1,'2017-02-13 18:02:15','2017-02-13 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (9,66,4,'EVENTO VOL 4',1,'2017-02-13 18:02:15','2017-02-13 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (10,67,5,'EVENTO VOL 5',1,'2017-02-13 18:02:15','2017-02-13 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_eventos` (`id`,`id_programa`,`id_estatus_ec`,`nombre_ec`,`modalidad`,`fecha_inicial`,`fecha_final`,`nivel_ensenanza`,`alcance_ec`,`no_registro_ec`,`objetivo_general_ec`,`perfil_ec`,`requisitos_ec`,`constancia`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (11,67,5,'EVENTO VOL 5',1,'2017-02-13 18:02:15','2017-02-13 18:02:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

--
-- Dumping data for table `rel_reponsable_produccion_ec`
--

INSERT INTO `rel_reponsable_produccion_ec` (`id_evento_capacitacion`,`id_reponsable_produccion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (11,19,'2017-03-24 16:28:13','2017-03-24 16:28:13',2);



/*tbl_ambiente_virtual_aprendizaje*/
INSERT INTO `tbl_ambiente_virtual_aprendizaje` (`id`,`id_cat_estado_ava`,`id_resp_construccion`,`id_evento_capacitacion`,`porcentaje_avance_ava`,`validacion_ava`,`activo`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,1,2,1,60,0,0,'2017-03-23 00:00:00','2017-03-23 00:00:00',1);
INSERT INTO `tbl_ambiente_virtual_aprendizaje` (`id`,`id_cat_estado_ava`,`id_resp_construccion`,`id_evento_capacitacion`,`porcentaje_avance_ava`,`validacion_ava`,`activo`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (2,2,2,2,60,0,0,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);
INSERT INTO `tbl_ambiente_virtual_aprendizaje` (`id`,`id_cat_estado_ava`,`id_resp_construccion`,`id_evento_capacitacion`,`porcentaje_avance_ava`,`validacion_ava`,`activo`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (3,2,2,3,60,0,0,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);
INSERT INTO `tbl_ambiente_virtual_aprendizaje` (`id`,`id_cat_estado_ava`,`id_resp_construccion`,`id_evento_capacitacion`,`porcentaje_avance_ava`,`validacion_ava`,`activo`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (4,2,2,4,60,0,0,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);
INSERT INTO `tbl_ambiente_virtual_aprendizaje` (`id`,`id_cat_estado_ava`,`id_resp_construccion`,`id_evento_capacitacion`,`porcentaje_avance_ava`,`validacion_ava`,`activo`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (5,2,2,5,60,0,0,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);

--
-- Dumping data for table `tbl_lotes_carga_usuarios`
--

INSERT INTO `tbl_lotes_carga_usuarios` VALUES (1,'nachoprueba','0d539357-5148-4238-a73d-c178125411bf','2017-02-23 10:13:34',39),(2,'nachoprueba2','485a20b5-1969-4a59-abd8-c7ed897d6abe','2017-02-23 10:23:49',39),(3,'capacitación de CUIS','e5e6cb82-386a-4a4f-ba89-c457880bb401','2017-02-24 16:13:27',2);
--
-- Dumping data for table `tbl_plantillas`
--

INSERT INTO `tbl_plantillas` VALUES (1,'plantilla_test1','elearning_logo_d.png','footer.jpg','C:\\Users\\PlanetMedia\\plantillasSISI\\headers\\elearning_logo_d.png','C:\\Users\\PlanetMedia\\plantillasSISI\\footers\\footer.jpg',2,NULL,'2016-08-02 17:11:54',NULL),(2,'plantilla_test2','header.jpg','elearning_logo.jpg','C:\\Users\\PlanetMedia\\plantillasSISI\\headers\\header.jpg','C:\\Users\\PlanetMedia\\plantillasSISI\\footers\\elearning_logo.jpg',2,NULL,'2016-08-02 17:15:45',NULL),(3,'plantilla_sisi','header_sisi.png','footer_1.png','C:\\Users\\PlanetMedia\\plantillasSISI\\headers\\header_sisi.png','C:\\Users\\PlanetMedia\\plantillasSISI\\footers\\footer_1.png',2,NULL,'2016-08-03 17:57:05',NULL),(7,'test_nombres','11082016181326_elearning_logo.jpg','11082016182324_footer_2.png','C:\\Users\\PlanetMedia\\plantillasSISI\\headers\\11082016181326_elearning_logo.jpg','C:\\Users\\PlanetMedia\\plantillasSISI\\footers\\11082016182324_footer_2.png',2,NULL,'2016-08-11 18:25:24',NULL),(8,'test_unitario','nombre_img_header','nombre_img_footer','C:\\test\\directory','C:\\test\\directory',1,NULL,'2016-09-27 16:16:04',NULL);

--
-- Dumping data for table `tbl_preguntas_encuesta`
--

INSERT INTO `tbl_preguntas_encuesta` VALUES (54,41,'2017-02-09 18:30:39',NULL,4,NULL,'GA-280120170947-1',NULL),(55,41,'2017-02-09 18:30:39',NULL,4,NULL,'GA-280120170947-2',NULL),(56,41,'2017-02-09 18:30:39',NULL,4,NULL,'GA-280120170947-3',NULL),(57,41,'2017-02-09 18:30:39',NULL,4,NULL,'GA-280120170947-4',NULL),(58,NULL,'2017-02-09 18:30:39',NULL,4,NULL,'GA-280120170947-5',NULL),(59,NULL,'2017-01-31 09:49:02',NULL,4,NULL,'GA-280120170949-1',NULL),(60,NULL,'2017-01-31 09:49:07',NULL,4,NULL,'GA-280120170949-2',NULL),(61,NULL,'2017-01-31 09:49:11',NULL,4,NULL,'GA-280120170949-3',NULL),(62,NULL,'2017-01-31 09:49:15',NULL,4,NULL,'GA-280120170949-4',NULL),(63,NULL,'2017-01-31 09:49:19',NULL,4,NULL,'GA-280120170949-5',NULL),(64,NULL,'2017-02-01 17:23:16',NULL,39,NULL,'Pregunta1',NULL),(65,NULL,'2017-02-01 17:23:20',NULL,39,NULL,'Pregunta2',NULL),(66,NULL,'2017-02-01 17:23:30',NULL,39,NULL,'Pregunta3',NULL),(67,NULL,'2017-02-01 17:23:35',NULL,39,NULL,'Pregunta4',NULL),(68,NULL,'2017-02-01 17:23:42',NULL,39,NULL,'Pregunta5',NULL),(69,NULL,'2017-02-07 15:45:41',NULL,39,NULL,'Pregunta No.1',NULL),(70,NULL,'2017-02-07 15:45:47',NULL,39,NULL,'Pregunta No.2',NULL),(71,NULL,'2017-02-07 15:45:51',NULL,39,NULL,'Pregunta No.3',NULL),(72,NULL,'2017-02-07 15:45:55',NULL,39,NULL,'Pregunta No.4',NULL),(73,NULL,'2017-02-07 15:46:02',NULL,39,NULL,'Pregunta No.5',NULL),(74,NULL,'2017-02-08 16:12:37',NULL,4,NULL,'nueva pregunta',NULL),(77,NULL,'2017-02-08 16:37:07',NULL,39,NULL,'Pregunta 1',NULL),(78,NULL,'2017-02-08 16:37:07',NULL,39,NULL,'Pregunta 2',NULL),(79,NULL,'2017-02-08 16:37:07',NULL,39,NULL,'Pregunta 3',NULL),(80,NULL,'2017-02-08 16:37:07',NULL,39,NULL,'Pregunta 4',NULL),(81,NULL,'2017-02-08 16:37:07',NULL,39,NULL,'Pregunta 5',NULL),(82,NULL,'2017-02-08 16:40:04',NULL,39,NULL,'Pregunta No.1',NULL),(83,NULL,'2017-02-08 16:40:04',NULL,39,NULL,'Pregunta No.2',NULL),(84,NULL,'2017-02-08 16:40:04',NULL,39,NULL,'Pregunta No.3',NULL),(85,NULL,'2017-02-08 16:40:04',NULL,39,NULL,'Pregunta No.4',NULL),(86,NULL,'2017-02-08 16:40:04',NULL,39,NULL,'Pregunta No.5',NULL),(87,NULL,'2017-02-08 17:18:33',NULL,39,NULL,'¿Cómo ha sido tu experiencia en el curso eLearning ?',NULL),(88,NULL,'2017-02-08 17:18:33',NULL,39,NULL,'¿Recomendarías el curso?',NULL),(89,NULL,'2017-02-08 17:18:33',NULL,39,NULL,'¿Te quedaron claras todos los conceptos?',NULL),(90,NULL,'2017-02-08 17:18:33',NULL,39,NULL,'¿Cómo calificas al facilitador?',NULL),(91,NULL,'2017-02-08 17:18:33',NULL,39,NULL,'¿Te gustaría tomar otro curso?',NULL),(92,NULL,'2017-02-08 17:16:52',NULL,39,NULL,'¿ Entendiste a todo el curso?',NULL),(93,NULL,'2017-02-08 17:16:52',NULL,39,NULL,'¿Te quedo alguna duda?',NULL),(94,NULL,'2017-02-08 17:16:52',NULL,39,NULL,'¿Recomendarías el curso?',NULL),(95,NULL,'2017-02-08 17:16:52',NULL,39,NULL,'¿Te pareció aburrido?',NULL),(96,NULL,'2017-02-08 17:16:52',NULL,39,NULL,'¿Tomarías otro curso?',NULL),(97,NULL,'2017-02-09 12:49:19',NULL,4,NULL,'Pregunta 1',NULL),(98,NULL,'2017-02-09 12:49:19',NULL,4,NULL,'Pregunta 2',NULL),(99,NULL,'2017-02-09 12:49:19',NULL,4,NULL,'Pregunta 3',NULL),(100,NULL,'2017-02-09 12:49:19',NULL,4,NULL,'Pregunta 4',NULL),(101,NULL,'2017-02-09 12:49:19',NULL,4,NULL,'Pregunta 5',NULL),(102,NULL,'2017-02-09 13:44:11',NULL,39,NULL,'Pregunta No. 1',NULL),(103,NULL,'2017-02-09 13:44:11',NULL,39,NULL,'Pregunta No. 2',NULL),(104,NULL,'2017-02-09 13:44:11',NULL,39,NULL,'Pregunta No. 3',NULL),(105,NULL,'2017-02-09 13:44:11',NULL,39,NULL,'Pregunta No. 4',NULL),(106,NULL,'2017-02-09 13:44:11',NULL,39,NULL,'Pregunta No. 5',NULL),(107,NULL,'2017-02-08 17:24:00',NULL,39,NULL,'Preg. 1',NULL),(108,NULL,'2017-02-08 17:24:00',NULL,39,NULL,'Preg. 2',NULL),(109,NULL,'2017-02-08 17:24:00',NULL,39,NULL,'Preg. 3',NULL),(110,NULL,'2017-02-08 17:24:00',NULL,39,NULL,'Preg. 4',NULL),(111,NULL,'2017-02-08 17:24:00',NULL,39,NULL,'Preg. 5',NULL),(112,NULL,'2017-02-09 12:41:48',NULL,39,NULL,'¿Mi experiencia durante el curso fue?',NULL),(113,NULL,'2017-02-09 12:41:48',NULL,39,NULL,'¿Cómo me pareció la interfaz gráfica del curso?',NULL),(114,NULL,'2017-02-09 12:41:48',NULL,39,NULL,'¿Los contenidos del curso, fueron versátiles?',NULL),(115,NULL,'2017-02-09 12:41:48',NULL,39,NULL,'¿El curso me mostró el progreso de los módulos revisados y pendientes?',NULL),(116,NULL,'2017-02-09 12:41:48',NULL,39,NULL,'Al inscribirme al curso, ¿Recibí información adecuada para su aprobación?',NULL),(117,NULL,'2017-02-09 12:39:13',NULL,39,NULL,'1',NULL),(118,NULL,'2017-02-09 12:39:13',NULL,39,NULL,'2',NULL),(119,NULL,'2017-02-09 12:39:13',NULL,39,NULL,'3',NULL),(120,NULL,'2017-02-09 12:39:13',NULL,39,NULL,'4',NULL),(121,NULL,'2017-02-09 12:39:13',NULL,39,NULL,'5',NULL),(122,NULL,'2017-02-09 12:43:28',NULL,39,NULL,'1',NULL),(123,NULL,'2017-02-09 12:43:28',NULL,39,NULL,'2',NULL),(124,NULL,'2017-02-09 12:43:28',NULL,39,NULL,'3',NULL),(125,NULL,'2017-02-09 12:43:28',NULL,39,NULL,'4',NULL),(126,NULL,'2017-02-09 12:43:28',NULL,39,NULL,'5',NULL),(127,48,'2017-02-09 16:35:14',NULL,4,NULL,'uno',NULL),(128,48,'2017-02-09 16:35:14',NULL,4,NULL,'dos',NULL),(129,48,'2017-02-09 16:35:14',NULL,4,NULL,'tres',NULL),(130,48,'2017-02-09 16:35:14',NULL,4,NULL,'cuatro',NULL),(131,48,'2017-02-09 16:35:14',NULL,4,NULL,'cinco',NULL),(132,NULL,'2017-02-09 18:10:08',NULL,39,NULL,'¿Qué te pareció el curso?',NULL),(133,NULL,'2017-02-09 18:10:08',NULL,39,NULL,'¿Consideras entendible el curso?',NULL),(134,NULL,'2017-02-09 18:10:08',NULL,39,NULL,'¿Te quedo alguna duda?',NULL),(135,NULL,'2017-02-09 18:10:08',NULL,39,NULL,'¿Recomendarías el curso?',NULL),(136,NULL,'2017-02-09 18:10:08',NULL,39,NULL,'¿Cómo consideras la interfaz?',NULL),(137,NULL,'2017-02-09 16:52:17',NULL,39,NULL,'1',NULL),(138,NULL,'2017-02-09 16:52:17',NULL,39,NULL,'2',NULL),(139,NULL,'2017-02-09 16:52:17',NULL,39,NULL,'3',NULL),(140,NULL,'2017-02-09 16:52:17',NULL,39,NULL,'4',NULL),(141,NULL,'2017-02-09 16:52:17',NULL,39,NULL,'5',NULL),(142,NULL,'2017-02-15 12:23:23',NULL,39,NULL,'¿Pregunta 1 ?',NULL),(143,NULL,'2017-02-15 12:23:23',NULL,39,NULL,'¿Pregunta 2?',NULL),(144,NULL,'2017-02-15 12:23:23',NULL,39,NULL,'¿Pregunta 3 ?',NULL),(145,NULL,'2017-02-15 12:23:23',NULL,39,NULL,'¿Pregunta 4 ?',NULL),(146,NULL,'2017-02-15 12:23:23',NULL,39,NULL,'¿Pregunta 5 ?',NULL),(147,NULL,'2017-02-09 17:44:57',NULL,39,NULL,'¿Pregunta uno?',NULL),(148,NULL,'2017-02-09 17:44:57',NULL,39,NULL,'¿Pregunta dos?',NULL),(149,NULL,'2017-02-09 17:44:57',NULL,39,NULL,'¿Pregunta tres?',NULL),(150,NULL,'2017-02-09 17:44:57',NULL,39,NULL,'¿Pregunta cuatro?',NULL),(151,NULL,'2017-02-09 17:44:57',NULL,39,NULL,'¿Pregunta cinco?',NULL),(152,NULL,'2017-02-09 17:52:11',NULL,39,NULL,'¿Cómo funciona?',NULL),(153,NULL,'2017-02-09 17:52:11',NULL,39,NULL,'¿Qué debo hacer?',NULL),(154,NULL,'2017-02-09 17:52:11',NULL,39,NULL,'¿Porqué funciona así?',NULL),(155,NULL,'2017-02-09 17:52:11',NULL,39,NULL,'¿Quiénes lo utilizan?',NULL),(156,NULL,'2017-02-09 17:52:11',NULL,39,NULL,'¿Dónde se implementa?',NULL),(159,NULL,'2017-02-10 11:12:11',NULL,2,NULL,'¿La organización del curso ha sido?...',NULL),(160,NULL,'2017-02-10 11:12:11',NULL,2,NULL,'¿El nivel de los contenidos ha sido?',NULL),(161,NULL,'2017-02-10 11:12:11',NULL,2,NULL,'¿La utilidad de los contenidos aprendidos?',NULL),(162,NULL,'2017-02-10 11:12:11',NULL,2,NULL,'¿La utilización de casos prácticos?',NULL),(163,NULL,'2017-02-10 11:12:11',NULL,2,NULL,'¿La utilización de medios audiovisuales?',NULL),(164,NULL,'2017-02-10 11:12:11',NULL,2,NULL,'¿La utilización de dinámicas de grupo?',NULL),(167,NULL,'2017-02-10 11:30:28',NULL,2,NULL,'¿La organización del curso ha sido?...',NULL),(168,NULL,'2017-02-10 11:30:28',NULL,2,NULL,'¿El nivel de los contenidos ha sido?',NULL),(169,NULL,'2017-02-10 11:30:28',NULL,2,NULL,'¿La utilidad de los contenidos aprendidos?',NULL),(170,NULL,'2017-02-10 11:30:28',NULL,2,NULL,'¿La utilización de medios audiovisuales?',NULL),(171,NULL,'2017-02-10 11:30:28',NULL,2,NULL,'¿La utilización de dinámicas de grupo?',NULL),(172,NULL,'2017-02-10 11:30:28',NULL,2,NULL,'¿La duración de la sesión  ha sido?',NULL),(177,NULL,'2017-02-15 12:25:17',NULL,39,NULL,'pregunta 1',NULL),(178,NULL,'2017-02-15 12:25:17',NULL,39,NULL,'pregunta 2',NULL),(179,NULL,'2017-02-15 12:25:17',NULL,39,NULL,'pregunta 3',NULL),(180,NULL,'2017-02-15 12:25:17',NULL,39,NULL,'pregunta 4',NULL),(181,NULL,'2017-02-15 12:25:17',NULL,39,NULL,'pregunta 5',NULL),(182,49,'2017-03-01 13:12:21',NULL,2,NULL,'¿Ser o no ser?',NULL),(183,49,'2017-03-01 13:12:21',NULL,2,NULL,'¿Qué de qué?',NULL),(184,49,'2017-03-01 13:12:21',NULL,2,NULL,'¿Si o sí?',NULL),(185,49,'2017-03-01 13:12:21',NULL,2,NULL,'¿La cuarta?',NULL),(186,49,'2017-03-01 13:12:21',NULL,2,NULL,'La quinta...',NULL),(187,NULL,'2017-03-07 00:32:05',NULL,2,NULL,'¿Ser o no ser?',NULL),(188,NULL,'2017-03-07 00:32:05',NULL,2,NULL,'¿Qué de qué?',NULL),(189,NULL,'2017-03-07 00:32:05',NULL,2,NULL,'¿Si o sí?',NULL),(190,NULL,'2017-03-07 00:32:05',NULL,2,NULL,'¿La cuarta?',NULL),(191,NULL,'2017-03-07 00:32:05',NULL,2,NULL,'La quinta...',NULL),(192,NULL,'2017-03-07 00:32:05',NULL,2,NULL,'nUEVO',NULL),(193,NULL,'2017-03-07 00:32:05',NULL,2,NULL,'Revisón',NULL);

--
-- Dumping data for table `tbl_respuestas`
--

INSERT INTO `tbl_respuestas` VALUES (1,4,69,0),(2,4,70,8),(3,4,71,10),(4,4,72,8),(5,4,73,10),(6,5,77,0),(7,5,78,6),(8,5,79,8),(9,5,80,6),(10,5,81,8),(11,6,92,0),(12,6,93,8),(13,6,94,6),(14,6,95,10),(15,6,96,8),(16,7,92,0),(17,7,93,8),(18,7,94,6),(19,7,95,8),(20,7,96,4),(21,8,87,0),(22,8,88,10),(23,8,89,10),(24,8,90,6),(25,8,91,8),(26,9,107,0),(27,9,108,6),(28,9,109,8),(29,9,110,10),(30,9,111,6),(31,10,102,0),(32,10,103,8),(33,10,104,8),(34,10,105,8),(35,10,106,8),(36,11,159,0),(37,11,160,2),(38,11,161,10),(39,11,162,4),(40,11,163,6),(41,11,164,8),(42,11,159,0),(43,11,160,2),(44,11,161,10),(45,11,162,4),(46,11,163,6),(47,11,164,8),(48,11,159,0),(49,11,160,2),(50,11,161,10),(51,11,162,4),(52,11,163,6),(53,11,164,8),(54,11,164,10),(55,12,167,0),(56,12,168,10),(57,12,169,10),(58,12,170,10),(59,12,171,10),(60,12,172,10),(61,13,127,0),(62,13,128,10),(63,13,129,10),(64,13,130,10),(65,13,131,10),(66,14,127,0),(67,14,128,6),(68,14,129,6),(69,14,130,6),(70,14,131,6),(71,15,187,0),(72,15,188,6),(73,15,189,4),(74,15,190,6),(75,15,191,8),(76,15,192,10),(77,15,193,6);
--
-- Dumping data for table `tbl_temas`
--

INSERT INTO `tbl_temas` VALUES (1,'Base','base',1,1,'2016-10-11 17:52:47','2016-12-18 20:18:50',39),(2,'base','base',2,1,'2017-01-19 13:13:03','2017-02-13 12:05:11',39),(3,'Nuevo','197ac470-fb21-42f6-8085-c6ed48f2240c',1,0,'2016-10-12 15:37:14','2016-11-14 17:16:20',4),(7,'Tema noviembre 2017','1e6be371-5eef-4413-8ed9-d71213d57621',2,0,'2017-01-19 13:06:42','2017-02-17 11:12:09',2),(8,'rojo','d07cc647-6824-4114-a81d-b7e5c42222d9',2,0,'2017-01-19 13:07:56','2017-02-13 12:05:11',39),(12,'cafe','bb83fcf0-574b-4125-95ba-6f2a129dcc18',2,0,'2017-01-19 15:54:10','2017-02-13 12:05:11',39),(13,'BaseSuper','8e5b75c2-bbe8-4875-983a-077b35c8683c',2,0,'2017-02-01 17:40:12','2017-02-13 12:05:11',39),(14,'FER','2446b11e-9f07-4425-ab22-93a7dde4f911',1,0,'2017-02-24 16:50:58',NULL,2),(15,'fer2','83bba2c5-b2ed-41bb-8748-2b54455953d9',2,0,'2017-02-24 17:29:11','2017-02-24 17:29:24',2),(16,'Prueba test 0001','7087a4ac-3c8f-4f13-9229-669de9b90739',2,0,'2017-02-27 11:17:46',NULL,2),(17,'lista2','e99267b4-278d-4f2e-b863-1f69c6e39a7e',2,0,'2017-03-02 15:38:42',NULL,2),(18,'PRUEBA 1','f9331120-6106-4e3d-8e2a-636d1f067924',1,0,'2017-03-02 16:29:31',NULL,2);

--
-- Dumping data for table `tbl_textos_sistema`
--

INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('ge.textos.publico.eventos','Eventos',49,'2016-09-12 11:45:52',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.boton.agregar','Crear',124,'2016-12-29 10:59:23','2017-02-14 16:56:07',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.boton.finalizar','Finalizar',124,'2016-12-29 10:54:43',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.columna.acciones','Acciones',124,'2016-12-29 10:54:20',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.columna.actividad','Tarea',124,'2016-12-29 10:52:59','2017-02-14 17:22:46',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.columna.estatus','Estatus',124,'2016-12-29 10:53:50',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.columna.fecha.fin','Fecha de fin',124,'2016-12-29 10:53:27','2016-12-29 10:56:20',39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.columna.fecha.inicio','Fecha de inicio',124,'2016-12-29 10:53:14',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.columna.tiempo','Tiempo',124,'2016-12-29 17:56:21',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.etiqueta.nuevaTarea','Nueva tarea',124,'2017-02-14 18:08:12',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.boton.cancelar','Cancelar',124,'2016-12-29 11:00:07',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.boton.guardar','Guardar',124,'2016-12-29 11:00:36',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.confirmacion.boton.no','No',124,'2016-12-29 17:13:32',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.confirmacion.boton.si','Si',124,'2016-12-29 17:13:51',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.confirmacion.mensaje','¿Está seguro(a) que desea finalizar la actividad?',124,'2016-12-29 17:12:38',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.confirmacion.titulo','Confirmación',124,'2016-12-29 17:12:11',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.etiqueta.actividad','Tarea',124,'2016-12-29 10:55:30','2017-02-15 13:30:07',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.etiqueta.fecha.fin','Fecha de fin',124,'2016-12-29 11:06:36',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.etiqueta.fecha.inicio','Fecha de inicio',124,'2016-12-29 10:56:06',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.modal.titulo','Agregar tarea',124,'2016-12-29 10:55:05','2017-02-15 13:29:52',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.actividad.req','¡La actividad es requerida!',124,'2016-12-29 17:20:53',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.editar.error','¡Ocurrió un error al finalizar la actividad!',124,'2016-12-29 17:25:02',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.editar.exito','¡La actividad se finalizó con éxito!',124,'2016-12-29 17:24:08',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.estatus.req','¡El estatus es requerido!',124,'2016-12-29 17:21:14',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.fecha.fin.req','¡La fecha de fin es requerida!',124,'2016-12-29 17:21:54',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.fecha.inicio.req','¡La fecha de inicio es requerida!',124,'2016-12-29 17:21:31',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.fecha.registro.req','¡La fecha de registro es requerida!',124,'2016-12-29 17:22:18',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.fecha.termino.req','¡La fecha de termino es requerida!',124,'2016-12-29 17:22:38',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.guardar.error','¡Ocurrió un error al guardar la actividad!',124,'2016-12-29 17:24:33',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.guardar.exito','¡La actividad se guardó con éxito!',124,'2016-12-29 17:23:39',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.id.req','¡El ID es requerido!',124,'2016-12-29 17:20:27',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.msj.sis.id.usuario.req','¡El ID del usuario es requerido!',124,'2016-12-29 17:22:59',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.actividades.titulo','Lista de tareas',124,'2016-12-29 10:47:48','2017-02-14 17:24:06',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.admin.personas.curp.existe','¡El CURP ya éxiste!',44,'2017-02-15 10:22:06',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.admin.personas.curp.formato','¡El formato del CURP es incorrecto!',44,'2017-02-17 09:14:04',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.admin.personas.usuario.existe','¡El nombre se usuario ya éxiste!',44,'2017-02-15 10:20:43',NULL,39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.acciones.agregar','Agregar',130,'2017-02-02 12:18:21',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.acciones.buscar','Buscar',130,'2017-02-01 11:40:36',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.acciones.eliminar','Eliminar',130,'2017-01-19 17:51:40',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.boton.guardar','Guardar',130,'2017-01-27 12:16:41',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.btn.adm.competencias','Administrar competencias',130,'2017-01-20 11:48:02',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.catPlanesProgramas','Catálogos Planes y Programas',2,'2017-01-05 17:00:54',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.dialog.competencias','Competencias',130,'2017-01-20 11:49:00',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.dialog.modificar','Editar',130,'2017-01-20 11:49:52','2017-03-09 10:44:23',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.etiqueta.acciones','Acciones',130,'2017-02-01 11:49:58',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.etiqueta.activo','Activo',130,'2017-02-01 11:48:49',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.etiqueta.comp.desc','Descripción',130,'2017-01-27 11:22:37',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.etiqueta.comp.pond','Ponderación',130,'2017-02-01 11:46:17',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.etiqueta.edita.comp','Edición de competencias específicas por eje',130,'2017-01-18 13:43:03',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.etiqueta.nombre.comp.esp','Nombre',130,'2017-01-27 11:22:07',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.logisticaInfra',' Catálogos Logística e infraestructura',135,'2017-02-23 13:20:16',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.modal.boton.cancelar','Cancelar',130,'2017-01-23 17:38:15',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.modal.boton.guardar','Guardar',130,'2017-01-23 17:37:19',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.PlanesProgramas','Catálogos Planes y Programas',130,'2017-01-05 17:16:26',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.tblheader.accion','Accion',130,'2017-01-19 16:22:26',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.tblheader.competencia','Competencia',130,'2017-01-19 10:27:48',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.tblheader.id','ID',130,'2017-01-19 10:26:45',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.titulo.eje.comp','Competencias específicas por eje',130,'2017-01-18 11:55:43','2017-01-18 13:39:59',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.administracion.catalogos.titulo.selecciona.eje','Editar competencias específicas',130,'2017-01-18 13:43:43',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.agregarPersona.msg.activacion','Bienvenido al sistema elearning de sedesol  {0} tu cuenta  a sido activada.',45,'2016-09-30 12:09:41',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.agregarPersona.msg.activarCuenta','Para activar su cuenta por favor de click en el siguiente enlace {0} , o copie la url en su navegador',45,'2016-10-04 11:34:14',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.agregarPersona.msg.recuperacionContrasenia','Para recuperar su contraseña por favor de click en el siguiente enlace {0} , o copie la url en su navegador.',45,'2016-09-30 12:46:33',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.agregarPersona.msg.tokenInvalido','El token proporcionado es invalido',45,'2016-09-30 13:21:59',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.aceptar','Guardar',44,'2016-07-26 11:40:41','2017-02-20 15:48:03',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.agregar','Individual',44,'2016-07-26 11:41:32','2017-02-24 16:55:36',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.buscar','Buscar',44,'2016-07-26 11:41:22',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.cancelar','Cancelar',44,'2016-07-26 12:44:43',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.editar','Editar usuario',44,'2016-10-03 11:05:30','2017-03-09 10:45:38',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.limpiar','Cancelar',44,'2016-09-29 15:35:30','2016-10-04 10:49:24',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.permisos','Asignar Permisos',44,'2016-10-03 11:05:59',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.regresar','Regresar',44,'2016-09-29 15:28:37',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.boton.ver','Ver usuario',44,'2016-10-03 11:05:00',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.accion','Acciones',44,'2016-07-26 11:54:47','2017-03-10 10:56:28',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.activo','Activo',44,'2016-07-26 11:53:23',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.apMaterno','Segundo apellido',44,'2016-07-26 11:50:38','2017-02-15 10:40:44',39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.apPaterno','Primer apellido',44,'2016-07-26 11:50:21','2017-02-15 10:39:51',39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.contrasenia','Contraseña',44,'2016-07-26 12:19:21',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.correo','Correo electrónico',44,'2016-07-26 11:52:30',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.correoPrincipal','Correo principal',44,'2016-07-26 12:22:59',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.curp','CURP',44,'2016-07-26 12:24:41',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.datoRequerido','El dato es requerido',44,'2016-07-26 12:12:55',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.fechaNac','Fecha de nacimiento',44,'2016-07-26 11:51:27','2017-01-05 12:44:22',39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.genero','Género',44,'2016-07-26 11:52:13','2017-01-05 12:45:20',39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.genero.femenino','Femenino',44,'2016-09-29 13:37:33',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.genero.masculino','Masculino',44,'2016-09-29 13:35:43',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.nombre','Nombre',44,'2016-07-26 11:49:54',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.nuevoUsuario','Crear nuevo usuario',44,'2017-02-16 12:52:06',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.persona','Usuario',44,'2016-07-26 12:11:50','2016-09-09 10:50:31',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.personas','Usuarios',44,'2016-07-26 11:42:46','2016-09-09 10:50:10',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.registro','Alta usuarios',44,'2016-07-26 12:27:46','2016-09-09 10:51:02',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.rfc','RFC',44,'2016-07-26 12:24:58',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.sinRegistros','Sin registros',44,'2016-07-26 12:09:51',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.tipoCorreo','Tipo de correo',44,'2016-07-26 12:20:41','2017-01-05 13:04:40',39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.etiqueta.usuario','Usuario',44,'2016-07-26 11:51:12',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.titulo.busquedaAvanzada','Búsqueda avanzada',44,'2016-09-26 16:09:20','2017-01-05 11:23:35',39);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.usuarios.boton.editar','Editar usuario',44,'2016-10-03 16:26:32','2017-03-09 10:45:45',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.adminPersona.usuarios.boton.ver','Ver usuario',44,'2016-10-03 16:26:18',NULL,2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.boton.agregar','Agregar',39,'2016-07-26 12:30:25',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.boton.editar','Editar',39,'2016-07-26 12:30:37','2017-03-09 10:45:00',2);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.boton.regresar','Regresar',39,'2016-07-26 12:30:04',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.columna.activo','Activo',39,'2016-07-26 12:32:45',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.columna.codigo','Código postal',39,'2016-07-26 12:31:53',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.columna.id','ID',39,'2016-07-26 12:31:03','2017-01-05 15:24:25',5);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.columna.nombre','Nombre',39,'2016-07-26 12:31:32',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.columna.tipo','Tipo',39,'2016-07-26 12:32:29',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.etiqueta.entidad','Entidad federativa',39,'2016-07-26 12:29:00',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.etiqueta.municipio','Municipio',39,'2016-07-26 12:29:18',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.modal.boton.cancelar','Cancelar',39,'2016-07-26 12:35:54',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.modal.boton.guardar','Guardar',39,'2016-07-26 12:36:12',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.modal.etiqueta.activo','Activo',39,'2016-07-26 12:35:33',NULL,1);
INSERT INTO `tbl_textos_sistema` (`clave`,`valor`,`id_funcionalidad`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES ('gw.asentamientos.modal.etiqueta.codigo','Código postal',39,'2016-07-26 12:34:50',NULL,1);


/*
tbl_ficha_descriptiva_objeto_aprendizaje
*/

INSERT INTO `tbl_ficha_descriptiva_objeto_aprendizaje` (`id_foa`,`descripcion_contenido`,`palabras_clave`,`proposito`,`tratamiento_editorial`,`resumen`,`version`,`lugar_edicion`,`anexos`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`rango_tipico_de_edad`,`tiempo_aprendizaje`,`condiciones_uso`,`descripcion_objeto_relacionado`,`anotacion`,`tiempo_estimado_lectura`,`peso_archivos_scorm`) VALUES (1,'contenido uno','palabra clave',NULL,NULL,NULL,NULL,NULL,NULL,'2017-03-15 15:57:40',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `tbl_ficha_descriptiva_objeto_aprendizaje` (`id_foa`,`descripcion_contenido`,`palabras_clave`,`proposito`,`tratamiento_editorial`,`resumen`,`version`,`lugar_edicion`,`anexos`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`,`rango_tipico_de_edad`,`tiempo_aprendizaje`,`condiciones_uso`,`descripcion_objeto_relacionado`,`anotacion`,`tiempo_estimado_lectura`,`peso_archivos_scorm`) VALUES (2,'contenido dos','palabra clave dos',NULL,NULL,NULL,NULL,NULL,NULL,'2017-03-15 15:57:40',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*tbl_estructura_tematica*/
INSERT INTO `tbl_estructura_tematica` (`id_estructura_tematica`,`num_unidades_tematicas`,`id_programa`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`,`activo`) VALUES (7,3,81,'2017-03-22 00:00:01',2,'2017-03-22 00:00:02',1);

/*det_etematica_tema*/
INSERT INTO `det_etematica_tema` (`id_det_tema`,`id_estructura_tematica`,`nombre_tema`,`usuario_modifico`,`fecha_registro`,`fecha_actualizacion`) VALUES (10,7,'Tema Informatica 1',1,'2017-03-22 00:00:01','2017-03-22 00:00:02');

/*det_est_unidad_didactica*/
INSERT INTO `det_est_unidad_didactica` (`id_unidad_didactica`,`titulo_ua`,`objetivos_especificos`,`num_subtemas`,`fuentes_informacion`,`evaluacion`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`,`activo`,`actividades_aprendizaje`,`medios_recursos`) VALUES (3,'Tema 1 Introduccion a la inforamatica','Objetivo conocer la introduccion a la informatica',1,'Libros,e foros,wikis ','Presencial','2017-03-22 00:00:01',2,'2017-03-22 00:00:01',1,'Actividades de aprendizaje','Actividades de aprendizaje');
INSERT INTO `det_est_unidad_didactica` (`id_unidad_didactica`,`titulo_ua`,`objetivos_especificos`,`num_subtemas`,`fuentes_informacion`,`evaluacion`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`,`activo`,`actividades_aprendizaje`,`medios_recursos`) VALUES (4,'Tema 2 Sistemas Operativos','Objetivos Conocer los sistemas operativos',1,'Libros,e foros,wikis ','Presencial','2017-03-22 00:00:01',2,'2017-03-22 00:00:01',1,'Actividades de aprendizaje','Actividades de aprendizaje');
INSERT INTO `det_est_unidad_didactica` (`id_unidad_didactica`,`titulo_ua`,`objetivos_especificos`,`num_subtemas`,`fuentes_informacion`,`evaluacion`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`,`activo`,`actividades_aprendizaje`,`medios_recursos`) VALUES (5,'Tema 3 Programacion orientada a objetos','Objetivos Conocer la programacion Orientada a objetos',1,'Libros,e foros,wikis ','Presencial','2017-03-22 00:00:01',2,'2017-03-22 00:00:01',1,'Actividades de aprendizaje','Actividades de aprendizaje');
INSERT INTO `det_est_unidad_didactica` (`id_unidad_didactica`,`titulo_ua`,`objetivos_especificos`,`num_subtemas`,`fuentes_informacion`,`evaluacion`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`,`activo`,`actividades_aprendizaje`,`medios_recursos`) VALUES (6,'Tema 4 Arquitectura de software','Objetivo Conocer los topicos para la arquitectura de software',1,'Libros,e foros,wikis ','Presencial','2017-03-22 00:00:01',2,'2017-03-22 00:00:01',1,'Actividades de aprendizaje','Actividades de aprendizaje');
INSERT INTO `det_est_unidad_didactica` (`id_unidad_didactica`,`titulo_ua`,`objetivos_especificos`,`num_subtemas`,`fuentes_informacion`,`evaluacion`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`,`activo`,`actividades_aprendizaje`,`medios_recursos`) VALUES (7,'Tema 5  Metodologias de desarrollo de software','Objetivo Conocer las diferentes metodologias de desarrollo de software.',1,'Libros,e foros,wikis ','Presencial','2017-03-22 00:00:01',2,'2017-03-22 00:00:01',1,'Actividades de aprendizaje','Actividades de aprendizaje');

/*rel_estructura_unidad_didactica*/
INSERT INTO `rel_estructura_unidad_didactica` (`id_det_tema`,`id_unidad_didactica`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`) VALUES (10,3,'2017-03-22 00:00:01',2,'2017-03-22 00:00:02');
INSERT INTO `rel_estructura_unidad_didactica` (`id_det_tema`,`id_unidad_didactica`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`) VALUES (10,4,'2017-03-22 00:00:01',2,'2017-03-22 00:00:02');
INSERT INTO `rel_estructura_unidad_didactica` (`id_det_tema`,`id_unidad_didactica`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`) VALUES (10,5,'2017-03-22 00:00:01',2,'2017-03-22 00:00:02');
INSERT INTO `rel_estructura_unidad_didactica` (`id_det_tema`,`id_unidad_didactica`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`) VALUES (10,6,'2017-03-22 00:00:01',2,'2017-03-22 00:00:02');
INSERT INTO `rel_estructura_unidad_didactica` (`id_det_tema`,`id_unidad_didactica`,`fecha_registro`,`usuario_modifico`,`fecha_actualizacion`) VALUES (10,7,'2017-03-22 00:00:01',2,'2017-03-22 00:00:02');

/*rel_unidad_oa_ava*/

INSERT INTO `rel_unidad_oa_ava` (`id`,`id_ava`,`id_unidad_didactica`,`id_foa`,`validacion_guion_inst`,`validacion_desarrollo_oa`,`validacion_scorm`,`funcionalidad`,`porcentaje_avance_oa`,`fecha_actualizacion`,`fecha_registro`,`usuario_modifico`) VALUES (1,1,3,1,0,1,NULL,NULL,10,'2017-03-23 10:28:51','2017-03-23 10:28:51',1);
INSERT INTO `rel_unidad_oa_ava` (`id`,`id_ava`,`id_unidad_didactica`,`id_foa`,`validacion_guion_inst`,`validacion_desarrollo_oa`,`validacion_scorm`,`funcionalidad`,`porcentaje_avance_oa`,`fecha_actualizacion`,`fecha_registro`,`usuario_modifico`) VALUES (2,1,4,NULL,NULL,NULL,NULL,NULL,10,'2017-03-23 10:28:51','2017-03-23 10:28:51',1);
INSERT INTO `rel_unidad_oa_ava` (`id`,`id_ava`,`id_unidad_didactica`,`id_foa`,`validacion_guion_inst`,`validacion_desarrollo_oa`,`validacion_scorm`,`funcionalidad`,`porcentaje_avance_oa`,`fecha_actualizacion`,`fecha_registro`,`usuario_modifico`) VALUES (3,1,5,NULL,NULL,NULL,NULL,NULL,10,'2017-03-23 10:28:51','2017-03-23 10:28:51',1);
INSERT INTO `rel_unidad_oa_ava` (`id`,`id_ava`,`id_unidad_didactica`,`id_foa`,`validacion_guion_inst`,`validacion_desarrollo_oa`,`validacion_scorm`,`funcionalidad`,`porcentaje_avance_oa`,`fecha_actualizacion`,`fecha_registro`,`usuario_modifico`) VALUES (4,1,6,NULL,NULL,NULL,NULL,NULL,10,'2017-03-23 10:28:51','2017-03-23 10:28:51',1);
INSERT INTO `rel_unidad_oa_ava` (`id`,`id_ava`,`id_unidad_didactica`,`id_foa`,`validacion_guion_inst`,`validacion_desarrollo_oa`,`validacion_scorm`,`funcionalidad`,`porcentaje_avance_oa`,`fecha_actualizacion`,`fecha_registro`,`usuario_modifico`) VALUES (5,1,7,NULL,NULL,NULL,NULL,NULL,10,'2017-03-23 10:28:51','2017-03-23 10:28:51',1);

/*cat_tema_recursos*/
INSERT INTO `cat_tema_recursos` (`id`,`nombre`,`descripcion`,`fecha_registro`,`activo`,`orden`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,'SCORM','SCORM','2017-03-27 12:56:55',1,NULL,'2017-03-27 12:56:55',2);
INSERT INTO `cat_tema_recursos` (`id`,`nombre`,`descripcion`,`fecha_registro`,`activo`,`orden`,`fecha_actualizacion`,`usuario_modifico`) VALUES (2,'Foro','Foro','2017-03-27 12:56:55',1,NULL,'2017-03-27 12:56:55',2);
INSERT INTO `cat_tema_recursos` (`id`,`nombre`,`descripcion`,`fecha_registro`,`activo`,`orden`,`fecha_actualizacion`,`usuario_modifico`) VALUES (3,'Tema de discusión','Tema de discusión','2017-03-27 12:56:55',1,NULL,'2017-03-27 12:56:55',2);

/*tbl_recursos_oa*/
INSERT INTO `tbl_recursos_oa` (`id_unidad_oa`,`id_cat_tema_recurso`,`nombre`,`instrucciones_contenido`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,1,'SCORM INFORMATICA','Instalar el contenido en plataforma Moodle','2017-03-27 15:48:58','2017-03-27 15:48:58',2);
INSERT INTO `tbl_recursos_oa` (`id_unidad_oa`,`id_cat_tema_recurso`,`nombre`,`instrucciones_contenido`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,2,'Curso informatica un','Curso de informatica uno.','2017-03-28 17:15:26','2017-03-28 17:15:26',2);

/*rel_reponsable_produccion_oa*/

INSERT INTO `rel_reponsable_produccion_oa` VALUES (1,3,1,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (2,4,1,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (3,6,1,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (4,7,2,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (5,8,2,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (6,12,2,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (7,14,3,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (8,16,4,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (9,17,3,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);
INSERT INTO `rel_reponsable_produccion_oa` VALUES (10,20,1,'2017-03-23 10:31:48','2017-03-23 10:31:48',2);



INSERT INTO `rel_reponsable_produccion_ec` (`id_evento_capacitacion`,`id_reponsable_produccion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,6,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);
INSERT INTO `rel_reponsable_produccion_ec` (`id_evento_capacitacion`,`id_reponsable_produccion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (2,7,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);
INSERT INTO `rel_reponsable_produccion_ec` (`id_evento_capacitacion`,`id_reponsable_produccion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (3,12,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);
INSERT INTO `rel_reponsable_produccion_ec` (`id_evento_capacitacion`,`id_reponsable_produccion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (4,17,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);
INSERT INTO `rel_reponsable_produccion_ec` (`id_evento_capacitacion`,`id_reponsable_produccion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (5,18,'2017-03-27 00:00:00','2017-03-27 00:00:00',1);



/*tbl_carga_archivos_oa*/

INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,1,1,'',1,'Introduccion a la informatica SCORM',0,'/home/Scorm/20170329/','2017-03-29 11:48:26','2017-03-29 11:48:26',2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (2,1,1,'',1,'Introduccion a la informatica SCORM',1,'/home/Scorm/20170329/','2017-03-29 11:48:26','2017-03-29 11:48:26',2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (29,1,1,'f79169dd-ee7d-4539-b99f-a6f89e605d6e',1,'AsistenciasGrupo.pdf',0,'/home/gramirez/upload/','2017-03-31 12:54:38',NULL,2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (30,1,1,'5e34e5d2-29b9-4567-9da5-5e4a5de1b4ae',1,'AsistenciasGrupo.pdf',1,'/home/gramirez/upload/','2017-03-31 12:54:55',NULL,2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (31,1,2,'081b4075-8fc7-47a3-b407-c3bd26358fe5',1,'el-gran-libro-de-html5-css3-y-javascript.pdf',2,'/home/gramirez/upload/','2017-03-31 13:33:52',NULL,2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (50,1,2,'081b4075-8fc7-47a3-b407-c3bd26358fe5',1,'el-gran-libro-de-html5-css3-y-javascript.pdf',2,'/home/gramirez/upload/','2017-03-31 13:33:52',NULL,2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (32,1,2,'07d01a1f-93c6-4311-ada9-fbd79ae93020',1,'AsistenciasGrupo.xls',3,'/home/gramirez/upload/','2017-03-31 13:41:30',NULL,2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (33,1,3,'49112a35-2ff4-4047-aa82-afad94800296',1,'AsistenciasGrupo(1).pdf',0,'/home/gramirez/upload/','2017-03-31 13:45:24',NULL,2);
INSERT INTO `tbl_carga_archivos_oa` (`id`,`id_unidad_oa`,`id_clasificacion`,`id_archivo`,`id_lms`,`nombre_archivo`,`version_archivo`,`directorio`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (34,1,3,'d0bda02a-7c80-4a1c-8a49-9cad267c2f35',1,'AsistenciasGrupo(2).pdf',1,'/home/gramirez/upload/','2017-03-31 13:45:28',NULL,2);



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-10 13:29:52

