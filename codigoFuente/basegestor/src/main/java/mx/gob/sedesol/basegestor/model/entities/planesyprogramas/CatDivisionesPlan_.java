package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-29T10:57:04.324-0600")
@StaticMetamodel(CatDivisionesPlan.class)
public class CatDivisionesPlan_ {
	public static volatile SingularAttribute<CatDivisionesPlan, Integer> id;
	public static volatile SingularAttribute<CatDivisionesPlan, Integer> activo;
	public static volatile SingularAttribute<CatDivisionesPlan, String> descripcion;
	public static volatile SingularAttribute<CatDivisionesPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatDivisionesPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<CatDivisionesPlan, String> nombre;
	public static volatile SingularAttribute<CatDivisionesPlan, Integer> orden;
	public static volatile SingularAttribute<CatDivisionesPlan, Long> usuarioModifico;
}
