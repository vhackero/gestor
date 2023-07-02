package mx.gob.sedesol.basegestor.planesprogramas;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

public class MockObjetoCurricularTest {

	@Mock
	private MallaCurricularDTO mallaCurrDTO;
	@Mock
	private MallaCurricularService mallaCurricularService;
	@Mock
	private CatalogoComunService<CatObjetoCurricular, Integer> objetoCurricularService;
	
	@Before
	public void setUpMock(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void verificaServicios(){
		assertNotNull(mallaCurrDTO);
		assertNotNull(mallaCurricularService);
	}
	
	@Test
	public void testFind(){
		
		CatalogoComunDTO catStub = new CatalogoComunDTO();
		catStub.setNombre("Test Mocking");
		catStub.setActivo(1);
		catStub.setId(122);
		
		when(objetoCurricularService.buscarPorId(2,CatObjetoCurricular.class)).thenReturn(catStub);
		
		assertThat(objetoCurricularService.buscarPorId(2,CatObjetoCurricular.class), 
				allOf(hasProperty("nombre", is("Test Mocking")), 
				  hasProperty("activo", is(1)),
				  hasProperty("id", is(122))));
		
		verify(objetoCurricularService, atLeastOnce()).buscarPorId(2, CatObjetoCurricular.class);
	}
	
	
	public void generaObjetoCurricularTest(){
		
		CatalogoComunDTO objCurr = new CatalogoComunDTO();
		objCurr.setNombre("Programa");
		objCurr.setDescripcion("objeto curricular de tipo programa");
		objCurr.setActivo(1);
		objCurr.setFechaRegistro(new Date());
		objCurr.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		
		try {
			ResultadoDTO res = objetoCurricularService.guardar(objCurr, CatObjetoCurricular.class);
			assertThat(res.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	//@Test
	public void buscaObjetoCurricularById(){
		CatalogoComunDTO resDto = objetoCurricularService.buscarPorId(2, CatObjetoCurricular.class);
		assertThat(resDto, hasProperty("id", is(2)));
	}
}
