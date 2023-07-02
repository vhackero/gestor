package mx.gob.sedesol.gestorscheduler.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.CronConfDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.service.cronscheduler.ConfiguracionCronService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.gestorscheduler.util.AplicationContextProvider;
import mx.gob.sedesol.gestorscheduler.util.CronValuesEnum;
import mx.gob.sedesol.gestorscheduler.util.SchedulerUtils;





public class ActualizaEstatusAvaJob implements Job
{
	private static final Logger logger = Logger.getLogger(ActualizaEstatusAvaJob.class);

	
	private ConfiguracionCronService configuracionCronService;
	
	private AmbienteVirtualApService ambienteVirtualApService;

	

	public void execute(JobExecutionContext context)throws JobExecutionException {

		logger.info("Se esta ejecutando el Cron Actualiza AVA");
		
		
			AplicationContextProvider aplicationContextProvider = new AplicationContextProvider();
						
			configuracionCronService = 
					(ConfiguracionCronService)aplicationContextProvider.getApplicationContext().getBean("configuracionCronService");
		
			ambienteVirtualApService = 
					(AmbienteVirtualApService)aplicationContextProvider.getApplicationContext().getBean("ambienteVirtualApService");
			
			CronConfDTO cronConfDTO =
				configuracionCronService.obtenerCronProClave(CronValuesEnum.CRON_AVA.getClave());
			logger.info("El Cron Actualiza Ava es activo  "+cronConfDTO.getActivo());
			
			if(cronConfDTO.getActivo()){
				this.activaAva();
			}
			
			logger.info("Termino de ejecutar el cron actualiza Ava");
		
	}
	
	

	/**
	 * Metodo que activara todos los avas cuando
	 * la fecha inicial del evento de capacitacion 
	 * es menor a el dia actual
	 */
	
	private void activaAva(){
		
		Date fechaActual = 		
		SchedulerUtils.obtenerFechaTrunca(new Date());
		/**
		 * calendarizado
		 */
	
		logger.info("La fecha de referencia es "+fechaActual);
		
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado =  
				this.ambienteVirtualApService.activaAvas(fechaActual, Boolean.FALSE);
				logger.info("El resultado es "+resultado.getResultado());
		
				
		logger.info("@@@@@@@@@@ El resultado de la actualizacion por el CRON fue :"+resultado.getResultado()+" @@@@@@@@@");		
				
	
	}
	

	
}
