package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.269-0600")
@StaticMetamodel(TblMallaCurricular.class)
public class TblMallaCurricular_ {
	public static volatile SingularAttribute<TblMallaCurricular, Integer> id;
	public static volatile SingularAttribute<TblMallaCurricular, Integer> activo;
	public static volatile SingularAttribute<TblMallaCurricular, String> descripcion;
	public static volatile SingularAttribute<TblMallaCurricular, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblMallaCurricular, Date> fechaRegistro;
	public static volatile SingularAttribute<TblMallaCurricular, TblMallaCurricular> mallaCurricularPadre;
	public static volatile ListAttribute<TblMallaCurricular, TblMallaCurricular> lstHijosMallaCurr;
	public static volatile SingularAttribute<TblMallaCurricular, Integer> idTipoPlan;
	public static volatile SingularAttribute<TblMallaCurricular, String> nombre;
	public static volatile SingularAttribute<TblMallaCurricular, Long> usuarioModifico;
	public static volatile SingularAttribute<TblMallaCurricular, CatObjetoCurricular> objetoCurricular;
}
