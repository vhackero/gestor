package mx.gob.sedesol.basegestor.administracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Grupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.controller.WSClientBase;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

/**
 * Test para el servicio de ParametroWSMoodleService
 * @author eolf
 *
 */
public class ParametroWSMoodleServiceTest extends BaseTest {
	
	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;
	
	private static final Logger LOG = Logger.getLogger(ParametroWSMoodleServiceTest.class);
	
	/**
	 * Metodo test para guardar
	 */
	@Ignore
	@Test
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    @Rollback(value = true)
	public void guardar() {
		LOG.info("[guardar]");
		int id = 666;
		ParametroWSMoodleDTO dto = new ParametroWSMoodleDTO();
		dto.setIdParametroWSMoodle(id);
		dto.setHost("HOST 666");
		dto.setPath("PATH 666");
		dto.setService("SERVICE 666");
		dto.setUsername("USERNAME 666");
		dto.setPassword("PASSWORD 666");
		dto.setOuth("OUTH 666");
		dto.setServer("SERVER 666");
		dto.setActivo(false);
		dto.setNombre("NOMBRE 666");
		dto.setDescripcion("DESCRIPCION 666");
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		ResultadoDTO<ParametroWSMoodleDTO> resultadoDTO = parametroWSMoodleService.guardar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		ParametroWSMoodleDTO parametroWSMoodleDTO = parametroWSMoodleService.buscarPorId(id);
		Assert.notNull(parametroWSMoodleDTO);
		LOG.info(parametroWSMoodleDTO.toString());
	}
	
	/**
	 * Metodo test para buscarPorId
	 */
	@Ignore
	@Test
	public void buscarPorId() {
		LOG.debug("[buscarPorId]");
		int id = 1; 
		ParametroWSMoodleDTO dto = parametroWSMoodleService.buscarPorId(id);
		Assert.notNull(dto);
		LOG.info(dto.toString());
	}
	
	/**
	 * Metodo test para obtenerMoodleActivo
	 */
	@Ignore
	@Test
	public void obtenerMoodleActivo() {
		LOG.info("[obtenerMoodleActivo]");
		ParametroWSMoodleDTO dto = parametroWSMoodleService.obtenerMoodleActivo();
		Assert.notNull(dto);
		LOG.info(dto.toString());
	}
	
	@Ignore
	@Test
	public void ejecutarServicioListGET() throws ErrorWS {
		LOG.info("[ejecutarServicioListGET]");
		int idX = 1; 
		ParametroWSMoodleDTO dto = parametroWSMoodleService.buscarPorId(idX);
		LOG.info(dto.toString());
		
		List<Integer> ids = new ArrayList<>();
		ids.add(23);
        HashMap<String, Object> paramMap = new HashMap<>();
        int x = 0;
        for (int id : ids) {
            paramMap.put("groupids[" + x + "]", id);
            x++;
        }
		
        WSClientBase ws = new WSClientBase(dto);
		List<Grupo> respuesta = ws.ejecutarServicioListGET("core_group_get_groups", paramMap, new TypeReference<List<Grupo>>() {}, ids);
		LOG.info(respuesta);
	}
	
	/**
	 * Metodo test para actualizar
	 */
	@Ignore
	@Test
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    @Rollback(value = true)
	public void actualizar() {
		LOG.info("[actualizar]");
		int id = 1;
		ParametroWSMoodleDTO parametroWSMoodleDTO = parametroWSMoodleService.buscarPorId(id);
		Assert.notNull(parametroWSMoodleDTO);
		LOG.info(parametroWSMoodleDTO.toString());
		ParametroWSMoodleDTO dto = new ParametroWSMoodleDTO();
		dto.setIdParametroWSMoodle(id);
		dto.setHost("HOST X");
		dto.setPath("PATH X");
		dto.setService("SERVICE X");
		dto.setUsername("USERNAME X");
		dto.setPassword("PASSWORD X");
		dto.setOuth("OUTH X");
		dto.setServer("SERVER X");
		dto.setActivo(false);
		dto.setNombre("NOMBRE X");
		dto.setDescripcion("DESCRIPCION X");
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		ResultadoDTO<ParametroWSMoodleDTO> resultadoDTO = parametroWSMoodleService.actualizar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		parametroWSMoodleDTO = parametroWSMoodleService.buscarPorId(id);
		Assert.notNull(parametroWSMoodleDTO);
		LOG.info(parametroWSMoodleDTO.toString());
	}
	
	/**
	 * Metodo test para eliminar
	 */
	@Ignore
	@Test
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    @Rollback(value = true)
	public void eliminar() {
		LOG.info("[eliminar]");
		int id = 1;
		ParametroWSMoodleDTO dto = new ParametroWSMoodleDTO();
		dto.setIdParametroWSMoodle(id);
		dto.setNombre("NOMBRE X");
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		ResultadoDTO<ParametroWSMoodleDTO> resultadoDTO = parametroWSMoodleService.eliminar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		ParametroWSMoodleDTO parametroWSMoodleDTO = parametroWSMoodleService.buscarPorId(id);
		Assert.isNull(parametroWSMoodleDTO);
	}

}
