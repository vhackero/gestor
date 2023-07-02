package mx.gob.sedesol.basegestor.gestion.aprendizaje;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ReponsableProduccionOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.ReponsableProduccionOaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class ReponsableProduccionOaTest {

	/**
	 * variable logger
	 */
	private static final Logger logger = Logger.getLogger(ReponsableProduccionOaTest.class);
	
	/**
	 * Inyeccion del servicio 
	 */
	@Autowired private ReponsableProduccionOaService reponsableProduccionOaService;
	
	
	//@Test
	public void findAllTest(){
		List<ReponsableProduccionOaDTO> dtos = 
		
		reponsableProduccionOaService.findAll();
		
		
		logger.info("***** El numero de registros es : "+dtos.size() +"*****");
	}

	
	//@Test 
	/*	
		List<Integer> idsUnidadOa = new ArrayList<Integer>();
		idsUnidadOa.add(new Integer(1));
		idsUnidadOa.add(new Integer(2));
		
		
		List<ReponsableProduccionOaDTO> dtos = 
				
				reponsableProduccionOaService.findByIds(idsUnidadOa);
		
		logger.info("***** El numero de registros es : "+dtos.size() +"*****");
		
	}
	
	//@Test
	public void findByTipoResponsabilidadTest(){
		
		CatalogoComunDTO catalogoComunDTO = new CatalogoComunDTO();
		catalogoComunDTO.setId(1);
		
		
		List<ReponsableProduccionOaDTO> responsableProduccionOaDTOs = 
		reponsableProduccionOaService.findByTipoResponsabilidad(catalogoComunDTO);
		
		logger.info("El numero de responsables encontrados es "+responsableProduccionOaDTOs.size());
		
		
	}
	*/
	//@Test
/*	public void findByIdUnidadOaAVATest(){
		
		UnidadOaAvaDTO unidadOaAvaDTO = new UnidadOaAvaDTO(); 
		
		unidadOaAvaDTO.setId(1);
		
		List<ReponsableProduccionOaDTO> responsableProduccionOaDTOs = 
		reponsableProduccionOaService.findByIdUnidadAva(unidadOaAvaDTO);
		
		logger.info("El numero de responsables encontrados es "+responsableProduccionOaDTOs.size());
		
		
	}*/
	
	
	//@Test
		public void findByTipoResponsabilidadesTest(){
			
			
			
			List<Integer> catalogoComunDTOs = new ArrayList<Integer>();
			catalogoComunDTOs.add(2);
			catalogoComunDTOs.add(5);
			catalogoComunDTOs.add(6);
			catalogoComunDTOs.add(7);
			
			
			List<ReponsableProduccionOaDTO> responsableProduccionOaDTOs = 
			reponsableProduccionOaService.findByTipoResponsabilidades(catalogoComunDTOs);
			
			logger.info("El numero de responsables encontrados es "+responsableProduccionOaDTOs.size());
			
			
		}
	//@Test
	public void guardarTest(){
		List<ReponsableProduccionOaDTO> reponsableProduccionOaDTOs = 
		reponsableProduccionOaService.findAll();
		
		logger.info(
		reponsableProduccionOaDTOs.size());
		
		ReponsableProduccionOaDTO reponsableProduccionOaDTO = new ReponsableProduccionOaDTO();
		reponsableProduccionOaDTO.setFechaRegistro(new Date());
		reponsableProduccionOaDTO.setFechaActualizacion(new Date());
		reponsableProduccionOaDTO.setUsuarioModifico(BigInteger.valueOf(1) );
		reponsableProduccionOaDTO.setIdUnidadOa(1);
		reponsableProduccionOaDTO.setIdResponsableProduccion(20);
		
	
		ResultadoDTO<ReponsableProduccionOaDTO> resultado  = 
		reponsableProduccionOaService.guardar(reponsableProduccionOaDTO);
		
		logger.info("El resultado de la operacion es "+resultado.getResultado());
		
		List<ReponsableProduccionOaDTO> reponsableProduccionOaDTO2s = 
				reponsableProduccionOaService.findAll();
				
				logger.info(
						reponsableProduccionOaDTO2s.size());
	}	
	
	//@Test
	/*public void actualizarTest(){
		List<ReponsableProduccionOaDTO> reponsableProduccionOaDTOs = 
		reponsableProduccionOaService.findAll();
		
		logger.info(
		reponsableProduccionOaDTOs.size());
		
		ReponsableProduccionOaDTO reponsableProduccionOaDTO = 
				reponsableProduccionOaDTOs.get(0);
	
		reponsableProduccionOaDTO.setIdResponsableProduccion(20);
		reponsableProduccionOaDTO.setPersonaResponsabilidade(null);
		
		//PersonaResponsabilidadesDTO dto = new PersonaResponsabilidadesDTO();
		//dto.setId(20);
		
		//reponsableProduccionOaDTO.setPersonaResponsabilidade(dto);
		
		ResultadoDTO<ReponsableProduccionOaDTO> resultado  = 
		reponsableProduccionOaService.actualizarByIdAvaAndIdRespProd
			(reponsableProduccionOaDTO,reponsableProduccionOaDTO.getIdUnidadOa(),Integer.valueOf(20));
		
		logger.info("El resultado de la operacion es "+resultado.getResultado());
		
		List<ReponsableProduccionOaDTO> reponsableProduccionOaDTO2s = 
				reponsableProduccionOaService.findAll();
				
				logger.info(
						reponsableProduccionOaDTO2s.size());
	}
	*/
	
	//@Test
	public void actualizarTest2(){
		List<ReponsableProduccionOaDTO> reponsableProduccionOaDTOs = 
		reponsableProduccionOaService.findAll();
		
		logger.info(
		reponsableProduccionOaDTOs.size());
		
		ReponsableProduccionOaDTO reponsableProduccionOaDTO = 
				reponsableProduccionOaDTOs.get(0);
	
		reponsableProduccionOaDTO.setIdResponsableProduccion(20);
		reponsableProduccionOaDTO.setPersonaResponsabilidade(null);
		reponsableProduccionOaDTO.setUnidadOaAva(null);
		
		PersonaResponsabilidadesDTO dto = new PersonaResponsabilidadesDTO();
		dto.setId(20);
		
		UnidadOaAvaDTO avaDTO = new UnidadOaAvaDTO();
		avaDTO.setId(reponsableProduccionOaDTO.getIdUnidadOa());
		
		reponsableProduccionOaDTO.setPersonaResponsabilidade(dto);
		reponsableProduccionOaDTO.setUnidadOaAva(avaDTO);
		
		ResultadoDTO<ReponsableProduccionOaDTO> resultado  = 
		reponsableProduccionOaService.actualizar(reponsableProduccionOaDTO);
		
		logger.info("El resultado de la operacion es "+resultado.getResultado());
		
		List<ReponsableProduccionOaDTO> reponsableProduccionOaDTO2s = 
				reponsableProduccionOaService.findAll();
				
				logger.info(
						reponsableProduccionOaDTO2s.size());
	}
	
		
	@Test
	public void test(){
		
		Assert.assertTrue(true);
	}
	
	
	
	
	
	
}

