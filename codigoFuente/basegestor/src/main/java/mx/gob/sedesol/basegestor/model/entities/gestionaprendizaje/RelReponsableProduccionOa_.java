package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;

@Generated(value="Dali", date="2017-02-23T16:26:10.940-0600")
@StaticMetamodel(RelReponsableProduccionOa.class)
public class RelReponsableProduccionOa_ {
	public static volatile SingularAttribute<RelReponsableProduccionOa, Integer> idRelResponsableProduccionOa;
	public static volatile SingularAttribute<RelReponsableProduccionOa, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelReponsableProduccionOa, Date> fechaRegistro;
	public static volatile SingularAttribute<RelReponsableProduccionOa, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelReponsableProduccionOa, RelPersonaResponsabilidades> relPersonaResponsabilidade;
	public static volatile SingularAttribute<RelReponsableProduccionOa, RelUnidadOaAva> relUnidadOaAva;
}
