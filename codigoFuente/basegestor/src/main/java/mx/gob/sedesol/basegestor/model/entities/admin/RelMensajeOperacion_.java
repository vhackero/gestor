package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.077-0600")
@StaticMetamodel(RelMensajeOperacion.class)
public class RelMensajeOperacion_ {
	public static volatile SingularAttribute<RelMensajeOperacion, Long> idMensajeOperacion;
	public static volatile SingularAttribute<RelMensajeOperacion, TblFuncionalidad> funcionalidad;
	public static volatile SingularAttribute<RelMensajeOperacion, String> titulo;
	public static volatile SingularAttribute<RelMensajeOperacion, String> mensaje;
	public static volatile SingularAttribute<RelMensajeOperacion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelMensajeOperacion, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelMensajeOperacion, Long> usuarioModifico;
	public static volatile SingularAttribute<RelMensajeOperacion, Boolean> activo;
	public static volatile SingularAttribute<RelMensajeOperacion, Integer> tipo;
}
