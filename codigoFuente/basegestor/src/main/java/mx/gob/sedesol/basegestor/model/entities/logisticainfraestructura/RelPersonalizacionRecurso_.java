package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-06T18:26:06.342-0600")
@StaticMetamodel(RelPersonalizacionRecurso.class)
public class RelPersonalizacionRecurso_ {
	public static volatile SingularAttribute<RelPersonalizacionRecurso, RelPersonalizacionRecursoPK> id;
	public static volatile SingularAttribute<RelPersonalizacionRecurso, Byte> activo;
	public static volatile SingularAttribute<RelPersonalizacionRecurso, Integer> cantidad;
	public static volatile SingularAttribute<RelPersonalizacionRecurso, Date> fechaRegistro;
	public static volatile SingularAttribute<RelPersonalizacionRecurso, BigInteger> usuarioRegistro;
	public static volatile SingularAttribute<RelPersonalizacionRecurso, TblPersonalizacionArea> tblPersonalizacionArea;
}
