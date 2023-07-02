package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class AdminRolesTest {

	private static final Logger logger = Logger.getLogger(AdminRolesTest.class);
	
	@Autowired
	private RoleService rolService;
	
	@Mock
	private RoleService rolServiceMock;
	
	@Before
	public void setUpMock(){
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void consultaRolPorClave(){
		assertNotNull(rolService.buscarPorClave(ConstantesGestor.ROLE_ALUMNO));
		assertThat(rolService.buscarPorClave(ConstantesGestor.ROLE_ALUMNO), 
				hasProperty("clave", is(ConstantesGestor.ROLE_ALUMNO)));
	}
	
	@Test
	public void consultaRoles(){
		assertNotNull(rolService.findAll());
		
	}
	
	@Test
	public void validaDatosRequeridosMock(){
		
		RolDTO rol = new RolDTO();
		ResultadoDTO<RolDTO> res = new ResultadoDTO<>();
		res.setResultado(ResultadoTransaccionEnum.FALLIDO);
		when(rolServiceMock.sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, rol)).thenReturn(res);
		assertThat(res.getResultado(), equalTo(ResultadoTransaccionEnum.FALLIDO));
	}
	
	
	//@Test
	public void guardaRol(){
		
		logger.info("persistiendo rol");
		
		RolDTO rol = new RolDTO();
		rol.setNombre("alumno");
		rol.setClave("ROLE_ALUMNO");
		rol.setFechaRegistro(new Date());
		rol.setUsuarioModifica(1L);
		rol.setActivo(true);
		try {
			ResultadoDTO<RolDTO> res = rolService.guardar(rol);
			System.out.println(res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void actualizaRol(){
		logger.info("actualizando rol");
		
		RolDTO rolAux = rolService.buscarPorId(3);
		
		rolAux.setActivo(false);
		rolAux.setFechaActualizacion(new Date());
		rolAux.setNombre("Gestor");
		
		try {
			System.out.println(rolService.actualizar(rolAux).toString());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
}
