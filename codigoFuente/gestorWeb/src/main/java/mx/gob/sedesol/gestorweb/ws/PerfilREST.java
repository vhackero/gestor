package mx.gob.sedesol.gestorweb.ws;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.ws.ArrayOfPerfil;
import mx.gob.sedesol.basegestor.commons.dto.ws.ConsultarPerfilResponse;
import mx.gob.sedesol.basegestor.commons.dto.ws.Perfil;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@RestController
@RequestMapping("/roles_permisos")
public class PerfilREST {

	private static final Logger logger = Logger.getLogger(PerfilREST.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/consultarPerfil")
	public ConsultarPerfilResponse consultaPerfil() {
		return new ConsultarPerfilResponse(llenarPerfiles(roleService.findAll()));
	}

	private ArrayOfPerfil llenarPerfiles(List<RolDTO> roles) {
		ArrayOfPerfil perfiles = new ArrayOfPerfil();
		for (RolDTO rol : roles) {
			Perfil perfil = new Perfil();
			perfil.setIdPerfil(rol.getIdRol().toString());
			perfil.setClavePerfil(rol.getClave());
			perfil.setDescripcionPerfil(rol.getNombre());
			perfiles.getPerfil().add(perfil);
		}
		return perfiles;
	}

}
