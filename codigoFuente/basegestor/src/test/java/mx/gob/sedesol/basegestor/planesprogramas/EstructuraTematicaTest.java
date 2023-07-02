package mx.gob.sedesol.basegestor.planesprogramas;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEtematicaTemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstructuraTematicaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class EstructuraTematicaTest {
	
	@Autowired
	private EstructuraTematicaService estructuraTematicaService;
	
	@Autowired
	private FichaDescProgramaService fichaDescProgramaService;
	
	
	@Test
	public void buscarTodos(){
		List<EstructuraTematicaDTO> resCns = estructuraTematicaService.findAll();
		assertFalse(resCns.isEmpty());
	}
	
	@Test
	public void consultaPorId(){
		EstructuraTematicaDTO objCons =  estructuraTematicaService.buscarPorId(7);
		assertThat(objCons, notNullValue());
	}
	
	
	@Test
	public void registraEstTematica(){
		
		EstructuraTematicaDTO et = new EstructuraTematicaDTO();
		et.setActivo(1);
		et.setFechaRegistro(new Date());
		et.setTblFichaDescriptivaPrograma(fichaDescProgramaService.buscarPorId(40));
		et.setNumUnidadesTematicas(2);
		et.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		
		ResultadoDTO<EstructuraTematicaDTO> resTx =  estructuraTematicaService.guardar(et);
		assertThat(resTx.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
		
	}
	
	//@Test
	public void actualizarEstTematica(){
	
		EstructuraTematicaDTO objCons =  estructuraTematicaService.buscarPorId(7);
		if(ObjectUtils.isNotNull(objCons)){
			objCons.setFechaActualizacion(new Date());
			objCons.setNumUnidadesTematicas(3);
			objCons.setActivo(1);
			
			ResultadoDTO<EstructuraTematicaDTO> rsTx = estructuraTematicaService.actualizar(objCons);
			assertThat(rsTx.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
		}
	}
	
	public void eliminarEstTematica(){
		
		EstructuraTematicaDTO objCons =  estructuraTematicaService.buscarPorId(7);
		ResultadoDTO<EstructuraTematicaDTO> rsTx = estructuraTematicaService.eliminar(objCons);
		
		assertThat(rsTx.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
	}
	
	
	public void obtieneDetEtematicaTema(){
		DetEtematicaTemaDTO obj =  estructuraTematicaService.obtieneDetEtematicaTema(7, "Tema Informatica 1");
		assertThat(obj, notNullValue());
	}
	
	public void obtieneRelacionesPorEstTematica(){
		List<RelEstUnidadDidacticaDTO> rsCons = estructuraTematicaService.obtieneRelacionesPorEstTematica(7);
		assertFalse(rsCons.isEmpty());
	}
	
	
	public void obtieneEstTematicaTemaPorPrograma(){
		EstructuraTematicaDTO obCons = estructuraTematicaService.obtieneEstTematicaTemaPorPrograma(40);
		assertThat(obCons, notNullValue());
	}
	
	
	public void obtieneRelEstUnidadDidPorPrograma(){
		List<RelEstUnidadDidacticaDTO> rsCons = estructuraTematicaService.obtieneRelEstUnidadDidPorPrograma(40);
		assertFalse(rsCons.isEmpty());
	}
	
	
	public void obtieneEstUnidadesPorIdPrograma(){
		List<RelEstUnidadDidacticaDTO> rsCons = estructuraTematicaService.obtieneEstUnidadesPorIdPrograma(40);
		assertFalse(rsCons.isEmpty());
	}
	 
	/**
	 * Fuera de Version
	 * 
	public void generaSubTema(){
		
		EstructuraTematicaDTO temaPadre = estructuraTematicaService.buscarPorId(1);
		
		EstructuraTematicaDTO etHijo = new EstructuraTematicaDTO();
		etHijo.setActivo(1);
		etHijo.setFechaRegistro(new Date());
		etHijo.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		System.out.println("#### RESULTADO: " + estructuraTematicaService.guardar(etHijo));
		
	} */
	

	
	 
		public void prueba() {
			assertThat(1==2, is(false));
		}
}
