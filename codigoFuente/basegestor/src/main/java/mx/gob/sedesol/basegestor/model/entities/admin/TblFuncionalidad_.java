package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.441-0500")
@StaticMetamodel(TblFuncionalidad.class)
public class TblFuncionalidad_ {
	public static volatile SingularAttribute<TblFuncionalidad, Long> idFuncionalidad;
	public static volatile SingularAttribute<TblFuncionalidad, String> clave;
	public static volatile SingularAttribute<TblFuncionalidad, String> descripcion;
	public static volatile SingularAttribute<TblFuncionalidad, Long> usuarioModifico;
	public static volatile SingularAttribute<TblFuncionalidad, Date> fechaRegistro;
	public static volatile SingularAttribute<TblFuncionalidad, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblFuncionalidad, Boolean> activo;
	public static volatile SingularAttribute<TblFuncionalidad, TblFuncionalidad> funcionalidadPadre;
}
