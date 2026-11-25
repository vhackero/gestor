package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.utils.ArchivoPersonaExterna;
import mx.gob.sedesol.basegestor.service.admin.PersonaExternaCargaService;

@ManagedBean
@ViewScoped
public class PersonaExternaCargaBean implements Serializable {
    private static final long serialVersionUID=1L;
    private static final Logger LOGGER=Logger.getLogger(PersonaExternaCargaBean.class);
    @ManagedProperty("#{personaExternaCargaService}")
    private PersonaExternaCargaService personaExternaCargaService;
    private transient byte[] contenido;
    private String nombreArchivo;
    private List<String> errores=new ArrayList<>();

    public void cargarArchivo(FileUploadEvent event) {
        contenido=null;nombreArchivo=null;errores.clear();
        String nombre=event.getFile().getFileName();
        if(nombre==null || !nombre.toLowerCase(Locale.ROOT).matches(".*\\.(csv|xls|xlsx)$")
                || event.getFile().getSize()>ArchivoPersonaExterna.MAX_BYTES || event.getFile().getSize()==0) {
            mensaje(FacesMessage.SEVERITY_ERROR,"Seleccione un archivo CSV, XLS o XLSX de hasta 5 MB.");return;
        }
        contenido=event.getFile().getContents();nombreArchivo=nombre;
        mensaje(FacesMessage.SEVERITY_INFO,"Archivo seleccionado. Pulse Importar para validar y guardar.");
    }
    public void importar() {
        errores.clear();
        if(contenido==null) {mensaje(FacesMessage.SEVERITY_ERROR,"Seleccione un archivo antes de importar.");return;}
        try {
            List<PersonaExternaCargaDTO> filas=ArchivoPersonaExterna.leer(nombreArchivo,contenido);
            ResultadoCargaExternaDTO resultado=personaExternaCargaService.importar(filas);
            errores.addAll(resultado.getErrores());
            if(errores.isEmpty()) {
                contenido=null;nombreArchivo=null;
                mensaje(FacesMessage.SEVERITY_INFO,"Se importaron "+resultado.getInsertados()+" registros a la fuente externa.");
            } else mensaje(FacesMessage.SEVERITY_ERROR,"No se importaron registros. Corrija los errores del archivo (se muestran hasta 100).");
        } catch(IllegalArgumentException e) {
            errores.add(e.getMessage());mensaje(FacesMessage.SEVERITY_ERROR,"No se importaron registros. Revise el archivo.");
        } catch(Exception e) {
            // No registrar la excepcion SQL: algunos motores incluyen los valores y la contraseña en ella.
            LOGGER.error("Carga de personas externas rechazada: "+e.getClass().getSimpleName());
            mensaje(FacesMessage.SEVERITY_ERROR,"No se importaron registros. Verifique si un usuario fue agregado simultáneamente o si el archivo es válido.");
        } finally { contenido=null; }
    }
    public StreamedContent getPlantilla() {
        try {
            byte[] plantilla=ArchivoPersonaExterna.plantilla(personaExternaCargaService.catalogosPlantilla());
            return new DefaultStreamedContent(new ByteArrayInputStream(plantilla),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","plantilla_personas_externos.xlsx");
        } catch(Exception e) {mensaje(FacesMessage.SEVERITY_ERROR,"No fue posible generar la plantilla.");return null;}
    }
    private void mensaje(FacesMessage.Severity tipo,String texto) {FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(tipo,texto,null));}
    public String getNombreArchivo() {return nombreArchivo;}
    public boolean isArchivoSeleccionado() {return contenido!=null;}
    public List<String> getErrores() {return errores;}
    public List<String> getColumnas() {return PersonaExternaCargaDTO.COLUMNAS;}
    public void setPersonaExternaCargaService(PersonaExternaCargaService servicio) {personaExternaCargaService=servicio;}
}
