package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblTema;

@Repository
public interface TemaRepo extends JpaRepository<TblTema, Integer> {
	
	@Query("SELECT t FROM TblTema t WHERE tipoTema = ?1")
	public List<TblTema> buscarPorTipo(Integer tipoTema);
	
	@Query("SELECT t FROM TblTema t WHERE tipoTema = ?1 and activo = ?2 ")
	public List<TblTema> buscarPorTipoActivo(Integer tipoTema, boolean activo);

}
