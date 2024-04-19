/**
 * 
 */
package mx.gob.sedesol.gestorweb.commons.constantes;

/**
 * @author PlanetMedia
 *
 */
public final class ConstantesGestorWeb {

	public static final String PARAM_USUARIO_SESSION = "usuario_session";
	public static final int INDICE_INICIAL = 0;
	public static final String ERRORES_LOGIN ="ERRORES_LOGIN";
	public static final Long ID_USUARIO_SYSTEM = 11L;
	public static final String USUARIO_SYSTEM ="system";
	public static final int ACTIVO = 1;
	public static final int INACTIVO = 0;
	
	
	//CONSTANTES PARA CLAVES DE FUNCIONALIDADES AL ENVIAR CORREOS Y NOTIFICACIONES
	public static final String CLAVE_NOTIFICACION_AL_CREAR_EVENTO ="NOTIFI_CREAR_EVT";
	public static final String CLAVE_CORREO_AL_CREAR_EVENTO ="CORREO_CREAR_EVT";
	
	public static final String CLAVE_NOTIFICACION_AL_CONSTRUIR_AVA ="NOTIFI_CONST_AVA";
	public static final String CLAVE_CORREO_AL_CONSTRUIR_AVA ="CORREO_CONST_AVA";

	public static final String CLAVE_NOTIFICACION_AL_AGREGAR_COLABORADORES ="NOTIFI_AGR_COLAB";
	public static final String CLAVE_CORREO_AL_AGREGAR_COLABORADORES ="CORREO_AGR_COLAB";
	
	public static final String CLAVE_NOTIFICACION_AL_MATRICULAR_USUARIOS ="NOTIFI_MTR_USR";
	public static final String CLAVE_CORREO_AL_MATRICULAR_USUARIOS ="CORREO_MTR_USR";
	
	public static final String CLAVE_NOTIFICACION_AL_CERRAR_ACTA ="CORREO_CER_ACT";
	public static final String CLAVE_CORREO_AL_CERRAR_ACTA ="NOTIFI_CER_ACT";
	
	public static final String CLAVE_NOTIFICACION_ENCUESTA_PENDIENTE ="NOTIFI_ENCUESTA_PENDIENTE";
	public static final String CLAVE_CORREO_ENCUESTA_PENDIENTE ="CORREO_ENCUESTA_PENDIENTE";
	
	
	
	//CONSTANTES DE NAVEGACION 
	public static final String RUTA_ADMIN_USUARIOS ="AdministraUsuarios.xhtml";
	public static final String RUTA_REGISTRO_USUARIO_INTERNO ="usuario-interno";
	public static final String RUTA_REGISTRO_USUARIO_EXTERNO ="usuario-externo";
	public static final String RUTA_AREAS = "RUTA_AREAS";
	
	public static final String RUTA_TABLERO ="/tablero";
	public static final String NAVEGA_TABLERO = "PANTALLA_INICIO";
	public static final String NAVEGA_EVENTOS_PUBLICOS ="EVENTOS";
	
	public static final String NAVEGA_MI_PERFIL_GE ="MI_PERFIL_GE";
	public static final String NAVEGA_CURSOS_DISPONIBLES ="CURSOS_DISPONIBLES";
	public static final String NAVEGA_MIS_CURSOS_PRIVADOS = "MIS_CURSOS_PRIVADOS";
	public static final String NAVEGA_MIS_LOGROS_GE ="MIS_LOGROS_GE";
	public static final String NAVEGA_CONSTANCIAS ="CONSTANCIAS_GE";
	
	public static final String NAVEGA_REGISTRO_ASISTENCIAS ="REGISTRO_ASISTENCIAS";
	
	public static final String NAVEGA_A_LOGIN ="LOGIN";
	public static final String NAVEGA_A_REGISTRO ="REGISTRO";
	public static final String NAVEGA_ADMIN_USUARIOS ="ADMIN_USUARIOS";
	public static final String NAVEGA_REGISTRO_USUARIO_INTERNO ="REGISTRO_USUARIO_INTERNO";
	public static final String NAVEGA_REGISTRO_USUARIO_EXTERNO ="REGISTRO_USUARIO_EXTERNO";
	public static final String NAVEGA_VER_EDITAR_USUARIO ="VER_EDITAR_USUARIO";
	public static final String NAVEGA_FUNCIONALIDADES ="FUNCIONALIDADES";
	public static final String NAVEGA_TEXTOS_SISTEMA = "TEXTOS_SISTEMA";
	public static final String NAVEGA_BUSQUEDA_TEXTOS_SISTEMA = "BUSQUEDA_TEXTOS_SISTEMA";
	public static final String NAVEGA_ACTIVIDADES_USUARIO = "ACTIVIDADES_USUARIO";
	public static final String NAVEGA_IMAGENES_DOCUMENTOS = "NAVEGA_IMAGENES_DOCUMENTOS";
	
	
	public static final String NAVEGA_ROLES ="ROLES";
	public static final String NAVEGA_ROLES_PERMISOS ="ROLES_PERMISOS";
	public static final String NAVEGA_MUNICIPIOS ="MUNICIPIOS";
	public static final String NAVEGA_ASENTAMIENTOS ="ASENTAMIENTOS";
	public static final String NAVEGA_CARGA_USUARIOS ="CARGA_USUARIOS";
	public static final String NAVEGA_CONSULTA_BITACORA ="CONSULTA_BITACORA";
	public static final String NAVEGA_CONSULTA_BITACORA_PERSONAS ="CONSULTA_BITACORA_PERSONAS";
	public static final String NAVEGA_INICIO ="PANTALLA_INICIO";
	public static final String NAVEGA_ASIGNAR_ROLES ="ASIGNACION_ROLES";
	public static final String NAVEGA_NOTIFICACIONES ="NOTIFICACIONES";
	
	public static final String NAVEGA_NOTIFICACIONES_SISTEMA ="NOTIFICACIONES_SISTEMA";
	
	public static final String NAVEGA_NOTIFICACION_DETALLE ="NOTIFICACION_DETALLE";
	public static final String NAVEGA_NOTIFICACION_EDICION ="NOTIFICACION_EDICION";
	public static final String NAVEGA_NOTIFICACION_RESPONDER ="NOTIFICACION_RESPONDER";
	public static final String NAVEGA_NOTIFICACION_PERSONAS ="NOTIFICACION_PERSONAS";
	public static final String NAVEGA_TEMAS ="TEMAS";
	public static final String NAVEGA_ARCHIVOS_TEMA ="ARCHIVOS_TEMA";
	public static final String NAVEGA_ARCHIVO_TEMA ="ARCHIVO_TEMA";
	public static final String NAVEGA_PARAMETROS_SISTEMA ="PARAMETROS_SISTEMA";
	
	public static final String NAVEGA_CAT_PLANES_PROGRAMAS ="CAT_PLANES_PROG";
	
	
	public static final String NAVEGA_ORG_GUB ="ORG_GUB";
	public static final String NAVEGA_CAT_GESTION_ESCOLAR ="CAT_GESTION_ESCOLAR";
	public static final String NAVEGA_PLANTILLAS_MENSAJES ="PLANTILLAS_MENSAJES";
	public static final String NAVEGA_CATALOGOS_ENCUESTA = "CATALOGOS_ENCUESTA";
	public static final String NAVEGA_ENCUESTAS = "ADMIN_ENCUESTA";
	public static final String NAVEGA_SEGUIMIENTO_ENCUESTAS = "SEGUIMIENTO_ENCUESTA";
	public static final String NAVEGA_ENCUESTAS_PENDIENTES = "ENCUESTAS_PENDIENTES";
	public static final String NAVEGA_CONTESTA_ENCUESTA = "CONTESTA_ENCUESTA";
	public static final String NAVEGA_DETALLE_ENCUESTADOS_PENDIENTES = "DETALLE_ENCUESTADOS_PENDIENTES";
	public static final String NOTIFICACION_ENVIO_RECORDATORIO ="ENVIO_RECORDATORIO_ENCUESTA";
	public static final String NAVEGA_RECURSOS = "RECURSOS";
	
	public static final String NAVEGA_PLANES_PROGRAMAS_CATALOGOS ="ADMIN_CAT_PLAN_PROG";
	public static final String NAVEGA_PLAN_PROGRAMA_MALLACURRICULAR= "MALLA_CURRICULAR";
	public static final String NAVEGA_ACTIVIDADES_RECURSOS_MOODLE = "RECURSOS_MOODLE";
	public static final String NAVEGA_BUSQUEDA_PROGRAMAS_CAPACIT = "BUSQUEDA_PROGRAMA_CAP";
	public static final String NAVEGA_NUEVO_PROGRAMA_CAPACITACION = "NUEVO_PROGRAMA_CAP";
	public static final String NAVEGA_FEC_PROGRAMA ="FEC_PROGRAMA";
	public static final String NAVEGA_FEC_EDITAR_PROGRAMA ="FEC_EDITAR_PROGRAMA";
	
	public static final String NAVEGA_BUSQUEDA_PLANES="BUSQUEDA_PLANES";
	public static final String NAVEGA_NUEVO_PLAN = "NUEVO_PLAN";
	
	public static final String NAVEGA_DETALLE_ENCUESTA = "DETALLE_ENCUESTA";
	public static final String NAVEGA_REGISTRA_ENCUESTA = "REGISTRA_ENCUESTA";
	
	public static final String NAVEGA_BUSQUEDA_EVENTO_CAP = "BUSQUEDA_EVENTO_CAP"; 
	public static final String NAVEGA_GENERA_DISPERSION = "GENERA_DISPERSION";
	public static final String NAVEGA_RESPONSABLES_EVE_CAP = "RESPONSABLES_EVE_CAP";
	
	public static final String NAVEGA_UNIDADES_RESPONSABLES = "UNIDADES_RESPONSABLES";
	public static final String NAVEGA_PROGRAMAS_SOCIALES = "PROGRAMAS_SOCIALES";
	public static final String NAVEGA_CONFIG_SEDES ="LOGISTICA_CONFIG_SEDES";
	
	public static final String NAVEGA_CLASIFICACION_BADGES = "CLASIFICACION_BADGES";
	public static final String NAVEGA_BADGES = "BADGES";
	public static final String NAVEGA_RESPONDER_ENCUESTA = "RESPONDER_ENCUESTA";
	
	public static final String NAVEGA_ANALISIS_USUARIO = "ANALISIS_USUARIO";
	public static final String NAVEGA_ANALISIS_GESTION_ESC = "ANALISIS_GESTION_ESCOLAR";
	public static final String NAVEGA_ANALISIS_EVENTOS = "ANALISIS_EVENTOS";
	
	public static final String NAVEGA_IMAGENES_DOCS = "IMAGENES_DOCS";
	public static final String NAVEGA_PLANTILLAS_DOCS = "PLANTILLAS_DOCS";
	public static final String NAVEGA_PLANTILLA_DOCS = "PLANTILLA_DOC";
	
	public static final String NAVEGA_EXPEDIENTE_ALUMNO = "EXPEDIENTE_ALUMNO";
	public static final String NAVEGA_BUSCAR_EXPEDIENTE_ALUMNO = "BUSCAR_EXPEDIENTE_ALUMNO";
	public static final String NAVEGA_EXPEDIENTE_GRUPO = "EXPEDIENTE_GRUPO";
	
	public static final String NAVEGA_EDICION_PLANTILLA_NOTIFICACION = "EDICION_PLANTILLA_NOTIFICACION";
	
	
	//AGREGAR_RESPONSABLE
	public static final String AGREGAR_RESPONSABLE = "AGREGAR_RESPONSABLE";
	//navega a REPONSABLES_EVENTO
	public static final String REPONSABLES_EVENTO = "RESPONSABLES_EVE_CAP";
	

	//CONSTANTES CATALOGOS_SESION
	public static final String CAT_TIPOS_CORREO = "cat_tipos_correo";
	public static final String CAT_ROLES = "cat_roles";
	public static final String CAT_TPO_PLAN ="cat_tipo_plan";
	public static final String CAT_PERIODOS ="cat_periodos";

	public static final String CAT_MODALIDAD_PLAN_PROG ="cat_modalidad_plan_programas";
	public static final String CAT_ESTATUS_PLAN ="cat_estatus_plan";
	public static final String CAT_ALCANCE_PLAN ="cat_alcance_plan";
	public static final String CAT_NIVEL_ENSE_PLAN_PROG ="cat_nivel_ensenanza_programa";
	public static final String CAT_COMPETENCIAS_PLAN ="cat_competencias_plan";
	public static final String CAT_CONOCIMIENTOS_PLAN ="cat_conocimientos_plan";
	public static final String CAT_APTITUDES_PLAN ="cat_aptitudes_plan";
	public static final String CAT_HABILIDADES_PLAN ="cat_habilidades_plan";
	public static final String CAT_DOCS_EXPIDE_PLAN ="cat_documentos_expide_plan";
	public static final String CAT_ORG_GUBERNAMENTALES ="tbl_organismos_gubernamentales";
	public static final String CAT_ESTATUS_PROG ="cat_estado_programa";
	public static final String CAT_DIRIGIDO_A_PROG ="cat_puestos_sedesol_ec";
	public static final String CAT_OBJ_CURRICULAR ="cat_objeto_curricular";
	public static final String CAT_EVENTO_CAPACITACION ="cat_tipo_evento_ec";
	public static final String CAT_NIVEL_CONOCIMIENTO ="cat_nivel_conocimiento";
	public static final String CAT_COMPETENCIA_ESPECIFICA = "cat_competencia_especifica";
	public static final String CAT_INSTITUCIIONES_CERTIFICADORAS ="cat_instituciones_certificadoras";	
	public static final String CAT_TIPO_CARGA_HORARIA ="cat_tpo_carga_horaria";
	public static final String CAT_MATERIAL_DIDACTICO = "cat_material_didactico";
	public static final String CAT_EST_PERSONAL_EXTERNO = "tbl_estructura_personal_ext";
	public static final String CAT_ESTADO_EVENTO_CAPACITACION = "cat_estado_evento_capacitacion";
	public static final String CAT_CLASIFICACIONES_BADGE = "cat_clasificaciones";
	
	public static final String CAT_CREDITOS_PLAN ="cat_creditos_plan";
	public static final String CAT_DIVISIONES_PLAN ="cat_divisiones_plan";
	public static final String CAT_TIPOS_COMPETENCIA ="cat_tipos_competencia";

	//REL_EJE_COMPETENCIA
	public static final String REL_EJE_COMPETENCIA = "EJE_COMPETENCIAS";
	
	//Objetos en sesion		
	public static final String OBJ_TEMA_PROGRAMA_SELEC = "OBJ_TEMA_PROG";
	public static final String OBJ_ENCUESTA_SELEC = "OBJ_ENCUESTA_SELEC";
	public static final String OBJ_EVENTO_SELEC = "OBJ_EVENTO_SELEC";
	public static final String OBJ_PARTICIPANTES_ENCUESTA = "OBJ_PARTICIPANTES_ENCUESTA";
	public static final String OBJ_PARTICIPANTES_PENDIENTES_ENCUESTA = "OBJ_PARTICIPANTES_PENDIENTES_ENCUESTA";
    public static final String OBJ_ENCUESTA_VISTA ="OBJ_ENCUESTA_VISTA" ;
	public static final String OBJ_ENCUESTA_RESPONDER = "OBJ_ENCUESTA_RESPONDER";
    public static final String OBJ_PLAN_SELEC ="OBJ_PLAN";
    public static final String OBJ_ENCUESTA_EDITAR = "OBJ_ENCUESTA_EDITAR";
    public static final String OBJ_ENCUESTA_DUPLICAR = "OBJ_ENCUESTA_DUPLICAR";
	public static final String OBJ_ENCUESTA_CONTEXTO = "CAT_ENCUESTA_CONTEXTO";
	public static final String OBJ_ENCUESTA_TIPO = "CAT_ENCUESTA_TIPO";
	public static final String OBJ_ENCUESTA_ESTADO = "CAT_ENCUESTA_ESTADO";
	public static final String OBJ_ENCUESTA_OBJETIVO = "CAT_ENCUESTA_OBJETIVO";
	public static final String OBJ_NUEVA_CONSULTA_ENCUESTA = "NUEVA_CONSULTA_ENCUESTA";
	public static final String OBJ_CRITERIOS_BUSQ_AREA = "OBJ_CRITERIOS_BUSQ_AREA";
	public static final String OBJ_ES_PERSONALIZACION = "OBJ_ES_PERSONALIZACION";

	
    public static final String OBJ_PROGRAMA ="OBJ_PROGRAMA";
    public static final String EDICION_PROGRAMA = "EDITAR_PROGRAMA";

	public static final Integer PRIORITARIO = 1;
	
	public static final String CONFIG_PATH = "classpath*:spring/applicationContext.xml";
	
	public static final Integer TIPO_IMAGEN_HEADER = 1;
	public static final Integer TIPO_IMAGEN_FOOTER = 2;
	public static final String DIR_HEADERS = "headers/";
	public static final String DIR_FOOTERS = "footers/";
	public static final String DIR_PLANTILLAS_SISI = "plantillasSISI";
	
	public static final String SUFFIX_HTTP_S= "http://";
	
	public static final String PARAM_ERROR_LOGIN="errorLogin";
	public static final String PARAM_TOKEN="token";
	public static final String PARAM_USUARIO="usuario";
	public static final String PARAM_ID_PERSONA="idPersona";
	public static final String PF_CMP_TREE_VERTICAL="vertical";
	public static final String PF_CMP_TREE_HORIZONTAL="horizontal";
	
	public static final Integer MDL_TIPO_MODULO_ACTIVIDADES = 1;
	public static final Integer MDL_TIPO_MODULO_RECURSOS = 2;


    public static final Integer NO_PREGUNTAS_MINIMO = 5 ;
	
	public static final String ESTATUS_PROG_BORRADOR ="Borrador";
	public static final Integer ESTADO_ENCUESTA_DEFAULT = 1;
	public static final Integer ESTADO_ENCUESTA_COMENTARIOS = 2;
	public static final Integer ESTADO_ENCUESTA_REVISION = 3;
	public static final Integer ESTADO_ENCUESTA_APROBADA = 4;
	
	public static final String TIPO_CARGA_HORARIA_TEORIA = "Teoría";
	public static final String TIPO_CARGA_HORARIA_PRACTICA = "Práctica";
	public static final String TIPO_CARGA_HORARIA_EVALUACION = "Evaluación";


	/*CATALOGOS DE GESTION ESCOLAR*/
	public static final String CATALOGOS_GESTION_ESCOLAR = "CATALOGOS_GESTION_ESCOLAR";
	public static final String CATALOGO_ASISTENCIA_ESCOLAR = "CATALOGO_ASISTENCIA_ESCOLAR";
	public static final String NUEVO_EVENTO_CAPACITACION = "NUEVO_EVENTO_CAPACITACION";
	public static final String NAVEGA_MATRICULACION_EVENTO = "MATRICULACION_EVENTO";
	public static final String DATOS_GENERALES_EC = "DATOS_GENERALES_EC";
	public static final String NAVEGA_NUEVO_EVENTO_CAPACITACION = "NUEVO_EVENTO_CAP";
	public static final String NAVEGA_REGISTRO_NUEVO_EVENTO_CAPACITACION = "REG_NUEVO_EVENT_CAP";
	public static final String NAVEGA_CALIFICACIONES_XEVENTO = "CALIFICACIONES_EVENTO";
	
	/**
	 * Gestion del aprendizaje
	 */
	public static final String NAVEGA_CAT_GESTION_APRENDIZAJE           = "CAT_GESTION_APRENDIZAJE";
	public static final String NAVEGA_AMBIENTES_VIRTUALES_APRENDIZAJE   = "AMBIENTES_VIRTUALES_APRENDIZAJE";
	public static final String NAVEGA_DOCUMENTAR_FOA                    = "NAVEGA_DOCUMENTAR_FOA"; 
	public static final String NAVEGA_MODIFICAR_SEGUIMIENTO_AVA         = "MODIFICA_SEG_AVA";
	public static final String NAVEGA_CATALOGO_GESTION_APRENDIZAJE      = "CATALOGO_GEST_APRENDIZAJE"; 	
	public static final String CAT_TEMA_RECURSO                         = "cat_tema_recurso";
	public static final String CAT_CLASIFICACION_ARCHIVO_OA             = "cat_clasificacion_archivo_oa";
	public static final String CAT_TIPO_RESPONSABILIDAD                 = "cat_tipo_responsabilidad_ec";
	public static final String AGREGAR_RESPONSABLE_GESTION_AP           = "AGREGAR_RESPONSABLE_GESTION_AP";	
	public static final String CLAVE_SEGURIDAD_AJUNTAR_GUION_INST       = "ADJ_GUION_INS";
	public static final String CLAVE_SEGURIDAD_AJUNTAR_GUION_INST_EXT   = "ADJ_GUION_INS_EXT";
	public static final String CLAVE_SEGURIDAD_AJUNTAR_DESARROLLO_OA    = "ADJ_DESA_OA";
	public static final String CLAVE_SEGURIDAD_AJUNTAR_DESARROLLO_OA_EXT= "ADJ_DESA_OA_EXT";
	public static final String CLAVE_SEGURIDAD_AJUNTAR_SCORM            = "ADJ_SCORM";
	public static final String CLAVE_SEGURIDAD_AJUNTAR_SCORM_EXT        = "ADJ_SCORM_EXT";
	public static final String CLAVE_SEGURIDAD_DESCARGAR_GUION_INST     = "DESC_GUION_INS";
	public static final String CLAVE_SEGURIDAD_DESCARGAR_DESARROLLO_OA  = "DESC_DESA_OA";
	public static final String CLAVE_SEGURIDAD_DESCARGAR_SCORM          = "DESC_SCORM";
	public static final String CLAVE_SEGURIDAD_VALIDAR_GUION_INST       = "VALIDA_GUION_INST";
	public static final String CLAVE_SEGURIDAD_VALIDAR_DESARROLLO_OA    = "VALIDA_DESA_OA";
	public static final String CLAVE_SEGURIDAD_VALIDAR_SCORM            = "VALIDA_SCORM";
	public static final String CLAVE_SEGURIDAD_VALIDAR_FUNCIONALIDAD_OA = "VAL_FUN_UNIDAD";
	public static final String CLAVE_SEGURIDAD_ACTIVAR_AVA              = "ACT_AVA";
	public static final String CLAVE_SEGURIDAD_VALIDAR_AVA              = "VAL_AVA";
	public static final String CAT_ELEMENTOS_MULTIMEDIA_FOA             = "cat_elemento_multimedia_foa";
	public static final Long   MILISEGUNDOS_POR_DIA                         = Long.valueOf(24 * 60 * 60 * 1000);
	public static final String OBJ_EXPEDIENTE_SELECCIONADO              = "OBJ_EXPEDIENTE_SELECCIONADO";
	

	
	/*LOGISTICA E INFRAESTRUCTURA*/
	public static final String NAVEGA_CATS_LOGISTICA_INFRA="CATALOGOS_LOGISTICA_INFRA";
	public static final String NAVEGA_BUSQ_AREAS_LOGISTICA_INFRA="BUSQUEDA_AREAS_INFRA";
	public static final String NAVEGA_RESERVACION_AREA="SOLICITA_RESERVACION_AREA";
	public static final String NAVEGA_CONF_AREAS_LOGISTICA_INFRA="CONFIGURACION_AREAS_INFRA";
	public static final String NAVEGA_PERSONALIACION_LOGISTICA_INFRA ="PERSONALIZACION_AREAS_INFRA";
	
	/*TIPO DE COMPETENCIA */
	public static final int COMPETENCIA_ESTRATEGICO = 2;
	public static final int COMPETENCIA_GESTION = 3;
	public static final int COMPETENCIA_INSTITUCIONAL = 4;
	public static final int COMPETENCIA_REGULATORIO = 7;
	public static final int COMPETENCIA_PRODUCTO = 8;
	public static final int COMPETENCIA_ESTRUCTURA = 9;
	public static final int COMPETENCIA_CONTEXTO_HUMANO = 10;
	
	/*MODALIDAD */
	public static final int MODALIDAD_EN_LINEA = 1;
	public static final int MODALIDAD_PRESENCIAL = 2;
	
	/*NIVEL ENSEÑANZA*/
	public static final int NIVEL_ACTUALIZACION = 1;
	public static final int NIVEL_INDUCCION = 2;
	public static final int NIVEL_FORMACION = 3;
	
	/*NIVEL CONOCIMIENTOS*/
	public static final int CONOCIMIENTO_BASICO = 1;
	public static final int CONOCIMIENTO_INTERMEDIO = 2;
	public static final int CONOCIMIENTO_AVANZADO = 3;
	
	/*TIPO EVENTO */
	public static final int CURSO = 1;
	public static final int TALLER = 2;
	public static final int SEMINARIO =3;
	
	/****/
	public static final Integer TIPO_CORREO_INSTITUCIONAL = 1;
	public static final Integer TIPO_TELEFONO_INSTITUCIONAL = 1;
	public static final Integer TIPO_TELEFONO_MOVIL = 2;
	public static final String ID_PAIS_MEXICO = "MX";

	//Cabeceros HTTP 	
	public static final String HTTP_HEADER_CONTENTTYPE_PDF = "application/pdf" ;
        public static final String HTTP_HEADER_CONTENTTYPE_XML = "application/xml" ;
	
	public static final String HTTP_HEADER_CONTENTTYPE_ZIP = "application/zip, application/octet-stream" ;
	
	
	public static final int TPO_CARGA_HORARIA_TEORICA =1;
	public static final int TPO_CARGA_HORARIA_PRACTICA =2;
	public static final int TPO_CARGA_HORARIA_EVALUACION =3;
	
	public static final String TIPO_COMP_ESPECIFICA = "tipo_comp_especifica";
	public static final String TIPO_PERSONAL_EXT = "tipo_personal_externo";

    public static final Integer ESTADO_PROGRAMA_FINAL = 2 ;

    /* CATALOGOS DE ROLES DE PRODUCCION EC */
	public static final Integer EXPERTO_CONTENIDO = 5;
	public static final Integer DISENIADOR_INSTRUCCIONAL = 6;
	public static final Integer DISENIADOR_ELEARNING = 7;
	public static final Integer DESARROLLADOR_ELEARNING = 4;
	public static final Integer NO_PREGUNTAS_MAXIMAS = 10;
	
	public static final Integer UBICACION_OFICINAS_CENTRALES = 1;
	public static final Integer UBICACION_DELEGACION_ESTATAL = 2;
	
	public static final Integer RESERV_EVENTO_CAPACITACION=1;
	public static final Integer RESERV_EVENTO_GENERAL=2;
	
	public static final String SCHEDULE_VIEW_MONTH ="month";
	public static final String SCHEDULE_VIEW_AGENDADAY="agendaDay";
	public static final String ASISTENCIA = "Asistencia";
	
	public static final String NO = "No";
	
	public static final String NOMBRE = "Nombre";
	
	public static final String APELLIDO_PATERNO = "Apellido Paterno";
	
	public static final String APELLIDO_MATERNO = "Apellido Materno";
	
	public static final String PORCENTAJE_ASISTENCIA = "Porcentaje de asistencia";
	
	public static final Integer NUMERO_CERO = 0;
	
	public static final Integer NUMERO_UNO = 1;
	
	public static final Integer NUMERO_DOS = 2;
	
	public static final Integer NUMERO_TRES = 3;
	
	public static final Integer NUMERO_CUATRO = 4;
	
	
	
	
	public static final String TAG_VALIDACION_REGASISTENCIA ="regAsistencia";
	public static final String TAG_VALIDACION_ALUMNONOMBRE ="alumnoNombre";
	public static final String TAG_VALIDACION_DIAEVENTO ="diaEvento";
	
	public static final String NUM_CERO_CADENA ="0";
	
	public static final Integer DGGPB = 377;
	public static final Integer ORG_GUB_SEDESOL = 13;
	
	public static final Integer TPO_CALIFICACION_PROMEDIO = 1;
	public static final Integer TPO_CALIFICACION_SUMA = 2;
	public static final Integer TPO_CALIFICACION_CUALITATIVO = 3;
	
	public static final String MOODLE_ACTIVITY_COURSE ="course";
	
	//Badges
	public static final Integer ID_ESTATUS_DEFAULT = 1;
	public static final String NAVEGA_MIS_CURSOS="MIS_CURSOS";
	public static final String NAVEGA_TAREAS_PROGRAMADAS = "TAREAS_PROGRAMADAS";
	
	//Posterior al dia en que finaliza un evento de capacitación las fechas de apertura de las encuestas se abren a los días:
	public static final Integer FECHA_APERTURA_REACCION = 0;
	public static final Integer FECHA_APERTURA_COMPORTAMIENTO = 60;
	public static final Integer FECHA_APERTURA_RESULTADO = 180;
	
	//Posterior a la fecha de apertura de las encuestas, las fechas límite para responder son a los días:
	public static final Integer FECHA_LIMITE_REACCION = 14;
	public static final Integer FECHA_LIMITE_COMPORTAMIENTO = 14;
	public static final Integer FECHA_LIMITE_RESULTADO = 14;
	//NOTA: Este valor debe actulizarse tambien en ContantesGestor.xml
	//ESCALA DE EVALUACION ENCUESTAS SOBRE EVENTOS
	public static final Double ESCALA_EVALUACION_ENCUESTA = (double) 10;
        
        public static final String NOMBRE_XML ="/plantilla.xml";

}
