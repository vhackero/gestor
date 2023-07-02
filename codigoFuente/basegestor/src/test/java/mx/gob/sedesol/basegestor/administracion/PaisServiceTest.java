package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;
import mx.gob.sedesol.basegestor.service.admin.PaisService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class PaisServiceTest {
	
	private static final Logger logger = Logger.getLogger(PaisServiceTest.class);
	
	@Autowired
	private PaisService paisService;
	
	@Test
	public void buscarTodos() {
		List<PaisDTO> lista = paisService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		PaisDTO dto = paisService.buscarPorId("MX");
		logger.info(dto.getNombre());
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void buscarPorIdNull() {
		PaisDTO dto = paisService.buscarPorId("zzz");
		assertThat(dto,  is(nullValue()));
	}

}
