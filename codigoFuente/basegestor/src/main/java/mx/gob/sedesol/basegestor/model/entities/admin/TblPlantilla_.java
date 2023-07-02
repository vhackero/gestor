package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.104-0600")
@StaticMetamodel(TblPlantilla.class)
public class TblPlantilla_ {
	
	public static volatile SingularAttribute<TblPlantilla, Long> idPlantilla;
	public static volatile SingularAttribute<TblPlantilla, String> nombre;
	
	public static volatile SingularAttribute<TblPlantilla, Long> usuarioCreo;
	public static volatile SingularAttribute<TblPlantilla, Long> usuarioModifico;
	public static volatile SingularAttribute<TblPlantilla, Date> fechaCreacion;
	public static volatile SingularAttribute<TblPlantilla, Date> fechaActualizacion;
	
}
