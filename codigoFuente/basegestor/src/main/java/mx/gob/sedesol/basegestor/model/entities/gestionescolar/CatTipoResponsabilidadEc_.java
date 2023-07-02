package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-09T16:17:35.441-0600")
@StaticMetamodel(CatTipoResponsabilidadEc.class)
public class CatTipoResponsabilidadEc_ {
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, Integer> id;
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, Byte> activo;
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, String> descripcion;
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, Date> fechaRegistro;
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, String> nombre;
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, Byte> orden;
	public static volatile SingularAttribute<CatTipoResponsabilidadEc, BigInteger> usuarioModifico;
}
