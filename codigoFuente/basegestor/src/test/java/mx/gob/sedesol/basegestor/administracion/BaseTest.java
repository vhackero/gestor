package mx.gob.sedesol.basegestor.administracion;

import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Clase base para configuracion de las pruebas unitarias
 * @author eolf
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public abstract class BaseTest {
	
    static {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n")));
        Logger spring = Logger.getLogger("org.springframework.*");
        spring.setLevel(Level.ERROR);
    }
    
	protected static final Logger LOG	= Logger.getLogger(BaseTest.class.getName());

	private static final String TARDO	= "TARDO [%s] SEGUNDOS";
	private long tiempoInicioTest;
    
	/**
	 * Metodo inicializador
	 * @author eolf
	 */
    @Before
    public void initializer() {
    	LOG.debug("[initializer]");
    	this.tiempoInicioTest = System.currentTimeMillis();

    }
    
    /**
     * Metodo finalizador
     * @author eolf
     */
	@After
	public void finalizer() {
		LOG.debug("[finalizer]");
		finTest(this.tiempoInicioTest);
	}
	
	 /**
	  * Metodo para obtener el tiempo fin para la prueba
	  * @author eolf
	  * @param inicio
	  */
	 private void finTest(long inicio) {
		LOG.debug("[finTest]");
		final long milisegundos = 1000L;
		long tiempoFinal = System.currentTimeMillis() - inicio;
		long tiempoFin = (tiempoFinal / milisegundos);		 
		LOG.info(String.format(TARDO, tiempoFin));
	 }
	 
	/**
	 * Metodo para recorrer una lista de objetos
	 * @author nnm_eolf
	 * @param list
	 */
	public void recorrerLista(List<?> list) {
		LOG.info("[recorrerLista]");
		Assert.notEmpty(list);
		for(Object obj : list) {
			Assert.notNull(obj);
			LOG.info(obj.toString());
		}
	}

}
