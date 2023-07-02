package mx.gob.sedesol.basegestor.planesprogramas;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblMallaCurricular;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.MallaCurricularRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.MallaCurricularServiceImpl;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class MockPlanProgramaTest {

	@Mock
	private MallaCurricularRepo mallaCurricularRepo;
	
	@Mock
	private AdministradorBitacora administradorBitacora;
	
	
	@InjectMocks
	private MallaCurricularServiceImpl mallaCurricularServiceImpl = new MallaCurricularServiceImpl();
	
	//Servicios sin Mock
	@Autowired
	private CatalogoComunService<CatObjetoCurricular, Integer> objetoCurricularService;
	@Autowired
	private MallaCurricularService mallaCurricularService;
	
	
	@Before
	public void setUpMock(){
		MockitoAnnotations.initMocks(this);
	}
	
	
	//@Test
	public void testInjectService(){
		
		TblMallaCurricular stubEntity = new TblMallaCurricular();
		stubEntity.setActivo(1);
		stubEntity.setId(1);
		stubEntity.setNombre("PLAN SEDESOL");
		stubEntity.setDescripcion("descripcion");
/*
		when(mallaCurricularRepo.save(any(TblMallaCurricular.class))).thenReturn(stubEntity);
		doNothing().when(administradorBitacora).almacenarBitacora(1L, null, ConstantesBitacora.MALLA_CURR_AGREGAR);
*/
		MallaCurricularDTO dto = new MallaCurricularDTO();
		dto.setActivo(1);
		dto.setNombre("Plan Sedesol");
		dto.setFechaRegistro(new Date());
		
		CatalogoComunDTO ccomun =  objetoCurricularService.buscarPorId(2, CatObjetoCurricular.class);
		dto.setObjetoCurricular(ccomun);
		
		ResultadoDTO res = mallaCurricularServiceImpl.guardar(dto);
		
		 // Validation  
        assertNotNull(res.getDto());
        assertThat( ((MallaCurricularDTO)res.getDto()),
        		hasProperty("id", is(1)));
       
	}
	
	//@Test
	public void guardaPlanCurricularDB(){
		
		MallaCurricularDTO dto = new MallaCurricularDTO();
		dto.setActivo(1);
		dto.setNombre("Plan Sedesol Test");
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		dto.setNombreUsuarioMod("system");
		
		//Tipo de Objeto
		CatalogoComunDTO ccomun =  objetoCurricularService.buscarPorId(2, CatObjetoCurricular.class);
		dto.setObjetoCurricular(ccomun);
		
		ResultadoDTO res = mallaCurricularService.guardar(dto);
		assertNotNull(res.getDto());
		
	}
	
	//@Test
	public void guardaEstructuraCurricularDB(){
		
		MallaCurricularDTO estructura = new MallaCurricularDTO();
		estructura.setActivo(1);
		estructura.setNombre("Estructura_2_Test");
		estructura.setDescripcion("estructura 2 del plan sedesol test");
		estructura.setFechaRegistro(new Date());
		estructura.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		estructura.setNombreUsuarioMod("system");
		
		//Tipo de Objeto
		CatalogoComunDTO tpoEstructura =  objetoCurricularService.buscarPorId(3, CatObjetoCurricular.class);
		estructura.setObjetoCurricular(tpoEstructura);
		
		//Plan Padre
		estructura.setMallaCurricularPadre(mallaCurricularService.buscarPorId(1));
		
		ResultadoDTO res = mallaCurricularService.guardar(estructura);
		assertNotNull(res.getDto());
	}
	
	//@Test
	public void guardaSubEstructuraCurrDB(){
		
		MallaCurricularDTO subEstructura = getDefinicionMallaCurricular();
		ResultadoDTO res = mallaCurricularService.guardar(subEstructura);
		assertNotNull(res.getDto());
	}
	
	//@Test
	public void guardaProgramaCurrDB(){
		MallaCurricularDTO programa = getDefinicionMallaCurricular();
		ResultadoDTO res = mallaCurricularService.guardar(programa);
		assertNotNull(res.getDto());
	}
	
	//@Test
	public void buscarMallaCurricularPorId(){
		 MallaCurricularDTO mallaPadre = mallaCurricularService.obtenerMallaCurricularPorId(1);
		 if(ObjectUtils.isNotNull(mallaPadre)){
			 System.out.println("#Padre: " + mallaPadre.getNombre());
			 this.muestraHijosMalla(mallaPadre.getLstHijosMallaCurr());
		 }
	}
	
	/**
	 * 
	 * @param mallasHijo
	 */
	private void muestraHijosMalla(List<MallaCurricularDTO> mallasHijo){
		if(!ObjectUtils.isNullOrEmpty(mallasHijo)){
			 for(MallaCurricularDTO mallaHijo : mallasHijo){
				 System.out.println("#Hijo: " + mallaHijo.getNombre());
				 if( mallaHijo.getLstHijosMallaCurr() != null &&  !mallaHijo.getLstHijosMallaCurr().isEmpty()){
					 muestraHijosMalla(mallaHijo.getLstHijosMallaCurr());
				 }
			 }
		 }
	}

	
	private MallaCurricularDTO  getDefinicionMallaCurricular(){
		
		MallaCurricularDTO obj = new MallaCurricularDTO();
		obj.setActivo(1);
		obj.setNombre("programa 2.1");
		obj.setDescripcion("programa perteneciente a la estructura 2");
		obj.setFechaRegistro(new Date());
		obj.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		obj.setNombreUsuarioMod("system");
		
		//Tipo de Objeto
		CatalogoComunDTO tpoEstructura =  objetoCurricularService.buscarPorId(5, CatObjetoCurricular.class);
		obj.setObjetoCurricular(tpoEstructura);
		
		//id Padre
		MallaCurricularDTO mallaPadre = mallaCurricularService.obtenerMallaCurricularPorId(3);
		obj.setMallaCurricularPadre(mallaPadre);
		
		return obj;
	}
	
	 @Test
		public void prueba() {
			assertThat(1==2, is(false));
		}
}
