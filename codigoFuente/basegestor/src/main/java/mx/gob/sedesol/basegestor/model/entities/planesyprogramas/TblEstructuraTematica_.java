package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.262-0600")
@StaticMetamodel(TblEstructuraTematica.class)
public class TblEstructuraTematica_ {
	public static volatile SingularAttribute<TblEstructuraTematica, Integer> idEstructuraTematica;
	public static volatile SingularAttribute<TblEstructuraTematica, Integer> activo;
	public static volatile SingularAttribute<TblEstructuraTematica, String> descripcion;
	public static volatile SingularAttribute<TblEstructuraTematica, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblEstructuraTematica, Date> fechaRegistro;
	public static volatile SingularAttribute<TblEstructuraTematica, TblEstructuraTematica> estTematicaPadre;
	public static volatile SingularAttribute<TblEstructuraTematica, String> nombreTema;
	public static volatile SingularAttribute<TblEstructuraTematica, Long> usuarioModifico;
	public static volatile ListAttribute<TblEstructuraTematica, TblEstructuraTematica> lstHijosEstTematica;
}
