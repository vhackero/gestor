/**
 * 
 */
package mx.gob.sedesol.basegestor.encuestas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
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
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaEstatus;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaObjetivo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.service.encuestas.CatalogoComunEncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.PreguntaEncuestaService;

/**
 * @author jhcortes
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudEncuestaTest {

	private static final Logger logger = Logger.getLogger(CrudEncuestaTest.class);

	@Autowired
	private EncuestaService encuestaService;
	@Autowired
	private PreguntaEncuestaService preguntaEncuestaService;
	@Autowired
	private CatalogoComunEncuestaService<CatEncuestaEstatus, Integer> encuestaEstatusService;
	@Autowired
	private CatalogoComunEncuestaService<CatEncuestaObjetivo, Integer> encuestaObjetivoService;
	@Autowired
	private CatalogoComunEncuestaService<CatEncuestaTipo, Integer> encuestaTipoService;

	private ModelMapper catComunMapper = new ModelMapper();

	private EncuestaDTO encuestaDTO;
	private CatalogoComunDTO tipoEncuesta;
	private CatalogoComunDTO objetivoEncuesta;
	private CatalogoComunDTO estatusEncuesta;

	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Inicializa los objetos, con id's hardcore, los id pueden variar segun la
	 * base de datos y la existencia de los registros, de catalogos y de la
	 * encuesta misma a usar.
	 */
	public void inicializarObj() {
		getEstatusEncuesta(1);
		getTipoEncuesta(1);
		getObjetivoEncuesta(1);
		setEncuesta();
	}

	
	@Test
	public void consultaEncuestas(){
		assertNotNull(encuestaService.findAll());
	}
	
	
	@Test
	public void consultaEncuestasCriterios(){
		EncuestaDTO dto = new EncuestaDTO();
		dto.setId(100);
		assertNotNull(encuestaService.buscarPorCriterios(dto));
	}
	
	/**
	 * Inserta registro de encuesta
	 */
	@Test
	public void insertarEncuestaTest() {
		inicializarObj();
		try {
			assertNotNull(encuestaService.guardar(encuestaDTO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Esta prueba es para verificar que se esta insertando correctamente un
	 * registro de nueva encuesta, a cada encuesta hay que asignarle sus
	 * preguntas, la cantidad de preguntas puede ser de "N". Recordar que por
	 * regla de negocio debe tener al menos 5 preguntas.
	 */
	@Test
	public void buscarEncuestaActualizar() {
		
		EncuestaDTO enc = encuestaService.buscarEncuestaNomClv("Demo JPCP", "01030101");
		ResultadoDTO<EncuestaDTO> res = new ResultadoDTO<>();
		if (enc != null) {
			enc.setFechaActualizacion(new Date());
			enc.getEncuestaEstatus().setId(1);//generando encuesta borrador
			enc.setIdUsrMensajeDestino(enc.getUsuarioModifico());
			res = encuestaService.actualizar(enc);
		}
		
		 assertThat(ResultadoTransaccionEnum.EXITOSO.equals(res.getResultado()), is(true));
	}

	
	@Test
	public void actualizarEncuesta(){
		
	}
	/**
	 * Genera una pregunta dummie y se le agrega la encuesta a la que estará
	 * ligada
	 * 
	 * @param encuesta
	 * @return
	 */
	private PreguntaEncuestaDTO inicializaPreguntaEncuesta(EncuestaDTO encuesta) {
		PreguntaEncuestaDTO preguntaEncuesta = new PreguntaEncuestaDTO();
		preguntaEncuesta.setEncuesta(encuesta);
		preguntaEncuesta.setDescripcion("Descripcion de la pregunta");
		preguntaEncuesta.setUsuarioModifico(1L);
		preguntaEncuesta.setNombre("¿Como evaluas el contenido del sitio?");
		preguntaEncuesta.setFechaRegistro(new Date());

		return preguntaEncuesta;
	}

	/**
	 * Genera una pregunta dummie y se le agrega la encuesta a la que estará
	 * ligada
	 * 
	 * @param encuesta
	 * @return
	 */
	private PreguntaEncuestaDTO inicializaPreguntaEncuesta2(EncuestaDTO encuesta) {
		PreguntaEncuestaDTO preguntaEncuesta = new PreguntaEncuestaDTO();
		preguntaEncuesta.setEncuesta(encuesta);
		preguntaEncuesta.setDescripcion("Descripcion de la pregunta test 3");
		preguntaEncuesta.setUsuarioModifico(1L);
		preguntaEncuesta.setNombre("¿Recomendarias el sitio a algún conocido?");
		preguntaEncuesta.setFechaRegistro(new Date());

		return preguntaEncuesta;
	}

	/**
	 * Inicializa el objeto encuesta, lo llena de datos dummie
	 * 
	 * NOTA: recordar que los ID's usados pueden variar dependiendo de la base
	 * de datos.
	 */
	private void setEncuesta() {

		encuestaDTO = new EncuestaDTO();
		encuestaDTO.setEncuestaTipo(tipoEncuesta);
//		encuestaDTO.setEncuestaTipo(null);
//		encuestaDTO.getEncuestaTipo().setId(1);
//		encuestaDTO.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());
//		encuestaDTO.getEncuestaTipo().getContexto().setId(4);;
		encuestaDTO.setEncuestaObjetivo(objetivoEncuesta);
		encuestaDTO.setEncuestaEstatus(estatusEncuesta);

		logger.info("seteando datos");

	//	encuestaDTO.setClave("ENC011120161038");
	//	encuestaDTO.setNombre("Encuesta 1038");
	//	encuestaDTO.setObservaciones("Descripcion de encuesta");
//		encuestaDTO.setInstrucciones("instrucciones de encuesta");
		encuestaDTO.setActivo(1);
	//	encuestaDTO.setOrden(6);
		encuestaDTO.setFechaCreacion(new Date());
		encuestaDTO.setUsuarioModifico(1L);
	}

	/**
	 * Busca una encuesta por medio del ID, hay que tener presente que tambien
	 * puede cambiar por la base de datos y los registros que existan o no, ya
	 * que la busqueda se realiza con datos dummie.
	 * 
	 */
	// @Test
	public void recuperaEncuesta() {
		EncuestaDTO encuestaSelDetalle = new EncuestaDTO();
		encuestaSelDetalle = encuestaService.buscarPorId(1);

		if (ObjectUtils.isNotNull(encuestaSelDetalle)) {
			encuestaSelDetalle.getPreguntasEncuesta();
		}
	}

	/**
	 * Busqueda por criterios, sirve para buscar encuestas bajo ciertos
	 * parametros es decir, se puede buscar registros con diferentes parametros
	 * o sin parametros y recibir una respuesta, con "N" cantidad de registros.
	 */
	// @Test
	public void buscarPorCriterios(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString1 = "2016-11-22";
			String dateInString2 = "2016-11-23";
			sdf.parse(dateInString1);
			sdf.parse(dateInString2);

			EncuestaDTO dto = new EncuestaDTO();

			List<EncuestaDTO> lista = encuestaService.buscarPorCriterios(dto);
			if (lista != null) {
				for (EncuestaDTO encuesta : lista) {
					logger.info("RESULTADO {" + encuesta.toString() + "}");
				}
			} else {
				logger.info("No se encontraron coincidencias");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * Busca un registro de encuestas por medio del ID
	 */
	// @Test
	public void buscarEncuesta() {
		Integer idEncuesta = 47;
		try {
			EncuestaDTO encuesta = encuestaService.buscarPorId(idEncuesta);
			logger.info(encuesta.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Busca una encuesta por ID
	 */
	// @Test
	public void buscarPorID() {
		 logger.info("***************************************");
		try {
			EncuestaDTO encuesta = catComunMapper.map(encuestaService.buscarPorId(41), EncuestaDTO.class);
			logger.info("RESULTADO {" + encuesta.toString() + "}");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Test
	public void eliminarEncuesta(){

		encuestaDTO = new EncuestaDTO();

		EncuestaDTO encuestAux = encuestaService.buscarPorId(41);
		encuestAux.setFechaActualizacion(new Date());

        List<PreguntaEncuestaDTO> lst = encuestAux.getPreguntasEncuesta();

        for (PreguntaEncuestaDTO pregunta: lst){
            pregunta.setEncuesta(null);
            preguntaEncuestaService.eliminar(pregunta);
            preguntaEncuestaService.eliminar(pregunta);
        }

        ResultadoDTO<EncuestaDTO> resultado = encuestaService.eliminar(encuestAux);
        
        assertThat(ResultadoTransaccionEnum.EXITOSO.equals(resultado.getResultado()), is(true));
	}

	/**
	 * Busqueda por criterios, se puede parametrizar el objeto encuestaDTO, y
	 * estos parametros seran los criterios de busqueda, puede estar vacio el
	 * objeto , en este caso trae todos los registros encontrados.
	 * 
	 */
	//@Test
	public void buscarTodo() {
		try {
			// List<EncuestaDTO> lista = encuestaService.findAll();
			encuestaDTO = new EncuestaDTO();
			// encuestaDTO.setNombre("10");
			// encuestaDTO.getEncuestaEstatus().setId(6);

			logger.info("*************************************************************************************");
			List<EncuestaDTO> lista = encuestaService.buscarPorCriterios(encuestaDTO);
			for (EncuestaDTO encuesta : lista) {
				logger.info("RESULTADO {" + encuesta.toString() + "}");
			}
			logger.info("*************************************************************************************");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Busqueda por id en el catálogo de objetivo de encuesta
	 * 
	 * @param id
	 */
	private void getObjetivoEncuesta(Integer id) {
		logger.info("obteniendo objetivo ==" + id);
		this.objetivoEncuesta = new CatalogoComunDTO();
		this.objetivoEncuesta = encuestaObjetivoService.buscarPorId(id, CatEncuestaObjetivo.class);
	}

	/**
	 * Busqueda por id en el catálogo de estado de encuesta
	 * 
	 * @param id
	 */
	private void getEstatusEncuesta(Integer id) {
		logger.info("obteniendo estatus ==" + id);
		this.estatusEncuesta = new CatalogoComunDTO();
		this.estatusEncuesta = encuestaEstatusService.buscarPorId(id, CatEncuestaEstatus.class);
	}

	/**
	 * Busqueda por id en el catálogo de tipo de encuesta
	 * 
	 * @param id
	 */
	private void getTipoEncuesta(Integer id) {
		logger.info("obteniendo tipo == " + id);
		this.tipoEncuesta = encuestaTipoService.buscarPorId(id, CatEncuestaTipo.class);
	}
	
	
	@Test
	public void asignacionDClaveTest() {
		//41,48,49
		  ResultadoDTO<EncuestaDTO> resultado = new ResultadoDTO<>();
		try {
			EncuestaDTO encuesta = encuestaService.buscarPorId(81);
			encuesta.getEncuestaEstatus().setId(4);
			resultado = encuestaService.actualizar(encuesta);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		assertThat(ResultadoTransaccionEnum.EXITOSO.equals(resultado.getResultado()), is(true));
	}

}
