/**
 * 
 */
package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author jhcortes
 *
 */
@StaticMetamodel(TblEncuesta.class)
public class TblEncuesta_ {

	public static volatile SingularAttribute<TblEncuesta, Long> id;
	public static volatile SingularAttribute<TblEncuesta, String> nombre;
	public static volatile SingularAttribute<TblEncuesta, String> instrucciones;
	public static volatile SingularAttribute<TblEncuesta, String> observaciones;
	public static volatile SingularAttribute<TblEncuesta, String> clave;
	public static volatile SingularAttribute<TblEncuesta, Long> usuarioModifico;
	public static volatile SingularAttribute<TblEncuesta, Long> usuarioAutor;
	public static volatile SingularAttribute<TblEncuesta, Date> fechaCreacion;
	public static volatile SingularAttribute<TblEncuesta, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblEncuesta, Integer> activo;
	public static volatile SingularAttribute<TblEncuesta, Integer> orden;
	public static volatile SingularAttribute<TblEncuesta, TblEncuesta> idParent;
	public static volatile SingularAttribute<TblEncuesta, CatEncuestaObjetivo>  encuestaObjetivo;
	public static volatile SingularAttribute<TblEncuesta, CatEncuestaEstatus >  encuestaEstatus;
	public static volatile SingularAttribute<TblEncuesta, CatEncuestaTipo>  encuestaTipo;
	public static volatile ListAttribute<TblEncuesta, TblPreguntasEncuesta>  preguntasEncuesta;
	public static volatile SingularAttribute<TblEncuesta, Date> fechaPublicacion;
	public static volatile SingularAttribute<TblEncuesta, Date> fechaCierre;
	
}
