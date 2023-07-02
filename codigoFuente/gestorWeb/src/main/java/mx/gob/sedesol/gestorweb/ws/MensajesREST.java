package mx.gob.sedesol.gestorweb.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.sedesol.basegestor.commons.dto.admin.InformacionMensajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeResumenDTO;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionService;

@RestController
@RequestMapping("/mensajes")
public class MensajesREST {
	
	@Autowired
	private NotificacionService notificacionService;
	
	@RequestMapping("/numero/{idPersona}")
	public InformacionMensajeDTO mensajesNuevos(@PathVariable long idPersona) {
		return notificacionService.obtenerNumeroMensajesNuevos(idPersona);
	}
	
	@RequestMapping("/primeros/{idPersona}")
	public List<MensajeResumenDTO> primerosMensajes(@PathVariable long idPersona) {
		return notificacionService.obtenerPrimerosMensajes(idPersona);
	}

}
