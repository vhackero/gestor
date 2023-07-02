package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.275-0500")
@StaticMetamodel(CatMunicipio.class)
public class CatMunicipio_ {
	public static volatile SingularAttribute<CatMunicipio, String> idMunicipio;
	public static volatile SingularAttribute<CatMunicipio, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatMunicipio, Date> fechaRegistro;
	public static volatile SingularAttribute<CatMunicipio, String> nombre;
	public static volatile SingularAttribute<CatMunicipio, Boolean> activo;
	public static volatile SingularAttribute<CatMunicipio, Long> usuarioModifico;
	public static volatile ListAttribute<CatMunicipio, CatAsentamiento> catAsentamientos;
	public static volatile SingularAttribute<CatMunicipio, CatEntidadFederativa> entidadFederativa;
}
