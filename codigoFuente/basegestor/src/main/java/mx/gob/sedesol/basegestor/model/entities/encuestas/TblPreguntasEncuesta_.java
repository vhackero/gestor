package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-11-29T11:01:50.138-0600")
@StaticMetamodel(TblPreguntasEncuesta.class)
public class TblPreguntasEncuesta_ {
	public static volatile SingularAttribute<TblPreguntasEncuesta, Integer> id;
	public static volatile SingularAttribute<TblPreguntasEncuesta, Byte> activo;
	public static volatile SingularAttribute<TblPreguntasEncuesta, String> descripcion;
	public static volatile SingularAttribute<TblPreguntasEncuesta, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblPreguntasEncuesta, Date> fechaRegistro;
	public static volatile SingularAttribute<TblPreguntasEncuesta, String> nombre;
	public static volatile SingularAttribute<TblPreguntasEncuesta, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<TblPreguntasEncuesta, TblEncuesta> encuesta;
}
