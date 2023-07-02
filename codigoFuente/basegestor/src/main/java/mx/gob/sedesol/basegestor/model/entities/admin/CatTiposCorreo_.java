package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.376-0500")
@StaticMetamodel(CatTiposCorreo.class)
public class CatTiposCorreo_ {
	public static volatile SingularAttribute<CatTiposCorreo, Integer> idTipoCorreo;
	public static volatile SingularAttribute<CatTiposCorreo, String> descripcion;
	public static volatile SingularAttribute<CatTiposCorreo, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatTiposCorreo, Date> fechaRegistro;
	public static volatile SingularAttribute<CatTiposCorreo, Long> usuarioModifico;
	public static volatile ListAttribute<CatTiposCorreo, RelPersonaCorreo> relPersonaCorreos;
	public static volatile SingularAttribute<CatTiposCorreo, Integer> activo;
}
