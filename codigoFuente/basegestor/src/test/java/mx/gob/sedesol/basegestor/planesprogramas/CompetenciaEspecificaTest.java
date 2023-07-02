package mx.gob.sedesol.basegestor.planesprogramas;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CompetenciaEspecificaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.planesyprogramas.CompetenciaEspecificaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@EnableTransactionManagement
public class CompetenciaEspecificaTest {
	private static final Logger logger = Logger.getLogger(CompetenciaEspecificaTest.class);

	@Autowired
	private CompetenciaEspecificaService competenciaEspecificaService;

	@Test
	public void obtenerTodas() {
		
		logger.info("Busqueda de comp Especificas");
		List<CompetenciaEspecificaDTO> ce;
		ce = competenciaEspecificaService.findAll();
		assertFalse(ce.isEmpty());
	}

	@Test
	public void porId() {
		
		logger.info("Busqueda por ID");
		CompetenciaEspecificaDTO ce;
		ce = competenciaEspecificaService.buscarPorId(1);
		assertThat(ce,notNullValue());
	}

	//@Test
	public void compEspEsVacio(){
		assertFalse(competenciaEspecificaService.estaVacio(1,"Comp Especifica"));
	}

	
	@Test
	public void guardaCompEsp() {
		logger.info("Persistiendo Comp especifica");
		
		ResultadoDTO<CompetenciaEspecificaDTO> res;
		CompetenciaEspecificaDTO ce = new CompetenciaEspecificaDTO();
		ce.setNombre("Competencia Especifica");
		ce.setFechaRegistro(new Date());
		ce.setPonderacion(10);
		ce.setUsuarioModifico(1L);
		ce.setActivo((byte) 1);

		res = competenciaEspecificaService.guardar(ce);
		assertThat(res.getDto(), notNullValue());
	}
	
	@Test
	public void actualizarCompEsp(){
		
		logger.info("Actualizando competencia especifica");
		CompetenciaEspecificaDTO objUp = competenciaEspecificaService.buscarPorId(1);
		
		if(ObjectUtils.isNull(objUp)){
			objUp.setNombre("Actualizacion Comp Esp");
			objUp.setFechaActualizacion(new Date());
			
			ResultadoDTO<CompetenciaEspecificaDTO> res = competenciaEspecificaService.actualizar(objUp);
			
			assertThat(res.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
			
		}
	}
	
	@Test
	public void eliminarCompEsp(){
		
		logger.info("Eliminando competencia especifica");
		CompetenciaEspecificaDTO objUp = competenciaEspecificaService.buscarPorId(29);
		ResultadoDTO<CompetenciaEspecificaDTO> res = competenciaEspecificaService.eliminar(objUp);
		assertThat(res.getResultado(), is(ResultadoTransaccionEnum.FALLIDO));
	}
	
}