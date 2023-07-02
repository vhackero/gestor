package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

@Generated(value="Dali", date="2017-03-21T12:10:49.869-0600")
@StaticMetamodel(TblAmbienteVirtualAprendizaje.class)
public class TblAmbienteVirtualAprendizaje_ {
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, Integer> id;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, Boolean> activo;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, Date> fechaRegistro;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, Byte> porcentajeAvance;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, Boolean> validacionAva;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, CatEstadoAva> catEstadoAva;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, RelPersonaResponsabilidades> personaResponsabilidades;
	public static volatile SingularAttribute<TblAmbienteVirtualAprendizaje, TblEvento> eventoCapacitacion;
}
