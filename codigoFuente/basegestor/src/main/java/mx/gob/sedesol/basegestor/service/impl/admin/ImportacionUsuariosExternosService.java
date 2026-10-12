package mx.gob.sedesol.basegestor.service.impl.admin;

import java.util.*;
import java.util.concurrent.*;
import javax.annotation.PreDestroy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.utils.PersonaExternaCapturaFactory;
import mx.gob.sedesol.basegestor.model.repositories.admin.IUsuariosImportarRepo;
import mx.gob.sedesol.basegestor.service.admin.*;

@Service("importacionUsuariosExternosService")
public class ImportacionUsuariosExternosService {
    private static final Logger LOGGER=Logger.getLogger(ImportacionUsuariosExternosService.class);
    @Autowired private IUsuariosImportarRepo repository;
    @Autowired private PersonaService personaService;
    @Autowired private EntidadFederativaService entidadService;
    @Autowired private MunicipioService municipioService;
    @Autowired private AsentamientoService asentamientoService;
    @Autowired private RoleService roleService;
    @Autowired private PersonaExternaCapturaFactory factory;
    @Autowired private org.springframework.transaction.PlatformTransactionManager transactionManager;
    private final Map<String,ImportacionFuenteEstadoDTO> trabajos=new ConcurrentHashMap<>();
    private final Map<String,String> activos=new ConcurrentHashMap<>();
    private final ThreadPoolExecutor executor=new ThreadPoolExecutor(1,1,0,TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(2),r->{Thread t=new Thread(r,"gestor-importacion-fuente-externa");t.setDaemon(true);return t;});

    @PreAuthorize("hasPermission(null, 'IMP_FUEN_EXT')")
    public synchronized String iniciar(Integer fuente,Integer convocatoria,boolean vincular,Long usuario,String foto) {
        if(fuente==null || convocatoria==null || usuario==null) throw new IllegalArgumentException("Seleccione fuente externa y convocatoria.");
        String clave=fuente+":"+convocatoria;
        if(activos.containsKey(clave)) throw new IllegalArgumentException("Ya existe una importación en curso para esa fuente y convocatoria.");
        trabajos.entrySet().removeIf(e->e.getValue().isTerminado() && System.currentTimeMillis()-e.getValue().getFin()>3600000);
        long[] resumen=repository.resumenFuenteImportar(fuente,convocatoria);
        String id=UUID.randomUUID().toString();
        ImportacionFuenteEstadoDTO estado=new ImportacionFuenteEstadoDTO(id,usuario,(int)Math.min(Integer.MAX_VALUE,resumen[1]));
        trabajos.put(id,estado);
        if(resumen[1]==0) {estado.finalizar("No hay usuarios pendientes de importar.");return id;}
        activos.put(clave,id);
        try {
            executor.execute(new DelegatingSecurityContextRunnable(()->ejecutar(estado,clave,fuente,convocatoria,vincular,usuario,foto,resumen[0])));
        } catch(RejectedExecutionException e) {
            activos.remove(clave);estado.finalizar("Hay otras importaciones en curso; intente nuevamente más tarde.");
        }
        return id;
    }
    private void ejecutar(ImportacionFuenteEstadoDTO estado,String clave,Integer fuente,Integer convocatoria,
            boolean vincular,Long usuario,String foto,long corte) {
        try {
            Catalogos catalogos=cargarCatalogos();
            long ultimo=0;Set<String> planesValidados=new HashSet<>();
            while(!Thread.currentThread().isInterrupted()) {
                List<PersonaSigeDTO> pagina=repository.paginaFuenteImportar(fuente,convocatoria,ultimo,corte,250);
                if(pagina.isEmpty()) break;
                List<CapturaPersonaDTO> datos=new ArrayList<>(pagina.size());
                for(PersonaSigeDTO persona:pagina) {
                    if(vincular && planesValidados.add(persona.getProgramaEducativo())
                            && !repository.verificarRelacionConvocatoria(persona.getProgramaEducativo(),convocatoria.toString()))
                        throw new IllegalArgumentException("Hay un plan no asociado a la convocatoria seleccionada.");
                    datos.add(factory.crear(persona,usuario,foto,catalogos.sede,catalogos.municipio,catalogos.asentamiento,catalogos.alumno));
                }
                int guardados=personaService.guardarPersonasFuenteExterna(datos,convocatoria,vincular);
                estado.avanzar(guardados);ultimo=pagina.get(pagina.size()-1).getIdPersonaSige();
            }
            if(Thread.currentThread().isInterrupted()) throw new IllegalStateException("Proceso interrumpido.");
            estado.finalizar("Se importaron "+estado.getImportados()+" usuarios. Los usuarios ya registrados se excluyeron de la importación.");
        } catch(Exception e) {
            String motivo=describirError(e);
            LOGGER.error("Importacion de fuente externa "+estado.getId()+" detenida: "+motivo);
            estado.finalizar(motivo+" Se conservaron "+estado.getImportados()+" usuarios importados. Puede reintentar los pendientes. Referencia: "+estado.getId());
        } finally {
            if(!estado.isTerminado()) estado.finalizar("Importación interrumpida. Puede reintentar los usuarios pendientes.");
            activos.remove(clave);
        }
    }
    private Catalogos cargarCatalogos() {
        // El trabajador no tiene OpenEntityManagerInView: mantener la sesión abierta
        // mientras los servicios convierten las relaciones LAZY a DTO.
        org.springframework.transaction.support.TransactionTemplate lectura=
            new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        lectura.setReadOnly(true);
        return lectura.execute(estado->{
            Catalogos resultado=new Catalogos();
            resultado.sede=entidadService.buscarPorId(11);
            List<MunicipioDTO> municipios=municipioService.buscarPorEntidadFederativa(11);
            resultado.asentamiento=asentamientoService.buscarPorId("100010407");
            resultado.alumno=roleService.buscarPorId(2);
            if(resultado.sede==null || municipios==null || municipios.isEmpty() || resultado.asentamiento==null || resultado.alumno==null)
                throw new IllegalArgumentException("Faltan catálogos requeridos por la importación de usuarios.");
            resultado.municipio=municipios.get(0);
            return resultado;
        });
    }
    private static class Catalogos {
        EntidadFederativaDTO sede;
        MunicipioDTO municipio;
        AsentamientoDTO asentamiento;
        RolDTO alumno;
    }
    static String describirError(Throwable error) {
        Set<Throwable> visitados=Collections.newSetFromMap(new IdentityHashMap<Throwable,Boolean>());
        Throwable raiz=error;
        while(raiz.getCause()!=null && visitados.add(raiz) && !visitados.contains(raiz.getCause())) raiz=raiz.getCause();
        // El mensaje SQL puede incluir valores de las filas, incluidas contraseñas.
        // Mostrar sólo códigos y nombres de campos, nunca el SQL ni sus parámetros.
        visitados.clear();
        for(Throwable causa=error;causa!=null && visitados.add(causa);causa=causa.getCause()) {
            if(causa instanceof java.sql.SQLException) return describirSql((java.sql.SQLException)causa);
        }
        if(raiz instanceof java.sql.SQLException) return describirSql((java.sql.SQLException)raiz);
        if(raiz instanceof org.springframework.security.access.AccessDeniedException)
            return "El usuario no tiene permiso para ejecutar la importación.";
        if(raiz instanceof javax.validation.ConstraintViolationException) {
            StringBuilder campos=new StringBuilder();
            for(javax.validation.ConstraintViolation<?> v:((javax.validation.ConstraintViolationException)raiz).getConstraintViolations()) {
                if(campos.length()>0) campos.append(", ");
                campos.append(v.getPropertyPath());
            }
            return "Datos que no cumplen las validaciones de los campos: "+campos+".";
        }
        String mensaje=raiz.getMessage();
        if(mensaje==null || mensaje.trim().isEmpty()) mensaje="sin detalle adicional";
        mensaje=mensaje.replaceAll("(?is)\\b(insert|update|select|delete)\\s+.*", "[consulta omitida]")
            .replaceAll("'[^']*'|\"[^\"]*\"", "[valor omitido]")
            .replaceAll("(?i)(password|contras[eñ]a|token)\\s*[:=]\\s*\\S+", "$1=[omitido]")
            .replaceAll("[\\r\\n]+", " ");
        if(mensaje.length()>350) mensaje=mensaje.substring(0,350)+"…";
        return "No fue posible importar el bloque: "+raiz.getClass().getSimpleName()+": "+mensaje;
    }
    private static String describirSql(java.sql.SQLException error) {
        String texto=error.getMessage()==null?"":error.getMessage();
        java.util.regex.Matcher campo=java.util.regex.Pattern.compile("(?i)(?:column|key|constraint) ['`]([a-zA-Z0-9_.-]+)['`]").matcher(texto);
        String detalle=campo.find()?" Campo o restricción: "+campo.group(1)+".":"";
        String motivo;
        switch(error.getErrorCode()) {
            case 1062: motivo="Existe un dato duplicado que debe ser único.";break;
            case 1048: case 1364: motivo="Falta un dato obligatorio.";break;
            case 1406: motivo="Un dato excede la longitud permitida.";break;
            case 1451: case 1452: motivo="Una relación apunta a un registro inexistente o impide el guardado.";break;
            case 1205: case 1213: motivo="La base de datos bloqueó la operación; intente nuevamente.";break;
            default: motivo="La base de datos rechazó la operación.";
        }
        return motivo+detalle+" Código BD: "+error.getErrorCode()+"; SQLState: "+error.getSQLState()+".";
    }
    public ImportacionFuenteEstadoDTO consultar(String id,Long usuario) {
        ImportacionFuenteEstadoDTO estado=id==null?null:trabajos.get(id);
        return estado!=null && estado.getUsuario().equals(usuario)?estado:null;
    }
    @PreDestroy public void cerrar() {executor.shutdownNow();}
}
