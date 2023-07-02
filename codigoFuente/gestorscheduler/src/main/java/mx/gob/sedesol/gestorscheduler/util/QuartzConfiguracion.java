package mx.gob.sedesol.gestorscheduler.util;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzConfiguracion {
	
	private static final Logger logger = Logger.getLogger(QuartzConfiguracion.class);
	
	 private static QuartzConfiguracion instance = null;
	
	 
	 private Scheduler scheduler; 
	 
	 
	 
	 
	  private QuartzConfiguracion() {
		  try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			logger.error("Error al obtener la instancia de Quartz Scheduler");
		}
	   }
	
	  public static QuartzConfiguracion getInstance() {
	      if(instance == null) {
	         instance = new QuartzConfiguracion();
	      }
	      return instance;
	   }

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	  
	  
	
	

}
