package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-17T10:59:51.099-0600")
@StaticMetamodel(RelDiasEventoCapacitacion.class)
public class RelDiasEventoCapacitacion_ {
	public static volatile SingularAttribute<RelDiasEventoCapacitacion, Integer> id;
	public static volatile SingularAttribute<RelDiasEventoCapacitacion, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelDiasEventoCapacitacion, Date> fechaEventoCapacitacion;
	public static volatile SingularAttribute<RelDiasEventoCapacitacion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelDiasEventoCapacitacion, Integer> idGrupo;
	public static volatile SingularAttribute<RelDiasEventoCapacitacion, BigInteger> usuarioModifico;
}
