package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CatObjetoCurricular.class)
public class CatObjetoCurricular_ {
	public static volatile SingularAttribute<CatObjetoCurricular, Integer> id;
	public static volatile SingularAttribute<CatObjetoCurricular, Integer> activo;
	public static volatile SingularAttribute<CatObjetoCurricular, String> descripcion;
	public static volatile SingularAttribute<CatObjetoCurricular, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatObjetoCurricular, Date> fechaRegistro;
	public static volatile SingularAttribute<CatObjetoCurricular, String> nombre;
	public static volatile SingularAttribute<CatObjetoCurricular, Integer> orden;
	public static volatile SingularAttribute<CatObjetoCurricular, Long> usuarioModifico;
	public static volatile SingularAttribute<CatObjetoCurricular, TblMallaCurricular> tblMallaCurricular;
}
