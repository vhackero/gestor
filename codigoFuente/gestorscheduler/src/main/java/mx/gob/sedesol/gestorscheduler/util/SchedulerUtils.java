package mx.gob.sedesol.gestorscheduler.util;

import java.util.Calendar;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import mx.gob.sedesol.gestorscheduler.job.ActualizaEstatusAvaJob;

public class SchedulerUtils {

	
	public static Date obtenerFechaTrunca(Date fecha) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		fecha = cal.getTime();

		return fecha;
	}

}
