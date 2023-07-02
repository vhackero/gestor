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
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.repositories.admin.RolesRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.RoleService;
import mx.gob.sedesol.basegestor.service.impl.admin.RoleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class RoleServiceMockTest {
/*
	private static final Logger logger = Logger.getLogger(RoleServiceMockTest.class);

	@Mock
	private RolesRepo rolesRepo;

	@Mock
	private AdministradorBitacora administradorBitacora;

	@InjectMocks
	private RoleService roleService = new RoleServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirException() {
		RolDTO dto = new RolDTO(1L);
		dto.setClave("ROL");
		dto.setNombre("Rol");

		Mockito.when(rolesRepo.save((CatRol) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		try {
			ResultadoDTO<RolDTO> resultado = roleService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarException() {
		RolDTO dto = new RolDTO(1L);
		dto.setClave("ROL");
		dto.setNombre("Rol");
		dto.setFechaActualizacion(new Date());

		Mockito.when(rolesRepo.saveAndFlush((CatRol) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		try {
			ResultadoDTO<RolDTO> resultado = roleService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarException() {
		RolDTO dto = new RolDTO(1L);
		dto.setClave("ROL");
		dto.setNombre("Rol");
		dto.setFechaActualizacion(new Date());

		Mockito.when(rolesRepo.saveAndFlush((CatRol) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		try {
			ResultadoDTO<RolDTO> resultado = roleService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
*/
}
