package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-29T10:57:04.324-0600")
@StaticMetamodel(CatCreditosPlan.class)
public class CatCreditosPlan_ {
	public static volatile SingularAttribute<CatCreditosPlan, Integer> id;
	public static volatile SingularAttribute<CatCreditosPlan, Integer> activo;
	public static volatile SingularAttribute<CatCreditosPlan, String> descripcion;
	public static volatile SingularAttribute<CatCreditosPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatCreditosPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatCreditosPlan, String> nombre;
	public static volatile SingularAttribute<CatCreditosPlan, Integer> orden;
	public static volatile SingularAttribute<CatCreditosPlan, Long> usuarioModifico;
}
