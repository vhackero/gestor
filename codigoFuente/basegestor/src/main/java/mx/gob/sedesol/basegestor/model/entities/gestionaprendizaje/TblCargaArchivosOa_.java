package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-21T16:57:11.163-0600")
@StaticMetamodel(TblCargaArchivosOa.class)
public class TblCargaArchivosOa_ {
	public static volatile SingularAttribute<TblCargaArchivosOa, Integer> id;
	public static volatile SingularAttribute<TblCargaArchivosOa, String> directorio;
	public static volatile SingularAttribute<TblCargaArchivosOa, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblCargaArchivosOa, Date> fechaRegistro;
	public static volatile SingularAttribute<TblCargaArchivosOa, Integer> idLms;
	public static volatile SingularAttribute<TblCargaArchivosOa, String> nombreArchivo;
	public static volatile SingularAttribute<TblCargaArchivosOa, String> idArchivo;
	public static volatile SingularAttribute<TblCargaArchivosOa, BigInteger> usuarioModifico;
	public static volatile SingularAttribute<TblCargaArchivosOa, CatClasificacionArchivoOa> catClasificacionArchivoOa;
	public static volatile SingularAttribute<TblCargaArchivosOa, RelUnidadOaAva> unidadOaAva;
	public static volatile SingularAttribute<TblCargaArchivosOa, Integer> versionArchivo;
}
