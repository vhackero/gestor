package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatPeriodo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value="Dali", date="2016-12-19T13:30:53.057-0600")
@StaticMetamodel(CatPeriodo.class)
public class CatPeriodo_ {
	public static volatile SingularAttribute<CatPeriodo, Integer> idPeriodo;
	public static volatile SingularAttribute<CatPeriodo, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatPeriodo, Date> fechaRegistro;
	public static volatile SingularAttribute<CatPeriodo, String> nombre;
	public static volatile SingularAttribute<CatPeriodo, Long> usuarioModifica;
	public static volatile SingularAttribute<CatPeriodo, Boolean> activo;
}
