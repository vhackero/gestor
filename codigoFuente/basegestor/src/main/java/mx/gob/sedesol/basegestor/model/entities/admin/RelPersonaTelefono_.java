package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.427-0500")
@StaticMetamodel(RelPersonaTelefono.class)
public class RelPersonaTelefono_ {
	public static volatile SingularAttribute<RelPersonaTelefono, Integer> idPersonaTelefono;
	public static volatile SingularAttribute<RelPersonaTelefono, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelPersonaTelefono, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPersonaTelefono, String> telefono;
	public static volatile SingularAttribute<RelPersonaTelefono, Long> usuarioModifico;
	public static volatile SingularAttribute<RelPersonaTelefono, TblPersona> persona;
	public static volatile SingularAttribute<RelPersonaTelefono, CatTiposTelefono> catTiposTelefono;
	public static volatile SingularAttribute<RelPersonaTelefono, Integer> activo;
}
