package mx.gob.sedesol.basegestor.encuestas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.service.encuestas.RespuestasUsuarioService;

/**
 * Created by jhcortes on 15/12/16.
 * Clase de pruebas, para el resgistro de respuesta de encuesta por usuario.
 * Esta de forma general, ya que no filtra o valida que el usuario este matriculado
 * o registrado en algun curso.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudRespuestasEncuestaTest {
/*	
    private static final Logger logger = Logger.getLogger(CrudRespuestasEncuestaTest.class);

    @Autowired
    private RespuestasUsuarioService respuestaUsuarioService;

    /**
     * Los resultados se ven reflejados en la tabla de respuestas y relacion de respuesta_usuario,
     * ya que aun no está definida una pantalla para verificar los resultados.
     *
     * El guardado de los resultados es en dos partes.
     *  1. Se guarda un registro donde se mantiene la relación usuario - encuesta.
     *  2. Se registra el id de la pregunta - ponderación. y el id del registro anterior
     *     de tal forma que se relacionan el registro usuario-encuesta con respuestas de encuesta.
     */
    //@Test
/*    public void insertaRespuestasUsuario(){
/*        logger.info("**********************************************************");
        logger.info("insertando respuestas de encuesta");

        RespuestaUsuarioDTO respuestausuarioDTO = new RespuestaUsuarioDTO();
        respuestausuarioDTO.getBitacoraDTO().setIdUsuario(1L);/*usuario*/
/*        respuestausuarioDTO.setIdEncuesta(43);/*encuesta*/
    	/*        respuestausuarioDTO.setFechaRegistro(new Date());/*fecha de registro*/

    	/*        ResultadoDTO<RespuestaUsuarioDTO> resultado = respuestaUsuarioService.guardar(respuestausuarioDTO);

        respuestausuarioDTO = resultado.getDto();

        RespuestaUsuarioDTO respuestaDTO = respuestausuarioDTO;

        /*Actualizar*/
    	/*        List<RespuestaDTO> listaRespuestas = new ArrayList<RespuestaDTO>();
        RespuestaDTO respuesta1 = new RespuestaDTO();
        respuesta1.setIdPregunta(5);
        respuesta1.setIdRespuesta(respuestausuarioDTO.getIdRespuestaUsuario());
        respuesta1.setPonderacion(2);
        respuesta1.setRelRespuestaUsuario(respuestaDTO);
        listaRespuestas.add(respuesta1);

        RespuestaDTO respuesta2 = new RespuestaDTO();
        respuesta2.setIdPregunta(6);
        respuesta2.setIdRespuesta(respuestausuarioDTO.getIdRespuestaUsuario());
        respuesta2.setPonderacion(2);
        respuesta2.setRelRespuestaUsuario(respuestaDTO);
        listaRespuestas.add(respuesta2);

        respuestaDTO.setTblRespuestas(listaRespuestas);

       resultado = respuestaUsuarioService.actualizar(respuestaDTO);
        logger.info(resultado.toString());
        logger.info("**********************************************************");
*/
 /*   }
    
/*    @Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
	*/
}
