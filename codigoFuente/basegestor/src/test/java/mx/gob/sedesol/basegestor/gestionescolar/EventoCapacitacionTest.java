package mx.gob.sedesol.basegestor.gestionescolar;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhcortes on 3/02/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class EventoCapacitacionTest {

    private static final Logger logger = Logger.getLogger(EventoCapacitacionTest.class);

    @Autowired
    FECServiceFacade fecServiceFacade;

    @Autowired
    private FichaDescProgramaService fichaDescProgramaService;
    
    
    private EventoCapacitacionServiceImpl eventoCapacitacionService = new EventoCapacitacionServiceImpl();

    private FichaDescProgramaDTO fichaDescProg;

   // @Test
    public void findProgramaCapacitacion() {
        List<FichaDescProgramaDTO> listaResultado = new ArrayList<>();

        logger.info("********************************************************************");
        setProgramaCapacitacion();

    try{

        listaResultado = fichaDescProgramaService.buscaProgramasPorCriterios(fichaDescProg);
      // fichaDescProgramaService.buscarPorId(63);

    }catch (Exception e ){
        e.printStackTrace();
    }
        logger.info("********************************************************************");
    }

    /**
     * Generación de parametros de busqueda por criterios para los programas de capacitación
     */
    private void        setProgramaCapacitacion(){
        fichaDescProg = new FichaDescProgramaDTO();

        fichaDescProg.setTipoCompetencia(4);
        fichaDescProg.setEjeCapacitacion(2);


    }
    
    //@Test
    public void buscarUnidadesDidacticasPorPrograma() {
    	TblEvento evento = new TblEvento();
    	evento.setIdPrograma(81);
    	//eventoCapacitacionService.crearEstructura(evento, new TblAmbienteVirtualAprendizaje());
    }
    
    @Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
}
