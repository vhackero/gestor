package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-13T11:05:00.006-0600")
@StaticMetamodel(CatCompetenciaEspecifica.class)
public class CatCompetenciaEspecifica_ {
	public static volatile SingularAttribute<CatCompetenciaEspecifica, Integer> id;
	public static volatile SingularAttribute<CatCompetenciaEspecifica, Byte> activo;
	public static volatile SingularAttribute<CatCompetenciaEspecifica, String> descripcion;
	public static volatile SingularAttribute<CatCompetenciaEspecifica, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatCompetenciaEspecifica, Date> fechaRegistro;
	public static volatile SingularAttribute<CatCompetenciaEspecifica, String> nombre;
	public static volatile SingularAttribute<CatCompetenciaEspecifica, Integer> orden;
	public static volatile SingularAttribute<CatCompetenciaEspecifica, BigInteger> usuarioModifico;
}
