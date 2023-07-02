package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.315-0500")
@StaticMetamodel(CatPais.class)
public class CatPais_ {
	public static volatile SingularAttribute<CatPais, String> idPais;
	public static volatile SingularAttribute<CatPais, String> abreviatura;
	public static volatile SingularAttribute<CatPais, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatPais, Date> fechaRegistro;
	public static volatile SingularAttribute<CatPais, String> nombre;
	public static volatile SingularAttribute<CatPais, Boolean> activo;
	public static volatile SingularAttribute<CatPais, Long> usuarioModifico;
	public static volatile ListAttribute<CatPais, CatEntidadFederativa> catEntidadesFederativas;
}
