package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import org.apache.commons.mail.EmailException;

import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDatosDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;

public interface PersonaService extends CommonService<PersonaDTO, Long> {

	TblPersona buscarPorNombreUsuario(String usuario);

	ResultadoDTO<PersonaDTO> guardarPersona(CapturaPersonaDTO datos);

	ResultadoDTO<PersonaDTO> actualizarPersona(CapturaPersonaDTO datos);

	CapturaPersonaDTO obtenerDatosPersona(PersonaDTO persona, Long usuarioModifico);

	ResultadoDTO<PersonaDTO> guardaPersonaRolAlumno(PersonaDTO personaDto, CorreoDTO correoDTO) throws EmailException;

	PersonaDTO obtienePersonaPorNombreUsuario(String usuario);

	ResultadoDTO<PersonaDTO> activaUsuarioWeb(String usuario, String token);

	ResultadoCargaDTO procesarArchivo(String ruta, Long idUsuario);

	List<PersonaDTO> busquedaPorCriterios(PersonaDTO personaDto);
	
	List<PersonaDTO> busquedaPorCriteriosPersonaBasica(PersonaDTO personaDto);

	ResultadoDTO<PersonaDTO> enviaCorreoRecuperarContrasenia(PersonaCorreoDTO personaCorreo, CorreoDTO correo);

	ResultadoDTO<LoteCargaUsuarioDTO> guardar(LoteCargaUsuarioDTO dto, ResultadoCargaDTO resultado, String ruta);

	List<PersonaDatosDTO> busquedaDatosLaboralesPorCriterios(PersonaDTO personaDto);

	List<RolDTO> estableceRolAlumnoPorDefecto(List<RolDTO> roles);

	List<PersonaDTO> obtenerPersonaPorCodigoPostal(String codigoPostal);

	ResultadoDTO<PersonaDTO> desactivarPersona(PersonaDTO persona);
	
	Boolean existeCurp(String curp);
	
	Boolean guardarPersonas(List<CapturaPersonaDTO> datos);

}
