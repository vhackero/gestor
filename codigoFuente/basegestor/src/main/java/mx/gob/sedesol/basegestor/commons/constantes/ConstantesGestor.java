/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.constantes;

/**
 * @author PlanetMedia
 *
 */
public final class ConstantesGestor {				   
	public static final String ENCUESTA_FACILITADOR = "ID_ENCUESTA_FACILITADOR";
	public static final String ENCUESTA_CURSO = "ID_ENCUESTA_CURSO";
	
	public static final String RUTA_POR_DEFECTO = "/sedesol/tmp";
	public static final String PARAMETRO_RUTA_PRINCIPAL = "RUTA_PRINCIPAL";
	public static final String PARAMETRO_RUTA_ADJUNTOS = "RUTA_ADJUNTOS";
	public static final String PARAMETRO_RUTA_RECURSOS = "RUTA_RECURSOS";
	public static final String PARAMETRO_RUTA_CARGA_MASIVA = "RUTA_CARGA_MASIVA";
	
	public static final String NOMBRE_DIRECTOR_GENERAL = "NOMBRE_DIRECTOR_GENERAL";
	
	public static final String CIUDAD_CONSTANCIA = "CIUDAD_CONSTANCIA";
	
	public static final String PARAMETRO_RUTA_FOTOS_USUARIOS = "RUTA_FOTOS_USUARIOS";

	public static final String PARAMETRO_RUTA_IMAGENES_EVENTOS = "RUTA_IMAGENES_EVENTOS";
	
	public static final String PARAMETRO_RUTA_IMAGENES_DOCUMENTOS = "RUTA_IMAGENES_DOCUMENTOS";

    public static final String PARAMETRO_RUTA_INSIGNIAS = "RUTA_INSIGNIAS";

	public static final String PARAMETRO_NOMBRE_FOTO_COMUN = "NOMBRE_FOTO_COMUN";
	public static final String PARAMETRO_NOMBRE_IMAGEN_EVENTO_COMUN = "NOMBRE_IMAGEN_EVENTO_COMUN";
	public static final String PARAMETRO_RUTA_UNDERTOW = "RUTA_UNDERTOW";
	public static final String RUTA_AREAS = "RUTA_AREAS";
	public static final String NOMBRE_FOTO_AREA_COMUN = "NOMBRE_FOTO_AREA_COMUN";

	public static final String PARAM_USUARIO_SESSION = "usuario_session";
	public static final String ROLE_ALUMNO = "ROLE_ALUMNO";
	public static final int ROL_ALUMNO = 2;
	public static final int ACTIVO = 1;
	public static final int INACTIVO = 0;
	public static final int PRIMER_ELEMENTO = 0;
	
	public static final int NOTIFICACION_MENSAJE = 3;
	public static final int NOTIFICACION_ALERTA = 1;
	public static final int NOTIFICACION_AVISO = 2;
	
	
	public static final String COLUMNA_TIPO_USUARIO = "tipo_usuario";
	public static final String COLUMNA_USUARIO = "usuario";
	public static final String COLUMNA_CONTRASENIA = "contrase\u00F1a";

	public static final String COLUMNA_PROGRAMA = "programa";
	public static final String COLUMNA_CURP = "curp";
	public static final String COLUMNA_NOMBRE = "nombre";
	public static final String COLUMNA_APELLIDO_PATERNO = "primer_apellido";
	public static final String COLUMNA_APELLIDO_MATERNO = "segundo_apellido";
	public static final String COLUMNA_FECHA_NACIMIENTO = "fecha_nacimiento";
	public static final String COLUMNA_INSTITUCION = "institucion";
	public static final String COLUMNA_CORREO = "correo";
	public static final String COLUMNA_TELEFONO = "telefono";
	public static final String COLUMNA_CELULAR = "celular";
	public static final String COLUMNA_SEDE = "sede";
	public static final String COLUMNA_MUNICIPIO = "municipio";
	public static final String COLUMNA_ORDEN = "orden_gob";
	public static final String COLUMNA_PUESTO = "puesto";
	
	public static final String MENSAJE_CORRECTO = "Correcto";
	public static final String MENSAJE_INCORRECTO = "Ocurri\u00F3 un error al guardar el registro";
	
	public static final Long ID_USER_SYSTEM = 1L;
	
	public static final String TEMA_BASE = "base";
	
	public static final String RUTA_RECURSOS_PUBLICO = "Publico";
	public static final String RUTA_RECURSOS_PRIVADO = "Privado";
	public static final String RUTA_IMAGENES = "img";
	
	public static final Integer VERSION_UNO = 1;
	public static final Integer IDENTIFICADOR_PLAN_SEDESOL =1;
	
	public static final String[] RECURSOS_PRIVADOS = {
			"css/sb-admin-2.css",
			"css/sb-admin-2.min.css",
			"css/timeline.css",
			"img/elearning_logo_d.png",
			"img/elearning_logo_largo.png",
			"img/favicon.ico",
			"js/script.js"};
	
	public static final String[] RECURSOS_PUBLICO = {
			"css/custom.css",
			"css/custom.css.map",
			"css/custom.min.css",
			"img/elearning_logo_d.png",
			"img/elearning_logo_largo.png",
			"img/elearning_logo_negativo.png",
			"img/favicon.ico",
			"js/script.js"};
	
	public static final Integer TIPO_CORREO_INSTITUCIONAL = 1;
	
	public static final Integer TIPO_TELEFONO_INSTITUCIONAL = 1;
	public static final Integer TIPO_TELEFONO_MOVIL = 2;
	
	public static final String ID_PAIS_MEXICO = "MX";
	
	public static final String ARCHIVO_TIPO_EXCEL = "application/vnd.ms-excel";
	public static final String NOMBRE_PLANTILLA_CARGA = "CargaUsuarios.xls";
	public static final String NOMBRE_RESULTADO_CARGA = "Resultado.xls";
	
	public static final String CLASE_UTILIDADES = "Clase para utilidades";
	
	
	public static final int ESTADO_ENCUESTA_BORRADOR = 1;
	public static final int ESTADO_ENCUESTA_COMENTARIOS = 2;
	public static final int ESTADO_ENCUESTA_REVISION = 3;
	public static final int ESTADO_ENCUESTA_APROBADA = 4;
	
	
	
	
	public static final Integer TIPO_BUSQUEDA_FECHA_INICIO_FIN_ENTRE_ = 1;
	public static final Integer TIPO_BUSQUEDA_FECHA_INICIO_ENTRE = 2;
	public static final Integer TIPO_BUSQUEDA_FECHA_FIN_ENTRE = 3;
	
	public static final Integer MODALIDAD_LINEA = 1;
	public static final Integer MODALIDAD_PRESENCIAL = 2;
	public static final Integer MODALIDAD_MIXTO = 3;
	
	public static final Integer TIPO_AREA_INTERNA = 1;
	public static final Integer TIPO_AREA_EXTERNA = 2;
	
	public static final Integer AVA_ESTADO_SOLICITUD = 1;
	public static final Integer AVA_ESTADO_CONTRUCCION= 2;
	public static final Integer AVA_ESTADO_ACTIVO =3;
	
	
	public static final Integer TIPO_RESPONSABILIDAD_FACILITADOR = 1;
	public static final Integer TIPO_RESPONSABILIDAD_COORDINADOR = 2;
	public static final Integer TIPO_RESPONSABILIDAD_PRODUCTOR = 3;
	
	public static final Integer DESTINATARIO_PROGRAMA_SOCIAL = 4;
	public static final Integer PROGRAMA_SOCIAL_CAPTURA_CUIS = 1;
	
	public static final Integer CLASIFICACION_AVA_EVENTO_ANTERIOR = 1;
	public static final Integer CLASIFICACION_AVA_CURSO_MOODLE = 2;
	public static final Integer CLASIFICACION_AVA_NUEVO = 3;
	
	public static final Integer EVEN_CAP_ESTATUS_CALENDARIZADO = 1;
	public static final Integer EVEN_CAP_ESTATUS_BORRADOR = 6;
	
	public static final Integer EVEN_CAP_ESTATUS_EJECUCION = 2;
	
	public static final int POSICION_GENERO_EN_CURP = 10;
	
	public static final String ROLE_COORDINADOR_OPERATIVO = "ROLE_ALUMNO";
	
	public static final Integer NUMERO_CIEN = 100; 
	
	public static final String TIPO_RESPONSABILIDAD_COORDINADOR_ACADEMICO = "Coordinador académico";
	
	public static final int VALOR_INICIAL_ASISTENCIA = 100;
	public static final int VALOR_INICIAL_RETARDO = 80;
	public static final int VALOR_INICIAL_FALTA_JUSTIFICADA = 80;
	public static final int VALOR_INICIAL_FALTA = 0;
	public static final int VALOR_INICIAL_INCONCLUSO = 50;
	
	public static final int VALOR_INICIAL_DICTAMEN_ASISTENCIA = 0;
	
	public static final int VALOR_INICIAL_PORCENTAJE_MIN_ASISTENCIA = 80;
	
	public static final int VALOR_INICIAL_CALIFICACION_MINIMA = 80;
	
	public static final int TIPO_CALIFICACION_PROMEDIO = 1;
	public static final int TIPO_DICTAMEN_PROMEDIO = 1;
	
	public static final Long   MILISEGUNDOS_POR_DIA                         = Long.valueOf(24 * 60 * 60 * 1000);

	/*CONSTANTES ESTATUS FOA*/
	public static final Integer FOA_ESTATUS_BORRADOR = 1;
	public static final Integer FOA_ESTATUS_FINAL = 3;
	
	/*GESTION DEL APRENDIZAJE*/ /*Trofeos*/
	public static final Integer TROFEO_ORO = 1;
	public static final Integer TROFEO_PLATA = 2;
	
	public static final Integer CRITERIO_TROFEO_ORO = 90;
	public static final Integer CRITERIO_TROFEO_PLATA = 80;

	//Posterior al dia en que finaliza un evento de capacitación las fechas de apertura de las encuestas se abren a los días:
	public static final Integer FECHA_APERTURA_REACCION = 0;
	public static final Integer FECHA_APERTURA_COMPORTAMIENTO = 60;
	public static final Integer FECHA_APERTURA_RESULTADO = 180;
	
	//Posterior a la fecha de apertura de las encuestas, las fechas límite para responder son a los días:
	public static final Integer FECHA_LIMITE_REACCION = 14;
	public static final Integer FECHA_LIMITE_COMPORTAMIENTO = 14;
	public static final Integer FECHA_LIMITE_RESULTADO = 14;
	//NOTA: Este valor debe actulizarse tambien en ContantesGestorWeb.xml
	
	public static final Integer NUMERO_DIAS_ANADIDOS_AL_LIMITE_DE_ENCUESTA = 14;


}
