package mx.gob.sedesol.basegestor.service.impl.encuestas;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta;

public class ActualizarEncuestaSync extends Thread {

	private static final Logger logger = Logger.getLogger(ActualizarEncuestaSync.class);

	private TblEncuesta encuesta;

	public ActualizarEncuestaSync(TblEncuesta encuesta) {
		super();
		this.encuesta = encuesta;
	}

	@Override
	public void run() {

		if (!ObjectUtils.isNullOrEmpty(encuesta.getClave())) {

			try {
				synchronized (encuesta) {
					encuesta.wait();
				}

			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
