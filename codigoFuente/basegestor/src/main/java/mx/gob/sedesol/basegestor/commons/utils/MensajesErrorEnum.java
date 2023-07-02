
package mx.gob.sedesol.basegestor.commons.utils;


public enum MensajesErrorEnum {
	
	EXITO_PERSISTENCIA_DATOS("EXITO_PERSISTE_DATOS", "El registro se guardo con exito."),
	EXITO_ACTUALIZACION_DATOS("EXITO_ACTUALIZ_DATOS", "El registro se actualizo con exito."),
	EXITO_ELIMINACION_DATOS("EXITO_ELIMINA_DATOS", "El registro se elimino con exito."),
	ERROR_PERSISTENCIA_DATOS("ERR_PERSISTE_DATOS", "Ocurrio un error al intentar guardar los datos."),
	ERROR_ACTUALIZACION_DATOS("ERR_ACTUALIZA_DATOS", "Ocurrio un error al intentar actualizar los datos."),
	ERROR_ELIMINAR_DATOS("ERR_ELIMINA_DATOS","Ocurrio un error al intentar  la eliminacion logica de los datos."),
	ERROR_BUSCAR_DATOS("ERR_BUSQUEDA_DATOS", "Ocurrio un error en la busqueda de los datos."),
	ERROR_CARGA_DATOS("ERR_CARGA_DATOS", "Ocurrio un error en la carga de los datos."),
	//TODO:Verificar con Analistas
	ERROR_ID_REQ("ERR_ID_REQ","El ID es requerido"),
	ERROR_NOMBRE_REQ("ERR_NOM_REQ","El nombre es requerido"),
	ERROR_USUARIO_REQ("ERR_NOM_REQ","El campo usuario es requerido"),
	ERROR_DESCRIP_REQ("ERR_DESCRIP_REQ","La descripcion es requerido"),
	ERROR_FECHA_REGISTRO_REQ("ERR_FECHA_REG_REQ","La fecha de registro es requerida"),
	ERROR_FECHA_UPDATE_REQ("ERR_FECHA_UPDATE_REQ","La fecha de actualizacion es requerida"),
	ERROR_ESTATUS_REQ("ERR_ESTATUS_REQ","El estatus del registro es requerido"),
	ERROR_DATOS_DIFERENTES("ERR_DATOS_DIF","Los identificadores no pueden modificarse."),
	ERROR_CONTRASENIA_REQ("ERROR_CONTRASENIA_REQ","La contrasenia es requerida"),
	ERROR_CLAVE_REQ("ERROR_CLAVE_REQ","La clave es requerida"),
	ERROR_VALOR_REQ("ERROR_VALOR_REQ","El valor es requerido"),
	ERROR_ENTIDADFED_REQ("ERROR_ENTIDADFED_REQ","La entidad federativa es requerida"),
	ERROR_TIPO_ASENTAMIENTO_REQ("ERROR_TIPO_ASENTAMIENTO_REQ","El tipo de asentamiento es requerido"),
	ERROR_MUNICIPIO_REQ("ERROR_MUNICIPIO_REQ","El municipio es requerido"),
	ERROR_CODIGO_POSTAL_REQ("ERROR_CODIGO_POSTAL_REQ","El codigo postal es requerido"),
	ERROR_APELLIDO_PATERNO_REQ("ERROR_APELLIDO_PATERNO_REQ","El apellido paterno es requerido"),
	ERROR_TITULO_REQ("ERROR_TITULO_REQ","El título es requerido"),
	ERROR_MENSAJE_REQ("ERROR_TITULO_REQ","El mensaje es requerido"),
	ERROR_PERSONA_REQ("ERROR_PERSONA_REQ","La persona es requerida"),
	ERROR_NOTIFICACION_REQ("ERROR_NOTIFICACION_REQ","La notificación es requerida"),
	ERROR_OPERACION_REQ("ERROR_OPERACION_REQ","La operación es requerida"),
	ERROR_TIPO_REQ("ERROR_TIPO_REQ","El tipo es requerido"),
	ERROR_DATO_REQUERIDO("ERR_DATO_REQ","El dato es requerido."),
	ERROR_USUARIO_NO_EXISTE("ERR_USU_NO_EXISTE", "El usuario no existe."),
	ERROR_VALIDA_TOKEN("ERR_TOKEN_USU", "El token del usuario a expirado o no existe."),
	ERROR_DATO_NULO("ERR_DATO_NULO", "El objeto es nulo");

	private String id;
	private String mensaje;
	
	MensajesErrorEnum(String id, String mensaje){
		this.id= id;
		this.mensaje = mensaje;
	}
	
	public String getId() {
		return id;
	}

	public String getMensaje() {
		return mensaje;
	}

}
