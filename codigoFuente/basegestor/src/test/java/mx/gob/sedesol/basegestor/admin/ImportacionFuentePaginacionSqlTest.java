package mx.gob.sedesol.basegestor.admin;

import static org.junit.Assert.*;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Assume;
import org.junit.Test;
import mx.gob.sedesol.basegestor.model.repositories.admin.UsuariosImportarRepo;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;

public class ImportacionFuentePaginacionSqlTest {
    @Test public void paginacionExcluyeRegistradosRespetaCorteYVinculaConvocatoria() throws Exception {
        String url=System.getenv("GESTOR_TEST_JDBC_URL"),user=System.getenv("GESTOR_TEST_JDBC_USER");
        Assume.assumeNotNull(url,user);Class.forName("com.mysql.jdbc.Driver");
        String pass=System.getenv("GESTOR_TEST_JDBC_PASSWORD");
        try(Connection c=DriverManager.getConnection(url,user,pass==null?"":pass);Statement s=c.createStatement()) {
            s.execute("CREATE TEMPORARY TABLE externos_fuente_test LIKE des_sisi_gestor.tbl_persona_externos");
            s.execute("CREATE TEMPORARY TABLE tbl_persona(id_persona BIGINT,sso_idUsuario VARCHAR(100))");
            s.execute("CREATE TEMPORARY TABLE aspirantes_fuente_test LIKE des_sisi_gestor.tbl_persona_aspirante");
            s.execute("CREATE TEMPORARY TABLE convocatoria_fuente_test(convocatoria_id INT)");
            s.execute("CREATE TEMPORARY TABLE relacion_fuente_test(id_convocatoria INT,id_plan INT)");
            s.execute("INSERT INTO convocatoria_fuente_test VALUES(3)");
            s.execute("INSERT INTO relacion_fuente_test VALUES(3,3)");
            s.execute("INSERT INTO tbl_persona(id_persona,sso_idUsuario) VALUES (999,'es_a')");
            for(int n=1;n<=4;n++) {
                s.execute("INSERT INTO externos_fuente_test(id_persona_externo,nombre_usuario,password,nombre,apellidop,apellidom,id_plan,division,correo_institucional,fecha_nacimiento,curp,nivel,id_fuente_externa,tipo_usuario,id_convocatoria) VALUES ("
                    +(n*10)+",'ES_"+(char)('A'+n-1)+"','Ejemplo2026!','Ana','Perez','Garcia',3,'DCSA','ejemplo"+n+"@example.com','1990-01-01','PEGA900101MDFRRL09','LIC',"+(n==3?2:1)+",1,"+(n==4?4:3)+")");
            }
            UsuariosImportarRepo repo=new UsuariosImportarRepo();
            repo.entityManager=(EntityManager)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{EntityManager.class},(p,m,a)->{
                String sql=((String)a[0]).replace("tbl_persona_externos","externos_fuente_test").replace("tbl_persona_aspirante","aspirantes_fuente_test")
                    .replace("tbl_convocatoria","convocatoria_fuente_test").replace("rel_convocatoria_planesyprogramas","relacion_fuente_test");Map<String,Object> params=new HashMap<>();int[] limite={0};
                return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Query.class},(q,method,args)->{
                    if(method.getName().equals("setParameter")) {params.put((String)args[0],args[1]);return q;}
                    if(method.getName().equals("setMaxResults")) {limite[0]=(Integer)args[0];return q;}
                    String consulta=sql;for(Map.Entry<String,Object> e:params.entrySet()) consulta=consulta.replace(":"+e.getKey(),e.getValue().toString());
                    if(limite[0]>0) consulta+=" LIMIT "+limite[0];
                    try(Statement st=c.createStatement()) {
                        if(method.getName().equals("executeUpdate")) return st.executeUpdate(consulta);
                        try(ResultSet r=st.executeQuery(consulta)) {
                            List<Object[]> filas=new ArrayList<>();int cols=r.getMetaData().getColumnCount();
                            Set<String> alias=new HashSet<>();for(int n=1;n<=cols;n++) assertTrue(alias.add(r.getMetaData().getColumnLabel(n).toLowerCase(Locale.ROOT)));
                            while(r.next()) {Object[] fila=new Object[cols];for(int n=0;n<cols;n++) fila[n]=r.getObject(n+1);filas.add(fila);}
                            if(cols==1) {List<Object> escalares=new ArrayList<>();for(Object[] fila:filas) escalares.add(fila[0]);return escalares;}
                            return method.getName().equals("getSingleResult")?filas.get(0):filas;
                        }
                    }
                });
            });
            assertArrayEquals(new long[]{20,1},repo.resumenFuenteImportar(1,3));
            assertTrue(repo.verificarRelacionConvocatoria("3","3"));
            assertFalse(repo.verificarRelacionConvocatoria("4","3"));
            List<PersonaSigeDTO> pagina=repo.paginaFuenteImportar(1,3,0,20,250);
            assertEquals(1,pagina.size());assertEquals("ES_B",pagina.get(0).getMatricula());
            s.execute("INSERT INTO tbl_persona VALUES (1000,'es_b')");
            assertTrue(repo.paginaFuenteImportar(1,3,0,20,250).isEmpty());
            s.execute("INSERT INTO externos_fuente_test(nombre_usuario,password,nombre,apellidop,apellidom,id_plan,division,correo_institucional,fecha_nacimiento,curp,nivel,id_fuente_externa,tipo_usuario,id_convocatoria) VALUES ('ES_NUEVO','Ejemplo2026!','Ana','Perez','Garcia',3,'DCSA','nuevo@example.com','1990-01-01','PEGA900101MDFRRL09','LIC',1,1,3)");
            assertTrue(repo.paginaFuenteImportar(1,3,20,20,250).isEmpty());
            assertEquals(1,repo.paginaFuenteImportar(1,3,20,1000,1).size());
            assertEquals(1,repo.consultaPersonasImportar("1","3").size());
            CapturaPersonaDTO captura=new CapturaPersonaDTO();captura.getPersona().setIdPersona(1000L);captura.getPersona().setFuenteExterna("3");
            repo.insertarAspirantesFuenteLote(Arrays.asList(captura),3);
            try(ResultSet r=s.executeQuery("SELECT id_persona,id_plan,id_convocatoria FROM aspirantes_fuente_test")) {
                assertTrue(r.next());assertEquals(1000,r.getInt(1));assertEquals(3,r.getInt(2));assertEquals(3,r.getInt(3));assertFalse(r.next());
            }
        }
    }
}
