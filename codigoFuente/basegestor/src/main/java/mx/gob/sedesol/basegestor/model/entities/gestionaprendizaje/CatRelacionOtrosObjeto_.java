package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.510-0500")
@StaticMetamodel(CatRelacionOtrosObjeto.class)
public class CatRelacionOtrosObjeto_ {
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, Integer> id;
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, Byte> activo;
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, String> descripcion;
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, Date> fechaRegistro;
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, String> nombre;
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, Integer> orden;
	public static volatile SingularAttribute<CatRelacionOtrosObjeto, BigInteger> usuarioModifico;
}
