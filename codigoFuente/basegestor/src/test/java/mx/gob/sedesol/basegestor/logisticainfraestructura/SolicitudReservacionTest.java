package mx.gob.sedesol.basegestor.logisticainfraestructura;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SolicitudReservacionDTO;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudReservacionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml"})
@Transactional
public class SolicitudReservacionTest {
	
	private static final Logger logger = Logger.getLogger(SolicitudReservacionTest.class);
	
	@Autowired
	private SolicitudReservacionService solicitudReservacionService;

	@Test
	public void guardar(){
		List<SolicitudReservacionDTO> z = solicitudReservacionService.findAll();
		SolicitudReservacionDTO a = new SolicitudReservacionDTO();
		a.setFechaRegistro(new Date());
		a.setUsuarioModifico(2L);
		
		a = solicitudReservacionService.guardar(a).getDto();
		logger.info("#########$#$#$#$#$#$#$#$#$#$#$#ID" + a.getIdSolicitud());
	}
	
	/**
	 * @return the solicitudReservacionService
	 */
	public SolicitudReservacionService getSolicitudReservacionService() {
		return solicitudReservacionService;
	}

	/**
	 * @param solicitudReservacionService the solicitudReservacionService to set
	 */
	public void setSolicitudReservacionService(SolicitudReservacionService solicitudReservacionService) {
		this.solicitudReservacionService = solicitudReservacionService;
	}

}
