package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTiposCorreo;
import mx.gob.sedesol.basegestor.model.repositories.admin.TiposCorreoRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.TiposCorreoService;
import mx.gob.sedesol.basegestor.service.impl.admin.TiposCorreoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class TiposCorreoServiceMockTest {
/*
	private static final Logger logger = Logger.getLogger(TiposCorreoServiceMockTest.class);

	@Mock
	private TiposCorreoRepo tiposCorreoRepo;

	@Mock
	private AdministradorBitacora administradorBitacora;

	@InjectMocks
	private TiposCorreoService tiposCorreoService = new TiposCorreoServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirException() {

		Mockito.when(tiposCorreoRepo.save((CatTiposCorreo) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		TipoCorreoDTO dto = new TipoCorreoDTO();
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarException() {

		Mockito.when(tiposCorreoRepo.saveAndFlush((CatTiposCorreo) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		TipoCorreoDTO dto = new TipoCorreoDTO();
		dto.setIdTipoCorreo(1);
		dto.setDescripcion("Institucional");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarException() {

		Mockito.when(tiposCorreoRepo.saveAndFlush((CatTiposCorreo) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		TipoCorreoDTO dto = new TipoCorreoDTO();
		dto.setIdTipoCorreo(1);
		dto.setDescripcion("Institucional");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<TipoCorreoDTO> resultado = tiposCorreoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
*/
}
