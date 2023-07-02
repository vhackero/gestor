package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.432-0500")
@StaticMetamodel(RelRolFuncionalidad.class)
public class RelRolFuncionalidad_ {
	public static volatile SingularAttribute<RelRolFuncionalidad, Long> idRolFuncionalidad;
	public static volatile SingularAttribute<RelRolFuncionalidad, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelRolFuncionalidad, Date> fechaRegistro;
	public static volatile SingularAttribute<RelRolFuncionalidad, Long> usuarioModifico;
	public static volatile SingularAttribute<RelRolFuncionalidad, TblFuncionalidad> funcionalidad;
	public static volatile SingularAttribute<RelRolFuncionalidad, CatRol> rol;
	public static volatile SingularAttribute<RelRolFuncionalidad, Integer> activo;
}
