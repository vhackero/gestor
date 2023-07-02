package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

@Generated(value="Dali", date="2017-05-15T12:59:45.756-0500")
@StaticMetamodel(RelEncuestaEventoCapacitacion.class)
public class RelEncuestaEventoCapacitacion_ {
	public static volatile SingularAttribute<RelEncuestaEventoCapacitacion, RelEncuestaEventoCapacitacionPK> id;
	public static volatile SingularAttribute<RelEncuestaEventoCapacitacion, Date> fechaRegistro;
	public static volatile SingularAttribute<RelEncuestaEventoCapacitacion, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelEncuestaEventoCapacitacion, TblEncuesta> tblEncuesta;
	public static volatile SingularAttribute<RelEncuestaEventoCapacitacion, TblEvento> tblEvento;
}
