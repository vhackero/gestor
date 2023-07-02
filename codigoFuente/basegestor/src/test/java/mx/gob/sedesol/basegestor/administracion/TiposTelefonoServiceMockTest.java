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
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTiposTelefono;
import mx.gob.sedesol.basegestor.model.repositories.admin.TiposTelefonoRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.TiposTelefonoService;
import mx.gob.sedesol.basegestor.service.impl.admin.TiposTelefonoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class TiposTelefonoServiceMockTest {
/*
	private static final Logger logger = Logger.getLogger(RoleServiceMockTest.class);

	@Mock
	private TiposTelefonoRepo tiposTelefonoRepo;

	@Mock
	private AdministradorBitacora administradorBitacora;

	@InjectMocks
	private TiposTelefonoService tiposTelefonoService = new TiposTelefonoServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirExito() {

		Mockito.when(tiposTelefonoRepo.save((CatTiposTelefono) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarExito() {

		Mockito.when(tiposTelefonoRepo.saveAndFlush((CatTiposTelefono) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setIdTipoTelefono(1);
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarExito() {

		Mockito.when(tiposTelefonoRepo.saveAndFlush((CatTiposTelefono) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		TipoTelefonoDTO dto = new TipoTelefonoDTO();
		dto.setIdTipoTelefono(1);
		dto.setDescripcion("tipo");
		dto.setActivo(1);
		dto.setFechaRegistro(new Date());
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<TipoTelefonoDTO> resultado = tiposTelefonoService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
*/
}
