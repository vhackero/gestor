package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionPreviaException;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean
@ViewScoped
public class InscripcionManualBean extends BaseBean {

	private static final long serialVersionUID = -4348522429538060563L;

	private static final Logger logger = Logger.getLogger(InscripcionManualBean.class);

	@ManagedProperty(value = "#{inscripcionFacade}")
	private InscripcionFacade inscripcionFacade;

	@ManagedProperty(value = "#{personaService}")
	private PersonaService personaService;

	private String mensajeDialog;

	private InscripcionContextoDTO contextoInscripcion;

	private Boolean aceptaTerminos;

	private Boolean mostrarAviso;

	private String tipoAviso;

	private String textoAviso;

	private String matriculaIngresada;

	public void buscarInscripcionDesdeMatricula() {
		logger.info(matriculaIngresada);
		cargarInscripcionEstudiante(matriculaIngresada);
	}

	private void cargarInscripcionEstudiante(String matricula) {
		try {
			inicializarInscripcion(matricula);
		} catch (InscripcionPreviaException ipe) {
			mostrarAviso(ipe.getMessage(), ConstantesGestorWeb.TIPO_AVISO_INFO);
		} catch (InscripcionException e) {
			manejarErrorDeInscripcion(e);
		} catch (Exception e) {
			manejarErrorGeneral(e);
		}
	}

	private void mostrarAviso(String mensaje, String tipo) {
		textoAviso = mensaje;
		mostrarAviso = Boolean.TRUE;
		tipoAviso = tipo;
	}

	private void ocultarAviso() {
		mostrarAviso = Boolean.FALSE;
	}

	public void validarSeleccionMateria(InscripcionMateriasDTO materiaSeleccionada) {
		if (esSeleccionEnFalse(materiaSeleccionada)) {
			return;
		}
		try {
			inscripcionFacade.validarSeleccionMateria(materiaSeleccionada, contextoInscripcion);
		} catch (InscripcionException ie) {
			manejarErrorSeleccionMateria(materiaSeleccionada, ie);
		} catch (Exception e) {
			manejarErrorGeneral(materiaSeleccionada, e);
		}

	}

	public void finalizarInscripcion() {
		try {
			inscripcionFacade.finalizarInscripcion(aceptaTerminos, contextoInscripcion);
			limpiarContextoInscripcion();
			mostrarMensajeDialog("Inscripcion de materias completa");
			mostrarAviso("Inscripcion de materias completa", ConstantesGestorWeb.TIPO_AVISO_SUCCESS);
		} catch (InscripcionException ie) {
			manejarErrorDeFinalizacion(ie);
		} catch (Exception e) {
			manejarErrorGeneral(e);
		}
	}

	private void inicializarInscripcion(String matricula) throws InscripcionException {
		limpiarBusqueda();
		Long idPersona = obtenerIdPersona(matricula);
		contextoInscripcion = obtenerContextoInscripcion(idPersona);
		aceptaTerminos = obtenerValorPorDefecto();
	}

	private void limpiarBusqueda() {
		limpiarContextoInscripcion();
		ocultarAviso();
		limpiarTerminos();
		limpiarTipoAviso();
		limpiarTextoAviso();
	}

	private void limpiarTextoAviso() {
		textoAviso = null;
	}

	private void limpiarTipoAviso() {
		tipoAviso = null;
	}

	private void limpiarTerminos() {
		aceptaTerminos = null;
	}

	private Long obtenerIdPersona(String matricula) throws InscripcionException {
		return personaService.obtenerIdPersonaPorMatricula(matricula)
				.orElseThrow(() -> new InscripcionException("No se encontró la matrícula: " + matricula));
	}

	private boolean obtenerValorPorDefecto() {
		return !contextoInscripcion.getTerminosCondiciones().getMostrarTerminosCondiciones();
	}

	private void limpiarContextoInscripcion() {
		contextoInscripcion = null;
	}

	private void manejarErrorGeneral(InscripcionMateriasDTO materiaSeleccionada, Exception e) {
		establecerSeleccionEnFalse(materiaSeleccionada);
		manejarErrorGeneral(e);
	}

	private void manejarErrorSeleccionMateria(InscripcionMateriasDTO materiaSeleccionada, InscripcionException ie) {
		establecerSeleccionEnFalse(materiaSeleccionada);
		mostrarMensajeDialog(ie.getMessage());
	}

	private void manejarErrorDeFinalizacion(InscripcionException ie) {
		mostrarMensajeDialog(ie.getMessage());
	}

	private void establecerSeleccionEnFalse(InscripcionMateriasDTO materiaSeleccionada) {
		materiaSeleccionada.setCheck(Boolean.FALSE);
	}

	private boolean esSeleccionEnFalse(InscripcionMateriasDTO materiaSeleccionada) {
		return materiaSeleccionada.getCheck().equals(Boolean.FALSE);
	}

	private InscripcionContextoDTO obtenerContextoInscripcion(Long idPersona) throws InscripcionException {
		return inscripcionFacade.obtenerContextoInscripcion(idPersona);
	}

	private void manejarErrorDeInscripcion(InscripcionException e) {
		mostrarMensajeDialog(e.getMessage());
	}

	private void manejarErrorGeneral(Exception e) {
		logger.error("Error inesperado: ", e);
		mostrarMensajeDialog("Ha ocurrido un error inesperado");
	}

	private void mostrarMensajeDialog(String mensaje) {
		this.mensajeDialog = mensaje;
		RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
	}

	public String getMensajeDialog() {
		return mensajeDialog;
	}

	public void setMensajeDialog(String mensajeDialog) {
		this.mensajeDialog = mensajeDialog;
	}

	public InscripcionContextoDTO getContextoInscripcion() {
		return contextoInscripcion;
	}

	public void setContextoInscripcion(InscripcionContextoDTO contextoInscripcion) {
		this.contextoInscripcion = contextoInscripcion;
	}

	public Boolean getAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(Boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}

	public InscripcionFacade getInscripcionFacade() {
		return inscripcionFacade;
	}

	public void setInscripcionFacade(InscripcionFacade inscripcionFacade) {
		this.inscripcionFacade = inscripcionFacade;
	}

	public Boolean getMostrarAviso() {
		return mostrarAviso;
	}

	public void setMostrarAviso(Boolean mostrarAviso) {
		this.mostrarAviso = mostrarAviso;
	}

	public String getTextoAviso() {
		return textoAviso;
	}

	public void setTextoAviso(String textoAviso) {
		this.textoAviso = textoAviso;
	}

	public String getTipoAviso() {
		return tipoAviso;
	}

	public void setTipoAviso(String tipoAviso) {
		this.tipoAviso = tipoAviso;
	}

	public String getMatriculaIngresada() {
		return matriculaIngresada;
	}

	public void setMatriculaIngresada(String matriculaIngresada) {
		this.matriculaIngresada = matriculaIngresada;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

}
