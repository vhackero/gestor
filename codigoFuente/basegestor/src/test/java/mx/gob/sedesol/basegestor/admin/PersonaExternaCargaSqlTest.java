package mx.gob.sedesol.basegestor.admin;

import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Assume;
import org.junit.Test;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaExternaCargaDTO;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaExternaCargaRepository;

public class PersonaExternaCargaSqlTest {
    @Test public void restriccionNormalizadaRepositorioYRollbackSinTocarTablaReal() throws Exception {
        String url=System.getenv("GESTOR_TEST_JDBC_URL"),user=System.getenv("GESTOR_TEST_JDBC_USER");
        Assume.assumeNotNull(url,user);Class.forName("com.mysql.jdbc.Driver");
        String pass=System.getenv("GESTOR_TEST_JDBC_PASSWORD");
        try(Connection c=DriverManager.getConnection(url,user,pass==null?"":pass);Statement s=c.createStatement()) {
            s.execute("CREATE TEMPORARY TABLE externos_carga_test LIKE tbl_persona_externos");
            PersonaExternaCargaRepository repo=new PersonaExternaCargaRepository();
            EntityManager em=(EntityManager)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{EntityManager.class},(proxy,method,args)->{
                String sql=((String)args[0]).replace("tbl_persona_externos","externos_carga_test");
                Map<String,Object> parametros=new HashMap<>();
                return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Query.class},(q,m,a)->{
                    if(m.getName().equals("setParameter")) {parametros.put((String)a[0],a[1]);return q;}
                    Matcher matcher=Pattern.compile(":([a-z_0-9]+)").matcher(sql);StringBuffer consulta=new StringBuffer();List<Object> valores=new ArrayList<>();
                    while(matcher.find()) {
                        Object valor=parametros.get(matcher.group(1));
                        if(valor instanceof Collection) {List<String> marcas=new ArrayList<>();for(Object v:(Collection<?>)valor){marcas.add("?");valores.add(v);}matcher.appendReplacement(consulta,String.join(",",marcas));}
                        else {matcher.appendReplacement(consulta,"?");valores.add(valor);}
                    }
                    matcher.appendTail(consulta);
                    try(PreparedStatement p=c.prepareStatement(consulta.toString())) {
                        for(int n=0;n<valores.size();n++) p.setObject(n+1,valores.get(n));
                        if(m.getName().equals("executeUpdate")) return p.executeUpdate();
                        try(ResultSet r=p.executeQuery()) {List<Object> filas=new ArrayList<>();while(r.next()) filas.add(r.getObject(1));return filas;}
                    }
                });
            });
            Field f=PersonaExternaCargaRepository.class.getDeclaredField("entityManager");f.setAccessible(true);f.set(repo,em);
            List<String> datos=Arrays.asList(" ES001 ","Ejemplo2026!","Ana","Pérez","García","3","DCSA","ana@example.com","1990-01-01","PEGA900101MDFRRL09","LIC","1","1","3");
            c.setAutoCommit(false);
            repo.insertar(new PersonaExternaCargaDTO(2,datos));
            assertTrue(repo.usuariosExistentes(Arrays.asList("es001")).contains("es001"));
            List<String> duplicada=new ArrayList<>(datos);duplicada.set(0,"es001");
            try {repo.insertar(new PersonaExternaCargaDTO(3,duplicada));fail("El indice unico debe impedir el duplicado.");}
            catch(Exception e) {Throwable causa=e;while(causa.getCause()!=null) causa=causa.getCause();assertTrue(causa instanceof SQLException);assertEquals(1062,((SQLException)causa).getErrorCode());}
            c.rollback();
            try(ResultSet r=s.executeQuery("SELECT COUNT(*) FROM externos_carga_test")) {assertTrue(r.next());assertEquals(0,r.getInt(1));}
            repo.insertar(new PersonaExternaCargaDTO(2,datos));c.commit();
            // La columna adicional no altera la posicion usada por el importador actual.
            try(ResultSet r=s.executeQuery("SELECT * FROM externos_carga_test")) {
                assertTrue(r.next());assertEquals("ES001",r.getString(2));assertEquals(3,r.getInt(7));assertEquals(3,r.getInt(15));
                assertEquals("id_convocatoria",r.getMetaData().getColumnName(15));
                assertEquals("nombre_usuario_normalizado",r.getMetaData().getColumnName(16));
            }
            s.executeUpdate("DELETE FROM externos_carga_test");c.commit();
            List<PersonaExternaCargaDTO> lote=new ArrayList<>();List<String> usuarios=new ArrayList<>();
            for(int n=0;n<15000;n++) {
                List<String> valores=new ArrayList<>(datos);valores.set(0,"ES_LOTE_"+n);
                lote.add(new PersonaExternaCargaDTO(n+2,valores));usuarios.add("es_lote_"+n);
            }
            long inicio=System.nanoTime();repo.insertarLote(lote);
            assertEquals(15000,repo.usuariosExistentes(usuarios).size());
            try(ResultSet r=s.executeQuery("SELECT COUNT(*) FROM externos_carga_test")) {assertTrue(r.next());assertEquals(15000,r.getInt(1));}
            c.rollback();
            try(ResultSet r=s.executeQuery("SELECT COUNT(*) FROM externos_carga_test")) {assertTrue(r.next());assertEquals(0,r.getInt(1));}
            System.out.println("Lote temporal de 15000 registros y consulta de duplicados: "+((System.nanoTime()-inicio)/1000000)+" ms");
        }
    }
}
