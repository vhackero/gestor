package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-24T11:05:34.720-0600")
@StaticMetamodel(RelReponsableProduccionEc.class)
public class RelReponsableProduccionEc_ {
	public static volatile SingularAttribute<RelReponsableProduccionEc, Integer> idEventoCapacitacion;
	public static volatile SingularAttribute<RelReponsableProduccionEc, Integer> idReponsableProduccion;
	public static volatile SingularAttribute<RelReponsableProduccionEc, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelReponsableProduccionEc, Date> fechaRegistro;
	public static volatile SingularAttribute<RelReponsableProduccionEc, BigInteger> usuarioModifico;
}
