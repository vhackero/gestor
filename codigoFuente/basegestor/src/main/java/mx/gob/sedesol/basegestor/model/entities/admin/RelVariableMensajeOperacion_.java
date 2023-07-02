package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.083-0600")
@StaticMetamodel(RelVariableMensajeOperacion.class)
public class RelVariableMensajeOperacion_ {
	public static volatile SingularAttribute<RelVariableMensajeOperacion, Integer> idVariableMensajeOperacion;
	public static volatile SingularAttribute<RelVariableMensajeOperacion, TblFuncionalidad> funcionalidad;
	public static volatile SingularAttribute<RelVariableMensajeOperacion, String> descripcion;
	public static volatile SingularAttribute<RelVariableMensajeOperacion, String> clave;
	public static volatile SingularAttribute<RelVariableMensajeOperacion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelVariableMensajeOperacion, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelVariableMensajeOperacion, Long> usuarioModifico;
}
