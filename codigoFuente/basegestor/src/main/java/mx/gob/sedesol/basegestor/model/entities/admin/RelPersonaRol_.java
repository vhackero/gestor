package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.422-0500")
@StaticMetamodel(RelPersonaRol.class)
public class RelPersonaRol_ {
	public static volatile SingularAttribute<RelPersonaRol, Long> idPersonaRol;
	public static volatile SingularAttribute<RelPersonaRol, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelPersonaRol, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPersonaRol, Long> usuarioModifico;
	public static volatile SingularAttribute<RelPersonaRol, TblPersona> persona;
	public static volatile SingularAttribute<RelPersonaRol, CatRol> rol;
	public static volatile SingularAttribute<RelPersonaRol, Integer> activo;
}
