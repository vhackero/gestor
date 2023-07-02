package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.055-0600")
@StaticMetamodel(CatParametroSistema.class)
public class CatParametroSistema_ {
	public static volatile SingularAttribute<CatParametroSistema, String> clave;
	public static volatile SingularAttribute<CatParametroSistema, String> valor;
	public static volatile SingularAttribute<CatParametroSistema, Date> fechaRegistro;
	public static volatile SingularAttribute<CatParametroSistema, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatParametroSistema, Long> usuarioModifico;
}
