package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-22T17:12:40.152-0600")
@StaticMetamodel(CatMobiliario.class)
public class CatMobiliario_ {
	public static volatile SingularAttribute<CatMobiliario, Integer> idCatMobiliario;
	public static volatile SingularAttribute<CatMobiliario, String> descripcion;
	public static volatile SingularAttribute<CatMobiliario, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatMobiliario, Date> fechaRegistro;
	public static volatile SingularAttribute<CatMobiliario, String> nombre;
	public static volatile SingularAttribute<CatMobiliario, BigInteger> usuarioModifico;
}
