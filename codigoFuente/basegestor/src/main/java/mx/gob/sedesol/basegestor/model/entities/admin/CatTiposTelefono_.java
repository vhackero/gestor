package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.406-0500")
@StaticMetamodel(CatTiposTelefono.class)
public class CatTiposTelefono_ {
	public static volatile SingularAttribute<CatTiposTelefono, Integer> idTipoTelefono;
	public static volatile SingularAttribute<CatTiposTelefono, String> descripcion;
	public static volatile SingularAttribute<CatTiposTelefono, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatTiposTelefono, Date> fechaRegistro;
	public static volatile SingularAttribute<CatTiposTelefono, Long> usuarioModifico;
	public static volatile ListAttribute<CatTiposTelefono, RelPersonaTelefono> relPersonaTelefonos;
	public static volatile SingularAttribute<CatTiposTelefono, Integer> activo;
}
