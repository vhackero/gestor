package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Acta;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.ICargaActaRepository;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelProgCompEspecificaRepo;
import mx.gob.sedesol.basegestor.service.gestionescolar.ICargaActaService;

/**
 *  ACTAS
 * @author ITTIVA
 * 
 */
@Service("cargaActaService")
public class CargaActaService implements ICargaActaService{
	
	private static final Logger log = Logger.getLogger(CargaActaService.class);
	
	@Autowired
	private ICargaActaRepository iCargaActaRepository;
	
	@Override
	public void cargaActa(Acta acta) {		
		
		log.info("EJECUTANDO QUERY");
		
		iCargaActaRepository.save(acta);
		
		log.info("SAVE CORRECTO");

	}

}
