package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-11T12:20:55.499-0600")
@StaticMetamodel(CatEncuestaContexto.class)
public class CatEncuestaContexto_ {
	public static volatile SingularAttribute<CatEncuestaContexto, Integer> id;
	public static volatile SingularAttribute<CatEncuestaContexto, String> descripcion;
	public static volatile SingularAttribute<CatEncuestaContexto, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEncuestaContexto, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEncuestaContexto, String> nombre;
	public static volatile SingularAttribute<CatEncuestaContexto, BigInteger> usuarioModifico;
	public static volatile ListAttribute<CatEncuestaContexto, CatEncuestaTipo> catEncuestaTipos;
	public static volatile SingularAttribute<CatEncuestaContexto, Integer> activo;
	public static volatile SingularAttribute<CatEncuestaContexto, Integer> orden;
}
