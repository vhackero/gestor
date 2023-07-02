package mx.gob.sedesol.basegestor.badges;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import mx.gob.sedesol.basegestor.administracion.BaseTest;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.ClasificacionBadgeDTO;
import mx.gob.sedesol.basegestor.service.badges.ClasificacionBadgeService;

/**
 * Test para el servicio de ClasificacionBadgeService
 * @author nnm_eolf
 *
 */
public class ClasificacionBadgeServiceTest extends BaseTest {
	
	@Autowired
	private ClasificacionBadgeService clasificacionBadgeService;
	
	private static final Logger LOG = Logger.getLogger(ClasificacionBadgeServiceTest.class);
	
	/**
	 * Metodo test para guardar
	 */
	@Ignore
	@Test
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    @Rollback(value = true)
	public void guardar() {
		LOG.info("[guardar]");
		int id = 4;
		ClasificacionBadgeDTO dto = new ClasificacionBadgeDTO();
		dto.setIdClasificacionBadge(id);
		dto.setNombre("NOMBRE 4");
		dto.setDescripcion("DESCRIPCION 4");
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setIdEstatus(4);
		ResultadoDTO<ClasificacionBadgeDTO> resultadoDTO = clasificacionBadgeService.guardar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		ClasificacionBadgeDTO clasificacionBadgeDTO = clasificacionBadgeService.buscarPorId(id);
		Assert.notNull(clasificacionBadgeDTO);
		LOG.info(clasificacionBadgeDTO.toString());
	}
	
	/**
	 * Metodo test para findAll
	 */
	@Ignore
	@Test
	public void findAll() {
		LOG.info("[findAll]");
		List<ClasificacionBadgeDTO> listClasificacionBadge = clasificacionBadgeService.findAll();
		recorrerLista(listClasificacionBadge);
	}
	
	/**
	 * Metodo test para buscarPorId
	 */
	@Ignore
	@Test
	public void buscarPorId() {
		LOG.info("[buscarPorId]");
		int id = 1;
		ClasificacionBadgeDTO clasificacionBadgeDTO = clasificacionBadgeService.buscarPorId(id);
		Assert.notNull(clasificacionBadgeDTO);
		LOG.info(clasificacionBadgeDTO.toString());
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
		ClasificacionBadgeDTO clasificacionBadgeDTO = clasificacionBadgeService.buscarPorId(id);
		Assert.notNull(clasificacionBadgeDTO);
		LOG.info(clasificacionBadgeDTO.toString());
		ClasificacionBadgeDTO dto = new ClasificacionBadgeDTO();
		dto.setIdClasificacionBadge(id);
		dto.setNombre("NOMBRE 4");
		dto.setDescripcion("DESCRIPCION 4");
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setIdEstatus(3);
		ResultadoDTO<ClasificacionBadgeDTO> resultadoDTO = clasificacionBadgeService.actualizar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		clasificacionBadgeDTO = clasificacionBadgeService.buscarPorId(id);
		Assert.notNull(clasificacionBadgeDTO);
		LOG.info(clasificacionBadgeDTO.toString());
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
		ClasificacionBadgeDTO dto = new ClasificacionBadgeDTO();
		dto.setIdClasificacionBadge(id);
		dto.setNombre("NOMBRE 1");
		dto.setUsuarioModifico(1L);
		ResultadoDTO<ClasificacionBadgeDTO> resultadoDTO = clasificacionBadgeService.eliminar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		ClasificacionBadgeDTO clasificacionBadgeDTO = clasificacionBadgeService.buscarPorId(id);
		Assert.isNull(clasificacionBadgeDTO);
	}
	
}
