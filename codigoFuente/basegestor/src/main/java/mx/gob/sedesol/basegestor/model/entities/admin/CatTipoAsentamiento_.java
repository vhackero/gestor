package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.362-0500")
@StaticMetamodel(CatTipoAsentamiento.class)
public class CatTipoAsentamiento_ {
	public static volatile SingularAttribute<CatTipoAsentamiento, Integer> idTipoAsentamiento;
	public static volatile SingularAttribute<CatTipoAsentamiento, String> descripcion;
	public static volatile SingularAttribute<CatTipoAsentamiento, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatTipoAsentamiento, Date> fechaRegistro;
	public static volatile SingularAttribute<CatTipoAsentamiento, Long> usuarioModifico;
	public static volatile ListAttribute<CatTipoAsentamiento, CatAsentamiento> catAsentamientos;
}
