/**
 * 
 */
package mx.gob.sedesol.basegestor.encuestas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.CatEncuestaContextoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaContexto;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaEstatus;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaObjetivo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.service.encuestas.CatalogoComunEncuestaService;

/**
 * @author jhcortes
 *
 *         Pruebas unitarias de catalogos de encuestas CRUD.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudEncuestaCatComunTest {

	private static final Logger logger = Logger.getLogger(CrudEncuestaCatComunTest.class);

	@Autowired
	private CatalogoComunEncuestaService<CatEncuestaContexto, Integer> encuestaContextoService;
	@Autowired
	private CatalogoComunEncuestaService<CatEncuestaEstatus, Integer> encuestaEstatusService;
	@Autowired
	private CatalogoComunEncuestaService<CatEncuestaObjetivo, Integer> encuestaObjetivoService;
	@Autowired
	private CatalogoComunEncuestaService<CatEncuestaTipo, Integer> encuestaTipoService;

	private ModelMapper mapper = new ModelMapper();

	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void consultaContexto() {
		assertNotNull(encuestaContextoService.findAll(CatEncuestaContexto.class));
	}

	@Test
	public void consultaEstatus() {
		assertNotNull(encuestaEstatusService.findAll(CatEncuestaEstatus.class));
	}

	@Test
	public void consultaObjetivo() {
		assertNotNull(encuestaObjetivoService.findAll(CatEncuestaObjetivo.class));
	}

	@Test
	public void consultaTipo() {
		assertNotNull(encuestaTipoService.findAll(CatEncuestaTipo.class));
	}

	/**
	 * Insertar registro de elementos de catalogo contexto
	 */
	@Test
	public void insertarContextoTest() {
		logger.info(
				"prueba de inserción de contexto  *****************************************************************");
		CatalogoComunDTO encuestaContexto = new CatalogoComunDTO();
		encuestaContexto.setActivo(1);
		encuestaContexto.setFechaRegistro(new Date());
		encuestaContexto.setNombre("Encuestas KirkPatrick");
		encuestaContexto.setNombreUsuarioMod("PlanetMedia Test");
		encuestaContexto.setDescripcion("Contexto de encuestas kirkPatrick.");
		encuestaContexto.setOrden(1);
		encuestaContexto.setUsuarioModifico(1l);

		try {

			logger.info(
					"RESULTADO {" + encuestaContextoService.guardar(encuestaContexto, CatEncuestaContexto.class) + "}");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info(
				"fin prueba de inserción  *************************************************************************");
	}

	@SuppressWarnings("unused")
	private CatEncuestaTipo getCatTipo(Integer id) {
		CatalogoComunDTO tipo = encuestaTipoService.buscarPorId(id, CatEncuestaTipo.class);
		return mapper.map(tipo, CatEncuestaTipo.class);
	}

	/**
	 * inserción masiva del estatus, catalogo de encuesta tipo
	 */
	@Test
	public void insertarEstatusTest() {
		logger.info("inserciion de estatus de encuesta ****************************************************");
		String[][] estatusLst = { { "Análisis y Diseño", "Diseño de la encuesta, aun no definida" },
				{ "Revisión", "En revision por el coordinador y supervisor" },
				{ "Asignada", "Encuesta asignada lista para ser publicada" }, { "Publicada",
						"Encuesta publicada, ya disponible para el curso, imposible su edición en este estado." } };

		for (int x = 0; x < estatusLst.length; x++) {
			CatalogoComunDTO encuestaEstatus = new CatalogoComunDTO();
			encuestaEstatus.setActivo(1);
			encuestaEstatus.setFechaRegistro(new Date());
			encuestaEstatus.setNombre(estatusLst[x][0]);
			encuestaEstatus.setDescripcion(estatusLst[x][1]);
			encuestaEstatus.setNombreUsuarioMod("system");
			encuestaEstatus.setOrden(1);
			encuestaEstatus.setUsuarioModifico(1l);

			try {

				logger.info("RESULTADO {" + encuestaEstatusService.guardar(encuestaEstatus, CatEncuestaEstatus.class)
						+ "}");

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("fin de insercion de estatus de encuesta  *************************************************");
	}

	/**
	 * Insercion masiva de objeto de objetivo de encuesta.
	 */
	@Test
	public void insertarObjetivoTest() {
		logger.info(
				"insercion de encuesta dirigido a ****************************************************************");
		String[][] dirigidoA = { { "Profesores", "Dirigido a profesores en general." },
				{ "Alumnos Internos", "Dirigido a alumnos que pertenecen a la dependencia." },
				{ "Alumnos Externos", "Dirigido a alumnos con matricula de externo." },
				{ "Coordinador", "Coordinador del curso" }, { "Supervisor", "Nivel de supervisor" } };
		int orden = 1;
		for (int x = 0; x < dirigidoA.length; x++) {

			CatalogoComunDTO encuestaObjetivo = new CatalogoComunDTO();
			encuestaObjetivo.setActivo(1);
			encuestaObjetivo.setFechaRegistro(new Date());
			encuestaObjetivo.setNombreUsuarioMod("system");
			encuestaObjetivo.setOrden(orden);
			encuestaObjetivo.setUsuarioModifico(1l);
			encuestaObjetivo.setNombre(dirigidoA[x][0]);
			encuestaObjetivo.setDescripcion(dirigidoA[x][1]);

			try {

				logger.info("RESULTADO {" + encuestaObjetivoService.guardar(encuestaObjetivo, CatEncuestaObjetivo.class)
						+ "}");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			orden++;
		}
		logger.info(
				"fin insercion de encuesta dirigido a ****************************************************************");
	}

	/**
	 * obtención por id del catálgo de tipo de encuesta
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void buscarPorID() {
		List<CatalogoComunDTO> lst = encuestaTipoService.findAll(CatEncuestaTipo.class);
		CatalogoComunDTO encuestaTipo = new CatalogoComunDTO();
		assertNotNull(lst);
		encuestaTipo.setId(lst.get(0).getId());
		
		CatalogoComunDTO catalogo = encuestaTipoService.buscarPorId(encuestaTipo.getId(), CatEncuestaTipo.class);
		assertNotNull(catalogo);
		
		ResultadoDTO<CatalogoComunDTO> resultado = new ResultadoDTO<>();
		try {
			resultado = encuestaTipoService.eliminar(encuestaTipo, CatEncuestaTipo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertThat(ResultadoTransaccionEnum.FALLIDO.equals(resultado.getResultado()), is(false));
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void actualizarCatalogo() {
		List<CatalogoComunDTO> lst = encuestaTipoService.findAll(CatEncuestaTipo.class);
		CatalogoComunDTO encuestaTipo = new CatalogoComunDTO();
		assertNotNull(lst);

		encuestaTipo = lst.get(0);
		encuestaTipo.setFechaActualizacion(new Date());
		ResultadoDTO<CatalogoComunDTO> resultado = new ResultadoDTO<>();

		try {
			resultado = encuestaTipoService.actualizar(encuestaTipo, CatEncuestaTipo.class);
		} catch (Exception e) {
			assertThat(ResultadoTransaccionEnum.EXITOSO.equals(resultado.getResultado()), is(false));
			e.printStackTrace();
		}

		assertThat(ResultadoTransaccionEnum.EXITOSO.equals(resultado.getResultado()), is(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void eliminarLogicamenteCatalogoError() {
		List<CatalogoComunDTO> lst = encuestaObjetivoService.findAll(CatEncuestaObjetivo.class);
		CatalogoComunDTO catalogoDTO = new CatalogoComunDTO();

		assertNotNull(lst);

		catalogoDTO = lst.get(0);
		ResultadoDTO<CatalogoComunDTO> resultado = new ResultadoDTO<>();

		try {
			resultado = encuestaObjetivoService.eliminaLogicamente(catalogoDTO, CatEncuestaObjetivo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertThat(ResultadoTransaccionEnum.FALLIDO.equals(resultado.getResultado()), is(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void eliminarCatalogo() {
		
		//CatalogoComunDTO catalogoDTO = encuestaObjetivoService.buscarPorId(9, CatEncuestaObjetivo.class);
		CatalogoComunDTO catalogoDTO = new CatalogoComunDTO();
		catalogoDTO.setId(9);
		ResultadoDTO<CatalogoComunDTO> resultado = new ResultadoDTO<>();
		try {
			resultado = encuestaObjetivoService.eliminar(catalogoDTO, CatEncuestaObjetivo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertThat(ResultadoTransaccionEnum.EXITOSO.equals(resultado.getResultado()), is(true));
	}

	/**
	 * Busqueda por id de un obejto del catalago de objetivo(dirigido a)
	 */
	// @Test
	public void buscarPorIDObjetivo() {
		CatalogoComunDTO catalogo = new CatalogoComunDTO();
		catalogo.setId(1);
		// catalogo.setId(1);
		CatalogoComunDTO resultado = encuestaObjetivoService.buscarPorId(catalogo.getId(), CatEncuestaObjetivo.class);
		logger.info("***************************************************************************************");
		logger.info("RESULTADO {" + resultado.toString() + "}");
		logger.info("***************************************************************************************");
	}

	/**
	 * Buqueda de un registro por id del catalgo de contexto.
	 */
	// @Test
	public void buscarTipoPorContexto() {

		Long idContexto = 9L;
		// Long idContexto = 1L;
		List<CatalogoComunDTO> lstCatTipo = encuestaTipoService.buscarPorContexto(idContexto, CatEncuestaTipo.class);
		logger.info("***************************************************************************************");

		for (CatalogoComunDTO tipo : lstCatTipo) {
			logger.info("{" + tipo.toString() + "}");
		}
		logger.info("***************************************************************************************");
	}

	/**
	 * Obtencion de todo el contenido del catalogo de tipo de encuesta
	 */
	@Test
	public void getAllCatalogo() {
		List<CatalogoComunDTO> lstCatTipo = encuestaTipoService.buscarTipoEncuesta(CatEncuestaTipo.class);
		for (CatalogoComunDTO tipo : lstCatTipo) {
			logger.info("{" + tipo.toString() + "}");
		}
	}

	/**
	 * Insercion de un nuevo registro de tipo de encuesta(nivel de aprendizaje)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void insertarTipoTest() {
		ResultadoDTO<CatalogoComunDTO> resultado = new ResultadoDTO<>();
		try {
			CatalogoComunDTO encuestaTipo = new CatalogoComunDTO();
			encuestaTipo.setActivo(1);
			encuestaTipo.setFechaRegistro(new Date());
			encuestaTipo.setNombre("Pruebas Comportamiento33");
			encuestaTipo.setDescripcion("Encuesta relacionada al comportamiento.");
			encuestaTipo.setNombreUsuarioMod("system");
			encuestaTipo.setOrden(2);
			encuestaTipo.setUsuarioModifico(1l);
			CatEncuestaContextoDTO contexto = new CatEncuestaContextoDTO();
			contexto.setId(1);
			logger.info("Contexto=" + contexto.toString());
			encuestaTipo.setContexto(contexto);

			resultado = encuestaTipoService.guardar(encuestaTipo, CatEncuestaTipo.class);
			// encuestaTipoService.guardar(encuestaTipo, CatEncuestaTipo.class);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		assertThat(ResultadoTransaccionEnum.EXITOSO.equals(resultado.getResultado()), is(true));
	}

	/**
	 * Obtener por medio de id un objeto del catálogo de contexto de la
	 * encuesta.
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unused")
	private CatalogoComunDTO getContexto(Integer id) {
		CatalogoComunDTO contexto = encuestaContextoService.buscarPorId(id, CatEncuestaContexto.class);
		return mapper.map(contexto, CatalogoComunDTO.class);
	}

	@Test
	public void prueba() {
		assertThat(1 == 2, is(false));
	}
}
