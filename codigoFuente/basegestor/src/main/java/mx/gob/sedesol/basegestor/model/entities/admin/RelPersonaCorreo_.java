package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.415-0500")
@StaticMetamodel(RelPersonaCorreo.class)
public class RelPersonaCorreo_ {
	public static volatile SingularAttribute<RelPersonaCorreo, Integer> idPersonaCorreo;
	public static volatile SingularAttribute<RelPersonaCorreo, String> correoElectronico;
	public static volatile SingularAttribute<RelPersonaCorreo, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelPersonaCorreo, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPersonaCorreo, Long> usuarioModifico;
	public static volatile SingularAttribute<RelPersonaCorreo, TblPersona> persona;
	public static volatile SingularAttribute<RelPersonaCorreo, CatTiposCorreo> tipoCorreo;
	public static volatile SingularAttribute<RelPersonaCorreo, Integer> activo;
	public static volatile SingularAttribute<RelPersonaCorreo, Integer> nivelPrioridad;
}
