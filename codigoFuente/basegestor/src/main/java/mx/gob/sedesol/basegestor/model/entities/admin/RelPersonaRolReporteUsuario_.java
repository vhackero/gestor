package mx.gob.sedesol.basegestor.model.entities.admin;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2016-07-13T12:40:10.422-0500")
@StaticMetamodel(RelPersonaRolReporteUsuario_.class)
public class RelPersonaRolReporteUsuario_ {
	public static volatile SingularAttribute<RelPersonaRolReporteUsuario, Long> idPersonaRol;
	public static volatile SingularAttribute<RelPersonaRolReporteUsuario, TblPersonaReporteUsuario> persona;
	public static volatile SingularAttribute<RelPersonaRolReporteUsuario, CatRolReporteUsuario> rol;
}
