package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;
import mx.gob.sedesol.basegestor.service.admin.LoteCargaUsuarioService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class LoteCargaUsuarioServiceTest {
	
	@Autowired
	private LoteCargaUsuarioService loteCargaUsuarioService;
	
	@Test
	public void obtenerLotesNulo() {
		LoteCargaUsuarioDTO criterios = null;
		List<LoteCargaUsuarioDTO> lista = loteCargaUsuarioService.buscarPorCriterios(criterios);
		assertThat(lista, is(empty()));
	}
	
	@Test
	public void obtenerLotes() {
		LoteCargaUsuarioDTO criterios = new LoteCargaUsuarioDTO();
		criterios.setFechaInicio(new Date());
		criterios.setFechaFin(new Date());
		List<LoteCargaUsuarioDTO> lista = loteCargaUsuarioService.buscarPorCriterios(criterios);
		assertThat(lista, is(empty()));
	}

}
