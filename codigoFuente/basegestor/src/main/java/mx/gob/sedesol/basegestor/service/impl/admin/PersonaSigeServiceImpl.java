package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaSige;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaSigeRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaSigeService;

@Service("personaSigeService")
public class PersonaSigeServiceImpl extends ComunValidacionService<PersonaSigeDTO> implements PersonaSigeService{
	
	private static final Logger logger = Logger.getLogger(PersonaServiceImpl.class);
	
	@Autowired
	private PersonaSigeRepo personaSigeRepo;
	private ModelMapper mapper = new ModelMapper();
	Type personaSigeDTO = new TypeToken<List<PersonaSigeDTO>>() {
	}.getType();
	
	@Override
	public List<PersonaSigeDTO> findAll() {
		List<PersonaSigeDTO> listaPersonas = new ArrayList<PersonaSigeDTO>();
		ArrayList<TblPersonaSige> personas = (ArrayList<TblPersonaSige>) personaSigeRepo.findAll();
		for(TblPersonaSige persona : personas) {
			PersonaSigeDTO personaObj = new PersonaSigeDTO();
			personaObj.setIdPersonaSige(persona.getIdPersonaSige());
			personaObj.setMatricula(persona.getMatricula());
			personaObj.setNombre(persona.getNombre());
			personaObj.setApellidoPaterno(persona.getApellidoPaterno());
			personaObj.setApellidoMaterno(persona.getApellidoMaterno());
			personaObj.setProgramaEducativo(persona.getProgramaEducativo());
			personaObj.setDivision(persona.getDivision());
			personaObj.setCorreoInstitucional(persona.getCorreoInstitucional());
			personaObj.setFechaNacimiento(persona.getFechaNacimiento());
			personaObj.setCurp(persona.getCurp());
			personaObj.setNivelSige(persona.getNivelSige());
			personaObj.setPersonaIdSige(persona.getPersonaIdSige());
			personaObj.setPerfilIdSige(persona.getPerfilIdSige());
			personaObj.setPassword(persona.getPassword());
			listaPersonas.add(personaObj);
		}
		
		return listaPersonas;
	}
	@Override
	public PersonaSigeDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> guardar(PersonaSigeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> actualizar(PersonaSigeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultadoDTO<PersonaSigeDTO> eliminar(PersonaSigeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void validarPersistencia(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void validarActualizacion(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void validarEliminacion(PersonaSigeDTO dto, ResultadoDTO<PersonaSigeDTO> resultado) {
		// TODO Auto-generated method stub
		
	}
	public PersonaSigeRepo getPersonaSigeRepo() {
		return personaSigeRepo;
	}
	public void setPersonaSigeRepo(PersonaSigeRepo personaSigeRepo) {
		this.personaSigeRepo = personaSigeRepo;
	}

	@Override
	public List<PersonaSigeDTO> buscarNoRegistrados() {
		List<PersonaSigeDTO> listaPersonas = new ArrayList<PersonaSigeDTO>();
		List<TblPersonaSige> personas = personaSigeRepo.obtenetPersonasNoRegistradas();
		for(TblPersonaSige persona : personas) {
			if(!persona.getCorreoInstitucional().isEmpty()) {
				PersonaSigeDTO personaObj = new PersonaSigeDTO();
				personaObj.setIdPersonaSige(persona.getIdPersonaSige());
				personaObj.setMatricula(persona.getMatricula());
				personaObj.setNombre(persona.getNombre());
				personaObj.setApellidoPaterno(persona.getApellidoPaterno());
				personaObj.setApellidoMaterno(persona.getApellidoMaterno());
				personaObj.setProgramaEducativo(persona.getProgramaEducativo());
				personaObj.setDivision(persona.getDivision());
				personaObj.setCorreoInstitucional(persona.getCorreoInstitucional());
				personaObj.setFechaNacimiento(persona.getFechaNacimiento());
				personaObj.setCurp(persona.getCurp());
				personaObj.setNivelSige(persona.getNivelSige());
				personaObj.setPersonaIdSige(persona.getPersonaIdSige());
				personaObj.setPerfilIdSige(persona.getPerfilIdSige());
				personaObj.setPassword(persona.getPassword());
				listaPersonas.add(personaObj);				
			}
		}
		
		return listaPersonas;
	}

	@Override
	public TblPersonaSige importarDesdeFuenteExterna(String idFuenteExterna, String matricula) throws Exception {
		Map<String, Object> configuracion = obtenerConfiguracionFuente(idFuenteExterna);
		String consulta = (String) configuracion.get("consulta");

		if (consulta == null || (!consulta.contains("?") && !consulta.toLowerCase().contains(":matricula")
				&& !consulta.toLowerCase().contains(":usuario"))) {
			throw new IllegalStateException("Configurar correctamente los datos de la fuente externa");
		}

		String servidor = (String) configuracion.get("servidor");
		String usuario = (String) configuracion.get("usuario");
		String password = (String) configuracion.get("alias");
		String baseDatos = (String) configuracion.get("nombre_base_datos");

		TblPersonaSige personaSige;
		try (Connection connection = obtenerConexionExterna(servidor, baseDatos, usuario, password);
				PreparedStatement statement = connection.prepareStatement(consulta)) {
			statement.setString(1, matricula);
			try (ResultSet rs = statement.executeQuery()) {
				if (!rs.next()) {
					throw new SQLException("No se encontraron resultados para la matrícula proporcionada");
				}
				personaSige = mapearPersonaSige(rs);
			}
		}

		return personaSigeRepo.saveAndFlush(personaSige);
	}

	private Map<String, Object> obtenerConfiguracionFuente(String idFuenteExterna) throws SQLException, NamingException {
		String sql = "SELECT servidor, usuario, alias, nombre_base_datos, consulta FROM cat_fuentes_externas WHERE id_fuente_externa = ?";
		Map<String, Object> configuracion = new HashMap<>();
		try (Connection connection = obtenerConexionGestor();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, idFuenteExterna);
			try (ResultSet rs = statement.executeQuery()) {
				if (!rs.next()) {
					throw new SQLException("No se encontró la configuración de la fuente externa seleccionada");
				}
				configuracion.put("servidor", rs.getString("servidor"));
				configuracion.put("usuario", rs.getString("usuario"));
				configuracion.put("alias", rs.getString("alias"));
				configuracion.put("nombre_base_datos", rs.getString("nombre_base_datos"));
				configuracion.put("consulta", rs.getString("consulta"));
			}
		}
		return configuracion;
	}

	private TblPersonaSige mapearPersonaSige(ResultSet rs) throws SQLException {
		TblPersonaSige personaSige = new TblPersonaSige();
		personaSige.setMatricula(obtenerColumna(rs, "matricula_sige", "matricula"));
		personaSige.setPassword(obtenerColumna(rs, "password_sige", "password"));
		personaSige.setNombre(obtenerColumna(rs, "nombre_sige", "nombre"));
		personaSige.setApellidoPaterno(obtenerColumna(rs, "apellidop_sige", "apellido_paterno"));
		personaSige.setApellidoMaterno(obtenerColumna(rs, "apellidom_sige", "apellido_materno"));
		personaSige.setProgramaEducativo(obtenerColumna(rs, "programa_educativo_sige", "programa_educativo"));
		personaSige.setDivision(obtenerColumna(rs, "division_sige", "division"));
		personaSige.setCorreoInstitucional(
				obtenerColumna(rs, "correo_institucional_sige", "correo_institucional"));
		personaSige.setFechaNacimiento(obtenerFecha(rs, "fecha_nacimiento_sige", "fecha_nacimiento"));
		personaSige.setCurp(obtenerColumna(rs, "curp_sige", "curp"));
		personaSige.setNivelSige(obtenerColumna(rs, "nivel_sige", "nivel"));
		personaSige.setPersonaIdSige(obtenerEntero(rs, "persona_id_sige"));
		personaSige.setPerfilIdSige(obtenerEntero(rs, "perfil_id_sige"));
		return personaSige;
	}

	private int obtenerEntero(ResultSet rs, String columna) {
		try {
			return rs.getInt(columna);
		} catch (SQLException e) {
			return 0;
		}
	}

	private String obtenerColumna(ResultSet rs, String... columnas) {
		for (String columna : columnas) {
			try {
				String valor = rs.getString(columna);
				if (valor != null) {
					return valor;
				}
			} catch (SQLException e) {
				// Ignorar y probar con la siguiente columna
			}
		}
		return null;
	}

	private java.util.Date obtenerFecha(ResultSet rs, String... columnas) {
		for (String columna : columnas) {
			try {
				return rs.getDate(columna);
			} catch (SQLException e) {
				// Ignorar y probar con la siguiente columna
			}
		}
		return null;
	}

	private Connection obtenerConexionGestor() throws NamingException, SQLException {
		InitialContext ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:jboss/jdbc_elearning");
		return ds.getConnection();
	}

	private Connection obtenerConexionExterna(String servidor, String baseDatos, String usuario, String password)
			throws SQLException {
		String url = String.format("jdbc:mysql://%s/%s", servidor, baseDatos);
		return DriverManager.getConnection(url, usuario, password);
	}
	
}
