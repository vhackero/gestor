package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-18T15:21:08.672-0600")
@StaticMetamodel(CatInstitucionesCertificadora.class)
public class CatInstitucionesCertificadora_ {
	public static volatile SingularAttribute<CatInstitucionesCertificadora, Integer> id;
	public static volatile SingularAttribute<CatInstitucionesCertificadora, Byte> activo;
	public static volatile SingularAttribute<CatInstitucionesCertificadora, String> descripcion;
	public static volatile SingularAttribute<CatInstitucionesCertificadora, Date> fechaActualizacion;
	public static volatile SingularAttribute<CatInstitucionesCertificadora, Date> fechaRegistro;
	public static volatile SingularAttribute<CatInstitucionesCertificadora, String> nombre;
	public static volatile SingularAttribute<CatInstitucionesCertificadora, Integer> orden;
	public static volatile SingularAttribute<CatInstitucionesCertificadora, BigInteger> usuarioModifico;
}
