package mx.gob.sedesol.basegestor.admin;

import java.lang.reflect.Proxy;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SQLQuery;
import org.hibernate.cfg.Configuration;
import org.junit.Assume;
import org.junit.Test;
import static org.junit.Assert.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.UsuariosImportarRepo;

/** Verifica la autodetección de escalares con Hibernate y MariaDB reales. */
public class ImportacionFuenteHibernateTest {
    @Test public void relacionPlanConvocatoriaUsaTiposNativosReales() {
        String url=System.getenv("GESTOR_TEST_JDBC_URL"),usuario=System.getenv("GESTOR_TEST_JDBC_USER");
        Assume.assumeNotNull(url,usuario);
        Configuration config=new Configuration();
        config.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
        config.setProperty("hibernate.connection.url",url);
        config.setProperty("hibernate.connection.username",usuario);
        String clave=System.getenv("GESTOR_TEST_JDBC_PASSWORD");
        config.setProperty("hibernate.connection.password",clave==null?"":clave);
        config.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        config.setProperty("hibernate.connection.pool_size","1");
        SessionFactory factory=config.buildSessionFactory();
        Session session=factory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TEMPORARY TABLE convocatoria_hibernate_test(convocatoria_id INT)").executeUpdate();
            session.createSQLQuery("CREATE TEMPORARY TABLE relacion_hibernate_test(id_convocatoria INT,id_plan INT)").executeUpdate();
            session.createSQLQuery("INSERT INTO convocatoria_hibernate_test VALUES(17)").executeUpdate();
            session.createSQLQuery("INSERT INTO relacion_hibernate_test VALUES(17,3)").executeUpdate();
            UsuariosImportarRepo repo=new UsuariosImportarRepo();
            repo.entityManager=(EntityManager)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{EntityManager.class},(p,m,a)->{
                String sql=((String)a[0]).replace("tbl_convocatoria","convocatoria_hibernate_test")
                    .replace("rel_convocatoria_planesyprogramas","relacion_hibernate_test");
                SQLQuery nativeQuery=session.createSQLQuery(sql);
                return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Query.class},(q,metodo,args)->{
                    if(metodo.getName().equals("setParameter")){nativeQuery.setParameter((String)args[0],args[1]);return q;}
                    if(metodo.getName().equals("getResultList"))return nativeQuery.list();
                    throw new UnsupportedOperationException(metodo.getName());
                });
            });
            assertTrue(repo.verificarRelacionConvocatoria("3","17"));
            assertFalse(repo.verificarRelacionConvocatoria("4","17"));
            assertFalse(repo.verificarRelacionConvocatoria("3","18"));
        } finally {
            if(session.getTransaction().getStatus()==org.hibernate.resource.transaction.spi.TransactionStatus.ACTIVE) session.getTransaction().rollback();
            session.close();factory.close();
        }
    }
}
