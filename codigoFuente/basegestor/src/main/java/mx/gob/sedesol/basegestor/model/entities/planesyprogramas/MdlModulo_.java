package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.180-0600")
@StaticMetamodel(MdlModulo.class)
public class MdlModulo_ {
	public static volatile SingularAttribute<MdlModulo, Integer> id;
	public static volatile SingularAttribute<MdlModulo, Integer> activo;
	public static volatile SingularAttribute<MdlModulo, Long> cron;
	public static volatile SingularAttribute<MdlModulo, String> identificador;
	public static volatile SingularAttribute<MdlModulo, String> nombre;
	public static volatile SingularAttribute<MdlModulo, String> rutaImagen;
	public static volatile SingularAttribute<MdlModulo, Long> ultimoCron;
	public static volatile SingularAttribute<MdlModulo, Integer> tipoModulo;
	public static volatile SingularAttribute<MdlModulo, String> descripcion;
}
