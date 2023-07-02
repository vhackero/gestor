package mx.gob.sedesol.gestorscheduler.conf;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.CronConfDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.cronscheduler.ConfiguracionCronService;
import mx.gob.sedesol.gestorscheduler.dto.RespuestaDTO;
import mx.gob.sedesol.gestorscheduler.job.ActualizaEstatusAvaJob;
import mx.gob.sedesol.gestorscheduler.util.CronValuesEnum;
import mx.gob.sedesol.gestorscheduler.util.QuartzConfiguracion;


@Service("baseCronConfiguration")
public class AvaCronConfiguration {

	private static final Logger logger = Logger.getLogger(AvaCronConfiguration.class);

	
	@Autowired
	private ConfiguracionCronService configuracionCronService;

	@PostConstruct
	public void init() {
		logger.info("inicializando Bean de configuracion de Cron ");
		this.inicializarCronAva();
	}

	/**
	 * Obtiene la configuracion del ava
	 */
	private void inicializarCronAva() {

		

		try {
			CronConfDTO cronConfDTO = configuracionCronService
					.obtenerCronProClave(CronValuesEnum.CRON_AVA.getClave());

			logger.info("cronConfDTO : " + cronConfDTO);

			logger.info("Es activo  : " + cronConfDTO.getActivo());


				
				logger.info("El nombre del cron es " + cronConfDTO.getClave()); 
				JobDetail job = this.obtenerJobDetail(cronConfDTO.getClave());
				Trigger trigger = null;
				try {
					trigger = this.obtenerTrigger(cronConfDTO.getClave(), cronConfDTO.getPatronEjecucion());
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("El cron expresion es incorrecto ! ");
				}
				if (ObjectUtils.isNotNull(trigger)) {

					Scheduler scheduler = QuartzConfiguracion.getInstance().getScheduler();
					scheduler.start();

					scheduler.scheduleJob(job, trigger);
				}



		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ocurrio un error al llamar la configuracion del cron de actualizar ava");

		}

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RespuestaDTO actualizarCronAva(String triggerName){
		RespuestaDTO respuestaDTO = new RespuestaDTO();
		
		
		CronConfDTO cronConfDTO =
				configuracionCronService.obtenerCronProClave(CronValuesEnum.CRON_AVA.getClave());
		
		respuestaDTO.setNombreCron(cronConfDTO.getNombreTarea()+" "+cronConfDTO.getClave());
		
		logger.info("cronConfDTO : "+cronConfDTO);		
		
		logger.info("Es activo  : "+cronConfDTO.getActivo());		
		
		if(cronConfDTO.getActivo()){
		
		Scheduler scheduler =  QuartzConfiguracion.getInstance().getScheduler();
		
		try {
			Trigger oldTrigger = scheduler.getTrigger(new TriggerKey(triggerName));
			if(ObjectUtils.isNotNull(oldTrigger)){
			
				TriggerBuilder tb = oldTrigger.getTriggerBuilder();
			
				Trigger newTrigger = tb.withSchedule(CronScheduleBuilder.cronSchedule(cronConfDTO.getPatronEjecucion())).build();
			
				scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
				
				respuestaDTO.setEstatus(0);				
				respuestaDTO.setDescripcionError("Sin error");
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			respuestaDTO.setDescripcionError(e.getMessage());			
			logger.error("No existe el trigger ava con el nombre "+triggerName);
		}
		
		}else{
			logger.info("Bnadera 1 @@@@");
			respuestaDTO.setEstatus(0);				
			respuestaDTO.setDescripcionError("Sin error");
			logger.info("Bnadera 2 @@@@");
			
		}				
		return respuestaDTO;
		
	}
	
	public RespuestaDTO desactivarCronAva(String triggerName){
		
		return null;
	}
	
	

	private JobDetail obtenerJobDetail(String jobName) {
		JobDetail job = JobBuilder.newJob(ActualizaEstatusAvaJob.class).withIdentity(jobName).build();

		return job;
	}

	private Trigger obtenerTrigger(String triggerName, String cronExpresion) {
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpresion)).build();
		return trigger;
	}

}
