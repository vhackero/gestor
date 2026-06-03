package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReconocimientoAsignaturaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.ReconocimientoAsignaturasService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean(name = "reconocimientoAsignaturasBean")
@ViewScoped
public class ReconocimientoAsignaturasBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReconocimientoAsignaturasBean.class);

	private String matricula;
	private String anioPeriodoSeleccionado;
	private String numeroElementosSeleccionado;
	private List<SelectItem> aniosPeriodo;
	private List<SelectItem> numerosElementos;
	private List<ReconocimientoAsignaturaDTO> resultados;
	private ReconocimientoAsignaturaDTO filaSeleccionada;

	@ManagedProperty(value = "#{reconocimientoAsignaturasService}")
	private ReconocimientoAsignaturasService reconocimientoAsignaturasService;

	@PostConstruct
	public void init() {
		aniosPeriodo = construirAniosPeriodo();
		numerosElementos = construirNumerosElementos();
		limpiar();
	}

	public void limpiar() {
		matricula = null;
		anioPeriodoSeleccionado = null;
		numeroElementosSeleccionado = null;
		filaSeleccionada = null;
		resultados = new ArrayList<ReconocimientoAsignaturaDTO>();
	}

	public void buscar() {
		if (StringUtils.isBlank(matricula) || StringUtils.isBlank(anioPeriodoSeleccionado)
				|| StringUtils.isBlank(numeroElementosSeleccionado)) {
			agregarMsgWarn("Debe capturar todos los criterios de búsqueda.", null);
			return;
		}

		String periodo = anioPeriodoSeleccionado.trim() + "-" + numeroElementosSeleccionado.trim();
		try {
			List<ReconocimientoAsignaturaDTO> encontrados = reconocimientoAsignaturasService
					.buscarPorMatriculaYPeriodo(matricula.trim(), periodo);
			logger.info("ReconocimientoAsignaturasBean.buscar - matricula=" + matricula.trim() + ", periodo=" + periodo
					+ ", encontrados=" + encontrados.size());
			resultados = encontrados;
			logger.info("ReconocimientoAsignaturasBean.buscar - filas asignadas=" + resultados.size());
			if (resultados.isEmpty()) {
				agregarMsgInfo("No se encontraron registros.", null);
			}
		} catch (Exception ex) {
			logger.error("Error al consultar reconocimiento de asignaturas", ex);
			agregarMsgError("Ocurrió un error al realizar la búsqueda.", null);
		}
	}

	public void prepararEliminar() {
		if (filaSeleccionada == null) {
			agregarMsgWarn("Debe seleccionar un registro válido.", null);
		}
	}

	public void prepararActualizar() {
		if (filaSeleccionada == null) {
			agregarMsgWarn("Debe seleccionar un registro válido.", null);
		}
	}

	public void eliminarRegistro() {
		if (filaSeleccionada == null || filaSeleccionada.getId() == null) {
			agregarMsgWarn("Debe seleccionar un registro válido.", null);
			return;
		}

		try {
			ResultadoDTO<RelGrupoParticipanteDTO> resultado = reconocimientoAsignaturasService
					.eliminarRegistro(filaSeleccionada, getUsuarioEnSession().getIdPersona());
			if (resultado != null && resultado.esCorrecto()) {
				resultados.remove(filaSeleccionada);
				agregarMsgInfo("Baja exitosa.", null);
			} else {
				agregarMsgError("Ocurrió un problema al eliminar el registro.", null);
			}
		} catch (Exception ex) {
			logger.error("Error al eliminar registro de reconocimiento de asignaturas", ex);
			agregarMsgError("Ocurrió un problema al eliminar el registro.", null);
		}
	}

	public void actualizarRegistro() {
		if (filaSeleccionada == null || filaSeleccionada.getId() == null) {
			agregarMsgWarn("Debe seleccionar un registro válido.", null);
			return;
		}

		try {
			boolean actualizado = reconocimientoAsignaturasService.actualizarRegistro(filaSeleccionada.getId());
			if (actualizado) {
				agregarMsgInfo("Registro actualizado con éxito", null);
			} else {
				agregarMsgError("Ocurrió un problema al actualizar el registro", null);
			}
		} catch (Exception ex) {
			logger.error("Error al actualizar registro de reconocimiento de asignaturas", ex);
			agregarMsgError("Ocurrió un problema al actualizar el registro", null);
		}
	}

	private List<SelectItem> construirAniosPeriodo() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		for (int anio = anioActual; anio >= anioActual - 20; anio--) {
			items.add(new SelectItem(String.valueOf(anio), String.valueOf(anio)));
		}
		return items;
	}

	private List<SelectItem> construirNumerosElementos() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (int numero = 1; numero <= 12; numero++) {
			items.add(new SelectItem(String.valueOf(numero), String.valueOf(numero)));
		}
		return items;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getAnioPeriodoSeleccionado() {
		return anioPeriodoSeleccionado;
	}

	public void setAnioPeriodoSeleccionado(String anioPeriodoSeleccionado) {
		this.anioPeriodoSeleccionado = anioPeriodoSeleccionado;
	}

	public String getNumeroElementosSeleccionado() {
		return numeroElementosSeleccionado;
	}

	public void setNumeroElementosSeleccionado(String numeroElementosSeleccionado) {
		this.numeroElementosSeleccionado = numeroElementosSeleccionado;
	}

	public List<SelectItem> getAniosPeriodo() {
		return aniosPeriodo;
	}

	public void setAniosPeriodo(List<SelectItem> aniosPeriodo) {
		this.aniosPeriodo = aniosPeriodo;
	}

	public List<SelectItem> getNumerosElementos() {
		return numerosElementos;
	}

	public void setNumerosElementos(List<SelectItem> numerosElementos) {
		this.numerosElementos = numerosElementos;
	}

	public List<ReconocimientoAsignaturaDTO> getResultados() {
		return resultados;
	}

	public void setResultados(List<ReconocimientoAsignaturaDTO> resultados) {
		this.resultados = resultados;
	}

	public ReconocimientoAsignaturaDTO getFilaSeleccionada() {
		return filaSeleccionada;
	}

	public void setFilaSeleccionada(ReconocimientoAsignaturaDTO filaSeleccionada) {
		this.filaSeleccionada = filaSeleccionada;
	}

	public ReconocimientoAsignaturasService getReconocimientoAsignaturasService() {
		return reconocimientoAsignaturasService;
	}

	public void setReconocimientoAsignaturasService(ReconocimientoAsignaturasService reconocimientoAsignaturasService) {
		this.reconocimientoAsignaturasService = reconocimientoAsignaturasService;
	}
}
