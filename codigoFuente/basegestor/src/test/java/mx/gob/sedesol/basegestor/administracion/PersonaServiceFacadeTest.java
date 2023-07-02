package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@Transactional
public class PersonaServiceFacadeTest {

	@Autowired
	private PersonaServiceFacade personaServiceFacade;

	@Test
	public void obtenerPaises() {
		List<PaisDTO> lista = personaServiceFacade.obtenerPaises();
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void obtenerEntidadesPorPais() {
		List<EntidadFederativaDTO> lista = personaServiceFacade.obtenerEntidadesPorPais("MX");
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void obtenerMunicipiosPorEntidad() {
		List<MunicipioDTO> lista = personaServiceFacade.obtenerMunicipiosPorEntidad(1);
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void obtenerAsentamientosPorMunicipio() {
		List<AsentamientoDTO> lista = personaServiceFacade.obtenerAsentamientosPorMunicipio("10001");
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void obtenerAsentamientoPorCodigoPostal() {
		AsentamientoDTO dto = personaServiceFacade.obtenerAsentamientoPorCodigoPostal("34450");
		assertThat(dto, is(not(nullValue())));
	}

	@Test
	public void buscarPersonaPorCriterios() {
		List<PersonaDTO> lista = personaServiceFacade.buscarPersonaPorCriterios(new PersonaDTO());
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void obtenerPersonaPorId() {
		PersonaDTO dto = personaServiceFacade.obtenerPersonaPorId(1L);
		assertThat(dto, is(not(nullValue())));
	}

	@Test
	public void obtenerDatosPersona() {
		PersonaDTO persona = new PersonaDTO();
		
		CapturaPersonaDTO dto = personaServiceFacade.obtenerDatosPersona(persona, 1L);
		assertThat(dto, is(not(nullValue())));
	}

	@Test
	public void guardarPersona() {
		ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.guardarPersona(new CapturaPersonaDTO());

		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
	}

	@Test
	public void actualizarDatosPersona() {
		ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.actualizarPersona(new CapturaPersonaDTO());

		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
	}

	@Test
	public void actualizarPersona() {
		ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.actualizarPersona(new PersonaDTO());

		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
	}

	@Test
	public void obtenerTodosRoles() {
		List<RolDTO> lista = personaServiceFacade.obtenerTodosRoles();
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void obtenerRolesPorUsuario() {
		List<PersonaRolDTO> lista = personaServiceFacade.obtenerRolesPorUsuario("system");
		assertThat(lista, is(not(empty())));
	}

	@Test
	public void almacenarRolesUsuario() {
		ResultadoDTO<PersonaRolDTO> resultado = personaServiceFacade.almacenarRolesUsuario(new PersonaDTO(),
				new ArrayList<>(), 1l);

		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}

	@Test
	public void obtenerRutaAlmacenamientoFotosUsuario() {
		String ruta = personaServiceFacade.obtenerRutaAlmacenamientoFotosUsuario();
		assertThat(ruta, is(not(nullValue())));
	}

	@Test
	public void obtenerNombreImagenComun() {
		String nombre = personaServiceFacade.obtenerNombreImagenComun();
		assertThat(nombre, is(not(nullValue())));
	}

	@Test
	public void obtenerRutaUndertow() {
		String ruta = personaServiceFacade.obtenerRutaUndertow();
		assertThat(ruta, is(not(nullValue())));
	}

}
