package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-24T12:23:59.515-0500")
@StaticMetamodel(RelFoaActividadReforzamiento.class)
public class RelFoaActividadReforzamiento_ {
	public static volatile SingularAttribute<RelFoaActividadReforzamiento, RelFoaActividadReforzamientoPK> id;
	public static volatile SingularAttribute<RelFoaActividadReforzamiento, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelFoaActividadReforzamiento, Date> fechaRegistro;
	public static volatile SingularAttribute<RelFoaActividadReforzamiento, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelFoaActividadReforzamiento, CatActividadReforzamientoFoa> catActividadReforzamientoFoa;
	public static volatile SingularAttribute<RelFoaActividadReforzamiento, TblFichaDescriptivaObjetoAprendizaje> tblFichaDescriptivaObjetoAprendizaje;
}
