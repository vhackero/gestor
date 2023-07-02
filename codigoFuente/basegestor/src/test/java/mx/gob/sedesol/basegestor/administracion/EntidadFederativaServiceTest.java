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
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
@Transactional
public class EntidadFederativaServiceTest {
	
	@Autowired
	private EntidadFederativaService entidadFederativaService;

	@Test
	public void obtenerEntidadesFederativas() {
		List<EntidadFederativaDTO> listaEntidades = entidadFederativaService.findAll();
		assertThat(listaEntidades, is(not(empty())));
	}

	@Test
	public void obtenerEntidadFederativa() {
		EntidadFederativaDTO dto = entidadFederativaService.buscarPorId(1);
		assertThat(dto, allOf(
				hasProperty("idEntidadFederativa",          is(1)), 
                hasProperty("nombre",      is("Aguascalientes")),
                hasProperty("abreviatura", is("Ags"))));
	}

	@Test
	public void obtenerEntidadFederativaNula() {
		EntidadFederativaDTO dto = entidadFederativaService.buscarPorId(99);
		assertThat(dto, is(nullValue()));
	}
	
	@Test
	public void obtenerEntidadesPorPais() {
		List<EntidadFederativaDTO> listaEntidades = entidadFederativaService.obtenerEntidadesPorPais("MX");
		assertThat(listaEntidades, is(not(empty())));
	}

}
