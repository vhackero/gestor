package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-20T13:19:11.072-0500")
@StaticMetamodel(RelCatalogoGeneralValor.class)
public class RelCatalogoGeneralValor_ {
	public static volatile SingularAttribute<RelCatalogoGeneralValor, Integer> activo;
	public static volatile SingularAttribute<RelCatalogoGeneralValor, String> etiqueta;
	public static volatile SingularAttribute<RelCatalogoGeneralValor, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelCatalogoGeneralValor, Date> fechaRegistro;
	public static volatile SingularAttribute<RelCatalogoGeneralValor, Integer> orden;
	public static volatile SingularAttribute<RelCatalogoGeneralValor, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<RelCatalogoGeneralValor, String> valor;
	public static volatile SingularAttribute<RelCatalogoGeneralValor, TblCatalogoGeneral> tblCatalogoGeneral;
}
