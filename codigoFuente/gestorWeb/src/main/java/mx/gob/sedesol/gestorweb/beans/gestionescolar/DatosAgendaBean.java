package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionEscolarEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatCategoriaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionAva;
import mx.gob.sedesol.basegestor.springinit.GestionEscolarServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ReservacionAreasDTO;

@ManagedBean
@ViewScoped
public class DatosAgendaBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DatosAgendaBean.class);

    @ManagedProperty(value = "#{gestionEscolarServiceAdapter}")
    private GestionEscolarServiceAdapter gestionEscolarServiceAdapter;

    private Date fechaInicio;
    private Date fechaFinal;

    private List<CatalogoComunDTO> categEventoCapacitacion;
    private CatalogoComunDTO categEventoCapacitacionSelected;

    private List<CatalogoComunDTO> modalidades;
    private CatalogoComunDTO modalidadSelected;

    private List<CatalogoComunDTO> plataformasMoodle;
    private CatalogoComunDTO plataformaSelected;

    private List<CatalogoComunDTO> clasifAva;

    private CatalogoComunDTO avaSelected;

    private CatalogoComunDTO tipoAreaSeleccionado;
    private boolean completado;

    private ReservacionAreasDTO eventodto;

    private List<ReservacionAreasDTO> listaEventos;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        listaEventos = new ArrayList<ReservacionAreasDTO>();
        eventodto = new ReservacionAreasDTO();
        eventodto.setFechaHoraRegistro("20/11/2016 15:00 h");
        eventodto.setFechasReservadas("20/11/2016 28/11/2016");
        eventodto.setNombreEvento("Inducción a SEDESOL");
        listaEventos.add(eventodto);

        setCompletado(false);
        tipoAreaSeleccionado = new CatalogoComunDTO();
        modalidadSelected = new CatalogoComunDTO();
        modalidadSelected.setId(0);

        clasifAva = (List<CatalogoComunDTO>) gestionEscolarServiceAdapter
                .getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum.CAT_CLASIFICACION_AVA)
                .findAll(CatClasificacionAva.class);

        categEventoCapacitacion = (List<CatalogoComunDTO>) gestionEscolarServiceAdapter
                .getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum.CAT_CATEGORIA_EVENTO_CAPACITACION)
                .findAll(CatCategoriaEventoCapacitacion.class);

        modalidades = (List<CatalogoComunDTO>) getSession().getServletContext()
                .getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
    }

    public void accionBut() {
        completado = true;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<CatalogoComunDTO> getCategEventoCapacitacion() {
        return categEventoCapacitacion;
    }

    public void setCategEventoCapacitacion(List<CatalogoComunDTO> categEventoCapacitacion) {
        this.categEventoCapacitacion = categEventoCapacitacion;
    }

    public CatalogoComunDTO getCategEventoCapacitacionSelected() {
        return categEventoCapacitacionSelected;
    }

    public void setCategEventoCapacitacionSelected(CatalogoComunDTO categEventoCapacitacionSelected) {
        this.categEventoCapacitacionSelected = categEventoCapacitacionSelected;
    }

    public GestionEscolarServiceAdapter getGestionEscolarServiceAdapter() {
        return gestionEscolarServiceAdapter;
    }

    public void setGestionEscolarServiceAdapter(GestionEscolarServiceAdapter gestionEscolarServiceAdapter) {
        this.gestionEscolarServiceAdapter = gestionEscolarServiceAdapter;
    }

    public List<CatalogoComunDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<CatalogoComunDTO> modalidades) {
        this.modalidades = modalidades;
    }

    public CatalogoComunDTO getModalidadSelected() {
        return modalidadSelected;
    }

    public void setModalidadSelected(CatalogoComunDTO modalidadSelected) {
        this.modalidadSelected = modalidadSelected;
    }

    public List<CatalogoComunDTO> getPlataformasMoodle() {
        return plataformasMoodle;
    }

    public void setPlataformasMoodle(List<CatalogoComunDTO> plataformasMoodle) {
        this.plataformasMoodle = plataformasMoodle;
    }

    public CatalogoComunDTO getPlataformaSelected() {
        return plataformaSelected;
    }

    public void setPlataformaSelected(CatalogoComunDTO plataformaSelected) {
        this.plataformaSelected = plataformaSelected;
    }

    public List<CatalogoComunDTO> getClasifAva() {
        return clasifAva;
    }

    public void setClasifAva(List<CatalogoComunDTO> clasifAva) {
        this.clasifAva = clasifAva;
    }

    public CatalogoComunDTO getAvaSelected() {
        return avaSelected;
    }

    public void setAvaSelected(CatalogoComunDTO avaSelected) {
        this.avaSelected = avaSelected;
    }

    public CatalogoComunDTO getTipoAreaSeleccionado() {
        return tipoAreaSeleccionado;
    }

    public void setTipoAreaSeleccionado(CatalogoComunDTO tipoAreaSeleccionado) {
        this.tipoAreaSeleccionado = tipoAreaSeleccionado;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public ReservacionAreasDTO getEventodto() {
        return eventodto;
    }

    public void setEventodto(ReservacionAreasDTO eventodto) {
        this.eventodto = eventodto;
    }

    public List<ReservacionAreasDTO> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<ReservacionAreasDTO> listaEventos) {
        this.listaEventos = listaEventos;
    }

}
