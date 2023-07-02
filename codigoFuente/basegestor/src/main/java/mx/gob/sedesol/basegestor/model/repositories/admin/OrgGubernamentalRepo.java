package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;

@Repository
public interface OrgGubernamentalRepo extends JpaRepository<TblOrganismoGubernamental, Integer>{

	@Query("select og from TblOrganismoGubernamental og where og.id = :id ")
	public List<TblOrganismoGubernamental> buscarOrgGubernamentalPorId(@Param("id") Integer id);
	
	@Query("SELECT og FROM TblOrganismoGubernamental og WHERE og.orgGubPadre.id is null")
	public List<TblOrganismoGubernamental> obtenerOrgGubsPadres();
}
