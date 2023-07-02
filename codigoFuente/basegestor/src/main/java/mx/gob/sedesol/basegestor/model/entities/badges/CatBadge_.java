package mx.gob.sedesol.basegestor.model.entities.badges;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Clase model para CatBadge
 * @author nnm_eolf
 */
 
@Generated(value="Dali", date="2017-04-27T23:04:53.055-0600")
@StaticMetamodel(CatBadge.class)
public class CatBadge_ {
	
	public static volatile SingularAttribute<CatBadge, Integer> idBadge;
	public static volatile SingularAttribute<CatBadge, Integer> idClasificacionBadge;
	public static volatile SingularAttribute<CatBadge, Integer> calificacionMaxima;
	public static volatile SingularAttribute<CatBadge, Integer> calificacionMinima;
	public static volatile SingularAttribute<CatBadge, String> nombre;
	public static volatile SingularAttribute<CatBadge, String> descripcion;
	public static volatile SingularAttribute<CatBadge, String> rutaImagen;
	public static volatile SingularAttribute<CatBadge, Long> usuarioModifico;
	public static volatile SingularAttribute<CatBadge, Date> fechaRegistro;
	public static volatile SingularAttribute<CatBadge, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatBadge, Integer> orden;
	public static volatile SingularAttribute<CatBadge, Integer> idEstatus;	

}
