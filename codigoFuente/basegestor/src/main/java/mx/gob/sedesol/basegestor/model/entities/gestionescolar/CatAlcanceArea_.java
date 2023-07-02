package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-03T12:56:58.721-0600")
@StaticMetamodel(CatAlcanceArea.class)
public class CatAlcanceArea_ {
	public static volatile SingularAttribute<CatAlcanceArea, Integer> id;
	public static volatile SingularAttribute<CatAlcanceArea, Byte> activo;
	public static volatile SingularAttribute<CatAlcanceArea, String> descripcion;
	public static volatile SingularAttribute<CatAlcanceArea, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatAlcanceArea, Date> fechaRegistro;
	public static volatile SingularAttribute<CatAlcanceArea, String> nombre;
	public static volatile SingularAttribute<CatAlcanceArea, Integer> orden;
	public static volatile SingularAttribute<CatAlcanceArea, BigInteger> usuarioModifico;
}
