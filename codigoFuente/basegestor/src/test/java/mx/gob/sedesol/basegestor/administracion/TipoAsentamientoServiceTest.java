package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.TipoAsentamientoDTO;
import mx.gob.sedesol.basegestor.service.admin.TipoAsentamientoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class TipoAsentamientoServiceTest {
	
	@Autowired
	private TipoAsentamientoService tipoAsentamientoService;
	
	@Test
	public void obtenerTiposAsentamiento() {
		List<TipoAsentamientoDTO> listaTiposAsentamiento = tipoAsentamientoService.findAll();
		assertThat(listaTiposAsentamiento, is(not(empty())));
	}
	

	@Test
	public void obtenerPorIdExito() {
		TipoAsentamientoDTO dto = tipoAsentamientoService.buscarPorId(1);
		
		assertThat(dto, allOf(
				hasProperty("idTipoAsentamiento", is(1)), 
				hasProperty("descripcion", is("Aeropuerto"))
				));
	}
	
	@Test
	public void obtenerPorIdFallo() {
		TipoAsentamientoDTO dto = tipoAsentamientoService.buscarPorId(999);
		assertThat(dto, is(nullValue()));
	}

}
