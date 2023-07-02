package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-13T12:40:10.204-0500")
@StaticMetamodel(CatAsentamiento.class)
public class CatAsentamiento_ {
	public static volatile SingularAttribute<CatAsentamiento, String> idAsentamiento;
	public static volatile SingularAttribute<CatAsentamiento, String> codigoPostal;
	public static volatile SingularAttribute<CatAsentamiento, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatAsentamiento, Date> fechaRegistro;
	public static volatile SingularAttribute<CatAsentamiento, String> nombre;
	public static volatile SingularAttribute<CatAsentamiento, Boolean> activo;
	public static volatile SingularAttribute<CatAsentamiento, Long> usuarioModifico;
	public static volatile SingularAttribute<CatAsentamiento, CatMunicipio> municipio;
	public static volatile SingularAttribute<CatAsentamiento, CatTipoAsentamiento> tipoAsentamiento;
	public static volatile ListAttribute<CatAsentamiento, TblDomiciliosPersona> domiciliosPersonas;
}
