package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-19T13:30:53.220-0600")
@StaticMetamodel(RelProgramaCargaHoraria.class)
public class RelProgramaCargaHoraria_ {
	public static volatile SingularAttribute<RelProgramaCargaHoraria, Integer> idPrograma;
	public static volatile SingularAttribute<RelProgramaCargaHoraria, Integer> idTpoCargaHoraria;	
	public static volatile SingularAttribute<RelProgramaCargaHoraria, String> horas;
	public static volatile SingularAttribute<RelProgramaCargaHoraria, String> minutos;
	public static volatile SingularAttribute<RelProgramaCargaHoraria, Date> fechaActualizacion;
	public static volatile SingularAttribute<RelProgramaCargaHoraria, Date> fechaRegistro;
	public static volatile SingularAttribute<RelProgramaCargaHoraria, Long> usuarioModifico;
	public static volatile SingularAttribute<RelProgramaCargaHoraria, CatTpoCargaHoraria> catTpoCargaHoraria;
	public static volatile SingularAttribute<RelProgramaCargaHoraria, TblFichaDescriptivaPrograma> tblFichaDescriptivaPrograma;
}
