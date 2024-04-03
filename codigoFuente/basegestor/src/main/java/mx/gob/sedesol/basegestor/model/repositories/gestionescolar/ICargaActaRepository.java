package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Acta;

/**
 *  ACTAS
 * @author ITTIVA
 * 
 */
@Repository
public interface ICargaActaRepository extends JpaRepository<Acta, Integer> {

}
