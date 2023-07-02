package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali")
@StaticMetamodel(TblEstPersonalExterno.class)
public class TblEstPersonalExterno_ {
	public static volatile SingularAttribute<TblEstPersonalExterno, Integer> id;
	public static volatile SingularAttribute<TblEstPersonalExterno, Integer> activo;
	public static volatile SingularAttribute<TblEstPersonalExterno, String> descripcion;
	public static volatile SingularAttribute<TblEstPersonalExterno, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblEstPersonalExterno, Date> fechaRegistro;
	public static volatile SingularAttribute<TblEstPersonalExterno, String> nombre;
	public static volatile SingularAttribute<TblEstPersonalExterno, Integer> orden;
	public static volatile SingularAttribute<TblEstPersonalExterno, Long> usuarioModifico;

}
