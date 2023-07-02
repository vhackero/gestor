
package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class PersonaServiceTest {
	
	private static final Logger logger = Logger.getLogger(PersonaServiceTest.class);

	@Autowired
	private PersonaService personaService;
	
	@Test
	public void buscarTodos() {
		List<PersonaDTO> lista = personaService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		PersonaDTO dto = personaService.buscarPorId(1L);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		PersonaDTO dto = personaService.buscarPorId(0L);
		assertThat(dto,  is(not(nullValue())));
	}
	
	@Test
	public void consultaPersonaPorNombreUsuario(){
		assertNotNull(personaService.obtienePersonaPorNombreUsuario("system"));
		assertThat(personaService.obtienePersonaPorNombreUsuario("system"),hasProperty("idPersona", equalTo(1L)));
	}
	
	@Test
	public void consultaPersonaPorNombreUsuarioNulo(){
		assertThat(personaService.obtienePersonaPorNombreUsuario("_usuario_"), is(nullValue()));
	}
	
	@Test
	public void consultaPersonaPorId(){
		long user_id_system = 1L;
		assertNotNull(personaService.buscarPorId(user_id_system));
		assertThat(personaService.buscarPorId(user_id_system),hasProperty("usuario", is("system")));
	}
	
	@Test
	public void busquedaPorCriteriosNulo(){
		PersonaDTO criterios = null;
		
		try {
			assertThat(personaService.busquedaPorCriterios(criterios), is(empty()));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	//@Test
	public void busquedaPorCriterios(){
		PersonaDTO criterios = new PersonaDTO();
		
		try {
			assertThat(personaService.busquedaPorCriterios(criterios), is(not(empty())));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void persistirExito() {
		PersonaDTO personadto = new PersonaDTO(1L, "MX");
		personadto.setUsuario("usuario1");
		personadto.setCurp("LOLC900909HTLPPR01");
		personadto.setNuevaContrasenia("123456");
		personadto.setConfirmacionContrasenia("123456");
		personadto.setNombre("Nombre");
		personadto.setApellidoPaterno("Apellido");
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.guardar(personadto);
			
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				for (String mensaje : resultado.getMensajes()) {
					logger.info(mensaje);
				}
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloNulo() {
		PersonaDTO dto = null;
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirFalloDatos() {
		PersonaDTO dto = new PersonaDTO();
		dto.setFechaRegistro(null);
		dto.setFechaNacimiento(null);
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarExito() {
		PersonaDTO personadto = new PersonaDTO(1L, "MX");
		personadto.setIdPersona(100L);
		personadto.setUsuario("usuario2");
		personadto.setCurp("LOLC900909HTLPPR02");
		personadto.setNuevaContrasenia("123456");
		personadto.setConfirmacionContrasenia("123456");
		personadto.setNombre("Nombre");
		personadto.setApellidoPaterno("Apellido");
		personadto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.actualizar(personadto);
			
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				for (String mensaje : resultado.getMensajes()) {
					logger.info(mensaje);
				}
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloNulo() {
		PersonaDTO dto = null;
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarFalloDatos() {
		PersonaDTO dto = new PersonaDTO();
		dto.setFechaNacimiento(null);
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarExito() {
		PersonaDTO personadto = new PersonaDTO(1L, "MX");
		personadto.setIdPersona(100L);
		personadto.setUsuario("usuario");
		personadto.setCurp("LOLC900909HTLPPR01");
		personadto.setNuevaContrasenia("123456");
		personadto.setConfirmacionContrasenia("123456");
		personadto.setNombre("Nombre");
		personadto.setApellidoPaterno("Apellido");
		personadto.setFechaActualizacion(new Date());
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.eliminar(personadto);
			
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				for (String mensaje : resultado.getMensajes()) {
					logger.info(mensaje);
				}
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloNulo() {
		PersonaDTO dto = null;
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarFalloDatos() {
		PersonaDTO dto = new PersonaDTO();
		dto.setFechaNacimiento(null);
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirDatosPersonaExito() {
		PersonaDTO personadto = new PersonaDTO(1L, "MX");
		personadto.setUsuario("usuario3");
		personadto.setCurp("LOLC900909HTLPPR03");
		personadto.setNuevaContrasenia("123456");
		personadto.setConfirmacionContrasenia("123456");
		personadto.setNombre("Nombre");
		personadto.setApellidoPaterno("Apellido");
		
		CapturaPersonaDTO datos = new CapturaPersonaDTO();
		
		datos.setPersona(personadto);
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(1L));
		datos.setTelefonoFijo(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(new PersonaCorreoDTO(1L, 
				ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(new DomicilioPersonaDTO(1L, 
				ConstantesGestor.ID_PAIS_MEXICO));
		datos.getDomicilioPersona().getAsentamiento().setIdAsentamiento("10010001");
		datos.getDomicilioPersona().setCalle("calle");
		datos.getDomicilioPersona().setNumeroExterior("S/N");
		datos.getDatosLaborales().getMunicipio().setIdMunicipio("1001");
		datos.getDatosLaborales().getSede().setIdEntidadFederativa(1);
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.guardarPersona(datos);
			
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				for (String mensaje : resultado.getMensajes()) {
					logger.info(mensaje);
				}
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirDatosPersonaFalloNulo() {
		CapturaPersonaDTO datos = new CapturaPersonaDTO();
		
		datos.setPersona(null);
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(1L));
		datos.setTelefonoFijo(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(new PersonaCorreoDTO(1L, 
				ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(new DomicilioPersonaDTO(1L, 
				ConstantesGestor.ID_PAIS_MEXICO));
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.guardarPersona(datos);
			
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				for (String mensaje : resultado.getMensajes()) {
					logger.info(mensaje);
				}
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void persistirDatosPersonaFalloDatos() {
		CapturaPersonaDTO datos = new CapturaPersonaDTO();
		
		datos.setPersona(new PersonaDTO());
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(1L));
		datos.setTelefonoFijo(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(new PersonaCorreoDTO(1L, 
				ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(new DomicilioPersonaDTO(1L, 
				ConstantesGestor.ID_PAIS_MEXICO));
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.guardarPersona(datos);
			
			for (String mensaje : resultado.getMensajes()) {
				logger.info(mensaje);
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarDatosPersonaExito() {
		PersonaDTO personadto = new PersonaDTO(1L, "MX");
		personadto.setIdPersona(187L);
		personadto.setUsuario("usuario4");
		personadto.setCurp("LOLC900909HTLPPR04");
		personadto.setNombre("Nombre");
		personadto.setApellidoPaterno("Apellido");
		
		CapturaPersonaDTO datos = personaService.obtenerDatosPersona(personadto, 1L);

		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.actualizarPersona(datos);
			
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				for (String mensaje : resultado.getMensajes()) {
					logger.info(mensaje);
				}
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarDatosPersonaFalloNulo() {
		CapturaPersonaDTO datos = new CapturaPersonaDTO();
		
		datos.setPersona(null);
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(1L));
		datos.setTelefonoFijo(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(new PersonaCorreoDTO(1L, 
				ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(new DomicilioPersonaDTO(1L, 
				ConstantesGestor.ID_PAIS_MEXICO));
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.actualizarPersona(datos);
			
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				for (String mensaje : resultado.getMensajes()) {
					logger.info(mensaje);
				}
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarDatosPersonaFalloDatos() {
		CapturaPersonaDTO datos = new CapturaPersonaDTO();
		
		datos.setPersona(new PersonaDTO());
		datos.getPersona().setNuevaContrasenia("123456");
		datos.setDatosLaborales(new UsuarioDatosLaboralesDTO(1L));
		datos.setTelefonoFijo(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_INSTITUCIONAL));
		datos.setCelular(new PersonaTelefonoDTO(1L, 
				ConstantesGestor.TIPO_TELEFONO_MOVIL));
		datos.setPersonaCorreo(new PersonaCorreoDTO(1L, 
				ConstantesGestor.TIPO_CORREO_INSTITUCIONAL));
		datos.setDomicilioPersona(new DomicilioPersonaDTO(1L, 
				ConstantesGestor.ID_PAIS_MEXICO));
		
		try {
			ResultadoDTO<PersonaDTO> resultado = personaService.actualizarPersona(datos);
			
			for (String mensaje : resultado.getMensajes()) {
				logger.info(mensaje);
			}

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void activaUsuarioWeb() {
		ResultadoDTO<PersonaDTO> resultado = personaService.activaUsuarioWeb("administradorSISI", "ZA5HIVNH");
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void activaUsuarioWebFalloUsuario() {
		ResultadoDTO<PersonaDTO> resultado = personaService.activaUsuarioWeb("_usuario_", "__");
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
	}
	
	@Test
	public void activaUsuarioWebFalloToken() {
		ResultadoDTO<PersonaDTO> resultado = personaService.activaUsuarioWeb("userDesa", "__");
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
	}
}
