package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.255-0600")
@StaticMetamodel(TblCatalogoGeneral.class)
public class TblCatalogoGeneral_ {
	public static volatile SingularAttribute<TblCatalogoGeneral, Integer> idCatGeneral;
	public static volatile SingularAttribute<TblCatalogoGeneral, Integer> activo;
	public static volatile SingularAttribute<TblCatalogoGeneral, String> claveCatalogo;
	public static volatile SingularAttribute<TblCatalogoGeneral, Date> fechaRegistro;
	public static volatile SingularAttribute<TblCatalogoGeneral, Long> usuarioModifico;
	//public static volatile ListAttribute<TblCatalogoGeneral, RelCatalogoGeneralValor> relCatalogoGeneralValores;
	public static volatile ListAttribute<TblCatalogoGeneral, RelCatalogoGeneralValor> datosValorCatGeneral;

}
