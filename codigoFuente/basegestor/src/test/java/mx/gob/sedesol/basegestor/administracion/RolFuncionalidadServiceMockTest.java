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

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolFuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.entities.admin.RelRolFuncionalidad;
import mx.gob.sedesol.basegestor.model.entities.admin.TblFuncionalidad;
import mx.gob.sedesol.basegestor.model.repositories.admin.RolFuncionalidadRepo;
import mx.gob.sedesol.basegestor.service.admin.RolFuncionalidadService;
import mx.gob.sedesol.basegestor.service.impl.admin.RolFuncionalidadServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class RolFuncionalidadServiceMockTest {

	private static final Logger logger = Logger.getLogger(RolFuncionalidadServiceMockTest.class);

	@Mock
	private RolFuncionalidadRepo rolFuncionalidadRepo;

	@InjectMocks
	private RolFuncionalidadService rolFuncionalidadService = new RolFuncionalidadServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirException() {

		Mockito.when(rolFuncionalidadRepo.save((RelRolFuncionalidad) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarException() {
		
		RelRolFuncionalidad entidad = new RelRolFuncionalidad();
		entidad.setFuncionalidad(new TblFuncionalidad());
		entidad.getFuncionalidad().setIdFuncionalidad(1L);
		entidad.setRol(new CatRol());
		entidad.getRol().setIdRol(1);
		
		Mockito.when(rolFuncionalidadRepo.findOne(Mockito.anyLong())).thenReturn(entidad);

		Mockito.when(rolFuncionalidadRepo.saveAndFlush((RelRolFuncionalidad) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setIdRolFuncionalidad(108L);
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);

		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarException() {

		Mockito.when(rolFuncionalidadRepo.saveAndFlush((RelRolFuncionalidad) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		RolFuncionalidadDTO dto = new RolFuncionalidadDTO();
		dto.setRol(new RolDTO());
		dto.getRol().setIdRol(1);
		dto.setFuncionalidadDTO(new FuncionalidadDTO());
		dto.getFuncionalidadDTO().setIdFuncionalidad(1L);
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);

		try {
			ResultadoDTO<RolFuncionalidadDTO> resultado = rolFuncionalidadService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

}
