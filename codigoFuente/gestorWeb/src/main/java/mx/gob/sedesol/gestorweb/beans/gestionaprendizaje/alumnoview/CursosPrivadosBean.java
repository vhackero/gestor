package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelSolicitudEnrolamientoEvtCapDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelSolicitudEnrolEvtCapService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class CursosPrivadosBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(CursosPrivadosBean.class);

    @ManagedProperty("#{sistema}")
    private SistemaBean textosSistema;

    @ManagedProperty(value = "#{relSolicitudEnrolEvtCapService}")
    private RelSolicitudEnrolEvtCapService relSolicitudEnrolEvtCapService;

    private String rutaImagenes;
    private String rutaUndertow;
    private String nombreImagenComun;
    private String filtroCursos;
    private EventoCapacitacionDTO eventoTemp = new EventoCapacitacionDTO();

    @ManagedProperty(value = "#{eventoCapacitacionService}")
    private EventoCapacitacionService eventoCapacitacionService;

    @ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
    private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

    private List<EventoCapacitacionDTO> eventos = new ArrayList<EventoCapacitacionDTO>();
    private List<EventoCapacitacionDTO> eventosTodos = new ArrayList<EventoCapacitacionDTO>();

    @PostConstruct
    public void init() {

        nombreImagenComun = eventoCapacitacionServiceFacade.obtenerNombreImagenComun();
        rutaUndertow = eventoCapacitacionServiceFacade.obtenerRutaUndertow();
        eventosTodos = eventoCapacitacionService.consultaEventoPorEstatus(ConstantesGestor.EVEN_CAP_ESTATUS_EJECUCION);
        crearRutaRelativa();
        eventos = eventosTodos;
        
        if(eventos.isEmpty()){
        	log.info("Eventos viene vacio");
        }
        for (EventoCapacitacionDTO eventoCapacitacionDTO : eventos) {
			log.info("Evento: " + eventoCapacitacionDTO.getNombreEc());
		}
    }

    public void validarSolicitud() {
        List<EventoCapacitacionDTO> evento = relSolicitudEnrolEvtCapService.obtenerSolicitudesPorIdPersonaPrograma(
                getUsuarioEnSession().getIdPersona(), true, eventoTemp.getIdPrograma());
        RequestContext context = RequestContext.getCurrentInstance();

        if (evento.isEmpty()) {
            context.execute("PF('wdlgConfirmacionSolicitud').show()");
        } else {
            context.execute("PF('wdlgAlertaSolicitud').show()");
        }
    }

    public void agregarSolicitud() {
        RelSolicitudEnrolamientoEvtCapDTO solicitud = new RelSolicitudEnrolamientoEvtCapDTO();
        solicitud.setActivo(true);
        solicitud.setFechaActualizacion(new Date());
        solicitud.setFechaRegistro(new Date());
        solicitud.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        solicitud.setIdPersona(getUsuarioEnSession().getIdPersona());
        solicitud.setIdEventoCapacitacion(eventoTemp.getIdEvento());
        //GUSTAVO --BitacoraUtil.llenarBitacora(solicitud, getUsuarioEnSession(), ConstantesBitacora.SOLICITUD_EVENTO_CAPACITACION_AGREGAR, request);
        relSolicitudEnrolEvtCapService.crearSolicitud(solicitud);
        agregarMsgInfo("Solicitud realizada", null, getTextosSistema());
    }

    private void crearRutaRelativa() {
        for (EventoCapacitacionDTO evento : eventosTodos) {
            if (ObjectUtils.isNullOrEmpty(evento.getUrlImagen())) {
                evento.setRutaRelativa(rutaUndertow + nombreImagenComun);
            } else {
                evento.setRutaRelativa(rutaUndertow + evento.getUrlImagen());
            }

        }
    }

    public void filtrarCursos() {
        if (this.filtroCursos == null || this.filtroCursos.equals("")) {
            eventos = eventosTodos;
        } else {
            // cursosPublicoGeneral = new LinkedList<Curso>();
            // cursosPublicoGeneral.add(cursosTodos.get(0));
            eventos = eventosTodos.parallelStream().filter(c -> c.hayCoincidencia(this.filtroCursos))
                    .collect(Collectors.toList());
        }
    }

    public EventoCapacitacionService getEventoCapacitacionService() {
        return eventoCapacitacionService;
    }

    public void setEventoCapacitacionService(EventoCapacitacionService eventoCapacitacionService) {
        this.eventoCapacitacionService = eventoCapacitacionService;
    }

    public List<EventoCapacitacionDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoCapacitacionDTO> eventos) {
        this.eventos = eventos;
    }

    public String getRutaImagenes() {
        return rutaImagenes;
    }

    public void setRutaImagenes(String rutaImagenes) {
        this.rutaImagenes = rutaImagenes;
    }

    public String getRutaUndertow() {
        return rutaUndertow;
    }

    public void setRutaUndertow(String rutaUndertow) {
        this.rutaUndertow = rutaUndertow;
    }

    public String getNombreImagenComun() {
        return nombreImagenComun;
    }

    public void setNombreImagenComun(String nombreImagenComun) {
        this.nombreImagenComun = nombreImagenComun;
    }

    public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
        return eventoCapacitacionServiceFacade;
    }

    public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
        this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
    }

    public String getFiltroCursos() {
        return filtroCursos;
    }

    public void setFiltroCursos(String filtroCursos) {
        this.filtroCursos = filtroCursos;
    }

    public List<EventoCapacitacionDTO> getEventosTodos() {
        return eventosTodos;
    }

    public void setEventosTodos(List<EventoCapacitacionDTO> eventosTodos) {
        this.eventosTodos = eventosTodos;
    }

    public EventoCapacitacionDTO getEventoTemp() {
        return eventoTemp;
    }

    public void setEventoTemp(EventoCapacitacionDTO eventoTemp) {
        this.eventoTemp = eventoTemp;
    }

    public RelSolicitudEnrolEvtCapService getRelSolicitudEnrolEvtCapService() {
        return relSolicitudEnrolEvtCapService;
    }

    public void setRelSolicitudEnrolEvtCapService(RelSolicitudEnrolEvtCapService relSolicitudEnrolEvtCapService) {
        this.relSolicitudEnrolEvtCapService = relSolicitudEnrolEvtCapService;
    }

    public SistemaBean getTextosSistema() {
        return textosSistema;
    }

    public void setTextosSistema(SistemaBean textosSistema) {
        this.textosSistema = textosSistema;
    }

}
