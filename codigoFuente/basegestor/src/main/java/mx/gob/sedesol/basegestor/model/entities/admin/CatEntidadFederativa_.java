package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.237-0500")
@StaticMetamodel(CatEntidadFederativa.class)
public class CatEntidadFederativa_ {
	public static volatile SingularAttribute<CatEntidadFederativa, Integer> idEntidadFederativa;
	public static volatile SingularAttribute<CatEntidadFederativa, String> abreviatura;
	public static volatile SingularAttribute<CatEntidadFederativa, String> abreviaturaCurp;
	public static volatile SingularAttribute<CatEntidadFederativa, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatEntidadFederativa, Date> fechaRegistro;
	public static volatile SingularAttribute<CatEntidadFederativa, String> nombre;
	public static volatile SingularAttribute<CatEntidadFederativa, Boolean> activo;
	public static volatile SingularAttribute<CatEntidadFederativa, Long> usuarioModifico;
	public static volatile SingularAttribute<CatEntidadFederativa, CatPais> catPais;
	public static volatile ListAttribute<CatEntidadFederativa, CatMunicipio> catMunicipios;
}
