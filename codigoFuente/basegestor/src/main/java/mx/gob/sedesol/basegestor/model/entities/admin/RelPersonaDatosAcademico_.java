package mx.gob.sedesol.basegestor.model.entities.admin;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@Generated(value="Dali", date="2017-05-24T12:42:31.994-0500")
@StaticMetamodel(RelPersonaDatosAcademico.class)
public class RelPersonaDatosAcademico_ {
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Integer> idDatosAcademicos;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Byte> contextoHumano;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Integer> horasMaximas;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Integer> horasMinimas;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Byte> institucionales;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Byte> procesosEstructura;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Byte> productosServicios;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, Byte> regulatorios;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, String> semblanza;
	public static volatile SingularAttribute<RelPersonaDatosAcademico, TblPersona> tblPersona;
}
