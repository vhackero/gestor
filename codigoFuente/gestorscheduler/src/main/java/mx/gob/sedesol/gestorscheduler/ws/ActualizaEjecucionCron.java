package mx.gob.sedesol.gestorscheduler.ws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import mx.gob.sedesol.gestorscheduler.conf.AvaCronConfiguration;
import mx.gob.sedesol.gestorscheduler.util.CronValuesEnum;
import mx.gob.sedesol.gestorscheduler.dto.RespuestaDTO;




@Controller
@RequestMapping("/actualizaEjecucionCron")
public class ActualizaEjecucionCron {

		
	@Autowired
	private AvaCronConfiguration baseCronConfiguration;
	
	private static final Logger logger = Logger.getLogger(ActualizaEjecucionCron.class);
		
	
	
	@RequestMapping(value = "/{nombreCron}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE  )
	public  @ResponseBody RespuestaDTO getMovie(@PathVariable String nombreCron, ModelMap model) {
				
		logger.info("Se va a actualizar el cron con el nombre : "+nombreCron);
		RespuestaDTO respuesta = null;
		
		
	
		if(nombreCron != null && "" != nombreCron  ){
			
			if(CronValuesEnum.CRON_AVA.getClave().
					equals(nombreCron.trim())){
				logger.info("Actualizara el cron Ava");
				respuesta =
				baseCronConfiguration.actualizarCronAva(nombreCron);
				
			}				
													
		}

		return respuesta;
	
	}


	

}

