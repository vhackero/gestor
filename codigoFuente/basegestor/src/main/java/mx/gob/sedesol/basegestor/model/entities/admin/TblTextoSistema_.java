package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-20T10:33:35.916-0600")
@StaticMetamodel(TblTextoSistema.class)
public class TblTextoSistema_ {
	public static volatile SingularAttribute<TblTextoSistema, String> clave;
	public static volatile SingularAttribute<TblTextoSistema, TblFuncionalidad> funcionalidad;
	public static volatile SingularAttribute<TblTextoSistema, String> valor;
	public static volatile SingularAttribute<TblTextoSistema, Date> fechaRegistro;
	public static volatile SingularAttribute<TblTextoSistema, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblTextoSistema, Long> usuarioModifico;
}
