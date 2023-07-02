package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-17T16:41:17.299-0600")
@StaticMetamodel(DetEstUnidadDidactica.class)
public class DetEstUnidadDidactica_ {
	public static volatile SingularAttribute<DetEstUnidadDidactica, Integer> idUnidadDidactica;
	public static volatile SingularAttribute<DetEstUnidadDidactica, Integer> activo;
	public static volatile SingularAttribute<DetEstUnidadDidactica, String> evaluacion;
	public static volatile SingularAttribute<DetEstUnidadDidactica, Date> fechaActualizacion;
	public static volatile SingularAttribute<DetEstUnidadDidactica, Date> fechaRegistro;
	public static volatile SingularAttribute<DetEstUnidadDidactica, String> fuentesInformacion;
	public static volatile SingularAttribute<DetEstUnidadDidactica, Integer> numSubtemas;
	public static volatile SingularAttribute<DetEstUnidadDidactica, String> objetivosEspecificos;
	public static volatile SingularAttribute<DetEstUnidadDidactica, String> tituloUa;
	public static volatile SingularAttribute<DetEstUnidadDidactica, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<DetEstUnidadDidactica, TblEstructuraTematica> tblEstructuraTematica;
	public static volatile ListAttribute<DetEstUnidadDidactica, TblSubtemasUdidactica> tblSubtemasUdidacticas;
}
