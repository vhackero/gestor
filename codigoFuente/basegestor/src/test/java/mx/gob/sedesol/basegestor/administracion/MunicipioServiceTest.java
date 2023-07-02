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

import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class MunicipioServiceTest {
	
	private static final Logger logger = Logger.getLogger(MunicipioServiceTest.class);
	
	@Autowired
	private MunicipioService municipioService;
	
	@Test
	public void buscarTodosExito() {
		List<MunicipioDTO> listaMunicipios = municipioService.findAll();
		assertThat(listaMunicipios, is(not(empty())));
	}

	@Test
	public void buscarPorEntidadExito() {
		List<MunicipioDTO> listaMunicipios = municipioService.buscarPorEntidadFederativa(1);
		assertThat(listaMunicipios, is(not(empty())));
	}
	
	@Test
	public void buscarPorEntidadFallo() {
		List<MunicipioDTO> listaMunicipios = municipioService.buscarPorEntidadFederativa(99);
		assertThat(listaMunicipios, is(empty()));
	}
	
	@Test
	public void buscarPorIdExito() {
		MunicipioDTO dto = municipioService.buscarPorId("1001");
		assertThat(dto, allOf(
				hasProperty("idMunicipio", is("1001")),
				hasProperty("nombre", is("Aguascalientes"))
				));
	}
	
	@Test
	public void buscarPorIdFallo() {
		MunicipioDTO dto = municipioService.buscarPorId("99999");
		assertThat(dto, is(nullValue()));
	}

	@Test
	public void guardarMunicipioExito() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		dto.setNombre("Municipio prueb");
		dto.setEntidadFederativa(new EntidadFederativaDTO());
		dto.getEntidadFederativa().setIdEntidadFederativa(1);
		
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<MunicipioDTO> resultado = municipioService.guardar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void guardarMunicipioFallo() {
		MunicipioDTO dto = null;
		
		try {
			ResultadoDTO<MunicipioDTO> resultado = municipioService.guardar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarMunicipioExito() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		dto.setNombre("Municipio prueba");
		dto.setEntidadFederativa(new EntidadFederativaDTO());
		dto.getEntidadFederativa().setIdEntidadFederativa(1);
		
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<MunicipioDTO> resultado = municipioService.actualizar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void actualizarMunicipioFallo() {
		MunicipioDTO dto = null;
		
		try {
			ResultadoDTO<MunicipioDTO> resultado = municipioService.actualizar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarMunicipioExito() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		dto.setNombre("Municipio prueba");
		dto.setEntidadFederativa(new EntidadFederativaDTO());
		dto.getEntidadFederativa().setIdEntidadFederativa(1);
		
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		try {
			ResultadoDTO<MunicipioDTO> resultado = municipioService.eliminar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarMunicipioNulo() {
		MunicipioDTO dto = null;
		
		try {
			ResultadoDTO<MunicipioDTO> resultado = municipioService.eliminar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void eliminarMunicipioFallo() {
		MunicipioDTO dto = new MunicipioDTO();
		
		try {
			ResultadoDTO<MunicipioDTO> resultado = municipioService.eliminar(dto);
			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
			assertThat(resultado.getMensajes(), 
					containsInAnyOrder(
							MensajesSistemaEnum.MUNICIPIOS_ID_REQ.getId(),
							MensajesSistemaEnum.MUNICIPIOS_FECHA_EDI_REQ.getId(),
							MensajesSistemaEnum.MUNICIPIOS_USUARIO_REQ.getId()
							));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void datosRequeridosPersistenciaExito() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		dto.setNombre("Municipio prueb");
		dto.setEntidadFederativa(new EntidadFederativaDTO());
		dto.getEntidadFederativa().setIdEntidadFederativa(1);
		
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void datosRequeridosPersistenciafalloPrimerNivel() {
		MunicipioDTO dto = new MunicipioDTO();
		dto.setFechaRegistro(null);
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.MUNICIPIOS_ID_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_NOMBRE_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_ENTIDAD_FED_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_FECHA_REG_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_USUARIO_REQ.getId()
						));
	}
	
	@Test
	public void datosRequeridosPersistenciafalloSegundoNivel() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		dto.setNombre("Municipio prueba");
		dto.setEntidadFederativa(new EntidadFederativaDTO());
		
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.MUNICIPIOS_ENTIDAD_FED_REQ.getId()
						));
	}
	
	@Test
	public void datosRequeridosActualizacionExito() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		dto.setNombre("Municipio prueba");
		dto.setEntidadFederativa(new EntidadFederativaDTO());
		dto.getEntidadFederativa().setIdEntidadFederativa(1);
		
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void datosRequeridosActualizacionfalloPrimerNivel() {
		MunicipioDTO dto = new MunicipioDTO();
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.MUNICIPIOS_ID_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_NOMBRE_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_ENTIDAD_FED_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_FECHA_EDI_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_USUARIO_REQ.getId()
						));
	}
	
	@Test
	public void datosRequeridosActualizacionfalloSegundoNivel() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		dto.setNombre("Municipio prueb");
		dto.setEntidadFederativa(new EntidadFederativaDTO());
		
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.MUNICIPIOS_ENTIDAD_FED_REQ.getId()
						));
	}
	
	@Test
	public void datosRequeridosEliminacionExito() {
		MunicipioDTO dto = new MunicipioDTO();
		
		dto.setIdMunicipio("99999");
		
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void datosRequeridosEliminacionFallo() {
		MunicipioDTO dto = new MunicipioDTO();
		
		ResultadoDTO<MunicipioDTO> resultado = municipioService.sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		assertThat(resultado.getMensajes(), 
				containsInAnyOrder(
						MensajesSistemaEnum.MUNICIPIOS_ID_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_FECHA_EDI_REQ.getId(),
						MensajesSistemaEnum.MUNICIPIOS_USUARIO_REQ.getId()
						));
	}

}
