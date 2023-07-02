package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

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

import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;
import mx.gob.sedesol.basegestor.model.repositories.admin.OrgGubernamentalRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.impl.admin.OrgGubernamentalServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class OrgGubernamentalServiceMockTest {
/*
	private static final Logger logger = Logger.getLogger(OrgGubernamentalServiceMockTest.class);

	@Mock
	private OrgGubernamentalRepo orgGubernamentalRepo;

	@Mock
	private AdministradorBitacora administradorBitacora;

	@InjectMocks
	private OrgGubernamentalService orgGubernamentalService = new OrgGubernamentalServiceImpl();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void persistirException() {

		Mockito.when(orgGubernamentalRepo.save((TblOrganismoGubernamental) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);
		dto.setDescripcion("Descripcion");
		dto.setIdTipoOrganismo(1);
		dto.setNombre("Nombre");
		dto.setOrden(0);

		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.guardar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void actualizarException() {

		Mockito.when(orgGubernamentalRepo.saveAndFlush((TblOrganismoGubernamental) Mockito.anyObject()))
				.thenThrow(new NullPointerException("Esta es una excepcion provocada"));

		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setId(1);
		dto.setFechaActualizacion(new Date());
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);
		dto.setDescripcion("Descripcion");
		dto.setIdTipoOrganismo(1);
		dto.setNombre("Nombre");
		dto.setOrden(0);

		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.actualizar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}

	@Test
	public void eliminarException() {
		
		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(orgGubernamentalRepo)
				.delete((TblOrganismoGubernamental) Mockito.anyObject());
		
		Mockito.doNothing().when(administradorBitacora).almacenarBitacora(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString());

		OrgGubernamentalDTO dto = new OrgGubernamentalDTO();
		dto.setId(1);
		dto.setFechaActualizacion(new Date());
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setActivo(1);
		dto.setDescripcion("Descripcion");
		dto.setIdTipoOrganismo(1);
		dto.setNombre("Nombre");
		dto.setOrden(0);

		try {
			ResultadoDTO<OrgGubernamentalDTO> resultado = orgGubernamentalService.eliminar(dto);

			assertThat(resultado.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			assertTrue(false);
		}
	}
	
	@Test
	public void obtenerOrgGubsPadres() {
		Mockito.when(orgGubernamentalRepo.obtenerOrgGubsPadres())
		.thenReturn(null);
		
		List<OrgGubernamentalDTO> lista = orgGubernamentalService.obtenerOrgGubsPadres();
		assertThat(lista, is(empty()));
	}
*/
}
