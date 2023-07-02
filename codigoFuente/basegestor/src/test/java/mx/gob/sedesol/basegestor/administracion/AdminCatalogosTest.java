package mx.gob.sedesol.basegestor.administracion;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class AdminCatalogosTest {

//	@Autowired
//	private RoleService rolService;
	
	//@Test
//	public void consultaRoles(){
//		List<RolDTO> roles = rolService.findAll();
//		for(RolDTO rol : roles){
//			System.out.println(rol.toString());
//		}
//	}
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
}
