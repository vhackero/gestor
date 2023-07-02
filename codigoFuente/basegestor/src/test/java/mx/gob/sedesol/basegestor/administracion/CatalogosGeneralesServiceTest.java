package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
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

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DatoValorCatGeneralDTO;
import mx.gob.sedesol.basegestor.service.admin.CatalogosGeneralesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CatalogosGeneralesServiceTest {
	
	@Autowired
	private CatalogosGeneralesService catalogosGeneralesService;
	
	@Test
	public void buscarTodos() {
		List<CatalogoGeneralDTO> lista = catalogosGeneralesService.findAll();
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void buscarPorId() {
		CatalogoGeneralDTO dto = catalogosGeneralesService.buscarPorId(1);
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void obtenerCatalogoPorCveCat() {
		CatalogoGeneralDTO dto = catalogosGeneralesService.obtenerCatalogoPorCveCat("cat_tpo_plataforma_lms");
		assertThat(dto, is(not(nullValue())));
	}
	
	@Test
	public void obtenerCatalogoPorCveCatNulo() {
		CatalogoGeneralDTO dto = catalogosGeneralesService.obtenerCatalogoPorCveCat(null);
		assertThat(dto, is(nullValue()));
	}
	
	@Test
	public void obtenerValoresPorCveCatalogo() {
		List<DatoValorCatGeneralDTO> lista = catalogosGeneralesService.obtenerValoresPorCveCatalogo("cat_tpo_plataforma_lms");
		assertThat(lista, is(not(empty())));
	}
	
	@Test
	public void obtenerValoresPorCveCatalogoNulo() {
		List<DatoValorCatGeneralDTO> lista = catalogosGeneralesService.obtenerValoresPorCveCatalogo(null);
		assertThat(lista, is(empty()));
	}
	
}
