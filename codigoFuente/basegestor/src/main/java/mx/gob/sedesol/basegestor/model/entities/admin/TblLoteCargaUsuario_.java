package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TblLoteCargaUsuario.class)
public class TblLoteCargaUsuario_ {
	
	public static volatile SingularAttribute<TblLoteCargaUsuario, Integer> idLoteCargaUsuarios;
	public static volatile SingularAttribute<TblLoteCargaUsuario, String> nombre;
	public static volatile SingularAttribute<TblLoteCargaUsuario, String> rutaArchivo;
	public static volatile SingularAttribute<TblLoteCargaUsuario, Date> fechaRegistro;
	public static volatile SingularAttribute<TblLoteCargaUsuario, Long> usuarioModifico;

}
