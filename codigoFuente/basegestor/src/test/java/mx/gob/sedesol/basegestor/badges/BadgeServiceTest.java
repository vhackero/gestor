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
import mx.gob.sedesol.basegestor.commons.dto.badges.BadgeDTO;
import mx.gob.sedesol.basegestor.service.badges.BadgeService;

/**
 * Test para el servicio de BadgeService
 * @author nnm_eolf
 *
 */
public class BadgeServiceTest extends BaseTest {
	
	@Autowired
	private BadgeService badgeService;
	
	private static final Logger LOG = Logger.getLogger(BadgeServiceTest.class);
	
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
		BadgeDTO dto = new BadgeDTO();
		dto.setIdBadge(id);
		dto.setIdClasificacionBadge(1);
		dto.setCalificacionMaxima(10);
		dto.setCalificacionMinima(1);
		dto.setNombre("BADGE 4");
		dto.setDescripcion("BADGE 4");
		dto.setRutaImagen("/tmp/ruta");
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(1L);
		dto.setIdEstatus(4);
		ResultadoDTO<BadgeDTO> resultadoDTO = badgeService.guardar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		BadgeDTO badgeDTO = badgeService.buscarPorId(id);
		Assert.notNull(badgeDTO);
		LOG.info(badgeDTO.toString());
	}
	
	/**
	 * Metodo test para findAll
	 */
	@Ignore
	@Test
	public void findAll() {
		LOG.info("[findAll]");
		List<BadgeDTO> listBadge = badgeService.findAll();
		recorrerLista(listBadge);
	}
	
	/**
	 * Metodo test para buscarPorId
	 */
	@Ignore
	@Test
	public void buscarPorId() {
		LOG.info("[buscarPorId]");
		int id = 1;
		BadgeDTO badgeDTO = badgeService.buscarPorId(id);
		Assert.notNull(badgeDTO);
		LOG.info(badgeDTO.toString());
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
		BadgeDTO badgeDTO = badgeService.buscarPorId(id);
		Assert.notNull(badgeDTO);
		LOG.info(badgeDTO.toString());
		BadgeDTO dto = new BadgeDTO();
		dto.setIdBadge(id);
		dto.setIdClasificacionBadge(2);
		dto.setCalificacionMaxima(100);
		dto.setCalificacionMinima(10);
		dto.setNombre("BADGE X");
		dto.setDescripcion("BADGE X");
		dto.setRutaImagen("/tmp/rutaX");
		dto.setFechaActualizacion(new Date());
		dto.setUsuarioModifico(1L);
		dto.setIdEstatus(4);
		ResultadoDTO<BadgeDTO> resultadoDTO = badgeService.actualizar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		badgeDTO = badgeService.buscarPorId(id);
		Assert.notNull(badgeDTO);
		LOG.info(badgeDTO.toString());
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
		BadgeDTO dto = new BadgeDTO();
		dto.setIdBadge(id);
		dto.setNombre("BADGE 4");
		dto.setUsuarioModifico(1L);
		ResultadoDTO<BadgeDTO> resultadoDTO = badgeService.eliminar(dto);
		Assert.notNull(resultadoDTO);
		LOG.info(resultadoDTO.toString());
		BadgeDTO badgeDTO = badgeService.buscarPorId(id);
		Assert.isNull(badgeDTO);
	}
	
}
