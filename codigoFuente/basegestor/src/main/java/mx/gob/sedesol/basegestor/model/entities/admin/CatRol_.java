package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.057-0600")
@StaticMetamodel(CatRol.class)
public class CatRol_ {
	public static volatile SingularAttribute<CatRol, Integer> idRol;
	public static volatile SingularAttribute<CatRol, String> clave;
	public static volatile SingularAttribute<CatRol, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatRol, Date> fechaRegistro;
	public static volatile SingularAttribute<CatRol, String> nombre;
	public static volatile SingularAttribute<CatRol, Long> usuarioModifica;
	public static volatile SingularAttribute<CatRol, Boolean> activo;
	public static volatile ListAttribute<CatRol, RelPersonaRol> relPersonaRoles;
	public static volatile ListAttribute<CatRol, RelRolFuncionalidad> relRolFuncionalidads;
}
