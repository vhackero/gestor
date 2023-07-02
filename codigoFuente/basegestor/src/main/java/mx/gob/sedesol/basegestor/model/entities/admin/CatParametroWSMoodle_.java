package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Clase model para CatParametroWSMoodle
 * @author eolf
 *
 */
@Generated(value="Dali", date="2017-03-29T13:30:53.055-0600")
@StaticMetamodel(CatParametroWSMoodle.class)
public class CatParametroWSMoodle_ {
	
	public static volatile SingularAttribute<CatParametroWSMoodle, Integer> idParametroWSMoodle;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> host;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> path;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> service;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> username;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> password;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> outh;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> server;
	public static volatile SingularAttribute<CatParametroWSMoodle, Boolean> activo;
	public static volatile SingularAttribute<CatParametroWSMoodle, Date> fechaRegistro;
	public static volatile SingularAttribute<CatParametroWSMoodle, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> nombre;
	public static volatile SingularAttribute<CatParametroWSMoodle, String> descripcion;
	public static volatile SingularAttribute<CatParametroWSMoodle, Long> usuarioModifico;
	public static volatile SingularAttribute<CatParametroWSMoodle, Integer> orden;
	
}
