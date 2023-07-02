package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogoComunRepoImpl<A,ID extends Serializable> implements CatalogoComunRepo<A, ID>{


	
	@PersistenceContext
	private EntityManager entityManagerFactory;
	
	private Class<A> entityClass;
	
	public void setEntityClass(Class<A> entityClass ){
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<A> findAll() {
		return entityManagerFactory.createQuery("Select a from " + entityClass.getSimpleName() + " a").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<A> buscarPorContexto(Long idContexto) {
		return entityManagerFactory.createQuery("Select a from " + entityClass.getSimpleName() + " a"
				+ " where id_encuesta_contexto ="+ idContexto).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<A> buscarCatTipoEncuesta() {
		return entityManagerFactory.createQuery("Select a from " + entityClass.getSimpleName() + " a").getResultList();
	}


	public <S extends A> S save(S entity) {
		entityManagerFactory.persist(entity);
		entityManagerFactory.flush();
		return entity;
		
	}

	//@Override
	public A findOne(ID id) {
		return entityManagerFactory.find(entityClass, id);
	}

	@Override
	public boolean exists(ID id) {
		A entidad = entityManagerFactory.find(entityClass, id);
        return entidad != null;

    }

	@Override
	public void delete(ID id) {
		entityManagerFactory.remove(entityManagerFactory.find(entityClass, id));
		entityManagerFactory.flush();
	}

	@Override
	public void delete(A entity) {
		entityManagerFactory.remove(entity);
	}

	@Override
	public void delete(Iterable<? extends A> entities) {
		for (A entity : entities) {
			delete(entity);
		}
	}

	@Override
	public <S extends A> List<S> save(Iterable<S> entities) {
		
		List<S> result = new ArrayList<S>();
		if (entities == null) {
			return result;
		}
		for (S entity : entities) {
			result.add(save(entity));
		}
		return result;
	}

	@Override
	public void flush() {
		entityManagerFactory.flush();
	}

	@Override
	public <S extends A> S saveAndFlush(S entity) {
		entityManagerFactory.merge(entity);
		entityManagerFactory.flush();
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public A buscarRegistroPorNombre(String nombre){
		
		return (A) entityManagerFactory
		.createQuery("Select a from " + entityClass.getSimpleName() + " a where a.nombre = :nombre ")
		.setParameter("nombre",nombre).getSingleResult();
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(Iterable<A> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<A> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<A> findAll(Iterable<ID> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends A> List<S> findAll(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends A> List<S> findAll(Example<S> arg0, Sort arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public A getOne(ID arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<A> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends A> long count(Example<S> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends A> boolean exists(Example<S> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends A> Page<S> findAll(Example<S> arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends A> S findOne(Example<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
