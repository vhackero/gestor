package mx.gob.sedesol.basegestor.admin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.utils.PersonaExternaCapturaFactory;
import mx.gob.sedesol.basegestor.model.entities.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.*;
import mx.gob.sedesol.basegestor.service.admin.*;
import mx.gob.sedesol.basegestor.service.impl.admin.*;

public class ImportacionUsuariosExternosTest {
    private void inyectar(Object objeto,String nombre,Object valor) throws Exception {
        Class<?> tipo=objeto.getClass();
        while(tipo!=null) {try {Field f=tipo.getDeclaredField(nombre);f.setAccessible(true);f.set(objeto,valor);return;}
            catch(NoSuchFieldException e){tipo=tipo.getSuperclass();}}
        throw new NoSuchFieldException(nombre);
    }
    private PersonaSigeDTO persona(long id) {
        PersonaSigeDTO p=new PersonaSigeDTO();p.setIdPersonaSige(id);p.setMatricula("es"+id);p.setPassword("Ejemplo2026!");
        p.setNombre("Ana");p.setApellidoPaterno("Pérez");p.setApellidoMaterno("García");
        p.setCurp("PEGA900101MDFRRL09");p.setFechaNacimiento(java.sql.Date.valueOf("1990-01-01"));
        p.setCorreoInstitucional("es"+id+"@example.com");p.setProgramaEducativo("3");return p;
    }
    @Test public void bcryptSeCalculaUnaVezYLaContraseniaGuardadaAutentica() {
        AtomicInteger llamadas=new AtomicInteger();BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder(4);
        PasswordEncoder encoder=new PasswordEncoder() {
            public String encode(CharSequence valor){llamadas.incrementAndGet();return bcrypt.encode(valor);}
            public boolean matches(CharSequence valor,String hash){return bcrypt.matches(valor,hash);}
        };
        RolDTO alumno=new RolDTO();alumno.setIdRol(2);
        CapturaPersonaDTO c=new PersonaExternaCapturaFactory(encoder).crear(persona(1),7L,"foto",new EntidadFederativaDTO(),new MunicipioDTO(),new AsentamientoDTO(),alumno);
        assertEquals(1,llamadas.get());assertEquals("es1",c.getPersona().getUsuario());
        assertTrue(bcrypt.matches("Ejemplo2026!",c.getPersona().getContrasenia()));
        assertEquals(c.getPersona().getContrasenia(),c.getPersona().getContraseniaEncriptada());
        assertEquals(c.getPersona().getNuevaContrasenia(),c.getPersona().getConfirmacionContrasenia());
        assertEquals(Long.valueOf(7),c.getPersona().getUsuarioModifico());
    }
    @Test public void bloqueGuardaRolesYElementosDeCadaPersonaSinProductoCruzado() throws Exception {
        PersonaServiceImpl servicio=new PersonaServiceImpl();PersonaRepo personas=mock(PersonaRepo.class);
        AtomicInteger id=new AtomicInteger();when(personas.save(any(TblPersona.class))).thenAnswer(inv->{TblPersona p=(TblPersona)inv.getArguments()[0];p.setIdPersona((long)id.incrementAndGet());return p;});
        PersonaRolesRepo roles=mock(PersonaRolesRepo.class);SsoElementoRepo elementos=mock(SsoElementoRepo.class);
        IUsuariosImportarRepo repo=mock(IUsuariosImportarRepo.class);EntityManager em=mock(EntityManager.class);
        inyectar(servicio,"personaRepo",personas);inyectar(servicio,"personaRolesRepo",roles);inyectar(servicio,"ssoElementoRepo",elementos);
        inyectar(servicio,"usuarioDatosLaboralesRepo",mock(UsuarioDatosLaboralesRepo.class));
        inyectar(servicio,"personaCorreoRepo",mock(PersonaCorreoRepo.class));inyectar(servicio,"domicilioPersonaRepo",mock(DomicilioPersonaRepo.class));
        inyectar(servicio,"usuariosImportarRepo",repo);inyectar(servicio,"entityManagerFuente",em);inyectar(servicio,"mapper",new ModelMapper());
        RolDTO alumno=new RolDTO();alumno.setIdRol(2);
        PersonaExternaCapturaFactory factory=new PersonaExternaCapturaFactory(new BCryptPasswordEncoder(4));
        CapturaPersonaDTO a=factory.crear(persona(1),7L,"foto",new EntidadFederativaDTO(),new MunicipioDTO(),new AsentamientoDTO(),alumno);
        CapturaPersonaDTO b=factory.crear(persona(2),7L,"foto",new EntidadFederativaDTO(),new MunicipioDTO(),new AsentamientoDTO(),alumno);
        SsoElementoDTO elemento=new SsoElementoDTO();a.getElementos().add(elemento);
        assertEquals(2,servicio.guardarPersonasFuenteExterna(Arrays.asList(a,b),3,true));
        org.mockito.ArgumentCaptor<Iterable> captorRoles=org.mockito.ArgumentCaptor.forClass(Iterable.class);
        verify(roles).save(captorRoles.capture());List<RelPersonaRol> guardados=new ArrayList<>();for(Object r:captorRoles.getValue()) guardados.add((RelPersonaRol)r);
        assertEquals(2,guardados.size());assertNotEquals(guardados.get(0).getPersona().getIdPersona(),guardados.get(1).getPersona().getIdPersona());
        org.mockito.ArgumentCaptor<Iterable> captorElementos=org.mockito.ArgumentCaptor.forClass(Iterable.class);
        verify(elementos).save(captorElementos.capture());int total=0;for(Object e:captorElementos.getValue()){total++;assertEquals(a.getPersona().getIdPersona(),((SsoElemento)e).getPersona().getIdPersona());}
        assertEquals(1,total);verify(elementos,never()).saveAndFlush(any(SsoElemento.class));
        verify(repo).insertarAspirantesFuenteLote(anyList(),eq(3));verify(em).flush();verify(em).clear();
        org.springframework.transaction.annotation.Transactional tx=PersonaServiceImpl.class.getMethod("guardarPersonasFuenteExterna",List.class,Integer.class,boolean.class).getAnnotation(org.springframework.transaction.annotation.Transactional.class);
        assertEquals(org.springframework.transaction.annotation.Propagation.REQUIRES_NEW,tx.propagation());
    }
    @Test public void trabajoEsAsincronoProtegeDuplicadosYReportaBloquesConfirmados() throws Exception {
        verificarTrabajo(false,true);
    }
    @Test public void trabajoCompletaTodosLosBloquesConVinculacion() throws Exception {
        verificarTrabajo(true,false);
    }
    @Test public void trabajoCompletaTodosLosBloquesSinVinculacion() throws Exception {
        verificarTrabajo(false,false);
    }
    private void verificarTrabajo(boolean vincular,boolean fallaSegundoBloque) throws Exception {
        ImportacionUsuariosExternosService servicio=new ImportacionUsuariosExternosService();
        try {
            IUsuariosImportarRepo repo=mock(IUsuariosImportarRepo.class);PersonaService personas=mock(PersonaService.class);
            EntidadFederativaService entidades=mock(EntidadFederativaService.class);MunicipioService municipios=mock(MunicipioService.class);
            AsentamientoService asentamientos=mock(AsentamientoService.class);RoleService roles=mock(RoleService.class);
            PersonaExternaCapturaFactory factory=mock(PersonaExternaCapturaFactory.class);
            inyectar(servicio,"repository",repo);inyectar(servicio,"personaService",personas);inyectar(servicio,"entidadService",entidades);
            inyectar(servicio,"municipioService",municipios);inyectar(servicio,"asentamientoService",asentamientos);inyectar(servicio,"roleService",roles);inyectar(servicio,"factory",factory);
            org.springframework.transaction.support.AbstractPlatformTransactionManager transacciones=
                new org.springframework.transaction.support.AbstractPlatformTransactionManager() {
                    protected Object doGetTransaction(){return new Object();}
                    protected void doBegin(Object tx,org.springframework.transaction.TransactionDefinition definicion){}
                    protected void doCommit(org.springframework.transaction.support.DefaultTransactionStatus estado){}
                    protected void doRollback(org.springframework.transaction.support.DefaultTransactionStatus estado){}
                };
            inyectar(servicio,"transactionManager",transacciones);
            when(repo.resumenFuenteImportar(1,3)).thenReturn(new long[]{300,300});
            when(repo.verificarRelacionConvocatoria("3","3")).thenReturn(true);
            when(entidades.buscarPorId(11)).thenAnswer(inv->{
                assertTrue("Los catálogos deben convertirse con transacción activa",org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive());
                assertTrue(org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly());
                return new EntidadFederativaDTO();
            });
            when(municipios.buscarPorEntidadFederativa(11)).thenReturn(Arrays.asList(new MunicipioDTO()));
            when(asentamientos.buscarPorId("100010407")).thenReturn(new AsentamientoDTO());when(roles.buscarPorId(2)).thenReturn(new RolDTO());
            List<PersonaSigeDTO> primera=new ArrayList<>(),segunda=new ArrayList<>();for(int n=1;n<=300;n++) (n<=250?primera:segunda).add(persona(n));
            when(repo.paginaFuenteImportar(1,3,0L,300L,250)).thenReturn(primera);
            when(repo.paginaFuenteImportar(1,3,250L,300L,250)).thenReturn(segunda);
            when(factory.crear(any(PersonaSigeDTO.class),anyLong(),anyString(),any(EntidadFederativaDTO.class),any(MunicipioDTO.class),any(AsentamientoDTO.class),any(RolDTO.class))).thenReturn(new CapturaPersonaDTO());
            CountDownLatch entrando=new CountDownLatch(1),continuar=new CountDownLatch(1);
            AtomicInteger bloques=new AtomicInteger();
            when(personas.guardarPersonasFuenteExterna(anyList(),eq(3),eq(vincular))).thenAnswer(inv->{
                assertFalse("La sesión de catálogos debe cerrarse antes del guardado",org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive());
                if(bloques.incrementAndGet()==1) {entrando.countDown();assertTrue(continuar.await(5,TimeUnit.SECONDS));return 250;}
                if(fallaSegundoBloque) throw new IllegalStateException("Fallo de prueba sin datos sensibles");
                return 50;
            });
            String id=servicio.iniciar(1,3,vincular,7L,"foto");assertTrue(entrando.await(5,TimeUnit.SECONDS));
            assertFalse(servicio.consultar(id,7L).isTerminado());assertNull(servicio.consultar(id,8L));
            try {servicio.iniciar(1,3,vincular,7L,"foto");fail("Debe impedir un segundo trabajo de la misma fuente.");}catch(IllegalArgumentException esperado){}
            continuar.countDown();long fin=System.currentTimeMillis()+5000;
            while(!servicio.consultar(id,7L).isTerminado() && System.currentTimeMillis()<fin) Thread.sleep(10);
            ImportacionFuenteEstadoDTO estado=servicio.consultar(id,7L);assertTrue(estado.isTerminado());assertEquals(fallaSegundoBloque?250:300,estado.getImportados());
            assertEquals(fallaSegundoBloque?83:100,estado.getPorcentaje());
            if(fallaSegundoBloque) assertTrue(estado.getMensaje().contains("reintentar"));
            if(vincular) verify(repo,times(1)).verificarRelacionConvocatoria("3","3");
            else verify(repo,never()).verificarRelacionConvocatoria(anyString(),anyString());
            verify(personas,never()).guardarPersonas(anyList(),anyString(),anyBoolean());
        } finally {servicio.cerrar();}
    }
}
