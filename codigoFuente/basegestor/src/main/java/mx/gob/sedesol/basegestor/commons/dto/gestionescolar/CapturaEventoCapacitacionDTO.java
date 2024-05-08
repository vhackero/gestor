package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SolicitudReservacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;

public class CapturaEventoCapacitacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private EventoCapacitacionDTO evento;
	private FichaDescProgramaDTO programa;
	private Integer idPrograma;
	private Integer idCoordinador;
	private Integer idProductor;

	private List<PersonaResponsabilidadesDTO> coordinadores = new ArrayList<>();
	private List<PersonaResponsabilidadesDTO> facilitadores = new ArrayList<>();
	private List<PersonaResponsabilidadesDTO> productores = new ArrayList<>();

	private String numeroHoras;
	private String numeroMinutos;

	private String rutaCompletaImagen;

	private Integer tipoAreaSeleccionado;

	private EncuestaDTO encuesta;

	// reservaciones
	private SolicitudReservacionDTO solicitud;

	private List<ReservacionEventoCapacitacionDTO> reservaciones;
	
	private CveEventoCapDTO cec;
	

	public CveEventoCapDTO getCec() {
		return cec;
	}

	public void setCec(CveEventoCapDTO cec) {
		this.cec = cec;
	}

	public CapturaEventoCapacitacionDTO() {
		evento = new EventoCapacitacionDTO();
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}

	public FichaDescProgramaDTO getPrograma() {
		return programa;
	}

	public void setPrograma(FichaDescProgramaDTO programa) {
		this.programa = programa;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getNumeroHoras() {
		return numeroHoras;
	}

	public void setNumeroHoras(String numeroHoras) {
		this.numeroHoras = numeroHoras;
	}

	public String getNumeroMinutos() {
		return numeroMinutos;
	}

	public void setNumeroMinutos(String numeroMinutos) {
		this.numeroMinutos = numeroMinutos;
	}

	public List<PersonaResponsabilidadesDTO> getCoordinadores() {
		return coordinadores;
	}

	public void setCoordinadores(List<PersonaResponsabilidadesDTO> coordinadores) {
		this.coordinadores = coordinadores;
	}

	public List<PersonaResponsabilidadesDTO> getFacilitadores() {
		return facilitadores;
	}

	public void setFacilitadores(List<PersonaResponsabilidadesDTO> facilitadores) {
		this.facilitadores = facilitadores;
	}

	public List<PersonaResponsabilidadesDTO> getProductores() {
		return productores;
	}

	public void setProductores(List<PersonaResponsabilidadesDTO> productores) {
		this.productores = productores;
	}

	public List<ReservacionEventoCapacitacionDTO> getReservaciones() {
		return reservaciones;
	}

	public void setReservaciones(List<ReservacionEventoCapacitacionDTO> reservaciones) {
		this.reservaciones = reservaciones;
	}

	public SolicitudReservacionDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudReservacionDTO solicitud) {
		this.solicitud = solicitud;
	}

	public Integer getIdCoordinador() {
		return idCoordinador;
	}

	public void setIdCoordinador(Integer idCoordinador) {
		this.idCoordinador = idCoordinador;
	}

	public Integer getIdProductor() {
		return idProductor;
	}

	public void setIdProductor(Integer idProductor) {
		this.idProductor = idProductor;
	}

	public String getRutaCompletaImagen() {
		return rutaCompletaImagen;
	}

	public void setRutaCompletaImagen(String rutaCompletaImagen) {
		this.rutaCompletaImagen = rutaCompletaImagen;
	}

	public Integer getTipoAreaSeleccionado() {
		return tipoAreaSeleccionado;
	}

	public void setTipoAreaSeleccionado(Integer tipoAreaSeleccionado) {
		this.tipoAreaSeleccionado = tipoAreaSeleccionado;
	}

	public EncuestaDTO getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(EncuestaDTO encuesta) {
		this.encuesta = encuesta;
	}

}
