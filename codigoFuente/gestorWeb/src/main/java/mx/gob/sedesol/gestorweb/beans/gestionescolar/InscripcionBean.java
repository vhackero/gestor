package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionPreviaException;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean
@ViewScoped
public class InscripcionBean extends BaseBean {

	private static final long serialVersionUID = -4348522429538060563L;

	private static final Logger logger = Logger.getLogger(InscripcionBean.class);

	@ManagedProperty(value = "#{inscripcionFacade}")
	private InscripcionFacade inscripcionFacade;

	private String mensajeDialog;

	private InscripcionContextoDTO contextoInscripcion;

	private Boolean aceptaTerminos;

	private Boolean mostrarAviso;

	private String tipoAviso;

	private String textoAviso;

	@PostConstruct
	public void init() {
		try {
			inicializarInscripcion();
		} catch (InscripcionException e) {
			manejarErrorDeInscripcion(e);
		} catch (InscripcionPreviaException ipe) {
			mostrarAviso(ipe.getMessage(), ConstantesGestorWeb.TIPO_AVISO_INFO);
		} catch (Exception e) {
			manejarErrorGeneral(e);
		}
	}

	private void mostrarAviso(String mensaje, String tipo) {
		textoAviso = mensaje;
		mostrarAviso = Boolean.TRUE;
		tipoAviso = tipo;
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
			mostrarMensajeDialog("Inscripción de unidades didácticas realizada con éxito, por favor, revisa tu correo institucional.");
			mostrarAviso("Inscripción de unidades didácticas completada con exito.", ConstantesGestorWeb.TIPO_AVISO_SUCCESS);
		} catch (InscripcionException ie) {
			manejarErrorDeFinalizacion(ie);
		} catch (Exception e) {
			manejarErrorGeneral(e);
		}
	}

	private void inicializarInscripcion() throws InscripcionException {
		contextoInscripcion = obtenerContextoInscripcion();
		aceptaTerminos = obtenerValorPorDefecto();
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

	private InscripcionContextoDTO obtenerContextoInscripcion() throws InscripcionException {
		return inscripcionFacade.obtenerContextoInscripcion(getUsuarioEnSession().getIdPersona());
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
}
