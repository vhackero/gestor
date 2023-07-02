package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
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
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoAsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.service.admin.AsentamientoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@Transactional
public class AsentamientoServiceTest {

	private static final Logger logger = Logger.getLogger(AsentamientoServiceTest.class);

	@Autowired
	private AsentamientoService asentamientoService;

	@Test
	public void buscarTodos() {
		List<AsentamientoDTO> listaAsentamientos = asentamientoService.findAll();
		assertThat(listaAsentamientos, is(not(empty())));
	}

	@Test
	public void buscarPorMunicipioConRegistros() {
		List<AsentamientoDTO> listaAsentamientos = asentamientoService.buscarPorMunicipio("1001");
		assertThat(listaAsentamientos, is(not(empty())));
	}

	@Test
	public void buscarPorMunicipioSinRegistros() {
		List<AsentamientoDTO> listaAsentamientos = asentamientoService.buscarPorMunicipio("99999");
		assertThat(listaAsentamientos, is(empty()));
	}

	@Test
	public void buscarPorCodigoPostal() {
		AsentamientoDTO dto = asentamientoService.buscarPorCodigoPostal("90300");
		assertThat(dto, is(not(nullValue())));
	}

	@Test
	public void buscarPorCodigoPostalNulo() {
		AsentamientoDTO dto = asentamientoService.buscarPorCodigoPostal("00000");
		assertThat(dto, is(nullValue()));
	}

	@Test
	public void buscarPorIdExito() {
		AsentamientoDTO dto = asentamientoService.buscarPorId("10010001");
		assertThat(dto, allOf(hasProperty("idAsentamiento", is("10010001")), hasProperty("codigoPostal", is("20000")),
				hasProperty("nombre", is("Zona Centro"))));
	}

	@Test
	public void buscarPorIdFallido() {
		AsentamientoDTO dto = asentamientoService.buscarPorId("99999999");
		assertThat(dto, is(nullValue()));
	}

	@Test
	public void guardarAsentamientoExito() {
		AsentamientoDTO dto = new AsentamientoDTO(1L);
		dto.setIdAsentamiento("10019998");
		dto.setNombre("Prueba TEST");
		dto.setCodigoPostal("99999");
		dto.setMunicipio(new MunicipioDTO());
		dto.getTipoAsentamiento().setIdTipoAsentamiento(1);
		dto.getMunicipio().setIdMunicipio("1001");
		try {
			ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void guardarAsentamientoNull() {
		AsentamientoDTO dto = null;
		try {
			ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.guardar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarAsentamientoExito() {
		AsentamientoDTO dto = asentamientoService.buscarPorId("10019999");

		dto.setUsuarioModifico(1L);
		dto.setFechaActualizacion(new Date());
		dto.setNombre("Prueba TEST");

		try {
			ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.actualizar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarAsentamientoFallido() {
		AsentamientoDTO dto = asentamientoService.buscarPorId("10019999");

		dto.setUsuarioModifico(1L);
		dto.setFechaActualizacion(new Date());
		dto.setNombre("Prueba TEST");
		dto.setMunicipio(null);
		dto.setFechaRegistro(null);

		try {
			ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.actualizar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarAsentamientoExito() {
		AsentamientoDTO dto = new AsentamientoDTO(1L);
		dto.setIdAsentamiento("10019998");
		dto.setFechaActualizacion(new Date());
		try {
			ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarAsentamientoNull() {
		AsentamientoDTO dto = null;
		try {
			ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.eliminar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void datosRequeridosPersistenciaExito() {
		AsentamientoDTO dto = new AsentamientoDTO();
		dto.setIdAsentamiento("0001");
		dto.setNombre("Asentamiento");
		dto.setCodigoPostal("99999");

		dto.setTipoAsentamiento(new TipoAsentamientoDTO());
		dto.setMunicipio(new MunicipioDTO());
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.getTipoAsentamiento().setIdTipoAsentamiento(1);
		dto.getMunicipio().setIdMunicipio("1");

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA,
				dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}

	@Test
	public void datosRequeridosPersistenciaFalloPrimerNivel() {
		AsentamientoDTO dto = new AsentamientoDTO();
		dto.setFechaRegistro(null);

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA,
				dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(),
				containsInAnyOrder(MensajesSistemaEnum.ASENTAMIENTOS_ID_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_NOMBRE_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_CODIGO_POSTAL_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_TIPO_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_MUNICIPIO_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_FECHA_REG_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_USUARIO_REQ.getId()));
	}

	@Test
	public void datosRequeridosPersistenciaFalloSegundoNivel() {
		AsentamientoDTO dto = new AsentamientoDTO();
		dto.setIdAsentamiento("0001");
		dto.setNombre("Asentamiento");
		dto.setCodigoPostal("99999");

		dto.setTipoAsentamiento(new TipoAsentamientoDTO());
		dto.setMunicipio(new MunicipioDTO());
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA,
				dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), containsInAnyOrder(MensajesSistemaEnum.ASENTAMIENTOS_TIPO_REQ.getId(),
				MensajesSistemaEnum.ASENTAMIENTOS_MUNICIPIO_REQ.getId()));
	}

	@Test
	public void datosRequeridosActualizacionExito() {
		AsentamientoDTO dto = new AsentamientoDTO();
		dto.setIdAsentamiento("0001");
		dto.setNombre("Asentamiento");
		dto.setCodigoPostal("99999");

		dto.setTipoAsentamiento(new TipoAsentamientoDTO());
		dto.setMunicipio(new MunicipioDTO());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.getTipoAsentamiento().setIdTipoAsentamiento(1);
		dto.getMunicipio().setIdMunicipio("1");

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService
				.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}

	@Test
	public void datosRequeridosActualizacionFalloPrimerNivel() {
		AsentamientoDTO dto = new AsentamientoDTO();

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService
				.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(),
				containsInAnyOrder(MensajesSistemaEnum.ASENTAMIENTOS_ID_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_NOMBRE_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_CODIGO_POSTAL_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_TIPO_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_MUNICIPIO_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_FECHA_EDI_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_USUARIO_REQ.getId()));
	}

	@Test
	public void datosRequeridosActualizacionFalloSegundoNivel() {
		AsentamientoDTO dto = new AsentamientoDTO();
		dto.setIdAsentamiento("0001");
		dto.setNombre("Asentamiento");
		dto.setCodigoPostal("99999");

		dto.setTipoAsentamiento(new TipoAsentamientoDTO());
		dto.setMunicipio(new MunicipioDTO());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService
				.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), containsInAnyOrder(MensajesSistemaEnum.ASENTAMIENTOS_TIPO_REQ.getId(),
				MensajesSistemaEnum.ASENTAMIENTOS_MUNICIPIO_REQ.getId()));
	}

	@Test
	public void datosRequeridosEliminacionExito() {
		AsentamientoDTO dto = new AsentamientoDTO();

		dto.setIdAsentamiento("99999999");
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.sonDatosRequeridosValidos(TipoAccion.ELIMINACION,
				dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}

	@Test
	public void datosRequeridosEliminacionFallo() {
		AsentamientoDTO dto = new AsentamientoDTO();

		ResultadoDTO<AsentamientoDTO> resultado = asentamientoService.sonDatosRequeridosValidos(TipoAccion.ELIMINACION,
				dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(),
				containsInAnyOrder(MensajesSistemaEnum.ASENTAMIENTOS_ID_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_FECHA_EDI_REQ.getId(),
						MensajesSistemaEnum.ASENTAMIENTOS_USUARIO_REQ.getId()));
	}

}
