package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CatalogoComunRepo<A, ID extends Serializable> extends JpaRepository<A, ID> {

	void setEntityClass(Class<A> entityClass);
	
	A buscarRegistroPorNombre(String nombre);
	
	List<A> buscarPorContexto(Long idContexto);

	List<A> buscarCatTipoEncuesta();
}
