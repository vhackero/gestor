package mx.gob.sedesol.basegestor.admin;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import javax.persistence.*;
import org.junit.Test;
import org.junit.Assume;
import static org.junit.Assert.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.RegistroUsuariosMoodleRepository;

public class RegistroUsuariosMoodleSqlTest {
    @Test public void filtraPlataformaPlanYActivosSinDuplicarUsuarios()throws Exception{
        String url=System.getenv("GESTOR_TEST_JDBC_URL"),user=System.getenv("GESTOR_TEST_JDBC_USER");Assume.assumeNotNull(url,user);Class.forName("com.mysql.jdbc.Driver");
        String pass=System.getenv("GESTOR_TEST_JDBC_PASSWORD");
        try(Connection c=DriverManager.getConnection(url,user,pass==null?"":pass);Statement s=c.createStatement()){
            s.execute("CREATE TEMPORARY TABLE tbl_persona(id_persona BIGINT PRIMARY KEY,sso_idUsuario VARCHAR(100),sso_nombre VARCHAR(100),sso_apellidoPaterno VARCHAR(100),sso_apellidoMaterno VARCHAR(100),activo INT)");
            s.execute("CREATE TEMPORARY TABLE tbl_persona_aspirante(id_persona BIGINT,id_plan INT)");
            s.execute("CREATE TEMPORARY TABLE rel_personas_plataformas_moodle(id_persona BIGINT,id_plataforma_moodle INT,id_persona_moodle INT NOT NULL,usuario_modifico BIGINT,PRIMARY KEY(id_persona,id_plataforma_moodle))");
            s.execute("INSERT INTO tbl_persona VALUES(10,'es1','Ana','Perez','G',1),(20,'docente','Luis','Perez','G',1),(30,'es3','Maria','Perez','G',0),(40,'es4','Pedro','Perez','G',1),(50,'es5','Rosa','Perez','G',1)");
            s.execute("INSERT INTO tbl_persona_aspirante VALUES(10,3),(30,3),(40,4),(50,3),(50,3)");
            s.execute("INSERT INTO rel_personas_plataformas_moodle VALUES(10,1,201,1),(20,2,202,1)");
            c.setAutoCommit(false);
            EntityManager em=(EntityManager)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{EntityManager.class},(p,m,a)->{
                String sql=(String)a[0];Map<String,Object> params=new HashMap<>();int[] limite={0};
                return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Query.class},(q,method,args)->{
                    if(method.getName().equals("setParameter")){params.put((String)args[0],args[1]);return q;}
                    if(method.getName().equals("setMaxResults")){limite[0]=(Integer)args[0];return q;}
                    Matcher matcher=Pattern.compile(":([A-Za-z][A-Za-z0-9_]*)").matcher(sql);StringBuffer expanded=new StringBuffer();List<Object> valores=new ArrayList<>();
                    while(matcher.find()){matcher.appendReplacement(expanded,"?");valores.add(params.get(matcher.group(1)));}matcher.appendTail(expanded);if(limite[0]>0)expanded.append(" LIMIT ").append(limite[0]);
                    try(PreparedStatement st=c.prepareStatement(expanded.toString())){
                        for(int n=0;n<valores.size();n++)st.setObject(n+1,valores.get(n));
                        if(method.getName().equals("executeUpdate"))return st.executeUpdate();
                        try(ResultSet rs=st.executeQuery()){int columnas=rs.getMetaData().getColumnCount();List<Object> filas=new ArrayList<>();
                            while(rs.next()){if(columnas==1)filas.add(rs.getObject(1));else{Object[] fila=new Object[columnas];for(int n=0;n<columnas;n++)fila[n]=rs.getObject(n+1);filas.add(fila);}}
                            return method.getName().equals("getSingleResult")?filas.get(0):filas;
                        }
                    }
                });
            });
            RegistroUsuariosMoodleRepository repo=new RegistroUsuariosMoodleRepository();Field f=repo.getClass().getDeclaredField("entityManager");f.setAccessible(true);f.set(repo,em);
            assertArrayEquals(new long[]{50,3},repo.resumen(1,null));assertArrayEquals(new long[]{50,1},repo.resumen(1,3));
            assertArrayEquals(new long[]{50,3},repo.resumen(2,null));
            assertEquals(Arrays.asList(20L,40L,50L),repo.pendientes(1,null,0,50));assertEquals(Arrays.asList(50L),repo.pendientes(1,3,0,50));
            assertNull(repo.personaPendiente(30L,1,null));assertNull(repo.personaPendiente(10L,1,null));assertNull(repo.personaPendiente(20L,1,3));
            assertEquals("docente",repo.personaPendiente(20L,1,null).getUsuario());
            assertArrayEquals(new long[]{50,1},repo.resumen(1,3,"es5"));
            assertEquals(Arrays.asList(50L),repo.pendientes(1,3,"es5",0,50));
            assertTrue(repo.pendientes(1,3,"es4",0,50).isEmpty());
            assertNull(repo.personaPendiente(50L,1,3,"es4"));
            Object[] registrado=repo.estadoUsuario("es1",1,3).get(0);
            assertEquals(Arrays.asList(10L),repo.pendientes(1,3,"es1",0,50));
            assertNotNull(repo.personaPendiente(10L,1,3,"es1"));
            repo.guardarReconciliado(10L,1,601,7L);
            try(ResultSet rs=s.executeQuery("SELECT id_persona_moodle FROM rel_personas_plataformas_moodle WHERE id_persona=10 AND id_plataforma_moodle=1")){assertTrue(rs.next());assertEquals(601,rs.getInt(1));assertFalse(rs.next());}
            assertEquals(1,((Number)registrado[2]).intValue());
            assertEquals(0,((Number)repo.estadoUsuario("docente",1,3).get(0)[1]).intValue());
            repo.guardar(50L,1,501,7L);assertEquals(0,repo.resumen(1,3)[1]);
            try(ResultSet rs=s.executeQuery("SELECT id_persona_moodle,usuario_modifico FROM rel_personas_plataformas_moodle WHERE id_persona=50 AND id_plataforma_moodle=1")){assertTrue(rs.next());assertEquals(501,rs.getInt(1));assertEquals(7,rs.getLong(2));}
            try{repo.guardar(40L,1,0,7L);fail();}catch(IllegalArgumentException esperado){}
            c.rollback();
        }
    }
}
