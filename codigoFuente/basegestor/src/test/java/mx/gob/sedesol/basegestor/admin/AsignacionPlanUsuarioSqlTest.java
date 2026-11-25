package mx.gob.sedesol.basegestor.admin;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Test;
import org.junit.Assume;
import static org.junit.Assert.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.AsignacionPlanUsuarioRepository;
import mx.gob.sedesol.basegestor.service.impl.admin.AsignacionPlanUsuarioServiceImpl;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;

/** SQL real exclusivamente sobre tablas temporales de la conexión. */
public class AsignacionPlanUsuarioSqlTest {
    @Test public void catalogosAsignacionYDuplicadoEnMariaDb()throws Exception{
        String url=System.getenv("GESTOR_TEST_JDBC_URL"),user=System.getenv("GESTOR_TEST_JDBC_USER");
        Assume.assumeNotNull(url,user);Class.forName("com.mysql.jdbc.Driver");String password=System.getenv("GESTOR_TEST_JDBC_PASSWORD");
        try(Connection connection=DriverManager.getConnection(url,user,password==null?"":password);Statement s=connection.createStatement()){
            s.execute("CREATE TEMPORARY TABLE tbl_persona(id_persona BIGINT PRIMARY KEY,sso_idUsuario VARCHAR(100),sso_nombre VARCHAR(100),sso_apellidoPaterno VARCHAR(100),sso_apellidoMaterno VARCHAR(100))");
            s.execute("CREATE TEMPORARY TABLE rel_persona_roles(id_persona BIGINT,id_rol INT)");
            s.execute("CREATE TEMPORARY TABLE tbl_planes(id_plan INT,nombre VARCHAR(100),id_estatus_plan INT)");
            s.execute("CREATE TEMPORARY TABLE tbl_convocatoria(convocatoria_id INT,nombre VARCHAR(100),activo INT)");
            s.execute("CREATE TEMPORARY TABLE rel_convocatoria_planesyprogramas(id_plan INT,id_convocatoria INT)");
            s.execute("CREATE TEMPORARY TABLE tbl_persona_aspirante(id_persona_aspirante INT PRIMARY KEY AUTO_INCREMENT,id_persona BIGINT,id_plan INT,id_convocatoria INT)");
            s.execute("INSERT INTO tbl_persona VALUES(10,'es1','Ana','Perez','G'),(20,'es2','Luis','Perez','G'),(30,'docente','Docente','Perez','G')");
            s.execute("INSERT INTO rel_persona_roles VALUES(10,2),(20,2),(30,3)");
            s.execute("INSERT INTO tbl_planes VALUES(3,'Plan A',3),(4,'Plan B',3),(5,'Plan sin relación',3)");
            s.execute("INSERT INTO tbl_convocatoria VALUES(17,'Activa',1),(18,'Otra activa',1),(19,'Inactiva',0)");
            s.execute("INSERT INTO rel_convocatoria_planesyprogramas VALUES(3,17),(4,18),(3,19)");
            connection.setAutoCommit(false);
            AsignacionPlanUsuarioRepository repo=new AsignacionPlanUsuarioRepository();
            EntityManager em=(EntityManager)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{EntityManager.class},(p,m,a)->{
                String sql=(String)a[0];Map<String,Object> params=new HashMap<>();int[] limite={0};
                return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Query.class},(q,method,args)->{
                    if(method.getName().equals("setParameter")){params.put((String)args[0],args[1]);return q;}
                    if(method.getName().equals("setMaxResults")){limite[0]=(Integer)args[0];return q;}
                    Matcher matcher=Pattern.compile(":([A-Za-z][A-Za-z0-9_]*)").matcher(sql);StringBuffer expanded=new StringBuffer();List<Object> values=new ArrayList<>();
                    while(matcher.find()){Object value=params.get(matcher.group(1));if(value instanceof Collection){Collection<?> group=(Collection<?>)value;matcher.appendReplacement(expanded,String.join(",",Collections.nCopies(group.size(),"?")));values.addAll(group);}else{matcher.appendReplacement(expanded,"?");values.add(value);}}
                    matcher.appendTail(expanded);
                    if(limite[0]>0)expanded.append(" LIMIT ").append(limite[0]);
                    try(PreparedStatement statement=connection.prepareStatement(expanded.toString())){
                        for(int n=0;n<values.size();n++)statement.setObject(n+1,values.get(n));
                        if(method.getName().equals("executeUpdate"))return statement.executeUpdate();
                        try(ResultSet rs=statement.executeQuery()){
                            int cols=rs.getMetaData().getColumnCount();List<Object> rows=new ArrayList<>();Set<String> aliases=new HashSet<>();
                            for(int n=1;n<=cols;n++)assertTrue(aliases.add(rs.getMetaData().getColumnLabel(n)));
                            while(rs.next()){if(cols==1)rows.add(rs.getObject(1));else{Object[] row=new Object[cols];for(int n=0;n<cols;n++)row[n]=rs.getObject(n+1);rows.add(row);}}
                            return method.getName().equals("getSingleResult")?rows.get(0):rows;
                        }
                    }
                });
            });
            Field field=repo.getClass().getDeclaredField("entityManager");field.setAccessible(true);field.set(repo,em);
            AsignacionPlanUsuarioServiceImpl service=new AsignacionPlanUsuarioServiceImpl();field=service.getClass().getDeclaredField("repository");field.setAccessible(true);field.set(service,repo);
            assertEquals(2,repo.planes().size());assertEquals(1,repo.convocatorias(3).size());
            assertArrayEquals(new long[]{20,2},repo.resumenSinPlan());assertEquals(2,repo.sinPlan(0,20,500).size());assertEquals(2,repo.catalogosMasivos().size());
            mx.gob.sedesol.basegestor.service.impl.admin.AsignacionPlanMasivaService masivo=new mx.gob.sedesol.basegestor.service.impl.admin.AsignacionPlanMasivaService();
            try{field=masivo.getClass().getDeclaredField("repository");field.setAccessible(true);field.set(masivo,repo);
                try(org.apache.poi.ss.usermodel.Workbook excel=org.apache.poi.ss.usermodel.WorkbookFactory.create(new java.io.ByteArrayInputStream(masivo.descargar()))){
                    assertEquals(3,excel.getNumberOfSheets());assertEquals(2,excel.getSheetAt(0).getLastRowNum());
                    assertEquals("es1",excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue());
                    assertEquals("",excel.getSheetAt(0).getRow(1).getCell(3).getStringCellValue());
                }
            }finally{masivo.cerrar();}
            assertFalse(repo.destinoValido(3,18));assertFalse(repo.destinoValido(3,19));
            assertEquals(2,service.asignar("ES1;es2",3,17,1L));
            assertEquals(2,service.buscar("es1 es2").size());
            try{service.asignar("es1;es2",3,17,1L);fail();}catch(ErrorAsignacionPlan esperado){assertTrue(esperado.getMessage().endsWith("asignacionExistente"));}
            try{service.asignar("es1",4,18,1L);fail();}catch(ErrorAsignacionPlan esperado){assertTrue(esperado.getMessage().endsWith("asignacionExistente"));}
            try{service.asignar("docente",3,17,1L);fail();}catch(ErrorAsignacionPlan esperado){assertTrue(esperado.getMessage().endsWith("noEstudiante"));}
            List<AsignacionPlanUsuarioDTO> anteriores=service.buscar("es1;es2");
            assertEquals(2,service.modificar(anteriores,4,18,1L));
            List<AsignacionPlanUsuarioDTO> actuales=service.buscar("es1;es2");
            for(int n=0;n<actuales.size();n++){
                assertEquals(anteriores.get(n).getIdAsignacion(),actuales.get(n).getIdAsignacion());
                assertEquals(Integer.valueOf(4),actuales.get(n).getIdPlan());assertEquals(Integer.valueOf(18),actuales.get(n).getIdConvocatoria());
            }
            try{service.modificar(anteriores,3,17,1L);fail();}catch(ErrorAsignacionPlan esperado){assertTrue(esperado.getMessage().endsWith("desactualizado"));}
            try(ResultSet rs=s.executeQuery("SELECT COUNT(*) FROM tbl_persona_aspirante")){assertTrue(rs.next());assertEquals(2,rs.getInt(1));}
            s.execute("INSERT INTO tbl_persona_aspirante(id_persona,id_plan,id_convocatoria) VALUES(10,3,17)");
            List<AsignacionPlanUsuarioDTO> duplicadas=service.buscar("es1");assertEquals(2,duplicadas.size());
            try{service.modificar(Collections.singletonList(duplicadas.get(0)),3,17,1L);fail();}catch(ErrorAsignacionPlan esperado){assertTrue(esperado.getMessage().endsWith("resolverDuplicados"));}
            service.eliminarAsignacion(duplicadas.get(1),1L);
            List<AsignacionPlanUsuarioDTO> restante=service.buscar("es1");assertEquals(1,restante.size());assertEquals(duplicadas.get(0).getIdAsignacion(),restante.get(0).getIdAsignacion());
            service.eliminarAsignacion(restante.get(0),1L);
            assertTrue(repo.asignaciones(Collections.singletonList(10L)).isEmpty());
            assertEquals(1,service.buscar("es2").size());
            assertArrayEquals(new int[]{1,1},service.asignarBloque(Arrays.asList(new FilaAsignacionPlanDTO(2,"es1","3","17"),new FilaAsignacionPlanDTO(3,"es2","","")),1L));
            assertEquals(Integer.valueOf(4),service.buscar("es2").get(0).getIdPlan());
            assertEquals(0,repo.resumenSinPlan()[1]);
            connection.rollback();
            try(ResultSet rs=s.executeQuery("SELECT COUNT(*) FROM tbl_persona_aspirante")){assertTrue(rs.next());assertEquals(0,rs.getInt(1));}
        }
    }
}
