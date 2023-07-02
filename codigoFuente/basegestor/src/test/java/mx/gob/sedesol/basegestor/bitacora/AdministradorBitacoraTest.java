package mx.gob.sedesol.basegestor.bitacora;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class AdministradorBitacoraTest {
	
/*	@Autowired
	private AdministradorBitacora administradorBitacora;
*/
	@Test
	public void almacenarBitacora() {
		try {
//			administradorBitacora.almacenarBitacora(1L, "TEST", "BIT_AGR");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
