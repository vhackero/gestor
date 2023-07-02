package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class ModalidadPlanProgTest {
	
	private static final Logger logger = Logger.getLogger(ModalidadPlanProgTest.class);
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}

}
