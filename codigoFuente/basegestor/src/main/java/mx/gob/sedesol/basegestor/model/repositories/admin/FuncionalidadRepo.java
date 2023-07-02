package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblFuncionalidad;

@Repository
public interface FuncionalidadRepo
		extends JpaRepository<TblFuncionalidad, Long>, JpaSpecificationExecutor<TblFuncionalidad> {

	// @Query("SELECT f FROM TblFuncionalidad f "
	// + "WHERE f.funcionalidadPadre = null ORDER BY f.descripcion")
	@Query("SELECT f FROM TblFuncionalidad f " + "WHERE f.funcionalidadPadre = null")
	List<TblFuncionalidad> buscarFuncionalidadesPadre();

	// @Query("SELECT f FROM TblFuncionalidad f "
	// + "JOIN FETCH f.funcionalidadPadre "
	// + "WHERE f.funcionalidadPadre.idFuncionalidad = ?1 ORDER BY
	// f.descripcion")
	@Query("SELECT f FROM TblFuncionalidad f " + "JOIN FETCH f.funcionalidadPadre "
			+ "WHERE f.funcionalidadPadre.idFuncionalidad = ?1")
	List<TblFuncionalidad> buscarFuncionalidadesHijo(Long idFuncionalidadPadre);

	// @Query("SELECT f FROM TblFuncionalidad f "
	// + "JOIN FETCH f.funcionalidadPadre "
	// + "ORDER BY f.funcionalidadPadre, f.descripcion")
	@Query("SELECT f FROM TblFuncionalidad f " + "JOIN FETCH f.funcionalidadPadre")
	List<TblFuncionalidad> obtenerTodas();

}
