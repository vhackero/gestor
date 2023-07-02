--
-- Dumping data for table `cat_unidades_responsables`
--

INSERT INTO `cat_unidades_responsables` VALUES (1,'00','Coordinación Nacional Prospera',1,'2016-07-22 11:41:04',NULL,1),(2,'213','Microrregiones',1,'2016-07-22 11:41:04',NULL,1);


--
-- Dumping data for table `cat_programas_sociales`
--

INSERT INTO `cat_programas_sociales` VALUES (1,'45','PROSPERA Programa de Inclusión Social',1,'2016-07-22 11:41:04',NULL,1,1),(2,'1','PROGRAMA 3X1 PARA MIGRANTES',1,'2016-07-22 11:41:04',NULL,1,1);

--
-- Dumping data for table `cat_actividades_aprendizaje_programa`
--

INSERT INTO `cat_actividades_aprendizaje_programa` VALUES (1,'Solución de problemas',NULL,1,1,'2016-08-19 16:54:54',NULL,1),(2,'Analogías',NULL,1,1,'2016-08-19 16:55:10',NULL,1),(3,'Mapas conceptuales y redes semánticas',NULL,1,1,'2016-08-19 16:55:22',NULL,1),(4,'Uso de estructuras textuales',NULL,1,1,'2016-08-19 16:55:37',NULL,1),(5,'Análisis de casos',NULL,1,1,'2016-08-19 16:55:52',NULL,1);


--
-- Dumping data for table `cat_alcance_area`
--

INSERT INTO `cat_alcance_area` VALUES (1,'Interna','----','2017-02-03 16:07:10',1,1,NULL,2),(2,'Externa','----','2017-02-03 16:07:28',1,1,NULL,2);

--
-- Dumping data for table `cat_alcance_plan`
--

INSERT INTO `cat_alcance_plan` VALUES (2,'Publico',NULL,'2016-12-29 10:41:34',1,NULL,NULL,1),(3,'Privado',NULL,'2016-12-29 10:41:34',1,NULL,NULL,1),(4,'Restringido',NULL,'2016-12-29 10:41:34',1,NULL,NULL,1);

--
-- Dumping data for table `cat_ambientes_aprendizaje_ec`
--

INSERT INTO `cat_ambientes_aprendizaje_ec` VALUES (1,'Sensei LMS',NULL,'2016-10-20 17:12:06',1,1,NULL,NULL,1),(2,'Moodle',NULL,'2016-10-20 17:12:12',2,1,NULL,NULL,1),(3,'Proyecto Sakai',NULL,'2016-10-20 17:12:18',2,1,NULL,NULL,1),(4,'Chamilo',NULL,'2016-10-20 17:12:23',2,1,NULL,NULL,1),(5,'ATutor',NULL,'2016-10-20 17:13:13',2,1,NULL,NULL,1),(6,'SuccessFactors LMS',NULL,'2016-10-20 17:13:20',1,1,NULL,NULL,1),(7,'eCollege',NULL,'2016-10-20 17:13:21',1,1,NULL,NULL,1),(8,'Blackboard',NULL,'2016-10-20 17:13:21',1,1,NULL,NULL,1);

--
-- Dumping data for table `cat_aptitudes_plan`
--

INSERT INTO `cat_aptitudes_plan` VALUES (1,'Lealtad','Lealtad',1,NULL,'2016-12-27 15:12:28',NULL,1),(2,'Proactividad','Proactividad',1,NULL,'2016-12-27 15:12:28',NULL,1),(3,'Capacidad de adaptación','Capacidad de adaptación',1,NULL,'2016-12-27 15:12:28',NULL,1);

--
-- Dumping data for table `cat_areas_conocimiento`
--

INSERT INTO `cat_areas_conocimiento` VALUES (1,'Psicologia en la sociedad','es un test de actualizacion II','2016-08-09 15:35:31',0,'2016-08-15 16:41:39',2),(2,'Matematicas Generales',NULL,'2016-10-20 17:43:27',1,NULL,1),(3,'Psicologia en la sociedad','es un test de actualizacion II','2016-08-09 15:35:31',0,'2016-08-15 16:41:39',2);

--
-- Dumping data for table `cat_ubicacion_territorial`
--

INSERT INTO `cat_ubicacion_territorial` VALUES (1,'Oficinas centrales',NULL,1,NULL,'2017-02-20 16:59:16',NULL,1),(2,'Delegación estatal',NULL,1,NULL,'2017-02-20 16:59:17',NULL,1);



--
-- Dumping data for table `tbl_organismos_gubernamentales`
--
INSERT INTO `tbl_organismos_gubernamentales` VALUES (1,'Poder Ejecutivo',NULL,NULL,1,'2016-12-02 14:30:49',1,NULL,1,NULL),(2,'Poder Legislativo',NULL,NULL,1,'2016-12-02 14:30:49',1,NULL,1,NULL),(3,'Poder Judicial',NULL,NULL,1,'2016-12-02 14:30:49',1,NULL,1,NULL),(4,'Seguridad Nacional',NULL,NULL,1,'2016-12-02 14:30:49',1,NULL,1,NULL),(5,'Energia',NULL,NULL,1,'2016-12-02 14:30:49',1,NULL,1,NULL),(6,'Presidencia de la República',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(7,'Banco de México',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(8,'Instituto Nacional de Estadística, Geografía e Informática (INEGI)',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(9,'Procuraduría General de la República (PGR)',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(10,'Secretaría de Agricultura, Ganadería y Desarrollo Rural',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(11,'Secretaría de Comunicaciones y Transportes (SCT)',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(12,'Secretaría de Contraloría y Desarrollo Administrativo (SECODAM)',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(13,'Secretaría de Desarrollo Social (SEDESOL)',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(14,'Secretaría de Gobernación (SG)',NULL,1,2,'2016-12-02 14:30:50',1,NULL,1,NULL),(15,'Secretaría de Hacienda y Crédito Público (SHCP)',NULL,1,2,'2016-12-02 14:35:49',1,NULL,1,NULL),(16,'Secretaría de Turismo',NULL,1,2,'2016-12-02 14:35:49',1,NULL,1,NULL),(17,'Secretaría de Medio Ambiente, Recursos Naturales y Pesca (SEMARNAP)',NULL,1,2,'2016-12-02 14:35:49',1,NULL,1,NULL),(18,'Secretaría de la Reforma Agraria (SRA)',NULL,1,2,'2016-12-02 14:35:50',1,NULL,1,NULL),(19,'Secretaría de Relaciones Exteriores (SRE)',NULL,1,2,'2016-12-02 14:35:50',1,NULL,1,NULL),(20,'Cámara de Diputados',NULL,2,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(21,'Cámara de Senadores',NULL,2,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(22,'H. Congreso de la Unión',NULL,2,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(23,'Consejo de la Judicatura Federal',NULL,3,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(24,'Suprema Corte de Justicia de la Nación',NULL,3,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(25,'Secretaría de la Defensa Nacional (SEDENA)',NULL,4,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(26,'Sitio Extraoficial de la Fuerza Aerea Mexicana',NULL,4,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(27,'Comisión Federal de Electricidad (CFE)',NULL,5,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(28,'Comisión Nacional para el Ahorro de Energía (CONAE)',NULL,5,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(29,'Luz y fuerza del Centro (LFC)',NULL,5,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(30,'Instituto de Investigaciones Eléctricas',NULL,5,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(31,'Instituto Mexicano del Petróleo (IMP)',NULL,5,2,'2016-12-02 14:44:20',1,NULL,1,NULL),(32,'Instituto Nacional de Investigaciones Nucleares (ININ)',NULL,5,2,'2016-12-02 14:44:21',1,NULL,1,NULL),(33,'Petróleos Mexicanos (PEMEX)',NULL,5,2,'2016-12-02 14:44:21',1,NULL,1,NULL),(34,'Secretaría de Energía',NULL,5,2,'2016-12-02 14:44:21',1,NULL,1,NULL),(35,'Instituto Nacional de Ciencias Penales',NULL,9,3,'2016-12-02 15:57:55',1,NULL,1,NULL),(36,'Apoyos y Servicios a la Comercialización Agropecuaria',NULL,10,3,'2016-12-02 15:58:08',1,NULL,1,NULL),(37,'Comisión Nacional de las Zonas Aridas',NULL,10,3,'2016-12-02 15:58:08',1,NULL,1,NULL),(38,'Fideicomiso de Riesgo Compartido',NULL,10,3,'2016-12-02 15:58:08',1,NULL,1,NULL),(39,'Productora Nacional de Biológicos Veterinarios',NULL,10,3,'2016-12-02 15:58:08',1,NULL,1,NULL),(40,'Servicio Nacional de Sanidad, Inocuidad y Calidad Agroalimentaria',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(41,'Servicio de Información Agroalimentaria y Pesquera',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(42,'Universidad Autónoma Agraria Antonio Narro',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(43,'Comité Nacional para el Desarrollo Sustentable de la Caña de Azucar',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(44,'Comisión Nacional de Acuacultura y Pesca',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(45,'Colegio de Postgraduados en Ciencias Agricolas',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(46,'Instituto Nacional de Investigaciones Forestales, Agrícolas y Pecuarias',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(47,'Instituto Nacional de Pesca',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(48,'Instituto Nacional Para el Desarrollo de Capacidades del Sector Rural A.C.',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(49,'Servicio Nacional de Inspección y Certificación de Semillas',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(50,'Universidad Autónoma Chaping',NULL,10,3,'2016-12-02 15:58:09',1,NULL,1,NULL),(51,'Colegio Superior Agropecuario del Estado de Guerrero',NULL,10,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(52,'Aeropuerto Internacional de la Ciudad de México',NULL,11,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(53,'Aeropuertos y Servicios Auxiliares',NULL,11,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(54,'Caminos y Puentes Federales',NULL,11,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(55,'Comisión Federal de Telecomunicaciones',NULL,11,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(56,'Correos de México',NULL,11,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(57,'Telecomunicaciones de México',NULL,11,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(58,'Instituto Mexicano del Transporte',NULL,11,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(59,'Oportunidades',NULL,13,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(60,'Instituto Nacional de las Personas Adultas Mayores',NULL,13,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(61,'Liconsa S.A. de C.V.',NULL,13,3,'2016-12-02 15:58:10',1,NULL,1,NULL),(62,'Instituto Nacional de Desarrollo Social',NULL,13,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(63,'Diconsa S. A. de C.V.',NULL,13,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(64,'Comisión para la Regularización de la Tenencia de la Tierra',NULL,13,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(65,'Fideicomiso Fondo Nacional de Habitaciones Populares',NULL,13,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(66,'Fondo Nacional para el Fomento de las Artesanías',NULL,13,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(67,'Centro de Investigación y Seguridad Nacional',NULL,14,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(68,'Centro de Producción de Programas Informativos Especiales',NULL,14,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(69,'Centro Nacional de Prevención de Desastres',NULL,14,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(70,'Comisión Nacional para Prevenir y Erradicar la Violencia contra las Mujeres',NULL,14,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(71,'Coordinación General de la Comisión Mexicana de Ayuda a Refugiados',NULL,14,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(72,'Instituto Nacional de Migración',NULL,14,3,'2016-12-02 15:58:11',1,NULL,1,NULL),(73,'Instituto Nacional para el Federalismo y el Desarrollo Municipal',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(74,'Secretariado Ejecutivo del Sistema Nacional de Seguridad Pública',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(75,'Secretaría General del Consejo Nacional de Población',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(76,'Secretaría Técnica de la Comisión Calificadora de Publicaciones y Revistas Ilustradas',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(77,'Secretaría Técnica del Consejo de Coordinación para la Implementación del Sistema de Justicia Penal',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(78,'Consejo Nacional para Prevenir la Discriminación',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(79,'Talleres Gráficos de México',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(80,'Órgano Promotor de Medios Audiovisuales',NULL,14,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(81,'Agroasemex, S.A.',NULL,15,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(82,'Banco del Ahorro Nacional y Servicios Financieros',NULL,15,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(83,'Banco Nacional de Comercio Exterior, S.N.C.',NULL,15,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(84,'Banco Nacional de Obras y Servicios Públicos, S.N.C.',NULL,15,3,'2016-12-02 15:58:12',1,NULL,1,NULL),(85,'Banco Nacional del Ejercito, Fuerza Aérea y Armada, S.N.C.',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(86,'Casa de Moneda de México',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(87,'Comisión Nacional Bancaria y de Valores',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(88,'Comisión Nacional de Seguros y Fianzas',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(89,'Comisión Nacional del Sistema de Ahorro para el Retiro',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(90,'Comisión Nacional para la Protección y Defensa de los Usuarios de Servicios Financieros',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(91,'Financiera Rural',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(92,'Fondo de Capitalización e Inversión del Sector Rural',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(93,'Instituto para el Desarrollo Técnico de la Haciendas Públicas',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(94,'Instituto para la Protección al Ahorro Bancario',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(95,'Lotería Nacional para la Asistencia Pública',NULL,15,3,'2016-12-02 15:58:13',1,NULL,1,NULL),(96,'Nacional Financiera',NULL,15,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(97,'Pronósticos para la Asistencia Pública',NULL,15,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(98,'Servicio de Administración Tributaria',NULL,15,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(99,'Servicio de Administración y Enajenación de Bienes',NULL,15,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(100,'Sistema de Fideicomisos Instituidos en Relación con la Agricultura',NULL,15,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(101,'Sociedad Hipotecaria Federal',NULL,15,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(102,'Consejo de Promoción Turística de México, S.A. de C.V.',NULL,16,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(103,'Fonatur Constructora, S.A. de C.V.',NULL,16,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(104,'Fonatur Mantenimiento Turístico, S.A. de C.V.',NULL,16,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(105,'Fonatur Operadora Portuaria, S.A. de C.V.',NULL,16,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(106,'Fondo Nacional de Fomento al Turismo',NULL,16,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(107,'Comisión Nacional de Áreas Naturales Protegidas',NULL,17,3,'2016-12-02 15:58:14',1,NULL,1,NULL),(108,'Comisión Nacional del Agua',NULL,17,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(109,'Comisión Nacional Forestal',NULL,17,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(110,'Comisión Nacional para el Conocimiento y Uso de la Biodiversidad',NULL,17,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(111,'Instituto Mexicano de Tecnología del Agua',NULL,17,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(112,'Instituto Nacional de Ecología',NULL,17,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(113,'Procuraduría Federal de Protección al Ambiente',NULL,17,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(114,'Fideicomiso Fondo Nacional de Fomento Ejidal',NULL,18,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(115,'Procuraduría Agraría',NULL,18,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(116,'Registro Agrario Nacional',NULL,18,3,'2016-12-02 15:58:15',1,NULL,1,NULL),(117,'Instituto de los Mexicanos en el Exterio',NULL,19,3,'2016-12-02 15:58:15',1,NULL,1,NULL);


--
-- Dumping data for table `cat_sedes`
--

INSERT INTO `cat_sedes` VALUES (1,'Sede Sedesol TEST',13,1,'Dir Desconocida','test','4000','1',NULL,'2017-03-07 10:33:54',NULL,1);

--
-- Dumping data for table `cat_areas_infraestructura`
--

INSERT INTO `cat_areas_infraestructura` VALUES (1,'Sala Artesanos',NULL,1,NULL,'2017-02-27 17:42:48',NULL,1),(2,'Sala Cantera',NULL,1,NULL,'2017-02-27 17:42:48',NULL,1),(3,'Sala Talavera',NULL,1,NULL,'2017-02-27 17:42:48',NULL,1),(4,'Sala Alfareros',NULL,1,NULL,'2017-02-27 17:42:48',NULL,1),(5,'Sala Evad',NULL,1,NULL,'2017-02-27 17:42:48',NULL,1),(6,'Sala Sedesol',NULL,1,NULL,'2017-02-27 17:42:48',NULL,1);



--
-- Dumping data for table `cat_estatus_area`
--

INSERT INTO `cat_estatus_area` VALUES (1,'Área activa',NULL,1,NULL,'2017-02-20 17:09:33',NULL,1),(2,'Área inactiva',NULL,1,NULL,'2017-02-20 17:09:35',NULL,1);

--
-- Dumping data for table `cat_areas_sede`
--


INSERT INTO `cat_areas_sede` VALUES (1,1,2,NULL,NULL,1,'2017-03-07 10:34:29',NULL,1);


--
-- Dumping data for table `cat_asistencia`
--

INSERT INTO `cat_asistencia` VALUES (1,'A','ASISTENCIA',' ',100,1,1,'2017-03-08 00:00:00','2017-03-08 00:00:00',1),(2,'R','RETARDO',' ',80,1,1,'2017-03-08 00:00:00','2017-03-08 00:00:00',1),(3,'FJ','FALTA JUSTIFICADA',' ',80,1,1,'2017-03-08 00:00:00','2017-03-08 00:00:00',1),(4,'F','FALTA',' ',0,1,1,'2017-03-08 00:00:00','2017-03-08 00:00:00',1),(5,'I','INCONCLUSO',' ',50,1,1,'2017-03-08 00:00:00','2017-03-08 00:00:00',1);

--
-- Dumping data for table `cat_categoria_evento_capacitacion`
--

INSERT INTO `cat_categoria_evento_capacitacion` VALUES (1,'Interno','----',1,1,'2017-02-07 14:00:36',NULL,2),(2,'En colaboración','----',1,1,'2017-02-07 14:00:54',NULL,2),(3,'Externo','----',1,1,'2017-02-07 14:01:07',NULL,2),(4,'Mixto','----',1,1,'2017-02-07 14:01:11',NULL,2);

--
-- Dumping data for table `cat_clasificacion_archivo_oa`
--

INSERT INTO `cat_clasificacion_archivo_oa` VALUES (1,'Guion instruccional','Guion instruccional','2017-02-20 16:01:06',1,1,'2017-02-23 10:53:20',39),(2,'Desarrollo AO','Desarrollo AO','2017-02-20 16:01:06',1,3,'2017-02-23 10:54:05',39),(3,'SCORM','SCORM','2017-02-20 16:01:06',1,NULL,NULL,1);

--
-- Dumping data for table `cat_clasificacion_ava`
--

INSERT INTO `cat_clasificacion_ava` VALUES (1,'De un evento de capacitación anterior','----',1,1,'2017-02-03 16:03:08',NULL,2),(2,'Usar curso moodle','----',1,1,'2017-02-03 16:03:31',NULL,2),(3,'Nuevo','----',1,1,'2017-02-03 16:04:01',NULL,2);


--
-- Dumping data for table `cat_competencias_especificas`
--

INSERT INTO `cat_competencias_especificas` VALUES (1,'Lealtad','',1,1,'2017-02-02 12:07:00','2017-02-02 17:12:12',2,70),(2,'Identidad','',1,1,'2017-02-02 12:07:00','2017-02-02 12:07:00',2,80),(4,'Entorno Cultural',NULL,1,1,'2017-02-02 12:07:00','2017-02-02 12:07:00',2,65),(5,'Sentido de pertenencia','',1,1,'2017-02-02 12:23:12',NULL,2,80),(6,'Liderazgo institucional','',1,1,'2017-02-02 12:19:50','2017-02-02 17:07:42',2,80),(7,'Ética institucional','',1,1,'2017-02-02 12:25:37','2017-02-02 17:09:01',2,75),(8,'Conducta institucional','',1,1,'2017-02-02 17:10:06',NULL,2,70),(9,'Colaboración','',1,1,'2017-02-02 17:10:43',NULL,2,80),(10,'Normatividad','',1,1,'2017-02-02 17:13:02',NULL,2,30),(11,'Ética profesional','',1,1,'2017-02-02 17:13:22',NULL,2,25),(12,'Valor social','',1,1,'2017-02-02 17:13:37',NULL,2,25),(13,'Norma','',1,1,'2017-02-02 17:13:53',NULL,2,25),(14,'Reglamento interno','',1,1,'2017-02-02 17:14:20',NULL,2,30),(15,'Indicador INAI','',1,1,'2017-02-02 17:14:40',NULL,2,40),(16,'Legalidad y normatividad','',1,1,'2017-02-02 17:14:59',NULL,2,40),(17,'Adaptabilidad ','',1,1,'2017-02-02 17:16:12',NULL,2,40),(18,'Justicia','',1,1,'2017-02-02 17:16:35',NULL,2,50),(19,'Imparcialidad','',1,1,'2017-02-02 17:16:51',NULL,2,45),(20,'Transparencia','',1,1,'2017-02-02 17:17:04',NULL,2,60),(21,'Rendición de cuentas','',1,1,'2017-02-02 17:17:31',NULL,2,60),(22,'Integridad','',1,1,'2017-02-02 17:17:46',NULL,2,50),(23,'Atención al beneficiario','',1,1,'2017-02-02 17:18:09',NULL,2,45),(24,'Entendimiento','',1,1,'2017-02-02 17:18:25',NULL,2,45),(25,'Trabajo bajo presión','',1,1,'2017-02-02 17:19:20',NULL,2,10),(26,'Toma de decisiones ','',1,1,'2017-02-02 17:19:36',NULL,2,15),(27,'Responsabilidad ','',1,1,'2017-02-02 17:20:05',NULL,2,20),(28,'Digitales','',1,1,'2017-02-02 17:20:23',NULL,2,10),(29,'Elaboración de documentos','',1,1,'2017-02-02 17:21:53',NULL,2,20),(30,'Administración','',1,1,'2017-02-02 17:22:48',NULL,2,10),(31,'Conocimientos de SISI','',1,1,'2017-02-02 17:23:22',NULL,2,5),(32,'Análisis','',1,1,'2017-02-02 17:23:42',NULL,2,5),(33,'Comunicación','',1,1,'2017-02-02 17:23:59',NULL,2,100),(34,'Igualdad de género','',1,1,'2017-02-02 17:24:14',NULL,2,90),(35,'Liderazgo','',1,1,'2017-02-02 17:24:41',NULL,2,95),(36,'Ética personal','',1,1,'2017-02-02 17:25:00',NULL,2,85),(37,'Adaptación al cambio','',1,1,'2017-02-02 17:25:19',NULL,2,100),(38,'Respeto','',1,1,'2017-02-02 17:25:32',NULL,2,85),(39,'Resiliencia laboral','',1,1,'2017-02-02 17:27:18',NULL,2,100),(40,'Enfoque al servicio','',1,1,'2017-02-02 17:27:44',NULL,2,100);

--
-- Dumping data for table `cat_competencias_plan`
--

INSERT INTO `cat_competencias_plan` VALUES (1,'Obligatorio','obligatorio man!',0,1,'2016-08-15 17:59:33','2016-10-26 17:58:34',1);

--
-- Dumping data for table `cat_dificultad_oa`
--

INSERT INTO `cat_dificultad_oa` VALUES (1,'Sencillo','Sencillo','2017-02-22 13:58:09',1,NULL,'2017-02-22 16:01:47',2),(2,'Muy sencillo','Muy sencillo','2017-02-22 13:58:09',1,NULL,NULL,NULL),(3,'Medio','Medio','2017-02-22 13:58:09',1,NULL,NULL,NULL),(4,'Complejo','Complejo','2017-02-22 13:58:09',1,NULL,NULL,NULL),(5,'Muy complejo','Muy complejo','2017-02-22 13:58:09',1,NULL,NULL,NULL);

--
-- Dumping data for table `cat_distribucion_area`
--

INSERT INTO `cat_distribucion_area` VALUES (1,'Auditorio',NULL,1,NULL,'2017-02-20 17:32:05',NULL,1),(2,'Forma de Escuela',NULL,1,NULL,'2017-02-20 17:32:05',NULL,1),(3,'Herradura',NULL,1,NULL,'2017-02-20 17:32:05',NULL,1),(4,'Mesa Redonda',NULL,1,NULL,'2017-02-20 17:32:05',NULL,1),(5,'Mesa Rusa',NULL,1,NULL,'2017-02-20 17:32:05',NULL,1);

--
-- Dumping data for table `cat_documentos_expide_plan`
--

INSERT INTO `cat_documentos_expide_plan` VALUES (1,'Certificado','Certificado',1,1,'2016-08-17 17:24:01','2016-09-30 15:57:58',2),(2,'Constancia','Constancia',1,2,'2016-08-19 16:10:11','2016-08-29 10:42:16',2),(3,'Diploma','Diploma',1,3,'2016-09-30 15:25:26',NULL,2),(4,'Reconocimiento','Reconocimiento',1,4,'2016-09-30 15:25:26',NULL,2),(5,'gdgdgf','dfgdf',0,2,'2017-02-15 13:34:31',NULL,2);

--
-- Dumping data for table `cat_encuesta_contexto`
--
INSERT INTO `cat_encuesta_contexto` VALUES (1,'DNC','encuestas tipo sistema','2016-11-16 13:04:26','2017-03-09 13:00:32',2,1,1),(3,'Kirkpatrick','Encuestas de de tipo de objeto de aprendizaje','2016-11-16 13:06:25','2017-03-09 13:00:53',2,1,2),(4,'Contenidos','Se evalua el contenido de los objetos de aprendizaje','2016-11-16 14:25:13','2016-12-06 10:39:08',2,0,3),(5,'Test ONE','Tipo de contexto relacionado al contenido del sistema.','2016-11-29 16:40:21','2016-12-05 16:31:44',2,0,1),(6,'Kirkpatrick','metodoloagia kirkpatrick','2017-03-17 16:22:51',NULL,2,0,5);
--
-- Dumping data for table `cat_encuesta_estatus`
--

INSERT INTO `cat_encuesta_estatus` VALUES (1,'Borrador','Indicativo que la encuestas aun no ha sido revisada y por lo tanto tampoco aprobada','2016-11-16 14:24:25','2017-03-09 12:38:26',2,1,1),(2,'Con comentarios','Con comentarios','2016-11-29 16:41:35','2017-03-09 12:44:48',2,1,2),(3,'En Revisión','En Revisión','2016-11-29 16:41:36','2017-03-09 12:42:49',2,1,3),(4,'A publicar','A publicar','2016-11-29 16:41:36','2017-03-09 12:49:48',2,1,4),(7,'Evaluacion Continua','evaluacion contina','2017-03-17 16:24:27',NULL,2,0,5);

--
-- Dumping data for table `cat_encuesta_objetivo`
--

INSERT INTO `cat_encuesta_objetivo` VALUES (1,'Alumnos','Personal inscrito en el evento','2016-11-22 14:21:51',NULL,4,1,1),(2,'Profesores','dirigido a profesores','2016-11-28 15:03:58',NULL,2,1,1),(3,'Facilitadores','Dirigido a profesores en general.','2016-11-29 16:47:29','2017-03-17 16:25:32',2,1,1),(4,'Alumnos Internos','Dirigido a alumnos que pertenecen a la dependencia.','2016-11-29 16:47:30',NULL,1,1,2),(5,'Alumnos Externos','Dirigido a alumnos con matricula de externo.','2016-11-29 16:47:31',NULL,1,1,3),(6,'Coordinador','Coordinador del curso','2016-11-29 16:47:31',NULL,1,1,4),(7,'Supervisor','Nivel de supervisor','2016-11-29 16:47:31',NULL,1,1,5),(8,'Borrar','borrar','2016-11-29 16:47:31',NULL,1,1,7);

--
-- Dumping data for table `cat_encuesta_tipo`
--

INSERT INTO `cat_encuesta_tipo` VALUES (1,4,'Aprendizaje','evaluación continua','2016-11-22 14:22:34',NULL,4,1,1),(3,1,'Pruebas Comportamiento','Encuesta relacionada al comportamiento.','2016-11-29 17:44:44',NULL,1,1,2),(4,1,'Pruebas Comportamiento2','Encuesta relacionada al comportamiento.','2016-11-29 18:00:09',NULL,1,1,2),(5,1,'Pruebas Comportamiento3','Encuesta relacionada al comportamiento.','2016-11-29 18:10:21',NULL,1,1,2),(6,1,'Pruebas Comportamiento3','Encuesta relacionada al comportamiento.','2016-11-30 12:21:13',NULL,1,1,2),(7,1,'Pruebas C','Encuesta relacionada al comportamiento.','2016-11-30 12:34:08','2017-03-17 16:26:08',2,0,2),(8,1,'Pruebas Comportamiento3','Encuesta relacionada al comportamiento.','2016-11-30 12:43:04','2016-12-05 16:31:00',2,0,2),(9,1,'Tipo encuesta web 2','Tipo encuesta web 2','2016-11-30 13:26:54','2016-12-05 16:31:14',2,0,5),(10,5,'tipo encuesta web 05122016','tipo encuesta web 05122016','2016-12-05 16:30:24',NULL,2,0,3),(11,3,'Reacción','Este tipo encuesta será asignado en los EC','2017-01-17 10:20:50',NULL,2,1,1),(12,3,'Aprendizaje','Este tipo encuesta será asignado en los EC despues de 2 meses','2017-01-17 10:21:57',NULL,2,1,0),(13,3,'Resultados','Este tipo encuesta será asignado en los EC despues de 6 meses','2017-01-17 10:22:21',NULL,2,1,1),(14,3,'Comportamiento','','2017-01-17 10:22:44',NULL,2,1,1);


--
-- Dumping data for table `cat_paises`
--

INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AC','Isla Ascension','AC','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AD','Andorra','AD','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AE','Emiratos arabes Unidos','AE','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AF','Afganistan','AF','2016-06-14 17:19:29',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AG','Antigua y Barbuda','AG','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AI','Anguila','AI','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AL','Albania','AL','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AM','Armenia','AM','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AN','Antillas holandesas','AN','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AO','Angola','AO','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AQ','Antartida','AQ','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AR','Argentina','AR','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AS','Samoa Americana','AS','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AT','Austria','AT','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AU','Australia','AU','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AW','Aruba','AW','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('AZ','Azerbaiyan','AZ','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BA','Bosnia y Herzegovina','BA','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BB','Barbados','BB','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BD','Bangladesh','BD','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BE','Belgica','BE','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BF','Burkina Faso','BF','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BG','Bulgaria','BG','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BH','Bahrein','BH','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BI','Burundi','BI','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BJ','Benin','BJ','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BM','Bermudas','BM','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BN','Brunei','BN','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BO','Bolivia','BO','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BR','Brasil','BR','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BS','Bahamas','BS','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BT','Bhutan','BT','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BV','Isla Bouvet','BV','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BW','Botsuana','BW','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BY','Bielorrusia','BY','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('BZ','Belice','BZ','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CA','Canada','CA','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CC','Islas Cocos','CC','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CD','Congo (RDC)','CD','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CF','Republica Centroafricana','CF','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CG','Congo','CG','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CH','Suiza','CH','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CI','Costa del Marfil','CI','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CK','Islas Cook','CK','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CL','Chile','CL','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CM','Camerun','CM','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CN','China','CN','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CO','Colombia','CO','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CR','Costa Rica','CR','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CU','Cuba','CU','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CV','Cabo Verde','CV','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CX','Isla Christmas','CX','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CY','Chipre','CY','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('CZ','Republica Checa','CZ','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('DE','Alemania','DE','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('DJ','Djibouri','DJ','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('DK','Dinamarca','DK','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('DM','Dominica','DM','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('DO','Republica Dominicana','DO','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('DZ','Argelia','DZ','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('EC','Ecuador','EC','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('EE','Estonia','EE','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('EG','Egipto','EG','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('ER','Eritrea','ER','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('ES','España','ES','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('ET','Etiopia','ET','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('EX','Extranjero','EX','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('FI','Finlandia','FI','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('FJ','Islas Fiji','FJ','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('FK','Islas Malvinas (Falkland Islands)','FK','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('FM','Micronesia','FM','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('FO','Islas Feroe','FO','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('FR','Francia','FR','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GA','Gabon','GA','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GD','Granada','GD','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GE','Georgia','GE','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GF','Guayana Francesa','GF','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GG','Guernsey','GG','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GH','Ghana','GH','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GI','Gibraltar','GI','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GL','Groenlandia','GL','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GM','Gambia','GM','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GN','Guinea','GN','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GP','Guadalupe','GP','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GQ','Guinea Ecuatorial','GQ','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GR','Grecia','GR','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GS','Georgia del Sur y las islas Sandwich del Sur','GS','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GT','Guatemala','GT','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GU','Guam','GU','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GW','Guinea-Bissau','GW','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('GY','Guayana','GY','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('HK','Hong Kong, ZAE','HK','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('HM','Islas Heard y McDonald','HM','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('HN','Honduras','HN','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('HR','Croacia (Hrvatska)','HR','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('HT','Haiti','HT','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('HU','Hungria','HU','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('ID','Indonesia','ID','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IE','Irlanda','IE','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IL','Israel','IL','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IM','Isla de Man','IM','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IN','India','IN','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IO','Territorio Britanico del Oceano &#205;ndico','IO','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IQ','Irak','IQ','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IR','Iran','IR','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IS','Islandia','IS','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('IT','Italia','IT','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('JE','Jersey','JE','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('JM','Jamaica','JM','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('JO','Jordania','JO','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('JP','Japon','JP','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KE','Kenia','KE','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KG','Kirguizistan','KG','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KH','Camboya','KH','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KI','Kiribati','KI','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KM','Comores','KM','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KN','Saint Kitts y Nevis','KN','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KP','Corea del Norte','KP','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KR','Corea','KR','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KW','Kuwait','KW','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KY','Islas Caiman','KY','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('KZ','Kazajstan','KZ','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LA','Laos','LA','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LB','Libano','LB','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LC','Santa Lucia','LC','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LI','Liechtenstein','LI','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LK','Sri Lanka','LK','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LR','Liberia','LR','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LS','Lesoto','LS','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LT','Lituania','LT','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LU','Luxemburgo','LU','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LV','Letonia','LV','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('LY','Libia','LY','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MA','Marruecos','MA','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MC','Monaco','MC','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MD','Moldavia','MD','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MG','Madagascar','MG','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MH','Islas Marshall','MH','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MK','Ex-Republica Yugoslava de Macedonia','MK','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('ML','Mali','ML','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MM','Birmania','MM','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MN','Mongolia','MN','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MO','Macao, ZAE','MO','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MP','Islas Marianas del Norte','MP','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MQ','Martinica','MQ','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MR','Mauritania','MR','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MS','Montserrat','MS','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MT','Malta','MT','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MU','Mauricio','MU','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MV','Maldivas','MV','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MW','Malawi','MW','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MX','México','MX','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MY','Malaisia','MY','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('MZ','Mozambique','MZ','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NA','Namibia','NA','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NC','Nueva Caledonia','NC','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NE','Niger','NE','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NF','Isla Norfolk','NF','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NG','Nigeria','NG','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NI','Nicaragua','NI','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NL','Holanda','NL','2016-06-14 17:23:12',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NO','Noruega','NO','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NP','Nepal','NP','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NR','Nauru','NR','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NU','Niue','NU','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('NZ','Nueva Zelanda','NZ','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('OM','Oman','OM','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PA','Panama','PA','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PE','Peru','PE','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PF','Polinesia Francesa','PF','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PG','Papua-Nueva Guinea','PG','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PH','Filipinas','PH','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PK','Pakistan','PK','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PL','Polonia','PL','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PM','San Pedro y Miquelon','PM','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PN','Islas Pitcairn','PN','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PR','Puerto Rico','PR','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PS','Autoridad Palestina','PS','2016-06-14 17:23:08',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PT','Portugal','PT','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PW','Palaos','PW','2016-06-14 17:23:16',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('PY','Paraguay','PY','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('QA','Qatar','QA','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('RE','Reunion','RE','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('RO','Rumania','RO','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('RU','Rusia','RU','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('RW','Ruanda','RW','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SA','Arabia Saudi','SA','2016-06-14 17:23:07',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SB','Islas Salomon','SB','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SC','Seychelles','SC','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SD','Sudan','SD','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SE','Suecia','SE','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SG','Singapur','SG','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SH','Santa Elena','SH','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SI','Eslovenia','SI','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SJ','Islas Svalbard y Jan Mayen','SJ','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SK','Eslovaquia','SK','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SM','San Marino','SM','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SN','Senegal','SN','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SO','Somalia','SO','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SR','Surinam','SR','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('ST','Santo Tome y Principe','ST','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SV','El Salvador','SV','2016-06-14 17:23:10',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SY','Siria','SY','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('SZ','Suazilandia','SZ','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TA','Tristan da Cunha','TA','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TC','Islas Turks y Caicos','TC','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TD','Chad','TD','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TF','Tierras Australes y Antarticas Francesas','TF','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TG','Togo','TG','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TH','Tailandia','TH','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TJ','Tayikistan','TJ','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TK','Tokelau','TK','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TM','Turkmenistan','TM','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TN','Tunez','TN','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TO','Tonga','TO','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TP','Timor Oriental','TP','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TR','Turquia','TR','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TT','Trinidad y Tobago','TT','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TV','Tuvalu','TV','2016-06-14 17:23:20',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TW','Taiwan','TW','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('TZ','Tanzania','TZ','2016-06-14 17:23:19',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('UA','Ucrania','UA','2016-06-14 17:23:20',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('UG','Uganda','UG','2016-06-14 17:23:20',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('UK','Reino Unido','UK','2016-06-14 17:23:17',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('UM','Islas perifericas menores de los Estados Unidos','UM','2016-06-14 17:23:13',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('US','Estados Unidos','US','2016-06-14 17:23:11',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('UY','Uruguay','UY','2016-06-14 17:23:20',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('UZ','Uzbekistan','UZ','2016-06-14 17:23:20',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('VA','Ciudad estado del Vaticano (Santa Sede)','VA','2016-06-14 17:23:09',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('VC','San Vicente y las Granadinas','VC','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('VE','Venezuela','VE','2016-06-14 17:23:20',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('VG','Islas Virgenes Britanicas','VG','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('VI','Islas Virgenes, EE.UU.','VI','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('VU','Vanuatu','VU','2016-06-14 17:23:20',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('WF','Islas Wallis y Futuna','WF','2016-06-14 17:23:14',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('WS','Samoa','WS','2016-06-14 17:23:18',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('YT','Mayotte','YT','2016-06-14 17:23:15',NULL,1,1,NULL);
INSERT INTO `cat_paises` (`id_pais`,`nombre`,`abreviatura`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`,`nacionalidad`) VALUES ('ZA','Republica de Sudafrica','ZA','2016-06-14 17:23:17',NULL,1,1,NULL);


--
-- Dumping data for table `cat_entidades_federativas`
--

INSERT INTO `cat_entidades_federativas` VALUES (1,'Aguascalientes','Ags','AS','MX','2016-06-14 17:48:11',NULL,1,1),(2,'Baja California','BC','BC','MX','2016-06-14 17:52:35',NULL,1,1),(3,'Baja California Sur','BCS','BS','MX','2016-06-14 17:52:35',NULL,1,1),(4,'Campeche','Camp','CC','MX','2016-06-14 17:52:35',NULL,1,1),(5,'Coahuila','Coah','CL','MX','2016-06-14 17:52:35',NULL,1,1),(6,'Colima','Col','CM','MX','2016-06-14 17:52:35',NULL,1,1),(7,'Chiapas','Chis','CS','MX','2016-06-14 17:52:35',NULL,1,1),(8,'Chihuahua','Chih','CH','MX','2016-06-14 17:52:35',NULL,1,1),(9,'Ciudad de México','CDMX','DF','MX','2016-06-14 17:52:35',NULL,1,1),(10,'Durango','Dgo','DG','MX','2016-06-14 17:52:35',NULL,1,1),(11,'Guanajuato','Gto','GT','MX','2016-06-14 17:52:35',NULL,1,1),(12,'Guerrero','Gro','GR','MX','2016-06-14 17:52:35',NULL,1,1),(13,'Hidalgo','Hgo','HG','MX','2016-06-14 17:52:35',NULL,1,1),(14,'Jalisco','Jal','JC','MX','2016-06-14 17:52:36',NULL,1,1),(15,'México','Mex','MC','MX','2016-06-14 17:52:36',NULL,1,1),(16,'Michoacán','Mich','MN','MX','2016-06-14 17:52:36',NULL,1,1),(17,'Morelos','Mor','MS','MX','2016-06-14 17:52:36',NULL,1,1),(18,'Nayarit','Nay','NT','MX','2016-06-14 17:52:36',NULL,1,1),(19,'Nuevo León','NL','NL','MX','2016-06-14 17:52:36',NULL,1,1),(20,'Oaxaca','Oax','OC','MX','2016-06-14 17:52:36',NULL,1,1),(21,'Puebla','Pue','PL','MX','2016-06-14 17:52:36',NULL,1,1),(22,'Querétaro','Qro','QT','MX','2016-06-14 17:52:36',NULL,1,1),(23,'Quintana Roo','Q Roo','QR','MX','2016-06-14 17:52:36',NULL,1,1),(24,'San Luis Potosí','SLP','SP','MX','2016-06-14 17:52:36',NULL,1,1),(25,'Sinaloa','Sin','SL','MX','2016-06-14 17:52:36',NULL,1,1),(26,'Sonora','Son','SR','MX','2016-06-14 17:52:36',NULL,1,1),(27,'Tabasco','Tab','TC','MX','2016-06-14 17:52:36',NULL,1,1),(28,'Tamaulipas','Tamps','TS','MX','2016-06-14 17:52:36',NULL,1,1),(29,'Tlaxcala','Tlax','TL','MX','2016-06-14 17:52:36',NULL,1,1),(30,'Veracruz','Ver','VZ','MX','2016-06-14 17:52:36',NULL,1,1),(31,'Yucatán','Yuc','YN','MX','2016-06-14 17:52:36',NULL,1,1),(32,'Zacatecas','Zac','ZS','MX','2016-06-14 17:52:36',NULL,1,1),(33,'Extranjero','Ext','TX','EX','2016-06-14 17:52:36',NULL,1,1);

--
-- Dumping data for table `cat_estado_ava`
--
INSERT INTO `cat_estado_ava` (`id`,`nombre`,`descripcion`,`fecha_registro`,`activo`,`orden`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,'En solicitud','En solicitud','2017-02-20 16:02:56',1,NULL,NULL,1);
INSERT INTO `cat_estado_ava` (`id`,`nombre`,`descripcion`,`fecha_registro`,`activo`,`orden`,`fecha_actualizacion`,`usuario_modifico`) VALUES (2,'En construccion','En construccion','2017-02-20 16:02:56',1,NULL,NULL,1);
INSERT INTO `cat_estado_ava` (`id`,`nombre`,`descripcion`,`fecha_registro`,`activo`,`orden`,`fecha_actualizacion`,`usuario_modifico`) VALUES (3,'Activo','Activo','2017-02-20 16:02:56',1,NULL,NULL,1);

--  
-- Dumping data for table `cat_estado_evento_capacitacion`
--

INSERT INTO `cat_estado_evento_capacitacion` VALUES (1,'Calendarizado','Calendaizado',1,NULL,'2017-02-13 17:43:14','2017-02-13 17:43:14',1),(2,'En ejecución','En ejecución',1,NULL,'2017-02-13 17:43:14','2017-02-13 17:43:14',1),(3,'Concluidos','Concluidos',1,NULL,'2017-02-13 17:43:14','2017-02-13 17:43:14',1),(4,'Bloqueados','Bloqueados',1,NULL,'2017-02-13 17:43:14','2017-02-13 17:43:14',1),(5,'Cancelado','Cancelado',1,NULL,'2017-02-13 17:43:14','2017-02-13 17:43:14',1);

--
-- Dumping data for table `cat_estatus_oa`
--

INSERT INTO `cat_estatus_oa` VALUES (1,'Borrador','Borrador','2017-02-22 14:06:05',1,NULL,'2017-02-22 16:05:41',2),(2,'Revisado','Revisado','2017-02-22 14:06:05',1,NULL,NULL,NULL),(3,'Final','Final','2017-02-22 14:06:05',1,NULL,NULL,NULL),(4,'No disponible','No disponible','2017-02-22 14:06:05',1,NULL,NULL,NULL);

--
-- Dumping data for table `cat_estatus_plan`
--

INSERT INTO `cat_estatus_plan` VALUES (1,'Desarrollo','Desarrollo',1,1,'2016-08-19 16:18:05',NULL,1),(2,'Cancelado','Cancelado',1,1,'2016-08-19 16:20:04',NULL,1),(3,'Ejecución','Estado de ejecución',1,1,'2016-08-19 16:22:57',NULL,1),(4,'Cerrado','Estado cerrado',1,1,'2016-08-19 16:23:40',NULL,1),(5,'prueba6','prueba desc66',0,5,'2016-08-23 16:37:57','2016-08-30 10:35:14',2),(6,'test update23','test update desc23',0,3,'2016-08-26 12:13:57','2016-08-26 16:15:49',2),(7,'test cuatro','test desc cuatro',0,2,'2016-08-30 10:38:12',NULL,2);

--
-- Dumping data for table `cat_estatus_reservacion`
--

INSERT INTO `cat_estatus_reservacion` VALUES (1,'Sin Reservación',NULL,1,NULL,'2017-03-08 11:39:26',NULL,1),(2,'Reservado - Sin Aprobar',NULL,1,NULL,'2017-03-08 11:39:26',NULL,1),(3,'Reservado - Aprobado',NULL,1,NULL,'2017-03-08 11:39:26',NULL,1),(4,'Aprobado y con reservaciones',NULL,1,NULL,'2017-03-08 11:39:26',NULL,1);

--
-- Dumping data for table `cat_formato_oa`
--

INSERT INTO `cat_formato_oa` VALUES (1,'application/excel','application/excel','2017-02-22 14:08:31',1,NULL,'2017-02-22 16:14:08',2),(2,'application/mspowerpoint','application/mspowerpoint','2017-02-22 14:08:31',1,NULL,NULL,NULL),(3,'text/html','text/html','2017-02-22 14:08:31',1,NULL,NULL,NULL),(4,'video/x-ms-asf','video/x-ms-asf','2017-02-22 14:08:31',1,NULL,NULL,NULL),(5,'video/x-mpeg','video/x-mpeg','2017-02-22 14:08:31',1,NULL,NULL,NULL);

--
-- Dumping data for table `cat_habilidades_plan`
--

INSERT INTO `cat_habilidades_plan` VALUES (1,'Autoconocimiento','Autoconocimiento',1,NULL,'2016-12-27 13:02:46',NULL,1),(2,'Empatía','Empatía',1,NULL,'2016-12-27 13:02:46',NULL,1),(3,'Comunicación asertiva','Comunicación asertiva',1,NULL,'2016-12-27 13:02:47',NULL,1);

--
-- Dumping data for table `cat_idioma_oa`
--

INSERT INTO `cat_idioma_oa` VALUES (1,'Español','Español','2017-02-22 14:07:33',1,NULL,'2017-02-22 17:15:15',2),(2,'Ingles','Ingles','2017-02-22 14:07:33',1,NULL,NULL,NULL);

--
-- Dumping data for table `cat_institucion_certificadora`
--

INSERT INTO `cat_institucion_certificadora` VALUES (1,'institucion 1','description institution','2017-02-02 18:13:23',1,NULL,'2017-02-02 18:13:23',1),(2,'institucion 2','description institution2','2017-02-02 18:13:23',1,NULL,'2017-02-02 18:13:23',1),(3,'institucion 3','description institution3','2017-02-02 18:13:23',1,NULL,'2017-02-02 18:13:23',1),(4,'institucion 4','description institution4','2017-02-02 18:13:23',1,NULL,'2017-02-02 18:13:23',1),(5,'institucion 5','description institution5','2017-02-02 18:13:23',1,NULL,'2017-02-02 18:13:23',1);

--
-- Dumping data for table `cat_material_didactico`
--

INSERT INTO `cat_material_didactico` VALUES (2,'Diagramas',NULL,'2017-01-19 17:04:43',1,NULL,NULL,1),(3,'Exámenes',NULL,'2017-01-19 17:04:43',1,NULL,NULL,1),(4,'Ejercicios',NULL,'2017-01-19 17:04:43',1,NULL,NULL,1),(5,'Experimentos',NULL,'2017-01-19 17:04:43',1,NULL,NULL,1),(6,'Diapositiva',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(7,'Figuras',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(8,'Gráficos',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(9,'Índice','','2017-01-19 17:04:44',1,NULL,NULL,1),(10,'Texto narrativo',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(11,'Tabla',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(12,'Planteamiento de problema',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(13,'Cuestionario',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(14,'Autoevaluación',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1),(15,'Simulación',NULL,'2017-01-19 17:04:44',1,NULL,NULL,1);

--
-- Dumping data for table `cat_medios_acceso`
--

INSERT INTO `cat_medios_acceso` VALUES (1,'local','acceso local','2016-07-12 13:54:40',NULL,1,1);

--
-- Dumping data for table `cat_modalidad_plan_programas`
--

INSERT INTO `cat_modalidad_plan_programas` (`id`,`nombre`,`descripcion`,`activo`,`orden`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,'En linea','En linea',1,1,'2016-08-15 17:19:37','2016-10-26 17:58:34',1);
INSERT INTO `cat_modalidad_plan_programas` (`id`,`nombre`,`descripcion`,`activo`,`orden`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (4,'Presencial','Presencial',1,2,'2016-08-15 17:19:37',NULL,1);
INSERT INTO `cat_modalidad_plan_programas` (`id`,`nombre`,`descripcion`,`activo`,`orden`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (5,'Mixto','Mixto',1,3,'2016-08-15 17:19:37',NULL,1);

--
-- Dumping data for table `cat_municipios`
--


INSERT INTO `cat_municipios` (`id_municipio`,`nombre`,`id_entidad_federativa`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('1001','Aguascalientes',1,'2016-06-15 10:34:46','2016-08-22 13:11:59',1,1);
INSERT INTO `cat_municipios` (`id_municipio`,`nombre`,`id_entidad_federativa`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('10001','Canatlán',10,'2016-06-15 10:34:45',NULL,1,1);
INSERT INTO `cat_municipios` (`id_municipio`,`nombre`,`id_entidad_federativa`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('29005','Apizaco',29,'2016-06-15 10:36:07',NULL,1,1);


--
-- Dumping data for table `cat_nivel_conocimiento`
--

INSERT INTO `cat_nivel_conocimiento` VALUES (1,'Basico',NULL,1,1,'2016-08-19 17:02:17',NULL,1),(2,'Intermedio',NULL,1,1,'2016-08-19 17:02:17',NULL,1),(3,'Avanzado',NULL,1,1,'2016-08-19 17:02:17',NULL,1);


--
-- Dumping data for table `cat_nivel_ensenanza_programa`
--

INSERT INTO `cat_nivel_ensenanza_programa` VALUES (1,'Actualización',NULL,1,1,'2016-08-19 17:00:42',NULL,1),(2,'Inducción',NULL,1,1,'2016-08-19 17:00:56',NULL,1),(3,'Formación',NULL,1,1,'2016-08-19 17:01:07',NULL,1),(4,'Especialización',NULL,1,1,'2016-08-19 17:01:07',NULL,1);

--
-- Dumping data for table `cat_objeto_curricular`
--

INSERT INTO `cat_objeto_curricular` VALUES (2,'Plan','objeto curricular del tipo plan','2016-09-12 17:58:41',1,NULL,NULL,1),(3,'Estructura','objeto curricular de tipo estructura','2016-09-12 18:00:22',1,NULL,NULL,1),(4,'SubEstructura','objeto curricular de tipo sub-estructura','2016-09-12 18:01:49',1,NULL,NULL,1),(5,'Programa','objeto curricular de tipo programa','2016-09-12 18:02:44',1,NULL,NULL,1);


--
-- Dumping data for table `cat_parametros_sistema`
--

INSERT INTO `cat_parametros_sistema` VALUES ('NOMBRE_FOTO_COMUN','Usuario.png','2017-02-17 13:13:58','2017-02-17 13:14:19',39),('RUTA_ADJUNTOS','general/archivos_adjuntos','2014-12-24 11:13:28','2016-11-16 16:11:04',2),('RUTA_AREAS','areas/','2017-02-23 12:48:35',NULL,2),('RUTA_CARGA_MASIVA','general/cargar_masiva','2016-11-03 17:23:08','2016-11-16 16:11:12',2),('RUTA_FOTOS_USUARIOS','usuarios/','2017-02-17 13:13:23','2017-02-17 13:14:44',39),('RUTA_PRINCIPAL','/SISI/elearning/plataformaAprendizaje/','2016-11-03 13:41:41','2016-11-17 08:36:04',2),('RUTA_RECURSOS','mod_administracion/temas/recursos/','2016-11-03 17:03:40','2016-11-16 16:10:46',2),('RUTA_UNDERTOW','/recursos/','2017-02-17 13:30:14',NULL,39);

--
-- Dumping data for table `cat_parametros_wsmoodle`
--

INSERT INTO `cat_parametros_wsmoodle` (id_parametro_wsmoodle, host, path, service, username, password, outh, server, activo, fecha_registro, nombre) VALUES (1,'http://189.206.122.67/sedesol/plataformaElearning','/oauth/token','wstemplate','admin','elearning','/login/token.php','/webservice/rest/server.php', 1, CURDATE(), 'Moodle1');

--
-- Dumping data for table `cat_puestos_sedesol_ec`
--

INSERT INTO `cat_puestos_sedesol_ec` VALUES (1,'Directores',NULL,'2016-10-20 00:00:00',1,NULL,NULL,1),(2,'Subdirectores',NULL,'2016-10-20 00:00:00',1,NULL,NULL,1),(3,'Programadores',NULL,'2016-10-20 00:00:00',1,NULL,NULL,1);

--
-- Dumping data for table `cat_rama_especialidad_plan_programas`
--
INSERT INTO `cat_rama_especialidad_plan_programas` VALUES (1,'Matematicas Avanzadas I','Rama_especialidad_test_update',1,1,'2016-08-15 16:56:10','2016-12-01 13:26:39',1);

--
-- Dumping data for table `cat_roles`
--

INSERT INTO `cat_roles` VALUES (1,'Administrador GENERAL','ROLE_ADMIN',2,'2014-12-24 11:13:28','2017-03-07 13:19:49',1),(2,'Alumno','ROLE_ALUMNO',39,'2014-12-24 11:13:28','2017-03-07 08:22:04',1),(4,'Instructor','ROLE_DOCENTE',2,'2016-07-26 16:54:39','2017-03-07 11:03:55',1),(5,'Revisor general','cve_revisor_general',39,'2016-11-29 20:18:17','2016-12-23 12:38:42',0),(6,'Facilitador','Facilitador_',39,'2017-01-05 15:32:15','2017-03-07 09:10:34',1),(7,'Personal autorizado','pruebael',2,'2017-01-05 15:33:05','2017-03-07 11:01:58',1),(8,'COORDINADOR_CAPACITACION','ROLE_COORDINADOR_CAPACITACION',39,'2017-01-06 11:50:12','2017-03-07 08:22:11',1),(9,'SUPERVISOR','ROLE_SUPERVISOR',39,'2017-01-06 14:32:54','2017-03-07 09:10:44',1),(10,'SUPERVIDOR_PROGRAMAS','ROLE_SUPERVIDOR_PROGRAMAS',39,'2017-01-06 14:34:31','2017-01-18 17:00:10',1),(11,'DESARROLLADOR_OAS','ROLE_DESARROLLADOR_OAS',4,'2017-01-06 14:35:57',NULL,1),(12,'FACILITADOR','ROLE_FACILITADOR',4,'2017-01-06 14:37:31',NULL,1),(13,'ESTUDIANTE','ROLE_ESTUDIANTE',4,'2017-01-06 14:38:30',NULL,1),(14,'Gerente ','GERENTE',39,'2017-01-16 16:12:02','2017-01-16 16:15:15',1),(15,'Facilitador_Activo','Facilitador_nuevo',5,'2017-01-17 11:41:09','2017-01-17 11:46:56',1),(16,'Alumno revisión 2','alumno_prueba',4,'2017-01-17 13:50:30','2017-01-17 13:54:22',1),(17,'revisor_encuestas','revisor_encuestas',2,'2017-03-03 13:28:26',NULL,1),(18,'revisor_encuesta2','revisor_encuestas2',2,'2017-03-03 13:42:14','2017-03-03 13:42:47',1),(19,'Lider proyecto','LIDER',2,'2017-03-07 00:25:20',NULL,1),(20,'Gerente','1234',2,'2017-03-07 10:10:54',NULL,1),(21,'Admin','Prueba',2,'2017-03-07 16:50:06','2017-03-07 16:50:41',1),(22,'Superuser','423543',2,'2017-03-09 10:42:24',NULL,1);


--
-- Dumping data for table `cat_status_programa`
--

INSERT INTO `cat_status_programa` VALUES (1,'Borrador','Borrador',1,1,'2016-08-19 16:45:05',NULL,1),(2,'Final','Final',1,2,'2016-08-19 16:47:42',NULL,1),(3,'Cancelado','Cancelado',1,3,'2016-08-19 16:50:01','2016-08-29 10:42:45',2),(4,'Bloqueado','Bloqueado',1,4,'2016-10-19 00:00:00',NULL,1);

--
-- Dumping data for table `cat_tecnicas_didacticas_programa`
--

INSERT INTO `cat_tecnicas_didacticas_programa` VALUES (1,'Técnica Expositiva',NULL,1,1,'2016-08-19 16:52:05',NULL,1),(2,'Aprendizaje basado en problemas',NULL,1,1,'2016-08-19 16:52:37',NULL,1),(3,'Aprendizaje colaborativo',NULL,1,1,'2016-08-19 16:52:51',NULL,1),(4,'Orientado a proyectos',NULL,1,1,'2016-08-19 16:53:07',NULL,1);

--
-- Dumping data for table `cat_tipo_evento_ec`
--

INSERT INTO `cat_tipo_evento_ec` VALUES (1,'Curso','Centrado en el análisis de aspectos teóricos en un área determinada de conocimiento.','2016-10-19 00:00:00',1,NULL,NULL,1),(2,'Taller','Evento de capacitación centrado en la vinculación teoría-práctica y con la elaboración de un producto final','2016-10-19 00:00:00',1,NULL,NULL,1),(3,'Seminario','Centrado en la investigación previa al análisis y síntesis de aspectos teóricos relacionados con el objeto de estudio','2016-10-19 00:00:00',1,NULL,NULL,1);


--
-- Dumping data for table `cat_tipo_plan`
--

INSERT INTO `cat_tipo_plan` VALUES (1,'Publico','Plan Publico',1,1,'2016-08-17 11:27:00','2017-02-01 16:48:32',2),(2,'Normalizado','Normalizado',1,1,'2016-08-19 16:11:54',NULL,1),(3,'Encuestadores','Tipo plan encuestadores',1,1,'2016-08-19 16:12:44',NULL,1),(4,'Educación Continua','Educación Continua',1,2,'2016-08-23 17:32:12','2016-08-26 11:46:29',2),(5,'Capacitación','Capacitación',1,1,'2016-08-26 16:34:52','2016-08-29 11:04:40',2),(6,'test tipo plan insert','test desc tipo plan',0,1,'2016-08-29 10:51:49','2016-08-29 11:04:58',2);

--
-- Dumping data for table `cat_tipo_recurso`
--

INSERT INTO `cat_tipo_recurso` VALUES (1,'Tecnologicos',NULL,1,NULL,'2017-02-20 17:29:11',NULL,1),(2,'Mobiliario',NULL,1,NULL,'2017-02-20 17:29:11',NULL,1);


--
-- Dumping data for table `cat_recursos_infraestructura`
--

INSERT INTO `cat_recursos_infraestructura` VALUES (1,'Silla',2,1,1,'2017-03-07 10:34:29','2017-03-07 10:34:29',2),(2,'Mouse',1,1,1,'2017-03-07 10:34:29','2017-03-07 10:34:29',2),(5,'iPad',1,1,1,'2017-03-07 16:46:40',NULL,2),(6,'Mesa',2,1,1,'2017-03-07 16:47:50',NULL,2);


--
-- Dumping data for table `cat_tipo_responsabilidad_ec`
--

INSERT INTO `cat_tipo_responsabilidad_ec` VALUES (1,'Facilitador','Facilitador',1,1,'2017-02-10 14:00:14',NULL,2),(2,'Coordinador académico','Coordinador académico',1,1,'2017-02-10 14:00:27','2017-02-10 14:00:55',2),(3,'Responsable de producción','Responsable de producción',1,1,'2017-02-10 14:01:14',NULL,2),(4,'Experto en contenido','Experto en contenido',1,1,'2017-02-10 14:01:14',NULL,NULL),(5,'Diseñador instruccional','Diseñador instruccional',1,1,'2017-02-10 14:01:14',NULL,NULL),(6,'Diseñador elearning','Diseñador elearning',1,1,'2017-02-10 14:01:14',NULL,NULL),(7,'Desarrollador elearning','Desarrollador elearning',1,1,'2017-02-10 14:01:14',NULL,NULL);

--
-- Dumping data for table `cat_tipos_asentamiento`
--

INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (1,'Aeropuerto','2016-06-15 16:34:00',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (2,'Barrio','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (4,'Campamento','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (8,'Ciudad','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (9,'Colonia','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (10,'Condominio','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (11,'Congregación','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (12,'Conjunto habitacional','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (15,'Ejido','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (16,'Estación','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (17,'Equipamiento','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (18,'Exhacienda','2016-06-15 16:35:37',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (20,'Finca','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (21,'Fraccionamiento','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (22,'Gran usuario','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (23,'Granja','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (24,'Hacienda','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (25,'Ingenio','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (26,'Parque industrial','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (27,'Poblado comunal','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (28,'Pueblo','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (29,'Ranchería','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (30,'Residencial','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (31,'Unidad habitacional','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (32,'Villa','2016-06-15 16:35:38',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (33,'Zona comercial','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (34,'Zona federal','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (37,'Zona industrial','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (38,'Ampliación','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (39,'Club de golf','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (40,'Puerto','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (45,'Paraje','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (47,'Zona militar','2016-06-15 16:35:39',NULL,1);
INSERT INTO `cat_tipos_asentamiento` (`id_tipo_asentamiento`,`descripcion`,`fecha_registro`,`fecha_actualizacion`,`usuario_modifico`) VALUES (48,'Rancho','2016-06-15 16:35:39',NULL,1);


--
-- Dumping data for table `cat_tipos_correo`
--

INSERT INTO `cat_tipos_correo` VALUES (1,'Institucional','2016-07-22 11:41:04',NULL,1,1),(2,'Externo','2016-07-22 11:41:08',NULL,1,1);

--
-- Dumping data for table `cat_tipos_telefono`
--

INSERT INTO `cat_tipos_telefono` VALUES (1,'Institucional','2016-07-22 11:41:08','2016-07-22 11:41:08',1,1),(2,'Movil','2016-07-22 11:41:08','2016-07-22 11:41:08',1,1);

--
-- Dumping data for table `cat_tpo_carga_horaria`
--

INSERT INTO `cat_tpo_carga_horaria` VALUES (1,'Teoría','null',1,'2016-10-21 15:40:12',NULL,1),(2,'Práctica','null',1,'2016-10-21 15:40:12',NULL,1),(3,'Evaluación','null',1,'2016-10-21 15:40:13',NULL,1);


/*
-- gestor_sisi.cat_asentamientos
*/
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010407','Canatlán de las Manzanas Centro',9,'34450','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010409','La Cañada',11,'34407','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010410','La Sauceda',28,'34466','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010412','Miguel Hidalgo (Viborillas)',28,'34405','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010413','El Pozole',28,'34407','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010414','Dolores Hidalgo (Gogogito)',28,'34406','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010415','Ignacio M. Altamirano (La Luz)',28,'34405','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010417','San José de Gracia',28,'34465','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010418','Donato Guerra',28,'34406','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010419','Medina',28,'34406','10001','2016-06-15 17:27:20',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010420','Bruno Martínez (San Bartolo)',28,'34466','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010466','San Diego de Alcalá',28,'34440','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010467','Maymorita',28,'34445','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010468','El Tule',28,'34445','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010469','Gomelia',28,'34445','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010470','Marquesotes de Guadalupe (Canelas)',28,'34443','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010471','San Jerónimo de Jacales',28,'34440','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010472','La Plazuela (El Rayo)',28,'34443','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010473','El Progreso (Los Pinos)',29,'34403','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010475','Francisco Zarco (Las Delicias)',28,'34400','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010476','Los Lirios',28,'34403','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010477','José Cruz Gálvez (Cañas)',28,'34404','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010478','Piedra Encimada',29,'34403','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010479','Santa Teresa de los Pinos',28,'34400','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010480','Nicolás Bravo',28,'34460','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010481','General Martín López',28,'34465','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010482','Nogales',28,'34465','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010483','Colonia Anáhuac (Palo Blanco)',28,'34467','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010484','José Guadalupe Aguilera (Santa Lucia)',28,'34465','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010485','Venustiano Carranza (Ocotán)',28,'34466','10001','2016-06-15 17:27:21',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010486','José Guadalupe Aguilera (La Granja)',28,'34465','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010487','Ricardo Flores Magón',28,'34467','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010488','Benjamín Aranda (San Rafael)',28,'34460','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010489','Veintidós de Mayo',28,'34460','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010490','Cerro Gordo',28,'34465','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010491','El Sáuz (Gustavo Silerio)',28,'34460','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100010492','El Sauz Bendito',29,'34465','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011904','Roma',9,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011905','Arboledas',21,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011906','Mijares',21,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011907','Plutarco Elías Calles',9,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011908','Real del Valle',21,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011909','Ayuntamiento',9,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011910','Soledad Alvarez',21,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011911','Los Manzanos',21,'34453','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011912','Los Nogales',21,'34454','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011913','Ejidal',9,'34454','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011914','11 de Julio',9,'34455','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011915','Valenzuela',9,'34455','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011917','Progresista',9,'34456','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011918','Las Brisas',21,'34456','10001','2016-06-15 17:27:22',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011919','San Diego',9,'34456','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011920','30 Viejos',2,'34457','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100011921','Bellavista',9,'34457','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012257','Agrícola Ganadera Bravo (san Rafael)',29,'34460','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012258','Alamitos',29,'34446','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012259','Altamira',29,'34403','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012260','Arnulfo R. Gómez (Los Sauces)',28,'34403','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012261','Los Sauces (Balneario)',29,'34403','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012262','Canatlán Viejo',2,'34407','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012263','Canoas',29,'34400','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012265','Charco Largo',29,'34405','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012266','Charoláis los Sauces I',29,'34460','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012267','Cieneguitas',28,'34446','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012269','Crucero del Ojo de Agua del Mezquital',29,'34407','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012270','Cuatillos',29,'34405','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012271','Diez Hermanos (Las Tapias)',29,'34445','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012272','Don Chema',29,'34466','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012273','Durangueño (Corralitos)',29,'34464','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012274','El Álamo',29,'34405','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012275','El Capricho',29,'34460','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012276','El Capricho',29,'34404','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012278','El Chaparro (los Méndez)',29,'34405','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012279','El Chaparro de los Díaz (Chaparro Díaz)',29,'34405','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012280','El Chaparro de los Rivera',29,'34405','10001','2016-06-15 17:27:23',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012281','El Coyote (ojo del Coyote)',29,'34460','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012282','El Faisán de las Tapias',29,'34445','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012284','El Nocebe',29,'34460','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012286','El Porvenir (Casas Coloradas)',29,'34460','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012287','El Ranchito',29,'34444','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012288','El Refugio (El Moradito)',29,'34460','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012289','El Refugio (la Esperanza)',29,'34460','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012290','El Regreso',29,'34465','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012291','El Río (Los García)',29,'34465','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012292','El Saucillal',29,'34444','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012294','El Vergel de Sauces',29,'34403','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012295','Elisa',29,'34465','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012296','Francisco Merino ( San Bernardino)',29,'34403','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012297','Guadalupe los Nogales (Las Papas)',29,'34464','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012298','Hacienda los Pinos',29,'34400','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012299','Hacienda San Rafael',29,'34460','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012300','Hermenegildo Galiana',29,'34466','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012301','Huerta Santa Cecilia',29,'34466','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('100012302','Huerta Varela',29,'34465','10001','2016-06-15 17:27:24',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('290050147','Centro',9,'90300','29005','2016-06-15 18:55:37',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('10019999','prueba2',12,'99999','1001','2016-07-04 15:46:30','2016-07-25 12:18:19',1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('10010001','Zona Centro',9,'20000','1001','2016-06-15 16:41:09',NULL,1,1);
INSERT INTO `cat_asentamientos` (`id_asentamiento`,`nombre`,`id_tipo_asentamiento`,`codigo_postal`,`id_municipio`,`fecha_registro`,`fecha_actualizacion`,`activo`,`usuario_modifico`) VALUES ('10011435','El Quelite',21,'20200','1001','2016-06-15 17:03:21',NULL,1,1);


INSERT INTO `tbl_encuesta` VALUES (41,'GA-280120170947-A','GA-280120170947-A Instrucciones','GA-280120170947-A Observaciones','2017-01-31 09:47:23','2017-02-09 18:30:39',1,NULL,3,4,1,'01030103',4,NULL,NULL,NULL,NULL,NULL),(48,'ENC-08022017-B','Contesta correctamente','','2017-02-08 16:46:37','2017-02-09 16:35:14',1,NULL,3,4,1,'01030102',4,NULL,NULL,NULL,NULL,NULL),(49,'Demo JPCP','Contesta con base en las instrucciones...','Pruebas....','2017-03-01 13:11:19','2017-03-01 13:12:21',1,NULL,3,4,1,'01030101',2,NULL,NULL,NULL,NULL,NULL),(51,'Encuesta 1038',NULL,NULL,'2017-03-14 13:39:36',NULL,1,NULL,1,1,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL),(52,NULL,NULL,NULL,'2017-03-14 13:43:10',NULL,1,NULL,1,1,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL),(53,NULL,NULL,NULL,'2017-03-14 13:45:30',NULL,1,NULL,1,1,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL),(54,NULL,NULL,NULL,'2017-03-15 10:23:52',NULL,1,NULL,NULL,1,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL),(59,'Nombre JHCC','instrucciones','observaciones','2017-03-15 12:52:15','2017-03-15 12:52:46',1,NULL,4,4,2,'01040201',2,NULL,NULL,NULL,NULL,'realizar revision'),(60,'Encuesta JHCC','Lee cuidadosament y responde','Nada es lo ue parece','2017-03-15 10:44:55','2017-03-15 12:47:16',1,NULL,11,4,1,'03110101',2,NULL,NULL,NULL,NULL,'hay que revisar bien los comentarios'),(71,'Encuesta Orddinaria','instrucciones requeridas','observaciones no requeridas','2017-03-15 13:18:48','2017-03-15 13:39:30',1,NULL,4,4,2,'01040202',2,NULL,NULL,NULL,NULL,''),(72,'Encuesta 2','instr','obser','2017-03-15 13:22:39','2017-03-15 13:38:50',1,NULL,12,4,6,'03120601',2,NULL,NULL,NULL,NULL,''),(73,'encuesta ABC','dddd','dddd','2017-03-15 13:25:12','2017-03-15 13:38:29',1,NULL,12,4,3,'03120301',2,NULL,NULL,NULL,NULL,''),(74,'encuesta abc','asdasdasd','asdasdasd','2017-03-15 14:00:25','2017-03-15 14:00:25',1,NULL,4,3,7,NULL,2,NULL,NULL,NULL,NULL,NULL),(75,NULL,NULL,NULL,'2017-03-15 16:07:25',NULL,1,NULL,NULL,1,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL),(76,'sdsdsd','sdsd','sdsd','2017-03-15 16:18:47','2017-03-15 16:18:47',1,NULL,11,3,1,NULL,2,NULL,NULL,NULL,NULL,NULL),(81,'Nombre JHCCddddd','dddd','ddddd','2017-03-21 17:32:42','2017-03-21 17:33:13',1,NULL,12,2,1,NULL,2,NULL,NULL,NULL,NULL,'rrrrrtttttttrrrrrtttttt'),(82,'Clone WAR','dddd','dddd','2017-03-15 17:22:55','2017-03-21 18:04:03',1,NULL,12,4,3,'03120302',2,NULL,NULL,NULL,NULL,''),(83,'WAR','war','war','2017-03-21 18:15:53','2017-03-21 18:16:24',1,NULL,12,2,2,NULL,2,NULL,NULL,NULL,NULL,'generacion de comentarios nexus'),(84,'ererer','ererr','ererer','2017-03-15 17:30:19','2017-03-21 17:06:30',1,NULL,12,4,2,'03120201',2,NULL,NULL,NULL,NULL,''),(85,'clone war 2','Contesta con base en las instrucciones...','Pruebas....','2017-03-21 16:46:08','2017-03-21 18:15:34',1,NULL,3,2,1,'',2,NULL,NULL,NULL,NULL,'comentarios nexus'),(92,'Encuesta Kirk','sssss','ssss','2017-03-21 18:01:22','2017-03-21 18:01:22',1,NULL,11,3,1,NULL,2,NULL,NULL,NULL,NULL,NULL),(94,'nombre encuesta','rferferf','erferferf','2017-03-21 17:57:07','2017-03-21 17:57:07',1,NULL,11,3,2,NULL,2,NULL,NULL,NULL,NULL,NULL),(95,'ABC','ABC instrucciones','ABC observaciones','2017-03-21 16:35:40','2017-03-21 16:35:40',1,NULL,11,3,2,NULL,2,NULL,NULL,NULL,NULL,NULL),(98,'ENCUESTA DE SATISFACCIÓN GENERAL DEL ALUMNO CON EL MÁSTER','Estimado alumno, Con objeto de evaluar su grado de satisfacción con el Máster que ha cursado y mejorarlo en sucesivas ediciones le agradeceríamos que rellenara este cuestionario. ','Por favor, conteste con sinceridad. Los cuestionarios son anónimos. ','2017-03-22 16:15:10','2017-03-22 16:15:32',1,NULL,12,3,2,NULL,2,NULL,NULL,NULL,NULL,'se agregaron preguntas\r\nlisto- agergadas'),(101,'Nombre de la encuesta','gw.encuestas.alta.placeholder.etiqueta.instrucciones','gw.encuestas.alta.placeholder.etiqueta.instrucciones','2017-03-22 16:01:36','2017-03-22 16:01:38',1,NULL,13,3,1,NULL,2,NULL,NULL,NULL,NULL,NULL);


--
-- Dumping data for table `cat_clasificaciones_badges`
--

INSERT INTO `cat_clasificaciones_badges` (id_clasificacion_badge, nombre, descripcion, fecha_registro, id_estatus) VALUES (1,'Por desempeño','Aqui la descripcion', CURDATE(), 1);

INSERT INTO `cat_clasificaciones_badges` (id_clasificacion_badge, nombre, descripcion, fecha_registro, id_estatus) VALUES (2,'Evento de capacitacion','Aqui la descripcion', CURDATE(), 1);

INSERT INTO `cat_clasificaciones_badges` (id_clasificacion_badge, nombre, descripcion, fecha_registro, id_estatus) VALUES (3,'Objeto de aprendizaje','Aqui la descripcion', CURDATE(), 1);


--
-- Dumping data for table `cat_badges`
--

INSERT INTO `cat_badges` (id_badge, id_clasificacion_badge, calificacion_maxima, calificacion_minima, nombre, descripcion, ruta_imagen, fecha_registro, id_estatus) VALUES (1, 1, 100, 90, 'Oro','Aqui la descripcion de los criterios', '/tmp/img/ejemplo', CURDATE(), 1);

INSERT INTO `cat_badges` (id_badge, id_clasificacion_badge, calificacion_maxima, calificacion_minima, nombre, descripcion, ruta_imagen, fecha_registro, id_estatus) VALUES (2, 1, 89, 80, 'Plata','Aqui la descripcion de los criterios', '/tmp/img/ejemplo', CURDATE(), 1);

INSERT INTO `cat_badges` (id_badge, id_clasificacion_badge, calificacion_maxima, calificacion_minima, nombre, descripcion, ruta_imagen, fecha_registro, id_estatus) VALUES (3, 1, 79, 0, 'Sin badge','Aqui la descripcion de los criterios', '/tmp/img/ejemplo', CURDATE(), 1);

