package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesReportesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;

@SessionScoped
@ManagedBean
public class ReporteProgramaBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private StreamedContent pdfFECMedia;

	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	public ReporteProgramaBean() {

	}

	/**
	 *
	 * @param programaSeleccionado
	 */
	public void verFECenVisor(FichaDescProgramaDTO programaSeleccionado) {
		if (!ObjectUtils.isNullOrEmpty(programaSeleccionado) || !ObjectUtils.isNullOrEmpty(programaSeleccionado)) {
			pdfFECMedia = new DefaultStreamedContent();

			List<FichaDescProgramaDTO> FichaDescProgramaDTOs = new ArrayList<FichaDescProgramaDTO>();
			FichaDescProgramaDTOs.add(programaSeleccionado);

			ReporteConfig reporteConfig = new ReporteConfig();
			reporteConfig.setNombreReporte(programaSeleccionado.getNombreTentativo());
			reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);
			reporteConfig.setPathJasper(ConstantesReportesGestorWeb.PATH_JASPER_PLANES_PROGRAMAS);
			reporteConfig.setDatos(FichaDescProgramaDTOs);

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			List<RelEstUnidadDidacticaDTO> estTemUnidad = fecServiceFacade.getEstructuraTematicaService()
					.obtieneRelEstUnidadDidPorPrograma(programaSeleccionado.getIdPrograma());

			if (!ObjectUtils.isNullOrEmpty(estTemUnidad)) {
				List<DetEstUniDidacticaDTO> unidades = new ArrayList<>();

				for (RelEstUnidadDidacticaDTO rud : estTemUnidad) {
					unidades.add(rud.getDetEstUnidadDidactica());
				}
				parametros.put("DS_UNIDADES_DIDACTICAS", unidades);
			}
			/* Se agrega la imagen de fondo */
			String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme()
					+ "://" + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
					+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
					+ "/recursos/documentos/fondoConstancia.png";

			reporteConfig.setParametros(parametros);
			parametros.put("IMAGEN_URL", rutaFondoConstancia);
			pdfFECMedia = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
					ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_PDF, programaSeleccionado.getNombreTentativo());

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_PRO_PDF",
					String.valueOf(programaSeleccionado.getIdPrograma()), requestActual(), TipoServicioEnum.LOCAL);

			RequestContext.getCurrentInstance().execute("PF('visorPDFProg').show()");

		} else {
			return;
		}
	}

	/**
	 * @return the pdfFECMedia
	 */
	public StreamedContent getPdfFECMedia() {
		return pdfFECMedia;
	}

	/**
	 * @param pdfFECMedia
	 *            the pdfFECMedia to set
	 */
	public void setPdfFECMedia(StreamedContent pdfFECMedia) {
		this.pdfFECMedia = pdfFECMedia;
	}

	/**
	 * @return the fecServiceFacade
	 */
	public FECServiceFacade getFecServiceFacade() {
		return fecServiceFacade;
	}

	/**
	 * @param fecServiceFacade
	 *            the fecServiceFacade to set
	 */
	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
